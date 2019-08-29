package com.amap.bundle.planhome.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.State;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.c;
import com.autonavi.bundle.vui.common.emojiview.VUIEmojiView;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;

public class RouteFrameLayout extends ViewGroup implements OnClickListener {
    private static int sIconGap;
    private static int sIconMargin;
    private static int sIconSize;
    private ImageView mAddImageView;
    /* access modifiers changed from: private */
    public AnimatorListenerAdapter mAnimatorListener;
    private a mChangeStateRunnable;
    private ImageView mExchangeImageView;
    private int mExpectAddVisibility;
    private int mExpectHeight;
    private int mExpectVUIVisibility;
    /* access modifiers changed from: private */
    public ValueAnimator mFadeInAnimator;
    /* access modifiers changed from: private */
    public ValueAnimator mFadeOutAnimator;
    private RouteEditMultiLineView mMultiLineView;
    private c mOnRouteEditClickListener;
    private State mState;
    private RouteEditMultiLineView mSummaryView;
    /* access modifiers changed from: private */
    public AnimatorUpdateListener mUpdateListener;
    /* access modifiers changed from: private */
    public ValueAnimator mValueAnimator;
    private VUIEmojiView mVuiEnojiView;
    private long sLastClickTimestamp;

    class a implements Runnable {
        View a;
        /* access modifiers changed from: 0000 */
        public View b;
        boolean c = false;
        /* access modifiers changed from: private */
        public int e = -1;
        /* access modifiers changed from: private */
        public int f = -1;

        public a(View view, View view2) {
            this.a = view;
            this.b = view2;
        }

        public final void run() {
            this.e = this.a.getMeasuredHeight();
            this.f = Math.max(this.a.getMeasuredHeight(), this.b.getMeasuredHeight());
            RouteFrameLayout.this.mValueAnimator = ValueAnimator.ofInt(new int[]{this.b.getMeasuredHeight(), this.e});
            RouteFrameLayout.this.mValueAnimator.setDuration(ach.b);
            RouteFrameLayout.this.mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if (RouteFrameLayout.this.mUpdateListener != null) {
                        RouteFrameLayout.this.mUpdateListener.onAnimationUpdate(valueAnimator);
                    }
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    if (intValue == a.this.e) {
                        intValue = -1;
                    }
                    RouteFrameLayout.this.setExpectHeight(intValue);
                }
            });
            RouteFrameLayout.this.mValueAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationStart(Animator animator) {
                    if (RouteFrameLayout.this.mAnimatorListener != null) {
                        RouteFrameLayout.this.mAnimatorListener.onAnimationStart(animator);
                    }
                }

                public final void onAnimationEnd(Animator animator) {
                    if (RouteFrameLayout.this.mAnimatorListener != null) {
                        RouteFrameLayout.this.mAnimatorListener.onAnimationEnd(animator);
                    }
                    a.this.b.setVisibility(8);
                    a.this.b.setAlpha(1.0f);
                    a.this.f = -1;
                    RouteFrameLayout.this.setExpectHeight(-1);
                    RouteFrameLayout.this.mAnimatorListener = null;
                    RouteFrameLayout.this.mUpdateListener = null;
                    RouteFrameLayout.this.mValueAnimator = null;
                    RouteFrameLayout.this.mFadeInAnimator = null;
                    RouteFrameLayout.this.mFadeOutAnimator = null;
                    RouteFrameLayout.this.cancelAnimator();
                }
            });
            RouteFrameLayout.this.mFadeOutAnimator = ach.d(this.b);
            RouteFrameLayout.this.mFadeOutAnimator.setDuration(ach.b);
            DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
            RouteFrameLayout.this.mFadeOutAnimator.setInterpolator(decelerateInterpolator);
            RouteFrameLayout.this.mFadeInAnimator = ach.c(this.a);
            RouteFrameLayout.this.mFadeInAnimator.setDuration(ach.b);
            RouteFrameLayout.this.mFadeInAnimator.setInterpolator(decelerateInterpolator);
            RouteFrameLayout.this.mValueAnimator.setInterpolator(decelerateInterpolator);
            RouteFrameLayout.this.mValueAnimator.start();
            RouteFrameLayout.this.mFadeOutAnimator.start();
            RouteFrameLayout.this.mFadeInAnimator.start();
        }
    }

    public RouteFrameLayout(Context context) {
        this(context, null);
    }

    public RouteFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mExpectHeight = -1;
        this.mExpectAddVisibility = 0;
        this.mExpectVUIVisibility = 0;
        this.mState = State.EDIT;
        init(context);
    }

    private void init(Context context) {
        ensureDimensValue();
        initView(context);
        addViewInLayout(this.mAddImageView, 0, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mExchangeImageView, 1, generateDefaultLayoutParams(), true);
        int i = 2;
        if (VUIStateManager.f().m()) {
            addViewInLayout(this.mVuiEnojiView, 2, generateDefaultLayoutParams(), true);
            i = 3;
        }
        addViewInLayout(this.mMultiLineView, i, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mSummaryView, i + 1, generateDefaultLayoutParams(), true);
        this.mExchangeImageView.bringToFront();
    }

    private void ensureDimensValue() {
        if (sIconSize <= 0) {
            sIconSize = getResources().getDimensionPixelOffset(R.dimen.route_edit_icon_size);
            sIconMargin = getResources().getDimensionPixelOffset(R.dimen.route_edit_icon_margin);
            sIconGap = getResources().getDimensionPixelOffset(R.dimen.route_edit_icon_gap);
        }
    }

    private void initView(Context context) {
        initAddView(context);
        initExchangeView(context);
        initVuiEmojiView(context);
        this.mMultiLineView = new RouteEditMultiLineView(context);
        this.mMultiLineView.setAddImageView(this.mAddImageView);
        this.mSummaryView = new RouteEditMultiLineView(context);
        this.mSummaryView.setAddImageView(this.mAddImageView);
        this.mSummaryView.setState(State.SUMMARY);
        this.mSummaryView.setupSummaryState();
        this.mSummaryView.setVisibility(8);
    }

    private void initAddView(Context context) {
        this.mAddImageView = new ImageView(context);
        this.mAddImageView.setId(R.id.route_edit_add);
        this.mAddImageView.setScaleType(ScaleType.CENTER);
        this.mAddImageView.setPadding(0, 0, sIconGap, 0);
        this.mAddImageView.setImageResource(R.drawable.icon_route_edit_add_selector);
        this.mAddImageView.setOnClickListener(this);
    }

    private void initExchangeView(Context context) {
        this.mExchangeImageView = new ImageView(context);
        this.mExchangeImageView.setId(R.id.route_edit_exchange);
        this.mExchangeImageView.setScaleType(ScaleType.CENTER);
        this.mExchangeImageView.setImageResource(R.drawable.icon_route_edit_exchange_selector);
        this.mExchangeImageView.setOnClickListener(this);
    }

    private void initVuiEmojiView(Context context) {
        this.mVuiEnojiView = new VUIEmojiView(context);
    }

    public RouteEditMultiLineView getMultiLineView() {
        return this.mMultiLineView;
    }

    public RouteEditMultiLineView getSummaryView() {
        return this.mSummaryView;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int size = MeasureSpec.getSize(i);
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(sIconSize, UCCore.VERIFY_POLICY_QUICK);
        int i4 = sIconMargin;
        if (isVuiEmojiShow()) {
            this.mVuiEnojiView.measure(makeMeasureSpec, makeMeasureSpec);
            i4 += this.mExchangeImageView.getMeasuredWidth();
        }
        if (this.mExchangeImageView.getVisibility() != 8) {
            this.mExchangeImageView.measure(MeasureSpec.makeMeasureSpec(sIconSize + sIconGap, UCCore.VERIFY_POLICY_QUICK), makeMeasureSpec);
            i4 += this.mExchangeImageView.getMeasuredWidth();
        }
        if (this.mAddImageView.getVisibility() != 8) {
            this.mAddImageView.measure(MeasureSpec.makeMeasureSpec(sIconSize + sIconGap, UCCore.VERIFY_POLICY_QUICK), makeMeasureSpec);
            i4 += this.mAddImageView.getMeasuredWidth();
        }
        if (i4 > sIconMargin) {
            i4 += sIconMargin;
        }
        int i5 = size - i4;
        boolean z = false;
        int max = Math.max(0, i5);
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(size * 2, Integer.MIN_VALUE);
        int makeMeasureSpec3 = MeasureSpec.makeMeasureSpec(max, UCCore.VERIFY_POLICY_QUICK);
        if (this.mMultiLineView.getVisibility() != 8) {
            this.mMultiLineView.measure(makeMeasureSpec3, makeMeasureSpec2);
            i3 = Math.max(0, this.mMultiLineView.getMeasuredHeight());
        } else {
            i3 = 0;
        }
        if (this.mSummaryView.getVisibility() != 8) {
            this.mSummaryView.measure(makeMeasureSpec3, makeMeasureSpec2);
            i3 = Math.max(i3, this.mSummaryView.getMeasuredHeight());
        }
        if (this.mChangeStateRunnable != null && !this.mChangeStateRunnable.c) {
            z = true;
        }
        if (z) {
            this.mChangeStateRunnable.c = true;
            this.mExpectHeight = this.mChangeStateRunnable.b.getMeasuredHeight();
            this.mChangeStateRunnable.run();
        }
        if (this.mExpectHeight >= 0) {
            i3 = this.mExpectHeight;
        }
        setMeasuredDimension(resolveSize(size, i), resolveSize(i3, i2));
    }

    public void setOnRouteEditClickListener(c cVar) {
        this.mOnRouteEditClickListener = cVar;
        this.mMultiLineView.setOnRouteEditClickListener(cVar);
        this.mSummaryView.setOnRouteEditClickListener(cVar);
    }

    public void changeState(State state, boolean z, AnimatorListenerAdapter animatorListenerAdapter, @Nullable AnimatorUpdateListener animatorUpdateListener) {
        if (this.mState != state) {
            State state2 = this.mState;
            this.mState = state;
            if (!z || !(state2 == State.SUMMARY || state == State.SUMMARY)) {
                switch (state) {
                    case EDIT:
                        this.mSummaryView.setVisibility(8);
                        this.mMultiLineView.setVisibility(0);
                        this.mMultiLineView.setState(State.EDIT);
                        return;
                    case SUMMARY:
                        this.mSummaryView.setVisibility(0);
                        this.mMultiLineView.setVisibility(8);
                        return;
                    case PRE_EDIT:
                        this.mSummaryView.setVisibility(8);
                        this.mMultiLineView.setVisibility(0);
                        this.mMultiLineView.setState(State.PRE_EDIT);
                        break;
                }
                return;
            }
            smoothChangeState(state, animatorListenerAdapter, animatorUpdateListener);
        }
    }

    public State getState() {
        return this.mState;
    }

    public void exchangeAnimator(AnimatorListener animatorListener) {
        if (isEditState()) {
            this.mMultiLineView.exchangeAnimator(animatorListener);
        } else {
            this.mSummaryView.exchangeAnimator(animatorListener);
        }
    }

    public void setAddExceptVisibility(int i) {
        if (this.mExpectAddVisibility != i) {
            this.mExpectAddVisibility = i;
            this.mAddImageView.setVisibility(this.mExpectAddVisibility);
        }
    }

    public void setVUIExceptVisibility(int i) {
        if (this.mExpectVUIVisibility != i) {
            this.mExpectVUIVisibility = i;
            this.mVuiEnojiView.setVisibility(this.mExpectVUIVisibility);
            this.mVuiEnojiView.requestLayout();
        }
    }

    public int getAddExceptVisibility() {
        return this.mExpectAddVisibility;
    }

    public void setEditable(boolean z) {
        this.mMultiLineView.setEditable(z);
        this.mSummaryView.setEditable(z);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i4 - i2;
        int i6 = (i3 - i) - sIconMargin;
        if (isVuiEmojiShow()) {
            int measuredHeight = (i5 - this.mVuiEnojiView.getMeasuredHeight()) >> 1;
            VUIEmojiView vUIEmojiView = this.mVuiEnojiView;
            vUIEmojiView.layout(i6 - vUIEmojiView.getMeasuredWidth(), measuredHeight, i6, this.mVuiEnojiView.getMeasuredHeight() + measuredHeight);
            i6 -= this.mVuiEnojiView.getMeasuredWidth();
        }
        if (this.mExchangeImageView.getVisibility() != 8) {
            int measuredHeight2 = (i5 - this.mExchangeImageView.getMeasuredHeight()) >> 1;
            ImageView imageView = this.mExchangeImageView;
            imageView.layout(i6 - imageView.getMeasuredWidth(), measuredHeight2, i6, this.mExchangeImageView.getMeasuredHeight() + measuredHeight2);
            i6 -= this.mExchangeImageView.getMeasuredWidth();
        }
        if (this.mAddImageView.getVisibility() != 8) {
            int measuredHeight3 = (i5 - this.mAddImageView.getMeasuredHeight()) >> 1;
            ImageView imageView2 = this.mAddImageView;
            imageView2.layout(i6 - imageView2.getMeasuredWidth(), measuredHeight3, i6, this.mAddImageView.getMeasuredHeight() + measuredHeight3);
        }
        if (this.mMultiLineView.getVisibility() != 8) {
            this.mMultiLineView.layout(0, 0, this.mMultiLineView.getMeasuredWidth(), this.mMultiLineView.getMeasuredHeight());
        }
        if (this.mSummaryView.getVisibility() != 8) {
            this.mSummaryView.layout(0, 0, this.mSummaryView.getMeasuredWidth(), this.mSummaryView.getMeasuredHeight());
        }
    }

    public void onClick(View view) {
        if (this.mOnRouteEditClickListener == null || interceptClickEvent(500)) {
            return;
        }
        if (view == this.mExchangeImageView) {
            this.mOnRouteEditClickListener.a(view, 3);
            return;
        }
        if (view == this.mAddImageView) {
            this.mOnRouteEditClickListener.a(view, 2);
        }
    }

    /* access modifiers changed from: private */
    public void cancelAnimator() {
        if (this.mValueAnimator != null) {
            this.mValueAnimator.end();
            this.mValueAnimator = null;
        }
        if (this.mFadeInAnimator != null) {
            this.mFadeInAnimator.end();
            this.mFadeInAnimator = null;
        }
        if (this.mFadeOutAnimator != null) {
            this.mFadeOutAnimator.end();
            this.mFadeOutAnimator = null;
        }
        if (this.mChangeStateRunnable != null) {
            this.mChangeStateRunnable.a.setAlpha(1.0f);
            this.mChangeStateRunnable.b.setVisibility(8);
            this.mChangeStateRunnable.b.setAlpha(1.0f);
            this.mChangeStateRunnable = null;
        }
    }

    public int getExpectHeight() {
        if (this.mChangeStateRunnable != null) {
            return this.mChangeStateRunnable.e;
        }
        return -1;
    }

    public int getMaxHeight() {
        if (this.mChangeStateRunnable != null) {
            return this.mChangeStateRunnable.f;
        }
        return -1;
    }

    private void smoothChangeState(State state, AnimatorListenerAdapter animatorListenerAdapter, AnimatorUpdateListener animatorUpdateListener) {
        cancelAnimator();
        this.mAnimatorListener = animatorListenerAdapter;
        this.mUpdateListener = animatorUpdateListener;
        this.mSummaryView.setVisibility(0);
        this.mMultiLineView.setVisibility(0);
        if (state == State.SUMMARY) {
            this.mSummaryView.setAlpha(0.0f);
            this.mMultiLineView.setAlpha(1.0f);
            this.mChangeStateRunnable = new a(this.mSummaryView, this.mMultiLineView);
        } else {
            this.mMultiLineView.setState(state);
            this.mMultiLineView.setAlpha(0.0f);
            this.mSummaryView.setAlpha(1.0f);
            this.mChangeStateRunnable = new a(this.mMultiLineView, this.mSummaryView);
        }
        requestLayout();
    }

    /* access modifiers changed from: private */
    public void setExpectHeight(int i) {
        if (this.mExpectHeight != i) {
            this.mExpectHeight = i;
            requestLayout();
        }
    }

    private boolean isEditState() {
        return this.mState == State.EDIT || this.mState == State.PRE_EDIT;
    }

    private boolean isVuiEmojiShow() {
        return (this.mVuiEnojiView.getVisibility() == 8 || this.mVuiEnojiView.getEmojiVisibility() == 8 || !VUIStateManager.f().m()) ? false : true;
    }

    private boolean interceptClickEvent(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - this.sLastClickTimestamp) < ((long) i)) {
            return true;
        }
        this.sLastClickTimestamp = currentTimeMillis;
        return false;
    }
}
