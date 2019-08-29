package com.mpaas.nebula.plugin.apservice;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5JSApiPermissionProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.mpaas.nebula.NebulaBiz;
import com.mpaas.nebula.adapter.R;
import com.mpaas.nebula.adapter.api.H5APServiceCallbackProvider;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

public class H5APServicePlugin extends H5SimplePlugin {
    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("startAPService");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if (!"startAPService".equals(event.getAction())) {
            return super.handleEvent(event, bridgeContext);
        }
        a(event, bridgeContext);
        return true;
    }

    private static void a(H5Event event, H5BridgeContext bridgeContext) {
        String serviceName = H5Utils.getString(event.getParam(), (String) "name");
        if (TextUtils.isEmpty(serviceName)) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        JSONObject serviceInfo = a(serviceName);
        String serviceAppId = H5Utils.getString(serviceInfo, (String) "appId");
        String serviceLevel = H5Utils.getString(serviceInfo, (String) H5PermissionManager.level);
        if (TextUtils.isEmpty(serviceAppId)) {
            bridgeContext.sendError(Error.UNKNOWN_ERROR.ordinal(), NebulaBiz.getResources().getString(R.string.h5_ap_cannot_find_service));
            return;
        }
        H5Log.d("H5APServicePlugin", "startAPService: " + serviceAppId);
        String currentAppId = "";
        String currentPageUrl = "";
        if (event.getTarget() instanceof H5Page) {
            H5Page h5Page = (H5Page) event.getTarget();
            currentAppId = H5Utils.getString(h5Page.getParams(), (String) "appId");
            currentPageUrl = h5Page.getUrl();
        }
        H5JSApiPermissionProvider provider = (H5JSApiPermissionProvider) H5Utils.getProvider(H5JSApiPermissionProvider.class.getName());
        if (provider != null && !provider.hasThisPermission(serviceLevel, currentPageUrl)) {
            bridgeContext.sendError(Error.UNKNOWN_ERROR.ordinal(), NebulaBiz.getResources().getString(R.string.h5_ap_no_permission));
        } else if (!H5APServiceUtil.isAlipayInstalled(event.getActivity())) {
            bridgeContext.sendError(Error.UNKNOWN_ERROR.ordinal(), NebulaBiz.getResources().getString(R.string.h5_ap_alipay_not_installed));
        } else {
            String paramString = H5Utils.getString(event.getParam(), (String) "param");
            String callbackId = UUID.randomUUID().toString();
            String scheme = a(serviceAppId, paramString, currentAppId, currentPageUrl, callbackId);
            H5APServiceCallbackProvider h5APServiceCallbackProvider = (H5APServiceCallbackProvider) H5Utils.getProvider(H5APServiceCallbackProvider.class.getName());
            if (h5APServiceCallbackProvider != null) {
                h5APServiceCallbackProvider.registerCallback(callbackId, bridgeContext);
            }
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(scheme));
            intent.addFlags(268435456);
            if (intent.resolveActivity(NebulaBiz.getContext().getPackageManager()) == null) {
                bridgeContext.sendError(Error.UNKNOWN_ERROR.ordinal(), NebulaBiz.getResources().getString(R.string.h5_ap_not_resolved));
                return;
            }
            NebulaBiz.startActivity(null, intent);
        }
    }

    private static JSONObject a(String serviceName) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null) {
            return null;
        }
        String config = h5ConfigProvider.getConfig("h5_bizServiceConfig");
        H5Log.d("H5APServicePlugin", "getServiceInfo: " + config);
        JSONObject jsonObject = H5Utils.parseObject(config);
        if (jsonObject == null || jsonObject.isEmpty()) {
            return null;
        }
        return H5Utils.getJSONObject(jsonObject, serviceName, null);
    }

    private static String a(String appId, String paramString, String fromAppId, String fromPageUrl, String callbackId) {
        StringBuilder sb = new StringBuilder("alipays://platformapi/startapp?appId=");
        sb.append(appId);
        sb.append("&appClearTop=false&startMultApp=YES");
        if (!TextUtils.isEmpty(fromAppId)) {
            sb.append("&fromAppId=").append(fromAppId);
        }
        try {
            if (!TextUtils.isEmpty(fromPageUrl)) {
                sb.append("&fromPageUrl=").append(URLEncoder.encode(fromPageUrl, "utf-8"));
            }
            sb.append("&redirecturl=").append(URLEncoder.encode(a(callbackId, fromAppId, fromPageUrl), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            LoggerFactory.getTraceLogger().error((String) "H5APServicePlugin", (Throwable) e);
        }
        JSONObject param = JSONObject.parseObject(paramString);
        if (param != null) {
            for (String key : param.keySet()) {
                sb.append("&").append(key).append("=").append(param.get(key));
            }
        }
        return sb.toString();
    }

    private static String a(String callbackId, String appId, String url) {
        StringBuilder sb = new StringBuilder("amapuri://applets/platformapi/apserviceresult");
        sb.append("?amapCallbackId=").append(callbackId);
        if (!TextUtils.isEmpty(appId)) {
            sb.append("&amapAppId=").append(appId);
        }
        try {
            String page = b(url);
            if (!TextUtils.isEmpty(page)) {
                sb.append("&amapAppPage=").append(URLEncoder.encode(page, "utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            LoggerFactory.getTraceLogger().error((String) "H5APServicePlugin", (Throwable) e);
        }
        return sb.toString();
    }

    @NonNull
    private static String b(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        String fragment = Uri.parse(url).getFragment();
        if (TextUtils.isEmpty(fragment)) {
            return "";
        }
        int index = fragment.indexOf("?");
        return index >= 0 ? fragment.substring(0, index) : fragment;
    }
}
