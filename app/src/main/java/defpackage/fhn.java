package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import pl.droidsonroids.gif.GifDrawable;

/* renamed from: fhn reason: default package */
/* compiled from: InvalidationHandler */
public final class fhn extends Handler {
    private final WeakReference<GifDrawable> a;

    public fhn(GifDrawable gifDrawable) {
        super(Looper.getMainLooper());
        this.a = new WeakReference<>(gifDrawable);
    }

    public final void handleMessage(Message message) {
        GifDrawable gifDrawable = (GifDrawable) this.a.get();
        if (gifDrawable != null) {
            if (message.what == -1) {
                gifDrawable.invalidateSelf();
                return;
            }
            Iterator<fhi> it = gifDrawable.mListeners.iterator();
            while (it.hasNext()) {
                it.next();
                int i = message.what;
            }
        }
    }
}
