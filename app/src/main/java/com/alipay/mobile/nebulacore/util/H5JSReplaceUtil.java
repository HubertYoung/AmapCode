package com.alipay.mobile.nebulacore.util;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class H5JSReplaceUtil {
    static Boolean a = null;
    static List<String> b = Collections.synchronizedList(new ArrayList());
    static List<Integer> c = Collections.synchronizedList(new ArrayList());

    public static String replaceInvisibleStr(String javascript) {
        if (TextUtils.isEmpty(javascript) || c == null || c.isEmpty()) {
            return javascript;
        }
        String result = "";
        long time = System.currentTimeMillis();
        for (int i = 0; i < javascript.length(); i++) {
            if (c.contains(Integer.valueOf(javascript.charAt(i)))) {
                H5Log.d("H5JSReplaceUtil", "asciiList contains : " + javascript.charAt(i) + " not add");
            } else {
                result = result + javascript.charAt(i);
            }
        }
        H5Log.d("H5JSReplaceUtil", "coast " + (System.currentTimeMillis() - time) + " for replaceInvisibleStr");
        return result;
    }

    public static boolean containAction(String action) {
        if (b == null || b.isEmpty()) {
            return false;
        }
        return b.contains(action);
    }

    public static boolean enableReplace() {
        if (a != null) {
            return a.booleanValue();
        }
        JSONObject jsonObject = H5Environment.getConfigJSONObject("h5_replaceInvisibleCharacter");
        if (jsonObject == null) {
            Boolean valueOf = Boolean.valueOf(false);
            a = valueOf;
            return valueOf.booleanValue();
        }
        a = Boolean.valueOf(H5Utils.getBoolean(jsonObject, (String) "enable", false));
        JSONArray actionArray = H5Utils.getJSONArray(jsonObject, "jsapis", null);
        if (actionArray != null && !actionArray.isEmpty()) {
            Iterator<Object> it = actionArray.iterator();
            while (it.hasNext()) {
                Object action = it.next();
                if (action instanceof String) {
                    b.add(action.toString());
                }
            }
        }
        JSONArray asciiArray = H5Utils.getJSONArray(jsonObject, "ascii", null);
        if (actionArray != null && !actionArray.isEmpty()) {
            Iterator<Object> it2 = asciiArray.iterator();
            while (it2.hasNext()) {
                Object ascii = it2.next();
                if (ascii instanceof Integer) {
                    c.add(Integer.valueOf(((Integer) ascii).intValue()));
                }
            }
        }
        H5Log.d("H5JSReplaceUtil", "enable : " + a + " actionList : " + b + " asciiList : " + c);
        return a.booleanValue();
    }
}
