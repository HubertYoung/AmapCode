package com.autonavi.bundle.searchresult.ajx;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.UiThread;
import android.text.TextUtils;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.fragment.SearchResultBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.search.model.SearchConst.SearchFor;
import com.autonavi.sdk.log.util.LogConstant;
import de.greenrobot.event.EventBus;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("search_result")
@KeepPublicClassMembers
@KeepName
public class ModuleSearch extends AbstractModule {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String MODULE_NAME = "search_result";
    public static final String STATE_ANCHORED = "2";
    public static final String STATE_COLLAPSED = "3";
    public static final String STATE_EXPANDED = "1";
    public static final String STATE_INVALID = "invalid";
    public static final String STATE_MOVING = "4";
    private bze mAjxProxy;
    private a mAnimateEventListener;
    private b mPageActionListener;
    SuperId mSuperId = SuperId.getInstance();
    private c mViewState;
    private JsFunctionCallback mViewStateChangeCallback;

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public interface a {
        @UiThread
        void a(String str);

        @UiThread
        void a(String str, String str2, String str3, String str4, String str5, String str6);

        @UiThread
        void b(String str);
    }

    public interface b {
        @UiThread
        void a();

        @UiThread
        void a(int i);

        @UiThread
        void a(int i, int i2);

        void a(String str, String str2);

        @UiThread
        void b();

        void b(int i);

        void c();
    }

    static class c {
        String a;
        boolean b;

        private c() {
        }

        /* synthetic */ c(byte b2) {
            this();
        }
    }

    @AjxMethod("onHeaderVoiceClick")
    public void onHeaderVoiceClick() {
    }

    public ModuleSearch(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("notifyFoldItemState")
    public void notifyFoldItemState(String str, String str2) {
        if (this.mPageActionListener != null) {
            this.mPageActionListener.a(str, str2);
        }
    }

    @AjxMethod("registerSetPageStateCallback")
    public void registerSetPageStateCallback(JsFunctionCallback jsFunctionCallback) {
        this.mViewStateChangeCallback = jsFunctionCallback;
        if (this.mViewStateChangeCallback != null && this.mViewState != null) {
            this.mViewStateChangeCallback.callback(this.mViewState.a, Boolean.valueOf(this.mViewState.b));
        }
    }

    @AjxMethod("onPageStateDidChanged")
    public void onPageStateDidChanged(String str, String str2) {
        if (this.mAnimateEventListener != null) {
            this.mAnimateEventListener.a(str);
        }
    }

    @AjxMethod("onStartAnimateToState")
    public void onStartAnimateToState(String str, String str2) {
        if (this.mAnimateEventListener != null) {
            this.mAnimateEventListener.b(str);
        }
    }

    @AjxMethod("onListHeightForAllState")
    public void onListHeightForAllState(String str, String str2, String str3, String str4, String str5, String str6) {
        if (this.mAnimateEventListener != null) {
            this.mAnimateEventListener.a(str, str2, str3, str4, str5, str6);
        }
    }

    @AjxMethod("searchForFilter")
    public void searchForFilter(String str, JsFunctionCallback jsFunctionCallback) {
        JsFunctionCallback jsFunctionCallback2 = jsFunctionCallback;
        JSONObject json = toJson(str);
        String optString = json.optString("city");
        if (NetworkReachability.b() || bby.c(optString)) {
            defpackage.bze.a aVar = new defpackage.bze.a(json.optString("classify_data"), json.optString("hotelcheckin"), json.optString("hotelcheckout"), json.optString("city"), json.optString(TrafficUtil.KEYWORD), json.optString("query_type"), json.optString("longitude"), json.optString("latitude"), json.optString("scenefilter"), json.optString("classifyLastSuperId"));
            bze bze = this.mAjxProxy;
            if (!bze.i && bze.c == null) {
                throw new AssertionError();
            } else if (bze.i || bze.f != null) {
                bze.e = "search_for_filter_callback";
                bze.g.put("search_for_filter_callback", jsFunctionCallback2);
                SuperId copy = SuperId.getInstance().getCopy();
                copy.setBit4(aVar.j);
                InfoliteParam clone = bze.f.c().clone();
                clone.pagenum = 1;
                clone.classify_data = aVar.a;
                clone.city = aVar.d;
                clone.hotelcheckin = aVar.b;
                clone.hotelcheckout = aVar.c;
                clone.scenefilter = aVar.i;
                if (!TextUtils.isEmpty(aVar.h)) {
                    clone.keywords = aVar.h;
                }
                if (!TextUtils.isEmpty(aVar.e) && !TextUtils.isEmpty(aVar.f)) {
                    clone.longitude = bze.a(aVar.e);
                    clone.latitude = bze.a(aVar.f);
                }
                if (!TextUtils.isEmpty(aVar.g)) {
                    clone.query_type = aVar.g;
                    clone.search_operate = 1;
                    if ("RQBXY".equals(aVar.g)) {
                        clone.search_operate = 2;
                    }
                }
                clone.superid = copy.getScenceId();
                bxi bxi = bze.c;
                bxh bxh = bze.f;
                ekv ekv = new ekv();
                bvu bvu = new bvu(bxh, 4);
                bvu.c = bxi.a.getResources().getString(R.string.searching);
                if (bxi.b != null) {
                    bxi.b.a();
                }
                bxi.b = ekv.a(clone, 1, bvu);
            } else {
                throw new AssertionError();
            }
        } else {
            ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.ic_net_error_tipinfo));
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("status", 0);
                if (jsFunctionCallback2 != null) {
                    jsFunctionCallback2.callback(jSONObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @AjxMethod("searchForTurnPage")
    public void searchForTurnPage(String str, JsFunctionCallback jsFunctionCallback) {
        JSONObject json = toJson(str);
        boolean z = false;
        int i = 1;
        if (json.optInt("waterFallForType", 0) == 2) {
            z = true;
        }
        bze bze = this.mAjxProxy;
        int optInt = json.optInt("page_number");
        if (bze.i || bze.c != null) {
            bze.e = "search_for_turnpage_callback";
            bze.g.put("search_for_turnpage_callback", jsFunctionCallback);
            bxi bxi = bze.c;
            bxh bxh = bze.f;
            if (bxh.c() != null) {
                InfoliteParam clone = bxh.c().clone();
                clone.pagenum = optInt;
                clone.query_mode = z ? "normal" : "preload";
                if (!z || !aey.b()) {
                    if (z) {
                        i = 5;
                    }
                    ekv ekv = new ekv();
                    bvw bvw = new bvw(bxh, i);
                    bvw.b = z;
                    if (bxi.b != null) {
                        bxi.b.a();
                    }
                    bxi.b = ekv.a(clone, 2, bvw);
                } else {
                    bxi.a(bxh);
                    return;
                }
            }
            return;
        }
        throw new AssertionError();
    }

    @AjxMethod("onFooterRecommendMoreClick")
    public void onFooterRecommendMoreClick(String str, JsFunctionCallback jsFunctionCallback) {
        JSONObject json = toJson(str);
        bze bze = this.mAjxProxy;
        boolean optBoolean = json.optBoolean("needAnim", false);
        if (bze.i || bze.c != null) {
            bze.e = "footer_recommend_more_callback";
            bze.g.put("footer_recommend_more_callback", jsFunctionCallback);
            bxi bxi = bze.c;
            bxh bxh = bze.f;
            if (bxh.c() != null) {
                InfoliteParam clone = bxh.c().clone();
                clone.need_recommend = "2";
                ekv ekv = new ekv();
                bvu bvu = new bvu(bxh, 3);
                bvu.b = optBoolean;
                if (bxi.b != null) {
                    bxi.b.a();
                }
                bxi.b = ekv.a(clone, 1, bvu);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    @AjxMethod("onFooterIndoorSearchMoreClick")
    public void onFooterIndoorSearchMoreClick(JsFunctionCallback jsFunctionCallback) {
        int i;
        int i2;
        bze bze = this.mAjxProxy;
        if (bze.i || bze.c != null) {
            bze.e = "footer_indoor_search_more_callback";
            bze.g.put("footer_indoor_search_more_callback", jsFunctionCallback);
            bty mapView = ((SearchResultBasePage) bze.a).getMapManager().getMapView();
            if (mapView != null) {
                bxi bxi = bze.c;
                bxh bxh = bze.f;
                elc.c = true;
                elc.b = false;
                GeoPoint geoPoint = elc.k;
                if (geoPoint == null) {
                    geoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
                }
                if (!TextUtils.isEmpty(bxh.b())) {
                    LogManager.actionLogV2(LogConstant.SEARCH_RESULT_LIST, LogConstant.MORE_CAR_OWNER_SHOP);
                    Rect rect = elc.l;
                    if (rect == null && mapView != null) {
                        rect = mapView.H();
                    }
                    if (rect != null) {
                        i = (rect.bottom - rect.top) / 2;
                        i2 = (rect.right - rect.left) / 2;
                    } else {
                        i2 = 0;
                        i = 0;
                    }
                    Rect rect2 = new Rect(geoPoint.x - i2, geoPoint.y - i, geoPoint.x + i2, geoPoint.y + i);
                    InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), bxh.b(), geoPoint, rect2);
                    a2.search_sceneid = SuperId.getInstance().getScenceId();
                    a2.log_center_id = elc.g;
                    ekv ekv = new ekv();
                    bvt bvt = new bvt();
                    bvt.c = 2;
                    bvt.e = a2.keywords;
                    bvt.b = SearchFor.DEFAULT;
                    bvt.d = new bwx(a2.keywords, 2, false);
                    bvt.a(rect2);
                    if (bxi.b != null) {
                        bxi.b.a();
                    }
                    bxi.b = ekv.a(a2, 1, bvt);
                    return;
                }
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    @AjxMethod("onHeaderSwichOnlineClick")
    public void onHeaderSwichOnlineClick(JsFunctionCallback jsFunctionCallback) {
        bze bze = this.mAjxProxy;
        if (bze.i || bze.c != null) {
            bze.e = "header_swich_online_callback";
            bze.g.put("header_swich_online_callback", jsFunctionCallback);
            bxi bxi = bze.c;
            InfoliteParam c2 = bze.f.c();
            int i = bze.b;
            bxh bxh = bze.f;
            aey.a(true);
            bvt bvt = new bvt();
            bvt.e = c2.keywords;
            bwx bwx = new bwx(c2.keywords, i, false);
            bwx.r = bxh.l;
            bvt.d = bwx;
            bvt.a(bxh.k);
            bvt.g = false;
            ekv ekv = new ekv();
            if (bxi.b != null) {
                bxi.b.a();
            }
            bxi.b = ekv.a(c2, 1, bvt);
            return;
        }
        throw new AssertionError();
    }

    @AjxMethod("onHeaderCitySearchClick")
    public void onHeaderCitySearchClick(String str, JsFunctionCallback jsFunctionCallback) {
        String str2;
        JSONObject json = toJson(str);
        bze bze = this.mAjxProxy;
        String optString = json.optString("key");
        long optLong = json.optLong(AutoJsonUtils.JSON_ADCODE);
        if (bze.i || bze.c != null) {
            bze.e = "header_city_search_callback";
            bze.g.put("header_city_search_callback", jsFunctionCallback);
            bxi bxi = bze.c;
            bxh bxh = bze.f;
            if (bxh.c() != null) {
                str2 = bxh.c().geoobj;
            } else {
                str2 = "";
            }
            SuperId.getInstance().setBit3("10");
            if (bxi.b != null) {
                bxi.b.a();
            }
            bwx bwx = new bwx(optString, -1, false);
            bxi.b = bwx.c.a("400001", true, str2, SuperId.getInstance(), optLong);
            return;
        }
        throw new AssertionError();
    }

    @AjxMethod("onHeaderSuggestKeyWordClick")
    public void onHeaderSuggestKeyWordClick(String str, JsFunctionCallback jsFunctionCallback) {
        JSONObject json = toJson(str);
        bze bze = this.mAjxProxy;
        String optString = json.optString("keywords");
        String optString2 = json.optString("superid");
        String optString3 = json.optString("tiprule");
        if (bze.i || bze.c != null) {
            bze.e = "header_suggest_keyword_callback";
            bze.g.put("header_suggest_keyword_callback", jsFunctionCallback);
            bty mapView = ((SearchResultBasePage) bze.a).getMapManager().getMapView();
            if (mapView != null) {
                SuperId.getInstance().setBit3("09");
                bxi bxi = bze.c;
                Rect H = mapView.H();
                bxh bxh = bze.f;
                String str2 = "";
                String str3 = "";
                if (bxh.c() != null) {
                    str2 = bxh.c().geoobj;
                    str3 = bxh.c().city;
                }
                InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), optString, H);
                a2.search_operate = 1;
                a2.utd_sceneid = "101000";
                if (optString2 != null) {
                    a2.superid = optString2;
                }
                if (optString3 != null) {
                    a2.tip_rule = optString3;
                }
                if (!TextUtils.isEmpty(str3)) {
                    a2.city = str3;
                }
                if (str2 != null && !str2.equals("")) {
                    a2.geoobj = str2;
                }
                aey.a(false);
                if (bxi.b != null) {
                    bxi.b.a();
                }
                bxi.b = new bwx(optString, -1, false).a(a2, (Rect) null, false);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    @AjxMethod("fetchReadedPoiList")
    public void fetchReadedPoiList(JsFunctionCallback jsFunctionCallback) {
        bze bze = this.mAjxProxy;
        if (bze.i || bze.d != null) {
            Set<String> set = bze.d.h;
            if (set == null) {
                set = Collections.emptySet();
            }
            try {
                JSONArray jSONArray = new JSONArray(Arrays.toString((String[]) set.toArray(new String[set.size()])));
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("poiIds", jSONArray);
                bze.a(jSONObject, jsFunctionCallback);
            } catch (JSONException e) {
                e.printStackTrace();
                bze.a(2, e, jsFunctionCallback);
            }
        } else {
            throw new AssertionError();
        }
    }

    @AjxMethod("onHeaderSearchClick")
    public void onHeaderSearchClick() {
        if (this.mPageActionListener != null) {
            this.mPageActionListener.a();
        }
    }

    @AjxMethod("registerSearchInKenCallback")
    public void registerSearchInKenCallback(JsFunctionCallback jsFunctionCallback) {
        bze bze = this.mAjxProxy;
        if (bze.i || bze.c != null) {
            bze.e = "register_search_inken_callback";
            bze.g.put("register_search_inken_callback", jsFunctionCallback);
            return;
        }
        throw new AssertionError();
    }

    @AjxMethod("onCellClick")
    public void onCellClick(String str) {
        if (this.mPageActionListener != null) {
            JSONObject json = toJson(str);
            int optInt = json.optInt("poi_index", -1);
            json.optInt("pageNumber", -1);
            json.optInt("layerIndex", -1);
            int optInt2 = json.optInt("subIndex", -1);
            if (optInt2 >= 0) {
                this.mPageActionListener.a(optInt, optInt2);
                return;
            }
            this.mPageActionListener.a(optInt);
        }
    }

    @AjxMethod("onGeoSubPointClick")
    public void onGeoSubPointClick(String str) {
        if (this.mPageActionListener != null) {
            this.mPageActionListener.b(toJson(str).optInt("index", -1));
        }
    }

    @AjxMethod("onGeoCellClick")
    public void onGeoCellClick() {
        if (this.mPageActionListener != null) {
            this.mPageActionListener.c();
        }
    }

    @AjxMethod("onGeoShowAllResult")
    public void onGeoShowAllResult() {
        Collection collection;
        bze bze = this.mAjxProxy;
        bze.f.b.searchInfo.a.u = null;
        bze.f.a();
        InfoliteResult infoliteResult = bze.f.b;
        if (bcy.c(infoliteResult)) {
            if (!bcy.a(infoliteResult)) {
                collection = null;
            } else {
                collection = infoliteResult.searchInfo.o.e;
            }
            if (collection != null) {
                infoliteResult.searchInfo.l = new ArrayList<>(collection);
                infoliteResult.searchInfo.o.e = null;
            } else {
                infoliteResult.searchInfo.l = new ArrayList<>();
            }
        }
        bxh bxh = bze.f;
        InfoliteResult infoliteResult2 = bxh.b;
        if (bcy.b(infoliteResult2)) {
            bcy.a((List<POI>) infoliteResult2.searchInfo.l);
        }
        bxh.e.c = false;
        bze.f.a(bze.f.b);
        bze.f.a(3);
        bze.f.a((String) "search_for_filter_callback");
    }

    @AjxMethod("onBusCellClick")
    public void onBusCellClick(String str) {
        SuperId.getInstance().reset();
        SuperId.getInstance().setBit1(this.mSuperId.getBit1());
        SuperId.getInstance().setBit2(this.mSuperId.getBit2());
        SuperId.getInstance().setBit3("11");
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                String optString = jSONObject.optString("id");
                String optString2 = jSONObject.optString("key_name");
                if (!TextUtils.isEmpty(optString)) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(getDetailUrl(optString, optString2));
                    esf.a().a(new ese(intent));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Uri getDetailUrl(String str, String str2) {
        StringBuilder sb = new StringBuilder("amapuri://realtimeBus/detail?param={");
        if (!TextUtils.isEmpty(str)) {
            sb.append("lineId:");
            sb.append(str);
        }
        sb.append(",from:amap_search}");
        return Uri.parse(sb.toString());
    }

    @AjxMethod("onBusMoreCellClick")
    public void onBusMoreCellClick() {
        if (this.mPageActionListener != null) {
            this.mPageActionListener.b();
        }
    }

    @AjxMethod("onListGoHereClick")
    public void onListGoHereClick(String str) {
        elk c2 = this.mAjxProxy.f.c(toJson(str).optInt("poi_index", -1));
        if (c2 != null) {
            elk elk = c2;
            if (elk.b != null) {
                EventBus.getDefault().post(new cbm());
                new cbp();
                cbp.a("bundle_key_poi_end", elk.b, "list");
                if (elk.f == 0 && !(c2 instanceof elh)) {
                    ccg a2 = ccg.a();
                    POI poi = elk.b;
                    if (!bcy.a(elk.b)) {
                        a2.a = elk.c;
                        a2.a(poi);
                    }
                }
            }
        }
    }

    private JSONObject toJson(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setOnAnimateEventListener(a aVar) {
        this.mAnimateEventListener = aVar;
    }

    public void setOnPageActionListener(b bVar) {
        this.mPageActionListener = bVar;
    }

    public void setAjxProxy(bze bze) {
        this.mAjxProxy = bze;
    }

    public void setViewState(String str, boolean z) {
        if (this.mViewStateChangeCallback != null) {
            this.mViewState = null;
            this.mViewStateChangeCallback.callback(str, Boolean.valueOf(z));
            return;
        }
        this.mViewState = new c(0);
        this.mViewState.a = str;
        this.mViewState.b = z;
    }
}
