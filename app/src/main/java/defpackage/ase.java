package defpackage;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import com.autonavi.bundle.amaphome.widget.guideview.MaskView.LayoutParams;

/* renamed from: ase reason: default package */
/* compiled from: Common */
final class ase {
    static View a(LayoutInflater layoutInflater, asf asf) {
        View a = asf.a(layoutInflater);
        LayoutParams layoutParams = new LayoutParams(-1);
        layoutParams.c = 0;
        layoutParams.d = asf.b();
        layoutParams.a = 2;
        layoutParams.b = 32;
        a.setLayoutParams(layoutParams);
        return a;
    }

    static Rect a(View view, int i, int i2) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        Rect rect = new Rect();
        rect.set(iArr[0], iArr[1], iArr[0] + view.getMeasuredWidth(), iArr[1] + view.getMeasuredHeight());
        rect.offset(-i, -i2);
        return rect;
    }
}
