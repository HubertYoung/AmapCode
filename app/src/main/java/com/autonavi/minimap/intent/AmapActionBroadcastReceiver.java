package com.autonavi.minimap.intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

public class AmapActionBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals("com.autonavi.minimap.Intent.Action")) {
                String stringExtra = intent.getStringExtra("method");
                if (!TextUtils.isEmpty(stringExtra) && stringExtra.equals("androidamap")) {
                    String stringExtra2 = intent.getStringExtra("action");
                    String stringExtra3 = intent.getStringExtra("params");
                    StringBuilder sb = new StringBuilder();
                    sb.append("androidamap://");
                    sb.append(stringExtra2);
                    sb.append("?");
                    if (stringExtra3.contains(DriveUtil.SOURCE_APPLICATION)) {
                        sb.append(stringExtra3);
                    } else {
                        sb.append("sourceApplication=web&");
                    }
                    Intent intent2 = new Intent();
                    intent2.setAction("android.intent.action.VIEW");
                    intent2.addCategory("android.intent.category.DEFAULT");
                    intent2.addCategory("android.intent.category.BROWSABLE");
                    intent2.setPackage(context.getPackageName());
                    intent2.setData(Uri.parse(sb.toString()));
                    intent2.setComponent(null);
                    intent2.setFlags(268435456);
                    context.startActivity(intent2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.intent_open_fail));
        }
    }
}
