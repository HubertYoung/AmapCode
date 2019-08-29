package defpackage;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.MapPointPOI;
import com.autonavi.map.fragmentcontainer.page.IPoiTipViewService;
import com.autonavi.map.search.fragment.BaseCQDetailOwner;
import com.autonavi.map.search.fragment.SearchCQDetailPage;
import com.autonavi.map.search.fragment.SearchCQDetailPage.POI_DETAIL_TYPE;
import com.autonavi.map.search.inter.SearchCQDetailDelegateImpl$2;
import com.autonavi.map.search.inter.SearchCQDetailDelegateImpl$4;
import com.autonavi.map.search.inter.SearchCQDetailDelegateImpl$6;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import java.lang.ref.WeakReference;

/* renamed from: byk reason: default package */
/* compiled from: SearchCQDetailDelegateImpl */
public final class byk implements byj {
    public AbstractPoiDetailView a;
    public ely b;
    public AbstractGpsTipView c;
    private final WeakReference<SearchCQDetailPage> d;
    private int e;
    private int f;
    private BaseCQDetailOwner g;

    /* renamed from: byk$4 reason: invalid class name */
    /* compiled from: SearchCQDetailDelegateImpl */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] a = new int[POI_DETAIL_TYPE.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.autonavi.map.search.fragment.SearchCQDetailPage$POI_DETAIL_TYPE[] r0 = com.autonavi.map.search.fragment.SearchCQDetailPage.POI_DETAIL_TYPE.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.map.search.fragment.SearchCQDetailPage$POI_DETAIL_TYPE r1 = com.autonavi.map.search.fragment.SearchCQDetailPage.POI_DETAIL_TYPE.PAGE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.map.search.fragment.SearchCQDetailPage$POI_DETAIL_TYPE r1 = com.autonavi.map.search.fragment.SearchCQDetailPage.POI_DETAIL_TYPE.CQ_VIEW     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.autonavi.map.search.fragment.SearchCQDetailPage$POI_DETAIL_TYPE r1 = com.autonavi.map.search.fragment.SearchCQDetailPage.POI_DETAIL_TYPE.DEFAULT     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.byk.AnonymousClass4.<clinit>():void");
        }
    }

    public byk(SearchCQDetailPage searchCQDetailPage, BaseCQDetailOwner baseCQDetailOwner) {
        this.d = new WeakReference<>(searchCQDetailPage);
        this.g = baseCQDetailOwner;
    }

    public final void b() {
        boolean z = false;
        if (this.a != null) {
            this.a.refreshByScreenState(c().getConfiguration().orientation == 2);
        }
        if (this.b != null) {
            this.b.refreshByScreenState(c().getConfiguration().orientation == 2);
        }
        if (this.c != null) {
            AbstractGpsTipView abstractGpsTipView = this.c;
            if (c().getConfiguration().orientation == 2) {
                z = true;
            }
            abstractGpsTipView.refreshByScreenState(z);
        }
    }

    private Resources c() {
        if (this.d.get() != null) {
            return ((SearchCQDetailPage) this.d.get()).getResources();
        }
        return null;
    }

    public final void a() {
        this.f = 0;
    }

    public final Object a(PageBundle pageBundle, final a aVar) {
        POI poi = (POI) pageBundle.getObject("POI");
        cdx cdx = null;
        if (poi == null) {
            return null;
        }
        if (MapPointPOI.class.isInstance(poi)) {
            POI poi2 = (POI) pageBundle.getObject("POI");
            if (poi2 == null) {
                return null;
            }
            if (this.b == null) {
                IPoiTipViewService iPoiTipViewService = (IPoiTipViewService) ank.a(IPoiTipViewService.class);
                if (iPoiTipViewService != null) {
                    this.b = iPoiTipViewService.createPoiTipView((ViewGroup) this.g.d(), (bid) this.d.get(), poi2);
                }
                if (this.b == null) {
                    return null;
                }
                this.b.setSingle(true);
                this.b.setFromSource("mainmap");
                this.b.adjustMargin();
                if (this.b instanceof ely) {
                    this.b.setTipItemEvent(iPoiTipViewService.createPoiTipEvent(false));
                }
            }
            if (this.b.getView() instanceof cbs) {
                cbs cbs = (cbs) this.b.getView();
                cbs.setShowTipClose(true);
                cbs.setTipViewListener(new a() {
                });
            }
            this.b.refreshByScreenState(ags.b(((SearchCQDetailPage) this.d.get()).getActivity()));
            SearchCQDetailDelegateImpl$2 searchCQDetailDelegateImpl$2 = new SearchCQDetailDelegateImpl$2(this, pageBundle, aVar);
            int[] iArr = AnonymousClass4.a;
            this.d.get();
            switch (iArr[SearchCQDetailPage.c().ordinal()]) {
                case 1:
                case 2:
                    this.f = (int) System.currentTimeMillis();
                    searchCQDetailDelegateImpl$2.callback(Integer.valueOf(this.f));
                    break;
            }
            return this.b;
        } else if (!GpsPOI.class.isInstance(poi)) {
            IPoiTipViewService iPoiTipViewService2 = (IPoiTipViewService) ank.a(IPoiTipViewService.class);
            if (this.a == null && iPoiTipViewService2 != null) {
                this.a = iPoiTipViewService2.createPoiDetailViewForCQ();
                this.a.adjustMargin();
            }
            if (this.a instanceof cbs) {
                cbs cbs2 = (cbs) this.a;
                cbs2.setShowTipClose(true);
                cbs2.setTipViewListener(new a() {
                });
            }
            this.a.refreshByScreenState(ags.b(((SearchCQDetailPage) this.d.get()).getActivity()));
            SearchCQDetailDelegateImpl$4 searchCQDetailDelegateImpl$4 = new SearchCQDetailDelegateImpl$4(this, pageBundle, poi, aVar);
            int[] iArr2 = AnonymousClass4.a;
            this.d.get();
            switch (iArr2[SearchCQDetailPage.c().ordinal()]) {
                case 1:
                case 2:
                    this.f = (int) System.currentTimeMillis();
                    searchCQDetailDelegateImpl$4.callback(Integer.valueOf(this.f));
                    break;
            }
            return this.a;
        } else if (((POI) pageBundle.getObject("POI")) == null || this.d.get() == null) {
            return null;
        } else {
            MapManager mapManager = ((SearchCQDetailPage) this.d.get()).getMapManager();
            if (this.c == null) {
                IPoiTipViewService iPoiTipViewService3 = (IPoiTipViewService) ank.a(IPoiTipViewService.class);
                if (iPoiTipViewService3 != null) {
                    bid bid = (bid) this.d.get();
                    if (mapManager != null) {
                        cdx = mapManager.getOverlayManager().getGpsLayer();
                    }
                    this.c = iPoiTipViewService3.createGpsTipViewForPoiDetaiilDelegate(bid, cdx);
                    this.c.adjustMargin();
                }
            } else {
                this.d.get();
                if (SearchCQDetailPage.c() == POI_DETAIL_TYPE.DEFAULT && this.c.getVisibility() == 0) {
                    return null;
                }
            }
            if (this.c instanceof cbs) {
                cbs cbs3 = (cbs) this.c;
                cbs3.setShowTipClose(true);
                cbs3.setTipViewListener(new a() {
                });
            }
            this.c.setFromPageID(10001);
            this.c.refreshByScreenState(ags.b(((SearchCQDetailPage) this.d.get()).getActivity()));
            SearchCQDetailDelegateImpl$6 searchCQDetailDelegateImpl$6 = new SearchCQDetailDelegateImpl$6(this, aVar);
            int[] iArr3 = AnonymousClass4.a;
            this.d.get();
            switch (iArr3[SearchCQDetailPage.c().ordinal()]) {
                case 1:
                case 2:
                    this.f = (int) System.currentTimeMillis();
                    searchCQDetailDelegateImpl$6.callback(Integer.valueOf(this.f));
                    break;
            }
            return this.c;
        }
    }

    public final void a(GeoPoint geoPoint) {
        int i;
        if (this.e <= 0) {
            if (this.a == null) {
                i = 0;
            } else {
                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
                this.a.measure(MeasureSpec.makeMeasureSpec(0, 0), makeMeasureSpec);
                i = this.a.getMeasuredHeight();
            }
            this.e = i;
        }
        if (this.e > 0 && geoPoint != null && this.d.get() != null) {
            Point point = new Point();
            bty mapView = ((SearchCQDetailPage) this.d.get()).getMapManager().getMapView();
            if (mapView != null) {
                mapView.a((GLGeoPoint) geoPoint, point);
                int a2 = this.e + agn.a(((SearchCQDetailPage) this.d.get()).getContext(), 88.0f);
                if (new Rect(0, mapView.am() - a2, mapView.al(), mapView.am()).contains(point.x, point.y)) {
                    mapView.a(mapView.c(mapView.al() / 2, (mapView.am() / 2) + (a2 - (mapView.am() - point.y)) + 150));
                }
            }
        }
    }
}
