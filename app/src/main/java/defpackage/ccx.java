package defpackage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.SuspendViewCommonTemplate;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.map.suspend.refactor.zoom.ZoomView;
import com.autonavi.minimap.R;

/* renamed from: ccx reason: default package */
/* compiled from: SuspendViewSimpleManager */
public abstract class ccx {
    protected IMapPage a;
    protected Context b;
    protected cde c;
    protected SuspendViewCommonTemplate d = new SuspendViewCommonTemplate(this.b);
    public ccu e;

    /* access modifiers changed from: protected */
    public abstract void c();

    /* access modifiers changed from: protected */
    public abstract void d();

    /* access modifiers changed from: protected */
    public abstract void f();

    public ccx(IMapPage iMapPage) {
        this.a = iMapPage;
        this.b = iMapPage.getContext();
        this.c = iMapPage.getSuspendManager();
        this.e = iMapPage.getSuspendWidgetHelper();
        b();
        c();
        int dimensionPixelSize = this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        LayoutParams layoutParams = new LayoutParams(dimensionPixelSize, dimensionPixelSize);
        int a2 = agn.a(this.b, 4.0f);
        layoutParams.leftMargin = a2;
        layoutParams.bottomMargin = a2;
        this.e.a(this.e.d());
        this.e.a(this.d, this.e.d(), layoutParams, 3);
        ScaleView f = this.e.f();
        f.setContentDescription(null);
        LayoutParams layoutParams2 = new LayoutParams(-1, this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size));
        f.setPadding(0, 0, 0, agn.a(this.b, 6.0f));
        layoutParams2.leftMargin = agn.a(this.b, 6.0f);
        this.d.addView(f, layoutParams2, 7);
        d();
        e();
        f();
    }

    public final View a() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public final void a(View view, ViewGroup.LayoutParams layoutParams) {
        this.d.addView(view, layoutParams, 1);
    }

    /* access modifiers changed from: protected */
    public final void b(View view, ViewGroup.LayoutParams layoutParams) {
        this.d.addView(view, layoutParams, 2);
    }

    /* access modifiers changed from: protected */
    public final void c(View view, ViewGroup.LayoutParams layoutParams) {
        this.d.addView(view, layoutParams, 4);
    }

    /* access modifiers changed from: protected */
    public final void d(View view, ViewGroup.LayoutParams layoutParams) {
        this.d.addView(view, layoutParams, 6);
    }

    /* access modifiers changed from: protected */
    public void b() {
        View a2 = this.e.a();
        a2.setContentDescription(null);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.leftMargin = agn.a(this.b, 7.0f);
        layoutParams.topMargin = agn.a(this.b, 7.0f);
        a(a2, layoutParams);
    }

    /* access modifiers changed from: protected */
    public void e() {
        ZoomView l = this.e.l();
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.rightMargin = agn.a(this.b, 4.0f);
        layoutParams.topMargin = agn.a(this.b, 4.0f);
        this.d.addView(l, layoutParams, 5);
    }
}
