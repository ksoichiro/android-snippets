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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AnimationTransitionActivity extends Activity {

    private static final String EXTRA_STYLE = "style";

    private static final int STYLE_SLIDE_HORIZONTAL = 1;
    private static final int STYLE_COVER_HORIZONTAL = 2;
    private static final int STYLE_COVER_VERTICAL = 3;
    private static final int STYLE_FADE_VERTICAL = 4;

    private int mStyle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStyle = 0;
        Intent intent = getIntent();
        if (intent != null) {
            mStyle = intent.getIntExtra("style", 0);
        }

        switch (mStyle) {
            case STYLE_SLIDE_HORIZONTAL:
                overridePendingTransition(R.anim.transition_slide_horizontal_entry_next,
                        R.anim.transition_slide_horizontal_exit_prev);
                break;
            case STYLE_COVER_HORIZONTAL:
                overridePendingTransition(R.anim.transition_cover_horizontal_entry_next,
                        R.anim.transition_cover_horizontal_exit_prev);
                break;
            case STYLE_COVER_VERTICAL:
                overridePendingTransition(R.anim.transition_cover_vertical_entry_next,
                        R.anim.transition_cover_vertical_exit_prev);
                break;
            case STYLE_FADE_VERTICAL:
                overridePendingTransition(R.anim.transition_fade_vertical_entry_next,
                        R.anim.transition_fade_vertical_exit_prev);
                break;
        }

        setContentView(R.layout.activity_animation_transition);

        findViewById(R.id.button_slide_horizontal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart(STYLE_SLIDE_HORIZONTAL);
            }
        });
        findViewById(R.id.button_cover_horizontal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart(STYLE_COVER_HORIZONTAL);
            }
        });
        findViewById(R.id.button_cover_vertical).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart(STYLE_COVER_VERTICAL);
            }
        });
        findViewById(R.id.button_fade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart(STYLE_FADE_VERTICAL);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        switch (mStyle) {
            case STYLE_SLIDE_HORIZONTAL:
                overridePendingTransition(R.anim.transition_slide_horizontal_entry_prev,
                        R.anim.transition_slide_horizontal_exit_next);
                break;
            case STYLE_COVER_HORIZONTAL:
                overridePendingTransition(R.anim.transition_cover_horizontal_entry_prev,
                        R.anim.transition_cover_horizontal_exit_next);
                break;
            case STYLE_COVER_VERTICAL:
                overridePendingTransition(R.anim.transition_cover_vertical_entry_prev,
                        R.anim.transition_cover_vertical_exit_next);
                break;
            case STYLE_FADE_VERTICAL:
                overridePendingTransition(R.anim.transition_fade_vertical_entry_prev,
                        R.anim.transition_fade_vertical_exit_next);
                break;
        }
    }

    private void restart(final int style) {
        Intent intent = new Intent(AnimationTransitionActivity.this,
                AnimationTransitionActivity.class);
        intent.putExtra(EXTRA_STYLE, style);
        startActivity(intent);
    }
}
