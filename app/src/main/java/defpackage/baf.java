package defpackage;

import android.view.MotionEvent;
import com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage;
import com.autonavi.map.fragmentcontainer.page.MapBasePresenter;

/* renamed from: baf reason: default package */
/* compiled from: AjxDriveCommutePresenter */
public final class baf extends MapBasePresenter {
    private AjxDriveCommutePage a = null;
    private boolean b = true;

    public baf(AjxDriveCommutePage ajxDriveCommutePage) {
        super(ajxDriveCommutePage);
        this.a = ajxDriveCommutePage;
        this.a.initMapView();
    }

    public final boolean onMapTouchEvent(MotionEvent motionEvent) {
        this.a.onPageMapTouchEvent(motionEvent);
        return super.onMapTouchEvent(motionEvent);
    }

    public final boolean onMapLevelChange(boolean z) {
        return super.onMapLevelChange(z);
    }

    public final void onMapSurfaceChanged(int i, int i2) {
        super.onMapSurfaceChanged(i, i2);
    }

    public final void onMapSurfaceCreated() {
        super.onMapSurfaceCreated();
    }

    public final void onStart() {
        super.onStart();
        if (this.b) {
            anf.a(2, 0);
            this.b = false;
        }
    }

    public final void onStop() {
        super.onStop();
        if (drl.a().b()) {
            anf.a(0, -1);
            this.b = true;
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        this.a.restoreMap();
    }
}
