package com.uc.webview.export.utility.download;

import android.content.Context;
import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.sdk.util.e;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import java.util.concurrent.ConcurrentHashMap;

@Api
/* compiled from: ProGuard */
public class DownloadTask implements Runnable {
    private static final ConcurrentHashMap<Integer, Integer> a = new ConcurrentHashMap<>();
    private final Object[] b = new Object[3];
    /* access modifiers changed from: private */
    public final ValueCallback<DownloadTask>[] c = new ValueCallback[10];
    /* access modifiers changed from: private */
    public final String[] d = new String[3];
    private final long[] e = new long[3];
    private ValueCallback<Object[]> f;

    public DownloadTask(Context context, String str, ValueCallback<Object[]> valueCallback) {
        int hashCode = str.hashCode();
        this.b[2] = context;
        synchronized (a) {
            if (a.containsKey(Integer.valueOf(hashCode))) {
                throw new RuntimeException("Duplicate task.");
            }
            a.put(Integer.valueOf(hashCode), Integer.valueOf(hashCode));
        }
        String valueOf = hashCode >= 0 ? String.valueOf(hashCode) : String.valueOf(hashCode).replace('-', '_');
        this.f = valueCallback;
        WaStat.stat((String) IWaStat.DOWNLOAD);
        this.d[0] = str;
        String[] strArr = this.d;
        StringBuilder sb = new StringBuilder();
        sb.append(context.getApplicationContext().getCacheDir().getAbsolutePath());
        sb.append("/ucdown");
        sb.append(valueOf);
        strArr[1] = sb.toString();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            int hashCode = this.d[0].hashCode();
            synchronized (a) {
                a.remove(Integer.valueOf(hashCode));
            }
        } catch (Throwable unused) {
        }
    }

    public DownloadTask onEvent(String str, ValueCallback<DownloadTask> valueCallback) {
        if (str.equals("success")) {
            this.c[0] = valueCallback;
        } else if (str.equals(e.b)) {
            this.c[1] = valueCallback;
        } else if (str.equals("recovered")) {
            this.c[2] = valueCallback;
        } else if (str.equals("progress")) {
            this.c[3] = valueCallback;
        } else if (str.equals(LogCategory.CATEGORY_EXCEPTION)) {
            this.c[4] = valueCallback;
        } else if (str.equals("check")) {
            this.c[5] = valueCallback;
        } else if (str.equals(Performance.KEY_LOG_HEADER)) {
            this.c[6] = valueCallback;
        } else if (str.equals("exists")) {
            this.c[7] = valueCallback;
        } else if (str.equals("beginDownload")) {
            this.c[8] = valueCallback;
        } else if (str.equals("delete")) {
            this.c[9] = valueCallback;
        } else {
            StringBuilder sb = new StringBuilder("The given event:");
            sb.append(str);
            sb.append(" is unknown.");
            throw new RuntimeException(sb.toString());
        }
        return this;
    }

    public Throwable getException() {
        return (Throwable) this.b[1];
    }

    public String getFilePath() {
        return this.d[2];
    }

    public long getTotalSize() {
        return this.e[0];
    }

    public long getCurrentSize() {
        return this.e[1];
    }

    public long getLastModified() {
        return this.e[2];
    }

    public DownloadTask start() {
        this.b[0] = new Thread(this);
        ((Thread) this.b[0]).start();
        return this;
    }

    public DownloadTask stop() {
        this.b[0] = null;
        return this;
    }

    public DownloadTask stopWith(Runnable runnable) {
        this.b[0] = new Thread(new a(this, runnable));
        ((Thread) this.b[0]).start();
        return this;
    }

    public DownloadTask planWith(Runnable runnable) {
        new Thread(new b(this, runnable)).start();
        return this;
    }

    public DownloadTask delete() {
        return delete(false);
    }

    public DownloadTask delete(boolean z) {
        WaStat.stat((String) IWaStat.SHARE_CORE_DELETE_UPD_FILE_PV);
        this.b[0] = new Thread(new c(this, z));
        ((Thread) this.b[0]).start();
        return this;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Can't wrap try/catch for region: R(12:(2:66|67)|68|69|(1:75)|76|77|(2:79|(2:81|82))|83|84|(2:86|(1:88)(7:89|90|91|92|93|94|(7:95|(7:97|98|99|100|101|102|(7:104|105|106|(4:113|114|(1:116)|(1:124)(1:123))|119|(1:121)(0)|124))(1:109)|110|(0)|119|(0)(0)|124)))|152|153) */
    /* JADX WARNING: Can't wrap try/catch for region: R(6:117|118|138|139|141|142) */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:19|20|199|200|(0)|203|204) */
    /* JADX WARNING: Can't wrap try/catch for region: R(8:128|129|146|147|148|149|150|151) */
    /* JADX WARNING: Code restructure failed: missing block: B:125:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0250, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x02bc, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x02de, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x02f1, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:141:0x0252 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:148:0x025d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:150:0x0260 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:176:0x02c9 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:203:0x031d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x00e6 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:68:0x0152 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:76:0x016f */
    /* JADX WARNING: Missing exception handler attribute for start block: B:83:0x01b8 */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0207 A[SYNTHETIC, Splitter:B:113:0x0207] */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x021c A[Catch:{ all -> 0x0211 }] */
    /* JADX WARNING: Removed duplicated region for block: B:157:0x0280 A[Catch:{ Throwable -> 0x031e }] */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x02cd A[Catch:{ Throwable -> 0x02df }] */
    /* JADX WARNING: Removed duplicated region for block: B:185:0x02e1  */
    /* JADX WARNING: Removed duplicated region for block: B:202:0x02fa A[Catch:{ Throwable -> 0x031d }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00f4 A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00f6 A[SYNTHETIC, Splitter:B:42:0x00f6] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0112 A[SYNTHETIC, Splitter:B:53:0x0112] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0193 A[Catch:{ Throwable -> 0x031e }] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x01c3 A[Catch:{ Throwable -> 0x031e }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:150:0x0260=Splitter:B:150:0x0260, B:83:0x01b8=Splitter:B:83:0x01b8, B:203:0x031d=Splitter:B:203:0x031d, B:76:0x016f=Splitter:B:76:0x016f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void run() {
        /*
            r30 = this;
            r1 = r30
            monitor-enter(r30)
            android.os.Looper r2 = android.os.Looper.myLooper()     // Catch:{ all -> 0x033a }
            android.os.Looper r3 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x033a }
            if (r2 != r3) goto L_0x0015
            java.lang.RuntimeException r2 = new java.lang.RuntimeException     // Catch:{ all -> 0x033a }
            java.lang.String r3 = "Download should not run in UI thread."
            r2.<init>(r3)     // Catch:{ all -> 0x033a }
            throw r2     // Catch:{ all -> 0x033a }
        L_0x0015:
            android.webkit.ValueCallback<com.uc.webview.export.utility.download.DownloadTask>[] r2 = r1.c     // Catch:{ all -> 0x033a }
            r3 = 0
            r2 = r2[r3]     // Catch:{ all -> 0x033a }
            android.webkit.ValueCallback<com.uc.webview.export.utility.download.DownloadTask>[] r4 = r1.c     // Catch:{ all -> 0x033a }
            r5 = 1
            r4 = r4[r5]     // Catch:{ all -> 0x033a }
            android.webkit.ValueCallback<com.uc.webview.export.utility.download.DownloadTask>[] r6 = r1.c     // Catch:{ all -> 0x033a }
            r7 = 2
            r6 = r6[r7]     // Catch:{ all -> 0x033a }
            android.webkit.ValueCallback<com.uc.webview.export.utility.download.DownloadTask>[] r8 = r1.c     // Catch:{ all -> 0x033a }
            r9 = 3
            r8 = r8[r9]     // Catch:{ all -> 0x033a }
            android.webkit.ValueCallback<com.uc.webview.export.utility.download.DownloadTask>[] r9 = r1.c     // Catch:{ all -> 0x033a }
            r10 = 4
            r9 = r9[r10]     // Catch:{ all -> 0x033a }
            android.webkit.ValueCallback<com.uc.webview.export.utility.download.DownloadTask>[] r10 = r1.c     // Catch:{ all -> 0x033a }
            r11 = 5
            r10 = r10[r11]     // Catch:{ all -> 0x033a }
            android.webkit.ValueCallback<com.uc.webview.export.utility.download.DownloadTask>[] r12 = r1.c     // Catch:{ all -> 0x033a }
            r13 = 6
            r12 = r12[r13]     // Catch:{ all -> 0x033a }
            android.webkit.ValueCallback<com.uc.webview.export.utility.download.DownloadTask>[] r14 = r1.c     // Catch:{ all -> 0x033a }
            r15 = 7
            r14 = r14[r15]     // Catch:{ all -> 0x033a }
            android.webkit.ValueCallback<com.uc.webview.export.utility.download.DownloadTask>[] r15 = r1.c     // Catch:{ all -> 0x033a }
            r16 = 8
            r15 = r15[r16]     // Catch:{ all -> 0x033a }
            if (r10 == 0) goto L_0x004f
            r10.onReceiveValue(r1)     // Catch:{ Throwable -> 0x0049 }
            goto L_0x004f
        L_0x0049:
            r0 = move-exception
            r2 = r0
            r23 = r9
            goto L_0x0324
        L_0x004f:
            java.lang.Object[] r10 = r1.b     // Catch:{ Throwable -> 0x0320 }
            r13 = 0
            r10[r5] = r13     // Catch:{ Throwable -> 0x0320 }
            java.lang.String[] r10 = r1.d     // Catch:{ Throwable -> 0x0320 }
            r10 = r10[r3]     // Catch:{ Throwable -> 0x0320 }
            java.lang.String[] r11 = r1.d     // Catch:{ Throwable -> 0x0320 }
            r11 = r11[r5]     // Catch:{ Throwable -> 0x0320 }
            android.webkit.ValueCallback<java.lang.Object[]> r7 = r1.f     // Catch:{ Throwable -> 0x0074, Exception -> 0x006e }
            if (r7 == 0) goto L_0x0074
            android.webkit.ValueCallback<java.lang.Object[]> r7 = r1.f     // Catch:{ Throwable -> 0x0074, Exception -> 0x006e }
            java.lang.Object[] r13 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0074, Exception -> 0x006e }
            java.lang.Integer r16 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x0074, Exception -> 0x006e }
            r13[r3] = r16     // Catch:{ Throwable -> 0x0074, Exception -> 0x006e }
            r7.onReceiveValue(r13)     // Catch:{ Throwable -> 0x0074, Exception -> 0x006e }
            goto L_0x0074
        L_0x006e:
            r0 = move-exception
            r2 = r0
            r23 = r9
            goto L_0x02f6
        L_0x0074:
            r7 = 0
            android.util.Pair r13 = com.uc.webview.export.internal.utility.j.a(r10, r7)     // Catch:{ Exception -> 0x02f2 }
            long[] r7 = r1.e     // Catch:{ Throwable -> 0x0320 }
            java.lang.Object r5 = r13.first     // Catch:{ Throwable -> 0x0320 }
            java.lang.Long r5 = (java.lang.Long) r5     // Catch:{ Throwable -> 0x0320 }
            r18 = r4
            long r4 = r5.longValue()     // Catch:{ Throwable -> 0x0320 }
            r7[r3] = r4     // Catch:{ Throwable -> 0x0320 }
            long[] r7 = r1.e     // Catch:{ Throwable -> 0x0320 }
            java.lang.Object r13 = r13.second     // Catch:{ Throwable -> 0x0320 }
            java.lang.Long r13 = (java.lang.Long) r13     // Catch:{ Throwable -> 0x0320 }
            r19 = r4
            long r3 = r13.longValue()     // Catch:{ Throwable -> 0x0320 }
            r5 = 2
            r7[r5] = r3     // Catch:{ Throwable -> 0x0320 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0320 }
            r5.<init>()     // Catch:{ Throwable -> 0x0320 }
            r21 = r14
            r13 = r19
            r5.append(r13)     // Catch:{ Throwable -> 0x0320 }
            java.lang.String r7 = "_"
            r5.append(r7)     // Catch:{ Throwable -> 0x0320 }
            r5.append(r3)     // Catch:{ Throwable -> 0x0320 }
            java.lang.String r3 = r5.toString()     // Catch:{ Throwable -> 0x0320 }
            java.lang.String[] r4 = r1.d     // Catch:{ Throwable -> 0x0320 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0320 }
            r5.<init>()     // Catch:{ Throwable -> 0x0320 }
            r5.append(r11)     // Catch:{ Throwable -> 0x0320 }
            java.lang.String r7 = "/"
            r5.append(r7)     // Catch:{ Throwable -> 0x0320 }
            r5.append(r3)     // Catch:{ Throwable -> 0x0320 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0320 }
            r7 = 2
            r4[r7] = r5     // Catch:{ Throwable -> 0x0320 }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x0320 }
            r4.<init>(r5)     // Catch:{ Throwable -> 0x0320 }
            long[] r5 = r1.e     // Catch:{ Throwable -> 0x0320 }
            r22 = r8
            long r7 = r4.length()     // Catch:{ Throwable -> 0x0320 }
            r16 = 1
            r5[r16] = r7     // Catch:{ Throwable -> 0x0320 }
            int r5 = (r7 > r13 ? 1 : (r7 == r13 ? 0 : -1))
            if (r5 == 0) goto L_0x00df
            r16 = 1
            goto L_0x00e1
        L_0x00df:
            r16 = 0
        L_0x00e1:
            if (r12 == 0) goto L_0x00e6
            r12.onReceiveValue(r1)     // Catch:{ Throwable -> 0x00e6 }
        L_0x00e6:
            java.lang.Object[] r12 = r1.b     // Catch:{ Throwable -> 0x0320 }
            r17 = 0
            r12 = r12[r17]     // Catch:{ Throwable -> 0x0320 }
            r23 = r9
            java.lang.Thread r9 = java.lang.Thread.currentThread()     // Catch:{ Throwable -> 0x031e }
            if (r12 == r9) goto L_0x00f6
            monitor-exit(r30)
            return
        L_0x00f6:
            android.webkit.ValueCallback<java.lang.Object[]> r9 = r1.f     // Catch:{ Throwable -> 0x010e }
            if (r9 == 0) goto L_0x010e
            android.webkit.ValueCallback<java.lang.Object[]> r9 = r1.f     // Catch:{ Throwable -> 0x010e }
            r24 = r2
            r12 = 1
            java.lang.Object[] r2 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x0110 }
            r12 = 2
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ Throwable -> 0x0110 }
            r17 = 0
            r2[r17] = r12     // Catch:{ Throwable -> 0x0110 }
            r9.onReceiveValue(r2)     // Catch:{ Throwable -> 0x0110 }
            goto L_0x0110
        L_0x010e:
            r24 = r2
        L_0x0110:
            if (r16 == 0) goto L_0x02e1
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x031e }
            r2.<init>(r11)     // Catch:{ Throwable -> 0x031e }
            com.uc.webview.export.cyclone.UCCyclone.expectCreateDirFile(r2)     // Catch:{ Throwable -> 0x031e }
            java.io.File[] r2 = r2.listFiles()     // Catch:{ Throwable -> 0x031e }
            int r9 = r2.length     // Catch:{ Throwable -> 0x031e }
            r11 = 0
        L_0x0120:
            if (r11 >= r9) goto L_0x0142
            r12 = r2[r11]     // Catch:{ Throwable -> 0x031e }
            r25 = r2
            java.lang.String r2 = r12.getName()     // Catch:{ Throwable -> 0x031e }
            boolean r2 = r2.equals(r3)     // Catch:{ Throwable -> 0x031e }
            if (r2 != 0) goto L_0x0138
            r26 = r3
            r2 = 0
            r3 = 0
            com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r12, r2, r3)     // Catch:{ Throwable -> 0x031e }
            goto L_0x013b
        L_0x0138:
            r26 = r3
            r3 = 0
        L_0x013b:
            int r11 = r11 + 1
            r2 = r25
            r3 = r26
            goto L_0x0120
        L_0x0142:
            boolean r2 = r4.exists()     // Catch:{ Throwable -> 0x031e }
            if (r2 != 0) goto L_0x014b
            r4.createNewFile()     // Catch:{ Throwable -> 0x031e }
        L_0x014b:
            if (r5 >= 0) goto L_0x027c
            if (r15 == 0) goto L_0x0152
            r15.onReceiveValue(r1)     // Catch:{ Exception -> 0x0152 }
        L_0x0152:
            boolean r2 = com.uc.webview.export.internal.SDKFactory.g     // Catch:{ Throwable -> 0x016f }
            if (r2 != 0) goto L_0x016f
            java.lang.String r2 = "traffic_stat"
            java.lang.String r2 = com.uc.webview.export.extension.UCCore.getParam(r2)     // Catch:{ Throwable -> 0x016f }
            boolean r2 = java.lang.Boolean.parseBoolean(r2)     // Catch:{ Throwable -> 0x016f }
            if (r2 == 0) goto L_0x016f
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x016f }
            r3 = 14
            if (r2 < r3) goto L_0x016f
            r2 = 40962(0xa002, float:5.74E-41)
            android.net.TrafficStats.setThreadStatsTag(r2)     // Catch:{ Throwable -> 0x016f }
        L_0x016f:
            java.net.URL r2 = new java.net.URL     // Catch:{ Throwable -> 0x031e }
            r2.<init>(r10)     // Catch:{ Throwable -> 0x031e }
            java.net.URLConnection r2 = r2.openConnection()     // Catch:{ Throwable -> 0x031e }
            java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2     // Catch:{ Throwable -> 0x031e }
            int r3 = com.uc.webview.export.internal.utility.j.a     // Catch:{ Throwable -> 0x031e }
            r2.setConnectTimeout(r3)     // Catch:{ Throwable -> 0x031e }
            int r3 = com.uc.webview.export.internal.utility.j.b     // Catch:{ Throwable -> 0x031e }
            r2.setReadTimeout(r3)     // Catch:{ Throwable -> 0x031e }
            r3 = 1
            r2.setInstanceFollowRedirects(r3)     // Catch:{ Throwable -> 0x031e }
            java.lang.String r3 = "GET"
            r2.setRequestMethod(r3)     // Catch:{ Throwable -> 0x031e }
            r9 = 0
            int r3 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r3 <= 0) goto L_0x01b8
            java.lang.String r3 = "Range"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x031e }
            java.lang.String r9 = "bytes="
            r5.<init>(r9)     // Catch:{ Throwable -> 0x031e }
            r5.append(r7)     // Catch:{ Throwable -> 0x031e }
            java.lang.String r7 = "-"
            r5.append(r7)     // Catch:{ Throwable -> 0x031e }
            r5.append(r13)     // Catch:{ Throwable -> 0x031e }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x031e }
            r2.setRequestProperty(r3, r5)     // Catch:{ Throwable -> 0x031e }
            java.lang.String r3 = "sdk_dl_r"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r3)     // Catch:{ Throwable -> 0x031e }
            if (r6 == 0) goto L_0x01b8
            r6.onReceiveValue(r1)     // Catch:{ Throwable -> 0x01b8 }
        L_0x01b8:
            r2.connect()     // Catch:{ Throwable -> 0x031e }
            int r3 = r2.getResponseCode()     // Catch:{ Throwable -> 0x031e }
            r5 = 200(0xc8, float:2.8E-43)
            if (r3 < r5) goto L_0x0261
            r5 = 303(0x12f, float:4.25E-43)
            if (r3 <= r5) goto L_0x01c9
            goto L_0x0261
        L_0x01c9:
            java.io.InputStream r3 = r2.getInputStream()     // Catch:{ Throwable -> 0x031e }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ all -> 0x0253 }
            r6 = 1
            r5.<init>(r4, r6)     // Catch:{ all -> 0x0253 }
            r6 = 51200(0xc800, float:7.1746E-41)
            byte[] r6 = new byte[r6]     // Catch:{ all -> 0x0245 }
        L_0x01d8:
            int r7 = r3.read(r6)     // Catch:{ all -> 0x0245 }
            if (r7 <= 0) goto L_0x01ff
            r8 = 0
            r5.write(r6, r8, r7)     // Catch:{ all -> 0x01f7 }
            long[] r8 = r1.e     // Catch:{ all -> 0x01f7 }
            r9 = 1
            r10 = r8[r9]     // Catch:{ all -> 0x01f7 }
            r27 = r2
            r28 = r3
            long r2 = (long) r7
            long r10 = r10 + r2
            r8[r9] = r10     // Catch:{ all -> 0x0211 }
            if (r22 == 0) goto L_0x0203
            r2 = r22
            r2.onReceiveValue(r1)     // Catch:{ Throwable -> 0x0205 }
            goto L_0x0205
        L_0x01f7:
            r0 = move-exception
            r4 = r0
            r29 = r3
            r3 = r2
            r2 = r29
            goto L_0x024c
        L_0x01ff:
            r27 = r2
            r28 = r3
        L_0x0203:
            r2 = r22
        L_0x0205:
            if (r7 <= 0) goto L_0x0218
            long[] r3 = r1.e     // Catch:{ all -> 0x0211 }
            r7 = 1
            r8 = r3[r7]     // Catch:{ all -> 0x0211 }
            int r3 = (r8 > r13 ? 1 : (r8 == r13 ? 0 : -1))
            if (r3 <= 0) goto L_0x021a
            goto L_0x0218
        L_0x0211:
            r0 = move-exception
            r4 = r0
            r3 = r27
            r2 = r28
            goto L_0x024c
        L_0x0218:
            r16 = 0
        L_0x021a:
            if (r16 == 0) goto L_0x022f
            java.lang.Object[] r3 = r1.b     // Catch:{ all -> 0x0211 }
            r7 = 0
            r3 = r3[r7]     // Catch:{ all -> 0x0211 }
            java.lang.Thread r7 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0211 }
            if (r3 == r7) goto L_0x0228
            goto L_0x022f
        L_0x0228:
            r22 = r2
            r2 = r27
            r3 = r28
            goto L_0x01d8
        L_0x022f:
            r5.close()     // Catch:{ Throwable -> 0x0232, all -> 0x0235 }
        L_0x0232:
            r2 = r28
            goto L_0x023c
        L_0x0235:
            r0 = move-exception
            r4 = r0
            r3 = r27
            r2 = r28
            goto L_0x025a
        L_0x023c:
            r2.close()     // Catch:{ Throwable -> 0x023f }
        L_0x023f:
            r3 = r27
            r3.disconnect()     // Catch:{ Throwable -> 0x027e }
            goto L_0x027e
        L_0x0245:
            r0 = move-exception
            r29 = r3
            r3 = r2
            r2 = r29
            r4 = r0
        L_0x024c:
            r5.close()     // Catch:{ Throwable -> 0x0252 }
            goto L_0x0252
        L_0x0250:
            r0 = move-exception
            goto L_0x0259
        L_0x0252:
            throw r4     // Catch:{ all -> 0x0250 }
        L_0x0253:
            r0 = move-exception
            r29 = r3
            r3 = r2
            r2 = r29
        L_0x0259:
            r4 = r0
        L_0x025a:
            r2.close()     // Catch:{ Throwable -> 0x025d }
        L_0x025d:
            r3.disconnect()     // Catch:{ Throwable -> 0x0260 }
        L_0x0260:
            throw r4     // Catch:{ Throwable -> 0x031e }
        L_0x0261:
            com.uc.webview.export.cyclone.UCKnownException r2 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ Throwable -> 0x031e }
            r4 = 4020(0xfb4, float:5.633E-42)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x031e }
            java.lang.String r6 = "httpcode:"
            r5.<init>(r6)     // Catch:{ Throwable -> 0x031e }
            r5.append(r3)     // Catch:{ Throwable -> 0x031e }
            java.lang.String r3 = " not correct."
            r5.append(r3)     // Catch:{ Throwable -> 0x031e }
            java.lang.String r3 = r5.toString()     // Catch:{ Throwable -> 0x031e }
            r2.<init>(r4, r3)     // Catch:{ Throwable -> 0x031e }
            throw r2     // Catch:{ Throwable -> 0x031e }
        L_0x027c:
            r16 = 0
        L_0x027e:
            if (r16 != 0) goto L_0x02df
            long r2 = r4.length()     // Catch:{ Throwable -> 0x031e }
            int r2 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r2 == 0) goto L_0x02bd
            java.lang.String r2 = "sdk_dl_f"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2)     // Catch:{ Throwable -> 0x031e }
            java.lang.Object[] r2 = r1.b     // Catch:{ Throwable -> 0x031e }
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ Throwable -> 0x031e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x031e }
            java.lang.String r6 = "Size mismatch:"
            r5.<init>(r6)     // Catch:{ Throwable -> 0x031e }
            long r6 = r4.length()     // Catch:{ Throwable -> 0x031e }
            r5.append(r6)     // Catch:{ Throwable -> 0x031e }
            java.lang.String r4 = "/"
            r5.append(r4)     // Catch:{ Throwable -> 0x031e }
            r5.append(r13)     // Catch:{ Throwable -> 0x031e }
            java.lang.String r4 = r5.toString()     // Catch:{ Throwable -> 0x031e }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x031e }
            r4 = 1
            r2[r4] = r3     // Catch:{ Throwable -> 0x031e }
            if (r18 == 0) goto L_0x02bb
            r2 = r18
            r2.onReceiveValue(r1)     // Catch:{ Throwable -> 0x02b9 }
            goto L_0x02bb
        L_0x02b9:
            monitor-exit(r30)
            return
        L_0x02bb:
            monitor-exit(r30)
            return
        L_0x02bd:
            java.lang.String r2 = "sdk_dl_s"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2)     // Catch:{ Throwable -> 0x031e }
            if (r24 == 0) goto L_0x02c9
            r2 = r24
            r2.onReceiveValue(r1)     // Catch:{ Throwable -> 0x02c9 }
        L_0x02c9:
            android.webkit.ValueCallback<java.lang.Object[]> r2 = r1.f     // Catch:{ Throwable -> 0x02df }
            if (r2 == 0) goto L_0x02dd
            android.webkit.ValueCallback<java.lang.Object[]> r2 = r1.f     // Catch:{ Throwable -> 0x02df }
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x02df }
            r4 = 5
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x02df }
            r5 = 0
            r3[r5] = r4     // Catch:{ Throwable -> 0x02df }
            r2.onReceiveValue(r3)     // Catch:{ Throwable -> 0x02df }
        L_0x02dd:
            monitor-exit(r30)
            return
        L_0x02df:
            monitor-exit(r30)
            return
        L_0x02e1:
            java.lang.String r2 = "sdk_dl_x"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2)     // Catch:{ Throwable -> 0x031e }
            if (r21 == 0) goto L_0x02f0
            r2 = r21
            r2.onReceiveValue(r1)     // Catch:{ Throwable -> 0x02ee }
            goto L_0x02f0
        L_0x02ee:
            monitor-exit(r30)
            return
        L_0x02f0:
            monitor-exit(r30)
            return
        L_0x02f2:
            r0 = move-exception
            r23 = r9
            r2 = r0
        L_0x02f6:
            android.webkit.ValueCallback<java.lang.Object[]> r3 = r1.f     // Catch:{ Throwable -> 0x031d }
            if (r3 == 0) goto L_0x031d
            android.webkit.ValueCallback<java.lang.Object[]> r3 = r1.f     // Catch:{ Throwable -> 0x031d }
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x031d }
            r5 = 6
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x031d }
            r6 = 0
            r4[r6] = r5     // Catch:{ Throwable -> 0x031d }
            java.lang.Class r5 = r2.getClass()     // Catch:{ Throwable -> 0x031d }
            java.lang.String r5 = r5.getName()     // Catch:{ Throwable -> 0x031d }
            int r5 = r5.hashCode()     // Catch:{ Throwable -> 0x031d }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x031d }
            r6 = 1
            r4[r6] = r5     // Catch:{ Throwable -> 0x031d }
            r3.onReceiveValue(r4)     // Catch:{ Throwable -> 0x031d }
        L_0x031d:
            throw r2     // Catch:{ Throwable -> 0x031e }
        L_0x031e:
            r0 = move-exception
            goto L_0x0323
        L_0x0320:
            r0 = move-exception
            r23 = r9
        L_0x0323:
            r2 = r0
        L_0x0324:
            java.lang.String r3 = "sdk_dl_e"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r3)     // Catch:{ all -> 0x033a }
            java.lang.Object[] r3 = r1.b     // Catch:{ all -> 0x033a }
            r4 = 1
            r3[r4] = r2     // Catch:{ all -> 0x033a }
            if (r23 == 0) goto L_0x0338
            r2 = r23
            r2.onReceiveValue(r1)     // Catch:{ Throwable -> 0x0336 }
            goto L_0x0338
        L_0x0336:
            monitor-exit(r30)
            return
        L_0x0338:
            monitor-exit(r30)
            return
        L_0x033a:
            r0 = move-exception
            r2 = r0
            monitor-exit(r30)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.utility.download.DownloadTask.run():void");
    }
}
