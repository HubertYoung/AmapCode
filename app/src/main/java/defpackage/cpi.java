package defpackage;

/* renamed from: cpi reason: default package */
/* compiled from: DbLiteTransfer */
public final class cpi {

    /* renamed from: cpi$a */
    /* compiled from: DbLiteTransfer */
    public static class a {
        public int a;
        public String b;
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x00ac A[SYNTHETIC, Splitter:B:45:0x00ac] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00b6 A[SYNTHETIC, Splitter:B:54:0x00b6] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<defpackage.le> a(java.io.File r15) {
        /*
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x00b3, all -> 0x00a8 }
            r2.<init>(r15)     // Catch:{ IOException -> 0x00b3, all -> 0x00a8 }
            r15 = 256(0x100, float:3.59E-43)
            byte[] r15 = new byte[r15]     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
        L_0x000f:
            int r3 = r2.read(r15)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            if (r3 < 0) goto L_0x001a
            r4 = 0
            r0.write(r15, r4, r3)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            goto L_0x000f
        L_0x001a:
            byte[] r15 = r0.toByteArray()     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            int r3 = r15.length     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            if (r3 != 0) goto L_0x0028
            r2.close()     // Catch:{ IOException -> 0x0027 }
            r0.close()     // Catch:{ IOException -> 0x0027 }
        L_0x0027:
            return r1
        L_0x0028:
            r4 = 4
            int r4 = defpackage.ahg.a(r15, r4)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            r5.<init>()     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            cpi$a r6 = new cpi$a     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            r6.<init>()     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            if (r4 <= 0) goto L_0x009f
            r4 = 8
        L_0x003b:
            if (r4 >= r3) goto L_0x009f
            int r7 = defpackage.ahg.a(r15, r4)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            int r4 = r4 + 4
            int r7 = r7 + r4
            le r8 = new le     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            r8.<init>()     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            int r9 = defpackage.ahg.a(r15, r4)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            r8.a = r9     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            int r4 = r4 + 4
        L_0x0051:
            if (r4 >= r7) goto L_0x009b
            int r9 = r4 + 1
            byte r4 = r15[r4]     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            a(r15, r9, r6)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            int r10 = r6.a     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            int r10 = r10 + 2
            int r9 = r9 + r10
            java.lang.String r10 = r6.b     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            switch(r4) {
                case 0: goto L_0x0090;
                case 1: goto L_0x0082;
                case 2: goto L_0x0077;
                case 3: goto L_0x0065;
                default: goto L_0x0064;
            }
        L_0x0064:
            goto L_0x0099
        L_0x0065:
            r11 = 0
            double r13 = defpackage.ahg.c(r15, r9)     // Catch:{ Exception -> 0x006c }
            goto L_0x0071
        L_0x006c:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            r13 = r11
        L_0x0071:
            int r9 = r9 + 8
            r8.a(r10, r13)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            goto L_0x0099
        L_0x0077:
            int r4 = defpackage.ahg.a(r15, r9)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            int r9 = r9 + 4
            r8.a(r10, r15, r9, r4)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            int r4 = r4 + r9
            goto L_0x0051
        L_0x0082:
            a(r15, r9, r6)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            int r4 = r6.a     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            int r4 = r4 + 2
            int r4 = r4 + r9
            java.lang.String r9 = r6.b     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            r8.a(r10, r9)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            goto L_0x0051
        L_0x0090:
            int r4 = defpackage.ahg.a(r15, r9)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            int r9 = r9 + 4
            r8.a(r10, r4)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
        L_0x0099:
            r4 = r9
            goto L_0x0051
        L_0x009b:
            r5.add(r8)     // Catch:{ IOException -> 0x00b4, all -> 0x00a6 }
            goto L_0x003b
        L_0x009f:
            r2.close()     // Catch:{ IOException -> 0x00a5 }
            r0.close()     // Catch:{ IOException -> 0x00a5 }
        L_0x00a5:
            return r5
        L_0x00a6:
            r15 = move-exception
            goto L_0x00aa
        L_0x00a8:
            r15 = move-exception
            r2 = r1
        L_0x00aa:
            if (r2 == 0) goto L_0x00af
            r2.close()     // Catch:{ IOException -> 0x00b2 }
        L_0x00af:
            r0.close()     // Catch:{ IOException -> 0x00b2 }
        L_0x00b2:
            throw r15
        L_0x00b3:
            r2 = r1
        L_0x00b4:
            if (r2 == 0) goto L_0x00b9
            r2.close()     // Catch:{ IOException -> 0x00bc }
        L_0x00b9:
            r0.close()     // Catch:{ IOException -> 0x00bc }
        L_0x00bc:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cpi.a(java.io.File):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c3 A[SYNTHETIC, Splitter:B:54:0x00c3] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00d4 A[SYNTHETIC, Splitter:B:63:0x00d4] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<defpackage.le> b(java.io.File r15) {
        /*
            boolean r0 = r15.exists()
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            r2.<init>(r15)     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            r15 = 256(0x100, float:3.59E-43)
            byte[] r15 = new byte[r15]     // Catch:{ Exception -> 0x00b7 }
        L_0x0016:
            int r3 = r2.read(r15)     // Catch:{ Exception -> 0x00b7 }
            if (r3 < 0) goto L_0x0021
            r4 = 0
            r0.write(r15, r4, r3)     // Catch:{ Exception -> 0x00b7 }
            goto L_0x0016
        L_0x0021:
            byte[] r15 = r0.toByteArray()     // Catch:{ Exception -> 0x00b7 }
            int r3 = r15.length     // Catch:{ Exception -> 0x00b7 }
            if (r3 != 0) goto L_0x0034
            r2.close()     // Catch:{ IOException -> 0x002f }
            r0.close()     // Catch:{ IOException -> 0x002f }
            goto L_0x0033
        L_0x002f:
            r15 = move-exception
            r15.printStackTrace()
        L_0x0033:
            return r1
        L_0x0034:
            r4 = 4
            int r4 = defpackage.ahg.a(r15, r4)     // Catch:{ Exception -> 0x00b7 }
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Exception -> 0x00b7 }
            r5.<init>()     // Catch:{ Exception -> 0x00b7 }
            cpi$a r6 = new cpi$a     // Catch:{ Exception -> 0x00b7 }
            r6.<init>()     // Catch:{ Exception -> 0x00b7 }
            if (r4 <= 0) goto L_0x00ab
            r4 = 8
        L_0x0047:
            if (r4 >= r3) goto L_0x00ab
            int r7 = defpackage.ahg.a(r15, r4)     // Catch:{ Exception -> 0x00b7 }
            int r4 = r4 + 4
            int r7 = r7 + r4
            le r8 = new le     // Catch:{ Exception -> 0x00b7 }
            r8.<init>()     // Catch:{ Exception -> 0x00b7 }
            int r9 = defpackage.ahg.a(r15, r4)     // Catch:{ Exception -> 0x00b7 }
            r8.a = r9     // Catch:{ Exception -> 0x00b7 }
            int r4 = r4 + 4
        L_0x005d:
            if (r4 >= r7) goto L_0x00a7
            int r9 = r4 + 1
            byte r4 = r15[r4]     // Catch:{ Exception -> 0x00b7 }
            a(r15, r9, r6)     // Catch:{ Exception -> 0x00b7 }
            int r10 = r6.a     // Catch:{ Exception -> 0x00b7 }
            int r10 = r10 + 2
            int r9 = r9 + r10
            java.lang.String r10 = r6.b     // Catch:{ Exception -> 0x00b7 }
            switch(r4) {
                case 0: goto L_0x009c;
                case 1: goto L_0x008e;
                case 2: goto L_0x0083;
                case 3: goto L_0x0071;
                default: goto L_0x0070;
            }
        L_0x0070:
            goto L_0x00a5
        L_0x0071:
            r11 = 0
            double r13 = defpackage.ahg.c(r15, r9)     // Catch:{ Exception -> 0x0078 }
            goto L_0x007d
        L_0x0078:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ Exception -> 0x00b7 }
            r13 = r11
        L_0x007d:
            int r9 = r9 + 8
            r8.a(r10, r13)     // Catch:{ Exception -> 0x00b7 }
            goto L_0x00a5
        L_0x0083:
            int r4 = defpackage.ahg.a(r15, r9)     // Catch:{ Exception -> 0x00b7 }
            int r9 = r9 + 4
            r8.a(r10, r15, r9, r4)     // Catch:{ Exception -> 0x00b7 }
            int r4 = r4 + r9
            goto L_0x005d
        L_0x008e:
            a(r15, r9, r6)     // Catch:{ Exception -> 0x00b7 }
            int r4 = r6.a     // Catch:{ Exception -> 0x00b7 }
            int r4 = r4 + 2
            int r4 = r4 + r9
            java.lang.String r9 = r6.b     // Catch:{ Exception -> 0x00b7 }
            r8.a(r10, r9)     // Catch:{ Exception -> 0x00b7 }
            goto L_0x005d
        L_0x009c:
            int r4 = defpackage.ahg.a(r15, r9)     // Catch:{ Exception -> 0x00b7 }
            int r9 = r9 + 4
            r8.a(r10, r4)     // Catch:{ Exception -> 0x00b7 }
        L_0x00a5:
            r4 = r9
            goto L_0x005d
        L_0x00a7:
            r5.add(r8)     // Catch:{ Exception -> 0x00b7 }
            goto L_0x0047
        L_0x00ab:
            r2.close()     // Catch:{ IOException -> 0x00b2 }
            r0.close()     // Catch:{ IOException -> 0x00b2 }
            goto L_0x00b6
        L_0x00b2:
            r15 = move-exception
            r15.printStackTrace()
        L_0x00b6:
            return r5
        L_0x00b7:
            r15 = move-exception
            goto L_0x00be
        L_0x00b9:
            r15 = move-exception
            r2 = r1
            goto L_0x00d2
        L_0x00bc:
            r15 = move-exception
            r2 = r1
        L_0x00be:
            r15.printStackTrace()     // Catch:{ all -> 0x00d1 }
            if (r2 == 0) goto L_0x00c9
            r2.close()     // Catch:{ IOException -> 0x00c7 }
            goto L_0x00c9
        L_0x00c7:
            r15 = move-exception
            goto L_0x00cd
        L_0x00c9:
            r0.close()     // Catch:{ IOException -> 0x00c7 }
            goto L_0x00d0
        L_0x00cd:
            r15.printStackTrace()
        L_0x00d0:
            return r1
        L_0x00d1:
            r15 = move-exception
        L_0x00d2:
            if (r2 == 0) goto L_0x00da
            r2.close()     // Catch:{ IOException -> 0x00d8 }
            goto L_0x00da
        L_0x00d8:
            r0 = move-exception
            goto L_0x00de
        L_0x00da:
            r0.close()     // Catch:{ IOException -> 0x00d8 }
            goto L_0x00e1
        L_0x00de:
            r0.printStackTrace()
        L_0x00e1:
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cpi.b(java.io.File):java.util.List");
    }

    private static void a(byte[] bArr, int i, a aVar) {
        try {
            aVar.a = ahg.b(bArr, i);
            aVar.b = new String(bArr, i + 2, aVar.a, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            aVar.a = 0;
            aVar.b = "";
        }
    }
}
