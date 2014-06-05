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
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class FontActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font);
        init();
    }

    private void init() {
        ViewGroup parent = (ViewGroup) findViewById(R.id.parent);
        String fontsDir = "fonts";

        try {
            String[] fontList = getAssets().list(fontsDir);
            for (String font : fontList) {
                Typeface typeface = Typeface.createFromAsset(getAssets(), fontsDir + "/" + font);
                View row = LayoutInflater.from(this).inflate(R.layout.row, null);
                TextView textView = (TextView) row.findViewById(R.id.text);
                textView.setTypeface(typeface);
                textView.setText(font);
                parent.addView(row);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Unexpected error!", Toast.LENGTH_SHORT).show();
        }
    }
}
