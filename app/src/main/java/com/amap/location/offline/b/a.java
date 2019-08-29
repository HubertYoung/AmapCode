package com.amap.location.offline.b;

import android.content.Context;
import android.support.annotation.NonNull;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.common.model.CellStatus;
import com.amap.location.common.model.FPS;
import com.amap.location.common.model.WiFi;
import com.amap.location.common.model.WifiStatus;
import com.amap.location.offline.b.a.b;
import com.amap.location.offline.b.b.d;
import com.amap.location.offline.c;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: OfflineCore */
public class a {
    private Context a;
    private c b;
    private com.amap.location.offline.b.c.a c;
    private C0033a d = new C0033a();
    private int e = 0;

    /* renamed from: com.amap.location.offline.b.a$a reason: collision with other inner class name */
    /* compiled from: OfflineCore */
    static class C0033a implements Comparator<WiFi> {
        private C0033a() {
        }

        /* renamed from: a */
        public int compare(WiFi wiFi, WiFi wiFi2) {
            return wiFi2.rssi - wiFi.rssi;
        }
    }

    public a(@NonNull Context context, @NonNull c cVar, @NonNull com.amap.location.offline.a aVar) {
        this.a = context;
        this.b = cVar;
        this.c = new com.amap.location.offline.b.c.a(context, cVar, aVar);
    }

    public void a() {
        this.c.a();
    }

    public void b() {
        this.c.b();
    }

    public void a(@NonNull c cVar) {
        this.c.a(cVar);
    }

    public AmapLoc a(FPS fps, int i, boolean z) {
        AmapLoc amapLoc;
        try {
            com.amap.location.offline.b.a.a a2 = a(fps.cellStatus);
            com.amap.location.offline.b.a.c a3 = a(fps.wifiStatus);
            if (!z) {
                amapLoc = b.a(a2, a3, i);
                if (amapLoc != null) {
                    StringBuilder sb = new StringBuilder("offline fps and res:");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(a2.toString());
                    sb2.append(",");
                    sb2.append(a3.toString());
                    sb2.append(",");
                    sb2.append(i);
                    sb.append(com.amap.location.common.d.a.a(sb2.toString()));
                    com.amap.location.common.d.a.b("offcore", sb.toString());
                } else {
                    StringBuilder sb3 = new StringBuilder("offline failed:");
                    sb3.append(a2.a);
                    sb3.append(",");
                    sb3.append(a3.a);
                    sb3.append(",");
                    sb3.append(a3.c);
                    com.amap.location.common.d.a.b("offcore", sb3.toString());
                }
            } else {
                amapLoc = null;
            }
            c.a(this.a, a2);
            c.a(this.a, a3);
            com.amap.location.offline.c.a.a().a(this.a, a2);
            boolean z2 = true;
            this.e++;
            if (this.e > 20) {
                d.a(this.a).b();
                this.e = 0;
            }
            if (i <= 0) {
                z2 = false;
            }
            AmapLoc a4 = a(z2, amapLoc);
            if (a4 != null) {
                a(a4, a3, a2, i);
            }
            return a4;
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
            return null;
        }
    }

    public void a(FPS fps, AmapLoc amapLoc) {
        com.amap.location.offline.b.a.a a2 = a(fps.cellStatus);
        com.amap.location.offline.b.a.c a3 = a(fps.wifiStatus);
        if (b.a(a2, a3, 0) != null) {
            StringBuilder sb = new StringBuilder("offline correct:");
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a2.toString());
            sb2.append(",");
            sb2.append(a3.toString());
            sb2.append(",(");
            sb2.append(amapLoc.getLat());
            sb2.append(",");
            sb2.append(amapLoc.getLon());
            sb2.append(")");
            sb.append(com.amap.location.common.d.a.a(sb2.toString()));
            com.amap.location.common.d.a.b("offcore", sb.toString());
        } else {
            StringBuilder sb3 = new StringBuilder("offline correct failed size:");
            sb3.append(a2.a);
            sb3.append(",");
            sb3.append(a3.c);
            sb3.append(",");
            sb3.append(a3.c);
            com.amap.location.common.d.a.b("offcore", sb3.toString());
        }
        if (amapLoc != null) {
            c.a(this.a, this.b, a2, a3, amapLoc);
        }
    }

    private com.amap.location.offline.b.a.a a(CellStatus cellStatus) {
        String a2 = com.amap.location.offline.e.a.a(cellStatus);
        return d.a(this.a).a(a2, com.amap.location.offline.e.a.a(a2));
    }

    private com.amap.location.offline.b.a.c a(WifiStatus wifiStatus) {
        com.amap.location.offline.b.a.c cVar = new com.amap.location.offline.b.a.c();
        d.a(this.a).a(a(wifiStatus, cVar), cVar);
        return cVar;
    }

    private String a(WifiStatus wifiStatus, com.amap.location.offline.b.a.c cVar) {
        StringBuilder sb = new StringBuilder();
        if (wifiStatus != null && wifiStatus.numWiFis() > 0) {
            List<WiFi> wifiList = wifiStatus.getWifiList();
            Collections.sort(wifiList, this.d);
            int min = Math.min(wifiList.size(), 30);
            cVar.a = min;
            boolean z = true;
            for (int i = 0; i < min; i++) {
                WiFi wiFi = wifiList.get(i);
                long a2 = com.amap.location.offline.e.a.a(wiFi.mac);
                if (a2 != -1) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(',');
                    }
                    sb.append(a2);
                    b bVar = new b();
                    bVar.a = a2;
                    bVar.b = wiFi.mac;
                    bVar.c = wiFi.rssi;
                    cVar.b.put(Long.valueOf(a2), bVar);
                }
            }
        }
        return sb.toString();
    }

    private AmapLoc a(boolean z, AmapLoc amapLoc) {
        if (amapLoc == null) {
            return null;
        }
        if (z && "file".equals(amapLoc.getType())) {
            return null;
        }
        if (z) {
            com.amap.location.offline.d.a.a(100035);
        } else {
            com.amap.location.offline.d.a.a(100036);
        }
        return amapLoc;
    }

    private void a(AmapLoc amapLoc, com.amap.location.offline.b.a.c cVar, com.amap.location.offline.b.a.a aVar, int i) {
        try {
            if (this.b.m != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(amapLoc.getType());
                sb.append(MergeUtil.SEPARATOR_KV);
                sb.append(amapLoc.getLon());
                sb.append(",");
                sb.append(amapLoc.getLat());
                sb.append(",");
                sb.append(amapLoc.getAccuracy());
                sb.append(MergeUtil.SEPARATOR_KV);
                if (amapLoc.getType().equals(AmapLoc.TYPE_OFFLINE_WIFI)) {
                    sb.append(cVar.d);
                    sb.append(AUScreenAdaptTool.PREFIX_ID);
                    sb.append(cVar.e);
                    sb.append(AUScreenAdaptTool.PREFIX_ID);
                    sb.append(i);
                    sb.append(AUScreenAdaptTool.PREFIX_ID);
                    sb.append(cVar.a);
                    if (aVar != null) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(aVar.c);
                        sb2.append(",");
                        sb2.append(aVar.b);
                        sb2.append(",");
                        sb2.append(aVar.d);
                        String sb3 = sb2.toString();
                        sb.append(AUScreenAdaptTool.PREFIX_ID);
                        sb.append(sb3);
                    }
                } else {
                    sb.append(aVar.f);
                    sb.append(AUScreenAdaptTool.PREFIX_ID);
                    sb.append(aVar.c);
                    sb.append(",");
                    sb.append(aVar.b);
                    sb.append(",");
                    sb.append(aVar.d);
                }
                this.b.m.a(sb.toString().getBytes());
            }
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
    }
}
