package com.uc.crashsdk.export;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.webkit.ValueCallback;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base;
import com.uc.crashsdk.CrashLogFilesUploader;
import com.uc.crashsdk.JNIBridge;
import com.uc.crashsdk.a;
import com.uc.crashsdk.a.c;
import com.uc.crashsdk.a.h;
import com.uc.crashsdk.b;
import com.uc.crashsdk.d;
import com.uc.crashsdk.e;
import com.uc.crashsdk.f;
import com.uc.crashsdk.k;
import com.uc.crashsdk.p;
import com.uc.crashsdk.r;
import java.util.Iterator;
import java.util.concurrent.Callable;

/* compiled from: ProGuard */
public class CrashApi {
    private static CrashApi a = null;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;
    private boolean e = false;
    private final Object f = new Object();

    public static synchronized CrashApi createInstance(Context context, CustomInfo customInfo, VersionInfo versionInfo, ICrashClient iCrashClient, String str, boolean z, boolean z2, boolean z3) {
        CrashApi crashApi;
        synchronized (CrashApi.class) {
            try {
                if (a == null) {
                    a = new CrashApi(context, customInfo, versionInfo, iCrashClient, str, z, z2, z3);
                }
                crashApi = a;
            }
        }
        return crashApi;
    }

    public static CrashApi createInstance(Context context, CustomInfo customInfo, VersionInfo versionInfo, ICrashClient iCrashClient, String str) {
        return createInstance(context, customInfo, versionInfo, iCrashClient, str, true, true, true);
    }

    public static CrashApi createInstance(Context context, CustomInfo customInfo, VersionInfo versionInfo, String str) {
        return createInstance(context, customInfo, versionInfo, (ICrashClient) null, str);
    }

    public static CrashApi createInstance(Context context, String str, Bundle bundle) {
        return createInstance(context, str, bundle, (ValueCallback<Bundle>) null, (ValueCallback<Bundle>) null);
    }

    public static CrashApi createInstance(Context context, String str, Bundle bundle, ValueCallback<Bundle> valueCallback, ValueCallback<Bundle> valueCallback2) {
        if (bundle == null) {
            throw new NullPointerException();
        }
        Context context2 = context;
        createInstance(context2, p.a(bundle), p.b(bundle), null, str, bundle.getBoolean("enableJavaLog", true), bundle.getBoolean("enableNaiveLog", true), bundle.getBoolean("enableUnexpLog", true));
        if (valueCallback != null) {
            d.c(valueCallback);
        }
        if (valueCallback2 != null) {
            d.a(valueCallback2);
        }
        String string = bundle.getString("soPathName");
        if (h.b(string) && f.c(string)) {
            a.crashSoLoaded();
        }
        return a;
    }

    public boolean registerCallback(int i, ValueCallback<Bundle> valueCallback) {
        if (valueCallback == null) {
            throw new NullPointerException();
        }
        switch (i) {
            case 1:
                return d.a(valueCallback);
            case 2:
                return d.b(valueCallback);
            case 3:
                return d.c(valueCallback);
            default:
                throw new IllegalArgumentException("Unknown event type: " + i);
        }
    }

    public int updateCustomInfo(CustomInfo customInfo) {
        if (customInfo != null) {
            return p.b(customInfo);
        }
        throw new NullPointerException();
    }

    public int updateCustomInfo(Bundle bundle) {
        if (bundle != null) {
            return updateCustomInfo(p.a(bundle));
        }
        throw new NullPointerException();
    }

    public static CrashApi getInstance() {
        return a;
    }

    public void crashSoLoaded() {
        this.d = true;
        synchronized (this.f) {
            if (this.b && this.d) {
                if (!b.b) {
                    b();
                    JNIBridge.nativeInstallBreakpad();
                    b.b = true;
                    JNIBridge.nativeBreakpadInited(Build.FINGERPRINT);
                    p.d();
                } else if (p.G()) {
                    c.c("Has enabled native log!");
                }
            }
        }
        a();
        a.h();
    }

    public void updateVersionInfo(VersionInfo versionInfo) {
        if (versionInfo == null) {
            throw new NullPointerException();
        }
        p.a(versionInfo);
    }

    public void updateVersionInfo(Bundle bundle) {
        if (bundle == null) {
            throw new NullPointerException();
        }
        updateVersionInfo(p.b(bundle));
    }

    public void disableLog(int i) {
        synchronized (this.f) {
            b.a(i);
            if (LogType.isForJava(i) && b.a) {
                f.l();
                b.a = false;
            }
            if (LogType.isForNative(i)) {
                if (b.b) {
                    JNIBridge.nativeUninstallBreakpad();
                    b.b = false;
                } else {
                    this.b = false;
                }
            }
            if (LogType.isForUnexp(i)) {
                if (!b.c) {
                    this.c = false;
                } else if (f.o()) {
                    b.c = false;
                }
            }
        }
    }

    public void setMainProcess() {
        b.r();
        if (b.d) {
            JNIBridge.nativeSetProcessType(b.s());
        }
        enableUnexpCrashStat();
        a.h();
        try {
            f.p();
        } catch (Throwable th) {
            com.uc.crashsdk.a.a.a(th, true);
        }
    }

    public void enableUnexpCrashStat() {
        b.p();
        b.j();
        k.c();
        a();
        com.uc.crashsdk.a.k.b();
    }

    public boolean addStatInfo(String str, String str2) {
        if (h.a(str)) {
            throw new NullPointerException();
        } else if (str.length() > 24) {
            throw new IllegalArgumentException("key is too long!");
        } else {
            if (str2 != null && str2.length() > 512) {
                str2 = str2.substring(0, 512);
            }
            return com.uc.crashsdk.a.k.a(str, str2);
        }
    }

    public void setCrashLogUploadUrl(String str) {
        f.a(str, false);
    }

    public String getCrashLogUploadUrl() {
        return f.f();
    }

    public int getLastExitType() {
        return b.u();
    }

    public int reportCrashStats(boolean z) {
        if (z) {
            boolean a2 = k.a(b.c());
            com.uc.crashsdk.a.k.c();
            if (a2) {
                return 1;
            }
            return 0;
        }
        int a3 = k.a();
        com.uc.crashsdk.a.k.c();
        return a3;
    }

    public int resetCrashStats(boolean z) {
        if (!z) {
            return k.b();
        }
        if (k.b(b.c())) {
            return 1;
        }
        return 0;
    }

    public void onExit() {
        b.a(false);
    }

    public void setNewInstall() {
        b.a(true);
    }

    public void setForeground(boolean z) {
        b.b(z);
    }

    public void uploadCrashLogs() {
        f.a(true);
    }

    public int registerThread(int i, String str) {
        return a.a(i, str);
    }

    public boolean generateCustomLog(CustomLogInfo customLogInfo) {
        StringBuilder sb;
        if (customLogInfo == null) {
            throw new NullPointerException();
        } else if (customLogInfo.mData == null || customLogInfo.mLogType == null) {
            throw new NullPointerException("mData or mLogType is null!");
        } else if (customLogInfo.mLogType.indexOf("_") >= 0 || customLogInfo.mLogType.indexOf(Token.SEPARATOR) >= 0) {
            throw new IllegalArgumentException("mLogType can not contain char '_' and ' '");
        } else {
            if (customLogInfo.mDumpTids == null || customLogInfo.mDumpTids.size() <= 0) {
                sb = null;
            } else {
                StringBuilder sb2 = new StringBuilder();
                Iterator<Integer> it = customLogInfo.mDumpTids.iterator();
                while (it.hasNext()) {
                    sb2.append(it.next().intValue());
                    sb2.append(Token.SEPARATOR);
                }
                sb = sb2;
            }
            return f.a(customLogInfo.mData, customLogInfo.mLogType, customLogInfo.mAddHeader, customLogInfo.mAddFooter, customLogInfo.mAddLogcat, customLogInfo.mAddThreadsDump, customLogInfo.mUploadNow, customLogInfo.mDumpFiles, customLogInfo.mCallbacks, customLogInfo.mCachedInfos, sb != null ? sb.toString() : null);
        }
    }

    public boolean generateCustomLog(StringBuffer stringBuffer, String str, Bundle bundle) {
        CustomLogInfo customLogInfo = new CustomLogInfo(stringBuffer, str);
        if (bundle != null) {
            customLogInfo.mAddHeader = bundle.getBoolean("mAddHeader", customLogInfo.mAddHeader);
            customLogInfo.mAddFooter = bundle.getBoolean("mAddFooter", customLogInfo.mAddFooter);
            customLogInfo.mAddLogcat = bundle.getBoolean("mAddLogcat", customLogInfo.mAddLogcat);
            customLogInfo.mUploadNow = bundle.getBoolean("mUploadNow", customLogInfo.mUploadNow);
            customLogInfo.mAddThreadsDump = bundle.getBoolean("mAddThreadsDump", customLogInfo.mAddThreadsDump);
            customLogInfo.mDumpFiles = bundle.getStringArrayList("mDumpFiles");
            customLogInfo.mCallbacks = bundle.getStringArrayList("mCallbacks");
            customLogInfo.mCachedInfos = bundle.getStringArrayList("mCachedInfos");
            customLogInfo.mDumpTids = bundle.getIntegerArrayList("mDumpTids");
        }
        return generateCustomLog(customLogInfo);
    }

    public void addHeaderInfo(String str, String str2) {
        if (str == null) {
            throw new NullPointerException();
        }
        a.a(str, str2);
    }

    public int createCachedInfo(String str, int i, int i2) {
        if (str == null) {
            throw new NullPointerException();
        } else if (i <= 0) {
            throw new IllegalArgumentException("capacity must > 0!");
        } else if ((i2 & LZMA_Base.kMatchMaxLen) == 0) {
            return 0;
        } else {
            return a.a(str, i, i2);
        }
    }

    public int addCachedInfo(String str, String str2) {
        if (str != null && str2 != null) {
            return a.b(str, str2);
        }
        throw new NullPointerException();
    }

    public int registerInfoCallback(String str, int i) {
        if (str == null) {
            throw new NullPointerException();
        } else if ((i & LZMA_Base.kMatchMaxLen) == 0) {
            return 0;
        } else {
            return a.a(str, i, (Callable<String>) null, 0);
        }
    }

    public int registerInfoCallback(String str, int i, Callable<String> callable) {
        if (str == null || callable == null) {
            throw new NullPointerException();
        } else if ((i & LZMA_Base.kMatchMaxLen) == 0) {
            return 0;
        } else {
            return a.a(str, i, callable, 0);
        }
    }

    public int addDumpFile(DumpFileInfo dumpFileInfo) {
        if (dumpFileInfo == null) {
            throw new NullPointerException();
        } else if (dumpFileInfo.mCategory == null || dumpFileInfo.mFileTobeDump == null) {
            throw new NullPointerException();
        } else if ((dumpFileInfo.mLogType & LZMA_Base.kMatchMaxLen) == 0) {
            return 0;
        } else {
            return a.a(dumpFileInfo.mCategory, dumpFileInfo.mFileTobeDump, dumpFileInfo.mIsEncrypted, dumpFileInfo.mWriteCategory, dumpFileInfo.mLogType, dumpFileInfo.mDeleteAfterDump);
        }
    }

    public int addDumpFile(String str, String str2, int i, Bundle bundle) {
        DumpFileInfo dumpFileInfo = new DumpFileInfo(str, str2, i);
        if (bundle != null) {
            dumpFileInfo.mIsEncrypted = bundle.getBoolean("mIsEncrypted", dumpFileInfo.mIsEncrypted);
            dumpFileInfo.mWriteCategory = bundle.getBoolean("mWriteCategory", dumpFileInfo.mWriteCategory);
            dumpFileInfo.mDeleteAfterDump = bundle.getBoolean("mDeleteAfterDump", dumpFileInfo.mDeleteAfterDump);
        }
        return addDumpFile(dumpFileInfo);
    }

    public Throwable getUncaughtException() {
        return f.m();
    }

    public boolean updateUnexpInfo() {
        return a.a(true);
    }

    private CrashApi(Context context, CustomInfo customInfo, VersionInfo versionInfo, ICrashClient iCrashClient, String str, boolean z, boolean z2, boolean z3) {
        if (customInfo == null || versionInfo == null) {
            throw new NullPointerException();
        }
        p.a(customInfo);
        this.b = z2;
        this.c = z3;
        try {
            f.a(str, true);
            e.a(context);
            p.a(customInfo, versionInfo);
            d.a(iCrashClient);
            f.i();
            a.a = context.getPackageName();
        } catch (Throwable th) {
            a(th);
        }
        if (z) {
            try {
                if (!b.a) {
                    f.k();
                    f.h();
                    b.a = true;
                } else if (p.G()) {
                    c.c("Has enabled java log!");
                }
            } catch (Throwable th2) {
                a(th2);
            }
        }
        try {
            r.a();
            com.uc.crashsdk.a.k.a();
            com.uc.crashsdk.a.f.a();
        } catch (Throwable th3) {
            com.uc.crashsdk.a.a.a(th3, false);
        }
        try {
            if (p.q() && !this.e) {
                CrashLogFilesUploader.a(context);
                this.e = true;
            }
        } catch (Throwable th4) {
            com.uc.crashsdk.a.a.a(th4, true);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a() {
        /*
            r2 = this;
            java.lang.Object r1 = r2.f
            monitor-enter(r1)
            boolean r0 = r2.c     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x0011
            boolean r0 = r2.d     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x0011
            boolean r0 = com.uc.crashsdk.b.q()     // Catch:{ all -> 0x0029 }
            if (r0 != 0) goto L_0x0013
        L_0x0011:
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
        L_0x0012:
            return
        L_0x0013:
            boolean r0 = com.uc.crashsdk.b.c     // Catch:{ all -> 0x0029 }
            if (r0 != 0) goto L_0x0027
            boolean r0 = com.uc.crashsdk.b.d     // Catch:{ all -> 0x0029 }
            if (r0 != 0) goto L_0x0021
            b()     // Catch:{ all -> 0x0029 }
            com.uc.crashsdk.p.d()     // Catch:{ all -> 0x0029 }
        L_0x0021:
            com.uc.crashsdk.f.n()     // Catch:{ all -> 0x0029 }
            r0 = 1
            com.uc.crashsdk.b.c = r0     // Catch:{ all -> 0x0029 }
        L_0x0027:
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
            goto L_0x0012
        L_0x0029:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.export.CrashApi.a():void");
    }

    private static void b() {
        if (!b.d) {
            p.b();
            JNIBridge.nativeInitNative();
            p.c();
            b.d = true;
        }
    }

    private static void a(Throwable th) {
        new f().a(Thread.currentThread(), th, true);
    }
}
