package defpackage;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.entity.BusPaths;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.bus.navidetail.model.pojo.BusAlternativeItem;
import java.util.Map;

/* renamed from: dwv reason: default package */
/* compiled from: BusNaviDetailRepository */
public final class dwv {
    public dwx a;
    public boolean b;
    public String[] c;
    public dww d;
    public ebu e = new ebu();
    public Map<String, Map<String, BusAlternativeItem>> f;
    private boolean[] g;
    private int h;

    public final void a(dwx dwx) {
        this.a = dwx;
        int c2 = c();
        this.c = new String[c2];
        this.g = new boolean[c2];
        eao.a((String) "zq", "setBusRouteResult size:".concat(String.valueOf(c2)));
        if (!(this.a == null || this.a.b == null)) {
            BusPaths busPathsResult = this.a.b.getBusPathsResult();
            POI fromPOI = this.a.b.getFromPOI();
            if (!"我的位置".equals(fromPOI.getName()) && !TextUtils.isEmpty(busPathsResult.mStartDes) && TextUtils.isEmpty(fromPOI.getName())) {
                fromPOI.setName(busPathsResult.mStartDes);
            }
            POI toPOI = this.a.b.getToPOI();
            if (!"我的位置".equals(toPOI.getName()) && !TextUtils.isEmpty(busPathsResult.mEndDes) && TextUtils.isEmpty(toPOI.getName())) {
                toPOI.setName(busPathsResult.mEndDes);
            }
        }
        a(this.e.b);
    }

    public final boolean a() {
        return this.a != null && this.a.d;
    }

    public final int b() {
        if (this.a == null || this.a.b == null) {
            return -1;
        }
        return this.a.b.getFocusBusPathIndex();
    }

    public final void a(boolean z, @Nullable View view) {
        if (z) {
            this.e.a(view);
        } else {
            this.e.a();
        }
    }

    public final int c() {
        if (!(this.a == null || this.a.b == null || this.h != 0)) {
            BusPaths busPathsResult = this.a.b.getBusPathsResult();
            if (!(busPathsResult == null || busPathsResult.mBusPaths == null)) {
                this.h = busPathsResult.mBusPaths.length;
            }
        }
        return this.h;
    }

    public final void a(boolean z) {
        if (this.a != null && this.a.b != null) {
            int b2 = b();
            int c2 = c();
            if (b2 != -1 && b2 < c2) {
                if (this.a.a && !z) {
                    this.c[b2] = this.a.e;
                } else if (this.a.b != null && !this.g[b2]) {
                    this.c[b2] = dwg.a(this.a.b);
                    this.g[b2] = true;
                }
                this.b = !TextUtils.isEmpty(this.c[b2]);
            }
        }
    }

    public final boolean d() {
        boolean z;
        if (!(this.a == null || this.a.b == null)) {
            IBusRouteResult iBusRouteResult = this.a.b;
            String a2 = ebk.a();
            cos b2 = dwg.b(iBusRouteResult);
            if (b2 == null) {
                z = false;
            } else {
                com com2 = (com) ank.a(com.class);
                cor cor = null;
                if (com2 != null) {
                    coq a3 = com2.a(a2);
                    if (a3 != null) {
                        cor = a3.a(b2);
                    }
                }
                String baseDataForFavorite = iBusRouteResult.getBaseDataForFavorite(iBusRouteResult.getFocusBusPathIndex());
                if (cor != null) {
                    asy asy = (asy) a.a.a(asy.class);
                    if (asy != null) {
                        asy.a(AMapAppGlobal.getApplication(), cor.f(), baseDataForFavorite);
                    }
                }
                z = true;
            }
            this.b = z;
            if (this.b) {
                int b3 = b();
                int c2 = c();
                if (b3 != -1 && b3 < c2 && TextUtils.isEmpty(this.c[b3])) {
                    this.c[b3] = dwg.a(this.a.b);
                }
            }
        }
        return this.b;
    }
}
