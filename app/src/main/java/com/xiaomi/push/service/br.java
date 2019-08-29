package com.xiaomi.push.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.smack.util.e;

class br extends BroadcastReceiver {
    final /* synthetic */ XMPushService a;

    br(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals("com.xiaomi.metoknlp.geofencing.state_change_protected", intent.getAction())) {
            String stringExtra = intent.getStringExtra("Describe");
            String stringExtra2 = intent.getStringExtra("State");
            if (!TextUtils.isEmpty(stringExtra)) {
                if (!this.a.a(stringExtra2, stringExtra, context)) {
                    stringExtra2 = "Unknown";
                    StringBuilder sb = new StringBuilder(" updated geofence statue about geo_id:");
                    sb.append(stringExtra);
                    sb.append(" falied. current_statue:");
                    sb.append(stringExtra2);
                    b.a(sb.toString());
                }
                e.a((Runnable) new bs(this, context, stringExtra, stringExtra2));
                StringBuilder sb2 = new StringBuilder("ownresilt结果:state= ");
                sb2.append(stringExtra2);
                sb2.append("\n describe=");
                sb2.append(stringExtra);
                b.c(sb2.toString());
            }
        }
    }
}
