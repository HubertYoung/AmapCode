package defpackage;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;

/* renamed from: bao reason: default package */
/* compiled from: DriveCommuteImpl */
public final class bao implements bas {
    public bah a;
    private a b;
    private b c;
    private bty d;
    private String e;

    /* renamed from: bao$a */
    /* compiled from: DriveCommuteImpl */
    class a implements czx {
        private a() {
        }

        /* synthetic */ a(bao bao, byte b) {
            this();
        }

        public final boolean onResult(int i, ResultType resultType, PageBundle pageBundle) {
            if (bao.this.a != null) {
                bah.a(i, pageBundle);
            }
            return false;
        }
    }

    /* renamed from: bao$b */
    /* compiled from: DriveCommuteImpl */
    class b implements amk {
        public final void afterDrawFrame(int i, GLMapState gLMapState) {
        }

        public final void beforeDrawFrame(int i, GLMapState gLMapState) {
        }

        public final void onDoubleTap(int i, MotionEvent motionEvent) {
        }

        public final void onEngineActionGesture(int i, alg alg) {
        }

        public final void onEngineVisible(int i, boolean z) {
        }

        public final boolean onFling(int i, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public final void onHorizontalMove(int i, float f) {
        }

        public final void onHorizontalMoveEnd(int i) {
        }

        public final void onHoveBegin(int i, MotionEvent motionEvent) {
        }

        public final void onLongPress(int i, MotionEvent motionEvent) {
        }

        public final void onMapAnimationFinished(int i, int i2) {
        }

        public final void onMapAnimationFinished(int i, aln aln) {
        }

        public final void onMapLevelChange(int i, boolean z) {
        }

        public final void onMapRenderCompleted(int i) {
        }

        public final void onMapSizeChange(int i) {
        }

        public final void onMapTipClear(int i) {
        }

        public final void onMapTipInfo(int i, String str) {
        }

        public final void onMotionFinished(int i, int i2) {
        }

        public final void onOfflineMap(int i, String str, int i2) {
        }

        public final void onRealCityAnimateFinish(int i) {
        }

        public final void onScreenShotFinished(int i, long j) {
        }

        public final void onScreenShotFinished(int i, Bitmap bitmap) {
        }

        public final void onScreenShotFinished(int i, String str) {
        }

        public final void onSelectSubWayActive(int i, byte[] bArr) {
        }

        public final void onShowPress(int i, MotionEvent motionEvent) {
        }

        public final void onUserMapTouchEvent(int i, MotionEvent motionEvent) {
        }

        public final void onZoomOutTap(int i, MotionEvent motionEvent) {
        }

        private b() {
        }

        /* synthetic */ b(bao bao, byte b) {
            this();
        }

        public final void onMoveBegin(int i, MotionEvent motionEvent) {
            if (bao.this.a != null) {
                bao.this.a.f();
            }
        }

        public final void onScaleRotateBegin(int i, MotionEvent motionEvent) {
            if (bao.this.a != null) {
                bao.this.a.f();
            }
        }

        public final boolean onSingleTapUp(int i, MotionEvent motionEvent) {
            if (bao.this.a != null) {
                bao.this.a.f();
            }
            return false;
        }
    }

    public final void f() {
    }

    public final void h() {
    }

    public bao(String str) {
        this.e = str;
    }

    public final void a(AbstractBaseMapPage abstractBaseMapPage) {
        AMapLog.d("Daniel-27", "DriveCommuteImpl init");
        this.a = new bah(abstractBaseMapPage, this.e);
        if (abstractBaseMapPage != null) {
            this.d = abstractBaseMapPage.getMapView();
        }
        k();
    }

    public final void a(int i, String str) {
        AMapLog.d("Daniel-27", "DriveCommuteImpl showDriveCommuteTip".concat(String.valueOf(i)));
        if (this.a != null) {
            this.a.a(i);
        }
    }

    public final void b() {
        AMapLog.d("Daniel-27", "DriveCommuteImpl onDefaultPageResume");
        this.a.d();
    }

    public final void c() {
        AMapLog.d("Daniel-27", "DriveCommuteImpl onDefaultPagePause");
        this.a.e();
    }

    public final void d() {
        AMapLog.d("Daniel-27", "DriveCommuteImpl onDefaultPageDestroy");
        this.a.a();
        l();
    }

    public final void e() {
        this.a.f();
    }

    public final void g() {
        if (this.a != null) {
            this.a.f();
        }
    }

    public final void a() {
        AMapLog.d("Daniel-27", "DriveCommuteImpl close");
        this.a.a();
        l();
    }

    public final void a(GeoPoint geoPoint) {
        if (this.a != null) {
            this.a.a(geoPoint);
        }
    }

    public final void i() {
        azi.i();
        azi.l();
        j();
    }

    public final void j() {
        if (this.a != null) {
            this.a.c();
        }
    }

    private void k() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (this.b == null) {
            this.b = new a(this, 0);
        }
        if (this.c == null) {
            this.c = new b(this, 0);
        }
        if (this.d != null) {
            this.d.a((amk) this.c);
        }
        iMainMapService.a((Object) this.b);
    }

    private void l() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (this.b != null) {
            iMainMapService.b(this.b);
        }
        if (this.c != null && this.d != null) {
            this.d.b((amk) this.c);
        }
    }
}
