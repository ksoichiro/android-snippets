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
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationRotateActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_rotate);
        startAnimation();
    }

    private void startAnimation() {
        Animation animSmall = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation animMedium = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation animLarge = AnimationUtils.loadAnimation(this, R.anim.rotate);

        View img1Small = findViewById(R.id.img1_small);
        img1Small.startAnimation(animSmall);
        View img1Medium = findViewById(R.id.img1_medium);
        img1Medium.startAnimation(animMedium);
        View img1Large = findViewById(R.id.img1_large);
        img1Large.startAnimation(animLarge);
        View img2 = findViewById(R.id.img2);
        img2.startAnimation(animLarge);
        View img3 = findViewById(R.id.img3);
        img3.startAnimation(animLarge);
        View img4 = findViewById(R.id.img4);
        img4.startAnimation(animLarge);
        View img5 = findViewById(R.id.img5);
        img5.startAnimation(animLarge);
        View img6 = findViewById(R.id.img6);
        img6.startAnimation(animLarge);

        // Counter clockwise
        Animation animSmallCcw = AnimationUtils.loadAnimation(this, R.anim.rotate_counter_clockwise);
        Animation animMediumCcw = AnimationUtils.loadAnimation(this, R.anim.rotate_counter_clockwise);
        Animation animLargeCcw = AnimationUtils.loadAnimation(this, R.anim.rotate_counter_clockwise);

        View img1SmallCcw = findViewById(R.id.img1_small_ccw);
        img1SmallCcw.startAnimation(animSmallCcw);
        View img1MediumCcw = findViewById(R.id.img1_medium_ccw);
        img1MediumCcw.startAnimation(animMediumCcw);
        View img1LargeCcw = findViewById(R.id.img1_large_ccw);
        img1LargeCcw.startAnimation(animLargeCcw);
        View img2Ccw = findViewById(R.id.img2_ccw);
        img2Ccw.startAnimation(animLargeCcw);
        View img3Ccw = findViewById(R.id.img3_ccw);
        img3Ccw.startAnimation(animLargeCcw);
        View img4Ccw = findViewById(R.id.img4_ccw);
        img4Ccw.startAnimation(animLargeCcw);
        View img5Ccw = findViewById(R.id.img5_ccw);
        img5Ccw.startAnimation(animLargeCcw);
        View img6Ccw = findViewById(R.id.img6_ccw);
        img6Ccw.startAnimation(animLargeCcw);
    }
}
