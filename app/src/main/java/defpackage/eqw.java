package defpackage;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;

@TargetApi(8)
/* renamed from: eqw reason: default package */
/* compiled from: FroyoPhtoGestureDetector */
public final class eqw extends eqv {
    protected final ScaleGestureDetector f;

    public eqw(Context context) {
        super(context);
        this.f = new ScaleGestureDetector(context, new OnScaleGestureListener() {
            public final boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                return true;
            }

            public final void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            }

            public final boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                float scaleFactor = scaleGestureDetector.getScaleFactor();
                if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor)) {
                    return false;
                }
                eqw.this.a.b(scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                return true;
            }
        });
    }

    public final boolean a() {
        return this.f.isInProgress();
    }

    public final boolean c(MotionEvent motionEvent) {
        this.f.onTouchEvent(motionEvent);
        return super.c(motionEvent);
    }
}
