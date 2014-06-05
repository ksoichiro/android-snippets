/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.snippet.app;

import com.snippet.R;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class ProgressDialogActivity extends FragmentActivity {
    private static final String DIALOG_TAG = "dialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dialog);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTask();
            }
        });
    }

    private void startTask() {
        DummyTask task = new DummyTask();
        task.execute((Void) null);
    }

    public void showDialogFragment(final DialogFragment dialogFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        // Don't add to back stack. Adding to back stack makes popping up
        // removed fragment when DialogFragment crushed with IllegalStateException.
        //ft.addToBackStack(null);

        if (dialogFragment != null) {
            dialogFragment.show(ft, DIALOG_TAG);
        }
    }

    public void dismissDialogFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogFragment prev = (DialogFragment) getSupportFragmentManager().findFragmentByTag(
                DIALOG_TAG);
        if (prev != null) {
            try {
                prev.dismiss();
            } catch (IllegalStateException e) {
                // Just ignore this exception
                e.printStackTrace();
            }
            ft.remove(prev);
            ft.commitAllowingStateLoss();
        }
    }

    public void showProgressDialog(final int resId) {
        showDialogFragment(ProgressDialogFragment.newInstance(getString(resId)));
    }

    public void dismissProgressDialog() {
        dismissDialogFragment();
    }

    /**
     * Fragment for ProgressDialog.<br>
     * Note that DialogFragment needs empty public constructor. So we should
     * instantiate this class with factory method.<br>
     * If we use DialogFragment as inner class, we should make it public static.
     * 
     * @author Soichiro Kashima
     */
    public static class ProgressDialogFragment extends DialogFragment {
        private static final String MESSAGE = "message";
        private static final String DISMISSED = "dismissed";

        /**
         * Empty constructor.
         */
        public ProgressDialogFragment() {
        }

        public static ProgressDialogFragment newInstance(final String message) {
            ProgressDialogFragment fragment = new ProgressDialogFragment();

            // Arguments must be handled with getArguments() and setArguments().
            // The argument "dismissed" is the key. we must dismiss by ourselves
            // if the DialogFragment crushed by IllegalStateException.
            // "Dismissed" is the mark that we must dismiss it.
            Bundle args = new Bundle();
            args.putString(MESSAGE, message);
            args.putBoolean(DISMISSED, false);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            Bundle args = getArguments();
            args.putBoolean(DISMISSED, true);
        }

        @Override
        public void onResume() {
            super.onResume();

            // Dismiss dialog when DialogFragment crushed
            if (getArguments().getBoolean(DISMISSED)) {
                dismiss();
            }
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getArguments().getString(MESSAGE));
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }

    private class DummyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(R.string.msg_progress);
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Just sleep for a while...
            final int sleepMillis = 10000;
            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dismissProgressDialog();
        }
    }
}
