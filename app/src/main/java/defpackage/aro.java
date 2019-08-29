package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.amaphome.controller.MapHomeMapEventController;
import com.autonavi.bundle.amaphome.page.MapHomePage;
import com.autonavi.bundle.amaphome.widget.guideview.BuildException;
import com.autonavi.bundle.amaphome.widget.guideview.GuideBuilder;
import com.autonavi.bundle.amaphome.widget.guideview.GuideBuilder.a;
import com.autonavi.bundle.amaphome.widget.guideview.GuideBuilder.b;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.LogVersionType;
import com.autonavi.bundle.uitemplate.receiver.TripToolSelectReceiver;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LOCATION_SCENE;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IActvitiyStateListener;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.AgroupScenes;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.map.FavoriteLayer;
import com.autonavi.minimap.statusbar.StatusBarManager;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.HashMap;
import java.util.List;

/* renamed from: aro reason: default package */
/* compiled from: MapHomePresenter */
public class aro extends AbstractBaseMapPagePresenter<MapHomePage> implements bgo, IActvitiyStateListener, czv {
    public static final String a = "aro";
    public aqw b = null;
    public final apw c;
    public apv d;
    public arc e;
    public boolean f = false;
    public boolean g = false;
    public boolean h = false;
    private MapHomeMapEventController i = null;
    private apy j;
    private ark k;
    private boolean l = false;
    private arj m;
    private float n;
    private boolean o;
    private final arq p;
    private c q;
    private boolean r = false;

    public boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public aro(MapHomePage mapHomePage) {
        super(mapHomePage);
        this.d = new apv(mapHomePage);
        this.c = new apw(mapHomePage);
        this.p = new arq(mapHomePage);
        this.j = new apy(mapHomePage);
        cnu.a();
        this.n = (float) cnu.a(mapHomePage.getContext());
    }

    public void onPageCreated() {
        super.onPageCreated();
        AMapPageUtil.setPageStateListener((bid) this.mPage, new IPageStateListener() {
            public final void onCover() {
                aro.this.h = true;
                aro.this.d.q();
                ((MapHomePage) aro.this.mPage).c.d.a();
                ((MapHomePage) aro.this.mPage).n();
            }

            public final void onAppear() {
                aro.this.h = false;
                if (((MapHomePage) aro.this.mPage).k) {
                    ((MapHomePage) aro.this.mPage).g();
                }
                aro.this.d.p();
            }
        });
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.a((Object) this);
        }
        this.d.a();
        AMapPageUtil.setActivityStateListener((bid) this.mPage, this);
        this.i = new MapHomeMapEventController((arn) this.mPage);
        this.b = new aqw((arn) this.mPage);
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a(((MapHomePage) this.mPage).getClass(), AgroupScenes.MapHome, ((MapHomePage) this.mPage).getArguments(), false);
        }
        this.k = new ark((bid) this.mPage);
        this.k.b = this.l;
        this.m = new arj((AbstractBaseMapPage) this.mPage);
        this.m.a();
        this.q = new c() {
            public final void c() {
            }

            public final void a() {
                if (!aro.this.h) {
                    aro.this.f = false;
                }
            }

            public final void b() {
                if (!aro.this.h) {
                    aro.this.f = true;
                    aro.this.g = true;
                }
            }
        };
        drm.a((a) this.q);
    }

    public void onMapRenderCompleted() {
        super.onMapRenderCompleted();
        ckb.a((String) "MapHomePresenter#onMapRenderCompleted");
        la.a((int) ((MapHomePage) this.mPage).getMapView().an());
        cke.f();
        cke.b(((MapHomePage) this.mPage).getMapView());
        if (bny.a) {
            ((MapHomePage) this.mPage).getMapView().f(0);
        }
    }

    public void onDestroy() {
        ((MapHomePage) this.mPage).q();
        super.onDestroy();
        this.d.f();
        Stub.getMapWidgetManager().removeAllWidget();
        if (this.m != null) {
            this.m.d();
            this.m = null;
        }
        ((MapHomePage) this.mPage).c.d.b();
        TripToolSelectReceiver.a();
        if (this.q != null) {
            drm.b((a) this.q);
        }
    }

    public void onStart() {
        super.onStart();
        StatusBarManager.d().c();
        StatusBarManager.d().a((bid) this.mPage);
        ((MapHomePage) this.mPage).requestScreenOrientation(1);
        this.d.b();
        this.i.onPageStart();
        this.j.c();
        this.k.b();
        brn brn = (brn) ank.a(brn.class);
        if (brn != null) {
            FavoriteLayer g2 = brn.g();
            if (g2 != null) {
                brn.i();
                this.k.a = bim.aa().k((String) "104");
                g2.setVisible(true);
                brn.d();
            }
        }
        dfm dfm = (dfm) ank.a(dfm.class);
        if (dfm != null) {
            dfm.a(bim.aa().k((String) UploadConstants.STATUS_FILE_UPLOADING_RETRY));
        }
        LocationInstrument.getInstance().subscribe(((MapHomePage) this.mPage).getContext(), LOCATION_SCENE.TYPE_MAINMAP);
    }

    public void onStop() {
        super.onStop();
        StatusBarManager.d().b(AMapPageUtil.getPageContext());
        this.d.c();
        this.i.onPageStop();
        MapHomePage.p();
        MapHomePage mapHomePage = (MapHomePage) this.mPage;
        if (mapHomePage.h) {
            mapHomePage.h = false;
            return;
        }
        if (!mapHomePage.p) {
            cfe.a((IMapPage) mapHomePage);
        }
    }

    public void onResume() {
        super.onResume();
        this.d.d();
        MapHomePage mapHomePage = (MapHomePage) this.mPage;
        if (!mapHomePage.e && !mapHomePage.k) {
            mapHomePage.l.setContainerBottomMargin(mapHomePage.d, false);
            mapHomePage.e = true;
        }
        mapHomePage.c.a.c();
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
            bfo.a(10);
        }
        aqq aqq = mapHomePage.f;
        aqq.b.pageShow(mapHomePage.p, null);
        if (aqq.e && aqq.c != null) {
            Stub.getMapWidgetManager().addWidget(aqq.c);
        }
        mapHomePage.a = SystemClock.currentThreadTimeMillis();
        mapHomePage.p = false;
        if (this.m != null) {
            this.m.b();
        }
        if (!this.o) {
            this.e = new arc(this);
            arc arc = this.e;
            Activity activity = ((MapHomePage) this.mPage).getActivity();
            if ("1".equals(arc.d.getStringValue("new_user_guide_is_shown", ""))) {
                MapHomePage mapHomePage2 = (MapHomePage) arc.a.mPage;
                mapHomePage2.c.d.a = new d() {
                };
                mapHomePage2.a(mapHomePage2.n);
                mapHomePage2.g.a(bet.a(mapHomePage2.getContext(), mapHomePage2.o + 8));
                new arx();
                aqo aqo = mapHomePage2.g;
                if (aqo.a()) {
                    aqo.b();
                    if (aqo.b == null) {
                        aqo.b = new b(aqo, 0);
                    }
                    aho.b(aqo.b);
                    aho.a(aqo.b);
                    SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences(LogVersionType.REDESIGN, 0);
                    int i2 = sharedPreferences.getInt("pull_tip_show_times", 0);
                    sharedPreferences.edit().putInt("pull_tip_show_times", i2 + 1).putLong("pull_tip_last_show", System.currentTimeMillis()).apply();
                }
            } else {
                View decorView = activity.getWindow().getDecorView();
                arc.b = decorView.findViewWithTag("tabLayout");
                arc.c = (ViewGroup) decorView.findViewWithTag("hostContainer");
                arc.b.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(activity) {
                    final /* synthetic */ Activity a;

                    {
                        this.a = r2;
                    }

                    public final void onGlobalLayout() {
                        if (arc.this.g) {
                            arc.this.g = false;
                            arc arc = arc.this;
                            View view = arc.this.b;
                            ViewGroup viewGroup = arc.this.c;
                            Activity activity = this.a;
                            GuideBuilder guideBuilder = new GuideBuilder();
                            guideBuilder.a(view).a().a(agn.a((Context) activity, 12.0f)).b(-agn.a((Context) activity, 8.0f)).c(-agn.a((Context) activity, 8.0f));
                            guideBuilder.a((b) new b(viewGroup, activity) {
                                final /* synthetic */ ViewGroup a;
                                final /* synthetic */ Activity b;

                                {
                                    this.a = r2;
                                    this.b = r3;
                                }

                                public final void a() {
                                    arc.this.i.setItem("operateGuideShowing", "1");
                                    ((MapHomePage) arc.this.a.mPage).c.d.c.a();
                                }

                                public final void b() {
                                    View view = ((MapHomePage) arc.this.a.mPage).c.b;
                                    arc arc = arc.this;
                                    ViewGroup viewGroup = this.a;
                                    Activity activity = this.b;
                                    GuideBuilder guideBuilder = new GuideBuilder();
                                    GuideBuilder c2 = guideBuilder.a(view).a().a(agn.a((Context) activity, 12.0f)).b(agn.a((Context) activity, 8.0f)).c(agn.a((Context) activity, 8.0f));
                                    int a2 = agn.a((Context) activity, 94.0f);
                                    if (c2.b) {
                                        throw new BuildException("Already created. rebuild a new one.");
                                    }
                                    if (a2 < 0) {
                                        c2.a.f = 0;
                                    }
                                    c2.a.f = a2;
                                    int i = -agn.a((Context) activity, 50.0f);
                                    if (c2.b) {
                                        throw new BuildException("Already created. rebuild a new one.");
                                    }
                                    if (i < 0) {
                                        c2.a.d = 0;
                                    }
                                    c2.a.d = i;
                                    guideBuilder.a((b) new b(viewGroup, activity) {
                                        final /* synthetic */ ViewGroup a;
                                        final /* synthetic */ Activity b;

                                        public final void a() {
                                        }

                                        {
                                            this.a = r2;
                                            this.b = r3;
                                        }

                                        public final void b() {
                                            arc arc = arc.this;
                                            View view = arc.this.b;
                                            ViewGroup viewGroup = this.a;
                                            Activity activity = this.b;
                                            GuideBuilder guideBuilder = new GuideBuilder();
                                            GuideBuilder a2 = guideBuilder.a(view).a();
                                            if (a2.b) {
                                                throw new BuildException("Already created, rebuild a new one.");
                                            }
                                            a2.a.o = true;
                                            guideBuilder.a((b) new b() {
                                                public final void a() {
                                                }

                                                public final void b() {
                                                    arc.this.d.putStringValue("new_user_guide_is_shown", "1");
                                                    arc.this.i.setItem("operateGuideShowing", "0");
                                                    if (!(arc.this.h == null || arc.this.h.a == null)) {
                                                        arc.this.h.a.cancelAnimation();
                                                    }
                                                    ((MapHomePage) arc.this.a.mPage).r.setPanelState(PanelState.EXPANDED, true);
                                                }
                                            });
                                            AnonymousClass5 r1 = new a() {
                                            };
                                            if (guideBuilder.b) {
                                                throw new BuildException("Already created, rebuild a new one.");
                                            }
                                            guideBuilder.c = r1;
                                            arc.h = new ard();
                                            guideBuilder.a((asf) arc.h);
                                            asg b2 = guideBuilder.b();
                                            arc.f.add(b2);
                                            arc.e = b2;
                                            b2.a(activity, viewGroup);
                                        }
                                    });
                                    guideBuilder.a((asf) new arf());
                                    asg b2 = guideBuilder.b();
                                    arc.f.add(b2);
                                    arc.e = b2;
                                    b2.a(activity, viewGroup);
                                }
                            });
                            guideBuilder.a((asf) new are());
                            asg b2 = guideBuilder.b();
                            arc.f.add(b2);
                            arc.e = b2;
                            b2.a(activity, viewGroup);
                        }
                    }
                });
            }
            this.o = true;
        }
        a.a.a();
        xp xpVar = (xp) a.a.a(xp.class);
        if (xpVar != null) {
            xpVar.c();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("status", bnv.d());
        kd.a("amap.P00001.0.0", this, hashMap);
        if (!this.r) {
            ((MapHomePage) this.mPage).r();
            this.r = true;
        }
    }

    public void onPause() {
        super.onPause();
        MapHomePage mapHomePage = (MapHomePage) this.mPage;
        mapHomePage.e = false;
        aqq aqq = mapHomePage.f;
        aqq.b.pageHide(mapHomePage.p);
        if (aqq.c != null) {
            Stub.getMapWidgetManager().removeWidget(aqq.c);
        }
        this.d.e();
        ark.c();
        this.j.b();
        if (this.m != null) {
            this.m.c();
        }
        xp xpVar = (xp) a.a.a(xp.class);
        if (xpVar != null) {
            xpVar.b();
        }
        kd.a((Object) this);
    }

    public void onActivityStart() {
        this.d.j();
    }

    public void onActivityResume() {
        this.d.k();
    }

    public void onActivityPause() {
        this.d.l();
    }

    public void onActivityStop() {
        ((MapHomePage) this.mPage).q();
        this.d.m();
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        this.d.a(i2, i3, intent);
    }

    public void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i2, resultType, pageBundle);
        if (i2 != 1000 || resultType != ResultType.OK || pageBundle == null || !Constant.OPEN_MAPLAYER_DRAWER.equals(pageBundle.getString(Constant.KEY_TRAFFIC_RESULT))) {
            this.d.a(i2, resultType, pageBundle);
            return;
        }
        if (this.b != null && !this.b.c()) {
            this.b.a();
        }
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.d.a(z);
    }

    public void onMapSurfaceChanged(int i2, int i3) {
        super.onMapSurfaceChanged(i2, i3);
        this.d.a(i2, i3);
    }

    public void onMapSurfaceCreated() {
        super.onMapSurfaceCreated();
        this.l = true;
        this.d.n();
        if (this.k != null) {
            this.k.b = true;
        }
    }

    public void onMapSurfaceDestroy() {
        super.onMapSurfaceDestroy();
        this.d.o();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.d.a(configuration);
        cnu.a();
        float a2 = cnu.a((float) configuration.screenWidthDp);
        if (configuration.orientation == 1 && bev.a(a2, this.n)) {
            MapHomePage mapHomePage = (MapHomePage) this.mPage;
            mapHomePage.r.computeSlideRange();
            mapHomePage.b.load("path://amap_bundle_quickservice/src/pages/QuickService.page.js", "", "", mapHomePage.getResources().getDisplayMetrics().widthPixels, mapHomePage.getResources().getDisplayMetrics().heightPixels - bet.a(mapHomePage.getContext(), 100));
            if (mapHomePage.k) {
                mapHomePage.g();
            } else {
                mapHomePage.r.setPanelState(mapHomePage.j);
            }
            this.n = a2;
            this.e.a();
        }
    }

    public void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        if (pageBundle != null) {
            int i2 = pageBundle.getInt("bundle_key_token", -1);
            if (i2 > 0) {
                d.a.a(i2, pageBundle.getInt("bundle_key_code", 10000), (String) null);
            }
        }
        this.d.a(pageBundle);
        if (!this.c.a(pageBundle)) {
            this.p.a(pageBundle);
            this.j.a(pageBundle);
        }
    }

    public void onFullScreenStateChanged(boolean z) {
        ((MapHomePage) this.mPage).c.d.a();
        ((MapHomePage) this.mPage).n();
    }
}
