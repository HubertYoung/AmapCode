package com.autonavi.minimap.offline.utils.log;

import android.os.Environment;
import com.autonavi.amap.app.AMapAppGlobal;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class Log extends Logger {
    private static final String APP_TAG = "offline";
    private static final String LOG_ENTRY_FORMAT = "[%tF %tT]%s";
    private static final String LOG_FILE_NAME = "offlineLog.txt";
    private static PrintStream logStream;

    public Log(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public void debug(String str) {
        write(str, null);
    }

    /* access modifiers changed from: protected */
    public void error(String str) {
        write(str, null);
    }

    /* access modifiers changed from: protected */
    public void info(String str) {
        write(str, null);
    }

    /* access modifiers changed from: protected */
    public void warn(String str) {
        write(str, null);
    }

    /* access modifiers changed from: protected */
    public void debug(String str, Throwable th) {
        write(str, th);
    }

    /* access modifiers changed from: protected */
    public void error(String str, Throwable th) {
        write(str, th);
    }

    /* access modifiers changed from: protected */
    public void info(String str, Throwable th) {
        write(str, th);
    }

    /* access modifiers changed from: protected */
    public void warn(String str, Throwable th) {
        write(str, th);
    }

    private void write(String str, Throwable th) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(LogUtils.getLogPath(AMapAppGlobal.getApplication().getApplicationContext()));
            sb.append(LOG_FILE_NAME);
            String sb2 = sb.toString();
            if (logStream == null || !LogUtils.isExistFile(sb2)) {
                synchronized (Log.class) {
                    init();
                }
            }
            Date date = new Date();
            if (logStream != null) {
                Object[] objArr = {date, date, str};
            }
            if (th != null) {
                th.printStackTrace(logStream);
            }
        } catch (Throwable unused) {
        }
    }

    public static void init() {
        String str = null;
        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                str = LogUtils.getLogPath(AMapAppGlobal.getApplication().getApplicationContext());
                File file = new File(str);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            if (str != null) {
                File file2 = new File(str, LOG_FILE_NAME);
                new StringBuilder("Log to file : ").append(file2);
                logStream = new PrintStream(new FileOutputStream(file2, true), true);
            }
        } catch (Throwable unused) {
        }
    }

    /* access modifiers changed from: protected */
    @SuppressFBWarnings({"LI_LAZY_INIT_STATIC"})
    public void finalize() throws Throwable {
        try {
            super.finalize();
            if (logStream != null) {
                logStream.close();
                logStream = null;
            }
        } catch (Throwable unused) {
        }
    }
}
