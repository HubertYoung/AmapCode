package defpackage;

import java.util.ArrayList;
import java.util.List;

/* renamed from: bxg reason: default package */
/* compiled from: SearchResultDataChangeObservable */
public final class bxg {
    int a = 0;
    public List<a> b = new ArrayList();

    /* renamed from: bxg$a */
    /* compiled from: SearchResultDataChangeObservable */
    public interface a {
        void a(int i);

        void a(int i, String str);

        void b_();
    }

    public final void a(String str) {
        if (this.a != 0) {
            for (a a2 : this.b) {
                a2.a(this.a, str);
            }
        }
    }

    public final void a() {
        for (a a2 : this.b) {
            a2.a(this.a);
        }
    }
}
