package com.autonavi.indoor.util;

import android.os.Environment;
import android.os.Process;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class L {
    public static boolean isLogging = false;
    public static final String logalgoPath;
    public static final String logcatPath;
    private static FileOutputStream mFileOutputStream = null;
    private static File mLogFile = null;
    static boolean mSave2File = false;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getPath());
        sb.append("/autonavi/indoor/logcat");
        logcatPath = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getPath());
        sb2.append("/autonavi/indoor/logalgo");
        logalgoPath = sb2.toString();
        onCreate();
    }

    private static void onCreate() {
        try {
            new Thread() {
                public final void run() {
                    L.isLogging = new File(L.logcatPath).exists();
                    L.mSave2File = new File(L.logcatPath).isDirectory();
                    StringBuilder sb = new StringBuilder("Log instrument onCreate success.isLogging:");
                    sb.append(L.isLogging);
                    sb.append(", mSave2File:");
                    sb.append(L.mSave2File);
                }
            }.start();
        } catch (Throwable unused) {
        }
    }

    public static void destroy() {
        try {
            mFileOutputStream.close();
            mFileOutputStream = null;
            mLogFile = null;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static boolean isSilent() {
        return !isLogging;
    }

    private static void d(String str, String str2) {
        if (mSave2File) {
            if (mLogFile == null) {
                createFile();
            }
            if (mFileOutputStream != null) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(new Date()));
                    sb.append("\tpid=");
                    sb.append(Process.myPid());
                    sb.append(" tid=");
                    sb.append(Thread.currentThread().getId());
                    sb.append("(");
                    sb.append(Thread.currentThread().getName());
                    sb.append(")\t");
                    sb.append(str2);
                    sb.append("\n");
                    mFileOutputStream.write(sb.toString().getBytes());
                    mFileOutputStream.flush();
                } catch (Throwable th) {
                    new StringBuilder("L:").append(th.toString());
                }
            }
        }
    }

    private static void createFile() {
        try {
            File file = new File(logcatPath);
            if (!file.exists()) {
                mSave2File = false;
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date()));
            sb.append(".txt");
            File file2 = new File(file, sb.toString());
            mLogFile = file2;
            if (file2.exists() || mLogFile.createNewFile()) {
                mFileOutputStream = new FileOutputStream(mLogFile, true);
            } else {
                mSave2File = false;
            }
        } catch (Throwable th) {
            mLogFile = null;
            mSave2File = false;
            new StringBuilder("L.createFile:").append(th.toString());
        }
    }

    public static void d(String str) {
        d(str, 2);
    }

    public static void d(Object obj) {
        d(String.valueOf(obj), 2);
    }

    private static void d(String str, int i) {
        String str2;
        if (!isSilent()) {
            StackTraceElement[] stackTrace = new Error().getStackTrace();
            if (stackTrace.length != 0) {
                if (i >= stackTrace.length) {
                    StringBuilder sb = new StringBuilder("getStackTrace() is not enough. n:");
                    sb.append(i);
                    sb.append(", length:");
                    sb.append(stackTrace.length);
                    return;
                }
                String stackTraceElement = stackTrace[i].toString();
                try {
                    String substring = stackTraceElement.substring(0, stackTraceElement.lastIndexOf("("));
                    int lastIndexOf = substring.lastIndexOf("$");
                    if (lastIndexOf < 0) {
                        String substring2 = substring.substring(0, substring.lastIndexOf(""));
                        str2 = stackTraceElement.replace(substring2.substring(0, substring2.lastIndexOf("")), "");
                    } else {
                        str2 = stackTraceElement.substring(lastIndexOf);
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append("\t[");
                    sb2.append(str2);
                    sb2.append("]");
                    d((String) "Locating", sb2.toString());
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }
    }

    public static void d(byte[] bArr) {
        if (!isSilent()) {
            if (bArr == null || bArr.length == 0) {
                d((String) "buf is null", 2);
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (byte b : bArr) {
                sb.append(String.format("%02X", new Object[]{Integer.valueOf(b & 255)}));
            }
            StringBuilder sb2 = new StringBuilder("[");
            sb2.append(bArr.length);
            sb2.append("]:");
            sb2.append(sb);
            d(sb2.toString(), 2);
        }
    }

    public static void d(String str, byte[] bArr) {
        if (!isSilent()) {
            if (bArr == null || bArr.length == 0) {
                d((String) "buf is null", 2);
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (byte b : bArr) {
                sb.append(String.format("%02X", new Object[]{Integer.valueOf(b & 255)}));
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("[");
            sb2.append(bArr.length);
            sb2.append("]:");
            sb2.append(sb);
            d(sb2.toString(), 2);
        }
    }

    public static void logStackTrace(String str) {
        if (!isSilent()) {
            d(str, 2);
            d("Caller ".concat(String.valueOf(str)), 3);
        }
    }

    public static void logStackTrace() {
        if (!isSilent()) {
            new Error().printStackTrace();
        }
    }

    public static void d(Throwable th) {
        if (!isSilent()) {
            d(th.getMessage(), 2);
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            d(stringWriter.toString(), 2);
        }
    }
}
