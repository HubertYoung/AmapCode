package com.alipay.mobile.nebulacore.manager;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class H5PluginManagerUtil {
    public static H5PluginManagerUtil instance;
    private JSONArray a = new JSONArray();
    private JSONArray b = new JSONArray();

    public static synchronized H5PluginManagerUtil getInstance() {
        H5PluginManagerUtil h5PluginManagerUtil;
        synchronized (H5PluginManagerUtil.class) {
            try {
                if (instance == null) {
                    instance = new H5PluginManagerUtil();
                }
                h5PluginManagerUtil = instance;
            }
        }
        return h5PluginManagerUtil;
    }

    private H5PluginManagerUtil() {
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider != null) {
            JSONObject config = provider.getConfigJSONObject("h5_jsapiandPluginsConfig");
            if (config != null && !config.isEmpty()) {
                this.a = H5Utils.getJSONArray(config, "jsapis", this.a);
                this.b = H5Utils.getJSONArray(config, "plugins", this.b);
            }
        }
    }

    public boolean isInPluginBlackList(String pluginName) {
        boolean result = this.b.contains(pluginName);
        if (result) {
            H5Log.d("H5PluginManagerUtil", "unregister plugin, " + pluginName + " is in blackList");
            H5LogUtil.logNebulaTech(H5LogData.seedId("PSD_Plugin_Register_Forbidden").param3().add("plugin", pluginName));
        }
        return result;
    }

    public boolean isInJsApiBlackList(String jsApiName) {
        boolean result = this.a.contains(jsApiName);
        if (result) {
            H5Log.d("H5PluginManagerUtil", "unregister jsApi, " + jsApiName + " is in blackList");
            H5LogUtil.logNebulaTech(H5LogData.seedId("PSD_Jsapi_Register_Forbidden").param3().add("jsapi", jsApiName));
        }
        return result;
    }

    public static boolean enableFilterPlugin() {
        String config = H5Environment.getConfigWithProcessCache("h5_enableFilterPlugin");
        if (TextUtils.isEmpty(config) || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(config)) {
            return true;
        }
        return false;
    }
}
