package com.alipay.mobile.antui.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUButton;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.utils.DensityUtil;

public class AUPopBar extends FrameLayout {
    private AURelativeLayout cancelButton;
    private Context context;
    private AUImageView leftImageView;
    private AURelativeLayout mBackground;
    private FrameLayout mContentView;
    private AUButton rightBotton;
    private AUTextView tipsDescTextView;
    private View tipsTextContainer;
    private AUTextView tipsTextView;

    public AUPopBar(Context context2) {
        super(context2);
        if (context2 instanceof Activity) {
            this.mContentView = (FrameLayout) ((Activity) context2).findViewById(16908290);
            LayoutParams lp = new LayoutParams(-1, -2);
            lp.gravity = 80;
            lp.bottomMargin = DensityUtil.dip2px(getContext(), 16.0f);
            lp.leftMargin = DensityUtil.dip2px(getContext(), 6.0f);
            lp.rightMargin = DensityUtil.dip2px(getContext(), 6.0f);
            setLayoutParams(lp);
            initView(context2);
            return;
        }
        throw new IllegalArgumentException("AUPopBar 构造方法参数需要 Activity 实例");
    }

    private void initView(Context context2) {
        this.context = context2;
        View rootView = LayoutInflater.from(context2).inflate(R.layout.au_pop_bar_view, this, true);
        this.mBackground = (AURelativeLayout) rootView.findViewById(R.id.pop_bar_background);
        this.leftImageView = (AUImageView) rootView.findViewById(R.id.left_icon);
        this.tipsTextView = (AUTextView) rootView.findViewById(R.id.tips_tv);
        this.tipsDescTextView = (AUTextView) rootView.findViewById(R.id.tip_desc);
        this.tipsTextContainer = rootView.findViewById(R.id.tips_text_container);
        this.cancelButton = (AURelativeLayout) rootView.findViewById(R.id.cancel_ly);
        this.rightBotton = (AUButton) rootView.findViewById(R.id.right_button);
        this.cancelButton.setOnClickListener(new a(this));
        this.rightBotton.setOnClickListener(new b(this));
        setTipsTextViewLeftPadding();
    }

    public void show() {
        this.mContentView.addView(this);
    }

    public AUImageView getLeftImageView() {
        if (this.leftImageView.getVisibility() == 8) {
            this.leftImageView.setVisibility(0);
            this.leftImageView.setBackgroundColor(-1);
        }
        setTipsTextViewLeftPadding();
        return this.leftImageView;
    }

    public void hideLeftImageView(boolean isHide) {
        if (isHide) {
            this.leftImageView.setVisibility(8);
        } else {
            this.leftImageView.setVisibility(0);
        }
        setTipsTextViewLeftPadding();
    }

    private void setTipsTextViewLeftPadding() {
        if (this.leftImageView.getVisibility() == 8) {
            this.tipsTextContainer.setPadding(0, 0, 0, 0);
        } else {
            this.tipsTextContainer.setPadding(DensityUtil.dip2px(getContext(), 9.0f), 0, 0, 0);
        }
    }

    public void setTipsText(CharSequence tipsString) {
        if (!TextUtils.isEmpty(tipsString)) {
            this.tipsTextView.setVisibility(0);
            this.tipsTextView.setText(tipsString);
            return;
        }
        this.tipsTextView.setVisibility(8);
    }

    public void setTipsDescText(CharSequence tipsDescText) {
        if (TextUtils.isEmpty(tipsDescText)) {
            this.tipsDescTextView.setVisibility(8);
            return;
        }
        this.tipsDescTextView.setVisibility(0);
        this.tipsDescTextView.setText(tipsDescText);
    }

    public void setRightBottonText(String rightBottonText) {
        if (!TextUtils.isEmpty(rightBottonText)) {
            this.rightBotton.setVisibility(0);
            this.rightBotton.setText(rightBottonText);
            return;
        }
        this.rightBotton.setVisibility(8);
    }

    public AUButton getRightBotton() {
        return this.rightBotton;
    }

    public AURelativeLayout getCancleButton() {
        return this.cancelButton;
    }

    public AUTextView getTipsTextView() {
        return this.tipsTextView;
    }

    public AUTextView getTipsDescTextView() {
        return this.tipsDescTextView;
    }

    public void hideRightButton(boolean isHide) {
        if (isHide) {
            this.rightBotton.setVisibility(8);
        } else {
            this.rightBotton.setVisibility(0);
        }
    }

    public void disappearAction() {
        startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.au_pop_bar_zoom_out));
        this.mContentView.removeView(this);
    }

    public void setPopBarColor(int color) {
        if (this.mBackground.getBackground() instanceof GradientDrawable) {
            ((GradientDrawable) this.mBackground.getBackground()).setColor(color);
        }
    }
}
