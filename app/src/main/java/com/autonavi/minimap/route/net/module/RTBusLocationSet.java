package com.autonavi.minimap.route.net.module;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class RTBusLocationSet {
    private HashMap<String, RTBusLocation> buses = new HashMap<>();
    private int code;

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public HashMap<String, RTBusLocation> getBuses() {
        return this.buses;
    }

    @JSONField(name = "buses")
    public void setBuses(ArrayList<RTBusLocation> arrayList) {
        Iterator<RTBusLocation> it = arrayList.iterator();
        while (it.hasNext()) {
            RTBusLocation next = it.next();
            this.buses.put(next.getStation(), next);
        }
    }
}
