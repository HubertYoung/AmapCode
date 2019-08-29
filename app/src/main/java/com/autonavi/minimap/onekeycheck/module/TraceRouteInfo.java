package com.autonavi.minimap.onekeycheck.module;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public class TraceRouteInfo extends ResultData {
    private Map<String, String> mTraceInfoNodeMap = new HashMap();

    public void putNode(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            this.mTraceInfoNodeMap.put(str, str2);
        }
    }

    public Map<String, String> getTraceInfoNodeMap() {
        return this.mTraceInfoNodeMap;
    }
}
