package defpackage;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/* renamed from: aft reason: default package */
/* compiled from: SoftKeyBoardListener */
public final class aft {
    public View a;
    int b;
    public a c;
    int d = 1;
    public OnGlobalLayoutListener e = new OnGlobalLayoutListener() {
        public final void onGlobalLayout() {
            if (aft.this.a != null) {
                Rect rect = new Rect();
                aft.this.a.getWindowVisibleDisplayFrame(rect);
                int height = rect.height();
                Configuration configuration = aft.this.a.getResources().getConfiguration();
                if (configuration.orientation != aft.this.d) {
                    aft.this.b = 0;
                    aft.this.d = configuration.orientation;
                }
                if (aft.this.b != height) {
                    if (aft.this.b == 0) {
                        aft.this.b = height;
                        return;
                    }
                    if (Math.abs(height - aft.this.b) < 150) {
                        aft.this.b = height;
                    }
                    if (aft.this.b - height > 150) {
                        if (aft.this.c != null && aft.this.b - height > 250) {
                            aft.this.a.getHeight();
                            int i = rect.bottom;
                            aft.this.c.a();
                        }
                        aft.this.b = height;
                        return;
                    }
                    if (height - aft.this.b > 150) {
                        if (aft.this.c != null && height - aft.this.b > 250) {
                            aft.this.c.b();
                        }
                        aft.this.b = height;
                    }
                }
            }
        }
    };

    /* renamed from: aft$a */
    /* compiled from: SoftKeyBoardListener */
    public interface a {
        void a();

        void b();
    }

    public aft(Activity activity) {
        if (activity != null) {
            this.a = activity.getWindow().getDecorView();
        }
    }
}
