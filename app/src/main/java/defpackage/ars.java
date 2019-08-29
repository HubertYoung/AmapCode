package defpackage;

import android.app.Activity;
import android.content.Intent;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.manger.IIntentUtil;
import com.autonavi.map.manger.MapInterfaceFactory;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.maphome.data.PoiList;
import com.autonavi.minimap.map.BasePoiOverlay;

/* renamed from: ars reason: default package */
/* compiled from: ShortUrlHandler */
public final class ars {
    arn a;
    BasePoiOverlay b = new BasePoiOverlay(a());

    /* renamed from: ars$a */
    /* compiled from: ShortUrlHandler */
    class a implements cqd {
        private a() {
        }

        /* synthetic */ a(ars ars, byte b) {
            this();
        }

        public final void a(int i) {
            if (i != 1) {
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.ic_net_error_tipinfo));
            }
        }

        public final void a(PoiList poiList) {
            MapManager mapManager;
            if (ars.this.a == null) {
                mapManager = null;
            } else {
                mapManager = DoNotUseTool.getMapManager();
            }
            if (mapManager != null && ars.this.b != null) {
                mapManager.getOverlayManager().clearAllFocus();
                ars.this.b.clear();
                if (poiList != null) {
                    poiList.addDataToOverLay(ars.this.b);
                }
            }
        }

        public final void a(final int i, final int i2) {
            aho.a(new Runnable() {
                final /* synthetic */ int a = 13;

                public final void run() {
                    bty a2 = ars.a();
                    if (a2 != null) {
                        a2.e(this.a);
                        a2.a((GLGeoPoint) new GeoPoint(i, i2));
                    }
                }
            });
        }

        public final void a(final POI poi, int i) {
            bty a2 = ars.a();
            if (a2 != null) {
                a2.f((float) i);
                aho.a(new Runnable() {
                    public final void run() {
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putObject("POI", poi);
                        pageBundle.putInt("poi_detail_page_type", 4);
                        ars.this.a.startPage((String) "amap.search.action.poidetail", pageBundle);
                    }
                }, 1000);
            }
        }
    }

    public ars(arn arn) {
        this.a = arn;
        this.a.addOverlay(this.b);
    }

    public final void a(PageBundle pageBundle) {
        Intent intent = (Intent) pageBundle.getObject("key_schema_short_url_intent");
        if (intent != null) {
            a(intent);
        }
    }

    private Activity b() {
        if (this.a == null) {
            return null;
        }
        return this.a.getActivity();
    }

    private cde c() {
        if (this.a == null) {
            return null;
        }
        return DoNotUseTool.getSuspendManager();
    }

    private void a(Intent intent) {
        if ((intent.getFlags() & 1048576) != 1048576 && c() != null && b() != null) {
            if (c().d() != null) {
                c().d().a(false);
            }
            IIntentUtil intentUtil = MapInterfaceFactory.getInstance().getIntentUtil(b(), intent);
            intentUtil.setMapCallBack(new a(this, 0));
            intentUtil.processIntent();
        }
    }

    static bty a() {
        MapManager mapManager = DoNotUseTool.getMapManager();
        if (mapManager == null) {
            return null;
        }
        bty mapView = mapManager.getMapView();
        if (mapView == null) {
            return null;
        }
        return mapView.e();
    }
}
