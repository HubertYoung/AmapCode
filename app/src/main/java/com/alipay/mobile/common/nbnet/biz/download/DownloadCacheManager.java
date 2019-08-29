package com.alipay.mobile.common.nbnet.biz.download;

import android.content.Context;
import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.nbnet.biz.GlobalNBNetContext;
import com.alipay.mobile.common.nbnet.biz.db.DownloadTaskDao;
import com.alipay.mobile.common.nbnet.biz.db.DownloadTaskModel;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.IOUtils;
import com.alipay.mobile.common.transport.utils.FileUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DownloadCacheManager {
    /* access modifiers changed from: private */
    public static final String a = DownloadCacheManager.class.getSimpleName();
    private static volatile DownloadCacheManager b;
    private static boolean f = false;
    private final Context c = GlobalNBNetContext.a().getApplicationContext();
    /* access modifiers changed from: private */
    public DownloadTaskDao d = new DownloadTaskDao(this.c);
    private long e = 0;

    private DownloadCacheManager() {
    }

    public static DownloadCacheManager a() {
        if (b != null) {
            return b;
        }
        synchronized (DownloadCacheManager.class) {
            try {
                DownloadCacheManager tmp = new DownloadCacheManager();
                if (b == null) {
                    b = tmp;
                }
            }
        }
        return b;
    }

    public final void b() {
        if (!f) {
            f = true;
            NetworkAsyncTaskExecutor.schedule((Runnable) new Runnable() {
                public void run() {
                    if (DownloadCacheManager.this.d != null) {
                        DownloadCacheManager.this.d.a(0);
                        NBNetLogCat.a(DownloadCacheManager.a, (String) "Init download module");
                    }
                }
            }, 4, TimeUnit.SECONDS);
        }
    }

    public final DownloadTaskDao c() {
        return this.d;
    }

    public final File a(int requestId) {
        File cacheDir = new File(this.c.getCacheDir(), "NBNetDownload");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return new File(cacheDir, Integer.toString(requestId));
    }

    public final void b(final int requestId) {
        NetworkAsyncTaskExecutor.executeIO(new Runnable() {
            public void run() {
                DownloadCacheManager.this.c(requestId);
            }
        });
    }

    public final void c(int requestId) {
        this.d.b(requestId);
        File cacheFile = a(requestId);
        if (cacheFile.exists()) {
            NBNetLogCat.a(a, "deleteCacheFile:" + cacheFile.getAbsolutePath());
            cacheFile.delete();
        }
    }

    private void e(final int exceptRequestId) {
        NetworkAsyncTaskExecutor.executeIO(new Runnable() {
            public void run() {
                DownloadCacheManager.this.d(exceptRequestId);
            }
        });
    }

    public final void d(int exceptRequestId) {
        if (System.currentTimeMillis() - this.e >= TimeUnit.DAYS.toMillis(1)) {
            this.e = System.currentTimeMillis();
            NBNetLogCat.a(a, (String) "cleanExpiredDownloadTask:");
            List expiredTasks = this.d.a();
            if (expiredTasks != null && expiredTasks.size() > 0) {
                for (DownloadTaskModel taskModel : expiredTasks) {
                    if (taskModel.requestId != exceptRequestId) {
                        c(taskModel.requestId);
                    }
                }
            }
        }
    }

    public final void a(int dataLength, int requestId) {
        e(requestId);
        File cacheDir = new File(this.c.getCacheDir(), "NBNetDownload");
        if (IOUtils.a(cacheDir) + ((long) dataLength) > 419430400) {
            List allTasks = this.d.b();
            if (allTasks != null) {
                for (int index = 0; index < allTasks.size() / 2; index++) {
                    DownloadTaskModel downloadTaskModel = allTasks.get(index);
                    if (downloadTaskModel.requestId != requestId) {
                        c(downloadTaskModel.requestId);
                    }
                }
            } else {
                return;
            }
        }
        if (!FileUtils.checkDataAvailableSpace(cacheDir, (long) dataLength)) {
            throw new NBNetException("Insufficient space on the disk, less then " + dataLength + " byte.  path: " + cacheDir.getAbsolutePath(), -19);
        }
    }
}
