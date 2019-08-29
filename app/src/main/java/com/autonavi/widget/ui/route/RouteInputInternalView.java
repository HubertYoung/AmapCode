package com.autonavi.widget.ui.route;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewGroupCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;

public class RouteInputInternalView extends ViewGroup implements OnClickListener {
    private ValueAnimator mAddAnimator;
    private ImageView mAddImageView;
    private ImageView mBackImageView;
    private int mContentHeight;
    private ImageView mExchangeImageView;
    private a mLayoutConfig;
    private RouteInputLinesView mLinesView;
    private eru mListener;
    private boolean mShowSummary;
    private RouteInputSummaryView mSummaryView;

    public static class a {
        public int a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public int h;
        public int i;
        public int j;

        static int a(Context context, int i2) {
            return context.getResources().getDimensionPixelOffset(i2);
        }
    }

    public boolean dismissOverLayer() {
        return false;
    }

    public boolean isOverLayerShowing() {
        return false;
    }

    public void setParentView(RelativeLayout relativeLayout) {
    }

    public boolean showOverLayer(boolean z) {
        return false;
    }

    public RouteInputInternalView(Context context) {
        this(context, null);
    }

    public RouteInputInternalView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteInputInternalView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowSummary = false;
        init(context);
    }

    private void init(Context context) {
        setClickable(false);
        ViewGroupCompat.setMotionEventSplittingEnabled(this, false);
        this.mLayoutConfig = new a();
        a aVar = this.mLayoutConfig;
        aVar.a = a.a(context, R.dimen.route_input_left);
        aVar.b = a.a(context, R.dimen.route_input_right);
        aVar.e = a.a(context, R.dimen.route_input_line_height);
        aVar.c = a.a(context, R.dimen.route_input_back_margin_left);
        aVar.d = a.a(context, R.dimen.route_input_back_width);
        aVar.f = a.a(context, R.dimen.route_input_back_margin_left);
        aVar.g = a.a(context, R.dimen.route_input_add_width);
        aVar.h = a.a(context, R.dimen.route_input_summary_height);
        aVar.i = a.a(context, R.dimen.route_input_padding_top);
        aVar.j = a.a(context, R.dimen.route_input_padding_bottom);
        this.mBackImageView = new RouteInputImageView(context);
        this.mBackImageView.setScaleType(ScaleType.CENTER);
        this.mBackImageView.setImageResource(R.drawable.icon_a15_selector);
        this.mBackImageView.setOnClickListener(this);
        this.mBackImageView.setContentDescription(getResources().getString(R.string.default_back));
        this.mAddImageView = new RouteInputImageView(context);
        this.mAddImageView.setScaleType(ScaleType.CENTER);
        this.mAddImageView.setImageResource(R.drawable.route_icon_input_add_selector);
        this.mAddImageView.setOnClickListener(this);
        this.mAddImageView.setContentDescription(getResources().getString(R.string.route_input_description_add_pass));
        this.mExchangeImageView = new RouteInputImageView(context);
        this.mExchangeImageView.setScaleType(ScaleType.CENTER);
        this.mExchangeImageView.setImageResource(R.drawable.route_icon_input_exchange_selector);
        this.mExchangeImageView.setOnClickListener(this);
        this.mExchangeImageView.setContentDescription(getResources().getString(R.string.route_input_description_exchange));
        this.mLinesView = new RouteInputLinesView(context);
        this.mSummaryView = new RouteInputSummaryView(context);
        this.mSummaryView.setBackgroundResource(R.drawable.route_bg_input_line);
        int i = (int) (getResources().getDisplayMetrics().density * 4.0f);
        this.mSummaryView.setPadding(0, i, getResources().getDimensionPixelOffset(R.dimen.route_input_universal_margin), i);
        this.mSummaryView.setVisibility(8);
        this.mSummaryView.setOnClickListener(this);
        addViewInLayout(this.mBackImageView, 0, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mAddImageView, 1, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mExchangeImageView, 2, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mLinesView, 3, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mSummaryView, 4, generateDefaultLayoutParams(), true);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        int i3 = 0;
        this.mContentHeight = 0;
        int i4 = (size - this.mLayoutConfig.a) - this.mLayoutConfig.b;
        if (this.mLinesView.getVisibility() != 8) {
            this.mLinesView.measure(MeasureSpec.makeMeasureSpec(i4, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(size2, 0));
            i3 = 0 + this.mLinesView.getMeasuredHeight();
        }
        if (this.mSummaryView.getVisibility() != 8) {
            this.mSummaryView.measure(MeasureSpec.makeMeasureSpec(i4, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(this.mLayoutConfig.h, UCCore.VERIFY_POLICY_QUICK));
            i3 += this.mSummaryView.getMeasuredHeight();
        }
        if (this.mBackImageView.getVisibility() != 8) {
            this.mBackImageView.measure(MeasureSpec.makeMeasureSpec(this.mLayoutConfig.d, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(this.mLayoutConfig.e, UCCore.VERIFY_POLICY_QUICK));
        }
        this.mAddImageView.measure(MeasureSpec.makeMeasureSpec(this.mLayoutConfig.g, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(this.mLayoutConfig.e, UCCore.VERIFY_POLICY_QUICK));
        if (this.mExchangeImageView.getVisibility() != 8) {
            int makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.mLayoutConfig.e, UCCore.VERIFY_POLICY_QUICK);
            this.mExchangeImageView.measure(makeMeasureSpec, makeMeasureSpec);
        }
        this.mContentHeight = i3;
        setMeasuredDimension(resolveSize(size, i), resolveSize(i3 + this.mLayoutConfig.i + this.mLayoutConfig.j, i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = this.mLayoutConfig.i;
        int i6 = i3 - i;
        this.mAddImageView.layout(this.mLayoutConfig.f, (this.mContentHeight + i5) - this.mAddImageView.getMeasuredHeight(), this.mLayoutConfig.f + this.mAddImageView.getMeasuredWidth(), this.mContentHeight + i5);
        if (this.mBackImageView.getVisibility() != 8) {
            this.mBackImageView.layout(this.mLayoutConfig.c, i5, this.mLayoutConfig.f + this.mBackImageView.getMeasuredHeight(), this.mBackImageView.getMeasuredHeight() + i5);
        }
        if (this.mExchangeImageView.getVisibility() != 8) {
            int measuredHeight = i5 + ((this.mContentHeight - this.mExchangeImageView.getMeasuredHeight()) / 2);
            int measuredWidth = i6 - ((this.mLayoutConfig.b - this.mExchangeImageView.getMeasuredWidth()) / 2);
            ImageView imageView = this.mExchangeImageView;
            imageView.layout(measuredWidth - imageView.getMeasuredWidth(), measuredHeight, measuredWidth, this.mExchangeImageView.getMeasuredHeight() + measuredHeight);
        }
        int i7 = this.mLayoutConfig.a;
        int i8 = this.mLayoutConfig.i;
        if (this.mLinesView.getVisibility() != 8) {
            this.mLinesView.layout(i7, i8, this.mLinesView.getMeasuredWidth() + i7, this.mLinesView.getMeasuredHeight() + i8);
            i8 += this.mLinesView.getMeasuredHeight();
        }
        if (this.mSummaryView.getVisibility() != 8) {
            this.mSummaryView.layout(i7, i8, this.mSummaryView.getMeasuredWidth() + i7, this.mSummaryView.getMeasuredHeight() + i8);
            this.mSummaryView.getMeasuredHeight();
        }
    }

    public void onClick(View view) {
        if (this.mListener != null) {
        }
    }

    public RouteInputLineView addPassView(boolean z) {
        RouteInputLineView addPassView = this.mLinesView.addPassView(z);
        if (this.mShowSummary) {
            return addPassView;
        }
        int i = this.mLinesView.canAddPassView() ? 0 : 8;
        if (i != this.mAddImageView.getVisibility()) {
            setAddVisibility(i, true);
        }
        return addPassView;
    }

    public boolean removePassView(RouteInputLineView routeInputLineView, boolean z) {
        boolean removePassView = this.mLinesView.removePassView(routeInputLineView, z);
        if (this.mShowSummary) {
            return removePassView;
        }
        int i = this.mLinesView.canAddPassView() ? 0 : 8;
        if (i != this.mAddImageView.getVisibility()) {
            setAddVisibility(i, true);
        }
        return removePassView;
    }

    public void exchangeAnimator() {
        if (this.mLinesView.getVisibility() != 8) {
            this.mLinesView.exchangeAnimator();
        }
        if (this.mSummaryView.getVisibility() != 8) {
            this.mSummaryView.exchangeAnimator();
        }
    }

    public void setExchangeEnable(boolean z) {
        this.mExchangeImageView.setEnabled(z);
    }

    public void setOnRouteInputClickListener(eru eru) {
        this.mListener = eru;
        this.mLinesView.setOnRouteInputClickListener(eru);
        this.mSummaryView.setOnRouteInputClickListener(eru);
    }

    public void setStartText(CharSequence charSequence) {
        this.mLinesView.setStartText(charSequence);
        this.mSummaryView.setStartText(charSequence);
    }

    public void setEndText(CharSequence charSequence) {
        this.mLinesView.setEndText(charSequence);
        this.mSummaryView.setEndText(charSequence);
    }

    public void setPassText(CharSequence... charSequenceArr) {
        this.mLinesView.setPassText(charSequenceArr);
        this.mSummaryView.setPassText(charSequenceArr);
    }

    public void animateContentView() {
        if (this.mLinesView.getVisibility() != 8) {
            ValueAnimator a2 = ert.a();
            a2.setDuration(a2.getDuration() * 2);
            a2.setTarget(this.mLinesView);
            a2.start();
        }
        if (this.mSummaryView.getVisibility() != 8) {
            ValueAnimator a3 = ert.a();
            a3.setDuration(a3.getDuration() * 2);
            a3.setTarget(this.mSummaryView);
            a3.start();
        }
    }

    public void showSummary(boolean z, boolean z2) {
        int i = 0;
        if (z2) {
            cancelAddAnimator();
            this.mAddImageView.setVisibility((z || !this.mLinesView.canAddPassView()) ? 8 : 0);
        } else if (z) {
            cancelAddAnimator();
            this.mAddImageView.setVisibility(8);
        }
        if (this.mShowSummary != z) {
            this.mShowSummary = z;
            this.mLinesView.setVisibility(this.mShowSummary ? 8 : 0);
            RouteInputSummaryView routeInputSummaryView = this.mSummaryView;
            if (!this.mShowSummary) {
                i = 8;
            }
            routeInputSummaryView.setVisibility(i);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void setAddVisibility(int i, boolean z) {
        if (!z) {
            cancelAddAnimator();
            this.mAddImageView.setVisibility(i);
            return;
        }
        startVisibilityAnimator(this.mAddImageView, i);
    }

    public RouteInputSummaryView getSummaryView() {
        return this.mSummaryView;
    }

    public void setDescription(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5) {
        if (!TextUtils.isEmpty(charSequence)) {
            this.mBackImageView.setContentDescription(charSequence);
        }
        if (!TextUtils.isEmpty(charSequence2)) {
            this.mAddImageView.setContentDescription(charSequence2);
        }
        if (!TextUtils.isEmpty(charSequence3)) {
            this.mExchangeImageView.setContentDescription(charSequence3);
        }
    }

    private void cancelAddAnimator() {
        if (this.mAddAnimator != null) {
            this.mAddAnimator.cancel();
            this.mAddAnimator = null;
        }
    }

    private void startVisibilityAnimator(View view, int i) {
        cancelAddAnimator();
        this.mAddAnimator = i == 0 ? ert.a(view) : ert.b(view);
        this.mAddAnimator.start();
    }

    public void setBackVisibility(int i, boolean z) {
        if (!z) {
            this.mBackImageView.setVisibility(i);
        } else {
            startVisibilityAnimator(this.mBackImageView, i);
        }
    }

    public RouteInputLineView getStartInputLineView() {
        return this.mLinesView.getStartLineView();
    }

    public RouteInputLineView getEndInputLineView() {
        return this.mLinesView.getEndLineView();
    }
}
