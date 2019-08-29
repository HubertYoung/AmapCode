package defpackage;

import android.content.Intent;
import android.content.res.Configuration;
import com.autonavi.bundle.amaphome.controller.MapHomeMapEventController;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LOCATION_SCENE;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IActvitiyStateListener;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.AgroupScenes;
import com.autonavi.minimap.bundle.amaphome.ab.page.OldMapHomePage;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.map.FavoriteLayer;
import com.autonavi.minimap.statusbar.StatusBarManager;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.HashMap;
import java.util.List;

/* renamed from: cun reason: default package */
/* compiled from: OldMapHomePresenter */
public final class cun extends AbstractBaseMapPagePresenter<OldMapHomePage> implements bgo, IActvitiyStateListener, czv {
    public aqw a = null;
    public apv b;
    private MapHomeMapEventController c = null;
    private final apw d;
    private apy e;
    private ark f;
    private boolean g = false;
    private arj h;
    private final arq i;

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public cun(OldMapHomePage oldMapHomePage) {
        super(oldMapHomePage);
        this.b = new apv(oldMapHomePage);
        this.d = new apw(oldMapHomePage);
        this.i = new arq(oldMapHomePage);
        this.e = new apy(oldMapHomePage);
    }

    /* access modifiers changed from: private */
    public static void b(String str) {
        brm brm = (brm) ank.a(brm.class);
        if (brm != null) {
            brm.a(str);
        }
    }

    public final void onPageCreated() {
        AMapPageUtil.setPageStateListener((bid) this.mPage, new IPageStateListener() {
            public final void onCover() {
                cun.this.b.q();
                cun.b((String) "2");
                ((OldMapHomePage) cun.this.mPage).c.c();
                ((OldMapHomePage) cun.this.mPage).a.onCover();
            }

            public final void onAppear() {
                if (((OldMapHomePage) cun.this.mPage).b) {
                    ((OldMapHomePage) cun.this.mPage).g();
                }
                cun.this.b.p();
                ((OldMapHomePage) cun.this.mPage).c.d();
            }
        });
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.a((Object) this);
        }
        this.b.a();
        AMapPageUtil.setActivityStateListener((bid) this.mPage, this);
        this.c = new MapHomeMapEventController((arn) this.mPage);
        this.a = new aqw((arn) this.mPage);
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a(((OldMapHomePage) this.mPage).getClass(), AgroupScenes.MapHome, ((OldMapHomePage) this.mPage).getArguments(), false);
        }
        this.f = new ark((bid) this.mPage);
        this.f.b = this.g;
        brm brm = (brm) ank.a(brm.class);
        if (brm != null) {
            brm.a(((OldMapHomePage) this.mPage).getSuspendManager(), ((OldMapHomePage) this.mPage).getMapManager());
        }
        super.onPageCreated();
        ((OldMapHomePage) this.mPage).c.f();
        this.h = new arj((AbstractBaseMapPage) this.mPage);
        this.h.a();
    }

    public final void onDestroy() {
        super.onDestroy();
        this.b.f();
        ((OldMapHomePage) this.mPage).c.g();
        Stub.getMapWidgetManager().removeAllWidget();
        brm brm = (brm) ank.a(brm.class);
        if (brm != null) {
            brm.j();
        }
        if (this.h != null) {
            this.h.d();
            this.h = null;
        }
    }

    public final void onStart() {
        super.onStart();
        ((OldMapHomePage) this.mPage).requestScreenOrientation(1);
        StatusBarManager.d().c();
        StatusBarManager.d().a((bid) this.mPage);
        this.b.b();
        this.c.onPageStart();
        this.e.c();
        this.f.b();
        brn brn = (brn) ank.a(brn.class);
        if (brn != null) {
            FavoriteLayer g2 = brn.g();
            if (g2 != null) {
                brn.i();
                this.f.a = bim.aa().k((String) "104");
                g2.setVisible(true);
                brn.d();
            }
        }
        LocationInstrument.getInstance().subscribe(((OldMapHomePage) this.mPage).getContext(), LOCATION_SCENE.TYPE_MAINMAP);
    }

    public final void onStop() {
        super.onStop();
        StatusBarManager.d().b(AMapPageUtil.getPageContext());
        this.b.c();
        this.c.onPageStop();
        OldMapHomePage.n();
    }

    public final void onResume() {
        super.onResume();
        this.b.d();
        StatusBarManager.d().c();
        if (!((OldMapHomePage) this.mPage).b) {
            Stub.getMapWidgetManager().setContainerBottomMargin(0, false);
        }
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.b(9001);
            if (awo.d()) {
                awo.e();
            }
            awo.a((List<Integer>) awo.j());
        }
        bfo bfo = (bfo) a.a.a(bfo.class);
        if (bfo != null) {
            bfo.a(55);
        }
        ((OldMapHomePage) this.mPage).c.a();
        if (((OldMapHomePage) this.mPage).a != null) {
            ((OldMapHomePage) this.mPage).a.onPageResume(((OldMapHomePage) this.mPage).b);
        }
        if (this.h != null) {
            this.h.b();
        }
        a.a.a();
        xp xpVar = (xp) a.a.a(xp.class);
        if (xpVar != null) {
            xpVar.c();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("status", bnv.d());
        kd.a("amap.P00001.0.0", this, hashMap);
    }

    public final void onPause() {
        super.onPause();
        this.b.e();
        ark.c();
        this.e.b();
        ((OldMapHomePage) this.mPage).c.b();
        if (this.h != null) {
            this.h.c();
        }
        xp xpVar = (xp) a.a.a(xp.class);
        if (xpVar != null) {
            xpVar.b();
        }
        kd.a((Object) this);
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (this.a.b()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        if (this.b.g()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        if (this.d.a()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }

    public final void onActivityStart() {
        this.b.j();
        ((OldMapHomePage) this.mPage).c.a.onActivityStart();
    }

    public final void onActivityResume() {
        this.b.k();
    }

    public final void onActivityPause() {
        this.b.l();
    }

    public final void onActivityStop() {
        this.b.m();
        b((String) "3");
        ((OldMapHomePage) this.mPage).c.a.onActivityStop();
    }

    public final void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        this.b.a(i2, i3, intent);
    }

    public final void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i2, resultType, pageBundle);
        if (i2 != 1000 || resultType != ResultType.OK || pageBundle == null || !Constant.OPEN_MAPLAYER_DRAWER.equals(pageBundle.getString(Constant.KEY_TRAFFIC_RESULT))) {
            this.b.a(i2, resultType, pageBundle);
            ((OldMapHomePage) this.mPage).c.a(i2, resultType, pageBundle);
            return;
        }
        if (this.a != null && !this.a.c()) {
            this.a.a();
        }
    }

    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.b.a(z);
    }

    public final void onMapSurfaceChanged(int i2, int i3) {
        super.onMapSurfaceChanged(i2, i3);
        this.b.a(i2, i3);
        ((OldMapHomePage) this.mPage).c.a(i2, i3);
    }

    public final void onMapSurfaceCreated() {
        super.onMapSurfaceCreated();
        this.g = true;
        this.b.n();
        if (this.f != null) {
            this.f.b = true;
        }
        ((OldMapHomePage) this.mPage).c.a.onMapSurfaceCreated();
    }

    public final void onMapSurfaceDestroy() {
        super.onMapSurfaceDestroy();
        this.b.o();
        ((OldMapHomePage) this.mPage).c.a.onMapSurfaceDestroy();
    }

    public final void onMapRenderCompleted() {
        super.onMapRenderCompleted();
        la.a((int) ((OldMapHomePage) this.mPage).getMapView().an());
        cke.f();
        cke.b(((OldMapHomePage) this.mPage).getMapView());
        if (bny.a) {
            ((OldMapHomePage) this.mPage).getMapView().f(0);
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.b.a(configuration);
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        if (pageBundle != null) {
            int i2 = pageBundle.getInt("bundle_key_token", -1);
            if (i2 > 0) {
                d.a.a(i2, pageBundle.getInt("bundle_key_code", 10000), (String) null);
            }
        }
        this.b.a(pageBundle);
        if (!this.d.a(pageBundle)) {
            this.i.a(pageBundle);
            this.e.a(pageBundle);
            ((OldMapHomePage) this.mPage).c.a(pageBundle);
        }
    }

    public final void onFullScreenStateChanged(boolean z) {
        ((OldMapHomePage) this.mPage).a.onFullScreenStateChanged(z);
    }
}
