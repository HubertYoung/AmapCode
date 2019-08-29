package com.autonavi.common.cloudsync.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.autonavi.minimap.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MultiStateView extends FrameLayout {
    public static final int VIEW_STATE_CONTENT = 0;
    public static final int VIEW_STATE_EMPTY = 2;
    public static final int VIEW_STATE_ERROR = 1;
    public static final int VIEW_STATE_LOADING = 3;
    public static final int VIEW_STATE_UNKNOWN = -1;
    private boolean mAnimateViewChanges;
    private View mContentView;
    private View mEmptyView;
    private View mErrorView;
    private LayoutInflater mInflater;
    private View mLoadingView;
    private int mViewState;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewState {
    }

    public MultiStateView(Context context) {
        this(context, null);
    }

    public MultiStateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAnimateViewChanges = false;
        this.mViewState = -1;
        init(attributeSet);
    }

    public MultiStateView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAnimateViewChanges = false;
        this.mViewState = -1;
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        this.mInflater = LayoutInflater.from(getContext());
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.MultiStateView);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.MultiStateView_msv_loadingView, -1);
        if (resourceId >= 0) {
            this.mLoadingView = this.mInflater.inflate(resourceId, this, false);
            addView(this.mLoadingView, this.mLoadingView.getLayoutParams());
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.MultiStateView_msv_emptyView, -1);
        if (resourceId2 >= 0) {
            this.mEmptyView = this.mInflater.inflate(resourceId2, this, false);
            addView(this.mEmptyView, this.mEmptyView.getLayoutParams());
        }
        int resourceId3 = obtainStyledAttributes.getResourceId(R.styleable.MultiStateView_msv_errorView, -1);
        if (resourceId3 >= 0) {
            this.mErrorView = this.mInflater.inflate(resourceId3, this, false);
            addView(this.mErrorView, this.mErrorView.getLayoutParams());
        }
        int i = obtainStyledAttributes.getInt(R.styleable.MultiStateView_msv_viewState, 0);
        this.mAnimateViewChanges = obtainStyledAttributes.getBoolean(R.styleable.MultiStateView_msv_animateViewChanges, false);
        switch (i) {
            case 0:
                this.mViewState = 0;
                break;
            case 1:
                this.mViewState = 1;
                break;
            case 2:
                this.mViewState = 2;
                break;
            case 3:
                this.mViewState = 3;
                break;
            default:
                this.mViewState = -1;
                break;
        }
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mContentView == null) {
            throw new IllegalArgumentException("Content view is not defined");
        }
        setView(-1);
    }

    public void addView(View view) {
        if (isValidContentView(view)) {
            this.mContentView = view;
        }
        super.addView(view);
    }

    public void addView(View view, int i) {
        if (isValidContentView(view)) {
            this.mContentView = view;
        }
        super.addView(view, i);
    }

    public void addView(View view, int i, LayoutParams layoutParams) {
        if (isValidContentView(view)) {
            this.mContentView = view;
        }
        super.addView(view, i, layoutParams);
    }

    public void addView(View view, LayoutParams layoutParams) {
        if (isValidContentView(view)) {
            this.mContentView = view;
        }
        super.addView(view, layoutParams);
    }

    public void addView(View view, int i, int i2) {
        if (isValidContentView(view)) {
            this.mContentView = view;
        }
        super.addView(view, i, i2);
    }

    /* access modifiers changed from: protected */
    public boolean addViewInLayout(View view, int i, LayoutParams layoutParams) {
        if (isValidContentView(view)) {
            this.mContentView = view;
        }
        return super.addViewInLayout(view, i, layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean addViewInLayout(View view, int i, LayoutParams layoutParams, boolean z) {
        if (isValidContentView(view)) {
            this.mContentView = view;
        }
        return super.addViewInLayout(view, i, layoutParams, z);
    }

    @Nullable
    public View getView(int i) {
        switch (i) {
            case 0:
                return this.mContentView;
            case 1:
                return this.mErrorView;
            case 2:
                return this.mEmptyView;
            case 3:
                return this.mLoadingView;
            default:
                return null;
        }
    }

    public int getViewState() {
        return this.mViewState;
    }

    public void setViewState(int i) {
        if (i != this.mViewState) {
            int i2 = this.mViewState;
            this.mViewState = i;
            setView(i2);
        }
    }

    private void setView(int i) {
        switch (this.mViewState) {
            case 1:
                if (this.mErrorView == null) {
                    throw new NullPointerException("Error View");
                }
                if (this.mLoadingView != null) {
                    this.mLoadingView.setVisibility(8);
                }
                if (this.mContentView != null) {
                    this.mContentView.setVisibility(8);
                }
                if (this.mEmptyView != null) {
                    this.mEmptyView.setVisibility(8);
                }
                if (this.mAnimateViewChanges) {
                    animateLayoutChange(getView(i));
                    return;
                } else {
                    this.mErrorView.setVisibility(0);
                    return;
                }
            case 2:
                if (this.mEmptyView == null) {
                    throw new NullPointerException("Empty View");
                }
                if (this.mLoadingView != null) {
                    this.mLoadingView.setVisibility(8);
                }
                if (this.mErrorView != null) {
                    this.mErrorView.setVisibility(8);
                }
                if (this.mContentView != null) {
                    this.mContentView.setVisibility(8);
                }
                if (this.mAnimateViewChanges) {
                    animateLayoutChange(getView(i));
                    return;
                } else {
                    this.mEmptyView.setVisibility(0);
                    return;
                }
            case 3:
                if (this.mLoadingView == null) {
                    throw new NullPointerException("Loading View");
                }
                if (this.mContentView != null) {
                    this.mContentView.setVisibility(8);
                }
                if (this.mErrorView != null) {
                    this.mErrorView.setVisibility(8);
                }
                if (this.mEmptyView != null) {
                    this.mEmptyView.setVisibility(8);
                }
                if (this.mAnimateViewChanges) {
                    animateLayoutChange(getView(i));
                    return;
                } else {
                    this.mLoadingView.setVisibility(0);
                    return;
                }
            default:
                if (this.mContentView == null) {
                    throw new NullPointerException("Content View");
                }
                if (this.mLoadingView != null) {
                    this.mLoadingView.setVisibility(8);
                }
                if (this.mErrorView != null) {
                    this.mErrorView.setVisibility(8);
                }
                if (this.mEmptyView != null) {
                    this.mEmptyView.setVisibility(8);
                }
                if (this.mAnimateViewChanges) {
                    animateLayoutChange(getView(i));
                    return;
                } else {
                    this.mContentView.setVisibility(0);
                    return;
                }
        }
    }

    private boolean isValidContentView(View view) {
        if ((this.mContentView != null && this.mContentView != view) || view == this.mLoadingView || view == this.mErrorView || view == this.mEmptyView) {
            return false;
        }
        return true;
    }

    public void setViewForState(View view, int i, boolean z) {
        switch (i) {
            case 0:
                if (this.mContentView != null) {
                    removeView(this.mContentView);
                }
                this.mContentView = view;
                addView(this.mContentView);
                break;
            case 1:
                if (this.mErrorView != null) {
                    removeView(this.mErrorView);
                }
                this.mErrorView = view;
                addView(this.mErrorView);
                break;
            case 2:
                if (this.mEmptyView != null) {
                    removeView(this.mEmptyView);
                }
                this.mEmptyView = view;
                addView(this.mEmptyView);
                break;
            case 3:
                if (this.mLoadingView != null) {
                    removeView(this.mLoadingView);
                }
                this.mLoadingView = view;
                addView(this.mLoadingView);
                break;
        }
        setView(-1);
        if (z) {
            setViewState(i);
        }
    }

    public void setViewForState(View view, int i) {
        setViewForState(view, i, false);
    }

    public void setViewForState(@LayoutRes int i, int i2, boolean z) {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getContext());
        }
        setViewForState(this.mInflater.inflate(i, this, false), i2, z);
    }

    public void setViewForState(@LayoutRes int i, int i2) {
        setViewForState(i, i2, false);
    }

    public void setAnimateLayoutChanges(boolean z) {
        this.mAnimateViewChanges = z;
    }

    private void animateLayoutChange(@Nullable final View view) {
        final View view2 = getView(this.mViewState);
        if (view == null) {
            if (view2 != null) {
                view2.setVisibility(0);
            }
            return;
        }
        view.setVisibility(0);
        ObjectAnimator duration = ObjectAnimator.ofFloat(view, "alpha", new float[]{1.0f, 0.0f}).setDuration(250);
        duration.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                view.setVisibility(8);
                if (view2 != null) {
                    view2.setVisibility(0);
                }
                ObjectAnimator.ofFloat(view2, "alpha", new float[]{0.0f, 1.0f}).setDuration(250).start();
            }
        });
        duration.start();
    }
}
