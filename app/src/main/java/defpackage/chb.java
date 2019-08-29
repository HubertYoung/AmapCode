package defpackage;

import android.text.TextUtils;
import com.autonavi.annotation.BundleInterface;

@BundleInterface(afz.class)
/* renamed from: chb reason: default package */
/* compiled from: DriveService */
public class chb extends esi implements afz {
    public final String a() {
        dgl c = dfx.a().c();
        if (c == null || TextUtils.isEmpty(c.a.l)) {
            return "";
        }
        return c.a.l;
    }

    public final String b() {
        dgl c = dfx.a().c();
        if (c != null) {
            return c.a.p;
        }
        return "";
    }
}
