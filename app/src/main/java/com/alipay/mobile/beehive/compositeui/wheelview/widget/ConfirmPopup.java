package com.alipay.mobile.beehive.compositeui.wheelview.widget;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.antui.utils.DensityUtil;

public abstract class ConfirmPopup<V extends View> extends BottomPopup<View> {
    protected CharSequence cancelText = "";
    protected int cancelTextColor = -16777216;
    protected boolean cancelVisible = true;
    protected CharSequence submitText = "";
    protected int submitTextColor = -16777216;
    protected CharSequence titleText = "";
    protected int titleTextColor = -16777216;
    protected int topBackgroundColor = -1;
    protected int topLineColor = -2236963;
    protected boolean topLineVisible = true;

    /* access modifiers changed from: protected */
    @NonNull
    public abstract V makeCenterView();

    public ConfirmPopup(Activity activity) {
        super(activity);
        this.cancelText = activity.getString(17039360);
        this.submitText = activity.getString(17039370);
    }

    public void setTopLineColor(@ColorInt int topLineColor2) {
        this.topLineColor = topLineColor2;
    }

    public void setTopBackgroundColor(@ColorInt int topBackgroundColor2) {
        this.topBackgroundColor = topBackgroundColor2;
    }

    public void setTopLineVisible(boolean topLineVisible2) {
        this.topLineVisible = topLineVisible2;
    }

    public void setCancelVisible(boolean cancelVisible2) {
        this.cancelVisible = cancelVisible2;
    }

    public void setCancelText(CharSequence cancelText2) {
        this.cancelText = cancelText2;
    }

    public void setCancelText(@StringRes int textRes) {
        this.cancelText = this.activity.getString(textRes);
    }

    public void setSubmitText(CharSequence submitText2) {
        this.submitText = submitText2;
    }

    public void setSubmitText(@StringRes int textRes) {
        this.submitText = this.activity.getString(textRes);
    }

    public void setTitleText(CharSequence titleText2) {
        this.titleText = titleText2;
    }

    public void setTitleText(@StringRes int textRes) {
        this.titleText = this.activity.getString(textRes);
    }

    public void setCancelTextColor(@ColorInt int cancelTextColor2) {
        this.cancelTextColor = cancelTextColor2;
    }

    public void setSubmitTextColor(@ColorInt int submitTextColor2) {
        this.submitTextColor = submitTextColor2;
    }

    public void setTitleTextColor(@ColorInt int titleTextColor2) {
        this.titleTextColor = titleTextColor2;
    }

    /* access modifiers changed from: protected */
    public final View makeContentView() {
        LinearLayout rootLayout = new LinearLayout(this.activity);
        rootLayout.setLayoutParams(new LayoutParams(-1, -1));
        rootLayout.setBackgroundColor(-1);
        rootLayout.setOrientation(1);
        rootLayout.setGravity(17);
        rootLayout.setPadding(0, 0, 0, 0);
        rootLayout.setClipToPadding(false);
        View headerView = makeHeaderView();
        if (headerView != null) {
            rootLayout.addView(headerView);
        }
        if (this.topLineVisible) {
            View lineView = new View(this.activity);
            lineView.setLayoutParams(new LayoutParams(-1, 1));
            lineView.setBackgroundColor(this.topLineColor);
            rootLayout.addView(lineView);
        }
        rootLayout.addView(makeCenterView(), new LayoutParams(-1, 0, 1.0f));
        View footerView = makeFooterView();
        if (footerView != null) {
            rootLayout.addView(footerView);
        }
        return rootLayout;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public View makeHeaderView() {
        RelativeLayout topButtonLayout = new RelativeLayout(this.activity);
        topButtonLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, DensityUtil.dip2px(this.activity, 40.0f)));
        topButtonLayout.setBackgroundColor(this.topBackgroundColor);
        topButtonLayout.setGravity(16);
        Button cancelButton = new Button(this.activity);
        cancelButton.setVisibility(this.cancelVisible ? 0 : 8);
        RelativeLayout.LayoutParams cancelButtonLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
        cancelButtonLayoutParams.addRule(9, -1);
        cancelButtonLayoutParams.addRule(15, -1);
        cancelButton.setLayoutParams(cancelButtonLayoutParams);
        cancelButton.setBackgroundColor(0);
        cancelButton.setGravity(17);
        if (!TextUtils.isEmpty(this.cancelText)) {
            cancelButton.setText(this.cancelText);
        }
        cancelButton.setTextColor(this.cancelTextColor);
        cancelButton.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                ConfirmPopup.this.dismiss();
                ConfirmPopup.this.onCancel();
            }
        });
        topButtonLayout.addView(cancelButton);
        TextView titleView = new TextView(this.activity);
        RelativeLayout.LayoutParams titleLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
        int margin = DensityUtil.dip2px(this.activity, 20.0f);
        titleLayoutParams.leftMargin = margin;
        titleLayoutParams.rightMargin = margin;
        titleLayoutParams.addRule(14, -1);
        titleLayoutParams.addRule(15, -1);
        titleView.setLayoutParams(titleLayoutParams);
        titleView.setGravity(17);
        if (!TextUtils.isEmpty(this.titleText)) {
            titleView.setText(this.titleText);
        }
        titleView.setTextColor(this.titleTextColor);
        topButtonLayout.addView(titleView);
        Button submitButton = new Button(this.activity);
        RelativeLayout.LayoutParams submitButtonLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
        submitButtonLayoutParams.addRule(11, -1);
        submitButtonLayoutParams.addRule(15, -1);
        submitButton.setLayoutParams(submitButtonLayoutParams);
        submitButton.setBackgroundColor(0);
        submitButton.setGravity(17);
        if (!TextUtils.isEmpty(this.submitText)) {
            submitButton.setText(this.submitText);
        }
        submitButton.setTextColor(this.submitTextColor);
        submitButton.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                ConfirmPopup.this.dismiss();
                ConfirmPopup.this.onSubmit();
            }
        });
        topButtonLayout.addView(submitButton);
        return topButtonLayout;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public View makeFooterView() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onSubmit() {
    }

    /* access modifiers changed from: protected */
    public void onCancel() {
    }
}
