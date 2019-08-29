package com.alipay.mobile.security.bio.common.statistics;

import android.content.Context;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import java.util.HashMap;
import java.util.Map;

public class StatisticsProcessor {
    private static StatisticsProcessor a;
    private final RecordExtService b = ((RecordExtService) BioServiceManager.getCurrentInstance().getBioService(RecordExtService.class));
    private Map<String, String> c;

    public static StatisticsProcessor getInstance(Context context) {
        if (a == null) {
            a = new StatisticsProcessor();
        }
        return a;
    }

    private StatisticsProcessor() {
    }

    public void init(String str) {
        this.b.setUniqueID(str);
    }

    public void setGlobalMap(Map map) {
        if (this.c == null) {
            this.c = new HashMap();
        }
        this.c.putAll(map);
    }

    public void write(RecordExtAction recordExtAction) {
        if (this.c != null) {
            this.b.write(recordExtAction, this.c);
        } else {
            this.b.write(recordExtAction);
        }
    }

    public void writeWithKey(RecordExtAction recordExtAction, String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put(str, str2);
        if (this.c != null) {
            hashMap.putAll(this.c);
        }
        this.b.write(recordExtAction, hashMap);
    }

    public void writeWithMap(RecordExtAction recordExtAction, Map map) {
        if (!(this.c == null || map == null)) {
            map.putAll(this.c);
        }
        this.b.write(recordExtAction, map);
    }

    public void writeWithKeys(RecordExtAction recordExtAction, String str, String str2, String str3, String str4) {
        HashMap hashMap = new HashMap();
        hashMap.put(str, str2);
        hashMap.put(str3, str4);
        if (this.c != null) {
            hashMap.putAll(this.c);
        }
        this.b.write(recordExtAction, hashMap);
    }

    public void release() {
        if (a != null) {
            a = null;
        }
    }
}
