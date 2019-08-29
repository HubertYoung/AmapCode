package com.alipay.mobile.nebula.appcenter.apphandler;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.H5BaseApp;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.callback.H5SimpleRpcListener;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5SimpleRpcProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5SharedPreUtil;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

public class H5AppScoreList {
    private static final int H5_LIMIT_CODE = 100201;
    public static final int PRE_DOWNLOAD_IN_WIFI_STRATEGY = 4;
    public static final int PRE_ZIP_STRATEGY = 1;
    private static final String RPC_LIMIT_TAG = "limit_tag";
    private static final String SCORE_RPC_TYPE = "com.alipay.hpmweb.queryAppCredit";
    public static final int STRONG_REQ_STRATEGY = 2;
    private static final String TAG = "H5AppScoreList";
    private static H5AppScoreList instance;
    /* access modifiers changed from: private */
    public H5AppCreditInfo appCreditInfo;
    private boolean enable;
    private long limitRate;
    private long reqRate;

    private H5AppScoreList() {
        initSwitchConfig();
    }

    public static synchronized H5AppScoreList getInstance() {
        H5AppScoreList h5AppScoreList;
        synchronized (H5AppScoreList.class) {
            try {
                if (instance == null) {
                    instance = new H5AppScoreList();
                }
                h5AppScoreList = instance;
            }
        }
        return h5AppScoreList;
    }

    public boolean isInStrategy(String appId, int strategy) {
        if (this.appCreditInfo == null || this.appCreditInfo.getStrategyMap() == null || !this.appCreditInfo.getStrategyMap().containsKey(Integer.valueOf(strategy)) || TextUtils.isEmpty(appId)) {
            return false;
        }
        List appIdList = this.appCreditInfo.getStrategyMap().get(Integer.valueOf(strategy));
        if (appIdList != null) {
            return appIdList.contains(appId);
        }
        return false;
    }

    public List<String> getAppListWithStrategy(int strategy) {
        if (this.appCreditInfo == null || this.appCreditInfo.getStrategyMap() == null) {
            return null;
        }
        return this.appCreditInfo.getStrategyMap().get(Integer.valueOf(strategy));
    }

    public String getAppCredit(String appId) {
        if (this.appCreditInfo == null || this.appCreditInfo.getCreditMap() == null || this.appCreditInfo.getCreditMap().size() == 0) {
            return "0";
        }
        for (Entry mapEntry : this.appCreditInfo.getCreditMap().entrySet()) {
            List appList = (List) mapEntry.getValue();
            if (appList != null && appList.size() > 0 && appList.contains(appId)) {
                return (String) mapEntry.getKey();
            }
        }
        return "0";
    }

    public void updateAppScoreInfo(final boolean forceRequest, final H5AppScoreRpcListener rpcListener) {
        if (!this.enable) {
            invokeCallback(rpcListener, false);
        } else {
            H5Utils.getExecutor(H5ThreadType.NORMAL).execute(new Runnable() {
                public void run() {
                    if (H5AppScoreList.this.enableSendRpc() || forceRequest) {
                        H5SimpleRpcProvider rpcProvider = (H5SimpleRpcProvider) H5Utils.getProvider(H5SimpleRpcProvider.class.getName());
                        if (rpcProvider != null) {
                            rpcProvider.sendSimpleRpc(H5AppScoreList.SCORE_RPC_TYPE, null, null, true, null, null, false, null, new H5SimpleRpcListener() {
                                public void onSuccess(String response) {
                                    H5Log.d(H5AppScoreList.TAG, " response : " + response);
                                    H5AppScoreList.this.saveResponse(response);
                                    H5AppScoreList.this.initAppCreditInfo(H5Utils.parseObject(response));
                                    H5AppScoreList.this.invokeCallback(rpcListener, true);
                                }

                                public void onFailed(int errorCode, String errorMessage) {
                                    if (H5AppScoreList.H5_LIMIT_CODE == errorCode) {
                                        H5SharedPreUtil.saveLongData(H5SharedPreUtil.H5_APP_SCORE_RPC_TIME, System.currentTimeMillis());
                                        H5SharedPreUtil.saveStringData(H5SharedPreUtil.H5_SCORE_RPC_LIMIT, H5AppScoreList.RPC_LIMIT_TAG);
                                    }
                                    if (forceRequest) {
                                        H5SharedPreUtil.removeData(H5SharedPreUtil.H5_APP_SCORE_RPC_TIME);
                                    }
                                    H5AppScoreList.this.initData();
                                    H5Log.d(H5AppScoreList.TAG, "errorCode : " + errorCode + " errorMessage : " + errorMessage);
                                    H5AppScoreList.this.invokeCallback(rpcListener, false);
                                }
                            });
                            return;
                        }
                        return;
                    }
                    H5AppScoreList.this.initData();
                    H5AppScoreList.this.invokeCallback(rpcListener, false);
                }
            });
        }
    }

    public void checkPreInstallApp() {
        if (this.enable) {
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    if (H5AppScoreList.this.appCreditInfo == null) {
                        H5AppScoreList.this.initData();
                    }
                    List preInstallList = H5AppScoreList.this.getAppListWithStrategy(1);
                    if (preInstallList != null && !preInstallList.isEmpty()) {
                        H5AppCenterService appCenterService = H5ServiceUtils.getAppCenterService();
                        if (appCenterService != null) {
                            H5AppDBService h5AppDBService = appCenterService.getAppDBService();
                            if (h5AppDBService != null) {
                                for (String appId : preInstallList) {
                                    String version = h5AppDBService.getHighestAppVersion(appId);
                                    AppInfo appInfo = h5AppDBService.getAppInfo(appId, version);
                                    H5BaseApp h5BaseApp = appCenterService.getH5App();
                                    if (!(appInfo == null || h5BaseApp == null)) {
                                        h5BaseApp.setAppInfo(appInfo);
                                        if (h5BaseApp.isAvailable() && !h5BaseApp.isInstalled()) {
                                            H5Log.d(H5AppScoreList.TAG, "pre install appId : " + appId + " version : " + version);
                                            h5BaseApp.installApp();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeCallback(H5AppScoreRpcListener rpcListener, boolean success) {
        if (rpcListener != null) {
            rpcListener.onFinish(success);
        }
    }

    /* access modifiers changed from: private */
    public void initData() {
        String creditStr = H5SharedPreUtil.getStringData(H5SharedPreUtil.H5_APP_SCORE_INFO);
        if (!TextUtils.isEmpty(creditStr)) {
            initAppCreditInfo(H5Utils.parseObject(creditStr));
        }
    }

    /* access modifiers changed from: private */
    public synchronized void initAppCreditInfo(JSONObject responseObj) {
        if (responseObj != null) {
            if (!responseObj.isEmpty()) {
                H5AppCreditInfo creditInfo = new H5AppCreditInfo();
                JSONArray appArray = H5Utils.getJSONArray(responseObj, "data", null);
                if (appArray == null || appArray.size() == 0) {
                    this.appCreditInfo = null;
                } else {
                    JSONObject configObj = H5Utils.getJSONObject(responseObj, "config", null);
                    if (configObj == null || configObj.isEmpty()) {
                        this.appCreditInfo = null;
                    } else {
                        for (int i = 0; i < appArray.size(); i++) {
                            JSONObject object = appArray.getJSONObject(i);
                            creditInfo.addCreditAppInfo(H5Utils.getString(object, (String) "credit"), H5Utils.getString(object, (String) "appId"));
                        }
                        Map creditMap = creditInfo.getCreditMap();
                        if (creditMap == null || creditMap.isEmpty()) {
                            this.appCreditInfo = null;
                        } else {
                            for (Entry mapEntry : creditMap.entrySet()) {
                                List list = (List) mapEntry.getValue();
                                int strategy = getCreditStrategy((String) mapEntry.getKey(), configObj);
                                if ((strategy & 1) == 1) {
                                    creditInfo.addStrategyInfo(1, list);
                                }
                                if ((strategy & 2) == 2) {
                                    creditInfo.addStrategyInfo(2, list);
                                }
                                if ((strategy & 4) == 4) {
                                    creditInfo.addStrategyInfo(4, list);
                                }
                            }
                            H5Log.d(TAG, "creditInfo : " + creditInfo);
                            this.appCreditInfo = creditInfo;
                        }
                    }
                }
            }
        }
        this.appCreditInfo = null;
    }

    private int getCreditStrategy(String credit, JSONObject configObj) {
        String strategyStr = H5Utils.getString(configObj, credit);
        if (TextUtils.isEmpty(strategyStr)) {
            return 0;
        }
        int strategy = 0;
        try {
            strategy = Integer.decode(strategyStr).intValue();
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
        H5Log.d(TAG, " strategy : " + strategy);
        return strategy;
    }

    /* access modifiers changed from: private */
    public boolean enableSendRpc() {
        long lastTime = H5SharedPreUtil.getLongData(H5SharedPreUtil.H5_APP_SCORE_RPC_TIME);
        if (lastTime > 0) {
            long currentTime = System.currentTimeMillis();
            H5Log.d(TAG, "currentTime : " + currentTime + " lastTime : " + lastTime + " reqRate : " + this.reqRate + " limitRate : " + this.limitRate);
            long rate = rpcLimit() ? this.limitRate : this.reqRate;
            if (lastTime <= 0 || currentTime - lastTime <= rate) {
                return false;
            }
            return true;
        }
        H5SharedPreUtil.saveLongData(H5SharedPreUtil.H5_APP_SCORE_RPC_TIME, System.currentTimeMillis());
        return true;
    }

    private boolean rpcLimit() {
        return !TextUtils.isEmpty(H5SharedPreUtil.getStringData(H5SharedPreUtil.H5_SCORE_RPC_LIMIT));
    }

    private void initSwitchConfig() {
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        this.enable = false;
        if (provider != null) {
            String config = provider.getConfigWithProcessCache("h5_nbcredit");
            if (!TextUtils.isEmpty(config)) {
                JSONObject configObj = H5Utils.parseObject(config);
                if (configObj != null && !configObj.isEmpty()) {
                    this.enable = "YES".equalsIgnoreCase(H5Utils.getString(configObj, (String) FunctionSupportConfiger.SWITCH_TAG));
                    int req = H5Utils.parseInt(H5Utils.getString(configObj, (String) "reqrate"));
                    if (req > 0) {
                        this.reqRate = TimeUnit.SECONDS.toMillis((long) req);
                    } else {
                        this.reqRate = TimeUnit.HOURS.toMillis(24);
                    }
                    int limit = H5Utils.parseInt(H5Utils.getString(configObj, (String) "limitrate"));
                    if (limit > 0) {
                        this.limitRate = TimeUnit.SECONDS.toMillis((long) limit);
                    } else {
                        this.limitRate = TimeUnit.MINUTES.toMillis(10);
                    }
                }
                H5Log.d(TAG, " enable : " + this.enable + " reqRate : " + this.reqRate + " limitRate : " + this.limitRate);
            }
        }
    }

    /* access modifiers changed from: private */
    public void saveResponse(String response) {
        try {
            H5SharedPreUtil.getSharedPreferences().edit().remove(H5SharedPreUtil.H5_SCORE_RPC_LIMIT).putLong(H5SharedPreUtil.H5_APP_SCORE_RPC_TIME, System.currentTimeMillis()).putString(H5SharedPreUtil.H5_APP_SCORE_INFO, response).apply();
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
    }

    public void clearAppScoreInfo() {
        try {
            this.appCreditInfo = null;
            H5SharedPreUtil.getSharedPreferences().edit().remove(H5SharedPreUtil.H5_SCORE_RPC_LIMIT).remove(H5SharedPreUtil.H5_APP_SCORE_RPC_TIME).remove(H5SharedPreUtil.H5_APP_SCORE_INFO).apply();
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
    }
}
