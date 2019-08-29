package com.alibaba.appmonitor.event;

import com.alibaba.fastjson.JSONObject;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;

public class CountEvent extends Event {
    public int count;
    public double value;

    public synchronized void addValue(double d, Long l) {
        this.value += d;
        this.count++;
        super.commit(l);
    }

    public synchronized JSONObject dumpToJSONObject() {
        JSONObject dumpToJSONObject;
        dumpToJSONObject = super.dumpToJSONObject();
        dumpToJSONObject.put((String) NewHtcHomeBadger.COUNT, (Object) Integer.valueOf(this.count));
        dumpToJSONObject.put((String) "value", (Object) Double.valueOf(this.value));
        return dumpToJSONObject;
    }

    public synchronized void fill(Object... objArr) {
        super.fill(objArr);
        this.value = 0.0d;
        this.count = 0;
    }
}
