package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import java.lang.ref.WeakReference;

/* renamed from: cie reason: default package */
/* compiled from: AGroupActiviesHelper */
public final class cie {
    public String a = "";
    public WeakReference<AbstractBasePage> b;
    public boolean c;
    public e d = new e() {
        public final void a() {
        }

        public final void b() {
            cie.this.c = true;
        }
    };
    public c e = new d() {
        public final void onPageLifePaused(@NonNull WeakReference<AbstractBasePage> weakReference) {
        }

        public final void onPageLifeResumed(@NonNull WeakReference<AbstractBasePage> weakReference) {
            cie.this.a();
            if (!TextUtils.equals(cie.this.a, "")) {
                AbstractBasePage abstractBasePage = (AbstractBasePage) cie.this.b.get();
                if (abstractBasePage != null && abstractBasePage.isAlive()) {
                    cie.a(cie.this, abstractBasePage, cie.this.a);
                    cie.this.a = "";
                }
            }
        }
    };
    private ctl f;

    public final void a() {
        drm.b((a) this.d);
        drm.b(this.e);
    }

    public final ctl b() {
        if (this.f != null) {
            return this.f;
        }
        this.f = (ctl) a.a.a(ctl.class);
        return this.f;
    }

    public static /* synthetic */ void a(cie cie, AbstractBasePage abstractBasePage, String str) {
        if (!abstractBasePage.hasViewLayer()) {
            cie.b().a(abstractBasePage, "21", str);
        }
    }
}
