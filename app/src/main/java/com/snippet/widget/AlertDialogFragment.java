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

package com.snippet.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;

public class AlertDialogFragment extends DialogFragment {

    private static final String ARG_ICON = "icon";
    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";
    private static final String ARG_CANCELABLE = "cancelable";

    public static AlertDialogFragment newInstance() {
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setIcon(final int icon) {
        getArguments().putInt(ARG_ICON, icon);
    }

    public void setTitle(final String title) {
        getArguments().putString(ARG_TITLE, title);
    }

    public void setMessage(final String message) {
        getArguments().putString(ARG_MESSAGE, message);
    }

    public void setCancelable(final boolean cancelable) {
        getArguments().putBoolean(ARG_CANCELABLE, cancelable);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        Bundle args = getArguments();
        int icon = args.getInt(ARG_ICON);
        String title = args.getString(ARG_TITLE);
        String message = args.getString(ARG_MESSAGE);
        boolean cancelable = args.getBoolean(ARG_CANCELABLE, true);

        setCancelable(cancelable);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (icon != 0) {
            builder.setIcon(android.R.drawable.ic_dialog_info);
        }
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(cancelable);

        return dialog;
    }

}
