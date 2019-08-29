package com.alipay.mobile.nebulauc.provider;

import android.content.res.Configuration;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.nebula.provider.H5UCProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulauc.util.CommonUtil;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.nebulaucsdk.UcSdkConstants;
import com.uc.webview.export.Build;
import com.uc.webview.export.Build.Version;
import com.uc.webview.export.extension.UCCore;
import java.io.File;

public class H5UCProviderImpl implements H5UCProvider {
    private static final String TAG = "H5UCProviderImpl";
    private String mLastExtraConfig;

    public boolean isM40() {
        return CommonUtil.isM40(Version.NAME + "_" + Build.CORE_TIME);
    }

    public boolean notifyConfigurationChanged(Configuration configuration) {
        H5Log.d(TAG, "notifyConfigurationChanged: " + configuration);
        H5ConfigUtil.getConfigJSONObject("h5_notifyWebViewConfigurationChanged");
        if (!H5Flag.ucReady) {
            return false;
        }
        JSONObject config = H5ConfigUtil.getConfigJSONObject("h5_notifyWebViewConfigurationChanged");
        if (!H5Utils.getBoolean(config, (String) "enable", false)) {
            return false;
        }
        try {
            Object extraConfig = Configuration.class.getDeclaredField(H5Utils.getString(config, (String) "field", (String) "extraConfig")).get(configuration);
            if (extraConfig == null) {
                return false;
            }
            String extraConfigStr = extraConfig.toString();
            if (extraConfigStr == null || extraConfigStr.equals(this.mLastExtraConfig)) {
                return false;
            }
            this.mLastExtraConfig = extraConfigStr;
            H5Log.d(TAG, "notify CORE_EVENT_ON_ACTIVITY_RECREATE");
            return true;
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
            return false;
        }
    }

    public String getWebViewCoreSoPath() {
        String soPath = "";
        if (UcSdkConstants.IS_DIFF_BUNDLE) {
            File ucmFinalFile = new File(H5Utils.getContext().getDir("plugins_lib", 0), UcSdkConstants.SO_NAME.replace("7z_uc.so", "zip_uc.so"));
            if (ucmFinalFile != null) {
                soPath = UCCore.getExtractDirPath(H5Utils.getContext(), ucmFinalFile.getPath());
            }
        } else {
            File mainSoFile = new File(new File(new File(new File(H5Utils.getContext().getDir("h5container", 0), "uc"), UcSdkConstants.UC_VERSION + "/so"), "/lib"), "libwebviewuc.so");
            if (mainSoFile != null) {
                soPath = mainSoFile.getPath();
            }
        }
        H5Log.d(TAG, "getWebViewCoreSoPath : " + soPath);
        return soPath;
    }
}
