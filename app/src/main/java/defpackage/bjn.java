package defpackage;

import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

/* renamed from: bjn reason: default package */
/* compiled from: DeferredRequestCreator */
public final class bjn implements OnPreDrawListener {
    final bka a;
    final WeakReference<ImageView> b;
    bjl c;

    public bjn(bka bka, ImageView imageView, bjl bjl) {
        this.a = bka;
        this.b = new WeakReference<>(imageView);
        this.c = bjl;
        imageView.getViewTreeObserver().addOnPreDrawListener(this);
    }

    public final boolean onPreDraw() {
        ImageView imageView = (ImageView) this.b.get();
        if (imageView == null) {
            return true;
        }
        ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) {
            return true;
        }
        int width = imageView.getWidth();
        int height = imageView.getHeight();
        if (width <= 0 || height <= 0) {
            return true;
        }
        viewTreeObserver.removeOnPreDrawListener(this);
        bka bka = this.a;
        bka.c = false;
        bka.a(width, height).a(imageView, this.c);
        return true;
    }

    public final void a() {
        this.a.i = null;
        this.c = null;
        ImageView imageView = (ImageView) this.b.get();
        if (imageView != null) {
            ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnPreDrawListener(this);
            }
        }
    }
}
