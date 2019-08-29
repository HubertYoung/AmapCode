package com.alipay.mobile.h5plugin;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.zoloz.R;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.lbs.LBSLocationRequest;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.framework.service.GeocodeService;
import com.alipay.mobile.framework.service.LBSLocationManagerService;
import com.alipay.mobile.framework.service.OnLBSLocationListener;
import com.alipay.mobile.h5container.api.H5AvailablePageData;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.map.model.LatLonPoint;
import com.alipay.mobile.map.model.geocode.PoiItem;
import com.alipay.mobile.map.model.geocode.ReGeocodeResult;
import com.alipay.mobile.map.model.geocode.StreetNumber;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class H5Location {
    private static final int DEFAULT_TIMEOUT = 10000;
    private static final String REQUEST_TYPE = "requestType";
    private static final int REQUEST_TYPE_COORDINATE_ONLY = 0;
    private static final int REQUEST_TYPE_REVERSE = 1;
    private static final int REQUEST_TYPE_REVERSE_POI = 3;
    private static final int REQUEST_TYPE_REVERSE_STREET = 2;
    private static final int RE_GEOCODE = 1;
    private static final int RE_GEOCODE_ALL = 0;
    public static final String TAG = "H5LocationPlugin";
    /* access modifiers changed from: private */
    public static JSONObject geoLocRes = null;
    /* access modifiers changed from: private */
    public static JSONObject lastLocRes = null;
    private static int lastRequestType = 0;
    /* access modifiers changed from: private */
    public static long locationStartTime = 0;
    private String bizType;
    /* access modifiers changed from: private */
    public H5BridgeContext bridgeContext;
    private final OnLBSLocationListener continuousListener = new OnLBSLocationListener() {
        public void onLocationUpdate(LBSLocation location) {
            if (location != null) {
                LoggerFactory.getTraceLogger().info("H5LocationPlugin", "location detail:" + location.getLongitude() + MergeUtil.SEPARATOR_KV + location.getLatitude() + MergeUtil.SEPARATOR_KV + location.getAccuracy());
            } else {
                LoggerFactory.getTraceLogger().info("H5LocationPlugin", "location is null");
            }
            if (location != null) {
                JSONObject result = new JSONObject();
                result.put((String) "latitude", (Object) Double.valueOf(location.getLatitude()));
                result.put((String) "longitude", (Object) Double.valueOf(location.getLongitude()));
                result.put((String) CameraControllerManager.MY_POILOCATION_ACR, (Object) Float.valueOf(location.getAccuracy()));
                result.put((String) "speed", (Object) Float.valueOf(location.getSpeed()));
                if (H5Location.this.bridgeContext != null) {
                    H5Location.this.bridgeContext.sendBridgeResultWithCallbackKept(result);
                }
            } else if (H5Location.this.bridgeContext != null) {
                JSONObject result2 = new JSONObject();
                result2.put((String) "error", (Object) Integer.valueOf(13));
                result2.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.locate_failed));
                H5Location.this.bridgeContext.sendBridgeResultWithCallbackKept(result2);
                Behavor behavor = new Behavor();
                behavor.setSeedID("continuousLocation");
                behavor.setBehaviourPro(RPCDataItems.LBSINFO);
                behavor.setParam1("error");
                behavor.setParam3("13");
                LoggerFactory.getBehavorLogger().event(null, behavor);
            }
        }

        public void onLocationFailed(int errorCode) {
            LoggerFactory.getTraceLogger().info("H5LocationPlugin", "errorCode:" + errorCode);
            JSONObject result = new JSONObject();
            if (errorCode == 7 || errorCode == 12 || errorCode == 13) {
                result.put((String) "error", (Object) Integer.valueOf(12));
                result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.locate_failed_gps));
            } else if (errorCode == 5 || errorCode == 6) {
                result.put((String) "error", (Object) Integer.valueOf(13));
                result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.locate_failed));
            } else if (errorCode == 4) {
                result.put((String) "error", (Object) Integer.valueOf(14));
                result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.locate_timeout));
            } else if ((errorCode > 0 && errorCode <= 3) || 8 <= errorCode) {
                result.put((String) "error", (Object) Integer.valueOf(15));
                result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.locate_net_error));
            }
            result.put((String) "extError", (Object) Integer.valueOf(errorCode));
            if (H5Location.this.bridgeContext != null) {
                H5Location.this.bridgeContext.sendBridgeResultWithCallbackKept(result);
            }
            Behavor behavor = new Behavor();
            behavor.setSeedID("continuousLocation");
            behavor.setBehaviourPro(RPCDataItems.LBSINFO);
            behavor.setParam1("error");
            behavor.setParam3(String.valueOf(errorCode));
            LoggerFactory.getBehavorLogger().event(null, behavor);
        }
    };
    private boolean isStartLocation;
    private long startCountinuousTime;

    public class LocationTask {
        public LocationListener callback;

        public LocationTask() {
        }
    }

    class RpcLocation implements Runnable {
        private final H5BridgeContext context;
        private final H5Event event;
        private final LocationListener listener;
        private final int locateDuration;
        private final LBSLocation location;
        private final long requestTime;
        private final TimeoutRunnable timeoutRunnable;

        public RpcLocation(H5Event event2, H5BridgeContext context2, LBSLocation location2, LocationListener listener2, long requestTime2, TimeoutRunnable timeoutRunnable2, int locateDuration2) {
            this.location = location2;
            this.context = context2;
            this.requestTime = requestTime2;
            this.event = event2;
            this.listener = listener2;
            this.timeoutRunnable = timeoutRunnable2;
            this.locateDuration = locateDuration2;
        }

        public void run() {
            H5Page h5Page = null;
            if (this.event != null && (this.event.getTarget() instanceof H5Page)) {
                h5Page = (H5Page) this.event.getTarget();
            }
            H5AvailablePageData availablePageData = null;
            if (h5Page != null) {
                availablePageData = h5Page.getAvailablePageData();
                if (availablePageData != null) {
                    availablePageData.reportLocStart();
                }
            }
            if (this.location != null) {
                H5Log.d("H5LocationPlugin", "getLocation " + this.location.getCity() + Token.SEPARATOR + this.location.getLatitude() + Token.SEPARATOR + this.location.getLongitude());
                GeocodeService geocodeService = (GeocodeService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(GeocodeService.class.getName());
                LatLonPoint locPoint = new LatLonPoint();
                locPoint.setLatitude(this.location.getLatitude());
                locPoint.setLongitude(this.location.getLongitude());
                float radius = 0.0f;
                if (this.event != null) {
                    try {
                        radius = (float) H5Utils.getInt(this.event.getParam(), (String) "radius");
                    } catch (Throwable e) {
                        LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e);
                    }
                }
                if (radius == 0.0f) {
                    radius = 200.0f;
                }
                JSONObject result = new JSONObject();
                result.put((String) "city", (Object) this.location.getCity());
                result.put((String) "latitude", (Object) Double.valueOf(this.location.getLatitude()));
                result.put((String) "longitude", (Object) Double.valueOf(this.location.getLongitude()));
                result.put((String) AutoJsonUtils.JSON_ADCODE, (Object) this.location.getAdCode());
                result.put((String) "citycode", (Object) this.location.getCityCode());
                result.put((String) "province", (Object) this.location.getProvince());
                result.put((String) "address", (Object) this.location.getAddress());
                result.put((String) "aoiname", (Object) this.location.getAoiname());
                result.put((String) "speed", (Object) Float.valueOf(this.location.getSpeed()));
                result.put((String) CameraControllerManager.MY_POILOCATION_ACR, (Object) Float.valueOf(this.location.getAccuracy()));
                result.put((String) "country", (Object) this.location.getCountry());
                runLocation(availablePageData, geocodeService, locPoint, radius, result);
            }
        }

        private void runLocation(H5AvailablePageData availablePageData, GeocodeService geocodeService, LatLonPoint locPoint, float radius, JSONObject result) {
            boolean succeed = false;
            long locationEnd = System.currentTimeMillis();
            try {
                List geocodeList = geocodeService.reGeocode(locPoint, radius);
                if (!this.timeoutRunnable.checkAndChangeTimeArriving("runLocation")) {
                    if (geocodeList != null && geocodeList.size() > 0) {
                        ReGeocodeResult reGeocodeResult = geocodeList.get(0);
                        StreetNumber streetNumber = reGeocodeResult.getStreetNumber();
                        JSONObject streetObject = new JSONObject();
                        H5Log.d("H5LocationPlugin", "getLocation streetNumber " + streetNumber.getNumber());
                        streetObject.put((String) "number", (Object) streetNumber.getNumber());
                        streetObject.put((String) "street", (Object) streetNumber.getStreet());
                        result.put((String) "streetNumber", (Object) streetObject);
                        List poitemList = reGeocodeResult.getPois();
                        JSONArray jsonArray = new JSONArray();
                        if (poitemList != null) {
                            for (PoiItem poiItem : poitemList) {
                                JSONObject poiObject = new JSONObject();
                                poiObject.put((String) "name", (Object) poiItem.getTitle());
                                poiObject.put((String) "address", (Object) poiItem.getAdName());
                                jsonArray.add(poiObject);
                            }
                        }
                        result.put((String) "pois", (Object) jsonArray);
                        H5Log.d("H5LocationPlugin", "getLocation pois" + jsonArray.toJSONString());
                        H5Location.geoLocRes = result;
                    }
                    succeed = true;
                    H5Log.d("H5LocationPlugin", "getLocation geocodeList result");
                    int reGeocodeDuration = (int) ((System.currentTimeMillis() - this.requestTime) - ((long) this.locateDuration));
                    JSONObject param = new JSONObject();
                    r0 = "result";
                    param.put((String) "result", (Object) succeed ? "success" : "error");
                    param.put((String) "locateDuration", (Object) Integer.valueOf(this.locateDuration));
                    param.put((String) "reGeocodeDuration", (Object) Integer.valueOf(reGeocodeDuration));
                    param.put((String) "reGeocodeStart", (Object) this.requestTime);
                    param.put((String) "reGeocodeEnd", (Object) String.valueOf(locationEnd));
                    H5Location.lastLocRes = result;
                    H5Location.locationStartTime = System.currentTimeMillis();
                    if (this.listener != null) {
                        this.listener.onLocationResult(result, 0);
                    }
                    if (this.context != null) {
                        this.context.sendBridgeResult(result);
                    }
                    if (availablePageData != null) {
                        availablePageData.reportLocEnd();
                    }
                    H5Log.d("H5LocationPlugin", "getLocation result " + result.toJSONString());
                }
            } catch (Throwable t) {
                H5Log.e("H5LocationPlugin", "getLocation exception", t);
            }
        }
    }

    class TimeoutRunnable implements Runnable {
        public H5BridgeContext context;
        private boolean timeArriving;
        private int timeOut;

        public TimeoutRunnable(H5BridgeContext context2, int timeOut2) {
            this.context = context2;
            this.timeOut = timeOut2;
        }

        public synchronized boolean checkAndChangeTimeArriving(String flag) {
            boolean z = true;
            synchronized (this) {
                H5Log.d("H5LocationPlugin", "checkAndChangeTimeArriving in," + flag);
                if (this.timeArriving) {
                    H5Log.d("H5LocationPlugin", "timeArriving is true," + flag);
                } else {
                    this.timeArriving = true;
                    z = false;
                }
            }
            return z;
        }

        public void run() {
            if (!checkAndChangeTimeArriving("timeout coming, timeout=" + this.timeOut)) {
                H5Location.this.onRequestTimeout(this.context);
            }
        }
    }

    public synchronized void getLocation(H5Event event, H5BridgeContext bridgeContext2, LocationListener listener, boolean useBufferedLocation, long startTime) {
        if (event == null) {
            LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", (String) "event = null");
        } else {
            H5Log.d("H5LocationPlugin", "getLocation useBufferedLocation " + useBufferedLocation);
            final long lastLocationTime = System.currentTimeMillis();
            new LocationTask().callback = listener;
            JSONObject param = event.getParam();
            int timeOut = 10000;
            try {
                timeOut = H5Utils.getInt(param, (String) "timeout", 10) * 1000;
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e);
            }
            boolean needAddress = true;
            String biz = H5Utils.getString(event.getParam(), (String) "bizType", (String) null);
            if (TextUtils.isEmpty(biz)) {
                String host = null;
                try {
                    host = new URL(((H5Page) event.getTarget()).getUrl()).getHost();
                } catch (MalformedURLException e2) {
                    LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", (Throwable) e2);
                }
                biz = host;
            }
            int cacheTimeout = 30;
            try {
                cacheTimeout = H5Utils.getInt(param, (String) "cacheTimeout", 30);
            } catch (Throwable e3) {
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e3);
            }
            int mRequestType = 0;
            try {
                mRequestType = H5Utils.getInt(param, (String) "requestType", 0);
            } catch (Throwable e4) {
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e4);
            }
            final int requestType = mRequestType;
            if (mRequestType > 1) {
                needAddress = false;
            }
            if (useBufferedLocation) {
                if (lastLocRes != null && requestType == lastRequestType) {
                    H5Log.d("H5LocationPlugin", "getLocation for last location result");
                    if (bridgeContext2 != null) {
                        bridgeContext2.sendBridgeResult(lastLocRes);
                    }
                    if (listener != null) {
                        listener.onLocationResult(lastLocRes, requestType);
                    }
                }
            }
            if (locationStartTime == 0 || System.currentTimeMillis() - locationStartTime > ((long) (cacheTimeout * 1000))) {
                geoLocRes = null;
            } else if (geoLocRes != null && requestType == 0) {
                H5Log.d("H5LocationPlugin", "getLocation for geo location cache result");
                if (bridgeContext2 != null) {
                    bridgeContext2.sendBridgeResult(geoLocRes);
                }
                if (listener != null) {
                    listener.onLocationResult(geoLocRes, requestType);
                }
            }
            lastRequestType = requestType;
            final long requestTime = System.currentTimeMillis();
            final TimeoutRunnable timeoutRunnable = new TimeoutRunnable(bridgeContext2, timeOut);
            final H5BridgeContext h5BridgeContext = bridgeContext2;
            final long j = startTime;
            final LocationListener locationListener = listener;
            final H5Event h5Event = event;
            OnLBSLocationListener lbsListener = new OnLBSLocationListener() {
                public void onLocationUpdate(LBSLocation location) {
                    H5Log.d("H5LocationPlugin", "onLocationUpdate in getLocation");
                    long locationEnd = System.currentTimeMillis();
                    int locateDuration = (int) (locationEnd - requestTime);
                    if (location == null) {
                        if (!timeoutRunnable.checkAndChangeTimeArriving("getLocation, location == null") && h5BridgeContext != null) {
                            JSONObject result = new JSONObject();
                            result.put((String) "error", (Object) Integer.valueOf(13));
                            result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.locate_failed));
                            h5BridgeContext.sendBridgeResult(result);
                        }
                    } else if (requestType == 0) {
                        BackgroundExecutor.execute((Runnable) new RpcLocation(h5Event, h5BridgeContext, location, locationListener, requestTime, timeoutRunnable, locateDuration));
                    } else if (!timeoutRunnable.checkAndChangeTimeArriving("getLocation, requestType != RE_GEOCODE_ALL")) {
                        JSONObject result2 = new JSONObject();
                        result2.put((String) "latitude", (Object) Double.valueOf(location.getLatitude()));
                        result2.put((String) "longitude", (Object) Double.valueOf(location.getLongitude()));
                        result2.put((String) CameraControllerManager.MY_POILOCATION_ACR, (Object) Float.valueOf(location.getAccuracy()));
                        result2.put((String) "speed", (Object) Float.valueOf(location.getSpeed()));
                        ReGeocodeResult reGeocodeResult = location.getReGeocodeResult();
                        if (requestType == 1 && reGeocodeResult == null) {
                            result2.put((String) "city", (Object) location.getCity());
                            result2.put((String) AutoJsonUtils.JSON_ADCODE, (Object) location.getAdCode());
                            result2.put((String) "citycode", (Object) location.getCityCode());
                            result2.put((String) "province", (Object) location.getProvince());
                            result2.put((String) "address", (Object) location.getAddress());
                            result2.put((String) "aoiname", (Object) location.getAoiname());
                            result2.put((String) "country", (Object) location.getCountry());
                        } else if (requestType == 1 && reGeocodeResult != null) {
                            result2.put((String) "city", (Object) reGeocodeResult.getCity());
                            result2.put((String) AutoJsonUtils.JSON_ADCODE, (Object) reGeocodeResult.getAdcode());
                            result2.put((String) "citycode", (Object) reGeocodeResult.getCityCode());
                            result2.put((String) "province", (Object) reGeocodeResult.getProvince());
                            result2.put((String) "address", (Object) reGeocodeResult.getFormatAddress());
                            result2.put((String) "country", (Object) reGeocodeResult.getCountry());
                            result2.put((String) "countryCode", (Object) reGeocodeResult.getCountryCode());
                        }
                        long locatingInterval = System.currentTimeMillis() - j;
                        LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "H5Location#getLocation#LBSLocationListener.onLocationUpdate()#jsapi cost time:" + (System.currentTimeMillis() - locationEnd) + RPCDataParser.TIME_MS);
                        LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "H5Location#getLocation#LBSLocationListener.onLocationUpdate()#h5 location success cost time:" + locatingInterval + RPCDataParser.TIME_MS);
                        if (h5BridgeContext != null) {
                            h5BridgeContext.sendBridgeResult(result2);
                        }
                        H5Location.lastLocRes = result2;
                        if (locationListener != null) {
                            locationListener.onLocationResult(result2, requestType);
                        }
                        LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "H5Location#getLocation#LBSLocationListener.onLocationUpdate()#jsapi remove callback");
                        Behavor behavor = new Behavor();
                        behavor.setSeedID("H5_LOCATION_INTERVAL");
                        behavor.setUserCaseID("H5_LOCATION");
                        behavor.setBehaviourPro(RPCDataItems.LBSINFO);
                        behavor.setParam1(String.valueOf(locatingInterval));
                        LoggerFactory.getBehavorLogger().event(null, behavor);
                        JSONObject param = new JSONObject();
                        param.put((String) "result", (Object) "success");
                        param.put((String) "locateDuration", (Object) Integer.valueOf(locateDuration));
                        param.put((String) "locateStart", (Object) requestTime);
                        param.put((String) "locateEnd", (Object) String.valueOf(locationEnd));
                        LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "param:" + param.toJSONString());
                    }
                }

                public void onLocationFailed(int errorCode) {
                    H5Log.d("H5LocationPlugin", "onLocationFailed() in errorCode:" + errorCode);
                    int failCode = 2;
                    long locationEnd = System.currentTimeMillis();
                    int locateDuration = (int) (locationEnd - requestTime);
                    if (!timeoutRunnable.checkAndChangeTimeArriving("getLocation, onLocationFailed")) {
                        JSONObject result = new JSONObject();
                        if (errorCode == 7 || errorCode == 12 || errorCode == 13) {
                            result.put((String) "error", (Object) Integer.valueOf(12));
                            result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.get_location_auth_failed));
                            failCode = 2;
                        } else if (errorCode == 5 || errorCode == 6) {
                            result.put((String) "error", (Object) Integer.valueOf(13));
                            result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.get_location_failed));
                            failCode = 2;
                        } else if (errorCode == 4) {
                            result.put((String) "error", (Object) Integer.valueOf(14));
                            result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.get_location_net_failed));
                            failCode = 1;
                        } else if ((errorCode > 0 && errorCode <= 3) || 8 <= errorCode) {
                            result.put((String) "error", (Object) Integer.valueOf(15));
                            result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.get_location_failed));
                            failCode = 3;
                        }
                        result.put((String) "extError", (Object) Integer.valueOf(errorCode));
                        JSONObject param = new JSONObject();
                        param.put((String) "result", (Object) "error");
                        param.put((String) "locateDuration", (Object) Integer.valueOf(locateDuration));
                        param.put((String) "failCode", (Object) Integer.valueOf(failCode));
                        param.put((String) "errorMessage", result.get("errorMessage"));
                        param.put((String) "errorCode", (Object) String.valueOf(errorCode));
                        param.put((String) "errorType", (Object) result.get("error"));
                        param.put((String) "locateStart", (Object) requestTime);
                        param.put((String) "locateEnd", (Object) String.valueOf(locationEnd));
                        LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "H5Location#getLocation#LBSLocationListener.onLocationUpdate()#h5 location failed cost time:" + (System.currentTimeMillis() - lastLocationTime) + RPCDataParser.TIME_MS);
                        LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "param:" + param.toJSONString());
                        if (h5BridgeContext != null) {
                            h5BridgeContext.sendBridgeResult(result);
                        }
                        Behavor behavor = new Behavor();
                        behavor.setSeedID("H5_LOCATION_FAILED");
                        behavor.setUserCaseID("H5_LOCATION");
                        behavor.setBehaviourPro(RPCDataItems.LBSINFO);
                        behavor.setParam1(String.valueOf(errorCode));
                        LoggerFactory.getBehavorLogger().event(null, behavor);
                    }
                }
            };
            try {
                H5Utils.runOnMain(timeoutRunnable, (long) timeOut);
                boolean isHighAccuracy = false;
                boolean isNeedSpeed = false;
                try {
                    isHighAccuracy = H5Utils.getBoolean(event.getParam(), (String) "isHighAccuracy", false);
                    isNeedSpeed = H5Utils.getBoolean(event.getParam(), (String) "isNeedSpeed", false);
                } catch (Throwable e5) {
                    LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e5);
                }
                LBSLocationRequest request = new LBSLocationRequest();
                request.setIsHighAccuracy(isHighAccuracy);
                request.setNeedAddress(needAddress);
                request.setOnceLocation(true);
                request.setTimeOut((long) timeOut);
                request.setCacheTimeInterval((long) (cacheTimeout * 1000));
                request.setBizType(biz);
                request.setNeedSpeed(isNeedSpeed);
                request.setReGeoLevel(7);
                ((LBSLocationManagerService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(LBSLocationManagerService.class.getName())).locationWithRequest(request, lbsListener);
            } catch (Exception e6) {
                H5Log.e("H5LocationPlugin", "request location exception.", e6);
            }
        }
        return;
    }

    public synchronized void getCurrentLocation(H5Event event, H5BridgeContext bridgeContext2, LocationListener listener, boolean useBufferedLocation, long startTime) {
        if (event == null) {
            LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", (String) "event = null");
        } else {
            H5Log.d("H5LocationPlugin", "getCurrentLocation useBufferedLocation " + useBufferedLocation);
            final long lastLocationTime = System.currentTimeMillis();
            new LocationTask().callback = listener;
            JSONObject param = event.getParam();
            int timeOut = 10000;
            try {
                timeOut = H5Utils.getInt(param, (String) "timeout", 10) * 1000;
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e);
            }
            boolean needAddress = false;
            String biz = H5Utils.getString(event.getParam(), (String) "bizType", (String) null);
            if (TextUtils.isEmpty(biz)) {
                String host = null;
                try {
                    host = new URL(((H5Page) event.getTarget()).getUrl()).getHost();
                } catch (MalformedURLException e2) {
                    LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", (Throwable) e2);
                }
                biz = host;
            }
            int cacheTimeout = 30;
            try {
                cacheTimeout = H5Utils.getInt(param, (String) "cacheTimeout", 30);
            } catch (Throwable e3) {
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e3);
            }
            int mRequestType = 0;
            try {
                mRequestType = H5Utils.getInt(param, (String) "requestType", 0);
            } catch (Throwable e4) {
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e4);
            }
            final int requestType = mRequestType;
            if (mRequestType > 0 && requestType < 4) {
                needAddress = true;
            }
            if (useBufferedLocation) {
                if (lastLocRes != null && requestType == lastRequestType) {
                    H5Log.d("H5LocationPlugin", "getCurrentLocation for last location result");
                    if (bridgeContext2 != null) {
                        bridgeContext2.sendBridgeResult(lastLocRes);
                    }
                    if (listener != null) {
                        listener.onLocationResult(lastLocRes, requestType);
                    }
                }
            }
            if (locationStartTime == 0 || System.currentTimeMillis() - locationStartTime > ((long) (cacheTimeout * 1000))) {
                geoLocRes = null;
            } else if (geoLocRes != null && requestType == 0) {
                H5Log.d("H5LocationPlugin", "getCurrentLocation for geo location cache result");
                if (bridgeContext2 != null) {
                    bridgeContext2.sendBridgeResult(geoLocRes);
                }
                if (listener != null) {
                    listener.onLocationResult(geoLocRes, requestType);
                }
            }
            lastRequestType = requestType;
            final long requestTime = System.currentTimeMillis();
            final TimeoutRunnable timeoutRunnable = new TimeoutRunnable(bridgeContext2, timeOut);
            final H5BridgeContext h5BridgeContext = bridgeContext2;
            final long j = startTime;
            final LocationListener locationListener = listener;
            OnLBSLocationListener lbsListener = new OnLBSLocationListener() {
                public void onLocationUpdate(LBSLocation location) {
                    H5Log.d("H5LocationPlugin", "onLocationUpdate in getCurrentLocation");
                    long locationEnd = System.currentTimeMillis();
                    int locateDuration = (int) (locationEnd - requestTime);
                    if (!timeoutRunnable.checkAndChangeTimeArriving("getCurrentLocation, onLocationUpdate")) {
                        if (location != null) {
                            JSONObject result = new JSONObject();
                            result.put((String) "latitude", (Object) Double.valueOf(location.getLatitude()));
                            result.put((String) "longitude", (Object) Double.valueOf(location.getLongitude()));
                            result.put((String) CameraControllerManager.MY_POILOCATION_ACR, (Object) Float.valueOf(location.getAccuracy()));
                            result.put((String) "speed", (Object) Float.valueOf(location.getSpeed()));
                            ReGeocodeResult reGeocodeResult = location.getReGeocodeResult();
                            if (requestType > 0 && reGeocodeResult != null) {
                                result.put((String) "country", (Object) reGeocodeResult.getCountry());
                                result.put((String) "countryCode", (Object) reGeocodeResult.getCountryCode());
                                result.put((String) "province", (Object) reGeocodeResult.getProvince());
                                result.put((String) "city", (Object) reGeocodeResult.getCity());
                                result.put((String) "cityAdcode", (Object) reGeocodeResult.getCityAdcode());
                                result.put((String) "district", (Object) reGeocodeResult.getDistrict());
                                result.put((String) "districtAdcode", (Object) reGeocodeResult.getDistrictAdcode());
                            }
                            if (requestType >= 2 && reGeocodeResult != null) {
                                StreetNumber streetNumber = reGeocodeResult.getStreetNumber();
                                JSONObject streetObject = new JSONObject();
                                H5Log.d("H5LocationPlugin", "getLocation streetNumber " + streetNumber.getNumber());
                                streetObject.put((String) "number", (Object) streetNumber.getNumber());
                                streetObject.put((String) "street", (Object) streetNumber.getStreet());
                                result.put((String) "streetNumber", (Object) streetObject);
                            }
                            if (requestType >= 3 && reGeocodeResult != null) {
                                List poitemList = reGeocodeResult.getPois();
                                JSONArray jsonArray = new JSONArray();
                                if (poitemList != null) {
                                    for (PoiItem poiItem : poitemList) {
                                        JSONObject poiObject = new JSONObject();
                                        poiObject.put((String) "name", (Object) poiItem.getTitle());
                                        poiObject.put((String) "address", (Object) poiItem.getSnippet());
                                        jsonArray.add(poiObject);
                                    }
                                }
                                result.put((String) "pois", (Object) jsonArray);
                            }
                            long locatingInterval = System.currentTimeMillis() - j;
                            LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "H5Location#getLocation#LBSLocationListener.onLocationUpdate()#jsapi cost time:" + (System.currentTimeMillis() - locationEnd) + RPCDataParser.TIME_MS);
                            LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "H5Location#getLocation#LBSLocationListener.onLocationUpdate()#h5 location success cost time:" + locatingInterval + RPCDataParser.TIME_MS);
                            if (h5BridgeContext != null) {
                                h5BridgeContext.sendBridgeResult(result);
                            }
                            H5Location.lastLocRes = result;
                            if (locationListener != null) {
                                locationListener.onLocationResult(result, requestType);
                            }
                            LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "H5Location#getCurrentLocation#LBSLocationListener.onLocationUpdate()#jsapi remove callback");
                            Behavor behavor = new Behavor();
                            behavor.setSeedID("H5_LOCATION_INTERVAL");
                            behavor.setUserCaseID("H5_LOCATION");
                            behavor.setBehaviourPro(RPCDataItems.LBSINFO);
                            behavor.setParam1(String.valueOf(locatingInterval));
                            LoggerFactory.getBehavorLogger().event(null, behavor);
                            JSONObject param = new JSONObject();
                            param.put((String) "result", (Object) "success");
                            param.put((String) "locateDuration", (Object) Integer.valueOf(locateDuration));
                            param.put((String) "locateStart", (Object) requestTime);
                            param.put((String) "locateEnd", (Object) String.valueOf(locationEnd));
                            LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "param:" + param.toJSONString());
                        } else if (h5BridgeContext != null) {
                            JSONObject result2 = new JSONObject();
                            result2.put((String) "error", (Object) Integer.valueOf(13));
                            result2.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.get_location_failed));
                            h5BridgeContext.sendBridgeResult(result2);
                        }
                    }
                }

                public void onLocationFailed(int errorCode) {
                    H5Log.d("H5LocationPlugin", "onLocationFailed() in errorCode:" + errorCode);
                    long locationEnd = System.currentTimeMillis();
                    int locateDuration = (int) (locationEnd - requestTime);
                    if (!timeoutRunnable.checkAndChangeTimeArriving("getCurrentLocation, onLocationFailed")) {
                        JSONObject result = new JSONObject();
                        if (errorCode == 12 || errorCode == 13) {
                            result.put((String) "error", (Object) Integer.valueOf(11));
                            result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.get_location_auth_failed));
                        } else if (errorCode == 4 || errorCode == -1 || errorCode == 30) {
                            result.put((String) "error", (Object) Integer.valueOf(12));
                            result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.get_location_net_failed));
                        } else {
                            result.put((String) "error", (Object) Integer.valueOf(13));
                            result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.get_location_failed));
                        }
                        result.put((String) "extError", (Object) Integer.valueOf(errorCode));
                        JSONObject param = new JSONObject();
                        param.put((String) "result", (Object) "error");
                        param.put((String) "locateDuration", (Object) Integer.valueOf(locateDuration));
                        param.put((String) "errorMessage", result.get("errorMessage"));
                        param.put((String) "errorCode", (Object) String.valueOf(errorCode));
                        param.put((String) "errorType", (Object) result.get("error"));
                        param.put((String) "locateStart", (Object) requestTime);
                        param.put((String) "locateEnd", (Object) String.valueOf(locationEnd));
                        LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "H5Location#getCurrentLocation#LBSLocationListener.onLocationUpdate()#h5 location failed cost time:" + (System.currentTimeMillis() - lastLocationTime) + RPCDataParser.TIME_MS);
                        LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "param:" + param.toJSONString());
                        if (h5BridgeContext != null) {
                            h5BridgeContext.sendBridgeResult(result);
                        }
                        Behavor behavor = new Behavor();
                        behavor.setSeedID("H5_LOCATION_FAILED");
                        behavor.setUserCaseID("H5_LOCATION");
                        behavor.setBehaviourPro(RPCDataItems.LBSINFO);
                        behavor.setParam1(String.valueOf(errorCode));
                        LoggerFactory.getBehavorLogger().event(null, behavor);
                    }
                }
            };
            try {
                H5Utils.runOnMain(timeoutRunnable, (long) timeOut);
                boolean isHighAccuracy = false;
                boolean isNeedSpeed = false;
                try {
                    isHighAccuracy = H5Utils.getBoolean(event.getParam(), (String) "isHighAccuracy", false);
                    isNeedSpeed = H5Utils.getBoolean(event.getParam(), (String) "isNeedSpeed", false);
                } catch (Throwable e5) {
                    LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e5);
                }
                LBSLocationManagerService lbsLocationManagerService = (LBSLocationManagerService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(LBSLocationManagerService.class.getName());
                LBSLocationRequest request = new LBSLocationRequest();
                request.setIsHighAccuracy(isHighAccuracy);
                request.setNeedAddress(needAddress);
                request.setOnceLocation(true);
                request.setTimeOut((long) timeOut);
                request.setCacheTimeInterval((long) (cacheTimeout * 1000));
                request.setBizType(biz);
                request.setNeedSpeed(isNeedSpeed);
                switch (requestType) {
                    case 0:
                        break;
                    case 1:
                        request.setReGeoLevel(5);
                        break;
                    case 2:
                        request.setReGeoLevel(7);
                        break;
                    case 3:
                        request.setReGeoLevel(8);
                        break;
                    default:
                        request.setReGeoLevel(5);
                        break;
                }
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", "getCurrentLocation regeocodeLevel:" + requestType);
                lbsLocationManagerService.locationWithRequest(request, lbsListener);
            } catch (Exception e6) {
                H5Log.e("H5LocationPlugin", "request CurrentLocation exception.", e6);
            }
        }
        return;
    }

    public void openLocation(H5Event event, H5BridgeContext bridgeContext2) {
        H5Log.d("H5LocationPlugin", H5LocationPlugin.OPEN_LOCATION);
        if (event == null) {
            H5Log.d("H5LocationPlugin", "openLocation event == null");
            return;
        }
        try {
            JSONObject param = event.getParam();
            if (param == null) {
                H5Log.d("H5LocationPlugin", "openLocation param == null");
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("latitude", param.getDoubleValue("latitude"));
            intent.putExtra("longitude", param.getDoubleValue("longitude"));
            intent.putExtra(WidgetType.SCALE, param.getDoubleValue(WidgetType.SCALE));
            intent.putExtra("name", H5Utils.getString(param, (String) "name", (String) ""));
            intent.putExtra("address", H5Utils.getString(param, (String) "address", (String) ""));
            intent.putExtra("hidden", H5Utils.getString(param, (String) "hidden", (String) ""));
            intent.setClass(LauncherApplicationAgent.getInstance().getApplicationContext(), H5MapActivity.class);
            MicroApplicationContext microApp = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
            if (microApp.getTopApplication() != null) {
                microApp.startActivity((MicroApplication) microApp.getTopApplication(), intent);
                return;
            }
            Context launchContext = (Context) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
            if (launchContext == null) {
                launchContext = LauncherApplicationAgent.getInstance().getApplicationContext();
                intent.setFlags(268435456);
            }
            if (launchContext != null) {
                launchContext.startActivity(intent);
            }
        } catch (Exception e) {
            H5Log.e("H5LocationPlugin", "openLocation exception.", e);
        }
    }

    /* access modifiers changed from: private */
    public void onRequestTimeout(H5BridgeContext bridgeContext2) {
        H5Log.d("H5LocationPlugin", "onRequestTimeout");
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(14));
        result.put((String) "errorMessage", (Object) "业务定位超时");
        if (bridgeContext2 != null) {
            bridgeContext2.sendBridgeResult(result);
        }
        new JSONObject().put((String) "result", (Object) "error");
    }

    public void startIndoorLocation(H5Event event, H5BridgeContext bridgeContext2) {
        if (event == null) {
            LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", (String) "event = null");
            return;
        }
        this.bridgeContext = bridgeContext2;
        JSONObject data = new JSONObject();
        data.put((String) "success", (Object) Boolean.valueOf(false));
        if (this.bridgeContext != null) {
            this.bridgeContext.sendBridgeResult(data);
        }
    }

    public void stopIndoorLocation() {
        synchronized (this) {
            this.bridgeContext = null;
        }
    }

    public synchronized void startContinuousLocation(H5Event event, H5BridgeContext bridgeContext2) {
        if (this.isStartLocation) {
            LoggerFactory.getTraceLogger().info("H5LocationPlugin", "isStartLocation");
        } else if (event == null) {
            LoggerFactory.getTraceLogger().info("H5LocationPlugin", "event = null");
        } else {
            this.bridgeContext = bridgeContext2;
            String interval = H5Utils.getString(event.getParam(), (String) "callbackInterval", (String) "2000");
            long locationInterval = 2000;
            String bizType2 = H5Utils.getString(event.getParam(), (String) "bizType", (String) null);
            LoggerFactory.getTraceLogger().info("H5LocationPlugin", "callbackInterval:" + interval + "|bizType:" + bizType2);
            try {
                locationInterval = Long.parseLong(interval);
            } catch (Throwable e) {
                JSONObject result = new JSONObject();
                result.put((String) "error", (Object) Integer.valueOf(2));
                result.put((String) "errorMessage", (Object) "无效参数");
                bridgeContext2.sendBridgeResultWithCallbackKept(result);
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e);
            }
            if (TextUtils.isEmpty(bizType2)) {
                JSONObject result2 = new JSONObject();
                result2.put((String) "error", (Object) Integer.valueOf(2));
                result2.put((String) "errorMessage", (Object) "缺少业务场景参数");
                if (bridgeContext2 != null) {
                    bridgeContext2.sendBridgeResultWithCallbackKept(result2);
                }
                this.isStartLocation = false;
            } else {
                boolean isInBlackList = false;
                try {
                    ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
                    if (configService != null) {
                        String config = configService.getConfig("H5ContinuousAppBlackList");
                        if (!TextUtils.isEmpty(config)) {
                            String[] apps = config.split(MergeUtil.SEPARATOR_KV);
                            int length = apps.length;
                            int i = 0;
                            while (true) {
                                if (i >= length) {
                                    break;
                                } else if (bizType2.equals(apps[i])) {
                                    isInBlackList = true;
                                    break;
                                } else {
                                    i++;
                                }
                            }
                        }
                    }
                } catch (Throwable e2) {
                    LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e2);
                }
                if (isInBlackList) {
                    LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", (String) "biz in blackList!");
                    JSONObject result3 = new JSONObject();
                    result3.put((String) "error", (Object) Integer.valueOf(12));
                    result3.put((String) "errorMessage", (Object) "定位失败");
                    if (bridgeContext2 != null) {
                        bridgeContext2.sendBridgeResultWithCallbackKept(result3);
                    }
                } else {
                    this.startCountinuousTime = System.currentTimeMillis();
                    if (!ActivityHelper.isBackgroundRunning()) {
                        boolean isNeedSpeed = false;
                        try {
                            isNeedSpeed = H5Utils.getBoolean(event.getParam(), (String) "isNeedSpeed", false);
                        } catch (Throwable e3) {
                            LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e3);
                        }
                        LBSLocationRequest request = new LBSLocationRequest();
                        request.setIsHighAccuracy(true);
                        request.setCallbackInterval(locationInterval);
                        request.setOnceLocation(false);
                        request.setBizType(bizType2);
                        request.setNeedSpeed(isNeedSpeed);
                        ((LBSLocationManagerService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(LBSLocationManagerService.class.getName())).locationWithRequest(request, this.continuousListener);
                        this.isStartLocation = true;
                        this.bizType = bizType2;
                        Behavor behavor = new Behavor();
                        behavor.setSeedID("continuousLocation");
                        behavor.setBehaviourPro(RPCDataItems.LBSINFO);
                        behavor.setParam1(H5PageData.KEY_UC_START);
                        behavor.setParam2(bizType2);
                        LoggerFactory.getBehavorLogger().event(null, behavor);
                    }
                }
            }
        }
    }

    public synchronized void stopContinuousLocation() {
        try {
            ((LBSLocationManagerService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(LBSLocationManagerService.class.getName())).stopLocation(this.continuousListener);
            if (this.bridgeContext != null) {
                JSONObject result = new JSONObject();
                result.put((String) "success", (Object) Boolean.valueOf(true));
                this.bridgeContext.sendBridgeResultWithCallbackKept(result);
            }
            Behavor behavor = new Behavor();
            behavor.setSeedID("continuousLocation");
            behavor.setBehaviourPro(RPCDataItems.LBSINFO);
            behavor.setParam1(AudioUtils.CMDSTOP);
            behavor.setParam2(this.bizType);
            behavor.setParam3((System.currentTimeMillis() - this.startCountinuousTime));
            LoggerFactory.getBehavorLogger().event(null, behavor);
        } catch (Throwable e) {
            if (this.bridgeContext != null) {
                JSONObject result2 = new JSONObject();
                result2.put((String) "success", (Object) Boolean.valueOf(false));
                this.bridgeContext.sendBridgeResultWithCallbackKept(result2);
            }
            LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e);
        }
        this.isStartLocation = false;
    }
}
