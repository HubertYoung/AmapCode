package defpackage;

import com.autonavi.widget.ui.AlertView;

/* renamed from: nl reason: default package */
/* compiled from: DriveAlertDialog */
public final class nl {
    public String a = "";
    public String b;
    public String c;
    public b d;
    public c e;
    public bid f;
    public AlertView g;
    public Object h = new Object();

    /* renamed from: nl$a */
    /* compiled from: DriveAlertDialog */
    public static class a {
        public String a;
        public String b;
        public String c;
        public String d;
        public b e;
        public c f;
    }

    /* renamed from: nl$b */
    /* compiled from: DriveAlertDialog */
    public interface b {
        void a();

        void b();
    }

    /* renamed from: nl$c */
    /* compiled from: DriveAlertDialog */
    public interface c {
        void a();
    }

    public nl(bid bid) {
        this.f = bid;
    }

    public final void a() {
        if (!(this.g == null || this.f == null)) {
            this.f.dismissViewLayer(this.g);
        }
        this.d = null;
    }
}
