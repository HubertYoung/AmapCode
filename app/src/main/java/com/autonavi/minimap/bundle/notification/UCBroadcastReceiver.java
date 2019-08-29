package com.autonavi.minimap.bundle.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.taobao.agoo.TaobaoRegister;

public class UCBroadcastReceiver extends BroadcastReceiver {
    public Context a;
    public UCBroadcastReceiver b = this;

    public UCBroadcastReceiver(Context context) {
        this.a = context;
    }

    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String string = extras.getString("task_id", "");
            String string2 = extras.getString("message_id", "");
            String string3 = extras.getString("market_package", "");
            String string4 = extras.getString("package_recommended_app", "");
            TaobaoRegister.clickMessage(context, string2, string);
            Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=".concat(String.valueOf(string4))));
            intent2.setPackage(string3);
            intent2.addFlags(268435456);
            context.startActivity(intent2);
            if (this.b != null) {
                this.a.unregisterReceiver(this.b);
            }
        }
    }
}
