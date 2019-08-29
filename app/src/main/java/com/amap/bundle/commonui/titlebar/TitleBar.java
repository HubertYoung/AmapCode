package com.amap.bundle.commonui.titlebar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.ClearableEditText;
import com.uc.webview.export.extension.UCCore;
import java.util.ArrayList;
import java.util.List;

public class TitleBar extends ViewGroup implements OnClickListener {
    public static final int INVALID_ID = -1;
    public static final int TITLE_A = 0;
    public static final int TITLE_A1 = 1;
    public static final int TITLE_A2 = 2;
    public static final int TITLE_A3 = 3;
    public static final int TITLE_B = 4;
    public static final int TITLE_C = 5;
    public static final int TITLE_C1 = 6;
    public static final int TITLE_D1 = 7;
    public static final int TITLE_D10 = 14;
    public static final int TITLE_D12 = 15;
    public static final int TITLE_D13 = 16;
    public static final int TITLE_D14 = 23;
    public static final int TITLE_D2 = 8;
    public static final int TITLE_D3 = 9;
    public static final int TITLE_D3N = 10;
    public static final int TITLE_D4 = 11;
    public static final int TITLE_D6 = 12;
    public static final int TITLE_D9 = 13;
    public static final int TITLE_E1 = 17;
    public static final int TITLE_E2 = 18;
    public static final int TITLE_E4 = 19;
    public static final int TITLE_E5 = 20;
    public static final int TITLE_E6 = 21;
    public static final int TITLE_E7 = 24;
    public static final int TITLE_F1 = 22;
    public static final int TITLE_FEED = 4096;
    private boolean isScreenWidth;
    private ImageView mActionImg;
    /* access modifiers changed from: private */
    public TextView mActionTView;
    /* access modifiers changed from: private */
    public ScaleAnimation mAnimRightIn;
    private ImageView mBackImg;
    private TextView mBackTView;
    private LinearLayout mCenterLayout;
    /* access modifiers changed from: private */
    public int mCurrentStyle;
    private View mDividerView;
    private ClearableEditText mEditText;
    private ImageView mExActionImg;
    private ImageView mExBackImg;
    private int mHeight;
    private LinearLayout mLeftLayout;
    private OnClickListener mOnActionClickListener;
    private OnClickListener mOnBackClickListener;
    private a mOnBackItemClickListener;
    private OnClickListener mOnExActionClickListener;
    private OnClickListener mOnExBackClickListener;
    private a mOnItemClickListener;
    private OnClickListener mOnSubTitleClickListener;
    private erq mOnTabSelectedListener;
    private err mOnTabSelectedListener2;
    private OnClickListener mOnTitleClickListener;
    private LinearLayout mRightLayout;
    private int mSelectIndex;
    private TextView mSubTitleTView;
    private LinearLayout mTabLayout;
    private OnClickListener mTabOnClickListener;
    private ArrayList<View> mTabViews;
    private TextWatcher mTextWatcher;
    private TextView mTitleTView;
    private int mTitleWidth;

    public interface a {
    }

    public static class b {
        public Drawable a;
        public CharSequence b;
        public CharSequence c;
        public Object d;

        public b(Drawable drawable) {
            this.a = drawable;
        }
    }

    static class c {
        public int a;
        public CharSequence b;
        public CharSequence c;
        public int d = -1;
        public CharSequence e;
        public CharSequence f;
        public int g = -1;
        public int h = -1;
        public int i = -1;
        public CharSequence j;
        public CharSequence k;
        public ColorStateList l;

        c() {
        }
    }

    private void dispatchItemClick(int i) {
    }

    public TitleBar(Context context, int i) {
        super(context);
        this.mSelectIndex = -1;
        this.isScreenWidth = true;
        this.mTextWatcher = new TextWatcher() {
            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (TitleBar.this.mCurrentStyle == 17 || TitleBar.this.mCurrentStyle == 18) {
                    if (!TextUtils.isEmpty(charSequence) && TitleBar.this.mActionTView.getVisibility() == 8) {
                        TitleBar.this.mActionTView.startAnimation(TitleBar.this.mAnimRightIn);
                        TitleBar.this.mActionTView.setVisibility(0);
                    } else if (TextUtils.isEmpty(charSequence) && TitleBar.this.mActionTView.getVisibility() == 0) {
                        TitleBar.this.mActionTView.setVisibility(8);
                    }
                }
            }
        };
        this.mTabOnClickListener = new OnClickListener() {
            public final void onClick(View view) {
                TitleBar.this.setSelectTab(((Integer) view.getTag()).intValue());
            }
        };
        init(i);
    }

    public TitleBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TitleBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSelectIndex = -1;
        this.isScreenWidth = true;
        this.mTextWatcher = new TextWatcher() {
            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (TitleBar.this.mCurrentStyle == 17 || TitleBar.this.mCurrentStyle == 18) {
                    if (!TextUtils.isEmpty(charSequence) && TitleBar.this.mActionTView.getVisibility() == 8) {
                        TitleBar.this.mActionTView.startAnimation(TitleBar.this.mAnimRightIn);
                        TitleBar.this.mActionTView.setVisibility(0);
                    } else if (TextUtils.isEmpty(charSequence) && TitleBar.this.mActionTView.getVisibility() == 0) {
                        TitleBar.this.mActionTView.setVisibility(8);
                    }
                }
            }
        };
        this.mTabOnClickListener = new OnClickListener() {
            public final void onClick(View view) {
                TitleBar.this.setSelectTab(((Integer) view.getTag()).intValue());
            }
        };
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        c cVar = new c();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TitleBar);
        if (obtainStyledAttributes != null) {
            cVar.a = obtainStyledAttributes.getInt(R.styleable.TitleBar_title_style, 5);
            cVar.b = obtainStyledAttributes.getText(R.styleable.TitleBar_title);
            cVar.c = obtainStyledAttributes.getText(R.styleable.TitleBar_sub_title);
            cVar.d = obtainStyledAttributes.getResourceId(R.styleable.TitleBar_action_img, R.drawable.icon_a13_selector);
            cVar.e = obtainStyledAttributes.getText(R.styleable.TitleBar_action_text);
            cVar.f = obtainStyledAttributes.getText(R.styleable.TitleBar_back_text);
            cVar.g = obtainStyledAttributes.getResourceId(R.styleable.TitleBar_back_img, -1);
            cVar.i = obtainStyledAttributes.getResourceId(R.styleable.TitleBar_ex_action_img, R.drawable.icon_a13_selector);
            cVar.j = obtainStyledAttributes.getText(R.styleable.TitleBar_ex_action_text);
            cVar.h = obtainStyledAttributes.getResourceId(R.styleable.TitleBar_ex_back_img, R.drawable.icon_a2_selector);
            cVar.k = obtainStyledAttributes.getText(R.styleable.TitleBar_edit_text_hint);
            cVar.l = obtainStyledAttributes.getColorStateList(R.styleable.TitleBar_actionText_color);
            this.isScreenWidth = obtainStyledAttributes.getBoolean(R.styleable.TitleBar_isScreenWidth, true);
            this.mCurrentStyle = cVar.a;
            obtainStyledAttributes.recycle();
        }
        this.mHeight = getResources().getDimensionPixelOffset(R.dimen.title_bar_default_height);
        inflateViewByStyle(cVar);
        initScaleAnimation(this.mCurrentStyle);
    }

    private void init(int i) {
        this.mHeight = getResources().getDimensionPixelOffset(R.dimen.title_bar_default_height);
        c cVar = new c();
        this.mCurrentStyle = i;
        cVar.a = i;
        inflateViewByStyle(cVar);
        initScaleAnimation(i);
    }

    private void initScaleAnimation(int i) {
        if (i == 17 || i == 18) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
            this.mAnimRightIn = scaleAnimation;
            this.mAnimRightIn.setDuration(200);
        }
    }

    private void inflateViewByStyle(c cVar) {
        LayoutInflater from = LayoutInflater.from(getContext());
        int i = this.mCurrentStyle;
        if (i != 4096) {
            switch (i) {
                case 0:
                    from.inflate(R.layout.view_title_a, this, true);
                    break;
                case 1:
                    from.inflate(R.layout.view_title_a1, this, true);
                    break;
                case 2:
                    from.inflate(R.layout.view_title_a2, this, true);
                    break;
                case 3:
                    from.inflate(R.layout.view_title_a3, this, true);
                    break;
                case 4:
                    from.inflate(R.layout.view_title_b, this, true);
                    break;
                case 5:
                    from.inflate(R.layout.view_title_c, this, true);
                    break;
                case 6:
                    from.inflate(R.layout.view_title_c1, this, true);
                    break;
                case 7:
                    from.inflate(R.layout.view_title_d1, this, true);
                    break;
                case 8:
                    from.inflate(R.layout.view_title_d2, this, true);
                    break;
                case 9:
                    from.inflate(R.layout.view_title_d3, this, true);
                    break;
                case 10:
                    from.inflate(R.layout.view_title_d3n, this, true);
                    break;
                case 11:
                    from.inflate(R.layout.view_title_d4, this, true);
                    break;
                case 12:
                    from.inflate(R.layout.view_title_d6, this, true);
                    break;
                case 13:
                    from.inflate(R.layout.view_title_d9, this, true);
                    break;
                case 14:
                    from.inflate(R.layout.view_title_d10, this, true);
                    break;
                case 15:
                    from.inflate(R.layout.view_title_d12, this, true);
                    break;
                case 16:
                    from.inflate(R.layout.view_title_d13, this, true);
                    break;
                case 17:
                case 18:
                    from.inflate(R.layout.view_title_e1, this, true);
                    break;
                case 19:
                    from.inflate(R.layout.view_title_e4, this, true);
                    break;
                case 20:
                    from.inflate(R.layout.view_title_e5, this, true);
                    break;
                case 21:
                    from.inflate(R.layout.view_title_e6, this, true);
                    break;
                case 22:
                    from.inflate(R.layout.view_title_f1, this, true);
                    break;
                case 23:
                    from.inflate(R.layout.view_title_d14, this, true);
                    break;
                case 24:
                    from.inflate(R.layout.view_title_e7, this, true);
                    break;
            }
        } else {
            from.inflate(R.layout.view_title_feed, this, true);
        }
        intView(cVar);
    }

    private void intView(c cVar) {
        this.mLeftLayout = (LinearLayout) findViewById(R.id.title_left_layout);
        this.mCenterLayout = (LinearLayout) findViewById(R.id.title_center_layout);
        this.mRightLayout = (LinearLayout) findViewById(R.id.title_right_layout);
        this.mDividerView = findViewById(R.id.title_divide);
        this.mBackImg = (ImageView) findViewById(R.id.title_back_img);
        this.mExBackImg = (ImageView) findViewById(R.id.title_ex_back);
        this.mBackTView = (TextView) findViewById(R.id.title_back_text);
        this.mTitleTView = (TextView) findViewById(R.id.title_title);
        this.mSubTitleTView = (TextView) findViewById(R.id.title_subtitle);
        this.mActionImg = (ImageView) findViewById(R.id.title_action_img);
        this.mExActionImg = (ImageView) findViewById(R.id.title_ex_action);
        this.mActionTView = (TextView) findViewById(R.id.title_action_text);
        this.mEditText = (ClearableEditText) findViewById(R.id.title_edit_text);
        setImageView(this.mBackImg, cVar.g);
        setImageView(this.mExBackImg, cVar.h);
        setTextView(this.mBackTView, cVar.f);
        setImageView(this.mActionImg, cVar.d);
        setImageView(this.mExActionImg, cVar.i);
        setTextView(this.mActionTView, cVar.e);
        setEditTextView(this.mEditText, cVar.k);
        setActionTextColor(cVar.l);
        setBackgroundResource(getBackgroundId(cVar.a));
        setClickable(true);
        if (this.mBackImg != null) {
            setBackImgContentDescription(getResources().getString(R.string.default_back));
        }
        if (this.mTitleTView != null && !TextUtils.isEmpty(cVar.b)) {
            setTitle(cVar.b);
        }
        if (this.mSubTitleTView != null && !TextUtils.isEmpty(cVar.c)) {
            setSubTitle(cVar.c);
        }
        if (isSupportTab()) {
            initTabLayout();
        }
    }

    private void setImageView(ImageView imageView, int i) {
        if (imageView != null) {
            imageView.setOnClickListener(this);
            if (i != -1) {
                imageView.setImageResource(i);
            }
        }
    }

    @Deprecated
    public void setTitleLayoutClickListener(OnClickListener onClickListener) {
        if (this.mCenterLayout != null) {
            if (this.mTitleTView != null) {
                this.mTitleTView.setOnClickListener(null);
            }
            if (this.mSubTitleTView != null) {
                this.mSubTitleTView.setOnClickListener(null);
            }
            this.mCenterLayout.setOnClickListener(onClickListener);
        }
    }

    public void setActionTextColor(ColorStateList colorStateList) {
        if (this.mActionTView != null && colorStateList != null) {
            this.mActionTView.setTextColor(colorStateList);
        }
    }

    public void setActionTextColor(int i) {
        if (this.mActionTView == null) {
            throw new IllegalArgumentException("current title style not support set action Text color");
        }
        this.mActionTView.setTextColor(i);
    }

    private void setTextView(TextView textView, CharSequence charSequence) {
        if (textView != null) {
            textView.setOnClickListener(this);
            if (!TextUtils.isEmpty(charSequence)) {
                textView.setText(charSequence);
            }
        }
    }

    private void setEditTextView(ClearableEditText clearableEditText, CharSequence charSequence) {
        if (clearableEditText != null) {
            clearableEditText.setEllipsize(TruncateAt.END);
            if (!TextUtils.isEmpty(charSequence)) {
                clearableEditText.setHint(charSequence);
            }
            if (this.mCurrentStyle == 17) {
                clearableEditText.addTextChangedListener(this.mTextWatcher);
                Drawable drawable = getResources().getDrawable(R.drawable.icon_b5_normal);
                setDrawableBounds(drawable);
                Drawable drawable2 = getResources().getDrawable(R.drawable.icon_b5_press);
                setDrawableBounds(drawable2);
                clearableEditText.setEmptyDrawable(drawable, drawable2);
                clearableEditText.showEmptyDrawable();
                return;
            }
            if (this.mCurrentStyle == 18) {
                clearableEditText.addTextChangedListener(this.mTextWatcher);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        return com.autonavi.minimap.R.color.c_12;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int getBackgroundId(int r3) {
        /*
            r2 = this;
            int r0 = com.autonavi.minimap.R.color.c_4
            r1 = 4096(0x1000, float:5.74E-42)
            if (r3 == r1) goto L_0x0019
            switch(r3) {
                case 0: goto L_0x0016;
                case 1: goto L_0x0019;
                case 2: goto L_0x0016;
                case 3: goto L_0x0016;
                case 4: goto L_0x0019;
                case 5: goto L_0x0019;
                case 6: goto L_0x0019;
                case 7: goto L_0x0019;
                case 8: goto L_0x0019;
                case 9: goto L_0x0019;
                default: goto L_0x0009;
            }
        L_0x0009:
            switch(r3) {
                case 11: goto L_0x0016;
                case 12: goto L_0x0013;
                case 13: goto L_0x0019;
                case 14: goto L_0x0010;
                case 15: goto L_0x0016;
                case 16: goto L_0x0016;
                case 17: goto L_0x0019;
                case 18: goto L_0x0019;
                case 19: goto L_0x0019;
                case 20: goto L_0x0019;
                case 21: goto L_0x0019;
                case 22: goto L_0x0019;
                case 23: goto L_0x0016;
                case 24: goto L_0x000d;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x001b
        L_0x000d:
            int r0 = com.autonavi.minimap.R.color.c_1
            goto L_0x001b
        L_0x0010:
            int r0 = com.autonavi.minimap.R.color.c_16
            goto L_0x001b
        L_0x0013:
            int r0 = com.autonavi.minimap.R.color.c_15
            goto L_0x001b
        L_0x0016:
            int r0 = com.autonavi.minimap.R.color.c_12
            goto L_0x001b
        L_0x0019:
            int r0 = com.autonavi.minimap.R.color.c_4
        L_0x001b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.commonui.titlebar.TitleBar.getBackgroundId(int):int");
    }

    private void setDrawableBounds(Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }

    public void addTabs(List<b> list, int i) {
        if (!isSupportTab()) {
            throw new IllegalArgumentException("current title style not support add tabs");
        } else if (list != null && !list.isEmpty() && i < list.size()) {
            int i2 = 0;
            if (isSupportImageTab()) {
                while (i2 < list.size()) {
                    addTabImageView(this.mTabLayout, i2, list.get(i2));
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    addTabTextView(this.mTabLayout, i2, list.get(i2));
                    i2++;
                }
                setTabTextBackground();
            }
            setSelectTab(i);
        }
    }

    public void setIsScreenWidth(boolean z) {
        this.isScreenWidth = z;
    }

    public void addTab(b bVar, boolean z) {
        if (!isSupportTab()) {
            throw new IllegalArgumentException("current title style not support add  tab");
        }
        int size = this.mTabViews.size();
        if (isSupportImageTab()) {
            addTabImageView(this.mTabLayout, size, bVar);
        } else {
            addTabTextView(this.mTabLayout, size, bVar);
            setTabTextBackground();
        }
        if (z) {
            setSelectTab(size);
        }
    }

    private void setTabTextBackground() {
        for (int i = 0; i < this.mTabViews.size(); i++) {
            this.mTabViews.get(i).setBackgroundDrawable(getTabTextBackground(i, this.mTabViews.size()));
        }
    }

    private void initTabLayout() {
        this.mTabLayout = new LinearLayout(getContext());
        this.mTabViews = new ArrayList<>();
        int i = -2;
        int i2 = -1;
        if (isSupportImageTab()) {
            i = -1;
        } else {
            i2 = -2;
        }
        if (this.mCurrentStyle == 0) {
            this.mTabLayout.setPadding(0, 0, getResources().getDimensionPixelOffset(R.dimen.tab_img_padding_right), 0);
        }
        this.mCenterLayout.addView(this.mTabLayout, new LayoutParams(i, i2));
    }

    public void removeAllTabs() {
        if (!isSupportTab()) {
            throw new IllegalArgumentException("current title style not support remove tabs");
        }
        this.mTabLayout.removeAllViews();
        this.mTabViews.clear();
        this.mSelectIndex = -1;
    }

    public void getTabLocationOnScreen(int i, int[] iArr) {
        if (!isSupportTab()) {
            throw new IllegalArgumentException("current title style not support get tab location");
        } else if (i < this.mTabLayout.getChildCount()) {
            this.mTabLayout.getChildAt(i).getLocationOnScreen(iArr);
        }
    }

    public void getTabLocationInWindow(int i, int[] iArr) {
        if (!isSupportTab()) {
            throw new IllegalArgumentException("current title style not support get tab location");
        } else if (i >= 0 && i < this.mTabLayout.getChildCount()) {
            this.mTabLayout.getChildAt(i).getLocationInWindow(iArr);
        }
    }

    public int getTabWidth(int i) {
        if (!isSupportTab()) {
            throw new IllegalArgumentException("current title style not support get tab width");
        } else if (i < 0 || i >= this.mTabLayout.getChildCount()) {
            return 0;
        } else {
            return this.mTabLayout.getChildAt(i).getWidth();
        }
    }

    public int getTabHeight(int i) {
        if (!isSupportTab()) {
            throw new IllegalArgumentException("current title style not support get tab width");
        } else if (i < 0 || i >= this.mTabLayout.getChildCount()) {
            return 0;
        } else {
            return this.mTabLayout.getChildAt(i).getHeight();
        }
    }

    public void removeTabAt(int i) {
        if (!isSupportTab()) {
            throw new IllegalArgumentException("current title style not support remove tab");
        } else if (i >= 0 && i < this.mTabViews.size()) {
            this.mTabLayout.removeViewAt(i);
            this.mTabViews.remove(i);
            int size = this.mTabViews.size();
            for (int i2 = i; i2 < size; i2++) {
                this.mTabViews.get(i2).setTag(Integer.valueOf(i2));
            }
            if (this.mSelectIndex == i) {
                setSelectTab(Math.max(0, i - 1));
            }
            if (!isSupportImageTab()) {
                for (int i3 = 0; i3 < this.mTabViews.size(); i3++) {
                    this.mTabViews.get(i3).setBackgroundDrawable(getTabTextBackground(i3, this.mTabViews.size()));
                }
            }
        }
    }

    public void setSelectTab(int i) {
        if (this.mTabViews == null) {
            throw new IllegalArgumentException("current title style not support set select tab");
        }
        int size = this.mTabViews.size();
        if (i >= 0 && i < size) {
            boolean z = false;
            int i2 = 0;
            while (true) {
                boolean z2 = true;
                if (i2 >= size) {
                    break;
                }
                View view = this.mTabViews.get(i2);
                if (i != i2) {
                    z2 = false;
                }
                view.setSelected(z2);
                i2++;
            }
            View view2 = this.mTabViews.get(i);
            if (i == this.mSelectIndex) {
                z = true;
            }
            this.mSelectIndex = i;
            if (z) {
                if (this.mOnTabSelectedListener != null) {
                    this.mOnTabSelectedListener.b(i);
                }
                if (!(this.mOnTabSelectedListener2 == null || view2 == null)) {
                    this.mOnTabSelectedListener2.onTabReselected(i, view2.getTag(R.id.tab_img_key));
                }
            } else {
                if (this.mOnTabSelectedListener != null) {
                    this.mOnTabSelectedListener.a(i);
                }
                if (!(this.mOnTabSelectedListener2 == null || view2 == null)) {
                    this.mOnTabSelectedListener2.onTabSelected(i, view2.getTag(R.id.tab_img_key));
                }
            }
        }
    }

    private boolean isSupportTab() {
        return this.mCurrentStyle == 0 || this.mCurrentStyle == 1 || this.mCurrentStyle == 2 || this.mCurrentStyle == 3;
    }

    private boolean isSupportImageTab() {
        return this.mCurrentStyle == 0 || this.mCurrentStyle == 3;
    }

    public void addDefaultTabs(int i) {
        if (this.mCurrentStyle == 0 || this.mCurrentStyle == 3) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new b(getResources().getDrawable(R.drawable.title_tab_car_selector)));
            arrayList.add(new b(getResources().getDrawable(R.drawable.title_tab_bus_selector)));
            arrayList.add(new b(getResources().getDrawable(R.drawable.title_tab_foot_selector)));
            addTabs(arrayList, i);
            return;
        }
        throw new IllegalArgumentException("current type not support add default tabs");
    }

    private void addTabImageView(LinearLayout linearLayout, int i, b bVar) {
        if (bVar.a != null) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(bVar.a);
            imageView.setScaleType(ScaleType.CENTER);
            linearLayout.setOrientation(0);
            imageView.setTag(Integer.valueOf(i));
            if (bVar.d != null) {
                imageView.setTag(R.id.tab_img_key, bVar.d);
            }
            imageView.setOnClickListener(this.mTabOnClickListener);
            if (!TextUtils.isEmpty(bVar.c)) {
                imageView.setContentDescription(bVar.c);
            }
            LayoutParams layoutParams = new LayoutParams(-2, -1);
            layoutParams.weight = 1.0f;
            linearLayout.addView(imageView, i, layoutParams);
            this.mTabViews.add(i, imageView);
        }
    }

    private void addTabTextView(LinearLayout linearLayout, int i, b bVar) {
        if (!TextUtils.isEmpty(bVar.b)) {
            TextView createTextView = createTextView(R.dimen.f_s_14, getTabTextColorId());
            createTextView.setText(bVar.b);
            linearLayout.setOrientation(0);
            linearLayout.setGravity(17);
            LayoutParams layoutParams = new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.title_a1_tab_width), getResources().getDimensionPixelOffset(R.dimen.title_a1_tab_height));
            createTextView.setTag(Integer.valueOf(i));
            createTextView.setOnClickListener(this.mTabOnClickListener);
            linearLayout.setBackgroundResource(getCenterLayoutBackgroundId());
            linearLayout.addView(createTextView, i, layoutParams);
            this.mTabViews.add(i, createTextView);
        }
    }

    private int getTabTextColorId() {
        return this.mCurrentStyle == 2 ? R.drawable.title_a2_tab_color_selector : R.drawable.title_a1_tab_color_selector;
    }

    private Drawable getTabTextBackground(int i, int i2) {
        Resources resources;
        int i3;
        Resources resources2;
        int i4;
        if (this.mCurrentStyle == 2) {
            resources = getResources();
            i3 = R.color.c_12;
        } else {
            resources = getResources();
            i3 = R.color.c_1;
        }
        int color = resources.getColor(i3);
        if (this.mCurrentStyle == 2) {
            resources2 = getResources();
            i4 = R.color.c_1;
        } else {
            resources2 = getResources();
            i4 = R.color.c_12;
        }
        int color2 = resources2.getColor(i4);
        return newSelector(createNormalDrawable(i, color2, color, i2), createTabShapeDrawable(i, color2, i2));
    }

    private Drawable createNormalDrawable(int i, int i2, int i3, int i4) {
        float[] fArr;
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.title_tab_radius);
        if (i == 0) {
            if (i4 == 1) {
                float f = (float) dimensionPixelOffset;
                fArr = new float[]{f, f, f, f, f, f, f, f};
            } else {
                float f2 = (float) dimensionPixelOffset;
                fArr = new float[]{f2, f2, 0.0f, 0.0f, 0.0f, 0.0f, f2, f2};
            }
        } else if (i == i4 - 1) {
            float f3 = (float) dimensionPixelOffset;
            fArr = new float[]{0.0f, 0.0f, f3, f3, f3, f3, 0.0f, 0.0f};
        } else {
            fArr = null;
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(i3);
        gradientDrawable.setCornerRadii(fArr);
        gradientDrawable.setStroke(1, i2);
        return gradientDrawable;
    }

    private int getCenterLayoutBackgroundId() {
        return this.mCurrentStyle == 2 ? R.drawable.title_a2_center_bg : R.drawable.title_a1_center_bg;
    }

    private ShapeDrawable createTabShapeDrawable(int i, int i2, int i3) {
        float[] fArr;
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.title_tab_radius);
        if (i == 0) {
            if (i3 == 1) {
                float f = (float) dimensionPixelOffset;
                fArr = new float[]{f, f, f, f, f, f, f, f};
            } else {
                float f2 = (float) dimensionPixelOffset;
                fArr = new float[]{f2, f2, 0.0f, 0.0f, 0.0f, 0.0f, f2, f2};
            }
        } else if (i == i3 - 1) {
            float f3 = (float) dimensionPixelOffset;
            fArr = new float[]{0.0f, 0.0f, f3, f3, f3, f3, 0.0f, 0.0f};
        } else {
            fArr = null;
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr, null, null));
        shapeDrawable.getPaint().setColor(i2);
        return shapeDrawable;
    }

    private StateListDrawable newSelector(Drawable drawable, Drawable drawable2) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842913}, drawable2);
        stateListDrawable.addState(new int[0], drawable);
        return stateListDrawable;
    }

    private TextView createTextView(int i, int i2) {
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(i);
        TextView textView = new TextView(getContext());
        textView.setTextSize(0, (float) dimensionPixelOffset);
        textView.setTextColor(getResources().getColorStateList(i2));
        textView.setSingleLine();
        textView.setGravity(17);
        textView.setEllipsize(TruncateAt.END);
        return textView;
    }

    private boolean isCentered() {
        return (this.mCurrentStyle == 0 || this.mCurrentStyle == 17 || this.mCurrentStyle == 18 || this.mCurrentStyle == 19 || this.mCurrentStyle == 24 || this.mCurrentStyle == 4096 || this.mCurrentStyle == 20 || this.mCurrentStyle == 22) ? false : true;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        this.mLeftLayout.layout(0, 0, this.mLeftLayout.getMeasuredWidth(), this.mLeftLayout.getMeasuredHeight());
        if (this.mRightLayout == null || this.mRightLayout.getMeasuredWidth() == 0) {
            i5 = 0;
        } else {
            i5 = this.mRightLayout.getMeasuredWidth();
            this.mRightLayout.layout(this.mTitleWidth - i5, 0, this.mTitleWidth, this.mRightLayout.getMeasuredHeight());
        }
        if (!isCentered()) {
            int measuredWidth = this.mLeftLayout.getMeasuredWidth();
            i6 = this.mTitleWidth - i5;
            i5 = measuredWidth;
        } else if (this.mLeftLayout.getMeasuredWidth() > i5) {
            i5 = this.mLeftLayout.getMeasuredWidth();
            i6 = this.mTitleWidth - this.mLeftLayout.getMeasuredWidth();
        } else {
            i6 = this.mTitleWidth - i5;
        }
        this.mCenterLayout.layout(i5, 0, i6, getMeasuredHeight());
        if (this.mDividerView != null) {
            this.mDividerView.layout(0, getMeasuredHeight() - this.mDividerView.getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight());
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.mHeight, UCCore.VERIFY_POLICY_QUICK);
        this.mTitleWidth = MeasureSpec.getSize(i);
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(this.mTitleWidth, Integer.MIN_VALUE);
        measureChild(this.mLeftLayout, makeMeasureSpec2, makeMeasureSpec);
        int measuredWidth = this.mLeftLayout.getMeasuredWidth();
        if (this.mRightLayout != null) {
            measureChild(this.mRightLayout, makeMeasureSpec2, makeMeasureSpec);
            i3 = this.mRightLayout.getMeasuredWidth();
        } else {
            i3 = 0;
        }
        if (!isCentered()) {
            i4 = (this.mTitleWidth - measuredWidth) - i3;
        } else if (measuredWidth > i3) {
            i4 = this.mTitleWidth - (measuredWidth * 2);
        } else {
            i4 = this.mTitleWidth - (i3 * 2);
        }
        this.mCenterLayout.measure(MeasureSpec.makeMeasureSpec(i4, UCCore.VERIFY_POLICY_QUICK), makeMeasureSpec);
        if (this.mDividerView != null) {
            measureChild(this.mDividerView, makeMeasureSpec2, makeMeasureSpec);
        }
        setMeasuredDimension(MeasureSpec.getSize(makeMeasureSpec2), this.mHeight);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.title_back_img || view.getId() == R.id.title_back_text) {
            if (this.mOnBackClickListener != null) {
                this.mOnBackClickListener.onClick(view);
            }
            dispatchItemClick(1);
        } else if (view.getId() == R.id.title_ex_back) {
            if (this.mOnExBackClickListener != null) {
                this.mOnExBackClickListener.onClick(view);
            }
            dispatchItemClick(2);
        } else if (view.getId() == R.id.title_action_img || view.getId() == R.id.title_action_text) {
            if (this.mOnActionClickListener != null) {
                this.mOnActionClickListener.onClick(view);
            }
            dispatchItemClick(33);
        } else if (view.getId() == R.id.title_ex_action) {
            if (this.mOnExActionClickListener != null) {
                this.mOnExActionClickListener.onClick(view);
            }
            dispatchItemClick(34);
        } else if (view == this.mTitleTView) {
            if (this.mOnTitleClickListener != null) {
                this.mOnTitleClickListener.onClick(view);
            }
            dispatchItemClick(17);
        } else {
            if (view == this.mSubTitleTView) {
                if (this.mOnSubTitleClickListener != null) {
                    this.mOnSubTitleClickListener.onClick(view);
                }
                dispatchItemClick(18);
            }
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (this.mTitleTView == null) {
            throw new IllegalArgumentException("current type not support set title");
        }
        this.mTitleTView.setText(charSequence);
        this.mTitleTView.setOnClickListener(this);
        if (!TextUtils.isEmpty(charSequence)) {
            this.mTitleTView.setContentDescription(getResources().getString(R.string.default_title_description_format, new Object[]{charSequence}));
            return;
        }
        this.mTitleTView.setContentDescription("");
    }

    public void setTitleTextColor(int i) {
        if (this.mTitleTView == null) {
            throw new IllegalArgumentException("current type not support set title text color");
        }
        this.mTitleTView.setTextColor(i);
    }

    public void setSubTitleTextColor(int i) {
        if (this.mSubTitleTView == null) {
            throw new IllegalArgumentException("current type not support set subTitle text color");
        }
        this.mSubTitleTView.setTextColor(i);
    }

    public void setSubTitle(CharSequence charSequence) {
        if (this.mSubTitleTView == null) {
            throw new IllegalArgumentException("current type not support set subTitle");
        } else if (!TextUtils.isEmpty(charSequence)) {
            this.mSubTitleTView.setText(charSequence);
            this.mSubTitleTView.setVisibility(0);
            this.mSubTitleTView.setOnClickListener(this);
        } else {
            this.mSubTitleTView.setVisibility(8);
        }
    }

    public void setBackText(CharSequence charSequence) {
        if (this.mBackTView == null) {
            throw new IllegalArgumentException("current type not support set backText");
        }
        this.mBackTView.setText(charSequence);
    }

    public void setBackImgContentDescription(CharSequence charSequence) {
        if (this.mBackImg == null) {
            throw new IllegalArgumentException("current type not support set back img contentDescription");
        }
        this.mBackImg.setContentDescription(charSequence);
    }

    public void setExBackImg(int i) {
        if (this.mExBackImg == null) {
            throw new IllegalArgumentException("current type not support set ex back img");
        }
        this.mExBackImg.setImageResource(i);
    }

    public void setBackImg(int i) {
        if (this.mBackImg == null) {
            throw new IllegalArgumentException("current type not support set back img");
        }
        this.mBackImg.setImageResource(i);
    }

    public void setExBackImgContentDescription(CharSequence charSequence) {
        if (this.mExBackImg == null) {
            throw new IllegalArgumentException("current type not support set back ex img contentDescription");
        }
        this.mExBackImg.setContentDescription(charSequence);
    }

    public void setActionImgContentDescription(CharSequence charSequence) {
        if (this.mActionImg == null) {
            throw new IllegalArgumentException("current type not support set action img contentDescription");
        }
        this.mActionImg.setContentDescription(charSequence);
    }

    public void setExActionImgContentDescription(CharSequence charSequence) {
        if (this.mExActionImg == null) {
            throw new IllegalArgumentException("current type not support set ex action img contentDescription");
        }
        this.mExActionImg.setContentDescription(charSequence);
    }

    public void setActionText(CharSequence charSequence) {
        if (this.mActionTView == null) {
            throw new IllegalArgumentException("current type not support set action text");
        }
        this.mActionTView.setText(charSequence);
    }

    public void setActionTextEnable(boolean z) {
        if (this.mActionTView == null) {
            throw new IllegalArgumentException("current type not support set action text enable");
        }
        this.mActionTView.setEnabled(z);
    }

    public void setActionTextVisibility(int i) {
        if (this.mActionTView == null) {
            throw new IllegalArgumentException("current type not support set action text visibility");
        } else if (i == 0) {
            this.mActionTView.setVisibility(0);
        } else {
            this.mActionTView.setVisibility(4);
        }
    }

    public void setActionImgVisibility(int i) {
        if (this.mActionImg == null) {
            throw new IllegalArgumentException("current type not support set action img visibility");
        }
        setChildVisibility(this.mActionImg, i);
    }

    public void setDivideVisibility(int i) {
        if (this.mDividerView == null) {
            throw new IllegalArgumentException("current type not support set divide view visibility");
        }
        setChildVisibility(this.mDividerView, i);
    }

    private void setChildVisibility(View view, int i) {
        if (i == 0) {
            view.setVisibility(0);
        } else {
            view.setVisibility(4);
        }
    }

    public void setActionImg(int i) {
        if (this.mActionImg == null) {
            throw new IllegalArgumentException("current title style not support set action img");
        }
        this.mActionImg.setImageResource(i);
    }

    public void setExActionImg(int i) {
        if (this.mExActionImg == null) {
            throw new IllegalArgumentException("current title style not support set ex action img");
        }
        this.mExActionImg.setImageResource(i);
    }

    public void setEditTextHint(CharSequence charSequence) {
        if (this.mEditText == null) {
            throw new IllegalArgumentException("current title style not support set edit text");
        }
        this.mEditText.setHint(charSequence);
    }

    public void changeTheme(int i) {
        if (this.mCurrentStyle != 4) {
            throw new IllegalArgumentException("current title style not support change Theme");
        }
        switch (i) {
            case 4097:
                setBackgroundResource(R.color.c_4);
                this.mBackImg.setImageResource(R.drawable.icon_a1_selector);
                this.mTitleTView.setTextColor(getResources().getColor(R.color.f_c_2));
                this.mDividerView.setVisibility(0);
                break;
            case 4098:
                setBackgroundResource(R.color.c_16);
                this.mBackImg.setImageResource(R.drawable.icon_a15_selector);
                this.mTitleTView.setTextColor(getResources().getColor(R.color.f_c_1));
                this.mDividerView.setVisibility(4);
                return;
        }
    }

    public EditText getEditText() {
        if (this.mEditText != null) {
            return this.mEditText;
        }
        throw new IllegalArgumentException("current title style not support get edit text");
    }

    public TextView getActionText() {
        if (this.mActionTView != null) {
            return this.mActionTView;
        }
        throw new IllegalArgumentException("current title style not support get action text");
    }

    public ImageView getActionImg() {
        if (this.mActionImg != null) {
            return this.mActionImg;
        }
        throw new IllegalArgumentException("current title style not support get action img");
    }

    public ViewGroup getTabLayout() {
        if (this.mTabLayout != null) {
            return this.mTabLayout;
        }
        throw new IllegalArgumentException("current title style not support get tab layout");
    }

    public void setOnBackClickListener(OnClickListener onClickListener) {
        this.mOnBackClickListener = onClickListener;
    }

    @Deprecated
    public void setOnBackClickListener(a aVar) {
        this.mOnBackItemClickListener = aVar;
    }

    public void setOnExBackClickListener(OnClickListener onClickListener) {
        this.mOnExBackClickListener = onClickListener;
    }

    public void setOnActionClickListener(OnClickListener onClickListener) {
        this.mOnActionClickListener = onClickListener;
    }

    public void setOnExActionClickListener(OnClickListener onClickListener) {
        this.mOnExActionClickListener = onClickListener;
    }

    public void setOnTitleClickListener(OnClickListener onClickListener) {
        this.mOnTitleClickListener = onClickListener;
    }

    public void setOnSubTitleClickListener(OnClickListener onClickListener) {
        this.mOnSubTitleClickListener = onClickListener;
    }

    public void setOnItemClickListener(a aVar) {
        this.mOnItemClickListener = aVar;
    }

    public void setOnTabSelectedListener(erq erq) {
        this.mOnTabSelectedListener = erq;
    }

    public void setOnTabSelectedListener2(err err) {
        this.mOnTabSelectedListener2 = err;
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        if (this.mEditText != null) {
            this.mEditText.addTextChangedListener(textWatcher);
        }
    }

    public void setOnEditorActionListener(OnEditorActionListener onEditorActionListener) {
        if (this.mEditText != null) {
            this.mEditText.setOnEditorActionListener(onEditorActionListener);
        }
    }

    public void setEditTextOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        if (this.mEditText != null) {
            this.mEditText.setOnFocusChangeListener(onFocusChangeListener);
        }
    }

    public void setEditTextOnEmptyClickListener(com.autonavi.widget.ui.ClearableEditText.a aVar) {
        if (this.mEditText != null) {
            this.mEditText.setOnEmptyDrawableClickListener(aVar);
        }
    }

    public void setEditTextOnKeyListener(OnKeyListener onKeyListener) {
        if (this.mEditText != null) {
            this.mEditText.setOnKeyListener(onKeyListener);
        }
    }

    public void setWidgetVisibility(int i, int i2) {
        switch (i) {
            case 1:
                if (this.mCurrentStyle == 13) {
                    setChildVisibility(this.mBackTView, i2);
                    return;
                } else {
                    setChildVisibility(this.mBackImg, i2);
                    return;
                }
            case 2:
                if (this.mCurrentStyle == 9 || this.mCurrentStyle == 10) {
                    setChildVisibility(this.mExBackImg, i2);
                    return;
                } else {
                    throwNoSupportException();
                    return;
                }
            case 17:
                setChildVisibility(this.mTitleTView, i2);
                return;
            case 18:
                throwNoSupportException();
                return;
            case 33:
                if (this.mCurrentStyle == 3 || this.mCurrentStyle == 4 || this.mCurrentStyle == 9 || this.mCurrentStyle == 14 || this.mCurrentStyle == 16 || this.mCurrentStyle == 17 || this.mCurrentStyle == 18 || this.mCurrentStyle == 20) {
                    throwNoSupportException();
                    return;
                } else if (this.mActionImg != null) {
                    setChildVisibility(this.mActionImg, i2);
                    return;
                } else if (this.mActionTView != null) {
                    setChildVisibility(this.mActionTView, i2);
                    return;
                }
                break;
            case 34:
                if (this.mCurrentStyle != 11 && this.mCurrentStyle != 8) {
                    throwNoSupportException();
                    break;
                } else {
                    setChildVisibility(this.mExActionImg, i2);
                    return;
                }
        }
    }

    public int getWidgetVisibility(int i) {
        switch (i) {
            case 1:
                if (this.mCurrentStyle == 13) {
                    return this.mBackTView.getVisibility();
                }
                return this.mBackImg.getVisibility();
            case 2:
                if (this.mCurrentStyle != 9 && this.mCurrentStyle != 10) {
                    throwNoSupportException();
                    break;
                } else {
                    return this.mExBackImg.getVisibility();
                }
                break;
            case 17:
                return this.mTitleTView.getVisibility();
            case 18:
                throwNoSupportException();
                break;
            case 33:
                if (this.mCurrentStyle == 3 || this.mCurrentStyle == 4 || this.mCurrentStyle == 9 || this.mCurrentStyle == 14 || this.mCurrentStyle == 16 || this.mCurrentStyle == 17 || this.mCurrentStyle == 18 || this.mCurrentStyle == 20) {
                    throwNoSupportException();
                    break;
                } else if (this.mActionImg != null) {
                    return this.mActionImg.getVisibility();
                } else {
                    if (this.mActionTView != null) {
                        return this.mActionTView.getVisibility();
                    }
                }
                break;
            case 34:
                if (this.mCurrentStyle != 11 && this.mCurrentStyle != 8) {
                    throwNoSupportException();
                    break;
                } else {
                    return this.mExActionImg.getVisibility();
                }
        }
        return 8;
    }

    public void setWidgetEnable(int i, boolean z) {
        switch (i) {
            case 1:
                if (this.mCurrentStyle == 13) {
                    this.mBackTView.setEnabled(z);
                    return;
                } else {
                    this.mBackImg.setEnabled(z);
                    return;
                }
            case 2:
                if (this.mCurrentStyle == 9 || this.mCurrentStyle == 10) {
                    this.mExBackImg.setEnabled(z);
                    return;
                } else {
                    throwNoSupportException();
                    return;
                }
            case 17:
                this.mTitleTView.setEnabled(z);
                return;
            case 18:
                throwNoSupportException();
                return;
            case 33:
                if (this.mCurrentStyle == 3 || this.mCurrentStyle == 4 || this.mCurrentStyle == 9 || this.mCurrentStyle == 14 || this.mCurrentStyle == 16 || this.mCurrentStyle == 17 || this.mCurrentStyle == 18 || this.mCurrentStyle == 20) {
                    throwNoSupportException();
                    return;
                } else if (this.mActionImg != null) {
                    this.mActionImg.setEnabled(z);
                    return;
                } else if (this.mActionTView != null) {
                    this.mActionTView.setEnabled(z);
                    return;
                }
                break;
            case 34:
                if (this.mCurrentStyle != 11 && this.mCurrentStyle != 8) {
                    throwNoSupportException();
                    break;
                } else {
                    this.mExActionImg.setEnabled(z);
                    return;
                }
        }
    }

    public boolean isWidgetVisibility(int i) {
        switch (i) {
            case 1:
                if (this.mCurrentStyle == 13) {
                    return this.mBackTView.isEnabled();
                }
                return this.mBackImg.isEnabled();
            case 2:
                if (this.mCurrentStyle != 9 && this.mCurrentStyle != 10) {
                    throwNoSupportException();
                    break;
                } else {
                    return this.mExBackImg.isEnabled();
                }
                break;
            case 17:
                return this.mTitleTView.isEnabled();
            case 18:
                throwNoSupportException();
                break;
            case 33:
                if (this.mCurrentStyle == 3 || this.mCurrentStyle == 4 || this.mCurrentStyle == 9 || this.mCurrentStyle == 14 || this.mCurrentStyle == 16 || this.mCurrentStyle == 17 || this.mCurrentStyle == 18 || this.mCurrentStyle == 20) {
                    throwNoSupportException();
                    break;
                } else if (this.mActionImg != null) {
                    return this.mActionImg.isEnabled();
                } else {
                    if (this.mActionTView != null) {
                        return this.mActionTView.isEnabled();
                    }
                }
                break;
            case 34:
                if (this.mCurrentStyle != 11 && this.mCurrentStyle != 8) {
                    throwNoSupportException();
                    break;
                } else {
                    return this.mExActionImg.isEnabled();
                }
        }
        return false;
    }

    private void throwNoSupportException() {
        throw new IllegalArgumentException("current title style not support the Widget");
    }
}
