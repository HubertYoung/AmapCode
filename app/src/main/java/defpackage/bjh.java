package defpackage;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import org.apache.http.conn.ConnectTimeoutException;

/* renamed from: bjh reason: default package */
/* compiled from: FileDownloader */
public class bjh {
    private static volatile bjh a;
    private static bpd e;
    private final HashMap<String, bph> b = new HashMap<>();
    private bqa c;
    /* access modifiers changed from: private */
    public final boy d = new boy(new bof());

    /* renamed from: bjh$a */
    /* compiled from: FileDownloader */
    interface a {
        bjf a();
    }

    /* renamed from: bjh$b */
    /* compiled from: FileDownloader */
    static class b implements a {
        bjf a;

        b(bjf bjf) {
            this.a = bjf;
        }

        public final bjf a() {
            return this.a;
        }
    }

    /* renamed from: bjh$c */
    /* compiled from: FileDownloader */
    static class c implements a {
        WeakReference<bjf> a;

        c(bjf bjf) {
            this.a = new WeakReference<>(bjf);
        }

        public final bjf a() {
            return (bjf) this.a.get();
        }
    }

    public static bjh a() {
        if (a == null) {
            synchronized (bjh.class) {
                try {
                    if (a == null) {
                        a = new bjh();
                    }
                }
            }
        }
        return a;
    }

    private bjh() {
        this.d.a(e);
        this.c = new bqa((String) "FileDownloader", 5);
    }

    public final void a(String str) {
        if (bpv.a(3)) {
            bpv.b("FileDownloader", "download request is canceled, path: ".concat(String.valueOf(str)));
        }
        synchronized (this.b) {
            bph remove = this.b.remove(str);
            if (remove != null) {
                remove.cancel();
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(bph bph, a aVar, int i, int i2) {
        if (bpv.a(6)) {
            StringBuilder sb = new StringBuilder("download file error, url:");
            sb.append(bph.getUrl());
            sb.append("errorCode: ");
            sb.append(i);
            sb.append(" statusCode: ");
            sb.append(i2);
            sb.append(" url:");
            sb.append(bph.getUrl());
            bpv.e("FileDownloader", sb.toString());
        }
        bjf a2 = aVar.a();
        if (!bph.isCancelled() && a2 != null) {
            a2.onError(i, i2);
        }
    }

    public final void a(final bjg bjg, bjf bjf) {
        if (bjg != null && !TextUtils.isEmpty(bjg.d)) {
            final String str = bjg.d;
            final a bVar = bjg.a ? new b(bjf) : new c(bjf);
            if (bpv.a(3)) {
                StringBuilder sb = new StringBuilder("start download request, url:");
                sb.append(bjg.getUrl());
                sb.append(", path: ");
                sb.append(str);
                sb.append(", support range:");
                sb.append(bjg.b);
                sb.append(", keep callback:");
                sb.append(bjg.a);
                bpv.b("FileDownloader", sb.toString());
            }
            synchronized (this.b) {
                if (this.b.containsKey(str)) {
                    b(bjg, bVar, -2, -1);
                    return;
                }
                this.b.put(str, bjg);
                this.c.a(new Runnable() {
                    /* JADX WARNING: type inference failed for: r4v0 */
                    /* JADX WARNING: type inference failed for: r13v0, types: [java.io.Closeable] */
                    /* JADX WARNING: type inference failed for: r4v1, types: [java.io.Closeable] */
                    /* JADX WARNING: type inference failed for: r13v1 */
                    /* JADX WARNING: type inference failed for: r4v2 */
                    /* JADX WARNING: type inference failed for: r14v0 */
                    /* JADX WARNING: type inference failed for: r13v2 */
                    /* JADX WARNING: type inference failed for: r4v3 */
                    /* JADX WARNING: type inference failed for: r14v1, types: [java.io.Closeable] */
                    /* JADX WARNING: type inference failed for: r4v4, types: [java.io.Closeable] */
                    /* JADX WARNING: type inference failed for: r13v3 */
                    /* JADX WARNING: type inference failed for: r14v2 */
                    /* JADX WARNING: type inference failed for: r14v3 */
                    /* JADX WARNING: type inference failed for: r13v4 */
                    /* JADX WARNING: type inference failed for: r14v4 */
                    /* JADX WARNING: type inference failed for: r14v7, types: [java.io.Closeable] */
                    /* JADX WARNING: type inference failed for: r13v8, types: [java.io.Closeable] */
                    /* JADX WARNING: type inference failed for: r13v9 */
                    /* JADX WARNING: type inference failed for: r13v10 */
                    /* JADX WARNING: type inference failed for: r14v8 */
                    /* JADX WARNING: type inference failed for: r14v9 */
                    /* JADX WARNING: type inference failed for: r4v16 */
                    /* JADX WARNING: type inference failed for: r14v10 */
                    /* JADX WARNING: type inference failed for: r13v13, types: [java.io.BufferedInputStream, java.io.Closeable] */
                    /* JADX WARNING: type inference failed for: r14v11 */
                    /* JADX WARNING: type inference failed for: r4v17 */
                    /* JADX WARNING: type inference failed for: r14v12 */
                    /* JADX WARNING: type inference failed for: r14v13, types: [java.io.Closeable, java.io.BufferedOutputStream] */
                    /* JADX WARNING: type inference failed for: r4v19 */
                    /* JADX WARNING: type inference failed for: r14v20 */
                    /* JADX WARNING: type inference failed for: r4v38 */
                    /* JADX WARNING: type inference failed for: r4v39 */
                    /* JADX WARNING: type inference failed for: r4v40 */
                    /* JADX WARNING: type inference failed for: r4v41 */
                    /* JADX WARNING: type inference failed for: r4v42 */
                    /* JADX WARNING: type inference failed for: r4v43 */
                    /* JADX WARNING: type inference failed for: r13v24 */
                    /* JADX WARNING: type inference failed for: r4v44 */
                    /* JADX WARNING: type inference failed for: r13v25 */
                    /* JADX WARNING: type inference failed for: r14v21 */
                    /* JADX WARNING: type inference failed for: r14v22 */
                    /* JADX WARNING: type inference failed for: r14v23 */
                    /* JADX WARNING: type inference failed for: r4v45 */
                    /* JADX WARNING: type inference failed for: r13v26 */
                    /* JADX WARNING: type inference failed for: r13v27 */
                    /* JADX WARNING: type inference failed for: r13v28 */
                    /* JADX WARNING: type inference failed for: r13v29 */
                    /* JADX WARNING: type inference failed for: r14v24 */
                    /* JADX WARNING: type inference failed for: r14v25 */
                    /* JADX WARNING: type inference failed for: r14v26 */
                    /* JADX WARNING: type inference failed for: r14v27 */
                    /* JADX WARNING: Code restructure failed: missing block: B:145:?, code lost:
                        r14.flush();
                        defpackage.bjh.a(r5, r1, r11);
                        r13 = r13;
                        r14 = r14;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:148:0x045e, code lost:
                        r0 = move-exception;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:149:0x045f, code lost:
                        r2 = r0;
                        r4 = r14;
                        r13 = r13;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0463, code lost:
                        r0 = th;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0464, code lost:
                        r8 = r2;
                        r14 = r14;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:167:0x04ce, code lost:
                        r0 = move-exception;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:168:0x04cf, code lost:
                        r2 = r0;
                        r14 = 0;
                        r4 = r4;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:169:0x04d2, code lost:
                        r0 = move-exception;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:170:0x04d3, code lost:
                        r2 = r0;
                        r14 = 0;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:171:0x04d6, code lost:
                        r0 = move-exception;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:172:0x04d7, code lost:
                        r2 = r0;
                        r13 = 0;
                        r4 = r4;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:173:0x04db, code lost:
                        r0 = move-exception;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:174:0x04dc, code lost:
                        r2 = r0;
                        r8 = null;
                        r14 = 0;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:175:0x04df, code lost:
                        r12 = -1;
                        r4 = r4;
                        r14 = r14;
                     */
                    /* JADX WARNING: Failed to process nested try/catch */
                    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r13v1
                      assigns: []
                      uses: []
                      mth insns count: 510
                    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
                    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
                    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
                    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
                    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
                    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
                    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
                    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
                    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
                    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
                    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
                    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
                     */
                    /* JADX WARNING: Removed duplicated region for block: B:150:0x0463 A[EDGE_INSN: B:145:?->B:150:0x0463 ?: BREAK  , ExcHandler: Throwable (th java.lang.Throwable), Splitter:B:122:0x03b7] */
                    /* JADX WARNING: Removed duplicated region for block: B:167:0x04ce A[ExcHandler: Throwable (r0v3 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:20:0x009d] */
                    /* JADX WARNING: Removed duplicated region for block: B:171:0x04d6 A[ExcHandler: all (r0v2 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:7:0x002d] */
                    /* JADX WARNING: Removed duplicated region for block: B:184:0x04fd  */
                    /* JADX WARNING: Removed duplicated region for block: B:193:0x0551  */
                    /* JADX WARNING: Removed duplicated region for block: B:23:0x00a3 A[Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }] */
                    /* JADX WARNING: Removed duplicated region for block: B:46:0x0145 A[Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }] */
                    /* JADX WARNING: Removed duplicated region for block: B:52:0x0171 A[Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }] */
                    /* JADX WARNING: Removed duplicated region for block: B:60:0x01eb  */
                    /* JADX WARNING: Unknown variable types count: 17 */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final void run() {
                        /*
                            r23 = this;
                            r1 = r23
                            bjg r2 = r5
                            boolean r2 = r2.isCancelled()
                            r3 = 3
                            if (r2 == 0) goto L_0x002b
                            boolean r2 = defpackage.bpv.a(r3)
                            if (r2 == 0) goto L_0x002a
                            java.lang.String r2 = "FileDownloader"
                            java.lang.StringBuilder r3 = new java.lang.StringBuilder
                            java.lang.String r4 = "download request is canceled, url: "
                            r3.<init>(r4)
                            bjg r4 = r5
                            java.lang.String r4 = r4.getUrl()
                            r3.append(r4)
                            java.lang.String r3 = r3.toString()
                            defpackage.bpv.b(r2, r3)
                        L_0x002a:
                            return
                        L_0x002b:
                            r2 = -1
                            r4 = 0
                            bjg r5 = r5     // Catch:{ Throwable -> 0x04db, all -> 0x04d6 }
                            boolean r5 = r5.b     // Catch:{ Throwable -> 0x04db, all -> 0x04d6 }
                            r6 = 0
                            if (r5 == 0) goto L_0x0087
                            java.lang.String r8 = r0     // Catch:{ Throwable -> 0x04db, all -> 0x04d6 }
                            java.io.File r8 = defpackage.bjh.b(r8)     // Catch:{ Throwable -> 0x04db, all -> 0x04d6 }
                            boolean r9 = r8.exists()     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            if (r9 == 0) goto L_0x0088
                            long r9 = r8.length()     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            bjg r11 = r5     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            java.lang.String r12 = "RANGE"
                            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            java.lang.String r14 = "bytes="
                            r13.<init>(r14)     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            r13.append(r9)     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            java.lang.String r14 = "-"
                            r13.append(r14)     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            java.lang.String r13 = r13.toString()     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            r11.addHeader(r12, r13)     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            boolean r11 = defpackage.bpv.a(r3)     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            if (r11 == 0) goto L_0x0089
                            java.lang.String r11 = "FileDownloader"
                            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            java.lang.String r13 = "download request, url :"
                            r12.<init>(r13)     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            bjg r13 = r5     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            java.lang.String r13 = r13.getUrl()     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            r12.append(r13)     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            java.lang.String r13 = ", range begin: "
                            r12.append(r13)     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            r12.append(r9)     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            java.lang.String r12 = r12.toString()     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            defpackage.bpv.b(r11, r12)     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            goto L_0x0089
                        L_0x0087:
                            r8 = r4
                        L_0x0088:
                            r9 = r6
                        L_0x0089:
                            bjh r11 = defpackage.bjh.this     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            boy r11 = r11.d     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            bjg r12 = r5     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            java.lang.Class<com.autonavi.core.network.inter.response.InputStreamResponse> r13 = com.autonavi.core.network.inter.response.InputStreamResponse.class
                            bpk r11 = r11.a(r12, r13)     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            com.autonavi.core.network.inter.response.InputStreamResponse r11 = (com.autonavi.core.network.inter.response.InputStreamResponse) r11     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            int r12 = r11.getStatusCode()     // Catch:{ Throwable -> 0x04d2, all -> 0x04d6 }
                            boolean r13 = defpackage.bpv.a(r3)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            if (r13 == 0) goto L_0x00c4
                            java.lang.String r13 = "FileDownloader"
                            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.lang.String r15 = "download request, url: "
                            r14.<init>(r15)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            bjg r15 = r5     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.lang.String r15 = r15.getUrl()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            r14.append(r15)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.lang.String r15 = ", statusCode: "
                            r14.append(r15)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            r14.append(r12)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.lang.String r14 = r14.toString()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            defpackage.bpv.b(r13, r14)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                        L_0x00c4:
                            r13 = -3
                            if (r5 == 0) goto L_0x0139
                            int r14 = defpackage.bok.e     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            if (r12 != r14) goto L_0x0139
                            if (r8 == 0) goto L_0x00e3
                            boolean r2 = r8.exists()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            if (r2 == 0) goto L_0x00e3
                            long r9 = r8.length()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            int r2 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
                            if (r2 <= 0) goto L_0x00e3
                            bjg r2 = r5     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            bjh$a r5 = r1     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            defpackage.bjh.a(r2, r5, r11)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            goto L_0x00ea
                        L_0x00e3:
                            bjg r2 = r5     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            bjh$a r5 = r1     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            defpackage.bjh.b(r2, r5, r13, r12)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                        L_0x00ea:
                            boolean r2 = defpackage.bpv.a(r3)
                            if (r2 == 0) goto L_0x012b
                            java.lang.String r2 = "FileDownloader"
                            java.lang.StringBuilder r3 = new java.lang.StringBuilder
                            java.lang.String r5 = "finish download request, url:"
                            r3.<init>(r5)
                            bjg r5 = r5
                            java.lang.String r5 = r5.getUrl()
                            r3.append(r5)
                            java.lang.String r5 = ", path: "
                            r3.append(r5)
                            java.lang.String r5 = r0
                            r3.append(r5)
                            java.lang.String r5 = ", support range:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.b
                            r3.append(r5)
                            java.lang.String r5 = ", keep callback:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.a
                            r3.append(r5)
                            java.lang.String r3 = r3.toString()
                            defpackage.bpv.b(r2, r3)
                        L_0x012b:
                            bjh r2 = defpackage.bjh.this
                            java.lang.String r3 = r0
                            defpackage.bjh.a(r2, r3)
                            defpackage.bow.a(r4)
                            defpackage.bow.a(r4)
                            return
                        L_0x0139:
                            if (r5 == 0) goto L_0x01ed
                            r14 = 206(0xce, float:2.89E-43)
                            if (r12 == r14) goto L_0x01ed
                            boolean r14 = defpackage.bpv.a(r3)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            if (r14 == 0) goto L_0x0163
                            java.lang.String r14 = "FileDownloader"
                            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.lang.String r6 = "download request, url:"
                            r15.<init>(r6)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            bjg r6 = r5     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.lang.String r6 = r6.getUrl()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            r15.append(r6)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.lang.String r6 = ", server can not support range!"
                            r15.append(r6)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.lang.String r6 = r15.toString()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            defpackage.bpv.b(r14, r6)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                        L_0x0163:
                            if (r8 == 0) goto L_0x01ed
                            boolean r6 = r8.exists()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            if (r6 == 0) goto L_0x01ed
                            boolean r6 = r8.delete()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            if (r6 != 0) goto L_0x01eb
                            bjg r5 = r5     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            bjh$a r6 = r1     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            defpackage.bjh.b(r5, r6, r2, r12)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            boolean r2 = defpackage.bpv.a(r3)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            if (r2 == 0) goto L_0x019c
                            java.lang.String r2 = "FileDownloader"
                            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.lang.String r6 = "download request, url:"
                            r5.<init>(r6)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            bjg r6 = r5     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.lang.String r6 = r6.getUrl()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            r5.append(r6)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.lang.String r6 = "server can not support range, delete local file fail!"
                            r5.append(r6)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            defpackage.bpv.b(r2, r5)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                        L_0x019c:
                            boolean r2 = defpackage.bpv.a(r3)
                            if (r2 == 0) goto L_0x01dd
                            java.lang.String r2 = "FileDownloader"
                            java.lang.StringBuilder r3 = new java.lang.StringBuilder
                            java.lang.String r5 = "finish download request, url:"
                            r3.<init>(r5)
                            bjg r5 = r5
                            java.lang.String r5 = r5.getUrl()
                            r3.append(r5)
                            java.lang.String r5 = ", path: "
                            r3.append(r5)
                            java.lang.String r5 = r0
                            r3.append(r5)
                            java.lang.String r5 = ", support range:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.b
                            r3.append(r5)
                            java.lang.String r5 = ", keep callback:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.a
                            r3.append(r5)
                            java.lang.String r3 = r3.toString()
                            defpackage.bpv.b(r2, r3)
                        L_0x01dd:
                            bjh r2 = defpackage.bjh.this
                            java.lang.String r3 = r0
                            defpackage.bjh.a(r2, r3)
                            defpackage.bow.a(r4)
                            defpackage.bow.a(r4)
                            return
                        L_0x01eb:
                            r9 = 0
                        L_0x01ed:
                            r6 = 304(0x130, float:4.26E-43)
                            if (r12 != r6) goto L_0x0247
                            bjg r2 = r5     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            bjh$a r5 = r1     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            defpackage.bjh.a(r2, r5, r11)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            boolean r2 = defpackage.bpv.a(r3)
                            if (r2 == 0) goto L_0x0239
                            java.lang.String r2 = "FileDownloader"
                            java.lang.StringBuilder r3 = new java.lang.StringBuilder
                            java.lang.String r5 = "finish download request, url:"
                            r3.<init>(r5)
                            bjg r5 = r5
                            java.lang.String r5 = r5.getUrl()
                            r3.append(r5)
                            java.lang.String r5 = ", path: "
                            r3.append(r5)
                            java.lang.String r5 = r0
                            r3.append(r5)
                            java.lang.String r5 = ", support range:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.b
                            r3.append(r5)
                            java.lang.String r5 = ", keep callback:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.a
                            r3.append(r5)
                            java.lang.String r3 = r3.toString()
                            defpackage.bpv.b(r2, r3)
                        L_0x0239:
                            bjh r2 = defpackage.bjh.this
                            java.lang.String r3 = r0
                            defpackage.bjh.a(r2, r3)
                            defpackage.bow.a(r4)
                            defpackage.bow.a(r4)
                            return
                        L_0x0247:
                            r6 = 400(0x190, float:5.6E-43)
                            if (r12 < r6) goto L_0x02a1
                            bjg r2 = r5     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            bjh$a r5 = r1     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            defpackage.bjh.b(r2, r5, r13, r12)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            boolean r2 = defpackage.bpv.a(r3)
                            if (r2 == 0) goto L_0x0293
                            java.lang.String r2 = "FileDownloader"
                            java.lang.StringBuilder r3 = new java.lang.StringBuilder
                            java.lang.String r5 = "finish download request, url:"
                            r3.<init>(r5)
                            bjg r5 = r5
                            java.lang.String r5 = r5.getUrl()
                            r3.append(r5)
                            java.lang.String r5 = ", path: "
                            r3.append(r5)
                            java.lang.String r5 = r0
                            r3.append(r5)
                            java.lang.String r5 = ", support range:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.b
                            r3.append(r5)
                            java.lang.String r5 = ", keep callback:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.a
                            r3.append(r5)
                            java.lang.String r3 = r3.toString()
                            defpackage.bpv.b(r2, r3)
                        L_0x0293:
                            bjh r2 = defpackage.bjh.this
                            java.lang.String r3 = r0
                            defpackage.bjh.a(r2, r3)
                            defpackage.bow.a(r4)
                            defpackage.bow.a(r4)
                            return
                        L_0x02a1:
                            long r6 = r11.getContentLength()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            r14 = 0
                            long r6 = r6 + r9
                            java.io.InputStream r14 = r11.getBodyInputStream()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            if (r14 == 0) goto L_0x0470
                            bjg r13 = r5     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            boolean r13 = r13.isCancelled()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            if (r13 != 0) goto L_0x0318
                            bjh$a r13 = r1     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            bjf r13 = r13.a()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            if (r13 == 0) goto L_0x02c9
                            java.util.Map r15 = r11.getHeaders()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            int r2 = r11.getStatusCode()     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            r13.onStart(r6, r15, r2)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            goto L_0x0318
                        L_0x02c9:
                            boolean r2 = defpackage.bpv.a(r3)
                            if (r2 == 0) goto L_0x030a
                            java.lang.String r2 = "FileDownloader"
                            java.lang.StringBuilder r3 = new java.lang.StringBuilder
                            java.lang.String r5 = "finish download request, url:"
                            r3.<init>(r5)
                            bjg r5 = r5
                            java.lang.String r5 = r5.getUrl()
                            r3.append(r5)
                            java.lang.String r5 = ", path: "
                            r3.append(r5)
                            java.lang.String r5 = r0
                            r3.append(r5)
                            java.lang.String r5 = ", support range:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.b
                            r3.append(r5)
                            java.lang.String r5 = ", keep callback:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.a
                            r3.append(r5)
                            java.lang.String r3 = r3.toString()
                            defpackage.bpv.b(r2, r3)
                        L_0x030a:
                            bjh r2 = defpackage.bjh.this
                            java.lang.String r3 = r0
                            defpackage.bjh.a(r2, r3)
                            defpackage.bow.a(r4)
                            defpackage.bow.a(r4)
                            return
                        L_0x0318:
                            if (r8 != 0) goto L_0x0392
                            java.lang.String r2 = r0     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            java.io.File r2 = defpackage.bjh.b(r2)     // Catch:{ Throwable -> 0x04ce, all -> 0x04d6 }
                            if (r5 != 0) goto L_0x0393
                            boolean r8 = r2.exists()     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            if (r8 == 0) goto L_0x0393
                            boolean r8 = r2.delete()     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            if (r8 != 0) goto L_0x0393
                            boolean r5 = defpackage.bpv.a(r3)     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            if (r5 == 0) goto L_0x033b
                            java.lang.String r5 = "FileDownloader"
                            java.lang.String r6 = "download file,  unsupported range, delete local file fail!"
                            defpackage.bpv.b(r5, r6)     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                        L_0x033b:
                            bjg r5 = r5     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            bjh$a r6 = r1     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            r7 = -1
                            defpackage.bjh.b(r5, r6, r7, r12)     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            boolean r2 = defpackage.bpv.a(r3)
                            if (r2 == 0) goto L_0x0384
                            java.lang.String r2 = "FileDownloader"
                            java.lang.StringBuilder r3 = new java.lang.StringBuilder
                            java.lang.String r5 = "finish download request, url:"
                            r3.<init>(r5)
                            bjg r5 = r5
                            java.lang.String r5 = r5.getUrl()
                            r3.append(r5)
                            java.lang.String r5 = ", path: "
                            r3.append(r5)
                            java.lang.String r5 = r0
                            r3.append(r5)
                            java.lang.String r5 = ", support range:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.b
                            r3.append(r5)
                            java.lang.String r5 = ", keep callback:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.a
                            r3.append(r5)
                            java.lang.String r3 = r3.toString()
                            defpackage.bpv.b(r2, r3)
                        L_0x0384:
                            bjh r2 = defpackage.bjh.this
                            java.lang.String r3 = r0
                            defpackage.bjh.a(r2, r3)
                            defpackage.bow.a(r4)
                            defpackage.bow.a(r4)
                            return
                        L_0x0392:
                            r2 = r8
                        L_0x0393:
                            if (r5 == 0) goto L_0x03a4
                            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            java.lang.String r13 = r0     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            r15 = 1
                            r8.<init>(r13, r15)     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            goto L_0x03ab
                        L_0x039e:
                            r0 = move-exception
                            r8 = r2
                            r14 = r4
                        L_0x03a1:
                            r2 = r0
                            goto L_0x04e0
                        L_0x03a4:
                            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            java.lang.String r13 = r0     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            r8.<init>(r13)     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                        L_0x03ab:
                            java.io.BufferedInputStream r13 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            r13.<init>(r14)     // Catch:{ Throwable -> 0x039e, all -> 0x04d6 }
                            java.io.BufferedOutputStream r14 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x046a, all -> 0x0466 }
                            r14.<init>(r8)     // Catch:{ Throwable -> 0x046a, all -> 0x0466 }
                            r4 = 4096(0x1000, float:5.74E-42)
                            byte[] r4 = new byte[r4]     // Catch:{ Throwable -> 0x0463, all -> 0x045e }
                            r16 = 0
                        L_0x03bb:
                            int r8 = r13.read(r4)     // Catch:{ Throwable -> 0x0463, all -> 0x045e }
                            r15 = -1
                            if (r8 == r15) goto L_0x044f
                            bjh$a r15 = r1     // Catch:{ Throwable -> 0x0463, all -> 0x045e }
                            bjf r15 = r15.a()     // Catch:{ Throwable -> 0x0463, all -> 0x045e }
                            if (r15 == 0) goto L_0x03f4
                            bjg r3 = r5     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            boolean r3 = r3.isCancelled()     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            if (r3 != 0) goto L_0x03f4
                            r3 = 0
                            r14.write(r4, r3, r8)     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            r18 = r4
                            long r3 = (long) r8     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            long r9 = r9 + r3
                            long r3 = android.os.SystemClock.uptimeMillis()     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            r8 = 0
                            long r19 = r3 - r16
                            bjg r8 = r5     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            r21 = r3
                            long r3 = r8.c     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            int r3 = (r19 > r3 ? 1 : (r19 == r3 ? 0 : -1))
                            if (r3 < 0) goto L_0x03f0
                            r15.onProgressUpdate(r9, r6)     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            r16 = r21
                        L_0x03f0:
                            r4 = r18
                            r3 = 3
                            goto L_0x03bb
                        L_0x03f4:
                            if (r5 != 0) goto L_0x03ff
                            boolean r3 = r2.exists()     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            if (r3 == 0) goto L_0x03ff
                            r2.delete()     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                        L_0x03ff:
                            r2 = 3
                            boolean r2 = defpackage.bpv.a(r2)
                            if (r2 == 0) goto L_0x0441
                            java.lang.String r2 = "FileDownloader"
                            java.lang.StringBuilder r3 = new java.lang.StringBuilder
                            java.lang.String r4 = "finish download request, url:"
                            r3.<init>(r4)
                            bjg r4 = r5
                            java.lang.String r4 = r4.getUrl()
                            r3.append(r4)
                            java.lang.String r4 = ", path: "
                            r3.append(r4)
                            java.lang.String r4 = r0
                            r3.append(r4)
                            java.lang.String r4 = ", support range:"
                            r3.append(r4)
                            bjg r4 = r5
                            boolean r4 = r4.b
                            r3.append(r4)
                            java.lang.String r4 = ", keep callback:"
                            r3.append(r4)
                            bjg r4 = r5
                            boolean r4 = r4.a
                            r3.append(r4)
                            java.lang.String r3 = r3.toString()
                            defpackage.bpv.b(r2, r3)
                        L_0x0441:
                            bjh r2 = defpackage.bjh.this
                            java.lang.String r3 = r0
                            defpackage.bjh.a(r2, r3)
                            defpackage.bow.a(r14)
                            defpackage.bow.a(r13)
                            return
                        L_0x044f:
                            r14.flush()     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            bjg r3 = r5     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            bjh$a r4 = r1     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            defpackage.bjh.a(r3, r4, r11)     // Catch:{ Throwable -> 0x0463, all -> 0x045a }
                            goto L_0x0479
                        L_0x045a:
                            r0 = move-exception
                            r2 = r0
                            goto L_0x0549
                        L_0x045e:
                            r0 = move-exception
                            r2 = r0
                            r4 = r14
                            goto L_0x054b
                        L_0x0463:
                            r0 = move-exception
                            r8 = r2
                            goto L_0x046d
                        L_0x0466:
                            r0 = move-exception
                            r2 = r0
                            goto L_0x054b
                        L_0x046a:
                            r0 = move-exception
                            r8 = r2
                            r14 = r4
                        L_0x046d:
                            r4 = r13
                            goto L_0x03a1
                        L_0x0470:
                            bjg r2 = r5     // Catch:{ Throwable -> 0x04ce, all -> 0x04c9 }
                            bjh$a r3 = r1     // Catch:{ Throwable -> 0x04ce, all -> 0x04c9 }
                            defpackage.bjh.b(r2, r3, r13, r12)     // Catch:{ Throwable -> 0x04ce, all -> 0x04c9 }
                            r13 = r4
                            r14 = r13
                        L_0x0479:
                            r2 = 3
                            boolean r2 = defpackage.bpv.a(r2)
                            if (r2 == 0) goto L_0x04bb
                            java.lang.String r2 = "FileDownloader"
                            java.lang.StringBuilder r3 = new java.lang.StringBuilder
                            java.lang.String r4 = "finish download request, url:"
                            r3.<init>(r4)
                            bjg r4 = r5
                            java.lang.String r4 = r4.getUrl()
                            r3.append(r4)
                            java.lang.String r4 = ", path: "
                            r3.append(r4)
                            java.lang.String r4 = r0
                            r3.append(r4)
                            java.lang.String r4 = ", support range:"
                            r3.append(r4)
                            bjg r4 = r5
                            boolean r4 = r4.b
                            r3.append(r4)
                            java.lang.String r4 = ", keep callback:"
                            r3.append(r4)
                            bjg r4 = r5
                            boolean r4 = r4.a
                            r3.append(r4)
                            java.lang.String r3 = r3.toString()
                            defpackage.bpv.b(r2, r3)
                        L_0x04bb:
                            bjh r2 = defpackage.bjh.this
                            java.lang.String r3 = r0
                            defpackage.bjh.a(r2, r3)
                            defpackage.bow.a(r14)
                            defpackage.bow.a(r13)
                            return
                        L_0x04c9:
                            r0 = move-exception
                            r2 = r0
                            r13 = r4
                            goto L_0x054a
                        L_0x04ce:
                            r0 = move-exception
                            r2 = r0
                            r14 = r4
                            goto L_0x04e0
                        L_0x04d2:
                            r0 = move-exception
                            r2 = r0
                            r14 = r4
                            goto L_0x04df
                        L_0x04d6:
                            r0 = move-exception
                            r2 = r0
                            r13 = r4
                            goto L_0x054b
                        L_0x04db:
                            r0 = move-exception
                            r2 = r0
                            r8 = r4
                            r14 = r8
                        L_0x04df:
                            r12 = -1
                        L_0x04e0:
                            int r2 = defpackage.bjh.a(r2)     // Catch:{ all -> 0x0546 }
                            bjg r3 = r5     // Catch:{ all -> 0x0546 }
                            bjh$a r5 = r1     // Catch:{ all -> 0x0546 }
                            defpackage.bjh.b(r3, r5, r2, r12)     // Catch:{ all -> 0x0546 }
                            if (r8 == 0) goto L_0x04f6
                            bjg r2 = r5     // Catch:{ all -> 0x0546 }
                            boolean r2 = r2.b     // Catch:{ all -> 0x0546 }
                            if (r2 != 0) goto L_0x04f6
                            r8.delete()     // Catch:{ all -> 0x0546 }
                        L_0x04f6:
                            r2 = 3
                            boolean r2 = defpackage.bpv.a(r2)
                            if (r2 == 0) goto L_0x0538
                            java.lang.String r2 = "FileDownloader"
                            java.lang.StringBuilder r3 = new java.lang.StringBuilder
                            java.lang.String r5 = "finish download request, url:"
                            r3.<init>(r5)
                            bjg r5 = r5
                            java.lang.String r5 = r5.getUrl()
                            r3.append(r5)
                            java.lang.String r5 = ", path: "
                            r3.append(r5)
                            java.lang.String r5 = r0
                            r3.append(r5)
                            java.lang.String r5 = ", support range:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.b
                            r3.append(r5)
                            java.lang.String r5 = ", keep callback:"
                            r3.append(r5)
                            bjg r5 = r5
                            boolean r5 = r5.a
                            r3.append(r5)
                            java.lang.String r3 = r3.toString()
                            defpackage.bpv.b(r2, r3)
                        L_0x0538:
                            bjh r2 = defpackage.bjh.this
                            java.lang.String r3 = r0
                            defpackage.bjh.a(r2, r3)
                            defpackage.bow.a(r14)
                            defpackage.bow.a(r4)
                            return
                        L_0x0546:
                            r0 = move-exception
                            r2 = r0
                            r13 = r4
                        L_0x0549:
                            r4 = r14
                        L_0x054a:
                            r3 = 3
                        L_0x054b:
                            boolean r3 = defpackage.bpv.a(r3)
                            if (r3 == 0) goto L_0x058c
                            java.lang.String r3 = "FileDownloader"
                            java.lang.StringBuilder r5 = new java.lang.StringBuilder
                            java.lang.String r6 = "finish download request, url:"
                            r5.<init>(r6)
                            bjg r6 = r5
                            java.lang.String r6 = r6.getUrl()
                            r5.append(r6)
                            java.lang.String r6 = ", path: "
                            r5.append(r6)
                            java.lang.String r6 = r0
                            r5.append(r6)
                            java.lang.String r6 = ", support range:"
                            r5.append(r6)
                            bjg r6 = r5
                            boolean r6 = r6.b
                            r5.append(r6)
                            java.lang.String r6 = ", keep callback:"
                            r5.append(r6)
                            bjg r6 = r5
                            boolean r6 = r6.a
                            r5.append(r6)
                            java.lang.String r5 = r5.toString()
                            defpackage.bpv.b(r3, r5)
                        L_0x058c:
                            bjh r3 = defpackage.bjh.this
                            java.lang.String r5 = r0
                            defpackage.bjh.a(r3, r5)
                            defpackage.bow.a(r4)
                            defpackage.bow.a(r13)
                            throw r2
                        */
                        throw new UnsupportedOperationException("Method not decompiled: defpackage.bjh.AnonymousClass1.run():void");
                    }
                }, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, "FileDownloader");
            }
        }
    }

    static /* synthetic */ File b(String str) {
        File file = new File(str);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return file;
    }

    static /* synthetic */ void a(bph bph, a aVar, bpk bpk) {
        if (bpv.a(3)) {
            StringBuilder sb = new StringBuilder("download file complete, url:");
            sb.append(bph.getUrl());
            bpv.c("FileDownloader", sb.toString());
        }
        bjf a2 = aVar.a();
        if (!bph.isCancelled() && a2 != null) {
            a2.onFinish(bpk);
        }
    }

    static /* synthetic */ int a(Throwable th) {
        if (th instanceof SocketTimeoutException) {
            return 11;
        }
        if (th instanceof ConnectTimeoutException) {
            return 12;
        }
        if (th instanceof MalformedURLException) {
            return 14;
        }
        return th instanceof IOException ? 13 : 1;
    }

    static /* synthetic */ void a(bjh bjh, String str) {
        synchronized (bjh.b) {
            bjh.b.remove(str);
        }
    }
}
