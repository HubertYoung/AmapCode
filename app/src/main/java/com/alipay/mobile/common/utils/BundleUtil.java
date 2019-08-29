package com.alipay.mobile.common.utils;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BundleUtil {
    public BundleUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static String deserialBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        StringBuilder params = new StringBuilder();
        for (String key : bundle.keySet()) {
            String string = bundle.get(key).toString();
            if (!(string == null || Uri.parse(string).getScheme() == null)) {
                try {
                    string = URLEncoder.encode(string, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e("BundleUtil", "", e);
                }
            }
            params.append(key + "=" + string + "&");
        }
        if (params.length() > 0) {
            return params.substring(0, params.length() - 1);
        }
        return null;
    }

    public static Bundle serialBundle(String params) {
        String[] split;
        Bundle bundle = null;
        if (params != null && params.length() > 0) {
            bundle = new Bundle();
            for (String str : params.split("&")) {
                int pos = str.indexOf("=");
                if (pos > 0 && pos < str.length() - 1) {
                    bundle.putString(str.substring(0, pos), str.substring(pos + 1));
                }
            }
        }
        return bundle;
    }
}
