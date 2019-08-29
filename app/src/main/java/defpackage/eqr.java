package defpackage;

import android.graphics.RectF;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.widget.ImageView;

/* renamed from: eqr reason: default package */
/* compiled from: DefaultOnDoubleTapListener */
public final class eqr implements OnDoubleTapListener {
    private eqt a;

    public final boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    public eqr(eqt eqt) {
        this.a = eqt;
    }

    public final boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        if (this.a == null) {
            return false;
        }
        ImageView c = this.a.c();
        if (this.a.j != null) {
            RectF b = this.a.b();
            if (b != null && b.contains(motionEvent.getX(), motionEvent.getY())) {
                float f = b.left;
                b.width();
                float f2 = b.top;
                b.height();
                this.a.j.a(c);
                return true;
            }
        }
        if (this.a.k != null) {
            e eVar = this.a.k;
            motionEvent.getX();
            motionEvent.getY();
            eVar.a();
        }
        return false;
    }

    public final boolean onDoubleTap(MotionEvent motionEvent) {
        if (this.a == null) {
            return false;
        }
        try {
            float d = this.a.d();
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (d < this.a.d) {
                this.a.a(this.a.d, x, y, true);
            } else if (d < this.a.d || d >= this.a.e) {
                this.a.a(this.a.c, x, y, true);
            } else {
                this.a.a(this.a.e, x, y, true);
            }
        } catch (ArrayIndexOutOfBoundsException unused) {
        }
        return true;
    }
}
