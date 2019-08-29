package com.autonavi.widget.ui.route;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;

public class RouteInputView extends ViewGroup implements OnClickListener {
    private ImageView mBottomView;
    private TextView mCompleteTextView;
    private View mDividerView;
    private int mExactlyHeight;
    private eru mListener;
    private RelativeLayout mParentView;
    private RouteInputInternalView mView;

    public RouteInputView(Context context) {
        this(context, null);
    }

    public RouteInputView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteInputView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public void setParentView(RelativeLayout relativeLayout) {
        this.mParentView = relativeLayout;
    }

    public RouteInputLineView addPassView(boolean z) {
        return this.mView.addPassView(z);
    }

    public boolean removePassView(RouteInputLineView routeInputLineView, boolean z) {
        return this.mView.removePassView(routeInputLineView, z);
    }

    public void exchangeAnimator() {
        this.mView.exchangeAnimator();
    }

    public void setOnRouteInputClickListener(eru eru) {
        this.mListener = eru;
        this.mView.setOnRouteInputClickListener(eru);
    }

    public void setStartText(CharSequence charSequence) {
        this.mView.setStartText(charSequence);
    }

    public void setEndText(CharSequence charSequence) {
        this.mView.setEndText(charSequence);
    }

    public void setPassText(CharSequence... charSequenceArr) {
        this.mView.setPassText(charSequenceArr);
    }

    public void animateContentView() {
        this.mView.animateContentView();
    }

    public void showSummary(boolean z, boolean z2) {
        this.mView.showSummary(z, z2);
    }

    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
    }

    public boolean showOverLayer(boolean z) {
        if (isOverLayerShowing()) {
            return false;
        }
        this.mExactlyHeight = getMeasuredHeight();
        removeView(this.mView);
        this.mParentView.addView(this.mView);
        addBottomView();
        this.mView.setBackVisibility(8, z);
        startBottomAnimator(z);
        return true;
    }

    private void startBottomAnimator(boolean z) {
        if (z) {
            ert.c(this.mBottomView);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mCompleteTextView, "scaleY", new float[]{0.0f, 1.0f});
            this.mCompleteTextView.setPivotY(0.0f);
            ofFloat.setDuration(ert.b);
            ofFloat.start();
        }
    }

    private void addBottomView() {
        if (this.mDividerView.getLayoutParams() == null) {
            LayoutParams layoutParams = new LayoutParams(-1, getResources().getDimensionPixelSize(R.dimen.route_input_divider_height));
            layoutParams.addRule(3, R.id.route_input);
            this.mDividerView.setLayoutParams(layoutParams);
        }
        this.mParentView.addView(this.mDividerView);
        if (this.mBottomView.getLayoutParams() == null) {
            LayoutParams layoutParams2 = new LayoutParams(-1, -1);
            layoutParams2.addRule(3, R.id.route_divider);
            this.mBottomView.setLayoutParams(layoutParams2);
        }
        this.mParentView.addView(this.mBottomView);
        if (this.mCompleteTextView.getLayoutParams() == null) {
            LayoutParams layoutParams3 = new LayoutParams(-1, getResources().getDimensionPixelOffset(R.dimen.route_input_left));
            layoutParams3.addRule(3, R.id.route_divider);
            this.mCompleteTextView.setLayoutParams(layoutParams3);
        }
        this.mParentView.addView(this.mCompleteTextView);
    }

    private void removeBottomView() {
        this.mParentView.removeView(this.mBottomView);
        this.mParentView.removeView(this.mCompleteTextView);
        this.mParentView.removeView(this.mDividerView);
    }

    public RouteInputSummaryView getSummaryView() {
        return this.mView.getSummaryView();
    }

    public void setDescription(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5) {
        this.mView.setDescription(charSequence, charSequence2, charSequence3, charSequence4, charSequence5);
        if (!TextUtils.isEmpty(charSequence4)) {
            this.mCompleteTextView.setContentDescription(charSequence4);
        }
        if (!TextUtils.isEmpty(charSequence5)) {
            this.mBottomView.setContentDescription(charSequence5);
        }
    }

    public boolean dismissOverLayer() {
        if (this.mView.getParent() == this) {
            return false;
        }
        if (this.mView.getParent() != null) {
            removeBottomView();
            ((ViewGroup) this.mView.getParent()).removeView(this.mView);
            this.mExactlyHeight = 0;
            addView(this.mView);
            this.mView.setBackVisibility(0, false);
        }
        return true;
    }

    public void onClick(View view) {
        if (this.mListener != null) {
        }
    }

    public boolean isOverLayerShowing() {
        return this.mView.getParent() != this;
    }

    public void setAddVisibility(int i, boolean z) {
        this.mView.setAddVisibility(i, z);
    }

    public void setExchangeEnable(boolean z) {
        this.mView.setExchangeEnable(z);
    }

    public RouteInputLineView getStartInputLineView() {
        return this.mView.getStartInputLineView();
    }

    public RouteInputLineView getEndInputLineView() {
        return this.mView.getEndInputLineView();
    }

    private void init(Context context) {
        this.mView = new RouteInputInternalView(context);
        this.mView.setId(R.id.route_input);
        addViewInLayout(this.mView, 0, generateDefaultLayoutParams(), true);
        this.mDividerView = new View(context);
        this.mDividerView.setId(R.id.route_divider);
        this.mDividerView.setBackgroundColor(getResources().getColor(R.color.c_12));
        this.mDividerView.setOnClickListener(this);
        this.mCompleteTextView = new TextView(context);
        this.mCompleteTextView.setGravity(17);
        this.mCompleteTextView.setText(R.string.route_input_complete);
        this.mCompleteTextView.setTextColor(getResources().getColor(R.color.f_c_1));
        this.mCompleteTextView.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.f_s_16));
        this.mCompleteTextView.setBackgroundResource(R.drawable.route_bg_input_complete_selector);
        this.mCompleteTextView.setOnClickListener(this);
        this.mBottomView = new ImageView(context);
        this.mBottomView.setOnClickListener(this);
        this.mBottomView.setBackgroundColor(Color.parseColor("#4D000000"));
        setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.c_12)));
        setOnClickListener(this);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        if (this.mView != null) {
            this.mView.setBackgroundDrawable(drawable);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (this.mExactlyHeight > 0 || this.mView.getParent() != this) {
            setMeasuredDimension(resolveSize(size, i), this.mExactlyHeight);
            return;
        }
        this.mView.measure(MeasureSpec.makeMeasureSpec(size, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE));
        setMeasuredDimension(resolveSize(size, i), resolveSize(this.mView.getMeasuredHeight(), i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mExactlyHeight <= 0 && this.mView.getParent() == this) {
            this.mView.layout(0, 0, this.mView.getMeasuredWidth(), this.mView.getMeasuredHeight());
        }
    }
}
