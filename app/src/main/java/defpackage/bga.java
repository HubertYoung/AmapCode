package defpackage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.vui.vuistate.VUIState;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;

/* renamed from: bga reason: default package */
/* compiled from: VUIHintController */
public class bga implements bfc, bgw {
    private static volatile bga b;
    private final String a = "VUIHintController";

    public static bga a() {
        if (b == null) {
            synchronized (bga.class) {
                try {
                    if (b == null) {
                        b = new bga();
                    }
                }
            }
        }
        return b;
    }

    private bga() {
    }

    public final void a(VUIState vUIState) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("onTipStateChanged()-state:");
            sb.append(vUIState.type);
            bfh.a("VUIHintController", sb.toString());
        }
        int i = vUIState.type;
        if (bno.a) {
            bfh.a("VUIHintController", "showErrorHint()-state:".concat(String.valueOf(i)));
        }
        if (!VUIStateManager.f().p() || !bfj.a().b()) {
            if (bno.a) {
                StringBuilder sb2 = new StringBuilder("audioPermission:");
                sb2.append(VUIStateManager.f().p());
                sb2.append(",awakeSwitch:");
                sb2.append(bfj.a().b());
                bfh.a("VUIHintController", sb2.toString());
            }
            return;
        }
        if (1 == i) {
            VUIStateManager.f();
            String string = AMapAppGlobal.getApplication().getString(R.string.battery_is_low);
            if (bno.a) {
                bfh.a("VUIHintController", "refreshBlueBar()");
            }
            try {
                Intent intent = new Intent("upload_local_blue_bar");
                if (!TextUtils.isEmpty(string)) {
                    intent.putExtra("upload_local_blue_bar", string);
                }
                LocalBroadcastManager.getInstance(AMapAppGlobal.getApplication()).sendBroadcast(intent);
            } catch (Exception e) {
                StringBuilder sb3 = new StringBuilder("refreshBlueBar():");
                sb3.append(Log.getStackTraceString(e));
                bfh.b("VUIHintController", sb3.toString());
            }
        }
    }

    public final void b() {
        if (bno.a) {
            bfh.a("VUIHintController", "onCreate()");
        }
        VUIStateManager.f();
        afq.a((Context) AMapAppGlobal.getTopActivity()).a((b) new b() {
            public final void a(boolean z) {
                if (z) {
                    MessageBoxManager.getInstance().removeMessages(AmapMessage.TOKEN_CLOUD_SYNC_DIALOG);
                } else {
                    VUIStateManager.f();
                }
            }
        });
    }

    public final void c() {
        if (bno.a) {
            bfh.a("VUIHintController", "onResume()");
        }
    }

    public final void d() {
        if (bno.a) {
            bfh.a("VUIHintController", "onPause()");
        }
    }

    public final void e() {
        if (bno.a) {
            bfh.a("VUIHintController", "onDestroy()");
        }
        if (VUIStateManager.f().m()) {
            VUIStateManager f = VUIStateManager.f();
            if (f.e != null) {
                f.e.remove(this);
            }
        }
    }
}
