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
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MashroomActivity extends Activity {
    private static final int REQUEST_CODE = 1;
    private static final String REPLACE_KEY = "replace_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mashroom);

        findViewById(R.id.btn_launch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                launch();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ((TextView) findViewById(R.id.new_string))
                        .setText(data.getStringExtra(REPLACE_KEY));
            }
        }
    }

    private void launch() {
        Intent intent = new Intent();
        intent.setAction("com.adamrocker.android.simeji.ACTION_INTERCEPT");
        intent.addCategory("com.adamrocker.android.simeji.REPLACE");
        String value = ((TextView) findViewById(R.id.old_string)).getText().toString();
        intent.putExtra(REPLACE_KEY, value);
        startActivityForResult(intent, REQUEST_CODE);
    }

}
