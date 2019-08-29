package defpackage;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/* renamed from: djt reason: default package */
/* compiled from: SoftKeyBoardListener */
public final class djt {
    View a;
    int b;
    a c;
    private OnGlobalLayoutListener d = new OnGlobalLayoutListener() {
        public final void onGlobalLayout() {
            if (djt.this.a != null) {
                Rect rect = new Rect();
                djt.this.a.getWindowVisibleDisplayFrame(rect);
                int i = rect.bottom;
                if (djt.this.b != i) {
                    if (djt.this.b == 0) {
                        djt.this.b = i;
                        return;
                    }
                    if (Math.abs(i - djt.this.b) < 150) {
                        djt.this.b = i;
                    }
                    if (djt.this.b - i > 150) {
                        if (djt.this.c != null) {
                            int i2 = djt.this.b - i;
                            if (i2 > 250) {
                                djt.this.c.a(i2);
                            }
                        }
                        djt.this.b = i;
                    } else if (i - djt.this.b > 150) {
                        if (djt.this.c != null && i - djt.this.b > 250) {
                            djt.this.c.a();
                        }
                        djt.this.b = i;
                    }
                }
            }
        }
    };

    /* renamed from: djt$a */
    /* compiled from: SoftKeyBoardListener */
    public interface a {
        void a();

        void a(int i);
    }

    public djt(Activity activity) {
        if (activity != null) {
            this.a = activity.getWindow().getDecorView();
        }
    }

    private void b() {
        if (this.a != null) {
            ViewTreeObserver viewTreeObserver = this.a.getViewTreeObserver();
            if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(this.d);
            }
        }
    }

    private void c() {
        if (this.a != null) {
            ViewTreeObserver viewTreeObserver = this.a.getViewTreeObserver();
            if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
                viewTreeObserver.removeGlobalOnLayoutListener(this.d);
            }
        }
    }

    public final void a(a aVar) {
        this.c = aVar;
        b();
    }

    public final void a() {
        this.c = null;
        c();
    }
}
