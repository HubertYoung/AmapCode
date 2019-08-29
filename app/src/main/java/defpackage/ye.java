package defpackage;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.internal.view.SupportMenu;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ajx3.util.DimensionUtils;

/* renamed from: ye reason: default package */
/* compiled from: IMDebuger */
public class ye {
    public static boolean a = false;
    private static volatile ye d;
    public TextView b;
    public Handler c;
    private WindowManager e;
    private LayoutParams f;

    private ye() {
        if (a) {
            this.c = new Handler(Looper.getMainLooper());
            if (Looper.myLooper() == Looper.getMainLooper()) {
                c();
            } else {
                this.c.post(new Runnable() {
                    public final void run() {
                        ye.this.c();
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        this.e = (WindowManager) AMapAppGlobal.getApplication().getSystemService(TemplateTinyApp.WINDOW_KEY);
        this.b = new TextView(AMapAppGlobal.getTopActivity());
        this.b.setTextColor(SupportMenu.CATEGORY_MASK);
        this.b.setGravity(3);
        this.b.setBackgroundColor(Color.argb(40, 0, 0, 0));
        this.b.setTextSize((float) DimensionUtils.dipToPixel(3.0f));
        this.b.setVerticalScrollBarEnabled(true);
        this.b.setMovementMethod(ScrollingMovementMethod.getInstance());
        this.b.setLayoutParams(new ViewGroup.LayoutParams(-1, DimensionUtils.dipToPixel(35.0f)));
        this.f = new LayoutParams();
        this.f.x = 0;
        this.f.y = 0;
        this.f.width = -1;
        this.f.height = DimensionUtils.dipToPixel(35.0f);
        this.f.type = 2002;
        this.f.format = 1;
        this.f.flags = 8;
        this.f.gravity = 8388659;
        this.e.addView(this.b, this.f);
    }

    public static void a(boolean z) {
        ye b2 = b();
        if (!(!a || b2.e == null || b2.b == null)) {
            b2.e.removeView(b2.b);
            b2.b = null;
            b2.e = null;
        }
        d = null;
        a = z;
    }

    public static boolean a() {
        return a;
    }

    public static synchronized ye b() {
        ye yeVar;
        synchronized (ye.class) {
            if (d == null) {
                synchronized (ye.class) {
                    if (d == null) {
                        d = new ye();
                    }
                }
            }
            yeVar = d;
        }
        return yeVar;
    }

    public final void a(final String str) {
        if (!a || TextUtils.isEmpty(str) || this.b == null) {
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.b.append(str);
            this.b.setScrollY(this.b.getLineCount());
            return;
        }
        this.c.post(new Runnable() {
            public final void run() {
                ye.this.b.append("\n");
                ye.this.b.append(str);
            }
        });
    }
}
