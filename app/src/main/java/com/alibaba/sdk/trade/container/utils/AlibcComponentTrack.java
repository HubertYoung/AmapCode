package com.alibaba.sdk.trade.container.utils;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.ut.AlibcUserTracker;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.utils.cache.MemoryCacheUtils;
import com.alibaba.baichuan.android.trade.utils.code.Base64Utils;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AlibcComponentTrack {
    public static final String ERRNO_COMPONENT_CART_CANCEL_AUTH = "210131";
    public static final String ERRNO_COMPONENT_CART_CANCEL_LOGIN = "210121";
    public static final String ERRNO_COMPONENT_CART_MTOP_FAIL = "210151";
    public static final String ERRNO_COMPONENT_CART_PARM = "210100";
    public static final String ERRNO_COMPONENT_COUPON_CANCEL_LOGIN = "210221";
    public static final String ERRNO_COMPONENT_COUPON_MTOP_FAIL = "210251";
    public static final String ERRNO_COMPONENT_COUPON_PARM = "210200";
    public static final String ERRNO_COMPONENT_INIT_FAIL = "180201";
    public static final String ERRNO_NBSDK_INIT_FAIL = "180101";
    public static final String ERRNO_WANT_CART_DISABLE = "220142";
    public static final String ERRNO_WANT_CART_NO_PACKEGE = "220141";
    public static final String ERRNO_WANT_CART_PARM = "220100";
    public static final String ITEMID_KEY_FOR_MTOP = "itemId";
    public static final String ITEMID_KEY_FOR_SHENYICANMOU = "item_id";
    public static final String ITEMID_KEY_FOR_YBHPSS = "item_id";
    public static final String LABEL_KEY_FOR_YBHPSS = "bc_label";
    public static final String LABEL_VALUE_FOR_YBHPSS_CART_API = "baichuan_cart_api";
    public static final String LABEL_VALUE_FOR_YBHPSS_CART_WANT = "baichuan_cart_want";
    public static final String LABEL_VALUE_FOR_YBHPSS_COUPON_API = "baichuan_coupon_api";
    public static final String LABEL_VALUE_FOR_YBHPSS_COUPON_WANT = "baichuan_coupon_want";
    public static final String MODEL_NAME_COMPONENT = "BCPCSDK";
    public static final String MODEL_NAME_WANT = "BCWant";
    public static final String MONITOR_POINT_COMPONENT_INIT = "PacketContainer_Init";
    public static final String MONITOR_POINT_WANT_CART = "addCart";
    public static final String MONITOR_POINT_WANT_COUPON_GET = "Coupon_Get";
    public static final String MONITOR_POINT_WANT_SELLER_COUPON_QUERY = "Coupon_Seller_Query";
    public static final String MTOP_ERRNO_AUTH_CANCEL = "FAIL_SYS_ACCESS_TOKEN_CANCEL";
    public static final String MTOP_ERRNO_LOGIN_CANCEL = "FAIL_SYS_LOGIN_CANCEL";
    private static final String TAG = "AlibcComponentTrack";
    public static final String UT_CONTROL_NAME_WANTCART_PRESS = "Page_Baichuan_Want_Cart_Succeed";
    public static final String UT_CONTROL_NAME_WANT_CART = "Page_Baichuan_Want_Cart";
    public static final String UT_CONTROL_NAME_WANT_DISAPPEAR = "Page_Baichuan_Want_Disappear";
    public static final String UT_CONTROL_NAME_WANT_PRESS = "Page_Baichuan_Want_Pressed";
    public static final int UT_EVENT_ID_ADDCART_CLICK = 2101;
    public static final int UT_EVENT_ID_ADDCART_UI = 2201;

    public static void sendUseabilityFailure(String str, String str2, String str3, String str4) {
        AlibcUserTracker instance = AlibcUserTracker.getInstance();
        if (instance != null) {
            instance.sendUseabilityFailure(str, str2, str3, str4);
        }
    }

    public static void sendUseabilitySuccess(String str, String str2) {
        AlibcUserTracker instance = AlibcUserTracker.getInstance();
        if (instance != null) {
            instance.sendUseabilitySuccess(str, str2);
        }
    }

    public static void sentUserTrack(int i, String str, Map<String, String> map, String str2) {
        HashMap hashMap;
        AlibcUserTracker instance = AlibcUserTracker.getInstance();
        if (instance != null) {
            HashMap hashMap2 = null;
            if (map != null) {
                HashMap hashMap3 = new HashMap();
                if (str2 != null) {
                    hashMap2 = new HashMap();
                    hashMap2.put("item_id", str2);
                    hashMap3.put("item_id", str2);
                }
                hashMap3.put("ybhpss", createYbhpss(map, hashMap2));
                hashMap = hashMap3;
            } else {
                hashMap = null;
            }
            instance.sendCustomHit((String) null, i, str, (String) null, (String) null, (Map) hashMap);
        }
    }

    private static void params2String(StringBuilder sb, Map<String, String> map) {
        if (map != null && map.size() != 0) {
            for (Entry next : map.entrySet()) {
                String str = (String) next.getKey();
                String str2 = (String) next.getValue();
                if (sb.length() > 0) {
                    sb.append("&");
                }
                try {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append("=");
                    sb2.append(URLEncoder.encode(new String(str2), "utf-8"));
                    sb.append(sb2.toString());
                } catch (Exception e) {
                    StringBuilder sb3 = new StringBuilder("构建Ybhpss参数错误：");
                    sb3.append(e.getMessage());
                    AlibcLogger.e(TAG, sb3.toString());
                }
            }
        }
    }

    public static String createYbhpss(Map<String, String> map, Map<String, String> map2) {
        if (map == null || map.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        params2String(sb, map);
        if (map2 != null) {
            params2String(sb, map2);
        }
        if (TextUtils.isEmpty(sb)) {
            return sb.toString();
        }
        String base64 = Base64Utils.getBase64(new String(sb));
        MemoryCacheUtils.setGroupProperity(AlibcConstants.TRADE_GROUP, "ybhpss", base64);
        return URLEncoder.encode(base64.trim());
    }

    public static Map<String, String> preprocessYbhpss(Map<String, String> map, String str, boolean z) {
        if (map == null) {
            AlibcLogger.e(TAG, "params is null when add label");
            return null;
        }
        map.put("ttid", AlibcConfig.getInstance().getWebTTID());
        if (TextUtils.equals(map.get(LABEL_KEY_FOR_YBHPSS), str)) {
            AlibcLogger.d(TAG, "label value equal");
        }
        if (z) {
            AlibcLogger.e(TAG, "removed label value is:".concat(String.valueOf(map.put(LABEL_KEY_FOR_YBHPSS, str))));
        } else if (map.get(LABEL_KEY_FOR_YBHPSS) == null) {
            map.put(LABEL_KEY_FOR_YBHPSS, str);
        }
        return map;
    }
}
