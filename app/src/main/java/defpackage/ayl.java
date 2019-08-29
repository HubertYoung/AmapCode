package defpackage;

import com.autonavi.bundle.routecommute.common.bean.NaviAddress;

/* renamed from: ayl reason: default package */
/* compiled from: BusCommuteTipManager */
public final class ayl {
    public static int a(NaviAddress naviAddress) {
        ayn ayn = new ayn(naviAddress);
        if (ayn.d()) {
            return 1;
        }
        aym aym = b.a;
        switch (ayn.a()) {
            case 0:
                return a(aym);
            case 1:
                return b(aym);
            case 2:
                return a(aym, ayn);
            default:
                return 0;
        }
    }

    private static int a(aym aym) {
        return aym.a(aym.a) ? 3 : 4;
    }

    private static int b(aym aym) {
        return aym.a(aym.b) ? 3 : 4;
    }

    private static int a(aym aym, ayn ayn) {
        if (aym.a(aym.a) || aym.a(aym.c)) {
            if (ayn.c()) {
                return 1;
            }
            return 4;
        } else if (ayn.b()) {
            return 1;
        } else {
            return 4;
        }
    }
}
