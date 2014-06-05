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

package com.snippet.service;

import com.snippet.app.CallHookHookedActivity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Thanks for:
 * http://stackoverflow.com/questions/3243635/what-is-the-correct-way-of-
 * handling-the-action-new-outgoing-call-while-looping
 * 
 * @author ksoichiro
 */
public class OutgoingCallHookService extends Service {

    private OutgoingCallReceiver mReceiver;

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        OutgoingCallHookService getService() {
            return OutgoingCallHookService.this;
        }
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mReceiver = new OutgoingCallReceiver();
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));
        Toast.makeText(OutgoingCallHookService.this, "Start watching", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        Toast.makeText(OutgoingCallHookService.this, "Stop watching", Toast.LENGTH_SHORT)
                .show();
    }

    public class OutgoingCallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction())) {
                String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                String uriFragment = Uri.parse(
                        intent.getStringExtra("android.phone.extra.ORIGINAL_URI")).getFragment();
                if (!CallHookHookedActivity.URI_FRAGMENT.equals(uriFragment)) {
                    Intent hook = new Intent(context, CallHookHookedActivity.class);
                    hook.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    hook.putExtra("number", number);
                    context.startActivity(hook);

                    abortBroadcast();
                    setResultData(null);
                }
            }
        }
    }
}
