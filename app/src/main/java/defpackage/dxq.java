package defpackage;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;

/* renamed from: dxq reason: default package */
/* compiled from: SuspendViewHolder */
public final class dxq {
    int a;
    View b;
    LayoutParams c;
    int d;

    dxq(int i, View view, LayoutParams layoutParams, int i2) {
        this.a = i;
        this.b = view;
        this.c = layoutParams;
        this.d = i2;
    }

    public final void a(int i) {
        if (this.b != null) {
            this.b.setVisibility(i);
        }
    }

    public final void a(float f) {
        if (this.b != null) {
            this.b.setAlpha(f);
        }
    }

    static LinearLayout.LayoutParams a() {
        int dimensionPixelSize = (AMapPageUtil.getAppContext() == null || AMapPageUtil.getAppContext().getResources() == null) ? 0 : AMapPageUtil.getAppContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        return new LinearLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
    }
}
