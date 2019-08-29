package com.amap.location.offline.b;

import android.content.Context;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.offline.b.a.a;
import com.amap.location.offline.b.b.d;

/* compiled from: OfflineTraining */
class c {
    public static void a(Context context, a aVar) {
        d.a(context).a(aVar);
    }

    public static void a(Context context, com.amap.location.offline.b.a.c cVar) {
        d.a(context).a(cVar);
    }

    public static void a(Context context, com.amap.location.offline.c cVar, a aVar, com.amap.location.offline.b.a.c cVar2, AmapLoc amapLoc) {
        if (amapLoc != null && amapLoc.isLocationCorrect()) {
            boolean a = a(amapLoc);
            if (!a || aVar.i == null) {
                if (!a && cVar2.f != null) {
                    AmapLoc a2 = a(cVar, cVar2.f);
                    if (a2 != null) {
                        double a3 = (double) com.amap.location.common.f.d.a(amapLoc, a2);
                        if (a3 > 100.0d) {
                            com.amap.location.common.d.a.b("offtrn", "correct wifi dis:".concat(String.valueOf(a3)));
                            d.a(context).a(cVar2, amapLoc);
                            com.amap.location.offline.d.a.a(100038, "wifiCorrect:".concat(String.valueOf(a3)).getBytes());
                        }
                    } else {
                        return;
                    }
                }
                return;
            }
            AmapLoc a4 = a(cVar, aVar.i);
            if (a4 != null) {
                double a5 = (double) com.amap.location.common.f.d.a(amapLoc, a4);
                if (a5 > 300.0d) {
                    com.amap.location.common.d.a.b("offtrn", "correct cell dis:".concat(String.valueOf(a5)));
                    d.a(context).b(aVar);
                    com.amap.location.offline.d.a.a(100038, "cellCorrect:".concat(String.valueOf(a5)).getBytes());
                }
            }
        }
    }

    private static boolean a(AmapLoc amapLoc) {
        String retype = amapLoc.getRetype();
        return "3".equals(retype) || "4".equals(retype) || "9".equals(retype);
    }

    private static AmapLoc a(com.amap.location.offline.c cVar, AmapLoc amapLoc) {
        if (amapLoc == null || cVar.b != 4) {
            return amapLoc;
        }
        if (cVar.p == null) {
            return null;
        }
        double[] a = cVar.p.a(new double[]{amapLoc.getLat(), amapLoc.getLon()});
        if (a == null || a.length < 2) {
            return null;
        }
        AmapLoc amapLoc2 = new AmapLoc();
        amapLoc2.setLat(a[0]);
        amapLoc2.setLon(a[1]);
        return amapLoc2;
    }
}
