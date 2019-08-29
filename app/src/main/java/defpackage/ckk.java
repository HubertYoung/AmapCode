package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.AjxInit;

/* renamed from: ckk reason: default package */
/* compiled from: AjxEngine */
public final class ckk extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "AjxEngine";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        emt a = emu.a(AMapPageUtil.getAppContext());
        emt a2 = emu.a((String) "VERSION_CURVERINFO");
        if (!a.equals(a2)) {
            if (a2 != null) {
                emu.a("VERSION_LASTVERINFO", a2.toString());
            }
            emu.a("VERSION_CURVERINFO", a.toString());
        }
        if (!AjxInit.sIsAjxEngineInited) {
            AjxInit.initAuiEngine(application);
            ckb.a();
            ckb.a((String) "AjxInit#initJsEnv");
            AjxInit.sIsAjxEngineInited = true;
        }
        la.i();
    }
}
