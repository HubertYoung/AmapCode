package com.huawei.android.pushselfshow.richpush.tools;

import android.content.Context;

public class c {
    private String a;
    private Context b;

    public c(Context context, String str) {
        this.a = str;
        this.b = context;
    }

    private String b() {
        return "﻿<!DOCTYPE html>\t\t<html>\t\t   <head>\t\t     <meta charset=\"utf-8\">\t\t     <title></title>\t\t     <style type=\"text/css\">\t\t\t\t html { height:100%;}\t\t\t\t body { height:100%; text-align:center;}\t    \t    .centerDiv { display:inline-block; zoom:1; *display:inline; vertical-align:top; text-align:left; width:200px; padding:10px;margin-top:100px;}\t\t\t   .hiddenDiv { height:100%; overflow:hidden; display:inline-block; width:1px; overflow:hidden; margin-left:-1px; zoom:1; *display:inline; *margin-top:-1px; _margin-top:0; vertical-align:middle;}\t\t  \t</style>    \t  </head>\t\t <body>\t\t\t<div id =\"container\" class=\"centerDiv\">";
    }

    private String c() {
        return "﻿\t\t</div>  \t\t<div class=\"hiddenDiv\"></div>\t  </body>   </html>";
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ea A[SYNTHETIC, Splitter:B:37:0x00ea] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00fb A[SYNTHETIC, Splitter:B:46:0x00fb] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String a() {
        /*
            r8 = this;
            android.content.Context r0 = r8.b
            r1 = 0
            if (r0 != 0) goto L_0x000d
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r2 = "CreateHtmlFile fail ,context is null"
            com.huawei.android.pushagent.a.a.c.d(r0, r2)
            return r1
        L_0x000d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = r8.b()
            r0.append(r2)
            java.lang.String r2 = r8.a
            r0.append(r2)
            java.lang.String r2 = r8.c()
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            android.content.Context r3 = r8.b
            java.io.File r3 = r3.getFilesDir()
            java.lang.String r3 = r3.getPath()
            r2.append(r3)
            java.lang.String r3 = java.io.File.separator
            r2.append(r3)
            java.lang.String r3 = "PushService"
            r2.append(r3)
            java.lang.String r3 = java.io.File.separator
            r2.append(r3)
            java.lang.String r3 = "richpush"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "error.html"
            java.io.File r4 = new java.io.File
            r4.<init>(r2)
            java.io.File r5 = new java.io.File
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r2)
            java.lang.String r7 = java.io.File.separator
            r6.append(r7)
            r6.append(r3)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            boolean r6 = r4.exists()     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            if (r6 != 0) goto L_0x0096
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.String r7 = "Create the path:"
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            java.lang.String r2 = r7.concat(r2)     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            com.huawei.android.pushagent.a.a.c.a(r6, r2)     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            boolean r2 = r4.mkdirs()     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            if (r2 != 0) goto L_0x0096
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r2 = "!path.mkdirs()"
            com.huawei.android.pushagent.a.a.c.a(r0, r2)     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            return r1
        L_0x0096:
            boolean r2 = r5.exists()     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            if (r2 == 0) goto L_0x009f
            com.huawei.android.pushselfshow.utils.a.a(r5)     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
        L_0x009f:
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r4 = "Create the file:"
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            java.lang.String r3 = r4.concat(r3)     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            com.huawei.android.pushagent.a.a.c.a(r2, r3)     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            boolean r2 = r5.createNewFile()     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            if (r2 != 0) goto L_0x00bc
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r2 = "!file.createNewFile()"
            com.huawei.android.pushagent.a.a.c.a(r0, r2)     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            return r1
        L_0x00bc:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            r2.<init>(r5)     // Catch:{ Exception -> 0x00df, all -> 0x00dd }
            java.lang.String r3 = "UTF-8"
            byte[] r0 = r0.getBytes(r3)     // Catch:{ Exception -> 0x00db }
            r2.write(r0)     // Catch:{ Exception -> 0x00db }
            r2.close()     // Catch:{ Exception -> 0x00ce }
            goto L_0x00d6
        L_0x00ce:
            r0 = move-exception
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = "stream.close() error "
            com.huawei.android.pushagent.a.a.c.a(r1, r2, r0)
        L_0x00d6:
            java.lang.String r0 = r5.getAbsolutePath()
            return r0
        L_0x00db:
            r0 = move-exception
            goto L_0x00e1
        L_0x00dd:
            r0 = move-exception
            goto L_0x00f9
        L_0x00df:
            r0 = move-exception
            r2 = r1
        L_0x00e1:
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r4 = "Create html error "
            com.huawei.android.pushagent.a.a.c.a(r3, r4, r0)     // Catch:{ all -> 0x00f7 }
            if (r2 == 0) goto L_0x00f6
            r2.close()     // Catch:{ Exception -> 0x00ee }
            return r1
        L_0x00ee:
            r0 = move-exception
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = "stream.close() error "
            com.huawei.android.pushagent.a.a.c.a(r2, r3, r0)
        L_0x00f6:
            return r1
        L_0x00f7:
            r0 = move-exception
            r1 = r2
        L_0x00f9:
            if (r1 == 0) goto L_0x0107
            r1.close()     // Catch:{ Exception -> 0x00ff }
            goto L_0x0107
        L_0x00ff:
            r1 = move-exception
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = "stream.close() error "
            com.huawei.android.pushagent.a.a.c.a(r2, r3, r1)
        L_0x0107:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.tools.c.a():java.lang.String");
    }
}
