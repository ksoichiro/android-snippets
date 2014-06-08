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

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.snippet.R;

public class AboutActivity extends SherlockActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((TextView) findViewById(R.id.version_name)).setText(getVersionName(this));
        ((TextView) findViewById(R.id.copyright)).setText(
                Html.fromHtml(getResources().getString(R.string.label_copyright)));
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem menu) {
        if (menu.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    public static String getVersionName(final Context context) {
        final PackageManager manager = context.getPackageManager();
        String versionName;
        try {
            final PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "";
        }
        return versionName;
    }

}
