package com.autonavi.widget.ui.route;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RouteInputLinesView extends ViewGroup {
    private int mDividerHeight;
    private RouteInputLineView mEndLineView;
    /* access modifiers changed from: private */
    public boolean mIsExchanging;
    private int mLineHeight;
    private eru mListener;
    private List<RouteInputLineView> mPassLineViews;
    private Stack<RouteInputLineView> mRecycler;
    private RouteInputLineView mStartLineView;

    public RouteInputLinesView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsExchanging = false;
        this.mPassLineViews = new ArrayList();
        this.mRecycler = new Stack<>();
        init(context);
    }

    public RouteInputLinesView(Context context) {
        this(context, null);
    }

    public RouteInputLinesView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private void init(Context context) {
        setClipChildren(false);
        setClipToPadding(false);
        this.mDividerHeight = getResources().getDimensionPixelOffset(R.dimen.route_input_universal_margin);
        this.mLineHeight = getResources().getDimensionPixelOffset(R.dimen.route_input_line_height);
        this.mStartLineView = new RouteInputLineView(context);
        this.mStartLineView.setFlagText(context.getString(R.string.route_input_from));
        this.mStartLineView.setBackgroundResource(R.drawable.route_bg_input_line);
        this.mEndLineView = new RouteInputLineView(context);
        this.mEndLineView.setFlagText(context.getString(R.string.route_input_to));
        this.mEndLineView.setBackgroundResource(R.drawable.route_bg_input_line);
        this.mEndLineView.setPosition(2);
        addViewInLayout(this.mStartLineView, 0, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mEndLineView, 1, generateDefaultLayoutParams(), true);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int childCount = getChildCount();
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(size, UCCore.VERIFY_POLICY_QUICK);
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(this.mLineHeight, UCCore.VERIFY_POLICY_QUICK);
        int i3 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (!(childAt == null || childAt.getVisibility() == 8)) {
                if (childAt instanceof RouteInputLineView) {
                    RouteInputLineView routeInputLineView = (RouteInputLineView) childAt;
                    if (routeInputLineView.getAllocHeight() >= 0) {
                        i3 += routeInputLineView.getAllocHeight();
                    }
                }
                childAt.measure(makeMeasureSpec, makeMeasureSpec2);
                i3 += childAt.getMeasuredHeight();
                if (i4 > 0) {
                    i3 += this.mDividerHeight;
                }
            }
        }
        setMeasuredDimension(resolveSize(size, i), resolveSize(i3, i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (!(childAt == null || childAt.getVisibility() == 8)) {
                if (childAt instanceof RouteInputLineView) {
                    RouteInputLineView routeInputLineView = (RouteInputLineView) childAt;
                    if (routeInputLineView.getAllocHeight() >= 0) {
                        i5 += routeInputLineView.getAllocHeight();
                    }
                }
                if (i6 > 0) {
                    i5 += this.mDividerHeight;
                }
                childAt.layout(0, i5, childAt.getMeasuredWidth(), childAt.getMeasuredHeight() + i5);
                i5 += childAt.getMeasuredHeight();
            }
        }
    }

    public boolean canAddPassView() {
        return this.mPassLineViews == null || this.mPassLineViews.size() < 3;
    }

    public void setOnRouteInputClickListener(eru eru) {
        this.mListener = eru;
        this.mStartLineView.setOnRouteInputClickListener(eru);
        this.mEndLineView.setOnRouteInputClickListener(eru);
        for (int i = 0; i < this.mPassLineViews.size(); i++) {
            this.mPassLineViews.get(i).setOnRouteInputClickListener(eru);
        }
    }

    public void setStartText(CharSequence charSequence) {
        this.mStartLineView.setText(charSequence);
    }

    public void setEndText(CharSequence charSequence) {
        this.mEndLineView.setText(charSequence);
    }

    private CharSequence getSinglePassText(int i, CharSequence... charSequenceArr) {
        if (charSequenceArr == null || charSequenceArr.length <= 0) {
            return "";
        }
        return i < charSequenceArr.length ? charSequenceArr[i] : "";
    }

    public void setPassText(CharSequence... charSequenceArr) {
        int size = this.mPassLineViews.size();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                this.mPassLineViews.get(i).setText(getSinglePassText(i, charSequenceArr));
            }
        }
    }

    public void exchangeAnimator() {
        int abs = Math.abs(this.mEndLineView.getTop() - this.mStartLineView.getTop());
        ValueAnimator a = ert.a(abs);
        a.setTarget(this.mStartLineView.getTextView());
        ValueAnimator b = ert.b(abs);
        b.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                RouteInputLinesView.this.invalidate();
            }
        });
        b.setTarget(this.mEndLineView.getTextView());
        AnimatorSet animatorSet = new AnimatorSet();
        Builder play = animatorSet.play(a);
        play.with(b);
        passExchangeAnimator(play);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                RouteInputLinesView.this.mIsExchanging = false;
                RouteInputLinesView.this.invalidate();
            }

            public final void onAnimationStart(Animator animator) {
                RouteInputLinesView.this.mIsExchanging = true;
                RouteInputLinesView.this.invalidate();
            }
        });
        animatorSet.start();
    }

    private void passExchangeAnimator(Builder builder) {
        RouteInputLineView routeInputLineView;
        RouteInputLineView routeInputLineView2;
        if (this.mPassLineViews.size() >= 2) {
            if (this.mPassLineViews.size() == 2) {
                routeInputLineView2 = this.mPassLineViews.get(0);
                routeInputLineView = this.mPassLineViews.get(1);
            } else {
                routeInputLineView2 = this.mPassLineViews.get(0);
                routeInputLineView = this.mPassLineViews.get(2);
            }
            int abs = Math.abs(routeInputLineView.getTop() - routeInputLineView2.getTop());
            ValueAnimator a = ert.a(abs);
            a.setTarget(routeInputLineView2.getTextView());
            ValueAnimator b = ert.b(abs);
            b.setTarget(routeInputLineView.getTextView());
            builder.with(a);
            builder.with(b);
        }
    }

    private RouteInputLineView obtainView() {
        if (!this.mRecycler.isEmpty()) {
            return this.mRecycler.pop();
        }
        RouteInputLineView routeInputLineView = new RouteInputLineView(getContext());
        routeInputLineView.setImageDrawable(getResources().getDrawable(R.drawable.route_icon_input_delete_selector));
        routeInputLineView.setBackgroundResource(R.drawable.route_bg_input_line);
        routeInputLineView.setPosition(4);
        routeInputLineView.getFlagImageView().setContentDescription(getResources().getString(R.string.route_input_description_delete_pass));
        return routeInputLineView;
    }

    /* access modifiers changed from: private */
    public void recycleView(RouteInputLineView routeInputLineView) {
        removeView(routeInputLineView);
        routeInputLineView.reset();
        this.mRecycler.add(routeInputLineView);
    }

    public boolean removePassView(final RouteInputLineView routeInputLineView, boolean z) {
        if (!this.mPassLineViews.contains(routeInputLineView) || routeInputLineView == null) {
            return false;
        }
        this.mPassLineViews.remove(routeInputLineView);
        if (z) {
            routeInputLineView.startRemoveAnimator(routeInputLineView.getMeasuredHeight() + this.mDividerHeight, new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    RouteInputLinesView.this.recycleView(routeInputLineView);
                }
            });
        } else {
            recycleView(routeInputLineView);
        }
        return true;
    }

    public RouteInputLineView addPassView(boolean z) {
        if (!canAddPassView() || getMeasuredWidth() <= 0) {
            return null;
        }
        RouteInputLineView obtainView = obtainView();
        obtainView.setOnRouteInputClickListener(this.mListener);
        if (z) {
            obtainView.startAddAnimator(getChildMeasuredHeight(obtainView) + this.mDividerHeight);
        }
        addView(obtainView, getChildCount() - 1, new LayoutParams(-1, -2));
        this.mPassLineViews.add(obtainView);
        return obtainView;
    }

    private int getChildMeasuredHeight(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(this.mLineHeight, UCCore.VERIFY_POLICY_QUICK));
        return view.getMeasuredHeight();
    }

    public RouteInputLineView getStartLineView() {
        return this.mStartLineView;
    }

    public RouteInputLineView getEndLineView() {
        return this.mEndLineView;
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        resetAlpha();
        super.dispatchDraw(canvas);
        if (this.mIsExchanging) {
            drawTextViews(canvas);
        }
    }

    private void drawTextViews(Canvas canvas) {
        drawTextView(canvas, this.mStartLineView);
        drawTextView(canvas, this.mEndLineView);
        if (this.mPassLineViews.size() >= 2) {
            if (this.mPassLineViews.size() == 2) {
                drawTextView(canvas, this.mPassLineViews.get(0));
                drawTextView(canvas, this.mPassLineViews.get(1));
                return;
            }
            if (this.mPassLineViews.size() == 3) {
                drawTextView(canvas, this.mPassLineViews.get(0));
                drawTextView(canvas, this.mPassLineViews.get(2));
            }
        }
    }

    private boolean resetAlpha() {
        if (this.mIsExchanging || this.mStartLineView.getTextView().getAlpha() >= 1.0f) {
            return false;
        }
        this.mStartLineView.getTextView().setAlpha(1.0f);
        this.mEndLineView.getTextView().setAlpha(1.0f);
        for (int i = 0; i < this.mPassLineViews.size(); i++) {
            this.mPassLineViews.get(i).getTextView().setAlpha(1.0f);
        }
        return true;
    }

    private void drawTextView(Canvas canvas, RouteInputLineView routeInputLineView) {
        if (routeInputLineView.getVisibility() == 0) {
            TextView textView = routeInputLineView.getTextView();
            textView.setAlpha(1.0f);
            int save = canvas.save();
            canvas.translate((float) (textView.getLeft() + routeInputLineView.getLeft()), ((float) (routeInputLineView.getTop() + textView.getTop())) + textView.getTranslationY());
            textView.draw(canvas);
            canvas.restoreToCount(save);
            textView.setAlpha(0.0f);
        }
    }
}
