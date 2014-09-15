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
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.snippet.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends Activity {

    private static final String TAG = CameraActivity.class.getSimpleName();
    private Camera myCamera;
    private boolean mClicked;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_camera);

        SurfaceView mySurfaceView = (SurfaceView) findViewById(R.id.surface);
        SurfaceHolder holder = mySurfaceView.getHolder();
        holder.addCallback(mSurfaceListener);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        findViewById(R.id.button_take_picture).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if (myCamera != null && !mClicked) {
                            mClicked = true;
                            autoFocus();
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();

        releaseCamera();
    }

    private SurfaceHolder.Callback mSurfaceListener = new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {
            if (myCamera == null) {
                myCamera = Camera.open();
            }
            try {
                myCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Camera.Parameters parameters = myCamera.getParameters();

            // Determine picture size
            final int minArea = 2000000;
            int area = 0;
            Camera.Size preferredSize = null;
            for (Camera.Size size : parameters.getSupportedPictureSizes()) {
                int currentSize = size.width * size.height;
                if (minArea <= currentSize) {
                    if (area == 0 || currentSize < area) {
                        area = currentSize;
                        preferredSize = size;
                    }
                }
            }
            if (preferredSize != null) {
                parameters.setPictureSize(preferredSize.width, preferredSize.height);
            }

            // Determine preview size
            double aspectRatioPicture = (double) parameters.getPictureSize().width
                    / parameters.getPictureSize().height;
            double minAspectRatioDiff = Double.MAX_VALUE;
            List<Camera.Size> availableSizes = new ArrayList<Camera.Size>();
            for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
                double aspectRatio = (double) size.width / size.height;
                double aspectRatioDiff = Math.abs(aspectRatio - aspectRatioPicture);
                if (aspectRatioDiff < minAspectRatioDiff) {
                    minAspectRatioDiff = aspectRatioDiff;
                    availableSizes.clear();
                    availableSizes.add(size);
                } else if (aspectRatioDiff == minAspectRatioDiff) {
                    availableSizes.add(size);
                }
            }
            int maxArea = 0;
            Camera.Size preferredPreviewSize = null;
            area = 0;
            View parent = findViewById(R.id.parent);
            int w = parent.getWidth();
            int h = parent.getHeight();
            for (Camera.Size size : availableSizes) {
                area = size.width * size.height;
                if (size.width <= w && size.height <= h && maxArea < area) {
                    maxArea = area;
                    preferredPreviewSize = size;
                }
            }
            if (preferredPreviewSize != null) {
                parameters.setPreviewSize(preferredPreviewSize.width,
                        preferredPreviewSize.height);
            }

            // Set focus mode
            String focusMode = parameters.getFocusMode();
            if (!Camera.Parameters.FOCUS_MODE_AUTO.equals(focusMode)
                    && !Camera.Parameters.FOCUS_MODE_MACRO.equals(focusMode)) {
                for (String mode : parameters.getSupportedFocusModes()) {
                    if (Camera.Parameters.FOCUS_MODE_MACRO.equals(mode)) {
                        parameters.setFocusMode(mode);
                        break;
                    }
                }
                if (!Camera.Parameters.FOCUS_MODE_MACRO.equals(parameters.getFocusMode())) {
                    for (String mode : parameters.getSupportedFocusModes()) {
                        if (Camera.Parameters.FOCUS_MODE_AUTO.equals(mode)) {
                            parameters.setFocusMode(mode);
                            break;
                        }
                    }
                }
            }
            myCamera.setParameters(parameters);
            parameters = myCamera.getParameters();

            Log.i("TEST", "PictureSize: "
                    + parameters.getPictureSize().width
                    + ", "
                    + parameters.getPictureSize().height
                    + " AspectRatio: "
                    + ((double) parameters.getPictureSize().width / parameters
                    .getPictureSize().height));
            Log.i("TEST", "PreviewSize: "
                    + parameters.getPreviewSize().width
                    + ", "
                    + parameters.getPreviewSize().height
                    + " AspectRatio: "
                    + ((double) parameters.getPreviewSize().width / parameters
                    .getPreviewSize().height));
            Log.i("TEST", "FocusMode: " + parameters.getFocusMode());

            View surface = findViewById(R.id.surface);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) surface.getLayoutParams();
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
                params.height = parameters.getPreviewSize().height;
                params.width = parameters.getPreviewSize().width;
                params.rightMargin = 0;
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
            } else {
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                // surface
                float ws = metrics.widthPixels -
                        getResources().getDimensionPixelSize(R.dimen.height_element);
                float hs = metrics.heightPixels;
                float rs = ws / hs;
                // preview
                float wp = parameters.getPreviewSize().width;
                float hp = parameters.getPreviewSize().height;
                float rp = parameters.getPreviewSize().width / hp;
                if (rp < rs) {
                    Log.i(TAG, "Surface is wider than preview");
                    params.width = (int) ws;
                    params.height = (int) (hp * ws / wp);
                    params.topMargin = (int) ((hs - params.height) / 2);
                    params.bottomMargin = (int) ((hs - params.height) / 2);
                } else {
                    Log.i(TAG, "Preview is wider than surface");
                    params.width = (int) (wp * hs / hp);
                    params.height = (int) hs;
                    params.leftMargin = (int) ((ws - params.width) / 2);
                    params.rightMargin = (int) ((ws - params.width) / 2);
                }
                Log.i(TAG, "metrics.widthPixels=" + metrics.widthPixels);
                Log.i(TAG, "metrics.heightPixels=" + metrics.heightPixels);
                Log.i(TAG, "ws=" + ws);
                Log.i(TAG, "hs=" + hs);
                Log.i(TAG, "wp=" + wp);
                Log.i(TAG, "hp=" + hp);
                Log.i(TAG, "params.width=" + params.width);
                Log.i(TAG, "params.height=" + params.height);
                Log.i(TAG, "params.leftMargin=" + params.leftMargin);
                Log.i(TAG, "params.rightMargin=" + params.rightMargin);
            }
            surface.setLayoutParams(params);
            surface.requestLayout();

            myCamera.startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            releaseCamera();
        }
    };

    private void releaseCamera() {
        if (myCamera != null) {
            myCamera.stopPreview();
            myCamera.release();
            myCamera = null;
        }
    }

    private void autoFocus() {
        myCamera.autoFocus(new AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                takePicture();
            }
        });
    }

    private void takePicture() {
        myCamera.takePicture(myShutterCallback, null, myJpegPictureCallback);
    }

    private Camera.ShutterCallback myShutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            // Do nothing, but this callback is necessary
        }
    };

    private Camera.PictureCallback myJpegPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if (data == null) {
                Toast.makeText(CameraActivity.this,
                        "Take picture failed", Toast.LENGTH_SHORT).show();
            } else {
                Camera.Parameters params = camera.getParameters();
                Camera.Size size = params.getPictureSize();
                Toast.makeText(CameraActivity.this,
                        "Size: " + size.width + " x " + size.height, Toast.LENGTH_SHORT)
                        .show();
            }

            camera.startPreview();
            mClicked = false;
        }
    };
}
