package com.alipay.mobile.common.logging.appender;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LogEvent;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.strategy.LogStrategyManager;
import com.alipay.mobile.common.logging.util.FileUtil;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.tianyan.mobilesdk.TianyanLoggingStatus;
import java.io.File;

public class MdapFileAppender extends FileAppender {
    protected static final String e = ("mdap" + File.separatorChar + "upload");
    protected File f;
    protected int g;
    protected int h;
    protected boolean i = true;
    protected StringBuffer j = new StringBuffer();

    public MdapFileAppender(LogContext logContext, String logCategory) {
        super(logContext, logCategory);
    }

    /* access modifiers changed from: protected */
    public final synchronized void a(LogEvent logEvent) {
        TianyanLoggingStatus.acceptTimeTicksMadly();
        if (this.i) {
            this.i = false;
            try {
                String filecontent = FileUtil.readFile(c());
                if (!TextUtils.isEmpty(filecontent)) {
                    this.g = filecontent.split("\\$\\$").length;
                }
            } catch (Throwable e2) {
                Log.e("Appender", this.b + " first append: [just check, not a real error] " + e2);
            }
        }
        if (LoggingUtil.isOfflineMode() && this.a.getLogAppenderistener() != null) {
            long startTime = SystemClock.uptimeMillis();
            this.a.getLogAppenderistener().onLogAppend(logEvent);
            long spendTime = SystemClock.uptimeMillis() - startTime;
            if (spendTime > 1000) {
                LoggerFactory.getTraceLogger().error((String) "Appender", "\n\n\nexternal appender listener spend too much time: " + spendTime);
            }
        }
        this.j.append(logEvent);
        this.h++;
        if (!LoggerFactory.getProcessInfo().isMainProcess() || TianyanLoggingStatus.isMonitorBackground() || this.h >= 3 || LoggingUtil.isOfflineMode() || LogStrategyManager.getInstance().isLogUpload(this.b, this.h, this.a)) {
            a(this.j.toString(), LogStrategyManager.getInstance().needEncrypt(this.b));
            this.g += this.h;
            this.j.setLength(0);
            this.h = 0;
        }
        if (LogStrategyManager.getInstance().isLogUpload(this.b, this.g, this.a)) {
            Log.w("Appender", "upload: " + this.b);
            Bundle bundle = new Bundle();
            bundle.putString("event", "maxLogCount");
            a(null, bundle);
            this.g = 0;
        }
        return;
    }

    /* access modifiers changed from: protected */
    public final synchronized void a(Bundle bundle) {
        if (this.h > 0) {
            LoggerFactory.getTraceLogger().info("Appender", this.b + " appender flush: " + this.h);
        }
        if (this.j.length() == 0) {
            b(bundle);
        } else {
            a(this.j.toString(), LogStrategyManager.getInstance().needEncrypt(this.b));
            this.j.setLength(0);
            this.g += this.h;
            this.h = 0;
            b(bundle);
        }
    }

    private void b(Bundle bundle) {
        if (bundle != null && bundle.getBoolean(LogContext.NEED_MOVE, false)) {
            try {
                File file = c();
                if (file.exists()) {
                    FileUtil.moveFile(file, d());
                    LoggerFactory.getTraceLogger().info("Appender", this.b + " appender flush move " + this.g);
                    this.g = 0;
                }
            } catch (Throwable th) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final synchronized void a() {
        a((Bundle) null);
    }

    /* access modifiers changed from: protected */
    public final synchronized void a(boolean isBackupOthers) {
    }

    /* access modifiers changed from: protected */
    public final File c() {
        if (this.f == null && LoggingUtil.isOfflineMode()) {
            File mdapLogDir = null;
            try {
                mdapLogDir = this.c.getExternalFilesDir("mdap");
            } catch (Throwable t) {
                Log.e("Appender", "getFile", t);
            }
            if (mdapLogDir != null) {
                try {
                    if (!mdapLogDir.exists()) {
                        mdapLogDir.mkdirs();
                    }
                    this.f = new File(mdapLogDir, this.d + "_" + this.b);
                } catch (Throwable t2) {
                    Log.e("Appender", "getFile", t2);
                }
            }
        }
        if (this.f == null) {
            File mdapLogDir2 = new File(this.c.getFilesDir(), "mdap");
            try {
                if (!mdapLogDir2.exists()) {
                    mdapLogDir2.mkdirs();
                }
            } catch (Throwable t3) {
                Log.e("Appender", "getFile", t3);
            }
            this.f = new File(mdapLogDir2, this.d + "_" + this.b);
        }
        return this.f;
    }

    private File d() {
        File uploadDir = new File(this.c.getFilesDir(), e);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        return new File(uploadDir, LoggingUtil.getMdapStyleName(c().getName()));
    }

    private File e() {
        File backupDir = new File(this.c.getExternalFilesDir("mdap"), "upload");
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }
        return new File(backupDir, LoggingUtil.getMdapStyleName(c().getName()));
    }

    /* access modifiers changed from: protected */
    public final void a(String uploadUrl, Bundle bundle) {
        if (this.g != 0) {
            try {
                if (LoggingUtil.isOfflineMode()) {
                    try {
                        FileUtil.copyFile(c(), e());
                    } catch (Throwable th) {
                    }
                }
                try {
                    FileUtil.moveFile(c(), d());
                } catch (Throwable th2) {
                }
                this.g = 0;
                this.a.upload(this.b, uploadUrl, bundle);
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().error("Appender", this.b, e2);
            }
        }
    }
}
