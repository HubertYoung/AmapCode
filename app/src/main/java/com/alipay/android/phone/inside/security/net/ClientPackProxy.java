package com.alipay.android.phone.inside.security.net;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.security.PublicKeyUpdateException;
import com.alipay.android.phone.inside.security.util.DesCBC;
import com.alipay.android.phone.inside.security.util.EncryptUtil;
import com.alipay.android.phone.inside.security.util.GzipUtils;
import com.alipay.android.phone.inside.security.util.Rsa;
import com.alipay.sdk.packet.d;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientPackProxy {
    private String a = EncryptUtil.a(24);
    private ClientPackEnum b;

    public ClientPackProxy(ClientPackEnum clientPackEnum) {
        this.b = clientPackEnum;
    }

    public final byte[] b(byte[] bArr) throws Exception {
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(bArr);
            try {
                byte[] bArr2 = new byte[6];
                byteArrayInputStream2.read(bArr2);
                byte[] bArr3 = new byte[c(bArr2)];
                byteArrayInputStream2.read(bArr3);
                if (a(new String(bArr3, "utf-8"))) {
                    throw new PublicKeyUpdateException();
                }
                byte[] bArr4 = new byte[6];
                byteArrayInputStream2.read(bArr4);
                byte[] bArr5 = new byte[c(bArr4)];
                byteArrayInputStream2.read(bArr5);
                byte[] b2 = GzipUtils.b(DesCBC.b(this.a, bArr5));
                try {
                    byteArrayInputStream2.close();
                } catch (Exception unused) {
                }
                return b2;
            } catch (Exception e) {
                e = e;
                byteArrayInputStream = byteArrayInputStream2;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    byteArrayInputStream2 = byteArrayInputStream;
                }
            } catch (Throwable th2) {
                th = th2;
                if (byteArrayInputStream2 != null) {
                    try {
                        byteArrayInputStream2.close();
                    } catch (Exception unused2) {
                    }
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            throw e;
        }
    }

    private boolean a(String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            String optString = new JSONObject(str).getJSONObject("data").optString(d.m, null);
            if (!TextUtils.isEmpty(optString)) {
                if (this.b == ClientPackEnum.RPC) {
                    PublicKeyManager.a(optString);
                } else if (this.b == ClientPackEnum.LOG) {
                    PublicKeyManager.b(optString);
                }
                z = true;
            }
        } catch (JSONException unused) {
        }
        return z;
    }

    private byte[] a(String str, String str2) throws Exception {
        try {
            return Rsa.a(str, str2);
        } catch (Throwable th) {
            if (this.b == ClientPackEnum.LOG) {
                PublicKeyManager.c();
            } else if (this.b == ClientPackEnum.RPC) {
                PublicKeyManager.d();
            }
            byte[] a2 = Rsa.a(str, a());
            LoggerFactory.e().a((String) "security", (String) "EncryptDesKeyEx", th);
            return a2;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:6|7|8|(3:12|10|9)|35|13|14|15|16|17|18) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0046 */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0054 A[SYNTHETIC, Splitter:B:26:0x0054] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0059 A[SYNTHETIC, Splitter:B:30:0x0059] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] a(byte[]... r8) throws java.io.IOException {
        /*
            r0 = 0
            if (r8 == 0) goto L_0x005d
            int r1 = r8.length
            if (r1 != 0) goto L_0x0007
            goto L_0x005d
        L_0x0007:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x004f }
            r1.<init>()     // Catch:{ all -> 0x004f }
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ all -> 0x004c }
            r2.<init>(r1)     // Catch:{ all -> 0x004c }
            r0 = 0
            r3 = 0
        L_0x0013:
            int r4 = r8.length     // Catch:{ all -> 0x004a }
            if (r3 >= r4) goto L_0x003c
            r4 = r8[r3]     // Catch:{ all -> 0x004a }
            int r4 = r4.length     // Catch:{ all -> 0x004a }
            java.util.Locale r5 = java.util.Locale.CHINA     // Catch:{ all -> 0x004a }
            java.lang.String r6 = "%06d"
            r7 = 1
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x004a }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x004a }
            r7[r0] = r4     // Catch:{ all -> 0x004a }
            java.lang.String r4 = java.lang.String.format(r5, r6, r7)     // Catch:{ all -> 0x004a }
            java.lang.String r5 = "utf-8"
            byte[] r4 = r4.getBytes(r5)     // Catch:{ all -> 0x004a }
            r2.write(r4)     // Catch:{ all -> 0x004a }
            r4 = r8[r3]     // Catch:{ all -> 0x004a }
            r2.write(r4)     // Catch:{ all -> 0x004a }
            int r3 = r3 + 1
            goto L_0x0013
        L_0x003c:
            r2.flush()     // Catch:{ all -> 0x004a }
            byte[] r8 = r1.toByteArray()     // Catch:{ all -> 0x004a }
            r1.close()     // Catch:{ Exception -> 0x0046 }
        L_0x0046:
            r2.close()     // Catch:{ Exception -> 0x0049 }
        L_0x0049:
            return r8
        L_0x004a:
            r8 = move-exception
            goto L_0x0052
        L_0x004c:
            r8 = move-exception
            r2 = r0
            goto L_0x0052
        L_0x004f:
            r8 = move-exception
            r1 = r0
            r2 = r1
        L_0x0052:
            if (r1 == 0) goto L_0x0057
            r1.close()     // Catch:{ Exception -> 0x0057 }
        L_0x0057:
            if (r2 == 0) goto L_0x005c
            r2.close()     // Catch:{ Exception -> 0x005c }
        L_0x005c:
            throw r8
        L_0x005d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.security.net.ClientPackProxy.a(byte[][]):byte[]");
    }

    private String a() {
        if (this.b == ClientPackEnum.LOG) {
            return PublicKeyManager.b();
        }
        if (this.b == ClientPackEnum.RPC) {
            return PublicKeyManager.a();
        }
        return null;
    }

    private static int c(byte[] bArr) throws UnsupportedEncodingException {
        return Integer.parseInt(new String(bArr, "utf-8"));
    }

    public final byte[] a(byte[] bArr) throws Exception {
        return a("{\"data\":{\"api_version\":\"1.0\"}}".getBytes("utf-8"), a(this.a, a()), DesCBC.a(this.a, GzipUtils.a(bArr)));
    }
}
