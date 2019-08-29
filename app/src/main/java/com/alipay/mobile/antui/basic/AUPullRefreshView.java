package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import com.alipay.mobile.antui.utils.AuiLogger;
import java.util.ArrayList;

public class AUPullRefreshView extends AUFrameLayout implements OnGestureListener {
    private static final byte STATE_CLOSE = 0;
    private static final byte STATE_OPEN = 1;
    private static final byte STATE_OPEN_RELEASE = 3;
    private static final byte STATE_OVER = 2;
    private static final byte STATE_OVER_RELEASE = 4;
    private static final byte STATE_REFRESH = 5;
    private static final byte STATE_REFRESH_RELEASE = 6;
    private LoadingHeightChangeListener heightChangeListener;
    private boolean mEnablePull = true;
    private ai mFlinger;
    private GestureDetector mGestureDetector;
    private int mLastY;
    protected int mMaxMagin;
    protected AUPullLoadingView mOverView;
    private RefreshListener mRefreshListener;
    private byte mState;

    public interface LoadingHeightChangeListener {
        void onChangeHeight(int i, boolean z);
    }

    public interface RefreshListener {
        boolean canRefresh();

        AUPullLoadingView getOverView();

        void onRefresh();
    }

    public AUPullRefreshView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AUPullRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AUPullRefreshView(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.mGestureDetector = new GestureDetector(this);
        this.mFlinger = new ai(this);
    }

    private void initListener() {
        setClipChildren(false);
        this.mOverView = this.mRefreshListener.getOverView();
        addView((View) this.mOverView, 0, (LayoutParams) new FrameLayout.LayoutParams(-1, -2));
        getViewTreeObserver().addOnGlobalLayoutListener(new ag(this));
    }

    public boolean onDown(MotionEvent evn) {
        return false;
    }

    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        return false;
    }

    public void onLongPress(MotionEvent arg0) {
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float disX, float disY) {
        if (Math.abs(disX) > Math.abs(disY)) {
            return false;
        }
        if (this.mRefreshListener != null && !this.mRefreshListener.canRefresh()) {
            return false;
        }
        View head = getChildAt(0);
        View child = getChildAt(1);
        if (child instanceof AdapterView) {
            if (((AdapterView) child).getFirstVisiblePosition() != 0) {
                return false;
            }
            if (((AdapterView) child).getFirstVisiblePosition() == 0 && ((AdapterView) child).getChildAt(0) != null && ((AdapterView) child).getChildAt(0).getTop() < 0) {
                return false;
            }
        } else if (child.getScrollY() > 0) {
            return false;
        }
        if (this.mState == 5 && head.getTop() > 0 && disY > 0.0f) {
            return false;
        }
        if ((child.getTop() <= 0 && disY > 0.0f) || this.mState == 3 || this.mState == 4 || this.mState == 6) {
            return false;
        }
        int speed = this.mLastY;
        if (head.getTop() >= 0) {
            speed = this.mLastY / 2;
        }
        boolean moveDown = moveDown(speed, true);
        this.mLastY = (int) (-disY);
        return moveDown;
    }

    private void release(int dis) {
        if (this.mRefreshListener == null || dis <= this.mMaxMagin) {
            this.mState = 3;
            this.mFlinger.a(dis);
            return;
        }
        this.mState = 4;
        this.mFlinger.a(dis - this.mMaxMagin);
    }

    public void onShowPress(MotionEvent arg0) {
    }

    public boolean onSingleTapUp(MotionEvent arg0) {
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!this.mEnablePull) {
            return super.dispatchTouchEvent(ev);
        }
        if (!this.mFlinger.a()) {
            return false;
        }
        View head = getChildAt(0);
        if ((ev.getAction() == 1 || ev.getAction() == 3 || ev.getAction() == 262 || ev.getAction() == 518) && head.getBottom() > 0) {
            if (this.mState == 5 && head.getBottom() > this.mMaxMagin) {
                release(head.getBottom() - this.mMaxMagin);
                return false;
            } else if (this.mState != 5) {
                release(head.getBottom());
                return false;
            }
        }
        boolean bool = this.mGestureDetector.onTouchEvent(ev);
        if ((bool || !(this.mState == 0 || this.mState == 5)) && head.getBottom() != 0) {
            ev.setAction(3);
            return super.dispatchTouchEvent(ev);
        } else if (bool) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        View head = getChildAt(0);
        View child = getChildAt(1);
        if (child == null) {
            Log.e("APPullRefreshView", "child is null");
            return;
        }
        int adapterViewTop = child.getTop();
        if (this.mState == 5) {
            boolean reqestRelayout = true;
            AdapterView listChild = null;
            if (child instanceof AdapterView) {
                listChild = (AdapterView) child;
            } else if (!(child instanceof AdapterView)) {
                View subChild = ((ViewGroup) child).getChildAt(0);
                if (subChild != null && (subChild instanceof AdapterView)) {
                    listChild = (AdapterView) subChild;
                }
            }
            if (listChild != null) {
                if (listChild.getFirstVisiblePosition() != 0) {
                    reqestRelayout = false;
                } else if (listChild.getChildAt(0) != null && listChild.getChildAt(0).getTop() < 0) {
                    reqestRelayout = false;
                }
            }
            if (reqestRelayout) {
                head.layout(0, this.mMaxMagin - head.getMeasuredHeight(), right, head.getMeasuredHeight());
                child.layout(0, this.mMaxMagin, right, this.mMaxMagin + child.getMeasuredHeight());
                return;
            }
            return;
        }
        head.layout(0, adapterViewTop - head.getMeasuredHeight(), right, adapterViewTop);
        child.layout(0, adapterViewTop, right, child.getMeasuredHeight() + adapterViewTop);
    }

    /* access modifiers changed from: private */
    public boolean moveDown(int dis, boolean changeState) {
        boolean isRefreshing = false;
        View head = getChildAt(0);
        View child = getChildAt(1);
        int childTop = child.getTop() + dis;
        if (this.mMaxMagin > 0 && changeState) {
            this.mOverView.onPullto(((double) childTop) / ((double) this.mMaxMagin), this.mState);
        }
        if (childTop <= 0) {
            if (childTop < 0) {
                dis = -child.getTop();
            }
            head.offsetTopAndBottom(dis);
            child.offsetTopAndBottom(dis);
            if (this.mState != 5) {
                this.mState = 0;
            }
        } else if (childTop <= this.mMaxMagin) {
            if (this.mOverView.getState() == 2) {
                this.mOverView.onOpen();
                this.mOverView.setState(1);
            }
            head.offsetTopAndBottom(dis);
            child.offsetTopAndBottom(dis);
            if (changeState && this.mState != 5) {
                this.mState = 1;
            } else if (childTop <= this.mMaxMagin && this.mState == 4) {
                refresh();
            }
        } else if (this.mState != 5) {
            if (this.mOverView.getState() != 2) {
                this.mOverView.onOver();
                this.mOverView.setState(2);
            }
            head.offsetTopAndBottom(dis);
            child.offsetTopAndBottom(dis);
            if (changeState) {
                this.mState = 2;
            }
        }
        if (this.heightChangeListener != null) {
            if (this.mState == 5) {
                isRefreshing = true;
            }
            AuiLogger.debug("AUPullRefreshView", "childTop:" + child.getTop() + ", isRefreshing:" + isRefreshing + ", state : " + this.mState);
            this.heightChangeListener.onChangeHeight(child.getTop(), isRefreshing);
        }
        invalidate();
        return true;
    }

    private void refresh() {
        if (this.mRefreshListener != null) {
            this.mState = 5;
            this.mOverView.onLoad();
            this.mOverView.setState(3);
            this.mRefreshListener.onRefresh();
        }
    }

    public void refreshFinished() {
        AuiLogger.debug("AUPullRefreshView", "refreshFinished");
        postDelayed(new ah(this), this.mOverView.getRemainedLoadingDuration());
    }

    /* access modifiers changed from: private */
    public void finishInternal() {
        View head = getChildAt(0);
        this.mOverView.onFinish();
        this.mOverView.setState(4);
        if (head.getBottom() > 0) {
            this.mState = 6;
            release(head.getBottom());
            return;
        }
        this.mState = 0;
        if (this.heightChangeListener != null) {
            AuiLogger.debug("AUPullRefreshView", "refreshFinished childTop:" + getChildAt(1).getTop() + ", isRefreshing:false");
            this.heightChangeListener.onChangeHeight(getChildAt(1).getTop(), false);
        }
    }

    public void setEnablePull(boolean enablePull) {
        this.mEnablePull = enablePull;
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        if (this.mOverView != null) {
            removeView(this.mOverView);
        }
        this.mRefreshListener = refreshListener;
        initListener();
    }

    public RefreshListener getRefreshListener() {
        return this.mRefreshListener;
    }

    public void addTouchables(ArrayList<View> views) {
        super.addTouchables(views);
        views.add(this);
    }

    public void autoRefresh() {
        this.mState = 5;
        this.mOverView.onLoad();
        this.mOverView.setState(3);
        requestLayout();
    }

    public void setLoadingHeightChangeListener(LoadingHeightChangeListener listener) {
        this.heightChangeListener = listener;
    }
}
