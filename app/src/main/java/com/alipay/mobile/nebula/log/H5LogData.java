package com.alipay.mobile.nebula.log;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.core.H5SessionImpl;
import com.alipay.mobile.scansdk.constant.Constants;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class H5LogData {
    private static final String TAG = "H5LogData";
    private Map<String, String> currentParamMap;
    private Map<String, String> param1Map;
    private Map<String, String> param2Map;
    private Map<String, String> param3Map;
    private Map<String, String> param4Map;
    private String seedId;

    public static H5LogData seedId(String seeId) {
        return new H5LogData(seeId);
    }

    private H5LogData(String seedId2) {
        this.seedId = seedId2;
    }

    public String getSeedId() {
        return this.seedId;
    }

    public Map<String, String> getParam1Map() {
        return this.param1Map;
    }

    public Map<String, String> getParam2Map() {
        return this.param2Map;
    }

    public Map<String, String> getParam3Map() {
        return this.param3Map;
    }

    public Map<String, String> getParam4Map() {
        return this.param4Map;
    }

    public H5LogData param1() {
        if (this.param1Map == null) {
            this.param1Map = new HashMap();
        }
        this.currentParamMap = this.param1Map;
        return this;
    }

    public H5LogData param2() {
        if (this.param2Map == null) {
            this.param2Map = new HashMap();
        }
        this.currentParamMap = this.param2Map;
        return this;
    }

    public H5LogData param3() {
        if (this.param3Map == null) {
            this.param3Map = new HashMap();
        }
        this.currentParamMap = this.param3Map;
        return this;
    }

    public H5LogData param4() {
        if (this.param4Map == null) {
            this.param4Map = new HashMap();
        }
        this.currentParamMap = this.param4Map;
        return this;
    }

    public H5LogData add(String key, Object value) {
        if (this.currentParamMap == null) {
            H5Log.e((String) TAG, (String) "please addParam first !");
        } else {
            this.currentParamMap.put(key, value == null ? "" : value.toString());
        }
        return this;
    }

    public H5LogData addNonNullValue(String key, Object value) {
        if (this.currentParamMap == null) {
            H5Log.e((String) TAG, (String) "please addParam first !");
        } else if (value == null || TextUtils.isEmpty(value.toString())) {
            H5Log.d(TAG, "empty value, key : " + key);
        } else {
            this.currentParamMap.put(key, value.toString());
        }
        return this;
    }

    public H5LogData addMapParam(Map<String, ? extends Object> map) {
        if (this.currentParamMap == null) {
            H5Log.e((String) TAG, (String) "please addParam first !");
        } else if (map != null && !map.isEmpty()) {
            for (Entry entry : map.entrySet()) {
                this.currentParamMap.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
            }
        }
        return this;
    }

    public H5LogData addUniteParam(H5PageData h5PageData) {
        String title;
        String customParams;
        if (this.currentParamMap == null) {
            H5Log.e((String) TAG, (String) "please addParam first !");
        } else if (h5PageData == null) {
            H5Log.d(TAG, "h5PageData == null, return");
        } else {
            String bizScenario = H5Logger.bizScenario;
            boolean isOfflineApp = h5PageData.getBooleanExtra(H5PageData.IS_OFFLINE_APP, false);
            try {
                title = h5PageData.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    title = title.trim().replace(Token.SEPARATOR, "").replace(",", "").replace("\n", "");
                }
                customParams = URLEncoder.encode(h5PageData.getCustomParams(), "utf-8");
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
            }
            String viewId = "";
            String refViewID = "";
            if (h5PageData.getBooleanExtra(H5PageData.IS_H5ACTIVITY, false)) {
                if (!TextUtils.isEmpty(h5PageData.getPageUrl())) {
                    viewId = H5Utils.getCleanUrl(h5PageData.getPageUrl());
                    if (H5Logger.enableStockTradeLog()) {
                        H5Logger.putContextParam(LogContext.STORAGE_VIEWID, viewId);
                    }
                } else if (H5Logger.getH5LogProvider() != null) {
                    viewId = H5Logger.getContextParam(LogContext.STORAGE_VIEWID);
                }
                if (!TextUtils.isEmpty(h5PageData.getReferUrl())) {
                    refViewID = H5Utils.getCleanUrl(h5PageData.getReferUrl());
                    if (H5Logger.enableStockTradeLog() && H5Logger.getH5LogProvider() != null) {
                        H5Logger.putContextParam(LogContext.STORAGE_REFVIEWID, refViewID);
                    }
                } else if (H5Logger.getH5LogProvider() != null) {
                    refViewID = H5Logger.getContextParam(LogContext.STORAGE_REFVIEWID);
                }
            } else if (H5Logger.getH5LogProvider() != null) {
                viewId = H5Logger.getContextParam(LogContext.STORAGE_VIEWID);
                refViewID = H5Logger.getContextParam(LogContext.STORAGE_REFVIEWID);
            }
            if (!TextUtils.isEmpty(h5PageData.getXContentVersion())) {
                this.currentParamMap.put("xContentVersion", h5PageData.getXContentVersion());
            }
            if (!TextUtils.isEmpty(h5PageData.getEagleId())) {
                this.currentParamMap.put("eagleId", h5PageData.getEagleId());
            }
            if (!TextUtils.isEmpty(h5PageData.getRequestId())) {
                this.currentParamMap.put(OkApacheClient.REQUESTID, h5PageData.getRequestId());
            }
            if (!TextUtils.isEmpty(h5PageData.getSpmId())) {
                this.currentParamMap.put("spmId", h5PageData.getSpmId());
            }
            if (!TextUtils.isEmpty(h5PageData.getPageToken())) {
                this.currentParamMap.put("pageId", H5Logger.getPageId(h5PageData.getPageToken()));
            }
            this.currentParamMap.put("url", H5Utils.getMaxLogStr(h5PageData.getPageUrl()));
            this.currentParamMap.put("referer", H5Utils.getMaxLogStr(h5PageData.getReferUrl()));
            this.currentParamMap.put("appId", h5PageData.getAppId());
            this.currentParamMap.put("version", h5PageData.getAppVersion());
            this.currentParamMap.put(H5Param.PUBLIC_ID, h5PageData.getPublicId());
            this.currentParamMap.put(Constants.SERVICE_SOURCE_ID, H5AppUtil.secAppId);
            this.currentParamMap.put("psd", H5Logger.getPsd(h5PageData, isOfflineApp));
            this.currentParamMap.put("viewId", viewId);
            this.currentParamMap.put("refviewId", refViewID);
            this.currentParamMap.put(H5Param.LONG_BIZ_SCENARIO, bizScenario);
            this.currentParamMap.put("token", h5PageData.getToken());
            this.currentParamMap.put("h5Token", h5PageData.getH5Token());
            this.currentParamMap.put("h5SessionToken", new StringBuilder(H5SessionImpl.TAG).append(h5PageData.getH5SessionToken()).toString());
            this.currentParamMap.put(H5Param.OPEN_APP_ID, h5PageData.getOpenAppId());
            this.currentParamMap.put("shopId", h5PageData.getShopId());
            this.currentParamMap.put("title", title);
            this.currentParamMap.put(H5Param.CUSTOM_PARAMS, customParams);
            this.currentParamMap.put("webViewType", h5PageData.getWebViewType());
            this.currentParamMap.put("isTinyApp", h5PageData.getIsTinyApp());
            this.currentParamMap.put("ucWebViewVersion", H5Utils.getUcVersion());
            this.currentParamMap.put("multiProcessMode", String.valueOf(h5PageData.getMultiProcessMode()));
            if (!TextUtils.isEmpty(h5PageData.getReleaseType())) {
                this.currentParamMap.put("log_release_type", h5PageData.getReleaseType());
            }
            String logGroup = getLogGroup();
            if (!TextUtils.isEmpty(logGroup)) {
                this.currentParamMap.put("logGroup", logGroup);
            }
            String isEntrance = h5PageData.getStringExtra(H5PageData.WEBVIEW_ERROR_DESC, "");
            if (!TextUtils.isEmpty(isEntrance)) {
                this.currentParamMap.put(H5PageData.WEBVIEW_ERROR_DESC, isEntrance);
            }
        }
        return this;
    }

    private String getLogGroup() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            return h5ConfigProvider.getConfigWithProcessCache("h5_logGroup");
        }
        return null;
    }
}
