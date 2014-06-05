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
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.TextView;

public class ScreenSpecActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_spec);

        ((TextView) findViewById(R.id.tv_screen_size)).setText(getScreenSizeName());
        ((TextView) findViewById(R.id.tv_screen_density_dpi)).setText(getScreenDensityDpiName());
    }

    private String getScreenSizeName() {
        int screenSize = getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        String screenSizeName = "unknown";
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                screenSizeName = "small";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                screenSizeName = "normal";
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                screenSizeName = "large";
                break;
            case Configuration.SCREENLAYOUT_SIZE_UNDEFINED:
                screenSizeName = "undefined";
                break;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            switch (screenSize) {
                case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    screenSizeName = "xlarge";
                    break;
            }
        }

        return screenSizeName;
    }

    private String getScreenDensityDpiName() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        String screenDensityDpiName = "unknown";
        switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                screenDensityDpiName = "ldpi";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                screenDensityDpiName = "mdpi";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                screenDensityDpiName = "hdpi";
                break;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            switch (metrics.densityDpi) {
                case DisplayMetrics.DENSITY_XHIGH:
                    screenDensityDpiName = "xdpi";
                    break;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            switch (metrics.densityDpi) {
                case DisplayMetrics.DENSITY_XXHIGH:
                    screenDensityDpiName = "xxhdpi";
                    break;
            }
        }
        return screenDensityDpiName;
    }
}
