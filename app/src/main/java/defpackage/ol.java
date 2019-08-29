package defpackage;

import android.view.MotionEvent;
import com.amap.bundle.drive.radar.page.NewRouteBoardPage;
import com.autonavi.map.fragmentcontainer.page.MapBasePresenter;

/* renamed from: ol reason: default package */
/* compiled from: NewRouteBoardPresenter */
public final class ol extends MapBasePresenter {
    private NewRouteBoardPage a = null;
    private boolean b = true;

    public ol(NewRouteBoardPage newRouteBoardPage) {
        super(newRouteBoardPage);
        this.a = newRouteBoardPage;
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
}
