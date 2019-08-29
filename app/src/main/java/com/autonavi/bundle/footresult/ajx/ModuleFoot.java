package com.autonavi.bundle.footresult.ajx;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.cloudconfig.appinit.request.AppInitCallback;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.footnavi.api.IFootNaviPage;
import com.autonavi.bundle.healthyrun.api.IHRunPage.PageType;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.pos.LocManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.route.ajx.inter.OnAjxFootMapInterface;
import com.autonavi.minimap.route.ajx.inter.OnAjxFootPreviewInterface;
import com.autonavi.minimap.route.ajx.inter.OnEndPoiChangeInterface;
import com.autonavi.minimap.route.ajx.inter.OnErrorReportClickInterface;
import com.autonavi.minimap.route.ajx.inter.OnNotifyCalcRouteListener;
import com.autonavi.minimap.route.ajx.inter.OnRouteSaveEventListener;
import com.autonavi.minimap.route.ajx.inter.RouteResultSuccessInterface;
import com.autonavi.minimap.route.ajx.inter.UnLockGpsButtonInterface;
import com.autonavi.minimap.route.common.util.RouteSharingUtil;
import com.autonavi.minimap.route.common.util.RouteSharingUtil.a;
import com.autonavi.minimap.route.foot.page.AjxFootBrowserPage;
import com.autonavi.minimap.route.foot.page.AjxFootMapPage;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil.OperationType;
import com.autonavi.minimap.route.run.beans.RunTraceHistory;
import com.autonavi.minimap.widget.ConfirmDlg;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("route_foot")
public class ModuleFoot extends AbstractModule {
    private static final String BACK_PAGE_DEFAULT = "backDefault";
    private static final int CALC_ROUTE_FROM_DATA = 15;
    private static final int COMPASS_VIEW_ICON_CLICK = 27;
    private static final int ERROR_REPORT_CLICK = 8;
    private static final int ERROR_REPORT_DATA = 7;
    private static final int EVENT_CLICK_SELF_CARICON = 21;
    private static final int EVENT_CLICK_UGC = 22;
    private static final int FINISH_PAGE_MESSAGE = 3;
    private static final int INDOOR_FLOOR_CHANGE = 20;
    private static final int INDOOR_FLOOR_CHANGE_PREVIEW_PAGE = 18;
    public static final String MODULE_NAME = "route_foot";
    private static final int NAVI_DIRECTION_MODE = 4;
    private static final int NAVI_FINISH_TO_PREVIEW = 13;
    private static final int NAVI_ON_NOPASS_CONFIRE = 101;
    private static final int NAVI_OPERATION_ACTIVITIES = 25;
    private static final int NAVI_UPDATE_NOTIFY = 9;
    private static final int NAVI_VOICE_STATUS_CHANGE = 10;
    private static final int NAVI_VOICE_TOAST = 17;
    private static final int ON_AJX_PAGE_SHOW = 30;
    public static final String PAGE_SOURCE_TYPE_COMMON = "source_common";
    public static final String PAGE_SOURCE_TYPE_ETRIP = "source_etrip";
    public static final String PAGE_SOURCE_TYPE_FAVORITE = "source_save";
    private static final int RESULT_ERROR_CODE_CALLBACK = 28;
    private static final int RESULT_SLIDING_STATUS = 26;
    private static final int RESULT_TAB_CHANGE = 12;
    private static final int ROUTE_SAVE_MESSAGE = 19;
    private static final int SAVE_NAVI_BAR_STATE_MESSAGE = 2;
    private static final int SAVE_TRACE_RESULT_MESSAGE = 1;
    private static final int SHARE_RESULT_MESSAGE = 23;
    private static final int SHOW_RESULT_MESSAGE = 0;
    private static final int SNAP_SHOT_IMG_FINISH = 16;
    private static final int START_CALCULATE_RIDE_ROUTE = 11;
    private static final String START_CAR_NAVI = "showCarNavi";
    private static final String START_PAGE_AGROUP = "agroup";
    private static final String START_PAGE_CREATENEWTRACK = "createNewTrack";
    private static final String START_PAGE_FOOT_END = "footEnd";
    private static final String START_PAGE_FOOT_NAVI = "footNavi";
    private static final String START_PAGE_FOOT_PREVIEW = "footPreview";
    private static final String START_PAGE_FOOT_ROUTE = "footRoute";
    private static final String START_PAGE_POIDETAIL = "showPoiDetail";
    private static final String START_PAGE_RECOMMENDROUTE = "recommendRoute";
    private static final String START_PAGE_SHAREBIKE = "sharebike";
    private static final String START_PAGE_SHOWALLTRACKS = "showAllTracks";
    private static final String START_PAGE_SINGLETRACK = "showSingleTrack";
    private static final String START_PAGE_STARTRUN = "startRun";
    private static final int UNLOCK_GPS_BUTTON = 5;
    private static final int UPDATA_ACHIEVEMENT_DB = 29;
    private static final int UPDATE_ZOOM_POS = 6;
    private static final int UPLOAD_OPERATION_ACTIVITIES = 24;
    public static final String URL_FOOT_BROWSER = "path://amap_bundle_foot/src/preview_page/FootPreviewPage.page.js";
    public static final String URL_FOOT_END = "path://amap_bundle_foot/src/end_page/FootEndPage.page.js";
    public static final String URL_FOOT_NAVI = "path://amap_bundle_foot/src/navi_page/FootNaviPage.page.js";
    public static final String URL_FOOT_ROUTE = "path://amap_bundle_foot/src/result_page/FootResultPage.page.js";
    private Boolean footArAccessCloudConfig;
    private OnClickListener mAvoidDoubleClickListener = new OnClickListener() {
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.cancel) {
                if (ModuleFoot.this.mConfirmDlg != null && ModuleFoot.this.mConfirmDlg.isShowing()) {
                    ModuleFoot.this.mConfirmDlg.dismiss();
                    ModuleFoot.this.mConfirmDlg = null;
                }
            } else if (id == R.id.confirm) {
                edr.b((String) "agree_onfoot_declare", true);
                ModuleFoot.this.startFootNaviPage();
            }
        }
    };
    private Comparator<RunTraceHistory> mComparator;
    /* access modifiers changed from: private */
    public ConfirmDlg mConfirmDlg = null;
    private Context mContext;
    private String mDestNaviParams = null;
    private OnEndPoiChangeInterface mEndPoiChangeListener;
    private OnErrorReportClickInterface mErrorReportClickListener;
    private String mErrorReportData = null;
    private axi mHistoryItemClickListener;
    private JsFunctionCallback mJsCallBack = null;
    private JsFunctionCallback mJsNaviFinshCallBack = null;
    private axj mListener;
    private OnNotifyCalcRouteListener mNotifyCalcRouteListener;
    private axk mOnAjxFootNaviListener;
    private OnAjxFootPreviewInterface mOnAjxPreviewListener;
    private OnAjxFootMapInterface mOnAjxResultListener;
    private axl mOnAjxRideEndListener;
    private JsFunctionCallback mOnCompassShowCallBack;
    private JsFunctionCallback mOnJunmSelPoiPageCallBack;
    private axo mOnOperationActivitiesListener;
    private JsFunctionCallback mOnOutDoorLineFocus;
    private JsFunctionCallback mOnRideAccuracyChanged;
    private OnRouteSaveEventListener mOnRouteSaveEventListener;
    private JsFunctionCallback mOnUGCStateChangeCallBack;
    private String mRequestData = null;
    private RouteResultSuccessInterface mResultSuccessListener;
    private JsFunctionCallback mRideEndShareClickCallback;
    /* access modifiers changed from: private */
    public List<RunTraceHistory> mRunTraceHistoryList = null;
    private UnLockGpsButtonInterface mUnLockGpsBtnListener;
    private String mWeatherData = null;
    private axm onFootEndClickUGCListener;
    private axn onOpenCompassViewListener;

    public ModuleFoot(IAjxContext iAjxContext) {
        super(iAjxContext);
        this.mContext = iAjxContext.getNativeContext();
    }

    public void setHistoryItemClickListener(axi axi) {
        this.mHistoryItemClickListener = axi;
    }

    public void setEndPoiChangeListener(OnEndPoiChangeInterface onEndPoiChangeInterface) {
        this.mEndPoiChangeListener = onEndPoiChangeInterface;
    }

    public void setRideEndShareListener(axl axl) {
        this.mOnAjxRideEndListener = axl;
    }

    public void setUnLockGpsBtnListener(UnLockGpsButtonInterface unLockGpsButtonInterface) {
        this.mUnLockGpsBtnListener = unLockGpsButtonInterface;
    }

    public void setOnRouteSaveEventListener(OnRouteSaveEventListener onRouteSaveEventListener) {
        this.mOnRouteSaveEventListener = onRouteSaveEventListener;
    }

    public void setOnErrorReportClickListener(OnErrorReportClickInterface onErrorReportClickInterface) {
        this.mErrorReportClickListener = onErrorReportClickInterface;
    }

    public void setOnNotifyCalcRouteListener(OnNotifyCalcRouteListener onNotifyCalcRouteListener) {
        this.mNotifyCalcRouteListener = onNotifyCalcRouteListener;
    }

    public void setOnRouteResultSuccessListener(RouteResultSuccessInterface routeResultSuccessInterface) {
        this.mResultSuccessListener = routeResultSuccessInterface;
    }

    public void setWidgetPosListener(axj axj) {
        this.mListener = axj;
    }

    public void setOnNotifyChangeInterface(axk axk) {
        this.mOnAjxFootNaviListener = axk;
    }

    public void setOnAjxResultListener(OnAjxFootMapInterface onAjxFootMapInterface) {
        this.mOnAjxResultListener = onAjxFootMapInterface;
    }

    public void setOnAjxPreviewListener(OnAjxFootPreviewInterface onAjxFootPreviewInterface) {
        this.mOnAjxPreviewListener = onAjxFootPreviewInterface;
    }

    public void setOnOpenCompassViewListener(axn axn) {
        this.onOpenCompassViewListener = axn;
    }

    public void setOnFootEndClickUGCListener(axm axm) {
        this.onFootEndClickUGCListener = axm;
    }

    public void setOnOperationActivitiesListener(axo axo) {
        this.mOnOperationActivitiesListener = axo;
    }

    @AjxMethod("getTrackData")
    public String getFootTraceHistory(JsFunctionCallback jsFunctionCallback) {
        return getRunHistory(jsFunctionCallback);
    }

    private String getRunHistory(final JsFunctionCallback jsFunctionCallback) {
        ahl.a(new a<String>() {
            public final void onError(Throwable th) {
            }

            public final /* synthetic */ void onFinished(Object obj) {
                String str = (String) obj;
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str);
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                ModuleFoot.this.mRunTraceHistoryList = efu.a();
                if (ModuleFoot.this.mRunTraceHistoryList == null || ModuleFoot.this.mRunTraceHistoryList.size() <= 0) {
                    return "";
                }
                if (ModuleFoot.this.mRunTraceHistoryList.size() > 1) {
                    ModuleFoot.this.sortRunHistory(ModuleFoot.this.mRunTraceHistoryList);
                }
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < ModuleFoot.this.mRunTraceHistoryList.size(); i++) {
                    jSONArray.put(ModuleFoot.this.parseRunTraceHistoryToJson((RunTraceHistory) ModuleFoot.this.mRunTraceHistoryList.get(i)));
                }
                return jSONArray.toString();
            }
        }, ahn.b());
        return "";
    }

    /* access modifiers changed from: private */
    public JSONObject parseRunTraceHistoryToJson(RunTraceHistory runTraceHistory) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("track_id", runTraceHistory.a);
            jSONObject.put("time", runTraceHistory.b);
            jSONObject.put("distance", runTraceHistory.c);
            jSONObject.put("calorie", runTraceHistory.d);
            jSONObject.put("average_speed", runTraceHistory.e);
            jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, runTraceHistory.f);
            jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, runTraceHistory.g);
            if (TextUtils.isEmpty(runTraceHistory.h)) {
                jSONObject.put("imgurl", "");
            } else {
                StringBuilder sb = new StringBuilder("file:/");
                sb.append(this.mContext.getFilesDir().getPath());
                sb.append(File.separator);
                sb.append("runTrace");
                sb.append(File.separator);
                sb.append(runTraceHistory.h);
                jSONObject.put("imgurl", sb.toString());
            }
            jSONObject.put("type", runTraceHistory.j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    /* access modifiers changed from: private */
    public void sortRunHistory(List<RunTraceHistory> list) {
        if (this.mComparator == null) {
            this.mComparator = new Comparator<RunTraceHistory>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    return eax.a(((RunTraceHistory) obj2).f, ((RunTraceHistory) obj).f);
                }
            };
        }
        if (list != null && list.size() > 1) {
            Collections.sort(list, this.mComparator);
        }
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

    @AjxMethod("setEndPoi")
    public void setEndPoi(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            POI createPOI = POIFactory.createPOI(jSONObject.getString("name"), new GeoPoint(jSONObject.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON), jSONObject.getDouble("lat")));
            if (this.mEndPoiChangeListener != null) {
                this.mEndPoiChangeListener.onEndPoiChangeListener(createPOI);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @AjxMethod("fetchRouteRequest")
    public void fetchRouteRequest(JsFunctionCallback jsFunctionCallback) {
        this.mJsCallBack = jsFunctionCallback;
    }

    public void requestFootRoute(String str) {
        if (this.mJsCallBack != null) {
            this.mJsCallBack.callback(str);
        }
    }

    public void setWeatherData(String str) {
        this.mWeatherData = str;
    }

    @AjxMethod(invokeMode = "sync", value = "getSyncRequestData")
    public String getSyncRequestData() {
        return this.mRequestData;
    }

    public void setRequestData(String str) {
        this.mRequestData = str;
    }

    public String getErrorReportData() {
        return this.mErrorReportData;
    }

    @AjxMethod("finshRideNavi")
    public void finshRideNavi(JsFunctionCallback jsFunctionCallback) {
        this.mJsNaviFinshCallBack = jsFunctionCallback;
    }

    public void finishRideNaviCallBack() {
        if (this.mJsNaviFinshCallBack != null) {
            this.mJsNaviFinshCallBack.callback(new Object[0]);
        }
    }

    @AjxMethod("showCompassView")
    public void showCompassView(JsFunctionCallback jsFunctionCallback) {
        this.mOnCompassShowCallBack = jsFunctionCallback;
    }

    public void showCompassView(boolean z) {
        if (this.mOnCompassShowCallBack != null) {
            JsFunctionCallback jsFunctionCallback = this.mOnCompassShowCallBack;
            Object[] objArr = new Object[1];
            objArr[0] = z ? "true" : "false";
            jsFunctionCallback.callback(objArr);
        }
    }

    @AjxMethod("onRideEndShareClick")
    public void onRideEndShareClick(String str, JsFunctionCallback jsFunctionCallback) {
        this.mRideEndShareClickCallback = jsFunctionCallback;
        if (this.mOnAjxRideEndListener != null && !TextUtils.isEmpty(str)) {
            this.mOnAjxRideEndListener.a(str);
        }
    }

    @com.autonavi.minimap.ajx3.modules.AjxMethod("jump")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startPage(java.lang.String r6, java.lang.String r7) {
        /*
            r5 = this;
            int r0 = r6.hashCode()
            r1 = 2
            r2 = 1
            r3 = 3
            switch(r0) {
                case -2005293913: goto L_0x009e;
                case -1581618112: goto L_0x0093;
                case -1419310402: goto L_0x0089;
                case -1046514932: goto L_0x007e;
                case -1042626835: goto L_0x0074;
                case -680032883: goto L_0x0069;
                case -664515973: goto L_0x005f;
                case -353552518: goto L_0x0054;
                case -292116290: goto L_0x0048;
                case 272721754: goto L_0x003d;
                case 394073396: goto L_0x0031;
                case 430171814: goto L_0x0025;
                case 941439965: goto L_0x0018;
                case 1316784841: goto L_0x000c;
                default: goto L_0x000a;
            }
        L_0x000a:
            goto L_0x00a8
        L_0x000c:
            java.lang.String r0 = "startRun"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 2
            goto L_0x00a9
        L_0x0018:
            java.lang.String r0 = "showCarNavi"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 13
            goto L_0x00a9
        L_0x0025:
            java.lang.String r0 = "showSingleTrack"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 1
            goto L_0x00a9
        L_0x0031:
            java.lang.String r0 = "footNavi"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 9
            goto L_0x00a9
        L_0x003d:
            java.lang.String r0 = "backDefault"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 11
            goto L_0x00a9
        L_0x0048:
            java.lang.String r0 = "showPoiDetail"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 12
            goto L_0x00a9
        L_0x0054:
            java.lang.String r0 = "footPreview"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 8
            goto L_0x00a9
        L_0x005f:
            java.lang.String r0 = "footRoute"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 7
            goto L_0x00a9
        L_0x0069:
            java.lang.String r0 = "footEnd"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 10
            goto L_0x00a9
        L_0x0074:
            java.lang.String r0 = "recommendRoute"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 4
            goto L_0x00a9
        L_0x007e:
            java.lang.String r0 = "showAllTracks"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 6
            goto L_0x00a9
        L_0x0089:
            java.lang.String r0 = "agroup"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 0
            goto L_0x00a9
        L_0x0093:
            java.lang.String r0 = "sharebike"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 5
            goto L_0x00a9
        L_0x009e:
            java.lang.String r0 = "createNewTrack"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00a8
            r0 = 3
            goto L_0x00a9
        L_0x00a8:
            r0 = -1
        L_0x00a9:
            r4 = 0
            switch(r0) {
                case 0: goto L_0x018f;
                case 1: goto L_0x0150;
                case 2: goto L_0x014c;
                case 3: goto L_0x014c;
                case 4: goto L_0x0132;
                case 5: goto L_0x00f4;
                case 6: goto L_0x00f0;
                case 7: goto L_0x00ec;
                case 8: goto L_0x00ec;
                case 9: goto L_0x00ec;
                case 10: goto L_0x00ec;
                case 11: goto L_0x00ec;
                case 12: goto L_0x00cb;
                case 13: goto L_0x00af;
                default: goto L_0x00ad;
            }
        L_0x00ad:
            goto L_0x01aa
        L_0x00af:
            com.autonavi.common.model.POI r6 = defpackage.bnx.a(r7)
            java.lang.Class<dfm> r7 = defpackage.dfm.class
            java.lang.Object r7 = defpackage.ank.a(r7)
            dfm r7 = (defpackage.dfm) r7
            if (r7 == 0) goto L_0x00c0
            r7.a(r6)
        L_0x00c0:
            bid r6 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            java.lang.String r7 = "amap.basemap.action.default_page"
            r6.startPage(r7, r4)
            goto L_0x01aa
        L_0x00cb:
            com.autonavi.common.PageBundle r6 = new com.autonavi.common.PageBundle
            r6.<init>()
            com.autonavi.common.model.POI r7 = defpackage.bnx.a(r7)
            java.lang.String r0 = "POI"
            r6.putObject(r0, r7)
            bid r7 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            java.lang.String r0 = "amap.basemap.action.default_page"
            r7.startPage(r0, r4)
            bid r7 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            java.lang.String r0 = "amap.search.action.poidetail"
            r7.startPage(r0, r6)
            return
        L_0x00ec:
            r5.startFootPage(r6, r7)
            return
        L_0x00f0:
            r5.startRunPage(r3, r4)
            return
        L_0x00f4:
            boolean r6 = android.text.TextUtils.isEmpty(r7)
            if (r6 != 0) goto L_0x011c
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0106 }
            r6.<init>(r7)     // Catch:{ JSONException -> 0x0106 }
            java.lang.String r7 = "firepage"
            java.lang.String r6 = r6.optString(r7)     // Catch:{ JSONException -> 0x0106 }
            goto L_0x010b
        L_0x0106:
            r6 = move-exception
            r6.printStackTrace()
            r6 = r4
        L_0x010b:
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto L_0x011c
            com.autonavi.common.PageBundle r4 = new com.autonavi.common.PageBundle
            r4.<init>()
            java.lang.String r7 = "sharebike_page_from"
            r4.putString(r7, r6)
        L_0x011c:
            esb r6 = defpackage.esb.a.a
            java.lang.Class<bdf> r7 = defpackage.bdf.class
            esc r6 = r6.a(r7)
            bdf r6 = (defpackage.bdf) r6
            if (r6 == 0) goto L_0x0131
            com.autonavi.bundle.sharebike.api.IBikePage r6 = r6.b()
            r6.a(r3, r4)
        L_0x0131:
            return
        L_0x0132:
            esb r6 = defpackage.esb.a.a
            java.lang.Class<bay> r7 = defpackage.bay.class
            esc r6 = r6.a(r7)
            bay r6 = (defpackage.bay) r6
            if (r6 == 0) goto L_0x01aa
            bid r7 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            java.lang.Class r6 = r6.a()
            r7.startPage(r6, r4)
            return
        L_0x014c:
            r5.startRunPage(r2, r4)
            return
        L_0x0150:
            java.lang.String r6 = r5.getTrackIdFromJson(r7)
            com.autonavi.minimap.route.run.beans.RunTraceHistory r6 = r5.getTraceHistoryByTrackId(r6)
            if (r6 != 0) goto L_0x015b
            return
        L_0x015b:
            com.autonavi.common.PageBundle r7 = new com.autonavi.common.PageBundle
            r7.<init>()
            com.autonavi.minimap.route.run.beans.RunTraceHistory$RunType r0 = r6.j
            com.autonavi.minimap.route.run.beans.RunTraceHistory$RunType r4 = com.autonavi.minimap.route.run.beans.RunTraceHistory.RunType.FOOT_TYPE
            if (r0 != r4) goto L_0x0186
            java.lang.String r0 = "url"
            java.lang.String r1 = "path://amap_bundle_foot/src/end_page/FootEndPage.page.js"
            r7.putString(r0, r1)
            java.lang.String r6 = defpackage.efu.b(r6)
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 != 0) goto L_0x017d
            java.lang.String r0 = "jsData"
            r7.putString(r0, r6)
        L_0x017d:
            java.lang.String r6 = "bundle_key_page_from_history"
            r7.putBoolean(r6, r2)
            r5.startFootNaviBundlePage(r3, r7)
            return
        L_0x0186:
            java.lang.String r0 = "data"
            r7.putObject(r0, r6)
            r5.startRunPage(r1, r7)
            return
        L_0x018f:
            java.lang.String r6 = "amapuri://AGroup/joinGroup"
            android.net.Uri r6 = android.net.Uri.parse(r6)
            android.content.Context r7 = r5.getNativeContext()
            boolean r0 = r7 instanceof defpackage.brr
            if (r0 == 0) goto L_0x01aa
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "android.intent.action.VIEW"
            r0.<init>(r1, r6)
            brr r7 = (defpackage.brr) r7
            r7.b(r0)
            return
        L_0x01aa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.footresult.ajx.ModuleFoot.startPage(java.lang.String, java.lang.String):void");
    }

    private void startFootPage(String str, String str2) {
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(str2)) {
            pageBundle.putString("jsData", str2);
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -680032883:
                if (str.equals(START_PAGE_FOOT_END)) {
                    c = 3;
                    break;
                }
                break;
            case -664515973:
                if (str.equals(START_PAGE_FOOT_ROUTE)) {
                    c = 0;
                    break;
                }
                break;
            case -353552518:
                if (str.equals(START_PAGE_FOOT_PREVIEW)) {
                    c = 1;
                    break;
                }
                break;
            case 272721754:
                if (str.equals(BACK_PAGE_DEFAULT)) {
                    c = 4;
                    break;
                }
                break;
            case 394073396:
                if (str.equals(START_PAGE_FOOT_NAVI)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                pageBundle.putString("url", URL_FOOT_ROUTE);
                AMapPageUtil.getPageContext().startPage(AjxFootMapPage.class, pageBundle);
                return;
            case 1:
                eav.a("performance-", "clickFootDetail");
                pageBundle.putString("url", URL_FOOT_BROWSER);
                String footPreviewJson = getFootPreviewJson(str2);
                if (!TextUtils.isEmpty(footPreviewJson)) {
                    pageBundle.putString("jsData", footPreviewJson);
                }
                AMapPageUtil.getPageContext().startPage(AjxFootBrowserPage.class, pageBundle);
                return;
            case 2:
                if (ebm.a(AMapAppGlobal.getTopActivity())) {
                    this.mDestNaviParams = str2;
                    if (edr.a((String) "agree_onfoot_declare", false)) {
                        startFootNaviPage();
                        return;
                    } else {
                        showDisclaimerView();
                        return;
                    }
                }
                break;
            case 3:
                pageBundle.putString("url", URL_FOOT_END);
                String footEndJson = getFootEndJson(str2);
                if (!TextUtils.isEmpty(footEndJson)) {
                    pageBundle.putString("jsData", footEndJson);
                }
                startFootNaviBundlePage(3, pageBundle);
                return;
            case 4:
                AMapPageUtil.getPageContext().finish();
                AMapPageUtil.getPageContext().startPage((String) "amap.basemap.action.default_page", pageBundle);
                break;
        }
    }

    /* access modifiers changed from: private */
    public void startFootNaviPage() {
        eav.a("performance-", "clickFootNavigationBtn");
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(this.mDestNaviParams)) {
            pageBundle.putString("bundle_key_obj_result", this.mDestNaviParams);
        }
        pageBundle.putString("weather", this.mWeatherData);
        startFootNaviBundlePage(1, pageBundle);
    }

    private void showDisclaimerView() {
        this.mConfirmDlg = new ConfirmDlg(AMapAppGlobal.getTopActivity(), this.mAvoidDoubleClickListener, R.layout.onfoot_declare);
        this.mConfirmDlg.show();
    }

    private String getFootEndJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String a = edn.a();
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put("trackStorageFolder", a);
            }
            return JsonUtil.toString(jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private String getFootPreviewJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.put("weather", this.mWeatherData);
            return JsonUtil.toString(jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private String getTrackIdFromJson(String str) {
        try {
            return new JSONObject(str).optString("track_id");
        } catch (JSONException e) {
            e.printStackTrace();
            r3 = "";
            return "";
        }
    }

    private RunTraceHistory getTraceHistoryByTrackId(String str) {
        RunTraceHistory runTraceHistory = null;
        if (this.mRunTraceHistoryList == null || this.mRunTraceHistoryList.size() <= 0 || str == null || str.trim().equals("")) {
            return null;
        }
        for (int i = 0; i < this.mRunTraceHistoryList.size(); i++) {
            runTraceHistory = this.mRunTraceHistoryList.get(i);
            if (runTraceHistory.a.equals(str)) {
                break;
            }
        }
        return runTraceHistory;
    }

    @AjxMethod(invokeMode = "sync", value = "getFootArAccessCloudConfig")
    public boolean getFootArAccessCloudConfig() {
        if (this.footArAccessCloudConfig == null) {
            avi avi = (avi) a.a.a(avi.class);
            if (avi != null) {
                this.footArAccessCloudConfig = Boolean.valueOf(avi.d().a());
            }
        }
        StringBuilder sb = new StringBuilder("----footarCloudConfig:");
        sb.append(this.footArAccessCloudConfig);
        eav.a(null, sb.toString());
        return this.footArAccessCloudConfig.booleanValue();
    }

    @AjxMethod("handleAjxMessage")
    public void handleAjxMessage(int i, String str) {
        int i2;
        AMapLog.e("ModuleRide", "caoyp -- handleAjxMessage key = ".concat(String.valueOf(i)));
        AMapLog.e("ModuleRide", "caoyp -- handleAjxMessage param = ".concat(String.valueOf(str)));
        if (i != 101) {
            boolean z = 0;
            switch (i) {
                case 0:
                    int i3 = 100;
                    try {
                        i3 = getSourceTypeFromAjxPageSource(new JSONObject(str).optString("source_type"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!(i3 == 102 || i3 == 101)) {
                        saveFootHistory(str);
                    }
                    if (this.mResultSuccessListener != null) {
                        this.mResultSuccessListener.onRouteResultSuccess(str);
                    }
                    UpLoadOperationDataUtil.a(OperationType.TYPE_FOOT_ROUTE_SELECT, 0, 0, 0);
                    return;
                case 1:
                    eav.a("tao----", "save trace : ".concat(String.valueOf(str)));
                    efu.a(str);
                    return;
                case 2:
                    edr.b((String) "foot_isindicatorhide", "1".equals(str));
                    return;
                case 3:
                    avi avi = (avi) a.a.a(avi.class);
                    if (avi != null) {
                        if (avi.c().a(AMapPageUtil.getPageContext())) {
                            AMapPageUtil.getPageContext().finish();
                            return;
                        }
                        ArrayList<akc> pagesStacks = AMapPageUtil.getPagesStacks();
                        if (pagesStacks != null && !pagesStacks.isEmpty()) {
                            int size = pagesStacks.size();
                            int i4 = z;
                            while (i4 < size) {
                                bid stackFragment = AMapPageUtil.getStackFragment(i4);
                                boolean a = avi.c().a(stackFragment);
                                if (stackFragment == null || !a) {
                                    i4++;
                                } else {
                                    stackFragment.finish();
                                    return;
                                }
                            }
                        }
                        return;
                    }
                    break;
                case 4:
                    edr.b((String) "foot_navi_mode_880", str);
                    setDirectiveToScene(str);
                    return;
                case 5:
                    if (this.mUnLockGpsBtnListener != null) {
                        this.mUnLockGpsBtnListener.unLockGpsButtonState();
                        return;
                    }
                    break;
                case 6:
                    if (this.mListener != null) {
                        this.mListener.a(str);
                        return;
                    }
                    break;
                case 7:
                    this.mErrorReportData = str;
                    return;
                case 8:
                    if (this.mErrorReportClickListener != null) {
                        this.mErrorReportClickListener.onErrorReportClickBtn(null);
                        return;
                    }
                    break;
                case 9:
                    notifyStatusBarForRideInfoChange(str);
                    return;
                case 10:
                    if (this.mOnAjxFootNaviListener != null) {
                        this.mOnAjxFootNaviListener.a(TextUtils.equals("true", str));
                        return;
                    }
                    break;
                case 11:
                    eav.a("performance-", "startFootRouteRequest");
                    return;
                case 12:
                    eav.a("performance-", "showFootResult");
                    try {
                        i2 = new JSONObject(str).optInt("hasOutDoorPath");
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                        i2 = 0;
                    }
                    if (this.mOnAjxResultListener != null) {
                        OnAjxFootMapInterface onAjxFootMapInterface = this.mOnAjxResultListener;
                        if (i2 == 1) {
                            z = 1;
                        }
                        onAjxFootMapInterface.onExistOutDoorData(z);
                    }
                    eko.a(10000);
                    return;
                case 13:
                    if (TextUtils.equals("manual", str)) {
                        eav.a("performance-", "clickFootExitNavi");
                    }
                    if (this.mOnAjxFootNaviListener != null) {
                        this.mOnAjxFootNaviListener.a();
                        return;
                    }
                    break;
                default:
                    switch (i) {
                        case 15:
                            if (this.mNotifyCalcRouteListener != null) {
                                this.mNotifyCalcRouteListener.onCalcRoute();
                                return;
                            }
                            break;
                        case 16:
                            if (this.mOnAjxRideEndListener != null) {
                                this.mOnAjxRideEndListener.b(str);
                                return;
                            }
                            break;
                        case 17:
                            if (this.mOnAjxFootNaviListener != null) {
                                this.mOnAjxFootNaviListener.b(TextUtils.equals("true", str));
                                return;
                            }
                            break;
                        case 18:
                            if (this.mOnAjxPreviewListener != null) {
                                this.mOnAjxPreviewListener.onIndoorFloorChange(str);
                                return;
                            }
                            break;
                        default:
                            switch (i) {
                                case 20:
                                    break;
                                case 21:
                                    eav.a("strongdog:", "过来了 哈哈哈哈");
                                    if (this.onOpenCompassViewListener != null) {
                                        this.onOpenCompassViewListener.a();
                                        return;
                                    }
                                    break;
                                case 22:
                                    if (this.onFootEndClickUGCListener != null) {
                                        this.onFootEndClickUGCListener.c(str);
                                        break;
                                    }
                                    break;
                                case 23:
                                    Context nativeContext = getNativeContext();
                                    dct dct = new dct(0);
                                    dct.h = true;
                                    dct.g = true;
                                    if (str != null && !"".equals(str)) {
                                        dct.f = true;
                                        dct.d = true;
                                        dct.e = true;
                                        dct.a = true;
                                        try {
                                            JSONObject jSONObject = new JSONObject(str);
                                            POI a2 = bnx.a(jSONObject.optString("startPoi"));
                                            POI a3 = bnx.a(jSONObject.optString("endPoi"));
                                            int optInt = jSONObject.optInt("routeLength");
                                            int optInt2 = jSONObject.optInt("costTime");
                                            a aVar = new a(0);
                                            aVar.a = a2;
                                            aVar.b = a3;
                                            aVar.c = optInt;
                                            aVar.d = optInt2;
                                            RouteSharingUtil.a(nativeContext, dct, aVar);
                                            return;
                                        } catch (JSONException e3) {
                                            e3.printStackTrace();
                                            return;
                                        }
                                    } else {
                                        return;
                                    }
                                case 24:
                                    uploadOperationActivities(str);
                                    return;
                                case 25:
                                    if (this.mOnOperationActivitiesListener != null) {
                                        this.mOnOperationActivitiesListener.c();
                                        return;
                                    }
                                    break;
                                case 26:
                                    if (this.mOnAjxResultListener != null) {
                                        this.mOnAjxResultListener.onSlidingUiStatue(TextUtils.equals(str, "true"));
                                        return;
                                    }
                                    break;
                                case 27:
                                    if (this.onOpenCompassViewListener != null) {
                                        this.onOpenCompassViewListener.b();
                                        return;
                                    }
                                    break;
                                case 28:
                                    if (this.mResultSuccessListener != null) {
                                        this.mResultSuccessListener.onRouteResultFail();
                                    }
                                    if (!eko.a(str)) {
                                        eko.b(str);
                                        return;
                                    }
                                    break;
                                case 29:
                                    saveAchievementToDB(str);
                                    return;
                                case 30:
                                    if (this.mOnAjxFootNaviListener != null) {
                                        this.mOnAjxFootNaviListener.b();
                                        break;
                                    }
                                    break;
                            }
                            if (this.mOnAjxResultListener != null && !TextUtils.isEmpty(str)) {
                                this.mOnAjxResultListener.onIndoorFloorChanged(str);
                                return;
                            }
                            break;
                    }
            }
            return;
        }
        eav.a("performance-", "AjxFootNaviPage upload no pass");
    }

    private void notifyStatusBarForRideInfoChange(String str) {
        eav.a("songping:", "notifyStatusBarForRideInfoChange = ".concat(String.valueOf(str)));
        if (TextUtils.isEmpty(str)) {
            eav.a("updateNotify", "ride updateNotify json is empty");
            return;
        }
        if (this.mOnAjxFootNaviListener != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                String optString = jSONObject.optString("title");
                String optString2 = jSONObject.optString("text");
                boolean optBoolean = jSONObject.optBoolean("force");
                if (TextUtils.isEmpty(optString)) {
                    optString = null;
                }
                if (TextUtils.isEmpty(optString2)) {
                    optString2 = null;
                }
                this.mOnAjxFootNaviListener.a(optString, optString2, optBoolean);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFootHistory(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            POI a = bnx.a(jSONObject.optString("startPoi"));
            POI a2 = bnx.a(jSONObject.optString("endPoi"));
            acg acg = (acg) a.a.a(acg.class);
            if (acg != null) {
                acg.a(a, a2, RouteType.ONFOOT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static int getSourceTypeFromAjxPageSource(String str) {
        if ("source_etrip".equalsIgnoreCase(str)) {
            return 102;
        }
        return "source_save".equalsIgnoreCase(str) ? 101 : 100;
    }

    @AjxMethod("registerUGCStateChange")
    public void registerUGCStateChange(JsFunctionCallback jsFunctionCallback) {
        this.mOnUGCStateChangeCallBack = jsFunctionCallback;
    }

    public void notifyUGCStateChange(String str) {
        if (this.mOnUGCStateChangeCallBack != null) {
            this.mOnUGCStateChangeCallBack.callback(str);
        }
    }

    @AjxMethod("onRideAccuracyChanged")
    public void onRideAccuracyChanged(JsFunctionCallback jsFunctionCallback) {
        this.mOnRideAccuracyChanged = jsFunctionCallback;
    }

    public void setOnRideAccuracyChanged(boolean z) {
        if (this.mOnRideAccuracyChanged != null) {
            this.mOnRideAccuracyChanged.callback(Boolean.valueOf(z));
            setNotifyMagInterfere(z);
        }
    }

    @AjxMethod("onOutDoorLineFocus")
    public void onOutDoorLineFocus(JsFunctionCallback jsFunctionCallback) {
        this.mOnOutDoorLineFocus = jsFunctionCallback;
    }

    public void setOnOutDoorLineFocus() {
        if (this.mOnOutDoorLineFocus != null) {
            this.mOnOutDoorLineFocus.callback(new Object[0]);
        }
    }

    @AjxMethod("saveRoute")
    public void saveRoute(String str, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            if (this.mOnRouteSaveEventListener != null) {
                jsFunctionCallback.callback(Boolean.valueOf(this.mOnRouteSaveEventListener.onRouteSaveEvent(str)));
                return;
            }
            jsFunctionCallback.callback(Boolean.FALSE);
        }
    }

    @AjxMethod("checkRouteSaved")
    public void checkRouteSaved(String str, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Boolean.valueOf(!TextUtils.isEmpty(ecu.a(str))));
        }
    }

    @AjxMethod("jumpToSelPoiPage")
    public void jumpToSelPoiPage(String str, JsFunctionCallback jsFunctionCallback) {
        this.mOnJunmSelPoiPageCallBack = jsFunctionCallback;
        if (this.mOnAjxFootNaviListener != null) {
            this.mOnAjxFootNaviListener.b(str);
        }
    }

    public void notifySelPoiPage(String str) {
        if (this.mOnJunmSelPoiPageCallBack != null) {
            this.mOnJunmSelPoiPageCallBack.callback(str);
        }
    }

    private void uploadOperationActivities(String str) {
        int i;
        int i2;
        if (!TextUtils.isEmpty(str)) {
            int i3 = 0;
            try {
                JSONObject jSONObject = new JSONObject(str);
                i = jSONObject.optInt("startTime");
                try {
                    i2 = jSONObject.optInt(AppInitCallback.SP_KEY_endTime);
                } catch (JSONException e) {
                    e = e;
                    i2 = 0;
                    e.printStackTrace();
                    UpLoadOperationDataUtil.a(OperationType.TYPE_FOOT_NAV, i, i2, i3);
                }
                try {
                    i3 = jSONObject.optInt("distance");
                } catch (JSONException e2) {
                    e = e2;
                    e.printStackTrace();
                    UpLoadOperationDataUtil.a(OperationType.TYPE_FOOT_NAV, i, i2, i3);
                }
            } catch (JSONException e3) {
                e = e3;
                i = 0;
                i2 = 0;
                e.printStackTrace();
                UpLoadOperationDataUtil.a(OperationType.TYPE_FOOT_NAV, i, i2, i3);
            }
            UpLoadOperationDataUtil.a(OperationType.TYPE_FOOT_NAV, i, i2, i3);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getScreenWidth")
    public String getScreenWidth() {
        return String.valueOf(getNativeContext().getResources().getDisplayMetrics().widthPixels);
    }

    @AjxMethod("getDistance")
    public void getDistance(String str, final JsFunctionCallback jsFunctionCallback) {
        final long j;
        if (!TextUtils.isEmpty(str) && jsFunctionCallback != null) {
            if (TextUtils.equals("yesterday", str)) {
                j = getYesterdayTime().longValue();
            } else {
                j = getBeforYeterdayTime().longValue();
            }
            ebr.a(false).post(new Runnable() {
                public final void run() {
                    AMapPageUtil.getAppContext();
                    int i = 0;
                    for (btg btg : bsq.a().b(j)) {
                        i += btg.c.intValue();
                    }
                    jsFunctionCallback.callback(Integer.valueOf(i));
                }
            });
        }
    }

    private Long getYesterdayTime() {
        long currentTimeMillis = System.currentTimeMillis();
        return Long.valueOf(currentTimeMillis - ((28800000 + currentTimeMillis) % 86400000));
    }

    private Long getBeforYeterdayTime() {
        long currentTimeMillis = System.currentTimeMillis() - 86400000;
        return Long.valueOf(currentTimeMillis - ((28800000 + currentTimeMillis) % 86400000));
    }

    private void saveAchievementToDB(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                defpackage.efu.AnonymousClass3 r1 = new Runnable(jSONObject.optLong("startTime"), jSONObject.optInt("his_distance"), jSONObject.optInt("ranking"), jSONObject.optInt("percent"), jSONObject.optString("img"), jSONObject.optInt("rising")) {
                    final /* synthetic */ long a;
                    final /* synthetic */ int b;
                    final /* synthetic */ int c;
                    final /* synthetic */ int d;
                    final /* synthetic */ String e;
                    final /* synthetic */ int f;

                    {
                        this.a = r1;
                        this.b = r3;
                        this.c = r4;
                        this.d = r5;
                        this.e = r6;
                        this.f = r7;
                    }

                    public final void run() {
                        AMapPageUtil.getAppContext();
                        btg a2 = bsq.a().a(this.a);
                        if (a2 != null) {
                            a2.l = Integer.valueOf(this.b);
                            a2.m = Integer.valueOf(this.c);
                            a2.n = Integer.valueOf(this.d);
                            a2.q = this.e;
                            a2.o = Integer.valueOf(1);
                            a2.p = Integer.valueOf(this.f);
                            AMapPageUtil.getAppContext();
                            bsq.a().a(a2);
                        }
                    }
                };
                ahm.a(r1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "openVoiceDebug")
    public boolean openVoiceDebug() {
        return ebb.c();
    }

    private void setDirectiveToScene(String str) {
        try {
            int parseInt = Integer.parseInt(str);
            eav.a("song---", "setDirectiveToScene param = ".concat(String.valueOf(parseInt)));
            if (parseInt == 3) {
                parseInt = 0;
            }
            LocManager.setScene(2, 2, parseInt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNotifyMagInterfere(boolean z) {
        if (!z) {
            LocManager.notifyMagInterfere();
        }
    }

    private void startRunPage(@PageType int i, PageBundle pageBundle) {
        avo avo = (avo) a.a.a(avo.class);
        if (avo != null) {
            avo.a().a(i, pageBundle);
        }
    }

    private void startFootNaviBundlePage(@IFootNaviPage.PageType int i, PageBundle pageBundle) {
        avi avi = (avi) a.a.a(avi.class);
        if (avi != null) {
            avi.c().a(i, pageBundle);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getFootNaviType")
    public int getFootNaviType() {
        avi avi = (avi) a.a.a(avi.class);
        if (avi != null) {
            return avi.d().b();
        }
        return 0;
    }

    @AjxMethod(invokeMode = "sync", value = "setFootNaviType")
    public void setFootNaviType(int i) {
        avi avi = (avi) a.a.a(avi.class);
        if (avi != null) {
            avi.d().a(i);
        }
    }
}
