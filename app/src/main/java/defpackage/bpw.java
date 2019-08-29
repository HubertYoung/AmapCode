package defpackage;

/* renamed from: bpw reason: default package */
/* compiled from: SdCardUtil */
public final class bpw {
    /* JADX WARNING: Removed duplicated region for block: B:57:? A[ExcHandler: IllegalAccessException | NoSuchMethodException | InvocationTargetException (unused java.lang.Throwable), SYNTHETIC, Splitter:B:3:0x000b] */
    @android.annotation.SuppressLint({"NewApi"})
    @android.annotation.TargetApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r17) {
        /*
            r0 = r17
            int r1 = android.os.Build.VERSION.SDK_INT
            r3 = 12
            if (r1 < r3) goto L_0x00f3
            java.lang.String r3 = "storage"
            java.lang.Object r3 = r0.getSystemService(r3)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            android.os.storage.StorageManager r3 = (android.os.storage.StorageManager) r3     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Class<android.os.storage.StorageManager> r4 = android.os.storage.StorageManager.class
            java.lang.String r5 = "getVolumeList"
            r6 = 0
            java.lang.Class[] r7 = new java.lang.Class[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.reflect.Method r4 = r4.getMethod(r5, r7)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Class<android.os.storage.StorageManager> r5 = android.os.storage.StorageManager.class
            java.lang.String r7 = "getVolumeState"
            r8 = 1
            java.lang.Class[] r9 = new java.lang.Class[r8]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Class<java.lang.String> r10 = java.lang.String.class
            r9[r6] = r10     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.reflect.Method r5 = r5.getMethod(r7, r9)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Object r4 = r4.invoke(r3, r7)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Object[] r4 = (java.lang.Object[]) r4     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.String r7 = ""
            java.lang.String r9 = ""
            int r10 = r4.length     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            r11 = r9
            r9 = r7
            r7 = 0
        L_0x003b:
            if (r7 >= r10) goto L_0x00d0
            r13 = r4[r7]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Class r14 = r13.getClass()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.String r15 = "getPath"
            java.lang.Class[] r2 = new java.lang.Class[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.reflect.Method r2 = r14.getMethod(r15, r2)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Class r14 = r13.getClass()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.String r15 = "isRemovable"
            java.lang.Class[] r12 = new java.lang.Class[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.reflect.Method r12 = r14.getMethod(r15, r12)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Object[] r14 = new java.lang.Object[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Object r14 = r2.invoke(r13, r14)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.String r14 = (java.lang.String) r14     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Object[] r15 = new java.lang.Object[r8]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Object[] r8 = new java.lang.Object[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Object r2 = r2.invoke(r13, r8)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            r15[r6] = r2     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Object r2 = r5.invoke(r3, r15)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Object[] r8 = new java.lang.Object[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Object r8 = r12.invoke(r13, r8)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.util.Locale r12 = java.util.Locale.US     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.String r12 = r14.toLowerCase(r12)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            java.lang.String r13 = "private"
            boolean r12 = r12.contains(r13)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            if (r12 != 0) goto L_0x00cb
            boolean r8 = r8.booleanValue()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            if (r8 == 0) goto L_0x00c9
            if (r14 == 0) goto L_0x00cb
            if (r2 == 0) goto L_0x00cb
            java.lang.String r8 = "mounted"
            boolean r2 = r2.equals(r8)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            if (r2 == 0) goto L_0x00cb
            r2 = 18
            if (r1 > r2) goto L_0x009c
            goto L_0x00c6
        L_0x009c:
            r2 = 0
            java.io.File[] r0 = r0.getExternalFilesDirs(r2)     // Catch:{ Throwable -> 0x00c6, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            if (r0 == 0) goto L_0x00c4
            int r2 = r0.length     // Catch:{ Throwable -> 0x00c6, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            if (r2 <= 0) goto L_0x00c4
            int r2 = r0.length     // Catch:{ Throwable -> 0x00c6, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            r3 = r14
        L_0x00a8:
            if (r6 >= r2) goto L_0x00c2
            r4 = r0[r6]     // Catch:{ Throwable -> 0x00c6, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            if (r4 == 0) goto L_0x00bf
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ Throwable -> 0x00c6, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x00c6, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            if (r5 != 0) goto L_0x00bf
            boolean r5 = r4.contains(r14)     // Catch:{ Throwable -> 0x00c6, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            if (r5 == 0) goto L_0x00bf
            r3 = r4
        L_0x00bf:
            int r6 = r6 + 1
            goto L_0x00a8
        L_0x00c2:
            r2 = r3
            goto L_0x00c5
        L_0x00c4:
            r2 = r14
        L_0x00c5:
            r14 = r2
        L_0x00c6:
            r0 = 18
            goto L_0x00d3
        L_0x00c9:
            r9 = r2
            r11 = r14
        L_0x00cb:
            int r7 = r7 + 1
            r8 = 1
            goto L_0x003b
        L_0x00d0:
            r0 = 18
            r14 = 0
        L_0x00d3:
            if (r1 > r0) goto L_0x00e5
            if (r14 != 0) goto L_0x00e4
            if (r11 == 0) goto L_0x00e4
            if (r9 == 0) goto L_0x00e4
            java.lang.String r0 = "mounted"
            boolean r0 = r9.equals(r0)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            if (r0 == 0) goto L_0x00e4
            r14 = r11
        L_0x00e4:
            return r14
        L_0x00e5:
            if (r11 == 0) goto L_0x00f2
            if (r9 == 0) goto L_0x00f2
            java.lang.String r0 = "mounted"
            boolean r0 = r9.equals(r0)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f3 }
            if (r0 == 0) goto L_0x00f2
            r14 = r11
        L_0x00f2:
            return r14
        L_0x00f3:
            java.lang.String r0 = android.os.Environment.getExternalStorageState()
            java.lang.String r1 = "mounted"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0108
            java.io.File r0 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r0 = r0.toString()
            return r0
        L_0x0108:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bpw.a(android.content.Context):java.lang.String");
    }
}
