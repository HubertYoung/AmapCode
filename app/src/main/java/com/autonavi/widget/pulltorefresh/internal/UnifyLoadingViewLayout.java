package com.autonavi.widget.pulltorefresh.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.amap.bundle.commonui.loading.LoadingView;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation;

public class UnifyLoadingViewLayout extends LoadingLayout {
    private LoadingView mLoadingView;
    private RelativeLayout mParentLayout;

    /* renamed from: com.autonavi.widget.pulltorefresh.internal.UnifyLoadingViewLayout$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[Orientation.values().length];
        static final /* synthetic */ int[] b = new int[Mode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(9:0|1|2|3|4|5|7|8|10) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Mode[] r0 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                b = r0
                r0 = 1
                int[] r1 = b     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Mode r2 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode.PULL_FROM_END     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r1 = b     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Mode r2 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode.PULL_FROM_START     // Catch:{ NoSuchFieldError -> 0x001f }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r3 = 2
                r1[r2] = r3     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Orientation[] r1 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation.values()
                int r1 = r1.length
                int[] r1 = new int[r1]
                a = r1
                int[] r1 = a     // Catch:{ NoSuchFieldError -> 0x0032 }
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Orientation r2 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation.VERTICAL     // Catch:{ NoSuchFieldError -> 0x0032 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0032 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0032 }
            L_0x0032:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.widget.pulltorefresh.internal.UnifyLoadingViewLayout.AnonymousClass1.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public int getDefaultDrawableResId() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onLoadingDrawableSet(Drawable drawable) {
    }

    /* access modifiers changed from: protected */
    public void onPullImpl(float f) {
    }

    /* access modifiers changed from: protected */
    public void resetImpl() {
    }

    public void setLastUpdatedLabel(CharSequence charSequence) {
    }

    public void setPullLabel(CharSequence charSequence) {
    }

    public void setRefreshingLabel(CharSequence charSequence) {
    }

    public void setReleaseLabel(CharSequence charSequence) {
    }

    public void setTextTypeface(Typeface typeface) {
    }

    public UnifyLoadingViewLayout(Context context, Mode mode, Orientation orientation, TypedArray typedArray) {
        super(context, mode, orientation);
        int[] iArr = AnonymousClass1.a;
        orientation.ordinal();
        initLayout(context);
        LayoutParams layoutParams = (LayoutParams) this.mParentLayout.getLayoutParams();
        if (AnonymousClass1.b[mode.ordinal()] != 1) {
            layoutParams.gravity = orientation == Orientation.VERTICAL ? 80 : 5;
            this.mPullLabel = context.getString(R.string.pulltorefresh_pull_label);
            this.mRefreshingLabel = context.getString(R.string.pulltorefresh_refreshing_label);
            this.mReleaseLabel = context.getString(R.string.pulltorefresh_release_label);
            return;
        }
        layoutParams.gravity = orientation == Orientation.VERTICAL ? 48 : 3;
        this.mPullLabel = context.getString(R.string.pulltorefresh_from_bottom_pull_label);
        this.mRefreshingLabel = context.getString(R.string.pulltorefresh_from_bottom_refreshing_label);
        this.mReleaseLabel = context.getString(R.string.pulltorefresh_from_bottom_release_label);
    }

    private void initLayout(Context context) {
        this.mParentLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        this.mParentLayout.setPadding(getResources().getDimensionPixelOffset(R.dimen.pulltorefresh_header_footer_left_right_padding), getResources().getDimensionPixelOffset(R.dimen.pulltorefresh_header_footer_top_bottom_padding), getResources().getDimensionPixelOffset(R.dimen.pulltorefresh_header_footer_left_right_padding), getResources().getDimensionPixelOffset(R.dimen.pulltorefresh_header_footer_top_bottom_padding));
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        this.mLoadingView = new LoadingView(context, 1);
        layoutParams2.addRule(13);
        layoutParams2.height = getResources().getDimensionPixelOffset(R.dimen.pulltorefresh_loading_view_height);
        this.mParentLayout.addView(this.mLoadingView, layoutParams2);
        addView(this.mParentLayout, layoutParams);
    }

    public final void reset() {
        this.pulltorefreshflag = false;
        this.mLoadingView.startAnimationByState(0);
    }

    public void hideAllViews() {
        if (this.mLoadingView.getVisibility() == 0) {
            this.mLoadingView.setVisibility(4);
        }
    }

    public void showInvisibleViews() {
        if (4 == this.mLoadingView.getVisibility()) {
            this.mLoadingView.setVisibility(0);
        }
    }

    public int getContentSize() {
        return this.mParentLayout.getHeight();
    }

    /* access modifiers changed from: protected */
    public void pullToRefreshImpl() {
        this.mLoadingView.setLoadingText(this.mPullLabel.toString());
        this.mLoadingView.startAnimationByState(0);
    }

    /* access modifiers changed from: protected */
    public void releaseToRefreshImpl() {
        this.mLoadingView.setLoadingText(this.mReleaseLabel.toString());
        this.mLoadingView.startAnimationByState(0);
    }

    /* access modifiers changed from: protected */
    public void refreshingImpl() {
        this.mLoadingView.setLoadingText(this.mRefreshingLabel.toString());
        this.mLoadingView.startAnimationByState(1);
    }
}
