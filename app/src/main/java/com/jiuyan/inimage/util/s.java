package com.jiuyan.inimage.util;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.MotionEvent;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: VersionedGestureDetector */
public abstract class s {
    x a;

    public abstract boolean a(MotionEvent motionEvent);

    public s() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static s a(Context context, x xVar) {
        s vVar;
        int i = VERSION.SDK_INT;
        if (i < 5) {
            vVar = new t(context);
        } else if (i < 8) {
            vVar = new u(context);
        } else {
            vVar = new v(context);
        }
        vVar.a = xVar;
        return vVar;
    }
}
