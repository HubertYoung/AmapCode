package com.autonavi.bundle.busnavi.ajx;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.entity.BusPaths;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3DialogPage;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.route.ajx.module.bus.IBusNaviDetailModuleBridge;
import com.autonavi.minimap.route.ajx.module.bus.IBusNaviDetailModuleBridge.Callback;
import com.autonavi.minimap.route.bus.localbus.RouteBusResultData;
import com.autonavi.minimap.route.bus.realtimebus.model.BusEndPointData;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil.OperationType;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import com.uc.webview.export.internal.SDKFactory;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("route_bus")
public class ModuleBus extends AbstractModule {
    public static final String BUS_CARD_PAGE = "bus_card_page";
    public static final String BUS_POPUP_PATH = "path://amap_bundle_basemap_route/src/BizRPBusPopUpView.page.js";
    public static final String MODULE_NAME = "route_bus";
    private static final String SHOW_TAXI = "showtaxi";
    public static final String START_PAGE_ACCESS_FOOT_RIDE_NAVI = "detail_access_foot_ride_navi";
    public static final String START_PAGE_ACCESS_REROUTE = "detail_access_reroute";
    public static final String START_PAGE_ACCESS_UGC = "detail_access_ugc";
    private static final String START_PAGE_AGROUP = "agroup";
    private static final String START_PAGE_BUSLINE = "busline";
    public static final String START_PAGE_DETAIL_FEEDBACK = "detail_feedback";
    public static final String START_PAGE_DETAIL_SAHRE = "detail_share";
    private static final String START_PAGE_FOLLOW = "follow";
    private static final String START_PAGE_NEARBY = "nearby";
    private static final String START_PAGE_NOTICE = "notice";
    private static final String START_PAGE_REALTIME = "realtime";
    private static final String START_PAGE_SHAREBIKE = "sharebike";
    private static final String START_PAGE_SUBWAY = "subway";
    private static final String START_PAGE_TOPUP = "topup";
    private static final String START_PAGE_VIPBUS = "vipbus";
    /* access modifiers changed from: private */
    public IBusNaviDetailModuleBridge mBusNaviDetailModuleBridge;
    /* access modifiers changed from: private */
    public IBusRouteResult mBusResult;
    private POI mEndPoi;
    private JsFunctionCallback mFreshPOIInfoCallBack;
    private boolean mHasModuleDestroy = false;
    private boolean mHasPopWindow = false;
    private axi mHistoryItemClickListener;
    private JsFunctionCallback mJsCallBack;
    private JsFunctionCallback mJsCommentCallBack;
    private JsFunctionCallback mJsExChangeStartEndPoiCallBack;
    private JsFunctionCallback mJsRealTimeBusCallBack;
    private JsFunctionCallback mJsRefreshCallBack;
    private POI mStartPoi;
    private String originalBusRouteData;
    private axh<IBusRouteResult> routeResultRouteDataParseListener;

    public interface a {
        void a(boolean z);
    }

    public ModuleBus(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setHistoryItemClickListener(axi axi) {
        this.mHistoryItemClickListener = axi;
    }

    public void setRouteDataParseListener(axh<IBusRouteResult> axh) {
        this.routeResultRouteDataParseListener = axh;
    }

    public void setBusNaviDetailModuleBridge(IBusNaviDetailModuleBridge iBusNaviDetailModuleBridge) {
        this.mBusNaviDetailModuleBridge = iBusNaviDetailModuleBridge;
    }

    @AjxMethod("getFavoriteBusStation")
    public String getFavoriteBusStation(final JsFunctionCallback jsFunctionCallback) {
        ahl.a(new defpackage.ahl.a<String>() {
            public final void onError(Throwable th) {
            }

            public final /* synthetic */ void onFinished(Object obj) {
                String str = (String) obj;
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str);
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                StringBuilder sb = new StringBuilder();
                sb.append(LocationInstrument.getInstance().getLatestPosition().getAdCode());
                String sb2 = sb.toString();
                if (TextUtils.isEmpty(sb2)) {
                    return "";
                }
                List<btd> list = null;
                awv awv = (awv) defpackage.esb.a.a.a(awv.class);
                if (awv != null) {
                    list = awv.a(sb2);
                }
                if (list == null || list.size() <= 0) {
                    return "";
                }
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    jSONArray.put(ModuleBus.this.getJsonObjectFromRealTimeBusItem(list.get(i)));
                }
                return jSONArray.toString();
            }
        }, ahn.b());
        return "";
    }

    /* access modifiers changed from: private */
    public JSONObject getJsonObjectFromRealTimeBusItem(btd btd) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AutoJsonUtils.JSON_ADCODE, btd.adcode);
            jSONObject.put("line_id", btd.bus_id);
            jSONObject.put("line_name", btd.bus_name);
            jSONObject.put("station_name", btd.station_name);
            jSONObject.put("station_id", btd.station_id);
            jSONObject.put("station_direction_name", btd.bus_describe);
            jSONObject.put("station_lon", btd.station_lon);
            jSONObject.put("station_lat", btd.station_lat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    @AjxMethod("requestRoute")
    public void requestRoute(String str) {
        chk chk = new chk();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("start_poi");
            String optString2 = jSONObject.optString("end_poi");
            chk.n = bnx.a(optString);
            chk.p = bnx.a(optString2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (this.mHistoryItemClickListener != null) {
            this.mHistoryItemClickListener.a(chk);
        }
    }

    @com.autonavi.minimap.ajx3.modules.AjxMethod("jump")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startPage(java.lang.String r6, java.lang.String r7) {
        /*
            r5 = this;
            int r0 = r6.hashCode()
            r1 = 10
            r2 = 2
            r3 = 0
            r4 = 1
            switch(r0) {
                case -1581618112: goto L_0x00cd;
                case -1537447580: goto L_0x00c2;
                case -1419310402: goto L_0x00b8;
                case -1268958287: goto L_0x00ad;
                case -1049482625: goto L_0x00a3;
                case -1039690024: goto L_0x0098;
                case -891525969: goto L_0x008d;
                case -859198101: goto L_0x0083;
                case -816323261: goto L_0x0078;
                case -589437084: goto L_0x006d;
                case 3552798: goto L_0x0060;
                case 110546608: goto L_0x0054;
                case 240184948: goto L_0x0049;
                case 871533169: goto L_0x003d;
                case 1107217865: goto L_0x0031;
                case 1303995231: goto L_0x0026;
                case 1319686067: goto L_0x001a;
                case 1702565577: goto L_0x000e;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x00d9
        L_0x000e:
            java.lang.String r0 = "detail_access_foot_ride_navi"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 15
            goto L_0x00da
        L_0x001a:
            java.lang.String r0 = "detail_feedback"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 14
            goto L_0x00da
        L_0x0026:
            java.lang.String r0 = "bus_card_page"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 1
            goto L_0x00da
        L_0x0031:
            java.lang.String r0 = "detail_access_reroute"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 17
            goto L_0x00da
        L_0x003d:
            java.lang.String r0 = "detail_share"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 13
            goto L_0x00da
        L_0x0049:
            java.lang.String r0 = "busline"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 4
            goto L_0x00da
        L_0x0054:
            java.lang.String r0 = "topup"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 7
            goto L_0x00da
        L_0x0060:
            java.lang.String r0 = "taxi"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 11
            goto L_0x00da
        L_0x006d:
            java.lang.String r0 = "detail_access_ugc"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 16
            goto L_0x00da
        L_0x0078:
            java.lang.String r0 = "vipbus"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 6
            goto L_0x00da
        L_0x0083:
            java.lang.String r0 = "realtime"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 2
            goto L_0x00da
        L_0x008d:
            java.lang.String r0 = "subway"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 5
            goto L_0x00da
        L_0x0098:
            java.lang.String r0 = "notice"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 8
            goto L_0x00da
        L_0x00a3:
            java.lang.String r0 = "nearby"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 3
            goto L_0x00da
        L_0x00ad:
            java.lang.String r0 = "follow"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 10
            goto L_0x00da
        L_0x00b8:
            java.lang.String r0 = "agroup"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 0
            goto L_0x00da
        L_0x00c2:
            java.lang.String r0 = "freeride"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 12
            goto L_0x00da
        L_0x00cd:
            java.lang.String r0 = "sharebike"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00d9
            r0 = 9
            goto L_0x00da
        L_0x00d9:
            r0 = -1
        L_0x00da:
            switch(r0) {
                case 0: goto L_0x022a;
                case 1: goto L_0x0208;
                case 2: goto L_0x0204;
                case 3: goto L_0x0200;
                case 4: goto L_0x01e9;
                case 5: goto L_0x01cd;
                case 6: goto L_0x018b;
                case 7: goto L_0x0184;
                case 8: goto L_0x0147;
                case 9: goto L_0x011a;
                case 10: goto L_0x0116;
                case 11: goto L_0x010c;
                case 12: goto L_0x010c;
                case 13: goto L_0x0102;
                case 14: goto L_0x00f8;
                case 15: goto L_0x00f4;
                case 16: goto L_0x00ea;
                case 17: goto L_0x00df;
                default: goto L_0x00dd;
            }
        L_0x00dd:
            goto L_0x0245
        L_0x00df:
            com.autonavi.minimap.route.ajx.module.bus.IBusNaviDetailModuleBridge r6 = r5.mBusNaviDetailModuleBridge
            if (r6 == 0) goto L_0x0245
            com.autonavi.minimap.route.ajx.module.bus.IBusNaviDetailModuleBridge r6 = r5.mBusNaviDetailModuleBridge
            r6.accessReroute()
            goto L_0x0245
        L_0x00ea:
            com.autonavi.minimap.route.ajx.module.bus.IBusNaviDetailModuleBridge r6 = r5.mBusNaviDetailModuleBridge
            if (r6 == 0) goto L_0x0245
            com.autonavi.minimap.route.ajx.module.bus.IBusNaviDetailModuleBridge r6 = r5.mBusNaviDetailModuleBridge
            r6.accessUgc()
            return
        L_0x00f4:
            r5.accessFootRideNavi(r7)
            return
        L_0x00f8:
            com.autonavi.minimap.route.ajx.module.bus.IBusNaviDetailModuleBridge r6 = r5.mBusNaviDetailModuleBridge
            if (r6 == 0) goto L_0x0245
            com.autonavi.minimap.route.ajx.module.bus.IBusNaviDetailModuleBridge r6 = r5.mBusNaviDetailModuleBridge
            r6.triggerFeedback()
            return
        L_0x0102:
            com.autonavi.minimap.route.ajx.module.bus.IBusNaviDetailModuleBridge r6 = r5.mBusNaviDetailModuleBridge
            if (r6 == 0) goto L_0x0245
            com.autonavi.minimap.route.ajx.module.bus.IBusNaviDetailModuleBridge r6 = r5.mBusNaviDetailModuleBridge
            r6.triggerShare()
            return
        L_0x010c:
            axh<com.autonavi.bundle.routecommon.entity.IBusRouteResult> r7 = r5.routeResultRouteDataParseListener
            if (r7 == 0) goto L_0x0245
            axh<com.autonavi.bundle.routecommon.entity.IBusRouteResult> r7 = r5.routeResultRouteDataParseListener
            r7.a(r6)
            return
        L_0x0116:
            r5.gotoRealTimeListFragment(r4, r4)
            return
        L_0x011a:
            boolean r6 = android.text.TextUtils.isEmpty(r7)
            r0 = 0
            if (r6 != 0) goto L_0x0143
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x012d }
            r6.<init>(r7)     // Catch:{ JSONException -> 0x012d }
            java.lang.String r7 = "firepage"
            java.lang.String r6 = r6.optString(r7)     // Catch:{ JSONException -> 0x012d }
            goto L_0x0132
        L_0x012d:
            r6 = move-exception
            r6.printStackTrace()
            r6 = r0
        L_0x0132:
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto L_0x0143
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r7 = "sharebike_page_from"
            r0.putString(r7, r6)
        L_0x0143:
            r5.goToShareBikePage(r0)
            return
        L_0x0147:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            com.amap.bundle.blutils.app.ConfigerHelper r7 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()
            java.lang.String r0 = "busnotice_url"
            java.lang.String r7 = r7.getKeyValue(r0)
            r6.append(r7)
            java.lang.String r7 = "?adcode=110000"
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            aja r7 = new aja
            r7.<init>(r6)
            ajf r6 = new ajf
            r6.<init>()
            r7.b = r6
            esb r6 = defpackage.esb.a.a
            java.lang.Class<aix> r0 = defpackage.aix.class
            esc r6 = r6.a(r0)
            aix r6 = (defpackage.aix) r6
            if (r6 == 0) goto L_0x0183
            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r6.a(r0, r7)
        L_0x0183:
            return
        L_0x0184:
            java.lang.String r6 = "暂未推出此功能"
            com.amap.bundle.utils.ui.ToastHelper.showToast(r6)
            return
        L_0x018b:
            com.autonavi.common.PageBundle r6 = new com.autonavi.common.PageBundle
            r6.<init>()
            java.lang.String r7 = "url"
            com.amap.bundle.blutils.app.ConfigerHelper r0 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()
            java.lang.String r1 = "dadabus_url"
            java.lang.String r0 = r0.getKeyValue(r1)
            r6.putString(r7, r0)
            java.lang.String r7 = "show_bottom_bar"
            r6.putBoolean(r7, r4)
            java.lang.String r7 = "show_loading_anim"
            r6.putBoolean(r7, r4)
            java.lang.String r7 = "allow_geolocation"
            r6.putBoolean(r7, r4)
            java.lang.String r7 = "thirdpart_name"
            android.content.Context r0 = r5.getNativeContext()
            int r1 = com.autonavi.minimap.R.string.dadabus
            java.lang.String r0 = r0.getString(r1)
            r6.putString(r7, r0)
            bid r7 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r7 == 0) goto L_0x01cc
            java.lang.String r0 = "amap.search.action.thirdpartweb"
            r7.startPage(r0, r6)
        L_0x01cc:
            return
        L_0x01cd:
            esb r6 = defpackage.esb.a.a
            java.lang.Class<bdk> r7 = defpackage.bdk.class
            esc r6 = r6.a(r7)
            bdk r6 = (defpackage.bdk) r6
            if (r6 == 0) goto L_0x0245
            bid r7 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            android.app.Activity r7 = r7.getActivity()
            java.lang.String r0 = ""
            r6.a(r7, r0)
            return
        L_0x01e9:
            esb r6 = defpackage.esb.a.a
            java.lang.Class<asy> r7 = defpackage.asy.class
            esc r6 = r6.a(r7)
            asy r6 = (defpackage.asy) r6
            if (r6 == 0) goto L_0x0245
            com.autonavi.common.PageBundle r7 = new com.autonavi.common.PageBundle
            r7.<init>()
            r6.a(r7)
            return
        L_0x0200:
            r5.gotoRealTimeListFragment(r3, r3)
            return
        L_0x0204:
            r5.gotoRealTimeListFragment(r3, r4)
            return
        L_0x0208:
            int r6 = java.lang.Integer.parseInt(r7, r1)
            if (r6 == r2) goto L_0x0218
            if (r6 == r4) goto L_0x0218
            java.lang.String r6 = "---modulebus"
            java.lang.String r7 = "--start my card page pagetype error!!!"
            defpackage.eao.b(r6, r7)
            return
        L_0x0218:
            esb r7 = defpackage.esb.a.a
            java.lang.Class<aso> r0 = defpackage.aso.class
            esc r7 = r7.a(r0)
            aso r7 = (defpackage.aso) r7
            if (r7 == 0) goto L_0x0245
            r7.a(r6)
            return
        L_0x022a:
            java.lang.String r6 = "amapuri://AGroup/joinGroup"
            android.net.Uri r6 = android.net.Uri.parse(r6)
            android.content.Context r7 = r5.getNativeContext()
            boolean r0 = r7 instanceof defpackage.brr
            if (r0 == 0) goto L_0x0245
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "android.intent.action.VIEW"
            r0.<init>(r1, r6)
            brr r7 = (defpackage.brr) r7
            r7.b(r0)
            return
        L_0x0245:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.busnavi.ajx.ModuleBus.startPage(java.lang.String, java.lang.String):void");
    }

    @AjxMethod("showBusNaviNotification")
    public void showBusNaviNotification(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("title", "");
            String optString2 = jSONObject.optString("content", "");
            eac a2 = eac.a();
            ead a3 = ead.a(1);
            a3.c = optString;
            a3.d = optString2;
            a2.a(a3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @AjxMethod("hideBusNaviNotification")
    public void hideBusNaviNotification() {
        eac.a().a(1);
    }

    private void gotoRealTimeListFragment(int i, int i2) {
        PageBundle pageBundle = new PageBundle();
        StringBuilder sb = new StringBuilder();
        sb.append(LocationInstrument.getInstance().getLatestPosition().getAdCode());
        pageBundle.putString("real_time_bus_adcode", sb.toString());
        pageBundle.putInt("where_click_real_time_bus", i);
        pageBundle.putInt("is_support_real_time_bus", i2);
        awv awv = (awv) defpackage.esb.a.a.a(awv.class);
        if (awv != null) {
            awv.a().a();
        }
    }

    @AjxMethod("fetchRouteRequest")
    public void fetchRouteRequest(JsFunctionCallback jsFunctionCallback) {
        this.mJsCallBack = jsFunctionCallback;
        if (eal.a(this.mStartPoi) && eal.a(this.mEndPoi)) {
            setStartEndPoiData(this.mStartPoi, this.mEndPoi, this.mJsCallBack);
        }
    }

    @AjxMethod("exchangeStartEndPoi")
    public void exchangeStartEndPoi(JsFunctionCallback jsFunctionCallback) {
        this.mJsExChangeStartEndPoiCallBack = jsFunctionCallback;
    }

    public void exChangeStartEndPoi(POI poi, POI poi2) {
        if (eal.a(poi) && eal.a(poi2)) {
            setStartEndPoiData(poi, poi2, this.mJsExChangeStartEndPoiCallBack);
        }
    }

    @AjxMethod("registerApplicationDidBecomeActiveTask")
    public void registerApplicationDidBecomeActiveTask(JsFunctionCallback jsFunctionCallback) {
        this.mFreshPOIInfoCallBack = jsFunctionCallback;
    }

    public void updatePOIInfo() {
        if (this.mFreshPOIInfoCallBack != null) {
            this.mFreshPOIInfoCallBack.callback(new Object[0]);
        }
    }

    public void requestRouteResult(POI poi, POI poi2) {
        if (eal.a(poi) && eal.a(poi2)) {
            if (this.mStartPoi == null || this.mEndPoi == null || eal.a(poi, this.mStartPoi) || eal.a(poi2, this.mEndPoi)) {
                setStartEndPoiData(poi, poi2, this.mJsCallBack);
            }
        }
    }

    private void setStartEndPoiData(POI poi, POI poi2, JsFunctionCallback jsFunctionCallback) {
        if (poi.getName().equals(eay.a(R.string.route_my_position))) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition != null) {
                poi.setPoint(latestPosition);
            }
        }
        if (poi2.getName().equals(eay.a(R.string.route_my_position))) {
            GeoPoint latestPosition2 = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition2 != null) {
                poi2.setPoint(latestPosition2);
            }
        }
        this.mStartPoi = poi;
        this.mEndPoi = poi2;
        this.mBusResult = new RouteBusResultData();
        this.mBusResult.setFromPOI(poi);
        this.mBusResult.setToPOI(poi2);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("start_poi", bnx.b(poi));
            jSONObject.put("end_poi", bnx.b(poi2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(jSONObject.toString());
        }
        saveEndPoint(poi2.getId(), poi2.getPoint().getLongitude(), poi2.getPoint().getLatitude());
    }

    private void saveEndPoint(String str, double d, double d2) {
        if (!TextUtils.isEmpty(str)) {
            BusEndPointData busEndPointData = new BusEndPointData();
            busEndPointData.setTimeStamp(System.currentTimeMillis());
            busEndPointData.setLastStationID(str);
            StringBuilder sb = new StringBuilder();
            sb.append(d);
            sb.append(",");
            sb.append(d2);
            busEndPointData.setLastStationLonLat(sb.toString());
            awv awv = (awv) defpackage.esb.a.a.a(awv.class);
            if (awv != null) {
                awv.a(busEndPointData);
            }
        }
    }

    @AjxMethod("shouldShowShareBikeEntrance")
    public void shouldShowShareBikeEntrance(final JsFunctionCallback jsFunctionCallback) {
        ahl.b(new defpackage.ahl.a<String>() {
            public final /* synthetic */ void onFinished(Object obj) {
                String str = (String) obj;
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str);
                }
            }

            /* JADX WARNING: Removed duplicated region for block: B:17:0x0063  */
            /* JADX WARNING: Removed duplicated region for block: B:24:0x007d  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final /* synthetic */ java.lang.Object doBackground() throws java.lang.Exception {
                /*
                    r7 = this;
                    lt r0 = defpackage.lt.a()
                    ls r0 = r0.c
                    lx r0 = r0.w
                    r1 = 0
                    if (r0 == 0) goto L_0x0016
                    java.lang.Boolean r2 = r0.b
                    if (r2 == 0) goto L_0x0016
                    java.lang.Boolean r0 = r0.b
                    boolean r0 = r0.booleanValue()
                    goto L_0x0017
                L_0x0016:
                    r0 = 0
                L_0x0017:
                    java.lang.String r2 = "modulebus"
                    java.lang.String r3 = "switch:"
                    java.lang.String r4 = java.lang.String.valueOf(r0)
                    java.lang.String r3 = r3.concat(r4)
                    defpackage.eao.a(r2, r3)
                    com.autonavi.bundle.busnavi.ajx.ModuleBus r2 = com.autonavi.bundle.busnavi.ajx.ModuleBus.this
                    com.autonavi.bundle.routecommon.entity.IBusRouteResult r2 = r2.mBusResult
                    if (r2 == 0) goto L_0x00c7
                    if (r0 == 0) goto L_0x00c7
                    com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()
                    com.autonavi.common.model.GeoPoint r0 = r0.getLatestPosition()
                    if (r0 == 0) goto L_0x004c
                    li r2 = defpackage.li.a()
                    int r3 = r0.x
                    int r0 = r0.y
                    lj r0 = r2.b(r3, r0)
                    if (r0 == 0) goto L_0x004c
                    int r0 = r0.j
                    goto L_0x004d
                L_0x004c:
                    r0 = 0
                L_0x004d:
                    com.autonavi.bundle.busnavi.ajx.ModuleBus r2 = com.autonavi.bundle.busnavi.ajx.ModuleBus.this
                    com.autonavi.bundle.routecommon.entity.IBusRouteResult r2 = r2.mBusResult
                    com.autonavi.common.model.POI r2 = r2.getFromPOI()
                    com.autonavi.bundle.busnavi.ajx.ModuleBus r3 = com.autonavi.bundle.busnavi.ajx.ModuleBus.this
                    com.autonavi.bundle.routecommon.entity.IBusRouteResult r3 = r3.mBusResult
                    com.autonavi.common.model.POI r3 = r3.getToPOI()
                    if (r2 == 0) goto L_0x007a
                    com.autonavi.common.model.GeoPoint r2 = r2.getPoint()
                    if (r2 == 0) goto L_0x007a
                    li r4 = defpackage.li.a()
                    int r5 = r2.x
                    int r2 = r2.y
                    lj r2 = r4.b(r5, r2)
                    if (r2 == 0) goto L_0x007a
                    int r2 = r2.j
                    goto L_0x007b
                L_0x007a:
                    r2 = 0
                L_0x007b:
                    if (r3 == 0) goto L_0x0094
                    com.autonavi.common.model.GeoPoint r3 = r3.getPoint()
                    if (r3 == 0) goto L_0x0094
                    li r4 = defpackage.li.a()
                    int r5 = r3.x
                    int r3 = r3.y
                    lj r3 = r4.b(r5, r3)
                    if (r3 == 0) goto L_0x0094
                    int r3 = r3.j
                    goto L_0x0095
                L_0x0094:
                    r3 = 0
                L_0x0095:
                    if (r0 != r2) goto L_0x009a
                    if (r2 != r3) goto L_0x009a
                    r1 = 1
                L_0x009a:
                    java.lang.String r4 = "modulebus"
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    java.lang.String r6 = "switch:"
                    r5.<init>(r6)
                    r5.append(r1)
                    java.lang.String r6 = "cur:"
                    r5.append(r6)
                    r5.append(r0)
                    java.lang.String r0 = ", start:"
                    r5.append(r0)
                    r5.append(r2)
                    java.lang.String r0 = ", end:"
                    r5.append(r0)
                    r5.append(r3)
                    java.lang.String r0 = r5.toString()
                    defpackage.eao.a(r4, r0)
                    r0 = r1
                L_0x00c7:
                    org.json.JSONObject r1 = new org.json.JSONObject
                    r1.<init>()
                    java.lang.String r2 = "show"
                    r1.put(r2, r0)
                    java.lang.String r0 = r1.toString()
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.busnavi.ajx.ModuleBus.AnonymousClass2.doBackground():java.lang.Object");
            }
        });
    }

    @AjxMethod("didClickComment")
    public void didClickComment(JsFunctionCallback jsFunctionCallback) {
        if (this.routeResultRouteDataParseListener != null) {
            this.routeResultRouteDataParseListener.a();
        }
        if (jsFunctionCallback != null) {
            this.mJsCommentCallBack = jsFunctionCallback;
        }
    }

    public void setCommentCloseState(boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(DataflowMonitorModel.METHOD_NAME_CLOSE, String.valueOf(z));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jSONObject2 = jSONObject.toString();
        if (this.mJsCommentCallBack != null) {
            this.mJsCommentCallBack.callback(jSONObject2);
        }
    }

    @AjxMethod("setRouteErrorType")
    public void setRouteErrorType(String str) {
        eao.b("----setRouteErrorType----errorJson:".concat(String.valueOf(str)));
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("errcode", "");
            if (TextUtils.isEmpty(optString)) {
                String optString2 = jSONObject.optString("code", "");
                String optString3 = jSONObject.optString("why", "");
                if (!eko.a(optString2)) {
                    eko.b(optString3);
                }
            } else if (!eko.a(optString)) {
                eko.b(optString);
                eko.a((int) SDKFactory.getCoreType);
            }
        } catch (JSONException unused) {
        }
    }

    @AjxMethod("setRouteData")
    public void setRouteData(String str) {
        AMapLog.e("ModuleBus", "caoyp -- setRouteData --- 0");
        this.originalBusRouteData = str;
        dvz dvz = new dvz(this.mBusResult);
        if (!TextUtils.isEmpty(str)) {
            try {
                dvz.parser(str.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            AMapLog.e("ModuleBus", "caoyp -- setRouteData --- 1");
            this.mBusResult.setMethod(getBusMethodFromMemoryStorge());
            if (this.routeResultRouteDataParseListener != null) {
                this.routeResultRouteDataParseListener.a(this.mBusResult);
            }
            ahm.c(new Runnable() {
                public final void run() {
                    bax bax = (bax) defpackage.esb.a.a.a(bax.class);
                    if (bax != null) {
                        bax.a(ModuleBus.this.mBusResult, RouteType.BUS);
                    }
                }
            });
        } else if (this.routeResultRouteDataParseListener != null) {
            this.routeResultRouteDataParseListener.a(null);
        }
        reverseGeocode();
    }

    @AjxMethod("didSelectRoute")
    public void didSelectRoute(String str, String str2) {
        if (!TextUtils.isEmpty(str) && this.routeResultRouteDataParseListener != null) {
            this.routeResultRouteDataParseListener.a(Integer.parseInt(str), str2);
        }
    }

    @AjxMethod("refreshBusList")
    public void refreshBusList(JsFunctionCallback jsFunctionCallback) {
        this.mJsRefreshCallBack = jsFunctionCallback;
    }

    public void setRefreshBusList(String str) {
        if (this.mJsRefreshCallBack != null) {
            this.mJsRefreshCallBack.callback(str);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getBusMethodFromMemoryStorge() {
        /*
            r3 = this;
            com.autonavi.minimap.ajx3.Ajx r0 = com.autonavi.minimap.ajx3.Ajx.getInstance()
            java.lang.String r1 = "ajx3_RPBus"
            com.autonavi.minimap.ajx3.core.MemoryStorageRef r0 = r0.getMemoryStorage(r1)
            java.lang.String r1 = "BizRPBusInfo"
            java.lang.Object r0 = r0.getItem(r1)
            java.lang.String r1 = ""
            if (r0 == 0) goto L_0x0029
            java.lang.String r0 = r0.toString()
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0025 }
            r2.<init>(r0)     // Catch:{ JSONException -> 0x0025 }
            java.lang.String r0 = "type"
            java.lang.String r0 = r2.optString(r0)     // Catch:{ JSONException -> 0x0025 }
            goto L_0x002a
        L_0x0025:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0029:
            r0 = r1
        L_0x002a:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x0032
            java.lang.String r0 = "0"
        L_0x0032:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.busnavi.ajx.ModuleBus.getBusMethodFromMemoryStorge():java.lang.String");
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        this.mHasModuleDestroy = true;
    }

    @AjxMethod("getOperaterInfo")
    public void getOperaterInfo(JsFunctionCallback jsFunctionCallback) {
        jsFunctionCallback.callback(getOperaterString());
    }

    private String getOperaterString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        defpackage.kx.a[] b = kx.b(AMapAppGlobal.getApplication());
        for (int i = 0; i <= 0; i++) {
            defpackage.kx.a aVar = b[i];
            String str = "";
            switch (aVar.c) {
                case 0:
                    str = "";
                    break;
                case 1:
                    str = "cu";
                    break;
                case 2:
                    str = LogItem.MM_C43_K4_CAMERA_TIME;
                    break;
                case 3:
                    str = "cm";
                    break;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(",");
            stringBuffer.append(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(aVar.a);
            sb2.append(",");
            stringBuffer2.append(sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(aVar.b);
            sb3.append(",");
            stringBuffer3.append(sb3.toString());
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer2.deleteCharAt(stringBuffer2.length() - 1);
        stringBuffer3.deleteCharAt(stringBuffer3.length() - 1);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("carrier", stringBuffer.toString());
            jSONObject.put("carrier_name", stringBuffer2.toString());
            jSONObject.put("carrier_code", stringBuffer3.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    @AjxMethod("jumpToBannerPopUPView")
    public void jumpToBannerPopUPView(String str) {
        if (this.mHasModuleDestroy) {
            ku.a().c("auiLog", "jumpToBannerPopUPView-->mHasModuleDestroy:true");
        } else if (getNativeContext() == null) {
            ku.a().c("auiLog", "jumpToBannerPopUPView-->context==null");
        } else {
            if (!TextUtils.isEmpty(str) && AMapPageUtil.getPageContext() != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("url", BUS_POPUP_PATH);
                pageBundle.putObject("jsData", str);
                AMapPageUtil.getPageContext().startPage(Ajx3DialogPage.class, pageBundle);
                this.mHasPopWindow = true;
            }
        }
    }

    @AjxMethod("closeBannerPopUPView")
    public void closePopUpWindow() {
        if (this.mHasPopWindow) {
            bid pageContext = AMapPageUtil.getPageContext();
            if ((pageContext instanceof Ajx3DialogPage) && BUS_POPUP_PATH.equals(((Ajx3DialogPage) pageContext).getAjx3Url())) {
                pageContext.finish();
            }
        }
    }

    public void onBusRemindPageDestroy() {
        eao.a((String) "modulebus", (String) "onBusRemindPageDestroy");
        this.mJsRealTimeBusCallBack = null;
        this.mBusNaviDetailModuleBridge = null;
    }

    private void accessFootRideNavi(String str) {
        eao.a((String) "modulebus", "accessFootRideNavi: ".concat(String.valueOf(str)));
        dxa a2 = dxa.a(str);
        if (a2 != null && this.mBusNaviDetailModuleBridge != null) {
            this.mBusNaviDetailModuleBridge.accessFootOrRideNavi(a2);
        }
    }

    @AjxMethod("registerRealTimeBusRefreshCallback")
    public void registerRealTimeBusRefreshCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsRealTimeBusCallBack = jsFunctionCallback;
    }

    public void requestRealTimeBusData() {
        if (this.mJsRealTimeBusCallBack != null) {
            this.mJsRealTimeBusCallBack.callback(new Object[0]);
        }
    }

    @AjxMethod("didExchangeBusPathInfo")
    public void didExchangeBusPathInfo(String str) {
        int i;
        eao.a((String) "modulebus", "didExchangeBusPathInfo: ".concat(String.valueOf(str)));
        try {
            i = Integer.parseInt(new JSONObject(str).optString("listNumber", null));
        } catch (Exception e) {
            e.printStackTrace();
            i = -1;
        }
        if (this.mBusNaviDetailModuleBridge != null) {
            this.mBusNaviDetailModuleBridge.selectBusRoute(i);
        }
    }

    @AjxMethod("exchangeAlterRoute")
    public void exchangeAlterRoute(String str) {
        eao.a((String) "modulebus", "exchangeAlterRoute: ".concat(String.valueOf(str)));
        checkSelectedRoute(str, "listNumber");
        dwy a2 = dwy.a(str);
        if (a2 != null && this.mBusNaviDetailModuleBridge != null) {
            this.mBusNaviDetailModuleBridge.onExchangeAlterRoute(a2);
        }
    }

    @AjxMethod("onFavorClick")
    public void onFavorClick(String str, final JsFunctionCallback jsFunctionCallback) {
        eao.a((String) "modulebus", "onFavorClick: ".concat(String.valueOf(str)));
        checkSelectedRoute(str, "listNumber");
        if (this.mBusNaviDetailModuleBridge != null) {
            this.mBusNaviDetailModuleBridge.triggerFavor(new a() {
                public final void a(boolean z) {
                    if (jsFunctionCallback != null) {
                        try {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("success", z ? "1" : "0");
                            jSONObject.put("state", (ModuleBus.this.mBusNaviDetailModuleBridge == null || !ModuleBus.this.mBusNaviDetailModuleBridge.isFavorite()) ? "0" : "1");
                            jsFunctionCallback.callback(jSONObject.toString());
                            StringBuilder sb = new StringBuilder("onFavorClick return ");
                            sb.append(jSONObject.toString());
                            eao.a((String) "modulebus", sb.toString());
                        } catch (Exception unused) {
                        }
                    }
                }
            });
        }
    }

    @AjxMethod("isAddedToFavorite")
    public void isAddedToFavorite(String str, JsFunctionCallback jsFunctionCallback) {
        eao.a((String) "modulebus", "isAddedToFavorite: ".concat(String.valueOf(str)));
        checkSelectedRoute(str, "listNumber");
        if (jsFunctionCallback != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("state", (this.mBusNaviDetailModuleBridge == null || !this.mBusNaviDetailModuleBridge.isFavorite()) ? "0" : "1");
                jsFunctionCallback.callback(jSONObject.toString());
                StringBuilder sb = new StringBuilder("isAddedToFavorite return ");
                sb.append(jSONObject.toString());
                eao.a((String) "modulebus", sb.toString());
            } catch (Exception unused) {
            }
        }
    }

    @AjxMethod("onListStatusChanged")
    public void onListStatusChanged(String str) {
        eao.a((String) "modulebus", "onListStatusChanged: ".concat(String.valueOf(str)));
        if (this.mBusNaviDetailModuleBridge != null) {
            this.mBusNaviDetailModuleBridge.onListStatusChange(str);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "notifyBusRemindPageClose")
    public void notifyBusRemindPageClose(String str) {
        eao.a((String) "modulebus", "notifyBusRemindPageClose: ".concat(String.valueOf(str)));
        if (str != null) {
            try {
                String optString = new JSONObject(str).optString("busRouteData", "");
                if (!TextUtils.isEmpty(optString)) {
                    this.originalBusRouteData = optString;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @AjxMethod("checkNearbyShareBike")
    public void checkNearbyShareBike(String str) {
        eao.a((String) "modulebus", "checkNearbyShareBike: ".concat(String.valueOf(str)));
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject optJSONObject = jSONObject.optJSONObject("startPoint");
                String optString = optJSONObject.optString("lat");
                String optString2 = optJSONObject.optString(LocationParams.PARA_FLP_AUTONAVI_LON);
                String optString3 = optJSONObject.optString("name");
                pageBundle.putString("lat", optString);
                pageBundle.putString(LocationParams.PARA_FLP_AUTONAVI_LON, optString2);
                pageBundle.putString("poi_name", optString3);
                pageBundle.putString("sharebike_page_from", "gjxq");
                JSONObject optJSONObject2 = jSONObject.optJSONObject("endPoint");
                String optString4 = optJSONObject2.optString("lat");
                String optString5 = optJSONObject2.optString(LocationParams.PARA_FLP_AUTONAVI_LON);
                String optString6 = optJSONObject2.optString("name");
                pageBundle.putString("end_lat", optString4);
                pageBundle.putString("end_lon", optString5);
                pageBundle.putString("end_name", optString6);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        goToShareBikePage(pageBundle);
    }

    public String getOriginalBusRouteData() {
        return this.originalBusRouteData;
    }

    public void setOriginalBusRouteData(String str) {
        this.originalBusRouteData = str;
    }

    private void checkSelectedRoute(String str, String str2) {
        int i;
        try {
            i = Integer.parseInt(new JSONObject(str).optString(str2, null));
        } catch (Exception e) {
            e.printStackTrace();
            i = -1;
        }
        if (this.mBusNaviDetailModuleBridge != null) {
            this.mBusNaviDetailModuleBridge.selectBusRoute(i);
        }
    }

    private void reverseGeocode() {
        if (this.mBusResult != null && this.mBusResult.getBusPathsResult() != null) {
            final BusPaths busPathsResult = this.mBusResult.getBusPathsResult();
            POI clone = this.mBusResult.getFromPOI().clone();
            if ("我的位置".equals(clone.getName())) {
                new edc().a(clone.getPoint(), new defpackage.edc.a() {
                    public final void a(String str) {
                        busPathsResult.mStartDes = str;
                    }
                });
            }
            POI clone2 = this.mBusResult.getToPOI().clone();
            if ("我的位置".equals(clone2.getName())) {
                new edc().a(clone2.getPoint(), new defpackage.edc.a() {
                    public final void a(String str) {
                        busPathsResult.mEndDes = str;
                    }
                });
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "isCurrentCitySupportRealtimeBus")
    public boolean isCurrentCitySupportRealtimeBus() {
        if (DoNotUseTool.getMapManager() == null) {
            return false;
        }
        bty mapView = DoNotUseTool.getMapManager().getMapView();
        if (!brj.b(mapView) || !brj.a(mapView)) {
            return false;
        }
        return true;
    }

    @AjxMethod("showBusNaviDisclaimer")
    public void showBusNaviDisclaimer(final JsFunctionCallback jsFunctionCallback) {
        if (this.mBusNaviDetailModuleBridge != null) {
            this.mBusNaviDetailModuleBridge.showBusNaviDisclaimer(new Callback() {
                public final void callback(Object[] objArr) {
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(new Object[0]);
                    }
                }
            });
        }
    }

    @AjxMethod("needShowRealTimeButton")
    public void needShowRealTimeButton(boolean z) {
        if (this.mBusNaviDetailModuleBridge != null) {
            this.mBusNaviDetailModuleBridge.switchRealtimeBusEnable(z);
        }
    }

    @AjxMethod("calcDistanceToCurrentPath")
    public void calcDistanceToCurrentPath(final JsFunctionCallback jsFunctionCallback) {
        if (this.mBusNaviDetailModuleBridge != null) {
            this.mBusNaviDetailModuleBridge.calcDistanceToCurrentPath(new Callback<Integer>() {
                public final /* bridge */ /* synthetic */ void callback(Object[] objArr) {
                    Integer[] numArr = (Integer[]) objArr;
                    if (numArr != null && numArr.length > 0 && jsFunctionCallback != null) {
                        jsFunctionCallback.callback(numArr[0]);
                    }
                }
            });
        }
    }

    @AjxMethod("updateBusRemindStatus")
    public void updateBusRemindStatus(boolean z) {
        if (this.mBusNaviDetailModuleBridge != null) {
            this.mBusNaviDetailModuleBridge.updateBusRemindStatus(z);
        }
    }

    @AjxMethod("updateAchievementRequest")
    public void uploadOperationData(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            long optLong = jSONObject.optLong(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, 0);
            long optLong2 = jSONObject.optLong(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, 0);
            UpLoadOperationDataUtil.a(OperationType.TYPE_BUS_NAV, (int) optLong, (int) optLong2, jSONObject.optInt("distance", 0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @AjxMethod("performanceLog")
    public void performanceLog(String str) {
        eao.d(str);
    }

    private void goToShareBikePage(PageBundle pageBundle) {
        bdf bdf = (bdf) defpackage.esb.a.a.a(bdf.class);
        if (bdf != null) {
            bdf.b().a(3, pageBundle);
        }
    }
}
