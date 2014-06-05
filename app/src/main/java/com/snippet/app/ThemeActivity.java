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
import android.view.Menu;
import android.view.MenuItem;

public class ThemeActivity extends Activity {

    private static final String EXTRA_THEME = "theme";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        if (intent != null) {
            int theme = intent.getIntExtra(EXTRA_THEME, R.style.AppTheme);
            setTheme(theme);
        }

        setContentView(R.layout.activity_theme);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_theme, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_theme1:
                resetTheme(R.style.AppTheme);
                break;
            case R.id.menu_theme2:
                resetTheme(R.style.AppThemeDark);
                break;
            case R.id.menu_theme3:
                resetTheme(R.style.Theme_Sherlock_Styled_Light);
                break;
            case R.id.menu_theme4:
                resetTheme(R.style.Theme_Sherlock_Styled_Dark);
                break;
        }
        return false;
    }

    private void resetTheme(final int resId) {
        Intent intent = new Intent(this, ThemeActivity.class);
        intent.putExtra(EXTRA_THEME, resId);
        startActivity(intent);
        finish();
    }
}
