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
import com.snippet.widget.AlertDialogFragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class DialogActivity extends FragmentActivity {
    private static final String DIALOG_TAG = "dialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog("Title", "The quick brown fox jumps over the lazy dog.");
            }
        });
    }

    private void showAlertDialog(final String title, final String message) {
        AlertDialogFragment dialogFragment = AlertDialogFragment.newInstance();
        dialogFragment.setTitle(title);
        dialogFragment.setMessage(message);
        showDialogFragment(dialogFragment);
    }

    public void showDialogFragment(final DialogFragment dialogFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        // Don't add to back stack. Adding to back stack makes popping up
        // removed fragment when DialogFragment crushed with
        // IllegalStateException.
        // ft.addToBackStack(null);

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

}
