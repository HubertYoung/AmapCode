package defpackage;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: akf reason: default package */
/* compiled from: PageContainer */
public final class akf {
    final ViewGroup a;
    final Handler b = new Handler();

    /* renamed from: akf$a */
    /* compiled from: PageContainer */
    static class a extends b {
        final ArrayList<View> a;
        final ArrayList<View> b;

        a(aka aka, akb akb, ArrayList<View> arrayList, ArrayList<View> arrayList2, Runnable runnable) {
            super(aka, akb, runnable);
            this.a = arrayList;
            this.b = arrayList2;
        }
    }

    /* renamed from: akf$b */
    /* compiled from: PageContainer */
    static class b {
        final aka c;
        final akb d;
        final Runnable e;

        b(aka aka, akb akb, Runnable runnable) {
            this.c = aka;
            this.d = akb;
            this.e = runnable;
        }
    }

    /* renamed from: akf$c */
    /* compiled from: PageContainer */
    static class c extends b {
        final ArrayList<ajz> a;
        final ArrayList<View> b;

        c(aka aka, akb akb, ArrayList<ajz> arrayList, ArrayList<View> arrayList2, Runnable runnable) {
            super(aka, akb, runnable);
            this.a = arrayList;
            this.b = arrayList2;
        }
    }

    /* renamed from: akf$d */
    /* compiled from: PageContainer */
    class d implements aka, Runnable {
        private final Runnable b;

        public final void a() {
        }

        d(Runnable runnable) {
            this.b = runnable;
        }

        public final void b() {
            akf.this.b.post(this);
        }

        public final void run() {
            if (this.b != null) {
                this.b.run();
            }
        }
    }

    /* renamed from: akf$e */
    /* compiled from: PageContainer */
    class e implements AnimationListener {
        private final aka b;
        private final aka c;

        public final void onAnimationRepeat(Animation animation) {
        }

        e(aka aka, aka aka2) {
            this.b = aka;
            this.c = aka2;
        }

        public final void onAnimationStart(Animation animation) {
            if (this.b != null) {
                this.b.a();
            }
            try {
                if (this.c != null) {
                    this.c.a();
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        public final void onAnimationEnd(Animation animation) {
            try {
                if (this.c != null) {
                    this.c.b();
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            if (this.b != null) {
                this.b.b();
            }
        }
    }

    public akf(ViewGroup viewGroup) {
        this.a = viewGroup;
    }

    /* access modifiers changed from: 0000 */
    public final void a(View view) {
        new StringBuilder("remove: v=").append(view);
        this.a.removeView(view);
    }

    static void b(View view) {
        new StringBuilder("remove: v=").append(view);
        view.setVisibility(8);
    }

    /* access modifiers changed from: 0000 */
    public final void a(a aVar) {
        new StringBuilder("animateAdd: size=").append(aVar.b.size());
        Iterator<View> it = aVar.b.iterator();
        while (it.hasNext()) {
            new StringBuilder("animateAdd: v=").append(it.next());
        }
        aka aka = aVar.c;
        if (aVar.a != null) {
            Iterator<View> it2 = aVar.a.iterator();
            while (it2.hasNext()) {
                it2.next().startAnimation(aVar.d.a(this.a));
            }
        }
        for (int i = 0; i < aVar.b.size(); i++) {
            View view = aVar.b.get(i);
            Animation b2 = aVar.d.b(this.a);
            if (i == 0) {
                b2.setAnimationListener(new e(new d(aVar.e), aka));
            }
            view.startAnimation(b2);
            if (this.a.indexOfChild(view) < 0) {
                this.a.addView(view, view.getLayoutParams());
            } else {
                view.setVisibility(0);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(c cVar) {
        new StringBuilder("animateRemove: size=").append(cVar.b.size());
        Iterator<View> it = cVar.b.iterator();
        while (it.hasNext()) {
            new StringBuilder("animateRemove: v=").append(it.next());
        }
        aka aka = cVar.c;
        for (int i = 0; i < cVar.b.size(); i++) {
            View view = cVar.b.get(i);
            Animation c2 = cVar.d.c(this.a);
            if (i == 0) {
                c2.setAnimationListener(new e(new d(cVar.e), aka));
            }
            view.startAnimation(c2);
        }
        if (cVar.a != null) {
            Iterator<ajz> it2 = cVar.a.iterator();
            while (it2.hasNext()) {
                it2.next().b().startAnimation(cVar.d.d(this.a));
            }
        }
    }
}
