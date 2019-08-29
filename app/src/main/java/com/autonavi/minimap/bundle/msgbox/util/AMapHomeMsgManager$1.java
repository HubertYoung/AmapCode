package com.autonavi.minimap.bundle.msgbox.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;

public class AMapHomeMsgManager$1 extends BroadcastReceiver {
    final /* synthetic */ dat a;

    public AMapHomeMsgManager$1(dat dat) {
        this.a = dat;
    }

    public void onReceive(Context context, Intent intent) {
        if (!(intent == null || !"upload_local_blue_bar".equals(intent.getAction()) || this.a.b == null)) {
            String stringExtra = intent.getStringExtra("upload_local_blue_bar");
            String blueTipMsg = this.a.b.getBlueTipMsg();
            if (TextUtils.isEmpty(stringExtra)) {
                AmapMessage dismissTipsView = this.a.b.dismissTipsView();
                if (dismissTipsView != null) {
                    MessageBoxManager.getInstance().setRead(dismissTipsView);
                }
                this.a.a(true);
                return;
            }
            dat.a(this.a, stringExtra, blueTipMsg);
        }
    }
}
