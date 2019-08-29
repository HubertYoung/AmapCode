package defpackage;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;

/* renamed from: bdd reason: default package */
/* compiled from: FullScreenOnLayoutChangeListener */
public final class bdd implements OnLayoutChangeListener {
    Activity a = null;

    public bdd(Activity activity) {
        this.a = activity;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (DoNotUseTool.haveStatusbarView()) {
            Rect rect = new Rect();
            this.a.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            if (i4 != 0 && i8 != 0 && i4 - rect.bottom <= 0) {
                if ((DoNotUseTool.getActivity().getWindow().getAttributes().flags & 1024) != 1024) {
                    DoNotUseTool.getActivity().getWindow().addFlags(1024);
                }
            } else if ((DoNotUseTool.getActivity().getWindow().getAttributes().flags & 1024) == 1024) {
                DoNotUseTool.getActivity().getWindow().clearFlags(1024);
            }
        }
    }
}
