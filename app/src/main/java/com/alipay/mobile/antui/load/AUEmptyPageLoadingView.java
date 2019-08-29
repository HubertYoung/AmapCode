package com.alipay.mobile.antui.load;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.lottie.AULottieLayout;
import com.alipay.mobile.antui.utils.AULottieFileUtils;

public class AUEmptyPageLoadingView extends AULinearLayout {
    private AULottieLayout lottieLayout;
    private AUTextView tipView;

    public AUEmptyPageLoadingView(Context context) {
        this(context, null);
    }

    public AUEmptyPageLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUEmptyPageLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(1);
        setGravity(17);
        LayoutInflater.from(context).inflate(R.layout.au_empty_page_loading, this, true);
        this.lottieLayout = (AULottieLayout) findViewById(R.id.loading_animation);
        this.tipView = (AUTextView) findViewById(R.id.empty_page_tips);
        this.lottieLayout.setAnimationSource(AULottieFileUtils.getRefreshAnimation(context));
        this.lottieLayout.setMinAndMaxProgress(0.0f, 0.68f);
    }

    public AULottieLayout getLottieLayout() {
        return this.lottieLayout;
    }

    public AUTextView getTipView() {
        return this.tipView;
    }

    public void setTips(String tips) {
        this.tipView.setText(tips);
    }
}
