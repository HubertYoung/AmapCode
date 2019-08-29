package com.ali.auth.third.login.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.ali.auth.third.core.util.CommonUtils;

final class a extends BroadcastReceiver {
    a() {
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent != null && !TextUtils.equals(CommonUtils.getCurrentProcessName(), intent.getStringExtra("currentProcess"))) {
            LoginStatus.a.set(intent.getBooleanExtra("isLogining", false));
        }
    }
}
