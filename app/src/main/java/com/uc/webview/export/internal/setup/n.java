package com.uc.webview.export.internal.setup;

/* compiled from: ProGuard */
public class n extends t {
    /* JADX WARNING: Can't wrap try/catch for region: R(10:175|176|177|178|179|180|181|182|183|184) */
    /* JADX WARNING: Can't wrap try/catch for region: R(19:60|61|62|63|(1:65)(1:66)|67|68|(3:86|87|88)(1:93)|94|95|96|(1:98)(1:99)|100|(7:102|103|104|105|106|107|108)(1:123)|124|125|126|127|128) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:127:0x0322 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:183:0x04b7 */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x025d A[Catch:{ UCKnownException -> 0x03ab, all -> 0x03a2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x029c  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0170 A[Catch:{ UCKnownException -> 0x0175, all -> 0x0173 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x017f A[SYNTHETIC, Splitter:B:50:0x017f] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x01b5 A[Catch:{ Exception -> 0x020a, UCKnownException -> 0x0202, all -> 0x01f7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x01f4  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0219  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0243  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0253 A[Catch:{ UCKnownException -> 0x03ab, all -> 0x03a2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0256 A[Catch:{ UCKnownException -> 0x03ab, all -> 0x03a2 }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:127:0x0322=Splitter:B:127:0x0322, B:183:0x04b7=Splitter:B:183:0x04b7} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r24 = this;
            r1 = r24
            java.lang.String r2 = "ucmZipDir"
            java.lang.Object r2 = r1.getOption(r2)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r3 = "ucmZipFile"
            java.lang.Object r3 = r1.getOption(r3)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "bo_dec_root_dir"
            java.lang.Object r4 = r1.getOption(r4)
            java.lang.String r4 = (java.lang.String) r4
            java.lang.String r5 = "DecompressSetupTask"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "zipDirPath : "
            r6.<init>(r7)
            r6.append(r2)
            java.lang.String r7 = " zipFilePath : "
            r6.append(r7)
            r6.append(r3)
            java.lang.String r7 = "decRootDirPath : "
            r6.append(r7)
            r6.append(r4)
            java.lang.String r6 = r6.toString()
            com.uc.webview.export.internal.utility.Log.d(r5, r6)
            boolean r5 = com.uc.webview.export.internal.utility.j.a(r2)
            boolean r6 = com.uc.webview.export.internal.utility.j.a(r3)
            r7 = 2
            r8 = 1
            r9 = 0
            if (r5 == 0) goto L_0x004c
            if (r6 != 0) goto L_0x0050
        L_0x004c:
            if (r5 != 0) goto L_0x0068
            if (r6 != 0) goto L_0x0068
        L_0x0050:
            com.uc.webview.export.internal.setup.UCSetupException r2 = new com.uc.webview.export.internal.setup.UCSetupException
            r3 = 3010(0xbc2, float:4.218E-42)
            java.lang.String r4 = "Option [%s] or  [%s] expected."
            java.lang.Object[] r5 = new java.lang.Object[r7]
            java.lang.String r6 = "ucmZipDir"
            r5[r9] = r6
            java.lang.String r6 = "ucmZipFile"
            r5[r8] = r6
            java.lang.String r4 = java.lang.String.format(r4, r5)
            r2.<init>(r3, r4)
            throw r2
        L_0x0068:
            java.lang.String r5 = "CONTEXT"
            java.lang.Object r5 = r1.getOption(r5)
            android.content.Context r5 = (android.content.Context) r5
            if (r6 == 0) goto L_0x0082
            r3 = 10028(0x272c, float:1.4052E-41)
            java.lang.Object[] r6 = new java.lang.Object[r7]
            r6[r9] = r2
            r6[r8] = r5
            java.lang.Object r3 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r3, r6)
            java.io.File r3 = (java.io.File) r3
        L_0x0080:
            r12 = r3
            goto L_0x0087
        L_0x0082:
            java.io.File r3 = com.uc.webview.export.cyclone.UCCyclone.expectFile(r3)
            goto L_0x0080
        L_0x0087:
            if (r12 != 0) goto L_0x009b
            com.uc.webview.export.internal.setup.UCSetupException r3 = new com.uc.webview.export.internal.setup.UCSetupException
            r4 = 3011(0xbc3, float:4.22E-42)
            java.lang.String r5 = "No kernel file found in dir [%s]."
            java.lang.Object[] r6 = new java.lang.Object[r8]
            r6[r9] = r2
            java.lang.String r2 = java.lang.String.format(r5, r6)
            r3.<init>(r4, r2)
            throw r3
        L_0x009b:
            java.lang.Class<com.uc.webview.export.internal.setup.n> r2 = com.uc.webview.export.internal.setup.n.class
            monitor-enter(r2)
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r4)     // Catch:{ all -> 0x04b8 }
            if (r3 != 0) goto L_0x00aa
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x04b8 }
            r3.<init>(r4)     // Catch:{ all -> 0x04b8 }
            goto L_0x00b6
        L_0x00aa:
            r3 = 10003(0x2713, float:1.4017E-41)
            java.lang.Object[] r4 = new java.lang.Object[r8]     // Catch:{ all -> 0x04b8 }
            r4[r9] = r5     // Catch:{ all -> 0x04b8 }
            java.lang.Object r3 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r3, r4)     // Catch:{ all -> 0x04b8 }
            java.io.File r3 = (java.io.File) r3     // Catch:{ all -> 0x04b8 }
        L_0x00b6:
            java.lang.Object[] r4 = new java.lang.Object[r7]     // Catch:{ all -> 0x04b8 }
            r4[r9] = r3     // Catch:{ all -> 0x04b8 }
            java.lang.String r3 = r12.getAbsolutePath()     // Catch:{ all -> 0x04b8 }
            java.lang.String r3 = com.uc.webview.export.cyclone.UCCyclone.getSourceHash(r3)     // Catch:{ all -> 0x04b8 }
            r4[r8] = r3     // Catch:{ all -> 0x04b8 }
            r3 = 10035(0x2733, float:1.4062E-41)
            java.lang.Object r4 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r3, r4)     // Catch:{ all -> 0x04b8 }
            java.io.File r4 = (java.io.File) r4     // Catch:{ all -> 0x04b8 }
            java.lang.Object[] r6 = new java.lang.Object[r7]     // Catch:{ all -> 0x04b8 }
            r6[r9] = r4     // Catch:{ all -> 0x04b8 }
            long r10 = r12.length()     // Catch:{ all -> 0x04b8 }
            long r13 = r12.lastModified()     // Catch:{ all -> 0x04b8 }
            java.lang.String r4 = com.uc.webview.export.cyclone.UCCyclone.getSourceHash(r10, r13)     // Catch:{ all -> 0x04b8 }
            r6[r8] = r4     // Catch:{ all -> 0x04b8 }
            java.lang.Object r3 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r3, r6)     // Catch:{ all -> 0x04b8 }
            java.io.File r3 = (java.io.File) r3     // Catch:{ all -> 0x04b8 }
            java.lang.String r4 = ""
            java.lang.String r6 = "0"
            java.lang.String r7 = "0"
            java.lang.String r15 = ""
            com.uc.webview.export.cyclone.UCElapseTime r14 = new com.uc.webview.export.cyclone.UCElapseTime     // Catch:{ UCKnownException -> 0x0410, all -> 0x03ff }
            r14.<init>()     // Catch:{ UCKnownException -> 0x0410, all -> 0x03ff }
            long r16 = r3.getFreeSpace()     // Catch:{ UCKnownException -> 0x0410, all -> 0x03ff }
            r18 = 1024(0x400, double:5.06E-321)
            long r10 = r16 / r18
            float r13 = (float) r10
            java.lang.String r10 = com.uc.webview.export.cyclone.UCCyclone.serverZipTag     // Catch:{ UCKnownException -> 0x03f1, all -> 0x03e0 }
            java.lang.String r11 = "o_zio_file_type"
            java.lang.Object r11 = r1.getOption(r11)     // Catch:{ UCKnownException -> 0x03f1, all -> 0x03e0 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ UCKnownException -> 0x03f1, all -> 0x03e0 }
            boolean r11 = r10.equalsIgnoreCase(r11)     // Catch:{ UCKnownException -> 0x03f1, all -> 0x03e0 }
            java.lang.String r10 = "bo_del_aft_extract"
            java.lang.Object r10 = r1.getOption(r10)     // Catch:{ UCKnownException -> 0x03f1, all -> 0x03e0 }
            java.lang.Boolean r10 = (java.lang.Boolean) r10     // Catch:{ UCKnownException -> 0x03f1, all -> 0x03e0 }
            boolean r10 = com.uc.webview.export.internal.utility.j.a(r10)     // Catch:{ UCKnownException -> 0x03f1, all -> 0x03e0 }
            java.util.concurrent.ConcurrentHashMap r9 = r1.mOptions     // Catch:{ UCKnownException -> 0x03f1, all -> 0x03e0 }
            java.lang.String r8 = "scst_flag"
            java.lang.Object r8 = r9.get(r8)     // Catch:{ UCKnownException -> 0x03f1, all -> 0x03e0 }
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch:{ UCKnownException -> 0x03f1, all -> 0x03e0 }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r8)     // Catch:{ UCKnownException -> 0x03f1, all -> 0x03e0 }
            if (r8 == 0) goto L_0x016b
            java.lang.String r8 = "ucmZipFile"
            java.lang.Object r8 = r1.getOption(r8)     // Catch:{ UCKnownException -> 0x0161, all -> 0x0158 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ UCKnownException -> 0x0161, all -> 0x0158 }
            java.lang.String r9 = "sc_ta_fp"
            java.lang.String r9 = com.uc.webview.export.extension.UCCore.getParam(r9)     // Catch:{ UCKnownException -> 0x0161, all -> 0x0158 }
            boolean r16 = com.uc.webview.export.internal.utility.j.a(r9)     // Catch:{ UCKnownException -> 0x0161, all -> 0x0158 }
            if (r16 != 0) goto L_0x016b
            boolean r16 = com.uc.webview.export.internal.utility.j.a(r8)     // Catch:{ UCKnownException -> 0x0161, all -> 0x0158 }
            if (r16 == 0) goto L_0x013f
            goto L_0x016b
        L_0x013f:
            r20 = r4
            java.io.File r4 = new java.io.File     // Catch:{ UCKnownException -> 0x0175, all -> 0x0173 }
            r4.<init>(r8)     // Catch:{ UCKnownException -> 0x0175, all -> 0x0173 }
            java.io.File r8 = new java.io.File     // Catch:{ UCKnownException -> 0x0175, all -> 0x0173 }
            r8.<init>(r9)     // Catch:{ UCKnownException -> 0x0175, all -> 0x0173 }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ UCKnownException -> 0x0175, all -> 0x0173 }
            java.lang.String r8 = r8.getAbsolutePath()     // Catch:{ UCKnownException -> 0x0175, all -> 0x0173 }
            boolean r9 = r4.startsWith(r8)     // Catch:{ UCKnownException -> 0x0175, all -> 0x0173 }
            goto L_0x016e
        L_0x0158:
            r0 = move-exception
            r20 = r4
        L_0x015b:
            r3 = r0
            r9 = r13
            r8 = r20
            goto L_0x03ef
        L_0x0161:
            r0 = move-exception
            r20 = r4
            r3 = r0
            r22 = r7
            r10 = r13
            r7 = r15
            goto L_0x03fd
        L_0x016b:
            r20 = r4
            r9 = 0
        L_0x016e:
            if (r9 == 0) goto L_0x017f
            int r4 = com.uc.webview.export.cyclone.UCCyclone.DecFileOrign.Sdcard_Share_Core     // Catch:{ UCKnownException -> 0x0175, all -> 0x0173 }
            goto L_0x0181
        L_0x0173:
            r0 = move-exception
            goto L_0x015b
        L_0x0175:
            r0 = move-exception
            r3 = r0
            r22 = r7
            r10 = r13
            r7 = r15
        L_0x017b:
            r4 = r20
            goto L_0x03fd
        L_0x017f:
            int r4 = com.uc.webview.export.cyclone.UCCyclone.DecFileOrign.Other     // Catch:{ UCKnownException -> 0x03d4, all -> 0x03d2 }
        L_0x0181:
            java.lang.String r8 = "DecompressSetupTask"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ UCKnownException -> 0x03d4, all -> 0x03d2 }
            r21 = r6
            java.lang.String r6 = "forceUsing7z : "
            r9.<init>(r6)     // Catch:{ UCKnownException -> 0x03c5, all -> 0x03b9 }
            r9.append(r11)     // Catch:{ UCKnownException -> 0x03c5, all -> 0x03b9 }
            java.lang.String r6 = "deleteAfterExtract : "
            r9.append(r6)     // Catch:{ UCKnownException -> 0x03c5, all -> 0x03b9 }
            r9.append(r10)     // Catch:{ UCKnownException -> 0x03c5, all -> 0x03b9 }
            java.lang.String r6 = "orign : "
            r9.append(r6)     // Catch:{ UCKnownException -> 0x03c5, all -> 0x03b9 }
            r9.append(r4)     // Catch:{ UCKnownException -> 0x03c5, all -> 0x03b9 }
            java.lang.String r6 = r9.toString()     // Catch:{ UCKnownException -> 0x03c5, all -> 0x03b9 }
            com.uc.webview.export.internal.utility.Log.d(r8, r6)     // Catch:{ UCKnownException -> 0x03c5, all -> 0x03b9 }
            r6 = 20
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r6)     // Catch:{ UCKnownException -> 0x03c5, all -> 0x03b9 }
            java.lang.String r6 = "bo_dec_cl"
            java.lang.Object r6 = r1.getOption(r6)     // Catch:{ Exception -> 0x020a, UCKnownException -> 0x0202, all -> 0x01f7 }
            com.uc.webview.export.extension.UCCore$Callable r6 = (com.uc.webview.export.extension.UCCore.Callable) r6     // Catch:{ Exception -> 0x020a, UCKnownException -> 0x0202, all -> 0x01f7 }
            if (r6 == 0) goto L_0x01f4
            android.os.Bundle r8 = new android.os.Bundle     // Catch:{ Exception -> 0x020a, UCKnownException -> 0x0202, all -> 0x01f7 }
            r8.<init>()     // Catch:{ Exception -> 0x020a, UCKnownException -> 0x0202, all -> 0x01f7 }
            java.lang.String r9 = "decDirPath"
            r22 = r7
            java.lang.String r7 = r3.getAbsolutePath()     // Catch:{ Exception -> 0x01f2, UCKnownException -> 0x01f0, all -> 0x01eb }
            r8.putString(r9, r7)     // Catch:{ Exception -> 0x01f2, UCKnownException -> 0x01f0, all -> 0x01eb }
            java.lang.String r7 = "zipFilePath"
            java.lang.String r9 = r12.getAbsolutePath()     // Catch:{ Exception -> 0x01f2, UCKnownException -> 0x01f0, all -> 0x01eb }
            r8.putString(r7, r9)     // Catch:{ Exception -> 0x01f2, UCKnownException -> 0x01f0, all -> 0x01eb }
            java.lang.String r7 = "zipFileType"
            if (r11 == 0) goto L_0x01d5
            java.lang.String r9 = "7z"
            goto L_0x01d7
        L_0x01d5:
            java.lang.String r9 = "zip"
        L_0x01d7:
            r8.putString(r7, r9)     // Catch:{ Exception -> 0x01f2, UCKnownException -> 0x01f0, all -> 0x01eb }
            java.lang.String r7 = "deleteAfterExtract"
            r8.putBoolean(r7, r10)     // Catch:{ Exception -> 0x01f2, UCKnownException -> 0x01f0, all -> 0x01eb }
            java.lang.Object r6 = r6.call(r8)     // Catch:{ Exception -> 0x01f2, UCKnownException -> 0x01f0, all -> 0x01eb }
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ Exception -> 0x01f2, UCKnownException -> 0x01f0, all -> 0x01eb }
            boolean r6 = r6.booleanValue()     // Catch:{ Exception -> 0x01f2, UCKnownException -> 0x01f0, all -> 0x01eb }
            r9 = 1
            goto L_0x0217
        L_0x01eb:
            r0 = move-exception
            r3 = r0
            r9 = r13
            goto L_0x03c0
        L_0x01f0:
            r0 = move-exception
            goto L_0x0205
        L_0x01f2:
            r0 = move-exception
            goto L_0x020d
        L_0x01f4:
            r22 = r7
            goto L_0x0215
        L_0x01f7:
            r0 = move-exception
            r22 = r7
            r3 = r0
            r9 = r13
            r8 = r20
            r6 = r21
            goto L_0x03ef
        L_0x0202:
            r0 = move-exception
            r22 = r7
        L_0x0205:
            r3 = r0
            r10 = r13
            r7 = r15
            goto L_0x03cd
        L_0x020a:
            r0 = move-exception
            r22 = r7
        L_0x020d:
            r6 = r0
            java.lang.String r7 = "DecompressSetupTask"
            java.lang.String r8 = "decCallback"
            com.uc.webview.export.internal.utility.Log.d(r7, r8, r6)     // Catch:{ UCKnownException -> 0x03b7, all -> 0x03b5 }
        L_0x0215:
            r6 = 0
            r9 = 0
        L_0x0217:
            if (r9 != 0) goto L_0x0243
            r7 = 0
            r8 = r10
            r10 = r5
            r9 = r13
            r13 = r3
            r23 = r6
            r6 = r14
            r14 = r7
            r7 = r15
            r15 = r8
            r16 = r4
            boolean r4 = com.uc.webview.export.cyclone.UCCyclone.decompressIfNeeded(r10, r11, r12, r13, r14, r15, r16)     // Catch:{ UCKnownException -> 0x0238, all -> 0x022b }
            goto L_0x024a
        L_0x022b:
            r0 = move-exception
            r3 = r0
            r15 = r7
            r8 = r20
            r6 = r21
            r7 = r22
            r4 = r23
            goto L_0x0437
        L_0x0238:
            r0 = move-exception
            r3 = r0
            r10 = r9
            r4 = r20
            r6 = r21
            r9 = r23
            goto L_0x041c
        L_0x0243:
            r23 = r6
            r9 = r13
            r6 = r14
            r7 = r15
            r4 = r23
        L_0x024a:
            r8 = 21
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r8)     // Catch:{ UCKnownException -> 0x03ab, all -> 0x03a2 }
            r8 = 19
            if (r4 == 0) goto L_0x0256
            java.lang.String r10 = "1"
            goto L_0x0258
        L_0x0256:
            java.lang.String r10 = "0"
        L_0x0258:
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r8, r10)     // Catch:{ UCKnownException -> 0x03ab, all -> 0x03a2 }
            if (r4 == 0) goto L_0x029c
            java.lang.String r8 = "0"
            long r10 = r6.getMilisCpu()     // Catch:{ UCKnownException -> 0x0293, all -> 0x028e }
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ UCKnownException -> 0x0293, all -> 0x028e }
            long r11 = r6.getMilis()     // Catch:{ UCKnownException -> 0x0286, all -> 0x027e }
            java.lang.String r6 = java.lang.String.valueOf(r11)     // Catch:{ UCKnownException -> 0x0286, all -> 0x027e }
            com.uc.webview.export.internal.setup.ay.a(r5)     // Catch:{ UCKnownException -> 0x0279, all -> 0x0273 }
            goto L_0x02a2
        L_0x0273:
            r0 = move-exception
            r3 = r0
            r15 = r7
            r7 = r10
            goto L_0x0437
        L_0x0279:
            r0 = move-exception
            r3 = r0
            r22 = r10
            goto L_0x028c
        L_0x027e:
            r0 = move-exception
            r3 = r0
            r15 = r7
            r7 = r10
            r6 = r21
            goto L_0x0437
        L_0x0286:
            r0 = move-exception
            r3 = r0
            r22 = r10
            r6 = r21
        L_0x028c:
            r10 = r9
            goto L_0x0298
        L_0x028e:
            r0 = move-exception
            r3 = r0
            r15 = r7
            goto L_0x03a7
        L_0x0293:
            r0 = move-exception
            r3 = r0
            r10 = r9
            r6 = r21
        L_0x0298:
            r9 = r4
            r4 = r8
            goto L_0x041c
        L_0x029c:
            java.lang.String r8 = "1"
            r6 = r21
            r10 = r22
        L_0x02a2:
            java.lang.String r5 = "-1"
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ Throwable -> 0x0322 }
            android.util.Pair r11 = new android.util.Pair     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r12 = "sdk_7z"
            com.uc.webview.export.cyclone.UCHashMap r13 = new com.uc.webview.export.cyclone.UCHashMap     // Catch:{ Throwable -> 0x0322 }
            r13.<init>()     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r14 = "cnt"
            java.lang.String r15 = "1"
            com.uc.webview.export.cyclone.UCHashMap r13 = r13.set(r14, r15)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r14 = "code"
            com.uc.webview.export.cyclone.UCHashMap r13 = r13.set(r14, r8)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r14 = "cost"
            com.uc.webview.export.cyclone.UCHashMap r13 = r13.set(r14, r6)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r14 = "cost_cpu"
            com.uc.webview.export.cyclone.UCHashMap r13 = r13.set(r14, r10)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r14 = "data"
            com.uc.webview.export.cyclone.UCHashMap r7 = r13.set(r14, r7)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r13 = "cpu_cnt"
            java.lang.String r14 = com.uc.webview.export.internal.utility.j.a()     // Catch:{ Throwable -> 0x0322 }
            com.uc.webview.export.cyclone.UCHashMap r7 = r7.set(r13, r14)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r13 = "cpu_freq"
            java.lang.String r14 = com.uc.webview.export.internal.utility.j.b()     // Catch:{ Throwable -> 0x0322 }
            com.uc.webview.export.cyclone.UCHashMap r7 = r7.set(r13, r14)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r13 = "link_so_code"
            com.uc.webview.export.cyclone.UCHashMap r5 = r7.set(r13, r5)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r7 = "free_disk_space"
            com.uc.webview.export.cyclone.UCHashMap r5 = r5.set(r7, r9)     // Catch:{ Throwable -> 0x0322 }
            r11.<init>(r12, r5)     // Catch:{ Throwable -> 0x0322 }
            r1.callbackStat(r11)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r5 = "DecompressSetupTask"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r9 = "need: "
            r7.<init>(r9)     // Catch:{ Throwable -> 0x0322 }
            r7.append(r4)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r9 = " code : "
            r7.append(r9)     // Catch:{ Throwable -> 0x0322 }
            r7.append(r8)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r8 = " CostsMilis"
            r7.append(r8)     // Catch:{ Throwable -> 0x0322 }
            r7.append(r6)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r6 = " costsCpuMilis: "
            r7.append(r6)     // Catch:{ Throwable -> 0x0322 }
            r7.append(r10)     // Catch:{ Throwable -> 0x0322 }
            java.lang.String r6 = r7.toString()     // Catch:{ Throwable -> 0x0322 }
            com.uc.webview.export.internal.utility.Log.d(r5, r6)     // Catch:{ Throwable -> 0x0322 }
        L_0x0322:
            monitor-exit(r2)     // Catch:{ all -> 0x04b8 }
            r2 = 0
            if (r4 != 0) goto L_0x033a
            com.uc.webview.export.internal.setup.UCMRunningInfo r4 = com.uc.webview.export.utility.SetupTask.getTotalLoadedUCM()
            if (r4 == 0) goto L_0x033a
            java.lang.String r4 = "bo_continue_odex"
            java.lang.Object r4 = r1.getOption(r4)
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r4 = com.uc.webview.export.internal.utility.j.a(r4)
            if (r4 == 0) goto L_0x039f
        L_0x033a:
            com.uc.webview.export.internal.setup.bw r4 = new com.uc.webview.export.internal.setup.bw
            r4.<init>()
            r5 = 10001(0x2711, float:1.4014E-41)
            r6 = 1
            java.lang.Object[] r7 = new java.lang.Object[r6]
            r8 = 0
            r7[r8] = r1
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r4.invoke(r5, r7)
            com.uc.webview.export.internal.setup.t r4 = (com.uc.webview.export.internal.setup.t) r4
            java.util.concurrent.ConcurrentHashMap r5 = r1.mOptions
            com.uc.webview.export.internal.setup.BaseSetupTask r4 = r4.setOptions(r5)
            com.uc.webview.export.internal.setup.t r4 = (com.uc.webview.export.internal.setup.t) r4
            r5 = 10002(0x2712, float:1.4016E-41)
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.util.concurrent.ConcurrentHashMap r7 = r1.mCallbacks
            r6[r8] = r7
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r4.invoke(r5, r6)
            com.uc.webview.export.internal.setup.t r4 = (com.uc.webview.export.internal.setup.t) r4
            java.lang.String r5 = "dexFilePath"
            com.uc.webview.export.internal.setup.BaseSetupTask r4 = r4.setup(r5, r2)
            com.uc.webview.export.internal.setup.t r4 = (com.uc.webview.export.internal.setup.t) r4
            java.lang.String r5 = "soFilePath"
            com.uc.webview.export.internal.setup.BaseSetupTask r4 = r4.setup(r5, r2)
            com.uc.webview.export.internal.setup.t r4 = (com.uc.webview.export.internal.setup.t) r4
            java.lang.String r5 = "resFilePath"
            com.uc.webview.export.internal.setup.BaseSetupTask r4 = r4.setup(r5, r2)
            com.uc.webview.export.internal.setup.t r4 = (com.uc.webview.export.internal.setup.t) r4
            java.lang.String r5 = "ucmCfgFile"
            com.uc.webview.export.internal.setup.BaseSetupTask r4 = r4.setup(r5, r2)
            com.uc.webview.export.internal.setup.t r4 = (com.uc.webview.export.internal.setup.t) r4
            java.lang.String r5 = "ucmKrlDir"
            java.lang.String r3 = r3.getAbsolutePath()
            com.uc.webview.export.internal.setup.BaseSetupTask r3 = r4.setup(r5, r3)
            com.uc.webview.export.internal.setup.t r3 = (com.uc.webview.export.internal.setup.t) r3
            java.lang.String r4 = "stop"
            com.uc.webview.export.internal.setup.UCAsyncTask$c r5 = new com.uc.webview.export.internal.setup.UCAsyncTask$c
            r5.<init>()
            com.uc.webview.export.internal.setup.BaseSetupTask r3 = r3.onEvent(r4, r5)
            com.uc.webview.export.internal.setup.t r3 = (com.uc.webview.export.internal.setup.t) r3
            r3.start()
        L_0x039f:
            r1.mCallbacks = r2
            return
        L_0x03a2:
            r0 = move-exception
            r3 = r0
            r15 = r7
            r8 = r20
        L_0x03a7:
            r6 = r21
            goto L_0x0435
        L_0x03ab:
            r0 = move-exception
            r3 = r0
            r10 = r9
            r6 = r21
            r9 = r4
            r4 = r20
            goto L_0x041c
        L_0x03b5:
            r0 = move-exception
            goto L_0x03bc
        L_0x03b7:
            r0 = move-exception
            goto L_0x03c8
        L_0x03b9:
            r0 = move-exception
            r22 = r7
        L_0x03bc:
            r9 = r13
            r7 = r15
            r8 = 0
            r3 = r0
        L_0x03c0:
            r8 = r20
            r6 = r21
            goto L_0x03ed
        L_0x03c5:
            r0 = move-exception
            r22 = r7
        L_0x03c8:
            r9 = r13
            r7 = r15
            r8 = 0
            r3 = r0
            r10 = r9
        L_0x03cd:
            r4 = r20
            r6 = r21
            goto L_0x03fd
        L_0x03d2:
            r0 = move-exception
            goto L_0x03e3
        L_0x03d4:
            r0 = move-exception
            r21 = r6
            r22 = r7
            r9 = r13
            r7 = r15
            r8 = 0
            r3 = r0
            r10 = r9
            goto L_0x017b
        L_0x03e0:
            r0 = move-exception
            r20 = r4
        L_0x03e3:
            r21 = r6
            r22 = r7
            r9 = r13
            r7 = r15
            r8 = 0
            r3 = r0
            r8 = r20
        L_0x03ed:
            r7 = r22
        L_0x03ef:
            r4 = 0
            goto L_0x0437
        L_0x03f1:
            r0 = move-exception
            r20 = r4
            r21 = r6
            r22 = r7
            r9 = r13
            r7 = r15
            r8 = 0
            r3 = r0
            r10 = r9
        L_0x03fd:
            r9 = 0
            goto L_0x041c
        L_0x03ff:
            r0 = move-exception
            r20 = r4
            r21 = r6
            r22 = r7
            r7 = r15
            r8 = 0
            r3 = r0
            r8 = r20
            r7 = r22
            r4 = 0
            r9 = 0
            goto L_0x0437
        L_0x0410:
            r0 = move-exception
            r20 = r4
            r21 = r6
            r22 = r7
            r7 = r15
            r8 = 0
            r3 = r0
            r9 = 0
            r10 = 0
        L_0x041c:
            java.lang.String r5 = "2"
            int r4 = r3.errCode()     // Catch:{ all -> 0x042b }
            java.lang.String r15 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x042b }
            throw r3     // Catch:{ all -> 0x0427 }
        L_0x0427:
            r0 = move-exception
            r3 = r0
            r8 = r5
            goto L_0x0433
        L_0x042b:
            r0 = move-exception
            r3 = r0
            r8 = r5
            goto L_0x0432
        L_0x042f:
            r0 = move-exception
            r3 = r0
            r8 = r4
        L_0x0432:
            r15 = r7
        L_0x0433:
            r4 = r9
            r9 = r10
        L_0x0435:
            r7 = r22
        L_0x0437:
            java.lang.String r5 = "-1"
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ Throwable -> 0x04b7 }
            android.util.Pair r10 = new android.util.Pair     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r11 = "sdk_7z"
            com.uc.webview.export.cyclone.UCHashMap r12 = new com.uc.webview.export.cyclone.UCHashMap     // Catch:{ Throwable -> 0x04b7 }
            r12.<init>()     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r13 = "cnt"
            java.lang.String r14 = "1"
            com.uc.webview.export.cyclone.UCHashMap r12 = r12.set(r13, r14)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r13 = "code"
            com.uc.webview.export.cyclone.UCHashMap r12 = r12.set(r13, r8)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r13 = "cost"
            com.uc.webview.export.cyclone.UCHashMap r12 = r12.set(r13, r6)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r13 = "cost_cpu"
            com.uc.webview.export.cyclone.UCHashMap r12 = r12.set(r13, r7)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r13 = "data"
            com.uc.webview.export.cyclone.UCHashMap r12 = r12.set(r13, r15)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r13 = "cpu_cnt"
            java.lang.String r14 = com.uc.webview.export.internal.utility.j.a()     // Catch:{ Throwable -> 0x04b7 }
            com.uc.webview.export.cyclone.UCHashMap r12 = r12.set(r13, r14)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r13 = "cpu_freq"
            java.lang.String r14 = com.uc.webview.export.internal.utility.j.b()     // Catch:{ Throwable -> 0x04b7 }
            com.uc.webview.export.cyclone.UCHashMap r12 = r12.set(r13, r14)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r13 = "link_so_code"
            com.uc.webview.export.cyclone.UCHashMap r5 = r12.set(r13, r5)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r12 = "free_disk_space"
            com.uc.webview.export.cyclone.UCHashMap r5 = r5.set(r12, r9)     // Catch:{ Throwable -> 0x04b7 }
            r10.<init>(r11, r5)     // Catch:{ Throwable -> 0x04b7 }
            r1.callbackStat(r10)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r5 = "DecompressSetupTask"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r10 = "need: "
            r9.<init>(r10)     // Catch:{ Throwable -> 0x04b7 }
            r9.append(r4)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r4 = " code : "
            r9.append(r4)     // Catch:{ Throwable -> 0x04b7 }
            r9.append(r8)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r4 = " CostsMilis"
            r9.append(r4)     // Catch:{ Throwable -> 0x04b7 }
            r9.append(r6)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r4 = " costsCpuMilis: "
            r9.append(r4)     // Catch:{ Throwable -> 0x04b7 }
            r9.append(r7)     // Catch:{ Throwable -> 0x04b7 }
            java.lang.String r4 = r9.toString()     // Catch:{ Throwable -> 0x04b7 }
            com.uc.webview.export.internal.utility.Log.d(r5, r4)     // Catch:{ Throwable -> 0x04b7 }
        L_0x04b7:
            throw r3     // Catch:{ all -> 0x04b8 }
        L_0x04b8:
            r0 = move-exception
            r3 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x04b8 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.n.run():void");
    }
}
