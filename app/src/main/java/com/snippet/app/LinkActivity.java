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

import com.snippet.util.LinkUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LinkActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String testStr = "Open URL with browser.Links http://www.yahoo.co.jp/ and http://d.hatena.ne.jp/ can be clicked.";

        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setText(testStr);
        LinkUtils.autoLink(textView, new LinkUtils.OnClickListener() {
            @Override
            public void onLinkClicked(final String link) {
                Log.i("SensibleUrlSpan", "Link Clicked:" + link);
            }

            @Override
            public void onClicked() {
                Log.i("SensibleUrlSpan", "View Clicked");
            }
        });

        setContentView(textView);
    }
}
