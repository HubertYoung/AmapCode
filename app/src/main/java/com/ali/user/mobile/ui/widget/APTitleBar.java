package com.ali.user.mobile.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout.LayoutParams;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.font.TextSizeGearGetter;
import com.ali.user.mobile.ui.widget.listener.ScrollTitleChangeListener;
import com.ali.user.mobile.utils.DensityUtil;
import java.math.BigDecimal;

public class APTitleBar extends APRelativeLayout {
    public static final int TITLE_CENTER = 17;
    public static final int TITLE_LEFT = 16;
    private static int[] W = {0, 0, 30, 107, 191, 255};
    private APLinearLayout A;
    private APButton B;
    private View C;
    private ColorStateList D = null;
    private APRelativeLayout E;
    private final String F;
    private final String G;
    private final String H;
    private final String I;
    private final boolean J;
    private final boolean K;
    private final boolean L;
    private final int M;
    private final int N;
    private final int O;
    private final int P;
    private final int Q;
    private boolean R = false;
    private View S;
    private View T;
    private View U;
    private APRelativeLayout V;
    private APTextView a;
    private APTextView b;
    private APRelativeLayout c;
    private APImageButton d;
    private APRelativeLayout e;
    private View f;
    private APButton g;
    private APRelativeLayout h;
    private APButton i;
    float icon_height = 0.0f;
    float icon_width = 0.0f;
    private APRelativeLayout j;
    private View k;
    private APButton l;
    private APRelativeLayout m;
    public int mDefaultScrollHeight = DensityUtil.a(getContext(), 148.0f);
    float margin_left_generic = 0.0f;
    float margin_right = 0.0f;
    float margin_right_generic = 0.0f;
    float margin_seperator = 0.0f;
    private View n;
    private APButton o;
    private APLinearLayout p;
    /* access modifiers changed from: private */
    public APProgressBar q;
    private APRelativeLayout r;
    private APRelativeLayout s;
    private APButton t;
    float touch_height = 0.0f;
    float touch_width = 0.0f;
    private APButton u;
    private View v;
    private APRelativeLayout w;
    private APImageView x;
    private APRelativeLayout y;
    private APRelativeLayout z;

    @Deprecated
    public void setBackButtonText(String str) {
    }

    public APTitleBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.q, this, true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.U);
        this.F = obtainStyledAttributes.getString(R.styleable.af);
        this.G = obtainStyledAttributes.getString(R.styleable.X);
        this.M = obtainStyledAttributes.getResourceId(R.styleable.W, 0);
        this.H = obtainStyledAttributes.getString(R.styleable.ab);
        this.N = obtainStyledAttributes.getResourceId(R.styleable.aa, 0);
        this.I = obtainStyledAttributes.getString(R.styleable.Z);
        this.O = obtainStyledAttributes.getResourceId(R.styleable.Y, 0);
        this.J = obtainStyledAttributes.getBoolean(R.styleable.ae, false);
        this.K = obtainStyledAttributes.getBoolean(R.styleable.ad, false);
        this.L = obtainStyledAttributes.getBoolean(R.styleable.ac, true);
        this.Q = obtainStyledAttributes.getResourceId(R.styleable.V, 0);
        this.P = obtainStyledAttributes.getInt(R.styleable.ah, 16);
        this.D = obtainStyledAttributes.getColorStateList(R.styleable.ag);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.t);
        this.R = obtainStyledAttributes2.getBoolean(R.styleable.u, false);
        obtainStyledAttributes2.recycle();
        this.margin_right_generic = (float) getResources().getDimensionPixelSize(R.dimen.z);
        this.margin_left_generic = (float) getResources().getDimensionPixelOffset(R.dimen.x);
        this.margin_seperator = (float) getResources().getDimensionPixelSize(R.dimen.A);
        this.margin_right = (float) getResources().getDimensionPixelSize(R.dimen.y);
        this.icon_height = (float) getResources().getDimensionPixelSize(R.dimen.w);
        this.icon_width = (float) getResources().getDimensionPixelSize(R.dimen.D);
        this.touch_height = (float) getResources().getDimensionPixelSize(R.dimen.B);
        this.touch_width = (float) getResources().getDimensionPixelSize(R.dimen.C);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        this.a = (APTextView) findViewById(R.id.cz);
        this.b = (APTextView) findViewById(R.id.cB);
        this.e = (APRelativeLayout) findViewById(R.id.ck);
        this.f = findViewById(R.id.cj);
        this.g = (APButton) findViewById(R.id.ci);
        this.d = (APImageButton) findViewById(R.id.cd);
        this.d.setContentDescription(getResources().getString(R.string.s));
        this.p = (APLinearLayout) findViewById(R.id.bO);
        this.h = (APRelativeLayout) findViewById(R.id.cm);
        this.i = (APButton) findViewById(R.id.cl);
        this.j = (APRelativeLayout) findViewById(R.id.cu);
        this.k = findViewById(R.id.ct);
        this.l = (APButton) findViewById(R.id.cs);
        this.z = (APRelativeLayout) findViewById(R.id.cx);
        this.C = findViewById(R.id.cy);
        this.B = (APButton) findViewById(R.id.cw);
        this.q = (APProgressBar) findViewById(R.id.cq);
        this.c = (APRelativeLayout) findViewById(R.id.cC);
        this.m = (APRelativeLayout) findViewById(R.id.ch);
        this.n = findViewById(R.id.cg);
        this.o = (APButton) findViewById(R.id.cf);
        this.r = (APRelativeLayout) findViewById(R.id.cF);
        this.s = (APRelativeLayout) findViewById(R.id.co);
        this.t = (APButton) findViewById(R.id.cn);
        this.v = findViewById(R.id.cp);
        this.w = (APRelativeLayout) findViewById(R.id.cD);
        this.x = (APImageView) findViewById(R.id.aB);
        this.y = (APRelativeLayout) findViewById(R.id.cA);
        this.A = (APLinearLayout) findViewById(R.id.ap);
        this.E = (APRelativeLayout) findViewById(R.id.ce);
        if (getContext() != null) {
            this.u = new APButton(getContext());
        }
        if (this.a != null) {
            this.a.setOnTextChangeListener(new OnTextViewTextChangeListener() {
                public final void a(String str) {
                    Context context = APTitleBar.this.getContext();
                    if (context != null && (context instanceof Activity)) {
                        ((Activity) context).setTitle(str);
                    }
                }
            });
        }
        setTitleText(this.F);
        setGenericButtonText(this.G);
        switch (this.P) {
            case 17:
                LayoutParams layoutParams = new LayoutParams(-2, -2);
                layoutParams.addRule(13);
                this.w.setLayoutParams(layoutParams);
                break;
        }
        if (this.L) {
            this.d.setVisibility(0);
            this.v.setVisibility(0);
        } else {
            this.d.setVisibility(8);
            this.v.setVisibility(8);
            this.y.setPadding(getResources().getDimensionPixelSize(R.dimen.G), 0, getResources().getDimensionPixelSize(R.dimen.H), 0);
        }
        this.d.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Context context = APTitleBar.this.getContext();
                    ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                    if (context != null && (context instanceof Activity)) {
                        ((Activity) context).onBackPressed();
                    }
                } catch (Exception unused) {
                }
            }
        });
        if (this.Q != 0) {
            setImageBackButtonIcon(this.Q);
        }
        if (this.M != 0) {
            setGenericButtonIcon(getResources().getDrawable(this.M));
        }
        if (this.O != 0) {
            setLeftButtonIcon(getResources().getDrawable(this.O));
        } else {
            setLeftButtonText(this.I);
        }
        if (this.N != 0) {
            setRightButtonIcon(getResources().getDrawable(this.N));
        } else {
            setRightButtonText(this.H);
        }
        if (this.D != null) {
            this.a.setTextColor(this.D);
        }
        if (this.R) {
            this.a.setDynamicTextSize(this.R);
            this.b.setDynamicTextSize(this.R);
        }
        if (this.a.isDynamicTextSize()) {
            TextSizeGearGetter textSizeGearGetter = APTextView.getTextSizeGearGetter();
            if (textSizeGearGetter != null && textSizeGearGetter.a() > 3) {
                this.a.setMaxEms(7);
            }
        }
        setGenericButtonVisiable(this.K);
        setSwitchContainerVisiable(this.J);
        a();
    }

    public void setTitleText(CharSequence charSequence) {
        this.a.setText(charSequence);
    }

    public void setTitleText(String str) {
        this.a.setText(str);
    }

    public void setSecondTitleTextView(CharSequence charSequence) {
        this.b.setVisibility(0);
        this.b.setText(charSequence);
        ViewGroup.LayoutParams layoutParams = this.d.getLayoutParams();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.u);
        layoutParams.height = dimensionPixelSize;
        this.d.setLayoutParams(layoutParams);
        this.h.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = this.h.getLayoutParams();
        layoutParams2.height = dimensionPixelSize;
        this.h.setLayoutParams(layoutParams2);
        this.j.getLayoutParams();
        ViewGroup.LayoutParams layoutParams3 = this.j.getLayoutParams();
        layoutParams3.height = dimensionPixelSize;
        this.j.setLayoutParams(layoutParams3);
        this.c.setPadding(0, getResources().getDimensionPixelSize(R.dimen.F), 0, 0);
    }

    public void setSecondTitleTextView(String str) {
        this.b.setVisibility(0);
        this.b.setText(str);
        ViewGroup.LayoutParams layoutParams = this.d.getLayoutParams();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.u);
        layoutParams.height = dimensionPixelSize;
        this.d.setLayoutParams(layoutParams);
        this.h.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = this.h.getLayoutParams();
        layoutParams2.height = dimensionPixelSize;
        this.h.setLayoutParams(layoutParams2);
        this.j.getLayoutParams();
        ViewGroup.LayoutParams layoutParams3 = this.j.getLayoutParams();
        layoutParams3.height = dimensionPixelSize;
        this.j.setLayoutParams(layoutParams3);
        this.c.setPadding(0, getResources().getDimensionPixelSize(R.dimen.F), 0, 0);
    }

    public APRelativeLayout getTitleBarTopRl() {
        return this.w;
    }

    public APRelativeLayout getThirdButtonParent() {
        return this.z;
    }

    public APRelativeLayout getTitleLinearLayout() {
        return this.c;
    }

    public APRelativeLayout getLeftButtonParent() {
        return this.h;
    }

    public APRelativeLayout getRightButtonParent() {
        return this.j;
    }

    public void attachFlagInTitleLinearLayout(View view, int i2, int i3, int i4, int i5) {
        this.U = view;
        this.c = (APRelativeLayout) findViewById(R.id.cC);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(10, -1);
        layoutParams.addRule(9, -1);
        layoutParams.setMargins(i2, i3, i4, i5);
        this.U.setLayoutParams(layoutParams);
        this.c.addView(this.U);
    }

    public void unAttachFlagFromTitleLinearLayout(View view) {
        if (this.U != null) {
            if (this.c != null) {
                this.c.removeView(this.U);
            }
            this.U = null;
        }
    }

    public void setBackButtonListener(OnClickListener onClickListener) {
        this.d.setVisibility(0);
        this.d.setOnClickListener(onClickListener);
    }

    public void setTitleTextClickListener(OnClickListener onClickListener) {
        this.x.setVisibility(0);
        this.w.setClickable(true);
        this.w.setOnClickListener(onClickListener);
    }

    public void clearTitleTextClickListener() {
        this.x.setVisibility(8);
        this.w.setClickable(false);
        this.w.setOnClickListener(null);
    }

    public void setSecondTitleTextViewGone() {
        this.b.setVisibility(8);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.E);
        this.c.setPadding(0, dimensionPixelSize, 0, dimensionPixelSize);
    }

    public void setSwitchContainerVisiable(boolean z2) {
        this.p.setVisibility(z2 ? 0 : 8);
    }

    public APLinearLayout getSwitchContainer() {
        return this.p;
    }

    public APLinearLayout getLeftSwitchContainer() {
        return this.A;
    }

    public void attachNewFlagView(View view) {
        this.S = view;
        this.V = (APRelativeLayout) findViewById(R.id.cr);
        this.V.addView(this.S);
    }

    public void unAttachNewFlagView(View view) {
        if (this.S != null) {
            if (this.V != null) {
                this.V.removeView(this.S);
            }
            this.S = null;
        }
    }

    public void attachFlagInThirdButton(View view, int i2, int i3, int i4, int i5) {
        this.T = view;
        this.z = (APRelativeLayout) findViewById(R.id.cx);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(10, -1);
        layoutParams.addRule(5, -1);
        layoutParams.setMargins(i2, i3, i4, i5);
        this.T.setLayoutParams(layoutParams);
        this.z.addView(this.T);
    }

    public void unAttachFlagFromThirdButton(View view) {
        if (this.T != null) {
            if (this.z != null) {
                this.z.removeView(this.T);
            }
            this.T = null;
        }
    }

    public void setGenericButtonVisiable(boolean z2) {
        int i2 = 8;
        if (!z2 || TextUtils.isEmpty(this.g.getText().toString())) {
            this.f.setVisibility(8);
        } else {
            this.f.setVisibility(0);
        }
        APRelativeLayout aPRelativeLayout = this.e;
        if (z2) {
            i2 = 0;
        }
        aPRelativeLayout.setVisibility(i2);
    }

    public void setFeedbackButtonVisible(boolean z2) {
        int i2 = 8;
        if (!z2 || TextUtils.isEmpty(this.o.getText().toString())) {
            this.n.setVisibility(8);
        } else {
            this.n.setVisibility(0);
        }
        APRelativeLayout aPRelativeLayout = this.m;
        if (z2) {
            i2 = 0;
        }
        aPRelativeLayout.setVisibility(i2);
    }

    public View getGenericButtonLeftLine() {
        return this.f;
    }

    public void setGenericButtonListener(OnClickListener onClickListener) {
        this.g.setOnClickListener(onClickListener);
    }

    public void setThirdButtonVisiable(boolean z2) {
        this.z.setVisibility(z2 ? 0 : 8);
        if (!z2 || TextUtils.isEmpty(this.B.getText().toString())) {
            this.C.setVisibility(8);
        } else {
            this.C.setVisibility(0);
        }
    }

    private void a() {
        if (this.l.getVisibility() != 0 || TextUtils.isEmpty(this.l.getText())) {
            this.k.setVisibility(8);
        } else {
            this.k.setVisibility(0);
        }
        if (this.g.getVisibility() != 0 || TextUtils.isEmpty(this.g.getText())) {
            this.f.setVisibility(8);
        } else {
            this.f.setVisibility(0);
        }
        if (this.o.getVisibility() != 0 || TextUtils.isEmpty(this.o.getText())) {
            this.n.setVisibility(8);
        } else {
            this.n.setVisibility(0);
        }
        if (this.B.getVisibility() != 0 || TextUtils.isEmpty(this.B.getText())) {
            this.C.setVisibility(8);
        } else {
            this.C.setVisibility(0);
        }
    }

    public void setThirdButtonListener(OnClickListener onClickListener) {
        this.B.setOnClickListener(onClickListener);
    }

    public void setCloseButton(OnClickListener onClickListener) {
        this.d.setImageResource(R.drawable.aj);
        this.d.setOnClickListener(onClickListener);
    }

    public void setImageBackButtonIcon(int i2) {
        this.d.setImageResource(i2);
    }

    public void setLeftButtonListener(OnClickListener onClickListener) {
        this.i.setOnClickListener(onClickListener);
    }

    public void setRightButtonListener(OnClickListener onClickListener) {
        this.l.setOnClickListener(onClickListener);
    }

    public void setGenericButtonText(String str) {
        if (this.g != null && !TextUtils.isEmpty(str)) {
            try {
                LayoutParams layoutParams = (LayoutParams) this.g.getLayoutParams();
                layoutParams.width = -2;
                this.g.setLayoutParams(layoutParams);
                this.g.setText(str);
            } catch (Exception unused) {
            }
        }
        a();
    }

    public void setLeftButtonText(String str) {
        this.i.setText(str);
        a();
    }

    public void setRightButtonText(String str) {
        this.l.setText(str);
        a();
    }

    public APButton getGenericButton() {
        return this.g;
    }

    public APButton getLeftGenericButton() {
        return this.t;
    }

    public APButton getFeedbackButton() {
        return this.o;
    }

    public APButton getLeftButton() {
        return this.i;
    }

    public View getLeftLine() {
        return this.v;
    }

    public APButton getContainerRightButton() {
        return this.l;
    }

    public APButton getRightButton() {
        return this.g;
    }

    public APTextView getTitleTextView() {
        return this.a;
    }

    public APImageButton getImageBackButton() {
        return this.d;
    }

    public APRelativeLayout getGenericButtonParent() {
        return this.e;
    }

    public APRelativeLayout getBlankMid() {
        return this.E;
    }

    @Deprecated
    public APButton getBackButton() {
        return this.u;
    }

    public APTextView getSecondTitleTextView() {
        return this.b;
    }

    public APRelativeLayout getTitlebarBg() {
        return this.r;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        a(this.i, this.touch_height, this.touch_width);
        a(this.l, this.touch_height, this.touch_width);
        a(this.g, this.touch_height, this.touch_width);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.t);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.s);
        if (this.S != null) {
            this.S.layout(((this.e.getLeft() + this.g.getRight()) - (this.S.getMeasuredWidth() / 2)) + dimensionPixelSize2, (this.g.getTop() - (this.S.getMeasuredHeight() / 2)) + dimensionPixelSize, this.e.getLeft() + this.g.getRight() + (this.S.getMeasuredWidth() / 2) + dimensionPixelSize2, this.g.getTop() + (this.S.getMeasuredHeight() / 2) + dimensionPixelSize);
        }
    }

    public void setGenericButtonIconResource(int i2) {
        setGenericButtonIcon(getResources().getDrawable(i2));
    }

    public void handleScrollChange(int i2, ScrollTitleChangeListener scrollTitleChangeListener) {
        handleScrollChange(this.mDefaultScrollHeight, i2, scrollTitleChangeListener, true);
    }

    public void handleScrollChange(int i2, ScrollTitleChangeListener scrollTitleChangeListener, boolean z2) {
        handleScrollChange(this.mDefaultScrollHeight, i2, scrollTitleChangeListener, z2);
    }

    public void handleScrollChange(int i2, int i3, ScrollTitleChangeListener scrollTitleChangeListener) {
        handleScrollChange(i2, i3, scrollTitleChangeListener, true);
    }

    public void handleScrollChange(int i2, int i3, ScrollTitleChangeListener scrollTitleChangeListener, boolean z2) {
        if (i2 <= 0) {
            i2 = this.mDefaultScrollHeight;
        }
        if (i3 >= 0) {
            int i4 = 0;
            if (i3 > 0) {
                i4 = new BigDecimal(i3 * 100).divide(new BigDecimal(i2), 0, 4).intValue();
            }
            if (i4 < 80 || !z2) {
                this.a.setTextColor(getContext().getResources().getColor(17170443));
                this.d.setImageResource(R.drawable.ae);
                this.v.setBackgroundResource(R.color.S);
            } else if (z2) {
                this.a.setTextColor(getContext().getResources().getColor(17170444));
                this.d.setImageResource(R.drawable.ac);
                this.v.setBackgroundResource(R.color.Q);
            }
            int i5 = i4 / 20;
            int i6 = i4 % 20;
            if (i5 >= 5) {
                i5 = 5;
            }
            a(i5, z2);
            b(i5, z2);
            if (i5 <= 0 || i5 >= 5) {
                if (i5 == 0 || i5 == 5) {
                    this.r.getBackground().setAlpha(W[i5]);
                }
                return;
            }
            this.r.getBackground().setAlpha(W[i5] + ((i6 * (W[i5 + 1] - W[i5])) / 20));
        }
    }

    private void a(int i2, boolean z2) {
        View[] viewArr = {this.d, this.m, this.h, this.e, this.z, this.s, this.j};
        for (int i3 = 0; i3 < 7; i3++) {
            View view = viewArr[i3];
            if (i2 < 5 || !z2) {
                view.setBackgroundResource(17170445);
            } else {
                view.setBackgroundResource(R.drawable.ag);
            }
        }
    }

    private void b(int i2, boolean z2) {
        APButton[] aPButtonArr = {this.l, this.i, this.B, this.g, this.t, this.o};
        for (int i3 = 0; i3 < 6; i3++) {
            APButton aPButton = aPButtonArr[i3];
            if (i2 < 4 || !z2) {
                aPButton.setTextColor(getResources().getColorStateList(R.color.M));
            } else {
                aPButton.setTextColor(getResources().getColorStateList(R.color.L));
            }
        }
    }

    public void setThirdButtonIconResource(int i2) {
        setThirdButtonIcon(getResources().getDrawable(i2));
    }

    public void setLeftButtonIconResource(int i2) {
        setLeftButtonIcon(getResources().getDrawable(i2));
    }

    public void setRightButtonIconResource(int i2) {
        setRightButtonIcon(getResources().getDrawable(i2));
    }

    public void setGenericButtonIcon(Drawable drawable) {
        a(drawable, (LayoutParams) this.g.getLayoutParams(), 0.0f, 0.0f, this.icon_width, this.g);
        a();
    }

    public void setLeftButtonIcon(Drawable drawable) {
        a(drawable, (LayoutParams) this.i.getLayoutParams(), 0.0f, 0.0f, this.icon_width, this.i);
        a();
    }

    public void setLeftGenericButtonIcon(Drawable drawable) {
        a(drawable, (LayoutParams) this.t.getLayoutParams(), 0.0f, 0.0f, this.icon_width, this.t);
        a();
    }

    public void setThirdButtonIcon(Drawable drawable) {
        this.B.setVisibility(0);
        a(drawable, (LayoutParams) this.B.getLayoutParams(), 0.0f, 0.0f, this.icon_width, this.B);
        a();
    }

    public void setRightButtonIcon(Drawable drawable) {
        a(drawable, (LayoutParams) this.l.getLayoutParams(), 0.0f, 0.0f, this.icon_width, this.l);
        a();
    }

    private static void a(Drawable drawable, MarginLayoutParams marginLayoutParams, float f2, float f3, float f4, APButton aPButton) {
        if (drawable != null) {
            int intrinsicHeight = drawable.getCurrent().getIntrinsicHeight();
            int intrinsicWidth = drawable.getCurrent().getIntrinsicWidth();
            float f5 = (float) intrinsicWidth;
            if (f5 > f4) {
                intrinsicWidth = (int) f4;
                intrinsicHeight = (int) ((((float) intrinsicHeight) * f4) / f5);
            }
            marginLayoutParams.height = intrinsicHeight;
            marginLayoutParams.width = intrinsicWidth;
            aPButton.setBackgroundDrawable(drawable);
            float f6 = (f4 - ((float) intrinsicWidth)) / 2.0f;
            marginLayoutParams.leftMargin = (int) (f2 + f6);
            marginLayoutParams.rightMargin = (int) (f6 + f3);
            StringBuilder sb = new StringBuilder("leftMargin=");
            sb.append(marginLayoutParams.leftMargin);
            sb.append(", rightMargin=");
            sb.append(marginLayoutParams.rightMargin);
        }
    }

    private static void a(APButton aPButton, float f2, float f3) {
        Rect rect = new Rect();
        aPButton.getHitRect(rect);
        if (((float) rect.height()) < f2) {
            int height = (int) ((f2 - ((float) rect.height())) / 2.0f);
            rect.top -= height;
            rect.bottom += height;
        }
        if (((float) rect.width()) < f3) {
            int width = (int) ((f3 - ((float) rect.width())) / 2.0f);
            rect.left -= width;
            rect.right += width;
        }
        TouchDelegate touchDelegate = new TouchDelegate(rect, aPButton);
        if (View.class.isInstance(aPButton.getParent())) {
            ((View) aPButton.getParent()).setTouchDelegate(touchDelegate);
        }
    }

    public void showBackButton() {
        this.d.setVisibility(0);
        this.v.setVisibility(0);
        this.y.setPadding(getResources().getDimensionPixelSize(R.dimen.I), 0, getResources().getDimensionPixelSize(R.dimen.J), 0);
    }

    public void hideBackButton() {
        this.d.setVisibility(8);
        this.v.setVisibility(8);
        this.y.setPadding(getResources().getDimensionPixelSize(R.dimen.G), 0, getResources().getDimensionPixelSize(R.dimen.H), 0);
    }

    public APRelativeLayout getTitleBarTitleRl() {
        return this.y;
    }

    public void startProgressBar() {
        this.q.post(new Runnable() {
            public void run() {
                try {
                    APTitleBar.this.q.setVisibility(0);
                } catch (Throwable unused) {
                }
            }
        });
    }

    public void stopProgressBar() {
        this.q.post(new Runnable() {
            public void run() {
                try {
                    APTitleBar.this.q.setVisibility(8);
                } catch (Throwable unused) {
                }
            }
        });
    }

    public void changeBlue() {
        changeTitleBarColorStyle(R.color.b);
    }

    public void changeTitleBarColorStyle(int i2) {
        handleScrollChange(this.mDefaultScrollHeight, (ScrollTitleChangeListener) null, false);
        getTitlebarBg().setBackgroundResource(i2);
    }
}
