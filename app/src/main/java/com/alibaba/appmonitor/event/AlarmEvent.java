package com.alibaba.appmonitor.event;

import com.alibaba.analytics.utils.StringUtils;
import com.alibaba.appmonitor.pool.BalancedPool;
import com.alibaba.appmonitor.pool.ReuseJSONArray;
import com.alibaba.appmonitor.pool.ReuseJSONObject;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AlarmEvent extends Event {
    private static final int ERROR_MSG_MAX_LENGTH = 100;
    public Map<String, Integer> errorCodeCount;
    public Map<String, String> errorMsgMap;
    public int failCount = 0;
    public int successCount = 0;

    public synchronized void incrSuccess(Long l) {
        this.successCount++;
        super.commit(l);
    }

    public synchronized void incrFail(Long l) {
        this.failCount++;
        super.commit(l);
    }

    public synchronized void addError(String str, String str2) {
        if (!StringUtils.isBlank(str)) {
            if (this.errorMsgMap == null) {
                this.errorMsgMap = new HashMap();
            }
            if (this.errorCodeCount == null) {
                this.errorCodeCount = new HashMap();
            }
            if (StringUtils.isNotBlank(str2)) {
                int i = 100;
                if (str2.length() <= 100) {
                    i = str2.length();
                }
                this.errorMsgMap.put(str, str2.substring(0, i));
            }
            if (!this.errorCodeCount.containsKey(str)) {
                this.errorCodeCount.put(str, Integer.valueOf(1));
            } else {
                this.errorCodeCount.put(str, Integer.valueOf(this.errorCodeCount.get(str).intValue() + 1));
            }
        }
    }

    public synchronized JSONObject dumpToJSONObject() {
        JSONObject dumpToJSONObject;
        dumpToJSONObject = super.dumpToJSONObject();
        dumpToJSONObject.put((String) "successCount", (Object) Integer.valueOf(this.successCount));
        dumpToJSONObject.put((String) "failCount", (Object) Integer.valueOf(this.failCount));
        if (this.errorCodeCount != null) {
            JSONArray jSONArray = (JSONArray) BalancedPool.getInstance().poll(ReuseJSONArray.class, new Object[0]);
            for (Entry next : this.errorCodeCount.entrySet()) {
                JSONObject jSONObject = (JSONObject) BalancedPool.getInstance().poll(ReuseJSONObject.class, new Object[0]);
                String str = (String) next.getKey();
                jSONObject.put((String) "errorCode", (Object) str);
                jSONObject.put((String) "errorCount", next.getValue());
                if (this.errorMsgMap.containsKey(str)) {
                    jSONObject.put((String) "errorMsg", (Object) this.errorMsgMap.get(str));
                }
                jSONArray.add(jSONObject);
            }
            dumpToJSONObject.put((String) "errors", (Object) jSONArray);
        }
        return dumpToJSONObject;
    }

    public synchronized void clean() {
        super.clean();
        this.successCount = 0;
        this.failCount = 0;
        if (this.errorMsgMap != null) {
            this.errorMsgMap.clear();
        }
        if (this.errorCodeCount != null) {
            this.errorCodeCount.clear();
        }
    }
}
