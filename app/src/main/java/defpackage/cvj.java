package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.bundle.apm.base.plugin.Plugin;
import java.lang.ref.WeakReference;
import org.json.JSONObject;

/* renamed from: cvj reason: default package */
/* compiled from: AppEventDetectPlugin */
public class cvj extends Plugin {
    private Application c;
    /* access modifiers changed from: private */
    public cuu d;
    private cuv e;
    private cut f;
    private defpackage.dro.c g = new a();
    private defpackage.dro.c h = new b();
    private defpackage.dro.c i = new c();

    /* renamed from: cvj$a */
    /* compiled from: AppEventDetectPlugin */
    class a implements defpackage.dro.a {
        a() {
        }

        public final void a(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null) {
                cvj.this.d.a(cus.a(1, abstractBasePage));
            }
        }

        public final void b(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null) {
                cvj.this.d.a(cus.a(6, abstractBasePage));
            }
        }
    }

    /* renamed from: cvj$b */
    /* compiled from: AppEventDetectPlugin */
    class b implements d {
        b() {
        }

        public final void onPageLifeResumed(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null) {
                cvj.this.d.a(cus.a(3, abstractBasePage));
            }
        }

        public final void onPageLifePaused(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null) {
                cvj.this.d.a(cus.a(4, abstractBasePage));
            }
        }
    }

    /* renamed from: cvj$c */
    /* compiled from: AppEventDetectPlugin */
    class c implements e {
        c() {
        }

        public final void a(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null) {
                cvj.this.d.a(cus.a(2, abstractBasePage));
            }
        }

        public final void b(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null) {
                cvj.this.d.a(cus.a(5, abstractBasePage));
            }
        }
    }

    public final void a(int i2, cur cur) {
    }

    public final void a(Application application, cuu cuu, JSONObject jSONObject) {
        this.b = 0;
        this.c = application;
        this.d = cuu;
        this.e = cuu.b();
        this.f = cuu.a();
        drm.a(this.g);
        drm.a(this.h);
        drm.a(this.i);
    }
}
