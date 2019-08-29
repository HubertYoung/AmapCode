package defpackage;

import android.app.Activity;
import android.app.Application;
import com.autonavi.common.PageBundle;

/* renamed from: esj reason: default package */
/* compiled from: WingContext */
public final class esj {
    public Application a = null;
    public Activity b = null;
    public bul c = null;

    public final void a(Class<? extends bid> cls, PageBundle pageBundle) {
        if (this.c != null) {
            akc akc = null;
            try {
                akd akd = this.c.b;
                if (akd != null) {
                    akc = akd.b();
                }
            } catch (Exception unused) {
            }
            if (akc instanceof buk) {
                ((buk) akc).h().startPage(cls, pageBundle);
            }
        }
    }
}
