package com.autonavi.bundle.msgbox;

import android.content.IntentFilter;
import android.os.Build.VERSION;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.msgbox.ajx.ModuleMessageBox;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.msgbox.MsgBoxBadgeReceiver;

public class MsgboxVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleMessageBox.class);
        if (AMapAppGlobal.getApplication().getApplicationInfo().targetSdkVersion >= 26) {
            int i = VERSION.SDK_INT;
            StringBuilder sb = new StringBuilder("OperationBroadcastCompatManager---- registerReceiver sdk = ");
            sb.append(i);
            sb.append(", targetsdk =");
            sb.append(AMapAppGlobal.getApplication().getApplicationInfo().targetSdkVersion);
            AMapLog.i("BroadcastCompat", sb.toString());
            if (i >= 26) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.autonavi.minimap.action.Badge");
                if (drw.a == null) {
                    drw.a = new MsgBoxBadgeReceiver();
                }
                AMapAppGlobal.getApplication().registerReceiver(drw.a, intentFilter);
            }
        }
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        if (drw.a != null) {
            AMapAppGlobal.getApplication().unregisterReceiver(drw.a);
            drw.a = null;
        }
    }
}
