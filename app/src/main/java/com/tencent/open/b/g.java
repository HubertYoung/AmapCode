package com.tencent.open.b;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.sdk.packet.d;
import com.taobao.accs.common.Constants;
import com.tencent.open.a.f;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.OpenConfig;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.ThreadManager;
import com.tencent.open.utils.Util;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class g {
    protected static g a;
    protected Random b = new Random();
    protected List<Serializable> c = Collections.synchronizedList(new ArrayList());
    protected List<Serializable> d = Collections.synchronizedList(new ArrayList());
    protected HandlerThread e = null;
    protected Handler f;
    protected Executor g = ThreadManager.newSerialExecutor();
    protected Executor h = ThreadManager.newSerialExecutor();

    public static synchronized g a() {
        g gVar;
        synchronized (g.class) {
            try {
                if (a == null) {
                    a = new g();
                }
                gVar = a;
            }
        }
        return gVar;
    }

    private g() {
        if (this.e == null) {
            this.e = new HandlerThread("opensdk.report.handlerthread", 10);
            this.e.start();
        }
        if (this.e.isAlive() && this.e.getLooper() != null) {
            this.f = new Handler(this.e.getLooper()) {
                public void handleMessage(Message message) {
                    switch (message.what) {
                        case 1000:
                            g.this.b();
                            break;
                        case 1001:
                            g.this.e();
                            break;
                    }
                    super.handleMessage(message);
                }
            };
        }
    }

    public void a(final Bundle bundle, String str, final boolean z) {
        if (bundle != null) {
            StringBuilder sb = new StringBuilder("-->reportVia, bundle: ");
            sb.append(bundle.toString());
            f.a("openSDK_LOG.ReportManager", sb.toString());
            if (a((String) "report_via", str) || z) {
                this.g.execute(new Runnable() {
                    public void run() {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putString("uin", "1000");
                            bundle.putString(Constants.KEY_IMEI, c.b(Global.getContext()));
                            bundle.putString(Constants.KEY_IMSI, c.c(Global.getContext()));
                            bundle.putString("android_id", c.d(Global.getContext()));
                            bundle.putString("mac", c.a());
                            bundle.putString("platform", "1");
                            bundle.putString("os_ver", VERSION.RELEASE);
                            bundle.putString("position", Util.getLocation(Global.getContext()));
                            bundle.putString("network", a.a(Global.getContext()));
                            bundle.putString("language", c.b());
                            bundle.putString(CaptureParam.CAPTURE_PICTURE_SIZE, c.a(Global.getContext()));
                            bundle.putString(CommonUtils.APN_PROP_APN, a.b(Global.getContext()));
                            bundle.putString("model_name", Build.MODEL);
                            bundle.putString("timezone", TimeZone.getDefault().getID());
                            bundle.putString("sdk_ver", com.tencent.connect.common.Constants.SDK_VERSION);
                            bundle.putString("qz_ver", Util.getAppVersionName(Global.getContext(), com.tencent.connect.common.Constants.PACKAGE_QZONE));
                            bundle.putString("qq_ver", Util.getVersionName(Global.getContext(), "com.tencent.mobileqq"));
                            bundle.putString("qua", Util.getQUA3(Global.getContext(), Global.getPackageName()));
                            bundle.putString("packagename", Global.getPackageName());
                            bundle.putString("app_ver", Util.getAppVersionName(Global.getContext(), Global.getPackageName()));
                            if (bundle != null) {
                                bundle.putAll(bundle);
                            }
                            g.this.d.add(new b(bundle));
                            int size = g.this.d.size();
                            int i = OpenConfig.getInstance(Global.getContext(), null).getInt("Agent_ReportTimeInterval");
                            if (i == 0) {
                                i = 10000;
                            }
                            if (!g.this.a((String) "report_via", size)) {
                                if (!z) {
                                    if (!g.this.f.hasMessages(1001)) {
                                        Message obtain = Message.obtain();
                                        obtain.what = 1001;
                                        g.this.f.sendMessageDelayed(obtain, (long) i);
                                    }
                                    return;
                                }
                            }
                            g.this.e();
                            g.this.f.removeMessages(1001);
                        } catch (Exception e) {
                            f.b("openSDK_LOG.ReportManager", "--> reporVia, exception in sub thread.", e);
                        }
                    }
                });
            }
        }
    }

    public void a(String str, long j, long j2, long j3, int i) {
        a(str, j, j2, j3, i, "", false);
    }

    public void a(String str, long j, long j2, long j3, int i, String str2, boolean z) {
        StringBuilder sb = new StringBuilder("-->reportCgi, command: ");
        final String str3 = str;
        sb.append(str3);
        sb.append(" | startTime: ");
        final long j4 = j;
        sb.append(j4);
        sb.append(" | reqSize:");
        final long j5 = j2;
        sb.append(j5);
        sb.append(" | rspSize: ");
        final long j6 = j3;
        sb.append(j6);
        sb.append(" | responseCode: ");
        final int i2 = i;
        sb.append(i2);
        sb.append(" | detail: ");
        final String str4 = str2;
        sb.append(str4);
        f.a("openSDK_LOG.ReportManager", sb.toString());
        if (a((String) "report_cgi", String.valueOf(i)) || z) {
            Executor executor = this.h;
            final boolean z2 = z;
            AnonymousClass3 r0 = new Runnable() {
                public void run() {
                    try {
                        long elapsedRealtime = SystemClock.elapsedRealtime() - j4;
                        Bundle bundle = new Bundle();
                        String a2 = a.a(Global.getContext());
                        bundle.putString(CommonUtils.APN_PROP_APN, a2);
                        bundle.putString("appid", "1000067");
                        bundle.putString("commandid", str3);
                        bundle.putString("detail", str4);
                        StringBuilder sb = new StringBuilder();
                        sb.append("network=");
                        sb.append(a2);
                        sb.append('&');
                        sb.append("sdcard=");
                        sb.append(Environment.getExternalStorageState().equals("mounted") ? 1 : 0);
                        sb.append('&');
                        sb.append("wifi=");
                        sb.append(a.e(Global.getContext()));
                        bundle.putString("deviceInfo", sb.toString());
                        int i = 100;
                        int a3 = 100 / g.this.a(i2);
                        if (a3 <= 0) {
                            i = 1;
                        } else if (a3 <= 100) {
                            i = a3;
                        }
                        bundle.putString("frequency", String.valueOf(i));
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(j5);
                        bundle.putString("reqSize", sb2.toString());
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(i2);
                        bundle.putString("resultCode", sb3.toString());
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(j6);
                        bundle.putString("rspSize", sb4.toString());
                        bundle.putString("timeCost", String.valueOf(elapsedRealtime));
                        bundle.putString("uin", "1000");
                        g.this.c.add(new b(bundle));
                        int size = g.this.c.size();
                        int i2 = OpenConfig.getInstance(Global.getContext(), null).getInt("Agent_ReportTimeInterval");
                        if (i2 == 0) {
                            i2 = 10000;
                        }
                        if (!g.this.a((String) "report_cgi", size)) {
                            if (!z2) {
                                if (!g.this.f.hasMessages(1000)) {
                                    Message obtain = Message.obtain();
                                    obtain.what = 1000;
                                    g.this.f.sendMessageDelayed(obtain, (long) i2);
                                }
                                return;
                            }
                        }
                        g.this.b();
                        g.this.f.removeMessages(1000);
                    } catch (Exception e2) {
                        f.b("openSDK_LOG.ReportManager", "--> reportCGI, exception in sub thread.", e2);
                    }
                }
            };
            executor.execute(r0);
        }
    }

    /* access modifiers changed from: protected */
    public void b() {
        this.h.execute(new Runnable() {
            /*  JADX ERROR: IF instruction can be used only in fallback mode
                jadx.core.utils.exceptions.CodegenException: IF instruction can be used only in fallback mode
                	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:571)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:477)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:242)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:205)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:311)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:68)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:315)
                	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:261)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:224)
                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:664)
                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:598)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:356)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:776)
                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:716)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:360)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:242)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:315)
                	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:261)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:224)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:109)
                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:75)
                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:32)
                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:20)
                	at jadx.core.ProcessClass.process(ProcessClass.java:36)
                	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
                	at jadx.api.JavaClass.decompile(JavaClass.java:62)
                	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
                */
            public void run() {
                /*
                    r10 = this;
                    com.tencent.open.b.g r0 = com.tencent.open.b.g.this     // Catch:{ Exception -> 0x00ba }
                    android.os.Bundle r0 = r0.c()     // Catch:{ Exception -> 0x00ba }
                    if (r0 != 0) goto L_0x0009     // Catch:{ Exception -> 0x00ba }
                    return     // Catch:{ Exception -> 0x00ba }
                L_0x0009:
                    android.content.Context r1 = com.tencent.open.utils.Global.getContext()     // Catch:{ Exception -> 0x00ba }
                    r2 = 0     // Catch:{ Exception -> 0x00ba }
                    com.tencent.open.utils.OpenConfig r1 = com.tencent.open.utils.OpenConfig.getInstance(r1, r2)     // Catch:{ Exception -> 0x00ba }
                    java.lang.String r3 = "Common_HttpRetryCount"     // Catch:{ Exception -> 0x00ba }
                    int r1 = r1.getInt(r3)     // Catch:{ Exception -> 0x00ba }
                    if (r1 != 0) goto L_0x001b     // Catch:{ Exception -> 0x00ba }
                    r1 = 3     // Catch:{ Exception -> 0x00ba }
                L_0x001b:
                    java.lang.String r3 = "openSDK_LOG.ReportManager"     // Catch:{ Exception -> 0x00ba }
                    java.lang.String r4 = "-->doReportCgi, retryCount: "     // Catch:{ Exception -> 0x00ba }
                    java.lang.String r5 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x00ba }
                    java.lang.String r4 = r4.concat(r5)     // Catch:{ Exception -> 0x00ba }
                    com.tencent.open.a.f.b(r3, r4)     // Catch:{ Exception -> 0x00ba }
                    r3 = 0
                    r4 = 0
                L_0x002c:
                    r5 = 1
                    int r4 = r4 + r5
                    android.content.Context r6 = com.tencent.open.utils.Global.getContext()     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    java.lang.String r7 = "http://wspeed.qq.com/w.cgi"     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    org.apache.http.client.HttpClient r6 = com.tencent.open.utils.HttpUtils.getHttpClient(r6, r2, r7)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    org.apache.http.client.methods.HttpPost r7 = new org.apache.http.client.methods.HttpPost     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    java.lang.String r8 = "http://wspeed.qq.com/w.cgi"     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    r7.<init>(r8)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    java.lang.String r8 = "Accept-Encoding"     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    java.lang.String r9 = "gzip"     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    r7.addHeader(r8, r9)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    java.lang.String r8 = "Content-Type"     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    java.lang.String r9 = "application/x-www-form-urlencoded"     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    r7.setHeader(r8, r9)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    java.lang.String r8 = com.tencent.open.utils.HttpUtils.encodeUrl(r0)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    byte[] r8 = com.tencent.open.utils.Util.getBytesUTF8(r8)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    org.apache.http.entity.ByteArrayEntity r9 = new org.apache.http.entity.ByteArrayEntity     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    r9.<init>(r8)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    r7.setEntity(r9)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    org.apache.http.HttpResponse r6 = r6.execute(r7)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    org.apache.http.StatusLine r6 = r6.getStatusLine()     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    int r6 = r6.getStatusCode()     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    java.lang.String r7 = "openSDK_LOG.ReportManager"     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    java.lang.String r8 = "-->doReportCgi, statusCode: "     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    java.lang.String r9 = java.lang.String.valueOf(r6)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    java.lang.String r8 = r8.concat(r9)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    com.tencent.open.a.f.b(r7, r8)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    r7 = 200(0xc8, float:2.8E-43)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    if (r6 != r7) goto L_0x00a3     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    com.tencent.open.b.f r6 = com.tencent.open.b.f.a()     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    java.lang.String r7 = "report_cgi"     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    r6.b(r7)     // Catch:{ ConnectTimeoutException -> 0x0099, SocketTimeoutException -> 0x0090, Exception -> 0x0087 }
                    r3 = 1
                    goto L_0x00a3
                L_0x0087:
                    r0 = move-exception
                    java.lang.String r1 = "openSDK_LOG.ReportManager"
                    java.lang.String r2 = "-->doReportCgi, doupload exception"
                    com.tencent.open.a.f.b(r1, r2, r0)     // Catch:{ Exception -> 0x00ba }
                    goto L_0x00a3     // Catch:{ Exception -> 0x00ba }
                L_0x0090:
                    r5 = move-exception     // Catch:{ Exception -> 0x00ba }
                    java.lang.String r6 = "openSDK_LOG.ReportManager"     // Catch:{ Exception -> 0x00ba }
                    java.lang.String r7 = "-->doReportCgi, doupload exception"     // Catch:{ Exception -> 0x00ba }
                    com.tencent.open.a.f.b(r6, r7, r5)     // Catch:{ Exception -> 0x00ba }
                    goto L_0x00a1     // Catch:{ Exception -> 0x00ba }
                L_0x0099:
                    r5 = move-exception     // Catch:{ Exception -> 0x00ba }
                    java.lang.String r6 = "openSDK_LOG.ReportManager"     // Catch:{ Exception -> 0x00ba }
                    java.lang.String r7 = "-->doReportCgi, doupload exception"     // Catch:{ Exception -> 0x00ba }
                    com.tencent.open.a.f.b(r6, r7, r5)     // Catch:{ Exception -> 0x00ba }
                L_0x00a1:
                    if (r4 < r1) goto L_0x002c     // Catch:{ Exception -> 0x00ba }
                L_0x00a3:
                    if (r3 != 0) goto L_0x00b2     // Catch:{ Exception -> 0x00ba }
                    com.tencent.open.b.f r0 = com.tencent.open.b.f.a()     // Catch:{ Exception -> 0x00ba }
                    java.lang.String r1 = "report_cgi"     // Catch:{ Exception -> 0x00ba }
                    com.tencent.open.b.g r2 = com.tencent.open.b.g.this     // Catch:{ Exception -> 0x00ba }
                    java.util.List<java.io.Serializable> r2 = r2.c     // Catch:{ Exception -> 0x00ba }
                    r0.a(r1, r2)     // Catch:{ Exception -> 0x00ba }
                L_0x00b2:
                    com.tencent.open.b.g r0 = com.tencent.open.b.g.this     // Catch:{ Exception -> 0x00ba }
                    java.util.List<java.io.Serializable> r0 = r0.c     // Catch:{ Exception -> 0x00ba }
                    r0.clear()     // Catch:{ Exception -> 0x00ba }
                    return
                L_0x00ba:
                    r0 = move-exception
                    java.lang.String r1 = "openSDK_LOG.ReportManager"
                    java.lang.String r2 = "-->doReportCgi, doupload exception out."
                    com.tencent.open.a.f.b(r1, r2, r0)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.b.g.AnonymousClass4.run():void");
            }
        });
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0052, code lost:
        if (r4.b.nextInt(100) < r5) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x003c, code lost:
        if (r4.b.nextInt(100) < r5) goto L_0x0054;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r0 = "openSDK_LOG.ReportManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "-->availableFrequency, report: "
            r1.<init>(r2)
            r1.append(r5)
            java.lang.String r2 = " | ext: "
            r1.append(r2)
            r1.append(r6)
            java.lang.String r1 = r1.toString()
            com.tencent.open.a.f.b(r0, r1)
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            r1 = 0
            if (r0 == 0) goto L_0x0023
            return r1
        L_0x0023:
            java.lang.String r0 = "report_cgi"
            boolean r0 = r5.equals(r0)
            r2 = 1
            r3 = 100
            if (r0 == 0) goto L_0x0040
            int r5 = java.lang.Integer.parseInt(r6)     // Catch:{ Exception -> 0x003f }
            int r5 = r4.a(r5)
            java.util.Random r6 = r4.b
            int r6 = r6.nextInt(r3)
            if (r6 >= r5) goto L_0x0058
            goto L_0x0054
        L_0x003f:
            return r1
        L_0x0040:
            java.lang.String r0 = "report_via"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0056
            int r5 = com.tencent.open.b.e.a(r6)
            java.util.Random r6 = r4.b
            int r6 = r6.nextInt(r3)
            if (r6 >= r5) goto L_0x0058
        L_0x0054:
            r1 = 1
            goto L_0x0058
        L_0x0056:
            r5 = 100
        L_0x0058:
            java.lang.String r6 = "openSDK_LOG.ReportManager"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "-->availableFrequency, result: "
            r0.<init>(r2)
            r0.append(r1)
            java.lang.String r2 = " | frequency: "
            r0.append(r2)
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            com.tencent.open.a.f.b(r6, r5)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.b.g.a(java.lang.String, java.lang.String):boolean");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0019, code lost:
        if (r0 == 0) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0033, code lost:
        if (r0 == 0) goto L_0x001b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x005e A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(java.lang.String r6, int r7) {
        /*
            r5 = this;
            java.lang.String r0 = "report_cgi"
            boolean r0 = r6.equals(r0)
            r1 = 5
            r2 = 0
            r3 = 0
            if (r0 == 0) goto L_0x001d
            android.content.Context r0 = com.tencent.open.utils.Global.getContext()
            com.tencent.open.utils.OpenConfig r0 = com.tencent.open.utils.OpenConfig.getInstance(r0, r2)
            java.lang.String r2 = "Common_CGIReportMaxcount"
            int r0 = r0.getInt(r2)
            if (r0 != 0) goto L_0x0037
        L_0x001b:
            r0 = 5
            goto L_0x0037
        L_0x001d:
            java.lang.String r0 = "report_via"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0036
            android.content.Context r0 = com.tencent.open.utils.Global.getContext()
            com.tencent.open.utils.OpenConfig r0 = com.tencent.open.utils.OpenConfig.getInstance(r0, r2)
            java.lang.String r2 = "Agent_ReportBatchCount"
            int r0 = r0.getInt(r2)
            if (r0 != 0) goto L_0x0037
            goto L_0x001b
        L_0x0036:
            r0 = 0
        L_0x0037:
            java.lang.String r1 = "openSDK_LOG.ReportManager"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "-->availableCount, report: "
            r2.<init>(r4)
            r2.append(r6)
            java.lang.String r6 = " | dataSize: "
            r2.append(r6)
            r2.append(r7)
            java.lang.String r6 = " | maxcount: "
            r2.append(r6)
            r2.append(r0)
            java.lang.String r6 = r2.toString()
            com.tencent.open.a.f.b(r1, r6)
            if (r7 < r0) goto L_0x005e
            r6 = 1
            return r6
        L_0x005e:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.b.g.a(java.lang.String, int):boolean");
    }

    /* access modifiers changed from: protected */
    public int a(int i) {
        if (i == 0) {
            int i2 = OpenConfig.getInstance(Global.getContext(), null).getInt("Common_CGIReportFrequencySuccess");
            if (i2 == 0) {
                return 10;
            }
            return i2;
        }
        int i3 = OpenConfig.getInstance(Global.getContext(), null).getInt("Common_CGIReportFrequencyFailed");
        if (i3 == 0) {
            return 100;
        }
        return i3;
    }

    /* access modifiers changed from: protected */
    public Bundle c() {
        if (this.c.size() == 0) {
            return null;
        }
        b bVar = (b) this.c.get(0);
        if (bVar == null) {
            f.b("openSDK_LOG.ReportManager", "-->prepareCgiData, the 0th cgireportitem is null.");
            return null;
        }
        String str = bVar.a.get("appid");
        List<Serializable> a2 = f.a().a("report_cgi");
        if (a2 != null) {
            this.c.addAll(a2);
        }
        StringBuilder sb = new StringBuilder("-->prepareCgiData, mCgiList size: ");
        sb.append(this.c.size());
        f.b("openSDK_LOG.ReportManager", sb.toString());
        if (this.c.size() == 0) {
            return null;
        }
        Bundle bundle = new Bundle();
        try {
            bundle.putString("appid", str);
            bundle.putString("releaseversion", com.tencent.connect.common.Constants.SDK_VERSION_REPORT);
            bundle.putString(d.n, Build.DEVICE);
            bundle.putString("qua", com.tencent.connect.common.Constants.SDK_QUA);
            bundle.putString("key", "apn,frequency,commandid,resultcode,tmcost,reqsize,rspsize,detail,touin,deviceinfo");
            for (int i = 0; i < this.c.size(); i++) {
                b bVar2 = (b) this.c.get(i);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(i);
                sb2.append("_1");
                bundle.putString(sb2.toString(), bVar2.a.get(CommonUtils.APN_PROP_APN));
                StringBuilder sb3 = new StringBuilder();
                sb3.append(i);
                sb3.append("_2");
                bundle.putString(sb3.toString(), bVar2.a.get("frequency"));
                StringBuilder sb4 = new StringBuilder();
                sb4.append(i);
                sb4.append("_3");
                bundle.putString(sb4.toString(), bVar2.a.get("commandid"));
                StringBuilder sb5 = new StringBuilder();
                sb5.append(i);
                sb5.append("_4");
                bundle.putString(sb5.toString(), bVar2.a.get("resultCode"));
                StringBuilder sb6 = new StringBuilder();
                sb6.append(i);
                sb6.append("_5");
                bundle.putString(sb6.toString(), bVar2.a.get("timeCost"));
                StringBuilder sb7 = new StringBuilder();
                sb7.append(i);
                sb7.append("_6");
                bundle.putString(sb7.toString(), bVar2.a.get("reqSize"));
                StringBuilder sb8 = new StringBuilder();
                sb8.append(i);
                sb8.append("_7");
                bundle.putString(sb8.toString(), bVar2.a.get("rspSize"));
                StringBuilder sb9 = new StringBuilder();
                sb9.append(i);
                sb9.append("_8");
                bundle.putString(sb9.toString(), bVar2.a.get("detail"));
                StringBuilder sb10 = new StringBuilder();
                sb10.append(i);
                sb10.append("_9");
                bundle.putString(sb10.toString(), bVar2.a.get("uin"));
                StringBuilder sb11 = new StringBuilder();
                sb11.append(c.e(Global.getContext()));
                sb11.append("&");
                sb11.append(bVar2.a.get("deviceInfo"));
                String sb12 = sb11.toString();
                StringBuilder sb13 = new StringBuilder();
                sb13.append(i);
                sb13.append("_10");
                bundle.putString(sb13.toString(), sb12);
            }
            StringBuilder sb14 = new StringBuilder("-->prepareCgiData, end. params: ");
            sb14.append(bundle.toString());
            f.a("openSDK_LOG.ReportManager", sb14.toString());
            return bundle;
        } catch (Exception e2) {
            f.b("openSDK_LOG.ReportManager", "-->prepareCgiData, exception.", e2);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public Bundle d() {
        List<Serializable> a2 = f.a().a("report_via");
        if (a2 != null) {
            this.d.addAll(a2);
        }
        StringBuilder sb = new StringBuilder("-->prepareViaData, mViaList size: ");
        sb.append(this.d.size());
        f.b("openSDK_LOG.ReportManager", sb.toString());
        if (this.d.size() == 0) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<Serializable> it = this.d.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = new JSONObject();
            b bVar = (b) it.next();
            for (String next : bVar.a.keySet()) {
                try {
                    String str = bVar.a.get(next);
                    if (str == null) {
                        str = "";
                    }
                    jSONObject.put(next, str);
                } catch (JSONException e2) {
                    f.b("openSDK_LOG.ReportManager", "-->prepareViaData, put bundle to json array exception", e2);
                }
            }
            jSONArray.put(jSONObject);
        }
        StringBuilder sb2 = new StringBuilder("-->prepareViaData, JSONArray array: ");
        sb2.append(jSONArray.toString());
        f.a("openSDK_LOG.ReportManager", sb2.toString());
        Bundle bundle = new Bundle();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("data", jSONArray);
            bundle.putString("data", jSONObject2.toString());
            return bundle;
        } catch (JSONException e3) {
            f.b("openSDK_LOG.ReportManager", "-->prepareViaData, put bundle to json array exception", e3);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void e() {
        this.g.execute(new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:13:0x004e, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:14:0x004f, code lost:
                r2 = r0;
                r18 = r4;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:15:0x0053, code lost:
                r8 = -4;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:28:0x0070, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:29:0x0071, code lost:
                r2 = r0;
                r10 = r4;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:30:0x0074, code lost:
                r0 = e;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:32:0x0078, code lost:
                r18 = r4;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:34:0x007a, code lost:
                r6 = r3;
                r4 = r18;
                r10 = 0;
                r12 = 0;
                r14 = -6;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:36:0x0085, code lost:
                r0 = e;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:37:0x0086, code lost:
                r18 = r4;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:41:0x008d, code lost:
                r14 = com.tencent.open.utils.HttpUtils.getErrorCodeFromException(r0);
                r4 = r18;
                r10 = 0;
                r12 = 0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:42:0x0095, code lost:
                r0 = e;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:43:0x0096, code lost:
                r18 = r4;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
                r1.a.d.clear();
                com.tencent.open.a.f.b("openSDK_LOG.ReportManager", "doReportVia, NetworkUnavailableException.");
             */
            /* JADX WARNING: Code restructure failed: missing block: B:53:0x00c7, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:54:0x00c8, code lost:
                r18 = r4;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ca, code lost:
                r4 = r18;
                r10 = 0;
                r12 = 0;
                r14 = -4;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:58:0x00d2, code lost:
                r4 = android.os.SystemClock.elapsedRealtime();
                r10 = 0;
                r12 = 0;
                r14 = -8;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:60:0x00dd, code lost:
                r4 = android.os.SystemClock.elapsedRealtime();
                r10 = 0;
                r12 = 0;
                r14 = -7;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:74:0x00e7, code lost:
                continue;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:76:0x00e7, code lost:
                continue;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:77:0x00e7, code lost:
                continue;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:78:0x00e7, code lost:
                continue;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:79:0x00e7, code lost:
                continue;
             */
            /* JADX WARNING: Failed to process nested try/catch */
            /* JADX WARNING: Missing exception handler attribute for start block: B:51:0x00b9 */
            /* JADX WARNING: Removed duplicated region for block: B:30:0x0074 A[ExcHandler: IOException (e java.io.IOException), Splitter:B:23:0x0066] */
            /* JADX WARNING: Removed duplicated region for block: B:33:? A[ExcHandler: Exception (unused java.lang.Exception), SYNTHETIC, Splitter:B:8:0x0035] */
            /* JADX WARNING: Removed duplicated region for block: B:35:? A[ExcHandler: Exception (unused java.lang.Exception), SYNTHETIC, Splitter:B:23:0x0066] */
            /* JADX WARNING: Removed duplicated region for block: B:36:0x0085 A[ExcHandler: IOException (e java.io.IOException), Splitter:B:8:0x0035] */
            /* JADX WARNING: Removed duplicated region for block: B:51:0x00b9 A[SYNTHETIC, Splitter:B:51:0x00b9] */
            /* JADX WARNING: Removed duplicated region for block: B:57:? A[ExcHandler: JSONException (unused org.json.JSONException), SYNTHETIC, Splitter:B:23:0x0066] */
            /* JADX WARNING: Removed duplicated region for block: B:59:? A[ExcHandler: SocketTimeoutException (unused java.net.SocketTimeoutException), PHI: r6 r9 
              PHI: (r6v5 int) = (r6v2 int), (r6v2 int), (r6v9 int), (r6v9 int), (r6v9 int), (r6v9 int), (r6v2 int) binds: [B:8:0x0035, B:11:0x0049, B:23:0x0066, B:24:?, B:25:0x0068, B:26:?, B:18:0x0056] A[DONT_GENERATE, DONT_INLINE]
              PHI: (r9v5 boolean) = (r9v1 boolean), (r9v1 boolean), (r9v11 boolean), (r9v11 boolean), (r9v11 boolean), (r9v11 boolean), (r9v1 boolean) binds: [B:8:0x0035, B:11:0x0049, B:23:0x0066, B:24:?, B:25:0x0068, B:26:?, B:18:0x0056] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC, Splitter:B:8:0x0035] */
            /* JADX WARNING: Removed duplicated region for block: B:61:? A[ExcHandler: ConnectTimeoutException (unused org.apache.http.conn.ConnectTimeoutException), PHI: r6 r9 
              PHI: (r6v4 int) = (r6v2 int), (r6v2 int), (r6v9 int), (r6v9 int), (r6v9 int), (r6v9 int), (r6v2 int) binds: [B:8:0x0035, B:11:0x0049, B:23:0x0066, B:24:?, B:25:0x0068, B:26:?, B:18:0x0056] A[DONT_GENERATE, DONT_INLINE]
              PHI: (r9v4 boolean) = (r9v1 boolean), (r9v1 boolean), (r9v11 boolean), (r9v11 boolean), (r9v11 boolean), (r9v11 boolean), (r9v1 boolean) binds: [B:8:0x0035, B:11:0x0049, B:23:0x0066, B:24:?, B:25:0x0068, B:26:?, B:18:0x0056] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC, Splitter:B:8:0x0035] */
            /* JADX WARNING: Removed duplicated region for block: B:66:0x0100 A[Catch:{ Exception -> 0x012e }] */
            /* JADX WARNING: Removed duplicated region for block: B:67:0x010a A[Catch:{ Exception -> 0x012e }] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r28 = this;
                    r1 = r28
                    com.tencent.open.b.g r2 = com.tencent.open.b.g.this     // Catch:{ Exception -> 0x012e }
                    android.os.Bundle r2 = r2.d()     // Catch:{ Exception -> 0x012e }
                    if (r2 != 0) goto L_0x000b
                    return
                L_0x000b:
                    java.lang.String r3 = "openSDK_LOG.ReportManager"
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x012e }
                    java.lang.String r5 = "-->doReportVia, params: "
                    r4.<init>(r5)     // Catch:{ Exception -> 0x012e }
                    java.lang.String r5 = r2.toString()     // Catch:{ Exception -> 0x012e }
                    r4.append(r5)     // Catch:{ Exception -> 0x012e }
                    java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x012e }
                    com.tencent.open.a.f.a(r3, r4)     // Catch:{ Exception -> 0x012e }
                    int r3 = com.tencent.open.b.e.a()     // Catch:{ Exception -> 0x012e }
                    long r4 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x012e }
                    r6 = 0
                    r9 = 0
                    r10 = 0
                    r12 = 0
                    r14 = 0
                L_0x0031:
                    r15 = 1
                    int r6 = r6 + r15
                    r16 = -4
                    android.content.Context r7 = com.tencent.open.utils.Global.getContext()     // Catch:{ ConnectTimeoutException -> 0x00dd, SocketTimeoutException -> 0x00d2, JSONException -> 0x00c8, NetworkUnavailableException -> 0x00b9, HttpStatusException -> 0x0095, IOException -> 0x0085, Exception -> 0x0078 }
                    java.lang.String r8 = "http://appsupport.qq.com/cgi-bin/appstage/mstats_batch_report"
                    java.lang.String r15 = "POST"
                    com.tencent.open.utils.Util$Statistic r7 = com.tencent.open.utils.HttpUtils.openUrl2(r7, r8, r15, r2)     // Catch:{ ConnectTimeoutException -> 0x00dd, SocketTimeoutException -> 0x00d2, JSONException -> 0x00c8, NetworkUnavailableException -> 0x00b9, HttpStatusException -> 0x0095, IOException -> 0x0085, Exception -> 0x0078 }
                    java.lang.String r8 = r7.response     // Catch:{ ConnectTimeoutException -> 0x00dd, SocketTimeoutException -> 0x00d2, JSONException -> 0x00c8, NetworkUnavailableException -> 0x00b9, HttpStatusException -> 0x0095, IOException -> 0x0085, Exception -> 0x0078 }
                    org.json.JSONObject r8 = com.tencent.open.utils.Util.parseJson(r8)     // Catch:{ ConnectTimeoutException -> 0x00dd, SocketTimeoutException -> 0x00d2, JSONException -> 0x00c8, NetworkUnavailableException -> 0x00b9, HttpStatusException -> 0x0095, IOException -> 0x0085, Exception -> 0x0078 }
                    java.lang.String r15 = "ret"
                    int r8 = r8.getInt(r15)     // Catch:{ JSONException -> 0x0053, ConnectTimeoutException -> 0x00dd, SocketTimeoutException -> 0x00d2, NetworkUnavailableException -> 0x00b9, HttpStatusException -> 0x004e, IOException -> 0x0085, Exception -> 0x0078 }
                    goto L_0x0054
                L_0x004e:
                    r0 = move-exception
                    r2 = r0
                    r18 = r4
                    goto L_0x0099
                L_0x0053:
                    r8 = -4
                L_0x0054:
                    if (r8 == 0) goto L_0x0062
                    java.lang.String r8 = r7.response     // Catch:{ ConnectTimeoutException -> 0x00dd, SocketTimeoutException -> 0x00d2, JSONException -> 0x00c8, NetworkUnavailableException -> 0x00b9, HttpStatusException -> 0x004e, IOException -> 0x0085, Exception -> 0x0078 }
                    boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ ConnectTimeoutException -> 0x00dd, SocketTimeoutException -> 0x00d2, JSONException -> 0x00c8, NetworkUnavailableException -> 0x00b9, HttpStatusException -> 0x004e, IOException -> 0x0085, Exception -> 0x0078 }
                    if (r8 != 0) goto L_0x005f
                    goto L_0x0062
                L_0x005f:
                    r18 = r4
                    goto L_0x0066
                L_0x0062:
                    r6 = r3
                    r18 = r4
                    r9 = 1
                L_0x0066:
                    long r4 = r7.reqSize     // Catch:{ ConnectTimeoutException -> 0x00dd, SocketTimeoutException -> 0x00d2, JSONException -> 0x00ca, NetworkUnavailableException -> 0x00b9, HttpStatusException -> 0x0076, IOException -> 0x0074, Exception -> 0x007a }
                    long r7 = r7.rspSize     // Catch:{ ConnectTimeoutException -> 0x00dd, SocketTimeoutException -> 0x00d2, JSONException -> 0x00ca, NetworkUnavailableException -> 0x00b9, HttpStatusException -> 0x0070, IOException -> 0x0074, Exception -> 0x007a }
                    r10 = r4
                    r12 = r7
                    r4 = r18
                    goto L_0x00e7
                L_0x0070:
                    r0 = move-exception
                    r2 = r0
                    r10 = r4
                    goto L_0x0099
                L_0x0074:
                    r0 = move-exception
                    goto L_0x0088
                L_0x0076:
                    r0 = move-exception
                    goto L_0x0098
                L_0x0078:
                    r18 = r4
                L_0x007a:
                    r4 = -6
                    r6 = r3
                    r4 = r18
                    r10 = 0
                    r12 = 0
                    r14 = -6
                    goto L_0x00e7
                L_0x0085:
                    r0 = move-exception
                    r18 = r4
                L_0x0088:
                    r4 = r0
                    int r4 = com.tencent.open.utils.HttpUtils.getErrorCodeFromException(r4)     // Catch:{ Exception -> 0x012e }
                    r14 = r4
                    r4 = r18
                    r10 = 0
                    r12 = 0
                    goto L_0x00e7
                L_0x0095:
                    r0 = move-exception
                    r18 = r4
                L_0x0098:
                    r2 = r0
                L_0x0099:
                    java.lang.String r2 = r2.getMessage()     // Catch:{ Exception -> 0x00b0 }
                    java.lang.String r3 = "http status code error:"
                    java.lang.String r4 = ""
                    java.lang.String r2 = r2.replace(r3, r4)     // Catch:{ Exception -> 0x00b0 }
                    int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ Exception -> 0x00b0 }
                    r25 = r2
                    r21 = r10
                    r23 = r12
                    goto L_0x00b6
                L_0x00b0:
                    r21 = r10
                    r23 = r12
                    r25 = r14
                L_0x00b6:
                    r19 = r18
                    goto L_0x00f1
                L_0x00b9:
                    com.tencent.open.b.g r2 = com.tencent.open.b.g.this     // Catch:{ Exception -> 0x012e }
                    java.util.List<java.io.Serializable> r2 = r2.d     // Catch:{ Exception -> 0x012e }
                    r2.clear()     // Catch:{ Exception -> 0x012e }
                    java.lang.String r2 = "openSDK_LOG.ReportManager"
                    java.lang.String r3 = "doReportVia, NetworkUnavailableException."
                    com.tencent.open.a.f.b(r2, r3)     // Catch:{ Exception -> 0x012e }
                    return
                L_0x00c8:
                    r18 = r4
                L_0x00ca:
                    r4 = r18
                    r10 = 0
                    r12 = 0
                    r14 = -4
                    goto L_0x00e7
                L_0x00d2:
                    long r4 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x012e }
                    r7 = -8
                    r10 = 0
                    r12 = 0
                    r14 = -8
                    goto L_0x00e7
                L_0x00dd:
                    long r4 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x012e }
                    r7 = -7
                    r10 = 0
                    r12 = 0
                    r14 = -7
                L_0x00e7:
                    if (r6 < r3) goto L_0x0031
                    r19 = r4
                    r21 = r10
                    r23 = r12
                    r25 = r14
                L_0x00f1:
                    com.tencent.open.b.g r2 = com.tencent.open.b.g.this     // Catch:{ Exception -> 0x012e }
                    java.lang.String r18 = "mapp_apptrace_sdk"
                    r26 = 0
                    r27 = 0
                    r17 = r2
                    r17.a(r18, r19, r21, r23, r25, r26, r27)     // Catch:{ Exception -> 0x012e }
                    if (r9 == 0) goto L_0x010a
                    com.tencent.open.b.f r2 = com.tencent.open.b.f.a()     // Catch:{ Exception -> 0x012e }
                    java.lang.String r3 = "report_via"
                    r2.b(r3)     // Catch:{ Exception -> 0x012e }
                    goto L_0x0117
                L_0x010a:
                    com.tencent.open.b.f r2 = com.tencent.open.b.f.a()     // Catch:{ Exception -> 0x012e }
                    java.lang.String r3 = "report_via"
                    com.tencent.open.b.g r4 = com.tencent.open.b.g.this     // Catch:{ Exception -> 0x012e }
                    java.util.List<java.io.Serializable> r4 = r4.d     // Catch:{ Exception -> 0x012e }
                    r2.a(r3, r4)     // Catch:{ Exception -> 0x012e }
                L_0x0117:
                    com.tencent.open.b.g r2 = com.tencent.open.b.g.this     // Catch:{ Exception -> 0x012e }
                    java.util.List<java.io.Serializable> r2 = r2.d     // Catch:{ Exception -> 0x012e }
                    r2.clear()     // Catch:{ Exception -> 0x012e }
                    java.lang.String r2 = "openSDK_LOG.ReportManager"
                    java.lang.String r3 = "-->doReportVia, uploadSuccess: "
                    java.lang.String r4 = java.lang.String.valueOf(r9)     // Catch:{ Exception -> 0x012e }
                    java.lang.String r3 = r3.concat(r4)     // Catch:{ Exception -> 0x012e }
                    com.tencent.open.a.f.b(r2, r3)     // Catch:{ Exception -> 0x012e }
                    return
                L_0x012e:
                    r0 = move-exception
                    r2 = r0
                    java.lang.String r3 = "openSDK_LOG.ReportManager"
                    java.lang.String r4 = "-->doReportVia, exception in serial executor."
                    com.tencent.open.a.f.b(r3, r4, r2)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.b.g.AnonymousClass5.run():void");
            }
        });
    }

    public void a(String str, String str2, Bundle bundle, boolean z) {
        final Bundle bundle2 = bundle;
        final String str3 = str;
        final boolean z2 = z;
        final String str4 = str2;
        AnonymousClass6 r0 = new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:44:0x00e2 A[Catch:{ Exception -> 0x00fa }] */
            /* JADX WARNING: Removed duplicated region for block: B:46:0x00ea A[Catch:{ Exception -> 0x00fa }] */
            /* JADX WARNING: Removed duplicated region for block: B:55:0x00e0 A[EDGE_INSN: B:55:0x00e0->B:43:0x00e0 ?: BREAK  , SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r10 = this;
                    android.os.Bundle r0 = r2     // Catch:{ Exception -> 0x00fa }
                    if (r0 != 0) goto L_0x000c
                    java.lang.String r0 = "openSDK_LOG.ReportManager"
                    java.lang.String r1 = "-->httpRequest, params is null!"
                    com.tencent.open.a.f.e(r0, r1)     // Catch:{ Exception -> 0x00fa }
                    return
                L_0x000c:
                    int r0 = com.tencent.open.b.e.a()     // Catch:{ Exception -> 0x00fa }
                    if (r0 != 0) goto L_0x0013
                    r0 = 3
                L_0x0013:
                    java.lang.String r1 = "openSDK_LOG.ReportManager"
                    java.lang.String r2 = "-->httpRequest, retryCount: "
                    java.lang.String r3 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x00fa }
                    java.lang.String r2 = r2.concat(r3)     // Catch:{ Exception -> 0x00fa }
                    com.tencent.open.a.f.b(r1, r2)     // Catch:{ Exception -> 0x00fa }
                    android.content.Context r1 = com.tencent.open.utils.Global.getContext()     // Catch:{ Exception -> 0x00fa }
                    r2 = 0
                    java.lang.String r3 = r3     // Catch:{ Exception -> 0x00fa }
                    org.apache.http.client.HttpClient r1 = com.tencent.open.utils.HttpUtils.getHttpClient(r1, r2, r3)     // Catch:{ Exception -> 0x00fa }
                    android.os.Bundle r2 = r2     // Catch:{ Exception -> 0x00fa }
                    java.lang.String r2 = com.tencent.open.utils.HttpUtils.encodeUrl(r2)     // Catch:{ Exception -> 0x00fa }
                    boolean r3 = r4     // Catch:{ Exception -> 0x00fa }
                    if (r3 == 0) goto L_0x003b
                    java.lang.String r2 = java.net.URLEncoder.encode(r2)     // Catch:{ Exception -> 0x00fa }
                L_0x003b:
                    java.lang.String r3 = r5     // Catch:{ Exception -> 0x00fa }
                    java.lang.String r3 = r3.toUpperCase()     // Catch:{ Exception -> 0x00fa }
                    java.lang.String r4 = "GET"
                    boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x00fa }
                    if (r3 == 0) goto L_0x005d
                    java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x00fa }
                    java.lang.String r4 = r3     // Catch:{ Exception -> 0x00fa }
                    r3.<init>(r4)     // Catch:{ Exception -> 0x00fa }
                    r3.append(r2)     // Catch:{ Exception -> 0x00fa }
                    org.apache.http.client.methods.HttpGet r2 = new org.apache.http.client.methods.HttpGet     // Catch:{ Exception -> 0x00fa }
                    java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00fa }
                    r2.<init>(r3)     // Catch:{ Exception -> 0x00fa }
                    goto L_0x007f
                L_0x005d:
                    java.lang.String r3 = r5     // Catch:{ Exception -> 0x00fa }
                    java.lang.String r3 = r3.toUpperCase()     // Catch:{ Exception -> 0x00fa }
                    java.lang.String r4 = "POST"
                    boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x00fa }
                    if (r3 == 0) goto L_0x00f2
                    org.apache.http.client.methods.HttpPost r3 = new org.apache.http.client.methods.HttpPost     // Catch:{ Exception -> 0x00fa }
                    java.lang.String r4 = r3     // Catch:{ Exception -> 0x00fa }
                    r3.<init>(r4)     // Catch:{ Exception -> 0x00fa }
                    byte[] r2 = com.tencent.open.utils.Util.getBytesUTF8(r2)     // Catch:{ Exception -> 0x00fa }
                    org.apache.http.entity.ByteArrayEntity r4 = new org.apache.http.entity.ByteArrayEntity     // Catch:{ Exception -> 0x00fa }
                    r4.<init>(r2)     // Catch:{ Exception -> 0x00fa }
                    r3.setEntity(r4)     // Catch:{ Exception -> 0x00fa }
                    r2 = r3
                L_0x007f:
                    java.lang.String r3 = "Accept-Encoding"
                    java.lang.String r4 = "gzip"
                    r2.addHeader(r3, r4)     // Catch:{ Exception -> 0x00fa }
                    java.lang.String r3 = "Content-Type"
                    java.lang.String r4 = "application/x-www-form-urlencoded"
                    r2.addHeader(r3, r4)     // Catch:{ Exception -> 0x00fa }
                    r3 = 0
                    r4 = 0
                L_0x008f:
                    r5 = 1
                    int r3 = r3 + r5
                    org.apache.http.HttpResponse r6 = r1.execute(r2)     // Catch:{ ConnectTimeoutException -> 0x00d7, SocketTimeoutException -> 0x00cf, Exception -> 0x00c7 }
                    org.apache.http.StatusLine r6 = r6.getStatusLine()     // Catch:{ ConnectTimeoutException -> 0x00d7, SocketTimeoutException -> 0x00cf, Exception -> 0x00c7 }
                    int r6 = r6.getStatusCode()     // Catch:{ ConnectTimeoutException -> 0x00d7, SocketTimeoutException -> 0x00cf, Exception -> 0x00c7 }
                    java.lang.String r7 = "openSDK_LOG.ReportManager"
                    java.lang.String r8 = "-->httpRequest, statusCode: "
                    java.lang.String r9 = java.lang.String.valueOf(r6)     // Catch:{ ConnectTimeoutException -> 0x00d7, SocketTimeoutException -> 0x00cf, Exception -> 0x00c7 }
                    java.lang.String r8 = r8.concat(r9)     // Catch:{ ConnectTimeoutException -> 0x00d7, SocketTimeoutException -> 0x00cf, Exception -> 0x00c7 }
                    com.tencent.open.a.f.b(r7, r8)     // Catch:{ ConnectTimeoutException -> 0x00d7, SocketTimeoutException -> 0x00cf, Exception -> 0x00c7 }
                    r7 = 200(0xc8, float:2.8E-43)
                    if (r6 == r7) goto L_0x00b8
                    java.lang.String r6 = "openSDK_LOG.ReportManager"
                    java.lang.String r7 = "-->ReportCenter httpRequest : HttpStatuscode != 200"
                    com.tencent.open.a.f.b(r6, r7)     // Catch:{ ConnectTimeoutException -> 0x00d7, SocketTimeoutException -> 0x00cf, Exception -> 0x00c7 }
                    goto L_0x00e0
                L_0x00b8:
                    java.lang.String r4 = "openSDK_LOG.ReportManager"
                    java.lang.String r6 = "-->ReportCenter httpRequest Thread success"
                    com.tencent.open.a.f.b(r4, r6)     // Catch:{ ConnectTimeoutException -> 0x00c5, SocketTimeoutException -> 0x00c3, Exception -> 0x00c1 }
                    r4 = 1
                    goto L_0x00e0
                L_0x00c1:
                    r4 = 1
                    goto L_0x00c7
                L_0x00c3:
                    r4 = 1
                    goto L_0x00cf
                L_0x00c5:
                    r4 = 1
                    goto L_0x00d7
                L_0x00c7:
                    java.lang.String r0 = "openSDK_LOG.ReportManager"
                    java.lang.String r1 = "-->ReportCenter httpRequest Exception"
                    com.tencent.open.a.f.b(r0, r1)     // Catch:{ Exception -> 0x00fa }
                    goto L_0x00e0
                L_0x00cf:
                    java.lang.String r6 = "openSDK_LOG.ReportManager"
                    java.lang.String r7 = "-->ReportCenter httpRequest SocketTimeoutException"
                    com.tencent.open.a.f.b(r6, r7)     // Catch:{ Exception -> 0x00fa }
                    goto L_0x00de
                L_0x00d7:
                    java.lang.String r6 = "openSDK_LOG.ReportManager"
                    java.lang.String r7 = "-->ReportCenter httpRequest ConnectTimeoutException"
                    com.tencent.open.a.f.b(r6, r7)     // Catch:{ Exception -> 0x00fa }
                L_0x00de:
                    if (r3 < r0) goto L_0x008f
                L_0x00e0:
                    if (r4 != r5) goto L_0x00ea
                    java.lang.String r0 = "openSDK_LOG.ReportManager"
                    java.lang.String r1 = "-->ReportCenter httpRequest Thread request success"
                    com.tencent.open.a.f.b(r0, r1)     // Catch:{ Exception -> 0x00fa }
                    return
                L_0x00ea:
                    java.lang.String r0 = "openSDK_LOG.ReportManager"
                    java.lang.String r1 = "-->ReportCenter httpRequest Thread request failed"
                    com.tencent.open.a.f.b(r0, r1)     // Catch:{ Exception -> 0x00fa }
                    return
                L_0x00f2:
                    java.lang.String r0 = "openSDK_LOG.ReportManager"
                    java.lang.String r1 = "-->httpRequest unkonw request method return."
                    com.tencent.open.a.f.e(r0, r1)     // Catch:{ Exception -> 0x00fa }
                    return
                L_0x00fa:
                    java.lang.String r0 = "openSDK_LOG.ReportManager"
                    java.lang.String r1 = "-->httpRequest, exception in serial executor."
                    com.tencent.open.a.f.b(r0, r1)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.b.g.AnonymousClass6.run():void");
            }
        };
        ThreadManager.executeOnSubThread(r0);
    }
}
