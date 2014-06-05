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
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

/**
 * Sample for showing Shift_JIS character and UTF-16 code point.<br>
 * Referred: http://ash.jp/code/unitbl21.htm
 * http://ja.wikipedia.org/wiki/%E5%8D%8A%E8%A7%92%E3%82%AB%E3%83%8A
 */
public class SjisActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjis);

        loadCharacters();
    }

    private void loadCharacters() {
        (new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                StringBuffer strList = new StringBuffer();
                strList.append("SJIS  |UTF-16|Chr|Len|CodePoint\n");
                for (int i = 0x21; i < 0x7F; i++) {
                    showSJISStringInfo(strList, i);
                }
                for (int i = 0xA1; i < 0xE0; i++) {
                    showSJISStringInfo(strList, i);
                }
                for (int i = 0x8140; i < 0x839F; i++) {
                    showSJISStringInfo(strList, i);
                }
                return strList.toString();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                ((TextView) findViewById(R.id.text_sjis_list)).setText(result);
            }
        }).execute((Void) null);
    }

    private void showSJISStringInfo(final StringBuffer strList, final int code) {
        strList.append("0x");
        strList.append(get4HexString(code));
        strList.append("|0x");
        strList.append(getCodePointWith4HexString(getSJISString(code)));
        strList.append("|");
        strList.append(getSJISString(code));
        strList.append("  |");
        strList.append(getSJISString(code).length());
        strList.append("  |0x");
        strList.append(getSJISCodePointWith4HexString(getSJISString(code)));
        strList.append("|\n");
    }

    private String getSJISString(final int code) {
        byte[] sjisBytes;
        if (code <= 0xff) {
            sjisBytes = new byte[] {
                    (byte) (code & 0xff)
            };
        } else {
            sjisBytes = new byte[] {
                    (byte) ((code >> 8) & 0xff), (byte) (code & 0xff)
            };
        }
        try {
            return new String(sjisBytes, "SJIS");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private String getCodePointWith4HexString(final String s) {
        return get4HexString(s.codePointAt(0));
    }

    private String get4HexString(final int i) {
        String org = "0000" + Integer.toHexString(i);
        return org.substring(org.length() - 4);
    }

    private String getSJISCodePointWith4HexString(final String s) {
        byte[] data;
        try {
            data = s.substring(0, 1).getBytes("SJIS");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        String org = "0000" + Integer.toHexString(data[0]);
        if (data.length > 1) {
            org += Integer.toHexString(data[1]);
        }
        return org.substring(org.length() - 4);
    }
}
