package com.alipay.mobile.common.transport.http.selfencrypt;

import com.alipay.mobile.common.mpaas_crypto.Client;
import com.alipay.mobile.common.transport.utils.GzipUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.MpaasNetConfigUtil;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;

public class ClientRpcPack {
    public static final byte ASYMMETRIC_ENCRYPT_ECC = 2;
    public static final byte ASYMMETRIC_ENCRYPT_RSA = 1;
    public static final byte ASYMMETRIC_ENCRYPT_SM2 = 3;
    public static final byte ASYMMETRIC_ENCRYPT_SM4 = 13;
    public static final byte SYMMETRIC_ENCRYPT_3DES = 12;
    public static final byte SYMMETRIC_ENCRYPT_AES = 11;
    private byte a;
    private byte b = SYMMETRIC_ENCRYPT_AES;
    private int c = 0;
    private Client d;
    private byte[] e;
    private String f;

    public ClientRpcPack() {
        if (MpaasNetConfigUtil.isCrypt(TransportEnvUtil.getContext())) {
            this.a = MpaasNetConfigUtil.getAsymmetricEncryptMethod(TransportEnvUtil.getContext());
            if (this.a == 3) {
                this.b = 13;
            } else {
                this.b = SYMMETRIC_ENCRYPT_AES;
            }
            this.f = a();
            boolean initResult = false;
            this.d = new Client();
            if (this.a == 1) {
                this.c = 0;
                initResult = this.d.init(this.f, null, null);
            } else if (this.a == 2) {
                this.c = 1;
                initResult = this.d.init(null, this.f, null);
            } else if (this.a == 3) {
                this.c = 2;
                initResult = this.d.init(null, null, this.f);
            }
            if (!initResult) {
                LogCatUtil.error((String) "ClientRpcPack", "fail to init client,error: " + this.d.error());
            }
        }
    }

    private static String a() {
        String pubKey = MpaasNetConfigUtil.getPublicKey(TransportEnvUtil.getContext());
        if (MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
            LogCatUtil.printInfo("ClientRpcPack", "pubKey: \n" + pubKey);
        }
        return pubKey;
    }

    public byte[] encrypt(byte[] data) {
        try {
            byte[][] encodeResult = this.d.encode(MiscUtils.generateRandomStr(16).getBytes(), GzipUtils.toGzip(data), this.c);
            this.e = encodeResult[0];
            return a(encodeResult[2], encodeResult[1]);
        } catch (Exception ex) {
            LogCatUtil.error((String) "ClientRpcPack", "encrypt ex: " + ex.toString() + " ,error: " + this.d.error());
            throw ex;
        }
    }

    public byte[] decrypt(byte[] encodeData) {
        try {
            return GzipUtils.unGzip(this.d.decode(this.e, encodeData, this.c));
        } catch (Exception ex) {
            LogCatUtil.error((String) "ClientRpcPack", "decrypt ex: " + ex.toString() + " ,error: " + this.d.error());
            throw ex;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x008b A[SYNTHETIC, Splitter:B:24:0x008b] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0090 A[SYNTHETIC, Splitter:B:27:0x0090] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] a(byte[] r14, byte[] r15) {
        /*
            r13 = this;
            if (r14 == 0) goto L_0x000a
            int r10 = r14.length
            if (r10 == 0) goto L_0x000a
            if (r15 == 0) goto L_0x000a
            int r10 = r15.length
            if (r10 != 0) goto L_0x000c
        L_0x000a:
            r8 = 0
        L_0x000b:
            return r8
        L_0x000c:
            r1 = 0
            r3 = 0
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0088 }
            r2.<init>()     // Catch:{ all -> 0x0088 }
            java.io.DataOutputStream r4 = new java.io.DataOutputStream     // Catch:{ all -> 0x00a2 }
            r4.<init>(r2)     // Catch:{ all -> 0x00a2 }
            byte r0 = r13.a     // Catch:{ all -> 0x00a5 }
            int r10 = r14.length     // Catch:{ all -> 0x00a5 }
            byte[] r7 = com.alipay.mobile.common.transport.utils.NumArrayUtils.mediumToByteArray(r10)     // Catch:{ all -> 0x00a5 }
            byte r9 = r13.b     // Catch:{ all -> 0x00a5 }
            int r10 = r15.length     // Catch:{ all -> 0x00a5 }
            byte[] r6 = com.alipay.mobile.common.transport.utils.NumArrayUtils.mediumToByteArray(r10)     // Catch:{ all -> 0x00a5 }
            r4.write(r0)     // Catch:{ all -> 0x00a5 }
            r4.write(r7)     // Catch:{ all -> 0x00a5 }
            r4.write(r14)     // Catch:{ all -> 0x00a5 }
            r4.write(r9)     // Catch:{ all -> 0x00a5 }
            r4.write(r6)     // Catch:{ all -> 0x00a5 }
            r4.write(r15)     // Catch:{ all -> 0x00a5 }
            java.lang.String r10 = "ClientRpcPack"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a5 }
            java.lang.String r12 = "asymType: "
            r11.<init>(r12)     // Catch:{ all -> 0x00a5 }
            java.lang.StringBuilder r11 = r11.append(r0)     // Catch:{ all -> 0x00a5 }
            java.lang.String r12 = ",encryptedKeyLen: "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x00a5 }
            int r12 = r14.length     // Catch:{ all -> 0x00a5 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x00a5 }
            java.lang.String r12 = ",symType: "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x00a5 }
            java.lang.StringBuilder r11 = r11.append(r9)     // Catch:{ all -> 0x00a5 }
            java.lang.String r12 = ",encryptedBodyLen: "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x00a5 }
            int r12 = r15.length     // Catch:{ all -> 0x00a5 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x00a5 }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x00a5 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r10, r11)     // Catch:{ all -> 0x00a5 }
            r4.flush()     // Catch:{ all -> 0x00a5 }
            byte[] r8 = r2.toByteArray()     // Catch:{ all -> 0x00a5 }
            r2.close()     // Catch:{ Exception -> 0x0081 }
        L_0x0076:
            r4.close()     // Catch:{ Exception -> 0x007a }
            goto L_0x000b
        L_0x007a:
            r5 = move-exception
            java.lang.String r10 = "ClientRpcPack"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r10, r5)
            goto L_0x000b
        L_0x0081:
            r5 = move-exception
            java.lang.String r10 = "ClientRpcPack"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r10, r5)
            goto L_0x0076
        L_0x0088:
            r10 = move-exception
        L_0x0089:
            if (r1 == 0) goto L_0x008e
            r1.close()     // Catch:{ Exception -> 0x0094 }
        L_0x008e:
            if (r3 == 0) goto L_0x0093
            r3.close()     // Catch:{ Exception -> 0x009b }
        L_0x0093:
            throw r10
        L_0x0094:
            r5 = move-exception
            java.lang.String r11 = "ClientRpcPack"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r11, r5)
            goto L_0x008e
        L_0x009b:
            r5 = move-exception
            java.lang.String r11 = "ClientRpcPack"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r11, r5)
            goto L_0x0093
        L_0x00a2:
            r10 = move-exception
            r1 = r2
            goto L_0x0089
        L_0x00a5:
            r10 = move-exception
            r3 = r4
            r1 = r2
            goto L_0x0089
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack.a(byte[], byte[]):byte[]");
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            if (this.d != null) {
                this.d.exit();
                LogCatUtil.debug("ClientRpcPack", "client exit");
            }
            super.finalize();
        } catch (Throwable ex) {
            LogCatUtil.error((String) "ClientRpcPack", "finalize ex:" + ex.toString());
        }
    }
}
