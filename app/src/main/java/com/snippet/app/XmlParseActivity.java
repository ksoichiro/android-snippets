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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class XmlParseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parse);

        ((TextView) findViewById(R.id.tv_raw_xml)).setText(getRawXml());

        Map<String, String> configs = parseConfig();
        StringBuilder sb = new StringBuilder();
        for (String configType : configs.keySet()) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            String configValue = configs.get(configType);
            sb.append(configType);
            sb.append("=");
            sb.append(configValue);
        }
        ((TextView) findViewById(R.id.tv_parsed_xml)).setText(sb.toString());
    }

    private String getRawXml() {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("xml/sample.xml")));
            String line;
            while ((line = reader.readLine()) != null) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    private Map<String, String> parseConfig() {
        Map<String, String> configs = new LinkedHashMap<String, String>();

        InputStream is = null;
        try {
            is = getAssets().open("xml/sample.xml");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is, "UTF-8");
            String configType = null;
            int eventType;
            eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("config".equals(parser.getName())) {
                            configType = parser.getAttributeValue(null, "type");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if (!TextUtils.isEmpty(configType)) {
                            configs.put(configType, parser.getText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        configType = null;
                        break;
                }
                eventType = parser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return configs;
    }
}
