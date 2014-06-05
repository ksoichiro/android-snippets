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

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CallHookHookedActivity extends Activity {

    private String mNumber;
    public static final String URI_FRAGMENT = "callhook";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_hook_hooked);

        Intent from = getIntent();

        if (from == null) {
            return;
        }

        mNumber = from.getStringExtra("number");
        if (TextUtils.isEmpty(mNumber)) {
            return;
        }

        ((TextView) findViewById(R.id.text_number)).setText(mNumber);

        ((Button) findViewById(R.id.button_call)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNormally();
            }
        });

        ((Button) findViewById(R.id.button_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callNormally() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mNumber
                + "#" + URI_FRAGMENT));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
