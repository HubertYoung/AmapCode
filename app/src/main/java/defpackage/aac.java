package defpackage;

import android.text.TextUtils;
import com.autonavi.server.aos.serverkey;

/* renamed from: aac reason: default package */
/* compiled from: AosEncryptor */
public final class aac implements is {
    public final String a() {
        return serverkey.getAosKey();
    }

    public final String a(byte[] bArr) {
        return serverkey.sign(bArr);
    }

    public final byte[] b(byte[] bArr) {
        return bArr != null ? serverkey.amapEncodeBinaryV2(bArr) : bArr;
    }

    public final String a(String str) {
        return !TextUtils.isEmpty(str) ? serverkey.amapEncodeV2(str) : str;
    }

    public final boolean b() {
        return aay.b();
    }

    public final String a(String... strArr) {
        return aay.a(strArr);
    }

    public final String c() {
        return aay.a();
    }

    public final String d() {
        return aay.c();
    }
}
