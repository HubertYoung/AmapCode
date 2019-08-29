package com.alipay.mobile.common.logging.appender;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LogEvent;
import com.alipay.mobile.common.logging.util.FileUtil;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class ExternalFileAppender extends FileAppender {
    protected static final int e = 2;
    protected static final Comparator<File> f = new a();
    protected File g;
    protected long h;
    protected File i;
    protected File j;
    protected File k;
    protected long l;
    protected long m;
    protected long n;
    protected StringBuilder o = new StringBuilder(this.p);
    protected int p;
    protected boolean q;

    public ExternalFileAppender(LogContext logContext, String logCategory, long fileRollTime, long expiredTime, long expiredSize, int eventBufferLength) {
        super(logContext, logCategory);
        this.l = fileRollTime;
        this.m = expiredTime;
        this.n = expiredSize;
        this.p = eventBufferLength / 2;
    }

    /* access modifiers changed from: protected */
    public final synchronized void a(LogEvent logEvent) {
        if (logEvent != null) {
            String content = logEvent.toString();
            if (!TextUtils.isEmpty(content)) {
                if (this.o.length() + content.length() + e > this.p) {
                    e();
                    if (this.o.length() + content.length() + e > this.p) {
                        try {
                            byte[] bytes = (content + "$$").getBytes("UTF-8");
                            a(bytes, bytes.length);
                        } catch (Throwable e2) {
                            if (!this.q) {
                                this.q = true;
                                Log.e("Appender", "appendLogEvent", e2);
                            }
                        }
                    } else {
                        a(content);
                    }
                } else {
                    a(content);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final synchronized void a() {
        if (this.o.length() != 0) {
            Log.v("Appender", this.b + " appender flush: " + this.o.length());
            e();
        }
    }

    /* access modifiers changed from: protected */
    public final synchronized void a(boolean isBackupOthers) {
        if (LoggingUtil.isOfflineForExternalFile()) {
            Log.i("Appender", "backupCurrentFile: need not backup in offline");
        } else {
            Log.i("Appender", "backupCurrentFile: need to backup, isBackupOthers=" + isBackupOthers);
            if (this.g == null || !this.g.exists() || !this.g.isFile() || this.g.length() == 0) {
                Log.e("Appender", "backupCurrentFile: no target log file");
            } else {
                try {
                    File backupFile = new File(h(), this.g.getName());
                    if (this.g.length() > 0) {
                        this.g.length();
                        backupFile.length();
                    }
                } catch (Throwable t) {
                    Log.e("Appender", "backupFileCore: " + this.g.getPath(), t);
                }
            }
            if (isBackupOthers) {
                f();
            }
        }
        return;
    }

    /* access modifiers changed from: protected */
    public final File c() {
        this.h = (System.currentTimeMillis() / this.l) * this.l;
        StringBuilder currentLogNameBuf = new StringBuilder();
        currentLogNameBuf.append(this.h).append("_").append(this.d);
        if (LoggingUtil.isOfflineForExternalFile()) {
            currentLogNameBuf.append("_dev");
        }
        String currentLogName = currentLogNameBuf.append(".2nd").toString();
        if (this.g == null || !this.g.exists() || !this.g.getName().equals(currentLogName)) {
            Log.i("Appender", "checkAndRollFile: " + currentLogName);
            File currentLogDir = g();
            if (currentLogDir == null) {
                Log.e("Appender", "currentLogDir is NULl with " + this.b);
                return null;
            }
            try {
                a(currentLogDir, this.m, this.n);
            } catch (Throwable t) {
                Log.w("Appender", "clean currentLogDir", t);
            }
            try {
                a(h(), 3 * this.m, 4 * this.n);
            } catch (Throwable t2) {
                Log.w("Appender", "clean backupLogsDir", t2);
            }
            try {
                a(i(), this.m, this.n);
            } catch (Throwable t3) {
                Log.w("Appender", "clean backupLogsDir_1", t3);
            }
            this.g = new File(currentLogDir, currentLogName);
            if (!LoggingUtil.isOfflineForExternalFile()) {
                new Thread(new b(this), this.b + "Extras").start();
            }
        }
        return this.g;
    }

    private void a(String data) {
        this.o.append(data).append("$$");
    }

    /* JADX INFO: finally extract failed */
    private boolean e() {
        try {
            byte[] bytes = this.o.toString().getBytes("UTF-8");
            boolean a = a(bytes, bytes.length);
            this.o.setLength(0);
            return a;
        } catch (Throwable th) {
            this.o.setLength(0);
            throw th;
        }
    }

    private static void a(File expiresDir, long expiresTime, long expiresSize) {
        if (expiresDir != null && expiresDir.isDirectory()) {
            File[] files = null;
            try {
                files = expiresDir.listFiles();
            } catch (Throwable t) {
                Log.w("Appender", "cleanExpiresFile", t);
            }
            if (files != null && files.length != 0) {
                long nowTime = System.currentTimeMillis();
                long effectiveStartTime = nowTime - expiresTime;
                long effectiveEndTime = nowTime + expiresTime;
                int length = files.length;
                for (int i2 = 0; i2 < length; i2++) {
                    File file = files[i2];
                    if (file != null && file.exists() && file.isFile()) {
                        try {
                            long timeStamp = Long.parseLong(file.getName().split("_")[0]);
                            if (timeStamp < effectiveStartTime || timeStamp > effectiveEndTime) {
                                file.delete();
                                Log.e("Appender", "cleanExpiresFile: " + file.getName() + " is too old !");
                            }
                        } catch (Throwable e2) {
                            Log.e("Appender", file.getName(), e2);
                        }
                    }
                }
                if (FileUtil.getFolderSize(expiresDir) >= expiresSize) {
                    File[] files2 = null;
                    try {
                        files2 = expiresDir.listFiles();
                    } catch (Throwable t2) {
                        Log.w("Appender", "cleanExpiresFile", t2);
                    }
                    if (files2 != null && files2.length >= 4) {
                        Arrays.sort(files2, f);
                        int len = files2.length / 4;
                        for (int i3 = 0; i3 < len; i3++) {
                            File file2 = files2[i3];
                            if (file2 != null && file2.exists() && file2.isFile()) {
                                try {
                                    file2.delete();
                                    Log.e("Appender", "cleanExpiresFile: " + file2.getName() + " is too large !");
                                } catch (Throwable e3) {
                                    Log.w("Appender", file2.getName() + " cleanExpiresFile", e3);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void f() {
        File[] listFiles;
        try {
            for (File curFile : g().listFiles()) {
                if (!a(curFile)) {
                    File bakFile = new File(h(), curFile.getName());
                    if (bakFile.isDirectory()) {
                        Log.e("Appender", "backupOtherFiles, bakFile should not be directory: " + bakFile);
                    } else if (!bakFile.exists() || !bakFile.isFile() || bakFile.length() != curFile.length()) {
                        Log.i("Appender", "backupOtherFiles: " + curFile);
                        FileUtil.copyFile(curFile, bakFile);
                    }
                }
            }
        } catch (Throwable t) {
            Log.e("Appender", "backupOtherFiles", t);
        }
    }

    private boolean a(File currentFile) {
        if (currentFile == null || !currentFile.exists() || !currentFile.isFile() || currentFile.length() == 0 || !currentFile.getName().contains(this.d)) {
            return true;
        }
        if (this.g != null && (currentFile.equals(this.g) || currentFile.getAbsolutePath().equals(this.g.getAbsolutePath()))) {
            return true;
        }
        try {
            if (Long.parseLong(currentFile.getName().split("_")[0]) < this.h) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public final void d() {
        Log.i("Appender", "handleExtrasOnGetNewFile, priority: " + Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(5);
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(20));
        f();
    }

    private File g() {
        if (this.i == null) {
            try {
                this.i = LoggingUtil.getStorageFilesDir(this.c, this.b);
            } catch (Throwable t) {
                Log.e("Appender", "getCurrentLogsDir", t);
            }
        }
        try {
            if (this.i != null && !this.i.exists()) {
                this.i.mkdirs();
            }
        } catch (Throwable t2) {
            Log.e("Appender", "getCurrentLogsDir", t2);
        }
        return this.i;
    }

    private File h() {
        if (this.j == null) {
            try {
                this.j = new File(new File(LoggingUtil.getCommonExternalStorageDir(), this.c.getPackageName()), this.b + LogItem.MM_C23_K4_IS_CANCEL);
            } catch (Throwable t) {
                Log.e("Appender", "getBackupLogsDir", t);
            }
        }
        try {
            if (this.j != null && !this.j.exists()) {
                this.j.mkdirs();
            }
        } catch (Throwable t2) {
            Log.e("Appender", "getBackupLogsDir", t2);
        }
        return this.j;
    }

    private File i() {
        if (this.k == null) {
            try {
                this.k = new File(new File(LoggingUtil.getCommonExternalStorageDir(), this.c.getPackageName()), this.b);
            } catch (Throwable t) {
                Log.e("Appender", "getBackupLogsDir_1", t);
            }
        }
        return this.k;
    }
}
