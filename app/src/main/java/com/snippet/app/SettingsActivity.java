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

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;
import com.snippet.R;

public class SettingsActivity extends SherlockPreferenceActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setPreferencesHoneycomb();
        } else {
            setPreferencesGingerbread();
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem menu) {
        if (menu.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setPreferencesHoneycomb() {
        SettingsFragment fragment = new SettingsFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                fragment).commit();
    }

    @SuppressWarnings("deprecation")
    private void setPreferencesGingerbread() {
        addPreferencesFromResource(R.xml.preferences);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static final class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

    }

}
