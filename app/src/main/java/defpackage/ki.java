package defpackage;

/* renamed from: ki reason: default package */
/* compiled from: MediaUtil */
public final class ki {
    private static String a = "MediaUtil";

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00ad, code lost:
        if (r3 != null) goto L_0x0087;
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0097 A[Catch:{ all -> 0x00a8, all -> 0x00b1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x009c A[Catch:{ all -> 0x00a8, all -> 0x00b1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00a4 A[Catch:{ all -> 0x00a8, all -> 0x00b1 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r7, android.graphics.Bitmap r8, java.lang.String r9, java.lang.String r10) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 0
            r2 = 29
            if (r0 >= r2) goto L_0x0030
            android.content.ContentResolver r0 = r7.getContentResolver()     // Catch:{ Exception -> 0x0022 }
            java.lang.String r8 = android.provider.MediaStore.Images.Media.insertImage(r0, r8, r9, r10)     // Catch:{ Exception -> 0x0022 }
            if (r8 == 0) goto L_0x002f
            android.content.Intent r9 = new android.content.Intent     // Catch:{ Exception -> 0x0020 }
            java.lang.String r10 = "android.intent.action.MEDIA_SCANNER_SCAN_FILE"
            android.net.Uri r0 = android.net.Uri.parse(r8)     // Catch:{ Exception -> 0x0020 }
            r9.<init>(r10, r0)     // Catch:{ Exception -> 0x0020 }
            r7.sendBroadcast(r9)     // Catch:{ Exception -> 0x0020 }
            goto L_0x002f
        L_0x0020:
            r7 = move-exception
            goto L_0x0024
        L_0x0022:
            r7 = move-exception
            r8 = r1
        L_0x0024:
            java.lang.String r9 = "paas.blutils"
            java.lang.String r10 = a
            java.lang.String r7 = java.lang.String.valueOf(r7)
            com.amap.bundle.logs.AMapLog.warning(r9, r10, r7)
        L_0x002f:
            return r8
        L_0x0030:
            android.content.ContentValues r10 = new android.content.ContentValues
            r10.<init>()
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 != 0) goto L_0x0040
            java.lang.String r0 = "title"
            r10.put(r0, r9)
        L_0x0040:
            java.lang.String r9 = "mime_type"
            java.lang.String r0 = "image/jpeg"
            r10.put(r9, r0)
            java.lang.String r9 = "is_pending"
            r0 = 1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r10.put(r9, r0)
            android.content.ContentResolver r7 = r7.getContentResolver()
            java.lang.String r9 = "external_primary"
            android.net.Uri r9 = android.provider.MediaStore.Images.Media.getContentUri(r9)
            android.net.Uri r9 = r7.insert(r9, r10)
            java.lang.String r0 = "w"
            android.os.ParcelFileDescriptor r0 = r7.openFileDescriptor(r9, r0, r1)     // Catch:{ IOException -> 0x00cc }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00a0, all -> 0x0092 }
            java.io.FileDescriptor r3 = r0.getFileDescriptor()     // Catch:{ IOException -> 0x00a0, all -> 0x0092 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x00a0, all -> 0x0092 }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0090, all -> 0x008d }
            r3.<init>()     // Catch:{ IOException -> 0x0090, all -> 0x008d }
            android.graphics.Bitmap$CompressFormat r4 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ IOException -> 0x00a2, all -> 0x008b }
            r5 = 100
            r8.compress(r4, r5, r3)     // Catch:{ IOException -> 0x00a2, all -> 0x008b }
            byte[] r8 = r3.toByteArray()     // Catch:{ IOException -> 0x00a2, all -> 0x008b }
            r2.write(r8)     // Catch:{ IOException -> 0x00a2, all -> 0x008b }
            r2.flush()     // Catch:{ IOException -> 0x00a2, all -> 0x008b }
            r2.close()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a8 }
        L_0x0087:
            r3.close()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a8 }
            goto L_0x00c6
        L_0x008b:
            r8 = move-exception
            goto L_0x0095
        L_0x008d:
            r8 = move-exception
            r3 = r1
            goto L_0x0095
        L_0x0090:
            r3 = r1
            goto L_0x00a2
        L_0x0092:
            r8 = move-exception
            r2 = r1
            r3 = r2
        L_0x0095:
            if (r2 == 0) goto L_0x009a
            r2.close()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a8 }
        L_0x009a:
            if (r3 == 0) goto L_0x009f
            r3.close()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a8 }
        L_0x009f:
            throw r8     // Catch:{ Throwable -> 0x00ab, all -> 0x00a8 }
        L_0x00a0:
            r2 = r1
            r3 = r2
        L_0x00a2:
            if (r2 == 0) goto L_0x00ad
            r2.close()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a8 }
            goto L_0x00ad
        L_0x00a8:
            r8 = move-exception
            r2 = r1
            goto L_0x00b5
        L_0x00ab:
            r8 = move-exception
            goto L_0x00b0
        L_0x00ad:
            if (r3 == 0) goto L_0x00c6
            goto L_0x0087
        L_0x00b0:
            throw r8     // Catch:{ all -> 0x00b1 }
        L_0x00b1:
            r2 = move-exception
            r6 = r2
            r2 = r8
            r8 = r6
        L_0x00b5:
            if (r0 == 0) goto L_0x00c5
            if (r2 == 0) goto L_0x00c2
            r0.close()     // Catch:{ Throwable -> 0x00bd }
            goto L_0x00c5
        L_0x00bd:
            r0 = move-exception
            r2.addSuppressed(r0)     // Catch:{ IOException -> 0x00cc }
            goto L_0x00c5
        L_0x00c2:
            r0.close()     // Catch:{ IOException -> 0x00cc }
        L_0x00c5:
            throw r8     // Catch:{ IOException -> 0x00cc }
        L_0x00c6:
            if (r0 == 0) goto L_0x00d0
            r0.close()     // Catch:{ IOException -> 0x00cc }
            goto L_0x00d0
        L_0x00cc:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00d0:
            r10.clear()
            java.lang.String r8 = "is_pending"
            r0 = 0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r10.put(r8, r0)
            r7.update(r9, r10, r1, r1)
            java.lang.String r7 = r9.toString()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ki.a(android.content.Context, android.graphics.Bitmap, java.lang.String, java.lang.String):java.lang.String");
    }
}
