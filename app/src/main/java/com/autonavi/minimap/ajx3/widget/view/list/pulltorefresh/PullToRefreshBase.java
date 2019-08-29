package com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.util.ViewUtils;

public abstract class PullToRefreshBase<T extends View> extends LinearLayout implements IPullToRefresh<T> {
    static final float FRICTION = 3.0f;
    static final int SMOOTH_SCROLL_DURATION_MS = 200;
    static final String STATE_CURRENT_MODE = "ptr_current_mode";
    static final String STATE_MODE = "ptr_mode";
    static final String STATE_STATE = "ptr_state";
    static final String STATE_SUPER = "ptr_super";
    private String footerArrowImage = null;
    private String footerBgImage = null;
    private String footerPullText = null;
    private String footerRefresh = null;
    private String footerRelease = null;
    private String headerArrowImage = null;
    private String headerBgImage = null;
    private String headerPullText = null;
    private String headerRefresh = null;
    private String headerRelease = null;
    private Mode mCurrentMode;
    private SmoothScrollRunnable mCurrentSmoothScrollRunnable;
    private LoadingLayout mFooterLayout;
    private LoadingLayout mHeaderLayout;
    private float mInitScrollY;
    private float mInitialMotionY;
    private boolean mIsBeingDragged = false;
    private float mLastMotionX;
    private float mLastMotionY;
    private boolean mLayoutVisibilityChangesEnabled = true;
    private Mode mMode = Mode.getDefault();
    private OnLoadMoreListener<T> mOnLoadMoreListener;
    private OnPullEventListener<T> mOnPullEventListener;
    private OnPullListener mOnPullListener;
    private OnRefreshListener<T> mOnRefreshListener;
    protected T mRefreshableView;
    private State mState = State.RESET;
    private int mTouchSlop;
    private int screenH;

    public enum Mode {
        DISABLED(0),
        PULL_FROM_START(1),
        PULL_FROM_END(2),
        BOTH(3),
        MANUAL_REFRESH_ONLY(4);
        
        private int mIntValue;

        static Mode mapIntToValue(int i) {
            Mode[] values;
            for (Mode mode : values()) {
                if (i == mode.getIntValue()) {
                    return mode;
                }
            }
            return getDefault();
        }

        static Mode getDefault() {
            return DISABLED;
        }

        private Mode(int i) {
            this.mIntValue = i;
        }

        /* access modifiers changed from: 0000 */
        public final boolean permitsPullToRefresh() {
            return (this == DISABLED || this == MANUAL_REFRESH_ONLY) ? false : true;
        }

        public final boolean showHeaderLoadingLayout() {
            return this == PULL_FROM_START || this == BOTH;
        }

        public final boolean showFooterLoadingLayout() {
            return this == PULL_FROM_END || this == BOTH || this == MANUAL_REFRESH_ONLY;
        }

        /* access modifiers changed from: 0000 */
        public final int getIntValue() {
            return this.mIntValue;
        }
    }

    public interface OnLoadMoreListener<V extends View> {
        void onLoadMore(PullToRefreshBase<V> pullToRefreshBase, boolean z);
    }

    public interface OnPullEventListener<V extends View> {
        void onPullEvent(PullToRefreshBase<V> pullToRefreshBase, State state, Mode mode);
    }

    public interface OnPullListener {
        void onPull(int i, int i2);
    }

    public interface OnRefreshListener<V extends View> {
        void onRefresh(PullToRefreshBase<V> pullToRefreshBase, boolean z);
    }

    interface OnSmoothScrollFinishedListener {
        void onSmoothScrollFinished();
    }

    final class SmoothScrollRunnable implements Runnable {
        private boolean mContinueRunning = true;
        private int mCurrentY = -1;
        private final long mDuration;
        private final Interpolator mInterpolator;
        private final OnSmoothScrollFinishedListener mListener;
        private final int mScrollFromY;
        private final int mScrollToY;
        private long mStartTime = -1;

        public SmoothScrollRunnable(int i, int i2, long j, OnSmoothScrollFinishedListener onSmoothScrollFinishedListener) {
            this.mScrollFromY = i;
            this.mScrollToY = i2;
            this.mInterpolator = new DecelerateInterpolator();
            this.mDuration = j;
            this.mListener = onSmoothScrollFinishedListener;
        }

        public final void run() {
            if (this.mStartTime == -1) {
                this.mStartTime = System.currentTimeMillis();
            } else {
                this.mCurrentY = this.mScrollFromY - Math.round(((float) (this.mScrollFromY - this.mScrollToY)) * this.mInterpolator.getInterpolation(((float) Math.max(Math.min(((System.currentTimeMillis() - this.mStartTime) * 1000) / this.mDuration, 1000), 0)) / 1000.0f));
                PullToRefreshBase.this.setHeaderScroll(this.mCurrentY);
            }
            if (!this.mContinueRunning || this.mScrollToY == this.mCurrentY) {
                if (this.mListener != null) {
                    this.mListener.onSmoothScrollFinished();
                }
                return;
            }
            ViewUtils.postOnAnimation(PullToRefreshBase.this, this);
        }

        public final void stop() {
            this.mContinueRunning = false;
            PullToRefreshBase.this.removeCallbacks(this);
        }
    }

    public enum State {
        RESET(0),
        PULL_TO_REFRESH(1),
        RELEASE_TO_REFRESH(2),
        REFRESHING(8),
        MANUAL_REFRESHING(9),
        OVERSCROLLING(16);
        
        private int mIntValue;

        static State mapIntToValue(int i) {
            State[] values;
            for (State state : values()) {
                if (i == state.getIntValue()) {
                    return state;
                }
            }
            return RESET;
        }

        private State(int i) {
            this.mIntValue = i;
        }

        /* access modifiers changed from: 0000 */
        public final int getIntValue() {
            return this.mIntValue;
        }
    }

    /* access modifiers changed from: protected */
    public abstract T createRefreshableView();

    /* access modifiers changed from: protected */
    public int getPullToRefreshScrollDuration() {
        return 200;
    }

    /* access modifiers changed from: protected */
    public abstract boolean isReadyForPullEnd();

    /* access modifiers changed from: protected */
    public abstract boolean isReadyForPullStart();

    public void setCurrentState(Mode mode) {
        this.mCurrentMode = mode;
    }

    public PullToRefreshBase(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        Point point = new Point();
        ((WindowManager) getContext().getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getSize(point);
        this.screenH = point.y;
    }

    public void addView(View view, int i, LayoutParams layoutParams) {
        View refreshableView = getRefreshableView();
        if (refreshableView instanceof ViewGroup) {
            ((ViewGroup) refreshableView).addView(view, i, layoutParams);
            return;
        }
        throw new UnsupportedOperationException("Refreshable View is not a ViewGroup so can't addView");
    }

    public final Mode getCurrentMode() {
        return this.mCurrentMode;
    }

    public final Mode getMode() {
        return this.mMode;
    }

    public final State getState() {
        return this.mState;
    }

    public final boolean isPullToRefreshEnabled() {
        return this.mMode.permitsPullToRefresh();
    }

    public final boolean isRefreshing() {
        return this.mState == State.REFRESHING || this.mState == State.MANUAL_REFRESHING;
    }

    public final T getRefreshableView() {
        return this.mRefreshableView;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (!isPullToRefreshEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 3 || action == 1) {
            this.mIsBeingDragged = false;
            return false;
        } else if (action != 0 && this.mIsBeingDragged) {
            return true;
        } else {
            if (action != 0) {
                if (action == 2 && (isReadyForPull() || isRefreshing())) {
                    float y = motionEvent.getY();
                    float x = motionEvent.getX();
                    float f = y - this.mLastMotionY;
                    float f2 = x - this.mLastMotionX;
                    float abs = Math.abs(f);
                    if (abs > ((float) this.mTouchSlop) && abs > Math.abs(f2)) {
                        if (f >= 1.0f) {
                            if (getScrollY() > 0) {
                                z = true;
                            }
                            if (z) {
                                this.mLastMotionY = y;
                                this.mLastMotionX = x;
                                this.mIsBeingDragged = true;
                            } else if (this.mMode.showHeaderLoadingLayout() && isReadyForPullStart()) {
                                this.mLastMotionY = y;
                                this.mLastMotionX = x;
                                this.mIsBeingDragged = true;
                                if (this.mMode == Mode.BOTH) {
                                    this.mCurrentMode = Mode.PULL_FROM_START;
                                }
                            }
                        } else if (f <= -1.0f) {
                            if (getScrollY() < 0) {
                                z = true;
                            }
                            if (z) {
                                this.mLastMotionY = y;
                                this.mLastMotionX = x;
                                this.mIsBeingDragged = true;
                            } else if (this.mMode.showFooterLoadingLayout() && isReadyForPullEnd()) {
                                this.mLastMotionY = y;
                                this.mLastMotionX = x;
                                this.mIsBeingDragged = true;
                                if (this.mMode == Mode.BOTH) {
                                    this.mCurrentMode = Mode.PULL_FROM_END;
                                }
                            }
                        }
                    }
                }
            } else if (isReadyForPull()) {
                this.mInitScrollY = (float) getScrollY();
                float y2 = motionEvent.getY();
                this.mInitialMotionY = y2;
                this.mLastMotionY = y2;
                this.mLastMotionX = motionEvent.getX();
                this.mIsBeingDragged = false;
            }
            return this.mIsBeingDragged;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isPullToRefreshEnabled()) {
            return false;
        }
        if (motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                if (isReadyForPull()) {
                    this.mInitScrollY = (float) getScrollY();
                    float y = motionEvent.getY();
                    this.mInitialMotionY = y;
                    this.mLastMotionY = y;
                    return true;
                }
                break;
            case 1:
            case 3:
                if (this.mIsBeingDragged) {
                    this.mIsBeingDragged = false;
                    if (this.mState != State.RELEASE_TO_REFRESH || (this.mOnRefreshListener == null && this.mOnLoadMoreListener == null)) {
                        updateState(State.RESET, new boolean[0]);
                        return true;
                    }
                    updateState(State.REFRESHING, true);
                    return true;
                }
                break;
            case 2:
                if (this.mIsBeingDragged) {
                    this.mLastMotionY = motionEvent.getY();
                    this.mLastMotionX = motionEvent.getX();
                    pullEvent();
                    return true;
                }
                break;
        }
        return false;
    }

    public final void onRefreshComplete() {
        if (isRefreshing()) {
            updateState(State.RESET, new boolean[0]);
        }
    }

    public void setLongClickable(boolean z) {
        getRefreshableView().setLongClickable(z);
    }

    public final void setMode(Mode mode) {
        if (mode != this.mMode) {
            this.mMode = mode;
            updateUIForMode();
        }
    }

    public void setOnPullEventListener(OnPullEventListener<T> onPullEventListener) {
        this.mOnPullEventListener = onPullEventListener;
    }

    public final void setOnRefreshListener(OnRefreshListener<T> onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }

    public final void setOnLoadMoreListener(OnLoadMoreListener<T> onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public final void setRefreshing() {
        setRefreshing(true);
    }

    public final void setRefreshing(boolean z) {
        if (!isRefreshing()) {
            updateState(State.MANUAL_REFRESHING, z);
        }
    }

    public void setOnPullListener(OnPullListener onPullListener) {
        this.mOnPullListener = onPullListener;
    }

    public final void setState(State state, boolean z, boolean... zArr) {
        this.mState = state;
        switch (this.mState) {
            case RESET:
                onReset();
                break;
            case PULL_TO_REFRESH:
                onPullToRefresh();
                break;
            case RELEASE_TO_REFRESH:
                onReleaseToRefresh();
                break;
            case REFRESHING:
            case MANUAL_REFRESHING:
                onRefreshing(z, zArr[0]);
                break;
        }
        if (this.mOnPullEventListener != null) {
            this.mOnPullEventListener.onPullEvent(this, this.mState, this.mCurrentMode);
        }
    }

    private void updateState(State state, boolean... zArr) {
        setState(state, true, zArr);
    }

    /* access modifiers changed from: protected */
    public final void addViewInternal(View view, int i, LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
    }

    /* access modifiers changed from: protected */
    public final void addViewInternal(View view, LayoutParams layoutParams) {
        super.addView(view, -1, layoutParams);
    }

    public LoadingLayout createLoadingLayout(Context context, Mode mode) {
        FlipLoadingLayout flipLoadingLayout = new FlipLoadingLayout(context, mode);
        flipLoadingLayout.setVisibility(4);
        return flipLoadingLayout;
    }

    /* access modifiers changed from: protected */
    public final int getFooterSize() {
        if (this.mFooterLayout == null) {
            return 0;
        }
        return this.mFooterLayout.getContentSize();
    }

    /* access modifiers changed from: protected */
    public final int getHeaderSize() {
        if (this.mHeaderLayout == null) {
            return 0;
        }
        return this.mHeaderLayout.getContentSize();
    }

    /* access modifiers changed from: protected */
    public void onPullToRefresh() {
        switch (this.mCurrentMode) {
            case PULL_FROM_END:
                if (this.mFooterLayout != null) {
                    this.mFooterLayout.pullToRefresh();
                    return;
                }
                break;
            case PULL_FROM_START:
                if (this.mHeaderLayout != null) {
                    this.mHeaderLayout.pullToRefresh();
                    break;
                }
                break;
        }
    }

    /* access modifiers changed from: protected */
    public void onRefreshing(final boolean z, boolean z2) {
        if (this.mMode.showHeaderLoadingLayout() && this.mHeaderLayout != null) {
            this.mHeaderLayout.refreshing();
        }
        if (this.mMode.showFooterLoadingLayout() && this.mFooterLayout != null) {
            this.mFooterLayout.refreshing();
        }
        if (z2) {
            AnonymousClass1 r3 = new OnSmoothScrollFinishedListener() {
                public void onSmoothScrollFinished() {
                    PullToRefreshBase.this.callRefreshListener(z);
                }
            };
            int i = AnonymousClass3.$SwitchMap$com$autonavi$minimap$ajx3$widget$view$list$pulltorefresh$PullToRefreshBase$Mode[this.mCurrentMode.ordinal()];
            if (i == 1 || i == 3) {
                smoothScrollTo(getFooterSize(), (OnSmoothScrollFinishedListener) r3);
            } else {
                smoothScrollTo(-getHeaderSize(), (OnSmoothScrollFinishedListener) r3);
            }
        } else {
            callRefreshListener(z);
        }
    }

    /* access modifiers changed from: protected */
    public void onReleaseToRefresh() {
        switch (this.mCurrentMode) {
            case PULL_FROM_END:
                if (this.mFooterLayout != null) {
                    this.mFooterLayout.releaseToRefresh();
                    return;
                }
                break;
            case PULL_FROM_START:
                if (this.mHeaderLayout != null) {
                    this.mHeaderLayout.releaseToRefresh();
                    break;
                }
                break;
        }
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        this.mIsBeingDragged = false;
        this.mLayoutVisibilityChangesEnabled = true;
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.reset();
        }
        if (this.mFooterLayout != null) {
            this.mFooterLayout.reset();
        }
        smoothScrollTo(0);
    }

    /* access modifiers changed from: protected */
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            setMode(Mode.mapIntToValue(bundle.getInt(STATE_MODE, 0)));
            this.mCurrentMode = Mode.mapIntToValue(bundle.getInt(STATE_CURRENT_MODE, 0));
            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER));
            State mapIntToValue = State.mapIntToValue(bundle.getInt(STATE_STATE, 0));
            if (mapIntToValue == State.REFRESHING || mapIntToValue == State.MANUAL_REFRESHING) {
                updateState(mapIntToValue, true);
            }
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    /* access modifiers changed from: protected */
    public final Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(STATE_STATE, this.mState.getIntValue());
        bundle.putInt(STATE_MODE, this.mMode.getIntValue());
        bundle.putInt(STATE_CURRENT_MODE, this.mCurrentMode.getIntValue());
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        return bundle;
    }

    /* access modifiers changed from: protected */
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        refreshLoadingViewsSize();
        refreshRefreshableViewSize(i, i2);
        post(new Runnable() {
            public void run() {
                PullToRefreshBase.this.requestLayout();
            }
        });
    }

    /* access modifiers changed from: protected */
    public final void refreshLoadingViewsSize() {
        int i;
        int maximumPullScroll = (int) (((float) getMaximumPullScroll()) * 1.2f);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int i2 = 0;
        if (this.mMode.showHeaderLoadingLayout()) {
            if (this.mHeaderLayout != null) {
                this.mHeaderLayout.setHeight(maximumPullScroll);
            }
            i = -maximumPullScroll;
        } else {
            i = 0;
        }
        if (this.mMode.showFooterLoadingLayout()) {
            if (this.mFooterLayout != null) {
                this.mFooterLayout.setHeight(maximumPullScroll);
            }
            i2 = -maximumPullScroll;
        }
        setPadding(paddingLeft, i, paddingRight, i2);
    }

    /* access modifiers changed from: protected */
    public final void refreshRefreshableViewSize(int i, int i2) {
        View refreshableView = getRefreshableView();
        LayoutParams layoutParams = refreshableView.getLayoutParams();
        if (layoutParams.height != i2) {
            layoutParams.height = i2;
            refreshableView.requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    @TargetApi(11)
    public final void setHeaderScroll(int i) {
        int maximumPullScroll = getMaximumPullScroll();
        int min = Math.min(maximumPullScroll, Math.max(-maximumPullScroll, i));
        if (this.mLayoutVisibilityChangesEnabled) {
            if (min < 0) {
                if (this.mHeaderLayout != null) {
                    this.mHeaderLayout.setVisibility(0);
                }
            } else if (min <= 0) {
                if (this.mHeaderLayout != null) {
                    this.mHeaderLayout.setVisibility(4);
                }
                if (this.mFooterLayout != null) {
                    this.mFooterLayout.setVisibility(4);
                }
            } else if (this.mFooterLayout != null) {
                this.mFooterLayout.setVisibility(0);
            }
        }
        scrollTo(0, min);
        if (this.mOnPullListener != null) {
            this.mOnPullListener.onPull(min, 0);
        }
    }

    public void scrollTo(int i, int i2) {
        super.scrollTo(i, i2);
    }

    /* access modifiers changed from: protected */
    public final void smoothScrollTo(int i) {
        smoothScrollTo(i, (long) getPullToRefreshScrollDuration());
    }

    /* access modifiers changed from: protected */
    public final void smoothScrollTo(int i, OnSmoothScrollFinishedListener onSmoothScrollFinishedListener) {
        smoothScrollTo(i, (long) getPullToRefreshScrollDuration(), 0, onSmoothScrollFinishedListener);
    }

    /* access modifiers changed from: protected */
    public void updateUIForMode() {
        removeHeaderLayout();
        if (this.mMode.showHeaderLoadingLayout()) {
            initHeaderLayout();
        }
        removeFooterLayout();
        if (this.mMode.showFooterLoadingLayout()) {
            initFooterLayout();
        }
        refreshLoadingViewsSize();
        try {
            if (this.mMode == Mode.DISABLED) {
                smoothScrollTo(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mCurrentMode = this.mMode != Mode.BOTH ? this.mMode : Mode.PULL_FROM_START;
    }

    /* access modifiers changed from: protected */
    public void callRefreshListener(boolean z) {
        if (this.mOnRefreshListener != null && this.mCurrentMode == Mode.PULL_FROM_START) {
            this.mOnRefreshListener.onRefresh(this, z);
        }
        if (this.mOnLoadMoreListener != null && this.mCurrentMode == Mode.PULL_FROM_END) {
            this.mOnLoadMoreListener.onLoadMore(this, z);
        }
    }

    /* access modifiers changed from: protected */
    public void init(boolean z) {
        setOrientation(1);
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mRefreshableView = createRefreshableView();
        if (!z) {
            addViewInternal(this.mRefreshableView, new LinearLayout.LayoutParams(-1, -1));
        } else {
            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -1);
            layoutParams.addRule(11, -1);
            layoutParams.addRule(15, -1);
            relativeLayout.addView(this.mRefreshableView, new RelativeLayout.LayoutParams(-1, -1));
            addViewInternal(relativeLayout, new LinearLayout.LayoutParams(-1, -1));
        }
        updateUIForMode();
    }

    private boolean isReadyForPull() {
        int i = AnonymousClass3.$SwitchMap$com$autonavi$minimap$ajx3$widget$view$list$pulltorefresh$PullToRefreshBase$Mode[this.mMode.ordinal()];
        if (i != 4) {
            switch (i) {
                case 1:
                    return isReadyForPullEnd();
                case 2:
                    return isReadyForPullStart();
                default:
                    return false;
            }
        } else if (isReadyForPullEnd() || isReadyForPullStart()) {
            return true;
        } else {
            return false;
        }
    }

    private void pullEvent() {
        int i;
        int i2;
        float f = this.mInitialMotionY;
        float f2 = this.mLastMotionY;
        float f3 = this.mInitScrollY;
        if (AnonymousClass3.$SwitchMap$com$autonavi$minimap$ajx3$widget$view$list$pulltorefresh$PullToRefreshBase$Mode[this.mCurrentMode.ordinal()] != 1) {
            i2 = Math.round((f - f2) / FRICTION);
            i = getHeaderSize();
        } else {
            i2 = Math.round(Math.max(f - f2, 0.0f) / FRICTION);
            i = getFooterSize();
        }
        setHeaderScroll(Math.round(((float) i2) + f3));
        if (i2 != 0 && !isRefreshing()) {
            if (this.mState != State.PULL_TO_REFRESH && i >= Math.abs(i2)) {
                updateState(State.PULL_TO_REFRESH, new boolean[0]);
            } else if (this.mState == State.PULL_TO_REFRESH && i < Math.abs(i2)) {
                updateState(State.RELEASE_TO_REFRESH, new boolean[0]);
            }
        }
    }

    /* access modifiers changed from: protected */
    public LinearLayout.LayoutParams getLoadingLayoutLayoutParams() {
        return new LinearLayout.LayoutParams(-1, -2);
    }

    private int getMaximumPullScroll() {
        return Math.round(((float) this.screenH) / FRICTION);
    }

    private void smoothScrollTo(int i, long j) {
        smoothScrollTo(i, j, 0, null);
    }

    private void smoothScrollTo(int i, long j, long j2, OnSmoothScrollFinishedListener onSmoothScrollFinishedListener) {
        if (this.mCurrentSmoothScrollRunnable != null) {
            this.mCurrentSmoothScrollRunnable.stop();
        }
        int scrollY = getScrollY();
        if (scrollY != i) {
            SmoothScrollRunnable smoothScrollRunnable = new SmoothScrollRunnable(scrollY, i, j, onSmoothScrollFinishedListener);
            this.mCurrentSmoothScrollRunnable = smoothScrollRunnable;
            if (j2 > 0) {
                postDelayed(this.mCurrentSmoothScrollRunnable, j2);
                return;
            }
            post(this.mCurrentSmoothScrollRunnable);
        }
    }

    /* access modifiers changed from: protected */
    public void initHeaderLayout() {
        if (this.mHeaderLayout == null) {
            this.mHeaderLayout = createLoadingLayout(getContext(), Mode.PULL_FROM_START);
            if (this.headerPullText != null) {
                this.mHeaderLayout.setPullText(this.headerPullText);
            }
            if (this.headerRefresh != null) {
                this.mHeaderLayout.setRefreshingText(this.headerRefresh);
            }
            if (this.headerRelease != null) {
                this.mHeaderLayout.setRefreshingText(this.headerRelease);
            }
            if (this.headerArrowImage != null) {
                this.mHeaderLayout.setArrowImage(this.headerArrowImage);
            }
            if (this.headerBgImage != null) {
                this.mHeaderLayout.setBgImage(this.headerBgImage);
            }
        }
        addViewInternal(this.mHeaderLayout, 0, getLoadingLayoutLayoutParams());
    }

    /* access modifiers changed from: protected */
    public void initFooterLayout() {
        if (this.mFooterLayout == null) {
            this.mFooterLayout = createLoadingLayout(getContext(), Mode.PULL_FROM_END);
            if (this.footerPullText != null) {
                this.mFooterLayout.setPullText(this.footerPullText);
            }
            if (this.footerRefresh != null) {
                this.mFooterLayout.setRefreshingText(this.footerRefresh);
            }
            if (this.footerRelease != null) {
                this.mFooterLayout.setReleaseText(this.footerRelease);
            }
            if (this.footerArrowImage != null) {
                this.mFooterLayout.setArrowImage(this.footerArrowImage);
            }
            if (this.footerBgImage != null) {
                this.mFooterLayout.setBgImage(this.footerBgImage);
            }
        }
        addViewInternal(this.mFooterLayout, getLoadingLayoutLayoutParams());
    }

    /* access modifiers changed from: protected */
    public void removeHeaderLayout() {
        if (this.mHeaderLayout != null && this == this.mHeaderLayout.getParent()) {
            removeView(this.mHeaderLayout);
        }
    }

    /* access modifiers changed from: protected */
    public void removeFooterLayout() {
        if (this.mFooterLayout != null && this == this.mFooterLayout.getParent()) {
            removeView(this.mFooterLayout);
        }
    }
}
