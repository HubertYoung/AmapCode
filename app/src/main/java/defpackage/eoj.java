package defpackage;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.minimap.R;
import java.io.File;

/* renamed from: eoj reason: default package */
/* compiled from: RecordAbstractFloatWindow */
public final class eoj extends eny implements OnClickListener {
    /* access modifiers changed from: private */
    public static eoj a;
    /* access modifiers changed from: private */
    public boolean b = false;
    /* access modifiers changed from: private */
    public WindowManager c;
    /* access modifiers changed from: private */
    public LayoutParams d;
    /* access modifiers changed from: private */
    public Context e;
    /* access modifiers changed from: private */
    public LinearLayout f;
    private a g = new a();
    /* access modifiers changed from: private */
    public Button h = null;
    private Button i = null;
    private EditText j = null;
    /* access modifiers changed from: private */
    public Activity k = null;

    /* renamed from: eoj$a */
    /* compiled from: RecordAbstractFloatWindow */
    class a extends Handler {
        a() {
        }

        public final void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    eoj.this.k = (Activity) message.obj;
                    try {
                        IBinder windowToken = eoj.this.k.getWindow().getDecorView().getWindowToken();
                        if (windowToken != null && !eoj.this.b) {
                            eoj.this.d.token = windowToken;
                            eoj.this.c.addView(eoj.this.f, eoj.this.d);
                            eoj.this.b = true;
                            if (eof.e()) {
                                eoj.this.h.setText("结束");
                            }
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                case 1:
                    eoj.a.f();
                    break;
            }
        }
    }

    /* renamed from: eoj$b */
    /* compiled from: RecordAbstractFloatWindow */
    class b implements OnTouchListener {
        private int b;
        private int c;

        private b() {
        }

        /* synthetic */ b(eoj eoj, byte b2) {
            this();
        }

        public final boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.b = (int) motionEvent.getRawX();
                this.c = (int) motionEvent.getRawY();
            } else if (action == 2) {
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                int i = rawY - this.c;
                this.b = rawX;
                this.c = rawY;
                LayoutParams c2 = eoj.this.d;
                c2.x = eoj.this.d.x + (rawX - this.b);
                eoj.this.d.y += i;
                eoj.this.c.updateViewLayout(view, eoj.this.d);
            }
            return false;
        }
    }

    public static synchronized eoj a(Context context) {
        eoj eoj;
        synchronized (eoj.class) {
            try {
                if (a == null) {
                    eoj eoj2 = new eoj(context);
                    a = eoj2;
                    eoj2.e();
                }
                eoj = a;
            }
        }
        return eoj;
    }

    private eoj(Context context) {
        this.e = context;
    }

    /* access modifiers changed from: protected */
    public final void a() throws NullPointerException {
        LayoutInflater layoutInflater = (LayoutInflater) this.e.getSystemService("layout_inflater");
        if (layoutInflater != null) {
            this.f = (LinearLayout) layoutInflater.inflate(R.layout.profile_float_window, null);
            if (this.f != null) {
                this.h = (Button) this.f.findViewById(R.id.profile_button_start);
                this.i = (Button) this.f.findViewById(R.id.profile_button_end);
            }
            if (eof.e()) {
                this.h.setText("结束");
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void b() {
        this.c = (WindowManager) this.e.getSystemService(TemplateTinyApp.WINDOW_KEY);
    }

    /* access modifiers changed from: protected */
    public final void c() {
        LayoutParams layoutParams = new LayoutParams(-2, -2, 1003, 520, -3);
        this.d = layoutParams;
        this.d.gravity = 51;
        this.d.x = 10;
        this.d.y = this.e.getResources().getDisplayMetrics().heightPixels / 2;
    }

    /* access modifiers changed from: protected */
    public final void d() {
        this.f.setOnTouchListener(new b(this, 0));
        this.h.setOnClickListener(this);
        this.i.setOnClickListener(this);
    }

    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.profile_button_start) {
            if ("开始".equals(this.h.getText())) {
                f();
                eoa eoa = new eoa(this.e);
                IBinder windowToken = eod.a().getWindow().getDecorView().getWindowToken();
                if (windowToken != null && !eoa.c) {
                    eoa.d.token = windowToken;
                    eoa.b.addView(eoa.a, eoa.d);
                    eoa.c = true;
                }
            } else {
                this.h.setText("开始");
                eof.a(this.e);
                File file = new File(eof.d());
                StringBuilder sb = new StringBuilder();
                sb.append(System.currentTimeMillis());
                eoc.a(file, sb.toString());
                eof.a(this.e);
                eof.a(false);
            }
        }
        if (id == R.id.profile_button_end) {
            new Builder(eod.a()).setTitle("重启录制").setMessage("删除刚录制case").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface dialogInterface, int i) {
                    eof.c();
                    eoj.this.h.setText("开始");
                    eof.a(eoj.this.e);
                    File file = new File(eof.d());
                    if (file.exists() && file.isFile()) {
                        file.delete();
                    }
                    eof.a(eoj.this.e);
                    eof.a(false);
                    eof.a(eoj.this.e);
                    eof.b("");
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface dialogInterface, int i) {
                }
            }).create().show();
        }
    }

    public final void a(Activity activity) {
        if (activity != null) {
            Message obtain = Message.obtain();
            obtain.what = 0;
            obtain.obj = activity;
            this.g.sendMessage(obtain);
        }
    }

    public final void f() {
        try {
            if (this.b) {
                this.c.removeViewImmediate(this.f);
                this.b = false;
                new StringBuilder().append(System.currentTimeMillis());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
