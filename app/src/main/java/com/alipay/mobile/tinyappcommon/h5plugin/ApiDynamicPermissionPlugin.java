package com.alipay.mobile.tinyappcommon.h5plugin;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.liteprocess.perf.H5PerformancePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.wallet.H5LoggerPlugin;
import com.alipay.mobile.tinyappcommon.api.TinyAppMixActionService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.alipay.mobile.tinyappcustom.h5plugin.H5RequestTemplateDataPlugin;
import com.autonavi.miniapp.biz.plugin.H5FavoritePlugin;
import com.mpaas.nebula.plugin.H5ServicePlugin;
import java.util.List;

public class ApiDynamicPermissionPlugin extends H5SimplePlugin {
    private static final String[] API_INIT_LIST = {CommonEvents.SET_OPTION_MENU, "showOptionMenu", "inputFocus4Android", H5ServicePlugin.GET_CONFIG, "getLifestyleInfo", "addFollow", H5PageData.FROM_TYPE_START_APP, H5RequestTemplateDataPlugin.REQUEST_TEMPLATE_DATA, "getAppType", H5PerformancePlugin.PERFORMANCE_JS_API, "getShareImageUrl", "hideCustomKeyBoard", "resetNativeKeyBoardInput", "updateNativeKeyBoardInput", "getStartupParams", "getConfig4Appx", "tinyDebugConsole", H5FavoritePlugin.ADD_TO_FAVORITE, "cancelKeepFavorite", TinyAppSharePlugin.SHARE_TINY_APP_MSG, TinyAppCommonInfoPlugin.SET_APPX_VERSION, TinyAppBackHomePlugin.SHOW_BACK_HOME, "getComponentAuth", "getBusinessAuth", "getAuthorize", "appxrpc", "launchApp", "goBackThirdPartApp", H5TinyAppLogUtil.TINY_APP_STANDARD_LOG, "shareToAlipayContact", "startMiniService", H5TinyAppLogUtil.TINY_APP_STANDARD_LOG, H5LoggerPlugin.REPORT_TINY_DATA, H5PkgResPlugin.ACTION_ADD_PKG_RES, "getExtConfig"};
    public static final String INTERNAL_API = "internalAPI";
    public static final String TAG = ApiDynamicPermissionPlugin.class.getSimpleName();

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(INTERNAL_API);
    }

    public void onRelease() {
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (INTERNAL_API.equals(event.getAction())) {
            internalApi(event, context);
        }
        return true;
    }

    private void internalApi(H5Event event, H5BridgeContext bridgeContext) {
        if (!(event.getTarget() instanceof H5Page)) {
            H5Log.d(TAG, "internalApi...not H5Page, do not allowed");
            return;
        }
        Bundle bundle = ((H5Page) event.getTarget()).getParams();
        boolean isTinyApp = H5Utils.getBoolean(bundle, (String) "isTinyApp", false);
        String value = H5Utils.getString(bundle, (String) H5Param.ENABLE_DSL);
        if (isTinyApp || "yes".equalsIgnoreCase(value)) {
            JSONObject params = event.getParam();
            if (params == null) {
                H5Log.d(TAG, "internalApi...param is null");
                return;
            }
            String realMethod = H5Utils.getString(params, (String) "method", (String) "");
            if (TextUtils.isEmpty(realMethod)) {
                H5Log.d(TAG, "internalApi...realMethod is null");
                return;
            }
            boolean found = false;
            String[] strArr = API_INIT_LIST;
            int length = strArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (TextUtils.equals(strArr[i], realMethod)) {
                    found = true;
                    break;
                } else {
                    i++;
                }
            }
            if (!found) {
                TinyAppMixActionService mixActionService = TinyAppService.get().getMixActionService();
                if (mixActionService == null) {
                    H5Log.d(TAG, "internalApi...mixActionService is null");
                    bridgeContext.sendError(event, Error.FORBIDDEN);
                    return;
                }
                List supportedInternalApiList = mixActionService.getSupportedInternalApiList();
                if (supportedInternalApiList == null || !supportedInternalApiList.contains(realMethod)) {
                    H5Log.d(TAG, "internalApi...realMethod is not allowed");
                    bridgeContext.sendError(event, Error.FORBIDDEN);
                    return;
                }
            }
            JSONObject methodParams = H5Utils.getJSONObject(params, "param", null);
            H5Log.d(TAG, "internalApi...dispatch plugin: " + realMethod + ", param: " + methodParams);
            event.setAction(realMethod);
            event.setParam(methodParams);
            H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
            if (h5Service != null) {
                h5Service.sendEvent(event, bridgeContext);
                return;
            }
            return;
        }
        H5Log.d(TAG, "internalApi...do not allowed");
    }
}
