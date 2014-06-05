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
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snippet.R;

import java.util.Locale;

public class PaletteActivity extends Activity {

    private static final String[] COLORS = new String[] {
            "#34B5E5",
            "#AA66CC",
            "#99CC00",
            "#FFBB33",
            "#FF4444",
            "#0099CC",
            "#9933CC",
            "#669900",
            "#FF8800",
            "#CC0000",
            "#E2F4FB",
            "#C5EAF8",
            "#A8DFF4",
            "#8AD5F0",
            "#6DCAEC",
            "#50C0E9",
            "#33B5E5",
            "#2CB1E1",
            "#24ADDE",
            "#1DA9DA",
            "#16A5D7",
            "#0FA1D3",
            "#079DD0",
            "#0099CC",
            "#F0F8DB",
            "#E2F0B6",
            "#D3E992",
            "#C5E26D",
            "#B6DB49",
            "#A8D324",
            "#99CC00",
            "#92C500",
            "#8ABD00",
            "#83B600",
            "#7CAF00",
            "#75A800",
            "#6DA000",
            "#669900",
            "#FFE4E4",
            "#FFCACA",
            "#FFAFAF",
            "#FF9494",
            "#FF7979",
            "#FF5F5F",
            "#FF4444",
            "#F83A3A",
            "#F03131",
            "#E92727",
            "#E21D1D",
            "#DB1313",
            "#D30A0A",
            "#CC0000",
            "#FFF6DF",
            "#FFECC0",
            "#FFE3A0",
            "#FFD980",
            "#FFD060",
            "#FFC641",
            "#FFBD21",
            "#FFB61C",
            "#FFAE18",
            "#FFA713",
            "#FFA00E",
            "#FF9909",
            "#FF9105",
            "#FF8A00",
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        LinearLayout list = (LinearLayout) findViewById(R.id.list);
        LayoutInflater inflater = LayoutInflater.from(this);
        for (String color : COLORS) {
            int c = Color.parseColor(color);
            TextView colorView = (TextView) inflater.inflate(R.layout.color_view, null);
            colorView.setText(String.format(Locale.getDefault(),
                    "%s (R,G,B)=(%3d,%3d,%3d)", color, Color.red(c), Color.green(c), Color.blue(c)));
            colorView.setBackgroundColor(c);
            list.addView(colorView);
            View divider = inflater.inflate(R.layout.divider, null);
            list.addView(divider);
        }
    }

}
