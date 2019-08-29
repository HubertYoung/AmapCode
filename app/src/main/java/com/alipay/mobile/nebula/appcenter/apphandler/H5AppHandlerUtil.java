package com.alipay.mobile.nebula.appcenter.apphandler;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5FallbackStreamProvider;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.tiny.H5TinyFallBackData;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.HashMap;
import java.util.Map;

public class H5AppHandlerUtil {
    private static final String TAG = "H5AppHandlerUtil";

    public interface FallbackResult {
        void onResult(boolean z, @Nullable String str);
    }

    public static void tryFallback(AppInfo appInfo, FallbackResult fallbackResult) {
        final H5FallbackStreamProvider h5FallbackStreamProvider = (H5FallbackStreamProvider) H5Utils.getProvider(H5FallbackStreamProvider.class.getName());
        if (!(h5FallbackStreamProvider == null || appInfo == null)) {
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider != null && BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_tiny_app_fallback"))) {
                fallbackResult.onResult(false, "configIsClose");
                return;
            } else if (H5Utils.isTinyApp(appInfo) || fallbackResult == null) {
                final String api_permission = appInfo.fallback_base_url + "api_permission";
                final String appConfigUrl = appInfo.fallback_base_url + H5StartParamManager.appConfig;
                H5Log.d(TAG, "api_permission " + api_permission + " appConfigUrl:" + appConfigUrl);
                if (!TextUtils.isEmpty(api_permission) && !TextUtils.isEmpty(appConfigUrl)) {
                    final AppInfo appInfo2 = appInfo;
                    final FallbackResult fallbackResult2 = fallbackResult;
                    H5Utils.getExecutor("RPC").execute(new Runnable() {
                        public final void run() {
                            try {
                                byte[] apiByte = H5Utils.readBytes(h5FallbackStreamProvider.getFallbackInputStream(api_permission));
                                byte[] configByte = H5Utils.readBytes(h5FallbackStreamProvider.getFallbackInputStream(appConfigUrl));
                                if (!(apiByte == null || configByte == null)) {
                                    Map apiMap = new HashMap();
                                    apiMap.put(appInfo2.version, apiByte);
                                    Map configMap = new HashMap();
                                    configMap.put(appInfo2.version, configByte);
                                    H5TinyFallBackData.apiPermissionByte.put(appInfo2.app_id, apiMap);
                                    H5TinyFallBackData.appConfigByte.put(appInfo2.app_id, configMap);
                                    if (fallbackResult2 != null) {
                                        fallbackResult2.onResult(true, "fallBackSuccess");
                                        return;
                                    }
                                }
                            } catch (Throwable e) {
                                H5Log.e((String) H5AppHandlerUtil.TAG, e);
                            }
                            if (fallbackResult2 != null) {
                                fallbackResult2.onResult(false, H5AppPrepareData.PREPARE_FALLBACK_FAIL);
                            }
                        }
                    });
                    return;
                }
            } else {
                fallbackResult.onResult(true, "");
                return;
            }
        }
        if (fallbackResult != null) {
            fallbackResult.onResult(false, "fallbackAppInfoIsEmpty");
        }
    }
}
