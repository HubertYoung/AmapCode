package defpackage;

import android.content.Context;
import android.os.storage.StorageManager;
import java.io.Closeable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* renamed from: chy reason: default package */
/* compiled from: FileStorageModel */
public final class chy {
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006a, code lost:
        r6 = new java.lang.String(r3.toByteArray(), "UTF-8");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0079, code lost:
        if (android.text.TextUtils.isEmpty(r6) != false) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0081, code lost:
        if (r6.contains(com.alipay.mobile.security.bio.common.record.MetaRecord.LOG_SEPARATOR) == false) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0083, code lost:
        r6 = r6.split(com.alipay.mobile.security.bio.common.record.MetaRecord.LOG_SEPARATOR);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0089, code lost:
        if (r6 == null) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x008d, code lost:
        if (r6.length != 2) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0095, code lost:
        if (android.text.TextUtils.equals(r7, r6[0]) == false) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0097, code lost:
        r6 = r6[1];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        a((java.io.Closeable) r3);
        a((java.io.Closeable) r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a1, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        a((java.io.Closeable) r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String a(android.content.Context r6, java.lang.String r7) {
        /*
            java.lang.Class<chy> r0 = defpackage.chy.class
            monitor-enter(r0)
            java.lang.String r6 = a(r6)     // Catch:{ all -> 0x00d6 }
            boolean r1 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x00d6 }
            if (r1 == 0) goto L_0x0011
            java.lang.String r6 = ""
            monitor-exit(r0)
            return r6
        L_0x0011:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d6 }
            r1.<init>()     // Catch:{ all -> 0x00d6 }
            r1.append(r6)     // Catch:{ all -> 0x00d6 }
            java.lang.String r6 = java.io.File.separator     // Catch:{ all -> 0x00d6 }
            r1.append(r6)     // Catch:{ all -> 0x00d6 }
            java.lang.String r6 = "backups"
            r1.append(r6)     // Catch:{ all -> 0x00d6 }
            java.lang.String r6 = r1.toString()     // Catch:{ all -> 0x00d6 }
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x00d6 }
            java.lang.String r2 = ".adiu"
            r1.<init>(r6, r2)     // Catch:{ all -> 0x00d6 }
            boolean r6 = r1.exists()     // Catch:{ all -> 0x00d6 }
            if (r6 == 0) goto L_0x00d2
            boolean r6 = r1.canRead()     // Catch:{ all -> 0x00d6 }
            if (r6 != 0) goto L_0x003c
            goto L_0x00d2
        L_0x003c:
            long r2 = r1.length()     // Catch:{ all -> 0x00d6 }
            r4 = 0
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x004d
            r1.delete()     // Catch:{ all -> 0x00d6 }
            java.lang.String r6 = ""
            monitor-exit(r0)
            return r6
        L_0x004d:
            r6 = 0
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch:{ FileNotFoundException -> 0x00c9, UnsupportedEncodingException -> 0x00c4, IOException -> 0x00bf, all -> 0x00b4 }
            java.lang.String r3 = "r"
            r2.<init>(r1, r3)     // Catch:{ FileNotFoundException -> 0x00c9, UnsupportedEncodingException -> 0x00c4, IOException -> 0x00bf, all -> 0x00b4 }
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]     // Catch:{ FileNotFoundException -> 0x00ca, UnsupportedEncodingException -> 0x00c5, IOException -> 0x00c0, all -> 0x00b1 }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ FileNotFoundException -> 0x00ca, UnsupportedEncodingException -> 0x00c5, IOException -> 0x00c0, all -> 0x00b1 }
            r3.<init>()     // Catch:{ FileNotFoundException -> 0x00ca, UnsupportedEncodingException -> 0x00c5, IOException -> 0x00c0, all -> 0x00b1 }
        L_0x005e:
            int r6 = r2.read(r1)     // Catch:{ FileNotFoundException -> 0x00af, UnsupportedEncodingException -> 0x00ad, IOException -> 0x00ab, all -> 0x00a9 }
            r4 = -1
            r5 = 0
            if (r6 == r4) goto L_0x006a
            r3.write(r1, r5, r6)     // Catch:{ FileNotFoundException -> 0x00af, UnsupportedEncodingException -> 0x00ad, IOException -> 0x00ab, all -> 0x00a9 }
            goto L_0x005e
        L_0x006a:
            java.lang.String r6 = new java.lang.String     // Catch:{ FileNotFoundException -> 0x00af, UnsupportedEncodingException -> 0x00ad, IOException -> 0x00ab, all -> 0x00a9 }
            byte[] r1 = r3.toByteArray()     // Catch:{ FileNotFoundException -> 0x00af, UnsupportedEncodingException -> 0x00ad, IOException -> 0x00ab, all -> 0x00a9 }
            java.lang.String r4 = "UTF-8"
            r6.<init>(r1, r4)     // Catch:{ FileNotFoundException -> 0x00af, UnsupportedEncodingException -> 0x00ad, IOException -> 0x00ab, all -> 0x00a9 }
            boolean r1 = android.text.TextUtils.isEmpty(r6)     // Catch:{ FileNotFoundException -> 0x00af, UnsupportedEncodingException -> 0x00ad, IOException -> 0x00ab, all -> 0x00a9 }
            if (r1 != 0) goto L_0x00a2
            java.lang.String r1 = "#"
            boolean r1 = r6.contains(r1)     // Catch:{ FileNotFoundException -> 0x00af, UnsupportedEncodingException -> 0x00ad, IOException -> 0x00ab, all -> 0x00a9 }
            if (r1 == 0) goto L_0x00a2
            java.lang.String r1 = "#"
            java.lang.String[] r6 = r6.split(r1)     // Catch:{ FileNotFoundException -> 0x00af, UnsupportedEncodingException -> 0x00ad, IOException -> 0x00ab, all -> 0x00a9 }
            if (r6 == 0) goto L_0x00a2
            int r1 = r6.length     // Catch:{ FileNotFoundException -> 0x00af, UnsupportedEncodingException -> 0x00ad, IOException -> 0x00ab, all -> 0x00a9 }
            r4 = 2
            if (r1 != r4) goto L_0x00a2
            r1 = r6[r5]     // Catch:{ FileNotFoundException -> 0x00af, UnsupportedEncodingException -> 0x00ad, IOException -> 0x00ab, all -> 0x00a9 }
            boolean r7 = android.text.TextUtils.equals(r7, r1)     // Catch:{ FileNotFoundException -> 0x00af, UnsupportedEncodingException -> 0x00ad, IOException -> 0x00ab, all -> 0x00a9 }
            if (r7 == 0) goto L_0x00a2
            r7 = 1
            r6 = r6[r7]     // Catch:{ FileNotFoundException -> 0x00af, UnsupportedEncodingException -> 0x00ad, IOException -> 0x00ab, all -> 0x00a9 }
            a(r3)     // Catch:{ all -> 0x00d6 }
            a(r2)     // Catch:{ all -> 0x00d6 }
            monitor-exit(r0)
            return r6
        L_0x00a2:
            a(r3)     // Catch:{ all -> 0x00d6 }
        L_0x00a5:
            a(r2)     // Catch:{ all -> 0x00d6 }
            goto L_0x00ce
        L_0x00a9:
            r6 = move-exception
            goto L_0x00b8
        L_0x00ab:
            r6 = r3
            goto L_0x00c0
        L_0x00ad:
            r6 = r3
            goto L_0x00c5
        L_0x00af:
            r6 = r3
            goto L_0x00ca
        L_0x00b1:
            r7 = move-exception
            r3 = r6
            goto L_0x00b7
        L_0x00b4:
            r7 = move-exception
            r2 = r6
            r3 = r2
        L_0x00b7:
            r6 = r7
        L_0x00b8:
            a(r3)     // Catch:{ all -> 0x00d6 }
            a(r2)     // Catch:{ all -> 0x00d6 }
            throw r6     // Catch:{ all -> 0x00d6 }
        L_0x00bf:
            r2 = r6
        L_0x00c0:
            a(r6)     // Catch:{ all -> 0x00d6 }
            goto L_0x00a5
        L_0x00c4:
            r2 = r6
        L_0x00c5:
            a(r6)     // Catch:{ all -> 0x00d6 }
            goto L_0x00a5
        L_0x00c9:
            r2 = r6
        L_0x00ca:
            a(r6)     // Catch:{ all -> 0x00d6 }
            goto L_0x00a5
        L_0x00ce:
            java.lang.String r6 = ""
            monitor-exit(r0)
            return r6
        L_0x00d2:
            java.lang.String r6 = ""
            monitor-exit(r0)
            return r6
        L_0x00d6:
            r6 = move-exception
            monitor-exit(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.chy.a(android.content.Context, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:42|43|(2:49|50)|(2:53|54)|55|56|57) */
    /* JADX WARNING: Can't wrap try/catch for region: R(8:45|44|(2:62|63)|(2:66|67)|68|69|70|71) */
    /* JADX WARNING: Can't wrap try/catch for region: R(9:19|20|(3:22|23|24)|(2:30|31)|(2:34|35)|36|37|38|39) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:36:0x0086 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x00a2 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:68:0x00b2 */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x009a A[SYNTHETIC, Splitter:B:49:0x009a] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x009f A[SYNTHETIC, Splitter:B:53:0x009f] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00aa A[SYNTHETIC, Splitter:B:62:0x00aa] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00af A[SYNTHETIC, Splitter:B:66:0x00af] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:55:0x00a2=Splitter:B:55:0x00a2, B:68:0x00b2=Splitter:B:68:0x00b2, B:36:0x0086=Splitter:B:36:0x0086} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(android.content.Context r4, java.lang.String r5, java.lang.String r6) {
        /*
            java.lang.Class<chy> r0 = defpackage.chy.class
            monitor-enter(r0)
            java.lang.String r4 = a(r4)     // Catch:{ all -> 0x00b7 }
            boolean r1 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x00b7 }
            if (r1 == 0) goto L_0x000f
            monitor-exit(r0)
            return
        L_0x000f:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b7 }
            r1.<init>()     // Catch:{ all -> 0x00b7 }
            r1.append(r5)     // Catch:{ all -> 0x00b7 }
            java.lang.String r5 = "#"
            r1.append(r5)     // Catch:{ all -> 0x00b7 }
            r1.append(r6)     // Catch:{ all -> 0x00b7 }
            java.lang.String r5 = r1.toString()     // Catch:{ all -> 0x00b7 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b7 }
            r6.<init>()     // Catch:{ all -> 0x00b7 }
            r6.append(r4)     // Catch:{ all -> 0x00b7 }
            java.lang.String r4 = java.io.File.separator     // Catch:{ all -> 0x00b7 }
            r6.append(r4)     // Catch:{ all -> 0x00b7 }
            java.lang.String r4 = "backups"
            r6.append(r4)     // Catch:{ all -> 0x00b7 }
            java.lang.String r4 = r6.toString()     // Catch:{ all -> 0x00b7 }
            java.io.File r6 = new java.io.File     // Catch:{ all -> 0x00b7 }
            r6.<init>(r4)     // Catch:{ all -> 0x00b7 }
            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x00b7 }
            java.lang.String r1 = ".adiu"
            r4.<init>(r6, r1)     // Catch:{ all -> 0x00b7 }
            r1 = 0
            boolean r2 = r6.exists()     // Catch:{ Throwable -> 0x00a6, all -> 0x0095 }
            if (r2 == 0) goto L_0x0052
            boolean r2 = r6.isDirectory()     // Catch:{ Throwable -> 0x00a6, all -> 0x0095 }
            if (r2 == 0) goto L_0x0055
        L_0x0052:
            r6.mkdirs()     // Catch:{ Throwable -> 0x00a6, all -> 0x0095 }
        L_0x0055:
            r4.createNewFile()     // Catch:{ Throwable -> 0x00a6, all -> 0x0095 }
            java.io.RandomAccessFile r6 = new java.io.RandomAccessFile     // Catch:{ Throwable -> 0x00a6, all -> 0x0095 }
            java.lang.String r2 = "rws"
            r6.<init>(r4, r2)     // Catch:{ Throwable -> 0x00a6, all -> 0x0095 }
            java.nio.channels.FileChannel r4 = r6.getChannel()     // Catch:{ Throwable -> 0x0093, all -> 0x0090 }
            java.nio.channels.FileLock r2 = r4.tryLock()     // Catch:{ Throwable -> 0x00a8, all -> 0x008b }
            if (r2 == 0) goto L_0x007c
            java.lang.String r1 = "UTF-8"
            byte[] r5 = r5.getBytes(r1)     // Catch:{ Throwable -> 0x007a, all -> 0x0077 }
            java.nio.ByteBuffer r5 = java.nio.ByteBuffer.wrap(r5)     // Catch:{ Throwable -> 0x007a, all -> 0x0077 }
            r4.write(r5)     // Catch:{ Throwable -> 0x007a, all -> 0x0077 }
            goto L_0x007c
        L_0x0077:
            r5 = move-exception
            r1 = r2
            goto L_0x008c
        L_0x007a:
            r1 = r2
            goto L_0x00a8
        L_0x007c:
            if (r2 == 0) goto L_0x0081
            r2.release()     // Catch:{ IOException -> 0x0081 }
        L_0x0081:
            if (r4 == 0) goto L_0x0086
            r4.close()     // Catch:{ IOException -> 0x0086 }
        L_0x0086:
            a(r6)     // Catch:{ all -> 0x00b7 }
            monitor-exit(r0)
            return
        L_0x008b:
            r5 = move-exception
        L_0x008c:
            r3 = r5
            r5 = r4
            r4 = r3
            goto L_0x0098
        L_0x0090:
            r4 = move-exception
            r5 = r1
            goto L_0x0098
        L_0x0093:
            r4 = r1
            goto L_0x00a8
        L_0x0095:
            r4 = move-exception
            r5 = r1
            r6 = r5
        L_0x0098:
            if (r1 == 0) goto L_0x009d
            r1.release()     // Catch:{ IOException -> 0x009d }
        L_0x009d:
            if (r5 == 0) goto L_0x00a2
            r5.close()     // Catch:{ IOException -> 0x00a2 }
        L_0x00a2:
            a(r6)     // Catch:{ all -> 0x00b7 }
            throw r4     // Catch:{ all -> 0x00b7 }
        L_0x00a6:
            r4 = r1
            r6 = r4
        L_0x00a8:
            if (r1 == 0) goto L_0x00ad
            r1.release()     // Catch:{ IOException -> 0x00ad }
        L_0x00ad:
            if (r4 == 0) goto L_0x00b2
            r4.close()     // Catch:{ IOException -> 0x00b2 }
        L_0x00b2:
            a(r6)     // Catch:{ all -> 0x00b7 }
            monitor-exit(r0)
            return
        L_0x00b7:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.chy.a(android.content.Context, java.lang.String, java.lang.String):void");
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable unused) {
            }
        }
    }

    private static String a(Context context) {
        StorageManager storageManager = (StorageManager) context.getSystemService("storage");
        try {
            Class<?> cls = Class.forName("android.os.storage.StorageVolume");
            Method method = storageManager.getClass().getMethod("getVolumeList", new Class[0]);
            Method method2 = cls.getMethod("getPath", new Class[0]);
            Method method3 = cls.getMethod("isRemovable", new Class[0]);
            Object invoke = method.invoke(storageManager, new Object[0]);
            int length = Array.getLength(invoke);
            for (int i = 0; i < length; i++) {
                Object obj = Array.get(invoke, i);
                String str = (String) method2.invoke(obj, new Object[0]);
                if (!((Boolean) method3.invoke(obj, new Object[0])).booleanValue()) {
                    return str;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
        }
        return null;
    }
}
