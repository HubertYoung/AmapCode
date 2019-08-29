package defpackage;

/* renamed from: tu reason: default package */
/* compiled from: TimeLogUtils */
public final class tu {
    public static void a(String str) {
        ku a = ku.a();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" =");
        sb.append(System.currentTimeMillis());
        a.c("route_board_time_log", sb.toString());
    }
}
