package com.alipay.mobile.framework.service.common.impl;

import android.os.Bundle;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.cache.disk.lru.DefaultLruDiskCache;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.common.DiskCacheService;
import java.io.File;

public class DiskCacheServiceImpl extends DiskCacheService {
    private final DefaultLruDiskCache a = DefaultLruDiskCache.getInstance();

    public DiskCacheServiceImpl() {
        LoggerFactory.getTraceLogger().warn((String) "DiskCacheService", (String) "new DiskCacheServiceImpl");
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public String getDirectory() {
        return this.a.getDirectory();
    }

    public long getSize() {
        return this.a.getSize();
    }

    public long getMaxsize() {
        return this.a.getMaxsize();
    }

    public byte[] get(String owner, String url) {
        open();
        return this.a.get(owner, url);
    }

    public void removeByGroup(String group) {
        open();
        this.a.removeByGroup(group);
    }

    public void removeByOwner(String owner) {
        open();
        this.a.removeByOwner(owner);
    }

    public void remove(String url) {
        open();
        this.a.remove(url);
    }

    public void put(String owner, String group, String url, byte[] data, long createTime, long period, String contentType) {
        open();
        this.a.put(owner, group, url, data, createTime, period, contentType);
    }

    public void close() {
        this.a.close();
    }

    public void open() {
        this.a.open();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle params) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle params) {
        close();
    }

    /* access modifiers changed from: private */
    public static void a(File file) {
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (File a2 : childFiles) {
                a(a2);
            }
            file.delete();
        }
    }
}
