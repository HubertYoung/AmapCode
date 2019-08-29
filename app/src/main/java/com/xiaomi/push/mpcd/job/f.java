package com.xiaomi.push.mpcd.job;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.misc.h.a;
import com.xiaomi.push.mpcd.b;
import com.xiaomi.push.mpcd.c;
import com.xiaomi.xmpush.thrift.d;
import com.xiaomi.xmpush.thrift.k;

public abstract class f extends a {
    protected int c;
    protected Context d;

    public f(Context context, int i) {
        this.c = i;
        this.d = context;
    }

    public static void a(Context context, k kVar) {
        b b = c.a().b();
        String a = b == null ? "" : b.a();
        if (!TextUtils.isEmpty(a) && !TextUtils.isEmpty(kVar.c())) {
            a(context, kVar, a);
        }
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r1v0, types: [java.nio.channels.FileLock] */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r6v3 */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r6v4, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.nio.channels.FileLock] */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r6v5 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v7, types: [java.nio.channels.FileLock] */
    /* JADX WARNING: type inference failed for: r6v7 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r6v8 */
    /* JADX WARNING: type inference failed for: r6v10, types: [java.io.OutputStream, java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r6v11 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r6v12 */
    /* JADX WARNING: type inference failed for: r6v13 */
    /* JADX WARNING: type inference failed for: r6v14 */
    /* JADX WARNING: Can't wrap try/catch for region: R(11:28|29|30|39|40|(3:42|43|(2:45|46))|47|48|24|49|50) */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:14|15|(3:17|18|(2:20|21))|22|23) */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:51|52|53|(2:57|58)|60|61|62) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x005e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x008a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:60:0x00a1 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v1
      assigns: []
      uses: []
      mth insns count: 84
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
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0081 A[SYNTHETIC, Splitter:B:42:0x0081] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:60:0x00a1=Splitter:B:60:0x00a1, B:47:0x008a=Splitter:B:47:0x008a, B:39:0x007c=Splitter:B:39:0x007c, B:22:0x005e=Splitter:B:22:0x005e} */
    /* JADX WARNING: Unknown variable types count: 10 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(android.content.Context r6, com.xiaomi.xmpush.thrift.k r7, java.lang.String r8) {
        /*
            byte[] r7 = com.xiaomi.xmpush.thrift.au.a(r7)
            byte[] r7 = com.xiaomi.push.mpcd.e.b(r8, r7)
            if (r7 == 0) goto L_0x00aa
            int r8 = r7.length
            if (r8 != 0) goto L_0x000e
            return
        L_0x000e:
            java.lang.Object r8 = com.xiaomi.push.mpcd.f.a
            monitor-enter(r8)
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ IOException -> 0x0079, all -> 0x0075 }
            java.io.File r2 = r6.getExternalFilesDir(r0)     // Catch:{ IOException -> 0x0079, all -> 0x0075 }
            java.lang.String r3 = "push_cdata.lock"
            r1.<init>(r2, r3)     // Catch:{ IOException -> 0x0079, all -> 0x0075 }
            com.xiaomi.channel.commonutils.file.a.a(r1)     // Catch:{ IOException -> 0x0079, all -> 0x0075 }
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch:{ IOException -> 0x0079, all -> 0x0075 }
            java.lang.String r3 = "rw"
            r2.<init>(r1, r3)     // Catch:{ IOException -> 0x0079, all -> 0x0075 }
            java.nio.channels.FileChannel r1 = r2.getChannel()     // Catch:{ IOException -> 0x0072, all -> 0x006f }
            java.nio.channels.FileLock r1 = r1.lock()     // Catch:{ IOException -> 0x0072, all -> 0x006f }
            java.io.File r3 = new java.io.File     // Catch:{ IOException -> 0x006b, all -> 0x0069 }
            java.io.File r6 = r6.getExternalFilesDir(r0)     // Catch:{ IOException -> 0x006b, all -> 0x0069 }
            java.lang.String r4 = "push_cdata.data"
            r3.<init>(r6, r4)     // Catch:{ IOException -> 0x006b, all -> 0x0069 }
            java.io.BufferedOutputStream r6 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x006b, all -> 0x0069 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x006b, all -> 0x0069 }
            r5 = 1
            r4.<init>(r3, r5)     // Catch:{ IOException -> 0x006b, all -> 0x0069 }
            r6.<init>(r4)     // Catch:{ IOException -> 0x006b, all -> 0x0069 }
            int r0 = r7.length     // Catch:{ IOException -> 0x0067, all -> 0x0065 }
            byte[] r0 = com.xiaomi.channel.commonutils.misc.b.a(r0)     // Catch:{ IOException -> 0x0067, all -> 0x0065 }
            r6.write(r0)     // Catch:{ IOException -> 0x0067, all -> 0x0065 }
            r6.write(r7)     // Catch:{ IOException -> 0x0067, all -> 0x0065 }
            r6.flush()     // Catch:{ IOException -> 0x0067, all -> 0x0065 }
            if (r1 == 0) goto L_0x005e
            boolean r7 = r1.isValid()     // Catch:{ all -> 0x009f }
            if (r7 == 0) goto L_0x005e
            r1.release()     // Catch:{ IOException -> 0x005e }
        L_0x005e:
            com.xiaomi.channel.commonutils.file.a.a(r6)     // Catch:{ all -> 0x009f }
        L_0x0061:
            com.xiaomi.channel.commonutils.file.a.a(r2)     // Catch:{ all -> 0x009f }
            goto L_0x008e
        L_0x0065:
            r7 = move-exception
            goto L_0x0092
        L_0x0067:
            r7 = move-exception
            goto L_0x006d
        L_0x0069:
            r7 = move-exception
            goto L_0x0093
        L_0x006b:
            r7 = move-exception
            r6 = r0
        L_0x006d:
            r0 = r1
            goto L_0x007c
        L_0x006f:
            r7 = move-exception
            r1 = r0
            goto L_0x0093
        L_0x0072:
            r7 = move-exception
            r6 = r0
            goto L_0x007c
        L_0x0075:
            r7 = move-exception
            r1 = r0
            r2 = r1
            goto L_0x0093
        L_0x0079:
            r7 = move-exception
            r6 = r0
            r2 = r6
        L_0x007c:
            r7.printStackTrace()     // Catch:{ all -> 0x0090 }
            if (r0 == 0) goto L_0x008a
            boolean r7 = r0.isValid()     // Catch:{ all -> 0x009f }
            if (r7 == 0) goto L_0x008a
            r0.release()     // Catch:{ IOException -> 0x008a }
        L_0x008a:
            com.xiaomi.channel.commonutils.file.a.a(r6)     // Catch:{ all -> 0x009f }
            goto L_0x0061
        L_0x008e:
            monitor-exit(r8)     // Catch:{ all -> 0x009f }
            return
        L_0x0090:
            r7 = move-exception
            r1 = r0
        L_0x0092:
            r0 = r6
        L_0x0093:
            if (r1 == 0) goto L_0x00a1
            boolean r6 = r1.isValid()     // Catch:{ all -> 0x009f }
            if (r6 == 0) goto L_0x00a1
            r1.release()     // Catch:{ IOException -> 0x00a1 }
            goto L_0x00a1
        L_0x009f:
            r6 = move-exception
            goto L_0x00a8
        L_0x00a1:
            com.xiaomi.channel.commonutils.file.a.a(r0)     // Catch:{ all -> 0x009f }
            com.xiaomi.channel.commonutils.file.a.a(r2)     // Catch:{ all -> 0x009f }
            throw r7     // Catch:{ all -> 0x009f }
        L_0x00a8:
            monitor-exit(r8)     // Catch:{ all -> 0x009f }
            throw r6
        L_0x00aa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.mpcd.job.f.a(android.content.Context, com.xiaomi.xmpush.thrift.k, java.lang.String):void");
    }

    public abstract String b();

    /* access modifiers changed from: protected */
    public boolean c() {
        return true;
    }

    public abstract d d();

    /* access modifiers changed from: protected */
    public boolean e() {
        return com.xiaomi.channel.commonutils.misc.f.a(this.d, String.valueOf(a()), (long) this.c);
    }

    public void run() {
        if (e()) {
            b b = c.a().b();
            String a = b == null ? "" : b.a();
            if (!TextUtils.isEmpty(a) && c()) {
                String b2 = b();
                if (!TextUtils.isEmpty(b2)) {
                    k kVar = new k();
                    kVar.a(b2);
                    kVar.a(System.currentTimeMillis());
                    kVar.a(d());
                    a(this.d, kVar, a);
                }
            }
        }
    }
}
