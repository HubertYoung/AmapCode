package com.jiuyan.inimage.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import com.alipay.android.hackbyte.ClassVerifier;

@TargetApi(5)
/* compiled from: VersionedGestureDetector */
class u extends t {
    private int f = -1;
    private int g = 0;

    public u(Context context) {
        super(context);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public float b(MotionEvent motionEvent) {
        try {
            return motionEvent.getX(this.g);
        } catch (Exception e) {
            return motionEvent.getX();
        }
    }

    /* access modifiers changed from: 0000 */
    public float c(MotionEvent motionEvent) {
        try {
            return motionEvent.getY(this.g);
        } catch (Exception e) {
            return motionEvent.getY();
        }
    }

    public boolean a(MotionEvent motionEvent) {
        int i;
        int i2 = 0;
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.f = motionEvent.getPointerId(0);
                break;
            case 1:
            case 3:
                this.f = -1;
                break;
            case 6:
                int action = (motionEvent.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                if (motionEvent.getPointerId(action) == this.f) {
                    if (action == 0) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    this.f = motionEvent.getPointerId(i);
                    this.b = motionEvent.getX(i);
                    this.c = motionEvent.getY(i);
                    break;
                }
                break;
        }
        if (this.f != -1) {
            i2 = this.f;
        }
        this.g = motionEvent.findPointerIndex(i2);
        return super.a(motionEvent);
    }
}
