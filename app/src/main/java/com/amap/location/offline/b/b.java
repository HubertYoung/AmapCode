package com.amap.location.offline.b;

import com.amap.location.common.model.AmapLoc;
import com.amap.location.offline.b.a.a;
import com.amap.location.offline.b.a.c;
import com.amap.location.security.Core;

/* compiled from: OfflineLocator */
class b {
    static AmapLoc a(a aVar, c cVar, int i) {
        AmapLoc a = a(aVar);
        AmapLoc a2 = a(a, cVar, i);
        aVar.i = a;
        cVar.f = a2;
        if (a2 != null) {
            return a2;
        }
        if (a != null) {
            return a;
        }
        return null;
    }

    private static AmapLoc a(a aVar) {
        if (!aVar.a || aVar.e <= 60) {
            return null;
        }
        try {
            String gcl = Core.gcl(aVar.c, aVar.b, aVar.d);
            if (gcl == null) {
                return null;
            }
            String[] split = gcl.split(",");
            AmapLoc amapLoc = new AmapLoc();
            amapLoc.setTime(System.currentTimeMillis());
            amapLoc.setCoord(0);
            amapLoc.setType("file");
            amapLoc.setLon(Double.parseDouble(split[0]));
            amapLoc.setLat(Double.parseDouble(split[1]));
            amapLoc.setAccuracy((float) Integer.parseInt(split[2]));
            return amapLoc;
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
            return null;
        }
    }

    private static AmapLoc a(AmapLoc amapLoc, c cVar, int i) {
        String str;
        if (cVar.e.length() <= 0) {
            return null;
        }
        String sb = cVar.e.toString();
        if (amapLoc == null) {
            str = null;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(amapLoc.getLon());
            sb2.append(",");
            sb2.append(amapLoc.getLat());
            str = sb2.toString();
        }
        try {
            String gwl = Core.gwl(sb, cVar.a, i, str);
            if (gwl == null) {
                return null;
            }
            String[] split = gwl.split(",");
            AmapLoc amapLoc2 = new AmapLoc();
            amapLoc2.setTime(System.currentTimeMillis());
            amapLoc2.setCoord(0);
            amapLoc2.setType(AmapLoc.TYPE_OFFLINE_WIFI);
            amapLoc2.setLon(Double.parseDouble(split[0]));
            amapLoc2.setLat(Double.parseDouble(split[1]));
            amapLoc2.setAccuracy((float) Integer.parseInt(split[2]));
            return amapLoc2;
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
            return null;
        }
    }
}
