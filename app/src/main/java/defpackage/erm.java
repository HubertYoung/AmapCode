package defpackage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* renamed from: erm reason: default package */
/* compiled from: AlertController */
public final class erm {
    public TextView A;
    public View B;
    public ListAdapter C;
    public int D;
    public int E;
    public int F;
    Handler G;
    public OnClickListener H = new OnClickListener() {
        public final void onClick(View view) {
            Message message = (view != erm.this.m || erm.this.o == null) ? (view != erm.this.p || erm.this.r == null) ? (view != erm.this.s || erm.this.u == null) ? null : Message.obtain(erm.this.u) : Message.obtain(erm.this.r) : Message.obtain(erm.this.o);
            if (message != null) {
                message.sendToTarget();
            }
            erm.this.G.obtainMessage(1, erm.this.b).sendToTarget();
        }
    };
    public final Context a;
    public AlertView b;
    public View c;
    public CharSequence d;
    public CharSequence e;
    public ListView f;
    public View g;
    public int h;
    public int i;
    public int j;
    public int k;
    public boolean l = false;
    public Button m;
    public CharSequence n;
    Message o;
    public Button p;
    public CharSequence q;
    Message r;
    public Button s;
    public CharSequence t;
    Message u;
    public ScrollView v;
    public int w = 0;
    public Drawable x;
    public ImageView y;
    public TextView z;

    /* renamed from: erm$a */
    /* compiled from: AlertController */
    public static class a {
        public final Context a;
        public final LayoutInflater b;
        public int c = 0;
        public Drawable d;
        public int e = 0;
        public CharSequence f;
        public View g;
        public CharSequence h;
        public CharSequence i;
        public defpackage.ern.a j;
        public CharSequence k;
        public defpackage.ern.a l;
        public CharSequence m;
        public defpackage.ern.a n;
        public boolean o;
        public boolean p;
        public CharSequence[] q;
        public ListAdapter r;
        public defpackage.ern.a s;
        public View t;
        public int u;
        public int v;
        public int w;
        public int x;
        public boolean y = false;
        public OnItemSelectedListener z;

        public a(Context context) {
            this.a = context;
            this.o = false;
            this.p = false;
            this.b = (LayoutInflater) context.getSystemService("layout_inflater");
        }
    }

    /* renamed from: erm$b */
    /* compiled from: AlertController */
    static final class b extends Handler {
        private WeakReference<AlertView> a;

        public b(AlertView alertView) {
            this.a = new WeakReference<>(alertView);
        }

        public final void handleMessage(Message message) {
            switch (message.what) {
                case -3:
                case -2:
                case -1:
                    ((defpackage.ern.a) message.obj).onClick((AlertView) this.a.get(), message.what);
                    return;
                default:
                    return;
            }
        }
    }

    public erm(Context context, AlertView alertView) {
        this.a = context;
        this.b = alertView;
        this.G = new b(alertView);
        this.D = R.layout.view_alert;
        this.E = R.layout.alert_select_list;
        this.F = R.layout.alert_select_item;
    }

    public final void a(int i2, CharSequence charSequence, defpackage.ern.a aVar, Message message) {
        if (aVar != null) {
            message = this.G.obtainMessage(i2, aVar);
        }
        switch (i2) {
            case -3:
                this.t = charSequence;
                this.u = message;
                return;
            case -2:
                this.q = charSequence;
                this.r = message;
                return;
            case -1:
                this.n = charSequence;
                this.o = message;
                return;
            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    public final void a(int i2) {
        this.w = i2;
        if (this.y != null) {
            if (i2 > 0) {
                this.y.setImageResource(this.w);
            } else if (i2 == 0) {
                this.y.setVisibility(8);
            }
        }
    }

    public final void a(View view, View view2, View view3, View view4, View view5, View view6, boolean z2) {
        boolean z3;
        boolean z4;
        if (z2) {
            ArrayList arrayList = new ArrayList();
            if (this.p.getVisibility() == 0) {
                arrayList.add(this.p);
            }
            if (this.s.getVisibility() == 0) {
                arrayList.add(this.s);
                z3 = true;
            } else {
                z3 = false;
            }
            if (this.m.getVisibility() == 0) {
                arrayList.add(this.m);
                z4 = true;
            } else {
                z4 = false;
            }
            if (!z4 || arrayList.size() == 1) {
                view3.setVisibility(8);
            }
            if (!z3 || arrayList.size() == 1) {
                view4.setVisibility(8);
            }
            if (z3 && arrayList.size() == 2) {
                if (z4) {
                    view3.setVisibility(8);
                } else {
                    view4.setVisibility(8);
                }
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                int size = arrayList.size();
                ShapeDrawable a2 = a(i2, this.a.getResources().getColor(R.color.c_1), size);
                ShapeDrawable a3 = a(i2, this.a.getResources().getColor(R.color.c_3), size);
                StateListDrawable stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(new int[]{16842919}, a3);
                stateListDrawable.addState(new int[0], a2);
                ((View) arrayList.get(i2)).setBackgroundDrawable(stateListDrawable);
            }
        }
        if (!(view5.getVisibility() == 0 || view6.getVisibility() == 0)) {
            view.setPadding(0, 0, 0, this.a.getResources().getDimensionPixelOffset(R.dimen.alert_view_content_padding));
        }
        if (this.f != null && this.C != null) {
            this.f.setAdapter(this.C);
            this.f.setBackgroundResource(R.color.c_1);
            this.c.setBackgroundResource(R.drawable.bg_alert_top_pannel);
            view.setPadding(0, 0, 0, this.a.getResources().getDimensionPixelOffset(R.dimen.alert_view_top_padding));
            view5.setPadding(0, 0, 0, this.a.getResources().getDimensionPixelOffset(R.dimen.alert_view_content_padding));
            view2.setVisibility(0);
        }
    }

    private ShapeDrawable a(int i2, int i3, int i4) {
        float[] fArr;
        int dimensionPixelOffset = this.a.getResources().getDimensionPixelOffset(R.dimen.title_tab_radius);
        if (i4 == 1) {
            float f2 = (float) dimensionPixelOffset;
            fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f, f2, f2, f2, f2};
        } else if (i2 == 0) {
            float f3 = (float) dimensionPixelOffset;
            fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f3, f3};
        } else if (i2 == i4 - 1) {
            float f4 = (float) dimensionPixelOffset;
            fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f, f4, f4, 0.0f, 0.0f};
        } else {
            fArr = null;
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr, null, null));
        shapeDrawable.getPaint().setColor(i3);
        return shapeDrawable;
    }
}
