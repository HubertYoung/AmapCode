package com.sijla.g;

import android.content.Context;
import android.os.Process;
import com.sijla.g.a.b;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONObject;

public class a {
    static final /* synthetic */ boolean a = true;
    private static Map<String, a> b = new HashMap();
    private C0061a c;

    /* renamed from: com.sijla.g.a$a reason: collision with other inner class name */
    public class C0061a {
        protected File a;
        /* access modifiers changed from: private */
        public final AtomicLong c;
        /* access modifiers changed from: private */
        public final AtomicInteger d;
        private final long e;
        private final int f;
        /* access modifiers changed from: private */
        public final Map<File, Long> g;

        private C0061a(File file, long j, int i) {
            this.g = Collections.synchronizedMap(new HashMap());
            this.a = file;
            this.e = j;
            this.f = i;
            this.c = new AtomicLong();
            this.d = new AtomicInteger();
            a();
        }

        private void a() {
            new Thread(new Runnable() {
                public void run() {
                    File[] listFiles = C0061a.this.a.listFiles();
                    if (listFiles != null) {
                        int i = 0;
                        int i2 = 0;
                        for (File file : listFiles) {
                            i = (int) (((long) i) + C0061a.this.b(file));
                            i2++;
                            C0061a.this.g.put(file, Long.valueOf(file.lastModified()));
                        }
                        C0061a.this.c.set((long) i);
                        C0061a.this.d.set(i2);
                    }
                }
            }).start();
        }

        /* access modifiers changed from: private */
        public void a(File file) {
            int i = this.d.get();
            while (i + 1 > this.f) {
                this.c.addAndGet(-b());
                i = this.d.addAndGet(-1);
            }
            this.d.addAndGet(1);
            long b2 = b(file);
            long j = this.c.get();
            while (j + b2 > this.e) {
                j = this.c.addAndGet(-b());
            }
            this.c.addAndGet(b2);
            Long valueOf = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(valueOf.longValue());
            this.g.put(file, valueOf);
        }

        /* access modifiers changed from: private */
        public File a(String str) {
            File b2 = b(str);
            Long valueOf = Long.valueOf(System.currentTimeMillis());
            b2.setLastModified(valueOf.longValue());
            this.g.put(b2, valueOf);
            return b2;
        }

        /* access modifiers changed from: private */
        public File b(String str) {
            return new File(this.a, String.valueOf(str.hashCode()));
        }

        /* access modifiers changed from: private */
        public boolean c(String str) {
            return a(str).delete();
        }

        private long b() {
            File file;
            if (this.g.isEmpty()) {
                return 0;
            }
            Set<Entry<File, Long>> entrySet = this.g.entrySet();
            synchronized (this.g) {
                file = null;
                Long l = null;
                for (Entry next : entrySet) {
                    if (file == null) {
                        file = (File) next.getKey();
                        l = (Long) next.getValue();
                    } else {
                        Long l2 = (Long) next.getValue();
                        if (l2.longValue() < l.longValue()) {
                            file = (File) next.getKey();
                            l = l2;
                        }
                    }
                }
            }
            long b2 = b(file);
            if (file != null && file.delete()) {
                this.g.remove(file);
            }
            return b2;
        }

        /* access modifiers changed from: private */
        public long b(File file) {
            return file.length();
        }
    }

    static class b {
        /* access modifiers changed from: private */
        public static boolean c(String str) {
            return a(str.getBytes());
        }

        private static boolean a(byte[] bArr) {
            String[] c = c(bArr);
            if (c != null && c.length == 2) {
                String str = c[0];
                while (str.startsWith("0")) {
                    str = str.substring(1, str.length());
                }
                if (System.currentTimeMillis() > Long.valueOf(str).longValue() + (Long.valueOf(c[1]).longValue() * 1000)) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: private */
        public static String d(String str) {
            return (str == null || !b(str.getBytes())) ? str : str.substring(str.indexOf(32) + 1, str.length());
        }

        private static boolean b(byte[] bArr) {
            return bArr != null && bArr.length > 15 && bArr[13] == 45 && a(bArr, ' ') > 14;
        }

        private static String[] c(byte[] bArr) {
            if (!b(bArr)) {
                return null;
            }
            return new String[]{new String(a(bArr, 0, 13)), new String(a(bArr, 14, a(bArr, ' ')))};
        }

        /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=byte, for r3v0, types: [byte, char] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static int a(byte[] r2, byte r3) {
            /*
                r0 = 0
            L_0x0001:
                int r1 = r2.length
                if (r0 >= r1) goto L_0x000c
                byte r1 = r2[r0]
                if (r1 != r3) goto L_0x0009
                return r0
            L_0x0009:
                int r0 = r0 + 1
                goto L_0x0001
            L_0x000c:
                r2 = -1
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.sijla.g.a.b.a(byte[], char):int");
        }

        private static byte[] a(byte[] bArr, int i, int i2) {
            int i3 = i2 - i;
            if (i3 < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append(" > ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
            }
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, i, bArr2, 0, Math.min(bArr.length - i, i3));
            return bArr2;
        }
    }

    public static a a(Context context) {
        return a(context, (String) "QCahe");
    }

    public static a a(Context context, String str) {
        File file;
        if (com.sijla.g.a.a.d(context, (String) "android.permission.WRITE_EXTERNAL_STORAGE")) {
            file = new File(b.a(context), str);
        } else {
            file = new File(context.getCacheDir(), str);
        }
        return a(file, 50000000, Integer.MAX_VALUE);
    }

    public static a a(File file, long j, int i) {
        Map<String, a> map = b;
        StringBuilder sb = new StringBuilder();
        sb.append(file.getAbsoluteFile());
        sb.append(a());
        a aVar = map.get(sb.toString());
        if (aVar != null) {
            return aVar;
        }
        a aVar2 = new a(file, j, i);
        Map<String, a> map2 = b;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(file.getAbsolutePath());
        sb2.append(a());
        map2.put(sb2.toString(), aVar2);
        return aVar2;
    }

    private static String a() {
        StringBuilder sb = new StringBuilder("_");
        sb.append(Process.myPid());
        return sb.toString();
    }

    private a(File file, long j, int i) {
        if (!file.exists()) {
            file.mkdirs();
        }
        C0061a aVar = new C0061a(file, j, i);
        this.c = aVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0042 A[SYNTHETIC, Splitter:B:23:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x004d A[SYNTHETIC, Splitter:B:27:0x004d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            com.sijla.g.a$a r0 = r4.c
            java.io.File r5 = r0.b(r5)
            boolean r0 = r5.exists()
            if (r0 != 0) goto L_0x0013
            java.io.File r0 = r5.getParentFile()
            r0.mkdirs()
        L_0x0013:
            r0 = 0
            java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch:{ IOException -> 0x003c }
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch:{ IOException -> 0x003c }
            r2.<init>(r5)     // Catch:{ IOException -> 0x003c }
            r3 = 1024(0x400, float:1.435E-42)
            r1.<init>(r2, r3)     // Catch:{ IOException -> 0x003c }
            r1.write(r6)     // Catch:{ IOException -> 0x0037, all -> 0x0034 }
            r1.flush()     // Catch:{ IOException -> 0x002a }
            r1.close()     // Catch:{ IOException -> 0x002a }
            goto L_0x002e
        L_0x002a:
            r6 = move-exception
        L_0x002b:
            r6.printStackTrace()
        L_0x002e:
            com.sijla.g.a$a r6 = r4.c
            r6.a(r5)
            return
        L_0x0034:
            r6 = move-exception
            r0 = r1
            goto L_0x004b
        L_0x0037:
            r6 = move-exception
            r0 = r1
            goto L_0x003d
        L_0x003a:
            r6 = move-exception
            goto L_0x004b
        L_0x003c:
            r6 = move-exception
        L_0x003d:
            r6.printStackTrace()     // Catch:{ all -> 0x003a }
            if (r0 == 0) goto L_0x002e
            r0.flush()     // Catch:{ IOException -> 0x0049 }
            r0.close()     // Catch:{ IOException -> 0x0049 }
            goto L_0x002e
        L_0x0049:
            r6 = move-exception
            goto L_0x002b
        L_0x004b:
            if (r0 == 0) goto L_0x0058
            r0.flush()     // Catch:{ IOException -> 0x0054 }
            r0.close()     // Catch:{ IOException -> 0x0054 }
            goto L_0x0058
        L_0x0054:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0058:
            com.sijla.g.a$a r0 = r4.c
            r0.a(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sijla.g.a.a(java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x005b A[SYNTHETIC, Splitter:B:32:0x005b] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0067 A[SYNTHETIC, Splitter:B:39:0x0067] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String a(java.lang.String r6) {
        /*
            r5 = this;
            com.sijla.g.a$a r0 = r5.c
            java.io.File r0 = r0.a(r6)
            boolean r1 = r0.exists()
            r2 = 0
            if (r1 != 0) goto L_0x000e
            return r2
        L_0x000e:
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            r3.<init>(r0)     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            r1.<init>(r3)     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            java.lang.String r0 = ""
        L_0x001a:
            java.lang.String r3 = r1.readLine()     // Catch:{ IOException -> 0x004f }
            if (r3 == 0) goto L_0x0030
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x004f }
            r4.<init>()     // Catch:{ IOException -> 0x004f }
            r4.append(r0)     // Catch:{ IOException -> 0x004f }
            r4.append(r3)     // Catch:{ IOException -> 0x004f }
            java.lang.String r0 = r4.toString()     // Catch:{ IOException -> 0x004f }
            goto L_0x001a
        L_0x0030:
            boolean r3 = com.sijla.g.a.b.c(r0)     // Catch:{ IOException -> 0x004f }
            if (r3 != 0) goto L_0x0043
            java.lang.String r6 = com.sijla.g.a.b.d(r0)     // Catch:{ IOException -> 0x004f }
            r1.close()     // Catch:{ IOException -> 0x003e }
            goto L_0x0042
        L_0x003e:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0042:
            return r6
        L_0x0043:
            r1.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x004b
        L_0x0047:
            r0 = move-exception
            r0.printStackTrace()
        L_0x004b:
            r5.c(r6)
            return r2
        L_0x004f:
            r6 = move-exception
            goto L_0x0056
        L_0x0051:
            r6 = move-exception
            r1 = r2
            goto L_0x0065
        L_0x0054:
            r6 = move-exception
            r1 = r2
        L_0x0056:
            r6.printStackTrace()     // Catch:{ all -> 0x0064 }
            if (r1 == 0) goto L_0x0063
            r1.close()     // Catch:{ IOException -> 0x005f }
            goto L_0x0063
        L_0x005f:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0063:
            return r2
        L_0x0064:
            r6 = move-exception
        L_0x0065:
            if (r1 == 0) goto L_0x006f
            r1.close()     // Catch:{ IOException -> 0x006b }
            goto L_0x006f
        L_0x006b:
            r0 = move-exception
            r0.printStackTrace()
        L_0x006f:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sijla.g.a.a(java.lang.String):java.lang.String");
    }

    public void a(String str, JSONObject jSONObject) {
        a(str, jSONObject.toString());
    }

    public JSONObject b(String str) {
        try {
            return new JSONObject(a(str));
        } catch (Exception unused) {
            return new JSONObject();
        }
    }

    public boolean c(String str) {
        return this.c.c(str);
    }
}
