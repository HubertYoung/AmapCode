package com.autonavi.minimap.bundle.apm.internal.report;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Keep;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

@Keep
public class ReportManager {
    public static final String LOG_PATH = "log";
    public static final String TAG = "ReportManager";
    /* access modifiers changed from: private */
    public static volatile byte initState = -1;
    public static long session;
    private volatile boolean isInited;

    static final class a {
        /* access modifiers changed from: private */
        public static final ReportManager a = new ReportManager();
    }

    /* access modifiers changed from: private */
    public native void appendBytesBody(short s, long j, byte[] bArr);

    /* access modifiers changed from: private */
    public native void appendNoBody(short s, long j);

    /* access modifiers changed from: private */
    public native void appendStringBody(short s, long j, String str);

    /* access modifiers changed from: private */
    public native boolean init(String str, String str2, HashMap<String, String> hashMap, HashMap<String, String> hashMap2, HashMap<String, String> hashMap3);

    private native void trim(String str, String str2);

    private ReportManager() {
        this.isInited = false;
    }

    public static final ReportManager getInstance() {
        return a.a;
    }

    public void append(final cuw cuw) {
        if (initState != 1) {
            runInReportThread(new Runnable() {
                public final void run() {
                    try {
                        if (cuw instanceof cuz) {
                            ((cuz) cuw).d();
                            if (!TextUtils.isEmpty(((cuz) cuw).c())) {
                                ReportManager.this.appendStringBody(cuw.b(), cuw.a(), ((cuz) cuw).c());
                            }
                        } else if (cuw instanceof cuy) {
                            if (((cuy) cuw).c() != null) {
                                ReportManager.this.appendBytesBody(cuw.b(), cuw.a(), ((cuy) cuw).c());
                            }
                        } else if (!(cuw instanceof cux)) {
                            ReportManager.this.appendNoBody(cuw.b(), cuw.a());
                        } else if (((cux) cuw).d() != null) {
                            cux cux = (cux) cuw;
                            if (cwa.a != null && cwa.a.size() > 0) {
                                Iterator<Object> it = cwa.a.iterator();
                                while (it.hasNext()) {
                                    it.next();
                                    cvc cvc = new cvc();
                                    cvc.a = cux.c();
                                    if (cux.e() != null) {
                                        cvc.b = "STACK";
                                    } else {
                                        cvc.b = "CONTENT";
                                    }
                                    cvc.c = cux.f();
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(cux.c());
                                    sb.append("_");
                                    sb.append(cux.a());
                                    cvc.d = sb.toString();
                                    cvc.e = cux.d();
                                    cvc.f = cux.e();
                                    cvc.g = null;
                                    cvc.h = "1.0.0.0";
                                    cvc.i = "arg1";
                                    cvc.j = "arg2";
                                    cvc.k = "arg3";
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } catch (Throwable th) {
                        cwl.e("native method not found.\n".concat(String.valueOf(th)), new Object[0]);
                    }
                }
            });
        }
    }

    private void runInReportThread(Runnable runnable) {
        if (Thread.currentThread() == defpackage.cvd.a.a.getThread()) {
            runnable.run();
        } else {
            defpackage.cvd.a.b.post(runnable);
        }
    }

    public void initSuperLog(final Application application, final HashMap<String, String> hashMap, final HashMap<String, String> hashMap2) {
        if (!this.isInited) {
            this.isInited = true;
            runInReportThread(new Runnable() {
                public final void run() {
                    if (ReportManager.initState != 1) {
                        HashMap<String, String> a2 = cwb.a();
                        String pathPrefix = ReportManager.getPathPrefix(application);
                        String pathCachPrefix = ReportManager.getPathCachPrefix(application);
                        ReportManager.session = System.currentTimeMillis();
                        ReportManager reportManager = ReportManager.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append(pathCachPrefix);
                        sb.append(File.separator);
                        sb.append(ReportManager.session);
                        String sb2 = sb.toString();
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(pathPrefix);
                        sb3.append(File.separator);
                        sb3.append(ReportManager.session);
                        if (reportManager.init(sb2, sb3.toString(), hashMap, hashMap2, a2)) {
                            ReportManager.initState = 0;
                        } else {
                            ReportManager.initState = 2;
                        }
                    }
                }
            });
        }
    }

    public void trimHotdataBeforeUpload(String str, String str2) {
        if (initState != 1 && !cwj.a(str) && !cwj.a(str2)) {
            trim(str, str2);
        }
    }

    public static String getPathPrefix(Context context) {
        String replace = cwg.a(context).replace(':', DjangoUtils.EXTENSION_SEPARATOR);
        if (TextUtils.isEmpty(replace)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(LOG_PATH);
        sb.append(File.separator);
        sb.append(replace);
        return cwe.a(context, sb.toString(), true);
    }

    public static String getPathCachPrefix(Context context) {
        String replace = cwg.a(context).replace(':', DjangoUtils.EXTENSION_SEPARATOR);
        if (TextUtils.isEmpty(replace)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(LOG_PATH);
        sb.append(File.separator);
        sb.append(replace);
        String sb2 = sb.toString();
        File dir = context.getDir("telescope", 0);
        if (dir == null) {
            return "";
        }
        File file = new File(dir.getAbsolutePath(), sb2);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    static {
        try {
            System.loadLibrary("superlog");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
