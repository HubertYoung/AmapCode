package com.autonavi.minimap.ajx3;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.autonavi.minimap.ajx3.util.KeyBoardUtil;
import com.autonavi.minimap.ajx3.widget.view.TextArea;
import com.autonavi.minimap.ajx3.widget.view.TextArea.IPageLifeCircleListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AjxSoftKeyboardListener {
    private static final int THRESHOLD = 250;
    private static volatile AjxSoftKeyboardListener sInstance = null;
    private static boolean sLayoutListenerEnable = false;
    /* access modifiers changed from: private */
    public boolean isActivityPaused = false;
    /* access modifiers changed from: private */
    public boolean isActivityStopped = false;
    private boolean isContentLayout = false;
    private boolean isKeyboardShown = false;
    /* access modifiers changed from: private */
    public WeakReference<Activity> mActivity;
    private int mContentHeight;
    private int mDecorHeight;
    /* access modifiers changed from: private */
    public View mDecorView;
    private int mDecorWidth;
    /* access modifiers changed from: private */
    public WeakReference<View> mFocusView = null;
    private OnGlobalLayoutListener mGlobalLayoutListener = new OnGlobalLayoutListener() {
        public void onGlobalLayout() {
            AjxSoftKeyboardListener.this.handleLayoutChange();
        }
    };
    private List<SoftKeyboardChangeListener> mListeners = new ArrayList();
    private IPageLifeCircleListener mPageLifeCircleListener = null;
    private Rect mRect = new Rect();
    private AjxViewResizeHelper mResizeHelper = new AjxViewResizeHelper();

    public interface SoftKeyboardChangeListener {
        void onSoftKeyboardHidden(int i);

        void onSoftKeyboardShown(int i);
    }

    public static AjxSoftKeyboardListener getInstance() {
        if (sInstance == null) {
            synchronized (AjxSoftKeyboardListener.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new AjxSoftKeyboardListener();
                    }
                }
            }
        }
        return sInstance;
    }

    public void init(Activity activity) {
        if (this.mDecorView == null && activity != null) {
            this.mActivity = new WeakReference<>(activity);
            this.mDecorView = activity.getWindow().getDecorView();
            activity.getApplication().registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                public void onActivityCreated(Activity activity, Bundle bundle) {
                }

                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                public void onActivityStarted(Activity activity) {
                }

                public void onActivityResumed(Activity activity) {
                    if (activity != null && AjxSoftKeyboardListener.this.mActivity != null && activity == AjxSoftKeyboardListener.this.mActivity.get()) {
                        if (AjxSoftKeyboardListener.this.isActivityStopped) {
                            AjxSoftKeyboardListener.this.handleContentChange(false);
                        }
                        AjxSoftKeyboardListener.this.isActivityStopped = false;
                        AjxSoftKeyboardListener.this.isActivityPaused = false;
                    }
                }

                public void onActivityPaused(Activity activity) {
                    if (activity != null && AjxSoftKeyboardListener.this.mActivity != null && activity == AjxSoftKeyboardListener.this.mActivity.get()) {
                        AjxSoftKeyboardListener.this.isActivityPaused = true;
                    }
                }

                public void onActivityStopped(Activity activity) {
                    if (activity != null && AjxSoftKeyboardListener.this.mActivity != null && activity == AjxSoftKeyboardListener.this.mActivity.get()) {
                        AjxSoftKeyboardListener.this.isActivityStopped = true;
                    }
                }

                public void onActivityDestroyed(Activity activity) {
                    if (activity != null && AjxSoftKeyboardListener.this.mActivity != null && activity == AjxSoftKeyboardListener.this.mActivity.get()) {
                        AjxSoftKeyboardListener.this.mDecorView = null;
                        AjxSoftKeyboardListener.this.mActivity = null;
                        AjxSoftKeyboardListener.this.clearAllListener();
                    }
                }
            });
        }
        this.mPageLifeCircleListener = new IPageLifeCircleListener() {
            public void onPageResume(View view) {
            }

            public void onPageStop(View view) {
            }

            public void onPageDestroy(View view) {
                if (AjxSoftKeyboardListener.this.mFocusView != null && AjxSoftKeyboardListener.this.mFocusView.get() != null && AjxSoftKeyboardListener.this.mFocusView.get() == view) {
                    AjxSoftKeyboardListener.this.handleContentChange(true);
                }
            }
        };
    }

    private void addLayoutListenerIfNeeded() {
        if (this.mListeners.size() > 0 && !sLayoutListenerEnable && this.mDecorView != null) {
            this.mDecorView.getViewTreeObserver().addOnGlobalLayoutListener(this.mGlobalLayoutListener);
            sLayoutListenerEnable = true;
        }
    }

    private void removeLayoutListenerIfNeeded() {
        if (this.mListeners.size() == 0 && sLayoutListenerEnable && this.mDecorView != null) {
            if (VERSION.SDK_INT < 16) {
                this.mDecorView.getViewTreeObserver().removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
            } else {
                this.mDecorView.getViewTreeObserver().removeOnGlobalLayoutListener(this.mGlobalLayoutListener);
            }
            sLayoutListenerEnable = false;
        }
    }

    /* access modifiers changed from: private */
    public void handleLayoutChange() {
        if (this.mDecorView != null && this.mActivity != null && this.mActivity.get() != null) {
            Activity activity = (Activity) this.mActivity.get();
            this.mRect.set(0, 0, 0, 0);
            this.mDecorView.getWindowVisibleDisplayFrame(this.mRect);
            int height = this.mRect.height();
            int width = this.mRect.width();
            View contentLayout = AjxViewResizeHelper.getContentLayout(activity);
            if (contentLayout != null) {
                int height2 = contentLayout.getHeight();
                if (this.mDecorHeight == 0 || this.mDecorWidth == 0) {
                    this.mDecorHeight = height;
                    this.mDecorWidth = width;
                    this.mContentHeight = height2;
                } else if (width != this.mDecorWidth) {
                    this.mDecorHeight = height;
                    this.mDecorWidth = width;
                    this.mContentHeight = height2;
                } else {
                    this.mDecorHeight = height;
                    this.mDecorWidth = width;
                    this.mContentHeight = height2;
                    dispatchLayoutChange(activity, this.mDecorHeight - height, height, this.mContentHeight - height2);
                }
            }
        }
    }

    private void dispatchLayoutChange(Activity activity, int i, int i2, int i3) {
        int screenHeight = AjxViewResizeHelper.getScreenHeight(activity);
        if (this.isKeyboardShown || 250 >= i || i2 + 250 + i < screenHeight) {
            if (this.isKeyboardShown) {
                int i4 = -i;
                if (250 < i4 && i2 + 250 >= screenHeight) {
                    callBackHide(i4, -i3);
                    this.isContentLayout = false;
                    return;
                }
            }
            if (this.isKeyboardShown && i != 0) {
                updateResizeShow(activity, i2);
                this.isContentLayout = false;
            }
            return;
        }
        callBackShow(i2, i, i3);
        this.isContentLayout = false;
    }

    public synchronized void addListener(SoftKeyboardChangeListener softKeyboardChangeListener) {
        if (softKeyboardChangeListener != null) {
            if (!this.mListeners.contains(softKeyboardChangeListener)) {
                this.mListeners.add(softKeyboardChangeListener);
                addLayoutListenerIfNeeded();
            }
        }
    }

    public synchronized void removeListener(SoftKeyboardChangeListener softKeyboardChangeListener) {
        if (softKeyboardChangeListener != null) {
            if (this.mListeners.contains(softKeyboardChangeListener)) {
                this.mListeners.remove(softKeyboardChangeListener);
            }
        }
    }

    public synchronized void clearAllListener() {
        this.mListeners.clear();
        removeLayoutListenerIfNeeded();
    }

    /* access modifiers changed from: private */
    public void handleContentChange(boolean z) {
        if (this.mActivity != null && this.mActivity.get() != null && this.mDecorView != null) {
            this.mRect.set(0, 0, 0, 0);
            this.mDecorView.getWindowVisibleDisplayFrame(this.mRect);
            int height = this.mRect.height();
            int width = this.mRect.width();
            Activity activity = (Activity) this.mActivity.get();
            View contentLayout = AjxViewResizeHelper.getContentLayout(activity);
            if (contentLayout != null) {
                int height2 = contentLayout.getHeight();
                int i = this.mContentHeight - height2;
                this.mDecorHeight = height;
                this.mDecorWidth = width;
                this.mContentHeight = height2;
                if (z && height - height2 <= 250 && AjxViewResizeHelper.isContentNeedResume()) {
                    height = AjxViewResizeHelper.getScreenHeight(activity);
                }
                int i2 = height2 - height;
                if (250 < (-i2) && (this.mFocusView == null || this.mFocusView.get() == null)) {
                    View focusView = getFocusView();
                    if (focusView != null) {
                        setPageLifeCircleListener(focusView);
                        this.mFocusView = new WeakReference<>(focusView);
                    }
                }
                this.isContentLayout = true;
                dispatchLayoutChange(activity, i2, height, i);
            }
        }
    }

    private void updateResizeShow(Activity activity, int i) {
        boolean isFullScreen = AjxViewResizeHelper.isFullScreen(activity);
        boolean isResizeModeEnable = AjxViewResizeHelper.isResizeModeEnable(activity);
        if (isFullScreen && isResizeModeEnable) {
            if (this.mFocusView == null || this.mFocusView.get() == null) {
                View focusView = getFocusView();
                if (focusView != null) {
                    setPageLifeCircleListener(focusView);
                    this.mFocusView = new WeakReference<>(focusView);
                }
            }
            if (!(this.mFocusView == null || this.mFocusView.get() == null)) {
                this.mResizeHelper.adjustContentLayout(i, (View) this.mFocusView.get());
            }
        }
    }

    private void callBackShow(int i, int i2, int i3) {
        this.isKeyboardShown = true;
        if (this.mListeners.size() > 0) {
            View focusView = getFocusView();
            if (focusView != null) {
                setPageLifeCircleListener(focusView);
                this.mFocusView = new WeakReference<>(focusView);
                this.mResizeHelper.showInputMethodWindow(i, focusView);
                KeyBoardUtil.setInputStateShow((Activity) this.mActivity.get(), true);
            }
            for (int i4 = 0; i4 < this.mListeners.size(); i4++) {
                SoftKeyboardChangeListener softKeyboardChangeListener = this.mListeners.get(i4);
                if (softKeyboardChangeListener != null) {
                    softKeyboardChangeListener.onSoftKeyboardShown(i2);
                }
            }
        }
    }

    private void callBackHide(int i, int i2) {
        boolean z = this.isContentLayout;
        this.isKeyboardShown = false;
        if (this.mFocusView != null) {
            if (!z && !this.isActivityPaused) {
                this.mDecorView.post(new Runnable() {
                    public void run() {
                        if (!AjxSoftKeyboardListener.this.isActivityPaused && AjxSoftKeyboardListener.this.mActivity != null && AjxSoftKeyboardListener.this.mActivity.get() != null) {
                            KeyBoardUtil.setInputStateShow((Activity) AjxSoftKeyboardListener.this.mActivity.get(), false);
                        }
                    }
                });
            }
            View view = (View) this.mFocusView.get();
            if (view != null) {
                this.mResizeHelper.hideInputMethodWindow(view);
            }
            this.mFocusView = null;
        }
        for (int i3 = 0; i3 < this.mListeners.size(); i3++) {
            SoftKeyboardChangeListener softKeyboardChangeListener = this.mListeners.get(i3);
            if (softKeyboardChangeListener != null) {
                softKeyboardChangeListener.onSoftKeyboardHidden(i);
            }
        }
    }

    public View getFocusView() {
        if (this.mListeners.size() <= 0) {
            return null;
        }
        for (SoftKeyboardChangeListener next : this.mListeners) {
            if (next instanceof View) {
                View view = (View) next;
                if (view.isFocused()) {
                    return view;
                }
            }
        }
        return null;
    }

    private void setPageLifeCircleListener(View view) {
        if (view instanceof TextArea) {
            ((TextArea) view).setPageLifeCircleListener(this.mPageLifeCircleListener);
            return;
        }
        if (view.getParent() instanceof TextArea) {
            ((TextArea) view.getParent()).setPageLifeCircleListener(this.mPageLifeCircleListener);
        }
    }

    private void clearPageLifeCircleListener(View view) {
        if (view instanceof TextArea) {
            ((TextArea) view).setPageLifeCircleListener(null);
            return;
        }
        if (view.getParent() instanceof TextArea) {
            ((TextArea) view.getParent()).setPageLifeCircleListener(null);
        }
    }
}
