package defpackage;

/* renamed from: pl reason: default package */
/* compiled from: MitVuiBreakGaodeModel */
public final class pl extends bgd {
    public final String a() {
        return "breakGaode";
    }

    public final boolean a(bgb bgb, bfb bfb) {
        StringBuilder sb = new StringBuilder("handleVUICmd ");
        sb.append(bgb.b);
        tq.b("NaviMonitor", "MitVuiBreakGaodeModel", sb.toString());
        d.a.a(bgb.a, 10000, (String) null);
        return true;
    }
}
