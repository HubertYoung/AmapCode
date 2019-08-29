package com.alibaba.baichuan.android.trade.adapter.ut;

import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.utils.a;
import com.alibaba.baichuan.android.trade.utils.json.JSONUtils;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class ErrorMsgBeanGererator {
    private static final String a = "ErrorMsgBeanGererator";
    private static Map b;
    private static Map c;
    private static Map d;
    private static Set e;

    static {
        b = new HashMap();
        c = new HashMap();
        d = new HashMap();
        HashSet hashSet = new HashSet();
        e = hashSet;
        hashSet.add(UserTrackerConstants.EM_LOAD_FAILURE);
        e.add(UserTrackerConstants.EM_NETWORK_ERROR);
        e.add(UserTrackerConstants.EM_REQUEST_FAILURE);
        b = getMap("{Init:\"10\",_Page_Native:\"11\",_Page_H5:\"12\",Fetch_Config:\"13\",Login:\"14\",InvokeMtop:\"15\",Pay_Result_Alipay:\"16\",Taoke_Trace_Async:\"17\",Taoke_Trace_Sync:\"17\"}");
        c = getMap("{Taoke_Trace_Async:\"01\",Init:\"01\",Detail_Page:\"01\",Fetch_Config:\"01\",Login:\"01\",Pay_Result_Alipay:\"01\",InvokeMtop:\"01\",Shop_Page:\"02\",Taoke_Trace_Sync:\"02\",Order_Page:\"03\",Cart_Page:\"04\",MyCarts_Page:\"05\",Other_Page:\"07\"}");
        d = getMap("{无安全图片:\"01\",安全图片不合法:\"02\",参数不合法:\"01\",applink不存在:\"02\",applink调用失败:\"03\",加载失败:\"网络错误码\",校验错误:\"01\",解析错误:\"02\",网络错误:\"网络错误码\",登陆失败:\"01\",查询失败:\"01\",支付失败:\"02\",请求失败:\"请求错误码\"}");
    }

    private static String a(String str) {
        Object obj;
        if (b.get(str) != null) {
            obj = b.get(str);
        } else {
            for (String str2 : b.keySet()) {
                if (str.contains(str2)) {
                    obj = b.get(str2);
                }
            }
            return null;
        }
        return (String) obj;
    }

    private static String a(String str, String str2) {
        return e.contains(str) ? str2 : (String) d.get(str);
    }

    private static String b(String str) {
        Object obj;
        if (c.get(str) != null) {
            obj = c.get(str);
        } else {
            for (String str2 : c.keySet()) {
                if (str.contains(str2)) {
                    obj = c.get(str2);
                }
            }
            return null;
        }
        return (String) obj;
    }

    private static boolean c(String str) {
        if (str == null) {
            a.a(a, "isContaionInNotNullList", "Param str is null!");
            AlibcLogger.e(a, "isContaionInNotNullList传递参数为空");
            return false;
        }
        for (String equals : e) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static Map getMap(String str) {
        try {
            return JSONUtils.toStringMap(new JSONObject(str));
        } catch (JSONException unused) {
            a.a(a, "getMap", "转换jsonStr为jsonObject失败");
            AlibcLogger.e(a, "转换jsonStr为jsonObject失败");
            return null;
        }
    }

    public static UsabilityErrorMsg getUsabilityMsg(String str, String str2) {
        return getUsabilityMsg(str, str2, null);
    }

    public static UsabilityErrorMsg getUsabilityMsg(String str, String str2, String str3) {
        if (c(str2) && str3 == null) {
            a.a(a, "getUsabilityMsg", "errCode is not right!");
            return null;
        } else if (b == null || c == null || d == null) {
            a.a(a, "getUsabilityMsg", "modelCodeMap/modelCodeMap/errCodeMap is null !");
            return null;
        } else {
            String a2 = a(str);
            String b2 = b(str);
            String a3 = a(str2, str3);
            if (a2 != null && b2 != null && a3 != null) {
                return new UsabilityErrorMsg(a2, b2, a3, str2);
            }
            a.a(a, "getUsabilityMsg", "modelCode/linkCode/errCode is null!");
            return null;
        }
    }
}
