package com.alipay.android.phone.wallet.tinytracker;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public class SpmUtils {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '+', '/'};
    public static boolean isDebug;

    public static String objectToString(Object object) {
        if (object == null) {
            return "";
        }
        if (object instanceof String) {
            return object.toString() + '@' + Integer.toHexString(object.hashCode());
        }
        return object.getClass().getName() + '@' + Integer.toHexString(object.hashCode());
    }

    public static void printBehaviour(String tag, Builder builder, String event) {
        if (isDebug) {
            Behavor behaviour = builder.build();
            StringBuilder sb = new StringBuilder(event).append(", [seedId]").append(behaviour.getSeedID()).append(", [pageId]").append(behaviour.getPageId());
            if (behaviour.getParam1() != null) {
                sb.append(", [param1]").append(behaviour.getParam1());
            }
            if (behaviour.getParam2() != null) {
                sb.append(", [param2]").append(behaviour.getParam2());
            }
            if (behaviour.getParam3() != null) {
                sb.append(", [param3]").append(behaviour.getParam3());
            }
            for (Entry entry : behaviour.getExtParams().entrySet()) {
                sb.append(", [").append((String) entry.getKey()).append("]").append((String) entry.getValue());
            }
            SpmLogCator.debug(tag, sb.toString());
        }
    }

    public static String getViewKey(Object view) {
        if (view == null) {
            return null;
        }
        if (view instanceof String) {
            return view.toString() + view.hashCode();
        }
        return view.getClass().getName() + view.hashCode();
    }

    public static String c10to64(long num) {
        return a(num);
    }

    private static String a(long i) {
        int bit = (int) Math.pow(2.0d, 6.0d);
        char[] buf = new char[bit];
        int charPos = bit;
        do {
            charPos--;
            buf[charPos] = a[(int) (63 & i)];
            i >>>= 6;
        } while (i != 0);
        return new String(buf, charPos, bit - charPos);
    }

    public static boolean checkAntEvent(Behavor behavor) {
        return "true".equals(behavor.getExtParams().get("isEventLink")) && !TextUtils.isEmpty(behavor.getExtParams().get("eventId")) && !TextUtils.isEmpty(behavor.getBehaviourPro());
    }

    public static Map<String, String> parseJsonToMap(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            Map resMap = new HashMap();
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                resMap.put(key, (String) jsonObject.get(key));
            }
            return resMap;
        } catch (Exception e) {
            return null;
        }
    }

    public static String refreshParam(String newParams, String pageParam) {
        try {
            if (TextUtils.isEmpty(newParams)) {
                return pageParam;
            }
            Map paramMap = parseJsonToMap(newParams);
            if (paramMap == null || paramMap.size() <= 0) {
                return pageParam;
            }
            if (TextUtils.isEmpty(pageParam)) {
                return newParams;
            }
            Map targetParamMap = parseJsonToMap(pageParam);
            if (targetParamMap == null || targetParamMap.size() <= 0) {
                return newParams;
            }
            targetParamMap.putAll(paramMap);
            return new JSONObject(targetParamMap).toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static void refreshParam(String newParams, Map<String, String> pageParam) {
        try {
            if (!TextUtils.isEmpty(newParams) && pageParam != null) {
                Map paramMap = parseJsonToMap(newParams);
                if (paramMap != null && paramMap.size() > 0) {
                    pageParam.putAll(paramMap);
                }
            }
        } catch (Exception e) {
        }
    }
}
