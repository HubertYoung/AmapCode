package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.impl;

import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.Collection;
import java.util.Map;

public class ExpiredLruMemCache<T> {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger((String) "ExpiredLruMemCache");
    /* access modifiers changed from: private */
    public LruCache<String, T> b;
    private long c;
    private Handler d = new Handler(TaskScheduleManager.get().commonLooper()) {
        public void handleMessage(Message msg) {
            String key = (String) msg.obj;
            ExpiredLruMemCache.a.d("expired remove: " + key + ", size: " + ExpiredLruMemCache.this.b.size(), new Object[0]);
            ExpiredLruMemCache.this.remove(key);
        }
    };

    public ExpiredLruMemCache(int size, long expiredTime) {
        this.c = expiredTime;
        this.b = new LruCache<String, T>(size) {
            /* access modifiers changed from: protected */
            public int sizeOf(String key, T value) {
                return ExpiredLruMemCache.this.sizeOf(key, value);
            }
        };
    }

    /* access modifiers changed from: protected */
    public int sizeOf(String key, T o) {
        return 1;
    }

    public boolean put(String key, T value) {
        if (key == null || value == null) {
            return false;
        }
        this.b.put(key, value);
        a(key);
        return true;
    }

    public T get(String key) {
        if (key == null) {
            return null;
        }
        Object val = this.b.get(key);
        if (val == null) {
            return val;
        }
        a(key);
        return val;
    }

    public T remove(String key) {
        if (key == null) {
            return null;
        }
        Object pre = this.b.remove(key);
        if (pre == null) {
            return pre;
        }
        this.d.removeMessages(key.hashCode());
        return pre;
    }

    public void trimToSize(int maxSize) {
        this.b.trimToSize(maxSize);
    }

    public long getMemoryMaxSize() {
        return (long) this.b.maxSize();
    }

    public Collection<String> keys() {
        return this.b.snapshot().keySet();
    }

    public void clear() {
        this.b.evictAll();
    }

    public long size() {
        return (long) this.b.size();
    }

    private void a(String key) {
        this.d.removeMessages(key.hashCode());
        this.d.sendMessageDelayed(this.d.obtainMessage(key.hashCode(), key), this.c);
    }

    public void debugInfo() {
        a.d("debugInfo:" + this.b.toString() + ", size: " + size(), new Object[0]);
    }

    public Map<String, T> snapshot() {
        if (this.b != null) {
            return this.b.snapshot();
        }
        return null;
    }
}
