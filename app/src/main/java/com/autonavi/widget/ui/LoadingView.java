package com.autonavi.widget.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheInfo;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.minimap.R;

public class LoadingView extends LinearLayout implements IViewLayer {
    public static final int COMPLETE_STATE = 2;
    public static final int LOADING_A = 0;
    public static final int LOADING_B = 1;
    public static final int LOADING_C = 2;
    public static final int LOADING_C2 = 6;
    public static final int LOADING_D = 3;
    public static final int LOADING_E = 4;
    public static final int LOADING_F = 5;
    public static final int LOADING_STATE = 1;
    public static final int PULLING_STATE = 0;
    private static final float ROTATE_ANGEL = -180.0f;
    private static final int ROTATE_DURATION = 500;
    private static final int[] VISIBILITY_FLAGS = {0, 4, 8};
    private ImageView mAppIcon;
    private boolean mCancelable;
    private ImageView mClose;
    private Context mContext;
    private int mCurrentStyle;
    private ObjectAnimator mImageRotateAnimation;
    private ObjectAnimator mImageRotateResetAnimation;
    private TextView mLoadingDetail;
    private TextView mLoadingText;
    private ProgressView mProgress;

    public View getView() {
        return this;
    }

    public void showBackground(boolean z) {
    }

    public LoadingView(Context context, int i) {
        super(context);
        this.mCancelable = false;
        init(context, null, i);
    }

    public LoadingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LoadingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCancelable = false;
        init(context, attributeSet, 0);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        int i2;
        CharSequence charSequence = "";
        this.mContext = context;
        this.mCurrentStyle = i;
        int i3 = 0;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LoadingView);
            this.mCurrentStyle = obtainStyledAttributes.getInt(R.styleable.LoadingView_loadingType, 0);
            charSequence = obtainStyledAttributes.getText(R.styleable.LoadingView_loadingText);
            int i4 = obtainStyledAttributes.getInt(R.styleable.LoadingView_closeIconVisibility, 0);
            i2 = i4 != 0 ? VISIBILITY_FLAGS[i4] : 0;
            int i5 = obtainStyledAttributes.getInt(R.styleable.LoadingView_appIconVisibility, 0);
            if (i5 != 0) {
                i3 = VISIBILITY_FLAGS[i5];
            }
            obtainStyledAttributes.recycle();
        } else {
            i2 = 0;
        }
        inflateView();
        initView(charSequence, i2, i3);
    }

    private void initView(CharSequence charSequence, int i, int i2) {
        this.mProgress = (ProgressView) findViewById(R.id.progress);
        this.mLoadingText = (TextView) findViewById(R.id.loading_text);
        this.mLoadingDetail = (TextView) findViewById(R.id.loading_sub_text);
        this.mClose = (ImageView) findViewById(R.id.close);
        this.mAppIcon = (ImageView) findViewById(R.id.app_icon);
        setTextView(this.mLoadingText, charSequence);
        setCloseIconVisibility(i);
        setAppIconVisibility(i2);
    }

    public void setCancelable(boolean z) {
        this.mCancelable = z;
    }

    public boolean isCancelable() {
        return this.mCancelable;
    }

    private void inflateView() {
        LayoutInflater from = LayoutInflater.from(getContext());
        switch (this.mCurrentStyle) {
            case 0:
                from.inflate(R.layout.view_loading_a, this, true);
                return;
            case 1:
                from.inflate(R.layout.view_loading_b, this, true);
                setUpRotateAnimation();
                return;
            case 2:
                from.inflate(R.layout.view_loading_c, this, true);
                return;
            case 3:
                from.inflate(R.layout.view_loading_d, this, true);
                setGravity(17);
                setBackgroundResource(R.color.c_5_c);
                return;
            case 4:
                from.inflate(R.layout.view_loading_e, this, true);
                return;
            case 5:
                from.inflate(R.layout.view_loading_f, this, true);
                break;
            case 6:
                from.inflate(R.layout.view_loading_c2, this, true);
                return;
        }
    }

    private void setUpRotateAnimation() {
        ImageView imageView = (ImageView) findViewById(R.id.arrow);
        this.mImageRotateAnimation = ObjectAnimator.ofFloat(imageView, APCacheInfo.EXTRA_ROTATION, new float[]{0.0f, ROTATE_ANGEL});
        this.mImageRotateAnimation.setDuration(500);
        this.mImageRotateResetAnimation = ObjectAnimator.ofFloat(imageView, APCacheInfo.EXTRA_ROTATION, new float[]{ROTATE_ANGEL, 0.0f});
        this.mImageRotateResetAnimation.setDuration(500);
    }

    public void startAnimationByState(int i) {
        switch (i) {
            case 0:
                this.mProgress.setCurrentSweepAngle(360.0f);
                startImageRotateAnimation();
                return;
            case 1:
                this.mProgress.startAnimation();
                return;
            case 2:
                startImageRotateResetAnimation();
                this.mProgress.stopAnimation();
                this.mProgress.setCurrentSweepAngle(360.0f);
                break;
        }
    }

    private void startImageRotateAnimation() {
        if (this.mImageRotateAnimation != null && !this.mImageRotateAnimation.isStarted()) {
            this.mImageRotateAnimation.start();
        }
    }

    private void startImageRotateResetAnimation() {
        if (this.mImageRotateResetAnimation != null && !this.mImageRotateAnimation.isStarted()) {
            this.mImageRotateResetAnimation.start();
        }
    }

    public void setExtraViewVisibility(int i) {
        View findViewById = findViewById(R.id.loading_extra);
        if (findViewById != null && findViewById.getVisibility() != i) {
            findViewById.setVisibility(i);
        }
    }

    public void setLoadingText(CharSequence charSequence) {
        if (this.mLoadingText == null) {
            throw new IllegalArgumentException("current type not support set loading text");
        } else if (!TextUtils.isEmpty(charSequence)) {
            this.mLoadingText.setText(charSequence);
            this.mLoadingText.setVisibility(0);
        } else {
            this.mLoadingText.setVisibility(8);
        }
    }

    public void setLoadingDetail(CharSequence charSequence) {
        if (this.mLoadingDetail == null) {
            throw new IllegalArgumentException("current type not support set loading detail");
        }
        this.mLoadingDetail.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        this.mLoadingDetail.setText(charSequence);
    }

    private void setTextView(TextView textView, CharSequence charSequence) {
        if (textView != null && !TextUtils.isEmpty(charSequence)) {
            textView.setText(charSequence);
        }
    }

    public void setOnCloseClickListener(OnClickListener onClickListener) {
        if (this.mClose != null) {
            this.mClose.setOnClickListener(onClickListener);
        }
    }

    public void setCloseIconVisibility(int i) {
        if (this.mClose != null) {
            this.mClose.setVisibility(i);
        }
    }

    public void setAppIconVisibility(int i) {
        if (this.mAppIcon != null) {
            this.mAppIcon.setVisibility(i);
        }
    }

    public boolean onBackPressed() {
        return this.mCancelable;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }
}
