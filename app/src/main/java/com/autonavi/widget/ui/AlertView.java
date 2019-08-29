package com.autonavi.widget.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.IViewLayerExt;
import com.autonavi.minimap.R;

public class AlertView extends LinearLayout implements OnTouchListener, IViewLayer, IViewLayerExt {
    /* access modifiers changed from: private */
    public erm mAlert = new erm(getContext(), this);
    private boolean mCancelable = false;
    private defpackage.ern.a mOnBackClickListener;
    private defpackage.ern.a mOnOutSideClickListener;
    private boolean mShouldInterruptBackEvent = false;

    public static class a {
        public final defpackage.erm.a a;
        public defpackage.ern.a b;
        public defpackage.ern.a c;

        public a(Context context) {
            this.a = new defpackage.erm.a(context);
        }

        public final a a(int i) {
            this.a.f = this.a.a.getText(i);
            return this;
        }

        public final a a(CharSequence charSequence) {
            this.a.f = charSequence;
            return this;
        }

        public final a b(int i) {
            this.a.h = this.a.a.getText(i);
            return this;
        }

        public final a b(CharSequence charSequence) {
            this.a.h = charSequence;
            return this;
        }

        public final a a(int i, defpackage.ern.a aVar) {
            this.a.i = this.a.a.getText(i);
            this.a.j = aVar;
            return this;
        }

        public final a a(CharSequence charSequence, defpackage.ern.a aVar) {
            this.a.i = charSequence;
            this.a.j = aVar;
            return this;
        }

        public final a b(int i, defpackage.ern.a aVar) {
            this.a.k = this.a.a.getText(i);
            this.a.l = aVar;
            return this;
        }

        public final a a(boolean z) {
            this.a.o = z;
            return this;
        }

        public final a b(boolean z) {
            this.a.p = z;
            return this;
        }

        public final a b(CharSequence charSequence, defpackage.ern.a aVar) {
            this.a.k = charSequence;
            this.a.l = aVar;
            return this;
        }

        public final a a(CharSequence[] charSequenceArr, defpackage.ern.a aVar) {
            this.a.q = charSequenceArr;
            this.a.s = aVar;
            return this;
        }

        public final a a(View view) {
            this.a.t = view;
            this.a.y = false;
            return this;
        }

        public final AlertView a() {
            char c2;
            AlertView alertView = new AlertView(this.a.a);
            alertView.setOnOutSideClickListener(this.b);
            alertView.setOnBackClickListener(this.c);
            defpackage.erm.a aVar = this.a;
            erm access$000 = alertView.mAlert;
            if (aVar.g != null) {
                access$000.B = aVar.g;
            } else {
                if (aVar.f != null) {
                    CharSequence charSequence = aVar.f;
                    access$000.d = charSequence;
                    if (access$000.z != null) {
                        access$000.z.setText(charSequence);
                    }
                }
                if (aVar.d != null) {
                    Drawable drawable = aVar.d;
                    access$000.x = drawable;
                    if (!(access$000.y == null || access$000.x == null)) {
                        access$000.y.setImageDrawable(drawable);
                    }
                }
                if (aVar.c >= 0) {
                    access$000.a(aVar.c);
                }
                if (aVar.e > 0) {
                    int i = aVar.e;
                    TypedValue typedValue = new TypedValue();
                    access$000.a.getTheme().resolveAttribute(i, typedValue, true);
                    access$000.a(typedValue.resourceId);
                }
            }
            if (aVar.h != null) {
                CharSequence charSequence2 = aVar.h;
                access$000.e = charSequence2;
                if (access$000.A != null) {
                    access$000.A.setText(charSequence2);
                }
            }
            if (aVar.i != null) {
                access$000.a(-1, aVar.i, aVar.j, null);
            }
            if (aVar.k != null) {
                access$000.a(-2, aVar.k, aVar.l, null);
            }
            if (aVar.m != null) {
                access$000.a(-3, aVar.m, aVar.n, null);
            }
            if (!(aVar.q == null && aVar.r == null)) {
                ListView listView = (ListView) aVar.b.inflate(access$000.E, null);
                access$000.C = aVar.r != null ? aVar.r : new ArrayAdapter(aVar.a, access$000.F, R.id.text1, aVar.q);
                if (aVar.s != null) {
                    listView.setOnItemClickListener(new AlertController$AlertParams$1(aVar, access$000));
                }
                if (aVar.z != null) {
                    listView.setOnItemSelectedListener(aVar.z);
                }
                access$000.f = listView;
            }
            if (aVar.t != null) {
                if (aVar.y) {
                    View view = aVar.t;
                    int i2 = aVar.u;
                    int i3 = aVar.v;
                    int i4 = aVar.w;
                    int i5 = aVar.x;
                    access$000.g = view;
                    access$000.l = true;
                    access$000.h = i2;
                    access$000.i = i3;
                    access$000.j = i4;
                    access$000.k = i5;
                } else {
                    access$000.g = aVar.t;
                    access$000.l = false;
                }
            }
            erm access$0002 = alertView.mAlert;
            LayoutInflater.from(access$0002.a).inflate(access$0002.D, access$0002.b, true);
            access$0002.c = access$0002.b.findViewById(R.id.parentPanel);
            LinearLayout linearLayout = (LinearLayout) access$0002.b.findViewById(R.id.contentPanel);
            access$0002.v = (ScrollView) access$0002.b.findViewById(R.id.scrollView);
            if (access$0002.v instanceof MaxHeightScrollView) {
                ((MaxHeightScrollView) access$0002.v).setMaxHeight(access$0002.a.getResources().getDimensionPixelOffset(R.dimen.alert_view_content_max_height));
            }
            access$0002.v.setFocusable(false);
            access$0002.A = (TextView) access$0002.b.findViewById(R.id.message);
            if (access$0002.A != null) {
                if (access$0002.e != null) {
                    access$0002.A.setText(access$0002.e);
                } else {
                    access$0002.A.setVisibility(8);
                    access$0002.v.removeView(access$0002.A);
                    if (access$0002.f != null) {
                        linearLayout.removeView(access$0002.b.findViewById(R.id.scrollView));
                        linearLayout.addView(access$0002.f, new LayoutParams(-1, -1));
                        linearLayout.setLayoutParams(new LayoutParams(-1, 0, 1.0f));
                    } else {
                        linearLayout.setVisibility(8);
                    }
                }
            }
            access$0002.p = (Button) access$0002.b.findViewById(R.id.button1);
            access$0002.p.setOnClickListener(access$0002.H);
            if (TextUtils.isEmpty(access$0002.q)) {
                access$0002.p.setVisibility(8);
                c2 = 0;
            } else {
                access$0002.p.setText(access$0002.q);
                access$0002.p.setVisibility(0);
                c2 = 2;
            }
            access$0002.s = (Button) access$0002.b.findViewById(R.id.button3);
            access$0002.s.setOnClickListener(access$0002.H);
            if (TextUtils.isEmpty(access$0002.t)) {
                access$0002.s.setVisibility(8);
            } else {
                access$0002.s.setText(access$0002.t);
                access$0002.s.setVisibility(0);
                c2 |= 4;
            }
            access$0002.m = (Button) access$0002.b.findViewById(R.id.button2);
            access$0002.m.setOnClickListener(access$0002.H);
            access$0002.p.setAllCaps(false);
            access$0002.m.setAllCaps(false);
            if (TextUtils.isEmpty(access$0002.n)) {
                access$0002.m.setVisibility(8);
            } else {
                access$0002.m.setText(access$0002.n);
                access$0002.m.setVisibility(0);
                c2 |= 1;
            }
            boolean z = c2 != 0;
            View findViewById = access$0002.b.findViewById(R.id.divide_left);
            View findViewById2 = access$0002.b.findViewById(R.id.divide_right);
            LinearLayout linearLayout2 = (LinearLayout) access$0002.b.findViewById(R.id.topPanel);
            if (access$0002.B != null) {
                linearLayout2.addView(access$0002.B, 0, new LayoutParams(-1, -2));
                access$0002.b.findViewById(R.id.title_template).setVisibility(8);
            } else {
                boolean z2 = !TextUtils.isEmpty(access$0002.d);
                access$0002.y = (ImageView) access$0002.b.findViewById(R.id.icon);
                if (z2) {
                    access$0002.z = (TextView) access$0002.b.findViewById(R.id.alertTitle);
                    access$0002.z.setText(access$0002.d);
                    if (access$0002.w > 0) {
                        access$0002.y.setImageResource(access$0002.w);
                    } else if (access$0002.x != null) {
                        access$0002.y.setImageDrawable(access$0002.x);
                    } else if (access$0002.w == 0) {
                        access$0002.y.setVisibility(8);
                    }
                } else {
                    access$0002.b.findViewById(R.id.title_template).setVisibility(8);
                    access$0002.y.setVisibility(8);
                    linearLayout2.setVisibility(8);
                }
            }
            View findViewById3 = access$0002.b.findViewById(R.id.titleDivider);
            View findViewById4 = access$0002.b.findViewById(R.id.buttonPanel);
            if (!z) {
                findViewById4.setVisibility(8);
            }
            FrameLayout frameLayout = (FrameLayout) access$0002.b.findViewById(R.id.customPanel);
            if (access$0002.g != null) {
                FrameLayout frameLayout2 = (FrameLayout) access$0002.b.findViewById(R.id.custom);
                frameLayout2.addView(access$0002.g, new FrameLayout.LayoutParams(-1, -1));
                if (access$0002.l) {
                    frameLayout2.setPadding(access$0002.h, access$0002.i, access$0002.j, access$0002.k);
                }
                if (access$0002.f != null) {
                    ((LayoutParams) frameLayout.getLayoutParams()).weight = 0.0f;
                }
                frameLayout.setVisibility(0);
            } else {
                frameLayout.setVisibility(8);
            }
            access$0002.a(access$0002.b.findViewById(R.id.title_template), findViewById3, findViewById, findViewById2, linearLayout, frameLayout, z);
            alertView.setCancelable(this.a.o);
            alertView.setShouldInterruptBackEvent(this.a.p);
            return alertView;
        }
    }

    public View getView() {
        return this;
    }

    public void showBackground(boolean z) {
    }

    protected AlertView(Context context) {
        super(context);
        setGravity(17);
        setBackgroundResource(R.color.c_5_b);
        setOnTouchListener(this);
    }

    public void setCancelable(boolean z) {
        this.mCancelable = z;
    }

    public boolean isCancelable() {
        return this.mCancelable;
    }

    public void setShouldInterruptBackEvent(boolean z) {
        this.mShouldInterruptBackEvent = z;
    }

    public boolean isShouldInterruptBackEvent() {
        return this.mShouldInterruptBackEvent;
    }

    public void startAnimation() {
        erm erm = this.mAlert;
        ObjectAnimator duration = ObjectAnimator.ofFloat(erm.c, "scaleX", new float[]{0.8f, 1.0f}).setDuration(300);
        ObjectAnimator duration2 = ObjectAnimator.ofFloat(erm.c, "scaleY", new float[]{0.8f, 1.0f}).setDuration(300);
        ObjectAnimator duration3 = ObjectAnimator.ofFloat(erm.c, "alpha", new float[]{0.5f, 1.0f}).setDuration(300);
        ObjectAnimator duration4 = ObjectAnimator.ofFloat(erm.b, "alpha", new float[]{0.0f, 1.0f}).setDuration(300);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{duration, duration2, duration3, duration4});
        animatorSet.start();
    }

    public void setOnOutSideClickListener(defpackage.ern.a aVar) {
        this.mOnOutSideClickListener = aVar;
    }

    public void setOnBackClickListener(defpackage.ern.a aVar) {
        this.mOnBackClickListener = aVar;
    }

    public boolean onBackPressed() {
        if (this.mOnBackClickListener != null) {
            this.mOnBackClickListener.onClick(this, -5);
        }
        return this.mCancelable || this.mShouldInterruptBackEvent;
    }

    public boolean isDismiss() {
        return !this.mShouldInterruptBackEvent;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1 && this.mOnOutSideClickListener != null) {
            this.mOnOutSideClickListener.onClick(this, -4);
        }
        return true;
    }
}
