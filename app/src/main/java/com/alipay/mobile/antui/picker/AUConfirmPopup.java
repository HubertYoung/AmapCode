package com.alipay.mobile.antui.picker;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUButton;
import com.alipay.mobile.antui.utils.DensityUtil;

public abstract class AUConfirmPopup<V extends View> extends AUCenterPopup<View> {
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

    public AUConfirmPopup(Activity activity) {
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
        rootLayout.setBackgroundDrawable(this.activity.getResources().getDrawable(R.drawable.date_picker_bg));
        rootLayout.setOrientation(1);
        rootLayout.setGravity(17);
        rootLayout.setPadding(0, 0, 0, 0);
        rootLayout.setClipToPadding(false);
        rootLayout.addView(makeTitle());
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
        View headerView = makeOperationButton();
        if (headerView != null) {
            rootLayout.addView(headerView);
        }
        return rootLayout;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public View makeTitle() {
        TextView title = new TextView(this.activity);
        if (!TextUtils.isEmpty(this.titleText)) {
            title.setText(this.titleText);
        }
        title.setTextColor(this.activity.getResources().getColor(R.color.AU_COLOR_TITLE));
        title.setTextSize(1, 20.0f);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.topMargin = DensityUtil.dip2px(this.activity, 14.0f);
        layoutParams.bottomMargin = DensityUtil.dip2px(this.activity, 12.0f);
        layoutParams.leftMargin = DensityUtil.dip2px(this.activity, 20.0f);
        title.setLayoutParams(layoutParams);
        return title;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public View makeOperationButton() {
        View view = LayoutInflater.from(this.activity).inflate(R.layout.confim_dialog_bottom_button, null);
        AUButton submitButton = (AUButton) view.findViewById(R.id.date_dialog_confirm);
        if (!TextUtils.isEmpty(this.submitText)) {
            submitButton.setText(this.submitText);
        }
        submitButton.setOnClickListener(new b(this));
        AUButton cancelButton = (AUButton) view.findViewById(R.id.date_dialog_cancle);
        if (!TextUtils.isEmpty(this.cancelText)) {
            cancelButton.setText(this.cancelText);
        }
        cancelButton.setOnClickListener(new c(this));
        return view;
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
