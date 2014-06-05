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
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

public class ZoomActivity extends Activity {

    private boolean mZoomed;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        findViewById(R.id.button_zoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom();
            }
        });
        findViewById(R.id.button_zoom_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomWithActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mZoomed) {
            finishZoom();
        } else {
            super.onBackPressed();
        }
    }

    private void zoom() {
        mZoomed = true;

        RelativeLayout background = new RelativeLayout(getApplicationContext());
        background.setId(R.id.background);
        RelativeLayout.LayoutParams paramsLayout = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        background.setLayoutParams(paramsLayout);
        background.setBackground(getResources().getDrawable(R.drawable.translucent));

        ImageView image = new ImageView(getApplicationContext());
        image.setId(R.id.image);
        image.setImageDrawable(getResources().getDrawable(R.drawable.sample));
        image.setScaleType(ScaleType.CENTER_INSIDE);
        image.setAdjustViewBounds(true);
        RelativeLayout.LayoutParams paramsImage = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, metrics);
        paramsImage.setMargins(margin, margin, margin, margin);
        paramsImage.addRule(RelativeLayout.CENTER_IN_PARENT);
        image.setLayoutParams(paramsImage);
        background.addView(image);
        background.setClickable(true);

        ImageView closeImage = new ImageView(getApplicationContext());
        closeImage.setId(R.id.close);
        closeImage.setImageDrawable(getResources().getDrawable(R.drawable.close));
        RelativeLayout.LayoutParams paramsCloseImage = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsCloseImage.addRule(RelativeLayout.ALIGN_RIGHT, R.id.image);
        paramsCloseImage.addRule(RelativeLayout.ALIGN_TOP, R.id.image);
        closeImage.setLayoutParams(paramsCloseImage);
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishZoom();
            }
        });
        background.addView(closeImage);

        ((ViewGroup) findViewById(R.id.content)).addView(background);
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        background.startAnimation(anim);
    }

    private void finishZoom() {
        mZoomed = false;

        View background = findViewById(R.id.background);
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        background.startAnimation(anim);
        ((ViewGroup) findViewById(R.id.content)).removeView(background);
    }

    private void zoomWithActivity() {
        startActivity(new Intent(this, ZoomedActivity.class));
    }
}
