package com.alipay.mobile.tinyappcommon.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.liteprocess.LiteProcessApi;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.a.a;
import com.alipay.mobile.tinyappcommon.a.b;

public class WalletTinyappUtils extends TinyappUtils {
    public static final String CONST_SCOPE_ADDRESS = "address";
    public static final String CONST_SCOPE_ALI_RUN = "alirun";
    public static final String CONST_SCOPE_CAMERA = "camera";
    public static final String CONST_SCOPE_INVOICE_TITLE = "invoiceTitle";
    public static final String CONST_SCOPE_RECORD = "audioRecord";
    public static final String CONST_SCOPE_USERINFO = "userInfo";
    public static final String CONST_SCOPE_USERLOCATION = "location";
    public static final String CONST_SCOPE_WRITE_PHOTOS_ALBUM = "album";
    private static final String TAG = WalletTinyappUtils.class.getSimpleName();

    public static String getConfig(String configName) {
        String str = null;
        ConfigService configService = (ConfigService) H5Utils.findServiceByInterface(ConfigService.class.getName());
        if (configService == null) {
            H5Log.e(TAG, (String) "failed get config service");
            return str;
        }
        try {
            return configService.getConfig(configName);
        } catch (Exception e) {
            H5Log.e(TAG, "getConfig exception", e);
            return str;
        }
    }

    public static String getUserId() {
        String userId = LoggerFactory.getLogContext().getUserId();
        if (!TextUtils.isEmpty(userId)) {
            return userId;
        }
        H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider != null) {
            return h5LoginProvider.getUserId();
        }
        return userId;
    }

    public static a getMultiProcessService() {
        try {
            if (LiteProcessApi.isLiteProcess()) {
                H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                if (h5EventHandlerService != null) {
                    return (a) h5EventHandlerService.getIpcProxy(a.class);
                }
                H5Log.d(TAG, "getMultiProcessService..h5EventHandlerService null");
                return b.a();
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "getMultiProcessService...e=" + e);
        }
        return b.a();
    }

    public static a getMultiProcessServiceFromMain() {
        try {
            if (LiteProcessApi.isMainProcess()) {
                H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                if (h5EventHandlerService != null) {
                    return (a) h5EventHandlerService.getIpcProxy(a.class);
                }
                H5Log.d(TAG, "getMultiProcessService..h5EventHandlerService null");
                return b.a();
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "getMultiProcessService...e=" + e);
        }
        return b.a();
    }

    public static final String getAppNameByAppId(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return "";
        }
        try {
            H5AppProvider provider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (provider != null) {
                return provider.getAppInfo(appId).name;
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "getNameByAppId...e=" + e);
        }
        return "";
    }

    public static void submitTask(Runnable runnable) {
        ((TaskScheduleService) H5Utils.findServiceByInterface(TaskScheduleService.class.getName())).acquireExecutor(ScheduleType.NORMAL).submit(runnable);
    }

    public static void runOnMainThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
