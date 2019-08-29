package defpackage;

import android.content.Context;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.annotation.MainMapFeature;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;

@MainMapFeature
/* renamed from: arl reason: default package */
/* compiled from: MainPageFeatureManager */
public class arl implements czu, czy, czz {
    bru a = new bru(new a() {
        public final Context a() {
            return arl.this.d.getContext();
        }

        public final bty b() {
            return DoNotUseTool.getMapView();
        }

        public final MapManager c() {
            return DoNotUseTool.getMapManager();
        }
    });
    czn b;
    private final String c = "RESET_TRAFFIC_STATE_FOR_REDESIGN";
    /* access modifiers changed from: private */
    public AbstractBaseMapPage d = ((AbstractBaseMapPage) ((IMainMapService) ank.a(IMainMapService.class)).e());
    private bdv e = new bdv(new a() {
        public final AbstractBaseMapPage a() {
            return arl.this.d;
        }
    });
    private final MapSharePreference f = new MapSharePreference(SharePreferenceName.SharedPreferences);

    public arl() {
        if (new bnv().a()) {
            this.b = new aqi(new a() {
                public final bru a() {
                    return arl.this.a;
                }

                public final bty b() {
                    return DoNotUseTool.getMapView();
                }
            });
            IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
            if (iMainMapService != null) {
                czn czn = (czn) iMainMapService.a(czn.class);
                if (czn != null) {
                    czn.setVoiceOperationServiceDelegate(this.b);
                }
            }
        }
    }

    public void onResume() {
        this.e.a();
    }

    public void onPause() {
        this.e.b();
    }

    public void onDestroy() {
        brz a2 = brz.a();
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            mapView.b((b) a2.c);
        }
        a2.a.removeCallbacks(a2.b);
    }

    public final void j_() {
        this.e.c();
    }

    public final void k_() {
        this.e.d();
    }

    public void onCreate() {
        if (!this.f.contains("RESET_TRAFFIC_STATE_FOR_REDESIGN")) {
            this.f.putIntValue("RESET_TRAFFIC_STATE_FOR_REDESIGN", 1);
            this.a.a(true, false, false);
        }
        brz a2 = brz.a();
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            mapView.a((b) a2.c);
        }
    }
}
