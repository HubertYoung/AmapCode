package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.load.AbsLoadMoreView;
import com.alipay.mobile.antui.lottie.AULottieLayout;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.utils.AULottieFileUtils;

public class AUDragLoadingView extends AbsLoadMoreView implements AntUI {
    private boolean isLoadingMore = false;
    private View leftDivide;
    private AUTextView mFinishedTextView;
    private AUTextView mLoadingTextView;
    private AULottieLayout mProgressBar;
    private View rightDivide;

    public AUDragLoadingView(Context context) {
        super(context);
        init(context, null);
    }

    public AUDragLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUDragLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        init(context, null, null);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AUDragLoadingView);
            initContent(context, null, array);
            initStyleByTheme(context);
            initAttrStyle(context, null, array);
            array.recycle();
            return;
        }
        initStyleByTheme(context);
    }

    public void init(Context context, AttributeSet attrs, TypedArray typedArray) {
        LayoutInflater.from(context).inflate(R.layout.au_drag_loading_view, this, true);
        this.mLoadingTextView = (AUTextView) findViewById(R.id.progress_right_text);
        this.mProgressBar = (AULottieLayout) findViewById(R.id.progressBar);
        this.mProgressBar.setAnimationSource(AULottieFileUtils.getLoadingAnimation(context));
        this.mProgressBar.loop(true);
        this.mFinishedTextView = (AUTextView) findViewById(R.id.drag_loading_finished_text);
        this.leftDivide = findViewById(R.id.left_divide);
        this.rightDivide = findViewById(R.id.right_divide);
        setLoadingText(getResources().getString(R.string.loading));
        setFinishedText(getResources().getString(R.string.no_more));
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray typedArray) {
        if (typedArray != null) {
            setLoadingText(typedArray.getString(0));
            setFinishedText(typedArray.getString(1));
        }
    }

    public void initStyleByTheme(Context context) {
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray typedArray) {
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    public void setLoadingText(CharSequence text) {
        if (text != null) {
            this.mLoadingTextView.setText(text);
        }
    }

    public void startLoading() {
        setVisibility(0);
        this.mProgressBar.playAnimation();
        this.mLoadingTextView.setVisibility(0);
        this.leftDivide.setVisibility(8);
        this.rightDivide.setVisibility(8);
        this.mFinishedTextView.setVisibility(8);
        this.isLoadingMore = true;
    }

    public void loadingFinished(boolean hasMore) {
        if (!hasMore) {
            onShowNoMoreText();
        } else {
            onLoadFinish();
        }
    }

    public String getLoadingText() {
        return (String) this.mLoadingTextView.getText();
    }

    public void setFinishedText(CharSequence text) {
        this.mFinishedTextView.setText(text);
    }

    public String getFinishedText() {
        return (String) this.mFinishedTextView.getText();
    }

    public Boolean isLoadingMore() {
        return Boolean.valueOf(this.isLoadingMore);
    }

    public void setProgressVisibility(int visibility) {
        this.mProgressBar.setVisibility(visibility);
    }

    @Deprecated
    public void onLoadMore() {
        startLoading();
    }

    @Deprecated
    public void onLoadFinish() {
        this.mProgressBar.cancelAnimation();
        setVisibility(8);
    }

    @Deprecated
    public void onShowNoMoreText() {
        setVisibility(0);
        this.mProgressBar.cancelAnimation();
        this.mProgressBar.setVisibility(8);
        this.mLoadingTextView.setVisibility(8);
        this.leftDivide.setVisibility(0);
        this.rightDivide.setVisibility(0);
        this.mFinishedTextView.setVisibility(0);
    }

    public AULottieLayout getProgressBar() {
        return this.mProgressBar;
    }
}
