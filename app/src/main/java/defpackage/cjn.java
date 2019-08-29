package defpackage;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.agroup.page.MyGroupMapPage;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: cjn reason: default package */
/* compiled from: MyGroupMapPresenter */
public final class cjn extends Ajx3PagePresenter {
    private bty a;
    private boolean b;
    private boolean c;
    private boolean d;
    private float e = 0.0f;
    private float f = 16.0f;
    private float g = 0.0f;
    private GeoPoint h = LocationInstrument.getInstance().getLatestPosition();
    private int i = 0;
    private int j = 0;

    public cjn(MyGroupMapPage myGroupMapPage) {
        super(myGroupMapPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.a = ((Ajx3Page) this.mPage).getMapManager().getMapView();
        cji.e().m = 0;
        a();
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        if (this.mPage instanceof MyGroupMapPage) {
            a.a.a(((MyGroupMapPage) this.mPage).getClass(), new ciz());
        }
        a();
    }

    public final void onResume() {
        super.onResume();
        if (this.d) {
            this.f = this.a.v();
            this.g = this.a.J();
            this.e = this.a.I();
            this.h = GeoPoint.glGeoPoint2GeoPoint(this.a.n());
            this.i = this.a.h();
            this.j = this.a.i();
            Rect a2 = ags.a(this.a.d());
            int width = a2.width() / 2;
            Resources resources = AMapAppGlobal.getApplication().getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.mygroup_top_margin);
            int height = (((a2.height() - dimensionPixelSize) - resources.getDimensionPixelSize(R.dimen.mygroup_bottom_margin)) / 2) + dimensionPixelSize;
            this.a.b(width, height);
            cji e2 = cji.e();
            Point point = new Point(width, height);
            if (e2.c != null) {
                e2.c.b(point.x, point.y + agn.a(e2.c.d(), 25.0f));
            }
            this.a.b(new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false));
            this.b = this.a.C();
            this.a.e(0.0f);
            this.a.e(false);
            this.c = this.a.A();
            this.a.g(0.0f);
            this.a.d(false);
            this.d = false;
            this.a.b((Runnable) new Runnable() {
                public final void run() {
                    if (((Ajx3Page) cjn.this.mPage).isAlive()) {
                        cji.e().h();
                    }
                }
            });
        }
    }

    public final void onPause() {
        super.onPause();
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onDestroy() {
        super.onDestroy();
        cji.e().m = 0;
        if (this.a != null) {
            this.a.a(this.h.x, this.h.y);
            this.a.e(this.e);
            this.a.d(this.f);
            this.a.g(this.g);
            this.a.b(this.i, this.j);
            if (!this.b) {
                this.a.B();
            }
            if (!this.c) {
                this.a.z();
            }
        }
    }

    public final void onMapSurfaceChanged(int i2, int i3) {
        super.onMapSurfaceChanged(i2, i3);
    }

    private void a() {
        b();
        this.d = true;
    }

    private void b() {
        int d2 = ags.d(this.a.d());
        Application application = AMapAppGlobal.getApplication();
        Resources resources = application.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.mygroup_top_margin) + d2;
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.mygroup_bottom_margin);
        float dimensionPixelSize3 = (float) (resources.getDimensionPixelSize(R.dimen.member_tip_max_width_mid) / 2);
        cji.e().a(agn.b(application, dimensionPixelSize3) + 15, agn.b(application, (float) dimensionPixelSize) + 82 + cji.e().m, agn.b(application, dimensionPixelSize3) + 15, agn.b(application, (float) dimensionPixelSize2) + 2);
    }

    public final boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        super.onMapLongPress(motionEvent, geoPoint);
        return true;
    }
}
