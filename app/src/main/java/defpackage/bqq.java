package defpackage;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.iflytek.tts.TtsService.Tts;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

/* renamed from: bqq reason: default package */
/* compiled from: AppAlert */
public final class bqq implements OnClickListener {
    Context a;
    /* access modifiers changed from: 0000 */
    public volatile boolean b;
    TextView c;
    CharSequence d;
    /* access modifiers changed from: 0000 */
    public Handler e = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: 0000 */
    public Runnable f = new Runnable() {
        public final void run() {
            boolean z;
            if (bqq.this.b) {
                bqq bqq = bqq.this;
                Iterator<RunningAppProcessInfo> it = ((ActivityManager) bqq.a.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses().iterator();
                while (true) {
                    z = false;
                    if (!it.hasNext()) {
                        break;
                    }
                    RunningAppProcessInfo next = it.next();
                    if (next.processName.equals(bqq.a.getPackageName())) {
                        if (next.importance == 400) {
                            z = true;
                        }
                    }
                }
                if (z) {
                    if (bqq.this.i) {
                        bqq.this.b();
                    }
                } else if (!bqq.this.i) {
                    bqq.this.a();
                }
                bqq.this.e.postDelayed(bqq.this.f, 40);
            }
        }
    };
    volatile long g;
    private Toast h;
    /* access modifiers changed from: private */
    public boolean i;
    private Object j;
    private Method k;
    private Method l;
    private Field m;
    private View n;

    public bqq(Context context) {
        this.a = context;
        int a2 = a(8);
        RelativeLayout relativeLayout = new RelativeLayout(this.a);
        relativeLayout.setBackgroundColor(0);
        relativeLayout.setGravity(81);
        relativeLayout.setPadding(a2, 0, a2, a2);
        relativeLayout.setLayoutParams(new LayoutParams(-1, -1));
        this.c = new TextView(this.a);
        this.c.setVerticalScrollBarEnabled(true);
        this.c.setMovementMethod(new ScrollingMovementMethod());
        this.c.setPadding(a2, a2, a2, a2);
        this.c.setTextSize(8.0f);
        this.c.setTextColor(-1);
        this.c.setBackgroundColor(-16777216);
        this.c.setMaxHeight(a((int) Tts.TTS_STATE_DESTROY));
        this.c.setLayoutParams(new LayoutParams(-2, -2));
        relativeLayout.addView(this.c);
        this.n = relativeLayout;
        this.c.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        this.h = new Toast(this.a);
        this.h.setView(this.n);
        this.h.setGravity(83, 0, 0);
        try {
            Field declaredField = this.h.getClass().getDeclaredField("mTN");
            declaredField.setAccessible(true);
            this.j = declaredField.get(this.h);
            Class<?> cls = this.j.getClass();
            this.k = cls.getMethod(ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW, new Class[0]);
            this.l = cls.getMethod(ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_HIDE, new Class[0]);
            this.m = cls.getDeclaredField("mNextView");
            this.m.setAccessible(true);
            Field declaredField2 = cls.getDeclaredField("mParams");
            declaredField2.setAccessible(true);
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) declaredField2.get(this.j);
            layoutParams.flags = 40;
            layoutParams.width = -1;
            layoutParams.height = -1;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        try {
            if (this.m != null) {
                this.m.set(this.j, this.n);
            }
            if (this.k != null) {
                this.k.invoke(this.j, new Object[0]);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.i = true;
    }

    /* access modifiers changed from: private */
    public void b() {
        try {
            if (this.l != null) {
                this.l.invoke(this.j, new Object[0]);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.i = false;
        this.e.removeCallbacks(this.f);
    }

    private int a(int i2) {
        return (int) TypedValue.applyDimension(1, (float) i2, this.a.getResources().getDisplayMetrics());
    }

    public final void onClick(View view) {
        if (this.b) {
            this.b = false;
            b();
        }
        ((ClipboardManager) this.a.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text", this.d));
        this.g = System.currentTimeMillis();
    }
}
