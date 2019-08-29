package com.ut.mini.core;

import android.taobao.windvane.jsbridge.WVApiPlugin;
import android.taobao.windvane.jsbridge.WVCallBackContext;
import android.taobao.windvane.jsbridge.WVResult;
import com.alibaba.analytics.utils.StringUtils;
import com.alibaba.mtl.appmonitor.AppMonitor;
import com.ut.mini.UTAnalytics;
import com.ut.mini.UTHybridHelper;
import com.ut.mini.internal.UTTeamWork;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class WVUserTrack extends WVApiPlugin {
    public boolean execute(String str, String str2, WVCallBackContext wVCallBackContext) {
        if ("toUT".equals(str)) {
            toUT(str2, wVCallBackContext);
            return true;
        } else if ("toUT2".equalsIgnoreCase(str)) {
            toUT2(str2, wVCallBackContext);
            return true;
        } else if ("turnOnUTRealtimeDebug".equals(str)) {
            turnOnUTRealtimeDebug(str2, wVCallBackContext);
            return true;
        } else if ("turnOffUTRealtimeDebug".equals(str)) {
            turnOffUTRealtimeDebug(str2, wVCallBackContext);
            return true;
        } else if ("turnOnRealtimeDebug".equals(str)) {
            turnOnAppMonitorRealtimeDebug(str2, wVCallBackContext);
            return true;
        } else if ("turnOffRealtimeDebug".equals(str)) {
            turnOffAppMonitorRealtimeDebug(str2, wVCallBackContext);
            return true;
        } else if ("selfCheck".equals(str)) {
            selfCheck(str2, wVCallBackContext);
            return true;
        } else if (!"skipPage".equals(str)) {
            return false;
        } else {
            try {
                UTAnalytics.getInstance().getDefaultTracker().skipPage(this.mContext);
            } catch (Throwable unused) {
            }
            return true;
        }
    }

    public final void toUT(String str, WVCallBackContext wVCallBackContext) {
        if (this.mContext != null) {
            Map<String, String> transStringToMap = transStringToMap(str);
            if (transStringToMap != null) {
                UTHybridHelper.getInstance().h5UT(transStringToMap, this.mContext);
            }
        }
        wVCallBackContext.success();
    }

    public void toUT2(String str, WVCallBackContext wVCallBackContext) {
        if (this.mContext != null) {
            Map<String, String> transStringToMap = transStringToMap(str);
            if (transStringToMap != null) {
                UTHybridHelper.getInstance().h5UT2(transStringToMap, this.mContext);
            }
        }
        wVCallBackContext.success();
    }

    private Map<String, String> transStringToMap(String str) {
        HashMap hashMap = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                if (!StringUtils.isEmpty(next)) {
                    String string = jSONObject.getString(next);
                    if (!StringUtils.isEmpty(string)) {
                        hashMap.put(next, string);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public final void selfCheck(String str, WVCallBackContext wVCallBackContext) {
        if (!isEmpty(str)) {
            try {
                String selfCheck = UTAnalytics.getInstance().selfCheck(com.alibaba.fastjson.JSONObject.parseObject(str).get("utap_sample").toString());
                WVResult wVResult = new WVResult();
                wVResult.addData("result", selfCheck);
                wVCallBackContext.success(wVResult);
            } catch (com.alibaba.fastjson.JSONException unused) {
                wVCallBackContext.error();
            }
        }
    }

    public final void turnOnUTRealtimeDebug(String str, WVCallBackContext wVCallBackContext) {
        if (!isEmpty(str)) {
            try {
                com.alibaba.fastjson.JSONObject parseObject = com.alibaba.fastjson.JSONObject.parseObject(str);
                Set<String> keySet = parseObject.keySet();
                HashMap hashMap = new HashMap();
                if (keySet != null && keySet.size() > 0) {
                    for (String next : keySet) {
                        hashMap.put(next, parseObject.get(next).toString());
                    }
                    UTTeamWork.getInstance().turnOnRealTimeDebug(hashMap);
                }
            } catch (com.alibaba.fastjson.JSONException unused) {
                wVCallBackContext.error();
            }
        }
        wVCallBackContext.success();
    }

    public final void turnOffUTRealtimeDebug(String str, WVCallBackContext wVCallBackContext) {
        try {
            UTTeamWork.getInstance().turnOffRealTimeDebug();
        } catch (com.alibaba.fastjson.JSONException unused) {
            wVCallBackContext.error();
        }
        wVCallBackContext.success();
    }

    public final void turnOnAppMonitorRealtimeDebug(String str, WVCallBackContext wVCallBackContext) {
        if (!isEmpty(str)) {
            try {
                com.alibaba.fastjson.JSONObject parseObject = com.alibaba.fastjson.JSONObject.parseObject(str);
                Set<String> keySet = parseObject.keySet();
                HashMap hashMap = new HashMap();
                if (keySet != null && keySet.size() > 0) {
                    for (String next : keySet) {
                        hashMap.put(next, parseObject.get(next).toString());
                    }
                    AppMonitor.turnOnRealTimeDebug(hashMap);
                }
            } catch (com.alibaba.fastjson.JSONException unused) {
                wVCallBackContext.error();
            }
        }
        wVCallBackContext.success();
    }

    public final void turnOffAppMonitorRealtimeDebug(String str, WVCallBackContext wVCallBackContext) {
        try {
            AppMonitor.turnOffRealTimeDebug();
        } catch (com.alibaba.fastjson.JSONException unused) {
            wVCallBackContext.error();
        }
        wVCallBackContext.success();
    }

    private boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }
}
