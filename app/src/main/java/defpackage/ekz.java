package defpackage;

import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.routecommon.model.IRouteBusLineResult;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.fragment.SearchResultMapPage;
import com.autonavi.minimap.R;

/* renamed from: ekz reason: default package */
/* compiled from: PoiDetailHelperImpl */
public final class ekz implements bcg {
    public final synchronized void a(POI poi, String str, InfoliteResult infoliteResult, IRouteBusLineResult iRouteBusLineResult, SuperId superId) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            if (str.equals("poilist") && infoliteResult != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("from_page", 11100);
                pageBundle.putObject("poi_search_result", infoliteResult);
                pageBundle.putBoolean("need_refresh", true);
                if (superId != null) {
                    pageBundle.putSerializable("SUPER_ID", superId);
                }
                pageContext.startPage(SearchResultMapPage.class, pageBundle);
            } else if (str.equals("poitip")) {
                PageBundle pageBundle2 = new PageBundle();
                pageBundle2.putInt("from_page", 11100);
                if (infoliteResult != null) {
                    pageBundle2.putObject("poi_search_result", infoliteResult);
                }
                pageBundle2.putBoolean("need_refresh", false);
                if (superId != null) {
                    pageBundle2.putSerializable("SUPER_ID", superId);
                }
                bby.a(pageContext, SearchResultMapPage.class, pageBundle2);
            } else if (str.equals("busline")) {
                pageContext.finish();
            } else if (str.equals("singlepoi")) {
                pageContext.finish();
            } else if (str.equals("buslinemap")) {
                PageBundle pageBundle3 = new PageBundle();
                pageBundle3.putObject("bundle_key_result_obj", iRouteBusLineResult);
                pageContext.finish();
                pageContext.startPage((String) "amap.extra.route.busline_station_map", pageBundle3);
            } else if (str.equals("buslinelist")) {
                PageBundle pageBundle4 = new PageBundle();
                pageBundle4.putObject("bundle_key_result_obj", iRouteBusLineResult);
                pageContext.finish();
                pageContext.startPage((String) "amap.extra.route.busline_station_list", pageBundle4);
            } else if (str.equals("life_groupbuy")) {
                PageBundle pageBundle5 = new PageBundle();
                pageBundle5.putString("title", poi.getName());
                pageBundle5.putSerializable("poi", poi);
                pageBundle5.putBoolean("EXTRA_SHOW_ONE_POINT_KEY", true);
                pageContext.startPage((String) "amap.life.action.NewGroupBuyToMapFragment", pageBundle5);
            } else if (str.equals("life_hotel")) {
                PageBundle pageBundle6 = new PageBundle();
                pageBundle6.putString("title", poi.getName());
                pageBundle6.putSerializable("poi", poi);
                pageBundle6.putBoolean("EXTRA_SHOW_ONE_POINT_KEY", true);
                pageContext.startPage((String) "amap.life.action.HotelToMapPage", pageBundle6);
            } else if (str.equals("life_movie")) {
                PageBundle pageBundle7 = new PageBundle();
                pageBundle7.putString("title", poi.getName());
                pageBundle7.putSerializable("poi", poi);
                pageBundle7.putBoolean("EXTRA_SHOW_ONE_POINT_KEY", true);
                pageContext.startPage((String) "amap.life.action.CinemaMapFragment", pageBundle7);
            } else {
                PageBundle pageBundle8 = new PageBundle();
                pageBundle8.putObject("POI", poi);
                pageBundle8.putBoolean("isGeoCode", false);
                pageBundle8.putBoolean("isGPSPoint", false);
                pageBundle8.putBoolean("isMarkPoi", false);
                pageBundle8.putInt("poi_detail_page_type", 4);
                pageContext.startPage((String) "amap.search.action.poidetail", pageBundle8);
            }
        }
    }

    public final void a(bid bid, POI poi) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("POI", poi);
        bid.startPage((String) "amap.search.action.category", pageBundle);
    }

    public final void a(POI poi) {
        bax bax = (bax) a.a.a(bax.class);
        if (bax != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_key_poi_start", null);
            pageBundle.putObject("bundle_key_poi_end", poi.clone());
            pageBundle.putObject("bundle_key_auto_route", Boolean.TRUE);
            bax.a(pageBundle);
        }
    }

    public final void b(bid bid, POI poi) {
        if (poi != null) {
            bax bax = (bax) a.a.a(bax.class);
            if (bax != null) {
                PageBundle pageBundle = new PageBundle();
                POI clone = poi.clone();
                clone.setName(lh.a(bid.getContext(), R.string.LocationMe));
                pageBundle.putObject("bundle_key_poi_start", clone);
                bax.a(pageBundle);
            }
        }
    }
}
