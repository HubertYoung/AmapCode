package defpackage;

import android.text.TextUtils;

/* renamed from: egr reason: default package */
/* compiled from: CpInfo */
public final class egr extends egn {
    public String a = "";
    public String b = "";

    public final boolean a(egn egn) {
        if (!(egn instanceof egr)) {
            return false;
        }
        egr egr = (egr) egn;
        if (TextUtils.isEmpty(this.b) || TextUtils.isEmpty(egr.b)) {
            return false;
        }
        return TextUtils.equals(this.b, egr.b);
    }
}
