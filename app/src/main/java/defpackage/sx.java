package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import com.amap.bundle.drivecommon.widget.ExpandableIconView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView;

/* renamed from: sx reason: default package */
/* compiled from: DriveSuspendViewHelper */
public abstract class sx extends ccv {
    private View a;
    public Context c;
    protected AGroupSuspendView d;
    protected ExpandableIconView e;
    protected boolean f = false;
    protected ccy g;

    public sx(IMapPage iMapPage) {
        super(iMapPage.getContext());
        this.c = iMapPage.getContext();
        this.g = iMapPage.getSuspendWidgetHelper();
    }

    public void addWidget(View view, LayoutParams layoutParams, int i) {
        a(view);
        super.addWidget(view, layoutParams, i);
    }

    public void addWidget(View view, LayoutParams layoutParams, int i, int i2) {
        a(view);
        super.addWidget(view, layoutParams, i, i2);
    }

    public void a(View view) {
        if (view != null && view.getParent() != null && (view.getParent() instanceof ViewGroup)) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    public final View e() {
        if (this.a == null) {
            this.a = LayoutInflater.from(this.c).inflate(R.layout.suspend_view_error_report, null);
        }
        return this.a;
    }

    public final void a(OnClickListener onClickListener) {
        NoDBClickUtil.a(e().findViewById(R.id.btn_error_report), onClickListener);
    }

    public final LinearLayout.LayoutParams f() {
        int dimensionPixelSize = this.c.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
        layoutParams.rightMargin = agn.a(this.c, 4.0f);
        return layoutParams;
    }

    public LinearLayout.LayoutParams a() {
        LinearLayout.LayoutParams m = this.g.m();
        if (m != null) {
            m.rightMargin = agn.a(this.c, 4.0f);
            m.topMargin = agn.a(this.c, 0.0f);
            m.bottomMargin = agn.a(this.c, 3.0f);
        }
        return m;
    }

    public final LinearLayout.LayoutParams g() {
        LinearLayout.LayoutParams k = this.g.k();
        if (k != null) {
            k.topMargin = agn.a(this.c, 4.0f);
        }
        return k;
    }
}
