package com.alipay.mobile.nebulauc.impl.network;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.locale.LocaleHelper;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;

public class AlipayNetworkLanUtils {
    private static final String TAG = "AlipayNetworkLanUtils";

    public static String getAcceptLanguageValue() {
        try {
            JSONObject acceptLanObj = H5Utils.getJSONObject(H5Utils.parseObject(H5ConfigUtil.getConfig("h5_networkParams")), "acceptlanguageheader", null);
            if (acceptLanObj != null && !acceptLanObj.isEmpty()) {
                String key = LocaleHelper.getInstance().getAlipayLocaleDes();
                String value = acceptLanObj.getString(key);
                H5Log.d(TAG, "convert " + key + " to " + value);
                if (!TextUtils.isEmpty(value)) {
                    return value;
                }
            }
        } catch (Throwable t) {
            H5Log.e(TAG, "catch exception ", t);
        }
        return "zh-CN,en-US;q=0.8";
    }
}
