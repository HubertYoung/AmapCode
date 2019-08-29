package defpackage;

/* renamed from: cuq reason: default package */
/* compiled from: AppEvent */
public final class cuq extends cur {
    private static final cuq c = new cuq(1);
    private static final cuq d = new cuq(2);
    public int a;

    private cuq(int i) {
        this.a = i;
        this.b = 2;
    }

    public static cuq a(int i) {
        if (i == 1) {
            return c;
        }
        if (i == 2) {
            return d;
        }
        StringBuilder sb = new StringBuilder("subEvent : ");
        sb.append(i);
        sb.append(" is not valid");
        throw new IllegalArgumentException(sb.toString());
    }
}
