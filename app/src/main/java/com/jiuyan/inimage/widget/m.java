package com.jiuyan.inimage.widget;

import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: PhotoCropViewFreedom */
class m extends SimpleOnGestureListener {
    final /* synthetic */ PhotoCropViewFreedom a;

    m(PhotoCropViewFreedom photoCropViewFreedom) {
        this.a = photoCropViewFreedom;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onLongPress(MotionEvent motionEvent) {
        if (this.a.D != null) {
            this.a.D.onLongClick(this.a);
        }
    }
}
