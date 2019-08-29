package defpackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.jni.ae.route.route.Route;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.search.overlay.SearchNaviBubbleOverlay;
import com.autonavi.map.search.overlay.SearchRouteOverlay;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.base.overlay.RouteItem;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.route.IDriveRouteResult;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import de.greenrobot.event.EventBus;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;

/* renamed from: bxm reason: default package */
/* compiled from: PoiRoutePlanningHandler */
public final class bxm {
    static final /* synthetic */ boolean i = true;
    final IMapPage a;
    final bty b;
    /* access modifiers changed from: 0000 */
    public b c;
    com.autonavi.common.Callback.a d;
    SearchRouteOverlay e;
    SearchNaviBubbleOverlay f;
    dhy g = new dhy() {
        public final void a(IDriveRouteResult iDriveRouteResult) {
            bxm.this.j = iDriveRouteResult == null ? null : iDriveRouteResult.getCalcRouteResult();
            if (bxm.this.j != null && bxm.this.j.getPathCount() > 0) {
                bxm.a(bxm.this, (POI) bxm.this.c.c, bxm.this.j.getRoute(0));
            }
        }

        public final void a() {
            bxm.this.a((POI) bxm.this.c.c, (String) "点击立即前往");
        }

        public final void b() {
            bxm.this.a((POI) bxm.this.c.c, (String) "点击立即前往");
        }
    };
    defpackage.bro.a h = new defpackage.bro.a() {
        public final void a() {
            bxm.this.c();
        }
    };
    /* access modifiers changed from: private */
    public CalcRouteResult j;
    private View k;
    private OnClickListener l = new OnClickListener() {
        public final void onClick(View view) {
            EventBus.getDefault().post(new cbm());
            bxm.h().a((POI) bxm.this.c.c);
            LogManager.actionLogV25(LogConstant.SEARCH_RESULT_MAP, LogConstant.MAIN_XUANDIAN_SOU_ZHOU_BIAN, new SimpleEntry(TrafficUtil.POIID, bxm.this.c.c.getId()), new SimpleEntry("text", bxm.this.c.d), new SimpleEntry("from", "2"));
        }
    };

    /* renamed from: bxm$a */
    /* compiled from: PoiRoutePlanningHandler */
    public interface a {
        boolean a();
    }

    /* renamed from: bxm$b */
    /* compiled from: PoiRoutePlanningHandler */
    public static class b {
        final int a;
        final String b;
        final SearchPoi c;
        final String d;
        final a e;

        public b(int i, SearchPoi searchPoi, String str, String str2, a aVar) {
            this.a = i;
            this.c = searchPoi;
            this.b = str;
            this.d = str2;
            this.e = aVar;
        }
    }

    public bxm(IMapPage iMapPage) {
        this.a = iMapPage;
        this.b = this.a.getMapManager().getMapView();
        this.e = new SearchRouteOverlay(this.b);
        this.b.F().b((BaseMapOverlay) this.e);
        this.f = new SearchNaviBubbleOverlay(this.b);
        this.b.F().b((BaseMapOverlay) this.f);
    }

    public final void a() {
        MapManager mapManager = this.a.getMapManager();
        if (mapManager != null) {
            mapManager.removeMapModeChangeListener(this.h);
            mapManager.addMapModeChangeListener(this.h);
        }
        c();
    }

    public final void b() {
        this.a.getMapManager().removeMapModeChangeListener(this.h);
        i();
    }

    private void i() {
        this.e.clear();
        this.f.clear();
    }

    private void j() {
        this.e.restore();
        this.f.restore();
    }

    private void k() {
        this.e.save(null);
        this.f.save(null);
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        int w = this.b.w();
        boolean z = false;
        int p = this.b.p(false);
        if (w < 17 && p == 0 && d() && (this.c.e == null || this.c.e.a())) {
            z = true;
        }
        if (z) {
            j();
        } else {
            i();
        }
        this.f.setEnableClick(z);
    }

    /* access modifiers changed from: 0000 */
    public final boolean d() {
        if (this.c == null || this.c.a != 0 || this.c.c.getRoutePlanning() != 1 || !this.c.b.equals("IDQ") || LocationInstrument.getInstance().getLatestPosition(false) == null) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final void e() {
        if (this.d != null) {
            this.d.cancel();
        }
        if (this.j != null) {
            this.j.destroy();
        }
        i();
        k();
    }

    /* access modifiers changed from: 0000 */
    public final void a(POI poi, String str) {
        i();
        View l2 = l();
        a(l2, str);
        a(this.f, poi, l2);
        c();
    }

    private static void a(View view, String str) {
        view.findViewById(R.id.layout_bubble_content).setVisibility(8);
        view.findViewById(R.id.layout_bubble_placeholder).setVisibility(0);
        ((TextView) view.findViewById(R.id.txt_bubble_placeholder)).setText(str);
    }

    private View l() {
        if (this.k == null) {
            this.k = LayoutInflater.from(this.a.getContext()).inflate(R.layout.poi_layout_overlay, null);
        }
        return this.k;
    }

    private void a(SearchNaviBubbleOverlay searchNaviBubbleOverlay, POI poi, View view) {
        searchNaviBubbleOverlay.setClickList(Collections.singletonList(a(view.findViewById(R.id.layout_bubble), this.l)));
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, poi.getPoint(), 3);
        mapViewLayoutParams.mode = 0;
        this.b.a(view, (LayoutParams) mapViewLayoutParams);
        PointOverlayItem pointOverlayItem = new PointOverlayItem(poi.getPoint());
        pointOverlayItem.mDefaultMarker = searchNaviBubbleOverlay.createMarker(0, view, 5, 0.0f, 0.0f, false);
        this.b.a(view);
        searchNaviBubbleOverlay.save(Collections.singletonList(pointOverlayItem));
    }

    private aly a(final View view, final OnClickListener onClickListener) {
        view.measure(0, 0);
        aly aly = new aly(view.getMeasuredWidth(), view.getMeasuredHeight());
        aly.e = new defpackage.aly.a() {
            public final void a() {
                if (onClickListener != null) {
                    onClickListener.onClick(view);
                }
            }
        };
        return aly;
    }

    static dhz f() {
        dhz dhz = (dhz) ank.a(dhz.class);
        if (i || dhz != null) {
            return dhz;
        }
        throw new AssertionError();
    }

    static GeoPoint g() {
        return LocationInstrument.getInstance().getLatestPosition(false);
    }

    static /* synthetic */ dfm h() {
        dfm dfm = (dfm) ank.a(dfm.class);
        if (i || dfm != null) {
            return dfm;
        }
        throw new AssertionError();
    }

    static /* synthetic */ void a(bxm bxm, POI poi, Route route) {
        bxm.i();
        bxm.e.save(Collections.singletonList((RouteItem) f().a(route)));
        View l2 = bxm.l();
        l2.findViewById(R.id.layout_bubble_content).setVisibility(0);
        l2.findViewById(R.id.layout_bubble_placeholder).setVisibility(8);
        ((TextView) l2.findViewById(R.id.txt_estimate_distance)).setText(cfe.a(route.getRouteLength()));
        ((TextView) l2.findViewById(R.id.txt_estimate_time)).setText(lf.c(route.getRouteTime()));
        bxm.a(bxm.f, poi, l2);
        bxm.c();
    }
}
