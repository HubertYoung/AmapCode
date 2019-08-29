package defpackage;

import com.autonavi.common.PageBundle;

/* renamed from: esk reason: default package */
/* compiled from: WingRouter */
public abstract class esk {
    public esj mWingContext;

    public abstract boolean start(ese ese);

    /* access modifiers changed from: 0000 */
    public void attachWingContext(esj esj) {
        this.mWingContext = esj;
    }

    public final void startPage(Class<? extends bid> cls, PageBundle pageBundle) {
        this.mWingContext.a(cls, pageBundle);
    }

    public final void startPageForResult(Class<? extends bid> cls, PageBundle pageBundle, int i) {
        esj esj = this.mWingContext;
        if (esj.c != null) {
            esj.c.a(cls, pageBundle, i);
        }
    }

    public final void startPageForCallback(Class<? extends bid> cls, PageBundle pageBundle, bha bha) {
        esj esj = this.mWingContext;
        if (esj.c != null) {
            if (pageBundle == null) {
                pageBundle = new PageBundle();
            }
            pageBundle.putObject("com.autonavi.mvphost.Callback", bha);
            esj.c.a(cls, pageBundle, 16952966);
        }
    }

    public final void startPageForCallback(String str, PageBundle pageBundle, bha bha) {
        esj esj = this.mWingContext;
        if (esj.c != null) {
            if (pageBundle == null) {
                pageBundle = new PageBundle();
            }
            pageBundle.putObject("com.autonavi.mvphost.Callback", bha);
            esj.c.a(str, pageBundle, 16952966);
        }
    }

    public final void startPage(String str, PageBundle pageBundle) {
        esj esj = this.mWingContext;
        if (esj.c != null) {
            akc akc = null;
            try {
                akd akd = esj.c.b;
                if (akd != null) {
                    akc = akd.b();
                }
            } catch (Exception unused) {
            }
            if (akc instanceof buk) {
                ((buk) akc).h().startPage(str, pageBundle);
            }
        }
    }

    public final void startPageForResult(String str, PageBundle pageBundle, int i) {
        esj esj = this.mWingContext;
        if (esj.c != null) {
            esj.c.a(str, pageBundle, i);
        }
    }
}
