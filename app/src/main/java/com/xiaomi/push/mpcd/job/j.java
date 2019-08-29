package com.xiaomi.push.mpcd.job;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigManager;
import com.xiaomi.channel.commonutils.misc.c;
import com.xiaomi.channel.commonutils.misc.h.a;
import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.push.mpcd.b;
import com.xiaomi.push.service.an;
import com.xiaomi.xmpush.thrift.ac;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.g;
import com.xiaomi.xmpush.thrift.k;
import com.xiaomi.xmpush.thrift.r;
import java.io.File;
import java.util.List;

public class j extends a {
    private Context a;
    private SharedPreferences b;
    private an c;

    public j(Context context) {
        this.a = context;
        this.b = context.getSharedPreferences("mipush_extra", 0);
        this.c = an.a(context);
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r8v0, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.nio.channels.FileLock] */
    /* JADX WARNING: type inference failed for: r8v1 */
    /* JADX WARNING: type inference failed for: r6v0, types: [java.nio.channels.FileLock] */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r6v1 */
    /* JADX WARNING: type inference failed for: r8v4 */
    /* JADX WARNING: type inference failed for: r6v3 */
    /* JADX WARNING: type inference failed for: r6v5, types: [java.nio.channels.FileLock] */
    /* JADX WARNING: type inference failed for: r8v5 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r8v6 */
    /* JADX WARNING: type inference failed for: r8v7, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r6v7 */
    /* JADX WARNING: type inference failed for: r8v8 */
    /* JADX WARNING: type inference failed for: r8v9 */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x007a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:53:0x009c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:64:0x00b3 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r8v5
      assigns: []
      uses: []
      mth insns count: 89
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
    /* JADX WARNING: Unknown variable types count: 8 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<com.xiaomi.xmpush.thrift.k> a(java.io.File r11) {
        /*
            r10 = this;
            com.xiaomi.push.mpcd.c r0 = com.xiaomi.push.mpcd.c.a()
            com.xiaomi.push.mpcd.b r0 = r0.b()
            if (r0 != 0) goto L_0x000d
            java.lang.String r0 = ""
            goto L_0x0011
        L_0x000d:
            java.lang.String r0 = r0.a()
        L_0x0011:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r2 = 0
            if (r1 == 0) goto L_0x0019
            return r2
        L_0x0019:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r3 = 4
            byte[] r4 = new byte[r3]
            java.lang.Object r5 = com.xiaomi.push.mpcd.f.a
            monitor-enter(r5)
            java.io.File r6 = new java.io.File     // Catch:{ Exception -> 0x00a3, all -> 0x008e }
            android.content.Context r7 = r10.a     // Catch:{ Exception -> 0x00a3, all -> 0x008e }
            java.io.File r7 = r7.getExternalFilesDir(r2)     // Catch:{ Exception -> 0x00a3, all -> 0x008e }
            java.lang.String r8 = "push_cdata.lock"
            r6.<init>(r7, r8)     // Catch:{ Exception -> 0x00a3, all -> 0x008e }
            com.xiaomi.channel.commonutils.file.a.a(r6)     // Catch:{ Exception -> 0x00a3, all -> 0x008e }
            java.io.RandomAccessFile r7 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x00a3, all -> 0x008e }
            java.lang.String r8 = "rw"
            r7.<init>(r6, r8)     // Catch:{ Exception -> 0x00a3, all -> 0x008e }
            java.nio.channels.FileChannel r6 = r7.getChannel()     // Catch:{ Exception -> 0x008c, all -> 0x0089 }
            java.nio.channels.FileLock r6 = r6.lock()     // Catch:{ Exception -> 0x008c, all -> 0x0089 }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0086, all -> 0x0084 }
            r8.<init>(r11)     // Catch:{ Exception -> 0x0086, all -> 0x0084 }
        L_0x0048:
            int r11 = r8.read(r4)     // Catch:{ Exception -> 0x0087, all -> 0x0081 }
            if (r11 != r3) goto L_0x006f
            int r11 = com.xiaomi.channel.commonutils.misc.b.a(r4)     // Catch:{ Exception -> 0x0087, all -> 0x0081 }
            byte[] r2 = new byte[r11]     // Catch:{ Exception -> 0x0087, all -> 0x0081 }
            int r9 = r8.read(r2)     // Catch:{ Exception -> 0x0087, all -> 0x0081 }
            if (r9 != r11) goto L_0x006f
            byte[] r11 = com.xiaomi.push.mpcd.e.a(r0, r2)     // Catch:{ Exception -> 0x0087, all -> 0x0081 }
            if (r11 == 0) goto L_0x0048
            int r2 = r11.length     // Catch:{ Exception -> 0x0087, all -> 0x0081 }
            if (r2 == 0) goto L_0x0048
            com.xiaomi.xmpush.thrift.k r2 = new com.xiaomi.xmpush.thrift.k     // Catch:{ Exception -> 0x0087, all -> 0x0081 }
            r2.<init>()     // Catch:{ Exception -> 0x0087, all -> 0x0081 }
            com.xiaomi.xmpush.thrift.au.a(r2, r11)     // Catch:{ Exception -> 0x0087, all -> 0x0081 }
            r1.add(r2)     // Catch:{ Exception -> 0x0087, all -> 0x0081 }
            goto L_0x0048
        L_0x006f:
            if (r6 == 0) goto L_0x007a
            boolean r11 = r6.isValid()     // Catch:{ all -> 0x00b1 }
            if (r11 == 0) goto L_0x007a
            r6.release()     // Catch:{ IOException -> 0x007a }
        L_0x007a:
            com.xiaomi.channel.commonutils.file.a.a(r8)     // Catch:{ all -> 0x00b1 }
        L_0x007d:
            com.xiaomi.channel.commonutils.file.a.a(r7)     // Catch:{ all -> 0x00b1 }
            goto L_0x00b7
        L_0x0081:
            r11 = move-exception
            r2 = r8
            goto L_0x0091
        L_0x0084:
            r11 = move-exception
            goto L_0x0091
        L_0x0086:
            r8 = r2
        L_0x0087:
            r2 = r6
            goto L_0x00a5
        L_0x0089:
            r11 = move-exception
            r6 = r2
            goto L_0x0091
        L_0x008c:
            r8 = r2
            goto L_0x00a5
        L_0x008e:
            r11 = move-exception
            r6 = r2
            r7 = r6
        L_0x0091:
            if (r6 == 0) goto L_0x009c
            boolean r0 = r6.isValid()     // Catch:{ all -> 0x00b1 }
            if (r0 == 0) goto L_0x009c
            r6.release()     // Catch:{ IOException -> 0x009c }
        L_0x009c:
            com.xiaomi.channel.commonutils.file.a.a(r2)     // Catch:{ all -> 0x00b1 }
            com.xiaomi.channel.commonutils.file.a.a(r7)     // Catch:{ all -> 0x00b1 }
            throw r11     // Catch:{ all -> 0x00b1 }
        L_0x00a3:
            r7 = r2
            r8 = r7
        L_0x00a5:
            if (r2 == 0) goto L_0x00b3
            boolean r11 = r2.isValid()     // Catch:{ all -> 0x00b1 }
            if (r11 == 0) goto L_0x00b3
            r2.release()     // Catch:{ IOException -> 0x00b3 }
            goto L_0x00b3
        L_0x00b1:
            r11 = move-exception
            goto L_0x00b9
        L_0x00b3:
            com.xiaomi.channel.commonutils.file.a.a(r8)     // Catch:{ all -> 0x00b1 }
            goto L_0x007d
        L_0x00b7:
            monitor-exit(r5)     // Catch:{ all -> 0x00b1 }
            return r1
        L_0x00b9:
            monitor-exit(r5)     // Catch:{ all -> 0x00b1 }
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.mpcd.job.j.a(java.io.File):java.util.List");
    }

    private boolean b() {
        if (d.e(this.a)) {
            return false;
        }
        if (!d.g(this.a) || d()) {
            return (d.h(this.a) && !c()) || d.i(this.a);
        }
        return true;
    }

    private boolean c() {
        if (!this.c.a(g.Upload3GSwitch.a(), true)) {
            return false;
        }
        return Math.abs((System.currentTimeMillis() / 1000) - this.b.getLong("last_upload_data_timestamp", -1)) > ((long) Math.max(86400, this.c.a(g.Upload3GFrequency.a(), 432000)));
    }

    private boolean d() {
        if (!this.c.a(g.Upload4GSwitch.a(), true)) {
            return false;
        }
        return Math.abs((System.currentTimeMillis() / 1000) - this.b.getLong("last_upload_data_timestamp", -1)) > ((long) Math.max(86400, this.c.a(g.Upload4GFrequency.a(), (int) H5NebulaAppConfigManager.DEFAULT_RES_INVALID_SECOND)));
    }

    private void e() {
        Editor edit = this.b.edit();
        edit.putLong("last_upload_data_timestamp", System.currentTimeMillis() / 1000);
        edit.commit();
    }

    public int a() {
        return 1;
    }

    public void run() {
        File file = new File(this.a.getExternalFilesDir(null), "push_cdata.data");
        if (!d.d(this.a)) {
            if (file.length() > 1863680) {
                file.delete();
            }
        } else if (!b() && file.exists()) {
            List<k> a2 = a(file);
            if (!c.a(a2)) {
                int size = a2.size();
                if (size > 4000) {
                    a2 = a2.subList(size - 4000, size);
                }
                ac acVar = new ac();
                acVar.a(a2);
                byte[] a3 = com.xiaomi.channel.commonutils.file.a.a(au.a(acVar));
                ai aiVar = new ai("-1", false);
                aiVar.c(r.DataCollection.W);
                aiVar.a(a3);
                b b2 = com.xiaomi.push.mpcd.c.a().b();
                if (b2 != null) {
                    b2.a(aiVar, com.xiaomi.xmpush.thrift.a.Notification, null);
                }
                e();
            }
            file.delete();
            this.b.edit().remove("ltapn").commit();
        }
    }
}
