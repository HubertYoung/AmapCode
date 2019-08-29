package defpackage;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;

/* renamed from: csm reason: default package */
/* compiled from: ReleatedTrafficEventPresenterImpl */
public class csm implements a, cry, c {
    private a a;
    private b b;
    private ViewGroup c;
    private WeakReference<ViewGroup> d;
    private boolean e = true;

    public final void a(a aVar) {
        ViewGroup viewGroup;
        this.a = aVar;
        if (aVar.b == null) {
            viewGroup = null;
        } else {
            viewGroup = (ViewGroup) aVar.b.getContentView().findViewById(R.id.defaultpage_drawer_content);
        }
        this.c = viewGroup;
        this.b = new csl(this, this.c);
        this.b.a((c) this);
        this.d = new WeakReference<>((ViewGroup) aVar.f());
    }

    public final void e() {
        this.b.d();
    }

    public final void a() {
        if (this.b != null && this.e) {
            this.b.a();
        }
    }

    public final void a(View view) {
        if (this.b != null && this.e) {
            this.b.a(view);
        }
    }

    public final void b() {
        if (this.b != null && this.e) {
            this.b.b();
        }
    }

    public final boolean c() {
        return this.b.c();
    }

    public final boolean d() {
        if (this.b == null || !c()) {
            return false;
        }
        this.b.b();
        return true;
    }

    public final void a(int i, int i2, boolean z) {
        if (this.b != null) {
            this.b.a(i, i2, z);
        }
    }

    public final void f() {
        if (this.a != null) {
            this.a.a();
        }
    }

    public final void g() {
        if (this.a != null) {
            this.a.b();
        }
    }

    public void onOffsetBG(int i) {
        ViewGroup viewGroup = (ViewGroup) this.d.get();
        if (viewGroup != null) {
            LayoutParams layoutParams = ((ViewGroup) this.d.get()).getLayoutParams();
            if (viewGroup.getChildAt(0) != null) {
                i = Math.max(viewGroup.getChildAt(0).getHeight(), i);
            }
            if (layoutParams.height != i) {
                layoutParams.height = i;
                viewGroup.setLayoutParams(layoutParams);
            }
        }
    }

    public void onResetBG() {
        if (this.a.f() != null) {
            LayoutParams layoutParams = this.a.f().getLayoutParams();
            if (layoutParams.height != -2) {
                layoutParams.height = -2;
                this.a.f().setLayoutParams(layoutParams);
            }
        }
    }
}
