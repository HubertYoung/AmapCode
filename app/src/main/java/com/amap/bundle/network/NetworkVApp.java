package com.amap.bundle.network;

import com.autonavi.annotation.VirtualApp;

@VirtualApp(priority = 100)
public class NetworkVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        zw.a(true);
        if (aaf.a != null) {
            aaf.a.b();
        }
    }

    public void vAppEnterForeground() {
        zk a = zk.a();
        if (Math.abs(System.currentTimeMillis() - a.c) >= ((long) a.b)) {
            zl.a();
            if (zl.b()) {
                ahm.a(new Runnable() {
                    /* JADX WARNING: Removed duplicated region for block: B:56:0x00f2 A[Catch:{ Exception -> 0x018d }] */
                    /* JADX WARNING: Removed duplicated region for block: B:88:0x0171 A[SYNTHETIC, Splitter:B:88:0x0171] */
                    /* JADX WARNING: Removed duplicated region for block: B:96:0x017c A[SYNTHETIC, Splitter:B:96:0x017c] */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final void run() {
                        /*
                            r17 = this;
                            r1 = r17
                            zk r2 = defpackage.zk.this
                            long r3 = java.lang.System.currentTimeMillis()
                            r2.c = r3
                            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x018d }
                            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x018d }
                            r4.<init>()     // Catch:{ Exception -> 0x018d }
                            java.lang.String r5 = r2.d     // Catch:{ Exception -> 0x018d }
                            r4.append(r5)     // Catch:{ Exception -> 0x018d }
                            java.lang.String r5 = "/apm/cache/"
                            r4.append(r5)     // Catch:{ Exception -> 0x018d }
                            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x018d }
                            r3.<init>(r4)     // Catch:{ Exception -> 0x018d }
                            boolean r4 = r3.exists()     // Catch:{ Exception -> 0x018d }
                            if (r4 == 0) goto L_0x018c
                            boolean r4 = r3.isDirectory()     // Catch:{ Exception -> 0x018d }
                            if (r4 == 0) goto L_0x018c
                            boolean r4 = r3.canRead()     // Catch:{ Exception -> 0x018d }
                            if (r4 != 0) goto L_0x0036
                            goto L_0x018c
                        L_0x0036:
                            java.io.File[] r3 = r3.listFiles()     // Catch:{ Exception -> 0x018d }
                            if (r3 == 0) goto L_0x018b
                            int r4 = r3.length     // Catch:{ Exception -> 0x018d }
                            if (r4 != 0) goto L_0x0041
                            goto L_0x018b
                        L_0x0041:
                            boolean r4 = com.amap.bundle.network.util.NetworkReachability.a()     // Catch:{ Exception -> 0x018d }
                            org.json.JSONArray r5 = new org.json.JSONArray     // Catch:{ Exception -> 0x018d }
                            r5.<init>()     // Catch:{ Exception -> 0x018d }
                            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x018d }
                            r10 = 0
                            r11 = 0
                            r12 = 0
                        L_0x0052:
                            int r14 = r3.length     // Catch:{ Exception -> 0x018d }
                            if (r11 >= r14) goto L_0x0100
                            r14 = r3[r11]     // Catch:{ Exception -> 0x018d }
                            if (r14 == 0) goto L_0x00f9
                            java.lang.String r15 = r14.getName()     // Catch:{ Exception -> 0x00ea }
                            java.lang.String r8 = "\\.tmpFile_"
                            boolean r8 = r15.startsWith(r8)     // Catch:{ Exception -> 0x00ea }
                            if (r8 == 0) goto L_0x0090
                            java.lang.String r8 = "\\."
                            java.lang.String[] r8 = r15.split(r8)     // Catch:{ Exception -> 0x00ea }
                            r8 = r8[r10]     // Catch:{ Exception -> 0x00ea }
                            long r8 = java.lang.Long.parseLong(r8)     // Catch:{ Exception -> 0x00ea }
                            r15 = 0
                            long r8 = r6 - r8
                            long r8 = java.lang.Math.abs(r8)     // Catch:{ Exception -> 0x00ea }
                            int r15 = r2.a     // Catch:{ Exception -> 0x00ea }
                            r16 = r11
                            long r10 = (long) r15
                            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
                            if (r8 <= 0) goto L_0x0086
                            r14.delete()     // Catch:{ Exception -> 0x00e8 }
                            goto L_0x00fb
                        L_0x0086:
                            r8 = 0
                            java.io.File r9 = defpackage.zp.a(r14, r8)     // Catch:{ Exception -> 0x00e8 }
                            r2.a(r9, r14)     // Catch:{ Exception -> 0x00e8 }
                            goto L_0x00fb
                        L_0x0090:
                            r16 = r11
                            java.lang.String r8 = "\\."
                            java.lang.String[] r8 = r15.split(r8)     // Catch:{ Exception -> 0x00a0 }
                            r9 = 0
                            r8 = r8[r9]     // Catch:{ Exception -> 0x00a0 }
                            long r8 = java.lang.Long.parseLong(r8)     // Catch:{ Exception -> 0x00a0 }
                            goto L_0x00a2
                        L_0x00a0:
                            r8 = 0
                        L_0x00a2:
                            r10 = 0
                            long r8 = r6 - r8
                            long r8 = java.lang.Math.abs(r8)     // Catch:{ Exception -> 0x00e8 }
                            int r10 = r2.a     // Catch:{ Exception -> 0x00e8 }
                            long r10 = (long) r10     // Catch:{ Exception -> 0x00e8 }
                            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
                            if (r8 <= 0) goto L_0x00b4
                            r14.delete()     // Catch:{ Exception -> 0x00e8 }
                            goto L_0x00fb
                        L_0x00b4:
                            if (r4 != 0) goto L_0x00bd
                            r8 = 40960(0xa000, double:2.0237E-319)
                            int r8 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
                            if (r8 > 0) goto L_0x0100
                        L_0x00bd:
                            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00e8 }
                            r8.<init>(r14)     // Catch:{ Exception -> 0x00e8 }
                            java.lang.String r8 = defpackage.ahe.b(r8)     // Catch:{ Exception -> 0x00e8 }
                            org.json.JSONArray r9 = new org.json.JSONArray     // Catch:{ Exception -> 0x00e8 }
                            r9.<init>(r8)     // Catch:{ Exception -> 0x00e8 }
                            int r10 = r9.length()     // Catch:{ Exception -> 0x00e8 }
                            r11 = 0
                        L_0x00d0:
                            if (r11 >= r10) goto L_0x00de
                            org.json.JSONObject r15 = r9.getJSONObject(r11)     // Catch:{ Exception -> 0x00e8 }
                            if (r15 == 0) goto L_0x00db
                            r5.put(r15)     // Catch:{ Exception -> 0x00e8 }
                        L_0x00db:
                            int r11 = r11 + 1
                            goto L_0x00d0
                        L_0x00de:
                            r14.delete()     // Catch:{ Exception -> 0x00e8 }
                            int r8 = r8.length()     // Catch:{ Exception -> 0x00e8 }
                            long r8 = (long) r8
                            long r12 = r12 + r8
                            goto L_0x00fb
                        L_0x00e8:
                            r0 = move-exception
                            goto L_0x00ed
                        L_0x00ea:
                            r0 = move-exception
                            r16 = r11
                        L_0x00ed:
                            r8 = r0
                            boolean r9 = defpackage.bno.a     // Catch:{ Exception -> 0x018d }
                            if (r9 == 0) goto L_0x00f5
                            r8.printStackTrace()     // Catch:{ Exception -> 0x018d }
                        L_0x00f5:
                            r14.delete()     // Catch:{ Exception -> 0x018d }
                            goto L_0x00fb
                        L_0x00f9:
                            r16 = r11
                        L_0x00fb:
                            int r11 = r16 + 1
                            r10 = 0
                            goto L_0x0052
                        L_0x0100:
                            int r3 = r5.length()     // Catch:{ Exception -> 0x018d }
                            if (r3 != 0) goto L_0x0107
                            return
                        L_0x0107:
                            java.lang.String r3 = r5.toString()     // Catch:{ Exception -> 0x018d }
                            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x018d }
                            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x018d }
                            r5.<init>()     // Catch:{ Exception -> 0x018d }
                            java.lang.String r6 = r2.d     // Catch:{ Exception -> 0x018d }
                            r5.append(r6)     // Catch:{ Exception -> 0x018d }
                            java.lang.String r6 = "/apm/cache/.tmpFile_"
                            r5.append(r6)     // Catch:{ Exception -> 0x018d }
                            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x018d }
                            r5.append(r6)     // Catch:{ Exception -> 0x018d }
                            java.lang.String r6 = ".zip"
                            r5.append(r6)     // Catch:{ Exception -> 0x018d }
                            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x018d }
                            r4.<init>(r5)     // Catch:{ Exception -> 0x018d }
                            boolean r5 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x018d }
                            if (r5 != 0) goto L_0x0182
                            java.io.File r5 = r4.getParentFile()     // Catch:{ Exception -> 0x018d }
                            boolean r6 = r5.exists()     // Catch:{ Exception -> 0x018d }
                            if (r6 != 0) goto L_0x0142
                            r5.mkdirs()     // Catch:{ Exception -> 0x018d }
                        L_0x0142:
                            boolean r5 = r4.exists()     // Catch:{ Exception -> 0x018d }
                            if (r5 == 0) goto L_0x014b
                            r4.delete()     // Catch:{ Exception -> 0x018d }
                        L_0x014b:
                            r5 = 0
                            r4.createNewFile()     // Catch:{ Exception -> 0x017a, all -> 0x016d }
                            byte[] r3 = r3.getBytes()     // Catch:{ Exception -> 0x017a, all -> 0x016d }
                            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x017a, all -> 0x016d }
                            r6.<init>(r4)     // Catch:{ Exception -> 0x017a, all -> 0x016d }
                            r6.write(r3)     // Catch:{ Exception -> 0x016b, all -> 0x0167 }
                            r6.flush()     // Catch:{ Exception -> 0x016b, all -> 0x0167 }
                            r6.close()     // Catch:{ IOException -> 0x0162 }
                            goto L_0x0182
                        L_0x0162:
                            r0 = move-exception
                        L_0x0163:
                            r0.printStackTrace()     // Catch:{ Exception -> 0x018d }
                            goto L_0x0182
                        L_0x0167:
                            r0 = move-exception
                            r2 = r0
                            r5 = r6
                            goto L_0x016f
                        L_0x016b:
                            r5 = r6
                            goto L_0x017a
                        L_0x016d:
                            r0 = move-exception
                            r2 = r0
                        L_0x016f:
                            if (r5 == 0) goto L_0x0179
                            r5.close()     // Catch:{ IOException -> 0x0175 }
                            goto L_0x0179
                        L_0x0175:
                            r0 = move-exception
                            r0.printStackTrace()     // Catch:{ Exception -> 0x018d }
                        L_0x0179:
                            throw r2     // Catch:{ Exception -> 0x018d }
                        L_0x017a:
                            if (r5 == 0) goto L_0x0182
                            r5.close()     // Catch:{ IOException -> 0x0180 }
                            goto L_0x0182
                        L_0x0180:
                            r0 = move-exception
                            goto L_0x0163
                        L_0x0182:
                            r3 = 0
                            java.io.File r3 = defpackage.zp.a(r4, r3)     // Catch:{ Exception -> 0x018d }
                            r2.a(r3, r4)     // Catch:{ Exception -> 0x018d }
                            return
                        L_0x018b:
                            return
                        L_0x018c:
                            return
                        L_0x018d:
                            r0 = move-exception
                            r2 = r0
                            boolean r3 = defpackage.bno.a
                            if (r3 == 0) goto L_0x0197
                            r2.printStackTrace()
                            throw r2
                        L_0x0197:
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: defpackage.zk.AnonymousClass2.run():void");
                    }
                });
            }
        }
    }

    public void vAppMapLoadCompleted() {
        zw.a();
        aao.d().a();
    }
}
