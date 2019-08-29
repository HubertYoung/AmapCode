package defpackage;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.idqmax.page.SearchIdqMaxPage;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.search.common.PoiSearcherDispatchImpl$3;
import com.autonavi.map.search.common.PoiSearcherDispatchImpl$4;
import com.autonavi.map.search.fragment.PoiDetailPageNew;
import com.autonavi.map.search.fragment.SearchResultMapPage;
import com.autonavi.map.search.page.SearchAjxCitySuggestionPage;
import com.autonavi.map.search.page.SearchAjxCitySuggestionPage.a;
import com.autonavi.map.search.page.SearchAjxErrorPage;
import com.autonavi.map.search.page.SearchAjxNoResultPage;
import com.autonavi.map.search.page.SearchErrorSuggestionPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.minimap.search.utils.SearchUtils;
import com.autonavi.minimap.widget.ListDialog;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bwz reason: default package */
/* compiled from: PoiSearcherDispatchImpl */
public final class bwz implements bws {
    public bwx a;

    public bwz(bwx bwx) {
        this.a = bwx;
    }

    public final bbq a(InfoliteParam infoliteParam, Rect rect, boolean z) {
        this.a.d = z;
        this.a.o = rect;
        ekv ekv = new ekv();
        bvt bvt = new bvt();
        bvt.a(rect);
        bvt.d = this.a;
        bvt.b = bwx.a;
        bvt.e = infoliteParam.keywords;
        return ekv.a(infoliteParam, 1, bvt);
    }

    public final bbq a(String str, String str2, SuperId superId) {
        return a(str, false, str2, superId, 0);
    }

    public final void a(InfoliteResult infoliteResult, boolean z, int i) {
        a(infoliteResult, z, i, false, false, false, "");
    }

    public final void a(InfoliteResult infoliteResult, boolean z, int i, boolean z2, boolean z3, boolean z4, String str) {
        String str2;
        ArrayList<POI> arrayList = infoliteResult.searchInfo.l;
        ArrayList<aue> arrayList2 = infoliteResult.searchInfo.r;
        ArrayList<String> arrayList3 = infoliteResult.searchInfo.f;
        int size = infoliteResult.searchInfo.l.size();
        int size2 = arrayList2 != null ? arrayList2.size() : 0;
        PageBundle pageBundle = new PageBundle();
        if (this.a.o != null) {
            pageBundle.putObject("map_center_rect", this.a.o);
        }
        if (this.a.r != null) {
            pageBundle.putObject("center_poi", this.a.r);
        }
        pageBundle.putObject("poi_search_result", infoliteResult);
        pageBundle.putBoolean("is_cache", z4);
        pageBundle.putBoolean("key_can_search_with_hint", z3);
        pageBundle.putBoolean("is_from_new_nearby", z2);
        pageBundle.putString("hint_content", str);
        if (!z && ((infoliteResult.searchInfo.r == null || infoliteResult.searchInfo.r.size() == 0) && infoliteResult.responseHeader.f)) {
            if (!a(infoliteResult)) {
                if (infoliteResult.searchInfo.a.I != 0 || infoliteResult.searchInfo.a.J == null || !infoliteResult.searchInfo.a.J.equals("interior")) {
                    str2 = AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.ic_net_error_noresult);
                } else {
                    str2 = AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.ic_net_error_indoor_noresult);
                }
                this.a.b.b(str2);
            }
        } else if ((arrayList.size() == 0 || size <= 0) && (arrayList2 == null || arrayList2.size() == 0 || size2 == 0)) {
            List<POI> h = bcy.h(infoliteResult);
            if ((arrayList3 == null || arrayList3.size() <= 0) && h.size() <= 0) {
                if (!a(infoliteResult)) {
                    if (infoliteResult.searchInfo.a.I != 0 || infoliteResult.searchInfo.a.J == null || !infoliteResult.searchInfo.a.J.equals("interior")) {
                        this.a.b.b(AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.ic_net_error_noresult));
                    } else {
                        this.a.b.b(AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.ic_net_error_indoor_noresult));
                        return;
                    }
                }
                return;
            }
            a(infoliteResult, pageBundle, i);
        } else if (arrayList.size() <= 0 || size <= 0) {
            if (arrayList2 != null && size2 > 0) {
                a(infoliteResult, pageBundle, i);
            }
        } else {
            a(infoliteResult, pageBundle, i);
        }
    }

    private static int b(InfoliteResult infoliteResult) {
        if (infoliteResult == null || !bcy.d(infoliteResult)) {
            return 0;
        }
        int i = 2;
        if (!bxz.a((List<POI>) infoliteResult.searchInfo.l) && !byb.a(infoliteResult) && infoliteResult.searchInfo.a.b != 0) {
            i = infoliteResult.searchInfo.a.b == 1 ? 1 : 0;
        }
        return i;
    }

    private void a(InfoliteResult infoliteResult, PageBundle pageBundle, int i) {
        int b = b(infoliteResult);
        if (i == -1) {
            i = b;
        }
        pageBundle.putInt("search_page_type", this.a.k);
        pageBundle.putSerializable("SUPER_ID", SuperId.getInstance().getCopy());
        pageBundle.putInt("list_anchored_key", i);
        if (this.a.s) {
            pageBundle.putBoolean("key_is_switch_online", true);
        }
        if (this.a.m) {
            this.a.m = false;
        }
        boolean equals = "1".equals(infoliteResult.searchInfo.a.U);
        int a2 = a(i, infoliteResult);
        if (a2 == -1) {
            a(SearchResultMapPage.class, pageBundle);
        } else if (equals) {
            bbx.a(SearchIdqMaxPage.class);
            pageBundle.putInt("poi_detail_page_type", 11);
            a(SearchIdqMaxPage.class, pageBundle);
        } else {
            pageBundle.putInt("poi_detail_page_type", a2);
            if (a2 == 8) {
                bbx.a(PoiDetailPageNew.class);
            }
            a(PoiDetailPageNew.class, pageBundle);
        }
        if (infoliteResult.responseHeader.f) {
            LogManager.actionLogV25(LogConstant.SEARCH_RESULT_LIST, "B060", new SimpleEntry("status", ccd.a(infoliteResult)), new SimpleEntry("text", bcy.k(infoliteResult)));
            return;
        }
        if (!(infoliteResult.mWrapper == null || infoliteResult.mWrapper.offline_param == null)) {
            StringBuilder sb = new StringBuilder();
            sb.append(infoliteResult.mWrapper.offline_param.longitude);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(infoliteResult.mWrapper.offline_param.latitude);
            LogManager.actionLogV25(LogConstant.SEARCH_RESULT_LIST, LogConstant.HONGBAO_MAIL_DESTROY, new SimpleEntry(TrafficUtil.KEYWORD, infoliteResult.mWrapper.offline_param.keyword), new SimpleEntry("geoobj", sb.toString()), new SimpleEntry("query_type", infoliteResult.mWrapper.query_type), new SimpleEntry("user_loc", infoliteResult.mWrapper.user_loc), new SimpleEntry("gsid", bcy.l(infoliteResult)));
        }
    }

    public final void a(final InfoliteResult infoliteResult, final TipItem tipItem) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, infoliteResult);
        pageBundle.putString(TrafficUtil.KEYWORD, infoliteResult.mWrapper.keywords);
        pageBundle.putObject(SearchErrorSuggestionPage.BUNDLE_KEY_ONITEMLISTENER, new a() {
            public final void a(String str) {
                InfoliteParam clone = infoliteResult.mWrapper.clone();
                clone.city = str;
                clone.utd_sceneid = "400002";
                SuperId.getInstance().setBit3("09");
                clone.superid = SuperId.getInstance().getScenceId();
                ekv ekv = new ekv();
                if (aey.b(bby.a(str))) {
                    bvt bvt = new bvt();
                    if (a.a.a != null) {
                        a.a.a.mWrapper = clone;
                    }
                    a.a.b = clone;
                    bvt.e = bwz.this.a.e;
                    bvt.b = bwx.a;
                    bvt.f = tipItem;
                    bvt.d = bwz.this.a;
                    ekv.a(clone, 3, bvt);
                    return;
                }
                bvt bvt2 = new bvt();
                bvt2.e = bwz.this.a.e;
                bvt2.b = bwx.a;
                bvt2.f = tipItem;
                bvt2.d = bwz.this.a;
                ekv.a(clone, 0, bvt2);
            }
        });
        this.a.b.a(SearchAjxCitySuggestionPage.class, pageBundle);
    }

    private POI d() {
        POI poi = this.a.r;
        if (poi != null) {
            return poi;
        }
        if (this.a.o == null) {
            return null;
        }
        if (this.a.k == 2 || this.a.k == 1) {
            poi = POIFactory.createPOI("", new GeoPoint(this.a.o.left + ((this.a.o.right - this.a.o.left) / 2), this.a.o.top + ((this.a.o.bottom - this.a.o.top) / 2)));
        }
        return poi;
    }

    public final void a(InfoliteResult infoliteResult, final TipItem tipItem, int i, boolean z) {
        this.a.m = false;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString(TrafficUtil.KEYWORD, infoliteResult.mWrapper.keywords);
        pageBundle.putInt("search_page_type", this.a.k);
        pageBundle.putObject("map_center_rect", this.a.o);
        pageBundle.putObject("search_url_wrapper", infoliteResult.mWrapper);
        pageBundle.putObject("native_no_result_type", Integer.valueOf(i));
        POI d = d();
        if (d != null) {
            pageBundle.putObject(SearchAjxNoResultPage.a, d);
        }
        if (z) {
            ahm.a(new Runnable() {
                public final void run() {
                    SearchHistoryHelper.getInstance(AMapAppGlobal.getApplication()).saveTipItem(tipItem);
                }
            });
        }
        a(SearchAjxNoResultPage.class, pageBundle);
    }

    public final void a() {
        if (this.a.j.routingInfo.b == 1) {
            POI createPOI = POIFactory.createPOI(AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.LocationMe), new GeoPoint());
            if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                createPOI.setPoint(LocationInstrument.getInstance().getLatestPosition());
                a(createPOI, 1008);
            }
        } else if (this.a.f != null && this.a.f.size() == 1) {
            a(this.a.f.get(0), 1008);
        } else if (this.a.f == null || this.a.f.size() <= 1) {
            a((POI) null, 1008);
        } else {
            a(this.a.f, AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.Title_SetStart), (Handler) this.a.n, 1008);
        }
    }

    public final void b() {
        if (this.a.j.routingInfo.f == 1) {
            POI createPOI = POIFactory.createPOI();
            if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                createPOI.setPoint(LocationInstrument.getInstance().getLatestPosition());
                createPOI.setName(AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.LocationMe));
                a(createPOI, 1010);
            }
        } else if (this.a.g != null && this.a.g.size() == 1) {
            a(this.a.g.get(0), 1010);
        } else if (this.a.g == null || this.a.g.size() <= 1) {
            a((POI) null, 1010);
        } else {
            a(this.a.g, AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.Title_SetEnd), (Handler) this.a.n, 1010);
        }
    }

    private void a(POI poi, int i) {
        Message message = new Message();
        message.obj = poi;
        message.what = i;
        this.a.n.sendMessage(message);
    }

    public final void c() {
        if (this.a.i != null && this.a.i.size() > 0) {
            ArrayList<String> arrayList = this.a.i;
            String str = this.a.q;
            ListDialog a2 = this.a.b.a(arrayList, AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.Title_SuggestFromto_End));
            if (a2 != null) {
                a2.setOnItemClickListener(new PoiSearcherDispatchImpl$4(this, arrayList, str, a2));
            }
        } else if (this.a.h != null && this.a.h.size() > 0) {
            SuperId.getInstance().setBit3("08");
            a((String) "101000", (String) "", SuperId.getInstance());
        }
    }

    public final void a(POI poi, POI poi2) {
        if (poi != null && poi2 != null && poi.getPoint().x == poi2.getPoint().x && poi.getPoint().y == poi2.getPoint().y) {
            poi2 = null;
            this.a.b.a(AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.title_start_end_cannot_be_same));
        }
        PageBundle pageBundle = new PageBundle("amap.extra.route.route", "com.autonavi.minimap");
        pageBundle.putObject("bundle_key_poi_start", poi);
        pageBundle.putObject("bundle_key_poi_end", poi2);
        pageBundle.putBoolean("bundle_key_auto_route", true);
        bax bax = (bax) a.a.a(bax.class);
        if (bax != null) {
            bax.a(pageBundle);
        }
    }

    public final void a(ArrayList<String> arrayList, String str, String str2, Handler handler) {
        ListDialog a2 = this.a.b.a(arrayList, str2);
        if (a2 != null) {
            PoiSearcherDispatchImpl$3 poiSearcherDispatchImpl$3 = new PoiSearcherDispatchImpl$3(this, arrayList, str, 1009, handler, a2);
            a2.setOnItemClickListener(poiSearcherDispatchImpl$3);
        }
    }

    private void a(ArrayList<POI> arrayList, String str, Handler handler, int i) {
        int i2;
        if (i == 1010) {
            i2 = 2;
        } else if (i == 1008) {
            i2 = 1;
        } else {
            return;
        }
        this.a.b.a(arrayList, str, handler, i, i2, this.a.j);
    }

    public final boolean a(InfoliteResult infoliteResult) {
        if (infoliteResult == null) {
            return false;
        }
        if (infoliteResult.searchInfo.a.I == 0 && infoliteResult.searchInfo.a.J != null && infoliteResult.searchInfo.a.J.equals("interior")) {
            return false;
        }
        if ((infoliteResult.searchInfo.a.e != 2 && infoliteResult.searchInfo.a.e != 3) || infoliteResult.mWrapper.pagenum != 1 || !TextUtils.isEmpty(infoliteResult.mWrapper.classify_data)) {
            return false;
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("SearchErrorFragment.searchKeyWord", infoliteResult.mWrapper.keywords);
        pageBundle.putInt(SearchAjxErrorPage.c, infoliteResult.searchInfo.a.e);
        pageBundle.putString(SearchAjxErrorPage.d, infoliteResult.searchInfo.a.f);
        if (infoliteResult.searchInfo.a.B != null) {
            pageBundle.putObject(SearchAjxErrorPage.e, infoliteResult.searchInfo.a.B);
        }
        pageBundle.putObject(SearchAjxErrorPage.f, infoliteResult.mWrapper);
        pageBundle.putObject("map_center_rect", this.a.o);
        if (this.a.k != -1) {
            pageBundle.putInt(SearchAjxErrorPage.h, this.a.k);
        }
        if (this.a.o != null) {
            pageBundle.putObject(SearchAjxErrorPage.g, this.a.o);
        }
        pageBundle.putObject("POI", d());
        a(SearchAjxErrorPage.class, pageBundle);
        return true;
    }

    private void a(Class<? extends AbstractBasePage> cls, PageBundle pageBundle) {
        if (this.a.d) {
            this.a.b.b(cls, pageBundle);
        } else {
            this.a.b.a(cls, pageBundle);
        }
    }

    private static int a(int i, InfoliteResult infoliteResult) {
        if (infoliteResult == null || infoliteResult.mWrapper == null) {
            return -1;
        }
        if (infoliteResult.mWrapper.query_type.equals("IDQ") && infoliteResult.searchInfo.a.V) {
            return 8;
        }
        List<POI> p = bcy.p(infoliteResult);
        if (p.size() == 1) {
            POI poi = p.get(0);
            if (poi != null) {
                SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
                if (searchPoi != null && searchPoi.getRichInfoFlag()) {
                    return 8;
                }
                if (searchPoi != null && "citycard".equals(searchPoi.getIndustry())) {
                    return 10;
                }
            }
        }
        if ((infoliteResult.mWrapper.query_type.equals("IDQ") || (infoliteResult.responseHeader.f && p.size() == 1 && i != 2)) && infoliteResult.searchInfo.a.n != 1) {
            return 3;
        }
        return -1;
    }

    public final bbq a(String str, boolean z, String str2, SuperId superId, long j) {
        try {
            InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), this.a.e, DoNotUseTool.getPixel20Bound());
            a2.utd_sceneid = str;
            a2.search_operate = 1;
            a2.loc_strict = z;
            if (superId != null) {
                a2.superid = superId.getScenceId();
            }
            if (str2 != null && !str2.equals("")) {
                a2.geoobj = str2;
                String[] split = str2.split("\\|");
                if (split.length >= 4) {
                    double b = bby.b(split[0]);
                    double b2 = bby.b(split[1]);
                    GeoPoint geoPoint = new GeoPoint((bby.b(split[2]) + b) / 2.0d, (bby.b(split[3]) + b2) / 2.0d);
                    if (j <= 0) {
                        j = (long) li.a().a(geoPoint.getLongitude(), geoPoint.getLatitude());
                    }
                    a2.city = String.valueOf(j);
                }
            }
            ekv ekv = new ekv();
            bvt bvt = new bvt();
            bvt.e = this.a.e;
            bvt.b = bwx.a;
            bvt.d = this.a;
            SearchUtils.getCenterPoint(a2.geoobj);
            ekv.a(a2, 1, bvt);
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return null;
    }
}
