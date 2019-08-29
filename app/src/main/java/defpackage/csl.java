package defpackage;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.alipay.sdk.util.h;
import com.autonavi.map.widget.ITrafficViewForFeed;
import com.autonavi.minimap.R;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.e;

/* renamed from: csl reason: default package */
/* compiled from: RelatedTrafficEventViewImpl */
public final class csl implements b {
    private final String a = getClass().getSimpleName();
    private cry b;
    private Context c;
    private LayoutInflater d;
    private ViewGroup e;
    private View f;
    private ViewGroup g;
    private SlidingUpPanelLayout h;
    private View i;
    private int j = 0;
    private int k = 0;
    private c l;
    private Handler m = new Handler(Looper.getMainLooper());
    private int n;
    private int o;
    private e p = new e() {
        public final void onPanelSlide(View view, float f) {
            csl.a(csl.this, new Runnable() {
                public final void run() {
                    csl.this.e();
                }
            });
        }

        public final void a() {
            csl.a(csl.this, new Runnable() {
                public final void run() {
                    csl.this.e();
                }
            });
        }

        public final void b() {
            csl.a(csl.this, new Runnable() {
                public final void run() {
                    csl.this.e();
                }
            });
        }

        public final void c() {
            csl.a(csl.this, new Runnable() {
                public final void run() {
                    csl.this.e();
                }
            });
        }

        public final void d() {
            csl.a(csl.this, new Runnable() {
                public final void run() {
                    csl.this.e();
                }
            });
        }
    };

    public csl(cry cry, ViewGroup viewGroup) {
        if (viewGroup == null) {
            throw new RuntimeException("presenter or container can not be null!");
        }
        this.b = cry;
        this.e = viewGroup;
        this.c = viewGroup.getContext();
        this.d = LayoutInflater.from(this.c);
        this.f = this.d.inflate(R.layout.traffic_event_view_layout, this.e, true);
        this.h = (SlidingUpPanelLayout) this.f.findViewById(R.id.traffic_slidingview);
        this.g = (ViewGroup) this.f.findViewById(R.id.traffic_event_container);
        this.h.setDragEnabled(false);
        this.h.addPanelSlideListener(this.p);
    }

    public final void d() {
        if (this.i != null) {
            StringBuilder sb = new StringBuilder("transmit surface change event for  {");
            sb.append(this.i);
            sb.append(h.d);
            if (this.i instanceof ITrafficViewForFeed) {
                ((ITrafficViewForFeed) this.i).onMapSurfaceChanged();
            }
        }
    }

    public final void a(View view) {
        if (view != null) {
            if (this.i != null) {
                StringBuilder sb = new StringBuilder("Clean old view {");
                sb.append(this.i);
                sb.append(h.d);
                ((ITrafficViewForFeed) this.i).dismiss(true);
                ((ITrafficViewForFeed) this.i).clearTrafficItem();
            }
            this.g.removeAllViews();
            this.g.addView(view);
            this.i = view;
            this.g.requestLayout();
            View view2 = this.i;
            view2.measure(MeasureSpec.makeMeasureSpec(view2.getResources().getDisplayMetrics().widthPixels, Integer.MIN_VALUE), 0);
            this.j = view2.getMeasuredHeight();
        }
    }

    public final void a(int i2, int i3, boolean z) {
        this.j = i2;
        this.h.setPanelHeight(i2);
        this.k = i3;
        if (z) {
            this.h.setPanelState(PanelState.EXPANDED, false);
            this.g.setBackgroundColor(-1);
        } else {
            this.g.setBackgroundColor(0);
        }
        e();
    }

    public final void a() {
        if (this.h != null) {
            this.h.showPanel();
            this.h.setPanelState(PanelState.COLLAPSED, false);
            if (this.l != null) {
                this.l.f();
            }
        }
    }

    public final void b() {
        if (this.h != null && c()) {
            this.h.getPanelState();
            this.h.setPanelState(PanelState.HIDDEN, false);
            if (this.i != null) {
                StringBuilder sb = new StringBuilder("Release view {");
                sb.append(this.i);
                sb.append(h.d);
                if (this.i instanceof ITrafficViewForFeed) {
                    ((ITrafficViewForFeed) this.i).clearTrafficItem();
                    ((ITrafficViewForFeed) this.i).dismiss(true);
                }
            }
            if (this.l != null) {
                this.l.g();
            }
        }
    }

    public final boolean c() {
        if (this.h == null || this.h.getPanelState() == PanelState.HIDDEN) {
            return false;
        }
        View slideableView = this.h.getSlideableView();
        if (slideableView == null || slideableView.getVisibility() == 0) {
            return true;
        }
        return false;
    }

    public final void a(c cVar) {
        this.l = cVar;
    }

    /* access modifiers changed from: 0000 */
    public final void e() {
        if (this.h != null) {
            this.n = this.h.getHeight();
            this.o = this.h.getSlideOffsetHeight() - this.k;
        }
    }

    static /* synthetic */ void a(csl csl, Runnable runnable) {
        if (VERSION.SDK_INT <= 18) {
            csl.m.post(runnable);
        } else {
            runnable.run();
        }
    }
}
