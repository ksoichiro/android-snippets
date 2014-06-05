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
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CheckFeatureActivity extends Activity {

    private String[] FEATURES = new String[] {
            "android.hardware.audio.low_latency",
            "android.hardware.bluetooth",
            "android.hardware.camera",
            "android.hardware.camera.any",
            "android.hardware.camera.autofocus",
            "android.hardware.camera.flash",
            "android.hardware.camera.front",
            "android.hardware.faketouch",
            "android.hardware.faketouch.multitouch.distinct",
            "android.hardware.faketouch.multitouch.jazzhand",
            "android.software.live_wallpaper",
            "android.hardware.location",
            "android.hardware.location.gps",
            "android.hardware.location.network",
            "android.hardware.microphone",
            "android.hardware.nfc",
            "android.hardware.screen.landscape",
            "android.hardware.screen.portrait",
            "android.hardware.sensor.accelerometer",
            "android.hardware.sensor.barometer",
            "android.hardware.sensor.compass",
            "android.hardware.sensor.gyroscope",
            "android.hardware.sensor.light",
            "android.hardware.sensor.proximity",
            "android.software.sip",
            "android.software.sip.voip",
            "android.hardware.telephony",
            "android.hardware.telephony.cdma",
            "android.hardware.telephony.gsm",
            "android.hardware.type.television",
            "android.hardware.touchscreen",
            "android.hardware.touchscreen.multitouch",
            "android.hardware.touchscreen.multitouch.distinct",
            "android.hardware.touchscreen.multitouch.jazzhand",
            "android.hardware.usb.accessory",
            "android.hardware.usb.host",
            "android.hardware.wifi",
            "android.hardware.wifi.direct",
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_feature);

        checkFeatures();
    }

    private void checkFeatures() {
        PackageManager manager = getPackageManager();
        for (String featureName : FEATURES) {
            addRow(featureName, manager.hasSystemFeature(featureName));
        }
    }

    private void addRow(final String key, final boolean available) {
        ViewGroup table = (ViewGroup) findViewById(R.id.table);
        View row = LayoutInflater.from(this).inflate(R.layout.table_row, null);
        ((TextView) row.findViewById(R.id.key)).setText(key);
        String availability;
        String colorString;
        if (available) {
            availability = "Available";
            colorString = "#669900";
        } else {
            availability = "Unavailable";
            colorString = "#cc0000";
        }
        ((TextView) row.findViewById(R.id.value)).setText(availability);
        ((TextView) row.findViewById(R.id.value)).setTextColor(Color.parseColor(colorString));
        table.addView(row);
    }
}
