package com.jiuyan.inimage.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import com.alipay.android.hackbyte.ClassVerifier;

@TargetApi(8)
/* compiled from: VersionedGestureDetector */
class v extends u {
    private final ScaleGestureDetector f;
    private final OnScaleGestureListener g = new w(this);

    public v(Context context) {
        super(context);
        this.f = new ScaleGestureDetector(context, this.g);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean a(MotionEvent motionEvent) {
        this.f.onTouchEvent(motionEvent);
        return super.a(motionEvent);
    }
}
