package com.alipay.android.phone.inside.offlinecode.gen;

import com.alipay.offlinepay.usersslwrapper.CryptoJNI;

public abstract class ICodeProtocol {
    public abstract String generateCode(String str, String str2) throws Exception;

    /* access modifiers changed from: protected */
    public String sha1Digest(String str) throws Exception {
        return getCryptoJNIResult(CryptoJNI.sha1Digest(str));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0040 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getCryptoJNIResult(java.lang.String r6) throws java.lang.Exception {
        /*
            r5 = this;
            java.lang.String r0 = ""
            java.lang.String r1 = ""
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Throwable -> 0x001b }
            r2.<init>(r6)     // Catch:{ Throwable -> 0x001b }
            java.lang.String r3 = "result"
            java.lang.String r3 = r2.optString(r3)     // Catch:{ Throwable -> 0x001b }
            java.lang.String r0 = "resultCode"
            java.lang.String r0 = r2.optString(r0)     // Catch:{ Throwable -> 0x0018 }
            goto L_0x0027
        L_0x0018:
            r0 = move-exception
            r2 = r0
            goto L_0x001d
        L_0x001b:
            r2 = move-exception
            r3 = r0
        L_0x001d:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r4 = "inside"
            r0.c(r4, r2)
            r0 = r1
        L_0x0027:
            java.lang.String r1 = "1"
            boolean r0 = android.text.TextUtils.equals(r0, r1)
            if (r0 != 0) goto L_0x0040
            java.lang.Exception r0 = new java.lang.Exception
            java.lang.String r1 = "sign failed! > "
            java.lang.String r6 = java.lang.String.valueOf(r6)
            java.lang.String r6 = r1.concat(r6)
            r0.<init>(r6)
            throw r0
        L_0x0040:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.offlinecode.gen.ICodeProtocol.getCryptoJNIResult(java.lang.String):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public String getHexDataLength(String str) {
        return Integer.toHexString(str.length() / 2);
    }

    /* access modifiers changed from: protected */
    public String getHexTime() {
        return Integer.toHexString((int) (System.currentTimeMillis() / 1000));
    }
}
