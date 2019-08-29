package com.jiuyan.inimage.widget;

import android.util.Log;
import android.view.ViewTreeObserver.OnPreDrawListener;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.util.c;

/* compiled from: RotateView */
class ab implements OnPreDrawListener {
    final /* synthetic */ RotateView a;

    ab(RotateView rotateView) {
        this.a = rotateView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean onPreDraw() {
        if (!this.a.o && c.a(this.a.g)) {
            this.a.d.a(this.a.d.getMeasuredWidth(), this.a.d.getMeasuredHeight(), 0, (String) "#00000000");
            this.a.h();
            this.a.o = true;
        }
        return true;
    }
}
