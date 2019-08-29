package com.autonavi.minimap.ajx3.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import java.io.File;

public class SnapshotUtil {
    /* access modifiers changed from: private */
    public static String AJX3_SNAPSHOT_PATH = null;
    private static int ID = 0;
    public static int SAVE_DCIM = 1;
    public static int SAVE_SDCARD = 0;
    private static String SNAPSHOT_SUFFIX_PNG = ".png";

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getExternalStorageDirectory());
        sb.append("/autonavi/data/ajx3/snapshot");
        AJX3_SNAPSHOT_PATH = sb.toString();
    }

    @Nullable
    public static String snapShotByView(View view, int i) {
        String saveBitmapToAmapSdcard;
        String str = null;
        if (view.getWidth() == 0 || view.getHeight() == 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
        view.draw(new SnapshotCanvas(createBitmap));
        try {
            if (i == SAVE_DCIM) {
                saveBitmapToAmapSdcard = saveBitmapToAlbum(view.getContext(), createBitmap);
            } else {
                if (i == SAVE_SDCARD) {
                    saveBitmapToAmapSdcard = saveBitmapToAmapSdcard(createBitmap);
                }
                createBitmap.recycle();
                return str;
            }
            str = saveBitmapToAmapSdcard;
        } catch (Exception e) {
            e.printStackTrace();
        }
        createBitmap.recycle();
        return str;
    }

    public static Bitmap getSnapShotByView(View view) {
        if (view.getWidth() == 0 || view.getHeight() == 0) {
            return null;
        }
        new StringBuilder("overlay getSnapShotByView view=").append(view);
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
        view.draw(new SnapshotCanvas(createBitmap));
        return createBitmap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x0143  */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String saveBitmapToAlbum(android.content.Context r7, android.graphics.Bitmap r8) throws java.lang.Exception {
        /*
            r0 = 0
            if (r8 != 0) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = isHasSDCard()     // Catch:{ all -> 0x0140 }
            if (r1 != 0) goto L_0x000b
            return r0
        L_0x000b:
            r1 = 1
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0140 }
            r2.<init>()     // Catch:{ all -> 0x0140 }
            java.lang.String r3 = com.autonavi.minimap.ajx3.util.FileUtil.getExternalStorageDirectory()     // Catch:{ all -> 0x0140 }
            r2.append(r3)     // Catch:{ all -> 0x0140 }
            java.lang.String r3 = java.io.File.separator     // Catch:{ all -> 0x0140 }
            r2.append(r3)     // Catch:{ all -> 0x0140 }
            java.lang.String r3 = android.os.Environment.DIRECTORY_DCIM     // Catch:{ all -> 0x0140 }
            r2.append(r3)     // Catch:{ all -> 0x0140 }
            java.lang.String r3 = java.io.File.separator     // Catch:{ all -> 0x0140 }
            r2.append(r3)     // Catch:{ all -> 0x0140 }
            java.lang.String r3 = "Camera"
            r2.append(r3)     // Catch:{ all -> 0x0140 }
            java.lang.String r3 = java.io.File.separator     // Catch:{ all -> 0x0140 }
            r2.append(r3)     // Catch:{ all -> 0x0140 }
            java.lang.String r3 = "snapshot_"
            r2.append(r3)     // Catch:{ all -> 0x0140 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0140 }
            r2.append(r3)     // Catch:{ all -> 0x0140 }
            java.lang.String r3 = SNAPSHOT_SUFFIX_PNG     // Catch:{ all -> 0x0140 }
            r2.append(r3)     // Catch:{ all -> 0x0140 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0140 }
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x0140 }
            r3.<init>(r2)     // Catch:{ all -> 0x0140 }
            java.io.File r4 = r3.getParentFile()     // Catch:{ all -> 0x0140 }
            boolean r4 = r4.exists()     // Catch:{ all -> 0x0140 }
            r5 = 0
            if (r4 != 0) goto L_0x0062
            java.io.File r4 = r3.getParentFile()     // Catch:{ all -> 0x0140 }
            boolean r4 = r4.mkdirs()     // Catch:{ all -> 0x0140 }
            if (r4 != 0) goto L_0x0062
            r1 = 0
        L_0x0062:
            java.io.File r4 = r3.getParentFile()     // Catch:{ all -> 0x0140 }
            boolean r4 = r4.isDirectory()     // Catch:{ all -> 0x0140 }
            if (r4 == 0) goto L_0x0080
            java.io.File r4 = r3.getParentFile()     // Catch:{ all -> 0x0140 }
            boolean r4 = r4.canRead()     // Catch:{ all -> 0x0140 }
            if (r4 == 0) goto L_0x0080
            java.io.File r4 = r3.getParentFile()     // Catch:{ all -> 0x0140 }
            boolean r4 = r4.canWrite()     // Catch:{ all -> 0x0140 }
            if (r4 != 0) goto L_0x0081
        L_0x0080:
            r1 = 0
        L_0x0081:
            if (r1 != 0) goto L_0x00f0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0140 }
            r1.<init>()     // Catch:{ all -> 0x0140 }
            java.io.File r2 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ all -> 0x0140 }
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ all -> 0x0140 }
            r1.append(r2)     // Catch:{ all -> 0x0140 }
            java.lang.String r2 = java.io.File.separator     // Catch:{ all -> 0x0140 }
            r1.append(r2)     // Catch:{ all -> 0x0140 }
            java.lang.String r2 = android.os.Environment.DIRECTORY_PICTURES     // Catch:{ all -> 0x0140 }
            r1.append(r2)     // Catch:{ all -> 0x0140 }
            java.lang.String r2 = java.io.File.separator     // Catch:{ all -> 0x0140 }
            r1.append(r2)     // Catch:{ all -> 0x0140 }
            java.lang.String r2 = "snapshot_"
            r1.append(r2)     // Catch:{ all -> 0x0140 }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0140 }
            r1.append(r2)     // Catch:{ all -> 0x0140 }
            java.lang.String r2 = SNAPSHOT_SUFFIX_PNG     // Catch:{ all -> 0x0140 }
            r1.append(r2)     // Catch:{ all -> 0x0140 }
            java.lang.String r2 = r1.toString()     // Catch:{ all -> 0x0140 }
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x0140 }
            r3.<init>(r2)     // Catch:{ all -> 0x0140 }
            java.io.File r1 = r3.getParentFile()     // Catch:{ all -> 0x0140 }
            boolean r1 = r1.exists()     // Catch:{ all -> 0x0140 }
            if (r1 != 0) goto L_0x00f0
            java.io.File r1 = r3.getParentFile()     // Catch:{ all -> 0x0140 }
            boolean r1 = r1.mkdirs()     // Catch:{ all -> 0x0140 }
            if (r1 != 0) goto L_0x00f0
            java.io.File r1 = r3.getParentFile()     // Catch:{ all -> 0x0140 }
            boolean r1 = r1.exists()     // Catch:{ all -> 0x0140 }
            if (r1 == 0) goto L_0x00ef
            java.io.File r1 = r3.getParentFile()     // Catch:{ all -> 0x0140 }
            boolean r1 = r1.canRead()     // Catch:{ all -> 0x0140 }
            if (r1 == 0) goto L_0x00ef
            java.io.File r1 = r3.getParentFile()     // Catch:{ all -> 0x0140 }
            boolean r1 = r1.canWrite()     // Catch:{ all -> 0x0140 }
            if (r1 != 0) goto L_0x00f0
        L_0x00ef:
            return r0
        L_0x00f0:
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x0140 }
            r1.<init>(r3)     // Catch:{ all -> 0x0140 }
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ all -> 0x013d }
            r4 = 100
            boolean r8 = r8.compress(r3, r4, r1)     // Catch:{ all -> 0x013d }
            if (r8 == 0) goto L_0x0139
            java.io.File r8 = new java.io.File     // Catch:{ all -> 0x013d }
            r8.<init>(r2)     // Catch:{ all -> 0x013d }
            long r3 = r8.length()     // Catch:{ all -> 0x013d }
            r5 = 0
            int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 != 0) goto L_0x010f
            goto L_0x0139
        L_0x010f:
            boolean r8 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x013d }
            if (r8 != 0) goto L_0x012b
            android.content.Intent r8 = new android.content.Intent     // Catch:{ all -> 0x013d }
            java.lang.String r0 = "android.intent.action.MEDIA_SCANNER_SCAN_FILE"
            r8.<init>(r0)     // Catch:{ all -> 0x013d }
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x013d }
            r0.<init>(r2)     // Catch:{ all -> 0x013d }
            android.net.Uri r0 = android.net.Uri.fromFile(r0)     // Catch:{ all -> 0x013d }
            r8.setData(r0)     // Catch:{ all -> 0x013d }
            r7.sendBroadcast(r8)     // Catch:{ all -> 0x013d }
        L_0x012b:
            java.lang.String r7 = "file:/"
            java.lang.String r8 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x013d }
            java.lang.String r7 = r7.concat(r8)     // Catch:{ all -> 0x013d }
            r1.close()
            return r7
        L_0x0139:
            r1.close()
            return r0
        L_0x013d:
            r7 = move-exception
            r0 = r1
            goto L_0x0141
        L_0x0140:
            r7 = move-exception
        L_0x0141:
            if (r0 == 0) goto L_0x0146
            r0.close()
        L_0x0146:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.SnapshotUtil.saveBitmapToAlbum(android.content.Context, android.graphics.Bitmap):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x009d  */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String saveBitmapToAmapSdcard(android.graphics.Bitmap r8) throws java.lang.Exception {
        /*
            r0 = 0
            if (r8 != 0) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = isHasSDCard()     // Catch:{ all -> 0x009a }
            if (r1 != 0) goto L_0x000b
            return r0
        L_0x000b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x009a }
            r1.<init>()     // Catch:{ all -> 0x009a }
            java.lang.String r2 = AJX3_SNAPSHOT_PATH     // Catch:{ all -> 0x009a }
            r1.append(r2)     // Catch:{ all -> 0x009a }
            java.lang.String r2 = "/snapshot_temp_"
            r1.append(r2)     // Catch:{ all -> 0x009a }
            int r2 = ID     // Catch:{ all -> 0x009a }
            int r3 = r2 + 1
            ID = r3     // Catch:{ all -> 0x009a }
            r1.append(r2)     // Catch:{ all -> 0x009a }
            java.lang.String r2 = SNAPSHOT_SUFFIX_PNG     // Catch:{ all -> 0x009a }
            r1.append(r2)     // Catch:{ all -> 0x009a }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x009a }
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x009a }
            r2.<init>(r1)     // Catch:{ all -> 0x009a }
            java.io.File r3 = r2.getParentFile()     // Catch:{ all -> 0x009a }
            boolean r3 = r3.exists()     // Catch:{ all -> 0x009a }
            if (r3 != 0) goto L_0x0046
            java.io.File r3 = r2.getParentFile()     // Catch:{ all -> 0x009a }
            boolean r3 = r3.mkdirs()     // Catch:{ all -> 0x009a }
            if (r3 != 0) goto L_0x0046
            return r0
        L_0x0046:
            java.io.File r3 = r2.getParentFile()     // Catch:{ all -> 0x009a }
            boolean r3 = r3.isDirectory()     // Catch:{ all -> 0x009a }
            if (r3 == 0) goto L_0x0099
            java.io.File r3 = r2.getParentFile()     // Catch:{ all -> 0x009a }
            boolean r3 = r3.canRead()     // Catch:{ all -> 0x009a }
            if (r3 == 0) goto L_0x0099
            java.io.File r3 = r2.getParentFile()     // Catch:{ all -> 0x009a }
            boolean r3 = r3.canWrite()     // Catch:{ all -> 0x009a }
            if (r3 != 0) goto L_0x0065
            goto L_0x0099
        L_0x0065:
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ all -> 0x009a }
            r3.<init>(r2)     // Catch:{ all -> 0x009a }
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ all -> 0x0096 }
            r4 = 100
            boolean r8 = r8.compress(r2, r4, r3)     // Catch:{ all -> 0x0096 }
            if (r8 == 0) goto L_0x0092
            java.io.File r8 = new java.io.File     // Catch:{ all -> 0x0096 }
            r8.<init>(r1)     // Catch:{ all -> 0x0096 }
            long r4 = r8.length()     // Catch:{ all -> 0x0096 }
            r6 = 0
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 != 0) goto L_0x0084
            goto L_0x0092
        L_0x0084:
            java.lang.String r8 = "file:/"
            java.lang.String r0 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x0096 }
            java.lang.String r8 = r8.concat(r0)     // Catch:{ all -> 0x0096 }
            r3.close()
            return r8
        L_0x0092:
            r3.close()
            return r0
        L_0x0096:
            r8 = move-exception
            r0 = r3
            goto L_0x009b
        L_0x0099:
            return r0
        L_0x009a:
            r8 = move-exception
        L_0x009b:
            if (r0 == 0) goto L_0x00a0
            r0.close()
        L_0x00a0:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.SnapshotUtil.saveBitmapToAmapSdcard(android.graphics.Bitmap):java.lang.String");
    }

    /* access modifiers changed from: private */
    public static boolean isHasSDCard() {
        return Environment.getExternalStorageState().equals("mounted") && FileUtil.getExternalStorageDirectory() != null;
    }

    public static void deleteAjx3Snapshot() {
        new Thread(new Runnable() {
            public final void run() {
                if (SnapshotUtil.isHasSDCard()) {
                    File file = new File(SnapshotUtil.AJX3_SNAPSHOT_PATH);
                    if (file.exists() && file.isDirectory() && file.listFiles() != null) {
                        File[] listFiles = file.listFiles();
                        if (listFiles != null) {
                            try {
                                for (File delete : listFiles) {
                                    delete.delete();
                                }
                            } catch (Exception unused) {
                            }
                        }
                    }
                }
            }
        }).start();
    }
}
