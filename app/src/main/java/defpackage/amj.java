package defpackage;

import android.view.MotionEvent;

/* renamed from: amj reason: default package */
/* compiled from: MapGestureListener */
public interface amj {
    boolean onClick(int i, float f, float f2);

    boolean onDoubleClick(int i, float f, float f2);

    boolean onLongPress(int i, float f, float f2);

    boolean onMontionFinish(int i);

    boolean onMontionStart(int i, float f, float f2);

    boolean onTouchEvent(int i, MotionEvent motionEvent);
}
