package defpackage;

import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;

/* renamed from: dwg reason: default package */
/* compiled from: LocalBusFavoriteUtil */
public final class dwg {
    public static String a(IBusRouteResult iBusRouteResult) {
        String a = ebk.a();
        cos b = b(iBusRouteResult);
        if (b == null) {
            return null;
        }
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            coq a2 = com2.a(a);
            if (a2 != null) {
                cor b2 = a2.b(b);
                if (b2 != null) {
                    return b2.f();
                }
            }
        }
        return null;
    }

    private static String a(BusPath busPath) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < busPath.mSectionNum; i++) {
            if (busPath.mPathSections[i] != null) {
                stringBuffer.append(ebm.c(busPath.mPathSections[i].mSectionName));
                if (busPath.mSectionNum > 1 && i < busPath.mSectionNum - 1) {
                    stringBuffer.append("→");
                }
            }
        }
        return stringBuffer.toString();
    }

    public static cos b(IBusRouteResult iBusRouteResult) {
        BusPath focusBusPath = iBusRouteResult.getFocusBusPath();
        if (focusBusPath == null) {
            return null;
        }
        focusBusPath.reqStartTime = iBusRouteResult.getReqTime();
        cos cos = new cos();
        cos.a = 2;
        cos.m = iBusRouteResult.getShareFromPOI();
        cos.b = iBusRouteResult.getShareFromPOI().getPoint().x;
        cos.c = iBusRouteResult.getShareFromPOI().getPoint().y;
        cos.n = iBusRouteResult.getShareToPOI();
        cos.d = iBusRouteResult.getShareToPOI().getPoint().x;
        cos.e = iBusRouteResult.getShareToPOI().getPoint().y;
        cos.l = focusBusPath;
        cos.f = iBusRouteResult.getMethod();
        cos.j = focusBusPath.mTotalLength;
        cos.h = a(focusBusPath);
        return cos;
    }
}
