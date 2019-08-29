package com.alipay.mobile.nebulauc.plugin;

import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.WebViewType;
import com.alipay.mobile.nebulauc.util.CommonUtil;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.sdk.packet.d;
import com.uc.webview.export.extension.UCSettings;
import java.util.HashMap;

public class H5UCPlugin extends H5SimplePlugin {
    private static final String APPEND_UC_CORE_PERFORMANCE_DATA = "appendUCCorePerformanceData";
    private static final int ARGUMENT_ERROR = 102;
    private static final String CLEAR_SW = "clearServiceWorker";
    private static final String FLUSH_UC_LOG = "flushUcLog";
    private static final String PUSH_SW_MESSAGE = "pushSWMessage";
    private static final String SET_SERVICEWORKER_ID = "setServiceWorkerID";
    private static final String TAG = "H5UCPlugin";
    private static final int USER_ID_ERROR = 103;

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(SET_SERVICEWORKER_ID);
        filter.addAction(PUSH_SW_MESSAGE);
        filter.addAction(CLEAR_SW);
        filter.addAction(FLUSH_UC_LOG);
        filter.addAction(APPEND_UC_CORE_PERFORMANCE_DATA);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        H5Page h5Page = null;
        if (event.getTarget() instanceof H5Page) {
            h5Page = (H5Page) event.getTarget();
        }
        String action = event.getAction();
        if (TextUtils.equals(SET_SERVICEWORKER_ID, action)) {
            JSONObject param = event.getParam();
            if (param != null && !param.isEmpty()) {
                String id = H5Utils.getString(param, (String) "id");
                if (h5Page != null) {
                    H5Session h5Session = h5Page.getSession();
                    if (h5Session != null) {
                        h5Session.setServiceWorkerID(id);
                        context.sendSuccess();
                    }
                    H5Log.d(TAG, "success setServiceWorkerID " + id);
                }
            }
            return true;
        } else if (TextUtils.equals(PUSH_SW_MESSAGE, action)) {
            if (h5Page != null) {
                H5Session h5Session2 = h5Page.getSession();
                if (h5Session2 != null) {
                    String serviceWorkerId = h5Session2.getServiceWorkerID();
                    if (!TextUtils.isEmpty(serviceWorkerId)) {
                        H5Log.d(TAG, "success getServiceWorkerID " + serviceWorkerId);
                        HashMap hashMap = new HashMap();
                        hashMap.put("appId", serviceWorkerId);
                        hashMap.put("message", "testdata");
                        hashMap.put("messageId", System.currentTimeMillis() + "");
                        H5Service h5Service = H5ServiceUtils.getH5Service();
                        if (h5Service != null) {
                            h5Service.sendServiceWorkerPushMessage(hashMap);
                        }
                        context.sendSuccess();
                    } else {
                        context.sendBridgeResult("2", "ServiceWorkerID is null,please invoke setServiceWorkerID");
                    }
                }
            }
            return true;
        } else if (TextUtils.equals(CLEAR_SW, action)) {
            String host = H5Utils.getString(event.getParam(), (String) "host");
            H5Service h5Service2 = H5ServiceUtils.getH5Service();
            if (h5Service2 != null) {
                h5Service2.clearServiceWorker(host);
            }
            context.sendSuccess();
            return true;
        } else if (TextUtils.equals(FLUSH_UC_LOG, action)) {
            if (h5Page != null && h5Page.getWebView().getType() == WebViewType.THIRD_PARTY && enableFlushUcLog()) {
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public void run() {
                        try {
                            UCSettings.setGlobalBoolValue("FLUSH_HTTP_LOG", true);
                        } catch (Throwable throwable) {
                            H5Log.e((String) H5UCPlugin.TAG, throwable);
                        }
                    }
                });
            }
            context.sendSuccess();
            return true;
        } else if (!TextUtils.equals(APPEND_UC_CORE_PERFORMANCE_DATA, action)) {
            return super.handleEvent(event, context);
        } else {
            appendUcCorePerformanceData(event, context);
            return true;
        }
    }

    private void appendUcCorePerformanceData(final H5Event event, final H5BridgeContext bridgeContext) {
        H5Utils.executeOrdered(TAG, new Runnable() {
            public void run() {
                String content = H5Utils.getString(event.getParam(), (String) "content", (String) "");
                if (TextUtils.isEmpty(content)) {
                    CommonUtil.sendError(bridgeContext, 102, "content is empty");
                    return;
                }
                if (((H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName())) != null) {
                    JSONObject config = H5ConfigUtil.getConfigJSONObject("h5_ucCorePerfConfig");
                    if (!"yes".equalsIgnoreCase(H5Utils.getString(config, (String) "enable"))) {
                        CommonUtil.sendError(bridgeContext, 103, "config is not available");
                        return;
                    }
                    JSONArray whiteList = H5Utils.getJSONArray(config, "domainList", null);
                    if (whiteList != null && whiteList.size() > 0) {
                        H5Page h5Page = event.getH5page();
                        if (h5Page != null) {
                            Uri uri = H5UrlHelper.parseUrl(h5Page.getUrl());
                            if (uri == null) {
                                return;
                            }
                            if (!CommonUtil.isUrlMatch(uri.getHost(), whiteList) || h5Page.getPageData() == null) {
                                CommonUtil.sendError(bridgeContext, 102, "url is not match");
                                return;
                            }
                            if (!h5Page.getPageData().appendUCCorePerfExtra(content, H5Utils.getInt(config, (String) "limitKB", 0))) {
                                CommonUtil.sendError(bridgeContext, 102, "content is too long");
                            }
                        } else {
                            return;
                        }
                    }
                }
                if (bridgeContext != null) {
                    bridgeContext.sendSuccess();
                }
            }
        });
    }

    private boolean enableFlushUcLog() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null) {
            return true;
        }
        String value = h5ConfigProvider.getConfig("h5_canUseFlushUcLog");
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        String phoneInfo = Build.MANUFACTURER + MetaRecord.LOG_SEPARATOR + Build.MODEL + MetaRecord.LOG_SEPARATOR + VERSION.SDK_INT;
        JSONObject jsonObject = H5Utils.parseObject(value);
        if (!H5Utils.getBoolean(jsonObject, (String) "enable", false)) {
            return false;
        }
        JSONArray deviceList = H5Utils.getJSONArray(jsonObject, d.n, null);
        if (deviceList == null || deviceList.isEmpty()) {
            return true;
        }
        JSONArray jsonArray = H5Utils.parseArray(value);
        if (jsonArray == null || jsonArray.isEmpty()) {
            return true;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            if (phoneInfo.equalsIgnoreCase(jsonArray.getString(i))) {
                return false;
            }
        }
        return true;
    }
}
