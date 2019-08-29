package com.amap.bundle.appupgrade;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;

public class InstallListener extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            StringBuilder sb = new StringBuilder("InstallListener --- action = ");
            sb.append(intent.getAction());
            AMapLog.i("BroadcastCompat", sb.toString());
            String string = AMapAppGlobal.getApplication().getSharedPreferences("appDownloadUrl", 0).getString("scheme", "");
            if (!TextUtils.isEmpty(string)) {
                try {
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(string)));
                } catch (ActivityNotFoundException unused) {
                }
            }
        }
    }
}
