package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.minimap.index.page.DefaultPage;

/* renamed from: dlb reason: default package */
/* compiled from: RealTimeBusManager */
public final class dlb implements ccz {
    public DefaultPage a;
    public awv b;

    public dlb(@NonNull DefaultPage defaultPage) {
        this.a = defaultPage;
    }

    public final void a(boolean z, boolean z2) {
        if (this.b == null) {
            this.b = (awv) a.a.a(awv.class);
        }
        if (this.b != null) {
            if (z) {
                awu b2 = this.b.b();
                DefaultPage defaultPage = this.a;
                this.a.getTipContainer();
                this.a.getPoiDetailDelegate();
                b2.a(defaultPage, z2);
            } else {
                this.b.b().a(this.a, this.a.getTipContainer(), z2);
            }
        }
        axv axv = (axv) a.a.a(axv.class);
        if (axv != null) {
            axv.a(z);
        }
    }

    public final void a() {
        if (this.b == null) {
            this.b = (awv) a.a.a(awv.class);
        }
        if (this.b != null) {
            awu b2 = this.b.b();
            DefaultPage defaultPage = this.a;
            this.a.getTipContainer();
            b2.a(defaultPage);
        }
    }

    public final void b() {
        if (this.b != null) {
            awu b2 = this.b.b();
            DefaultPage defaultPage = this.a;
            this.a.getTipContainer();
            b2.b(defaultPage);
        }
    }

    public final void c() {
        bmn.b();
        if (brj.a()) {
            a(false, false);
        } else {
            a(false, false);
        }
    }

    public final void d() {
        if (this.b != null) {
            this.b.b().g();
        }
    }

    public final void e() {
        if (this.b != null) {
            this.b.b().k();
        }
    }
}
