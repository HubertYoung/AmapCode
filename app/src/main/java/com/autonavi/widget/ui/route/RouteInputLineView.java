package com.autonavi.widget.ui.route;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class RouteInputLineView extends RelativeLayout implements OnClickListener {
    public static final int END = 2;
    public static final int PASS = 4;
    public static final int START = 1;
    private int mAllocHeight;
    private ImageView mFlagImageView;
    private TextView mFlagTextView;
    /* access modifiers changed from: private */
    public boolean mInAnimating;
    private AnimatorSet mInAnimator;
    private boolean mIsValid;
    private eru mListener;
    /* access modifiers changed from: private */
    public boolean mOutAnimating;
    private AnimatorSet mOutAnimator;
    private int mPosition;
    private TextView mTextView;

    public RouteInputLineView(Context context) {
        this(context, null);
    }

    public RouteInputLineView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteInputLineView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPosition = 1;
        this.mIsValid = false;
        this.mAllocHeight = -1;
        init(context);
    }

    private void init(Context context) {
        setClipChildren(false);
        setClipToPadding(false);
        View.inflate(context, R.layout.view_input_line, this);
        this.mFlagImageView = (ImageView) findViewById(R.id.iv_input_line_flag);
        this.mFlagTextView = (TextView) findViewById(R.id.tv_input_line_flag);
        this.mTextView = (TextView) findViewById(R.id.tv_input_line);
        this.mFlagImageView.setOnClickListener(this);
        this.mTextView.setOnClickListener(this);
    }

    public ImageView getFlagImageView() {
        return this.mFlagImageView;
    }

    public TextView getFlagTextView() {
        return this.mFlagTextView;
    }

    public TextView getTextView() {
        return this.mTextView;
    }

    public CharSequence getText() {
        return this.mTextView.getText();
    }

    public void setText(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
        this.mIsValid = !TextUtils.isEmpty(charSequence);
    }

    public void setHint(CharSequence charSequence) {
        this.mTextView.setHint(charSequence);
    }

    public void reset() {
        this.mTextView.setText("");
        this.mAllocHeight = -1;
        setAlpha(1.0f);
        this.mIsValid = false;
    }

    public void setPosition(int i) {
        this.mPosition = i;
    }

    public void setOnRouteInputClickListener(eru eru) {
        this.mListener = eru;
    }

    private void fitVisible(boolean z) {
        int i = 0;
        this.mFlagImageView.setVisibility(z ? 8 : 0);
        TextView textView = this.mFlagTextView;
        if (!z) {
            i = 8;
        }
        textView.setVisibility(i);
    }

    public void setFlagText(CharSequence charSequence) {
        fitVisible(true);
        this.mFlagTextView.setText(charSequence);
    }

    public void setImageDrawable(Drawable drawable) {
        fitVisible(false);
        this.mFlagImageView.setImageDrawable(drawable);
    }

    public boolean isValid() {
        return this.mIsValid;
    }

    public int getAllocHeight() {
        return this.mAllocHeight;
    }

    public void setAllocHeight(int i) {
        this.mAllocHeight = i;
        requestLayout();
    }

    public void setTextSize(int i, float f) {
        this.mTextView.setTextSize(i, f);
    }

    public void setTextColor(int i) {
        this.mTextView.setTextColor(i);
    }

    private ValueAnimator obtainFadeAnimator(final View view, final boolean z) {
        ValueAnimator a = z ? ert.a() : ert.b();
        a.setTarget(view);
        a.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                view.setAlpha(z ? 1.0f : 0.0f);
                RouteInputLineView.this.mInAnimating = false;
            }
        });
        return a;
    }

    private ValueAnimator obtainChangeAnimator(boolean z, int i) {
        int[] iArr = new int[2];
        iArr[0] = z ? 0 : i;
        if (!z) {
            i = 0;
        }
        iArr[1] = i;
        ValueAnimator a = ert.a(iArr);
        a.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                RouteInputLineView.this.setAllocHeight(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        a.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                RouteInputLineView.this.setAllocHeight(-1);
                RouteInputLineView.this.mOutAnimating = false;
            }
        });
        return a;
    }

    private void cancelAnimator() {
        if (this.mInAnimating && this.mInAnimator != null) {
            this.mInAnimator.end();
        }
        if (this.mOutAnimating && this.mOutAnimator != null) {
            this.mOutAnimator.end();
        }
        this.mInAnimating = false;
        this.mOutAnimating = false;
        this.mInAnimator = null;
        this.mOutAnimator = null;
    }

    public void startAddAnimator(int i) {
        cancelAnimator();
        setAlpha(0.0f);
        this.mInAnimator = new AnimatorSet();
        this.mInAnimator.play(obtainChangeAnimator(true, i)).before(obtainFadeAnimator(this, true));
        this.mInAnimator.start();
    }

    public void startRemoveAnimator(int i, AnimatorListener animatorListener) {
        cancelAnimator();
        this.mOutAnimator = new AnimatorSet();
        ValueAnimator obtainChangeAnimator = obtainChangeAnimator(false, i);
        if (animatorListener != null) {
            obtainChangeAnimator.addListener(animatorListener);
        }
        this.mOutAnimator.play(obtainChangeAnimator).after(obtainFadeAnimator(this, false));
        this.mOutAnimator.start();
    }

    public void onClick(View view) {
        if (this.mListener != null) {
        }
    }
}
