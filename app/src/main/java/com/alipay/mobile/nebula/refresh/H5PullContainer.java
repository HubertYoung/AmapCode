package com.alipay.mobile.nebula.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Scroller;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5PullContainer extends FrameLayout implements H5OverScrollListener {
    public static final int DEFALUT_DURATION = 600;
    private static final int MIN_REFRESH_TIME = 2000;
    public static final String TAG = "H5PullContainer";
    private View contentView;
    private Flinger flinger = new Flinger();
    private H5Page h5Page;
    private boolean handleM57OverScroll = false;
    protected int headerHeight;
    private View headerView;
    private H5PullInterceptHandler interceptHandler;
    /* access modifiers changed from: private */
    public boolean isIntercept;
    private int lastY;
    /* access modifiers changed from: private */
    public int offsets = 0;
    private boolean overScrolled;
    /* access modifiers changed from: private */
    public H5PullAdapter pullAdapter;
    private int pullInterceptDistance = 0;
    private long startLoadingTime = 0;
    protected int state = H5PullState.STATE_FIT_CONTENT;

    private class Flinger implements Runnable {
        private boolean finished = true;
        private int lastScrollY;
        private Scroller scroller;

        public Flinger() {
            this.scroller = new Scroller(H5PullContainer.this.getContext());
        }

        public void run() {
            if (this.scroller.computeScrollOffset()) {
                H5PullContainer.this.moveOffset(this.lastScrollY - this.scroller.getCurrY());
                this.lastScrollY = this.scroller.getCurrY();
                H5PullContainer.this.post(this);
                return;
            }
            this.finished = true;
            H5PullContainer.this.removeCallbacks(this);
            if (H5PullContainer.this.pullAdapter != null) {
                H5PullContainer.this.pullAdapter.onFinish();
            }
        }

        public void recover(int offset) {
            try {
                H5PullContainer.this.removeCallbacks(this);
                this.lastScrollY = 0;
                H5PullContainer.this.offsets = 0;
                this.finished = false;
                this.scroller.startScroll(0, 0, 0, offset, 600);
                H5PullContainer.this.post(this);
            } catch (Throwable throwable) {
                H5Log.e((String) H5PullContainer.TAG, throwable);
            }
        }

        public void scroll(int offset, int duration) {
            try {
                H5PullContainer.this.removeCallbacks(this);
                this.lastScrollY = 0;
                H5PullContainer.this.offsets = 0;
                this.finished = false;
                this.scroller.startScroll(0, 0, 0, offset, duration);
                H5PullContainer.this.post(this);
            } catch (Throwable throwable) {
                H5Log.e((String) H5PullContainer.TAG, throwable);
            }
        }

        public boolean isFinished() {
            return this.finished;
        }
    }

    class H5PullInterceptHandler implements H5CallBack {
        H5PullInterceptHandler() {
        }

        public void onCallBack(JSONObject param) {
            boolean prevent = H5Utils.getBoolean(param, (String) "prevent", false);
            H5Log.d(H5PullContainer.TAG, "pullIntercept event prevent " + prevent);
            if (prevent) {
                H5PullContainer.this.isIntercept = true;
            } else {
                H5PullContainer.this.isIntercept = false;
            }
        }
    }

    public H5PullContainer(Context context) {
        super(context);
        init();
    }

    public H5PullContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public H5PullContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        boolean z = true;
        if (H5Utils.isUCM57()) {
            this.handleM57OverScroll = true;
            H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (provider != null) {
                if ("NO".equalsIgnoreCase(provider.getConfig("h5_handleM57OverScroll"))) {
                    z = false;
                }
                this.handleM57OverScroll = z;
            }
        }
        this.interceptHandler = new H5PullInterceptHandler();
        this.isIntercept = false;
    }

    public void setH5Page(H5Page h5Page2) {
        this.h5Page = h5Page2;
        setInterceptDistance();
    }

    private void setInterceptDistance() {
        if (this.h5Page != null && this.h5Page.getParams() != null) {
            int distance = H5Utils.getInt(this.h5Page.getParams(), (String) H5Param.PULL_INTERCEPT_DISTANCE);
            if (distance > 0) {
                this.pullInterceptDistance = dpToPx(distance);
            }
        }
    }

    private int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * ((float) dps));
    }

    private static int getScrollY(View view) {
        if (view != null) {
            return view.getScrollY();
        }
        return 0;
    }

    public boolean dispatchTouchEvent(MotionEvent intent) {
        return handleTouch(intent) || super.dispatchTouchEvent(intent);
    }

    private boolean canPull() {
        if ((this.pullAdapter == null || this.pullAdapter.canPull()) && this.contentView != null) {
            return true;
        }
        return false;
    }

    private boolean canRefresh() {
        return this.pullAdapter != null && this.pullAdapter.canRefresh();
    }

    private boolean handleTouch(MotionEvent intent) {
        boolean actionUp;
        boolean needHandle;
        boolean z = false;
        if (!canPull() || this.isIntercept) {
            return false;
        }
        int action = intent.getAction();
        int contentTop = this.contentView.getTop();
        if (action == 1 || action == 3) {
            actionUp = true;
        } else {
            actionUp = false;
        }
        if (actionUp || action == 0) {
            this.overScrolled = false;
        }
        if (contentTop > 0 && actionUp) {
            if (hasHeader()) {
                if (this.state == H5PullState.STATE_OVER) {
                    fitExtras();
                    return false;
                } else if (this.state != H5PullState.STATE_FIT_EXTRAS) {
                    int i = H5PullState.STATE_OPEN;
                } else if (contentTop <= this.headerHeight) {
                    return false;
                } else {
                    this.flinger.recover(contentTop - this.headerHeight);
                    return false;
                }
            }
            this.flinger.recover(contentTop);
            return false;
        } else if (action != 2) {
            return false;
        } else {
            boolean handled = false;
            int scrollY = getScrollY(this.contentView);
            int offset = ((int) (intent.getY() - ((float) this.lastY))) / 2;
            if (!this.overScrolled || scrollY > 0) {
                needHandle = false;
            } else {
                needHandle = true;
            }
            if (this.handleM57OverScroll && this.offsets == 0) {
                if (offset > 0) {
                    z = true;
                }
                needHandle &= z;
            }
            if (needHandle) {
                if (this.offsets < this.pullInterceptDistance || this.pullInterceptDistance <= 0) {
                    this.offsets += offset;
                    if (this.offsets > 300) {
                        offset /= 2;
                    }
                    moveOffset(offset);
                    handled = true;
                } else {
                    sendToWebIntercept();
                    return true;
                }
            }
            this.lastY = (int) intent.getY();
            return handled;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int contentTop = 0;
        if (this.contentView != null) {
            contentTop = this.contentView.getTop();
            this.contentView.layout(0, contentTop, right, this.contentView.getMeasuredHeight() + contentTop);
        }
        int headerTop = contentTop - this.headerHeight;
        if (hasHeader()) {
            this.headerView.layout(0, headerTop, right, this.headerHeight + headerTop);
        }
    }

    /* access modifiers changed from: private */
    public boolean moveOffset(int offset) {
        if (this.contentView == null) {
            return false;
        }
        if (this.state != H5PullState.STATE_FIT_EXTRAS) {
            int nextTop = this.contentView.getTop() + offset;
            if (nextTop <= 0) {
                offset = -this.contentView.getTop();
            } else if (nextTop <= this.headerHeight) {
                if ((this.state == H5PullState.STATE_OVER || this.state == H5PullState.STATE_FIT_CONTENT) && this.flinger.isFinished()) {
                    if (this.pullAdapter != null) {
                        this.pullAdapter.onOpen();
                    }
                    this.state = H5PullState.STATE_OPEN;
                }
                if (this.pullAdapter != null) {
                    this.pullAdapter.onProgressUpdate((int) (100.0f * (((float) nextTop) / ((float) this.headerHeight))));
                }
            } else if (nextTop > this.headerHeight && this.state == H5PullState.STATE_OPEN) {
                if (this.pullAdapter != null) {
                    this.pullAdapter.onOver();
                }
                this.state = H5PullState.STATE_OVER;
            }
        }
        this.contentView.offsetTopAndBottom(offset);
        if (hasHeader()) {
            this.headerView.offsetTopAndBottom(offset);
        }
        invalidate();
        return true;
    }

    private boolean hasHeader() {
        if (this.headerView != null && this.headerView.getVisibility() == 0) {
            return true;
        }
        return false;
    }

    public void startPullToRefresh() {
        if (this.headerView == null) {
            updateHeader();
        }
        this.pullAdapter.onOpen();
        this.headerView.setVisibility(0);
        this.flinger.scroll(this.headerHeight, 600);
        fitExtras();
    }

    private void fitExtras() {
        if (this.state != H5PullState.STATE_FIT_EXTRAS) {
            this.state = H5PullState.STATE_FIT_EXTRAS;
            if (hasHeader()) {
                this.flinger.recover(this.contentView.getTop() - this.headerHeight);
            }
            if (this.pullAdapter != null) {
                this.pullAdapter.onLoading();
                this.startLoadingTime = System.currentTimeMillis();
            }
        }
    }

    public void setContentView(View view) {
        this.contentView = view;
        addView(this.contentView, 0);
    }

    public void setPullableView(H5PullableView view) {
        if (view != null) {
            view.setH5OverScrollListener(this);
        }
    }

    public void fitContent() {
        if (this.state != H5PullState.STATE_FIT_EXTRAS || this.contentView == null) {
            return;
        }
        if (!enableUsePullHeader() || System.currentTimeMillis() - this.startLoadingTime >= 2000) {
            performFitContent();
            return;
        }
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                H5PullContainer.this.performFitContent();
            }
        }, (long) (2000 - ((int) (System.currentTimeMillis() - this.startLoadingTime))));
    }

    /* access modifiers changed from: private */
    public void performFitContent() {
        int offset = this.contentView.getTop();
        if (offset > 0) {
            this.flinger.recover(offset);
        }
        this.state = H5PullState.STATE_FIT_CONTENT;
        if (this.pullAdapter != null) {
            this.pullAdapter.onRefreshFinish();
        }
    }

    public boolean enableUsePullHeader() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || "NO".equalsIgnoreCase(h5ConfigProvider.getConfig("h5_enableLottiePullHeader"))) {
            return false;
        }
        return true;
    }

    public boolean isBackToTop() {
        return this.contentView.getTop() <= 0;
    }

    public void setPullAdapter(H5PullAdapter adapter) {
        if (this.headerView != null) {
            removeView(this.headerView);
            this.headerView = null;
        }
        this.pullAdapter = adapter;
    }

    public void onOverScrolled(int deltaX, int deltaY, int scrollX, int scrollY) {
        if (this.contentView != null && getScrollY(this.contentView) <= 0 && deltaY < 0 && scrollY <= 0) {
            this.overScrolled = true;
        }
    }

    public void notifyViewChanged() {
        if (this.headerView == null) {
            updateHeader();
        }
        if (this.headerView == null) {
            return;
        }
        if (!canRefresh()) {
            this.headerView.setVisibility(8);
        } else {
            this.headerView.setVisibility(0);
        }
    }

    private void updateHeader() {
        if (getChildCount() <= 0) {
            throw new IllegalStateException("content view not added yet");
        }
        this.headerView = this.pullAdapter.getHeaderView();
        if (this.headerView != null) {
            this.headerView.measure(0, 0);
            this.headerHeight = this.headerView.getMeasuredHeight();
            addView(this.headerView, 0, new LayoutParams(-1, this.headerHeight));
        }
    }

    public void setWebViewTop(String position, boolean animated) {
        int animateDuration;
        this.isIntercept = false;
        int offset = this.contentView.getTop();
        if (animated) {
            animateDuration = 600;
        } else {
            animateDuration = 0;
        }
        if (position.equalsIgnoreCase("top") && offset > 0) {
            this.flinger.scroll(offset, animateDuration);
        } else if (position.equalsIgnoreCase("bottom")) {
            this.flinger.scroll(-(this.contentView.getHeight() - offset), animateDuration);
        }
    }

    private void sendToWebIntercept() {
        if (this.h5Page != null && this.h5Page.getBridge() != null) {
            H5Log.d(TAG, "isIntercept : " + this.isIntercept);
            this.h5Page.getBridge().sendToWeb("pullIntercept", null, this.interceptHandler);
        }
    }
}
