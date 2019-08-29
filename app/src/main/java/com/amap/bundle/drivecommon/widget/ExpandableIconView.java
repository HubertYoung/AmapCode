package com.amap.bundle.drivecommon.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class ExpandableIconView extends LinearLayout {
    public static final int BLUE_BG = R.drawable.layer_tip_left;
    private static final String TAG = "ExpandableIconView";
    public static final int WHITE_BG = R.drawable.prefer_expand_text_bg;
    private int iconId;
    private int iconLayoutType;
    private a mAnimation;
    private Handler mHandler;
    private View mIconView;
    private boolean mIsExpand = true;
    private View mRootView;
    private ViewGroup mTextContainer;
    private TextView mTextView;
    private int mTextViewWidth;

    static class a extends Animation {
        int a;
        int b;
        private ViewGroup c;

        public final boolean willChangeBounds() {
            return true;
        }

        public a(ViewGroup viewGroup) {
            this.c = viewGroup;
        }

        /* access modifiers changed from: protected */
        public final void applyTransformation(float f, Transformation transformation) {
            LayoutParams layoutParams = this.c.getLayoutParams();
            layoutParams.width = (int) (((float) this.a) + (((float) this.b) * f));
            if (layoutParams.width < 0) {
                layoutParams.width = 0;
            }
            this.c.setLayoutParams(layoutParams);
            if (layoutParams.width <= 2) {
                this.c.setVisibility(4);
                if (this.c.getChildCount() > 0) {
                    this.c.getChildAt(0).setVisibility(4);
                }
            } else {
                this.c.setVisibility(0);
                if (this.c.getChildCount() > 0) {
                    this.c.getChildAt(0).setVisibility(0);
                }
            }
        }
    }

    public ExpandableIconView(Context context) {
        super(context);
        init(null);
    }

    public ExpandableIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public ExpandableIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ExpandableIconView);
            this.iconLayoutType = obtainStyledAttributes.getInt(R.styleable.ExpandableIconView_iconLayoutType, 0);
            this.iconId = obtainStyledAttributes.getResourceId(R.styleable.ExpandableIconView_childIconId, R.id.expandable_icon_icon);
            obtainStyledAttributes.recycle();
        }
        if (this.iconLayoutType == 1) {
            this.mRootView = inflate(getContext(), R.layout.expandable_icon_right_container, this);
        } else {
            this.mRootView = inflate(getContext(), R.layout.expandable_icon_left_container, this);
        }
        this.mIconView = this.mRootView.findViewById(R.id.expandable_icon_icon);
        this.mTextView = (TextView) this.mRootView.findViewById(R.id.expandable_icon_text);
        this.mTextContainer = (LinearLayout) this.mRootView.findViewById(R.id.expandable_icon_text_container);
        this.mIconView.setId(this.iconId);
        this.mAnimation = new a(this.mTextContainer);
        this.mAnimation.setDuration(400);
        this.mAnimation.setInterpolator(new OvershootInterpolator());
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public boolean isExpand() {
        return this.mIsExpand;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mIconView.setOnClickListener(onClickListener);
        this.mTextContainer.setOnClickListener(onClickListener);
    }

    public void setContentDescription(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mIconView.setContentDescription(str);
        }
    }

    public void setExpandText(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
        this.mTextView.setMaxLines(getMaxLines(charSequence));
        this.mTextView.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        this.mTextViewWidth = this.mTextView.getMeasuredWidth();
        if (TextUtils.isEmpty(charSequence) && this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mTextContainer.setVisibility(4);
        }
    }

    private int getMaxLines(CharSequence charSequence) {
        int i = 1;
        if (!TextUtils.isEmpty(charSequence)) {
            String charSequence2 = charSequence.toString();
            while (true) {
                int indexOf = charSequence2.indexOf("\n");
                if (indexOf == -1) {
                    break;
                }
                i++;
                charSequence2 = charSequence2.substring(indexOf + 1);
            }
        }
        return i;
    }

    public void setBackground(int i) {
        this.mTextView.setBackgroundResource(i);
        if (i == BLUE_BG) {
            this.mTextView.setTextColor(-1);
        }
    }

    public void setExpand(final boolean z, final boolean z2, int i) {
        if (i == 0) {
            doExpand(z, z2);
        } else {
            this.mHandler.postDelayed(new Runnable() {
                public final void run() {
                    ExpandableIconView.this.doExpand(z, z2);
                }
            }, (long) i);
        }
    }

    public void stopExpand() {
        if (this.mTextContainer != null) {
            this.mTextContainer.clearAnimation();
            this.mTextContainer.setVisibility(4);
            this.mHandler.removeCallbacksAndMessages(null);
        }
    }

    /* access modifiers changed from: private */
    public void doExpand(boolean z, boolean z2) {
        if (this.mIsExpand != z) {
            this.mIsExpand = z;
            int i = 1;
            int i2 = this.mIsExpand ? 1 : this.mTextViewWidth;
            if (this.mIsExpand) {
                i = this.mTextViewWidth;
            }
            if (z2) {
                a aVar = this.mAnimation;
                aVar.a = i2;
                aVar.b = i - i2;
                StringBuilder sb = new StringBuilder("from ");
                sb.append(aVar.a);
                sb.append(", to ");
                sb.append(i);
                this.mTextContainer.startAnimation(this.mAnimation);
                return;
            }
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTextContainer.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(i, -2);
            } else {
                layoutParams.width = i;
            }
            this.mTextContainer.setLayoutParams(layoutParams);
        }
    }
}
