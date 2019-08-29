package com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alipay.sdk.widget.a;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.util.ViewUtils;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.Mode;

@SuppressLint({"ViewConstructor"})
public class FlipLoadingLayout extends LoadingLayout {
    private static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
    private static final int FLIP_ANIMATION_DURATION = 150;
    private boolean mHidden = false;
    private final ImageView mImageView;
    private final ViewGroup mInnerLayout;
    private final ProgressBar mProgressBar;
    private final Animation mResetRotateAnimation;
    private final Animation mRotateAnimation;
    private final TextView mSubTextView;
    private final TextView mTextView;

    /* renamed from: com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.FlipLoadingLayout$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$autonavi$minimap$ajx3$widget$view$list$pulltorefresh$PullToRefreshBase$Mode = new int[Mode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase$Mode[] r0 = com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.Mode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$autonavi$minimap$ajx3$widget$view$list$pulltorefresh$PullToRefreshBase$Mode = r0
                int[] r0 = $SwitchMap$com$autonavi$minimap$ajx3$widget$view$list$pulltorefresh$PullToRefreshBase$Mode     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase$Mode r1 = com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.Mode.PULL_FROM_END     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$autonavi$minimap$ajx3$widget$view$list$pulltorefresh$PullToRefreshBase$Mode     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase$Mode r1 = com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.Mode.PULL_FROM_START     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.FlipLoadingLayout.AnonymousClass1.<clinit>():void");
        }
    }

    public FlipLoadingLayout(Context context, Mode mode) {
        super(context, mode);
        LayoutInflater.from(context).inflate(R.layout.ajx3_pull_to_refresh_header_vertical, this);
        this.mInnerLayout = (ViewGroup) findViewById(R.id.fl_inner);
        this.mTextView = (TextView) this.mInnerLayout.findViewById(R.id.pull_to_refresh_text);
        this.mProgressBar = (ProgressBar) this.mInnerLayout.findViewById(R.id.pull_to_refresh_progress);
        this.mSubTextView = (TextView) this.mInnerLayout.findViewById(R.id.pull_to_refresh_sub_text);
        this.mImageView = (ImageView) this.mInnerLayout.findViewById(R.id.pull_to_refresh_image);
        LayoutParams layoutParams = (LayoutParams) this.mInnerLayout.getLayoutParams();
        if (AnonymousClass1.$SwitchMap$com$autonavi$minimap$ajx3$widget$view$list$pulltorefresh$PullToRefreshBase$Mode[mode.ordinal()] != 1) {
            layoutParams.gravity = 80;
            setLabel("下拉刷新", "松开即可刷新", a.a);
        } else {
            layoutParams.gravity = 48;
            setLabel("上拉加载更多", "放开以加载", a.a);
        }
        onLoadingDrawableSet(this.mImageView.getDrawable());
        float f = (float) (mode == Mode.PULL_FROM_START ? -180 : 180);
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, f, 1, 0.5f, 1, 0.5f);
        this.mRotateAnimation = rotateAnimation;
        this.mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        this.mRotateAnimation.setDuration(150);
        this.mRotateAnimation.setFillAfter(true);
        RotateAnimation rotateAnimation2 = new RotateAnimation(f, 0.0f, 1, 0.5f, 1, 0.5f);
        this.mResetRotateAnimation = rotateAnimation2;
        this.mResetRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        this.mResetRotateAnimation.setDuration(150);
        this.mResetRotateAnimation.setFillAfter(true);
        reset();
    }

    private void setLabel(String str, String str2, String str3) {
        setPullText(str);
        setReleaseText(str2);
        setRefreshingText(str3);
    }

    public boolean isHidden() {
        return this.mHidden;
    }

    public void setHidden(boolean z) {
        this.mHidden = z;
    }

    public void setTextColor(int i) {
        if (this.mTextView != null) {
            this.mTextView.setTextColor(i);
        }
    }

    public void setArrowImage(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mImageView.setImageResource(R.drawable.ajx3_pull_to_refresh_arrow);
        }
    }

    public void setBgImage(String str) {
        if (TextUtils.isEmpty(str)) {
            ViewUtils.setBackground(this.mInnerLayout, null);
        }
    }

    public void setBgColor(int i) {
        setBackgroundColor(i);
    }

    private void onLoadingDrawableSet(Drawable drawable) {
        if (drawable != null) {
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            ViewGroup.LayoutParams layoutParams = this.mImageView.getLayoutParams();
            int max = Math.max(intrinsicHeight, intrinsicWidth);
            layoutParams.height = max;
            layoutParams.width = max;
            this.mImageView.requestLayout();
            this.mImageView.setScaleType(ScaleType.MATRIX);
            Matrix matrix = new Matrix();
            matrix.postTranslate(((float) (layoutParams.width - intrinsicWidth)) / 2.0f, ((float) (layoutParams.height - intrinsicHeight)) / 2.0f);
            matrix.postRotate(getDrawableRotationAngle(), ((float) layoutParams.width) / 2.0f, ((float) layoutParams.height) / 2.0f);
            this.mImageView.setImageMatrix(matrix);
        }
    }

    private float getDrawableRotationAngle() {
        return AnonymousClass1.$SwitchMap$com$autonavi$minimap$ajx3$widget$view$list$pulltorefresh$PullToRefreshBase$Mode[this.mMode.ordinal()] != 1 ? 0.0f : 180.0f;
    }

    /* access modifiers changed from: protected */
    public final int getContentSize() {
        return this.mInnerLayout.getHeight();
    }

    /* access modifiers changed from: protected */
    public final void pullToRefresh() {
        if (this.mTextView != null) {
            this.mTextView.setText(this.mPullLabel);
        }
        if (this.mRotateAnimation == this.mImageView.getAnimation()) {
            this.mImageView.startAnimation(this.mResetRotateAnimation);
        }
    }

    /* access modifiers changed from: protected */
    public final void releaseToRefresh() {
        if (this.mTextView != null) {
            this.mTextView.setText(this.mReleaseLabel);
        }
        this.mImageView.startAnimation(this.mRotateAnimation);
    }

    public final void refreshing() {
        if (this.mTextView != null) {
            this.mTextView.setText(this.mRefreshingLabel);
        }
        this.mImageView.clearAnimation();
        this.mImageView.setVisibility(4);
        this.mProgressBar.setVisibility(0);
        if (this.mSubTextView != null) {
            this.mSubTextView.setVisibility(8);
        }
    }

    public final void reset() {
        if (this.mTextView != null) {
            this.mTextView.setText(this.mPullLabel);
            this.mTextView.setVisibility(0);
        }
        this.mImageView.clearAnimation();
        this.mProgressBar.setVisibility(8);
        this.mImageView.setVisibility(0);
        if (this.mSubTextView != null) {
            if (TextUtils.isEmpty(this.mSubTextView.getText())) {
                this.mSubTextView.setVisibility(8);
                return;
            }
            this.mSubTextView.setVisibility(0);
        }
    }
}
