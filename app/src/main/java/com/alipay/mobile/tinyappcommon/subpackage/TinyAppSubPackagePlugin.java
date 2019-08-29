package com.alipay.mobile.tinyappcommon.subpackage;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.api.H5ParseResult;
import com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage;
import com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackagePool;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.TinyappUtils.NetworkType;
import com.alipay.mobile.tinyappcommon.utils.Callback;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TinyAppSubPackagePlugin extends H5SimplePlugin {
    public static final String DOWNLOAD_STATUS = "downloadStatus";
    public static final String DOWNLOAD_URL = "downloadUrl";
    public static final String INSTALL_STATUS = "installStatus";
    private static final String LOAD_SUB_PACKAGE = "loadSubPackage";
    private static final String NETWORK_TYPE_WIFI = "wifi";
    public static final String ROOT_ATTRIB = "rootAttrib";
    public static final String SUB_PACKAGES = "subPackages";
    private static final String TAG = "TinyAppSubPackagePlugin";
    private static Map<String, Set<String>> sSubPackageStatusMap = new ConcurrentHashMap();

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(LOAD_SUB_PACKAGE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (LOAD_SUB_PACKAGE.equals(event.getAction())) {
            loadSubPackage(event, context);
        }
        return true;
    }

    private void loadSubPackage(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            H5Log.w(TAG, "loadSubPackage...h5Page is null");
            return;
        }
        JSONObject subPackages = JSONObject.parseObject(H5Utils.getString(h5Page.getParams(), (String) SUB_PACKAGES));
        if (subPackages == null || subPackages.isEmpty()) {
            H5Log.w(TAG, "loadSubPackage...non subPackage mode");
            context.sendError(11, (String) "非分包模式");
            return;
        }
        String preload = H5Utils.getString(event.getParam(), (String) "preload");
        if (!"wifi".equals(preload) || NetworkType.NETWORK_WIFI == TinyappUtils.getNetworkType(event.getActivity())) {
            JSONArray array = H5Utils.getJSONArray(event.getParam(), "packages", null);
            if (array == null || array.isEmpty()) {
                context.sendError(event, Error.INVALID_PARAM);
                H5Log.w(TAG, "loadSubPackage...packages is null");
                return;
            }
            String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
            if (TextUtils.isEmpty(appId)) {
                context.sendError(event, Error.UNKNOWN_ERROR);
                H5Log.w(TAG, "loadSubPackage...appId is null");
                return;
            }
            JSONArray nonReadyArray = new JSONArray();
            boolean allSubPackageReady = true;
            for (int i = 0; i < array.size(); i++) {
                String rootAttrib = array.getString(i);
                if (!TextUtils.isEmpty(rootAttrib)) {
                    String downloadUrl = subPackages.getString(rootAttrib);
                    if (!TextUtils.isEmpty(downloadUrl)) {
                        Set packageStatusSet = sSubPackageStatusMap.get(appId);
                        if (packageStatusSet == null || !packageStatusSet.contains(downloadUrl)) {
                            allSubPackageReady = false;
                            nonReadyArray.add(rootAttrib);
                        }
                    }
                }
            }
            if (allSubPackageReady) {
                context.sendSuccess();
                H5Log.d(TAG, "loadSubPackage... all packages has loaded.");
                return;
            }
            prepareSubPackage(appId, nonReadyArray, subPackages, context, array.size() == 1);
            return;
        }
        H5Log.w(TAG, "loadSubPackage...networkType is dismatch:" + preload);
        context.sendError(12, (String) "网络类型不匹配，不下载");
    }

    private void prepareSubPackage(final String appId, JSONArray rootAttribArray, JSONObject subPackages, final H5BridgeContext context, final boolean needSendResult) {
        if (rootAttribArray == null || rootAttribArray.isEmpty()) {
            H5Log.w(TAG, "loadSubPackage...no valid root need load: " + appId);
            context.sendError(13, (String) "无有效的分包");
            return;
        }
        for (int i = 0; i < rootAttribArray.size(); i++) {
            String rootAttrib = rootAttribArray.getString(i);
            H5Utils.runNotOnMain(H5ThreadType.URGENT, new e(appId, rootAttrib, subPackages.getString(rootAttrib), new Callback<JSONObject>() {
                /* access modifiers changed from: private */
                /* renamed from: a */
                public void callback(JSONObject jsonObject) {
                    if (jsonObject != null) {
                        String rootAttrib = jsonObject.getString(TinyAppSubPackagePlugin.ROOT_ATTRIB);
                        Boolean downloadStatus = jsonObject.getBoolean(TinyAppSubPackagePlugin.DOWNLOAD_STATUS);
                        if (downloadStatus == null || downloadStatus.booleanValue()) {
                            Boolean installStatus = jsonObject.getBoolean(TinyAppSubPackagePlugin.INSTALL_STATUS);
                            if (installStatus == null || installStatus.booleanValue()) {
                                TinyAppSubPackagePlugin.this.packageParse(appId, rootAttrib, jsonObject.getString(TinyAppSubPackagePlugin.DOWNLOAD_URL), needSendResult, context);
                                return;
                            }
                            H5Log.w(TinyAppSubPackagePlugin.TAG, "callback...install failed:" + rootAttrib);
                            if (needSendResult && context != null) {
                                context.sendError(15, "安装失败_" + rootAttrib);
                                return;
                            }
                            return;
                        }
                        H5Log.w(TinyAppSubPackagePlugin.TAG, "callback...download failed:" + rootAttrib);
                        if (needSendResult && context != null) {
                            context.sendError(14, "下载失败_" + rootAttrib);
                        }
                    }
                }
            }));
        }
        if (!needSendResult) {
            context.sendSuccess();
        }
    }

    /* access modifiers changed from: private */
    public void packageParse(String appId, String rootAttrib, String downloadUrl, boolean needSendResult, H5BridgeContext bridgeContext) {
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(rootAttrib) || TextUtils.isEmpty(downloadUrl)) {
            H5Log.w(TAG, "callback...appId downloadUrl is null");
            return;
        }
        H5Page topH5Page = TinyappUtils.getTopH5Page();
        if (topH5Page == null) {
            H5Log.w(TAG, "callback...topH5Page is null");
            return;
        }
        Bundle startupParams = topH5Page.getParams();
        String sessionId = H5Utils.getString(startupParams, (String) "sessionId");
        H5ContentPackage contentPackage = H5ContentPackagePool.getPackage(sessionId);
        if (contentPackage == null) {
            H5ContentPackagePool.preparePackage(startupParams, true);
            contentPackage = H5ContentPackagePool.getPackage(sessionId);
        }
        H5ParseResult result = d.a(startupParams, contentPackage, downloadUrl);
        H5Log.d(TAG, "callback...package parse: " + rootAttrib);
        if (result != null) {
            if (result.code == 0) {
                Set packageStatusSet = sSubPackageStatusMap.get(appId);
                if (packageStatusSet == null) {
                    packageStatusSet = new HashSet();
                    sSubPackageStatusMap.put(appId, packageStatusSet);
                }
                packageStatusSet.add(downloadUrl);
                if (needSendResult) {
                    bridgeContext.sendSuccess();
                }
            } else if (needSendResult) {
                bridgeContext.sendError(16, "解析失败_" + rootAttrib + "_" + result.code);
            }
        } else if (needSendResult) {
            bridgeContext.sendError(16, "解析失败_" + rootAttrib);
        }
    }
}
