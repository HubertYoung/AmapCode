package defpackage;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.suspend.SuspendPartitionView.LayoutParams;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.gpsbutton.GPSButtonNewMainPage;

/* renamed from: dlf reason: default package */
/* compiled from: NewMainMapView */
public final class dlf extends dle {
    private GPSButtonNewMainPage j;

    public dlf(bid bid, View view) {
        super(bid, view);
    }

    public final View e() {
        if (this.g == null) {
            return null;
        }
        return this.g.d;
    }

    public final void a(@Nullable Context context) {
        if (this.g != null && this.g.d != null && context != null) {
            if (this.j == null) {
                this.j = new GPSButtonNewMainPage(context, null);
                this.j.setContentDescription(context.getString(R.string.LocationMe));
                this.e.d().a((ced) this.j);
            }
            if (this.j.getParent() == null) {
                int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.main_map_msgbox_width);
                LayoutParams layoutParams = new LayoutParams(dimensionPixelSize, dimensionPixelSize);
                int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.map_container_btn_margin);
                layoutParams.leftMargin = dimensionPixelSize2;
                layoutParams.bottomMargin = dimensionPixelSize2;
                layoutParams.b = 5.0f;
                this.g.d.addView(this.j, layoutParams, 3);
            }
        }
    }

    public final void d() {
        if (this.g != null) {
            dlc dlc = this.g;
            if (dlc.h != null) {
                dlc.h.setAmapLogoVisibility(true);
            }
        }
    }

    public final View f() {
        if (this.g == null) {
            return null;
        }
        return this.g.d.getViewGroupByPosition(6);
    }

    public final void g() {
        if (this.j != null) {
            LayoutParams layoutParams = (LayoutParams) this.j.getLayoutParams();
            agn.a(AMapPageUtil.getAppContext(), 125.0f);
            int dimensionPixelSize = AMapPageUtil.getAppContext().getResources().getDimensionPixelSize(R.dimen.main_map_msgbox_width);
            layoutParams.bottomMargin = 0;
            layoutParams.width = dimensionPixelSize;
            layoutParams.height = dimensionPixelSize;
        }
    }

    public final void a(boolean z) {
        super.a(z);
        if (this.g != null) {
            int i = z ? 8 : 0;
            this.g.g.setVisibility(i);
            this.g.a(i);
        }
    }

    public final void a() {
        super.a();
    }

    /* access modifiers changed from: protected */
    public final void c() {
        super.c();
    }

    public final void b() {
        if (this.j != null) {
            ViewParent parent = this.j.getParent();
            if (ViewGroup.class.isInstance(parent)) {
                ((ViewGroup) parent).removeView(this.j);
            }
            this.e.d().b((ced) this.j);
            this.j = null;
        }
        super.b();
    }
}
