package defpackage;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.minimap.R;
import org.json.JSONObject;

/* renamed from: cxg reason: default package */
/* compiled from: ProblemFloatWindow */
public class cxg extends cxf implements OnClickListener {
    /* access modifiers changed from: private */
    @SuppressLint({"StaticFieldLeak"})
    public static cxg d;
    public b a = new b();
    public JSONObject b;
    public JSONObject c;
    /* access modifiers changed from: private */
    public boolean e = false;
    /* access modifiers changed from: private */
    public WindowManager f;
    /* access modifiers changed from: private */
    public LayoutParams g;
    /* access modifiers changed from: private */
    public LinearLayout h;
    private Button i = null;
    private Button j = null;

    /* renamed from: cxg$a */
    /* compiled from: ProblemFloatWindow */
    class a extends Thread {
        a() {
        }

        public final void run() {
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message obtain = Message.obtain();
            obtain.what = 1;
            cxg.this.a.sendMessage(obtain);
        }
    }

    /* renamed from: cxg$b */
    /* compiled from: ProblemFloatWindow */
    public class b extends Handler {
        b() {
        }

        public final void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    try {
                        IBinder windowToken = cxk.a().b.getWindow().getDecorView().getWindowToken();
                        if (windowToken != null && !cxg.this.e) {
                            cxg.this.g.token = windowToken;
                            cxg.this.f.addView(cxg.this.h, cxg.this.g);
                            cxg.this.e = true;
                            new a().start();
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                case 1:
                    cxl.b(5, cxg.this.b, cxg.this.c);
                    cxg.d.g();
                    break;
            }
        }
    }

    public static cxg f() {
        if (d == null) {
            synchronized (cxg.class) {
                try {
                    if (d == null) {
                        cxg cxg = new cxg();
                        d = cxg;
                        cxg.e();
                    }
                }
            }
        }
        return d;
    }

    private cxg() {
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"InflateParams"})
    public final void a() throws NullPointerException {
        LayoutInflater layoutInflater = (LayoutInflater) cxk.a().a.getSystemService("layout_inflater");
        if (layoutInflater != null) {
            this.h = (LinearLayout) layoutInflater.inflate(R.layout.evaluate_float_window, null);
            if (this.h != null) {
                this.i = (Button) this.h.findViewById(R.id.evaluate_button_user_insight_no_problem);
                this.j = (Button) this.h.findViewById(R.id.evaluate_button_user_insight_feedback);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void b() {
        this.f = (WindowManager) cxk.a().a.getSystemService(TemplateTinyApp.WINDOW_KEY);
    }

    /* access modifiers changed from: protected */
    public final void c() {
        LayoutParams layoutParams = new LayoutParams(-2, -2, 1003, 520, -3);
        this.g = layoutParams;
        this.g.gravity = 8388661;
        this.g.x = 0;
        this.g.y = cxk.a().a.getResources().getDisplayMetrics().heightPixels / 2;
    }

    /* access modifiers changed from: protected */
    public final void d() {
        this.h.setOnClickListener(this);
        this.i.setOnClickListener(this);
        this.j.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.evaluate_button_user_insight_no_problem) {
            d.g();
            cxl.b(1, this.b, this.c);
            return;
        }
        if (id == R.id.evaluate_button_user_insight_feedback) {
            d.g();
            cxe cxe = new cxe();
            JSONObject jSONObject = this.b;
            JSONObject jSONObject2 = this.c;
            cxe.e = jSONObject;
            cxe.f = jSONObject2;
            IBinder windowToken = cxk.a().b.getWindow().getDecorView().getWindowToken();
            if (windowToken != null && !cxe.c) {
                cxe.d.token = windowToken;
                cxe.b.addView(cxe.a, cxe.d);
                cxe.c = true;
            }
        }
    }

    public final void g() {
        try {
            if (this.e) {
                this.f.removeViewImmediate(this.h);
                this.e = false;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
