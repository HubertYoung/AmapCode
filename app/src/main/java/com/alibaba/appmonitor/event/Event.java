package com.alibaba.appmonitor.event;

import com.alibaba.appmonitor.pool.BalancedPool;
import com.alibaba.appmonitor.pool.Reusable;
import com.alibaba.appmonitor.pool.ReuseJSONObject;
import com.alibaba.fastjson.JSONObject;

public abstract class Event implements Reusable {
    protected static final String EXTRA_KEY_BASE = "arg";
    public long begin = Long.MAX_VALUE;
    public long end = 0;
    public int eventId;
    public String extraArg;
    public String module;
    public String monitorPoint;

    public JSONObject dumpToJSONObject() {
        JSONObject jSONObject = (JSONObject) BalancedPool.getInstance().poll(ReuseJSONObject.class, new Object[0]);
        jSONObject.put((String) "page", (Object) this.module);
        jSONObject.put((String) "monitorPoint", (Object) this.monitorPoint);
        jSONObject.put((String) "begin", (Object) Long.valueOf(this.begin));
        jSONObject.put((String) "end", (Object) Long.valueOf(this.end));
        if (this.extraArg != null) {
            jSONObject.put((String) EXTRA_KEY_BASE, (Object) this.extraArg);
        }
        return jSONObject;
    }

    public void clean() {
        this.eventId = 0;
        this.module = null;
        this.monitorPoint = null;
        this.extraArg = null;
        this.begin = Long.MAX_VALUE;
        this.end = 0;
    }

    public void fill(Object... objArr) {
        this.eventId = objArr[0].intValue();
        this.module = objArr[1];
        this.monitorPoint = objArr[2];
        if (objArr.length > 3 && objArr[3] != null) {
            this.extraArg = objArr[3];
        }
    }

    public void commit(Long l) {
        if (l == null) {
            l = Long.valueOf(System.currentTimeMillis() / 1000);
        }
        if (this.begin > l.longValue()) {
            this.begin = l.longValue();
        }
        if (this.end < l.longValue()) {
            this.end = l.longValue();
        }
    }
}
