package defpackage;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.gps.IGpsStateResume;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter;
import com.autonavi.map.fragmentcontainer.page.IPageHost;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IActvitiyStateListener;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.map.search.fragment.SearchCQDetailPage;
import com.autonavi.map.search.fragment.SearchCQDetailPage.POI_DETAIL_TYPE;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.AgroupScenes;
import com.autonavi.minimap.statusbar.StatusBarManager;
import com.autonavi.minimap.widget.SyncPopupWindow;

/* renamed from: cba reason: default package */
/* compiled from: SearchCQDetailPresenter */
public final class cba<Page extends SearchCQDetailPage> extends AbstractBaseMapPagePresenter<Page> implements IActvitiyStateListener {
    private bdv a = new bdv(new a() {
        public final AbstractBaseMapPage a() {
            return (AbstractBaseMapPage) cba.this.mPage;
        }
    });
    private SyncPopupWindow b;
    private axv c = ((axv) a.a.a(axv.class));
    private apt d;
    private boolean e;
    private boolean f;

    public final void onActivityPause() {
    }

    public final void onActivityResume() {
    }

    public cba(Page page) {
        super(page);
        if (this.c != null) {
            this.c.a(1);
        }
    }

    public final void onPageCreated() {
        super.onPageCreated();
        SearchCQDetailPage searchCQDetailPage = (SearchCQDetailPage) this.mPage;
        AMapPageUtil.setPageStateListener(searchCQDetailPage, new IPageStateListener() {
            public final void onCover() {
                bty bty;
                SearchCQDetailPage searchCQDetailPage = SearchCQDetailPage.this;
                if (searchCQDetailPage.b != null) {
                    searchCQDetailPage.b.onPageCover();
                }
                MapManager mapManager = searchCQDetailPage.getMapManager();
                if (mapManager == null) {
                    bty = null;
                } else {
                    bty = mapManager.getMapView();
                }
                if (bty != null) {
                    bty.E();
                }
                if (searchCQDetailPage.n != null) {
                    searchCQDetailPage.n.onCover();
                }
                if (!Stub.getMapWidgetManager().isNewHomePage()) {
                    SearchCQDetailPage.this.k.c();
                }
            }

            public final void onAppear() {
                SearchCQDetailPage searchCQDetailPage = SearchCQDetailPage.this;
                if (searchCQDetailPage.b != null) {
                    searchCQDetailPage.b.onPageAppear();
                }
                if (searchCQDetailPage.n != null) {
                    searchCQDetailPage.n.onAppear();
                }
                SearchCQDetailPage.this.getMapView().a((GLGeoPoint) SearchCQDetailPage.this.i.getPoint());
                if (!Stub.getMapWidgetManager().isNewHomePage()) {
                    SearchCQDetailPage.this.k.d();
                }
            }
        });
        this.d = (apt) ank.a(apt.class);
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            ((SearchCQDetailPage) this.mPage).k.f();
        }
        this.e = true;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        SearchCQDetailPage searchCQDetailPage = (SearchCQDetailPage) this.mPage;
        if (searchCQDetailPage.c != null) {
            searchCQDetailPage.c.b();
        }
    }

    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
    }

    public final void onStart() {
        StatusBarManager.d().a((bid) this.mPage);
        SearchCQDetailPage searchCQDetailPage = (SearchCQDetailPage) this.mPage;
        searchCQDetailPage.addMainMapEventListener(searchCQDetailPage.o);
        bza bza = searchCQDetailPage.d;
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            bza.a.registerListener(WidgetType.GPS, Stub.getCombineWidgetsTag(WidgetType.GPS, WidgetType.SCALE), bza);
        }
        bza.a.registerListener(WidgetType.LAYER, bza);
        bza.a.registerListener(WidgetType.AUTO_REMOTE, bza);
        bec bec = (bec) ank.a(bec.class);
        if (bec != null) {
            searchCQDetailPage.m = bec.a();
            if (searchCQDetailPage.m != null) {
                searchCQDetailPage.m.a(searchCQDetailPage.p);
            }
        }
        SearchCQDetailPage searchCQDetailPage2 = (SearchCQDetailPage) this.mPage;
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a(searchCQDetailPage2.getClass(), AgroupScenes.SearchCQDetail, searchCQDetailPage2.getArguments(), false);
        }
        if (SearchCQDetailPage.c() == POI_DETAIL_TYPE.CQ_VIEW) {
            ((SearchCQDetailPage) this.mPage).requestScreenOrientation(1);
        }
        super.onStart();
        this.a.c();
        if (this.f || this.e) {
            ((SearchCQDetailPage) this.mPage).a(false);
        } else {
            ((SearchCQDetailPage) this.mPage).a(true);
        }
        this.f = false;
        this.e = false;
    }

    public final void onActivityStart() {
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            ((SearchCQDetailPage) this.mPage).k.a.onActivityStart();
        }
    }

    public final void onActivityStop() {
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            ((SearchCQDetailPage) this.mPage).k.a.onActivityStop();
        }
    }

    public final void onResume() {
        super.onResume();
        this.a.a();
        SearchCQDetailPage.d();
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            ((SearchCQDetailPage) this.mPage).k.a();
        }
        if (this.c != null) {
            this.c.b();
        }
        bin.a.b(new biv() {
            public final void saveSucess() {
                cba.b(cba.this);
            }
        });
        if (((SearchCQDetailPage) this.mPage).l != null) {
            IGpsStateResume gpsStateResume = ((SearchCQDetailPage) this.mPage).l.getGpsStateResume();
            if (gpsStateResume != null) {
                gpsStateResume.resumeGpsState();
            }
        }
        if (this.d != null) {
            this.d.onResume();
        }
    }

    public final void onStop() {
        StatusBarManager.d().b(AMapPageUtil.getPageContext());
        SearchCQDetailPage searchCQDetailPage = (SearchCQDetailPage) this.mPage;
        searchCQDetailPage.removeMainMapEventListener(searchCQDetailPage.o);
        bza bza = searchCQDetailPage.d;
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            bza.a.unregisterListener(WidgetType.GPS, Stub.getCombineWidgetsTag(WidgetType.GPS, WidgetType.SCALE), bza);
        }
        bza.a.unregisterListener(WidgetType.LAYER, bza);
        bza.a.unregisterListener(WidgetType.AUTO_REMOTE, bza);
        if (searchCQDetailPage.m != null) {
            searchCQDetailPage.m.b(searchCQDetailPage.p);
        }
        SearchCQDetailPage searchCQDetailPage2 = (SearchCQDetailPage) this.mPage;
        if (searchCQDetailPage2.b != null) {
            searchCQDetailPage2.b.onPause();
        }
        super.onStop();
        SearchCQDetailPage.e();
        this.a.d();
        if (((SearchCQDetailPage) this.mPage).l != null) {
            IGpsStateResume gpsStateResume = ((SearchCQDetailPage) this.mPage).l.getGpsStateResume();
            if (gpsStateResume != null) {
                gpsStateResume.restoreGpsState();
            }
        }
        if (!((IPageHost) ((SearchCQDetailPage) this.mPage).getActivity()).isHostPaused()) {
            this.f = true;
        }
    }

    public final void onPause() {
        super.onPause();
        this.a.b();
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            ((SearchCQDetailPage) this.mPage).k.b();
        }
        bin.a.b(null);
        if (this.b != null) {
            this.b.hide();
        }
        if (this.d != null) {
            this.d.onPause();
        }
    }

    public final void onDestroy() {
        AMapLog.d("MapWidget-SetMargin", "---SearchCQDetailPage---onDestroy--");
        if (this.d != null) {
            this.d.a();
        }
        SearchCQDetailPage searchCQDetailPage = (SearchCQDetailPage) this.mPage;
        if (searchCQDetailPage.f != null) {
            searchCQDetailPage.f.clear();
        }
        if (searchCQDetailPage.e != null) {
            searchCQDetailPage.e.clear();
        }
        SearchCQDetailPage searchCQDetailPage2 = (SearchCQDetailPage) this.mPage;
        if (searchCQDetailPage2.b != null) {
            searchCQDetailPage2.b.onDestroy();
        }
        AMapPageUtil.removePageStateListener((bid) this.mPage);
        super.onDestroy();
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            ((SearchCQDetailPage) this.mPage).k.g();
        }
        if (this.c != null) {
            this.c.d();
        }
        if (((SearchCQDetailPage) this.mPage).l != null) {
            IGpsStateResume gpsStateResume = ((SearchCQDetailPage) this.mPage).l.getGpsStateResume();
            if (gpsStateResume != null) {
                gpsStateResume.cancelResumeGpsState();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001d, code lost:
        if (r0 != com.autonavi.common.Page.ON_BACK_TYPE.TYPE_NORMAL) goto L_0x0022;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.autonavi.common.Page.ON_BACK_TYPE onBackPressed() {
        /*
            r2 = this;
            com.autonavi.common.Page$ON_BACK_TYPE r0 = super.onBackPressed()
            if (r0 == 0) goto L_0x000b
            com.autonavi.common.Page$ON_BACK_TYPE r1 = com.autonavi.common.Page.ON_BACK_TYPE.TYPE_NORMAL
            if (r0 == r1) goto L_0x000b
            return r0
        L_0x000b:
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r2.mPage
            com.autonavi.map.search.fragment.SearchCQDetailPage r0 = (com.autonavi.map.search.fragment.SearchCQDetailPage) r0
            com.autonavi.minimap.search.inter.ICQDetailPageController r1 = r0.b
            if (r1 == 0) goto L_0x0020
            com.autonavi.minimap.search.inter.ICQDetailPageController r0 = r0.b
            com.autonavi.common.Page$ON_BACK_TYPE r0 = r0.onBackPressed()
            if (r0 == 0) goto L_0x0020
            com.autonavi.common.Page$ON_BACK_TYPE r1 = com.autonavi.common.Page.ON_BACK_TYPE.TYPE_NORMAL
            if (r0 == r1) goto L_0x0020
            goto L_0x0022
        L_0x0020:
            com.autonavi.common.Page$ON_BACK_TYPE r0 = com.autonavi.common.Page.ON_BACK_TYPE.TYPE_NORMAL
        L_0x0022:
            if (r0 == 0) goto L_0x0029
            com.autonavi.common.Page$ON_BACK_TYPE r1 = com.autonavi.common.Page.ON_BACK_TYPE.TYPE_NORMAL
            if (r0 == r1) goto L_0x0029
            return r0
        L_0x0029:
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r2.mPage
            com.autonavi.map.search.fragment.SearchCQDetailPage r0 = (com.autonavi.map.search.fragment.SearchCQDetailPage) r0
            bza r0 = r0.d
            bzb r0 = r0.b
            boolean r0 = r0.b()
            if (r0 == 0) goto L_0x003a
            com.autonavi.common.Page$ON_BACK_TYPE r0 = com.autonavi.common.Page.ON_BACK_TYPE.TYPE_IGNORE
            return r0
        L_0x003a:
            com.autonavi.common.Page$ON_BACK_TYPE r0 = com.autonavi.common.Page.ON_BACK_TYPE.TYPE_NORMAL
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cba.onBackPressed():com.autonavi.common.Page$ON_BACK_TYPE");
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        SearchCQDetailPage searchCQDetailPage = (SearchCQDetailPage) this.mPage;
        searchCQDetailPage.a(pageBundle);
        searchCQDetailPage.e.clear();
        searchCQDetailPage.f.clear();
        if (!(searchCQDetailPage.i == null || searchCQDetailPage.h == 2)) {
            searchCQDetailPage.a();
            searchCQDetailPage.e.a(searchCQDetailPage.i, searchCQDetailPage.h == 0);
        }
        searchCQDetailPage.b.showCQLayer(searchCQDetailPage.i, searchCQDetailPage.j, false, true);
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            ((SearchCQDetailPage) this.mPage).k.a(pageBundle);
        }
        if (((SearchCQDetailPage) this.mPage).i.getPoiExtra().get("ScenicGuidePoi.ClearFocus") == null && this.d != null) {
            this.d.a();
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    public final void onMapSurfaceChanged(int i, int i2) {
        super.onMapSurfaceChanged(i, i2);
        SearchCQDetailPage searchCQDetailPage = (SearchCQDetailPage) this.mPage;
        if (searchCQDetailPage.b != null) {
            searchCQDetailPage.b.onMapSurfaceChanged(i, i2);
        }
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            ((SearchCQDetailPage) this.mPage).k.a(i, i2);
        }
    }

    public final void onMapSurfaceCreated() {
        super.onMapSurfaceCreated();
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            ((SearchCQDetailPage) this.mPage).k.a.onMapSurfaceCreated();
        }
    }

    public final void onMapSurfaceDestroy() {
        super.onMapSurfaceDestroy();
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            ((SearchCQDetailPage) this.mPage).k.a.onMapSurfaceDestroy();
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        SearchCQDetailPage searchCQDetailPage = (SearchCQDetailPage) this.mPage;
        if (searchCQDetailPage.b != null) {
            searchCQDetailPage.b.onFragmentResult(i, resultType, pageBundle);
        }
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            ((SearchCQDetailPage) this.mPage).k.a(i, resultType, pageBundle);
        }
    }

    static /* synthetic */ void b(cba cba) {
        if (!bin.a.a()) {
            if (cba.b == null) {
                cba.b = new SyncPopupWindow(((SearchCQDetailPage) cba.mPage).getContentView());
            }
            cba.b.show();
        }
    }
}
