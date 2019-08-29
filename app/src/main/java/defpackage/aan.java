package defpackage;

import android.text.TextUtils;

/* renamed from: aan reason: default package */
/* compiled from: ParamOptFilter */
public final class aan implements bpd {
    public final bph a(bph bph) {
        if (bph == null) {
            return bph;
        }
        boolean c = aao.d().c();
        if (aao.d().a(bph.getUrl())) {
            bph.addHeader("param-opt", c ? "1" : "0");
        }
        return bph;
    }

    public final bpk a(bpk bpk) {
        if (bpk == null) {
            return bpk;
        }
        String header = bpk.getHeader("param-cs");
        if (!TextUtils.isEmpty(header)) {
            aao.d().c(header);
        }
        return bpk;
    }
}
