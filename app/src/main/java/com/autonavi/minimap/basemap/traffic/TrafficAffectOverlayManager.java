package com.autonavi.minimap.basemap.traffic;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlay;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import com.autonavi.minimap.base.overlay.PolygonOverlay;
import com.autonavi.minimap.base.overlay.PolygonOverlayItem;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;

public class TrafficAffectOverlayManager implements brp {
    private AffectStrokeOverlay a;
    private AffectLineOverlay b;
    private PolygonOverlay<PolygonOverlayItem> c;
    private boolean d = false;

    static class AffectLineOverlay extends LineOverlay<LineOverlayItem> {
        public AffectLineOverlay(bty bty) {
            super(bty);
            setClickable(false);
        }
    }

    static class AffectPolygonOverlay extends PolygonOverlay<PolygonOverlayItem> {
        public AffectPolygonOverlay(bty bty) {
            super(bty);
            setClickable(false);
        }
    }

    static class AffectStrokeOverlay extends LineOverlay<LineOverlayItem> {
        public AffectStrokeOverlay(bty bty) {
            super(bty);
            setClickable(false);
        }
    }

    public final void a() {
        if (this.a != null) {
            this.a.clear();
        }
        if (this.c != null) {
            this.c.clear();
        }
        if (this.b != null) {
            this.b.clear();
        }
        this.d = false;
    }

    public final void a(bty bty) {
        if (bty == null) {
            throw new RuntimeException("mapview can not be null1");
        }
        this.a = new AffectStrokeOverlay(bty);
        this.c = new AffectPolygonOverlay(bty);
        this.b = new AffectLineOverlay(bty);
    }

    public final void b(bty bty) {
        bty.F().b((BaseMapOverlay) this.a);
        bty.F().b((BaseMapOverlay) this.c);
        bty.F().b((BaseMapOverlay) this.b);
    }

    public final void c(bty bty) {
        bty.F().c(this.a);
        bty.F().c(this.c);
        bty.F().c(this.b);
    }

    public final void a(boolean z) {
        if (this.a != null) {
            this.a.setVisible(z);
        }
        if (this.c != null) {
            this.c.setVisible(z);
        }
        if (this.b != null) {
            this.b.setVisible(z);
        }
    }

    public final boolean b() {
        if ((this.a == null || this.c == null) && this.b == null) {
            return false;
        }
        return this.d;
    }

    public final void a(bsa bsa) {
        if (bsa.a != null && bsa.a.a()) {
            a aVar = bsa.a;
            ArrayList<ArrayList<GeoPoint>> arrayList = aVar.b;
            int i = aVar.c;
            int i2 = aVar.d;
            boolean z = aVar.e;
            if (arrayList != null && arrayList.size() > 0) {
                if (z) {
                    a();
                }
                if (!(this.a == null || arrayList == null || arrayList.size() <= 0)) {
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        ArrayList arrayList2 = arrayList.get(i3);
                        int size = arrayList2.size();
                        GeoPoint[] geoPointArr = new GeoPoint[size];
                        for (int i4 = 0; i4 < size; i4++) {
                            geoPointArr[i4] = (GeoPoint) arrayList2.get(i4);
                        }
                        LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, 1);
                        lineOverlayItem.setFillLineId(R.drawable.map_lr);
                        lineOverlayItem.setFillLineColor(i);
                        this.a.addItem(lineOverlayItem);
                    }
                }
                if (!(this.c == null || arrayList == null)) {
                    for (int i5 = 0; i5 < arrayList.size(); i5++) {
                        this.c.addPolygon(arrayList.get(i5), i2);
                    }
                }
                this.d = true;
            }
            if (aVar.a) {
                LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B006");
            }
        }
        if (bsa.b != null && bsa.b.a()) {
            b bVar = bsa.b;
            ArrayList<ArrayList<GeoPoint>> arrayList3 = bVar.a;
            int i6 = bVar.d;
            boolean z2 = bVar.b;
            int i7 = bVar.c;
            if (arrayList3 != null && arrayList3.size() > 0) {
                for (int i8 = 0; i8 < arrayList3.size(); i8++) {
                    ArrayList arrayList4 = arrayList3.get(i8);
                    int size2 = arrayList4.size();
                    GeoPoint[] geoPointArr2 = new GeoPoint[size2];
                    for (int i9 = 0; i9 < size2; i9++) {
                        geoPointArr2[i9] = (GeoPoint) arrayList4.get(i9);
                    }
                    LineOverlayItem lineOverlayItem2 = new LineOverlayItem(!z2 ? 1 : 5, geoPointArr2, i6);
                    if (!z2) {
                        lineOverlayItem2.setFillLineId(R.drawable.map_lr);
                    } else {
                        lineOverlayItem2.setFillLineId(R.drawable.ic_cross_road_dash);
                    }
                    lineOverlayItem2.setFillLineColor(i7);
                    this.b.addItem(lineOverlayItem2);
                }
            }
        }
    }
}
