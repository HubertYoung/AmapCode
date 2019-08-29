package defpackage;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

/* renamed from: wc reason: default package */
/* compiled from: TimeToast */
public final class wc {
    public final Handler a = new Handler();
    final LayoutParams b = new LayoutParams();
    int c = 17;
    int d;
    int e;
    float f;
    float g;
    View h;
    public View i;
    WindowManager j;
    public final Runnable k = new Runnable() {
        public final void run() {
            wc wcVar = wc.this;
            if (wcVar.h != wcVar.i) {
                wcVar.b();
                wcVar.h = wcVar.i;
                int i = wcVar.c;
                wcVar.b.gravity = i;
                if ((i & 7) == 7) {
                    wcVar.b.horizontalWeight = 1.0f;
                }
                if ((i & 112) == 112) {
                    wcVar.b.verticalWeight = 1.0f;
                }
                wcVar.b.x = wcVar.d;
                wcVar.b.y = wcVar.e;
                wcVar.b.verticalMargin = wcVar.g;
                wcVar.b.horizontalMargin = wcVar.f;
                if (wcVar.h.getParent() != null) {
                    wcVar.j.removeView(wcVar.h);
                }
                wcVar.j.addView(wcVar.h, wcVar.b);
            }
        }
    };
    private final Runnable l = new Runnable() {
        public final void run() {
            wc.this.b();
        }
    };

    public wc(Context context) {
        LayoutParams layoutParams = this.b;
        layoutParams.height = -2;
        layoutParams.width = -2;
        layoutParams.flags = 152;
        layoutParams.format = -3;
        layoutParams.windowAnimations = 16973828;
        layoutParams.type = VERSION.SDK_INT >= 26 ? 2038 : 2002;
        layoutParams.setTitle("Toast");
        this.j = (WindowManager) context.getApplicationContext().getSystemService(TemplateTinyApp.WINDOW_KEY);
    }

    public final void a() {
        this.a.post(this.l);
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        if (this.h != null) {
            if (this.h.getParent() != null) {
                this.j.removeView(this.h);
            }
            this.h = null;
        }
    }
}
