package com.alipay.mobile.common.transport.monitor;

import java.util.HashMap;
import java.util.Map;

public class RPCDataContainer implements DataContainer {
    private Map<String, String> a = new HashMap();
    private Map<String, DateModel> b = new HashMap();

    class DateModel {
        long mRequestTime;

        DateModel() {
            this.mRequestTime = -1;
            this.mRequestTime = System.currentTimeMillis();
        }

        DateModel(long requestTime) {
            this.mRequestTime = -1;
            this.mRequestTime = requestTime;
        }

        public long getCost() {
            return System.currentTimeMillis() - this.mRequestTime;
        }
    }

    public void putDataItem(String k, String v) {
        this.a.put(k, v);
    }

    public String getDataItem(String k) {
        return a(this.a.get(k));
    }

    public void removeDataItem(String k) {
        this.a.remove(k);
    }

    public void timeItemDot(String k) {
        this.b.put(k, new DateModel());
    }

    public void timeItemDot(String k, long timeMillis) {
        this.b.put(k, new DateModel(timeMillis));
    }

    public void timeItemRelease(String k) {
        DateModel dateModel = this.b.get(k);
        if (dateModel == null || dateModel.mRequestTime == -1) {
            putDataItem(k, "-1");
        } else {
            putDataItem(k, dateModel.getCost());
        }
    }

    private static String a(String v) {
        if (v == null) {
            return null;
        }
        return v.replaceAll(",", "*").replaceAll("=", "*");
    }
}
