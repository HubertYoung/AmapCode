package com.alipay.mobile.common.transport.monitor;

import com.alipay.mobile.common.logging.api.monitor.Performance;

public class TransportPerformance extends Performance {
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getSubType()).append(",");
        sb.append(getParam1()).append(",");
        sb.append(getParam2()).append(",");
        sb.append(getParam3()).append(",");
        for (String key : getExtPramas().keySet()) {
            sb.append(key + "=" + getExtPramas().get(key) + "^");
        }
        return sb.toString();
    }
}
