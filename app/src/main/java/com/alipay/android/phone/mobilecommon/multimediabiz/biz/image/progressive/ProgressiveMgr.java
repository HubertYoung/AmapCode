package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.progressive;

import android.text.TextUtils;
import java.util.concurrent.ConcurrentHashMap;

public class ProgressiveMgr {
    private static ProgressiveMgr a;
    private ConcurrentHashMap<String, Integer> b = new ConcurrentHashMap<>();

    private ProgressiveMgr() {
    }

    public static ProgressiveMgr getInstance() {
        if (a == null) {
            synchronized (ProgressiveMgr.class) {
                try {
                    if (a == null) {
                        a = new ProgressiveMgr();
                    }
                }
            }
        }
        return a;
    }

    public int getProgressiveVal(String key) {
        if (TextUtils.isEmpty(key) || !this.b.containsKey(key)) {
            return -1;
        }
        return this.b.get(key).intValue();
    }

    public boolean isExistKey(String key) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        return this.b.containsKey(key);
    }

    public void putProgressiveVal(String key, int value) {
        if (!TextUtils.isEmpty(key) && value >= 0 && value <= 100) {
            this.b.put(key, Integer.valueOf(value));
        }
    }

    public void removeProgressiveVal(String key) {
        this.b.remove(key);
    }
}
