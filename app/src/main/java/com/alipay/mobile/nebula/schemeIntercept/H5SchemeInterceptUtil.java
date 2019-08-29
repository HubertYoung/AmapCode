package com.alipay.mobile.nebula.schemeIntercept;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5SchemeInterceptUtil {
    public static H5SchemeIntercept getCompetitiveListWarp() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null) {
            return new H5SchemeIntercept(null, false);
        }
        String value = h5ConfigProvider.getConfigWithProcessCache("h5_newcompetitiveList");
        if (!TextUtils.isEmpty(value)) {
            JSONObject jsonObject = H5Utils.parseObject(value);
            if (jsonObject != null && !jsonObject.isEmpty() && "YES".equalsIgnoreCase(H5Utils.getString(jsonObject, (String) "enableUse"))) {
                return new H5SchemeIntercept(value, true);
            }
        }
        return new H5SchemeIntercept(h5ConfigProvider.getConfigWithProcessCache("h5_competitiveList"), false);
    }
}
