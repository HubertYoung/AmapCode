package com.autonavi.miniapp.plugin.map.route;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.jni.ae.busplan.MiniAppBusService;
import com.autonavi.jni.ae.busplan.interfaces.IBusServiceListener;
import com.autonavi.jni.ae.busplan.model.BusPlanParam;
import com.autonavi.jni.eyrie.amap.maps.MapViewManager;
import com.autonavi.jni.eyrie.amap.tbt.CustomTextureObserver;
import com.autonavi.jni.eyrie.amap.tbt.NaviEventReceiver;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.jni.eyrie.amap.tbt.TextureLoader;
import com.autonavi.jni.eyrie.amap.tbt.TextureParam;
import com.autonavi.jni.eyrie.amap.tbt.TextureWrapper;
import com.autonavi.jni.eyrie.amap.tbt.scene.NaviBusiness;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper.AmapEngineBusErrors;
import com.autonavi.miniapp.plugin.map.util.AjxTextureUtils;
import com.autonavi.miniapp.plugin.map.util.AjxTextureUtils.StaticTexture;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.util.PathUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MiniAppShowRouteManager {
    private static final int CMD_ATTACH_COMPONENTS = 102001;
    private static final int CMD_CALC_ROUTE = 102003;
    private static final int CMD_CANCEL_CALC_ROUTE = 102006;
    private static final int CMD_CLEAR_ROUTE = 1008;
    private static final int CMD_DETACH_COMPONENTS = 102002;
    private static final int CMD_SET_COMPONENT_ADAPTER = 102007;
    private static final int CMD_SHOW_ROUTE = 102004;
    private static final int CMD_ZOOM_ROUTESET = 102008;
    private static final String CONFIG_104_BUS = MiniAppShowRouteConfigHelper.getBusLineConfig();
    private static final String CONFIG_104_DRIVE = MiniAppShowRouteConfigHelper.getDriveLineConfig();
    private static final String CONFIG_104_RIDE = MiniAppShowRouteConfigHelper.getRideFootLineConfig();
    private static final String CONFIG_104_WALK = CONFIG_104_RIDE;
    public static final int ROUTE_MODE_BUS = 3;
    public static final int ROUTE_MODE_DRIVE = 0;
    public static final int ROUTE_MODE_RIDE = 1;
    public static final int ROUTE_MODE_WALK = 2;
    static final String TAG = "MiniAppShowRouteManager";
    private int engineId;
    /* access modifiers changed from: private */
    public SparseArray<H5BridgeContext> idCallbackMap = new SparseArray<>();
    /* access modifiers changed from: private */
    public Context mContext;
    private MiniAppBusService mMiniAppBusService;
    private NaviEventReceiver mNaviEventReceiver;
    private TextureLoader mTextureLoader;
    private NaviBusiness naviBusiness;
    /* access modifiers changed from: private */
    public SparseArray<OnShowRouteCallback> reqIdAndCallbackMap = new SparseArray<>();
    /* access modifiers changed from: private */
    public AtomicInteger requestId = new AtomicInteger(0);
    private int sceneId;

    public static class BusShowRouteParam implements Serializable {
        public String data;
        public Options options;

        public static class Options implements Serializable {
            public CarLocation carLocation;
            public GuideTips guideTips;

            public static class CarLocation implements Serializable {
                public int visible = 0;
            }

            public static class GuideTips implements Serializable {
                public int visible = 0;
            }
        }
    }

    public static class CalcRouteParam implements Serializable {
        public int calcType;
        public int constrainCode;
        public POI endPOI;
        public List<POI> midPOI;
        public long requestId;
        public int routeMode;
        public POI startPOI;
        public int strategy;
        public int type;

        public static class POI implements Serializable {
            public String angel;
            public double angleComp;
            public double angleGps;
            public int angleType;
            public double fittingCredit;
            public double fittingDir;
            public int floor;
            public String floorName;
            public double gpsCredit;
            public double lat;
            public double lon;
            public double matchingDir;
            public String name;
            public String naviExtCode;
            public List<Pos> naviPos;
            public int overhead;
            public String parentID;
            public String parentName;
            public String parentRel;
            public String parentSimpleName;
            public String poiID;
            public double precision;
            public double radius;
            public double reliability;
            public int roadID;
            public int sigType;
            public double sigshelter;
            public double startDir;
            public int startSpeed;
            public int type;
            public String typeCode;
        }

        public static class Pos implements Serializable {
            double lat;
            double lon;
        }
    }

    public static class NaviEventCalcRouteResp implements Serializable {
        public static final int EVENT_TYPE_CALC_FAIL = 8;
        public static final int EVENT_TYPE_CALC_SUCCEED = 7;
        public int errorCode;
        public int eventType;
        public int requestId;
        public List<Long> routes;
        public String summary;
        public Summary summaryObj;

        public static class Summary implements Serializable {
            public List<PathInfo> pathArray;

            public static class PathInfo implements Serializable {
                public int length;
                public int time;
            }
        }
    }

    public interface OnShowRouteCallback {
        void onFail(int i);

        void onSucceed(int i, int i2);
    }

    public static class ShowRouteParam implements Serializable {
        public Options options;
        public List<Long> routeID;
        public int routeMode;

        public enum DisplayMode {
            WONT_DISPLAY(0),
            ONLY_FIXPOINT(1),
            FIXPOINT_BUBBLE(2);
            
            private int value;

            private DisplayMode(int i) {
                this.value = 0;
                this.value = i;
            }

            public static DisplayMode valueOf(int i) {
                switch (i) {
                    case 0:
                        return WONT_DISPLAY;
                    case 1:
                        return ONLY_FIXPOINT;
                    case 2:
                        return FIXPOINT_BUBBLE;
                    default:
                        return null;
                }
            }

            public final int value() {
                return this.value;
            }
        }

        public static class Options implements Serializable {
            public ActionPoint actionPoint;
            public Milestone milestone;

            public static class ActionPoint implements Serializable {
                public int visible;
            }

            public static class Milestone implements Serializable {
                public int visible;
            }
        }
    }

    public void init(Context context, int i) {
        this.engineId = i;
        this.mContext = context;
        this.mNaviEventReceiver = new NaviEventReceiver() {
            public void onNaviEvent(String str) {
                AMapLog.debug("infoservice.miniapp", MiniAppShowRouteManager.TAG, "onNaviEvent, e: ".concat(String.valueOf(str)));
                NaviEventCalcRouteResp naviEventCalcRouteResp = (NaviEventCalcRouteResp) JSON.parseObject(str, NaviEventCalcRouteResp.class);
                if (!TextUtils.isEmpty(naviEventCalcRouteResp.summary)) {
                    naviEventCalcRouteResp.summaryObj = (Summary) JSON.parseObject(naviEventCalcRouteResp.summary, Summary.class);
                }
                OnShowRouteCallback onShowRouteCallback = (OnShowRouteCallback) MiniAppShowRouteManager.this.reqIdAndCallbackMap.get(naviEventCalcRouteResp.requestId);
                switch (naviEventCalcRouteResp.eventType) {
                    case 7:
                        if (naviEventCalcRouteResp.requestId == MiniAppShowRouteManager.this.requestId.get()) {
                            if (naviEventCalcRouteResp.routes == null || naviEventCalcRouteResp.routes.size() <= 0) {
                                AMapLog.error("infoservice.miniapp", MiniAppShowRouteManager.TAG, "calc route succeed, but routes is empty, abort");
                            } else {
                                MiniAppShowRouteManager.this.showRoute(naviEventCalcRouteResp.routes.get(0).longValue());
                            }
                        }
                        if (onShowRouteCallback != null) {
                            if (naviEventCalcRouteResp.summaryObj == null || naviEventCalcRouteResp.summaryObj.pathArray == null || naviEventCalcRouteResp.summaryObj.pathArray.size() <= 0) {
                                AMapLog.error("infoservice.miniapp", MiniAppShowRouteManager.TAG, "calc route callback abort, no path info");
                                return;
                            }
                            PathInfo pathInfo = naviEventCalcRouteResp.summaryObj.pathArray.get(0);
                            onShowRouteCallback.onSucceed(pathInfo.length, pathInfo.time);
                            MiniAppShowRouteManager.this.reqIdAndCallbackMap.remove(naviEventCalcRouteResp.requestId);
                            return;
                        }
                        break;
                    case 8:
                        AMapLog.error("infoservice.miniapp", MiniAppShowRouteManager.TAG, "calc route fail, abort");
                        if (onShowRouteCallback != null) {
                            onShowRouteCallback.onFail(naviEventCalcRouteResp.errorCode);
                            MiniAppShowRouteManager.this.reqIdAndCallbackMap.remove(naviEventCalcRouteResp.requestId);
                            break;
                        }
                        break;
                }
            }
        };
        NaviManager.registerEventReceiver(this.mNaviEventReceiver);
        setupTextureLoader();
        MapViewManager.newMapView(i);
        this.sceneId = NaviManager.createScene(i);
        this.naviBusiness = new NaviBusiness(this.sceneId);
        this.mMiniAppBusService = new MiniAppBusService();
        this.mMiniAppBusService.init(1, aaf.b(ConfigerHelper.AOS_URL_KEY));
        this.mMiniAppBusService.setBusServiceListener(new IBusServiceListener() {
            public void onResult(int i, String str) {
                if (i == MiniAppShowRouteManager.this.requestId.get() && MiniAppShowRouteManager.this.idCallbackMap != null && MiniAppShowRouteManager.this.idCallbackMap.size() != 0) {
                    StringBuilder sb = new StringBuilder("onResult, requestId: ");
                    sb.append(i);
                    sb.append(", pathResult: ");
                    sb.append(str);
                    AMapLog.debug("infoservice.miniapp", MiniAppShowRouteManager.TAG, sb.toString());
                    H5BridgeContext h5BridgeContext = (H5BridgeContext) MiniAppShowRouteManager.this.idCallbackMap.get(i);
                    if (h5BridgeContext != null) {
                        JSONObject jSONObject = new JSONObject();
                        JSONObject parseObject = JSONObject.parseObject(str);
                        String str2 = "0";
                        boolean z = true;
                        if (parseObject.containsKey("samecity")) {
                            if (parseObject.getInteger("samecity").intValue() != 1) {
                                z = false;
                            }
                            if (!z) {
                                str2 = "7";
                            }
                        }
                        if (z && parseObject.containsKey("why")) {
                            str2 = parseObject.getString("why");
                        }
                        if (!z || !"0".equals(str2)) {
                            AmapEngineBusErrors valueOf = AmapEngineBusErrors.valueOf(Integer.parseInt(str2));
                            if (h5BridgeContext != null) {
                                jSONObject.put((String) "success", (Object) Boolean.FALSE);
                                jSONObject.put((String) "error", (Object) Integer.valueOf(valueOf.value()));
                                h5BridgeContext.sendBridgeResult(jSONObject);
                                MiniAppShowRouteManager.this.idCallbackMap.remove(i);
                            }
                            return;
                        }
                        jSONObject.put((String) "success", (Object) Boolean.TRUE);
                        try {
                            JSONObject jSONObject2 = parseObject.getJSONArray("buslist").getJSONObject(0);
                            long longValue = jSONObject2.getLongValue("allLength");
                            long longValue2 = jSONObject2.getLongValue("expensetime");
                            jSONObject.put((String) "distance", (Object) Long.valueOf(longValue));
                            jSONObject.put((String) "duration", (Object) Long.valueOf(longValue2));
                        } catch (Exception e) {
                            StringBuilder sb2 = new StringBuilder("parse routjson fail =");
                            sb2.append(e.toString());
                            sb2.append(",routejson=");
                            sb2.append(str);
                            AMapLog.error("infoservice.miniapp", MiniAppShowRouteManager.TAG, sb2.toString());
                        }
                        h5BridgeContext.sendBridgeResult(jSONObject);
                        MiniAppShowRouteManager.this.idCallbackMap.remove(i);
                    }
                    MiniAppShowRouteManager.this.showRoute((long) i, str);
                }
            }

            public void onError(int i, int i2) {
                if (i == MiniAppShowRouteManager.this.requestId.get()) {
                    StringBuilder sb = new StringBuilder("requestId=");
                    sb.append(MiniAppShowRouteManager.this.requestId);
                    sb.append(",error=");
                    sb.append(i2);
                    AMapLog.error("infoservice.miniapp", MiniAppShowRouteManager.TAG, sb.toString());
                    if (MiniAppShowRouteManager.this.idCallbackMap != null && MiniAppShowRouteManager.this.idCallbackMap.size() != 0) {
                        AmapEngineBusErrors valueOf = AmapEngineBusErrors.valueOf(i2);
                        H5BridgeContext h5BridgeContext = (H5BridgeContext) MiniAppShowRouteManager.this.idCallbackMap.get(i);
                        if (h5BridgeContext != null) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put((String) "success", (Object) Boolean.FALSE);
                            jSONObject.put((String) "error", (Object) Integer.valueOf(valueOf.value()));
                            h5BridgeContext.sendBridgeResult(jSONObject);
                            MiniAppShowRouteManager.this.idCallbackMap.remove(i);
                        }
                    }
                }
            }
        });
        onShow();
    }

    private void setupTextureLoader() {
        this.mTextureLoader = new TextureLoader() {
            public boolean loadTextureData(int i, TextureParam textureParam, TextureWrapper textureWrapper) {
                StringBuilder sb = new StringBuilder("loadTextureData, sceneType: ");
                sb.append(i);
                sb.append(", textureParam: ");
                sb.append(textureParam);
                sb.append(", textureWrapper: ");
                sb.append(textureWrapper);
                AMapLog.debug("infoservice.miniapp", MiniAppShowRouteManager.TAG, sb.toString());
                int i2 = 0;
                if (textureParam == null || textureWrapper == null || textureParam.resId < 0) {
                    AMapLog.debug("infoservice.miniapp", MiniAppShowRouteManager.TAG, "loadTextureData input parameters illegal!");
                    return false;
                }
                Map<String, StaticTexture> textureMap = MiniAppTextureMapHelper.getInstance().getTextureMap();
                if (textureMap == null || textureMap.size() <= 0) {
                    return false;
                }
                PictureParams pictureParams = new PictureParams();
                StaticTexture staticTexture = textureMap.get(String.valueOf(textureParam.resId));
                if (staticTexture == null) {
                    StringBuilder sb2 = new StringBuilder("loadTextureData staticTexture is illegal! id=");
                    sb2.append(textureParam.resId);
                    AMapLog.debug("infoservice.miniapp", MiniAppShowRouteManager.TAG, sb2.toString());
                    return false;
                }
                String str = staticTexture.path;
                boolean z = staticTexture.isNinePatch;
                StringBuilder sb3 = new StringBuilder("loadTextureData notice url=");
                sb3.append(str);
                sb3.append(",isNinePatch=");
                sb3.append(z);
                AMapLog.debug("infoservice.miniapp", MiniAppShowRouteManager.TAG, sb3.toString());
                if (str == null || TextUtils.isEmpty(str)) {
                    StringBuilder sb4 = new StringBuilder("loadTextureData url is illegal! id=");
                    sb4.append(textureParam.resId);
                    AMapLog.debug("infoservice.miniapp", MiniAppShowRouteManager.TAG, sb4.toString());
                    return false;
                }
                pictureParams.isNeedScale = !textureParam.isNeedPng;
                pictureParams.url = str;
                pictureParams.patchIndex = PathUtils.getPatchIndexByUrl(str);
                byte[] loadImage = AjxTextureUtils.loadImage(pictureParams, str);
                if (loadImage == null) {
                    AMapLog.debug("infoservice.miniapp", MiniAppShowRouteManager.TAG, "loadTextureData loadImage data fail!!, url=".concat(String.valueOf(str)));
                    return false;
                }
                StringBuilder sb5 = new StringBuilder("loadTextureData url=");
                sb5.append(str);
                sb5.append(",data=");
                sb5.append(loadImage);
                AMapLog.debug("infoservice.miniapp", MiniAppShowRouteManager.TAG, sb5.toString());
                float f = (float) pictureParams.bitmapWidth;
                float f2 = (float) pictureParams.bitmapHeight;
                textureWrapper.scale = 1.0f;
                if (textureParam.isNeedPng) {
                    float[] readImageSize = Ajx.getInstance().lookupLoader(str).readImageSize(pictureParams);
                    f = readImageSize[0];
                    f2 = readImageSize[1];
                    float f3 = readImageSize[2];
                    if (MiniAppShowRouteManager.this.mContext != null) {
                        textureWrapper.scale = MiniAppShowRouteManager.this.mContext.getResources().getDisplayMetrics().density / f3;
                    } else {
                        AMapLog.debug("infoservice.miniapp", MiniAppShowRouteManager.TAG, "loadTextureData mContext is null!!");
                    }
                }
                textureWrapper.isNinePatch = z;
                textureWrapper.width = f;
                textureWrapper.height = f2;
                textureWrapper.engineId = textureParam.engineId;
                if (textureParam.isNeedPng) {
                    i2 = 2;
                }
                textureWrapper.setData(i2, loadImage);
                return true;
            }

            public boolean loadCustomTextureData(int i, TextureParam textureParam, CustomTextureObserver customTextureObserver) {
                StringBuilder sb = new StringBuilder("loadCustomTextureData, sceneType: ");
                sb.append(i);
                sb.append(", textureParam: ");
                sb.append(textureParam);
                sb.append(", customTextureObserver: ");
                sb.append(customTextureObserver);
                AMapLog.debug("infoservice.miniapp", MiniAppShowRouteManager.TAG, sb.toString());
                return false;
            }
        };
        MapViewManager.addTextureLoader(this.mTextureLoader, 200);
    }

    public void calcBusRoute(H5BridgeContext h5BridgeContext, CalcRouteParam calcRouteParam) {
        if (calcRouteParam != null && configComponents(calcRouteParam.routeMode)) {
            int prepareCalcRoute = prepareCalcRoute(calcRouteParam);
            if (!(this.mMiniAppBusService == null || calcRouteParam == null || this.idCallbackMap == null)) {
                this.mMiniAppBusService.cancel(prepareCalcRoute);
                this.idCallbackMap.put(prepareCalcRoute, h5BridgeContext);
                BusPlanParam busPlanParam = new BusPlanParam(calcRouteParam.startPOI.lon, calcRouteParam.startPOI.lat, calcRouteParam.endPOI.lon, calcRouteParam.endPOI.lat);
                busPlanParam.setType(calcRouteParam.type);
                this.mMiniAppBusService.request(prepareCalcRoute, busPlanParam);
            }
        }
    }

    public void calcRoute(CalcRouteParam calcRouteParam, OnShowRouteCallback onShowRouteCallback) {
        if (configComponents(calcRouteParam.routeMode)) {
            this.reqIdAndCallbackMap.put(prepareCalcRoute(calcRouteParam), onShowRouteCallback);
            String jSONString = ((JSONObject) JSONObject.toJSON(calcRouteParam)).toJSONString();
            AMapLog.debug("infoservice.miniapp", TAG, "sendCommand, cmd: 102003, param: ".concat(String.valueOf(jSONString)));
            this.naviBusiness.sendCommand(CMD_CALC_ROUTE, jSONString);
        }
    }

    private int prepareCalcRoute(CalcRouteParam calcRouteParam) {
        abortPendingShowRoute();
        int addAndGet = this.requestId.addAndGet(1);
        calcRouteParam.requestId = (long) addAndGet;
        return addAndGet;
    }

    private void abortPendingShowRoute() {
        if (this.reqIdAndCallbackMap != null) {
            this.reqIdAndCallbackMap.clear();
        }
        if (this.idCallbackMap != null) {
            this.idCallbackMap.clear();
        }
    }

    private boolean configComponents(int i) {
        int i2;
        String str;
        String str2;
        switch (i) {
            case 0:
                i2 = 10003;
                str2 = "[10001,10101]";
                str = CONFIG_104_DRIVE;
                break;
            case 1:
                i2 = 10007;
                str2 = "[10001,10102]";
                str = CONFIG_104_RIDE;
                break;
            case 2:
                i2 = 10005;
                str2 = "[10001,10102]";
                str = CONFIG_104_WALK;
                break;
            case 3:
                i2 = 10008;
                str2 = "[10103]";
                str = CONFIG_104_BUS;
                break;
            default:
                AMapLog.error("infoservice.miniapp", AMapH5EmbedMapView.TAG, "illegal routeMode, ".concat(String.valueOf(i)));
                return false;
        }
        StringBuilder sb = new StringBuilder("setConfig, 104, config: ");
        sb.append(str);
        sb.append(", sceneId: ");
        sb.append(this.sceneId);
        AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
        NaviManager.setConfig(104, str, this.sceneId);
        String b = aaf.b(ConfigerHelper.AOS_URL_KEY);
        AMapLog.debug("infoservice.miniapp", TAG, "setConfig, 100, aosurl: ".concat(String.valueOf(b)));
        NaviManager.setConfig(100, b);
        String str3 = MiniAppShowRouteConfigHelper.get7PaddingConfig();
        AMapLog.debug("infoservice.miniapp", TAG, "setConfig, 7, config: ".concat(String.valueOf(str3)));
        NaviManager.setConfig(7, str3, this.sceneId);
        String cardXmlConfig = MiniAppShowRouteConfigHelper.getCardXmlConfig();
        AMapLog.debug("infoservice.miniapp", TAG, "setConfig, 1201, config: ".concat(String.valueOf(cardXmlConfig)));
        NaviManager.setConfig(1201, cardXmlConfig, this.sceneId);
        AMapLog.debug("infoservice.miniapp", TAG, "sendCommand, cmd: 102007, param: ".concat(String.valueOf(i2)));
        this.naviBusiness.sendCommand(CMD_SET_COMPONENT_ADAPTER, String.valueOf(i2));
        AMapLog.debug("infoservice.miniapp", TAG, "sendCommand, cmd: 102001, param: ".concat(String.valueOf(str2)));
        this.naviBusiness.sendCommand(CMD_ATTACH_COMPONENTS, str2);
        return true;
    }

    public void showRoute(long j) {
        ShowRouteParam showRouteParam = new ShowRouteParam();
        showRouteParam.routeID = new ArrayList(1);
        showRouteParam.routeID.add(Long.valueOf(j));
        showRouteParam.routeMode = 0;
        showRouteParam.options = new Options();
        showRouteParam.options.actionPoint = new ActionPoint();
        showRouteParam.options.actionPoint.visible = DisplayMode.ONLY_FIXPOINT.value();
        showRouteParam.options.milestone = new Milestone();
        showRouteParam.options.milestone.visible = DisplayMode.ONLY_FIXPOINT.value();
        String jSONString = ((JSONObject) JSONObject.toJSON(showRouteParam)).toJSONString();
        AMapLog.debug("infoservice.miniapp", TAG, "sendCommand, cmd: 102004, param: ".concat(String.valueOf(jSONString)));
        this.naviBusiness.sendCommand(CMD_SHOW_ROUTE, jSONString);
        zoomRouteset();
    }

    public void showRoute(long j, String str) {
        BusShowRouteParam busShowRouteParam = new BusShowRouteParam();
        busShowRouteParam.data = str;
        busShowRouteParam.options = new Options();
        busShowRouteParam.options.carLocation = new CarLocation();
        busShowRouteParam.options.guideTips = new GuideTips();
        String jSONString = ((JSONObject) JSONObject.toJSON(busShowRouteParam)).toJSONString();
        StringBuilder sb = new StringBuilder("requestId: ");
        sb.append(j);
        sb.append(", sendCommand, cmd: 102004, param: ");
        sb.append(jSONString);
        AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
        this.naviBusiness.sendCommand(CMD_SHOW_ROUTE, jSONString);
        zoomRouteset();
    }

    public void destroy() {
        onHide();
        if (this.mMiniAppBusService != null) {
            this.mMiniAppBusService.cancelAll();
            this.mMiniAppBusService.destory();
        }
        if (this.mNaviEventReceiver != null) {
            NaviManager.unregisterEventReceiver(this.mNaviEventReceiver);
        }
        this.naviBusiness.destroy();
        NaviManager.syncDestroyScene(this.sceneId);
        if (this.mTextureLoader != null) {
            MapViewManager.removeTextureLoader(this.mTextureLoader);
        }
        MapViewManager.safeDestroyMapView(this.engineId);
    }

    public void onShow() {
        this.naviBusiness.show();
    }

    public void onHide() {
        this.naviBusiness.hide();
    }

    public void clearRoute() {
        this.naviBusiness.sendCommand(1008);
    }

    public void zoomRouteset() {
        this.naviBusiness.sendCommand(CMD_ZOOM_ROUTESET);
    }
}
