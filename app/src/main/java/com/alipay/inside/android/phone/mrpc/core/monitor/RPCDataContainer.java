package com.alipay.inside.android.phone.mrpc.core.monitor;

import java.util.HashMap;
import java.util.Map;

public class RPCDataContainer implements DataContainer {
    private Map<String, String> dataMap = new HashMap();
    private Map<String, DateModel> timeMap = new HashMap();

    class DateModel {
        long mRequestTime;

        DateModel() {
            this.mRequestTime = -1;
            this.mRequestTime = System.currentTimeMillis();
        }

        DateModel(long j) {
            this.mRequestTime = -1;
            this.mRequestTime = j;
        }

        public long getCost() {
            return System.currentTimeMillis() - this.mRequestTime;
        }
    }

    public void putDataItem(String str, String str2) {
        this.dataMap.put(str, str2);
    }

    public String getDataItem(String str) {
        return valueGuard(this.dataMap.get(str));
    }

    public void removeDataItem(String str) {
        this.dataMap.remove(str);
    }

    public void timeItemDot(String str) {
        this.timeMap.put(str, new DateModel());
    }

    public void timeItemDot(String str, long j) {
        this.timeMap.put(str, new DateModel(j));
    }

    public void timeItemRelease(String str) {
        DateModel dateModel = this.timeMap.get(str);
        if (dateModel == null || dateModel.mRequestTime == -1) {
            putDataItem(str, "-1");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(dateModel.getCost());
        putDataItem(str, sb.toString());
    }

    private String valueGuard(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll(",", "*").replaceAll("=", "*");
    }
}
