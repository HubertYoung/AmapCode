package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.lottie.AULottieLayout;
import com.alipay.mobile.antui.utils.AULottieFileUtils;

public class AUPullLottieHeaderView extends AUPullLoadingView {
    private static final float REFRESH_END_PROGRESS = 0.8f;
    private static final float REFRESH_START_PROGRESS = 0.17f;
    public static final String STYLE_BLUE = "_BLUE";
    public static final String STYLE_WHITE = "_WHITE";
    private AULottieLayout mAntAnimationView;
    private String mCurrentStyle = "_BLUE";
    private LoadingTextSource mLoadingTextSource;
    private View mRootView;
    private String mTitle;
    private AUEmptyGoneTextView mTitleView;
    private Rect mVisibilityRect = new Rect();

    public interface LoadingTextSource {
        String getTitle();
    }

    public AUPullLottieHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AUPullLottieHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AUPullLottieHeaderView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void initAttrs(Context context, AttributeSet attrs, int defStyle) {
    }

    public void init() {
        setClipChildren(false);
        this.mRootView = LayoutInflater.from(getContext()).inflate(R.layout.au_pullrefresh_lottie_header_view, this, true);
        this.mTitleView = (AUEmptyGoneTextView) findViewById(R.id.title);
        this.mAntAnimationView = (AULottieLayout) findViewById(R.id.animation);
        this.mTitleView.setVisibility(0);
        setAntColor(this.mCurrentStyle);
    }

    public void setAntColor(String style) {
        if (!TextUtils.equals(this.mCurrentStyle, style)) {
            this.mCurrentStyle = style;
            if (TextUtils.equals(style, "_BLUE")) {
                this.mAntAnimationView.setAnimationSource(AULottieFileUtils.getRefreshAnimation(getContext()));
                this.mTitleView.setTextColor(getResources().getColor(R.color.AU_COLOR_SUB_CONTENT));
            } else if (TextUtils.equals(style, "_WHITE")) {
                this.mAntAnimationView.setAnimationSource(AULottieFileUtils.getRefreshAnimation(getContext(), 40, -1, 22, Color.parseColor("#070707")));
                this.mTitleView.setTextColor(Color.parseColor("#9AFFFFFF"));
            }
            this.mAntAnimationView.loop(true);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAntAnimationView.playAnimation();
    }

    public void onOpen() {
        this.mAntAnimationView.setMinAndMaxProgress(REFRESH_START_PROGRESS, REFRESH_END_PROGRESS);
        this.mAntAnimationView.setProgress(REFRESH_START_PROGRESS);
        this.mAntAnimationView.loop(true);
        this.mAntAnimationView.playAnimation();
    }

    public void onPullto(double pos, byte pullRefreshState) {
        if (this.mRootView.getGlobalVisibleRect(this.mVisibilityRect) && pullRefreshState <= 1) {
            float positionPercent = ((float) this.mVisibilityRect.height()) / ((float) this.mRootView.getMeasuredHeight());
            if (positionPercent < 0.3f) {
                this.mAntAnimationView.setProgress(0.0f);
                this.mAntAnimationView.pauseAnimation();
            } else if (positionPercent < 1.0f) {
                this.mAntAnimationView.setProgress(REFRESH_START_PROGRESS * positionPercent);
                this.mAntAnimationView.pauseAnimation();
            } else {
                this.mAntAnimationView.setProgress(REFRESH_START_PROGRESS);
                this.mAntAnimationView.pauseAnimation();
            }
            if (this.mLoadingTextSource != null) {
                this.mTitle = this.mLoadingTextSource.getTitle();
            }
            if (!TextUtils.isEmpty(this.mTitle)) {
                this.mTitleView.setText(this.mTitle);
                this.mTitleView.setVisibility(0);
            }
        }
    }

    public void onOver() {
    }

    public void onLoad() {
        if (!TextUtils.isEmpty(this.mTitle)) {
            this.mTitleView.setVisibility(4);
        }
    }

    public void onFinish() {
        this.mAntAnimationView.setMinAndMaxProgress(0.0f, 1.0f);
        this.mAntAnimationView.loop(false);
        this.mAntAnimationView.cancelAnimation();
    }

    public int getOverViewHeight() {
        View v = this.mRootView;
        int height = v.getMeasuredHeight();
        if (height > 0) {
            return height;
        }
        v.measure(0, 0);
        return v.getMeasuredHeight();
    }

    public void setLoadingText(String loadingText) {
        if (!TextUtils.isEmpty(loadingText)) {
            this.mTitle = loadingText;
            this.mTitleView.setText(loadingText, BufferType.NORMAL);
        }
    }

    public long getRemainedLoadingDuration() {
        return (long) (((float) this.mAntAnimationView.getDuration()) * REFRESH_END_PROGRESS * (REFRESH_END_PROGRESS - this.mAntAnimationView.getProgress()));
    }

    public TextView getTitleView() {
        return this.mTitleView;
    }

    public void setLoadingTextSource(LoadingTextSource source) {
        this.mLoadingTextSource = source;
    }
}
