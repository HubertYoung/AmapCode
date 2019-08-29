package com.uc.webview.export.internal.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.setup.UCSetupException;
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/* compiled from: ProGuard */
public final class i {

    /* compiled from: ProGuard */
    static class a {
        public static Signature[] a(Context context, String str) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 64);
                if (packageInfo == null) {
                    return null;
                }
                return packageInfo.signatures;
            } catch (Exception e) {
                Log.e("SignatureVerifier", e.getMessage());
                return null;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:33:0x013f A[SYNTHETIC, Splitter:B:33:0x013f] */
        /* JADX WARNING: Removed duplicated region for block: B:37:0x0154 A[SYNTHETIC, Splitter:B:37:0x0154] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static android.content.pm.Signature[] a(android.content.Context r13, java.lang.String r14, android.webkit.ValueCallback<java.lang.Object[]> r15) {
            /*
                java.lang.String r0 = "SignatureVerifier"
                java.lang.String r1 = "getUninstalledAPKSignature(): archiveApkFilePath = %1s"
                r2 = 1
                java.lang.Object[] r3 = new java.lang.Object[r2]
                r4 = 0
                r3[r4] = r14
                java.lang.String r1 = java.lang.String.format(r1, r3)
                com.uc.webview.export.internal.utility.Log.d(r0, r1)
                r0 = 0
                long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x018c }
                java.lang.String r1 = "sc_vrfv1"
                java.lang.String r1 = com.uc.webview.export.extension.UCCore.getParam(r1)     // Catch:{ Throwable -> 0x018c }
                boolean r1 = java.lang.Boolean.parseBoolean(r1)     // Catch:{ Throwable -> 0x018c }
                java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)     // Catch:{ Throwable -> 0x018c }
                if (r1 != 0) goto L_0x0028
                r1 = 0
                goto L_0x002c
            L_0x0028:
                boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x018c }
            L_0x002c:
                r3 = 2
                if (r1 != 0) goto L_0x006d
                boolean r7 = com.uc.webview.export.cyclone.UCCyclone.detect7zFromFileName(r14)     // Catch:{ Throwable -> 0x018c }
                if (r7 == 0) goto L_0x0037
                r7 = 0
                goto L_0x0043
            L_0x0037:
                boolean r7 = com.uc.webview.export.internal.utility.a.a(r14)     // Catch:{ Throwable -> 0x018c }
                if (r7 == 0) goto L_0x003f
                r7 = 1
                goto L_0x0043
            L_0x003f:
                boolean r7 = com.uc.webview.export.internal.utility.n.a(r14)     // Catch:{ Throwable -> 0x018c }
            L_0x0043:
                if (r7 == 0) goto L_0x006d
                java.lang.String r13 = "SignatureVerifier"
                java.lang.String r1 = "摘要校验V2!"
                com.uc.webview.export.internal.utility.Log.d(r13, r1)     // Catch:{ Throwable -> 0x018c }
                java.security.cert.X509Certificate[][] r13 = com.uc.webview.export.internal.utility.a.b(r14)     // Catch:{ Throwable -> 0x018c }
                int r14 = r13.length     // Catch:{ Throwable -> 0x018c }
                android.content.pm.Signature[] r14 = new android.content.pm.Signature[r14]     // Catch:{ Throwable -> 0x018c }
                r1 = 0
            L_0x0055:
                int r7 = r13.length     // Catch:{ Throwable -> 0x018c }
                if (r1 >= r7) goto L_0x006a
                android.content.pm.Signature r7 = new android.content.pm.Signature     // Catch:{ Throwable -> 0x018c }
                r8 = r13[r1]     // Catch:{ Throwable -> 0x018c }
                r8 = r8[r4]     // Catch:{ Throwable -> 0x018c }
                byte[] r8 = r8.getEncoded()     // Catch:{ Throwable -> 0x018c }
                r7.<init>(r8)     // Catch:{ Throwable -> 0x018c }
                r14[r1] = r7     // Catch:{ Throwable -> 0x018c }
                int r1 = r1 + 1
                goto L_0x0055
            L_0x006a:
                r13 = 2
                goto L_0x013d
            L_0x006d:
                java.lang.String r7 = "SignatureVerifier"
                java.lang.String r8 = "摘要校验V1! 强制V1:"
                java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x018c }
                java.lang.String r1 = r8.concat(r1)     // Catch:{ Throwable -> 0x018c }
                com.uc.webview.export.internal.utility.Log.d(r7, r1)     // Catch:{ Throwable -> 0x018c }
                java.lang.String r1 = "android.content.pm.PackageParser"
                java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ Throwable -> 0x018c }
                int r7 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x018c }
                r8 = 21
                if (r7 < r8) goto L_0x0096
                java.lang.Class[] r7 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x018c }
                java.lang.reflect.Constructor r7 = r1.getConstructor(r7)     // Catch:{ Throwable -> 0x018c }
                java.lang.Object[] r9 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x018c }
            L_0x0091:
                java.lang.Object r7 = r7.newInstance(r9)     // Catch:{ Throwable -> 0x018c }
                goto L_0x00a7
            L_0x0096:
                java.lang.Class[] r7 = new java.lang.Class[r2]     // Catch:{ Throwable -> 0x018c }
                java.lang.Class<java.lang.String> r9 = java.lang.String.class
                r7[r4] = r9     // Catch:{ Throwable -> 0x018c }
                java.lang.reflect.Constructor r7 = r1.getConstructor(r7)     // Catch:{ Throwable -> 0x018c }
                java.lang.Object[] r9 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x018c }
                java.lang.String r10 = ""
                r9[r4] = r10     // Catch:{ Throwable -> 0x018c }
                goto L_0x0091
            L_0x00a7:
                int r9 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x018c }
                if (r9 < r8) goto L_0x00cf
                java.lang.String r13 = "parsePackage"
                java.lang.Class[] r8 = new java.lang.Class[r3]     // Catch:{ Throwable -> 0x018c }
                java.lang.Class<java.io.File> r9 = java.io.File.class
                r8[r4] = r9     // Catch:{ Throwable -> 0x018c }
                java.lang.Class r9 = java.lang.Integer.TYPE     // Catch:{ Throwable -> 0x018c }
                r8[r2] = r9     // Catch:{ Throwable -> 0x018c }
                java.lang.reflect.Method r13 = r1.getMethod(r13, r8)     // Catch:{ Throwable -> 0x018c }
                java.lang.Object[] r8 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x018c }
                java.io.File r9 = new java.io.File     // Catch:{ Throwable -> 0x018c }
                r9.<init>(r14)     // Catch:{ Throwable -> 0x018c }
                r8[r4] = r9     // Catch:{ Throwable -> 0x018c }
                java.lang.Integer r14 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x018c }
                r8[r2] = r14     // Catch:{ Throwable -> 0x018c }
                java.lang.Object r13 = r13.invoke(r7, r8)     // Catch:{ Throwable -> 0x018c }
                goto L_0x0108
            L_0x00cf:
                java.lang.String r8 = "parsePackage"
                r9 = 4
                java.lang.Class[] r10 = new java.lang.Class[r9]     // Catch:{ Throwable -> 0x018c }
                java.lang.Class<java.io.File> r11 = java.io.File.class
                r10[r4] = r11     // Catch:{ Throwable -> 0x018c }
                java.lang.Class<java.lang.String> r11 = java.lang.String.class
                r10[r2] = r11     // Catch:{ Throwable -> 0x018c }
                java.lang.Class<android.util.DisplayMetrics> r11 = android.util.DisplayMetrics.class
                r10[r3] = r11     // Catch:{ Throwable -> 0x018c }
                java.lang.Class r11 = java.lang.Integer.TYPE     // Catch:{ Throwable -> 0x018c }
                r12 = 3
                r10[r12] = r11     // Catch:{ Throwable -> 0x018c }
                java.lang.reflect.Method r8 = r1.getMethod(r8, r10)     // Catch:{ Throwable -> 0x018c }
                java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x018c }
                java.io.File r10 = new java.io.File     // Catch:{ Throwable -> 0x018c }
                r10.<init>(r14)     // Catch:{ Throwable -> 0x018c }
                r9[r4] = r10     // Catch:{ Throwable -> 0x018c }
                r9[r2] = r0     // Catch:{ Throwable -> 0x018c }
                android.content.res.Resources r13 = r13.getResources()     // Catch:{ Throwable -> 0x018c }
                android.util.DisplayMetrics r13 = r13.getDisplayMetrics()     // Catch:{ Throwable -> 0x018c }
                r9[r3] = r13     // Catch:{ Throwable -> 0x018c }
                java.lang.Integer r13 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x018c }
                r9[r12] = r13     // Catch:{ Throwable -> 0x018c }
                java.lang.Object r13 = r8.invoke(r7, r9)     // Catch:{ Throwable -> 0x018c }
            L_0x0108:
                java.lang.String r14 = "collectCertificates"
                java.lang.Class[] r8 = new java.lang.Class[r3]     // Catch:{ Throwable -> 0x018c }
                java.lang.String r9 = "android.content.pm.PackageParser$Package"
                java.lang.Class r9 = java.lang.Class.forName(r9)     // Catch:{ Throwable -> 0x018c }
                r8[r4] = r9     // Catch:{ Throwable -> 0x018c }
                java.lang.Class r9 = java.lang.Integer.TYPE     // Catch:{ Throwable -> 0x018c }
                r8[r2] = r9     // Catch:{ Throwable -> 0x018c }
                java.lang.reflect.Method r14 = r1.getMethod(r14, r8)     // Catch:{ Throwable -> 0x018c }
                java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x018c }
                r1[r4] = r13     // Catch:{ Throwable -> 0x018c }
                r8 = 64
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Throwable -> 0x018c }
                r1[r2] = r8     // Catch:{ Throwable -> 0x018c }
                r14.invoke(r7, r1)     // Catch:{ Throwable -> 0x018c }
                java.lang.Class r14 = r13.getClass()     // Catch:{ Throwable -> 0x018c }
                java.lang.String r1 = "mSignatures"
                java.lang.reflect.Field r14 = r14.getField(r1)     // Catch:{ Throwable -> 0x018c }
                java.lang.Object r13 = r14.get(r13)     // Catch:{ Throwable -> 0x018c }
                android.content.pm.Signature[] r13 = (android.content.pm.Signature[]) r13     // Catch:{ Throwable -> 0x018c }
                r14 = r13
                r13 = 1
            L_0x013d:
                if (r15 == 0) goto L_0x0152
                java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0152 }
                r3 = 10
                java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x0152 }
                r1[r4] = r3     // Catch:{ Throwable -> 0x0152 }
                java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ Throwable -> 0x0152 }
                r1[r2] = r13     // Catch:{ Throwable -> 0x0152 }
                r15.onReceiveValue(r1)     // Catch:{ Throwable -> 0x0152 }
            L_0x0152:
                if (r14 == 0) goto L_0x0164
                int r13 = r14.length     // Catch:{ Throwable -> 0x018c }
                if (r13 <= 0) goto L_0x0164
                java.lang.String r13 = "SignatureVerifier"
                java.lang.String r15 = "摘要校验成功!"
                com.uc.webview.export.internal.utility.Log.d(r13, r15)     // Catch:{ Throwable -> 0x0161 }
                r0 = r14
                goto L_0x016c
            L_0x0161:
                r13 = move-exception
                r0 = r14
                goto L_0x018d
            L_0x0164:
                java.lang.String r13 = "SignatureVerifier"
                java.lang.String r14 = "摘要校验失败"
                com.uc.webview.export.internal.utility.Log.e(r13, r14)     // Catch:{ Throwable -> 0x018c }
            L_0x016c:
                java.lang.String r13 = "SignatureVerifier"
                java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x018c }
                java.lang.String r15 = "耗时："
                r14.<init>(r15)     // Catch:{ Throwable -> 0x018c }
                long r1 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x018c }
                r15 = 0
                long r1 = r1 - r5
                r14.append(r1)     // Catch:{ Throwable -> 0x018c }
                java.lang.String r15 = "ms"
                r14.append(r15)     // Catch:{ Throwable -> 0x018c }
                java.lang.String r14 = r14.toString()     // Catch:{ Throwable -> 0x018c }
                com.uc.webview.export.internal.utility.Log.i(r13, r14)     // Catch:{ Throwable -> 0x018c }
                goto L_0x0196
            L_0x018c:
                r13 = move-exception
            L_0x018d:
                java.lang.String r14 = "SignatureVerifier"
                java.lang.String r13 = r13.getMessage()
                com.uc.webview.export.internal.utility.Log.e(r14, r13)
            L_0x0196:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.i.a.a(android.content.Context, java.lang.String, android.webkit.ValueCallback):android.content.pm.Signature[]");
        }

        static Signature[] a(byte[] bArr) throws Exception {
            X509Certificate[][] a = a.a(ByteBuffer.wrap(bArr));
            Signature[] signatureArr = new Signature[a.length];
            for (int i = 0; i < a.length; i++) {
                signatureArr[i] = new Signature(a[i][0].getEncoded());
            }
            return signatureArr;
        }

        public static PublicKey[] a(Signature[] signatureArr) {
            if (signatureArr != null) {
                try {
                    if (signatureArr.length != 0) {
                        int length = signatureArr.length;
                        PublicKey[] publicKeyArr = new PublicKey[length];
                        for (int i = 0; i < length; i++) {
                            publicKeyArr[i] = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(signatureArr[i].toByteArray()))).getPublicKey();
                        }
                        return publicKeyArr;
                    }
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder("获取公钥异常：\n");
                    sb.append(e.getMessage());
                    Log.e("SignatureVerifier", sb.toString());
                    return null;
                }
            }
            return null;
        }

        public static final boolean a(PublicKey[] publicKeyArr) {
            return publicKeyArr == null || publicKeyArr.length <= 0;
        }
    }

    /* compiled from: ProGuard */
    public static class b implements ValueCallback<Object[]> {
        private String a;

        public final /* synthetic */ void onReceiveValue(Object obj) {
            Object[] objArr = (Object[]) obj;
            StringBuilder sb = new StringBuilder("orign: ");
            sb.append(this.a);
            sb.append(" objects: ");
            sb.append(Arrays.toString(objArr));
            Log.d("VerifyStat", sb.toString());
            if (objArr != null && objArr.length == 2 && (objArr[0] instanceof Integer) && (objArr[1] instanceof Integer)) {
                String num = ((Integer) objArr[0]).toString();
                int intValue = ((Integer) objArr[0]).intValue();
                if (intValue == 8) {
                    WaStat.stat(String.format("%s_err_%s", new Object[]{this.a, num}));
                } else if (intValue == 10) {
                    WaStat.stat(String.format("%s_ver_%s", new Object[]{this.a, num}));
                }
            }
        }

        public b(String str) {
            this.a = str;
        }
    }

    public static boolean a(String str, Context context, Context context2, String str2, ValueCallback<Object[]> valueCallback) {
        try {
            m.a(str, context);
            boolean a2 = a(str, context, context2, str2, valueCallback, null);
            if (!a2) {
                m.a(str, context, a2);
            }
            return a2;
        } catch (UCSetupException unused) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0042 A[Catch:{ Throwable -> 0x02b9, Throwable -> 0x02dd, all -> 0x02b6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0099 A[SYNTHETIC, Splitter:B:23:0x0099] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0338  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r19, android.content.Context r20, android.content.Context r21, java.lang.String r22, android.webkit.ValueCallback<java.lang.Object[]> r23, com.uc.webview.export.internal.utility.g.a r24) {
        /*
            r1 = r19
            r2 = r20
            r3 = r22
            r4 = r23
            r5 = r24
            java.io.File r6 = new java.io.File
            r6.<init>(r1)
            boolean r6 = r6.exists()
            r7 = 0
            if (r6 != 0) goto L_0x0017
            return r7
        L_0x0017:
            java.lang.String r6 = "SignatureVerifier"
            java.lang.String r8 = "verify: file = "
            java.lang.String r9 = java.lang.String.valueOf(r19)
            java.lang.String r8 = r8.concat(r9)
            com.uc.webview.export.internal.utility.Log.d(r6, r8)
            long r8 = java.lang.System.currentTimeMillis()
            r6 = 8
            r10 = 2
            r11 = 1
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02b9 }
            android.content.pm.Signature[] r1 = com.uc.webview.export.internal.utility.i.a.a(r2, r1, r4)     // Catch:{ Throwable -> 0x02b9 }
            if (r1 == 0) goto L_0x003f
            int r14 = r1.length     // Catch:{ Throwable -> 0x02b9 }
            if (r14 > 0) goto L_0x003d
            goto L_0x003f
        L_0x003d:
            r14 = 0
            goto L_0x0040
        L_0x003f:
            r14 = 1
        L_0x0040:
            if (r14 == 0) goto L_0x0099
            java.lang.String r1 = "SignatureVerifier"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r3 = "verify: failed: Signatures of archive is empty. Costs "
            r2.<init>(r3)     // Catch:{ Throwable -> 0x02b9 }
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02b9 }
            r3 = 0
            long r14 = r14 - r12
            r2.append(r14)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r3 = "ms."
            r2.append(r3)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x02b9 }
            com.uc.webview.export.internal.utility.Log.d(r1, r2)     // Catch:{ Throwable -> 0x02b9 }
            if (r4 == 0) goto L_0x0074
            java.lang.Object[] r1 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0074 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x0074 }
            r1[r7] = r2     // Catch:{ Throwable -> 0x0074 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r11)     // Catch:{ Throwable -> 0x0074 }
            r1[r11] = r2     // Catch:{ Throwable -> 0x0074 }
            r4.onReceiveValue(r1)     // Catch:{ Throwable -> 0x0074 }
        L_0x0074:
            if (r5 == 0) goto L_0x007b
            long r1 = com.uc.webview.export.internal.utility.g.a.i     // Catch:{ Throwable -> 0x02b9 }
            r5.a(r1)     // Catch:{ Throwable -> 0x02b9 }
        L_0x007b:
            java.lang.String r1 = "SignatureVerifier"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Verify: total costs:"
            r2.<init>(r3)
            long r3 = java.lang.System.currentTimeMillis()
            long r3 = r3 - r8
            r2.append(r3)
            java.lang.String r3 = "ms"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return r7
        L_0x0099:
            java.security.PublicKey[] r1 = com.uc.webview.export.internal.utility.i.a.a(r1)     // Catch:{ Throwable -> 0x02b9 }
            boolean r14 = com.uc.webview.export.internal.utility.i.a.a(r1)     // Catch:{ Throwable -> 0x02b9 }
            if (r14 == 0) goto L_0x00fa
            java.lang.String r1 = "SignatureVerifier"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r3 = "verify: failed: PublicKeys of archive is empty. Costs "
            r2.<init>(r3)     // Catch:{ Throwable -> 0x02b9 }
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02b9 }
            r3 = 0
            long r14 = r14 - r12
            r2.append(r14)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r3 = "ms."
            r2.append(r3)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x02b9 }
            com.uc.webview.export.internal.utility.Log.d(r1, r2)     // Catch:{ Throwable -> 0x02b9 }
            if (r4 == 0) goto L_0x00d5
            java.lang.Object[] r1 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x00d5 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x00d5 }
            r1[r7] = r2     // Catch:{ Throwable -> 0x00d5 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r10)     // Catch:{ Throwable -> 0x00d5 }
            r1[r11] = r2     // Catch:{ Throwable -> 0x00d5 }
            r4.onReceiveValue(r1)     // Catch:{ Throwable -> 0x00d5 }
        L_0x00d5:
            if (r5 == 0) goto L_0x00dc
            long r1 = com.uc.webview.export.internal.utility.g.a.j     // Catch:{ Throwable -> 0x02b9 }
            r5.a(r1)     // Catch:{ Throwable -> 0x02b9 }
        L_0x00dc:
            java.lang.String r1 = "SignatureVerifier"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Verify: total costs:"
            r2.<init>(r3)
            long r3 = java.lang.System.currentTimeMillis()
            long r3 = r3 - r8
            r2.append(r3)
            java.lang.String r3 = "ms"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return r7
        L_0x00fa:
            java.lang.String r14 = "SignatureVerifier"
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r7 = "verify: step 0: get PublicKeys of archive ok. Costs "
            r15.<init>(r7)     // Catch:{ Throwable -> 0x02b9 }
            long r16 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02b9 }
            r7 = 0
            long r12 = r16 - r12
            r15.append(r12)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r7 = "ms."
            r15.append(r7)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r7 = r15.toString()     // Catch:{ Throwable -> 0x02b9 }
            com.uc.webview.export.internal.utility.Log.d(r14, r7)     // Catch:{ Throwable -> 0x02b9 }
            if (r21 == 0) goto L_0x018b
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r7 = r21.getPackageName()     // Catch:{ Throwable -> 0x02b9 }
            android.content.pm.Signature[] r7 = com.uc.webview.export.internal.utility.i.a.a(r2, r7)     // Catch:{ Throwable -> 0x02b9 }
            boolean r7 = a(r1, r7)     // Catch:{ Throwable -> 0x02b9 }
            if (r7 == 0) goto L_0x016b
            java.lang.String r1 = "SignatureVerifier"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r3 = "verify: step 1: get Signatures of app from current context and verify ok. Costs "
            r2.<init>(r3)     // Catch:{ Throwable -> 0x02b9 }
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02b9 }
            r3 = 0
            long r14 = r14 - r12
            r2.append(r14)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r3 = "ms."
            r2.append(r3)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x02b9 }
            com.uc.webview.export.internal.utility.Log.d(r1, r2)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r1 = "SignatureVerifier"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Verify: total costs:"
            r2.<init>(r3)
            long r3 = java.lang.System.currentTimeMillis()
            long r3 = r3 - r8
            r2.append(r3)
            java.lang.String r3 = "ms"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return r11
        L_0x016b:
            java.lang.String r7 = "SignatureVerifier"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r15 = "verify: step 1: get Signatures of app from current context and verify failed. Costs "
            r14.<init>(r15)     // Catch:{ Throwable -> 0x02b9 }
            long r16 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02b9 }
            r15 = 0
            long r12 = r16 - r12
            r14.append(r12)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r12 = "ms."
            r14.append(r12)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r12 = r14.toString()     // Catch:{ Throwable -> 0x02b9 }
            com.uc.webview.export.internal.utility.Log.d(r7, r12)     // Catch:{ Throwable -> 0x02b9 }
        L_0x018b:
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r7 = "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"
            byte[] r7 = android.util.Base64.decode(r7, r10)     // Catch:{ Throwable -> 0x02b9 }
            android.content.pm.Signature[] r7 = com.uc.webview.export.internal.utility.i.a.a(r7)     // Catch:{ Throwable -> 0x01de }
            boolean r7 = a(r1, r7)     // Catch:{ Throwable -> 0x01de }
            if (r7 == 0) goto L_0x01de
            java.lang.String r7 = "SignatureVerifier"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01de }
            java.lang.String r15 = "verify: step 2: get Signatures of app from hardcode app and verify ok. Costs "
            r14.<init>(r15)     // Catch:{ Throwable -> 0x01de }
            long r16 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x01de }
            r15 = 0
            long r10 = r16 - r12
            r14.append(r10)     // Catch:{ Throwable -> 0x01de }
            java.lang.String r10 = "ms."
            r14.append(r10)     // Catch:{ Throwable -> 0x01de }
            java.lang.String r10 = r14.toString()     // Catch:{ Throwable -> 0x01de }
            com.uc.webview.export.internal.utility.Log.d(r7, r10)     // Catch:{ Throwable -> 0x01de }
            java.lang.String r1 = "SignatureVerifier"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Verify: total costs:"
            r2.<init>(r3)
            long r3 = java.lang.System.currentTimeMillis()
            long r3 = r3 - r8
            r2.append(r3)
            java.lang.String r3 = "ms"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            r1 = 1
            return r1
        L_0x01de:
            java.lang.String r7 = "SignatureVerifier"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r11 = "verify: step 2: get Signatures of app from hardcode app and verify failed. Costs "
            r10.<init>(r11)     // Catch:{ Throwable -> 0x02b9 }
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02b9 }
            r11 = 0
            long r14 = r14 - r12
            r10.append(r14)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r11 = "ms."
            r10.append(r11)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x02b9 }
            com.uc.webview.export.internal.utility.Log.d(r7, r10)     // Catch:{ Throwable -> 0x02b9 }
            if (r3 == 0) goto L_0x0280
            int r7 = r22.length()     // Catch:{ Throwable -> 0x02b9 }
            if (r7 <= 0) goto L_0x0280
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02b9 }
            android.content.pm.Signature[] r2 = com.uc.webview.export.internal.utility.i.a.a(r2, r3)     // Catch:{ Throwable -> 0x02b9 }
            boolean r1 = a(r1, r2)     // Catch:{ Throwable -> 0x02b9 }
            if (r1 == 0) goto L_0x0259
            java.lang.String r1 = "SignatureVerifier"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r7 = "verify: step 3: get Signatures of app from "
            r2.<init>(r7)     // Catch:{ Throwable -> 0x02b9 }
            r2.append(r3)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r3 = " and verify ok. Costs "
            r2.append(r3)     // Catch:{ Throwable -> 0x02b9 }
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02b9 }
            r3 = 0
            long r12 = r12 - r10
            r2.append(r12)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r3 = "ms."
            r2.append(r3)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x02b9 }
            com.uc.webview.export.internal.utility.Log.d(r1, r2)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r1 = "SignatureVerifier"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Verify: total costs:"
            r2.<init>(r3)
            long r3 = java.lang.System.currentTimeMillis()
            long r3 = r3 - r8
            r2.append(r3)
            java.lang.String r3 = "ms"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            r1 = 1
            return r1
        L_0x0259:
            java.lang.String r1 = "SignatureVerifier"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r7 = "verify: step 3: get Signatures of app from "
            r2.<init>(r7)     // Catch:{ Throwable -> 0x02b9 }
            r2.append(r3)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r3 = " and verify failed. Costs "
            r2.append(r3)     // Catch:{ Throwable -> 0x02b9 }
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02b9 }
            r3 = 0
            long r12 = r12 - r10
            r2.append(r12)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r3 = "ms."
            r2.append(r3)     // Catch:{ Throwable -> 0x02b9 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x02b9 }
            com.uc.webview.export.internal.utility.Log.d(r1, r2)     // Catch:{ Throwable -> 0x02b9 }
        L_0x0280:
            if (r4 == 0) goto L_0x0297
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0297 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x0297 }
            r3 = 0
            r1[r3] = r2     // Catch:{ Throwable -> 0x0297 }
            r2 = 3
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Throwable -> 0x0297 }
            r3 = 1
            r1[r3] = r2     // Catch:{ Throwable -> 0x0297 }
            r4.onReceiveValue(r1)     // Catch:{ Throwable -> 0x0297 }
        L_0x0297:
            java.lang.String r1 = "SignatureVerifier"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Verify: total costs:"
            r2.<init>(r3)
            long r3 = java.lang.System.currentTimeMillis()
            long r3 = r3 - r8
            r2.append(r3)
            java.lang.String r3 = "ms"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            goto L_0x0336
        L_0x02b6:
            r0 = move-exception
            r1 = r0
            goto L_0x02fb
        L_0x02b9:
            r0 = move-exception
            r1 = r0
            if (r4 == 0) goto L_0x02dd
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x02dd }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x02dd }
            r6 = 0
            r2[r6] = r3     // Catch:{ Throwable -> 0x02dd }
            java.lang.Class r3 = r1.getClass()     // Catch:{ Throwable -> 0x02dd }
            java.lang.String r3 = r3.getName()     // Catch:{ Throwable -> 0x02dd }
            int r3 = r3.hashCode()     // Catch:{ Throwable -> 0x02dd }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x02dd }
            r6 = 1
            r2[r6] = r3     // Catch:{ Throwable -> 0x02dd }
            r4.onReceiveValue(r2)     // Catch:{ Throwable -> 0x02dd }
        L_0x02dd:
            if (r5 == 0) goto L_0x02e4
            long r2 = com.uc.webview.export.internal.utility.g.a.m     // Catch:{ all -> 0x02b6 }
            r5.a(r2)     // Catch:{ all -> 0x02b6 }
        L_0x02e4:
            if (r5 == 0) goto L_0x02eb
            java.lang.String r2 = "csc_sigvrfe"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2)     // Catch:{ all -> 0x02b6 }
        L_0x02eb:
            if (r5 == 0) goto L_0x0319
            java.lang.String r2 = "csc_sigvrfe_v"
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x02b6 }
            java.lang.String r1 = com.uc.webview.export.internal.utility.g.b(r1)     // Catch:{ all -> 0x02b6 }
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2, r1)     // Catch:{ all -> 0x02b6 }
            goto L_0x0319
        L_0x02fb:
            java.lang.String r2 = "SignatureVerifier"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Verify: total costs:"
            r3.<init>(r4)
            long r4 = java.lang.System.currentTimeMillis()
            long r4 = r4 - r8
            r3.append(r4)
            java.lang.String r4 = "ms"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.uc.webview.export.internal.utility.Log.d(r2, r3)
            throw r1
        L_0x0319:
            java.lang.String r1 = "SignatureVerifier"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Verify: total costs:"
            r2.<init>(r3)
            long r3 = java.lang.System.currentTimeMillis()
            long r3 = r3 - r8
            r2.append(r3)
            java.lang.String r3 = "ms"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
        L_0x0336:
            if (r5 == 0) goto L_0x033d
            long r1 = com.uc.webview.export.internal.utility.g.a.l
            r5.a(r1)
        L_0x033d:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.i.a(java.lang.String, android.content.Context, android.content.Context, java.lang.String, android.webkit.ValueCallback, com.uc.webview.export.internal.utility.g$a):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x004e A[Catch:{ Throwable -> 0x0060 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final boolean a(java.security.PublicKey[] r6, android.content.pm.Signature[] r7) {
        /*
            r0 = 0
            java.security.PublicKey[] r7 = com.uc.webview.export.internal.utility.i.a.a(r7)     // Catch:{ Throwable -> 0x0060 }
            boolean r1 = com.uc.webview.export.internal.utility.i.a.a(r7)     // Catch:{ Throwable -> 0x0060 }
            if (r1 == 0) goto L_0x0014
            java.lang.String r6 = "SignatureVerifier"
            java.lang.String r7 = "公钥校验错误：Implement.isEmpty(appPublicKeys) == true"
            com.uc.webview.export.internal.utility.Log.d(r6, r7)     // Catch:{ Throwable -> 0x0060 }
            return r0
        L_0x0014:
            r1 = 1
            if (r7 == 0) goto L_0x0044
            if (r6 != 0) goto L_0x001a
            goto L_0x0044
        L_0x001a:
            java.util.HashSet r2 = new java.util.HashSet     // Catch:{ Throwable -> 0x0060 }
            r2.<init>()     // Catch:{ Throwable -> 0x0060 }
            int r3 = r7.length     // Catch:{ Throwable -> 0x0060 }
            r4 = 0
        L_0x0021:
            if (r4 >= r3) goto L_0x002b
            r5 = r7[r4]     // Catch:{ Throwable -> 0x0060 }
            r2.add(r5)     // Catch:{ Throwable -> 0x0060 }
            int r4 = r4 + 1
            goto L_0x0021
        L_0x002b:
            java.util.HashSet r7 = new java.util.HashSet     // Catch:{ Throwable -> 0x0060 }
            r7.<init>()     // Catch:{ Throwable -> 0x0060 }
            int r3 = r6.length     // Catch:{ Throwable -> 0x0060 }
            r4 = 0
        L_0x0032:
            if (r4 >= r3) goto L_0x003c
            r5 = r6[r4]     // Catch:{ Throwable -> 0x0060 }
            r7.add(r5)     // Catch:{ Throwable -> 0x0060 }
            int r4 = r4 + 1
            goto L_0x0032
        L_0x003c:
            boolean r6 = r2.equals(r7)     // Catch:{ Throwable -> 0x0060 }
            if (r6 == 0) goto L_0x004b
            r6 = 1
            goto L_0x004c
        L_0x0044:
            java.lang.String r6 = "SignatureVerifier"
            java.lang.String r7 = "Sign.equals: s1 == null || s2 == null"
            com.uc.webview.export.internal.utility.Log.e(r6, r7)     // Catch:{ Throwable -> 0x0060 }
        L_0x004b:
            r6 = 0
        L_0x004c:
            if (r6 != 0) goto L_0x0057
            java.lang.String r6 = "SignatureVerifier"
            java.lang.String r7 = "公钥校验错误：Implement.equals(appPublicKeys, archiveKeys) == false"
            com.uc.webview.export.internal.utility.Log.d(r6, r7)     // Catch:{ Throwable -> 0x0060 }
            return r0
        L_0x0057:
            java.lang.String r6 = "SignatureVerifier"
            java.lang.String r7 = "公钥校验正确!"
            com.uc.webview.export.internal.utility.Log.d(r6, r7)
            return r1
        L_0x0060:
            java.lang.String r6 = "SignatureVerifier"
            java.lang.String r7 = "公钥校验错误：Implement.isEmpty(appPublicKeys) == true"
            com.uc.webview.export.internal.utility.Log.d(r6, r7)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.i.a(java.security.PublicKey[], android.content.pm.Signature[]):boolean");
    }
}
