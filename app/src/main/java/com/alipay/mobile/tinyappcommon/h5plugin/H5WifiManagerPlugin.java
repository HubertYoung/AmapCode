package com.alipay.mobile.tinyappcommon.h5plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import java.lang.reflect.Method;
import java.util.List;

public class H5WifiManagerPlugin extends H5SimplePlugin {
    private static final int ERROR_12000 = 12000;
    private static final int ERROR_12001 = 12001;
    private static final int ERROR_12004 = 12004;
    private static final int ERROR_12005 = 12005;
    private static final int ERROR_12006 = 12006;
    private static final int ERROR_12010 = 12010;
    private static final int ERROR_12011 = 12011;
    private static final String EVENT_CONNECT_WIFI = "connectWifi";
    private static final String EVENT_GET_CONNECTED_WIFI = "getConnectedWifi";
    private static final String EVENT_GET_WIFI_LIST = "getWifiList";
    private static final String EVENT_START_WIFI = "startWifi";
    private static final String EVENT_STOP_WIFI = "stopWifi";
    private static final String LOG_TAG = "H5WifiManagerPlugin";
    private H5BridgeContext bridgeContext;
    private boolean flagPaused = false;
    private boolean isRegistered = false;
    private boolean isWifiStarted = false;
    private BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            H5Log.d(H5WifiManagerPlugin.LOG_TAG, "onReceiveWifiBroadcast... action = " + action);
            char c = 65535;
            switch (action.hashCode()) {
                case -1875733435:
                    if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                        c = 1;
                        break;
                    }
                    break;
                case -343630553:
                    if (action.equals("android.net.wifi.STATE_CHANGE")) {
                        c = 0;
                        break;
                    }
                    break;
                case 1878357501:
                    if (action.equals("android.net.wifi.SCAN_RESULTS")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    H5WifiManagerPlugin.this.checkNetWorkState(intent);
                    return;
                case 1:
                    H5WifiManagerPlugin.this.checkWifiState(intent);
                    return;
                case 2:
                    H5WifiManagerPlugin.this.processScanResult();
                    return;
                default:
                    return;
            }
        }
    };
    private WifiManager wifiManager;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(EVENT_START_WIFI);
        filter.addAction(EVENT_STOP_WIFI);
        filter.addAction(EVENT_CONNECT_WIFI);
        filter.addAction(EVENT_GET_WIFI_LIST);
        filter.addAction(EVENT_GET_CONNECTED_WIFI);
        filter.addAction(CommonEvents.H5_SESSION_PAUSE);
        filter.addAction(CommonEvents.H5_SESSION_RESUME);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (CommonEvents.H5_SESSION_PAUSE.equals(action)) {
            H5Log.d(LOG_TAG, "interceptEvent... H5_SESSION_PAUSE");
            this.flagPaused = true;
            unregisterWifiReceiver();
        } else if (CommonEvents.H5_SESSION_RESUME.equals(action)) {
            H5Log.d(LOG_TAG, "interceptEvent... H5_SESSION_PAUSE");
            if (this.flagPaused) {
                registerWifiReceiver();
            }
            this.flagPaused = false;
        }
        return super.interceptEvent(event, context);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        this.bridgeContext = context;
        String action = event.getAction();
        H5Log.d(LOG_TAG, "handleEvent... action = " + action);
        if (EVENT_START_WIFI.equals(action)) {
            this.isWifiStarted = startWifi();
            return true;
        } else if (EVENT_STOP_WIFI.equals(action)) {
            stopWifi();
            return true;
        } else if (EVENT_CONNECT_WIFI.equals(action)) {
            connectWifi(event);
            return true;
        } else if (EVENT_GET_WIFI_LIST.equals(action)) {
            getWifiList();
            return true;
        } else if (!EVENT_GET_CONNECTED_WIFI.equals(action)) {
            return false;
        } else {
            getConnectedWifi();
            return true;
        }
    }

    public void onRelease() {
        super.onRelease();
        H5Log.d(LOG_TAG, "onRelease... ");
        stopWifi();
        this.bridgeContext = null;
    }

    private WifiManager getWifiManager() {
        if (this.wifiManager == null) {
            this.wifiManager = (WifiManager) H5Utils.getContext().getApplicationContext().getSystemService("wifi");
        }
        return this.wifiManager;
    }

    private boolean startWifi() {
        getWifiManager();
        try {
            boolean isWifiEnable = this.wifiManager.isWifiEnabled();
            H5Log.d(LOG_TAG, "startWifi... isWifiEnable = " + isWifiEnable);
            if (!isWifiEnable) {
                this.bridgeContext.sendError((int) ERROR_12005, (String) "未打开 Wi-Fi 开关");
            }
            boolean result = isWifiEnable;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(result ? "success" : UploadDataResult.FAIL_MSG, (Object) Boolean.valueOf(true));
            this.bridgeContext.sendBridgeResult(jsonObject);
            H5Log.d(LOG_TAG, "startWifi... flag isWifiStarted = " + result);
            return result;
        } catch (Exception e) {
            H5Log.e(LOG_TAG, "startWifi... fail with exception", e);
            this.bridgeContext.sendError((int) ERROR_12001, (String) "当前系统不支持相关能力");
            return false;
        }
    }

    private boolean stopWifi() {
        getWifiManager();
        if (!this.isWifiStarted) {
            this.bridgeContext.sendError(12000, (String) "未先调用startWifi接口");
        } else {
            if (!this.wifiManager.isWifiEnabled()) {
                this.bridgeContext.sendError((int) ERROR_12005, (String) "未打开 Wi-Fi 开关");
            }
            try {
                unregisterWifiReceiver();
                this.isWifiStarted = false;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put((String) "success", (Object) Boolean.valueOf(true));
                this.bridgeContext.sendBridgeResult(jsonObject);
            } catch (Exception e) {
                H5Log.e(LOG_TAG, "stopWifi... fail with exception", e);
                this.bridgeContext.sendError((int) ERROR_12001, (String) "当前系统不支持相关能力");
                this.isWifiStarted = false;
            }
        }
        return false;
    }

    private boolean getWifiList() {
        boolean result = false;
        getWifiManager();
        if (!this.isWifiStarted) {
            this.bridgeContext.sendError(12000, (String) "未先调用startWifi接口");
        } else {
            if (!this.wifiManager.isWifiEnabled()) {
                this.bridgeContext.sendError((int) ERROR_12005, (String) "未打开 Wi-Fi 开关");
            }
            boolean isAppPermissionOPen = isAppPermissionOPen();
            boolean isGpsSwitchOPen = isGpsSwitchOPen();
            H5Log.e((String) LOG_TAG, "getWifiList... isAppPermissionOPen = " + isAppPermissionOPen + " & isGpsSwitchOPen = " + isGpsSwitchOPen);
            if (!isAppPermissionOPen) {
                this.bridgeContext.sendError((int) ERROR_12010, (String) "系统其他错误: 未获得定位权限");
            } else if (!isGpsSwitchOPen) {
                this.bridgeContext.sendError((int) ERROR_12006, (String) "未打开 GPS 定位开关");
            } else {
                registerWifiReceiver();
                result = this.wifiManager.startScan();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(result ? "success" : UploadDataResult.FAIL_MSG, (Object) Boolean.valueOf(true));
                this.bridgeContext.sendBridgeResult(jsonObject);
            }
        }
        return result;
    }

    private boolean getConnectedWifi() {
        getWifiManager();
        if (!this.isWifiStarted) {
            this.bridgeContext.sendError(12000, (String) "未先调用startWifi接口");
            return false;
        }
        if (!this.wifiManager.isWifiEnabled()) {
            this.bridgeContext.sendError((int) ERROR_12005, (String) "未打开 Wi-Fi 开关");
        }
        WifiInfo info = this.wifiManager.getConnectionInfo();
        if (info != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "SSID", (Object) info.getSSID());
            jsonObject.put((String) "BSSID", (Object) info.getBSSID());
            jsonObject.put((String) "secure", (Object) Boolean.valueOf(checkWifiSecurity(info)));
            jsonObject.put((String) "signalStrength", (Object) Integer.valueOf(WifiManager.calculateSignalLevel(info.getRssi(), 100)));
            JSONObject params = new JSONObject();
            params.put((String) "wifi", (Object) jsonObject);
            H5Log.d(LOG_TAG, "getConnectedWifi... wifiInfo = " + jsonObject.toJSONString());
            this.bridgeContext.sendBridgeResult(params);
            return true;
        }
        JSONObject failObject = new JSONObject();
        failObject.put((String) UploadDataResult.FAIL_MSG, (Object) Boolean.valueOf(true));
        this.bridgeContext.sendBridgeResult(failObject);
        return false;
    }

    private boolean connectWifi(H5Event event) {
        if (event == null || event.getParam() == null) {
            return false;
        }
        getWifiManager();
        if (!this.isWifiStarted) {
            this.bridgeContext.sendError(12000, (String) "未先调用startWifi接口");
            return false;
        } else if (!this.wifiManager.isWifiEnabled()) {
            this.bridgeContext.sendError((int) ERROR_12005, (String) "未打开 Wi-Fi 开关");
            return false;
        } else {
            JSONObject params = event.getParam();
            String ssid = params.getString("SSID");
            String bssid = params.getString("BSSID");
            String pw = params.getString("password");
            Boolean isWep = params.getBoolean("isWEP");
            if (TextUtils.isEmpty(ssid) || TextUtils.isEmpty(bssid)) {
                this.bridgeContext.sendError(event, Error.INVALID_PARAM);
            }
            WifiInfo info = this.wifiManager.getConnectionInfo();
            if (info != null) {
                String connectSSID = info.getSSID();
                String connectBSSID = info.getBSSID();
                H5Log.d(LOG_TAG, "connectWifi... now connecting SSID = " + connectSSID + " BSSID = " + connectBSSID);
                if (TextUtils.equals(ssid, connectSSID) && TextUtils.equals(bssid, connectBSSID)) {
                    H5Log.d(LOG_TAG, "connectWifi... 重复连接 Wi-Fi ");
                    this.bridgeContext.sendError((int) ERROR_12004, (String) "重复连接 Wi-Fi");
                    return true;
                }
            }
            int netId = getNetworkIdFromConfig(ssid, bssid);
            if (netId < 0) {
                H5Log.d(LOG_TAG, "connectWifi... no config found, create new WifiConfig, ssid = " + ssid + " bssid = " + bssid + " pw = " + pw + " isWep = " + isWep);
                try {
                    netId = addWifiConfig(ssid, bssid, pw, isWep);
                } catch (Exception e) {
                    H5Log.e(LOG_TAG, "connectWifi... addWifiConfig fail, ", e);
                    this.bridgeContext.sendError((int) ERROR_12011, (String) "应用在后台无法配置 Wi-Fi");
                    return false;
                }
            }
            if (netId >= 0) {
                H5Log.d(LOG_TAG, "connectWifi... create success, and connect");
                boolean result = this.wifiManager.enableNetwork(netId, true);
                registerWifiReceiver();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(result ? "success" : UploadDataResult.FAIL_MSG, (Object) Boolean.valueOf(true));
                this.bridgeContext.sendBridgeResult(jsonObject);
                return result;
            }
            H5Log.d(LOG_TAG, "connectWifi... create config fail");
            this.bridgeContext.sendError((int) ERROR_12011, (String) "应用在后台无法配置 Wi-Fi");
            return false;
        }
    }

    private void registerWifiReceiver() {
        Context context = H5Utils.getContext();
        if (!this.isRegistered && context != null) {
            H5Log.d(LOG_TAG, "registerWifiReceiver... is not Registered , register receiver!!");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            intentFilter.addAction("android.net.wifi.STATE_CHANGE");
            intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            context.registerReceiver(this.mWifiReceiver, intentFilter);
            this.isRegistered = true;
        }
    }

    private void unregisterWifiReceiver() {
        Context context = H5Utils.getContext();
        if (this.isRegistered && context != null) {
            H5Log.d(LOG_TAG, "unregisterWifiReceiver... isRegistered = true, unregister receiver");
            context.unregisterReceiver(this.mWifiReceiver);
            this.isRegistered = false;
        }
    }

    /* access modifiers changed from: private */
    public void checkNetWorkState(Intent intent) {
        NetworkInfo info = (NetworkInfo) intent.getParcelableExtra("networkInfo");
        if (info.getDetailedState().equals(DetailedState.DISCONNECTED)) {
            H5Log.d(LOG_TAG, "onReceiveWifiBroadcast... onWifiDisconnect");
        } else if (info.getDetailedState().equals(DetailedState.CONNECTING)) {
            H5Log.d(LOG_TAG, "onReceiveWifiBroadcast... onWifiConnecting");
        } else if (info.getDetailedState().equals(DetailedState.CONNECTED)) {
            H5Log.d(LOG_TAG, "onReceiveWifiBroadcast... onWifiConnected");
            processWifiConnectedCallBack();
        } else if (info.getDetailedState().equals(DetailedState.OBTAINING_IPADDR)) {
            H5Log.d(LOG_TAG, "onReceiveWifiBroadcast... onWifiGettingIP");
        } else if (info.getDetailedState().equals(DetailedState.FAILED)) {
            H5Log.d(LOG_TAG, "onReceiveWifiBroadcast... onWifiConnecting");
        }
    }

    /* access modifiers changed from: private */
    public void checkWifiState(Intent intent) {
        getWifiManager();
        if (intent.getIntExtra("wifi_state", 1) == 0) {
            H5Log.d(LOG_TAG, "onReceiveWifiBroadcast... onWifi DISABLING");
        } else if (this.wifiManager.getWifiState() == 1) {
            H5Log.d(LOG_TAG, "onReceiveWifiBroadcast... onWifi DISABLED");
        } else if (this.wifiManager.getWifiState() == 2) {
            H5Log.d(LOG_TAG, "onReceiveWifiBroadcast... onWifi ENABLING");
        } else if (this.wifiManager.getWifiState() == 3) {
            H5Log.d(LOG_TAG, "onReceiveWifiBroadcast... onWifi ENABLED");
        } else if (this.wifiManager.getWifiState() == 4) {
            H5Log.d(LOG_TAG, "onReceiveWifiBroadcast... onWifi UNKNOWN");
        }
    }

    /* access modifiers changed from: private */
    public void processScanResult() {
        try {
            List resultList = this.wifiManager.getScanResults();
            if (resultList == null || resultList.size() <= 0) {
                this.bridgeContext.sendError((int) ERROR_12010, (String) "系统其他错误: 扫描wifi失败");
                return;
            }
            H5Log.d(LOG_TAG, "processScanResult... getWifiList success, unregisterWifiReceiver");
            unregisterWifiReceiver();
            JSONArray wifiList = new JSONArray(resultList.size() * 2);
            for (ScanResult scanResult : resultList) {
                JSONObject wifiObj = new JSONObject();
                wifiObj.put((String) "SSID", (Object) scanResult.SSID);
                wifiObj.put((String) "BSSID", (Object) scanResult.BSSID);
                wifiObj.put((String) "secure", (Object) Boolean.valueOf(getSecurity(scanResult.capabilities) > 0));
                wifiObj.put((String) "signalStrength", (Object) Integer.valueOf(WifiManager.calculateSignalLevel(scanResult.level, 100)));
                wifiList.add(wifiObj);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "wifiList", (Object) wifiList);
            JSONObject resultObj = new JSONObject();
            resultObj.put((String) "data", (Object) jsonObject);
            H5Log.d(LOG_TAG, "processScanResult... onGetWifiList, wifiList = " + wifiList.toJSONString());
            this.bridgeContext.sendToWeb(EVENT_GET_WIFI_LIST, resultObj, null);
        } catch (Exception e) {
            H5Log.e(LOG_TAG, "processScanResult... fail with exception", e);
            this.bridgeContext.sendError((int) ERROR_12010, "系统其他错误: " + e.getClass().getName());
        }
    }

    private void processWifiConnectedCallBack() {
        getWifiManager();
        WifiInfo info = this.wifiManager.getConnectionInfo();
        if (info != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "SSID", (Object) info.getSSID());
            jsonObject.put((String) "BSSID", (Object) info.getBSSID());
            jsonObject.put((String) "secure", (Object) Boolean.valueOf(checkWifiSecurity(info)));
            jsonObject.put((String) "signalStrength", (Object) Integer.valueOf(WifiManager.calculateSignalLevel(info.getRssi(), 100)));
            JSONObject params = new JSONObject();
            params.put((String) "wifi", (Object) jsonObject);
            JSONObject resultObj = new JSONObject();
            resultObj.put((String) "data", (Object) params);
            H5Log.d(LOG_TAG, "processWifiConnectedCallBack... onWifiConnected, JsonParams = " + jsonObject.toJSONString());
            this.bridgeContext.sendToWeb("wifiConnected", resultObj, null);
        }
    }

    private boolean checkWifiSecurity(WifiInfo info) {
        List wifiConfigList = this.wifiManager.getConfiguredNetworks();
        if (wifiConfigList == null) {
            return false;
        }
        for (WifiConfiguration wifiConfiguration : wifiConfigList) {
            String configSSid = wifiConfiguration.SSID.replace("\"", "");
            String currentSSid = info.getSSID().replace("\"", "");
            H5Log.d(LOG_TAG, "checkWifiSecurity... currentSSid = " + currentSSid + " configSSid = " + configSSid + " networkId = " + wifiConfiguration.networkId);
            if (TextUtils.equals(currentSSid, configSSid) && info.getNetworkId() == wifiConfiguration.networkId) {
                int securityLevel = getSecurity(wifiConfiguration);
                H5Log.d(LOG_TAG, "checkWifiSecurity... 当前网络安全性：" + securityLevel);
                if (securityLevel > 0) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private static int getSecurity(WifiConfiguration config) {
        if (config.allowedKeyManagement.get(1)) {
            return 2;
        }
        if (config.allowedKeyManagement.get(2) || config.allowedKeyManagement.get(3)) {
            return 3;
        }
        return config.wepKeys[0] != null ? 1 : 0;
    }

    private static int getSecurity(String capabilities) {
        if (TextUtils.isEmpty(capabilities)) {
            return 0;
        }
        if (capabilities.contains("WPA") || capabilities.contains("wpa")) {
            return 2;
        }
        if (capabilities.contains("WEP") || capabilities.contains("wep")) {
            return 1;
        }
        if (capabilities.contains("EAP") || capabilities.contains("eap")) {
            return 3;
        }
        return 0;
    }

    private int getNetworkIdFromConfig(String ssid, String bssid) {
        List wifiLists = this.wifiManager.getConfiguredNetworks();
        if (wifiLists == null || wifiLists.size() <= 0) {
            return -1;
        }
        String ssIdName = "\"" + ssid + "\"";
        for (WifiConfiguration config : wifiLists) {
            if (TextUtils.equals(config.SSID, ssIdName) && TextUtils.equals(config.BSSID, bssid)) {
                return config.networkId;
            }
        }
        return -1;
    }

    private int addWifiConfig(String ssid, String bssid, String pwd, Boolean isWep) {
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + ssid + "\"";
        config.BSSID = bssid;
        config.hiddenSSID = false;
        config.status = 2;
        if (TextUtils.isEmpty(pwd)) {
            config.wepKeys[0] = "\"" + pwd + "\"";
            config.allowedKeyManagement.set(0);
            config.wepTxKeyIndex = 0;
        } else if (isWep == null || !isWep.booleanValue()) {
            config.preSharedKey = "\"" + pwd + "\"";
        } else {
            config.wepKeys[0] = "\"" + pwd + "\"";
            config.allowedKeyManagement.set(0);
            config.wepTxKeyIndex = 0;
        }
        return this.wifiManager.addNetwork(config);
    }

    private boolean isGpsSwitchOPen() {
        Context context = LauncherApplicationAgent.getInstance().getApplicationContext();
        H5Log.d(LOG_TAG, "Build.VERSION.SDK_INT=" + VERSION.SDK_INT);
        if (VERSION.SDK_INT < 24) {
            return true;
        }
        try {
            if (Secure.getInt(context.getContentResolver(), "location_mode", 0) != 0) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            H5Log.e((String) LOG_TAG, "isGpsSwitchOPen, error,msg=" + th);
            return true;
        }
    }

    private static boolean isAppPermissionOPen() {
        try {
            Context context = LoggerFactory.getLogContext().getApplicationContext();
            if (context == null) {
                return false;
            }
            if (VERSION.SDK_INT < 23) {
                return isLessThanMarshmallowHasLocation(context);
            }
            if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) LOG_TAG, e);
            return false;
        }
    }

    private static boolean isLessThanMarshmallowHasLocation(Context context) {
        if (VERSION.SDK_INT >= 19) {
            return isPermissionGranted(context, 0);
        }
        return true;
    }

    private static boolean isPermissionGranted(Context context, int permissionCode) {
        try {
            Object object = context.getSystemService("appops");
            if (object == null) {
                return false;
            }
            Method method = object.getClass().getMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class});
            if (method == null) {
                return false;
            }
            return ((Integer) method.invoke(object, new Object[]{Integer.valueOf(permissionCode), Integer.valueOf(Binder.getCallingUid()), context.getPackageName()})).intValue() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
