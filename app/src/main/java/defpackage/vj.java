package defpackage;

import android.view.MotionEvent;
import com.autonavi.jni.eyrie.amap.maps.MapViewManager;

/* renamed from: vj reason: default package */
/* compiled from: EyrieMapListenerImpl */
final class vj implements amj {
    vj() {
    }

    public final boolean onClick(int i, float f, float f2) {
        return MapViewManager.onClick(i, f, f2);
    }

    public final boolean onDoubleClick(int i, float f, float f2) {
        MapViewManager.onDoubleClick(i, f, f2);
        return false;
    }

    public final boolean onLongPress(int i, float f, float f2) {
        MapViewManager.onLongPress(i, f, f2);
        return false;
    }

    public final boolean onMontionStart(int i, float f, float f2) {
        MapViewManager.onMotionStart(i);
        return false;
    }

    public final boolean onMontionFinish(int i) {
        MapViewManager.onMotionFinish(i);
        return false;
    }

    public final boolean onTouchEvent(int i, MotionEvent motionEvent) {
        if (bno.a) {
            ku a = ku.a();
            StringBuilder sb = new StringBuilder("onTouchEvent--engineId=");
            sb.append(i);
            sb.append("event--action=");
            sb.append(motionEvent.getAction());
            a.c("#16121476", sb.toString());
        }
        MapViewManager.onTouchEvent(i, motionEvent.getX(), motionEvent.getY(), motionEvent.getAction());
        return false;
    }
}
