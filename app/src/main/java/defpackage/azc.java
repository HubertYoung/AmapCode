package defpackage;

import com.autonavi.bundle.routecommute.common.DialogModuleUtils$1;

/* renamed from: azc reason: default package */
/* compiled from: DialogModuleUtils */
public final class azc {
    public static void a(int i, azr azr) {
        a(String.valueOf(i), azr);
    }

    private static void a(String str, azr azr) {
        if (azr != null && azr.a() != null && azr.a().isResumed()) {
            ctl ctl = (ctl) a.a.a(ctl.class);
            if (ctl == null) {
                azb.a(null, "DialogModuleUtils------service is Null!!!");
            } else {
                ctl.a(str, new DialogModuleUtils$1(ctl, azr, str));
            }
        }
    }
}
