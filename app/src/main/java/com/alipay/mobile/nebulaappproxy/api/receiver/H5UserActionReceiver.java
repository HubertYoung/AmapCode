package com.alipay.mobile.nebulaappproxy.api.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulaappproxy.api.H5AppInfoUploadUtil;
import com.alipay.mobile.nebulaappproxy.api.H5ClearPackageUtil;
import com.alipay.mobile.nebulaappproxy.api.H5ReceiverUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class H5UserActionReceiver extends BroadcastReceiver {
    public static final String TAG = "H5UserActionReceiver";

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            H5Log.d(TAG, "action:" + action);
            if ("com.alipay.mobile.framework.USERLEAVEHINT".equalsIgnoreCase(action)) {
                H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                if (h5ConfigProvider != null) {
                    h5ConfigProvider.clearProcessCache();
                }
                H5Utils.getScheduledExecutor().schedule(new Runnable() {
                    public void run() {
                        try {
                            if (ActivityHelper.isBackgroundRunning()) {
                                H5ClearPackageUtil.clearUnusedAppPackage();
                                H5AppInfoUploadUtil.uploadAllAppInfo();
                                if (H5Utils.isInWifi()) {
                                    JSONObject jsonObject = H5Utils.parseObject(((ConfigService) H5Utils.findServiceByInterface(ConfigService.class.getName())).getConfig("h5_user_leave_config"));
                                    int checkDownloadDay = 1;
                                    if (jsonObject != null && !jsonObject.isEmpty()) {
                                        checkDownloadDay = H5Utils.getInt(jsonObject, (String) "checkDownloadDay");
                                    }
                                    H5UserActionReceiver.b(checkDownloadDay);
                                    return;
                                }
                                H5Log.d(H5UserActionReceiver.TAG, "not in wifi  not checkDownloadTime");
                                return;
                            }
                            H5Log.d(H5UserActionReceiver.TAG, "is not isBackgroundRunning");
                        } catch (Throwable throwable) {
                            H5Log.e((String) H5UserActionReceiver.TAG, throwable);
                        }
                    }
                }, 3, TimeUnit.SECONDS);
            }
        }
    }

    private static String a(long time) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(time));
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
            return "";
        }
    }

    private static boolean a(String key) {
        ConfigService configService = (ConfigService) H5Utils.findServiceByInterface(ConfigService.class.getName());
        if (configService == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(configService.getConfig(key))) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static void b(int checkDay) {
        if (a((String) "H5_downloadApp_user_Leave")) {
            SharedPreferences preferences = H5Utils.getContext().getSharedPreferences("nebula_download", 0);
            long oldTime = preferences.getLong("last_nebula_download_check_time", 0);
            long checkInterval = TimeUnit.DAYS.toMillis((long) checkDay);
            if (Math.abs(System.currentTimeMillis() - oldTime) < checkInterval) {
                H5Log.d(TAG, "checkDownloadTime ignore, CheckInterval:" + checkInterval + ", lastCheckTime:" + oldTime + Token.SEPARATOR + a(oldTime));
                return;
            }
            H5Log.d(TAG, "checkDownloadTime match begin download ");
            preferences.edit().putLong("last_nebula_download_check_time", System.currentTimeMillis()).commit();
            H5ReceiverUtil.downLoadNebula(H5DownloadRequest.USER_LEAVE_HINT_SCENE);
        }
    }
}
