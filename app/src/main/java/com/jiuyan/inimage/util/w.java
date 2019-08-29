package com.jiuyan.inimage.util;

import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: VersionedGestureDetector */
class w implements OnScaleGestureListener {
    final /* synthetic */ v a;

    w(v vVar) {
        this.a = vVar;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        this.a.a.a(scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
        return true;
    }

    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
    }
}
