package defpackage;

import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapWidgetManager;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;

/* renamed from: bdu reason: default package */
/* compiled from: DIYFrequentLocationController */
public final class bdu {
    public DIYMainMapWidgetManager a;
    private cya b;

    public bdu(DIYMainMapWidgetManager dIYMainMapWidgetManager, cya cya) {
        this.a = dIYMainMapWidgetManager;
        this.b = cya;
    }

    public final void a() {
        this.a.onResume();
        if (this.b != null) {
            this.b.onResume();
        }
    }

    public final void b() {
        this.a.onPause();
        if (this.b != null) {
            this.b.onPause();
        }
    }

    public final void a(PageBundle pageBundle) {
        this.a.onNewIntent(pageBundle);
    }

    public final void a(int i, int i2) {
        this.a.onMapSurfaceChanged(i, i2);
    }

    @Deprecated
    public final void c() {
        this.a.onCover();
        if (this.b != null) {
            this.b.onCover();
        }
    }

    @Deprecated
    public final void d() {
        this.a.onAppear();
        if (this.b != null) {
            this.b.onAppear();
        }
    }

    public final void a(int i, ResultType resultType, PageBundle pageBundle) {
        this.a.onResult(i, resultType, pageBundle);
        if (this.b != null) {
            this.b.onResult(i, resultType, pageBundle);
        }
    }

    public final void e() {
        this.a.onCreate();
        if (this.b != null) {
            this.b.onCreate();
        }
    }

    public final void f() {
        if (this.b != null) {
            cya cya = this.b;
            cya.a.a(cya.b);
            cya.b.bindView(cya.a);
            bhz bhz = (bhz) ((IMainMapService) ank.a(IMainMapService.class)).a(bhz.class);
            if (bhz != null) {
                bhz.a(cya);
            }
        }
    }

    public final void g() {
        this.a.onDestroy();
        if (this.b != null) {
            this.b.onDestroy();
        }
    }
}
