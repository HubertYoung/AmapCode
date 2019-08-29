package defpackage;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.bus.busline.overlay.BusLineLineOverlay;
import com.autonavi.minimap.route.bus.busline.overlay.BusLinePointOverlay;
import com.autonavi.minimap.route.bus.busline.overlay.RealTimeBusOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusPointOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusline;

/* renamed from: dum reason: default package */
/* compiled from: BusLineToMapOverlayManager */
public final class dum {
    public BusLinePointOverlay a;
    public BusLinePointOverlay b;
    public BusLineLineOverlay c = new BusLineLineOverlay(this.i);
    public RealTimeBusOverlay d;
    public RouteBusPointOverlay e;
    public ean f;
    public RouteBusStationNameOverlay g;
    public a h;
    public bty i;
    private BusLinePointOverlay j;

    /* renamed from: dum$a */
    /* compiled from: BusLineToMapOverlayManager */
    public interface a {
        void a(int i);

        void b(int i);
    }

    public dum(AbstractBaseMapPage abstractBaseMapPage) {
        this.i = abstractBaseMapPage.getMapManager().getMapView();
        abstractBaseMapPage.addOverlay(this.c);
        this.a = new BusLinePointOverlay(this.i);
        abstractBaseMapPage.addOverlay(this.a);
        this.g = new RouteBusStationNameOverlay(this.i);
        this.g.setMinDisplayLevel(13);
        abstractBaseMapPage.addOverlay(this.g);
        this.e = new RouteBusPointOverlay(this.i);
        abstractBaseMapPage.addOverlay(this.e);
        this.d = new RealTimeBusOverlay(this.i, this.a, this.e);
        abstractBaseMapPage.addOverlay(this.d);
        this.b = new BusLinePointOverlay(this.i);
        this.b.addCheckCoverOverlay(this.g, this.i);
        abstractBaseMapPage.addOverlay(this.b);
        this.j = new BusLinePointOverlay(this.i);
        this.j.addCheckCoverOverlay(this.g, this.i);
        abstractBaseMapPage.addOverlay(this.j);
        if (this.f == null) {
            this.f = new ean(this.i, this.c, abstractBaseMapPage.getSuspendManager().d());
            if (1 == abstractBaseMapPage.getResources().getConfiguration().orientation) {
                this.f.a(100, 130, 100, 150);
            } else {
                this.f.a(100, 90, 100, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
            }
        }
        this.a.setOnItemClickListener(new OnItemClickListener() {
            public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                if (dum.this.h != null) {
                    dum.this.h.b(dum.this.a.getItems().indexOf(obj));
                }
            }
        });
        this.b.setOnItemClickListener(new OnItemClickListener() {
            public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                if (dum.this.h != null) {
                    dum.this.h.a(dum.this.b.getItems().indexOf(obj));
                }
            }
        });
        this.g.setOnItemClickListener(new OnItemClickListener() {
            public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                if (dum.this.h != null) {
                    int indexOf = dum.this.g.getItems().indexOf(obj) + 1;
                    dum.this.a.setFocus(indexOf, true);
                    dum.this.h.b(indexOf);
                }
            }
        });
    }

    public final void a() {
        if (this.e != null) {
            this.e.clear();
        }
        if (this.d != null) {
            this.d.clear();
            this.d.clearFocus();
        }
        if (this.c != null) {
            this.c.clear();
            this.c.clearFocus();
        }
        if (this.a != null) {
            this.a.clear();
            this.a.clearFocus();
        }
        if (this.b != null) {
            this.b.clear();
            this.b.clearFocus();
        }
        if (this.j != null) {
            this.j.clear();
            this.j.clearFocus();
        }
        if (this.g != null) {
            this.g.clear();
        }
    }

    public final void a(Context context, int i2, GeoPoint geoPoint, CharSequence charSequence) {
        if (context != null) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.busline_map_stationname_layout, null);
            TextView textView = (TextView) inflate.findViewById(R.id.station_name);
            textView.setText(charSequence);
            textView.setTextColor(context.getResources().getColor(R.color.f_c_2));
            textView.getPaint().setShadowLayer(10.0f, 0.0f, 0.0f, context.getResources().getColor(R.color.white));
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            pointOverlayItem.mBgMarker = this.j.createMarker(i2, inflate, 7, 0.0f, 0.0f, false);
            this.j.addItem(pointOverlayItem);
        }
    }

    public static int a(String str) {
        if (TextUtils.isEmpty(str) || str.trim().equals(MetaRecord.LOG_SEPARATOR)) {
            return 0;
        }
        try {
            if (str.contains(MetaRecord.LOG_SEPARATOR)) {
                return Color.parseColor(str);
            }
            return Color.parseColor(MetaRecord.LOG_SEPARATOR.concat(String.valueOf(str)));
        } catch (Exception e2) {
            e2.printStackTrace();
            return -8995585;
        }
    }

    public final void a(Bus bus, RealTimeBusline realTimeBusline) {
        this.d.clear();
        this.e.clear();
        if (bus != null && realTimeBusline != null) {
            this.d.addRealTimeBuses(bus, realTimeBusline);
        }
    }

    public final void a(Bus bus, int i2) {
        if (bus != null && bus.stations != null) {
            int length = bus.stations.length;
            if (i2 < 0) {
                this.a.clearFocus();
                this.b.clearFocus();
            } else if (i2 == 0) {
                this.b.setFocus(0, true);
                this.a.clearFocus();
            } else if (i2 == length - 1) {
                this.b.setFocus(1, true);
                this.a.clearFocus();
            } else {
                this.a.setFocus(i2, true);
                this.b.clearFocus();
            }
        }
    }
}
