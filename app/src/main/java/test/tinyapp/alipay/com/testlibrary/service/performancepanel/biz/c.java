package test.tinyapp.alipay.com.testlibrary.service.performancepanel.biz;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import test.tinyapp.alipay.com.testlibrary.core.DataProvider.UserAction;
import test.tinyapp.alipay.com.testlibrary.service.performancepanel.a.b;

/* compiled from: PerformanceViewController */
public final class c {
    /* access modifiers changed from: private */
    public b a;
    /* access modifiers changed from: private */
    public PerformanceDataProvider b;
    /* access modifiers changed from: private */
    public volatile boolean c = false;
    /* access modifiers changed from: private */
    public Activity d;
    /* access modifiers changed from: private */
    public View e;
    /* access modifiers changed from: private */
    public ViewGroup f;
    private Handler g = new Handler(Looper.getMainLooper());

    /* compiled from: PerformanceViewController */
    private static class a implements Runnable {
        private Runnable a;

        a(Runnable runnable) {
            this.a = runnable;
        }

        public final void run() {
            try {
                this.a.run();
            } catch (Throwable exception) {
                Log.e("PerformanceView", exception.getMessage());
            }
        }
    }

    public c(Activity attachedActivity, b viewProvider, PerformanceDataProvider dataProvider) {
        this.d = attachedActivity;
        this.a = viewProvider;
        this.b = dataProvider;
    }

    public final boolean a() {
        a((Runnable) new a(new Runnable() {
            public final void run() {
                if (c.this.c() && !c.this.c) {
                    c.this.c = true;
                    if (c.this.f == null) {
                        c.this.f = (ViewGroup) c.this.d.findViewById(16908290);
                    }
                    if (c.this.e == null) {
                        c.this.e = c.this.a.a((Context) c.this.d);
                    }
                    c.this.a.a(c.this.b.a(UserAction.ACTION_NORMAL));
                    c.this.d.addContentView(c.this.e, b.a(c.this.d));
                }
            }
        }));
        return true;
    }

    public final boolean b() {
        a((Runnable) new a(new Runnable() {
            public final void run() {
                if (c.this.c && c.this.f != null && c.this.c()) {
                    c.this.c = false;
                    c.this.f.removeView(c.this.e);
                }
            }
        }));
        return true;
    }

    public final boolean a(final UserAction userAction) {
        a((Runnable) new a(new Runnable() {
            public final void run() {
                if (c.this.c() && c.this.c) {
                    c.this.a.b(c.this.b.a(userAction));
                }
            }
        }));
        return true;
    }

    /* access modifiers changed from: private */
    public boolean c() {
        return this.d != null && !this.d.isFinishing() && !this.d.isDestroyed();
    }

    private void a(Runnable r) {
        if (test.tinyapp.alipay.com.testlibrary.a.b.a()) {
            r.run();
        } else {
            this.g.post(r);
        }
    }
}
