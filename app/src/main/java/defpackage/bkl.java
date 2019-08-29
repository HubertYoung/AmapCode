package defpackage;

import android.content.Context;
import com.autonavi.common.KeyValueStorage.WebStorage;
import com.autonavi.common.SQLiteMapper;
import com.autonavi.common.impl.io.DynamicStorageImpl;
import com.autonavi.common.impl.io.SQLiteMapperImpl;
import java.util.HashMap;
import java.util.Map;

/* renamed from: bkl reason: default package */
/* compiled from: StorageFactory */
public final class bkl {
    public static final bkl a = new bkl();
    private Map<Object, Object> b = new HashMap();

    private bkl() {
    }

    public final <T> SQLiteMapper<T> a(Class<T> cls, Context context) {
        SQLiteMapper<T> sQLiteMapper = (SQLiteMapper) this.b.get(cls);
        if (sQLiteMapper == null) {
            synchronized (this.b) {
                try {
                    sQLiteMapper = (SQLiteMapper) this.b.get(cls);
                    if (sQLiteMapper == null) {
                        sQLiteMapper = new SQLiteMapperImpl<>(context, cls);
                        this.b.put(cls, sQLiteMapper);
                    }
                }
            }
        }
        return sQLiteMapper;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:16|17) */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r0 = r10.getName();
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x003e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <T extends com.autonavi.common.KeyValueStorage<?>> T b(java.lang.Class<T> r10, android.content.Context r11) {
        /*
            r9 = this;
            boolean r0 = r10.isInterface()
            if (r0 != 0) goto L_0x0013
            java.lang.String r0 = "KvStroage  必须从接口创建！"
            java.lang.String r1 = java.lang.String.valueOf(r10)
            java.lang.String r0 = r0.concat(r1)
            defpackage.bkj.a(r0)
        L_0x0013:
            java.util.Map<java.lang.Object, java.lang.Object> r0 = r9.b
            java.lang.Object r0 = r0.get(r10)
            com.autonavi.common.KeyValueStorage r0 = (com.autonavi.common.KeyValueStorage) r0
            if (r0 != 0) goto L_0x00af
            java.util.Map<java.lang.Object, java.lang.Object> r1 = r9.b
            monitor-enter(r1)
            java.util.Map<java.lang.Object, java.lang.Object> r0 = r9.b     // Catch:{ all -> 0x00ac }
            java.lang.Object r0 = r0.get(r10)     // Catch:{ all -> 0x00ac }
            com.autonavi.common.KeyValueStorage r0 = (com.autonavi.common.KeyValueStorage) r0     // Catch:{ all -> 0x00ac }
            if (r0 != 0) goto L_0x00aa
            java.lang.Class<com.autonavi.common.KeyValueStorage$StorageKey> r0 = com.autonavi.common.KeyValueStorage.StorageKey.class
            java.lang.annotation.Annotation r0 = r10.getAnnotation(r0)     // Catch:{ Exception -> 0x003e }
            com.autonavi.common.KeyValueStorage$StorageKey r0 = (com.autonavi.common.KeyValueStorage.StorageKey) r0     // Catch:{ Exception -> 0x003e }
            if (r0 != 0) goto L_0x0039
            java.lang.String r0 = r10.getName()     // Catch:{ Exception -> 0x003e }
            goto L_0x0042
        L_0x0039:
            java.lang.String r0 = r0.name()     // Catch:{ Exception -> 0x003e }
            goto L_0x0042
        L_0x003e:
            java.lang.String r0 = r10.getName()     // Catch:{ all -> 0x00ac }
        L_0x0042:
            com.autonavi.common.KeyValueStorage r2 = defpackage.bki.a(r10, r11, r0)     // Catch:{ all -> 0x00ac }
            java.util.Map<java.lang.Object, java.lang.Object> r3 = r9.b     // Catch:{ all -> 0x00ac }
            r3.put(r10, r2)     // Catch:{ all -> 0x00ac }
            r3 = 0
            android.content.SharedPreferences r11 = r11.getSharedPreferences(r0, r3)     // Catch:{ all -> 0x00ac }
            java.lang.Class[] r0 = r10.getDeclaredClasses()     // Catch:{ all -> 0x00ac }
            if (r0 == 0) goto L_0x00a9
            int r4 = r0.length     // Catch:{ all -> 0x00ac }
            if (r4 > 0) goto L_0x005a
            goto L_0x00a9
        L_0x005a:
            r4 = 0
            int r5 = r0.length     // Catch:{ all -> 0x00ac }
            r6 = 0
        L_0x005d:
            if (r6 >= r5) goto L_0x006e
            r7 = r0[r6]     // Catch:{ all -> 0x00ac }
            java.lang.Class<com.autonavi.common.KeyValueStorage$a> r8 = com.autonavi.common.KeyValueStorage.a.class
            boolean r8 = r8.isAssignableFrom(r7)     // Catch:{ all -> 0x00ac }
            if (r8 == 0) goto L_0x006b
            r4 = r7
            goto L_0x006e
        L_0x006b:
            int r6 = r6 + 1
            goto L_0x005d
        L_0x006e:
            if (r4 == 0) goto L_0x00a9
            java.lang.String r0 = "#version"
            r5 = -1
            int r0 = r11.getInt(r0, r5)     // Catch:{ all -> 0x00ac }
            r6 = 1
            if (r0 != r5) goto L_0x007c
            r0 = 1
            r3 = 1
        L_0x007c:
            java.lang.Class<com.autonavi.common.KeyValueStorage$StorageKey> r5 = com.autonavi.common.KeyValueStorage.StorageKey.class
            java.lang.annotation.Annotation r10 = r10.getAnnotation(r5)     // Catch:{ all -> 0x00ac }
            com.autonavi.common.KeyValueStorage$StorageKey r10 = (com.autonavi.common.KeyValueStorage.StorageKey) r10     // Catch:{ all -> 0x00ac }
            if (r10 == 0) goto L_0x008b
            int r10 = r10.version()     // Catch:{ all -> 0x00ac }
            goto L_0x008c
        L_0x008b:
            r10 = r0
        L_0x008c:
            r4.newInstance()     // Catch:{ InstantiationException -> 0x00a5, IllegalAccessException -> 0x00a0 }
            if (r3 != 0) goto L_0x00a9
            if (r10 <= r0) goto L_0x00a9
            android.content.SharedPreferences$Editor r11 = r11.edit()     // Catch:{ InstantiationException -> 0x00a5, IllegalAccessException -> 0x00a0 }
            java.lang.String r0 = "#version"
            r11.putInt(r0, r10)     // Catch:{ InstantiationException -> 0x00a5, IllegalAccessException -> 0x00a0 }
            r11.commit()     // Catch:{ InstantiationException -> 0x00a5, IllegalAccessException -> 0x00a0 }
            goto L_0x00a9
        L_0x00a0:
            r10 = move-exception
            r10.printStackTrace()     // Catch:{ all -> 0x00ac }
            goto L_0x00a9
        L_0x00a5:
            r10 = move-exception
            r10.printStackTrace()     // Catch:{ all -> 0x00ac }
        L_0x00a9:
            r0 = r2
        L_0x00aa:
            monitor-exit(r1)     // Catch:{ all -> 0x00ac }
            goto L_0x00af
        L_0x00ac:
            r10 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00ac }
            throw r10
        L_0x00af:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bkl.b(java.lang.Class, android.content.Context):com.autonavi.common.KeyValueStorage");
    }

    public final WebStorage a(String str) {
        WebStorage webStorage = (WebStorage) this.b.get(str);
        if (webStorage == null) {
            synchronized (this.b) {
                try {
                    webStorage = (WebStorage) this.b.get(str);
                    if (webStorage == null) {
                        webStorage = new DynamicStorageImpl(str);
                        this.b.put(str, webStorage);
                    }
                }
            }
        }
        return webStorage;
    }
}
