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
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SpinnerActivity extends Activity {

    private static final String INVALID_VALUE = "INVALID";
    private static final String[] mSpinner2Entries = new String[] {
            "Please choose", "candidate1", "candidate2"
    };
    private static final String[] mSpinner2Values = new String[] {
            INVALID_VALUE, "v1", "v2",
    };
    enum Spinner3Entry {
        ENTRY0(0, "INVALID"),
        ENTRY1(1, "X"),
        ENTRY2(2, "Y"),
        ENTRY3(3, "Z");
        private int mNum;
        private String mId;
        private Spinner3Entry(int num, String id) {
            mNum = num;
            mId = id;
        }
        public int getNum() {
            return mNum;
        }
        public String getId() {
            return mId;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        findViewById(R.id.btnCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                checkSpinner();
            }
        });

        findViewById(R.id.btnCheck2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                checkSpinner2();
            }
        });

        findViewById(R.id.btnCheck3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                checkSpinner3();
            }
        });

        setupSpinner2();
        setupSpinner3();
    }

    private void setupSpinner2() {
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, mSpinner2Entries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setPrompt("Select");
    }

    private void setupSpinner3() {
        Resources res = getResources();
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner3);
        List<String> entries = new ArrayList<String>();
        for (Spinner3Entry entryEnum : Spinner3Entry.values()) {
            String entry = res.getString(R.string.entry_format_sample_spinner3,
                    new Object[] { entryEnum.getNum(), entryEnum.getId() });
            entries.add(entry);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, entries.toArray(new String[]{}));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setPrompt("Select");
    }

    private void checkSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        int selectedPosition = spinner.getSelectedItemPosition();
        String[] entries = getResources().getStringArray(R.array.sample_entries);
        String[] values = getResources().getStringArray(R.array.sample_values);
        if (selectedPosition == Spinner.INVALID_POSITION
                || selectedPosition < 0 || entries.length <= selectedPosition) {
            Toast.makeText(this, "Please select one", Toast.LENGTH_SHORT).show();
        } else {
            String value = values[selectedPosition];
            if (INVALID_VALUE.equals(value)) {
                Toast.makeText(this, "Please select one", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Selected value: " + values[selectedPosition],
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkSpinner2() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        int selectedPosition = spinner.getSelectedItemPosition();
        String[] entries = mSpinner2Entries;
        String[] values = mSpinner2Values;
        if (selectedPosition == Spinner.INVALID_POSITION
                || selectedPosition < 0 || entries.length <= selectedPosition) {
            Toast.makeText(this, "Please select one", Toast.LENGTH_SHORT).show();
        } else {
            String value = values[selectedPosition];
            if (INVALID_VALUE.equals(value)) {
                Toast.makeText(this, "Please select one", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Selected value: " + values[selectedPosition],
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkSpinner3() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner3);
        int selectedPosition = spinner.getSelectedItemPosition();
        int length = spinner.getCount();
        if (selectedPosition == Spinner.INVALID_POSITION
                || selectedPosition < 0 || length <= selectedPosition) {
            Toast.makeText(this, "Please select one", Toast.LENGTH_SHORT).show();
        } else {
            Spinner3Entry[] entries = Spinner3Entry.values();
            Spinner3Entry selectedEntry = entries[selectedPosition];
            String value = selectedEntry.getId();
            if (INVALID_VALUE.equals(value)) {
                Toast.makeText(this, "Please select one", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Selected value: " + value,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
