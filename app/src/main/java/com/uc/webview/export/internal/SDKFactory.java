package com.uc.webview.export.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Pair;
import android.webkit.ValueCallback;
import com.uc.webview.export.WebView;
import com.uc.webview.export.annotations.Reflection;
import com.uc.webview.export.extension.InitCallback;
import com.uc.webview.export.extension.NotAvailableUCListener;
import com.uc.webview.export.internal.android.WebViewAndroid;
import com.uc.webview.export.internal.interfaces.CommonDef;
import com.uc.webview.export.internal.interfaces.IGlobalSettings;
import com.uc.webview.export.internal.interfaces.IPreloadManager;
import com.uc.webview.export.internal.interfaces.IWebView;
import com.uc.webview.export.internal.interfaces.UCMobileWebKit;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import com.uc.webview.export.internal.setup.UCSetupException;
import com.uc.webview.export.internal.uc.CoreFactory;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.utility.SetupTask;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/* compiled from: ProGuard */
public final class SDKFactory {
    public static ValueCallback<String> A = null;
    public static ValueCallback<UCSetupException> B = null;
    public static long C = 0;
    public static Map<String, Integer> D = new HashMap();
    public static Map<String, Object> E = new HashMap();
    public static Map<String, String> F = null;
    public static String G = null;
    public static SetupTask H = null;
    static boolean I = false;
    static boolean J = false;
    static boolean K = false;
    static boolean L = false;
    /* access modifiers changed from: private */
    public static int M = 0;
    private static IGlobalSettings N = null;
    private static IPreloadManager O = null;
    private static AbstractWebViewFactory P = new b(0);
    private static int Q = -1;
    private static String R = null;
    private static final Object S = new Object();
    private static boolean T = false;
    public static NotAvailableUCListener a = null;
    public static ValueCallback<Pair<String, HashMap<String, String>>> b = null;
    public static ClassLoader c = SDKFactory.class.getClassLoader();
    public static UCMobileWebKit d = null;
    public static Context e = null;
    public static int f = a.a;
    public static boolean g = false;
    @Reflection
    public static final int getCoreType = 10020;
    @Reflection
    public static final int getGlobalSettings = 10022;
    public static String h = null;
    @Reflection
    public static final int handlePerformanceTests = 10030;
    public static int i = -1;
    @Reflection
    public static final int isInited = 10010;
    public static long j = 4000;
    public static int k = 1;
    public static boolean l = false;
    public static boolean m = false;
    public static boolean n = false;
    public static int o = 0;
    public static InitCallback p = null;
    public static boolean q = false;
    public static boolean r = false;
    public static boolean s = false;
    @Reflection
    public static final int setBrowserFlag = 10009;
    @Reflection
    public static final int setCoreType = 10021;
    @Reflection
    public static final int setPreloadManager = 10059;
    @Reflection
    public static final int setWebViewFactory = 10008;
    public static boolean t = true;
    public static int u = 0;
    public static int v = 0;
    public static boolean w = false;
    public static String x;
    public static String y;
    public static ValueCallback<String> z;

    /* compiled from: ProGuard */
    enum a {
        ;

        static {
            d = new int[]{a, b, c};
        }
    }

    /* compiled from: ProGuard */
    static class b extends AbstractWebViewFactory {
        private b() {
        }

        /* synthetic */ b(byte b) {
            this();
        }

        public final IWebView createWebView(Context context, AttributeSet attributeSet, WebView webView, boolean z, int[] iArr) {
            if (SDKFactory.e == null) {
                SDKFactory.e = context.getApplicationContext();
            }
            SDKFactory.invoke(UCMPackageInfo.getLibFilter, context);
            int i = (CommonDef.sOnCreateWindowType == 1 || z) ? 2 : CommonDef.sOnCreateWindowType == 2 ? CoreFactory.getCoreType().intValue() : SDKFactory.M;
            Log.d("SDKFactory", String.format("createWebView(forceUsSystem=%b, sOnCreateWindowType=%d)=%d", new Object[]{Boolean.valueOf(z), Integer.valueOf(CommonDef.sOnCreateWindowType), Integer.valueOf(i)}));
            iArr[0] = i;
            if (i == 2) {
                return new WebViewAndroid(context, attributeSet, webView);
            }
            return CoreFactory.createWebView(context, attributeSet);
        }
    }

    /* compiled from: ProGuard */
    static class c {
        private static Object a = new Object();
        private static String b = null;
        private static Map<String, String> c = null;
        private static boolean d = false;

        public static void a(String str) {
            if (str.startsWith("JSON_CMD")) {
                synchronized (a) {
                    b = str.substring(8);
                    c = null;
                    d = false;
                }
                try {
                    new b().start();
                } catch (Exception e) {
                    Log.e("SDKFactory", "parser", e);
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0023, code lost:
            return null;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static java.lang.String b(java.lang.String r3) {
            /*
                a()
                java.lang.String r0 = b
                r1 = 0
                if (r0 == 0) goto L_0x0027
                java.util.Map<java.lang.String, java.lang.String> r0 = c
                if (r0 != 0) goto L_0x000d
                goto L_0x0027
            L_0x000d:
                java.lang.Object r0 = a
                monitor-enter(r0)
                java.lang.String r2 = b     // Catch:{ all -> 0x0024 }
                if (r2 == 0) goto L_0x0022
                java.util.Map<java.lang.String, java.lang.String> r2 = c     // Catch:{ all -> 0x0024 }
                if (r2 == 0) goto L_0x0022
                java.util.Map<java.lang.String, java.lang.String> r1 = c     // Catch:{ all -> 0x0024 }
                java.lang.Object r3 = r1.get(r3)     // Catch:{ all -> 0x0024 }
                java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x0024 }
                monitor-exit(r0)     // Catch:{ all -> 0x0024 }
                return r3
            L_0x0022:
                monitor-exit(r0)     // Catch:{ all -> 0x0024 }
                return r1
            L_0x0024:
                r3 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0024 }
                throw r3
            L_0x0027:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.SDKFactory.c.b(java.lang.String):java.lang.String");
        }

        /* JADX WARNING: Removed duplicated region for block: B:48:0x00e1 A[Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084, all -> 0x00dd }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static void a() {
            /*
                java.lang.String r0 = b
                if (r0 == 0) goto L_0x00f5
                java.util.Map<java.lang.String, java.lang.String> r0 = c
                if (r0 != 0) goto L_0x00f5
                boolean r0 = d
                if (r0 == 0) goto L_0x000e
                goto L_0x00f5
            L_0x000e:
                java.lang.Object r0 = a
                monitor-enter(r0)
                java.lang.String r1 = b     // Catch:{ all -> 0x00f2 }
                if (r1 == 0) goto L_0x00f0
                java.util.Map<java.lang.String, java.lang.String> r1 = c     // Catch:{ all -> 0x00f2 }
                if (r1 != 0) goto L_0x00f0
                boolean r1 = d     // Catch:{ all -> 0x00f2 }
                if (r1 != 0) goto L_0x00f0
                r1 = 0
                r2 = 1
                java.lang.String r3 = "SDKFactory"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.String r5 = "ParamCD.parser sCD : "
                r4.<init>(r5)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.String r5 = b     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                r4.append(r5)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.String r4 = r4.toString()     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                com.uc.webview.export.internal.utility.Log.d(r3, r4)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.String r3 = "cd_pp_co"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r3)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.util.HashMap r3 = new java.util.HashMap     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                r3.<init>()     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.String r5 = b     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                r4.<init>(r5)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.util.Iterator r5 = r4.keys()     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
            L_0x0049:
                boolean r6 = r5.hasNext()     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                if (r6 == 0) goto L_0x007c
                java.lang.Object r6 = r5.next()     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.String r6 = (java.lang.String) r6     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.Object r7 = r4.get(r6)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.String r7 = r7.toString()     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.String r8 = "SDKFactory"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.String r10 = "ParamCD.parser key : "
                r9.<init>(r10)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                r9.append(r6)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.String r10 = " value: "
                r9.append(r10)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                r9.append(r7)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.String r9 = r9.toString()     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                com.uc.webview.export.internal.utility.Log.d(r8, r9)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                r3.put(r6, r7)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                goto L_0x0049
            L_0x007c:
                c = r3     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                java.lang.String r3 = "cd_pp_su"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r3)     // Catch:{ ClassCastException -> 0x00c1, JSONException -> 0x00a5, Throwable -> 0x0086, all -> 0x0084 }
                goto L_0x00f0
            L_0x0084:
                r3 = move-exception
                goto L_0x00df
            L_0x0086:
                r1 = move-exception
                java.lang.String r3 = "SDKFactory"
                java.lang.String r4 = "ParamCD.parser cd exception java.lang.Throwable "
                java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x00dd }
                java.lang.String r1 = r4.concat(r1)     // Catch:{ all -> 0x00dd }
                com.uc.webview.export.internal.utility.Log.d(r3, r1)     // Catch:{ all -> 0x00dd }
                java.lang.String r1 = "SDKFactory"
                java.lang.String r3 = "ParamCD.parser faulure!!"
                com.uc.webview.export.internal.utility.Log.d(r1, r3)     // Catch:{ all -> 0x00f2 }
                d = r2     // Catch:{ all -> 0x00f2 }
                java.lang.String r1 = "cd_pp_fa"
            L_0x00a1:
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1)     // Catch:{ all -> 0x00f2 }
                goto L_0x00f0
            L_0x00a5:
                r1 = move-exception
                java.lang.String r3 = "SDKFactory"
                java.lang.String r4 = "ParamCD.parser cd exception org.json.JSONException "
                java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x00dd }
                java.lang.String r1 = r4.concat(r1)     // Catch:{ all -> 0x00dd }
                com.uc.webview.export.internal.utility.Log.d(r3, r1)     // Catch:{ all -> 0x00dd }
                java.lang.String r1 = "SDKFactory"
                java.lang.String r3 = "ParamCD.parser faulure!!"
                com.uc.webview.export.internal.utility.Log.d(r1, r3)     // Catch:{ all -> 0x00f2 }
                d = r2     // Catch:{ all -> 0x00f2 }
                java.lang.String r1 = "cd_pp_fa"
                goto L_0x00a1
            L_0x00c1:
                r1 = move-exception
                java.lang.String r3 = "SDKFactory"
                java.lang.String r4 = "ParamCD.parser cd exception java.lang.ClassCastException "
                java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x00dd }
                java.lang.String r1 = r4.concat(r1)     // Catch:{ all -> 0x00dd }
                com.uc.webview.export.internal.utility.Log.d(r3, r1)     // Catch:{ all -> 0x00dd }
                java.lang.String r1 = "SDKFactory"
                java.lang.String r3 = "ParamCD.parser faulure!!"
                com.uc.webview.export.internal.utility.Log.d(r1, r3)     // Catch:{ all -> 0x00f2 }
                d = r2     // Catch:{ all -> 0x00f2 }
                java.lang.String r1 = "cd_pp_fa"
                goto L_0x00a1
            L_0x00dd:
                r3 = move-exception
                r1 = 1
            L_0x00df:
                if (r1 == 0) goto L_0x00ef
                java.lang.String r1 = "SDKFactory"
                java.lang.String r4 = "ParamCD.parser faulure!!"
                com.uc.webview.export.internal.utility.Log.d(r1, r4)     // Catch:{ all -> 0x00f2 }
                d = r2     // Catch:{ all -> 0x00f2 }
                java.lang.String r1 = "cd_pp_fa"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1)     // Catch:{ all -> 0x00f2 }
            L_0x00ef:
                throw r3     // Catch:{ all -> 0x00f2 }
            L_0x00f0:
                monitor-exit(r0)     // Catch:{ all -> 0x00f2 }
                return
            L_0x00f2:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00f2 }
                throw r1
            L_0x00f5:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.SDKFactory.c.a():void");
        }
    }

    /* compiled from: ProGuard */
    static class d extends Handler {
        /* access modifiers changed from: private */
        public static final ConcurrentLinkedQueue<Runnable> a = new ConcurrentLinkedQueue<>();
        /* access modifiers changed from: private */
        public static UCSetupException b;
        private static final Runnable c = new c();

        private d(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            if (message.what == 0 && SDKFactory.a != null) {
                SDKFactory.a.onNotAvailableUC(message.arg1);
                SDKFactory.a = null;
            }
        }

        static void a(Runnable runnable) {
            if (runnable != null) {
                a.add(runnable);
                new d(Looper.getMainLooper()).post(c);
            }
            if (((Boolean) SDKFactory.invoke(UCMPackageInfo.hadInstallUCMobile, new Object[0])).booleanValue()) {
                if (b == null) {
                    c.run();
                }
                if (b != null) {
                    throw b;
                }
            }
        }
    }

    /* JADX WARNING: type inference failed for: r12v0 */
    /* JADX WARNING: type inference failed for: r12v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v102, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r12v2 */
    /* JADX WARNING: type inference failed for: r2v103 */
    /* JADX WARNING: type inference failed for: r12v3 */
    /* JADX WARNING: type inference failed for: r2v104 */
    /* JADX WARNING: type inference failed for: r1v179 */
    /* JADX WARNING: type inference failed for: r2v105, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v106 */
    /* JADX WARNING: type inference failed for: r1v182, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v107 */
    /* JADX WARNING: type inference failed for: r12v4 */
    /* JADX WARNING: type inference failed for: r1v184 */
    /* JADX WARNING: type inference failed for: r1v185, types: [java.io.FileWriter] */
    /* JADX WARNING: type inference failed for: r2v108 */
    /* JADX WARNING: type inference failed for: r12v5, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r12v6 */
    /* JADX WARNING: type inference failed for: r12v7, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r12v8 */
    /* JADX WARNING: type inference failed for: r12v9 */
    /* JADX WARNING: type inference failed for: r1v198, types: [java.io.Closeable, java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r12v10 */
    /* JADX WARNING: type inference failed for: r12v11 */
    /* JADX WARNING: type inference failed for: r12v12 */
    /* JADX WARNING: type inference failed for: r4v33, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r7v7, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r12v13 */
    /* JADX WARNING: type inference failed for: r12v14 */
    /* JADX WARNING: type inference failed for: r12v15 */
    /* JADX WARNING: type inference failed for: r12v16 */
    /* JADX WARNING: type inference failed for: r12v17 */
    /* JADX WARNING: type inference failed for: r12v18 */
    /* JADX WARNING: type inference failed for: r2v234 */
    /* JADX WARNING: type inference failed for: r2v235 */
    /* JADX WARNING: type inference failed for: r1v347 */
    /* JADX WARNING: type inference failed for: r1v348 */
    /* JADX WARNING: type inference failed for: r12v19 */
    /* JADX WARNING: type inference failed for: r12v20 */
    /* JADX WARNING: type inference failed for: r12v21 */
    /* JADX WARNING: type inference failed for: r12v22 */
    /* JADX WARNING: Can't wrap try/catch for region: R(2:18|19) */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        f = com.uc.webview.export.internal.SDKFactory.a.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:305:0x05a2, code lost:
        if (com.uc.webview.export.internal.utility.j.c(r2[2]) >= 13) goto L_0x05af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:440:0x0857, code lost:
        r0 = th;
        r12 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:442:0x085a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:443:0x085b, code lost:
        r1 = r0;
        r2 = 0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x0045 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:422:0x0838 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r12v3
      assigns: []
      uses: []
      mth insns count: 1240
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:440:0x0857 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:404:0x07f9] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:288:0x0537=Splitter:B:288:0x0537, B:275:0x051d=Splitter:B:275:0x051d} */
    /* JADX WARNING: Unknown variable types count: 16 */
    @com.uc.webview.export.annotations.Reflection
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object invoke(int r22, java.lang.Object... r23) {
        /*
            r1 = r23
            r2 = 10006(0x2716, float:1.4021E-41)
            r3 = 5
            r4 = 10022(0x2726, float:1.4044E-41)
            r5 = 10064(0x2750, float:1.4103E-41)
            r6 = 10045(0x273d, float:1.4076E-41)
            r7 = 10001(0x2711, float:1.4014E-41)
            r8 = 3
            r9 = 10020(0x2724, float:1.4041E-41)
            r10 = 10010(0x271a, float:1.4027E-41)
            r11 = 10029(0x272d, float:1.4054E-41)
            r12 = 0
            r13 = 2
            r14 = 1
            r15 = 0
            switch(r22) {
                case 10001: goto L_0x0d68;
                case 10002: goto L_0x0d59;
                case 10003: goto L_0x0d41;
                case 10004: goto L_0x0cc4;
                case 10005: goto L_0x0cb2;
                case 10006: goto L_0x0c8b;
                case 10007: goto L_0x0c74;
                case 10008: goto L_0x0c6c;
                case 10009: goto L_0x0c49;
                case 10010: goto L_0x0c3e;
                case 10011: goto L_0x0c1f;
                case 10012: goto L_0x0baa;
                case 10013: goto L_0x0b82;
                case 10014: goto L_0x0b59;
                case 10015: goto L_0x0b45;
                case 10016: goto L_0x0b2b;
                case 10017: goto L_0x0b11;
                case 10018: goto L_0x0af7;
                case 10019: goto L_0x0add;
                case 10020: goto L_0x0ac8;
                case 10021: goto L_0x0a9a;
                case 10022: goto L_0x0a7d;
                case 10023: goto L_0x0a07;
                case 10024: goto L_0x09e5;
                case 10025: goto L_0x09cb;
                case 10026: goto L_0x09ba;
                case 10027: goto L_0x09b1;
                case 10028: goto L_0x0997;
                case 10029: goto L_0x0941;
                case 10030: goto L_0x092a;
                case 10031: goto L_0x090a;
                case 10032: goto L_0x08e6;
                case 10033: goto L_0x087e;
                case 10034: goto L_0x0798;
                case 10035: goto L_0x06f7;
                case 10036: goto L_0x0642;
                case 10037: goto L_0x05de;
                case 10038: goto L_0x055d;
                case 10039: goto L_0x0420;
                case 10040: goto L_0x0406;
                case 10041: goto L_0x03b9;
                case 10042: goto L_0x03b2;
                case 10043: goto L_0x03a6;
                case 10044: goto L_0x035f;
                case 10045: goto L_0x0266;
                case 10046: goto L_0x023c;
                case 10047: goto L_0x0239;
                case 10048: goto L_0x0204;
                case 10049: goto L_0x01ae;
                case 10050: goto L_0x01a7;
                case 10051: goto L_0x01a4;
                case 10052: goto L_0x018c;
                case 10053: goto L_0x014e;
                case 10054: goto L_0x0139;
                case 10055: goto L_0x011f;
                case 10056: goto L_0x00eb;
                case 10057: goto L_0x00d4;
                case 10058: goto L_0x00c1;
                case 10059: goto L_0x00b9;
                case 10060: goto L_0x00a7;
                case 10061: goto L_0x008e;
                case 10062: goto L_0x0075;
                case 10063: goto L_0x005c;
                case 10064: goto L_0x0026;
                case 10065: goto L_0x001d;
                default: goto L_0x001b;
            }
        L_0x001b:
            goto L_0x0d75
        L_0x001d:
            r1 = r1[r15]
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r1 = com.uc.webview.export.internal.SDKFactory.c.b(r1)
            return r1
        L_0x0026:
            int r1 = f
            int r2 = com.uc.webview.export.internal.SDKFactory.a.a
            if (r1 != r2) goto L_0x004f
            java.lang.Class<com.uc.webview.export.internal.SDKFactory> r1 = com.uc.webview.export.internal.SDKFactory.class
            monitor-enter(r1)
            int r2 = f     // Catch:{ all -> 0x004b }
            int r3 = com.uc.webview.export.internal.SDKFactory.a.a     // Catch:{ all -> 0x004b }
            if (r2 != r3) goto L_0x0049
            java.lang.String r2 = "com.uc.webview.export.cd.Utils"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ ClassNotFoundException -> 0x0045 }
            if (r2 == 0) goto L_0x0040
            int r2 = com.uc.webview.export.internal.SDKFactory.a.b     // Catch:{ ClassNotFoundException -> 0x0045 }
            goto L_0x0042
        L_0x0040:
            int r2 = com.uc.webview.export.internal.SDKFactory.a.c     // Catch:{ ClassNotFoundException -> 0x0045 }
        L_0x0042:
            f = r2     // Catch:{ ClassNotFoundException -> 0x0045 }
            goto L_0x0049
        L_0x0045:
            int r2 = com.uc.webview.export.internal.SDKFactory.a.c     // Catch:{ all -> 0x004b }
            f = r2     // Catch:{ all -> 0x004b }
        L_0x0049:
            monitor-exit(r1)     // Catch:{ all -> 0x004b }
            goto L_0x004f
        L_0x004b:
            r0 = move-exception
            r2 = r0
            monitor-exit(r1)     // Catch:{ all -> 0x004b }
            throw r2
        L_0x004f:
            int r1 = f
            int r2 = com.uc.webview.export.internal.SDKFactory.a.b
            if (r1 != r2) goto L_0x0056
            goto L_0x0057
        L_0x0056:
            r14 = 0
        L_0x0057:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r14)
            return r1
        L_0x005c:
            int r2 = r1.length
            if (r2 <= 0) goto L_0x0062
            r1 = r1[r15]
            goto L_0x0063
        L_0x0062:
            r1 = r12
        L_0x0063:
            boolean r2 = r1 instanceof java.lang.Boolean
            if (r2 == 0) goto L_0x0070
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x0070
            goto L_0x0071
        L_0x0070:
            r14 = 0
        L_0x0071:
            L = r14
            goto L_0x0d75
        L_0x0075:
            int r2 = r1.length
            if (r2 <= 0) goto L_0x007b
            r1 = r1[r15]
            goto L_0x007c
        L_0x007b:
            r1 = r12
        L_0x007c:
            boolean r2 = r1 instanceof java.lang.Boolean
            if (r2 == 0) goto L_0x0089
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x0089
            goto L_0x008a
        L_0x0089:
            r14 = 0
        L_0x008a:
            K = r14
            goto L_0x0d75
        L_0x008e:
            int r2 = r1.length
            if (r2 <= 0) goto L_0x0094
            r1 = r1[r15]
            goto L_0x0095
        L_0x0094:
            r1 = r12
        L_0x0095:
            boolean r2 = r1 instanceof java.lang.Boolean
            if (r2 == 0) goto L_0x00a2
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x00a2
            goto L_0x00a3
        L_0x00a2:
            r14 = 0
        L_0x00a3:
            J = r14
            goto L_0x0d75
        L_0x00a7:
            com.uc.webview.export.internal.interfaces.IPreloadManager r1 = O
            if (r1 == 0) goto L_0x00b6
            android.content.Context r1 = e
            if (r1 == 0) goto L_0x00b6
            com.uc.webview.export.internal.interfaces.IPreloadManager r1 = O
            android.content.Context r2 = e
            r1.setContext(r2)
        L_0x00b6:
            com.uc.webview.export.internal.interfaces.IPreloadManager r1 = O
            return r1
        L_0x00b9:
            r1 = r1[r15]
            com.uc.webview.export.internal.interfaces.IPreloadManager r1 = (com.uc.webview.export.internal.interfaces.IPreloadManager) r1
            O = r1
            goto L_0x0d75
        L_0x00c1:
            r1 = r1[r15]
            java.lang.String r1 = (java.lang.String) r1
            java.util.Map<java.lang.String, java.lang.Object> r2 = E
            boolean r2 = r2.containsKey(r1)
            if (r2 == 0) goto L_0x0d75
            java.util.Map<java.lang.String, java.lang.Object> r2 = E
            java.lang.Object r1 = r2.get(r1)
            return r1
        L_0x00d4:
            java.util.Map<java.lang.String, java.lang.Object> r2 = E
            int r2 = r2.size()
            r3 = 64
            if (r2 >= r3) goto L_0x0d75
            r2 = r1[r15]
            java.lang.String r2 = (java.lang.String) r2
            r1 = r1[r14]
            java.util.Map<java.lang.String, java.lang.Object> r3 = E
            r3.put(r2, r1)
            goto L_0x0d75
        L_0x00eb:
            r1 = r1[r15]
            android.content.Context r1 = (android.content.Context) r1
            int r2 = M
            if (r2 != r13) goto L_0x0107
            java.lang.String r2 = "android.webkit.WebSettings"
            java.lang.String r3 = "getDefaultUserAgent"
            java.lang.Class[] r4 = new java.lang.Class[r14]     // Catch:{ Exception -> 0x0106 }
            java.lang.Class<android.content.Context> r5 = android.content.Context.class
            r4[r15] = r5     // Catch:{ Exception -> 0x0106 }
            java.lang.Object[] r5 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0106 }
            r5[r15] = r1     // Catch:{ Exception -> 0x0106 }
            java.lang.Object r1 = com.uc.webview.export.internal.utility.ReflectionUtil.invokeNoThrow(r2, r3, r4, r5)     // Catch:{ Exception -> 0x0106 }
            return r1
        L_0x0106:
            return r12
        L_0x0107:
            int r2 = M
            if (r2 != r8) goto L_0x0d75
            java.lang.String r2 = "com.uc.webkit.WebSettings"
            java.lang.String r3 = "getDefaultUserAgent"
            java.lang.Class[] r4 = new java.lang.Class[r14]     // Catch:{ Exception -> 0x011e }
            java.lang.Class<android.content.Context> r5 = android.content.Context.class
            r4[r15] = r5     // Catch:{ Exception -> 0x011e }
            java.lang.Object[] r5 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x011e }
            r5[r15] = r1     // Catch:{ Exception -> 0x011e }
            java.lang.Object r1 = com.uc.webview.export.internal.utility.ReflectionUtil.invokeNoThrow(r2, r3, r4, r5)     // Catch:{ Exception -> 0x011e }
            return r1
        L_0x011e:
            return r12
        L_0x011f:
            int r1 = M
            if (r1 != r13) goto L_0x012c
            java.lang.String r1 = "android.webkit.WebView"
            java.lang.String r2 = "enableSlowWholeDocumentDraw"
            com.uc.webview.export.internal.utility.ReflectionUtil.invokeNoThrow(r1, r2)
            goto L_0x0d75
        L_0x012c:
            int r1 = M
            if (r1 != r8) goto L_0x0d75
            java.lang.String r1 = "com.uc.webkit.WebView"
            java.lang.String r2 = "enableSlowWholeDocumentDraw"
            com.uc.webview.export.internal.utility.ReflectionUtil.invokeNoThrow(r1, r2)
            goto L_0x0d75
        L_0x0139:
            r1 = r1[r15]
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            java.lang.Object[] r2 = new java.lang.Object[r15]
            invoke(r11, r2)
            if (r1 != r13) goto L_0x0149
            return r12
        L_0x0149:
            com.uc.webview.export.internal.interfaces.IServiceWorkerController r1 = com.uc.webview.export.internal.uc.CoreFactory.getServiceWorkerController()
            return r1
        L_0x014e:
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.Object r2 = invoke(r5, r2)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x015d
            return r12
        L_0x015d:
            r2 = r1[r15]
            java.lang.String r2 = (java.lang.String) r2
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r2)
            if (r3 != 0) goto L_0x0d75
            java.lang.String r3 = "pub_key"
            boolean r3 = r2.contains(r3)
            if (r3 == 0) goto L_0x0d75
            r1 = r1[r14]     // Catch:{ Exception -> 0x0d75 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0d75 }
            java.lang.String r3 = "com.uc.webview.export.cd.Utils"
            java.lang.String r4 = "addMiddlewareCD"
            java.lang.Class[] r5 = new java.lang.Class[r13]     // Catch:{ Exception -> 0x0d75 }
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r5[r15] = r6     // Catch:{ Exception -> 0x0d75 }
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r5[r14] = r6     // Catch:{ Exception -> 0x0d75 }
            java.lang.Object[] r6 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0d75 }
            r6[r15] = r2     // Catch:{ Exception -> 0x0d75 }
            r6[r14] = r1     // Catch:{ Exception -> 0x0d75 }
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r3, r4, r5, r6)     // Catch:{ Exception -> 0x0d75 }
            goto L_0x0d75
        L_0x018c:
            java.lang.Object[] r1 = new java.lang.Object[r15]
            java.lang.Object r1 = invoke(r4, r1)
            com.uc.webview.export.internal.interfaces.IGlobalSettings r1 = (com.uc.webview.export.internal.interfaces.IGlobalSettings) r1
            if (r1 == 0) goto L_0x01a1
            java.lang.String r2 = "IsRunningInWebViewSdk"
            boolean r1 = r1.getBoolValue(r2)
            if (r1 == 0) goto L_0x01a1
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            return r1
        L_0x01a1:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            return r1
        L_0x01a4:
            android.content.Context r1 = e
            return r1
        L_0x01a7:
            boolean r1 = r
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            return r1
        L_0x01ae:
            r1 = r1[r15]
            java.lang.String r1 = (java.lang.String) r1
            boolean r2 = com.uc.webview.export.internal.utility.j.a(r1)
            if (r2 == 0) goto L_0x01bc
            F = r12
            goto L_0x0d75
        L_0x01bc:
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            java.lang.String r3 = ";"
            java.lang.String[] r1 = r1.split(r3)
            int r3 = r1.length
            r4 = 0
        L_0x01c9:
            if (r4 >= r3) goto L_0x01e4
            r5 = r1[r4]
            java.lang.String r6 = "="
            java.lang.String[] r5 = r5.split(r6)
            int r6 = r5.length
            if (r6 != r13) goto L_0x01e1
            r6 = r5[r15]
            java.lang.String r6 = r6.trim()
            r5 = r5[r14]
            r2.put(r6, r5)
        L_0x01e1:
            int r4 = r4 + 1
            goto L_0x01c9
        L_0x01e4:
            java.lang.String r1 = "tag_test_log"
            java.lang.String r3 = "CDParam:"
            java.lang.String r4 = java.lang.String.valueOf(r2)
            java.lang.String r3 = r3.concat(r4)
            com.uc.webview.export.internal.utility.Log.i(r1, r3)
            boolean r1 = r2.isEmpty()
            if (r1 != 0) goto L_0x01fb
            F = r2
        L_0x01fb:
            r1 = 10033(0x2731, float:1.4059E-41)
            java.lang.Object[] r2 = new java.lang.Object[r15]
            invoke(r1, r2)
            goto L_0x0d75
        L_0x0204:
            r2 = r1[r15]
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            com.uc.webview.export.internal.utility.Log.sPrintLog = r2
            java.lang.String r2 = "com.uc.webview.export.cd.CDUtil"
            java.lang.String r4 = "setEnablePrintLog"
            java.lang.Class[] r5 = new java.lang.Class[r14]     // Catch:{ Exception -> 0x0225 }
            java.lang.Class<java.lang.Boolean> r6 = java.lang.Boolean.class
            r5[r15] = r6     // Catch:{ Exception -> 0x0225 }
            java.lang.Object[] r6 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0225 }
            boolean r7 = com.uc.webview.export.internal.utility.Log.sPrintLog     // Catch:{ Exception -> 0x0225 }
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x0225 }
            r6[r15] = r7     // Catch:{ Exception -> 0x0225 }
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r2, r4, r5, r6)     // Catch:{ Exception -> 0x0225 }
        L_0x0225:
            r2 = r1[r14]
            java.lang.Object[] r2 = (java.lang.Object[]) r2
            if (r2 == 0) goto L_0x0d75
            int r4 = r2.length
            if (r4 != r3) goto L_0x0d75
            r1 = r1[r15]
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            r2[r15] = r1
            com.uc.webview.export.cyclone.UCLogger.setup(r2)
            goto L_0x0d75
        L_0x0239:
            java.lang.String r1 = R
            return r1
        L_0x023c:
            java.lang.Object[] r1 = new java.lang.Object[r15]
            java.lang.Object r1 = invoke(r10, r1)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 != 0) goto L_0x0252
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "UC WebView Sdk not inited."
            r1.<init>(r2)
            throw r1
        L_0x0252:
            java.lang.Object[] r1 = new java.lang.Object[r15]
            java.lang.Object r1 = invoke(r9, r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            if (r1 != r13) goto L_0x0263
            java.lang.String r1 = "System WebView"
            return r1
        L_0x0263:
            java.lang.String r1 = h
            return r1
        L_0x0266:
            boolean r1 = J
            if (r1 == 0) goto L_0x0274
            android.os.Looper r1 = android.os.Looper.myLooper()
            android.os.Looper r2 = android.os.Looper.getMainLooper()
            if (r1 == r2) goto L_0x0d75
        L_0x0274:
            com.uc.webview.export.cyclone.UCElapseTime r1 = new com.uc.webview.export.cyclone.UCElapseTime
            r1.<init>()
        L_0x0279:
            boolean r2 = com.uc.webview.export.utility.SetupTask.isSetupThread()
            if (r2 == 0) goto L_0x02ce
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.Object r2 = invoke(r10, r2)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x02ce
            java.lang.Thread r1 = java.lang.Thread.currentThread()
            java.lang.StackTraceElement[] r1 = r1.getStackTrace()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
        L_0x029a:
            int r3 = r1.length
            if (r8 >= r3) goto L_0x02c2
            r3 = 8
            if (r8 >= r3) goto L_0x02c2
            r3 = r1[r8]
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = "com.uc.webview.export."
            java.lang.String r5 = ""
            java.lang.String r3 = r3.replace(r4, r5)
            java.lang.String r4 = "\\(.+\\)"
            java.lang.String r5 = ""
            java.lang.String r3 = r3.replaceAll(r4, r5)
            r2.append(r3)
            java.lang.String r3 = ","
            r2.append(r3)
            int r8 = r8 + 1
            goto L_0x029a
        L_0x02c2:
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            r3 = 3013(0xbc5, float:4.222E-42)
            java.lang.String r2 = r2.toString()
            r1.<init>(r3, r2)
            throw r1
        L_0x02ce:
            com.uc.webview.export.internal.SDKFactory.d.a(r12)
            boolean r2 = q
            if (r2 == 0) goto L_0x02d8
            com.uc.webview.export.utility.SetupTask.resumeAll()
        L_0x02d8:
            int r2 = M
            if (r2 != 0) goto L_0x02f9
            r2 = 10024(0x2728, float:1.4047E-41)
            java.lang.Object[] r3 = new java.lang.Object[r14]
            r4 = 200(0xc8, double:9.9E-322)
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r3[r15] = r4
            invoke(r2, r3)
            long r2 = r1.getMilis()
            long r4 = j
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 < 0) goto L_0x0279
            int r2 = k
            if (r2 == r14) goto L_0x0279
        L_0x02f9:
            com.uc.webview.export.internal.SDKFactory.d.a(r12)
            java.lang.Class<com.uc.webview.export.internal.SDKFactory> r2 = com.uc.webview.export.internal.SDKFactory.class
            monitor-enter(r2)
            int r1 = M     // Catch:{ all -> 0x035b }
            if (r1 != 0) goto L_0x0330
            int r1 = k     // Catch:{ all -> 0x035b }
            if (r1 != r13) goto L_0x030a
            M = r13     // Catch:{ all -> 0x035b }
            goto L_0x0330
        L_0x030a:
            int r1 = k     // Catch:{ all -> 0x035b }
            if (r1 != r8) goto L_0x0330
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ all -> 0x035b }
            r3 = 4009(0xfa9, float:5.618E-42)
            java.lang.String r4 = "Thread [%s] waitting for init is up to [%s] milis."
            java.lang.Object[] r5 = new java.lang.Object[r13]     // Catch:{ all -> 0x035b }
            java.lang.Thread r6 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x035b }
            java.lang.String r6 = r6.getName()     // Catch:{ all -> 0x035b }
            r5[r15] = r6     // Catch:{ all -> 0x035b }
            long r6 = j     // Catch:{ all -> 0x035b }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x035b }
            r5[r14] = r6     // Catch:{ all -> 0x035b }
            java.lang.String r4 = java.lang.String.format(r4, r5)     // Catch:{ all -> 0x035b }
            r1.<init>(r3, r4)     // Catch:{ all -> 0x035b }
            throw r1     // Catch:{ all -> 0x035b }
        L_0x0330:
            java.lang.String r1 = "SDKFactory"
            java.util.Locale r3 = java.util.Locale.CHINA     // Catch:{ all -> 0x035b }
            java.lang.String r4 = "waitForInit(sWebViewPolicy=%d, sMaxWaitMillis=%d)=%d"
            java.lang.Object[] r5 = new java.lang.Object[r8]     // Catch:{ all -> 0x035b }
            int r6 = k     // Catch:{ all -> 0x035b }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x035b }
            r5[r15] = r6     // Catch:{ all -> 0x035b }
            long r6 = j     // Catch:{ all -> 0x035b }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x035b }
            r5[r14] = r6     // Catch:{ all -> 0x035b }
            int r6 = M     // Catch:{ all -> 0x035b }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x035b }
            r5[r13] = r6     // Catch:{ all -> 0x035b }
            java.lang.String r3 = java.lang.String.format(r3, r4, r5)     // Catch:{ all -> 0x035b }
            com.uc.webview.export.internal.utility.Log.d(r1, r3)     // Catch:{ all -> 0x035b }
            monitor-exit(r2)     // Catch:{ all -> 0x035b }
            goto L_0x0d75
        L_0x035b:
            r0 = move-exception
            r1 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x035b }
            throw r1
        L_0x035f:
            r1 = r1[r15]
            java.util.Map r1 = (java.util.Map) r1
            if (r1 == 0) goto L_0x0385
            java.lang.String r2 = "ucPlayerRoot"
            java.lang.Object r2 = r1.get(r2)
            if (r2 == 0) goto L_0x0374
            java.lang.String r2 = r2.toString()
            x = r2
        L_0x0374:
            java.lang.String r2 = "ucPlayer"
            java.lang.Object r1 = r1.get(r2)
            if (r1 == 0) goto L_0x0385
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            t = r1
        L_0x0385:
            java.lang.String r1 = "tag_test_log"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "sUseUCPlayer:"
            r2.<init>(r3)
            boolean r3 = t
            r2.append(r3)
            java.lang.String r3 = ",sUCPlayerSoRoot:"
            r2.append(r3)
            java.lang.String r3 = x
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            goto L_0x0d75
        L_0x03a6:
            r1 = r1[r15]
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            Q = r1
            goto L_0x0d75
        L_0x03b2:
            int r1 = Q
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            return r1
        L_0x03b9:
            java.lang.Object[] r1 = new java.lang.Object[r15]
            java.lang.Object r1 = invoke(r9, r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            if (r1 != r13) goto L_0x03c8
            return r12
        L_0x03c8:
            r1 = 10015(0x271f, float:1.4034E-41)
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.Object r1 = invoke(r1, r2)
            com.uc.webview.export.internal.interfaces.UCMobileWebKit r1 = (com.uc.webview.export.internal.interfaces.UCMobileWebKit) r1
            java.lang.Class r2 = r1.getClass()     // Catch:{ Throwable -> 0x03fc }
            java.lang.String r3 = "sTrafficSent"
            java.lang.reflect.Field r2 = r2.getField(r3)     // Catch:{ Throwable -> 0x03fc }
            long r2 = r2.getLong(r1)     // Catch:{ Throwable -> 0x03fc }
            java.lang.Class r4 = r1.getClass()     // Catch:{ Throwable -> 0x03fc }
            java.lang.String r5 = "sTrafficReceived"
            java.lang.reflect.Field r4 = r4.getField(r5)     // Catch:{ Throwable -> 0x03fc }
            long r4 = r4.getLong(r1)     // Catch:{ Throwable -> 0x03fc }
            android.util.Pair r1 = new android.util.Pair     // Catch:{ Throwable -> 0x03fc }
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x03fc }
            java.lang.Long r3 = java.lang.Long.valueOf(r4)     // Catch:{ Throwable -> 0x03fc }
            r1.<init>(r2, r3)     // Catch:{ Throwable -> 0x03fc }
            return r1
        L_0x03fc:
            r0 = move-exception
            r1 = r0
            java.lang.String r2 = "tag_test_log"
            java.lang.String r3 = "getTraffic"
            com.uc.webview.export.internal.utility.Log.d(r2, r3, r1)
            return r12
        L_0x0406:
            java.lang.Object[] r1 = new java.lang.Object[r15]
            java.lang.Object r1 = invoke(r9, r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            if (r1 != r13) goto L_0x0417
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            return r1
        L_0x0417:
            boolean r1 = com.uc.webview.export.internal.uc.CoreFactory.p()
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            return r1
        L_0x0420:
            r2 = r1[r15]
            java.lang.String r2 = (java.lang.String) r2
            r1 = r1[r14]
            java.lang.Runnable r1 = (java.lang.Runnable) r1
            android.content.Context r3 = e
            if (r3 == 0) goto L_0x0436
            android.content.Context r3 = e
            android.content.pm.ApplicationInfo r3 = r3.getApplicationInfo()
            java.lang.String r3 = r3.dataDir
            G = r3
        L_0x0436:
            java.lang.Object[] r3 = new java.lang.Object[r15]
            java.lang.Object r3 = invoke(r9, r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 == r13) goto L_0x0547
            java.lang.String r3 = G
            if (r3 != 0) goto L_0x044a
            goto L_0x0547
        L_0x044a:
            java.io.File r3 = new java.io.File
            java.lang.String r4 = G
            java.lang.String r5 = "shared_prefs"
            r3.<init>(r4, r5)
            java.io.File r4 = new java.io.File
            java.lang.String r5 = "typeface.xml"
            r4.<init>(r3, r5)
            java.io.File r5 = new java.io.File
            java.lang.String r6 = "uc_typeface_hash_"
            r5.<init>(r3, r6)
            if (r2 == 0) goto L_0x0471
            java.lang.String r6 = r2.trim()
            int r6 = r6.length()
            if (r6 != 0) goto L_0x0470
            goto L_0x0471
        L_0x0470:
            r14 = 0
        L_0x0471:
            if (r14 == 0) goto L_0x047f
            java.lang.String r6 = "uc_font_sys"
            int r6 = r6.hashCode()
            java.lang.String r6 = java.lang.String.valueOf(r6)
            goto L_0x04a3
        L_0x047f:
            java.io.File r6 = new java.io.File
            r6.<init>(r2)
            boolean r7 = r6.exists()
            if (r7 != 0) goto L_0x049b
            java.lang.String r1 = "tag_test_log"
            java.lang.String r2 = "字体文件不存在-"
            java.lang.String r3 = java.lang.String.valueOf(r6)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return r12
        L_0x049b:
            int r6 = r2.hashCode()
            java.lang.String r6 = java.lang.String.valueOf(r6)
        L_0x04a3:
            r7 = 45
            r8 = 95
            java.lang.String r6 = r6.replace(r7, r8)
            java.io.File r7 = new java.io.File
            r7.<init>(r5, r6)
            boolean r6 = r7.exists()
            if (r6 == 0) goto L_0x04c5
            boolean r6 = r4.exists()
            if (r6 == 0) goto L_0x04c5
            java.lang.String r1 = "tag_test_log"
            java.lang.String r2 = "字体没变化..."
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return r12
        L_0x04c5:
            boolean r6 = r5.exists()     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
            if (r6 == 0) goto L_0x04da
            java.io.File[] r5 = r5.listFiles()     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
            int r6 = r5.length     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
        L_0x04d0:
            if (r15 >= r6) goto L_0x04dd
            r8 = r5[r15]     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
            r8.delete()     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
            int r15 = r15 + 1
            goto L_0x04d0
        L_0x04da:
            r5.mkdirs()     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
        L_0x04dd:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
            r5.<init>()     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
            if (r14 == 0) goto L_0x04ea
            java.lang.String r2 = "<?xml version='1.0' encoding='utf-8' standalone='yes' ?>\n<map />"
            r5.append(r2)     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
            goto L_0x04f7
        L_0x04ea:
            java.lang.String r6 = "<?xml version='1.0' encoding='utf-8' standalone='yes' ?>\n<map>\n<string name=\"current_typeface_path\">"
            r5.append(r6)     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
            r5.append(r2)     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
            java.lang.String r2 = "</string>\n</map>"
            r5.append(r2)     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
        L_0x04f7:
            boolean r2 = r3.exists()     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
            if (r2 != 0) goto L_0x0500
            r3.mkdirs()     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
        L_0x0500:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
            r2.<init>(r4)     // Catch:{ Exception -> 0x0529, all -> 0x0525 }
            java.lang.String r3 = r5.toString()     // Catch:{ Exception -> 0x0522 }
            byte[] r3 = r3.getBytes()     // Catch:{ Exception -> 0x0522 }
            r2.write(r3)     // Catch:{ Exception -> 0x0522 }
            r7.createNewFile()     // Catch:{ Exception -> 0x0522 }
            com.uc.webview.export.internal.interfaces.UCMobileWebKit r3 = d     // Catch:{ Exception -> 0x0522 }
            r3.fontDownloadFinished()     // Catch:{ Exception -> 0x0522 }
            if (r1 == 0) goto L_0x051d
            r1.run()     // Catch:{ Exception -> 0x0522 }
        L_0x051d:
            com.uc.webview.export.cyclone.UCCyclone.close(r2)     // Catch:{ Exception -> 0x053b }
            goto L_0x0d75
        L_0x0522:
            r0 = move-exception
            r1 = r0
            goto L_0x052c
        L_0x0525:
            r0 = move-exception
            r1 = r0
            r2 = r12
            goto L_0x0537
        L_0x0529:
            r0 = move-exception
            r1 = r0
            r2 = r12
        L_0x052c:
            java.lang.String r3 = "tag_test_log"
            java.lang.String r4 = "updateTypefacePath"
            com.uc.webview.export.internal.utility.Log.i(r3, r4, r1)     // Catch:{ all -> 0x0535 }
            goto L_0x051d
        L_0x0535:
            r0 = move-exception
            r1 = r0
        L_0x0537:
            com.uc.webview.export.cyclone.UCCyclone.close(r2)     // Catch:{ Exception -> 0x053b }
            throw r1     // Catch:{ Exception -> 0x053b }
        L_0x053b:
            r0 = move-exception
            r1 = r0
            java.lang.String r2 = "tag_test_log"
            java.lang.String r3 = "updateTypefacePath"
            com.uc.webview.export.internal.utility.Log.i(r2, r3, r1)
            goto L_0x0d75
        L_0x0547:
            java.lang.String r1 = "tag_test_log"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "is system webView - "
            r2.<init>(r3)
            java.lang.String r3 = G
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.w(r1, r2)
            return r12
        L_0x055d:
            java.lang.Object[] r1 = new java.lang.Object[r13]
            java.lang.String r3 = "swv"
            r1[r15] = r3
            java.lang.Boolean r3 = java.lang.Boolean.FALSE
            r1[r14] = r3
            java.lang.Object r1 = invoke(r2, r1)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.Object r2 = invoke(r10, r2)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            r3 = 131072(0x20000, double:6.47582E-319)
            if (r2 == 0) goto L_0x05c0
            boolean r2 = l
            if (r2 == 0) goto L_0x0d75
            if (r1 == 0) goto L_0x0d75
            java.lang.String r2 = com.uc.webview.export.Build.UCM_VERSION     // Catch:{ Exception -> 0x05a5 }
            java.lang.String r5 = "\\."
            java.lang.String[] r2 = r2.split(r5)     // Catch:{ Exception -> 0x05a5 }
            r5 = r2[r14]     // Catch:{ Exception -> 0x05a5 }
            int r5 = com.uc.webview.export.internal.utility.j.c(r5)     // Catch:{ Exception -> 0x05a5 }
            r6 = 9
            if (r5 < r6) goto L_0x05ae
            r2 = r2[r13]     // Catch:{ Exception -> 0x05a5 }
            int r2 = com.uc.webview.export.internal.utility.j.c(r2)     // Catch:{ Exception -> 0x05a5 }
            r5 = 13
            if (r2 >= r5) goto L_0x05af
            goto L_0x05ae
        L_0x05a5:
            r0 = move-exception
            r1 = r0
            java.lang.String r2 = "tag_test_log"
            java.lang.String r5 = "checkForceSystemWebViewParam"
            com.uc.webview.export.internal.utility.Log.i(r2, r5, r1)
        L_0x05ae:
            r1 = 0
        L_0x05af:
            if (r1 == 0) goto L_0x0d75
            M = r13
            java.lang.Object[] r1 = new java.lang.Object[r14]
            java.lang.Long r2 = java.lang.Long.valueOf(r3)
            r1[r15] = r2
            invoke(r7, r1)
            goto L_0x0d75
        L_0x05c0:
            if (r1 == 0) goto L_0x05cf
            java.lang.Object[] r1 = new java.lang.Object[r14]
            java.lang.Long r2 = java.lang.Long.valueOf(r3)
            r1[r15] = r2
            invoke(r7, r1)
            goto L_0x0d75
        L_0x05cf:
            r1 = 10002(0x2712, float:1.4016E-41)
            java.lang.Object[] r2 = new java.lang.Object[r14]
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r2[r15] = r3
            invoke(r1, r2)
            goto L_0x0d75
        L_0x05de:
            r2 = r1[r15]
            java.io.File r2 = (java.io.File) r2
            r1 = r1[r14]
            java.io.File[] r1 = (java.io.File[]) r1
            boolean r3 = r2.exists()
            if (r3 == 0) goto L_0x0d75
            boolean r3 = r2.isDirectory()
            if (r3 == 0) goto L_0x0d75
            java.io.File r3 = new java.io.File
            java.lang.String r4 = "libu3player.so"
            r3.<init>(r2, r4)
            boolean r4 = r3.exists()
            if (r4 == 0) goto L_0x0622
            java.lang.String r4 = "libu3player.so"
            boolean r4 = com.uc.webview.export.utility.download.UpdateTask.isFinished(r2, r4)
            if (r4 == 0) goto L_0x0622
            r4 = r1[r15]
            if (r4 == 0) goto L_0x0620
            long r3 = r3.lastModified()
            java.io.File r5 = new java.io.File
            r6 = r1[r15]
            java.lang.String r7 = "libu3player.so"
            r5.<init>(r6, r7)
            long r5 = r5.lastModified()
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto L_0x0622
        L_0x0620:
            r1[r15] = r2
        L_0x0622:
            java.io.File[] r2 = r2.listFiles()
            if (r2 == 0) goto L_0x0d75
            int r3 = r2.length
            r4 = 0
        L_0x062a:
            if (r4 >= r3) goto L_0x0d75
            r5 = r2[r4]
            boolean r6 = r5.isDirectory()
            if (r6 == 0) goto L_0x063f
            r6 = 10037(0x2735, float:1.4065E-41)
            java.lang.Object[] r7 = new java.lang.Object[r13]
            r7[r15] = r5
            r7[r14] = r1
            invoke(r6, r7)
        L_0x063f:
            int r4 = r4 + 1
            goto L_0x062a
        L_0x0642:
            r1 = r1[r15]
            android.content.Context r1 = (android.content.Context) r1
            android.content.Context r2 = e
            if (r2 != 0) goto L_0x0650
            android.content.Context r2 = r1.getApplicationContext()
            e = r2
        L_0x0650:
            java.io.File[] r2 = new java.io.File[r14]     // Catch:{ Throwable -> 0x06ed }
            r2[r15] = r12     // Catch:{ Throwable -> 0x06ed }
            java.io.File r3 = com.uc.webview.export.utility.download.UpdateTask.getUCPlayerRoot(r1)     // Catch:{ Throwable -> 0x06ed }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Throwable -> 0x06ed }
            android.content.pm.ApplicationInfo r1 = r1.getApplicationInfo()     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r1 = r1.nativeLibraryDir     // Catch:{ Throwable -> 0x06ed }
            if (r1 == 0) goto L_0x06da
            java.lang.String r4 = r3.getAbsolutePath()     // Catch:{ Throwable -> 0x06ed }
            boolean r4 = r1.equals(r4)     // Catch:{ Throwable -> 0x06ed }
            if (r4 == 0) goto L_0x06da
            java.lang.Object[] r3 = new java.lang.Object[r14]     // Catch:{ Throwable -> 0x06ed }
            android.content.Context r4 = e     // Catch:{ Throwable -> 0x06ed }
            r3[r15] = r4     // Catch:{ Throwable -> 0x06ed }
            java.lang.Object r3 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r6, r3)     // Catch:{ Throwable -> 0x06ed }
            java.io.File r3 = (java.io.File) r3     // Catch:{ Throwable -> 0x06ed }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r5 = "libffmpeg.so"
            r4.<init>(r1, r5)     // Catch:{ Throwable -> 0x06ed }
            java.io.File r5 = new java.io.File     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r6 = "libffmpeg.so"
            r5.<init>(r3, r6)     // Catch:{ Throwable -> 0x06ed }
            java.io.File r6 = new java.io.File     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r7 = "libffmpeg.so"
            r6.<init>(r3, r7)     // Catch:{ Throwable -> 0x06ed }
            com.uc.webview.export.internal.utility.j.a(r4, r5, r6, r15)     // Catch:{ Throwable -> 0x06ed }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r5 = "libu3player.so"
            r4.<init>(r1, r5)     // Catch:{ Throwable -> 0x06ed }
            java.io.File r5 = new java.io.File     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r6 = "libu3player.so"
            r5.<init>(r3, r6)     // Catch:{ Throwable -> 0x06ed }
            java.io.File r6 = new java.io.File     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r7 = "libu3player.so"
            r6.<init>(r3, r7)     // Catch:{ Throwable -> 0x06ed }
            com.uc.webview.export.internal.utility.j.a(r4, r5, r6, r15)     // Catch:{ Throwable -> 0x06ed }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r5 = "librotate.so"
            r4.<init>(r1, r5)     // Catch:{ Throwable -> 0x06ed }
            java.io.File r5 = new java.io.File     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r6 = "librotate.so"
            r5.<init>(r3, r6)     // Catch:{ Throwable -> 0x06ed }
            java.io.File r6 = new java.io.File     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r7 = "librotate.so"
            r6.<init>(r3, r7)     // Catch:{ Throwable -> 0x06ed }
            com.uc.webview.export.internal.utility.j.a(r4, r5, r6, r15)     // Catch:{ Throwable -> 0x06ed }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r5 = "libinitHelper.so"
            r4.<init>(r1, r5)     // Catch:{ Throwable -> 0x06ed }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r5 = "libinitHelper.so"
            r1.<init>(r3, r5)     // Catch:{ Throwable -> 0x06ed }
            java.io.File r5 = new java.io.File     // Catch:{ Throwable -> 0x06ed }
            java.lang.String r6 = "libinitHelper.so"
            r5.<init>(r3, r6)     // Catch:{ Throwable -> 0x06ed }
            com.uc.webview.export.internal.utility.j.a(r4, r1, r5, r15)     // Catch:{ Throwable -> 0x06ed }
        L_0x06da:
            r1 = 10037(0x2735, float:1.4065E-41)
            java.lang.Object[] r4 = new java.lang.Object[r13]     // Catch:{ Throwable -> 0x06ed }
            r4[r15] = r3     // Catch:{ Throwable -> 0x06ed }
            r4[r14] = r2     // Catch:{ Throwable -> 0x06ed }
            invoke(r1, r4)     // Catch:{ Throwable -> 0x06ed }
            r1 = r2[r15]     // Catch:{ Throwable -> 0x06ed }
            if (r1 != 0) goto L_0x06ea
            return r12
        L_0x06ea:
            r1 = r2[r15]     // Catch:{ Throwable -> 0x06ed }
            return r1
        L_0x06ed:
            r0 = move-exception
            r1 = r0
            java.lang.String r2 = "tag_test_log"
            java.lang.String r3 = "getUCPlayerDir"
            com.uc.webview.export.internal.utility.Log.e(r2, r3, r1)
            return r12
        L_0x06f7:
            r2 = r1[r15]
            android.content.Context r2 = (android.content.Context) r2
            r1 = r1[r14]
            java.lang.String r1 = (java.lang.String) r1
            android.content.Context r3 = e
            if (r3 != 0) goto L_0x0709
            android.content.Context r3 = r2.getApplicationContext()
            e = r3
        L_0x0709:
            android.content.Context r1 = r2.createPackageContext(r1, r13)     // Catch:{ Exception -> 0x077c, all -> 0x0778 }
            java.lang.String r2 = "sdk_2"
            java.io.File r1 = r1.getFileStreamPath(r2)     // Catch:{ Exception -> 0x077c, all -> 0x0778 }
            boolean r2 = r1.exists()     // Catch:{ Exception -> 0x077c, all -> 0x0778 }
            if (r2 == 0) goto L_0x0776
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Exception -> 0x077c, all -> 0x0778 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x077c, all -> 0x0778 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0773 }
            r3 = 500(0x1f4, float:7.0E-43)
            r1.<init>(r2, r3)     // Catch:{ Exception -> 0x0773 }
            java.lang.String r3 = r1.readLine()     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            if (r3 == 0) goto L_0x076b
            java.lang.String r3 = r3.trim()     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            r4.<init>(r3)     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            boolean r4 = r4.exists()     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            if (r4 == 0) goto L_0x076b
            java.lang.String r4 = "tag_test_log"
            java.lang.String r5 = "setupUCPlayerForThin:"
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            java.lang.String r5 = r5.concat(r6)     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            com.uc.webview.export.internal.utility.Log.i(r4, r5)     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            java.lang.String r4 = "com.uc.media.interfaces.IApolloHelper$Global"
            java.lang.ClassLoader r5 = c     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            java.lang.Class r4 = java.lang.Class.forName(r4, r14, r5)     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            java.lang.String r5 = "setApolloSoPath"
            java.lang.Class[] r6 = new java.lang.Class[r14]     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r6[r15] = r7     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            java.lang.Object[] r7 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            r7[r15] = r3     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r4, r5, r6, r7)     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            I = r14     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            java.lang.Boolean r3 = java.lang.Boolean.TRUE     // Catch:{ Exception -> 0x0770, all -> 0x076d }
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
            com.uc.webview.export.cyclone.UCCyclone.close(r2)
            return r3
        L_0x076b:
            r12 = r1
            goto L_0x0786
        L_0x076d:
            r0 = move-exception
            r12 = r1
            goto L_0x0790
        L_0x0770:
            r0 = move-exception
            r12 = r1
            goto L_0x0774
        L_0x0773:
            r0 = move-exception
        L_0x0774:
            r1 = r0
            goto L_0x077f
        L_0x0776:
            r2 = r12
            goto L_0x0786
        L_0x0778:
            r0 = move-exception
            r1 = r0
            r2 = r12
            goto L_0x0791
        L_0x077c:
            r0 = move-exception
            r1 = r0
            r2 = r12
        L_0x077f:
            java.lang.String r3 = "tag_test_log"
            java.lang.String r4 = "setupUCPlayerForThin"
            com.uc.webview.export.internal.utility.Log.i(r3, r4, r1)     // Catch:{ all -> 0x078f }
        L_0x0786:
            com.uc.webview.export.cyclone.UCCyclone.close(r12)
            com.uc.webview.export.cyclone.UCCyclone.close(r2)
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            return r1
        L_0x078f:
            r0 = move-exception
        L_0x0790:
            r1 = r0
        L_0x0791:
            com.uc.webview.export.cyclone.UCCyclone.close(r12)
            com.uc.webview.export.cyclone.UCCyclone.close(r2)
            throw r1
        L_0x0798:
            r1 = r1[r15]
            android.content.Context r1 = (android.content.Context) r1
            android.content.Context r2 = e
            if (r2 != 0) goto L_0x07a6
            android.content.Context r2 = r1.getApplicationContext()
            e = r2
        L_0x07a6:
            boolean r2 = t
            if (r2 == 0) goto L_0x0d75
            boolean r2 = I
            if (r2 != 0) goto L_0x0d75
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.Object r2 = invoke(r10, r2)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x0d75
            int r2 = M
            if (r2 == r13) goto L_0x0d75
            r2 = 10036(0x2734, float:1.4063E-41)
            java.lang.Object[] r3 = new java.lang.Object[r14]
            r3[r15] = r1
            java.lang.Object r1 = invoke(r2, r3)
            java.io.File r1 = (java.io.File) r1
            if (r1 == 0) goto L_0x0d75
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r1.getAbsolutePath()
            r2.append(r3)
            java.lang.String r3 = "/"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "tag_test_log"
            java.lang.String r4 = "ucPlayerDir:"
            java.lang.String r5 = java.lang.String.valueOf(r1)
            java.lang.String r4 = r4.concat(r5)
            com.uc.webview.export.internal.utility.Log.d(r3, r4)
            java.lang.String r3 = "com.uc.apollo.sdk.browser.Settings"
            java.lang.String r4 = "com.uc.apollo.Settings"
            java.lang.String r5 = "com.uc.media.interfaces.IApolloHelper$Global"
            java.lang.String[] r3 = new java.lang.String[]{r3, r4, r5}     // Catch:{ Throwable -> 0x085a, all -> 0x0857 }
            r4 = 0
        L_0x07fe:
            if (r4 == r8) goto L_0x081b
            r5 = r3[r4]     // Catch:{ Throwable -> 0x0818, all -> 0x0857 }
            java.lang.ClassLoader r6 = c     // Catch:{ Throwable -> 0x0818, all -> 0x0857 }
            java.lang.Class r5 = java.lang.Class.forName(r5, r14, r6)     // Catch:{ Throwable -> 0x0818, all -> 0x0857 }
            java.lang.String r6 = "setApolloSoPath"
            java.lang.Class[] r7 = new java.lang.Class[r14]     // Catch:{ Throwable -> 0x0818, all -> 0x0857 }
            java.lang.Class<java.lang.String> r9 = java.lang.String.class
            r7[r15] = r9     // Catch:{ Throwable -> 0x0818, all -> 0x0857 }
            java.lang.Object[] r9 = new java.lang.Object[r14]     // Catch:{ Throwable -> 0x0818, all -> 0x0857 }
            r9[r15] = r2     // Catch:{ Throwable -> 0x0818, all -> 0x0857 }
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r5, r6, r7, r9)     // Catch:{ Throwable -> 0x0818, all -> 0x0857 }
            goto L_0x081b
        L_0x0818:
            int r4 = r4 + 1
            goto L_0x07fe
        L_0x081b:
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x0845, all -> 0x083c }
            java.lang.String r4 = ".lock"
            r3.<init>(r1, r4)     // Catch:{ Throwable -> 0x0845, all -> 0x083c }
            boolean r1 = r3.exists()     // Catch:{ Throwable -> 0x0845, all -> 0x083c }
            if (r1 != 0) goto L_0x0837
            java.io.FileWriter r1 = new java.io.FileWriter     // Catch:{ Throwable -> 0x0845, all -> 0x083c }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x0845, all -> 0x083c }
            java.lang.String r3 = "2.6.0.167"
            r1.write(r3)     // Catch:{ Throwable -> 0x0838, all -> 0x0833 }
            goto L_0x0838
        L_0x0833:
            r0 = move-exception
            r2 = r1
            r1 = r0
            goto L_0x083f
        L_0x0837:
            r1 = r12
        L_0x0838:
            com.uc.webview.export.cyclone.UCCyclone.close(r1)     // Catch:{ Throwable -> 0x0853, all -> 0x0850 }
            goto L_0x0847
        L_0x083c:
            r0 = move-exception
            r1 = r0
            r2 = r12
        L_0x083f:
            com.uc.webview.export.cyclone.UCCyclone.close(r2)     // Catch:{ Throwable -> 0x0843 }
            throw r1     // Catch:{ Throwable -> 0x0843 }
        L_0x0843:
            r0 = move-exception
            goto L_0x0855
        L_0x0845:
            r1 = r12
            goto L_0x0838
        L_0x0847:
            y = r2     // Catch:{ Throwable -> 0x0853, all -> 0x0850 }
            I = r14     // Catch:{ Throwable -> 0x0853, all -> 0x0850 }
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
            goto L_0x0d75
        L_0x0850:
            r0 = move-exception
            r12 = r1
            goto L_0x0858
        L_0x0853:
            r0 = move-exception
            r2 = r1
        L_0x0855:
            r1 = r0
            goto L_0x085d
        L_0x0857:
            r0 = move-exception
        L_0x0858:
            r1 = r0
            goto L_0x087a
        L_0x085a:
            r0 = move-exception
            r1 = r0
            r2 = r12
        L_0x085d:
            java.lang.String r3 = "tag_test_log"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0877 }
            java.lang.String r5 = "setupForUCPlayer:"
            r4.<init>(r5)     // Catch:{ all -> 0x0877 }
            java.lang.ClassLoader r5 = c     // Catch:{ all -> 0x0877 }
            r4.append(r5)     // Catch:{ all -> 0x0877 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0877 }
            com.uc.webview.export.internal.utility.Log.d(r3, r4, r1)     // Catch:{ all -> 0x0877 }
            com.uc.webview.export.cyclone.UCCyclone.close(r2)
            goto L_0x0d75
        L_0x0877:
            r0 = move-exception
            r1 = r0
            r12 = r2
        L_0x087a:
            com.uc.webview.export.cyclone.UCCyclone.close(r12)
            throw r1
        L_0x087e:
            java.lang.Object[] r1 = new java.lang.Object[r13]
            java.lang.String r3 = "apollo"
            r1[r15] = r3
            java.lang.Boolean r3 = java.lang.Boolean.TRUE
            r1[r14] = r3
            java.lang.Object r1 = invoke(r2, r1)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 != 0) goto L_0x08a3
            java.lang.Object[] r2 = new java.lang.Object[r14]
            r5 = 262144(0x40000, double:1.295163E-318)
            java.lang.Long r3 = java.lang.Long.valueOf(r5)
            r2[r15] = r3
            invoke(r7, r2)
            goto L_0x08b3
        L_0x08a3:
            r2 = 10002(0x2712, float:1.4016E-41)
            java.lang.Object[] r3 = new java.lang.Object[r14]
            r5 = 262144(0x40000, double:1.295163E-318)
            java.lang.Long r5 = java.lang.Long.valueOf(r5)
            r3[r15] = r5
            invoke(r2, r3)
        L_0x08b3:
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.Object r2 = invoke(r10, r2)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x0d75
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.Object r2 = invoke(r4, r2)
            com.uc.webview.export.internal.interfaces.IGlobalSettings r2 = (com.uc.webview.export.internal.interfaces.IGlobalSettings) r2
            if (r2 == 0) goto L_0x0d75
            if (r1 != 0) goto L_0x08dd
            java.lang.String r1 = "tag_test_log"
            java.lang.String r3 = "sdk cd forbid apollo"
            com.uc.webview.export.internal.utility.Log.i(r1, r3)
            java.lang.String r1 = "sdk_apollo_forbid"
            java.lang.String r3 = "1"
            r2.setStringValue(r1, r3)
            goto L_0x0d75
        L_0x08dd:
            java.lang.String r1 = "sdk_apollo_forbid"
            java.lang.String r3 = "0"
            r2.setStringValue(r1, r3)
            goto L_0x0d75
        L_0x08e6:
            r2 = r1[r15]
            android.content.Context r2 = (android.content.Context) r2
            r1 = r1[r14]
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            android.content.Context r3 = e
            if (r3 != 0) goto L_0x08fc
            android.content.Context r3 = r2.getApplicationContext()
            e = r3
        L_0x08fc:
            java.lang.Object[] r3 = new java.lang.Object[r15]
            invoke(r11, r3)
            if (r1 != r13) goto L_0x0909
            com.uc.webview.export.internal.android.u r1 = new com.uc.webview.export.internal.android.u
            r1.<init>(r2)
            return r1
        L_0x0909:
            return r12
        L_0x090a:
            r1 = r1[r15]
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.Object r2 = invoke(r9, r2)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 != r13) goto L_0x091d
            return r12
        L_0x091d:
            com.uc.webview.export.WebResourceResponse r1 = com.uc.webview.export.internal.uc.CoreFactory.b(r1)     // Catch:{ Throwable -> 0x0922 }
            return r1
        L_0x0922:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "The getResponseByUrl() is not support in this version."
            r1.<init>(r2)
            throw r1
        L_0x092a:
            r1 = r1[r15]
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.Object r2 = invoke(r9, r2)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 == r13) goto L_0x0d75
            com.uc.webview.export.internal.uc.CoreFactory.a(r1)
            goto L_0x0d75
        L_0x0941:
            boolean r1 = L
            if (r1 == 0) goto L_0x094f
            android.os.Looper r1 = android.os.Looper.myLooper()
            android.os.Looper r2 = android.os.Looper.getMainLooper()
            if (r1 == r2) goto L_0x0d75
        L_0x094f:
            r1 = 10011(0x271b, float:1.4028E-41)
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.Object r1 = invoke(r1, r2)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 != 0) goto L_0x0d75
            boolean r1 = g
            if (r1 == 0) goto L_0x097a
            java.lang.Object[] r1 = new java.lang.Object[r15]
            java.lang.Object r1 = invoke(r10, r1)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 != 0) goto L_0x097a
            com.uc.webview.export.extension.InitCallback r1 = p
            if (r1 == 0) goto L_0x097a
            com.uc.webview.export.extension.InitCallback r1 = p
            r1.notInit()
        L_0x097a:
            boolean r1 = g
            if (r1 == 0) goto L_0x0983
            boolean r1 = com.uc.webview.export.Build.IS_INTERNATIONAL_VERSION
            if (r1 == 0) goto L_0x0983
            return r12
        L_0x0983:
            boolean r1 = q
            if (r1 != 0) goto L_0x0990
            com.uc.webview.export.utility.SetupTask r1 = H
            if (r1 == 0) goto L_0x0990
            com.uc.webview.export.utility.SetupTask r1 = H
            r1.start()
        L_0x0990:
            java.lang.Object[] r1 = new java.lang.Object[r15]
            invoke(r6, r1)
            goto L_0x0d75
        L_0x0997:
            r1 = r1[r15]
            android.content.Context r1 = (android.content.Context) r1
            android.content.Context r2 = e
            if (r2 != 0) goto L_0x09a5
            android.content.Context r2 = r1.getApplicationContext()
            e = r2
        L_0x09a5:
            boolean r2 = T
            if (r2 == 0) goto L_0x09aa
            return r12
        L_0x09aa:
            com.uc.webview.export.internal.uc.wa.a.a(r1)
            T = r14
            goto L_0x0d75
        L_0x09b1:
            r1 = r1[r15]
            java.lang.Runnable r1 = (java.lang.Runnable) r1
            com.uc.webview.export.internal.SDKFactory.d.a(r1)
            goto L_0x0d75
        L_0x09ba:
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            android.os.Looper r2 = android.os.Looper.myLooper()
            if (r1 != r2) goto L_0x09c5
            goto L_0x09c6
        L_0x09c5:
            r14 = 0
        L_0x09c6:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r14)
            return r1
        L_0x09cb:
            java.lang.Object r2 = S
            monitor-enter(r2)
            java.lang.Object r1 = S     // Catch:{ Exception -> 0x09d7 }
            r1.notifyAll()     // Catch:{ Exception -> 0x09d7 }
            goto L_0x09e0
        L_0x09d4:
            r0 = move-exception
            r1 = r0
            goto L_0x09e3
        L_0x09d7:
            r0 = move-exception
            r1 = r0
            java.lang.String r3 = "tag_test_log"
            java.lang.String r4 = "releaseLock"
            com.uc.webview.export.internal.utility.Log.i(r3, r4, r1)     // Catch:{ all -> 0x09d4 }
        L_0x09e0:
            monitor-exit(r2)     // Catch:{ all -> 0x09d4 }
            goto L_0x0d75
        L_0x09e3:
            monitor-exit(r2)     // Catch:{ all -> 0x09d4 }
            throw r1
        L_0x09e5:
            r1 = r1[r15]
            java.lang.Long r1 = (java.lang.Long) r1
            long r1 = r1.longValue()
            java.lang.Object r3 = S
            monitor-enter(r3)
            java.lang.Object r4 = S     // Catch:{ Exception -> 0x09f9 }
            r4.wait(r1)     // Catch:{ Exception -> 0x09f9 }
            goto L_0x0a02
        L_0x09f6:
            r0 = move-exception
            r1 = r0
            goto L_0x0a05
        L_0x09f9:
            r0 = move-exception
            r1 = r0
            java.lang.String r2 = "tag_test_log"
            java.lang.String r4 = "getLock"
            com.uc.webview.export.internal.utility.Log.i(r2, r4, r1)     // Catch:{ all -> 0x09f6 }
        L_0x0a02:
            monitor-exit(r3)     // Catch:{ all -> 0x09f6 }
            goto L_0x0d75
        L_0x0a05:
            monitor-exit(r3)     // Catch:{ all -> 0x09f6 }
            throw r1
        L_0x0a07:
            boolean r2 = K
            if (r2 == 0) goto L_0x0a15
            android.os.Looper r2 = android.os.Looper.myLooper()
            android.os.Looper r3 = android.os.Looper.getMainLooper()
            if (r2 == r3) goto L_0x0d75
        L_0x0a15:
            r1 = r1[r15]
            android.content.Context r1 = (android.content.Context) r1
            android.content.Context r2 = e
            if (r2 != 0) goto L_0x0a23
            android.content.Context r2 = r1.getApplicationContext()
            e = r2
        L_0x0a23:
            boolean r2 = g
            if (r2 != 0) goto L_0x0a7c
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.Object r2 = invoke(r10, r2)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x0a36
            goto L_0x0a7c
        L_0x0a36:
            java.lang.Class<com.uc.webview.export.internal.SDKFactory> r2 = com.uc.webview.export.internal.SDKFactory.class
            monitor-enter(r2)
            boolean r3 = q     // Catch:{ all -> 0x0a78 }
            if (r3 != 0) goto L_0x0a70
            com.uc.webview.export.utility.SetupTask r3 = H     // Catch:{ all -> 0x0a78 }
            if (r3 == 0) goto L_0x0a47
            com.uc.webview.export.utility.SetupTask r1 = H     // Catch:{ all -> 0x0a78 }
            r1.start()     // Catch:{ all -> 0x0a78 }
            goto L_0x0a70
        L_0x0a47:
            com.uc.webview.export.internal.setup.bs r3 = new com.uc.webview.export.internal.setup.bs     // Catch:{ all -> 0x0a78 }
            r3.<init>()     // Catch:{ all -> 0x0a78 }
            java.lang.String r4 = "CONTEXT"
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ all -> 0x0a78 }
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r3.setup(r4, r1)     // Catch:{ all -> 0x0a78 }
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1     // Catch:{ all -> 0x0a78 }
            java.lang.String r3 = "AC"
            java.lang.String r4 = "true"
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.setup(r3, r4)     // Catch:{ all -> 0x0a78 }
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1     // Catch:{ all -> 0x0a78 }
            java.lang.String r3 = "VIDEO_AC"
            java.lang.String r4 = "false"
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.setup(r3, r4)     // Catch:{ all -> 0x0a78 }
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1     // Catch:{ all -> 0x0a78 }
            r1.start()     // Catch:{ all -> 0x0a78 }
        L_0x0a70:
            monitor-exit(r2)     // Catch:{ all -> 0x0a78 }
            java.lang.Object[] r1 = new java.lang.Object[r15]
            invoke(r6, r1)
            goto L_0x0d75
        L_0x0a78:
            r0 = move-exception
            r1 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x0a78 }
            throw r1
        L_0x0a7c:
            return r12
        L_0x0a7d:
            com.uc.webview.export.internal.interfaces.IGlobalSettings r1 = N
            if (r1 == 0) goto L_0x0a84
            com.uc.webview.export.internal.interfaces.IGlobalSettings r1 = N
            return r1
        L_0x0a84:
            java.lang.Object[] r1 = new java.lang.Object[r15]
            java.lang.Object r1 = invoke(r9, r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            if (r1 != r13) goto L_0x0a93
            return r12
        L_0x0a93:
            com.uc.webview.export.internal.interfaces.IGlobalSettings r1 = com.uc.webview.export.internal.uc.CoreFactory.l()
            N = r1
            return r1
        L_0x0a9a:
            r1 = r1[r15]
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            if (r1 == 0) goto L_0x0d75
            M = r1
            r2 = 10025(0x2729, float:1.4048E-41)
            java.lang.Object[] r3 = new java.lang.Object[r15]
            invoke(r2, r3)
            java.lang.String r2 = "i"
            java.lang.String r3 = "SDKFactory"
            com.uc.webview.export.cyclone.UCLogger r2 = com.uc.webview.export.cyclone.UCLogger.create(r2, r3)
            if (r2 == 0) goto L_0x0d75
            java.lang.String r3 = "setCoreType: type="
            java.lang.String r1 = java.lang.String.valueOf(r1)
            java.lang.String r1 = r3.concat(r1)
            java.lang.Throwable[] r3 = new java.lang.Throwable[r15]
            r2.print(r1, r3)
            goto L_0x0d75
        L_0x0ac8:
            java.lang.Object[] r1 = new java.lang.Object[r15]
            invoke(r11, r1)
            int r1 = M
            if (r1 != 0) goto L_0x0ad6
            java.lang.Integer r1 = java.lang.Integer.valueOf(r14)
            return r1
        L_0x0ad6:
            int r1 = M
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            return r1
        L_0x0add:
            r1 = r1[r15]
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            java.lang.Object[] r2 = new java.lang.Object[r15]
            invoke(r11, r2)
            if (r1 != r13) goto L_0x0af2
            com.uc.webview.export.internal.android.f r1 = new com.uc.webview.export.internal.android.f
            r1.<init>()
            return r1
        L_0x0af2:
            com.uc.webview.export.internal.interfaces.IMimeTypeMap r1 = com.uc.webview.export.internal.uc.CoreFactory.o()
            return r1
        L_0x0af7:
            r1 = r1[r15]
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            java.lang.Object[] r2 = new java.lang.Object[r15]
            invoke(r11, r2)
            if (r1 != r13) goto L_0x0b0c
            com.uc.webview.export.internal.android.b r1 = new com.uc.webview.export.internal.android.b
            r1.<init>()
            return r1
        L_0x0b0c:
            com.uc.webview.export.internal.interfaces.IGeolocationPermissions r1 = com.uc.webview.export.internal.uc.CoreFactory.m()
            return r1
        L_0x0b11:
            r1 = r1[r15]
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            java.lang.Object[] r2 = new java.lang.Object[r15]
            invoke(r11, r2)
            if (r1 != r13) goto L_0x0b26
            com.uc.webview.export.internal.android.CookieManagerAndroid r1 = new com.uc.webview.export.internal.android.CookieManagerAndroid
            r1.<init>()
            return r1
        L_0x0b26:
            com.uc.webview.export.internal.interfaces.ICookieManager r1 = com.uc.webview.export.internal.uc.CoreFactory.getCookieManager()
            return r1
        L_0x0b2b:
            r1 = r1[r15]
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            java.lang.Object[] r2 = new java.lang.Object[r15]
            invoke(r11, r2)
            if (r1 != r13) goto L_0x0b40
            com.uc.webview.export.internal.android.q r1 = new com.uc.webview.export.internal.android.q
            r1.<init>()
            return r1
        L_0x0b40:
            com.uc.webview.export.internal.interfaces.IWebStorage r1 = com.uc.webview.export.internal.uc.CoreFactory.n()
            return r1
        L_0x0b45:
            java.lang.Object[] r1 = new java.lang.Object[r15]
            java.lang.Object r1 = invoke(r9, r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            if (r1 != r13) goto L_0x0b54
            return r12
        L_0x0b54:
            com.uc.webview.export.internal.interfaces.UCMobileWebKit r1 = com.uc.webview.export.internal.uc.CoreFactory.getUCMobileWebKit()
            return r1
        L_0x0b59:
            r2 = r1[r15]
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            r1 = r1[r14]
            android.content.Context r1 = (android.content.Context) r1
            android.content.Context r3 = e
            if (r3 != 0) goto L_0x0b6f
            android.content.Context r3 = r1.getApplicationContext()
            e = r3
        L_0x0b6f:
            java.lang.Object[] r3 = new java.lang.Object[r15]
            invoke(r11, r3)
            if (r2 != r13) goto L_0x0b7c
            com.uc.webview.export.internal.android.v r1 = new com.uc.webview.export.internal.android.v
            r1.<init>()
            return r1
        L_0x0b7c:
            com.uc.webview.export.internal.uc.c r2 = new com.uc.webview.export.internal.uc.c
            r2.<init>(r1)
            return r2
        L_0x0b82:
            r2 = r1[r15]
            android.content.Context r2 = (android.content.Context) r2
            r3 = r1[r14]
            com.uc.webview.export.internal.interfaces.IWebView r3 = (com.uc.webview.export.internal.interfaces.IWebView) r3
            r1 = r1[r13]
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            android.content.Context r4 = e
            if (r4 != 0) goto L_0x0b9c
            android.content.Context r2 = r2.getApplicationContext()
            e = r2
        L_0x0b9c:
            java.lang.Object[] r2 = new java.lang.Object[r15]
            invoke(r11, r2)
            if (r1 != r13) goto L_0x0ba4
            return r12
        L_0x0ba4:
            com.uc.webview.export.extension.UCExtension r1 = new com.uc.webview.export.extension.UCExtension
            r1.<init>(r3)
            return r1
        L_0x0baa:
            com.uc.webview.export.internal.uc.startup.StartupTrace.a()
            com.uc.webview.export.internal.setup.aa.a()
            r2 = r1[r15]
            android.content.Context r2 = (android.content.Context) r2
            r4 = r1[r14]
            r18 = r4
            android.util.AttributeSet r18 = (android.util.AttributeSet) r18
            r4 = r1[r13]
            r19 = r4
            com.uc.webview.export.WebView r19 = (com.uc.webview.export.WebView) r19
            r4 = r1[r8]
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r20 = r4.booleanValue()
            r4 = 4
            r4 = r1[r4]
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r4 = r4.booleanValue()
            r1 = r1[r3]
            r21 = r1
            int[] r21 = (int[]) r21
            android.content.Context r1 = e
            if (r1 != 0) goto L_0x0be1
            android.content.Context r1 = r2.getApplicationContext()
            e = r1
        L_0x0be1:
            if (r4 != 0) goto L_0x0bec
            boolean r1 = g
            if (r1 == 0) goto L_0x0bec
            java.lang.Object[] r1 = new java.lang.Object[r15]
            invoke(r11, r1)
        L_0x0bec:
            android.webkit.ValueCallback<android.util.Pair<java.lang.String, java.util.HashMap<java.lang.String, java.lang.String>>> r1 = b
            if (r1 == 0) goto L_0x0bfc
            android.webkit.ValueCallback<android.util.Pair<java.lang.String, java.util.HashMap<java.lang.String, java.lang.String>>> r1 = b
            android.util.Pair r3 = new android.util.Pair
            java.lang.String r5 = "sdk_wv_b"
            r3.<init>(r5, r12)
            r1.onReceiveValue(r3)
        L_0x0bfc:
            com.uc.webview.export.internal.AbstractWebViewFactory r16 = P
            r17 = r2
            com.uc.webview.export.internal.interfaces.IWebView r1 = r16.createWebView(r17, r18, r19, r20, r21)
            android.webkit.ValueCallback<android.util.Pair<java.lang.String, java.util.HashMap<java.lang.String, java.lang.String>>> r2 = b
            if (r2 == 0) goto L_0x0c14
            android.webkit.ValueCallback<android.util.Pair<java.lang.String, java.util.HashMap<java.lang.String, java.lang.String>>> r2 = b
            android.util.Pair r3 = new android.util.Pair
            java.lang.String r5 = "sdk_wv_a"
            r3.<init>(r5, r12)
            r2.onReceiveValue(r3)
        L_0x0c14:
            if (r4 != 0) goto L_0x0c19
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.upload()
        L_0x0c19:
            java.lang.String r2 = "SDKFactory.invoke createWebView"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEventEnd(r2)
            return r1
        L_0x0c1f:
            java.lang.Object[] r1 = new java.lang.Object[r15]
            java.lang.Object r1 = invoke(r10, r1)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x0c38
            java.util.concurrent.ConcurrentLinkedQueue r1 = com.uc.webview.export.internal.SDKFactory.d.a
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x0c38
            goto L_0x0c39
        L_0x0c38:
            r14 = 0
        L_0x0c39:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r14)
            return r1
        L_0x0c3e:
            int r1 = M
            if (r1 == 0) goto L_0x0c43
            goto L_0x0c44
        L_0x0c43:
            r14 = 0
        L_0x0c44:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r14)
            return r1
        L_0x0c49:
            g = r14
            java.lang.String r1 = "i"
            java.lang.String r2 = "SDKFactory"
            com.uc.webview.export.cyclone.UCLogger r1 = com.uc.webview.export.cyclone.UCLogger.create(r1, r2)
            if (r1 == 0) goto L_0x0d75
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "setBrowserFlag: sIsBrowser="
            r2.<init>(r3)
            boolean r3 = g
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.Throwable[] r3 = new java.lang.Throwable[r15]
            r1.print(r2, r3)
            goto L_0x0d75
        L_0x0c6c:
            r1 = r1[r15]
            com.uc.webview.export.internal.AbstractWebViewFactory r1 = (com.uc.webview.export.internal.AbstractWebViewFactory) r1
            P = r1
            goto L_0x0d75
        L_0x0c74:
            r1 = r1[r15]
            java.lang.String r1 = (java.lang.String) r1
            java.util.Map<java.lang.String, java.lang.String> r2 = F
            if (r2 == 0) goto L_0x0c85
            java.util.Map<java.lang.String, java.lang.String> r2 = F
            boolean r1 = r2.containsKey(r1)
            if (r1 == 0) goto L_0x0c85
            goto L_0x0c86
        L_0x0c85:
            r14 = 0
        L_0x0c86:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r14)
            return r1
        L_0x0c8b:
            r2 = r1[r15]
            java.lang.String r2 = (java.lang.String) r2
            r1 = r1[r14]
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            r3 = 10005(0x2715, float:1.402E-41)
            java.lang.Object[] r4 = new java.lang.Object[r14]
            r4[r15] = r2
            java.lang.Object r2 = invoke(r3, r4)
            java.lang.String r2 = (java.lang.String) r2
            if (r2 != 0) goto L_0x0ca6
            goto L_0x0cad
        L_0x0ca6:
            java.lang.String r1 = "true"
            boolean r1 = r1.equalsIgnoreCase(r2)
        L_0x0cad:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            return r1
        L_0x0cb2:
            r1 = r1[r15]
            java.lang.String r1 = (java.lang.String) r1
            java.util.Map<java.lang.String, java.lang.String> r2 = F
            if (r2 != 0) goto L_0x0cbb
            return r12
        L_0x0cbb:
            java.util.Map<java.lang.String, java.lang.String> r2 = F
            java.lang.Object r1 = r2.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            return r1
        L_0x0cc4:
            r1 = r1[r15]
            java.lang.String r1 = (java.lang.String) r1
            if (r1 == 0) goto L_0x0cdf
            java.lang.String r2 = "JSON_CMD"
            boolean r2 = r1.startsWith(r2)
            if (r2 != 0) goto L_0x0cdf
            java.lang.String r2 = "JSON_CMD"
            int r2 = r1.indexOf(r2)
            r3 = -1
            if (r2 == r3) goto L_0x0cdf
            java.lang.String r1 = r1.substring(r2)
        L_0x0cdf:
            boolean r2 = com.uc.webview.export.internal.utility.j.a(r1)
            if (r2 != 0) goto L_0x0cf2
            java.lang.String r2 = "JSON_CMD"
            boolean r2 = r1.startsWith(r2)
            if (r2 == 0) goto L_0x0cf2
            com.uc.webview.export.internal.SDKFactory.c.a(r1)
            goto L_0x0d75
        L_0x0cf2:
            if (r1 == 0) goto L_0x0d09
            java.lang.String r2 = "JSON_CD"
            boolean r2 = r1.startsWith(r2)
            if (r2 != 0) goto L_0x0d09
            java.lang.String r2 = "JSON_CD"
            int r2 = r1.indexOf(r2)
            r3 = -1
            if (r2 == r3) goto L_0x0d09
            java.lang.String r1 = r1.substring(r2)
        L_0x0d09:
            boolean r2 = com.uc.webview.export.internal.utility.j.a(r1)
            if (r2 != 0) goto L_0x0d75
            java.lang.String r2 = "JSON_CD"
            boolean r2 = r1.startsWith(r2)
            if (r2 == 0) goto L_0x0d75
            java.lang.String r2 = "pub_key"
            boolean r2 = r1.contains(r2)
            if (r2 == 0) goto L_0x0d75
            R = r1
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.Object r2 = invoke(r5, r2)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x0d75
            java.lang.String r2 = "com.uc.webview.export.cd.Utils"
            java.lang.String r3 = "addParamCD"
            java.lang.Class[] r4 = new java.lang.Class[r14]     // Catch:{ Exception -> 0x0d75 }
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r4[r15] = r5     // Catch:{ Exception -> 0x0d75 }
            java.lang.Object[] r5 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0d75 }
            r5[r15] = r1     // Catch:{ Exception -> 0x0d75 }
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r2, r3, r4, r5)     // Catch:{ Exception -> 0x0d75 }
            goto L_0x0d75
        L_0x0d41:
            r1 = r1[r15]
            java.lang.Long r1 = (java.lang.Long) r1
            long r1 = r1.longValue()
            long r3 = C
            long r1 = r1 & r3
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 == 0) goto L_0x0d53
            goto L_0x0d54
        L_0x0d53:
            r14 = 0
        L_0x0d54:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r14)
            return r1
        L_0x0d59:
            r1 = r1[r15]
            java.lang.Long r1 = (java.lang.Long) r1
            long r1 = r1.longValue()
            long r3 = C
            long r1 = ~r1
            long r1 = r1 & r3
            C = r1
            goto L_0x0d75
        L_0x0d68:
            r1 = r1[r15]
            java.lang.Long r1 = (java.lang.Long) r1
            long r1 = r1.longValue()
            long r3 = C
            long r1 = r1 | r3
            C = r1
        L_0x0d75:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.SDKFactory.invoke(int, java.lang.Object[]):java.lang.Object");
    }

    public static String a(UCMPackageInfo uCMPackageInfo, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Type:");
        sb.append(str);
        if (uCMPackageInfo.pkgName != null) {
            sb.append("\nPackage Name:");
            sb.append(uCMPackageInfo.pkgName);
        }
        sb.append("\nSo files path:");
        sb.append(uCMPackageInfo.soDirPath);
        sb.append("\nDex files:\n");
        sb.append((String) uCMPackageInfo.sdkShellModule.first);
        sb.append("\n");
        sb.append((String) uCMPackageInfo.browserIFModule.first);
        sb.append("\n");
        sb.append((String) uCMPackageInfo.coreImplModule.first);
        return sb.toString();
    }

    public static void a(String str) {
        h = str;
    }

    public static void a(boolean z2) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setStringValue("apollo_str", z2 ? "ap_cache3=1&ap_cache=1&ap_cache_preload=1&ap_enable_preload2=1&ap_enable_cache2=1&ap_next_buf=7000&ap_max_buf=15000" : "ap_cache3=0&ap_cache=0&ap_cache_preload=0&ap_enable_preload2=0&ap_enable_cache2=0");
        }
    }
}
