package com.jiuyan.inimage.widget;

import android.util.Log;
import android.view.ViewTreeObserver.OnPreDrawListener;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.util.c;

/* compiled from: CropperView */
class d implements OnPreDrawListener {
    final /* synthetic */ CropperView a;

    d(CropperView cropperView) {
        this.a = cropperView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean onPreDraw() {
        if (!this.a.G && c.a(this.a.o)) {
            this.a.j();
            this.a.i();
            this.a.G = true;
        }
        return true;
    }
}
