package com.autonavi.minimap.search.inter.impl;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Looper;
import android.text.TextUtils;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.searchresult.service.callback.AosSearchCallBack;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.idqmax.page.SearchIdqMaxPage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.fragment.PoiDetailPageNew;
import com.autonavi.map.search.fragment.SearchResultMapPage;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.geo.GeoRequestHolder;
import com.autonavi.minimap.geo.param.GeoCodeRequest;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.minimap.poi.PoiRequestHolder;
import com.autonavi.minimap.poi.param.InfoRequest;
import com.autonavi.minimap.search.model.SearchConst.SearchFor;
import com.autonavi.minimap.search.utils.SearchUtils;
import com.tencent.connect.common.Constants;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class SearchIntentDispatcherImpl extends BaseIntentDispatcher implements ekx {
    private static final String CENTER_AROUND_SEARCH_LAT_Q = "lat";
    private static final String CENTER_AROUND_SEARCH_LON_Q = "lon";
    private static final String CENTER_AROUND_SEARCH_POI_NAME_Q = "poiname";
    private static final String CENTER_AROUND_SEARCH_TRANSPARENT_Q = "transparent";
    public static final int DEFAULT_VIEWMAP_ZOOM_LEVEL = 16;
    private static final String ENVIRONMENT = "environment";
    private static final String ENVIRONMENT_INDEX = "index";
    private static final String GENERAL_SEARCH_TRANSPARENT = "transparent";
    private static final String HOST_ARROUND_POI = "arroundpoi";
    private static final String HOST_POI = "poi";
    private static final String HOST_ROUND_PAGE = "roundPage";
    private static final String HOST_VIEWMAP = "viewMap";
    private static final String HOST_VIEW_GEO = "viewGeo";
    private static final String HOST_VIEW_POIDETAIL = "viewPOIDetail";
    private static final String HOST_VIEW_REGEO = "viewReGeo";
    public static final int MAX_ZOOM_LEVEL = 19;
    public static final int MIN_ZOOM_LEVEL = 3;
    private static final String NEW_AROUND_SEARCH = "around";
    private static final String NEW_HOST_MINE = "mine";
    private static final String NEW_HOST_POI = "poi";
    private static final String NEW_HOST_SEARCH = "search";
    private static final String NEW_HOST_WEBVIEW = "webview";
    private static final String NEW_IDQ_SEARCH_KEY_ACTION = "action";
    private static final String NEW_IDQ_SEARCH_KEY_FROMPAGE = "frompage";
    private static final String NEW_IDQ_SEARCH_KEY_FUNTTYPE = "funtype";
    private static final String NEW_IDQ_SEARCH_KEY_POIID = "poiid";
    private static final String NEW_IDQ_SEARCH_KEY_POINAME = "poiname";
    private static final String NEW_IDQ_SEARCH_KEY_ZOOMLEVEL = "zoomlevel";
    private static final String NEW_KEY_WORD_GENERAL_SEARCH = "keyword";
    private static final String NEW_PATH_AMAPONLINE = "amaponline";
    private static final String NEW_PATH_COMMENT = "comment";
    private static final String NEW_PATH_DETAIL = "detail";
    private static final String NEW_PATH_GENERAL_SEARCH = "general";
    private static final String NEW_PATH_IDQ_SEARCH = "idq";
    private static final String NEW_PATH_LOCAL = "local";
    private static final String NEW_PATH_MINE_COMMENTS = "comments";
    private static final String NEW_PATH_ROUND_PAGE_MAIN = "main";
    private static final String NEW_PATH_THIRDPARTY = "thirdparty";
    private static final String NEW_PATH_TIP = "tip";
    private static final String NEW_PATH_TRANSPARENT = "transparent";
    private static final String OPEN_CENTER_AROUND_FRAGMENT_PATH = "centerAround";
    private static final String PARAMS_DISCLAIMER = "ThirdPartyServices";
    private static final String PARAMS_FROM_SEARCH_INNER_PAGE_HOME = "homePage";
    private static final String PARAMS_FROM_SEARCH_INNER_PAGE_KEY = "fromSearchInnerPage";
    private static final String PARAMS_GENERAL_SEARCH = "GeneralSearch";
    private static final String PARAMS_GENERAL_SEARCH_RESULT = "GeneralSearchResult";
    private static final String PARAMS_NATIVE_LOADING = "native_loading";
    private static final String PARAMS_OPEN_CAR_BRAND = "car_brand";
    private static final String PARAMS_OPEN_URL = "OpenURL";
    private static final String PARAMS_POI_SEARCH_FROM_SHENMA = "PoiSearchFromShenma";
    private static final String PARAMS_SEARCH_MORE = "SearchMore";
    private static final String SCENICAREA = "ScenicArea";
    private static final String SCENICAREA_BANNER_DETAIL = "banner_detail";
    private static final String SCENICAREA_BUS_DETAIL = "bus_detail";
    private static final String SCENICAREA_HOME = "home";
    private static final String SCENICAREA_HOMEPAGE = "homepage";
    private static final String SCENICAREA_MAP = "map";
    private static final String SCENICAREA_MIDDLE_DETAIL = "scenic_middle_detail";
    private static final String SCENICAREA_PARK_DETAIL = "park_detail";
    private static final String SCENICAREA_ROUTE = "route";
    private static final String SCENICAREA_SCENIC_LIST = "scenic_list";
    private static final String SCENICAREA_SCENIC_LIVE = "scenic_live";
    private static final String SCENICAREA_TICKETS_LIST = "tickets_list";
    private static final String SCENICAREA_TOPIC = "topic";
    private static final String SCENICAREA_WALKMAN_MAP = "walkman_map";
    private static final String SEARCH_SCHEMA_CENTER_NAME = "name";
    private static final String SEARCH_SCHEMA_CITY = "city";
    private static final String SEARCH_SCHEMA_NEED_HISTORY = "record_history";
    private static final String SEARCH_SCHEMA_SOURCE = "schema_source";
    private static final String SEARCH_SCHEMA_SUPER_ID = "superid";
    private static final String SMART_SCENICAREA_SET_MAP = "map_mode";
    private static final String WZCX_SCHEMA = "trafficViolations/index.html";
    /* access modifiers changed from: private */
    public Activity mActivity;
    private ProgressDlg mProgressDlg;

    static class IDSearchCallBack extends AosSearchCallBack {
        private WeakReference<SearchIntentDispatcherImpl> mSearchIntent = null;

        public IDSearchCallBack(SearchIntentDispatcherImpl searchIntentDispatcherImpl) {
            this.mSearchIntent = new WeakReference<>(searchIntentDispatcherImpl);
        }

        public void callback(final InfoliteResult infoliteResult) {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                aho.a(new Runnable() {
                    public final void run() {
                        IDSearchCallBack.this.callback(infoliteResult);
                    }
                });
                return;
            }
            bct.a();
            if (this.mSearchIntent != null) {
                SearchIntentDispatcherImpl searchIntentDispatcherImpl = (SearchIntentDispatcherImpl) this.mSearchIntent.get();
                if (searchIntentDispatcherImpl != null) {
                    searchIntentDispatcherImpl.dismissProgressDlg();
                    if (infoliteResult != null && infoliteResult.searchInfo != null && infoliteResult.searchInfo.l != null) {
                        ArrayList<POI> arrayList = infoliteResult.searchInfo.l;
                        if (arrayList != null && arrayList.size() > 0) {
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putObject("POI", arrayList.get(0));
                            pageBundle.putBoolean("isGeoCode", false);
                            pageBundle.putBoolean("isGPSPoint", false);
                            pageBundle.putBoolean("isMarkPoi", false);
                            pageBundle.putBoolean("isFromScheme", true);
                            pageBundle.putBoolean("viewMap_scheme", true);
                            pageBundle.putBoolean("animateToTop", true);
                            pageBundle.putInt("poi_detail_page_type", 1);
                            searchIntentDispatcherImpl.startPage(PoiDetailPageNew.class, pageBundle);
                        }
                    }
                }
            }
        }

        public void error(Throwable th, boolean z) {
            if (this.mSearchIntent != null) {
                ((SearchIntentDispatcherImpl) this.mSearchIntent.get()).dismissProgressDlg();
            }
        }
    }

    class a extends bvt {
        final /* synthetic */ String h;
        final /* synthetic */ String i;
        final /* synthetic */ int j;
        final /* synthetic */ int k;
        final /* synthetic */ String l;
        final /* synthetic */ int m;
        final /* synthetic */ boolean n;

        a(String str, String str2, int i2, int i3, String str3, int i4, boolean z) {
            this.h = str;
            this.i = str2;
            this.j = i2;
            this.k = i3;
            this.l = str3;
            this.m = i4;
            this.n = z;
        }

        /* renamed from: a */
        public final void callback(InfoliteResult infoliteResult) {
            if (infoliteResult != null && infoliteResult.searchInfo != null && infoliteResult.searchInfo.l != null && infoliteResult.searchInfo.l.size() > 0) {
                infoliteResult.mWrapper.keywords = this.i;
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("from_page", this.j);
                if (this.k != -1) {
                    pageBundle.putInt("from_page", this.k);
                }
                pageBundle.putString("action", this.l);
                pageBundle.putInt(SearchIntentDispatcherImpl.NEW_IDQ_SEARCH_KEY_ZOOMLEVEL, this.m);
                pageBundle.putString(SearchIntentDispatcherImpl.NEW_IDQ_SEARCH_KEY_FUNTTYPE, this.h);
                pageBundle.putObject("poi_search_result", infoliteResult);
                if (!bcy.d(infoliteResult) || !infoliteResult.searchInfo.a.V) {
                    pageBundle.putInt("poi_detail_page_type", 3);
                } else {
                    pageBundle.putInt("poi_detail_page_type", 8);
                }
                if ("1".equals(infoliteResult.searchInfo.a.U)) {
                    bbx.a(SearchIdqMaxPage.class);
                    SearchIntentDispatcherImpl.this.startPage(SearchIdqMaxPage.class, pageBundle);
                } else {
                    SearchIntentDispatcherImpl.this.startPage(PoiDetailPageNew.class, pageBundle);
                }
                if (this.n && bcy.b(infoliteResult) && infoliteResult.searchInfo.l.size() > 0) {
                    ccg.a().a(infoliteResult.searchInfo.l.get(0));
                }
            } else if (bby.a(this.h) == 1) {
                ToastHelper.showToast(AMapAppGlobal.getApplication().getResources().getString(R.string.no_result_please_retry_later));
            } else {
                bby.a(this.h);
                ToastHelper.showToast(AMapAppGlobal.getApplication().getResources().getString(R.string.network_error_message));
            }
        }

        public final String getLoadingMessage() {
            return SearchUtils.getLoadingMessage(this.i);
        }
    }

    static class b extends cbf implements bxj {
        private boolean b;
        private String c;
        private Uri d;

        public final boolean d() {
            return false;
        }

        public b(boolean z, String str, Uri uri) {
            this.b = z;
            this.c = str;
            this.d = uri;
        }

        public final boolean f() {
            return this.b;
        }

        public final void onPageCreated() {
            super.onPageCreated();
            if (this.c.contains("rank/index.html")) {
                bxb.a(this);
            }
        }

        public final void onDestroy() {
            super.onDestroy();
            bxb.b(this);
        }

        public final boolean h() {
            String str = this.c;
            StringBuilder sb = new StringBuilder();
            sb.append(ConfigerHelper.getInstance().getGoldcoinUrl());
            sb.append("index.html");
            if (str.contains(sb.toString()) || this.c.contains("userfeedback/feed_submit.html")) {
                return false;
            }
            if (!this.c.contains(ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.REAL_SCENE_ACTIVITY))) {
                return true;
            }
            return super.h();
        }

        public final boolean g() {
            String queryParameter = this.d.getQueryParameter("hide_title");
            if (TextUtils.equals(queryParameter, "1")) {
                return false;
            }
            if (TextUtils.equals(queryParameter, "0")) {
                return true;
            }
            return super.g();
        }

        public final String b() {
            String queryParameter = this.d.getQueryParameter("title");
            return queryParameter == null ? super.b() : queryParameter;
        }

        public final defpackage.ajh.b c() {
            return new defpackage.cbf.a() {
                public final boolean a() {
                    return true;
                }
            };
        }

        public final boolean a() {
            ((bid) this.a).finish();
            return false;
        }
    }

    public SearchIntentDispatcherImpl(Activity activity) {
        this.mActivity = activity;
    }

    public static void reverse(final Callback<POI> callback, String str, String str2) {
        if (callback == null || str == null) {
            throw new IllegalArgumentException("Callback should not bue null");
        }
        GeoCodeRequest geoCodeRequest = new GeoCodeRequest();
        geoCodeRequest.b = str;
        geoCodeRequest.c = str2;
        GeoRequestHolder.getInstance().sendGeoCode(geoCodeRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                if (aosByteResponse != null) {
                    callback.callback(defpackage.eky.a.a((byte[]) aosByteResponse.getResult()).a);
                } else {
                    callback.callback(null);
                }
            }

            public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                callback.error(aosResponseException, false);
            }
        });
    }

    public boolean dispatch(Intent intent) {
        Uri data = intent.getData();
        if (data == null) {
            return false;
        }
        String host = data.getHost();
        if (TextUtils.isEmpty(host)) {
            return false;
        }
        if (doProcessNewSchema(data, intent)) {
            return true;
        }
        if (host.equals(HOST_ARROUND_POI)) {
            showArroundPoi(data);
        } else if (host.equals(HOST_VIEWMAP)) {
            viewMap(data);
        } else if (host.equals(HOST_VIEW_GEO)) {
            startGeo(data);
        } else if (host.equals(HOST_VIEW_REGEO)) {
            startReGeo(data);
        } else if (host.equals(HOST_VIEW_POIDETAIL)) {
            searchPoiID(data);
        } else if (host.equalsIgnoreCase("poi")) {
            if (!doPoiScheme(data)) {
                return false;
            }
        } else if (host.equalsIgnoreCase(BaseIntentDispatcher.HOST_OPENFEATURE)) {
            if (!doOpenFeature(data)) {
                return false;
            }
        } else if (host.equals(ENVIRONMENT)) {
            startEnvironmentMapPage(data);
        } else if (host.equals(SCENICAREA)) {
            return startScenicAreaPage(data);
        } else {
            if (!host.equals(HOST_ROUND_PAGE)) {
                return false;
            }
            startRoundPage(data);
        }
        return true;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean doProcessNewSchema(android.net.Uri r7, android.content.Intent r8) {
        /*
            r6 = this;
            java.lang.String r0 = r7.getHost()
            java.util.List r1 = r7.getPathSegments()
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            r3 = 0
            if (r2 != 0) goto L_0x00fb
            if (r1 == 0) goto L_0x00fb
            boolean r2 = r1.isEmpty()
            if (r2 == 0) goto L_0x0019
            goto L_0x00fb
        L_0x0019:
            java.lang.Object r1 = r1.get(r3)
            java.lang.String r1 = (java.lang.String) r1
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto L_0x0026
            return r3
        L_0x0026:
            r2 = -1
            int r4 = r0.hashCode()
            r5 = 1
            switch(r4) {
                case -1176033972: goto L_0x005a;
                case -906336856: goto L_0x004f;
                case 111178: goto L_0x0045;
                case 3351635: goto L_0x003b;
                case 1224424441: goto L_0x0030;
                default: goto L_0x002f;
            }
        L_0x002f:
            goto L_0x0064
        L_0x0030:
            java.lang.String r4 = "webview"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0064
            r0 = 1
            goto L_0x0065
        L_0x003b:
            java.lang.String r4 = "mine"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0064
            r0 = 3
            goto L_0x0065
        L_0x0045:
            java.lang.String r4 = "poi"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0064
            r0 = 0
            goto L_0x0065
        L_0x004f:
            java.lang.String r4 = "search"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0064
            r0 = 2
            goto L_0x0065
        L_0x005a:
            java.lang.String r4 = "openFeature"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0064
            r0 = 4
            goto L_0x0065
        L_0x0064:
            r0 = -1
        L_0x0065:
            switch(r0) {
                case 0: goto L_0x00e2;
                case 1: goto L_0x00b0;
                case 2: goto L_0x007f;
                case 3: goto L_0x0072;
                case 4: goto L_0x006b;
                default: goto L_0x0068;
            }
        L_0x0068:
            r5 = 0
            goto L_0x00fa
        L_0x006b:
            boolean r3 = r6.doOpenFeature(r7)
        L_0x006f:
            r5 = r3
            goto L_0x00fa
        L_0x0072:
            java.lang.String r8 = "comments"
            boolean r8 = android.text.TextUtils.equals(r1, r8)
            if (r8 == 0) goto L_0x0068
            r6.openMineCommentsFragment(r7)
            goto L_0x00fa
        L_0x007f:
            java.lang.String r8 = "general"
            boolean r8 = android.text.TextUtils.equals(r1, r8)
            if (r8 == 0) goto L_0x008c
            r6.openSearchFragment(r7)
            goto L_0x00fa
        L_0x008c:
            java.lang.String r8 = "idq"
            boolean r8 = android.text.TextUtils.equals(r1, r8)
            if (r8 == 0) goto L_0x0098
            r6.openIdqSearchFragment(r7)
            goto L_0x00fa
        L_0x0098:
            java.lang.String r8 = "centerAround"
            boolean r8 = r8.equalsIgnoreCase(r1)
            if (r8 == 0) goto L_0x00a4
            r6.openCenterAroundFragment(r7)
            goto L_0x00fa
        L_0x00a4:
            java.lang.String r8 = "around"
            boolean r8 = android.text.TextUtils.equals(r1, r8)
            if (r8 == 0) goto L_0x0068
            r6.searchCenterAround(r7)
            goto L_0x00fa
        L_0x00b0:
            java.lang.String r0 = "local"
            boolean r0 = android.text.TextUtils.equals(r1, r0)
            if (r0 == 0) goto L_0x00bc
            r6.viewLocal(r7)
            goto L_0x00fa
        L_0x00bc:
            java.lang.String r0 = "amaponline"
            boolean r0 = android.text.TextUtils.equals(r1, r0)
            if (r0 == 0) goto L_0x00c8
            r6.viewAmapOnline(r7, r8)
            goto L_0x00fa
        L_0x00c8:
            java.lang.String r0 = "thirdparty"
            boolean r0 = android.text.TextUtils.equals(r1, r0)
            if (r0 == 0) goto L_0x00d5
            r6.viewThirdPartWebView(r7, r8)
            goto L_0x00fa
        L_0x00d5:
            java.lang.String r8 = "transparent"
            boolean r8 = android.text.TextUtils.equals(r1, r8)
            if (r8 == 0) goto L_0x0068
            r6.viewTransparentWebView(r7)
            goto L_0x00fa
        L_0x00e2:
            java.lang.String r8 = "tip"
            boolean r8 = android.text.TextUtils.equals(r1, r8)
            if (r8 == 0) goto L_0x00ef
            r6.viewMapNew(r7)
            r3 = 1
        L_0x00ef:
            java.lang.String r8 = "detail"
            boolean r8 = android.text.TextUtils.equals(r1, r8)
            if (r8 == 0) goto L_0x006f
            r6.doOpenFeatureShowPoiDetailNew(r7)
        L_0x00fa:
            return r5
        L_0x00fb:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl.doProcessNewSchema(android.net.Uri, android.content.Intent):boolean");
    }

    public void viewLocal(final Uri uri) {
        String queryParameter = uri.getQueryParameter("url");
        if (TextUtils.isEmpty(queryParameter)) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.intent_open_web_fail));
            return;
        }
        if (!ajn.a(queryParameter)) {
            queryParameter = ajn.b(queryParameter);
        }
        ((bgx) defpackage.esb.a.a.a(bgx.class)).getUrl(queryParameter, new defpackage.bgx.a() {
            public final void a(final String str) {
                if (!TextUtils.isEmpty(str)) {
                    aho.a(new Runnable() {
                        public final void run() {
                            final String queryParameter = uri.getQueryParameter("hide_title");
                            aja aja = new aja(str);
                            aja.b = new cav() {
                                public final boolean f() {
                                    return true;
                                }

                                public final boolean g() {
                                    return !TextUtils.equals(queryParameter, "1");
                                }

                                public final defpackage.ajh.a l_() {
                                    return new defpackage.ajh.a() {
                                        public final boolean b() {
                                            return false;
                                        }

                                        public final String a() {
                                            return AnonymousClass1.this.a.getContext().getString(R.string.refresh);
                                        }
                                    };
                                }

                                public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
                                    super.onResult(i, resultType, pageBundle);
                                    if (ResultType.OK == resultType && i == 1) {
                                        bxw.a(pageBundle, this.a.b());
                                    }
                                }
                            };
                            aix aix = (aix) defpackage.esb.a.a.a(aix.class);
                            if (aix != null) {
                                aix.a(AMapPageUtil.getPageContext(), aja);
                            }
                        }
                    });
                }
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0083  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void viewAmapOnline(android.net.Uri r4, android.content.Intent r5) {
        /*
            r3 = this;
            java.lang.String r0 = "login_check"
            java.lang.String r0 = r4.getQueryParameter(r0)
            java.lang.String r1 = "1"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000f
            return
        L_0x000f:
            java.lang.String r0 = "url"
            java.lang.String r0 = r4.getQueryParameter(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x002a
            android.app.Application r4 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r5 = com.autonavi.minimap.R.string.intent_open_web_fail
            java.lang.String r4 = r4.getString(r5)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r4)
            return
        L_0x002a:
            r1 = 0
            if (r5 == 0) goto L_0x004b
            java.lang.String r2 = "ajxData"
            boolean r2 = r5.hasExtra(r2)
            if (r2 == 0) goto L_0x004b
            java.lang.String r2 = "ajxData"
            java.lang.String r5 = r5.getStringExtra(r2)     // Catch:{ Exception -> 0x0047 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0047 }
            r2.<init>(r5)     // Catch:{ Exception -> 0x0047 }
            java.lang.String r5 = "multitab"
            boolean r5 = r2.optBoolean(r5)     // Catch:{ Exception -> 0x0047 }
            goto L_0x004c
        L_0x0047:
            r5 = move-exception
            r5.printStackTrace()
        L_0x004b:
            r5 = 0
        L_0x004c:
            java.lang.String r2 = "alipay.com"
            boolean r2 = r0.contains(r2)
            if (r2 != 0) goto L_0x005c
            java.lang.String r2 = "alipay.net"
            boolean r2 = r0.contains(r2)
            if (r2 == 0) goto L_0x005d
        L_0x005c:
            r1 = 1
        L_0x005d:
            boolean r2 = defpackage.ajn.a(r0)
            if (r2 != 0) goto L_0x0069
            if (r1 != 0) goto L_0x0069
            java.lang.String r0 = defpackage.ajn.b(r0)
        L_0x0069:
            aja r1 = new aja
            r1.<init>(r0)
            com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl$b r2 = new com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl$b
            r2.<init>(r5, r0, r4)
            r1.b = r2
            esb r4 = defpackage.esb.a.a
            java.lang.Class<aix> r5 = defpackage.aix.class
            esc r4 = r4.a(r5)
            aix r4 = (defpackage.aix) r4
            if (r4 == 0) goto L_0x008a
            bid r5 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r4.a(r5, r1)
        L_0x008a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl.viewAmapOnline(android.net.Uri, android.content.Intent):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0061  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void viewThirdPartWebView(final android.net.Uri r4, android.content.Intent r5) {
        /*
            r3 = this;
            java.lang.String r0 = "url"
            java.lang.String r0 = r4.getQueryParameter(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x001b
            android.app.Application r4 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r5 = com.autonavi.minimap.R.string.intent_open_web_fail
            java.lang.String r4 = r4.getString(r5)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r4)
            return
        L_0x001b:
            boolean r1 = defpackage.ajn.a(r0)
            if (r1 != 0) goto L_0x0025
            java.lang.String r0 = defpackage.ajn.b(r0)
        L_0x0025:
            r1 = 0
            if (r5 == 0) goto L_0x0046
            java.lang.String r2 = "ajxData"
            boolean r2 = r5.hasExtra(r2)
            if (r2 == 0) goto L_0x0046
            java.lang.String r2 = "ajxData"
            java.lang.String r5 = r5.getStringExtra(r2)     // Catch:{ Exception -> 0x0042 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0042 }
            r2.<init>(r5)     // Catch:{ Exception -> 0x0042 }
            java.lang.String r5 = "multitab"
            boolean r5 = r2.optBoolean(r5)     // Catch:{ Exception -> 0x0042 }
            goto L_0x0047
        L_0x0042:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0046:
            r5 = 0
        L_0x0047:
            aja r1 = new aja
            r1.<init>(r0)
            com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl$8 r0 = new com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl$8
            r0.<init>(r5, r4)
            r1.b = r0
            esb r4 = defpackage.esb.a.a
            java.lang.Class<aix> r5 = defpackage.aix.class
            esc r4 = r4.a(r5)
            aix r4 = (defpackage.aix) r4
            if (r4 == 0) goto L_0x0068
            bid r5 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r4.a(r5, r1)
        L_0x0068:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl.viewThirdPartWebView(android.net.Uri, android.content.Intent):void");
    }

    private void viewTransparentWebView(Uri uri) {
        String queryParameter = uri.getQueryParameter("url");
        if (TextUtils.isEmpty(queryParameter)) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.intent_open_web_fail));
            return;
        }
        if (!ajn.a(queryParameter)) {
            queryParameter = ajn.b(queryParameter);
        }
        aix aix = (aix) defpackage.esb.a.a.a(aix.class);
        if (aix != null) {
            aix.a(AMapPageUtil.getPageContext(), queryParameter);
        }
    }

    private boolean doOpenFeature(Uri uri) {
        String queryParameter = uri.getQueryParameter("featureName");
        if (TextUtils.isEmpty(queryParameter)) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.intent_open_fail_param_error));
            return true;
        }
        if (queryParameter.equalsIgnoreCase(PARAMS_OPEN_CAR_BRAND)) {
            openFeatureCarBrand(uri);
        } else if (queryParameter.equalsIgnoreCase(PARAMS_GENERAL_SEARCH)) {
            doOpenFeatureGeneralSearch();
        } else if (queryParameter.equalsIgnoreCase(PARAMS_OPEN_URL)) {
            doOpenFeatureUrl(uri);
        } else if (PARAMS_DISCLAIMER.equalsIgnoreCase(queryParameter)) {
            doOpenFeatureShowDisclaimerpage(uri);
        } else if (queryParameter.equalsIgnoreCase(PARAMS_SEARCH_MORE)) {
            doOpenFeatureSearchMore(uri);
        } else if (queryParameter.equalsIgnoreCase(PARAMS_POI_SEARCH_FROM_SHENMA)) {
            doOpenFeaturePoiSearchFromShenma(uri);
        } else if (!queryParameter.equalsIgnoreCase(PARAMS_GENERAL_SEARCH_RESULT)) {
            return false;
        } else {
            doOpenFeatureSearchResultFragment(uri);
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x005d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x005e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void showArroundPoi(final android.net.Uri r6) {
        /*
            r5 = this;
            java.lang.String r0 = "keywords"
            java.lang.String r0 = r6.getQueryParameter(r0)
            java.lang.String r1 = "lat"
            java.lang.String r1 = r6.getQueryParameter(r1)
            java.lang.String r2 = "lon"
            java.lang.String r2 = r6.getQueryParameter(r2)
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 != 0) goto L_0x0062
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x0062
            java.lang.Double r1 = java.lang.Double.valueOf(r1)
            double r3 = r1.doubleValue()
            java.lang.Double r1 = java.lang.Double.valueOf(r2)
            double r1 = r1.doubleValue()
            android.graphics.Point r1 = defpackage.cfg.a(r3, r1)
            java.lang.String r2 = "dev"
            java.lang.String r2 = r6.getQueryParameter(r2)
            r3 = 0
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L_0x0044
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ NumberFormatException -> 0x0044 }
            goto L_0x0045
        L_0x0044:
            r2 = 0
        L_0x0045:
            r3 = 1
            if (r2 != r3) goto L_0x0051
            int r2 = r1.x
            int r1 = r1.y
            com.autonavi.common.model.GeoPoint r1 = defpackage.cff.a(r2, r1)
            goto L_0x005b
        L_0x0051:
            com.autonavi.common.model.GeoPoint r2 = new com.autonavi.common.model.GeoPoint
            int r3 = r1.x
            int r1 = r1.y
            r2.<init>(r3, r1)
            r1 = r2
        L_0x005b:
            if (r1 != 0) goto L_0x005e
            return
        L_0x005e:
            r5.showArroundPoiReally(r6, r1, r0)
            return
        L_0x0062:
            java.lang.String r1 = "定位中"
            r5.showUriProgressDialog(r1)
            com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl$4 r1 = new com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl$4
            r1.<init>(r6, r0)
            r6 = 10000(0x2710, float:1.4013E-41)
            defpackage.bib.a(r1, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl.showArroundPoi(android.net.Uri):void");
    }

    public void viewMap(Uri uri) {
        GeoPoint geoPoint;
        PageBundle pageBundle = new PageBundle();
        POI createPOI = POIFactory.createPOI();
        createPOI.setName(uri.getQueryParameter("poiname"));
        String queryParameter = uri.getQueryParameter("lat");
        String queryParameter2 = uri.getQueryParameter("lon");
        int i = 16;
        try {
            int parseInt = Integer.parseInt(uri.getQueryParameter("zoom"));
            if (parseInt >= 3 && parseInt <= 19) {
                i = parseInt;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(queryParameter) && !TextUtils.isEmpty(queryParameter2)) {
            try {
                Point a2 = cfg.a(Double.valueOf(queryParameter).doubleValue(), Double.valueOf(queryParameter2).doubleValue());
                int i2 = 0;
                if (uri.getQueryParameter("dev") != null) {
                    i2 = Integer.parseInt(uri.getQueryParameter("dev"));
                }
                if (i2 == 1) {
                    geoPoint = cff.a(a2.x, a2.y);
                } else {
                    geoPoint = new GeoPoint(a2.x, a2.y);
                }
                createPOI.setPoint(geoPoint);
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
                return;
            }
        }
        createPOI.setId(uri.getQueryParameter("poiid"));
        pageBundle.putObject("POI", createPOI);
        AMapLog.d("ViewMap", "zoomLevel: ".concat(String.valueOf(i)));
        if (DoNotUseTool.getMapView() != null) {
            DoNotUseTool.getMapView().e(i);
        }
        pageBundle.putInt("map_level", i);
        pageBundle.putInt("poi_detail_page_type", 4);
        startPage(PoiDetailPageNew.class, pageBundle);
    }

    public void startGeo(Uri uri) {
        String queryParameter = uri.getQueryParameter("addr");
        if (!TextUtils.isEmpty(queryParameter)) {
            reverse(new Callback<POI>() {
                public void callback(POI poi) {
                    if (poi == null) {
                        Application application = AMapAppGlobal.getApplication();
                        if (application != null) {
                            ToastHelper.showLongToast(application.getString(R.string.ic_net_error_tipinfo));
                        }
                        return;
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("POI", poi);
                    pageBundle.putBoolean("viewMap_scheme", true);
                    pageBundle.putInt("poi_detail_page_type", 4);
                    SearchIntentDispatcherImpl.this.startPage(PoiDetailPageNew.class, pageBundle);
                }

                public void error(Throwable th, boolean z) {
                    Application application = AMapAppGlobal.getApplication();
                    if (application != null) {
                        ToastHelper.showLongToast(application.getString(R.string.ic_net_error_tipinfo));
                    }
                }
            }, queryParameter, null);
        }
    }

    public void startReGeo(Uri uri) {
        GeoPoint geoPoint;
        String queryParameter = uri.getQueryParameter("lat");
        String queryParameter2 = uri.getQueryParameter("lon");
        if (queryParameter != null && queryParameter2 != null) {
            Point a2 = cfg.a(Double.valueOf(queryParameter).doubleValue(), Double.valueOf(queryParameter2).doubleValue());
            if (bby.a(uri.getQueryParameter("dev")) == 1) {
                geoPoint = cff.a(a2.x, a2.y);
            } else {
                geoPoint = new GeoPoint(a2.x, a2.y);
            }
            ReverseGeocodeManager.get(new Callback<POI>() {
                public void callback(POI poi) {
                    if (poi != null) {
                        if (TextUtils.isEmpty(poi.getName())) {
                            poi.setName(AMapAppGlobal.getApplication().getString(R.string.map_specific_location));
                        }
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putObject("POI", poi);
                        pageBundle.putBoolean("viewMap_scheme", true);
                        pageBundle.putInt("poi_detail_page_type", 4);
                        SearchIntentDispatcherImpl.this.startPage(PoiDetailPageNew.class, pageBundle);
                    }
                }

                public void error(Throwable th, boolean z) {
                    Application application = AMapAppGlobal.getApplication();
                    if (application != null) {
                        ToastHelper.showLongToast(application.getString(R.string.ic_net_error_tipinfo));
                    }
                }
            }, geoPoint);
        }
    }

    /* access modifiers changed from: private */
    public void dismissProgressDlg() {
        if (this.mProgressDlg != null) {
            this.mProgressDlg.dismiss();
            this.mProgressDlg = null;
        }
    }

    public void searchPoiID(Uri uri) {
        String queryParameter = uri.getQueryParameter("poiid");
        if (queryParameter != null) {
            ela ela = new ela();
            IDSearchCallBack iDSearchCallBack = new IDSearchCallBack(this);
            InfoRequest infoRequest = new InfoRequest();
            infoRequest.c = queryParameter;
            infoRequest.e = "";
            infoRequest.f = "";
            infoRequest.h = null;
            CompatDialog a2 = aav.a(infoRequest, AMapPageUtil.getPageContext().getContext().getResources().getString(R.string.progress_message));
            a2.show();
            PoiRequestHolder.getInstance().sendInfo(infoRequest, new SearchRequestManagerImpl$1(ela, a2, iDSearchCallBack));
        }
    }

    public boolean startEnvironmentMapPage(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments != null && pathSegments.size() > 0 && !TextUtils.equals("index", pathSegments.get(0))) {
            return false;
        }
        startPage((String) "environment_map_page", new PageBundle());
        return true;
    }

    public boolean startRoundPage(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.size() <= 0 || !TextUtils.equals("main", pathSegments.get(0))) {
            return false;
        }
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        cxx cxx = (cxx) defpackage.esb.a.a.a(cxx.class);
        if (!(cxx == null || iMainMapService == null)) {
            HashMap hashMap = new HashMap();
            Set<String> queryParameterNames = uri.getQueryParameterNames();
            if (queryParameterNames != null && queryParameterNames.size() > 0) {
                for (String next : queryParameterNames) {
                    hashMap.put(next, uri.getQueryParameter(next));
                }
            }
            cxx.a().a(getPageContext(), hashMap);
        }
        return true;
    }

    public boolean startScenicAreaPage(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments != null && pathSegments.size() > 0) {
            String str = pathSegments.get(0);
            if (TextUtils.equals(SCENICAREA_HOME, str)) {
                cca.a(uri, "path://amap_bundle_scenic_area/src/pages/BizScenicMapHomePage.page.js", this);
                return true;
            } else if (TextUtils.equals(SCENICAREA_HOMEPAGE, str)) {
                return compareVersionJumpScenic(uri);
            } else {
                if (TextUtils.equals(SCENICAREA_BUS_DETAIL, str)) {
                    cca.a(uri, "path://amap_bundle_scenic_area/src/pages/BizScenicBusDetailPage.page.js", this);
                    return true;
                } else if (TextUtils.equals(SCENICAREA_SCENIC_LIVE, str)) {
                    cca.a(uri, "path://amap_bundle_scenic_area/src/pages/BizScenicMapLivePage.page.js", this);
                    return true;
                } else if (TextUtils.equals(SCENICAREA_SCENIC_LIST, str)) {
                    cca.a(uri, "path://amap_bundle_scenic_area/src/pages/BizScenicMustPlay.page.js", this);
                    return true;
                } else if (TextUtils.equals(SCENICAREA_TOPIC, str)) {
                    cca.a(uri, "path://amap_bundle_scenic_area/src/pages/BizScenicTopic.page.js", this);
                    return true;
                } else if (TextUtils.equals(SCENICAREA_BANNER_DETAIL, str)) {
                    cca.a(uri, "path://amap_bundle_scenic_area/src/pages/BizScenicBannerDetailPage.page.js", this);
                    return true;
                } else if (TextUtils.equals("map", str)) {
                    startPage((String) "search_scenicarea_eyire", getScenicMapBundle(uri));
                    return true;
                } else if (TextUtils.equals(SMART_SCENICAREA_SET_MAP, str)) {
                    startPage((String) "search_smart_scenicarea_set_page", getSmartScenicBundle(uri));
                    return true;
                } else if (TextUtils.equals(SCENICAREA_MIDDLE_DETAIL, str)) {
                    cca.a(uri, "path://amap_bundle_scenic_area/src/pages/BizScenicMiddleDetailPage.page.js", this);
                    return true;
                } else if (TextUtils.equals(SCENICAREA_TICKETS_LIST, str)) {
                    cca.a(uri, "path://amap_bundle_scenic_area/src/pages/BizScenicTicketDetailPage.page.js", this);
                    return true;
                } else if (TextUtils.equals(SCENICAREA_PARK_DETAIL, str)) {
                    cca.a(uri, "path://amap_bundle_scenic_area/src/pages/BizScenicParkDetailPage.page.js", this);
                    return true;
                } else if (TextUtils.equals("route", str)) {
                    startPage((String) "search_scenicarea_walk_route", getScenicMapBundle(uri));
                    return true;
                } else if (TextUtils.equals(SCENICAREA_WALKMAN_MAP, str)) {
                    startPage((String) "search_scenicarea_walkman_map", getScenicMapBundle(uri));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean compareVersionJumpScenic(Uri uri) {
        String substring = NetworkParam.getDiv().substring(4);
        String queryParameter = uri.getQueryParameter("support_version");
        if ((TextUtils.isEmpty(substring) || TextUtils.isEmpty(queryParameter)) ? false : compareVersion(substring, queryParameter)) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.intent_not_support_and_update));
            return true;
        }
        cca.a(uri, "path://amap_bundle_scenic_area/src/pages/BizScenicMapHomePage.page.js", this);
        return true;
    }

    private boolean compareVersion(String str, String str2) {
        if (str.equals(str2)) {
            return false;
        }
        int length = str.length();
        int length2 = str2.length();
        if (length2 > length) {
            return true;
        }
        if (length2 < length) {
            return false;
        }
        char[] charArray = str2.toCharArray();
        char[] charArray2 = str.toCharArray();
        for (int i = 0; i < length2; i++) {
            char c = charArray[i];
            char c2 = charArray2[i];
            if (c > c2) {
                return true;
            }
            if (c < c2) {
                return false;
            }
        }
        return false;
    }

    private PageBundle getScenicMapBundle(Uri uri) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("jsData", cca.a(uri).toString());
        return pageBundle;
    }

    private PageBundle getSmartScenicBundle(Uri uri) {
        String queryParameter = uri.getQueryParameter("lat");
        String queryParameter2 = uri.getQueryParameter("lon");
        String queryParameter3 = uri.getQueryParameter("zoom");
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(queryParameter2) && !TextUtils.isEmpty(queryParameter)) {
            pageBundle.putString("lon", queryParameter2);
            pageBundle.putString("lat", queryParameter);
        }
        if (!TextUtils.isEmpty(queryParameter3)) {
            pageBundle.putString("key_map_level", queryParameter3);
        }
        return pageBundle;
    }

    public boolean doPoiScheme(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments != null && pathSegments.size() > 0 && TextUtils.equals("comment", pathSegments.get(0))) {
            return false;
        }
        String queryParameter = uri.getQueryParameter("keywords");
        String queryParameter2 = uri.getQueryParameter("lat1");
        String queryParameter3 = uri.getQueryParameter("lon1");
        String queryParameter4 = uri.getQueryParameter("lat2");
        String queryParameter5 = uri.getQueryParameter("lon2");
        String queryParameter6 = uri.getQueryParameter("dev");
        String queryParameter7 = uri.getQueryParameter(RpcConstant.SHOW_TYPE);
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(queryParameter2) && !TextUtils.isEmpty(queryParameter3) && !TextUtils.isEmpty(queryParameter4) && !TextUtils.isEmpty(queryParameter5)) {
            Point a2 = cfg.a(bby.b(queryParameter2), bby.b(queryParameter3));
            Point a3 = cfg.a(bby.b(queryParameter4), bby.b(queryParameter5));
            Rect rect = new Rect(a2.x, a2.y, a3.x, a3.y);
            if (bby.a(queryParameter6) == 1) {
                GeoPoint a4 = cff.a(a2.x, a2.y);
                GeoPoint a5 = cff.a(a2.x, a2.y);
                rect = new Rect(a4.x, a4.y, a5.x, a5.y);
            }
            pageBundle.putObject("searchRect", rect);
        }
        pageBundle.putObject("searchFor", SearchFor.SCHEME_POI);
        pageBundle.putString("keyword", queryParameter);
        int i = -1;
        if (queryParameter7 != null && !queryParameter7.equals("")) {
            try {
                i = bby.a(queryParameter7);
            } catch (NumberFormatException unused) {
            }
        }
        pageBundle.putInt(RpcConstant.SHOW_TYPE, i);
        dlg backSchemeHole = getBackSchemeHole(uri);
        if (backSchemeHole != null) {
            pageBundle.putObject(Constants.KEY_ACTION, "actiono_back_scheme");
            pageBundle.putObject("key_back_scheme_param", backSchemeHole);
        }
        startPage((String) "amap.search.action.searchfragment", pageBundle);
        return true;
    }

    public void doOpenFeatureUrl(final Uri uri) {
        String queryParameter = uri.getQueryParameter("url");
        if (TextUtils.isEmpty(queryParameter)) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.intent_open_web_fail));
            return;
        }
        if (!ajn.a(queryParameter)) {
            queryParameter = ajn.b(queryParameter);
        }
        if ("1".equals(uri.getQueryParameter("urlType"))) {
            ((bgx) defpackage.esb.a.a.a(bgx.class)).getUrl(queryParameter, new defpackage.bgx.a() {
                public final void a(final String str) {
                    aho.a(new Runnable() {
                        public final void run() {
                            SearchIntentDispatcherImpl.this.startFragmentWithUrl(uri, str);
                        }
                    });
                }
            });
            return;
        }
        if (!ajn.a(queryParameter)) {
            queryParameter = ajn.b(queryParameter);
        }
        startFragmentWithUrl(uri, queryParameter);
    }

    /* access modifiers changed from: private */
    public void startFragmentWithUrl(Uri uri, String str) {
        String queryParameter = uri.getQueryParameter("contentType");
        String queryParameter2 = uri.getQueryParameter("urlType");
        String decode = Uri.decode(str);
        if (!decode.contains("amap-redirect") || ajn.a(Uri.parse(decode).getQueryParameter("target"))) {
            String queryParameter3 = uri.getQueryParameter("url");
            if (!"autonavi".equals(queryParameter) || !"1".equals(queryParameter2)) {
                startWebViewWithUrl(uri, str, false, false);
                return;
            }
            if (!WZCX_SCHEMA.equals(queryParameter3) || isLogin()) {
                startWebViewWithUrl(uri, str, false);
            } else if (!isLogin()) {
                DoNotUseTool.startScheme(new Intent("android.intent.action.VIEW", Uri.parse("amapuri://carownerservice/addcar?from=0&isLogin=0")));
                return;
            }
            return;
        }
        startWebViewWithUrl(uri, str, true, false);
    }

    private void startWebViewWithUrl(Uri uri, String str, boolean z) {
        startWebViewWithUrl(uri, str, z, true);
    }

    private void startWebViewWithUrl(Uri uri, String str, boolean z, boolean z2) {
        aja aja = new aja(str);
        PageBundle pageBundle = new PageBundle();
        Uri parse = !TextUtils.isEmpty(str) ? Uri.parse(str) : null;
        boolean z3 = parse != null && "1".equals(parse.getQueryParameter(PARAMS_NATIVE_LOADING));
        final boolean z4 = z;
        final boolean z5 = z2;
        AnonymousClass10 r3 = new cax(uri, str) {
            public final boolean i() {
                return z4;
            }

            public final boolean f() {
                return z5;
            }
        };
        aja.b = r3;
        if (getPageContext() != null) {
            if (z3) {
                getPageContext().startPage((String) "amap.basemap.action.activity_preload_page", pageBundle);
                return;
            }
            aix aix = (aix) defpackage.esb.a.a.a(aix.class);
            if (aix != null) {
                aix.a(getPageContext(), aja);
            }
        }
    }

    public void doOpenFeatureGeneralSearch() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putBoolean("noSearchRect", true);
        startPage((String) "amap.search.action.searchfragment", pageBundle);
    }

    public void openFeatureCarBrand(Uri uri) {
        String queryParameter = uri.getQueryParameter("url");
        if (!TextUtils.isEmpty(queryParameter) && !ajn.a(queryParameter)) {
            queryParameter = ajn.b(queryParameter);
        }
        final String queryParameter2 = uri.getQueryParameter("title");
        if (!TextUtils.isEmpty(queryParameter2)) {
            try {
                queryParameter2 = URLDecoder.decode(queryParameter2, "UTF-8");
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
        if (!TextUtils.isEmpty(queryParameter)) {
            try {
                queryParameter = URLDecoder.decode(queryParameter, "UTF-8");
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
        new PageBundle();
        aja aja = new aja(queryParameter);
        aja.b = new cav() {
            public final String b() {
                return queryParameter2;
            }

            public final defpackage.ajh.b c() {
                return new defpackage.ajh.b() {
                    public final String b() {
                        return null;
                    }

                    public final long c() {
                        return 1000;
                    }

                    public final boolean a() {
                        return (aaw.b(AMapAppGlobal.getApplication()) == 4 || aaw.b(AMapAppGlobal.getApplication()) == 2 || aaw.b(AMapAppGlobal.getApplication()) == 3) ? false : true;
                    }
                };
            }
        };
        aix aix = (aix) defpackage.esb.a.a.a(aix.class);
        if (aix != null) {
            aix.a(getPageContext(), aja);
        }
    }

    public void doOpenFeatureSearchMore(Uri uri) {
        processSuperId(uri);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_search_around/src/pages/AllCategoryPage.page.js");
        startPage(Ajx3Page.class, pageBundle);
    }

    /* access modifiers changed from: private */
    public boolean processSuperId(Uri uri) {
        SuperId.getInstance().reset();
        String queryParameter = uri.getQueryParameter(SEARCH_SCHEMA_SUPER_ID);
        if (!TextUtils.isEmpty(queryParameter)) {
            String[] split = queryParameter.split("_");
            if (split.length >= 2 && split.length <= 4) {
                try {
                    if (!TextUtils.isEmpty(split[0])) {
                        SuperId.getInstance().setBit1(split[0]);
                    }
                    if (!TextUtils.isEmpty(split[1])) {
                        SuperId.getInstance().setBit2(split[1]);
                    }
                    if (!TextUtils.isEmpty(split[2])) {
                        SuperId.getInstance().setBit3(split[2]);
                    }
                    if (TextUtils.isEmpty(split[3])) {
                        return true;
                    }
                    SuperId.getInstance().setBit4(split[3]);
                    return true;
                } catch (Exception unused) {
                    return true;
                }
            }
        }
        return false;
    }

    public void doOpenFeaturePoiSearchFromShenma(Uri uri) {
        String queryParameter = uri.getQueryParameter("keywords");
        String queryParameter2 = uri.getQueryParameter("lat1");
        String queryParameter3 = uri.getQueryParameter("lon1");
        String queryParameter4 = uri.getQueryParameter("lat2");
        String queryParameter5 = uri.getQueryParameter("lon2");
        String queryParameter6 = uri.getQueryParameter("dev");
        String queryParameter7 = uri.getQueryParameter("transfer_mode");
        String queryParameter8 = uri.getQueryParameter("sc_stype");
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(queryParameter2) && !TextUtils.isEmpty(queryParameter3) && !TextUtils.isEmpty(queryParameter4) && !TextUtils.isEmpty(queryParameter5)) {
            Point a2 = cfg.a(bby.b(queryParameter2), bby.b(queryParameter3));
            Point a3 = cfg.a(bby.b(queryParameter4), bby.b(queryParameter5));
            Rect rect = new Rect(a2.x, a2.y, a3.x, a3.y);
            if (bby.a(queryParameter6) == 1) {
                GeoPoint a4 = cff.a(a2.x, a2.y);
                GeoPoint a5 = cff.a(a2.x, a2.y);
                rect = new Rect(a4.x, a4.y, a5.x, a5.y);
            }
            pageBundle.putObject("searchRect", rect);
        }
        pageBundle.putObject("searchFor", SearchFor.SCHEME_POI);
        pageBundle.putString("keyword", queryParameter);
        if (TextUtils.isEmpty(queryParameter7)) {
            queryParameter7 = "map";
        }
        if (TextUtils.isEmpty(queryParameter8)) {
            queryParameter8 = "food_searcher";
        }
        pageBundle.putString("transfer_mode", queryParameter7);
        pageBundle.putString("sc_stype", queryParameter8);
        startPage((String) "amap.search.action.searchfragment", pageBundle);
    }

    public void doOpenFeatureShowDisclaimerpage(final Uri uri) {
        this.mActivity.runOnUiThread(new Runnable() {
            public final void run() {
                try {
                    if (!NetworkReachability.b()) {
                        ToastHelper.showLongToast(SearchIntentDispatcherImpl.this.mActivity.getString(R.string.network_error_message));
                        return;
                    }
                    SharedPreferences sharedPreferences = SearchIntentDispatcherImpl.this.mActivity.getSharedPreferences("category_save_v2", 0);
                    String queryParameter = uri.getQueryParameter("announce");
                    String queryParameter2 = uri.getQueryParameter("url");
                    if (!TextUtils.isEmpty(queryParameter2) && !ajn.a(queryParameter2)) {
                        queryParameter2 = ajn.b(queryParameter2);
                    }
                    final String queryParameter3 = uri.getQueryParameter("website_name");
                    if (!sharedPreferences.getBoolean(queryParameter, false)) {
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putString("license_url", queryParameter);
                        pageBundle.putString("url", queryParameter2);
                        pageBundle.putString("website_name", queryParameter3);
                        SearchIntentDispatcherImpl.this.getPageContext().startPage((String) "amap.basemap.action.licenseconfirm_page", pageBundle);
                        return;
                    }
                    aja aja = new aja(queryParameter2);
                    aja.b = new cbf() {
                        public final boolean d() {
                            return false;
                        }

                        public final String b() {
                            return queryParameter3;
                        }

                        public final defpackage.ajh.b c() {
                            return new defpackage.cbf.a() {
                                public final String b() {
                                    return queryParameter3;
                                }
                            };
                        }
                    };
                    aix aix = (aix) defpackage.esb.a.a.a(aix.class);
                    if (aix != null) {
                        aix.a(SearchIntentDispatcherImpl.this.getPageContext(), aja);
                    }
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void showArroundPoiReally(Uri uri, GeoPoint geoPoint, String str) {
        bid pageContext = getPageContext();
        if (pageContext != null) {
            if (getBackSchemeHole(uri) != null) {
                apr apr = (apr) defpackage.esb.a.a.a(apr.class);
                if (apr != null) {
                    apr.b(pageContext);
                }
            }
            bty mapView = DoNotUseTool.getMapView();
            if (mapView != null) {
                mapView.a(geoPoint.x, geoPoint.y);
            }
            if (!TextUtils.isEmpty(str)) {
                processSuperId(uri);
                Rect rect = new Rect(geoPoint.x - 100, geoPoint.y - 100, geoPoint.x + 100, geoPoint.y + 100);
                InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), str, geoPoint, rect);
                a2.schema_source = uri.getQueryParameter(SEARCH_SCHEMA_SOURCE);
                ekv ekv = new ekv();
                bvt bvt = new bvt();
                bvt.e = str;
                bwx bwx = new bwx(str, 2, false);
                String queryParameter = uri.getQueryParameter("name");
                if (TextUtils.isEmpty(queryParameter)) {
                    queryParameter = "";
                }
                bwx.r = POIFactory.createPOI(queryParameter, geoPoint);
                bvt.d = bwx;
                bvt.a(rect);
                aey.a(false);
                ekv.a(a2, 1, bvt);
            }
        }
    }

    private void doOpenFeatureSearchResultFragment(Uri uri) {
        String queryParameter = uri.getQueryParameter("keyword");
        String queryParameter2 = uri.getQueryParameter("lon1");
        String queryParameter3 = uri.getQueryParameter("lat1");
        String queryParameter4 = uri.getQueryParameter("lon2");
        String queryParameter5 = uri.getQueryParameter("lat2");
        if (!TextUtils.isEmpty(queryParameter)) {
            Rect rect = null;
            if (!TextUtils.isEmpty(queryParameter2) && !TextUtils.isEmpty(queryParameter3) && !TextUtils.isEmpty(queryParameter4) && !TextUtils.isEmpty(queryParameter5)) {
                try {
                    double parseDouble = Double.parseDouble(queryParameter2);
                    double parseDouble2 = Double.parseDouble(queryParameter3);
                    double parseDouble3 = Double.parseDouble(queryParameter4);
                    double parseDouble4 = Double.parseDouble(queryParameter5);
                    if (parseDouble > 0.0d && parseDouble2 > 0.0d && parseDouble3 > 0.0d && parseDouble4 > 0.0d) {
                        Point a2 = cfg.a(parseDouble2, parseDouble);
                        Point a3 = cfg.a(parseDouble4, parseDouble3);
                        rect = new Rect(a2.x, a2.y, a3.x, a3.y);
                    }
                } catch (NumberFormatException unused) {
                }
            }
            if (rect == null) {
                rect = DoNotUseTool.getPixel20Bound();
            }
            if (rect != null) {
                InfoliteParam a4 = bbv.a(AppManager.getInstance().getUserLocInfo(), queryParameter, rect);
                a4.search_operate = 1;
                ekv ekv = new ekv();
                bvt bvt = new bvt();
                bvt.d = new bwx(queryParameter, -1, false);
                bvt.e = queryParameter;
                ekv.a(a4, 1, bvt);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x0162  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0175 A[SYNTHETIC, Splitter:B:55:0x0175] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x019d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void viewMapNew(android.net.Uri r19) {
        /*
            r18 = this;
            r1 = r19
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle
            r2.<init>()
            com.autonavi.common.model.POI r3 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            java.lang.String r4 = "poiname"
            java.lang.String r4 = r1.getQueryParameter(r4)
            r3.setName(r4)
            java.lang.String r4 = "lat"
            java.lang.String r4 = r1.getQueryParameter(r4)
            java.lang.String r5 = "lon"
            java.lang.String r5 = r1.getQueryParameter(r5)
            java.lang.String r6 = "centerLon"
            java.lang.String r6 = r1.getQueryParameter(r6)
            java.lang.String r7 = "centerLat"
            java.lang.String r7 = r1.getQueryParameter(r7)
            java.lang.String r8 = "zoomlevel"
            java.lang.String r8 = r1.getQueryParameter(r8)
            java.lang.String r9 = "view_region"
            java.lang.String r9 = r1.getQueryParameter(r9)
            java.lang.String r10 = "superid"
            java.lang.String r10 = r1.getQueryParameter(r10)
            r11 = 4
            r12 = 1
            r13 = 0
            boolean r15 = android.text.TextUtils.isEmpty(r8)     // Catch:{ NumberFormatException -> 0x00c8 }
            r14 = 3
            if (r15 != 0) goto L_0x005d
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ NumberFormatException -> 0x00c8 }
            if (r8 < r14) goto L_0x0058
            r9 = 19
            if (r8 > r9) goto L_0x0058
            r16 = r8
            goto L_0x005a
        L_0x0058:
            r16 = 16
        L_0x005a:
            r14 = r16
            goto L_0x00b7
        L_0x005d:
            boolean r8 = android.text.TextUtils.isEmpty(r9)     // Catch:{ NumberFormatException -> 0x00c8 }
            if (r8 != 0) goto L_0x00b5
            r8 = 44
            int r8 = r9.indexOf(r8)     // Catch:{ NumberFormatException -> 0x00c8 }
            if (r8 <= 0) goto L_0x00b5
            java.lang.String r8 = ","
            java.lang.String[] r8 = r9.split(r8)     // Catch:{ NumberFormatException -> 0x00c8 }
            if (r8 == 0) goto L_0x00b5
            int r9 = r8.length     // Catch:{ NumberFormatException -> 0x00c8 }
            if (r9 != r11) goto L_0x00b5
            com.autonavi.common.model.GeoPoint r9 = new com.autonavi.common.model.GeoPoint     // Catch:{ NumberFormatException -> 0x00c8 }
            r15 = r8[r13]     // Catch:{ NumberFormatException -> 0x00c8 }
            double r13 = java.lang.Double.parseDouble(r15)     // Catch:{ NumberFormatException -> 0x00c8 }
            r15 = r8[r12]     // Catch:{ NumberFormatException -> 0x00c8 }
            double r11 = java.lang.Double.parseDouble(r15)     // Catch:{ NumberFormatException -> 0x00c8 }
            r9.<init>(r13, r11)     // Catch:{ NumberFormatException -> 0x00c8 }
            com.autonavi.common.model.GeoPoint r11 = new com.autonavi.common.model.GeoPoint     // Catch:{ NumberFormatException -> 0x00c8 }
            r12 = 2
            r12 = r8[r12]     // Catch:{ NumberFormatException -> 0x00c8 }
            double r12 = java.lang.Double.parseDouble(r12)     // Catch:{ NumberFormatException -> 0x00c8 }
            r14 = 3
            r8 = r8[r14]     // Catch:{ NumberFormatException -> 0x00c8 }
            double r14 = java.lang.Double.parseDouble(r8)     // Catch:{ NumberFormatException -> 0x00c8 }
            r11.<init>(r12, r14)     // Catch:{ NumberFormatException -> 0x00c8 }
            com.autonavi.map.core.MapManager r8 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapManager()     // Catch:{ NumberFormatException -> 0x00c8 }
            bty r8 = r8.getMapView()     // Catch:{ NumberFormatException -> 0x00c8 }
            int r12 = r9.x     // Catch:{ NumberFormatException -> 0x00c8 }
            int r9 = r9.y     // Catch:{ NumberFormatException -> 0x00c8 }
            int r13 = r11.x     // Catch:{ NumberFormatException -> 0x00c8 }
            int r11 = r11.y     // Catch:{ NumberFormatException -> 0x00c8 }
            float r8 = r8.a(r12, r9, r13, r11)     // Catch:{ NumberFormatException -> 0x00c8 }
            r9 = 1058642330(0x3f19999a, float:0.6)
            float r8 = r8 - r9
            int r8 = (int) r8
            r14 = r8
            goto L_0x00b7
        L_0x00b5:
            r14 = 16
        L_0x00b7:
            com.amap.bundle.mapstorage.MapSharePreference r8 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ NumberFormatException -> 0x00c5 }
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r9 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ NumberFormatException -> 0x00c5 }
            r8.<init>(r9)     // Catch:{ NumberFormatException -> 0x00c5 }
            java.lang.String r9 = "PRESISE_ZOOM_LEVEL"
            float r11 = (float) r14     // Catch:{ NumberFormatException -> 0x00c5 }
            r8.putFloatValue(r9, r11)     // Catch:{ NumberFormatException -> 0x00c5 }
            goto L_0x00cf
        L_0x00c5:
            r0 = move-exception
            r8 = r0
            goto L_0x00cc
        L_0x00c8:
            r0 = move-exception
            r8 = r0
            r14 = 16
        L_0x00cc:
            r8.printStackTrace()
        L_0x00cf:
            boolean r8 = android.text.TextUtils.isEmpty(r4)
            if (r8 != 0) goto L_0x011f
            boolean r8 = android.text.TextUtils.isEmpty(r5)
            if (r8 != 0) goto L_0x011f
            java.lang.Double r4 = java.lang.Double.valueOf(r4)     // Catch:{ NumberFormatException -> 0x011e }
            double r8 = r4.doubleValue()     // Catch:{ NumberFormatException -> 0x011e }
            java.lang.Double r4 = java.lang.Double.valueOf(r5)     // Catch:{ NumberFormatException -> 0x011e }
            double r4 = r4.doubleValue()     // Catch:{ NumberFormatException -> 0x011e }
            android.graphics.Point r4 = defpackage.cfg.a(r8, r4)     // Catch:{ NumberFormatException -> 0x011e }
            java.lang.String r5 = "dev"
            java.lang.String r5 = r1.getQueryParameter(r5)     // Catch:{ NumberFormatException -> 0x011e }
            if (r5 == 0) goto L_0x0103
            java.lang.String r5 = "dev"
            java.lang.String r5 = r1.getQueryParameter(r5)     // Catch:{ NumberFormatException -> 0x011e }
            int r13 = java.lang.Integer.parseInt(r5)     // Catch:{ NumberFormatException -> 0x011e }
            r5 = 1
            goto L_0x0105
        L_0x0103:
            r5 = 1
            r13 = 0
        L_0x0105:
            if (r13 != r5) goto L_0x0110
            int r8 = r4.x     // Catch:{ NumberFormatException -> 0x011e }
            int r4 = r4.y     // Catch:{ NumberFormatException -> 0x011e }
            com.autonavi.common.model.GeoPoint r4 = defpackage.cff.a(r8, r4)     // Catch:{ NumberFormatException -> 0x011e }
            goto L_0x011a
        L_0x0110:
            com.autonavi.common.model.GeoPoint r8 = new com.autonavi.common.model.GeoPoint     // Catch:{ NumberFormatException -> 0x011e }
            int r9 = r4.x     // Catch:{ NumberFormatException -> 0x011e }
            int r4 = r4.y     // Catch:{ NumberFormatException -> 0x011e }
            r8.<init>(r9, r4)     // Catch:{ NumberFormatException -> 0x011e }
            r4 = r8
        L_0x011a:
            r3.setPoint(r4)     // Catch:{ NumberFormatException -> 0x011e }
            goto L_0x0120
        L_0x011e:
            return
        L_0x011f:
            r5 = 1
        L_0x0120:
            r18.processSuperId(r19)
            r4 = 0
            boolean r8 = android.text.TextUtils.isEmpty(r7)
            if (r8 != 0) goto L_0x014e
            boolean r8 = android.text.TextUtils.isEmpty(r6)
            if (r8 != 0) goto L_0x014e
            java.lang.Double r4 = java.lang.Double.valueOf(r7)
            double r7 = r4.doubleValue()
            java.lang.Double r4 = java.lang.Double.valueOf(r6)
            double r11 = r4.doubleValue()
            android.graphics.Point r4 = defpackage.cfg.a(r7, r11)
            com.autonavi.common.model.GeoPoint r6 = new com.autonavi.common.model.GeoPoint
            int r7 = r4.x
            int r4 = r4.y
            r6.<init>(r7, r4)
            r4 = r6
        L_0x014e:
            java.lang.String r6 = "poiid"
            java.lang.String r6 = r1.getQueryParameter(r6)
            r3.setId(r6)
            java.lang.String r6 = "POI"
            r2.putObject(r6, r3)
            bty r3 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapView()
            if (r3 == 0) goto L_0x0169
            bty r3 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapView()
            r3.e(r14)
        L_0x0169:
            java.lang.String r3 = "external"
            java.lang.String r1 = r1.getQueryParameter(r3)
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 != 0) goto L_0x0181
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x017d }
            if (r1 != 0) goto L_0x0181
            r13 = 1
            goto L_0x0182
        L_0x017d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0181:
            r13 = 0
        L_0x0182:
            java.lang.String r1 = "map_level"
            r2.putInt(r1, r14)
            java.lang.String r1 = "is_external"
            r2.putBoolean(r1, r13)
            java.lang.String r1 = "poi_detail_page_type"
            r3 = 4
            r2.putInt(r1, r3)
            java.lang.String r1 = "key_tip_schema_center_point"
            r2.putObject(r1, r4)
            boolean r1 = android.text.TextUtils.isEmpty(r10)
            if (r1 != 0) goto L_0x01a2
            java.lang.String r1 = "key_superid"
            r2.putString(r1, r10)
        L_0x01a2:
            java.lang.Class<com.autonavi.map.search.fragment.PoiDetailPageNew> r1 = com.autonavi.map.search.fragment.PoiDetailPageNew.class
            r3 = r18
            r3.startPage(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl.viewMapNew(android.net.Uri):void");
    }

    public void openIdqSearchFragment(Uri uri) {
        Uri uri2 = uri;
        String queryParameter = uri2.getQueryParameter("poiid");
        if (!TextUtils.isEmpty(queryParameter)) {
            int a2 = bby.a(uri2.getQueryParameter(NEW_IDQ_SEARCH_KEY_FROMPAGE));
            String queryParameter2 = uri2.getQueryParameter("action");
            int a3 = bby.a(uri2.getQueryParameter(NEW_IDQ_SEARCH_KEY_ZOOMLEVEL));
            String queryParameter3 = uri2.getQueryParameter(NEW_IDQ_SEARCH_KEY_FUNTTYPE);
            String queryParameter4 = uri2.getQueryParameter("poiname");
            String queryParameter5 = uri2.getQueryParameter(H5Param.LONG_TRANSPARENT);
            int i = TextUtils.equals(PARAMS_FROM_SEARCH_INNER_PAGE_HOME, uri2.getQueryParameter(PARAMS_FROM_SEARCH_INNER_PAGE_KEY)) ? 0 : -1;
            String queryParameter6 = uri2.getQueryParameter(SEARCH_SCHEMA_NEED_HISTORY);
            boolean equals = !TextUtils.isEmpty(queryParameter6) ? "1".equals(queryParameter6) : false;
            boolean processSuperId = processSuperId(uri);
            a aVar = new a(queryParameter3, queryParameter4, a2, i, queryParameter2, a3, equals);
            aVar.g = equals;
            ekv ekv = new ekv();
            InfoliteParam a4 = bbv.a(AppManager.getInstance().getUserLocInfo(), queryParameter4, queryParameter);
            if (!TextUtils.isEmpty(queryParameter5)) {
                a4.transparent = queryParameter5;
            }
            if (processSuperId) {
                a4.superid = SuperId.getInstance().getScenceId();
            } else {
                a4.superid = "0_00_00";
            }
            String queryParameter7 = uri2.getQueryParameter("city");
            if (!TextUtils.isEmpty(queryParameter7)) {
                a4.city = queryParameter7;
            }
            ekv.a(a4, 1, aVar);
        }
    }

    public void openSearchFragment(Uri uri) {
        String queryParameter = uri.getQueryParameter("keyword");
        if (!TextUtils.isEmpty(queryParameter)) {
            String queryParameter2 = uri.getQueryParameter(H5Param.LONG_TRANSPARENT);
            String queryParameter3 = uri.getQueryParameter(SEARCH_SCHEMA_SOURCE);
            String queryParameter4 = uri.getQueryParameter(SEARCH_SCHEMA_NEED_HISTORY);
            boolean equals = !TextUtils.isEmpty(queryParameter4) ? "1".equals(queryParameter4) : true;
            processSuperId(uri);
            String queryParameter5 = uri.getQueryParameter("lat1");
            String queryParameter6 = uri.getQueryParameter("lon1");
            String queryParameter7 = uri.getQueryParameter("lat2");
            String queryParameter8 = uri.getQueryParameter("lon2");
            String queryParameter9 = uri.getQueryParameter("dev");
            Rect rect = null;
            if (!TextUtils.isEmpty(queryParameter5) && !TextUtils.isEmpty(queryParameter6) && !TextUtils.isEmpty(queryParameter7) && !TextUtils.isEmpty(queryParameter8)) {
                Point a2 = cfg.a(Double.parseDouble(queryParameter5), Double.parseDouble(queryParameter6));
                Point a3 = cfg.a(Double.parseDouble(queryParameter7), Double.parseDouble(queryParameter8));
                rect = new Rect(a2.x, a2.y, a3.x, a3.y);
                try {
                    if (Integer.parseInt(queryParameter9) == 1) {
                        GeoPoint a4 = cff.a(a2.x, a2.y);
                        GeoPoint a5 = cff.a(a2.x, a2.y);
                        rect = new Rect(a4.x, a4.y, a5.x, a5.y);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (rect == null) {
                rect = DoNotUseTool.getPixel20Bound();
            }
            if (rect != null) {
                int i = -1;
                if (TextUtils.equals(PARAMS_FROM_SEARCH_INNER_PAGE_HOME, uri.getQueryParameter(PARAMS_FROM_SEARCH_INNER_PAGE_KEY))) {
                    i = 0;
                }
                InfoliteParam a6 = bbv.a(AppManager.getInstance().getUserLocInfo(), queryParameter, rect);
                a6.search_operate = 1;
                a6.transparent = queryParameter2;
                a6.schema_source = queryParameter3;
                a6.superid = SuperId.getInstance().getScenceId();
                String queryParameter10 = uri.getQueryParameter("city");
                if (!TextUtils.isEmpty(queryParameter10)) {
                    a6.city = queryParameter10;
                }
                ekv ekv = new ekv();
                bvt bvt = new bvt();
                bvt.d = new bwx(queryParameter, i, false);
                bvt.e = queryParameter;
                bvt.g = equals;
                aey.a(false);
                ekv.a(a6, 1, bvt);
            }
        }
    }

    private void openMineCommentsFragment(Uri uri) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("state", uri.getQueryParameter("state"));
        startPage((String) "comment_list_page", pageBundle);
    }

    private void openCenterAroundFragment(Uri uri) {
        String queryParameter = uri.getQueryParameter("poiname");
        String queryParameter2 = uri.getQueryParameter("lat");
        String queryParameter3 = uri.getQueryParameter("lon");
        if (!TextUtils.isEmpty(queryParameter) && !TextUtils.isEmpty(queryParameter2) && !TextUtils.isEmpty(queryParameter3)) {
            try {
                GeoPoint geoPoint = new GeoPoint(Double.parseDouble(queryParameter3), Double.parseDouble(queryParameter2));
                String queryParameter4 = uri.getQueryParameter(H5Param.LONG_TRANSPARENT);
                POI createPOI = POIFactory.createPOI(queryParameter, geoPoint);
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("POI", createPOI);
                pageBundle.putString("feed_transparent", queryParameter4);
                startPage((String) "amap.search.action.category", pageBundle);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void searchCenterAround(Uri uri) {
        String queryParameter = uri.getQueryParameter("keywords");
        String queryParameter2 = uri.getQueryParameter("lat");
        String queryParameter3 = uri.getQueryParameter("lon");
        if (!TextUtils.isEmpty(queryParameter2) && !TextUtils.isEmpty(queryParameter3)) {
            try {
                showArroundPoiReally(uri, new GeoPoint(Double.parseDouble(queryParameter3), Double.parseDouble(queryParameter2)), queryParameter);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void doOpenFeatureShowPoiDetailNew(final Uri uri) {
        this.mActivity.runOnUiThread(new Runnable() {
            public final void run() {
                try {
                    POI createPOI = POIFactory.createPOI();
                    String queryParameter = uri.getQueryParameter("lon");
                    String queryParameter2 = uri.getQueryParameter("lat");
                    String queryParameter3 = uri.getQueryParameter("centerLon");
                    String queryParameter4 = uri.getQueryParameter("centerLat");
                    String decode = URLDecoder.decode(uri.getQueryParameter("poiname"), "UTF-8");
                    String queryParameter5 = uri.getQueryParameter("ranklistmore");
                    if (!TextUtils.isEmpty(queryParameter) && !TextUtils.isEmpty(queryParameter2)) {
                        try {
                            Point a2 = cfg.a(Double.valueOf(queryParameter2).doubleValue(), Double.valueOf(queryParameter).doubleValue());
                            createPOI.setPoint(new GeoPoint(a2.x, a2.y));
                            createPOI.setName(decode);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    createPOI.setId(uri.getQueryParameter("poiid"));
                    createPOI.setAddr(uri.getQueryParameter("address"));
                    Object obj = null;
                    if (!TextUtils.isEmpty(queryParameter4) && !TextUtils.isEmpty(queryParameter3)) {
                        Point a3 = cfg.a(Double.valueOf(queryParameter4).doubleValue(), Double.valueOf(queryParameter3).doubleValue());
                        obj = new GeoPoint(a3.x, a3.y);
                    }
                    SearchIntentDispatcherImpl.this.processSuperId(uri);
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putSerializable("POI", createPOI);
                    pageBundle.putInt("from_id", 1);
                    pageBundle.putString(H5Param.LONG_TRANSPARENT, uri.getQueryParameter(H5Param.LONG_TRANSPARENT));
                    pageBundle.putInt("poi_detail_page_type", 1);
                    pageBundle.putObject("key_tip_schema_center_point", obj);
                    if (!TextUtils.isEmpty(queryParameter5)) {
                        pageBundle.putString("rank_list_more", queryParameter5);
                    }
                    bid pageContext = AMapPageUtil.getPageContext();
                    if (pageContext instanceof SearchResultMapPage) {
                        pageBundle.putLong("key_search_process_key", ((SearchResultMapPage) pageContext).a());
                    }
                    if (pageContext != null) {
                        pageContext.startPage((String) "amap.search.action.poidetail", pageBundle);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private boolean isLogin() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        return iAccountService != null && iAccountService.a();
    }
}
