package com.autonavi.bundle.rideresult.ajx;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.cloudconfig.appinit.request.AppInitCallback;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.route.ajx.inter.OnAjxRideNaviInterface;
import com.autonavi.minimap.route.ajx.inter.OnEndPoiChangeInterface;
import com.autonavi.minimap.route.ajx.inter.OnErrorReportClickInterface;
import com.autonavi.minimap.route.ajx.inter.OnNotifyCalcRouteListener;
import com.autonavi.minimap.route.ajx.inter.RouteResultSuccessInterface;
import com.autonavi.minimap.route.ajx.inter.RouteRideShowEleAnimInterface;
import com.autonavi.minimap.route.ajx.inter.UnLockGpsButtonInterface;
import com.autonavi.minimap.route.ajx.module.ModuleValues;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil.OperationType;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.a;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.b;
import com.autonavi.minimap.widget.ConfirmDlg;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("route_ride")
public class ModuleRide extends AbstractModule {
    private static final String BACK_PAGE_DEFAULT = "backDefault";
    private static final int CALC_ROUTE_FROM_DATA = 15;
    private static final int ERROR_REPORT_CLICK = 8;
    private static final int ERROR_REPORT_DATA = 7;
    private static final int FINISH_PAGE_MESSAGE = 3;
    public static final String MODULE_NAME = "route_ride";
    private static final int NAVI_DIRECTION_MODE = 4;
    private static final int NAVI_FINISH_TO_PREVIEW = 13;
    private static final int NAVI_UPDATE_NOTIFY = 9;
    private static final int NAVI_VOICE_STATUS_CHANGE = 10;
    private static final int NAVI_VOICE_TOAST = 17;
    private static final int RESULT_ERROR_CODE_CALLBACK = 28;
    private static final int RESULT_LIST_SHOW = 12;
    private static final int SAVE_NAVI_BAR_STATE_MESSAGE = 2;
    private static final int SAVE_TRACE_RESULT_MESSAGE = 1;
    private static final int SHOW_RESULT_MESSAGE = 0;
    private static final int SNAP_SHOT_IMG_FINISH = 16;
    private static final int START_CALCULATE_RIDE_ROUTE = 11;
    private static final String START_CAR_NAVI = "showCarNavi";
    private static final String START_PAGE_AGROUP = "agroup";
    private static final String START_PAGE_CREATENEWTRACK = "createNewTrack";
    private static final String START_PAGE_RIDE_END = "rideEnd";
    private static final String START_PAGE_RIDE_NAVI = "rideNavi";
    private static final String START_PAGE_RIDE_PREVIEW = "ridePreview";
    private static final String START_PAGE_RIDE_ROUTE = "rideRoute";
    private static final String START_PAGE_SHAREBIKE = "sharebike";
    private static final String START_PAGE_SHOWALLTRACKS = "showAllTracks";
    private static final String START_PAGE_SINGLETRACK = "showSingleTrack";
    private static final String START_PAGE_STARTRUN = "startRide";
    private static final int UNLOCK_GPS_BUTTON = 5;
    private static final int UPDATE_ZOOM_POS = 6;
    private static final int UPLOAD_OPERATION_ACTIVITIES = 24;
    private OnClickListener mAvoidDoubleClickListener = new OnClickListener() {
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.cancel) {
                if (ModuleRide.this.mConfirmDlg != null && ModuleRide.this.mConfirmDlg.isShowing()) {
                    ModuleRide.this.mConfirmDlg.dismiss();
                    ModuleRide.this.mConfirmDlg = null;
                }
            } else if (id == R.id.confirm) {
                edr.b((String) "agree_ondest_declare", true);
                ModuleRide.this.startRideNaviPage();
            }
        }
    };
    /* access modifiers changed from: private */
    public ConfirmDlg mConfirmDlg = null;
    private Context mContext;
    private String mDestNaviParams = null;
    private RouteRideShowEleAnimInterface mEleRemindAnimListener;
    private OnEndPoiChangeInterface mEndPoiChangeListener;
    private OnErrorReportClickInterface mErrorReportClickListener;
    private String mErrorReportData = null;
    private axi mHistoryItemClickListener;
    private JsFunctionCallback mJsCallBack = null;
    private JsFunctionCallback mJsNaviFinshCallBack = null;
    private axj mListener;
    private OnNotifyCalcRouteListener mNotifyCalcRouteListener;
    private axl mOnAjxRideEndListener;
    private OnAjxRideNaviInterface mOnAjxRideNaviListener;
    private JsFunctionCallback mOnRideAccuracyChanged;
    private JsFunctionCallback mRegisterPhoneCallback;
    private String mRequestData = null;
    private RouteResultSuccessInterface mResultSuccessListener;
    private JsFunctionCallback mRideEndShareClickCallback;
    /* access modifiers changed from: private */
    public List<RideTraceHistory> mRideTraceHistoryList = null;
    private UnLockGpsButtonInterface mUnLockGpsBtnListener;
    private String mWeatherData = "";

    public ModuleRide(IAjxContext iAjxContext) {
        super(iAjxContext);
        this.mContext = iAjxContext.getNativeContext();
        this.mWeatherData = "";
    }

    public void setHistoryItemClickListener(axi axi) {
        this.mHistoryItemClickListener = axi;
    }

    public void setEndPoiChangeListener(OnEndPoiChangeInterface onEndPoiChangeInterface) {
        this.mEndPoiChangeListener = onEndPoiChangeInterface;
    }

    public void setEleRemindAnimListener(RouteRideShowEleAnimInterface routeRideShowEleAnimInterface) {
        this.mEleRemindAnimListener = routeRideShowEleAnimInterface;
    }

    public void setRideEndShareListener(axl axl) {
        this.mOnAjxRideEndListener = axl;
    }

    public void setUnLockGpsBtnListener(UnLockGpsButtonInterface unLockGpsButtonInterface) {
        this.mUnLockGpsBtnListener = unLockGpsButtonInterface;
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

    @AjxMethod("getTrackData")
    public String getRideTraceHistory(JsFunctionCallback jsFunctionCallback) {
        return getRideHistory(jsFunctionCallback);
    }

    private String getRideHistory(final JsFunctionCallback jsFunctionCallback) {
        ahl.a(new a<String>() {
            public final void onError(Throwable th) {
            }

            public final /* synthetic */ void onFinished(Object obj) {
                String str = (String) obj;
                AMapLog.e("ModuleFoot", "caoyp -1- ".concat(String.valueOf(str)));
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str);
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                ModuleRide.this.mRideTraceHistoryList = ees.b();
                if (ModuleRide.this.mRideTraceHistoryList == null || ModuleRide.this.mRideTraceHistoryList.size() <= 0) {
                    return "";
                }
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < ModuleRide.this.mRideTraceHistoryList.size(); i++) {
                    jSONArray.put(ModuleRide.this.parseRideTraceHistoryToJson((RideTraceHistory) ModuleRide.this.mRideTraceHistoryList.get(i)));
                }
                return jSONArray.toString();
            }
        }, ahn.b());
        return "";
    }

    /* access modifiers changed from: private */
    public JSONObject parseRideTraceHistoryToJson(RideTraceHistory rideTraceHistory) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("track_id", rideTraceHistory.a);
            jSONObject.put("time", rideTraceHistory.b);
            jSONObject.put("distance", rideTraceHistory.c);
            jSONObject.put("calorie", rideTraceHistory.d);
            jSONObject.put("average_speed", rideTraceHistory.e);
            jSONObject.put("max_speed", rideTraceHistory.f);
            jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, rideTraceHistory.g);
            jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, rideTraceHistory.h);
            if (TextUtils.isEmpty(rideTraceHistory.i)) {
                jSONObject.put("imgurl", "");
            } else {
                StringBuilder sb = new StringBuilder("file:/");
                sb.append(this.mContext.getFilesDir().getPath());
                sb.append(File.separator);
                sb.append("rideTrace");
                sb.append(File.separator);
                sb.append(rideTraceHistory.i);
                jSONObject.put("imgurl", sb.toString());
            }
            jSONObject.put("type", rideTraceHistory.k);
            jSONObject.put("start_name", rideTraceHistory.l);
            jSONObject.put("end_name", rideTraceHistory.m);
            if (rideTraceHistory.k == RideType.SHARE_RIDE_TYPE) {
                eab a = eaa.a().a(rideTraceHistory.i);
                String str = "";
                if (a != null) {
                    str = new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue(a.b, "");
                    if (TextUtils.isEmpty(str)) {
                        if ("mobike".equalsIgnoreCase(a.b)) {
                            str = "摩拜";
                        } else if ("ofo".equalsIgnoreCase(a.b)) {
                            str = "ofo";
                        }
                    }
                }
                jSONObject.put("cpname", str);
            }
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

    @AjxMethod("fetchRouteRequest")
    public void fetchRouteRequest(JsFunctionCallback jsFunctionCallback) {
        this.mJsCallBack = jsFunctionCallback;
    }

    public void requestRideRoute(String str) {
        if (this.mJsCallBack != null) {
            this.mJsCallBack.callback(str);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getSyncRequestData")
    public String getSyncRequestData() {
        return this.mRequestData;
    }

    public void setRequestData(String str) {
        this.mRequestData = str;
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

    @AjxMethod("setRideType")
    public void setRideType(String str) {
        try {
            edr.a(new JSONObject(str).getInt("ride_type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setWeatherData(String str) {
        this.mWeatherData = str;
    }

    public void setWidgetPosListener(axj axj) {
        this.mListener = axj;
    }

    public String getErrorReportData() {
        return this.mErrorReportData;
    }

    @AjxMethod("handleAjxMessage")
    public void handleAjxMessage(int i, String str) {
        OperationType operationType;
        AMapLog.e("ModuleRide", "caoyp -- handleAjxMessage key = ".concat(String.valueOf(i)));
        AMapLog.e("ModuleRide", "caoyp -- handleAjxMessage param = ".concat(String.valueOf(str)));
        if (i != 24) {
            if (i != 28) {
                boolean z = true;
                switch (i) {
                    case 0:
                        int i2 = 100;
                        try {
                            i2 = getSourceTypeFromAjxPageSource(new JSONObject(str).optString("source_type"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (i2 != 102) {
                            if (this.mEleRemindAnimListener != null) {
                                this.mEleRemindAnimListener.showEleRemindAnimation();
                            }
                            saveRideHistory(str);
                        }
                        if (this.mResultSuccessListener != null) {
                            this.mResultSuccessListener.onRouteResultSuccess(str);
                        }
                        if (edr.a() != 1) {
                            z = false;
                        }
                        if (z) {
                            operationType = OperationType.TYPE_ELE_RIDE_ROUTE_SELECT;
                        } else {
                            operationType = OperationType.TYPE_RIDE_ROUTE_SELECT;
                        }
                        UpLoadOperationDataUtil.a(operationType, 0, 0, 0);
                        return;
                    case 1:
                        edp.a("tao----", "save trace : ".concat(String.valueOf(str)));
                        saveAjxRideNaviEndData(str);
                        return;
                    case 2:
                        edr.b((String) "ride_isindicatorhide", "1".equals(str));
                        return;
                    case 3:
                        aww aww = (aww) a.a.a(aww.class);
                        if (aww != null) {
                            Class<? extends AbstractBasePage> a = aww.a().a(1);
                            if (a == null || !a.equals(AMapPageUtil.getTopPageClass())) {
                                ArrayList<akc> pagesStacks = AMapPageUtil.getPagesStacks();
                                if (pagesStacks != null && !pagesStacks.isEmpty()) {
                                    int size = pagesStacks.size();
                                    for (int i3 = 0; i3 < size; i3++) {
                                        bid stackFragment = AMapPageUtil.getStackFragment(i3);
                                        if (aww.a().a(stackFragment)) {
                                            stackFragment.finish();
                                            return;
                                        }
                                    }
                                }
                                return;
                            }
                            AMapPageUtil.getPageContext().finish();
                            return;
                        }
                        return;
                    case 4:
                        edr.b((String) "destnavimodewithangle", "1".equals(str));
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
                        if (this.mOnAjxRideNaviListener != null) {
                            this.mOnAjxRideNaviListener.onVoiceStatusChange(TextUtils.equals("true", str));
                            return;
                        }
                        break;
                    case 11:
                        edp.a("performance-", "startRideRouteRequest");
                        return;
                    case 12:
                        edp.a("performance-", "showRideResult");
                        eko.a(10000);
                        return;
                    case 13:
                        if (TextUtils.equals("manual", str)) {
                            edp.a("performance-", "clickExitNavi");
                        }
                        if (this.mOnAjxRideNaviListener != null) {
                            this.mOnAjxRideNaviListener.onExitPage();
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
                                if (this.mOnAjxRideNaviListener != null) {
                                    this.mOnAjxRideNaviListener.onVoiceToast(TextUtils.equals("true", str));
                                    return;
                                }
                                break;
                        }
                }
            } else {
                if (this.mResultSuccessListener != null) {
                    this.mResultSuccessListener.onRouteResultFail();
                }
                if (!eko.a(str)) {
                    eko.b(str);
                }
            }
            return;
        }
        uploadOperationActivities(str);
    }

    private void saveAjxRideNaviEndData(String str) {
        try {
            RideTraceHistory rideTraceHistory = new RideTraceHistory();
            JSONObject jSONObject = new JSONObject(new JSONObject(str).optString("trackInfo"));
            rideTraceHistory.e = jSONObject.optDouble("averageSpeed");
            rideTraceHistory.f = jSONObject.optDouble("maxSpeed");
            rideTraceHistory.d = jSONObject.optInt("calorie");
            rideTraceHistory.g = jSONObject.optLong("startTime");
            rideTraceHistory.h = jSONObject.optLong(AppInitCallback.SP_KEY_endTime);
            int optInt = jSONObject.optInt("drivenTime");
            if (optInt == 0) {
                optInt = ((int) (rideTraceHistory.h - rideTraceHistory.g)) / 1000;
            }
            rideTraceHistory.b = optInt;
            rideTraceHistory.c = jSONObject.optInt("distance");
            rideTraceHistory.l = jSONObject.optString("startName");
            rideTraceHistory.m = jSONObject.optString("endName");
            b bVar = new b();
            bVar.a = POIFactory.createPOI();
            JSONObject optJSONObject = jSONObject.optJSONObject("startPoint");
            bVar.a.getPoint().x = optJSONObject.getInt(DictionaryKeys.CTRLXY_X);
            bVar.a.getPoint().y = optJSONObject.getInt(DictionaryKeys.CTRLXY_Y);
            bVar.a.setName(rideTraceHistory.l);
            bVar.b = POIFactory.createPOI();
            JSONObject optJSONObject2 = jSONObject.optJSONObject("endPoint");
            bVar.b.getPoint().x = optJSONObject2.getInt(DictionaryKeys.CTRLXY_X);
            bVar.b.getPoint().y = optJSONObject2.getInt(DictionaryKeys.CTRLXY_Y);
            bVar.b.setName(rideTraceHistory.m);
            bVar.c = POIFactory.createPOI();
            JSONObject optJSONObject3 = jSONObject.optJSONObject("exitPoint");
            GeoPoint point = bVar.c.getPoint();
            point.x = optJSONObject3.getInt(DictionaryKeys.CTRLXY_X);
            point.y = optJSONObject3.getInt(DictionaryKeys.CTRLXY_Y);
            if (point.x > 0 && point.y > 0) {
                bVar.d = true;
            }
            bVar.e = new ArrayList<>();
            JSONArray jSONArray = jSONObject.getJSONArray("trackPoints");
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                a aVar = new a();
                GeoPoint geoPoint = new GeoPoint();
                geoPoint.x = jSONObject2.optInt(DictionaryKeys.CTRLXY_X);
                geoPoint.y = jSONObject2.optInt(DictionaryKeys.CTRLXY_Y);
                aVar.a = POIFactory.createPOI();
                aVar.a.setPoint(geoPoint);
                bVar.e.add(aVar);
            }
            rideTraceHistory.j = bVar;
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            rideTraceHistory.a = agy.a(sb.toString());
            rideTraceHistory.k = RideType.DEST_TYPE;
            rideTraceHistory.i = jSONObject.optString("imagePath");
            ees.a(rideTraceHistory);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static int getSourceTypeFromAjxPageSource(String str) {
        return "source_etrip".equalsIgnoreCase(str) ? 102 : 100;
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00db  */
    @com.autonavi.minimap.ajx3.modules.AjxMethod("jump")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startPage(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            int r0 = r5.hashCode()
            r1 = 0
            r2 = 2
            switch(r0) {
                case -2129354630: goto L_0x007a;
                case -2005293913: goto L_0x0070;
                case -1581618112: goto L_0x0065;
                case -1525867330: goto L_0x0059;
                case -1419310402: goto L_0x004f;
                case -1228239952: goto L_0x0044;
                case -1046514932: goto L_0x0039;
                case -53136335: goto L_0x002e;
                case 272721754: goto L_0x0023;
                case 430171814: goto L_0x0018;
                case 1197696195: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x0085
        L_0x000b:
            java.lang.String r0 = "rideEnd"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0085
            r0 = 9
            goto L_0x0086
        L_0x0018:
            java.lang.String r0 = "showSingleTrack"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0085
            r0 = 1
            goto L_0x0086
        L_0x0023:
            java.lang.String r0 = "backDefault"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0085
            r0 = 10
            goto L_0x0086
        L_0x002e:
            java.lang.String r0 = "rideRoute"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0085
            r0 = 6
            goto L_0x0086
        L_0x0039:
            java.lang.String r0 = "showAllTracks"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0085
            r0 = 5
            goto L_0x0086
        L_0x0044:
            java.lang.String r0 = "ridePreview"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0085
            r0 = 7
            goto L_0x0086
        L_0x004f:
            java.lang.String r0 = "agroup"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0085
            r0 = 0
            goto L_0x0086
        L_0x0059:
            java.lang.String r0 = "rideNavi"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0085
            r0 = 8
            goto L_0x0086
        L_0x0065:
            java.lang.String r0 = "sharebike"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0085
            r0 = 4
            goto L_0x0086
        L_0x0070:
            java.lang.String r0 = "createNewTrack"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0085
            r0 = 3
            goto L_0x0086
        L_0x007a:
            java.lang.String r0 = "startRide"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0085
            r0 = 2
            goto L_0x0086
        L_0x0085:
            r0 = -1
        L_0x0086:
            r3 = 0
            switch(r0) {
                case 0: goto L_0x0160;
                case 1: goto L_0x00f2;
                case 2: goto L_0x00ec;
                case 3: goto L_0x00e6;
                case 4: goto L_0x0095;
                case 5: goto L_0x0091;
                case 6: goto L_0x008c;
                case 7: goto L_0x008c;
                case 8: goto L_0x008c;
                case 9: goto L_0x008c;
                case 10: goto L_0x008c;
                default: goto L_0x008a;
            }
        L_0x008a:
            goto L_0x017b
        L_0x008c:
            r4.startRidePage(r5, r6)
            goto L_0x017b
        L_0x0091:
            r4.goToShareBikePage(r3)
            return
        L_0x0095:
            boolean r5 = android.text.TextUtils.isEmpty(r6)
            if (r5 != 0) goto L_0x00e2
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00af }
            r5.<init>(r6)     // Catch:{ JSONException -> 0x00af }
            java.lang.String r6 = "firepage"
            java.lang.String r6 = r5.optString(r6)     // Catch:{ JSONException -> 0x00af }
            java.lang.String r0 = "endPoi"
            java.lang.String r5 = r5.optString(r0)     // Catch:{ JSONException -> 0x00ad }
            goto L_0x00b5
        L_0x00ad:
            r5 = move-exception
            goto L_0x00b1
        L_0x00af:
            r5 = move-exception
            r6 = r3
        L_0x00b1:
            r5.printStackTrace()
            r5 = r3
        L_0x00b5:
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 != 0) goto L_0x00c7
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r2 = "sharebike_page_from"
            r0.putString(r2, r6)
            r3 = r0
        L_0x00c7:
            java.lang.String r6 = "sharebikenew"
            defpackage.edr.b(r6, r1)
            esb r6 = defpackage.esb.a.a
            java.lang.Class<bdf> r0 = defpackage.bdf.class
            esc r6 = r6.a(r0)
            bdf r6 = (defpackage.bdf) r6
            if (r6 == 0) goto L_0x00e2
            com.autonavi.common.model.POI r5 = defpackage.bnx.a(r5)
            r6.a(r5)
        L_0x00e2:
            r4.goToShareBikePage(r3)
            return
        L_0x00e6:
            java.lang.String r5 = "2"
            r4.startPageRideMap(r5)
            return
        L_0x00ec:
            java.lang.String r5 = "1"
            r4.startPageRideMap(r5)
            return
        L_0x00f2:
            java.lang.String r5 = r4.getTrackIdFromJson(r6)
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r5 = r4.getTraceHistoryByTrackId(r5)
            com.autonavi.common.PageBundle r6 = new com.autonavi.common.PageBundle
            r6.<init>()
            java.lang.String r0 = "bundle_key_page_from"
            java.lang.String r1 = "1"
            r6.putString(r0, r1)
            java.lang.String r0 = "bundle_key_back_page"
            java.lang.String r1 = "page_go_back_last_page"
            r6.putString(r0, r1)
            java.lang.String r0 = "data"
            r6.putObject(r0, r5)
            if (r5 == 0) goto L_0x014a
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$RideType r0 = r5.k
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$RideType r1 = com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType.SHARE_RIDE_TYPE
            if (r0 != r1) goto L_0x014a
            eaa r0 = defpackage.eaa.a()
            java.lang.String r5 = r5.i
            eab r5 = r0.a(r5)
            if (r5 == 0) goto L_0x0134
            java.lang.String r0 = "bundle_orderid_key"
            java.lang.String r1 = r5.a
            r6.putString(r0, r1)
            java.lang.String r0 = "bundle_cpsource_key"
            java.lang.String r5 = r5.b
            r6.putString(r0, r5)
        L_0x0134:
            esb r5 = defpackage.esb.a.a
            java.lang.Class<bdf> r0 = defpackage.bdf.class
            esc r5 = r5.a(r0)
            bdf r5 = (defpackage.bdf) r5
            if (r5 == 0) goto L_0x0149
            com.autonavi.bundle.sharebike.api.IBikePage r5 = r5.b()
            r5.a(r2, r6)
        L_0x0149:
            return
        L_0x014a:
            esb r5 = defpackage.esb.a.a
            java.lang.Class<avn> r0 = defpackage.avn.class
            esc r5 = r5.a(r0)
            avn r5 = (defpackage.avn) r5
            if (r5 == 0) goto L_0x015f
            com.autonavi.bundle.healthyride.api.IHRidePage r5 = r5.a()
            r5.a(r2, r6)
        L_0x015f:
            return
        L_0x0160:
            java.lang.String r5 = "amapuri://AGroup/joinGroup"
            android.net.Uri r5 = android.net.Uri.parse(r5)
            android.content.Context r6 = r4.getNativeContext()
            boolean r0 = r6 instanceof defpackage.brr
            if (r0 == 0) goto L_0x017b
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "android.intent.action.VIEW"
            r0.<init>(r1, r5)
            brr r6 = (defpackage.brr) r6
            r6.b(r0)
            return
        L_0x017b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.rideresult.ajx.ModuleRide.startPage(java.lang.String, java.lang.String):void");
    }

    private void startRidePage(String str, String str2) {
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(str2)) {
            pageBundle.putString("jsData", str2);
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -1525867330:
                if (str.equals(START_PAGE_RIDE_NAVI)) {
                    c = 2;
                    break;
                }
                break;
            case -1228239952:
                if (str.equals(START_PAGE_RIDE_PREVIEW)) {
                    c = 1;
                    break;
                }
                break;
            case -53136335:
                if (str.equals(START_PAGE_RIDE_ROUTE)) {
                    c = 0;
                    break;
                }
                break;
            case 272721754:
                if (str.equals(BACK_PAGE_DEFAULT)) {
                    c = 4;
                    break;
                }
                break;
            case 941439965:
                if (str.equals(START_CAR_NAVI)) {
                    c = 5;
                    break;
                }
                break;
            case 1197696195:
                if (str.equals(START_PAGE_RIDE_END)) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                pageBundle.putString("url", ModuleValues.URL_RIDE_ROUTE);
                awy awy = (awy) a.a.a(awy.class);
                if (awy != null) {
                    awy.a().a(1, pageBundle);
                    return;
                }
                break;
            case 1:
                edp.a("performance-", "clickRideDetail");
                pageBundle.putString("url", ModuleValues.URL_RIDE_BROWSER);
                String ridePreviewJson = getRidePreviewJson(str2);
                if (!TextUtils.isEmpty(ridePreviewJson)) {
                    pageBundle.putString("jsData", ridePreviewJson);
                }
                awy awy2 = (awy) a.a.a(awy.class);
                if (awy2 != null) {
                    awy2.a().a(2, pageBundle);
                    return;
                }
                break;
            case 2:
                if (ebm.a(AMapAppGlobal.getTopActivity())) {
                    this.mDestNaviParams = str2;
                    if (edr.a((String) "agree_ondest_declare", false)) {
                        startRideNaviPage();
                        return;
                    } else {
                        showDisclaimerView();
                        return;
                    }
                }
                break;
            case 3:
                pageBundle.putString("url", ModuleValues.URL_RIDE_END);
                String rideEndJson = getRideEndJson(str2);
                if (!TextUtils.isEmpty(rideEndJson)) {
                    pageBundle.putString("jsData", rideEndJson);
                }
                aww aww = (aww) a.a.a(aww.class);
                if (aww != null) {
                    aww.a().a(2, pageBundle);
                    return;
                }
                break;
            case 4:
                AMapPageUtil.getPageContext().finish();
                AMapPageUtil.getPageContext().startPage((String) "amap.basemap.action.default_page", pageBundle);
                return;
            case 5:
                POI a = bnx.a(str2);
                dfm dfm = (dfm) ank.a(dfm.class);
                if (dfm != null) {
                    dfm.a(a);
                }
                AMapPageUtil.getPageContext().startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
                break;
        }
    }

    private void startPageRideMap(String str) {
        if (ebm.a(AMapAppGlobal.getTopActivity())) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("bundle_key_page_from", str);
            avn avn = (avn) a.a.a(avn.class);
            if (avn != null) {
                avn.a().a(1, pageBundle);
            }
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

    private String getRideEndJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.put("ride_type", edr.a());
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

    private String getRidePreviewJson(String str) {
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

    private RideTraceHistory getTraceHistoryByTrackId(String str) {
        RideTraceHistory rideTraceHistory = null;
        if (this.mRideTraceHistoryList == null || this.mRideTraceHistoryList.size() <= 0 || str == null || str.trim().equals("")) {
            return null;
        }
        for (int i = 0; i < this.mRideTraceHistoryList.size(); i++) {
            rideTraceHistory = this.mRideTraceHistoryList.get(i);
            if (rideTraceHistory.a.equals(str)) {
                break;
            }
        }
        return rideTraceHistory;
    }

    /* access modifiers changed from: private */
    public void startRideNaviPage() {
        edp.a("performance-", "clickRideNavigationBtn");
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(this.mDestNaviParams)) {
            pageBundle.putString("bundle_key_obj_result", this.mDestNaviParams);
        }
        pageBundle.putString("weather", this.mWeatherData);
        aww aww = (aww) a.a.a(aww.class);
        if (aww != null) {
            aww.a().a(1, pageBundle);
        }
    }

    private void showDisclaimerView() {
        this.mConfirmDlg = new ConfirmDlg(AMapAppGlobal.getTopActivity(), this.mAvoidDoubleClickListener, R.layout.ondest_declare);
        this.mConfirmDlg.show();
    }

    private void saveRideHistory(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            POI a = bnx.a(jSONObject.optString("startPoi"));
            POI a2 = bnx.a(jSONObject.optString("endPoi"));
            acg acg = (acg) a.a.a(acg.class);
            if (acg != null) {
                acg.a(a, a2, RouteType.RIDE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @AjxMethod("onRideEndShareClick")
    public void onRideEndShareClick(String str, JsFunctionCallback jsFunctionCallback) {
        this.mRideEndShareClickCallback = jsFunctionCallback;
        if (this.mOnAjxRideEndListener != null && !TextUtils.isEmpty(str)) {
            this.mOnAjxRideEndListener.a(str);
        }
    }

    @AjxMethod("onRideAccuracyChanged")
    public void onRideAccuracyChanged(JsFunctionCallback jsFunctionCallback) {
        this.mOnRideAccuracyChanged = jsFunctionCallback;
    }

    public void setOnRideAccuracyChanged(boolean z) {
        if (this.mOnRideAccuracyChanged != null) {
            this.mOnRideAccuracyChanged.callback(Boolean.valueOf(z));
        }
    }

    private void notifyStatusBarForRideInfoChange(String str) {
        edp.a("songping:", "notifyStatusBarForRideInfoChange = ".concat(String.valueOf(str)));
        if (TextUtils.isEmpty(str)) {
            edp.a("updateNotify", "ride updateNotify json is empty");
            return;
        }
        if (this.mOnAjxRideNaviListener != null) {
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
                this.mOnAjxRideNaviListener.onNotifyChange(optString, optString2, optBoolean);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setOnNotifyChangeInterface(OnAjxRideNaviInterface onAjxRideNaviInterface) {
        this.mOnAjxRideNaviListener = onAjxRideNaviInterface;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0039  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void uploadOperationActivities(java.lang.String r6) {
        /*
            r5 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0025 }
            r1.<init>(r6)     // Catch:{ JSONException -> 0x0025 }
            java.lang.String r6 = "startTime"
            int r6 = r1.optInt(r6)     // Catch:{ JSONException -> 0x0025 }
            java.lang.String r2 = "endTime"
            int r2 = r1.optInt(r2)     // Catch:{ JSONException -> 0x0023 }
            java.lang.String r3 = "distance"
            int r1 = r1.optInt(r3)     // Catch:{ JSONException -> 0x0021 }
            goto L_0x002c
        L_0x0021:
            r1 = move-exception
            goto L_0x0028
        L_0x0023:
            r1 = move-exception
            goto L_0x0027
        L_0x0025:
            r1 = move-exception
            r6 = 0
        L_0x0027:
            r2 = 0
        L_0x0028:
            r1.printStackTrace()
            r1 = 0
        L_0x002c:
            int r3 = defpackage.edr.a()
            r4 = 1
            if (r3 != r4) goto L_0x0034
            r0 = 1
        L_0x0034:
            if (r0 == 0) goto L_0x0039
            com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil$OperationType r0 = com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil.OperationType.TYPE_ELE_RIDE_NAV
            goto L_0x003b
        L_0x0039:
            com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil$OperationType r0 = com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil.OperationType.TYPE_RIDE_NAV
        L_0x003b:
            com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil.a(r0, r6, r2, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.rideresult.ajx.ModuleRide.uploadOperationActivities(java.lang.String):void");
    }

    private void goToShareBikePage(PageBundle pageBundle) {
        bdf bdf = (bdf) a.a.a(bdf.class);
        if (bdf != null) {
            bdf.b().a(3, pageBundle);
        }
    }
}
