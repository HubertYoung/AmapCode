package com.autonavi.widget.pulltorefresh.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation;

@SuppressLint({"ViewConstructor"})
public abstract class LoadingLayout extends FrameLayout implements ere {
    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
    static final String LOG_TAG = "PullToRefreshAttrs-LoadingLayout";
    public boolean headImageFlag = true;
    protected final ImageView mHeaderImage;
    protected final ProgressBar mHeaderProgress;
    private final TextView mHeaderText;
    private RelativeLayout mInnerLayout;
    protected final Mode mMode;
    protected CharSequence mPullLabel;
    protected CharSequence mRefreshingLabel;
    private a mRefreshingListener;
    protected CharSequence mReleaseLabel;
    protected final Orientation mScrollDirection;
    private final TextView mSubHeaderText;
    private boolean mUseIntrinsicAnimation;
    public boolean pulltorefreshflag = false;

    /* renamed from: com.autonavi.widget.pulltorefresh.internal.LoadingLayout$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[Orientation.values().length];
        static final /* synthetic */ int[] b = new int[Mode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(11:0|1|2|3|(2:5|6)|7|9|10|11|12|14) */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0032 */
        static {
            /*
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Mode[] r0 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                b = r0
                r0 = 1
                int[] r1 = b     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Mode r2 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode.PULL_FROM_START     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = b     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Mode r3 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode.PULL_FROM_END     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Orientation[] r2 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                a = r2
                int[] r2 = a     // Catch:{ NoSuchFieldError -> 0x0032 }
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Orientation r3 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation.HORIZONTAL     // Catch:{ NoSuchFieldError -> 0x0032 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0032 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0032 }
            L_0x0032:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x003c }
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Orientation r2 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation.VERTICAL     // Catch:{ NoSuchFieldError -> 0x003c }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x003c }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x003c }
            L_0x003c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.widget.pulltorefresh.internal.LoadingLayout.AnonymousClass1.<clinit>():void");
        }
    }

    public interface a {
    }

    /* access modifiers changed from: protected */
    public abstract int getDefaultDrawableResId();

    /* access modifiers changed from: protected */
    public abstract void onLoadingDrawableSet(Drawable drawable);

    /* access modifiers changed from: protected */
    public abstract void onPullImpl(float f);

    /* access modifiers changed from: protected */
    public abstract void pullToRefreshImpl();

    /* access modifiers changed from: protected */
    public abstract void refreshingImpl();

    /* access modifiers changed from: protected */
    public abstract void releaseToRefreshImpl();

    /* access modifiers changed from: protected */
    public abstract void resetImpl();

    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01b0, code lost:
        r7 = r8.getDrawable(com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrDrawableTop);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01f4, code lost:
        r7 = r8.getDrawable(com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrDrawableBottom);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LoadingLayout(android.content.Context r5, com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode r6, com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation r7, android.content.res.TypedArray r8) {
        /*
            r4 = this;
            r4.<init>(r5)
            r0 = 1
            r4.headImageFlag = r0
            r1 = 0
            r4.pulltorefreshflag = r1
            r4.mMode = r6
            r4.mScrollDirection = r7
            int[] r2 = com.autonavi.widget.pulltorefresh.internal.LoadingLayout.AnonymousClass1.a
            int r3 = r7.ordinal()
            r2 = r2[r3]
            if (r2 == r0) goto L_0x0021
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r5)
            int r3 = com.autonavi.minimap.R.layout.pulltorefresh_header_vertical
            r2.inflate(r3, r4)
            goto L_0x002a
        L_0x0021:
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r5)
            int r3 = com.autonavi.minimap.R.layout.pulltorefresh_header_horizontal
            r2.inflate(r3, r4)
        L_0x002a:
            int r2 = com.autonavi.minimap.R.id.frameLayout
            android.view.View r2 = r4.findViewById(r2)
            android.widget.RelativeLayout r2 = (android.widget.RelativeLayout) r2
            r4.mInnerLayout = r2
            android.widget.RelativeLayout r2 = r4.mInnerLayout
            int r3 = com.autonavi.minimap.R.id.pulltorefresh_text
            android.view.View r2 = r2.findViewById(r3)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r4.mHeaderText = r2
            int[] r2 = com.autonavi.widget.pulltorefresh.internal.LoadingLayout.AnonymousClass1.b
            int r3 = r6.ordinal()
            r2 = r2[r3]
            if (r2 == r0) goto L_0x0057
            android.widget.RelativeLayout r0 = r4.mInnerLayout
            int r1 = com.autonavi.minimap.R.id.pulltorefresh_progressBar
            android.view.View r0 = r0.findViewById(r1)
            android.widget.ProgressBar r0 = (android.widget.ProgressBar) r0
            r4.mHeaderProgress = r0
            goto L_0x0078
        L_0x0057:
            int r0 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrSpecialHeaderProgressBarStart
            boolean r0 = r8.getBoolean(r0, r1)
            if (r0 == 0) goto L_0x006c
            android.widget.RelativeLayout r0 = r4.mInnerLayout
            int r1 = com.autonavi.minimap.R.id.pulltorefresh_progress_nearby
            android.view.View r0 = r0.findViewById(r1)
            android.widget.ProgressBar r0 = (android.widget.ProgressBar) r0
            r4.mHeaderProgress = r0
            goto L_0x0078
        L_0x006c:
            android.widget.RelativeLayout r0 = r4.mInnerLayout
            int r1 = com.autonavi.minimap.R.id.pulltorefresh_progressBar
            android.view.View r0 = r0.findViewById(r1)
            android.widget.ProgressBar r0 = (android.widget.ProgressBar) r0
            r4.mHeaderProgress = r0
        L_0x0078:
            android.widget.RelativeLayout r0 = r4.mInnerLayout
            int r1 = com.autonavi.minimap.R.id.pulltorefresh_sub_text
            android.view.View r0 = r0.findViewById(r1)
            android.widget.TextView r0 = (android.widget.TextView) r0
            r4.mSubHeaderText = r0
            android.widget.RelativeLayout r0 = r4.mInnerLayout
            int r1 = com.autonavi.minimap.R.id.pulltorefresh_imageView
            android.view.View r0 = r0.findViewById(r1)
            android.widget.ImageView r0 = (android.widget.ImageView) r0
            r4.mHeaderImage = r0
            android.widget.RelativeLayout r0 = r4.mInnerLayout
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r0 = (android.widget.FrameLayout.LayoutParams) r0
            int[] r1 = com.autonavi.widget.pulltorefresh.internal.LoadingLayout.AnonymousClass1.b
            int r2 = r6.ordinal()
            r1 = r1[r2]
            r2 = 2
            if (r1 == r2) goto L_0x00c6
            com.autonavi.widget.pulltorefresh.PullToRefreshBase$Orientation r1 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation.VERTICAL
            if (r7 != r1) goto L_0x00aa
            r7 = 80
            goto L_0x00ab
        L_0x00aa:
            r7 = 5
        L_0x00ab:
            r0.gravity = r7
            int r7 = com.autonavi.minimap.R.string.pulltorefresh_pull_label
            java.lang.String r7 = r5.getString(r7)
            r4.mPullLabel = r7
            int r7 = com.autonavi.minimap.R.string.pulltorefresh_refreshing_label
            java.lang.String r7 = r5.getString(r7)
            r4.mRefreshingLabel = r7
            int r7 = com.autonavi.minimap.R.string.pulltorefresh_release_label
            java.lang.String r7 = r5.getString(r7)
            r4.mReleaseLabel = r7
            goto L_0x00e8
        L_0x00c6:
            com.autonavi.widget.pulltorefresh.PullToRefreshBase$Orientation r1 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation.VERTICAL
            if (r7 != r1) goto L_0x00cd
            r7 = 48
            goto L_0x00ce
        L_0x00cd:
            r7 = 3
        L_0x00ce:
            r0.gravity = r7
            int r7 = com.autonavi.minimap.R.string.pulltorefresh_from_bottom_pull_label
            java.lang.String r7 = r5.getString(r7)
            r4.mPullLabel = r7
            int r7 = com.autonavi.minimap.R.string.pulltorefresh_from_bottom_refreshing_label
            java.lang.String r7 = r5.getString(r7)
            r4.mRefreshingLabel = r7
            int r7 = com.autonavi.minimap.R.string.pulltorefresh_from_bottom_release_label
            java.lang.String r7 = r5.getString(r7)
            r4.mReleaseLabel = r7
        L_0x00e8:
            int r7 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderBackground
            boolean r7 = r8.hasValue(r7)
            if (r7 == 0) goto L_0x00fb
            int r7 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderBackground
            android.graphics.drawable.Drawable r7 = r8.getDrawable(r7)
            if (r7 == 0) goto L_0x00fb
            defpackage.erg.a(r4, r7)
        L_0x00fb:
            int r7 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderTextAppearance
            boolean r7 = r8.hasValue(r7)
            if (r7 == 0) goto L_0x0112
            android.util.TypedValue r7 = new android.util.TypedValue
            r7.<init>()
            int r0 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderTextAppearance
            r8.getValue(r0, r7)
            int r7 = r7.data
            r4.setTextAppearance(r7)
        L_0x0112:
            int r7 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrSubHeaderTextAppearance
            boolean r7 = r8.hasValue(r7)
            if (r7 == 0) goto L_0x0129
            android.util.TypedValue r7 = new android.util.TypedValue
            r7.<init>()
            int r0 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrSubHeaderTextAppearance
            r8.getValue(r0, r7)
            int r7 = r7.data
            r4.setSubTextAppearance(r7)
        L_0x0129:
            int r7 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderTextColor
            boolean r7 = r8.hasValue(r7)
            if (r7 == 0) goto L_0x013c
            int r7 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderTextColor
            android.content.res.ColorStateList r7 = r8.getColorStateList(r7)
            if (r7 == 0) goto L_0x013c
            r4.setTextColor(r7)
        L_0x013c:
            int r7 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderSubTextColor
            boolean r7 = r8.hasValue(r7)
            if (r7 == 0) goto L_0x014f
            int r7 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderSubTextColor
            android.content.res.ColorStateList r7 = r8.getColorStateList(r7)
            if (r7 == 0) goto L_0x014f
            r4.setSubTextColor(r7)
        L_0x014f:
            r7 = 0
            int r0 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrDrawable
            boolean r0 = r8.hasValue(r0)
            if (r0 == 0) goto L_0x015e
            int r7 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrDrawable
            android.graphics.drawable.Drawable r7 = r8.getDrawable(r7)
        L_0x015e:
            int[] r0 = com.autonavi.widget.pulltorefresh.internal.LoadingLayout.AnonymousClass1.b
            int r6 = r6.ordinal()
            r6 = r0[r6]
            if (r6 == r2) goto L_0x01b7
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderBackgroundStart
            boolean r6 = r8.hasValue(r6)
            if (r6 == 0) goto L_0x0186
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderBackgroundStart
            android.graphics.drawable.Drawable r6 = r8.getDrawable(r6)
            int r0 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderBackgroundStart
            java.lang.String r1 = "#FFFFFF"
            int r1 = android.graphics.Color.parseColor(r1)
            r8.getColor(r0, r1)
            if (r6 == 0) goto L_0x0186
            defpackage.erg.a(r4, r6)
        L_0x0186:
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderTextColorStart
            boolean r6 = r8.hasValue(r6)
            if (r6 == 0) goto L_0x0199
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderTextColorStart
            android.content.res.ColorStateList r6 = r8.getColorStateList(r6)
            if (r6 == 0) goto L_0x0199
            r4.setTextColor(r6)
        L_0x0199:
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrDrawableStart
            boolean r6 = r8.hasValue(r6)
            if (r6 == 0) goto L_0x01a8
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrDrawableStart
            android.graphics.drawable.Drawable r7 = r8.getDrawable(r6)
            goto L_0x01fa
        L_0x01a8:
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrDrawableTop
            boolean r6 = r8.hasValue(r6)
            if (r6 == 0) goto L_0x01fa
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrDrawableTop
            android.graphics.drawable.Drawable r7 = r8.getDrawable(r6)
            goto L_0x01fa
        L_0x01b7:
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderBackgroundEnd
            boolean r6 = r8.hasValue(r6)
            if (r6 == 0) goto L_0x01ca
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderBackgroundEnd
            android.graphics.drawable.Drawable r6 = r8.getDrawable(r6)
            if (r6 == 0) goto L_0x01ca
            defpackage.erg.a(r4, r6)
        L_0x01ca:
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderTextColorEnd
            boolean r6 = r8.hasValue(r6)
            if (r6 == 0) goto L_0x01dd
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrHeaderTextColorEnd
            android.content.res.ColorStateList r6 = r8.getColorStateList(r6)
            if (r6 == 0) goto L_0x01dd
            r4.setTextColor(r6)
        L_0x01dd:
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrDrawableEnd
            boolean r6 = r8.hasValue(r6)
            if (r6 == 0) goto L_0x01ec
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrDrawableEnd
            android.graphics.drawable.Drawable r7 = r8.getDrawable(r6)
            goto L_0x01fa
        L_0x01ec:
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrDrawableBottom
            boolean r6 = r8.hasValue(r6)
            if (r6 == 0) goto L_0x01fa
            int r6 = com.autonavi.minimap.R.styleable.PullToRefreshAttrs_mPtrDrawableBottom
            android.graphics.drawable.Drawable r7 = r8.getDrawable(r6)
        L_0x01fa:
            if (r7 != 0) goto L_0x0208
            android.content.res.Resources r5 = r5.getResources()
            int r6 = r4.getDefaultDrawableResId()
            android.graphics.drawable.Drawable r7 = r5.getDrawable(r6)
        L_0x0208:
            r4.setLoadingDrawable(r7)
            r4.reset()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.widget.pulltorefresh.internal.LoadingLayout.<init>(android.content.Context, com.autonavi.widget.pulltorefresh.PullToRefreshBase$Mode, com.autonavi.widget.pulltorefresh.PullToRefreshBase$Orientation, android.content.res.TypedArray):void");
    }

    public LoadingLayout(Context context, Mode mode, Orientation orientation) {
        super(context);
        this.mMode = mode;
        this.mScrollDirection = orientation;
        this.mHeaderText = null;
        this.mSubHeaderText = null;
        this.mHeaderImage = null;
        this.mHeaderProgress = null;
    }

    public void setHeaderBgColor(String str) {
        setBackgroundColor(Color.parseColor(str));
    }

    public final void setHeight(int i) {
        getLayoutParams().height = i;
        requestLayout();
    }

    public final void setWidth(int i) {
        getLayoutParams().width = i;
        requestLayout();
    }

    public int getContentSize() {
        if (AnonymousClass1.a[this.mScrollDirection.ordinal()] != 1) {
            return this.mInnerLayout.getHeight();
        }
        return this.mInnerLayout.getWidth();
    }

    public void hideAllViews() {
        if (this.mHeaderText.getVisibility() == 0) {
            this.mHeaderText.setVisibility(4);
        }
        if (this.mHeaderProgress.getVisibility() == 0) {
            this.mHeaderProgress.setVisibility(4);
        }
        if (this.mHeaderImage.getVisibility() == 0) {
            this.mHeaderImage.setVisibility(4);
        }
        if (this.mSubHeaderText.getVisibility() == 0) {
            this.mSubHeaderText.setVisibility(4);
        }
    }

    public final void onPull(float f) {
        if (!this.mUseIntrinsicAnimation) {
            onPullImpl(f);
        }
    }

    public final void pullToRefresh() {
        if (this.mHeaderText != null) {
            this.mHeaderText.setText(this.mPullLabel);
        }
        this.pulltorefreshflag = true;
        pullToRefreshImpl();
    }

    public final void refreshing() {
        if (this.mHeaderText != null) {
            this.mHeaderText.setText(this.mRefreshingLabel);
        }
        if (this.mUseIntrinsicAnimation) {
            ((AnimationDrawable) this.mHeaderImage.getDrawable()).start();
        } else {
            refreshingImpl();
        }
        if (this.mSubHeaderText != null) {
            this.mSubHeaderText.setVisibility(8);
        }
    }

    public void setRefreshListener(a aVar) {
        this.mRefreshingListener = aVar;
    }

    public final void releaseToRefresh() {
        if (this.mHeaderText != null) {
            this.mHeaderText.setText(this.mReleaseLabel);
        }
        releaseToRefreshImpl();
    }

    public void reset() {
        this.pulltorefreshflag = false;
        if (this.mHeaderText != null) {
            this.mHeaderText.setText(this.mPullLabel);
            this.mHeaderText.setVisibility(0);
        }
        if (this.headImageFlag) {
            this.mHeaderImage.setVisibility(0);
        }
        if (this.mUseIntrinsicAnimation) {
            ((AnimationDrawable) this.mHeaderImage.getDrawable()).stop();
        } else {
            resetImpl();
        }
        if (this.mSubHeaderText != null) {
            if (TextUtils.isEmpty(this.mSubHeaderText.getText())) {
                this.mSubHeaderText.setVisibility(8);
                return;
            }
            this.mSubHeaderText.setVisibility(0);
        }
    }

    public void setLastUpdatedLabel(CharSequence charSequence) {
        setSubHeaderText(charSequence);
    }

    public final void setLoadingDrawable(Drawable drawable) {
        this.mHeaderImage.setImageDrawable(drawable);
        this.mUseIntrinsicAnimation = drawable instanceof AnimationDrawable;
        onLoadingDrawableSet(drawable);
    }

    public void setPullLabel(CharSequence charSequence) {
        this.mPullLabel = charSequence;
    }

    public void setRefreshingLabel(CharSequence charSequence) {
        this.mRefreshingLabel = charSequence;
    }

    public void setReleaseLabel(CharSequence charSequence) {
        this.mReleaseLabel = charSequence;
    }

    public void setTextTypeface(Typeface typeface) {
        this.mHeaderText.setTypeface(typeface);
    }

    public void hideImageHead() {
        this.mHeaderImage.setVisibility(4);
        this.headImageFlag = false;
    }

    public void showImageHead() {
        this.headImageFlag = true;
        this.mHeaderImage.setVisibility(0);
    }

    public void showInvisibleViews() {
        if (4 == this.mHeaderText.getVisibility()) {
            this.mHeaderText.setVisibility(0);
        }
        if (4 == this.mHeaderProgress.getVisibility()) {
            this.mHeaderProgress.setVisibility(0);
        }
        if (4 == this.mHeaderImage.getVisibility()) {
            this.mHeaderImage.setVisibility(0);
        }
        if (4 == this.mSubHeaderText.getVisibility()) {
            this.mSubHeaderText.setVisibility(0);
        }
    }

    private void setSubHeaderText(CharSequence charSequence) {
        if (this.mSubHeaderText != null) {
            if (TextUtils.isEmpty(charSequence)) {
                this.mSubHeaderText.setVisibility(8);
                return;
            }
            this.mSubHeaderText.setText(charSequence);
            if (8 == this.mSubHeaderText.getVisibility()) {
                this.mSubHeaderText.setVisibility(0);
            }
        }
    }

    private void setSubTextAppearance(int i) {
        if (this.mSubHeaderText != null) {
            this.mSubHeaderText.setTextAppearance(getContext(), i);
        }
    }

    private void setSubTextColor(ColorStateList colorStateList) {
        if (this.mSubHeaderText != null) {
            this.mSubHeaderText.setTextColor(colorStateList);
        }
    }

    private void setTextAppearance(int i) {
        if (this.mHeaderText != null) {
            this.mHeaderText.setTextAppearance(getContext(), i);
        }
        if (this.mSubHeaderText != null) {
            this.mSubHeaderText.setTextAppearance(getContext(), i);
        }
    }

    private void setTextColor(ColorStateList colorStateList) {
        if (this.mHeaderText != null) {
            this.mHeaderText.setTextColor(colorStateList);
        }
        if (this.mSubHeaderText != null) {
            this.mSubHeaderText.setTextColor(colorStateList);
        }
    }

    public void setHeaderTextTextSize(float f) {
        if (this.mHeaderText != null) {
            this.mHeaderText.setTextSize(f);
        }
    }

    public void setHeaderTextTextColor(int i) {
        if (this.mHeaderText != null) {
            this.mHeaderText.setTextColor(i);
        }
    }

    public void setHeaderTextTextStyle(int i) {
        if (this.mHeaderText != null) {
            this.mHeaderText.setTextAppearance(getContext(), i);
        }
    }

    public void setInnerLayoutPadTop(int i) {
        if (this.mInnerLayout != null) {
            this.mInnerLayout.setPadding(this.mInnerLayout.getPaddingLeft(), i, this.mInnerLayout.getPaddingRight(), this.mInnerLayout.getPaddingBottom());
        }
    }

    public void setHeadViewImageVisibility(int i) {
        this.mHeaderImage.setVisibility(i);
    }

    public void setProgressVisibility(int i) {
        this.mHeaderProgress.setVisibility(i);
    }

    public void setHeaderTextVisibility(int i) {
        this.mHeaderText.setVisibility(i);
    }

    public void setHeaderHeight(int i, int i2) {
        LayoutParams layoutParams = (LayoutParams) this.mInnerLayout.getLayoutParams();
        layoutParams.height = i;
        layoutParams.topMargin = i2;
    }
}
