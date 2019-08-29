package defpackage;

import android.webkit.MimeTypeMap;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.util.UUID;

/* renamed from: zp reason: default package */
/* compiled from: ApmUploadUtil */
public final class zp {
    public static File a(File file, boolean z) {
        if (file == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(file.getParent());
        sb.append("/");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(UUID.randomUUID().toString());
        sb3.append(FilePathHelper.SUFFIX_DOT_ZIP);
        File file2 = new File(sb3.toString());
        StringBuilder sb4 = new StringBuilder("compressFile ---> ");
        sb4.append(file);
        sb4.append(" zipFile ");
        sb4.append(file2);
        AMapLog.d("ApmUploadUtil", sb4.toString());
        try {
            if (!file2.exists()) {
                file2.createNewFile();
            }
            a(file2, file);
            if (z) {
                file.delete();
            }
            return file2;
        } catch (Exception e) {
            StringBuilder sb5 = new StringBuilder("compressFile failed ---> ");
            sb5.append(e);
            sb5.append(" exception ");
            sb5.append(e.getCause().getMessage());
            AMapLog.d("ApmUploadUtil", sb5.toString());
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0045 A[SYNTHETIC, Splitter:B:26:0x0045] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x004a A[Catch:{ IOException -> 0x004d }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0051 A[SYNTHETIC, Splitter:B:36:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0056 A[Catch:{ IOException -> 0x005a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(java.io.File r3, java.io.File r4) {
        /*
            boolean r0 = r4.isFile()
            if (r0 == 0) goto L_0x005c
            r0 = 0
            java.util.zip.ZipOutputStream r1 = new java.util.zip.ZipOutputStream     // Catch:{ IOException -> 0x004e, all -> 0x0041 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x004e, all -> 0x0041 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x004e, all -> 0x0041 }
            r1.<init>(r2)     // Catch:{ IOException -> 0x004e, all -> 0x0041 }
            java.util.zip.ZipEntry r3 = new java.util.zip.ZipEntry     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            java.lang.String r2 = r4.getName()     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            r3.<init>(r2)     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            r1.putNextEntry(r3)     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            r3.<init>(r4)     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            r4 = 2048(0x800, float:2.87E-42)
            byte[] r4 = new byte[r4]     // Catch:{ IOException -> 0x003d, all -> 0x003a }
        L_0x0026:
            int r0 = r3.read(r4)     // Catch:{ IOException -> 0x003d, all -> 0x003a }
            r2 = -1
            if (r0 == r2) goto L_0x0032
            r2 = 0
            r1.write(r4, r2, r0)     // Catch:{ IOException -> 0x003d, all -> 0x003a }
            goto L_0x0026
        L_0x0032:
            r3.close()     // Catch:{ IOException -> 0x0039 }
            r1.close()     // Catch:{ IOException -> 0x0039 }
            return
        L_0x0039:
            return
        L_0x003a:
            r4 = move-exception
            r0 = r3
            goto L_0x0043
        L_0x003d:
            r0 = r3
            goto L_0x004f
        L_0x003f:
            r4 = move-exception
            goto L_0x0043
        L_0x0041:
            r4 = move-exception
            r1 = r0
        L_0x0043:
            if (r0 == 0) goto L_0x0048
            r0.close()     // Catch:{ IOException -> 0x004d }
        L_0x0048:
            if (r1 == 0) goto L_0x004d
            r1.close()     // Catch:{ IOException -> 0x004d }
        L_0x004d:
            throw r4
        L_0x004e:
            r1 = r0
        L_0x004f:
            if (r0 == 0) goto L_0x0054
            r0.close()     // Catch:{ IOException -> 0x005a }
        L_0x0054:
            if (r1 == 0) goto L_0x005b
            r1.close()     // Catch:{ IOException -> 0x005a }
            goto L_0x005b
        L_0x005a:
            return
        L_0x005b:
            return
        L_0x005c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zp.a(java.io.File, java.io.File):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x004d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.io.File r5, com.autonavi.common.Callback<java.lang.Integer> r6) {
        /*
            java.lang.String r0 = "ApmUploadUtil"
            java.lang.String r1 = "uploadLogSync data --> "
            java.lang.String r2 = java.lang.String.valueOf(r5)
            java.lang.String r1 = r1.concat(r2)
            com.amap.bundle.logs.AMapLog.d(r0, r1)
            aae$k r0 = defpackage.aaf.e()
            if (r0 != 0) goto L_0x0017
            return
        L_0x0017:
            boolean r1 = r5.exists()
            if (r1 == 0) goto L_0x007e
            long r1 = r5.length()
            r3 = 1
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 <= 0) goto L_0x007e
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x003e }
            r2.<init>(r5)     // Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x003e }
            r3 = 50
            byte[] r3 = new byte[r3]     // Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x003e }
            r2.read(r3)     // Catch:{ FileNotFoundException -> 0x003b, IOException -> 0x0038 }
            r2.close()     // Catch:{ FileNotFoundException -> 0x003b, IOException -> 0x0038 }
            goto L_0x0049
        L_0x0038:
            r1 = move-exception
            r2 = r1
            goto L_0x0040
        L_0x003b:
            r1 = move-exception
            r2 = r1
            goto L_0x0046
        L_0x003e:
            r2 = move-exception
            r3 = r1
        L_0x0040:
            r2.printStackTrace()
            goto L_0x0049
        L_0x0044:
            r2 = move-exception
            r3 = r1
        L_0x0046:
            r2.printStackTrace()
        L_0x0049:
            java.lang.String r1 = ""
            if (r3 == 0) goto L_0x0051
            java.lang.String r1 = defpackage.agx.a(r3)
        L_0x0051:
            java.lang.String r0 = r0.a(r1)
            com.amap.bundle.aosservice.request.AosFileUploadRequest r1 = new com.amap.bundle.aosservice.request.AosFileUploadRequest
            r1.<init>()
            r1.setUrl(r0)
            r0 = 1
            r1.setWithoutSign(r0)
            r0 = -1
            r1.setCommonParamStrategy(r0)
            r1.a(r5)
            java.lang.String r5 = r5.getName()
            java.lang.String r5 = a(r5)
            r1.a(r5)
            defpackage.yq.a()
            com.amap.bundle.network.biz.statistic.apm.ApmUploadUtil$1 r5 = new com.amap.bundle.network.biz.statistic.apm.ApmUploadUtil$1
            r5.<init>(r6)
            defpackage.yq.a(r1, r5)
        L_0x007e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zp.a(java.io.File, com.autonavi.common.Callback):void");
    }

    private static String a(String str) {
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return FilePart.DEFAULT_CONTENT_TYPE;
        }
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(lastIndexOf + 1));
    }
}
