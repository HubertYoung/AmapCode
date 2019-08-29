package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.basemap.traffic.SinaTrafficOverlay;
import com.autonavi.minimap.basemap.traffic.page.TrafficMapPage;

/* renamed from: csq reason: default package */
/* compiled from: TrafficMapPresenter */
public final class csq extends AbstractBaseMapPagePresenter<TrafficMapPage> implements OnClickListener {
    private static final String a = TrafficMapPage.class.getName();
    private POI b;
    private int c = 17;
    private SinaTrafficOverlay d;

    public csq(TrafficMapPage trafficMapPage) {
        super(trafficMapPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        cde suspendManager = ((TrafficMapPage) this.mPage).getSuspendManager();
        if (suspendManager != null) {
            suspendManager.d().f();
        }
        PageBundle arguments = ((TrafficMapPage) this.mPage).getArguments();
        NetworkParam.setSa(Constant.SOURCE_SINA);
        if (arguments != null) {
            this.b = (POI) arguments.getObject("POI");
            this.c = arguments.getInt(H5PermissionManager.level);
            if (this.b != null) {
                cde suspendManager2 = ((TrafficMapPage) this.mPage).getSuspendManager();
                if (!(suspendManager2 == null || suspendManager2.d() == null)) {
                    suspendManager2.d().a(false);
                }
                MapManager mapManager = ((TrafficMapPage) this.mPage).getMapManager();
                if (mapManager != null) {
                    this.d = new SinaTrafficOverlay(mapManager.getMapView());
                    ((TrafficMapPage) this.mPage).addOverlay(this.d);
                }
                onShowGeoPoiDetailView(arguments, 0);
                if (suspendManager2 != null && suspendManager2.d() != null) {
                    suspendManager2.d().a(false);
                }
            }
        }
    }

    public final void onStart() {
        super.onStart();
        if (this.b != null && this.d != null) {
            bqx bqx = (bqx) ank.a(bqx.class);
            if (bqx != null) {
                bqx.a(true, true, ((TrafficMapPage) this.mPage).getMapManager(), ((TrafficMapPage) this.mPage).getContext());
            }
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("traffic", true);
            if (((TrafficMapPage) this.mPage).getMapView() != null) {
                ((TrafficMapPage) this.mPage).getMapView().f((float) this.c);
                ((TrafficMapPage) this.mPage).getMapView().a(this.b.getPoint().x, this.b.getPoint().y);
            }
            this.d.setItem(new POIOverlayItem(this.b));
        }
    }

    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_btn_left) {
            ((TrafficMapPage) this.mPage).getActivity().onBackPressed();
            return;
        }
        if (id == R.id.title_btn_right) {
            NetworkParam.setSa(null);
            ((TrafficMapPage) this.mPage).finish();
        }
    }
}
