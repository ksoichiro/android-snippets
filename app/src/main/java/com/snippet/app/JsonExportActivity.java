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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.snippet.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonExportActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_export);

        findViewById(R.id.btn_process).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process();
            }
        });
    }

    private void process() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", true);
        Map<String, Object> subMap = new HashMap<String, Object>();
        subMap.put("f", "foo");
        subMap.put("g", "bar");
        map.put("e", subMap);

        try {
            JSONObject jsonObject = toJSON(map);
            ((TextView) findViewById(R.id.json)).setText(jsonObject.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject toJSON(Map<String, Object> map) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (String key : map.keySet()) {
            if (map.get(key) instanceof Map) {
                jsonObject.put(key, toJSON((Map) map.get(key)));
            } else {
                jsonObject.put(key, map.get(key));
            }
        }
        return jsonObject;
    }

}