package com.alipay.mobile.framework.util;

import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;

public final class JSONUtil {
    public static String set2Json(Set<String> set) {
        if (set == null) {
            return "null";
        }
        JSONArray jsonArray = new JSONArray();
        if (!set.isEmpty()) {
            for (String str : set) {
                jsonArray.put(str);
            }
        }
        return jsonArray.toString();
    }

    public static Set<String> json2Set(String json) {
        Set setRet = null;
        if (json == null) {
            return null;
        }
        try {
            JSONArray jsonArray = new JSONArray(json);
            int N = jsonArray.length();
            Set setRet2 = new HashSet(N);
            int i = 0;
            while (i < N) {
                try {
                    setRet2.add(jsonArray.get(i).toString());
                    i++;
                } catch (JSONException e) {
                    e = e;
                    setRet = setRet2;
                    TraceLogger.w((String) "JSONUtil", (Throwable) e);
                    return setRet;
                }
            }
            return setRet2;
        } catch (JSONException e2) {
            e = e2;
            TraceLogger.w((String) "JSONUtil", (Throwable) e);
            return setRet;
        }
    }

    public static String list2Json(List<String> list) {
        if (list == null) {
            return "null";
        }
        JSONArray jsonArray = new JSONArray();
        if (!list.isEmpty()) {
            for (String str : list) {
                jsonArray.put(str);
            }
        }
        return jsonArray.toString();
    }

    public static List<String> json2List(String json) {
        List listRet = null;
        if (json == null) {
            return null;
        }
        try {
            JSONArray jsonArray = new JSONArray(json);
            int N = jsonArray.length();
            List listRet2 = new ArrayList(N);
            int i = 0;
            while (i < N) {
                try {
                    listRet2.add(jsonArray.get(i).toString());
                    i++;
                } catch (JSONException e) {
                    e = e;
                    listRet = listRet2;
                    TraceLogger.w((String) "JSONUtil", (Throwable) e);
                    return listRet;
                }
            }
            return listRet2;
        } catch (JSONException e2) {
            e = e2;
            TraceLogger.w((String) "JSONUtil", (Throwable) e);
            return listRet;
        }
    }
}
