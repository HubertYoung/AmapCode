package com.autonavi.widget.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.internal.FlipLoadingLayout;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;
import com.autonavi.widget.pulltorefresh.internal.RotateLoadingLayout;
import com.autonavi.widget.pulltorefresh.internal.UnifyLoadingViewLayout;

public abstract class PullToRefreshBase<T extends View> extends LinearLayout {
    static final int DEMO_SCROLL_INTERVAL = 225;
    static final float FRICTION = 3.0f;
    static final String LOG_TAG = "PullToRefreshAttrs";
    public static final int SMOOTH_SCROLL_DURATION_MS = 200;
    public static final int SMOOTH_SCROLL_LONG_DURATION_MS = 325;
    static final String STATE_CURRENT_MODE = "ptr_current_mode";
    static final String STATE_MODE = "ptr_mode";
    static final String STATE_SCROLLING_REFRESHING_ENABLED = "ptr_disable_scrolling";
    static final String STATE_SHOW_REFRESHING_VIEW = "ptr_show_refreshing_view";
    static final String STATE_STATE = "ptr_state";
    static final String STATE_SUPER = "ptr_super";
    static final boolean USE_HW_LAYERS = false;
    private boolean footershowflag = true;
    private Mode mCurrentMode;
    private f mCurrentSmoothScrollRunnable;
    private boolean mFilterTouchEvents = true;
    private LoadingLayout mFooterLayout;
    private LoadingLayout mFooterLayoutOther;
    private LoadingLayout mHeaderLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsBeingDragged = false;
    private float mLastMotionX;
    private float mLastMotionY;
    private boolean mLayoutVisibilityChangesEnabled = true;
    private AnimationStyle mLoadingAnimationStyle = AnimationStyle.a();
    private Mode mMode = Mode.a();
    private b<T> mOnPullEventListener;
    private c<T> mOnRefreshListener;
    private d<T> mOnRefreshListener2;
    private boolean mOverScrollEnabled = true;
    T mRefreshableView;
    private FrameLayout mRefreshableViewWrapper;
    /* access modifiers changed from: private */
    public Interpolator mScrollAnimationInterpolator;
    private boolean mScrollingWhileRefreshingEnabled = false;
    private boolean mShowViewWhileRefreshing = true;
    private State mState = State.RESET;
    private int mTouchSlop;

    public enum AnimationStyle {
        ROTATE,
        FLIP,
        CUSTOM,
        UNIFY;

        static AnimationStyle a() {
            return FLIP;
        }

        static AnimationStyle a(int i) {
            switch (i) {
                case 1:
                    return FLIP;
                case 2:
                    return CUSTOM;
                case 3:
                    return UNIFY;
                default:
                    return ROTATE;
            }
        }
    }

    public enum Mode {
        DISABLED(0),
        PULL_FROM_START(1),
        PULL_FROM_END(2),
        BOTH(3),
        MANUAL_REFRESH_ONLY(4);
        
        @Deprecated
        public static Mode PULL_DOWN_TO_REFRESH;
        @Deprecated
        public static Mode PULL_UP_TO_REFRESH;
        int a;

        static {
            PULL_DOWN_TO_REFRESH = PULL_FROM_START;
            PULL_UP_TO_REFRESH = PULL_FROM_END;
        }

        static Mode a(int i) {
            Mode[] values;
            for (Mode mode : values()) {
                if (i == mode.a) {
                    return mode;
                }
            }
            return PULL_FROM_START;
        }

        static Mode a() {
            return PULL_FROM_START;
        }

        private Mode(int i) {
            this.a = i;
        }

        public final boolean permitsPullToRefresh() {
            return (this == DISABLED || this == MANUAL_REFRESH_ONLY) ? false : true;
        }

        public final boolean showHeaderLoadingLayout() {
            return this == PULL_FROM_START || this == BOTH;
        }

        public final boolean showFooterLoadingLayout() {
            return this == PULL_FROM_END || this == BOTH || this == MANUAL_REFRESH_ONLY;
        }
    }

    public enum Orientation {
        VERTICAL,
        HORIZONTAL
    }

    public enum State {
        RESET(0),
        PULL_TO_REFRESH(1),
        RELEASE_TO_REFRESH(2),
        REFRESHING(8),
        MANUAL_REFRESHING(9),
        OVERSCROLLING(16);
        
        int a;

        static State a(int i) {
            State[] values;
            for (State state : values()) {
                if (i == state.a) {
                    return state;
                }
            }
            return RESET;
        }

        private State(int i) {
            this.a = i;
        }
    }

    public interface a {
    }

    public interface b<V extends View> {
        void a(State state);
    }

    public interface c<V extends View> {
        void a(PullToRefreshBase<V> pullToRefreshBase);
    }

    public interface d<V extends View> {
        void onPullDownToRefresh(PullToRefreshBase<V> pullToRefreshBase);

        void onPullUpToRefresh(PullToRefreshBase<V> pullToRefreshBase);
    }

    interface e {
        void a();
    }

    final class f implements Runnable {
        boolean a = true;
        private final Interpolator c;
        private final int d;
        private final int e;
        private final long f;
        private final e g;
        private long h = -1;
        private int i = -1;

        public f(int i2, int i3, long j, e eVar) {
            this.e = i2;
            this.d = i3;
            this.c = PullToRefreshBase.this.mScrollAnimationInterpolator;
            this.f = j;
            this.g = eVar;
        }

        public final void run() {
            if (this.h == -1) {
                this.h = System.currentTimeMillis();
            } else {
                this.i = this.e - Math.round(((float) (this.e - this.d)) * this.c.getInterpolation(((float) Math.max(Math.min(((System.currentTimeMillis() - this.h) * 1000) / this.f, 1000), 0)) / 1000.0f));
                PullToRefreshBase.this.setHeaderScroll(this.i);
            }
            if (!this.a || this.d == this.i) {
                if (this.g != null) {
                    this.g.a();
                }
                return;
            }
            PullToRefreshBase pullToRefreshBase = PullToRefreshBase.this;
            if (VERSION.SDK_INT >= 16) {
                pullToRefreshBase.postOnAnimation(this);
            } else {
                pullToRefreshBase.postDelayed(this, 16);
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract T createRefreshableView(Context context, AttributeSet attributeSet);

    public abstract Orientation getPullToRefreshScrollDirection();

    /* access modifiers changed from: protected */
    public int getPullToRefreshScrollDuration() {
        return 200;
    }

    /* access modifiers changed from: protected */
    public int getPullToRefreshScrollDurationLonger() {
        return SMOOTH_SCROLL_LONG_DURATION_MS;
    }

    /* access modifiers changed from: protected */
    public void handleStyledAttributes(TypedArray typedArray) {
    }

    /* access modifiers changed from: protected */
    public abstract boolean isReadyForPullEnd();

    /* access modifiers changed from: protected */
    public abstract boolean isReadyForPullStart();

    /* access modifiers changed from: protected */
    public void onPtrRestoreInstanceState(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onPtrSaveInstanceState(Bundle bundle) {
    }

    public void unRegistAllListener() {
        this.mOnRefreshListener = null;
        this.mOnRefreshListener2 = null;
        this.mOnPullEventListener = null;
    }

    public void setHeaderText(String str, String str2, String str3) {
        this.mHeaderLayout.setPullLabel(str);
        this.mHeaderLayout.setReleaseLabel(str2);
        this.mHeaderLayout.setRefreshingLabel(str3);
        this.mHeaderLayout.reset();
    }

    public void setFooterText(String str, String str2, String str3) {
        this.mFooterLayoutOther.setPullLabel(str);
        this.mFooterLayoutOther.setReleaseLabel(str2);
        this.mFooterLayoutOther.setRefreshingLabel(str3);
        this.mFooterLayoutOther.reset();
    }

    public void setRealFooterText(String str, String str2, String str3) {
        this.mFooterLayout.setPullLabel(str);
        this.mFooterLayout.setReleaseLabel(str2);
        this.mFooterLayout.setRefreshingLabel(str3);
        this.mFooterLayout.reset();
    }

    public void hideImageHead() {
        this.mHeaderLayout.hideImageHead();
    }

    public void showImageHead() {
        this.mHeaderLayout.showImageHead();
    }

    public void hideImageFoot() {
        this.mFooterLayoutOther.hideImageHead();
    }

    public void showImageFoot() {
        this.mFooterLayoutOther.showImageHead();
    }

    public void hideFoot() {
        this.mFooterLayout.hideAllViews();
    }

    public LoadingLayout changeFooter() {
        return this.mFooterLayoutOther;
    }

    public PullToRefreshBase(Context context) {
        super(context);
        init(context, null);
    }

    public PullToRefreshBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public PullToRefreshBase(Context context, Mode mode) {
        super(context);
        this.mMode = mode;
        init(context, null);
    }

    public PullToRefreshBase(Context context, Mode mode, AnimationStyle animationStyle) {
        super(context);
        this.mMode = mode;
        this.mLoadingAnimationStyle = animationStyle;
        init(context, null);
    }

    public void addView(View view, int i, LayoutParams layoutParams) {
        View refreshableView = getRefreshableView();
        if (refreshableView instanceof ViewGroup) {
            ((ViewGroup) refreshableView).addView(view, i, layoutParams);
            return;
        }
        throw new UnsupportedOperationException("Refreshable View is not a ViewGroup so can't addView");
    }

    public final boolean demo() {
        if (this.mMode.showHeaderLoadingLayout() && isReadyForPullStart()) {
            smoothScrollToAndBack((-getHeaderSize()) * 2);
            return true;
        } else if (!this.mMode.showFooterLoadingLayout() || !isReadyForPullEnd()) {
            return false;
        } else {
            smoothScrollToAndBack(getFooterSize() * 2);
            return true;
        }
    }

    public final Mode getCurrentMode() {
        return this.mCurrentMode;
    }

    public final boolean getFilterTouchEvents() {
        return this.mFilterTouchEvents;
    }

    public final ere getLoadingLayoutProxy() {
        return getLoadingLayoutProxy(true, true);
    }

    public final ere getLoadingLayoutProxy(boolean z, boolean z2) {
        return createLoadingLayoutProxy(z, z2);
    }

    public final Mode getMode() {
        return this.mMode;
    }

    public final T getRefreshableView() {
        return this.mRefreshableView;
    }

    public final boolean getShowViewWhileRefreshing() {
        return this.mShowViewWhileRefreshing;
    }

    public void setFootershowflag(boolean z) {
        this.footershowflag = z;
    }

    public boolean isFootershowflag() {
        return this.footershowflag;
    }

    public final State getState() {
        return this.mState;
    }

    @Deprecated
    public final boolean isDisableScrollingWhileRefreshing() {
        return !isScrollingWhileRefreshingEnabled();
    }

    public final boolean isPullToRefreshEnabled() {
        return this.mMode.permitsPullToRefresh();
    }

    public final boolean isPullToRefreshOverScrollEnabled() {
        if (VERSION.SDK_INT >= 9 && this.mOverScrollEnabled) {
            if (this.mRefreshableView.getOverScrollMode() != 2) {
                return true;
            }
        }
        return false;
    }

    public final boolean isRefreshing() {
        return this.mState == State.REFRESHING || this.mState == State.MANUAL_REFRESHING;
    }

    public final boolean isScrollingWhileRefreshingEnabled() {
        return this.mScrollingWhileRefreshingEnabled;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        float f2;
        float f3;
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
                if (action == 2) {
                    if (!this.mScrollingWhileRefreshingEnabled && isRefreshing()) {
                        return true;
                    }
                    if (isReadyForPull()) {
                        float y = motionEvent.getY();
                        float x = motionEvent.getX();
                        if (AnonymousClass4.a[getPullToRefreshScrollDirection().ordinal()] != 1) {
                            f3 = y - this.mLastMotionY;
                            f2 = x - this.mLastMotionX;
                        } else {
                            f3 = x - this.mLastMotionX;
                            f2 = y - this.mLastMotionY;
                        }
                        float abs = Math.abs(f3);
                        if (abs > ((float) this.mTouchSlop) && (!this.mFilterTouchEvents || abs > Math.abs(f2))) {
                            if (this.mMode.showHeaderLoadingLayout() && f3 >= 1.0f && isReadyForPullStart()) {
                                this.mLastMotionY = y;
                                this.mLastMotionX = x;
                                this.mIsBeingDragged = true;
                                if (this.mMode == Mode.BOTH) {
                                    this.mCurrentMode = Mode.PULL_FROM_START;
                                }
                            } else if (this.mMode.showFooterLoadingLayout() && f3 <= -1.0f && isReadyForPullEnd()) {
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
                float y2 = motionEvent.getY();
                this.mInitialMotionY = y2;
                this.mLastMotionY = y2;
                float x2 = motionEvent.getX();
                this.mInitialMotionX = x2;
                this.mLastMotionX = x2;
                this.mIsBeingDragged = false;
            }
            return this.mIsBeingDragged;
        }
    }

    public final void onRefreshComplete() {
        if (isRefreshing()) {
            setState(State.RESET, new boolean[0]);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isPullToRefreshEnabled()) {
            return false;
        }
        if (!this.mScrollingWhileRefreshingEnabled && isRefreshing()) {
            return true;
        }
        if (motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                if (isReadyForPull()) {
                    float y = motionEvent.getY();
                    this.mInitialMotionY = y;
                    this.mLastMotionY = y;
                    float x = motionEvent.getX();
                    this.mInitialMotionX = x;
                    this.mLastMotionX = x;
                    return true;
                }
                break;
            case 1:
            case 3:
                if (this.mIsBeingDragged) {
                    this.mIsBeingDragged = false;
                    if (this.mState == State.RELEASE_TO_REFRESH && (this.mOnRefreshListener != null || this.mOnRefreshListener2 != null)) {
                        setState(State.REFRESHING, true);
                        return true;
                    } else if (isRefreshing()) {
                        smoothScrollTo(0);
                        return true;
                    } else {
                        setState(State.RESET, new boolean[0]);
                        return true;
                    }
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

    public final void setScrollingWhileRefreshingEnabled(boolean z) {
        this.mScrollingWhileRefreshingEnabled = z;
    }

    @Deprecated
    public void setDisableScrollingWhileRefreshing(boolean z) {
        setScrollingWhileRefreshingEnabled(!z);
    }

    public final void setFilterTouchEvents(boolean z) {
        this.mFilterTouchEvents = z;
    }

    @Deprecated
    public void setLastUpdatedLabel(CharSequence charSequence) {
        getLoadingLayoutProxy().setLastUpdatedLabel(charSequence);
    }

    @Deprecated
    public void setLoadingDrawable(Drawable drawable) {
        getLoadingLayoutProxy().setLoadingDrawable(drawable);
    }

    @Deprecated
    public void setLoadingDrawable(Drawable drawable, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setLoadingDrawable(drawable);
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

    public void setOnPullEventListener(b<T> bVar) {
        this.mOnPullEventListener = bVar;
    }

    public final void setOnRefreshListener(c<T> cVar) {
        this.mOnRefreshListener = cVar;
        this.mOnRefreshListener2 = null;
    }

    public final void setOnRefreshListener(d<T> dVar) {
        this.mOnRefreshListener2 = dVar;
        this.mOnRefreshListener = null;
    }

    @Deprecated
    public void setPullLabel(CharSequence charSequence) {
        getLoadingLayoutProxy().setPullLabel(charSequence);
    }

    @Deprecated
    public void setPullLabel(CharSequence charSequence, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setPullLabel(charSequence);
    }

    @Deprecated
    public final void setPullToRefreshEnabled(boolean z) {
        setMode(z ? Mode.a() : Mode.DISABLED);
    }

    public final void setPullToRefreshOverScrollEnabled(boolean z) {
        this.mOverScrollEnabled = z;
    }

    public final void setRefreshing() {
        setRefreshing(true);
    }

    public final void setRefreshing(boolean z) {
        if (!isRefreshing()) {
            setState(State.MANUAL_REFRESHING, z);
        }
    }

    @Deprecated
    public void setRefreshingLabel(CharSequence charSequence) {
        getLoadingLayoutProxy().setRefreshingLabel(charSequence);
    }

    @Deprecated
    public void setRefreshingLabel(CharSequence charSequence, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setRefreshingLabel(charSequence);
    }

    @Deprecated
    public void setReleaseLabel(CharSequence charSequence) {
        setReleaseLabel(charSequence, Mode.BOTH);
    }

    @Deprecated
    public void setReleaseLabel(CharSequence charSequence, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setReleaseLabel(charSequence);
    }

    public void setScrollAnimationInterpolator(Interpolator interpolator) {
        this.mScrollAnimationInterpolator = interpolator;
    }

    public final void setShowViewWhileRefreshing(boolean z) {
        this.mShowViewWhileRefreshing = z;
    }

    public final void setState(State state, boolean... zArr) {
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
                onRefreshing(zArr[0]);
                break;
        }
        if (this.mOnPullEventListener != null) {
            this.mOnPullEventListener.a(this.mState);
        }
    }

    /* access modifiers changed from: protected */
    public final void addViewInternal(View view, int i, LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
    }

    /* access modifiers changed from: protected */
    public final void addViewInternal(View view, LayoutParams layoutParams) {
        super.addView(view, -1, layoutParams);
    }

    public LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray typedArray) {
        LoadingLayout loadingLayout;
        AnimationStyle animationStyle = this.mLoadingAnimationStyle;
        Orientation pullToRefreshScrollDirection = getPullToRefreshScrollDirection();
        switch (animationStyle) {
            case FLIP:
                loadingLayout = new FlipLoadingLayout(context, mode, pullToRefreshScrollDirection, typedArray);
                break;
            case CUSTOM:
                loadingLayout = null;
                if (this instanceof erd) {
                    loadingLayout = ((erd) this).a();
                }
                if (loadingLayout == null) {
                    loadingLayout = new RotateLoadingLayout(context, mode, pullToRefreshScrollDirection, typedArray);
                    break;
                }
                break;
            case UNIFY:
                loadingLayout = new UnifyLoadingViewLayout(context, mode, pullToRefreshScrollDirection, typedArray);
                break;
            default:
                loadingLayout = new RotateLoadingLayout(context, mode, pullToRefreshScrollDirection, typedArray);
                break;
        }
        loadingLayout.setVisibility(4);
        return loadingLayout;
    }

    /* access modifiers changed from: protected */
    public erh createLoadingLayoutProxy(boolean z, boolean z2) {
        erh erh = new erh();
        if (z && this.mMode.showHeaderLoadingLayout()) {
            erh.a(this.mHeaderLayout);
        }
        if (z2 && this.mMode.showFooterLoadingLayout()) {
            erh.a(this.mFooterLayout);
        }
        return erh;
    }

    /* access modifiers changed from: protected */
    public final void disableLoadingLayoutVisibilityChanges() {
        this.mLayoutVisibilityChangesEnabled = false;
    }

    public final LoadingLayout getFooterLayout() {
        return this.mFooterLayout;
    }

    public final void setFooterHeight(int i, int i2) {
        this.mFooterLayout.setHeaderHeight(i, i2);
    }

    /* access modifiers changed from: protected */
    public final int getFooterSize() {
        return this.mFooterLayout.getContentSize();
    }

    /* access modifiers changed from: protected */
    public final LoadingLayout getHeaderLayout() {
        return this.mHeaderLayout;
    }

    /* access modifiers changed from: protected */
    public final int getHeaderSize() {
        return this.mHeaderLayout.getContentSize();
    }

    /* access modifiers changed from: protected */
    public FrameLayout getRefreshableViewWrapper() {
        return this.mRefreshableViewWrapper;
    }

    /* access modifiers changed from: protected */
    public void onPullToRefresh() {
        switch (this.mCurrentMode) {
            case PULL_FROM_END:
                this.mFooterLayout.pullToRefresh();
                this.mFooterLayoutOther.pullToRefresh();
                return;
            case PULL_FROM_START:
                this.mHeaderLayout.pullToRefresh();
                break;
        }
    }

    /* access modifiers changed from: protected */
    public void onRefreshing(boolean z) {
        if (this.mCurrentMode.showHeaderLoadingLayout()) {
            this.mHeaderLayout.refreshing();
        }
        if (this.mCurrentMode.showFooterLoadingLayout()) {
            this.mFooterLayout.refreshing();
            this.mFooterLayoutOther.refreshing();
        }
        if (!z) {
            callRefreshListener();
        } else if (this.mShowViewWhileRefreshing) {
            AnonymousClass1 r3 = new e() {
                public final void a() {
                    PullToRefreshBase.this.callRefreshListener();
                }
            };
            int i = AnonymousClass4.c[this.mCurrentMode.ordinal()];
            if (i == 1 || i == 3) {
                smoothScrollTo(getFooterSize(), (e) r3);
            } else {
                smoothScrollTo(-getHeaderSize(), (e) r3);
            }
        } else {
            smoothScrollTo(0);
        }
    }

    /* access modifiers changed from: protected */
    public void onReleaseToRefresh() {
        switch (this.mCurrentMode) {
            case PULL_FROM_END:
                this.mFooterLayout.releaseToRefresh();
                this.mFooterLayoutOther.releaseToRefresh();
                return;
            case PULL_FROM_START:
                this.mHeaderLayout.releaseToRefresh();
                break;
        }
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        this.mIsBeingDragged = false;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mHeaderLayout.reset();
        this.mFooterLayout.reset();
        this.mFooterLayoutOther.reset();
        smoothScrollTo(0);
    }

    /* access modifiers changed from: protected */
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            setMode(Mode.a(bundle.getInt(STATE_MODE, 0)));
            this.mCurrentMode = Mode.a(bundle.getInt(STATE_CURRENT_MODE, 0));
            this.mScrollingWhileRefreshingEnabled = bundle.getBoolean(STATE_SCROLLING_REFRESHING_ENABLED, false);
            this.mShowViewWhileRefreshing = bundle.getBoolean(STATE_SHOW_REFRESHING_VIEW, true);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER));
            State a2 = State.a(bundle.getInt(STATE_STATE, 0));
            if (a2 == State.REFRESHING || a2 == State.MANUAL_REFRESHING) {
                setState(a2, true);
            }
            onPtrRestoreInstanceState(bundle);
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    /* access modifiers changed from: protected */
    public final Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        onPtrSaveInstanceState(bundle);
        bundle.putInt(STATE_STATE, this.mState.a);
        bundle.putInt(STATE_MODE, this.mMode.a);
        bundle.putInt(STATE_CURRENT_MODE, this.mCurrentMode.a);
        bundle.putBoolean(STATE_SCROLLING_REFRESHING_ENABLED, this.mScrollingWhileRefreshingEnabled);
        bundle.putBoolean(STATE_SHOW_REFRESHING_VIEW, this.mShowViewWhileRefreshing);
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        return bundle;
    }

    /* access modifiers changed from: protected */
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        refreshLoadingViewsSize();
        refreshRefreshableViewSize(i, i2);
        post(new Runnable() {
            public final void run() {
                PullToRefreshBase.this.requestLayout();
            }
        });
    }

    /* access modifiers changed from: protected */
    public final void refreshLoadingViewsSize() {
        int maximumPullScroll = (int) (((float) getMaximumPullScroll()) * 1.2f);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        switch (getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                if (this.mMode.showHeaderLoadingLayout()) {
                    this.mHeaderLayout.setWidth(maximumPullScroll);
                    paddingLeft = -maximumPullScroll;
                } else {
                    paddingLeft = 0;
                }
                if (!this.mMode.showFooterLoadingLayout()) {
                    paddingRight = 0;
                    break;
                } else {
                    this.mFooterLayout.setWidth(maximumPullScroll);
                    paddingRight = -maximumPullScroll;
                    break;
                }
            case VERTICAL:
                if (this.mMode.showHeaderLoadingLayout()) {
                    this.mHeaderLayout.setHeight(maximumPullScroll);
                    paddingTop = -maximumPullScroll;
                } else {
                    paddingTop = 0;
                }
                if (!this.mMode.showFooterLoadingLayout()) {
                    paddingBottom = 0;
                    break;
                } else {
                    this.mFooterLayout.setHeight(maximumPullScroll);
                    paddingBottom = -maximumPullScroll;
                    break;
                }
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    /* access modifiers changed from: protected */
    public final void refreshRefreshableViewSize(int i, int i2) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mRefreshableViewWrapper.getLayoutParams();
        switch (getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                if (layoutParams.width != i) {
                    layoutParams.width = i;
                    this.mRefreshableViewWrapper.requestLayout();
                    return;
                }
                break;
            case VERTICAL:
                if (layoutParams.height != i2) {
                    layoutParams.height = i2;
                    this.mRefreshableViewWrapper.requestLayout();
                    break;
                }
                break;
        }
    }

    @TargetApi(11)
    public final void setHeaderScroll(int i) {
        int maximumPullScroll = getMaximumPullScroll();
        int min = Math.min(maximumPullScroll, Math.max(-maximumPullScroll, i));
        if (this.mLayoutVisibilityChangesEnabled) {
            if (min < 0) {
                this.mHeaderLayout.setVisibility(0);
            } else if (min <= 0) {
                this.mHeaderLayout.setVisibility(4);
                this.mFooterLayout.setVisibility(4);
            } else if (this.footershowflag) {
                this.mFooterLayout.setVisibility(0);
            } else {
                this.mFooterLayout.setVisibility(4);
            }
        }
        switch (getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                scrollTo(min, 0);
                break;
            case VERTICAL:
                scrollTo(0, min);
                return;
        }
    }

    @TargetApi(11)
    public void setLayerType(int i) {
        FrameLayout frameLayout = this.mRefreshableViewWrapper;
        int i2 = i != 0 ? 2 : 0;
        if (VERSION.SDK_INT >= 11) {
            frameLayout.setLayerType(i2, null);
        }
    }

    /* access modifiers changed from: protected */
    public final void smoothScrollTo(int i) {
        smoothScrollTo(i, (long) getPullToRefreshScrollDuration());
    }

    /* access modifiers changed from: protected */
    public final void smoothScrollTo(int i, e eVar) {
        smoothScrollTo(i, (long) getPullToRefreshScrollDuration(), 0, eVar);
    }

    /* access modifiers changed from: protected */
    public final void smoothScrollToLonger(int i) {
        smoothScrollTo(i, (long) getPullToRefreshScrollDurationLonger());
    }

    /* access modifiers changed from: protected */
    public void updateUIForMode() {
        LinearLayout.LayoutParams loadingLayoutLayoutParams = getLoadingLayoutLayoutParams();
        if (this == this.mHeaderLayout.getParent()) {
            removeView(this.mHeaderLayout);
        }
        if (this.mMode.showHeaderLoadingLayout()) {
            addViewInternal(this.mHeaderLayout, 0, loadingLayoutLayoutParams);
        }
        if (this == this.mFooterLayout.getParent()) {
            removeView(this.mFooterLayout);
        }
        if (this.mMode.showFooterLoadingLayout()) {
            addViewInternal(this.mFooterLayout, loadingLayoutLayoutParams);
        }
        refreshLoadingViewsSize();
        this.mCurrentMode = this.mMode != Mode.BOTH ? this.mMode : Mode.PULL_FROM_START;
    }

    private void addRefreshableView(Context context, T t) {
        this.mRefreshableViewWrapper = new RefreshViewFrame(context);
        this.mRefreshableViewWrapper.addView(t, -1, -1);
        addViewInternal(this.mRefreshableViewWrapper, new LinearLayout.LayoutParams(-1, -1));
    }

    /* access modifiers changed from: private */
    public void callRefreshListener() {
        if (this.mOnRefreshListener != null) {
            this.mOnRefreshListener.a(this);
            return;
        }
        if (this.mOnRefreshListener2 != null) {
            if (this.mCurrentMode == Mode.PULL_FROM_START) {
                this.mOnRefreshListener2.onPullDownToRefresh(this);
            } else if (this.mCurrentMode == Mode.PULL_FROM_END) {
                this.mOnRefreshListener2.onPullUpToRefresh(this);
            }
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (AnonymousClass4.a[getPullToRefreshScrollDirection().ordinal()] != 1) {
            setOrientation(1);
        } else {
            setOrientation(0);
        }
        setGravity(17);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PullToRefreshAttrs);
        if (obtainStyledAttributes.hasValue(R.styleable.PullToRefreshAttrs_mPtrMode)) {
            this.mMode = Mode.a(obtainStyledAttributes.getInteger(R.styleable.PullToRefreshAttrs_mPtrMode, 0));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.PullToRefreshAttrs_mPtrAnimationStyle)) {
            this.mLoadingAnimationStyle = AnimationStyle.a(obtainStyledAttributes.getInteger(R.styleable.PullToRefreshAttrs_mPtrAnimationStyle, 0));
        }
        this.mRefreshableView = createRefreshableView(context, attributeSet);
        addRefreshableView(context, this.mRefreshableView);
        this.mHeaderLayout = createLoadingLayout(context, Mode.PULL_FROM_START, obtainStyledAttributes);
        this.mFooterLayout = createLoadingLayout(context, Mode.PULL_FROM_END, obtainStyledAttributes);
        this.mFooterLayoutOther = createLoadingLayout(context, Mode.PULL_FROM_END, obtainStyledAttributes);
        if (obtainStyledAttributes.hasValue(R.styleable.PullToRefreshAttrs_mPtrRefreshableViewBackground)) {
            Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.PullToRefreshAttrs_mPtrRefreshableViewBackground);
            if (drawable != null) {
                this.mRefreshableView.setBackgroundDrawable(drawable);
            }
        } else if (obtainStyledAttributes.hasValue(R.styleable.PullToRefreshAttrs_mPtrAdapterViewBackground)) {
            Drawable drawable2 = obtainStyledAttributes.getDrawable(R.styleable.PullToRefreshAttrs_mPtrAdapterViewBackground);
            if (drawable2 != null) {
                this.mRefreshableView.setBackgroundDrawable(drawable2);
            }
        }
        if (obtainStyledAttributes.hasValue(R.styleable.PullToRefreshAttrs_mPtrOverScroll)) {
            this.mOverScrollEnabled = obtainStyledAttributes.getBoolean(R.styleable.PullToRefreshAttrs_mPtrOverScroll, true);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.PullToRefreshAttrs_mPtrScrollingWhileRefreshingEnabled)) {
            this.mScrollingWhileRefreshingEnabled = obtainStyledAttributes.getBoolean(R.styleable.PullToRefreshAttrs_mPtrScrollingWhileRefreshingEnabled, false);
        }
        handleStyledAttributes(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        updateUIForMode();
    }

    private boolean isReadyForPull() {
        int i = AnonymousClass4.c[this.mMode.ordinal()];
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
        float f2;
        float f3;
        int i;
        int i2;
        if (AnonymousClass4.a[getPullToRefreshScrollDirection().ordinal()] != 1) {
            f3 = this.mInitialMotionY;
            f2 = this.mLastMotionY;
        } else {
            f3 = this.mInitialMotionX;
            f2 = this.mLastMotionX;
        }
        if (AnonymousClass4.c[this.mCurrentMode.ordinal()] != 1) {
            i2 = Math.round(Math.min(f3 - f2, 0.0f) / FRICTION);
            i = getHeaderSize();
        } else {
            i2 = Math.round(Math.max(f3 - f2, 0.0f) / FRICTION);
            i = getFooterSize();
        }
        setHeaderScroll(i2);
        if (i2 != 0 && !isRefreshing()) {
            float f4 = (float) i;
            float abs = ((float) Math.abs(i2)) / f4;
            if (AnonymousClass4.c[this.mCurrentMode.ordinal()] != 1) {
                this.mHeaderLayout.onPull(abs);
            } else {
                this.mFooterLayout.onPull(abs);
                f4 = (float) (i / 2);
            }
            if (this.mState != State.PULL_TO_REFRESH && f4 >= ((float) Math.abs(i2))) {
                setState(State.PULL_TO_REFRESH, new boolean[0]);
            } else if (this.mState == State.PULL_TO_REFRESH && f4 < ((float) Math.abs(i2))) {
                setState(State.RELEASE_TO_REFRESH, new boolean[0]);
            }
            if (this.mState == State.RESET && f4 < ((float) Math.abs(i2)) && !this.mFooterLayout.pulltorefreshflag) {
                setState(State.PULL_TO_REFRESH, new boolean[0]);
            }
        }
    }

    private LinearLayout.LayoutParams getLoadingLayoutLayoutParams() {
        return AnonymousClass4.a[getPullToRefreshScrollDirection().ordinal()] != 1 ? new LinearLayout.LayoutParams(-1, -2) : new LinearLayout.LayoutParams(-2, -1);
    }

    private int getMaximumPullScroll() {
        if (AnonymousClass4.a[getPullToRefreshScrollDirection().ordinal()] != 1) {
            return Math.round(((float) getHeight()) / FRICTION);
        }
        return Math.round(((float) getWidth()) / FRICTION);
    }

    private final void smoothScrollTo(int i, long j) {
        smoothScrollTo(i, j, 0, null);
    }

    /* access modifiers changed from: private */
    public final void smoothScrollTo(int i, long j, long j2, e eVar) {
        int scrollX;
        if (this.mCurrentSmoothScrollRunnable != null) {
            f fVar = this.mCurrentSmoothScrollRunnable;
            fVar.a = false;
            PullToRefreshBase.this.removeCallbacks(fVar);
        }
        if (AnonymousClass4.a[getPullToRefreshScrollDirection().ordinal()] != 1) {
            scrollX = getScrollY();
        } else {
            scrollX = getScrollX();
        }
        int i2 = scrollX;
        if (i2 != i) {
            if (this.mScrollAnimationInterpolator == null) {
                this.mScrollAnimationInterpolator = new DecelerateInterpolator();
            }
            f fVar2 = new f(i2, i, j, eVar);
            this.mCurrentSmoothScrollRunnable = fVar2;
            if (j2 > 0) {
                postDelayed(this.mCurrentSmoothScrollRunnable, j2);
                return;
            }
            post(this.mCurrentSmoothScrollRunnable);
        }
    }

    private final void smoothScrollToAndBack(int i) {
        smoothScrollTo(i, 200, 0, new e() {
            public final void a() {
                PullToRefreshBase.this.smoothScrollTo(0, 200, 225, null);
            }
        });
    }

    public void setHeaderTextTextSize(float f2) {
        this.mHeaderLayout.setHeaderTextTextSize(f2);
        this.mFooterLayout.setHeaderTextTextSize(f2);
    }

    public void setHeaderTextTextColor(int i) {
        this.mHeaderLayout.setHeaderTextTextColor(i);
        this.mFooterLayout.setHeaderTextTextColor(i);
    }

    public void setHeaderTextTextStyle(int i) {
        this.mHeaderLayout.setHeaderTextTextStyle(i);
        this.mFooterLayout.setHeaderTextTextStyle(i);
    }

    public void setInnerLayoutPadTop(int i) {
        this.mHeaderLayout.setInnerLayoutPadTop(i);
        this.mFooterLayout.setInnerLayoutPadTop(i);
    }
}
