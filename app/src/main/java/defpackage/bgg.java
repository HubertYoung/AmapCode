package defpackage;

import com.autonavi.vcs.NativeVcsManager;

/* renamed from: bgg reason: default package */
/* compiled from: MitVuiRefuseModel */
public final class bgg extends bgd {
    public final String a() {
        return "refuse";
    }

    public final boolean a(bgb bgb, bfb bfb) {
        StringBuilder sb = new StringBuilder("handleVUICmd ");
        sb.append(bgb.b);
        bfh.a("MitVuiRefuseModel", sb.toString());
        NativeVcsManager.getInstance().stopListening();
        d.a.a(bgb.a, 10000, (String) null, false);
        return true;
    }
}
