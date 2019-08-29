package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.bundle.routecommute.drive.page.AjxDriveCommuteEndPage;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;

/* renamed from: bae reason: default package */
/* compiled from: AjxDriveCommuteEndPresenter */
public final class bae<Page extends AjxDriveCommuteEndPage> extends Ajx3PagePresenter {
    protected Page a;

    public bae(Page page) {
        super(page);
        this.a = page;
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onResume() {
        super.onResume();
        Page page = this.a;
        bty mapView = page.getMapManager().getMapView();
        if (mapView != null) {
            mapView.t(false);
            mapView.b(true);
            if (page.getSuspendManager() != null && page.getSuspendManager().d() != null) {
                page.getSuspendManager().d().f();
            }
        }
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onDestroy() {
        super.onDestroy();
        Page page = this.a;
        bty mapView = page.getMapManager().getMapView();
        if (mapView != null) {
            if (page.a.a) {
                mapView.b(page.a.a);
            }
            mapView.e(page.a.e);
            mapView.g(page.a.f);
            mapView.f((float) page.a.g);
            mapView.a(page.a.d, page.a.c, page.a.b);
            mapView.t(new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("blind_mode_status", false));
        }
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.a(true);
        }
        if (page.b != null) {
            page.b.removeDialogModuleProvider("drive");
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
    }
}
