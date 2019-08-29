package defpackage;

import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IActvitiyStateListener;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.basemap.traffic.page.TrafficMainMapPage;

/* renamed from: csp reason: default package */
/* compiled from: TrafficMainMapPresenter */
public final class csp extends Ajx3PagePresenter implements IActvitiyStateListener {
    private bdv a = new bdv(new a() {
        public final AbstractBaseMapPage a() {
            return csp.this.b;
        }
    });
    /* access modifiers changed from: private */
    public TrafficMainMapPage b;
    private axv c = ((axv) a.a.a(axv.class));

    public final void onActivityPause() {
    }

    public final void onActivityResume() {
    }

    public csp(TrafficMainMapPage trafficMainMapPage) {
        super(trafficMainMapPage);
        this.b = trafficMainMapPage;
        if (this.c != null) {
            this.c.a(1);
        }
    }

    public final void onPageCreated() {
        AMapPageUtil.setPageStateListener(this.b, new IPageStateListener() {
            public final void onCover() {
                if (!csp.this.a()) {
                    csp.this.b.b.c();
                }
            }

            public final void onAppear() {
                if (!csp.this.a()) {
                    csp.this.b.b.d();
                }
            }
        });
        super.onPageCreated();
        if (!a()) {
            this.b.b.f();
        }
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        this.b.a(pageBundle);
        if (!a()) {
            this.b.b.a(pageBundle);
        }
    }

    public final void onStart() {
        super.onStart();
        this.a.c();
    }

    public final void onStop() {
        super.onStop();
        this.a.d();
    }

    public final void onResume() {
        super.onResume();
        this.a.a();
        if (!a()) {
            this.b.b.a();
        }
        if (this.c != null) {
            this.c.b();
        }
    }

    public final void onPause() {
        super.onPause();
        this.a.b();
        if (!a()) {
            this.b.b.b();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        if (!a()) {
            this.b.b.g();
        }
        if (this.c != null) {
            this.c.d();
        }
    }

    public final void onMapSurfaceChanged(int i, int i2) {
        super.onMapSurfaceChanged(i, i2);
        if (!a()) {
            this.b.b.a(i, i2);
        }
    }

    public final void onMapSurfaceCreated() {
        super.onMapSurfaceCreated();
        if (!a()) {
            this.b.b.a.onMapSurfaceCreated();
        }
    }

    public final void onMapSurfaceDestroy() {
        super.onMapSurfaceDestroy();
        if (!a()) {
            this.b.b.a.onMapSurfaceDestroy();
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (!a()) {
            this.b.b.a(i, resultType, pageBundle);
        }
    }

    public final void onActivityStart() {
        if (!a()) {
            this.b.b.a.onActivityStart();
        }
    }

    /* access modifiers changed from: private */
    public boolean a() {
        if (this.b != null) {
            return this.b.b();
        }
        return false;
    }

    public final void onActivityStop() {
        if (!a()) {
            this.b.b.a.onActivityStop();
        }
    }
}
