package com.alipay.mobile.nebulauc.impl.setup;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider.OnConfigChangeListener;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.extension.UCSettings;
import java.util.concurrent.Callable;

public class UcVideoSetup {
    private static final String TAG = "H5UcService::UcVideoSetup";
    private static OnConfigChangeListener observer1 = new OnConfigChangeListener() {
        public void onChange(String value) {
            UcBizSetupHelper.configure(value, UCSettings.KEY_USE_RAW_VIDEO_CONTROLS);
        }
    };
    private static OnConfigChangeListener observer2 = new OnConfigChangeListener() {
        public void onChange(String value) {
            UcBizSetupHelper.configure(value, UCSettings.KEY_VIDEO_SUPPORT_RAW_CONTROLS_ATTR);
        }
    };
    private static OnConfigChangeListener observer3 = new OnConfigChangeListener() {
        public void onChange(String value) {
            UcBizSetupHelper.configure(value, UCSettings.KEY_DISABLE_FLOAT_VIDEO_VIEW);
        }
    };
    private static OnConfigChangeListener observer4 = new OnConfigChangeListener() {
        public void onChange(String value) {
            UcVideoSetup.updateWebAudioDecodePolicy(value);
        }
    };

    public static long initVideoControl(Context context, boolean useApollo, String downloadUrl, boolean downloadApolloIn4G, boolean downloadApolloInLiteProcess) {
        long ts = System.currentTimeMillis();
        UcSetupTracing.beginTrace("initVideoControl");
        if (useApollo) {
            long start = System.currentTimeMillis();
            UCSettings.setEnableUCVideoViewFullscreen(true);
            if (TextUtils.isEmpty(downloadUrl)) {
                downloadUrl = "https://gw.alipayobjects.com/os/rmsportal/EtIoWfqVaQlxNOWBsAaI.zip";
            }
            H5Log.d(TAG, "useApollo downloadUrl: " + downloadUrl);
            if (H5Utils.isMainProcess() || downloadApolloInLiteProcess) {
                try {
                    final boolean z = downloadApolloIn4G;
                    final Context context2 = context;
                    UCCore.updateUCPlayer(context, downloadUrl, new Callable<Boolean>() {
                        public Boolean call() {
                            if (z) {
                                H5Log.d(UcVideoSetup.TAG, "downloadApolloIn4G config yes");
                                return Boolean.valueOf(true);
                            }
                            boolean isWifi = TextUtils.equals("WIFI", H5Utils.getNetworkType(context2));
                            H5Log.d(UcVideoSetup.TAG, "updateUCPlayer isWifi " + isWifi);
                            return Boolean.valueOf(isWifi);
                        }
                    });
                    H5Log.d(TAG, "updateUCPlayer cost:" + (System.currentTimeMillis() - start));
                } catch (Exception e) {
                    H5Log.e(TAG, "download apollo exception ", e);
                }
            } else {
                H5Log.d(TAG, "lite process and downloadApolloInLiteProcess false");
            }
        }
        H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (configProvider != null) {
            UcBizSetupHelper.configure(configProvider.getConfigWithNotifyChange("h5_ucVideoHWACCfg", observer1), UCSettings.KEY_USE_RAW_VIDEO_CONTROLS);
            UcBizSetupHelper.configure(configProvider.getConfigWithNotifyChange("h5_ucVideoUseRawControl", observer2), UCSettings.KEY_VIDEO_SUPPORT_RAW_CONTROLS_ATTR);
            UcBizSetupHelper.configure(configProvider.getConfigWithNotifyChange("h5_ucVideoDisableFloat", observer3), UCSettings.KEY_DISABLE_FLOAT_VIDEO_VIEW);
            updateWebAudioDecodePolicy(configProvider.getConfigWithNotifyChange("h5_ucApolloConfig", observer4));
        }
        UcSetupTracing.endTrace("initVideoControl");
        return ts - System.currentTimeMillis();
    }

    /* access modifiers changed from: private */
    public static void updateWebAudioDecodePolicy(String value) {
        boolean disableDefaultDecoder = true;
        try {
            JSONObject jsonObject = H5Utils.parseObject(value);
            if (jsonObject != null) {
                disableDefaultDecoder = !"NO".equalsIgnoreCase(jsonObject.getString("webAudioDisableDefaultDecoder"));
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "updateWebAudioDecodePolicy parseObject error", e);
        }
        UCSettings.setGlobalBoolValue(UCSettings.KEY_WEBAUDIO_DISABLE_DEFAULT_DECODER, disableDefaultDecoder);
    }
}
