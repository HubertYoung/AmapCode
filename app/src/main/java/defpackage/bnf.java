package defpackage;

import android.text.TextUtils;
import java.io.File;

/* renamed from: bnf reason: default package */
/* compiled from: OpenUploadUtil */
public final class bnf {
    private static char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static void a(int i, String str, String str2, String str3, String str4, File file, bmy bmy) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4) && file != null && file.exists()) {
            StringBuilder sb = new StringBuilder("channel: ");
            sb.append(str);
            sb.append("; key: ");
            sb.append(str2);
            String a2 = a(i, str, str2, str3, str4, file);
            if (!TextUtils.isEmpty(a2)) {
                new bmz();
                bmz.a(a2, file, bmy);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00d3 A[SYNTHETIC, Splitter:B:32:0x00d3] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00df A[SYNTHETIC, Splitter:B:39:0x00df] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(int r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, java.io.File r13) {
        /*
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r1 = 50
            r2 = 0
            byte[] r3 = new byte[r1]     // Catch:{ Exception -> 0x00cc, all -> 0x00c9 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00cc, all -> 0x00c9 }
            r4.<init>(r13)     // Catch:{ Exception -> 0x00cc, all -> 0x00c9 }
            int r13 = r4.read(r3)     // Catch:{ Exception -> 0x00c7 }
            if (r13 == r1) goto L_0x001e
            r4.close()     // Catch:{ IOException -> 0x0019 }
            goto L_0x001d
        L_0x0019:
            r8 = move-exception
            r8.printStackTrace()
        L_0x001d:
            return r2
        L_0x001e:
            r13 = 0
        L_0x001f:
            if (r13 >= r1) goto L_0x003a
            byte r5 = r3[r13]     // Catch:{ Exception -> 0x00c7 }
            char[] r6 = a     // Catch:{ Exception -> 0x00c7 }
            int r7 = r5 >> 4
            r7 = r7 & 15
            char r6 = r6[r7]     // Catch:{ Exception -> 0x00c7 }
            r0.append(r6)     // Catch:{ Exception -> 0x00c7 }
            char[] r6 = a     // Catch:{ Exception -> 0x00c7 }
            r5 = r5 & 15
            char r5 = r6[r5]     // Catch:{ Exception -> 0x00c7 }
            r0.append(r5)     // Catch:{ Exception -> 0x00c7 }
            int r13 = r13 + 1
            goto L_0x001f
        L_0x003a:
            r13 = 64
            r0.append(r13)     // Catch:{ Exception -> 0x00c7 }
            r0.append(r10)     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r10 = r0.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r10 = defpackage.bmq.a(r10)     // Catch:{ Exception -> 0x00c7 }
            boolean r13 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x00c7 }
            if (r13 != 0) goto L_0x0054
            java.lang.String r10 = r10.toUpperCase()     // Catch:{ Exception -> 0x00c7 }
        L_0x0054:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c7 }
            r13.<init>()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = "product="
            r13.append(r1)     // Catch:{ Exception -> 0x00c7 }
            r13.append(r8)     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r8 = "&type="
            r13.append(r8)     // Catch:{ Exception -> 0x00c7 }
            r13.append(r11)     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r8 = "&platform="
            r13.append(r8)     // Catch:{ Exception -> 0x00c7 }
            r13.append(r12)     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r8 = "&channel="
            r13.append(r8)     // Catch:{ Exception -> 0x00c7 }
            r13.append(r9)     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r8 = "&sign="
            r13.append(r8)     // Catch:{ Exception -> 0x00c7 }
            r13.append(r10)     // Catch:{ Exception -> 0x00c7 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r9 = "getUploadUrl sign:"
            r8.<init>(r9)     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r9 = r0.toString()     // Catch:{ Exception -> 0x00c7 }
            r8.append(r9)     // Catch:{ Exception -> 0x00c7 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r9 = "getUploadUrl:"
            r8.<init>(r9)     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r9 = r13.toString()     // Catch:{ Exception -> 0x00c7 }
            r8.append(r9)     // Catch:{ Exception -> 0x00c7 }
            com.autonavi.common.tool.sign.Sign r8 = new com.autonavi.common.tool.sign.Sign     // Catch:{ Exception -> 0x00c7 }
            r8.<init>()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r9 = r13.toString()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r8 = r8.sign(r9)     // Catch:{ Exception -> 0x00c7 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r10 = "http://logs.amap.com/ws/log/upload/?in="
            r9.<init>(r10)     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r10 = "UTF-8"
            java.lang.String r8 = java.net.URLEncoder.encode(r8, r10)     // Catch:{ Exception -> 0x00c7 }
            r9.append(r8)     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r8 = r9.toString()     // Catch:{ Exception -> 0x00c7 }
            r4.close()     // Catch:{ IOException -> 0x00c2 }
            goto L_0x00c6
        L_0x00c2:
            r9 = move-exception
            r9.printStackTrace()
        L_0x00c6:
            return r8
        L_0x00c7:
            r8 = move-exception
            goto L_0x00ce
        L_0x00c9:
            r8 = move-exception
            r4 = r2
            goto L_0x00dd
        L_0x00cc:
            r8 = move-exception
            r4 = r2
        L_0x00ce:
            r8.printStackTrace()     // Catch:{ all -> 0x00dc }
            if (r4 == 0) goto L_0x00db
            r4.close()     // Catch:{ IOException -> 0x00d7 }
            goto L_0x00db
        L_0x00d7:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00db:
            return r2
        L_0x00dc:
            r8 = move-exception
        L_0x00dd:
            if (r4 == 0) goto L_0x00e7
            r4.close()     // Catch:{ IOException -> 0x00e3 }
            goto L_0x00e7
        L_0x00e3:
            r9 = move-exception
            r9.printStackTrace()
        L_0x00e7:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bnf.a(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.io.File):java.lang.String");
    }
}
