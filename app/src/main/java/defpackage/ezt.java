package defpackage;

import android.text.TextUtils;
import com.alipay.sdk.util.h;

/* renamed from: ezt reason: default package */
/* compiled from: PushPackageInfo */
public final class ezt {
    public String a;
    public long b = -1;
    public int c = -1;
    public String d;
    public boolean e = false;
    public boolean f = false;

    public ezt(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalAccessError("PushPackageInfo need a non-null pkgName.");
        }
        this.a = str;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PushPackageInfo{mPackageName=");
        sb.append(this.a);
        sb.append(", mPushVersion=");
        sb.append(this.b);
        sb.append(", mPackageVersion=");
        sb.append(this.c);
        sb.append(", mInBlackList=");
        sb.append(this.e);
        sb.append(", mPushEnable=");
        sb.append(this.f);
        sb.append(h.d);
        return sb.toString();
    }
}
