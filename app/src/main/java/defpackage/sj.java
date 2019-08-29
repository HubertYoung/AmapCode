package defpackage;

import android.text.TextUtils;
import com.autonavi.common.model.POI;

/* renamed from: sj reason: default package */
/* compiled from: NaviHistory */
public class sj {
    public String a;
    public Integer b;
    public String c;
    public String d;
    public Long e;
    public POI f;

    public final POI a() {
        if (this.d != null && this.f == null) {
            this.f = chk.a(this.d);
        }
        return this.f;
    }

    public final String b() {
        POI a2 = a();
        if (a2 == null) {
            return "";
        }
        return !TextUtils.isEmpty(a2.getAddr()) ? a2.getAddr() : "";
    }
}
