package defpackage;

import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drive.naviend.motor.page.AjxRouteMotorNaviEndPage;
import com.amap.bundle.drivecommon.navi.navidata.NavigationDataResult;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.basemap.errorback.inter.IReportErrorManager;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.iflytek.tts.TtsService.PlaySoundUtils;

/* renamed from: og reason: default package */
/* compiled from: AjxRouteMotorNaviEndPresenter */
public final class og<Page extends AjxRouteMotorNaviEndPage> extends Ajx3PagePresenter {
    protected Page a;

    public og(Page page) {
        super(page);
        this.a = page;
    }

    public final void onPageCreated() {
        super.onPageCreated();
        Page page = this.a;
        PageBundle arguments = page.getArguments();
        if (arguments != null) {
            page.f = (NavigationDataResult) arguments.getObject("key_navigation_data_result");
            page.g = arguments.getString("navi_type", "");
            page.h = (IReportErrorManager) ank.a(IReportErrorManager.class);
        }
        Page page2 = this.a;
        bty mapView = page2.getMapManager().getMapView();
        if (mapView != null) {
            mapView.a(mapView.p(false), 0, 4);
            mapView.m(true);
            mapView.g(page2.getResources().getColor(R.color.navigation_done_map_shadow));
            if (bno.a) {
                ku.a().c("NaviMonitor", "[AjxRouteMotorNaviEndPage]onResume#setMask(true)");
            }
            NaviManager.a().a(false);
            mapView.b(false);
            mapView.e(0.0f);
            mapView.g(0.0f);
        }
    }

    public final void onResume() {
        super.onResume();
        Page page = this.a;
        if (page.getSuspendManager() != null && page.getSuspendManager().d() != null) {
            page.getSuspendManager().d().f();
        }
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onPause() {
        super.onPause();
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onDestroy() {
        super.onDestroy();
        Page page = this.a;
        if (page.a != null) {
            page.a.setDriveEndCallback(null);
        }
        MapManager mapManager = page.getMapManager();
        if (mapManager != null) {
            IOverlayManager overlayManager = mapManager.getOverlayManager();
            if (overlayManager != null) {
                overlayManager.setGPSVisible(true);
            }
        }
        Page page2 = this.a;
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        NaviManager.a().a(false);
        boolean booleanValue = mapSharePreference.getBooleanValue("traffic", false);
        if (page2.getMapManager().getMapView() != null) {
            page2.getMapManager().getMapView().b(booleanValue);
        }
        bty mapView = page2.getMapManager().getMapView();
        if (mapView != null) {
            mapView.a(mapView.p(false), 0, 0);
            mapView.a(0.5f, 0.5f);
        }
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.a(true);
        }
        PlaySoundUtils.getInstance().release();
    }

    public final ON_BACK_TYPE onBackPressed() {
        return super.onBackPressed();
    }
}
