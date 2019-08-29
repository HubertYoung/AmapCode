package com.uc.webview.export.internal.setup;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import android.webkit.ValueCallback;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.uc.webview.export.annotations.Reflection;
import com.uc.webview.export.cyclone.UCLogger;
import com.uc.webview.export.internal.setup.UCAsyncTask;
import java.lang.ref.WeakReference;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/* compiled from: ProGuard */
public class UCAsyncTask<RETURN_TYPE extends UCAsyncTask, CALLBACK_TYPE extends UCAsyncTask> implements Runnable {
    protected static final String EVENT_COST = "cost";
    protected static final String EVENT_DIE = "die";
    protected static final String EVENT_EXCEPTION = "exception";
    protected static final String EVENT_GONE = "gone";
    protected static final String EVENT_PAUSE = "pause";
    protected static final String EVENT_PROGRESS = "progress";
    protected static final String EVENT_RESUME = "resume";
    protected static final String EVENT_START = "start";
    protected static final String EVENT_STOP = "stop";
    protected static final String EVENT_SUCCESS = "success";
    public static final int cleanThread = 10008;
    public static final int clearSubTasks = 10012;
    public static final int getBlankString = 10011;
    public static final int getCallback = 10007;
    public static final int getCosts = 10006;
    public static final int getEvent = 10009;
    public static final int getParent = 10005;
    public static final int getParentCnt = 10010;
    public static final int getPercent = 10017;
    public static final int getPriority = 10013;
    public static final int getRootTask = 10015;
    public static final int getTaskCount = 10014;
    public static final int inThread = 10016;
    public static final int isPaused = 10018;
    /* access modifiers changed from: private */
    public static final Boolean n = Boolean.FALSE;
    public static final int post = 10004;
    public static final int setCallbacks = 10002;
    public static final int setParent = 10001;
    public static final int setPriority = 10003;
    /* access modifiers changed from: private */
    public UCAsyncTask a;
    /* access modifiers changed from: private */
    public ConcurrentLinkedQueue<UCAsyncTask> b;
    private int c;
    /* access modifiers changed from: private */
    public final Object d;
    private final Integer e;
    /* access modifiers changed from: private */
    public boolean f;
    /* access modifiers changed from: private */
    public boolean g;
    /* access modifiers changed from: private */
    public final bx h;
    private HandlerThread i;
    /* access modifiers changed from: private */
    public Handler j;
    private String k;
    /* access modifiers changed from: private */
    public long l;
    private Runnable m;
    public ConcurrentHashMap<String, ValueCallback<CALLBACK_TYPE>> mCallbacks;
    protected UCSetupException mException;
    protected UCSetupException mExtraException;
    protected boolean mHasStarted;
    protected int mPercent;
    /* access modifiers changed from: private */
    public Vector<Pair<String, Pair<Long, Long>>> o;

    /* compiled from: ProGuard */
    public class a<CB_TYPE extends UCAsyncTask<CB_TYPE, CB_TYPE>> implements ValueCallback<CB_TYPE> {
        public a() {
        }

        public final /* synthetic */ void onReceiveValue(Object obj) {
            UCAsyncTask.this.callback((String) ((UCAsyncTask) obj).invokeO(10009, new Object[0]));
        }
    }

    /* compiled from: ProGuard */
    public class b<CB_TYPE extends UCAsyncTask<CB_TYPE, CB_TYPE>> implements ValueCallback<CB_TYPE> {
        public b() {
        }

        public final /* synthetic */ void onReceiveValue(Object obj) {
            UCAsyncTask.this.setException(((UCAsyncTask) obj).getException());
        }
    }

    /* compiled from: ProGuard */
    public class c<CB_TYPE extends UCAsyncTask<CB_TYPE, CB_TYPE>> implements ValueCallback<CB_TYPE> {
        public c() {
        }

        public final /* synthetic */ void onReceiveValue(Object obj) {
            UCAsyncTask.this.stop();
        }
    }

    static /* synthetic */ int e(UCAsyncTask uCAsyncTask) {
        int i2 = uCAsyncTask.c + 1;
        uCAsyncTask.c = i2;
        return i2;
    }

    public UCAsyncTask(Integer num) {
        this.c = 0;
        this.d = new Object();
        this.f = false;
        this.g = false;
        this.h = new bx();
        this.l = 0;
        this.mHasStarted = false;
        this.o = n.booleanValue() ? new Vector<>() : null;
        this.e = num;
    }

    public UCAsyncTask(Runnable runnable) {
        this(Integer.valueOf(0));
        this.m = runnable;
    }

    public UCAsyncTask(UCAsyncTask uCAsyncTask) {
        this((Runnable) null);
        invoke(10001, uCAsyncTask);
    }

    public final RETURN_TYPE invoke(int i2, Object... objArr) {
        invokeO(i2, objArr);
        return this;
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v6, types: [int] */
    /* JADX WARNING: type inference failed for: r0v7, types: [int] */
    /* JADX WARNING: type inference failed for: r0v8, types: [boolean] */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0
      assigns: [?[boolean, int, float, short, byte, char], ?[int, float, boolean, short, byte, char, OBJECT, ARRAY], int]
      uses: [int, boolean]
      mth insns count: 129
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
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeO(int r5, java.lang.Object... r6) {
        /*
            r4 = this;
            r0 = 1
            r1 = 10005(0x2715, float:1.402E-41)
            r2 = 0
            r3 = 0
            switch(r5) {
                case 10001: goto L_0x0139;
                case 10002: goto L_0x0111;
                case 10003: goto L_0x0105;
                case 10004: goto L_0x00dd;
                case 10005: goto L_0x00da;
                case 10006: goto L_0x00d7;
                case 10007: goto L_0x00c5;
                case 10008: goto L_0x00b1;
                case 10009: goto L_0x00ae;
                case 10010: goto L_0x0093;
                case 10011: goto L_0x0076;
                case 10012: goto L_0x0064;
                case 10013: goto L_0x0061;
                case 10014: goto L_0x004d;
                case 10015: goto L_0x003a;
                case 10016: goto L_0x002b;
                case 10017: goto L_0x0024;
                case 10018: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x013f
        L_0x000a:
            r5 = 10015(0x271f, float:1.4034E-41)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            java.lang.Object r5 = r4.invokeO(r5, r6)
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = (com.uc.webview.export.internal.setup.UCAsyncTask) r5
            com.uc.webview.export.internal.setup.bx r6 = r5.h
            monitor-enter(r6)
            com.uc.webview.export.internal.setup.bx r5 = r5.h     // Catch:{ all -> 0x0021 }
            boolean r5 = r5.a     // Catch:{ all -> 0x0021 }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x0021 }
            monitor-exit(r6)     // Catch:{ all -> 0x0021 }
            return r5
        L_0x0021:
            r5 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0021 }
            throw r5
        L_0x0024:
            int r5 = r4.mPercent
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            return r5
        L_0x002b:
            java.lang.Thread r5 = java.lang.Thread.currentThread()
            android.os.HandlerThread r6 = r4.i
            if (r5 != r6) goto L_0x0034
            goto L_0x0035
        L_0x0034:
            r0 = 0
        L_0x0035:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r0)
            return r5
        L_0x003a:
            r5 = r4
        L_0x003b:
            java.lang.Object[] r6 = new java.lang.Object[r3]
            java.lang.Object r6 = r5.invokeO(r1, r6)
            if (r6 == 0) goto L_0x004c
            java.lang.Object[] r6 = new java.lang.Object[r3]
            java.lang.Object r5 = r5.invokeO(r1, r6)
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = (com.uc.webview.export.internal.setup.UCAsyncTask) r5
            goto L_0x003b
        L_0x004c:
            return r5
        L_0x004d:
            java.util.concurrent.ConcurrentLinkedQueue<com.uc.webview.export.internal.setup.UCAsyncTask> r5 = r4.b
            if (r5 != 0) goto L_0x0052
            goto L_0x005c
        L_0x0052:
            java.util.concurrent.ConcurrentLinkedQueue<com.uc.webview.export.internal.setup.UCAsyncTask> r5 = r4.b
            int r5 = r5.size()
            int r6 = r4.c
            int r0 = r5 + r6
        L_0x005c:
            java.lang.Integer r5 = java.lang.Integer.valueOf(r0)
            return r5
        L_0x0061:
            java.lang.Integer r5 = r4.e
            return r5
        L_0x0064:
            java.util.concurrent.ConcurrentLinkedQueue<com.uc.webview.export.internal.setup.UCAsyncTask> r5 = r4.b
            if (r5 == 0) goto L_0x006d
            java.util.concurrent.ConcurrentLinkedQueue<com.uc.webview.export.internal.setup.UCAsyncTask> r5 = r4.b
            r5.clear()
        L_0x006d:
            java.lang.String r5 = "UCAsyncTask"
            java.lang.String r6 = "clearSubTasks"
            com.uc.webview.export.internal.utility.Log.d(r5, r6)
            goto L_0x013f
        L_0x0076:
            r5 = r6[r3]
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
        L_0x0083:
            int r0 = r5 + -1
            if (r5 <= 0) goto L_0x008e
            java.lang.String r5 = "    "
            r6.append(r5)
            r5 = r0
            goto L_0x0083
        L_0x008e:
            java.lang.String r5 = r6.toString()
            return r5
        L_0x0093:
            java.lang.Object[] r5 = new java.lang.Object[r3]
            java.lang.Object r5 = r4.invokeO(r1, r5)
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = (com.uc.webview.export.internal.setup.UCAsyncTask) r5
            r6 = 0
        L_0x009c:
            if (r5 == 0) goto L_0x00a9
            int r6 = r6 + 1
            java.lang.Object[] r0 = new java.lang.Object[r3]
            java.lang.Object r5 = r5.invokeO(r1, r0)
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = (com.uc.webview.export.internal.setup.UCAsyncTask) r5
            goto L_0x009c
        L_0x00a9:
            java.lang.Integer r5 = java.lang.Integer.valueOf(r6)
            return r5
        L_0x00ae:
            java.lang.String r5 = r4.k
            return r5
        L_0x00b1:
            r4.b = r2
            r4.j = r2
            android.os.HandlerThread r5 = r4.i
            r5.quit()
            r4.i = r2
            java.lang.String r5 = "UCAsyncTask"
            java.lang.String r6 = "cleanThread"
            com.uc.webview.export.internal.utility.Log.d(r5, r6)
            goto L_0x013f
        L_0x00c5:
            r5 = r6[r3]
            java.lang.String r5 = (java.lang.String) r5
            java.util.concurrent.ConcurrentHashMap<java.lang.String, android.webkit.ValueCallback<CALLBACK_TYPE>> r6 = r4.mCallbacks
            if (r6 != 0) goto L_0x00ce
            return r2
        L_0x00ce:
            java.util.concurrent.ConcurrentHashMap<java.lang.String, android.webkit.ValueCallback<CALLBACK_TYPE>> r6 = r4.mCallbacks
            java.lang.Object r5 = r6.get(r5)
            android.webkit.ValueCallback r5 = (android.webkit.ValueCallback) r5
            return r5
        L_0x00d7:
            java.util.Vector<android.util.Pair<java.lang.String, android.util.Pair<java.lang.Long, java.lang.Long>>> r5 = r4.o
            return r5
        L_0x00da:
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r4.a
            return r5
        L_0x00dd:
            r5 = r6[r3]
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = (com.uc.webview.export.internal.setup.UCAsyncTask) r5
            com.uc.webview.export.internal.setup.UCAsyncTask r6 = r5.a
            if (r6 == r4) goto L_0x00ed
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.String r6 = "Please use \"new UCAsyncTask(parentTask).start()\" instead of \"post(new UCAsyncTask())\" to add sub task."
            r5.<init>(r6)
            throw r5
        L_0x00ed:
            java.lang.Object r0 = r4.d
            monitor-enter(r0)
            java.util.concurrent.ConcurrentLinkedQueue<com.uc.webview.export.internal.setup.UCAsyncTask> r6 = r4.b     // Catch:{ all -> 0x0102 }
            if (r6 != 0) goto L_0x00fb
            java.util.concurrent.ConcurrentLinkedQueue r6 = new java.util.concurrent.ConcurrentLinkedQueue     // Catch:{ all -> 0x0102 }
            r6.<init>()     // Catch:{ all -> 0x0102 }
            r4.b = r6     // Catch:{ all -> 0x0102 }
        L_0x00fb:
            java.util.concurrent.ConcurrentLinkedQueue<com.uc.webview.export.internal.setup.UCAsyncTask> r6 = r4.b     // Catch:{ all -> 0x0102 }
            r6.add(r5)     // Catch:{ all -> 0x0102 }
            monitor-exit(r0)     // Catch:{ all -> 0x0102 }
            goto L_0x013f
        L_0x0102:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0102 }
            throw r5
        L_0x0105:
            r5 = r6[r3]
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            android.os.Process.setThreadPriority(r5)
            goto L_0x013f
        L_0x0111:
            r5 = r6[r3]
            java.util.concurrent.ConcurrentHashMap r5 = (java.util.concurrent.ConcurrentHashMap) r5
            java.util.Set r5 = r5.entrySet()
            java.util.Iterator r5 = r5.iterator()
        L_0x011d:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x013f
            java.lang.Object r6 = r5.next()
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6
            java.lang.Object r0 = r6.getKey()
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r6 = r6.getValue()
            android.webkit.ValueCallback r6 = (android.webkit.ValueCallback) r6
            r4.onEvent(r0, r6)
            goto L_0x011d
        L_0x0139:
            r5 = r6[r3]
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = (com.uc.webview.export.internal.setup.UCAsyncTask) r5
            r4.a = r5
        L_0x013f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.UCAsyncTask.invokeO(int, java.lang.Object[]):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public void callback(String str) {
        StringBuilder sb;
        String str2;
        this.k = str;
        try {
            UCSetupException exception = getException();
            if (!"stat".equals(str)) {
                UCLogger create = UCLogger.create("d", "UCAsyncTask");
                if (create != null) {
                    if (!"cost".equals(str)) {
                        sb = new StringBuilder("callback: ");
                        sb.append(invokeO(10011, (Integer) invokeO(10010, new Object[0])));
                        sb.append(getClass().getSimpleName());
                        sb.append(".");
                        sb.append(str);
                        sb.append(Token.SEPARATOR);
                        sb.append("progress".equals(str) ? invokeO(getPercent, new Object[0]) : "");
                        str2 = (!"exception".equals(str) || exception == null) ? "" : exception.toString();
                    } else if (n.booleanValue()) {
                        Pair lastElement = this.o.lastElement();
                        sb = new StringBuilder("callback: ");
                        sb.append(invokeO(10011, (Integer) invokeO(10010, new Object[0])));
                        sb.append(getClass().getSimpleName());
                        sb.append(".");
                        sb.append(str);
                        sb.append(" cost:");
                        sb.append(String.format("%5s", new Object[]{((Pair) lastElement.second).first}));
                        sb.append(" cost_cpu:");
                        sb.append(String.format("%5s", new Object[]{((Pair) lastElement.second).second}));
                        sb.append(" task:");
                        str2 = (String) lastElement.first;
                    }
                    sb.append(str2);
                    create.print(sb.toString(), new Throwable[0]);
                }
            }
            if ("exception".equals(str) && (this instanceof UCSetupTask) && exception != null) {
                UCLogger create2 = UCLogger.create("w", "UCAsyncTask");
                if (create2 != null) {
                    create2.print("callback: exception: ", exception);
                    if (exception != exception.getRootCause()) {
                        create2.print("callback: rootCause: ", exception.getRootCause());
                    }
                }
            }
        } catch (Throwable unused) {
        }
        Object invokeO = invokeO(10007, str);
        if (invokeO instanceof WeakReference) {
            invokeO = ((WeakReference) invokeO).get();
        }
        if (invokeO instanceof ValueCallback) {
            try {
                ((ValueCallback) invokeO).onReceiveValue(this);
            } catch (Throwable unused2) {
            }
        }
    }

    public RETURN_TYPE start() {
        synchronized (this.d) {
            if (!this.mHasStarted || (this.a == null && this.i == null)) {
                this.mHasStarted = true;
                if (this.a != null) {
                    this.a.invoke(10004, this);
                } else {
                    by byVar = new by(this, UCAsyncTask.class.getSimpleName(), this.e.intValue());
                    this.i = byVar;
                    byVar.start();
                }
            }
        }
        return this;
    }

    public final RETURN_TYPE start(long j2) {
        this.l = j2;
        return start();
    }

    public RETURN_TYPE stop() {
        synchronized (this.h) {
            resume();
            this.g = true;
        }
        return this;
    }

    public RETURN_TYPE pause() {
        UCAsyncTask uCAsyncTask = (UCAsyncTask) invokeO(getRootTask, new Object[0]);
        synchronized (uCAsyncTask.h) {
            if (!uCAsyncTask.h.a) {
                uCAsyncTask.f = true;
            }
        }
        return this;
    }

    public RETURN_TYPE resume() {
        UCAsyncTask uCAsyncTask = (UCAsyncTask) invokeO(getRootTask, new Object[0]);
        synchronized (uCAsyncTask.h) {
            uCAsyncTask.f = false;
            if (uCAsyncTask.h.a) {
                uCAsyncTask.h.a(0, null);
            }
        }
        return this;
    }

    public void run() {
        if (this.m != null) {
            this.m.run();
        }
    }

    public RETURN_TYPE onEvent(String str, ValueCallback<CALLBACK_TYPE> valueCallback) {
        if (str != null) {
            if (this.mCallbacks == null) {
                synchronized (this) {
                    if (this.mCallbacks == null) {
                        this.mCallbacks = new ConcurrentHashMap<>();
                    }
                }
            }
            if (valueCallback == null) {
                this.mCallbacks.remove(str);
            } else {
                this.mCallbacks.put(str, valueCallback);
            }
        }
        return this;
    }

    @Reflection
    public UCSetupException getException() {
        return this.mException;
    }

    public void setException(UCSetupException uCSetupException) {
        this.mException = uCSetupException;
    }

    @Reflection
    public UCSetupException getExtraException() {
        return this.mExtraException;
    }

    public void setExtraException(UCSetupException uCSetupException) {
        this.mExtraException = uCSetupException;
    }
}
