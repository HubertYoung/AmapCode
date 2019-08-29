package com.alipay.mobile.nebulacore.biz;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5JSApiPermissionProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.alipay.mobile.scansdk.constant.Constants;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

public class H5BizPlugin extends H5SimplePlugin {
    private static int a = 0;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("startBizService");
        filter.addAction("saveBizServiceResult");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if ("startBizService".equals(event.getAction())) {
            return a(event, bridgeContext);
        }
        if ("saveBizServiceResult".equals(event.getAction())) {
            return b(event, bridgeContext);
        }
        return super.handleEvent(event, bridgeContext);
    }

    private static boolean a(H5Event event, H5BridgeContext bridgeContext) {
        String serviceName = H5Utils.getString(event.getParam(), (String) "name");
        if (TextUtils.isEmpty(serviceName)) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return true;
        }
        JSONObject serviceInfo = a(serviceName);
        String serviceAppId = H5Utils.getString(serviceInfo, (String) "appId");
        String serviceLevel = H5Utils.getString(serviceInfo, (String) H5PermissionManager.level);
        if (TextUtils.isEmpty(serviceAppId)) {
            bridgeContext.sendError(Error.UNKNOWN_ERROR.ordinal(), H5Environment.getResources().getString(R.string.h5_biz_cannot_find_service));
            return true;
        }
        H5Log.d("H5BizPlugin", "startBizService: " + serviceAppId);
        H5Page h5Page = null;
        String currentAppId = "";
        String currentPageUrl = "";
        if (event.getTarget() instanceof H5Page) {
            h5Page = (H5Page) event.getTarget();
            currentAppId = H5Utils.getString(h5Page.getParams(), (String) "appId");
            currentPageUrl = h5Page.getUrl();
        }
        H5JSApiPermissionProvider provider = (H5JSApiPermissionProvider) H5ProviderManagerImpl.getInstance().getProvider(H5JSApiPermissionProvider.class.getName());
        if (provider == null || provider.hasThisPermission(serviceLevel, currentPageUrl)) {
            if (!TextUtils.isEmpty(currentAppId) && h5Page != null) {
                Bundle param = new Bundle();
                int i = a + 1;
                a = i;
                String sourceId = String.valueOf(i);
                param.putString("bizParam", event.getParam().getString("param"));
                param.putString("fromAppId", currentAppId);
                param.putString("fromPageUrl", currentPageUrl);
                param.putString("isAppServiceMode", "true");
                param.putBoolean(MicroApplication.KEY_APP_CLEAR_TOP, false);
                param.putString("startMultApp", "YES");
                param.putString(Constants.SERVICE_SOURCE_ID, sourceId);
                String key = serviceAppId + sourceId;
                if (!H5BizUtil.a(key, bridgeContext)) {
                    bridgeContext.sendError(Error.UNKNOWN_ERROR.ordinal(), H5Environment.getResources().getString(R.string.h5_biz_service_already_started));
                    return true;
                }
                try {
                    LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, serviceAppId, param);
                } catch (Exception e) {
                    H5BizUtil.a(bridgeContext, (String) "6002");
                    H5BizUtil.a(key);
                }
            }
            return true;
        }
        bridgeContext.sendError(Error.UNKNOWN_ERROR.ordinal(), H5Environment.getResources().getString(R.string.h5_biz_no_permission));
        return true;
    }

    private static boolean b(H5Event event, H5BridgeContext bridgeContext) {
        if (event.getTarget() instanceof H5Page) {
            String currentAppId = H5Utils.getString(((H5Page) event.getTarget()).getParams(), (String) "appId");
            ActivityApplication application = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopApplication();
            H5Log.d("H5BizPlugin", "getTopApplication: " + application.getAppId() + Token.SEPARATOR + application.getParams());
            String sourceId = application.getSourceId();
            if (currentAppId != null) {
                H5Log.d("H5BizPlugin", "saveBizServiceResult: " + currentAppId + ", sourceId: " + sourceId);
                String key = currentAppId + sourceId;
                if (H5BizUtil.c(key)) {
                    H5BizUtil.a(key, event.getParam());
                    JSONObject data = new JSONObject();
                    data.put((String) "success", (Object) Boolean.valueOf(true));
                    data.put((String) "savedData", (Object) event.getParam());
                    bridgeContext.sendBridgeResult(data);
                    return true;
                }
            }
        }
        bridgeContext.sendError(Error.UNKNOWN_ERROR.ordinal(), H5Environment.getResources().getString(R.string.h5_biz_cannot_save_result));
        return true;
    }

    private static JSONObject a(String serviceName) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null) {
            return null;
        }
        String config = h5ConfigProvider.getConfig("h5_bizServiceConfig");
        H5Log.d("H5BizPlugin", "getServiceInfo: " + config);
        JSONObject jsonObject = H5Utils.parseObject(config);
        if (jsonObject == null || jsonObject.isEmpty()) {
            return null;
        }
        return H5Utils.getJSONObject(jsonObject, serviceName, null);
    }
}
