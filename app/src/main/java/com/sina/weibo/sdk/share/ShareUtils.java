package com.sina.weibo.sdk.share;

public final class ShareUtils {
    /* JADX WARNING: Removed duplicated region for block: B:24:0x009f A[Catch:{ Exception -> 0x0139, all -> 0x0136 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a6 A[Catch:{ Exception -> 0x0139, all -> 0x0136 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b0 A[Catch:{ Exception -> 0x0139, all -> 0x0136 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x010b A[Catch:{ Exception -> 0x0133, all -> 0x0130 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x011e A[Catch:{ Exception -> 0x012e }, LOOP:0: B:48:0x0117->B:50:0x011e, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0147 A[SYNTHETIC, Splitter:B:69:0x0147] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x014c A[Catch:{ Exception -> 0x014f }] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0154 A[SYNTHETIC, Splitter:B:78:0x0154] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0159 A[Catch:{ Exception -> 0x015c }] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0123 A[EDGE_INSN: B:84:0x0123->B:51:0x0123 ?: BREAK  
    EDGE_INSN: B:84:0x0123->B:51:0x0123 ?: BREAK  
    EDGE_INSN: B:84:0x0123->B:51:0x0123 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0123 A[EDGE_INSN: B:84:0x0123->B:51:0x0123 ?: BREAK  
    EDGE_INSN: B:84:0x0123->B:51:0x0123 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static java.lang.String copyFileToWeiboTem(android.content.Context r8, android.net.Uri r9, int r10) {
        /*
            r0 = 0
            com.sina.weibo.sdk.auth.WbAppInfo r1 = com.sina.weibo.sdk.WeiboAppManager.queryWbInfoInternal(r8)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.lang.String r1 = r1.getPackageName()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            if (r2 == 0) goto L_0x0011
            java.lang.String r1 = "com.sina.weibo"
        L_0x0011:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.lang.String r3 = "/Android/data/"
            r2.<init>(r3)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            r2.append(r1)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.lang.String r1 = "/files/.composerTem/"
            r2.append(r1)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            r3.<init>()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.io.File r4 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            r3.append(r4)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            r3.append(r1)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            r2.mkdirs()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.util.Calendar r2 = java.util.Calendar.getInstance()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.lang.String r3 = r9.getScheme()     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            java.lang.String r4 = "file"
            boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            if (r3 == 0) goto L_0x006d
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            r3.<init>()     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            long r4 = r2.getTimeInMillis()     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            r3.append(r4)     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            java.lang.String r2 = r9.getLastPathSegment()     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            r3.append(r2)     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            java.lang.String r2 = r3.toString()     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            r3 = r2
            r2 = r0
            goto L_0x0095
        L_0x006d:
            android.content.ContentResolver r2 = r8.getContentResolver()     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            java.lang.String r3 = "_display_name"
            java.lang.String[] r4 = new java.lang.String[]{r3}     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            r5 = 0
            r6 = 0
            r7 = 0
            r3 = r9
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            if (r2 == 0) goto L_0x0094
            boolean r3 = r2.moveToFirst()     // Catch:{ Exception -> 0x00a4, all -> 0x0092 }
            if (r3 == 0) goto L_0x0094
            java.lang.String r3 = "_display_name"
            int r3 = r2.getColumnIndex(r3)     // Catch:{ Exception -> 0x00a4, all -> 0x0092 }
            java.lang.String r3 = r2.getString(r3)     // Catch:{ Exception -> 0x00a4, all -> 0x0092 }
            goto L_0x0095
        L_0x0092:
            r8 = move-exception
            goto L_0x009d
        L_0x0094:
            r3 = r0
        L_0x0095:
            if (r2 == 0) goto L_0x00aa
            r2.close()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            goto L_0x00aa
        L_0x009b:
            r8 = move-exception
            r2 = r0
        L_0x009d:
            if (r2 == 0) goto L_0x00a2
            r2.close()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
        L_0x00a2:
            throw r8     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
        L_0x00a3:
            r2 = r0
        L_0x00a4:
            if (r2 == 0) goto L_0x00a9
            r2.close()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
        L_0x00a9:
            r3 = r0
        L_0x00aa:
            boolean r2 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            if (r2 == 0) goto L_0x00ce
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            r2.<init>()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.util.Calendar r3 = java.util.Calendar.getInstance()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            long r3 = r3.getTimeInMillis()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            r2.append(r3)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            if (r10 != 0) goto L_0x00c5
            java.lang.String r10 = "_sdk_temp.mp4"
            goto L_0x00c7
        L_0x00c5:
            java.lang.String r10 = "_sdk_temp.jpg"
        L_0x00c7:
            r2.append(r10)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.lang.String r3 = r2.toString()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
        L_0x00ce:
            android.content.ContentResolver r8 = r8.getContentResolver()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.lang.String r10 = "r"
            android.os.ParcelFileDescriptor r8 = r8.openFileDescriptor(r9, r10)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.io.FileDescriptor r8 = r8.getFileDescriptor()     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.io.BufferedInputStream r9 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.io.FileInputStream r10 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            r10.<init>(r8)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            r9.<init>(r10)     // Catch:{ Exception -> 0x0139, all -> 0x0136 }
            java.io.File r8 = new java.io.File     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            r10.<init>()     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            java.io.File r2 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            r10.append(r2)     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            r10.append(r1)     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            r10.append(r3)     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            r8.<init>(r10)     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            boolean r10 = r8.exists()     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            if (r10 == 0) goto L_0x010e
            r8.delete()     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
        L_0x010e:
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            r10.<init>(r8)     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            r1 = 1444(0x5a4, float:2.023E-42)
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x012e }
        L_0x0117:
            int r2 = r9.read(r1)     // Catch:{ Exception -> 0x012e }
            r3 = -1
            if (r2 == r3) goto L_0x0123
            r3 = 0
            r10.write(r1, r3, r2)     // Catch:{ Exception -> 0x012e }
            goto L_0x0117
        L_0x0123:
            java.lang.String r8 = r8.getPath()     // Catch:{ Exception -> 0x012e }
            r9.close()     // Catch:{ Exception -> 0x012d }
            r10.close()     // Catch:{ Exception -> 0x012d }
        L_0x012d:
            return r8
        L_0x012e:
            r8 = move-exception
            goto L_0x013c
        L_0x0130:
            r8 = move-exception
            r10 = r0
            goto L_0x0151
        L_0x0133:
            r8 = move-exception
            r10 = r0
            goto L_0x013c
        L_0x0136:
            r8 = move-exception
            r10 = r0
            goto L_0x0152
        L_0x0139:
            r8 = move-exception
            r9 = r0
            r10 = r9
        L_0x013c:
            java.lang.String r1 = "weibo sdk copy"
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0150 }
            com.sina.weibo.sdk.utils.LogUtil.e(r1, r8)     // Catch:{ all -> 0x0150 }
            if (r9 == 0) goto L_0x014a
            r9.close()     // Catch:{ Exception -> 0x014f }
        L_0x014a:
            if (r10 == 0) goto L_0x014f
            r10.close()     // Catch:{ Exception -> 0x014f }
        L_0x014f:
            return r0
        L_0x0150:
            r8 = move-exception
        L_0x0151:
            r0 = r9
        L_0x0152:
            if (r0 == 0) goto L_0x0157
            r0.close()     // Catch:{ Exception -> 0x015c }
        L_0x0157:
            if (r10 == 0) goto L_0x015c
            r10.close()     // Catch:{ Exception -> 0x015c }
        L_0x015c:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.share.ShareUtils.copyFileToWeiboTem(android.content.Context, android.net.Uri, int):java.lang.String");
    }
}
