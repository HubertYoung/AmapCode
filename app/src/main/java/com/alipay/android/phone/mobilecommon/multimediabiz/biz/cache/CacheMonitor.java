package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache;

import android.content.SharedPreferences;
import android.os.FileObserver;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.DirConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.monitor.CacheDirStatistics;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CacheMonitor {
    private static final CacheMonitor b = new CacheMonitor();
    private Logger a = Logger.getLogger((String) "CacheMonitor");
    private Set<File> c = new HashSet();
    private final Set<FileObserver> d = new HashSet();
    private boolean e = false;
    private SharedPreferences f = AppUtils.getApplicationContext().getSharedPreferences("CacheMonitor", 0);
    private long g = 0;
    private CacheDirStatistics h = new CacheDirStatistics();

    private CacheMonitor() {
    }

    private void a() {
        this.c.add(new File(DirConstants.getDiskCacheDir(), ".nomedia"));
        this.c.add(new File(DirConstants.getMaterialCache(), ".nomedia"));
    }

    public static CacheMonitor get() {
        return b;
    }

    public void startMonitor() {
        synchronized (this) {
            if (!this.e) {
                this.e = true;
                TaskScheduleManager.get().schedule(new Runnable() {
                    public void run() {
                        CacheMonitor.this.b();
                    }
                }, 1000);
            }
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        this.a.d("checkAndStart", new Object[0]);
        a();
        c();
    }

    private void c() {
        Set deletedLockFiles = new HashSet();
        for (File lockFile : this.c) {
            long lastModifiedTime = this.f.getLong(lockFile.getAbsolutePath(), -1);
            this.a.d("checkLockFile file: " + lockFile + ", last: " + lastModifiedTime, new Object[0]);
            if (lastModifiedTime == -1) {
                a(lockFile);
            } else if (!lockFile.exists() || lastModifiedTime < lockFile.lastModified()) {
                deletedLockFiles.add(lockFile);
                a(lockFile);
            }
        }
        a(deletedLockFiles);
    }

    private void a(File lockFile) {
        this.a.d("createAndRecordLockFile file: " + lockFile + ", exists: " + lockFile.exists(), new Object[0]);
        lockFile.getParentFile().mkdirs();
        if (!lockFile.exists()) {
            try {
                lockFile.createNewFile();
            } catch (IOException e2) {
                this.a.e(e2, "checkLockFile fail, " + lockFile, new Object[0]);
            }
        }
        if (lockFile.exists()) {
            this.f.edit().putLong(lockFile.getAbsolutePath(), lockFile.lastModified()).apply();
        }
    }

    public void stopMonitor() {
        synchronized (this.d) {
            for (FileObserver stopWatching : this.d) {
                stopWatching.stopWatching();
            }
        }
    }

    public void doCheckInBackground() {
        this.a.d("doCheckInBackground", new Object[0]);
        c();
    }

    private boolean a(Set<File> deletedFiles) {
        if (System.currentTimeMillis() - this.g <= 20000) {
            return false;
        }
        this.a.d("report, set: " + deletedFiles, new Object[0]);
        this.g = System.currentTimeMillis();
        for (File f2 : deletedFiles) {
            this.h.dirSet.add(f2.getParentFile().getName());
        }
        this.h.submitRemoteAsync();
        this.h = new CacheDirStatistics();
        return true;
    }
}
