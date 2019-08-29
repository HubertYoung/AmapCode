package com.alibaba.baichuan.android.jsbridge.plugin;

import android.content.Context;
import android.content.Intent;
import com.alibaba.baichuan.android.trade.c.b.d;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AlibcPluginEntryManager {
    private Context a = null;
    private d b = null;
    private Map c = new HashMap();
    private ReentrantReadWriteLock d = new ReentrantReadWriteLock(true);

    public AlibcPluginEntryManager(Context context, d dVar) {
        this.a = context;
        this.b = dVar;
    }

    public void addEntry(String str, Object obj) {
        this.d.writeLock().lock();
        try {
            this.c.put(str, obj);
        } finally {
            this.d.writeLock().unlock();
        }
    }

    public Object getEntry(String str) {
        this.d.readLock().lock();
        try {
            Object obj = this.c.get(str);
            if (obj != null) {
                return obj;
            }
            this.d.writeLock().lock();
            try {
                if (this.c.get(str) == null) {
                    Object createPlugin = AlibcPluginManager.createPlugin(str, this.a, this.b);
                    if (createPlugin != null) {
                        this.c.put(str, createPlugin);
                        obj = createPlugin;
                    }
                }
                return obj;
            } finally {
                this.d.writeLock().unlock();
            }
        } finally {
            this.d.readLock().unlock();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        this.d.readLock().lock();
        try {
            for (Object next : this.c.values()) {
                if (next instanceof AlibcApiPlugin) {
                    ((AlibcApiPlugin) next).onActivityResult(i, i2, intent);
                }
            }
        } finally {
            this.d.readLock().unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    public void onDestroy() {
        this.d.readLock().lock();
        try {
            for (Object next : this.c.values()) {
                if (next instanceof AlibcApiPlugin) {
                    ((AlibcApiPlugin) next).onDestroy();
                }
            }
            this.d.readLock().unlock();
            this.d.writeLock().lock();
            try {
                this.c.clear();
            } finally {
                this.d.writeLock().unlock();
            }
        } catch (Throwable th) {
            this.d.readLock().unlock();
            throw th;
        }
    }

    public void onPause() {
        this.d.readLock().lock();
        try {
            for (Object next : this.c.values()) {
                if (next instanceof AlibcApiPlugin) {
                    ((AlibcApiPlugin) next).onPause();
                }
            }
        } finally {
            this.d.readLock().unlock();
        }
    }

    public void onResume() {
        this.d.readLock().lock();
        try {
            for (Object next : this.c.values()) {
                if (next instanceof AlibcApiPlugin) {
                    ((AlibcApiPlugin) next).onResume();
                }
            }
        } finally {
            this.d.readLock().unlock();
        }
    }

    public void onScrollChanged(int i, int i2, int i3, int i4) {
        this.d.readLock().lock();
        try {
            for (Object next : this.c.values()) {
                if (next instanceof AlibcApiPlugin) {
                    ((AlibcApiPlugin) next).onScrollChanged(i, i2, i3, i4);
                }
            }
        } finally {
            this.d.readLock().unlock();
        }
    }
}
