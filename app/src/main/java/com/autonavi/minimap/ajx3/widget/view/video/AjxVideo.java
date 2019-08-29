package com.autonavi.minimap.ajx3.widget.view.video;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.autonavi.minimap.ajx3.IHandleBackPressedView;
import com.autonavi.minimap.ajx3.IPageLifeCircleView;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.VideoProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.video.ui.VideoView;
import com.autonavi.minimap.ajx3.widget.view.video.ui.VideoView.OnPlayStateChangedListener;

public class AjxVideo extends RelativeLayout implements IHandleBackPressedView, IPageLifeCircleView, ViewExtension {
    private IAjxContext mAjxContext;
    private boolean mAutoPlay;
    private boolean mControls;
    private int mFullScreenBackgroundColor = -1;
    private VideoView mInnerView;
    private boolean mLoop;
    private boolean mMuted;
    private OnClickListener mOnClickListener;
    private int mPendingScaleType = -1;
    private final BaseProperty mProperty;
    private int mScreenState = -1;
    private String mSrc;
    private String mThumb;
    private String mTitle;
    private OnPlayStateChangedListener mUiStateChangedListener;
    private float mVolume = -1.0f;

    public void onNewIntent() {
    }

    public AjxVideo(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAjxContext = iAjxContext;
        this.mProperty = createProperty();
    }

    private void initInnerView() {
        this.mInnerView = new VideoView(this.mAjxContext.getNativeContext());
        addView(this.mInnerView, new LayoutParams(-1, -1));
        if (this.mAutoPlay) {
            this.mInnerView.setAutoPlay(true);
        }
        if (this.mThumb != null) {
            this.mInnerView.setThumbUrl(this.mThumb, (Drawable) null);
        }
        if (this.mOnClickListener != null) {
            this.mInnerView.setOnClickListener(this.mOnClickListener);
            this.mOnClickListener = null;
        }
        if (this.mUiStateChangedListener != null) {
            this.mInnerView.setPlayStateChangedListener(this.mUiStateChangedListener);
            this.mUiStateChangedListener = null;
        }
        if (this.mLoop) {
            this.mInnerView.setLoop(true);
            this.mLoop = false;
        }
        if (this.mFullScreenBackgroundColor != -1) {
            this.mInnerView.setFullScreenBackgroundColor(this.mFullScreenBackgroundColor);
            this.mFullScreenBackgroundColor = -1;
        }
        if (!this.mControls) {
            this.mInnerView.hideControllerView();
        }
        this.mInnerView.bind(this.mSrc, this.mTitle);
        if (this.mScreenState != -1) {
            this.mInnerView.updateScreenState(this.mScreenState);
            this.mScreenState = -1;
        }
        if (this.mPendingScaleType != -1) {
            this.mInnerView.setScaleType(this.mPendingScaleType);
            this.mPendingScaleType = -1;
        }
        if (this.mVolume > 0.0f) {
            this.mInnerView.setVolume(this.mVolume);
            this.mVolume = -1.0f;
        }
        if (this.mMuted) {
            this.mInnerView.setMuted(this.mMuted);
            this.mMuted = false;
        }
    }

    private VideoProperty createProperty() {
        return new VideoProperty(this, this.mAjxContext);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public void setSrc(String str) {
        this.mSrc = str;
        initInnerView();
    }

    public void setTitle(String str) {
        if (this.mInnerView == null) {
            this.mTitle = str;
            return;
        }
        if (this.mSrc != null) {
            this.mInnerView.bind(this.mSrc, this.mTitle);
        }
    }

    public void setThumb(String str) {
        if (this.mInnerView == null) {
            this.mThumb = str;
        } else {
            this.mInnerView.setThumbUrl(str, (Drawable) null);
        }
    }

    public void setAutoPlay(boolean z) {
        if (this.mInnerView == null) {
            this.mAutoPlay = z;
        } else {
            this.mInnerView.setAutoPlay(z);
        }
    }

    public void requestPause() {
        if (this.mInnerView != null) {
            this.mInnerView.requestPause();
        }
    }

    public void requestStop() {
        if (this.mInnerView != null) {
            this.mInnerView.requestStop();
        }
    }

    public void requestPlay() {
        if (this.mInnerView != null) {
            this.mInnerView.requestPlay();
        }
    }

    public void setUiStateChangedListener(OnPlayStateChangedListener onPlayStateChangedListener) {
        if (this.mInnerView == null) {
            this.mUiStateChangedListener = onPlayStateChangedListener;
        } else {
            this.mInnerView.setPlayStateChangedListener(onPlayStateChangedListener);
        }
    }

    public void setControls(boolean z) {
        if (this.mInnerView == null) {
            this.mControls = z;
        } else if (z) {
            this.mInnerView.showControllerView();
        } else {
            this.mInnerView.hideControllerView();
        }
    }

    public void setLoop(boolean z) {
        if (this.mInnerView == null) {
            this.mLoop = z;
        } else {
            this.mInnerView.setLoop(z);
        }
    }

    public void updateScreenState(int i) {
        if (this.mInnerView == null) {
            this.mScreenState = i;
        } else {
            this.mInnerView.updateScreenState(i);
        }
    }

    public void seekTo(long j) {
        if (this.mInnerView != null) {
            this.mInnerView.seekTo(j);
        }
    }

    public void setVolume(float f) {
        if (this.mInnerView != null) {
            this.mInnerView.setVolume(f);
        } else {
            this.mVolume = f;
        }
    }

    public void setBrightness(float f) {
        if (this.mInnerView != null) {
            this.mInnerView.setBrightness(f);
        }
    }

    public void setMuted(boolean z) {
        if (this.mInnerView != null) {
            this.mInnerView.setMuted(z);
        } else {
            this.mMuted = z;
        }
    }

    public void setFullScreenBackgroundColor(int i) {
        if (this.mInnerView != null) {
            this.mInnerView.setFullScreenBackgroundColor(i);
        } else {
            this.mFullScreenBackgroundColor = i;
        }
    }

    public void setVideoClickListener(@Nullable OnClickListener onClickListener) {
        if (this.mInnerView != null) {
            this.mInnerView.setOnClickListener(onClickListener);
        } else {
            this.mOnClickListener = onClickListener;
        }
    }

    public void setScaleType(int i) {
        if (this.mInnerView != null) {
            this.mInnerView.setScaleType(i);
        } else {
            this.mPendingScaleType = i;
        }
    }

    public void onPageResume() {
        if (this.mInnerView != null) {
            this.mInnerView.handlePageResume();
        }
    }

    public void onPageStop() {
        if (this.mInnerView != null) {
            this.mInnerView.handlePageStop();
        }
    }

    public void onPageDestroy() {
        if (this.mInnerView != null) {
            this.mInnerView.handlePageDestroy();
        }
    }

    public boolean onBackPressed() {
        if (this.mInnerView != null) {
            return this.mInnerView.onBackPressed();
        }
        return false;
    }
}
