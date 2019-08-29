package defpackage;

import android.graphics.Rect;
import android.view.View;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView.Event;
import com.autonavi.minimap.route.bus.busline.page.BusLineStationMapPage;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import java.util.ArrayList;
import java.util.List;

/* renamed from: duv reason: default package */
/* compiled from: BusLineStationMapPresenter */
public final class duv extends eae<BusLineStationMapPage> {
    public IBusLineSearchResult a;
    public int b = 0;
    /* access modifiers changed from: private */
    public boolean c = true;

    /* renamed from: duv$a */
    /* compiled from: BusLineStationMapPresenter */
    public class a implements Event {
        public IBusLineSearchResult a;

        public a() {
        }

        public final void onExecute(int i, POI poi) {
            if (i == 0) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putSerializable("POI", poi);
                pageBundle.putString("fromSource", "buslinemap");
                if (this.a != null) {
                    pageBundle.putObject("buslinedata", this.a);
                    pageBundle.putInt("poi_detail_page_type", 5);
                }
                ((BusLineStationMapPage) duv.this.mPage).startPage((String) "amap.search.action.poidetail", pageBundle);
                return;
            }
            if (i == 3) {
                duv.this.c = true;
                dfm dfm = (dfm) ank.a(dfm.class);
                if (dfm != null) {
                    dfm.a(poi);
                }
            }
        }
    }

    public duv(BusLineStationMapPage busLineStationMapPage) {
        super(busLineStationMapPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        a();
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        a();
    }

    private void a() {
        PageBundle arguments = ((BusLineStationMapPage) this.mPage).getArguments();
        if (arguments != null) {
            this.a = (IBusLineSearchResult) arguments.get("bundle_key_result_obj");
            if (this.a != null && arguments.containsKey("bundle_key_keyword")) {
                this.a.setSearchKeyword(arguments.getString("bundle_key_keyword"));
            }
        }
        ((BusLineStationMapPage) this.mPage).a(this.a);
    }

    public final void a(int i) {
        int i2;
        int i3;
        boolean z;
        if (i >= 0 && this.a != null) {
            boolean z2 = true;
            ArrayList<POI> poiList = this.a.getPoiList(1);
            if (poiList != null && i < poiList.size()) {
                this.a.setFocusedPoiIndex(i);
                GeoPoint point = poiList.get(i).getPoint();
                View topMapInteractiveView = ((BusLineStationMapPage) this.mPage).getTopMapInteractiveView();
                if (topMapInteractiveView != null) {
                    i2 = topMapInteractiveView.getHeight();
                } else {
                    i2 = 0;
                }
                View bottomMapInteractiveView = ((BusLineStationMapPage) this.mPage).getBottomMapInteractiveView();
                if (bottomMapInteractiveView != null) {
                    i3 = bottomMapInteractiveView.getHeight();
                } else {
                    i3 = 0;
                }
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext == null) {
                    z = false;
                } else {
                    z = cfe.a(pageContext, point, i2, i3, 0, 0);
                }
                if (z) {
                    z2 = false;
                }
                BusLineStationMapPage busLineStationMapPage = (BusLineStationMapPage) this.mPage;
                if (busLineStationMapPage.getMapManager() != null) {
                    busLineStationMapPage.getMapManager().getOverlayManager().clearAllFocus();
                }
                ((BusLineStationMapPage) this.mPage).b(i);
                BusLineStationMapPage busLineStationMapPage2 = (BusLineStationMapPage) this.mPage;
                if (busLineStationMapPage2.b != null) {
                    dul dul = busLineStationMapPage2.b;
                    if (dul.a != null) {
                        dul.a.setFocus(i, z2);
                    }
                }
                ((BusLineStationMapPage) this.mPage).e();
            }
        }
    }

    public final void onDestroy() {
        if (this.a != null) {
            this.a.setFocusedPoiIndex(-1);
        }
        super.onDestroy();
    }

    public final void onStart() {
        int i;
        int i2;
        int i3;
        super.onStart();
        ebo.a((AbstractBaseMapPage) this.mPage);
        ((BusLineStationMapPage) this.mPage).a.showListModel(false);
        BusLineStationMapPage busLineStationMapPage = (BusLineStationMapPage) this.mPage;
        if (busLineStationMapPage.getSuspendManager() != null) {
            busLineStationMapPage.getSuspendManager().d().f();
        }
        if (busLineStationMapPage.getMapView() != null) {
            busLineStationMapPage.getMapView().g(false);
        }
        if (this.a != null) {
            ((BusLineStationMapPage) this.mPage).a.setKeyword(this.a.getSearchKeyword());
            i = this.a.getFocusedPoiIndex();
            if (i == -1) {
                BusLineStationMapPage busLineStationMapPage2 = (BusLineStationMapPage) this.mPage;
                i = busLineStationMapPage2.b != null ? busLineStationMapPage2.b.a.getLastFocusedIndex() : -1;
            }
            BusLineStationMapPage busLineStationMapPage3 = (BusLineStationMapPage) this.mPage;
            ArrayList<POI> poiList = this.a.getPoiList(1);
            if (busLineStationMapPage3.b != null) {
                dul dul = busLineStationMapPage3.b;
                if (!(dul.a == null || poiList == null || poiList.size() == 0)) {
                    int size = poiList.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        POI poi = poiList.get(i4);
                        if (poi != null) {
                            dul.a.createOverlayItem(poi.getPoint(), i4);
                            if (i4 == i) {
                                dul.a.setFocus(i, true);
                            }
                        }
                    }
                }
            }
        } else {
            i = -1;
        }
        if (i != -1) {
            if (this.c) {
                this.c = false;
                BusLineStationMapPage busLineStationMapPage4 = (BusLineStationMapPage) this.mPage;
                Rect rect = null;
                PointOverlayItem pointOverlayItem = busLineStationMapPage4.b != null ? (PointOverlayItem) busLineStationMapPage4.b.a.getFocus() : null;
                BusLineStationMapPage busLineStationMapPage5 = (BusLineStationMapPage) this.mPage;
                if (busLineStationMapPage5.b != null) {
                    dul dul2 = busLineStationMapPage5.b;
                    if (!(dul2.a == null || busLineStationMapPage5.getMapView() == null)) {
                        float f = -1.0f;
                        GeoPoint geoPoint = pointOverlayItem != null ? pointOverlayItem.getGeoPoint() : null;
                        int p = busLineStationMapPage5.getMapView().p();
                        int q = busLineStationMapPage5.getMapView().q();
                        float w = (float) busLineStationMapPage5.getMapView().w();
                        float I = busLineStationMapPage5.getMapView().I();
                        float J = busLineStationMapPage5.getMapView().J();
                        if (geoPoint != null) {
                            p = geoPoint.x;
                            q = geoPoint.y;
                        }
                        List<GeoPoint> itemsGeoPoint = dul2.a.getItemsGeoPoint();
                        if (itemsGeoPoint != null && itemsGeoPoint.size() > 1) {
                            int i5 = p;
                            int i6 = i5;
                            int i7 = q;
                            int i8 = i7;
                            for (GeoPoint next : itemsGeoPoint) {
                                i5 = Math.min(i5, next.x);
                                i7 = Math.min(i7, next.y);
                                i6 = Math.max(i6, next.x);
                                i8 = Math.max(i8, next.y);
                            }
                            rect = new Rect(i5, i7, i6, i8);
                            f = busLineStationMapPage5.getMapView().a(i5, i7, i6, i8) - 1.0f;
                        }
                        float f2 = (itemsGeoPoint == null || itemsGeoPoint.size() != 1) ? f : 17.0f;
                        float f3 = f2 > 0.0f ? f2 : w;
                        if (rect != null) {
                            i3 = rect.centerX();
                            i2 = rect.centerY();
                        } else {
                            i3 = p;
                            i2 = q;
                        }
                        busLineStationMapPage5.getMapView().a(i3, i2, f3, I, J);
                    }
                }
                ((BusLineStationMapPage) this.mPage).b(i);
                ((BusLineStationMapPage) this.mPage).e();
                return;
            }
            a(i);
        }
    }

    public final void onStop() {
        super.onStop();
        BusLineStationMapPage busLineStationMapPage = (BusLineStationMapPage) this.mPage;
        if (busLineStationMapPage.b != null) {
            dul dul = busLineStationMapPage.b;
            if (busLineStationMapPage.getMapView() != null) {
                busLineStationMapPage.getMapView().g(true);
            }
            if (dul.a != null) {
                dul.a.clear();
            }
        }
    }
}
