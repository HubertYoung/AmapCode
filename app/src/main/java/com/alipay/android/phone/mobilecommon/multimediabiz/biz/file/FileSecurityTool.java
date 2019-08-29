package com.alipay.android.phone.mobilecommon.multimediabiz.biz.file;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.android.phone.mobilecommon.multimedia.file.APDecryptCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APDecryptReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APDecryptRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.taobao.wireless.security.sdk.SecurityGuardManager;
import com.taobao.wireless.security.sdk.staticdatastore.IStaticDataStoreComponent;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FileSecurityTool {
    private static final Logger a = Logger.getLogger((String) "FileSecurityTool");
    /* access modifiers changed from: private */
    public Context b;

    public static class FileSecurityReport {
        public static final int OP_TYPE_DECRYPT = 1;
        public static final int OP_TYPE_ENCRYPT = 0;
        public static final int RESULT_AES_NOT_AVAILABLE = 1;
        public static final int RESULT_ERROR_UNKNOWN = 6;
        public static final int RESULT_KEY_INVALID = 3;
        public static final int RESULT_OK = 0;
        public static final int RESULT_PADDING_NOT_AVAILABLE = 2;
        public static final int RESULT_PARAMETERS_EXCEPTION = 4;
        public static final int RESULT_SOURCE_FILE_NOT_EXIST = 5;
        public String business;
        public long cost;
        public int operationType;
        public int result;
        public long sourceFileSize;
        public String url;

        public void submit() {
            UCLogUtil.UC_MM_C55(this.operationType, this.result, this.sourceFileSize, this.cost, this.url, this.business);
        }
    }

    public FileSecurityTool(Context context) {
        this.b = context;
    }

    public static Cipher initAESCipher(Context context, int cipherMode, String fileKey, FileSecurityReport report) {
        byte[] key;
        Cipher cipher = null;
        try {
            if (TextUtils.isEmpty(fileKey)) {
                SecurityGuardManager sgMgr = SecurityGuardManager.getInstance(context);
                if (sgMgr == null) {
                    return null;
                }
                IStaticDataStoreComponent sdsComp = sgMgr.getStaticDataStoreComp();
                if (sdsComp == null) {
                    return null;
                }
                String extraData = sdsComp.getExtraData("mdaeskey");
                if (extraData == null) {
                    return null;
                }
                byte[] raw = extraData.getBytes();
                key = new byte[16];
                System.arraycopy(raw, 0, key, 0, raw.length);
            } else {
                byte[] raw2 = Base64.decode(fileKey, 0);
                if (raw2 == null || raw2.length != 32) {
                    a.e("encryptKey=" + fileKey + ",length=" + (raw2 == null ? 0 : raw2.length), new Object[0]);
                    return null;
                }
                key = new byte[32];
                System.arraycopy(raw2, 0, key, 0, raw2.length);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(cipherMode, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        } catch (NoSuchAlgorithmException e) {
            report.result = 1;
            a.e("NoSuchAlgorithmException", new Object[0]);
        } catch (NoSuchPaddingException e2) {
            report.result = 2;
            a.e("NoSuchPaddingException", new Object[0]);
        } catch (InvalidKeyException e3) {
            report.result = 3;
            a.e("InvalidKeyException", new Object[0]);
        } catch (InvalidAlgorithmParameterException e4) {
            report.result = 4;
            a.e("InvalidAlgorithmParameterException", new Object[0]);
        }
        return cipher;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x008f A[SYNTHETIC, Splitter:B:21:0x008f] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0094 A[SYNTHETIC, Splitter:B:24:0x0094] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x009c A[SYNTHETIC, Splitter:B:27:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x01e3 A[SYNTHETIC, Splitter:B:56:0x01e3] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x01e8 A[SYNTHETIC, Splitter:B:59:0x01e8] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x01f0 A[SYNTHETIC, Splitter:B:62:0x01f0] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x027a A[SYNTHETIC, Splitter:B:74:0x027a] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x027f A[SYNTHETIC, Splitter:B:77:0x027f] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0287 A[SYNTHETIC, Splitter:B:80:0x0287] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean encryptFile(android.content.Context r21, java.lang.String r22, java.lang.String r23, com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileSecurityTool.FileSecurityReport r24) {
        /*
            r10 = 0
            r13 = 0
            r6 = 0
            long r16 = java.lang.System.currentTimeMillis()
            r15 = 0
            r0 = r24
            r0.result = r15
            java.io.FileInputStream r11 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x030f, Throwable -> 0x01a8 }
            r0 = r22
            r11.<init>(r0)     // Catch:{ FileNotFoundException -> 0x030f, Throwable -> 0x01a8 }
            int r15 = r11.available()     // Catch:{ FileNotFoundException -> 0x0312, Throwable -> 0x0300, all -> 0x02f1 }
            long r0 = (long) r15     // Catch:{ FileNotFoundException -> 0x0312, Throwable -> 0x0300, all -> 0x02f1 }
            r18 = r0
            r0 = r18
            r2 = r24
            r2.sourceFileSize = r0     // Catch:{ FileNotFoundException -> 0x0312, Throwable -> 0x0300, all -> 0x02f1 }
            java.io.File r9 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0312, Throwable -> 0x0300, all -> 0x02f1 }
            r0 = r23
            r9.<init>(r0)     // Catch:{ FileNotFoundException -> 0x0312, Throwable -> 0x0300, all -> 0x02f1 }
            java.io.FileOutputStream r14 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0312, Throwable -> 0x0300, all -> 0x02f1 }
            r14.<init>(r9)     // Catch:{ FileNotFoundException -> 0x0312, Throwable -> 0x0300, all -> 0x02f1 }
            r15 = 1
            r18 = 0
            r0 = r21
            r1 = r18
            r2 = r24
            javax.crypto.Cipher r5 = initAESCipher(r0, r15, r1, r2)     // Catch:{ FileNotFoundException -> 0x0316, Throwable -> 0x0304, all -> 0x02f5 }
            javax.crypto.CipherInputStream r7 = new javax.crypto.CipherInputStream     // Catch:{ FileNotFoundException -> 0x0316, Throwable -> 0x0304, all -> 0x02f5 }
            r7.<init>(r11, r5)     // Catch:{ FileNotFoundException -> 0x0316, Throwable -> 0x0304, all -> 0x02f5 }
            r15 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r15]     // Catch:{ FileNotFoundException -> 0x0051, Throwable -> 0x0309, all -> 0x02fa }
        L_0x0042:
            int r12 = r7.read(r4)     // Catch:{ FileNotFoundException -> 0x0051, Throwable -> 0x0309, all -> 0x02fa }
            r15 = -1
            if (r12 == r15) goto L_0x00a1
            r15 = 0
            r14.write(r4, r15, r12)     // Catch:{ FileNotFoundException -> 0x0051, Throwable -> 0x0309, all -> 0x02fa }
            r14.flush()     // Catch:{ FileNotFoundException -> 0x0051, Throwable -> 0x0309, all -> 0x02fa }
            goto L_0x0042
        L_0x0051:
            r8 = move-exception
            r6 = r7
            r13 = r14
            r10 = r11
        L_0x0055:
            r15 = 5
            r0 = r24
            r0.result = r15     // Catch:{ all -> 0x0268 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a     // Catch:{ all -> 0x0268 }
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ all -> 0x0268 }
            java.lang.String r19 = "FileNotFoundException.e="
            r18.<init>(r19)     // Catch:{ all -> 0x0268 }
            java.lang.String r19 = r8.getMessage()     // Catch:{ all -> 0x0268 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x0268 }
            java.lang.String r18 = r18.toString()     // Catch:{ all -> 0x0268 }
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0268 }
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)     // Catch:{ all -> 0x0268 }
            long r18 = java.lang.System.currentTimeMillis()
            long r18 = r18 - r16
            r0 = r18
            r2 = r24
            r2.cost = r0
            r24.submit()
            if (r10 == 0) goto L_0x0092
            r10.close()     // Catch:{ IOException -> 0x0133 }
        L_0x0092:
            if (r13 == 0) goto L_0x009a
            r13.flush()     // Catch:{ IOException -> 0x015a }
            r13.close()     // Catch:{ IOException -> 0x015a }
        L_0x009a:
            if (r6 == 0) goto L_0x009f
            r6.close()     // Catch:{ IOException -> 0x0181 }
        L_0x009f:
            r15 = 0
        L_0x00a0:
            return r15
        L_0x00a1:
            long r18 = java.lang.System.currentTimeMillis()
            long r18 = r18 - r16
            r0 = r18
            r2 = r24
            r2.cost = r0
            r24.submit()
            r11.close()     // Catch:{ IOException -> 0x00c1 }
        L_0x00b3:
            r14.flush()     // Catch:{ IOException -> 0x00e7 }
            r14.close()     // Catch:{ IOException -> 0x00e7 }
        L_0x00b9:
            r7.close()     // Catch:{ IOException -> 0x010d }
        L_0x00bc:
            r15 = 1
            r6 = r7
            r13 = r14
            r10 = r11
            goto L_0x00a0
        L_0x00c1:
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
            goto L_0x00b3
        L_0x00e7:
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
            goto L_0x00b9
        L_0x010d:
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
            goto L_0x00bc
        L_0x0133:
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
            goto L_0x0092
        L_0x015a:
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
            goto L_0x009a
        L_0x0181:
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
            goto L_0x009f
        L_0x01a8:
            r8 = move-exception
        L_0x01a9:
            r15 = 6
            r0 = r24
            r0.result = r15     // Catch:{ all -> 0x0268 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a     // Catch:{ all -> 0x0268 }
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ all -> 0x0268 }
            java.lang.String r19 = "Throwable.e="
            r18.<init>(r19)     // Catch:{ all -> 0x0268 }
            java.lang.String r19 = r8.getMessage()     // Catch:{ all -> 0x0268 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x0268 }
            java.lang.String r18 = r18.toString()     // Catch:{ all -> 0x0268 }
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0268 }
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)     // Catch:{ all -> 0x0268 }
            long r18 = java.lang.System.currentTimeMillis()
            long r18 = r18 - r16
            r0 = r18
            r2 = r24
            r2.cost = r0
            r24.submit()
            if (r10 == 0) goto L_0x01e6
            r10.close()     // Catch:{ IOException -> 0x01f6 }
        L_0x01e6:
            if (r13 == 0) goto L_0x01ee
            r13.flush()     // Catch:{ IOException -> 0x021c }
            r13.close()     // Catch:{ IOException -> 0x021c }
        L_0x01ee:
            if (r6 == 0) goto L_0x01f3
            r6.close()     // Catch:{ IOException -> 0x0242 }
        L_0x01f3:
            r15 = 0
            goto L_0x00a0
        L_0x01f6:
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
            goto L_0x01e6
        L_0x021c:
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
            goto L_0x01ee
        L_0x0242:
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
            goto L_0x01f3
        L_0x0268:
            r15 = move-exception
        L_0x0269:
            long r18 = java.lang.System.currentTimeMillis()
            long r18 = r18 - r16
            r0 = r18
            r2 = r24
            r2.cost = r0
            r24.submit()
            if (r10 == 0) goto L_0x027d
            r10.close()     // Catch:{ IOException -> 0x028b }
        L_0x027d:
            if (r13 == 0) goto L_0x0285
            r13.flush()     // Catch:{ IOException -> 0x02ad }
            r13.close()     // Catch:{ IOException -> 0x02ad }
        L_0x0285:
            if (r6 == 0) goto L_0x028a
            r6.close()     // Catch:{ IOException -> 0x02cf }
        L_0x028a:
            throw r15
        L_0x028b:
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
            goto L_0x027d
        L_0x02ad:
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
            goto L_0x0285
        L_0x02cf:
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
            goto L_0x028a
        L_0x02f1:
            r15 = move-exception
            r10 = r11
            goto L_0x0269
        L_0x02f5:
            r15 = move-exception
            r13 = r14
            r10 = r11
            goto L_0x0269
        L_0x02fa:
            r15 = move-exception
            r6 = r7
            r13 = r14
            r10 = r11
            goto L_0x0269
        L_0x0300:
            r8 = move-exception
            r10 = r11
            goto L_0x01a9
        L_0x0304:
            r8 = move-exception
            r13 = r14
            r10 = r11
            goto L_0x01a9
        L_0x0309:
            r8 = move-exception
            r6 = r7
            r13 = r14
            r10 = r11
            goto L_0x01a9
        L_0x030f:
            r8 = move-exception
            goto L_0x0055
        L_0x0312:
            r8 = move-exception
            r10 = r11
            goto L_0x0055
        L_0x0316:
            r8 = move-exception
            r13 = r14
            r10 = r11
            goto L_0x0055
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileSecurityTool.encryptFile(android.content.Context, java.lang.String, java.lang.String, com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileSecurityTool$FileSecurityReport):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00a3 A[SYNTHETIC, Splitter:B:20:0x00a3] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00a8 A[SYNTHETIC, Splitter:B:23:0x00a8] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00b0 A[SYNTHETIC, Splitter:B:26:0x00b0] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0202 A[SYNTHETIC, Splitter:B:52:0x0202] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0207 A[SYNTHETIC, Splitter:B:55:0x0207] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x020f A[SYNTHETIC, Splitter:B:58:0x020f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean decryptFile(android.content.Context r24, java.lang.String r25, java.lang.String r26, java.lang.String r27, com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileSecurityTool.FileSecurityReport r28) {
        /*
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.String r18 = "FileSecurityTool"
            r19 = 1
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r20 = 0
            java.lang.String r21 = "decryptFile start...."
            r19[r20] = r21
            r0 = r18
            r1 = r19
            r15.d(r0, r1)
            r10 = 0
            r12 = 0
            r6 = 0
            long r16 = java.lang.System.currentTimeMillis()
            r15 = 0
            r0 = r28
            r0.result = r15
            java.io.File r8 = new java.io.File     // Catch:{ Throwable -> 0x028b }
            r0 = r26
            r8.<init>(r0)     // Catch:{ Throwable -> 0x028b }
            r15 = 2
            r0 = r24
            r1 = r27
            r2 = r28
            javax.crypto.Cipher r5 = initAESCipher(r0, r15, r1, r2)     // Catch:{ Throwable -> 0x028b }
            java.io.FileInputStream r11 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x028b }
            r0 = r25
            r11.<init>(r0)     // Catch:{ Throwable -> 0x028b }
            int r15 = r11.available()     // Catch:{ Throwable -> 0x028e, all -> 0x027c }
            long r0 = (long) r15     // Catch:{ Throwable -> 0x028e, all -> 0x027c }
            r18 = r0
            r0 = r18
            r2 = r28
            r2.sourceFileSize = r0     // Catch:{ Throwable -> 0x028e, all -> 0x027c }
            java.io.FileOutputStream r13 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x028e, all -> 0x027c }
            r13.<init>(r8)     // Catch:{ Throwable -> 0x028e, all -> 0x027c }
            javax.crypto.CipherOutputStream r7 = new javax.crypto.CipherOutputStream     // Catch:{ Throwable -> 0x0292, all -> 0x0280 }
            r7.<init>(r13, r5)     // Catch:{ Throwable -> 0x0292, all -> 0x0280 }
            r15 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r15]     // Catch:{ Throwable -> 0x0065, all -> 0x0285 }
        L_0x0059:
            int r14 = r11.read(r4)     // Catch:{ Throwable -> 0x0065, all -> 0x0285 }
            r15 = -1
            if (r14 == r15) goto L_0x00b8
            r15 = 0
            r7.write(r4, r15, r14)     // Catch:{ Throwable -> 0x0065, all -> 0x0285 }
            goto L_0x0059
        L_0x0065:
            r9 = move-exception
            r6 = r7
            r12 = r13
            r10 = r11
        L_0x0069:
            r15 = 6
            r0 = r28
            r0.result = r15     // Catch:{ all -> 0x01f0 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a     // Catch:{ all -> 0x01f0 }
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ all -> 0x01f0 }
            java.lang.String r19 = "Throwable.e="
            r18.<init>(r19)     // Catch:{ all -> 0x01f0 }
            java.lang.String r19 = r9.getMessage()     // Catch:{ all -> 0x01f0 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x01f0 }
            java.lang.String r18 = r18.toString()     // Catch:{ all -> 0x01f0 }
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x01f0 }
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)     // Catch:{ all -> 0x01f0 }
            long r18 = java.lang.System.currentTimeMillis()
            long r18 = r18 - r16
            r0 = r18
            r2 = r28
            r2.cost = r0
            r28.submit()
            if (r10 == 0) goto L_0x00a6
            r10.close()     // Catch:{ IOException -> 0x017b }
        L_0x00a6:
            if (r6 == 0) goto L_0x00ae
            r6.flush()     // Catch:{ IOException -> 0x01a2 }
            r6.close()     // Catch:{ IOException -> 0x01a2 }
        L_0x00ae:
            if (r12 == 0) goto L_0x00b6
            r12.flush()     // Catch:{ IOException -> 0x01c9 }
            r12.close()     // Catch:{ IOException -> 0x01c9 }
        L_0x00b6:
            r15 = 0
        L_0x00b7:
            return r15
        L_0x00b8:
            long r18 = java.lang.System.currentTimeMillis()
            long r18 = r18 - r16
            r0 = r18
            r2 = r28
            r2.cost = r0
            r28.submit()
            r11.close()     // Catch:{ IOException -> 0x0107 }
        L_0x00ca:
            r7.flush()     // Catch:{ IOException -> 0x012d }
            r7.close()     // Catch:{ IOException -> 0x012d }
        L_0x00d0:
            r13.flush()     // Catch:{ IOException -> 0x0154 }
            r13.close()     // Catch:{ IOException -> 0x0154 }
        L_0x00d6:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.String r18 = "FileSecurityTool"
            r19 = 1
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r20 = 0
            java.lang.StringBuilder r21 = new java.lang.StringBuilder
            java.lang.String r22 = "decryptFile end.cost="
            r21.<init>(r22)
            r0 = r28
            long r0 = r0.cost
            r22 = r0
            java.lang.StringBuilder r21 = r21.append(r22)
            java.lang.String r21 = r21.toString()
            r19[r20] = r21
            r0 = r18
            r1 = r19
            r15.d(r0, r1)
            r15 = 1
            r6 = r7
            r12 = r13
            r10 = r11
            goto L_0x00b7
        L_0x0107:
            r9 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r9.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x00ca
        L_0x012d:
            r9 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r9.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x00d0
        L_0x0154:
            r9 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r9.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x00d6
        L_0x017b:
            r9 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r9.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x00a6
        L_0x01a2:
            r9 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r9.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x00ae
        L_0x01c9:
            r9 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = a
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "IOException.e="
            r18.<init>(r19)
            java.lang.String r19 = r9.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r19 = 0
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = r18
            r1 = r19
            r15.e(r0, r1)
            goto L_0x00b6
        L_0x01f0:
            r15 = move-exception
        L_0x01f1:
            long r18 = java.lang.System.currentTimeMillis()
            long r18 = r18 - r16
            r0 = r18
            r2 = r28
            r2.cost = r0
            r28.submit()
            if (r10 == 0) goto L_0x0205
            r10.close()     // Catch:{ IOException -> 0x0216 }
        L_0x0205:
            if (r6 == 0) goto L_0x020d
            r6.flush()     // Catch:{ IOException -> 0x0238 }
            r6.close()     // Catch:{ IOException -> 0x0238 }
        L_0x020d:
            if (r12 == 0) goto L_0x0215
            r12.flush()     // Catch:{ IOException -> 0x025a }
            r12.close()     // Catch:{ IOException -> 0x025a }
        L_0x0215:
            throw r15
        L_0x0216:
            r9 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r18 = a
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            java.lang.String r20 = "IOException.e="
            r19.<init>(r20)
            java.lang.String r20 = r9.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r20 = 0
            r0 = r20
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r20 = r0
            r18.e(r19, r20)
            goto L_0x0205
        L_0x0238:
            r9 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r18 = a
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            java.lang.String r20 = "IOException.e="
            r19.<init>(r20)
            java.lang.String r20 = r9.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r20 = 0
            r0 = r20
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r20 = r0
            r18.e(r19, r20)
            goto L_0x020d
        L_0x025a:
            r9 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r18 = a
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            java.lang.String r20 = "IOException.e="
            r19.<init>(r20)
            java.lang.String r20 = r9.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r20 = 0
            r0 = r20
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r20 = r0
            r18.e(r19, r20)
            goto L_0x0215
        L_0x027c:
            r15 = move-exception
            r10 = r11
            goto L_0x01f1
        L_0x0280:
            r15 = move-exception
            r12 = r13
            r10 = r11
            goto L_0x01f1
        L_0x0285:
            r15 = move-exception
            r6 = r7
            r12 = r13
            r10 = r11
            goto L_0x01f1
        L_0x028b:
            r9 = move-exception
            goto L_0x0069
        L_0x028e:
            r9 = move-exception
            r10 = r11
            goto L_0x0069
        L_0x0292:
            r9 = move-exception
            r12 = r13
            r10 = r11
            goto L_0x0069
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileSecurityTool.decryptFile(android.content.Context, java.lang.String, java.lang.String, java.lang.String, com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileSecurityTool$FileSecurityReport):boolean");
    }

    public void processDecryptReq(final APDecryptReq req, final APDecryptCallback callback) {
        if (req != null) {
            a.d("FileSecurityTool", "processDecryptReq.cloudid=" + req.cloudId + ",sourceFilePath=" + req.sourceFilePath + ",destFilePath=" + req.destFilePath);
        }
        TaskScheduleManager.get().commonExecutor().submit(new Runnable() {
            public void run() {
                if (!TextUtils.isEmpty(req.cloudId)) {
                    String path = CacheContext.get().getDiskCache().genPathByKey(req.cloudId);
                    if (TextUtils.isEmpty(req.fileKey)) {
                        path = path + ".enc";
                    }
                    if (new File(path).exists()) {
                        req.sourceFilePath = path;
                    }
                }
                FileSecurityReport report = new FileSecurityReport();
                report.operationType = 1;
                report.business = req.businessId;
                boolean ret = FileSecurityTool.decryptFile(FileSecurityTool.this.b, req.sourceFilePath, req.destFilePath, req.fileKey, report);
                APDecryptRsp rsp = new APDecryptRsp();
                rsp.req = req;
                if (ret) {
                    rsp.result = 0;
                } else {
                    rsp.result = -1;
                }
                if (callback != null) {
                    callback.onDecryptFinish(rsp);
                }
            }
        });
    }
}
