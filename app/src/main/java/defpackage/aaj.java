package defpackage;

/* renamed from: aaj reason: default package */
/* compiled from: AosRetryHandlerDelegate */
public final class aaj implements a {
    public final boolean a(bpk bpk) {
        String header = bpk == null ? "" : bpk.getHeader("param-cs");
        return "2".equalsIgnoreCase(header) || "3".equalsIgnoreCase(header);
    }
}
