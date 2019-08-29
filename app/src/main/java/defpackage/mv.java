package defpackage;

import android.graphics.Rect;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

/* renamed from: mv reason: default package */
/* compiled from: StatusBarOnLayoutChangeListener */
public final class mv implements OnLayoutChangeListener {
    private AbstractBasePage a = null;

    public mv(AbstractBasePage abstractBasePage) {
        this.a = abstractBasePage;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (ra.a(this.a) && (this.a.getActivity().getWindow().getAttributes().flags & 1024) != 1024) {
            Rect rect = new Rect();
            this.a.getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            if (!(i4 == 0 || i8 == 0 || i4 - rect.bottom > 0)) {
                tt.a(this.a.getActivity());
            }
        }
    }
}
