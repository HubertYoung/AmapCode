package defpackage;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.uiperformance.page.UIPerformanceConfigPage;
import java.util.HashMap;

/* renamed from: ems reason: default package */
/* compiled from: MonitorView */
public final class ems implements emq, emr {
    LinearLayout a;
    WindowManager b;
    public Context c;
    GestureDetector d;
    volatile boolean e;
    HashMap<String, TextView> f = new HashMap<>();
    private Handler g;

    /* renamed from: ems$a */
    /* compiled from: MonitorView */
    class a implements OnTouchListener {
        private int b;
        private int c;
        private float d;
        private float e;
        private LayoutParams f;
        private WindowManager g;

        public a(LayoutParams layoutParams, WindowManager windowManager) {
            this.g = windowManager;
            this.f = layoutParams;
        }

        public final boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case 0:
                    this.b = this.f.x;
                    this.c = this.f.y;
                    this.d = motionEvent.getRawX();
                    this.e = motionEvent.getRawY();
                    break;
                case 2:
                    this.f.x = this.b - ((int) (motionEvent.getRawX() - this.d));
                    this.f.y = this.c + ((int) (motionEvent.getRawY() - this.e));
                    this.g.updateViewLayout(view, this.f);
                    break;
            }
            return ems.this.d.onTouchEvent(motionEvent);
        }
    }

    /* renamed from: ems$b */
    /* compiled from: MonitorView */
    class b extends SimpleOnGestureListener {
        public final boolean onContextClick(MotionEvent motionEvent) {
            return false;
        }

        public final boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return false;
        }

        public final boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public final void onLongPress(MotionEvent motionEvent) {
        }

        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public final void onShowPress(MotionEvent motionEvent) {
        }

        public final boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return false;
        }

        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        public b() {
        }

        public final boolean onDoubleTap(MotionEvent motionEvent) {
            bid pageContext = AMapPageUtil.getPageContext();
            if ((pageContext instanceof AbstractBasePage) && !(pageContext instanceof UIPerformanceConfigPage)) {
                ((AbstractBasePage) pageContext).startPage(UIPerformanceConfigPage.class, (PageBundle) null);
            }
            return true;
        }
    }

    public ems(Context context) {
        this.b = (WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY);
        this.c = context;
        this.g = new Handler(Looper.getMainLooper());
    }

    public final void a(final String str, final String str2, final boolean z) {
        this.g.post(new Runnable() {
            public final void run() {
                if (str != null && str2 != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(": ");
                    sb.append(str2);
                    String sb2 = sb.toString();
                    TextView textView = ems.this.f.get(str);
                    if (textView == null) {
                        textView = new TextView(ems.this.a.getContext());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                        layoutParams.setMargins(8, 8, 8, 8);
                        textView.setLayoutParams(layoutParams);
                        textView.setTextSize(15.0f);
                        ems.this.a.addView(textView);
                        ems.this.f.put(str, textView);
                    }
                    if (z) {
                        textView.setTextColor(ems.this.c.getResources().getColor(R.color.monitor_nomal_color));
                    } else {
                        textView.setTextColor(ems.this.c.getResources().getColor(R.color.monitor_error_color));
                    }
                    textView.setText(sb2);
                }
            }
        });
    }

    public final void a(final String str) {
        if (str != null && !str.isEmpty()) {
            this.g.post(new Runnable() {
                public final void run() {
                    TextView textView = ems.this.f.get(str);
                    if (textView != null) {
                        ems.this.a.removeView(textView);
                        ems.this.f.remove(str);
                    }
                }
            });
        }
    }

    public final void a() {
        if (!this.e && this.g != null) {
            this.g.post(new Runnable() {
                public final void run() {
                    ems ems = ems.this;
                    if (ems.a == null) {
                        ems.a = new LinearLayout(ems.c);
                        ems.a.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                        ems.a.setBackgroundResource(R.drawable.monitor_bg);
                        ems.a.setOrientation(1);
                    }
                    ems.a.setVisibility(0);
                    LayoutParams layoutParams = new LayoutParams(-2, -2, VERSION.SDK_INT >= 26 ? 2038 : 2002, 8, -3);
                    layoutParams.gravity = 8388661;
                    layoutParams.x = 20;
                    layoutParams.y = 20;
                    ems.b.addView(ems.a, layoutParams);
                    ems.a.setOnTouchListener(new a(layoutParams, ems.b));
                    ems.a.setHapticFeedbackEnabled(false);
                    ems.d = new GestureDetector(ems.c, new b());
                    ems.d.setOnDoubleTapListener(new b());
                    ems.e = true;
                }
            });
        }
    }

    public final void b() {
        this.g.post(new Runnable() {
            public final void run() {
                ems.this.a.setVisibility(8);
                ems.this.b.removeView(ems.this.a);
                ems.this.e = false;
            }
        });
    }
}
