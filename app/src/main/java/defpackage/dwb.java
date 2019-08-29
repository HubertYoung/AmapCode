package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PoiFilterPointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.bus.localbus.overlay.RealtimeBusNameOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RealtimeStationNameOverlay;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusPositionPage;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;

/* renamed from: dwb reason: default package */
/* compiled from: NearBusStopOverlayDisplay */
public final class dwb implements OnItemClickListener {
    private static final int j = R.drawable.icon_rt_bus_stop1;
    public RTBusPositionPage a;
    public bty b;
    public PoiFilterPointOverlay c;
    public RealtimeStationNameOverlay d;
    public RealtimeBusNameOverlay e;
    public ArrayList<dyi> f = new ArrayList<>();
    public int g = 0;
    public boolean h = true;
    public float i;
    private Context k;
    private LayoutInflater l;

    public dwb(RTBusPositionPage rTBusPositionPage) {
        this.b = rTBusPositionPage.getMapManager().getMapView();
        if (this.b != null) {
            this.k = rTBusPositionPage.getContext();
            this.a = rTBusPositionPage;
            this.l = (LayoutInflater) this.k.getSystemService("layout_inflater");
            this.d = new RealtimeStationNameOverlay(this.b);
            this.d.setCheckCover(true);
            this.d.setHideIconWhenCovered(true);
            this.d.setAutoSetFocus(false);
            this.d.setMinDisplayLevel(13);
            this.d.setPoiFilterType(-1);
            this.d.setPoiFilterAnchor(2);
            this.d.setOnItemClickListener(this);
            this.c = new PoiFilterPointOverlay(this.b);
            this.c.setHideIconWhenCovered(true);
            this.c.setAutoSetFocus(false);
            this.c.setMinDisplayLevel(13);
            this.c.setPoiFilterType(-1);
            this.c.setPoiFilterAnchor(2);
            this.e = new RealtimeBusNameOverlay(this.b);
            this.e.setClickable(false);
            this.e.addCheckCoverOverlay(this.d);
            this.e.setMinDisplayLevel(13);
            this.c.setOnItemClickListener(this);
        }
    }

    public final void b() {
        if (this.b != null) {
            this.b.e(16);
        }
    }

    public final void a(boolean z) {
        if (this.b != null) {
            this.b.b(this.b.al() / 2, (this.b.am() / 4) + 80);
            if (z) {
                this.b.a(LocationInstrument.getInstance().getLatestPosition().x, LocationInstrument.getInstance().getLatestPosition().y);
            }
        }
    }

    public final void b(boolean z) {
        if (this.a.getSuspendManager() != null && this.a.getSuspendManager().d() != null) {
            this.a.getSuspendManager().d().a(z ? 2 : 0);
        }
    }

    public final void c() {
        d();
        for (int i2 = 0; i2 < this.f.size(); i2++) {
            a(this.f.get(i2), i2);
        }
        b(this.g);
        b(this.f.get(this.g));
        this.g = this.g;
    }

    private void a(dyi dyi, int i2) {
        b(dyi, i2);
        a(dyi);
    }

    private PointOverlayItem b(dyi dyi, int i2) {
        GeoPoint geoPoint = new GeoPoint(dyi.d, dyi.c);
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 3);
        mapViewLayoutParams.mode = 0;
        View inflate = this.l.inflate(R.layout.layout_bus_realtime_3, null, false);
        ((TextView) inflate.findViewById(R.id.rt_label)).setText(dyi.b);
        this.b.a(inflate, (LayoutParams) mapViewLayoutParams);
        dwd dwd = new dwd(geoPoint);
        dwd.mDefaultMarker = this.d.createMarker(i2, inflate, 6, 0.0f, 0.0f, false);
        this.b.a(inflate);
        dwd.a = this.b.F().b(dwd.mDefaultMarker.mID);
        this.d.addItem(dwd);
        return dwd;
    }

    private PointOverlayItem a(dyi dyi) {
        PointOverlayItem pointOverlayItem = new PointOverlayItem(new GeoPoint(dyi.d, dyi.c));
        a(pointOverlayItem);
        this.c.addItem(pointOverlayItem);
        return pointOverlayItem;
    }

    private PointOverlayItem b(dyi dyi) {
        this.e.clear();
        this.e.clearFocus();
        GeoPoint geoPoint = new GeoPoint(dyi.d, dyi.c);
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 3);
        mapViewLayoutParams.mode = 0;
        View inflate = this.l.inflate(R.layout.layout_bus_realtime_4, null, false);
        ((TextView) inflate.findViewById(R.id.rt_label)).setText(dyi.b);
        this.b.a(inflate, (LayoutParams) mapViewLayoutParams);
        dwd dwd = new dwd(geoPoint);
        dwd.mDefaultMarker = this.e.createMarker(2, inflate, 4, 0.0f, 0.0f, false);
        this.b.a(inflate);
        dwd.a = this.b.F().b(dwd.mDefaultMarker.mID);
        this.e.addItem(dwd);
        return dwd;
    }

    private void a(PointOverlayItem pointOverlayItem) {
        pointOverlayItem.mDefaultMarker = this.c.createMarker(j, 4);
    }

    private void b(int i2) {
        this.c.setPointItemVisble(i2, false, false);
        this.d.setPointItemVisble(i2, false, false);
    }

    public final void d() {
        if (this.c != null) {
            this.c.clear();
            this.c.removeAll();
            this.c.clearFocus();
        }
        if (this.d != null) {
            this.d.clear();
            this.d.removeAll();
            this.d.clearFocus();
        }
        if (this.e != null) {
            this.e.clear();
            this.e.removeAll();
            this.e.clearFocus();
        }
    }

    public final void a(GeoPoint geoPoint) {
        if (this.b != null && geoPoint != null) {
            this.b.a((GLGeoPoint) geoPoint);
        }
    }

    public final GeoPoint a(int i2) {
        if (i2 < 0 || i2 >= this.f.size()) {
            return null;
        }
        dyi dyi = this.f.get(i2);
        return new GeoPoint(dyi.d, dyi.c);
    }

    public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
        if (this.a != null && (baseMapOverlay instanceof PointOverlay) && (obj instanceof PointOverlayItem)) {
            int itemIndex = ((PointOverlay) baseMapOverlay).getItemIndex((PointOverlayItem) obj);
            if (itemIndex >= 0 && itemIndex < this.f.size()) {
                b(itemIndex);
                b(this.f.get(itemIndex));
                a(a(itemIndex));
                int i2 = this.g;
                this.c.setPointItemVisble(i2, true, true);
                this.d.setPointItemVisble(i2, true, true);
                this.g = itemIndex;
                RTBusPositionPage rTBusPositionPage = this.a;
                if (rTBusPositionPage.g != null && rTBusPositionPage.g.getVisibility() == 0) {
                    rTBusPositionPage.g.showStation(itemIndex);
                }
            }
        }
    }

    public final void a() {
        if (this.b != null) {
            this.b.d(true);
        }
        b(true);
        if (this.h && !this.f.isEmpty()) {
            c();
        }
        a(a(this.g));
    }
}
