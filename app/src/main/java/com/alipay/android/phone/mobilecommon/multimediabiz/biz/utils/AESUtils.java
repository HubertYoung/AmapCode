package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.util.Base64;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    private static final Logger a = Logger.getLogger((String) "AESUtils");

    public static class DecryptException extends Exception {
    }

    public static Cipher initAESCipher(String encryptKey, int cipherMode) {
        Cipher cipher = null;
        try {
            byte[] raw = Base64.decode(encryptKey, 0);
            if (raw == null || raw.length != 32) {
                a.e("encryptKey=" + encryptKey + ",length=" + (raw == null ? 0 : raw.length), new Object[0]);
                return null;
            }
            byte[] key = new byte[32];
            System.arraycopy(raw, 0, key, 0, raw.length);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(cipherMode, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            return cipher;
        } catch (NoSuchAlgorithmException e) {
            a.e("NoSuchAlgorithmException", new Object[0]);
        } catch (NoSuchPaddingException e2) {
            a.e("NoSuchPaddingException", new Object[0]);
        } catch (InvalidKeyException e3) {
            a.e("InvalidKeyException", new Object[0]);
        } catch (InvalidAlgorithmParameterException e4) {
            a.e("InvalidAlgorithmParameterException", new Object[0]);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0063 A[SYNTHETIC, Splitter:B:19:0x0063] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0068 A[SYNTHETIC, Splitter:B:22:0x0068] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0070 A[SYNTHETIC, Splitter:B:25:0x0070] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01bf A[SYNTHETIC, Splitter:B:51:0x01bf] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x01c4 A[SYNTHETIC, Splitter:B:54:0x01c4] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x01cc A[SYNTHETIC, Splitter:B:57:0x01cc] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0247 A[SYNTHETIC, Splitter:B:68:0x0247] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x024c A[SYNTHETIC, Splitter:B:71:0x024c] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0254 A[SYNTHETIC, Splitter:B:74:0x0254] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:48:0x0199=Splitter:B:48:0x0199, B:16:0x003d=Splitter:B:16:0x003d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean encryptFile(java.lang.String r22, java.lang.String r23, java.lang.String r24) {
        /*
            long r16 = java.lang.System.currentTimeMillis()
            r10 = 0
            r13 = 0
            r6 = 0
            java.io.FileInputStream r11 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x02da, Throwable -> 0x0198 }
            r0 = r23
            r11.<init>(r0)     // Catch:{ FileNotFoundException -> 0x02da, Throwable -> 0x0198 }
            java.io.File r9 = new java.io.File     // Catch:{ FileNotFoundException -> 0x02dd, Throwable -> 0x02cb, all -> 0x02be }
            r0 = r24
            r9.<init>(r0)     // Catch:{ FileNotFoundException -> 0x02dd, Throwable -> 0x02cb, all -> 0x02be }
            java.io.FileOutputStream r14 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x02dd, Throwable -> 0x02cb, all -> 0x02be }
            r14.<init>(r9)     // Catch:{ FileNotFoundException -> 0x02dd, Throwable -> 0x02cb, all -> 0x02be }
            r15 = 1
            r0 = r22
            javax.crypto.Cipher r5 = initAESCipher(r0, r15)     // Catch:{ FileNotFoundException -> 0x02e1, Throwable -> 0x02cf, all -> 0x02c1 }
            javax.crypto.CipherInputStream r7 = new javax.crypto.CipherInputStream     // Catch:{ FileNotFoundException -> 0x02e1, Throwable -> 0x02cf, all -> 0x02c1 }
            r7.<init>(r11, r5)     // Catch:{ FileNotFoundException -> 0x02e1, Throwable -> 0x02cf, all -> 0x02c1 }
            r15 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r15]     // Catch:{ FileNotFoundException -> 0x0039, Throwable -> 0x02d4, all -> 0x02c5 }
        L_0x002a:
            int r12 = r7.read(r4)     // Catch:{ FileNotFoundException -> 0x0039, Throwable -> 0x02d4, all -> 0x02c5 }
            r15 = -1
            if (r12 == r15) goto L_0x0075
            r15 = 0
            r14.write(r4, r15, r12)     // Catch:{ FileNotFoundException -> 0x0039, Throwable -> 0x02d4, all -> 0x02c5 }
            r14.flush()     // Catch:{ FileNotFoundException -> 0x0039, Throwable -> 0x02d4, all -> 0x02c5 }
            goto L_0x002a
        L_0x0039:
            r8 = move-exception
            r6 = r7
            r13 = r14
            r10 = r11
        L_0x003d:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a     // Catch:{ all -> 0x0244 }
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ all -> 0x0244 }
            java.lang.String r19 = "FileNotFoundException.e="
            r18.<init>(r19)     // Catch:{ all -> 0x0244 }
            java.lang.String r19 = r8.getMessage()     // Catch:{ all -> 0x0244 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x0244 }
            java.lang.String r18 = r18.toString()     // Catch:{ all -> 0x0244 }
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0244 }
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)     // Catch:{ all -> 0x0244 }
            if (r10 == 0) goto L_0x0066
            r10.close()     // Catch:{ IOException -> 0x0123 }
        L_0x0066:
            if (r13 == 0) goto L_0x006e
            r13.flush()     // Catch:{ IOException -> 0x014a }
            r13.close()     // Catch:{ IOException -> 0x014a }
        L_0x006e:
            if (r6 == 0) goto L_0x0073
            r6.close()     // Catch:{ IOException -> 0x0171 }
        L_0x0073:
            r15 = 0
        L_0x0074:
            return r15
        L_0x0075:
            r11.close()     // Catch:{ IOException -> 0x00b0 }
        L_0x0078:
            r14.flush()     // Catch:{ IOException -> 0x00d6 }
            r14.close()     // Catch:{ IOException -> 0x00d6 }
        L_0x007e:
            r7.close()     // Catch:{ IOException -> 0x00fc }
        L_0x0081:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "encryptFile cost time="
            r18.<init>(r19)
            long r20 = java.lang.System.currentTimeMillis()
            long r20 = r20 - r16
            r0 = r18
            r1 = r20
            java.lang.StringBuilder r18 = r0.append(r1)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.i(r0, r1)
            r15 = 1
            r6 = r7
            r13 = r14
            r10 = r11
            goto L_0x0074
        L_0x00b0:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x0078
        L_0x00d6:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x007e
        L_0x00fc:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x0081
        L_0x0123:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x0066
        L_0x014a:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x006e
        L_0x0171:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x0073
        L_0x0198:
            r8 = move-exception
        L_0x0199:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a     // Catch:{ all -> 0x0244 }
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ all -> 0x0244 }
            java.lang.String r19 = "Throwable.e="
            r18.<init>(r19)     // Catch:{ all -> 0x0244 }
            java.lang.String r19 = r8.getMessage()     // Catch:{ all -> 0x0244 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x0244 }
            java.lang.String r18 = r18.toString()     // Catch:{ all -> 0x0244 }
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0244 }
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)     // Catch:{ all -> 0x0244 }
            if (r10 == 0) goto L_0x01c2
            r10.close()     // Catch:{ IOException -> 0x01d2 }
        L_0x01c2:
            if (r13 == 0) goto L_0x01ca
            r13.flush()     // Catch:{ IOException -> 0x01f8 }
            r13.close()     // Catch:{ IOException -> 0x01f8 }
        L_0x01ca:
            if (r6 == 0) goto L_0x01cf
            r6.close()     // Catch:{ IOException -> 0x021e }
        L_0x01cf:
            r15 = 0
            goto L_0x0074
        L_0x01d2:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x01c2
        L_0x01f8:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x01ca
        L_0x021e:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x01cf
        L_0x0244:
            r15 = move-exception
        L_0x0245:
            if (r10 == 0) goto L_0x024a
            r10.close()     // Catch:{ IOException -> 0x0258 }
        L_0x024a:
            if (r13 == 0) goto L_0x0252
            r13.flush()     // Catch:{ IOException -> 0x027a }
            r13.close()     // Catch:{ IOException -> 0x027a }
        L_0x0252:
            if (r6 == 0) goto L_0x0257
            r6.close()     // Catch:{ IOException -> 0x029c }
        L_0x0257:
            throw r15
        L_0x0258:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r18 = a
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            java.lang.String r20 = "IOException.e="
            r19.<init>(r20)
            java.lang.String r20 = r8.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r20 = 0
            r0 = r20
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r20 = r0
            r18.e(r19, r20)
            goto L_0x024a
        L_0x027a:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r18 = a
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            java.lang.String r20 = "IOException.e="
            r19.<init>(r20)
            java.lang.String r20 = r8.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r20 = 0
            r0 = r20
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r20 = r0
            r18.e(r19, r20)
            goto L_0x0252
        L_0x029c:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r18 = a
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            java.lang.String r20 = "IOException.e="
            r19.<init>(r20)
            java.lang.String r20 = r8.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r20 = 0
            r0 = r20
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r20 = r0
            r18.e(r19, r20)
            goto L_0x0257
        L_0x02be:
            r15 = move-exception
            r10 = r11
            goto L_0x0245
        L_0x02c1:
            r15 = move-exception
            r13 = r14
            r10 = r11
            goto L_0x0245
        L_0x02c5:
            r15 = move-exception
            r6 = r7
            r13 = r14
            r10 = r11
            goto L_0x0245
        L_0x02cb:
            r8 = move-exception
            r10 = r11
            goto L_0x0199
        L_0x02cf:
            r8 = move-exception
            r13 = r14
            r10 = r11
            goto L_0x0199
        L_0x02d4:
            r8 = move-exception
            r6 = r7
            r13 = r14
            r10 = r11
            goto L_0x0199
        L_0x02da:
            r8 = move-exception
            goto L_0x003d
        L_0x02dd:
            r8 = move-exception
            r10 = r11
            goto L_0x003d
        L_0x02e1:
            r8 = move-exception
            r13 = r14
            r10 = r11
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils.encryptFile(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:21:0x00a5=Splitter:B:21:0x00a5, B:16:0x003d=Splitter:B:16:0x003d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean encryptFile(java.lang.String r22, byte[] r23, java.lang.String r24) {
        /*
            long r16 = java.lang.System.currentTimeMillis()
            r10 = 0
            r13 = 0
            r6 = 0
            java.io.ByteArrayInputStream r11 = new java.io.ByteArrayInputStream     // Catch:{ FileNotFoundException -> 0x00f7, Throwable -> 0x00a4 }
            r0 = r23
            r11.<init>(r0)     // Catch:{ FileNotFoundException -> 0x00f7, Throwable -> 0x00a4 }
            java.io.File r9 = new java.io.File     // Catch:{ FileNotFoundException -> 0x00fa, Throwable -> 0x00eb, all -> 0x00df }
            r0 = r24
            r9.<init>(r0)     // Catch:{ FileNotFoundException -> 0x00fa, Throwable -> 0x00eb, all -> 0x00df }
            java.io.FileOutputStream r14 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x00fa, Throwable -> 0x00eb, all -> 0x00df }
            r14.<init>(r9)     // Catch:{ FileNotFoundException -> 0x00fa, Throwable -> 0x00eb, all -> 0x00df }
            r15 = 1
            r0 = r22
            javax.crypto.Cipher r5 = initAESCipher(r0, r15)     // Catch:{ FileNotFoundException -> 0x00fe, Throwable -> 0x00ee, all -> 0x00e2 }
            javax.crypto.CipherInputStream r7 = new javax.crypto.CipherInputStream     // Catch:{ FileNotFoundException -> 0x00fe, Throwable -> 0x00ee, all -> 0x00e2 }
            r7.<init>(r11, r5)     // Catch:{ FileNotFoundException -> 0x00fe, Throwable -> 0x00ee, all -> 0x00e2 }
            r15 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r15]     // Catch:{ FileNotFoundException -> 0x0039, Throwable -> 0x00f2, all -> 0x00e6 }
        L_0x002a:
            int r12 = r7.read(r4)     // Catch:{ FileNotFoundException -> 0x0039, Throwable -> 0x00f2, all -> 0x00e6 }
            r15 = -1
            if (r12 == r15) goto L_0x006c
            r15 = 0
            r14.write(r4, r15, r12)     // Catch:{ FileNotFoundException -> 0x0039, Throwable -> 0x00f2, all -> 0x00e6 }
            r14.flush()     // Catch:{ FileNotFoundException -> 0x0039, Throwable -> 0x00f2, all -> 0x00e6 }
            goto L_0x002a
        L_0x0039:
            r8 = move-exception
            r6 = r7
            r13 = r14
            r10 = r11
        L_0x003d:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a     // Catch:{ all -> 0x00d4 }
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d4 }
            java.lang.String r19 = "FileNotFoundException.e="
            r18.<init>(r19)     // Catch:{ all -> 0x00d4 }
            java.lang.String r19 = r8.getMessage()     // Catch:{ all -> 0x00d4 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x00d4 }
            java.lang.String r18 = r18.toString()     // Catch:{ all -> 0x00d4 }
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x00d4 }
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)     // Catch:{ all -> 0x00d4 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r10)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r13)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r6)
            r15 = 0
        L_0x006b:
            return r15
        L_0x006c:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r11)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r14)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r7)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "encryptFile cost time="
            r18.<init>(r19)
            long r20 = java.lang.System.currentTimeMillis()
            long r20 = r20 - r16
            r0 = r18
            r1 = r20
            java.lang.StringBuilder r18 = r0.append(r1)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.i(r0, r1)
            r15 = 1
            r6 = r7
            r13 = r14
            r10 = r11
            goto L_0x006b
        L_0x00a4:
            r8 = move-exception
        L_0x00a5:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a     // Catch:{ all -> 0x00d4 }
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d4 }
            java.lang.String r19 = "Throwable.e="
            r18.<init>(r19)     // Catch:{ all -> 0x00d4 }
            java.lang.String r19 = r8.getMessage()     // Catch:{ all -> 0x00d4 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x00d4 }
            java.lang.String r18 = r18.toString()     // Catch:{ all -> 0x00d4 }
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x00d4 }
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)     // Catch:{ all -> 0x00d4 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r10)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r13)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r6)
            r15 = 0
            goto L_0x006b
        L_0x00d4:
            r15 = move-exception
        L_0x00d5:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r10)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r13)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r6)
            throw r15
        L_0x00df:
            r15 = move-exception
            r10 = r11
            goto L_0x00d5
        L_0x00e2:
            r15 = move-exception
            r13 = r14
            r10 = r11
            goto L_0x00d5
        L_0x00e6:
            r15 = move-exception
            r6 = r7
            r13 = r14
            r10 = r11
            goto L_0x00d5
        L_0x00eb:
            r8 = move-exception
            r10 = r11
            goto L_0x00a5
        L_0x00ee:
            r8 = move-exception
            r13 = r14
            r10 = r11
            goto L_0x00a5
        L_0x00f2:
            r8 = move-exception
            r6 = r7
            r13 = r14
            r10 = r11
            goto L_0x00a5
        L_0x00f7:
            r8 = move-exception
            goto L_0x003d
        L_0x00fa:
            r8 = move-exception
            r10 = r11
            goto L_0x003d
        L_0x00fe:
            r8 = move-exception
            r13 = r14
            r10 = r11
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils.encryptFile(java.lang.String, byte[], java.lang.String):boolean");
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:23:0x00a1=Splitter:B:23:0x00a1, B:16:0x0036=Splitter:B:16:0x0036} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] encryptData(java.lang.String r22, byte[] r23) {
        /*
            long r16 = java.lang.System.currentTimeMillis()
            r9 = 0
            r12 = 0
            r6 = 0
            java.io.ByteArrayInputStream r10 = new java.io.ByteArrayInputStream     // Catch:{ FileNotFoundException -> 0x00f3, Throwable -> 0x00a0 }
            r0 = r23
            r10.<init>(r0)     // Catch:{ FileNotFoundException -> 0x00f3, Throwable -> 0x00a0 }
            java.io.ByteArrayOutputStream r13 = new java.io.ByteArrayOutputStream     // Catch:{ FileNotFoundException -> 0x00f6, Throwable -> 0x00e7, all -> 0x00db }
            r13.<init>()     // Catch:{ FileNotFoundException -> 0x00f6, Throwable -> 0x00e7, all -> 0x00db }
            r15 = 1
            r0 = r22
            javax.crypto.Cipher r5 = initAESCipher(r0, r15)     // Catch:{ FileNotFoundException -> 0x00fa, Throwable -> 0x00ea, all -> 0x00de }
            javax.crypto.CipherInputStream r7 = new javax.crypto.CipherInputStream     // Catch:{ FileNotFoundException -> 0x00fa, Throwable -> 0x00ea, all -> 0x00de }
            r7.<init>(r10, r5)     // Catch:{ FileNotFoundException -> 0x00fa, Throwable -> 0x00ea, all -> 0x00de }
            r15 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r15]     // Catch:{ FileNotFoundException -> 0x0032, Throwable -> 0x00ee, all -> 0x00e2 }
        L_0x0023:
            int r11 = r7.read(r4)     // Catch:{ FileNotFoundException -> 0x0032, Throwable -> 0x00ee, all -> 0x00e2 }
            r15 = -1
            if (r11 == r15) goto L_0x0065
            r15 = 0
            r13.write(r4, r15, r11)     // Catch:{ FileNotFoundException -> 0x0032, Throwable -> 0x00ee, all -> 0x00e2 }
            r13.flush()     // Catch:{ FileNotFoundException -> 0x0032, Throwable -> 0x00ee, all -> 0x00e2 }
            goto L_0x0023
        L_0x0032:
            r8 = move-exception
            r6 = r7
            r12 = r13
            r9 = r10
        L_0x0036:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a     // Catch:{ all -> 0x00d0 }
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d0 }
            java.lang.String r19 = "FileNotFoundException.e="
            r18.<init>(r19)     // Catch:{ all -> 0x00d0 }
            java.lang.String r19 = r8.getMessage()     // Catch:{ all -> 0x00d0 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x00d0 }
            java.lang.String r18 = r18.toString()     // Catch:{ all -> 0x00d0 }
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x00d0 }
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)     // Catch:{ all -> 0x00d0 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r9)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r12)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r6)
            r14 = 0
        L_0x0064:
            return r14
        L_0x0065:
            byte[] r14 = r13.toByteArray()     // Catch:{ FileNotFoundException -> 0x0032, Throwable -> 0x00ee, all -> 0x00e2 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r10)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r13)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r7)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "encryptData cost time="
            r18.<init>(r19)
            long r20 = java.lang.System.currentTimeMillis()
            long r20 = r20 - r16
            r0 = r18
            r1 = r20
            java.lang.StringBuilder r18 = r0.append(r1)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.i(r0, r1)
            r6 = r7
            r12 = r13
            r9 = r10
            goto L_0x0064
        L_0x00a0:
            r8 = move-exception
        L_0x00a1:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a     // Catch:{ all -> 0x00d0 }
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d0 }
            java.lang.String r19 = "Throwable.e="
            r18.<init>(r19)     // Catch:{ all -> 0x00d0 }
            java.lang.String r19 = r8.getMessage()     // Catch:{ all -> 0x00d0 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x00d0 }
            java.lang.String r18 = r18.toString()     // Catch:{ all -> 0x00d0 }
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x00d0 }
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)     // Catch:{ all -> 0x00d0 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r9)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r12)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r6)
            r14 = 0
            goto L_0x0064
        L_0x00d0:
            r15 = move-exception
        L_0x00d1:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r9)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r12)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r6)
            throw r15
        L_0x00db:
            r15 = move-exception
            r9 = r10
            goto L_0x00d1
        L_0x00de:
            r15 = move-exception
            r12 = r13
            r9 = r10
            goto L_0x00d1
        L_0x00e2:
            r15 = move-exception
            r6 = r7
            r12 = r13
            r9 = r10
            goto L_0x00d1
        L_0x00e7:
            r8 = move-exception
            r9 = r10
            goto L_0x00a1
        L_0x00ea:
            r8 = move-exception
            r12 = r13
            r9 = r10
            goto L_0x00a1
        L_0x00ee:
            r8 = move-exception
            r6 = r7
            r12 = r13
            r9 = r10
            goto L_0x00a1
        L_0x00f3:
            r8 = move-exception
            goto L_0x0036
        L_0x00f6:
            r8 = move-exception
            r9 = r10
            goto L_0x0036
        L_0x00fa:
            r8 = move-exception
            r12 = r13
            r9 = r10
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils.encryptData(java.lang.String, byte[]):byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0067 A[SYNTHETIC, Splitter:B:20:0x0067] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x006c A[SYNTHETIC, Splitter:B:23:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0074 A[SYNTHETIC, Splitter:B:26:0x0074] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] decryptFile(java.lang.String r22, java.io.File r23) {
        /*
            long r16 = java.lang.System.currentTimeMillis()
            r9 = 0
            r11 = 0
            r6 = 0
            r15 = 2
            r0 = r22
            javax.crypto.Cipher r5 = initAESCipher(r0, r15)     // Catch:{ Throwable -> 0x017e }
            java.io.FileInputStream r10 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x017e }
            r0 = r23
            r10.<init>(r0)     // Catch:{ Throwable -> 0x017e }
            java.io.ByteArrayOutputStream r12 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x0181, all -> 0x016f }
            long r18 = r23.length()     // Catch:{ Throwable -> 0x0181, all -> 0x016f }
            r0 = r18
            int r15 = (int) r0     // Catch:{ Throwable -> 0x0181, all -> 0x016f }
            r12.<init>(r15)     // Catch:{ Throwable -> 0x0181, all -> 0x016f }
            javax.crypto.CipherOutputStream r7 = new javax.crypto.CipherOutputStream     // Catch:{ Throwable -> 0x0185, all -> 0x0173 }
            r7.<init>(r12, r5)     // Catch:{ Throwable -> 0x0185, all -> 0x0173 }
            r15 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r15]     // Catch:{ Throwable -> 0x0036, all -> 0x0178 }
        L_0x002a:
            int r13 = r10.read(r4)     // Catch:{ Throwable -> 0x0036, all -> 0x0178 }
            r15 = -1
            if (r13 == r15) goto L_0x007b
            r15 = 0
            r7.write(r4, r15, r13)     // Catch:{ Throwable -> 0x0036, all -> 0x0178 }
            goto L_0x002a
        L_0x0036:
            r8 = move-exception
            r6 = r7
            r11 = r12
            r9 = r10
        L_0x003a:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a     // Catch:{ all -> 0x0064 }
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ all -> 0x0064 }
            java.lang.String r19 = "Throwable.e="
            r18.<init>(r19)     // Catch:{ all -> 0x0064 }
            java.lang.String r19 = r8.getMessage()     // Catch:{ all -> 0x0064 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x0064 }
            java.lang.String r18 = r18.toString()     // Catch:{ all -> 0x0064 }
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0064 }
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)     // Catch:{ all -> 0x0064 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils$DecryptException r15 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils$DecryptException     // Catch:{ all -> 0x0064 }
            r15.<init>()     // Catch:{ all -> 0x0064 }
            throw r15     // Catch:{ all -> 0x0064 }
        L_0x0064:
            r15 = move-exception
        L_0x0065:
            if (r9 == 0) goto L_0x006a
            r9.close()     // Catch:{ IOException -> 0x0106 }
        L_0x006a:
            if (r6 == 0) goto L_0x0072
            r6.flush()     // Catch:{ IOException -> 0x0129 }
            r6.close()     // Catch:{ IOException -> 0x0129 }
        L_0x0072:
            if (r11 == 0) goto L_0x007a
            r11.flush()     // Catch:{ IOException -> 0x014c }
            r11.close()     // Catch:{ IOException -> 0x014c }
        L_0x007a:
            throw r15
        L_0x007b:
            r7.flush()     // Catch:{ Throwable -> 0x0036, all -> 0x0178 }
            r7.close()     // Catch:{ Throwable -> 0x0036, all -> 0x0178 }
            r6 = 0
            byte[] r14 = r12.toByteArray()     // Catch:{ Throwable -> 0x0185, all -> 0x0173 }
            r10.close()     // Catch:{ IOException -> 0x00ba }
        L_0x0089:
            r12.flush()     // Catch:{ IOException -> 0x00e0 }
            r12.close()     // Catch:{ IOException -> 0x00e0 }
        L_0x008f:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "decryptFile.cost time="
            r18.<init>(r19)
            long r20 = java.lang.System.currentTimeMillis()
            long r20 = r20 - r16
            r0 = r18
            r1 = r20
            java.lang.StringBuilder r18 = r0.append(r1)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.d(r0, r1)
            return r14
        L_0x00ba:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x0089
        L_0x00e0:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x008f
        L_0x0106:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r18 = a
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            java.lang.String r20 = "IOException.e="
            r19.<init>(r20)
            java.lang.String r20 = r8.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r20 = 0
            r0 = r20
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r20 = r0
            r18.e(r19, r20)
            goto L_0x006a
        L_0x0129:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r18 = a
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            java.lang.String r20 = "IOException.e="
            r19.<init>(r20)
            java.lang.String r20 = r8.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r20 = 0
            r0 = r20
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r20 = r0
            r18.e(r19, r20)
            goto L_0x0072
        L_0x014c:
            r8 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r18 = a
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            java.lang.String r20 = "IOException.e="
            r19.<init>(r20)
            java.lang.String r20 = r8.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r20 = 0
            r0 = r20
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r20 = r0
            r18.e(r19, r20)
            goto L_0x007a
        L_0x016f:
            r15 = move-exception
            r9 = r10
            goto L_0x0065
        L_0x0173:
            r15 = move-exception
            r11 = r12
            r9 = r10
            goto L_0x0065
        L_0x0178:
            r15 = move-exception
            r6 = r7
            r11 = r12
            r9 = r10
            goto L_0x0065
        L_0x017e:
            r8 = move-exception
            goto L_0x003a
        L_0x0181:
            r8 = move-exception
            r9 = r10
            goto L_0x003a
        L_0x0185:
            r8 = move-exception
            r11 = r12
            r9 = r10
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils.decryptFile(java.lang.String, java.io.File):byte[]");
    }
}
