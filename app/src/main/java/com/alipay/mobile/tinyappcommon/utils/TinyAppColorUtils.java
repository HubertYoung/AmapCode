package com.alipay.mobile.tinyappcommon.utils;

import android.graphics.Color;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.utils.graphics.SafeColor;

public final class TinyAppColorUtils {
    private static final String TAG = "TinyAppColorUtils";

    public static SafeColor getColor(JSONObject params, String key) {
        String color = H5Utils.getString(params, key);
        if (TextUtils.isEmpty(color)) {
            return SafeColor.INVALID_COLOR;
        }
        try {
            return new SafeColor(Color.parseColor(color));
        } catch (Exception e) {
            H5Log.e(TAG, "parse color error", e);
            return SafeColor.INVALID_COLOR;
        }
    }
}
