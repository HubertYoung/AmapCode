package com.autonavi.minimap.ajx3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.autonavi.minimap.ajx3.widget.view.Scroller;
import java.lang.ref.WeakReference;

public class AjxViewResizeHelper {
    /* access modifiers changed from: private */
    public static int sCurrentContentHeight = -1;
    private static WeakReference<Runnable> sResizeTask;
    private Rect mFocusViewRect = new Rect();

    public Scroller getScrollerContainer(@NonNull View view) {
        for (ViewParent parent = view.getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof Scroller) {
                return (Scroller) parent;
            }
        }
        return null;
    }

    public int getScrollOffsetY(int i, @NonNull View view) {
        view.getGlobalVisibleRect(this.mFocusViewRect);
        int i2 = this.mFocusViewRect.bottom - i;
        if (i2 > 0) {
            return i2;
        }
        return 0;
    }

    public void adjustContentLayout(final int i, @NonNull View view) {
        Context context = view.getContext();
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (getScrollerContainer(view) != null) {
                final View contentLayout = getContentLayout(activity);
                if (contentLayout != null) {
                    final LayoutParams layoutParams = (LayoutParams) contentLayout.getLayoutParams();
                    if (layoutParams != null) {
                        int scrollOffsetY = getScrollOffsetY(i, view);
                        if (scrollOffsetY > 0) {
                            contentLayout.scrollTo(0, scrollOffsetY);
                        }
                        AnonymousClass1 r5 = new Runnable() {
                            public void run() {
                                contentLayout.scrollTo(0, 0);
                                layoutParams.height = i;
                                contentLayout.requestLayout();
                                AjxViewResizeHelper.sCurrentContentHeight = layoutParams.height;
                            }
                        };
                        if (!(sResizeTask == null || sResizeTask.get() == null)) {
                            contentLayout.removeCallbacks((Runnable) sResizeTask.get());
                        }
                        contentLayout.post(r5);
                        sResizeTask = new WeakReference<>(r5);
                    }
                }
            }
        }
    }

    public void showInputMethodWindow(int i, @NonNull View view) {
        Context context = view.getContext();
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            boolean isFullScreen = isFullScreen(activity);
            boolean isResizeModeEnable = isResizeModeEnable(activity);
            if (isFullScreen && isResizeModeEnable) {
                adjustContentLayout(i, view);
            }
        }
    }

    public void hideInputMethodWindow(@NonNull View view) {
        Context context = view.getContext();
        if (context instanceof Activity) {
            View contentLayout = getContentLayout((Activity) context);
            if (contentLayout != null) {
                if (!(sResizeTask == null || sResizeTask.get() == null)) {
                    contentLayout.removeCallbacks((Runnable) sResizeTask.get());
                    if (contentLayout.getScrollY() > 0) {
                        contentLayout.scrollTo(0, 0);
                    }
                }
                sResizeTask = null;
                if (isContentNeedResume()) {
                    LayoutParams layoutParams = (LayoutParams) contentLayout.getLayoutParams();
                    layoutParams.height = -1;
                    contentLayout.requestLayout();
                    sCurrentContentHeight = layoutParams.height;
                }
            }
        }
    }

    public static boolean isFullScreen(@NonNull Activity activity) {
        return (activity.getWindow().getAttributes().flags & 1024) == 1024;
    }

    public static int getScreenHeight(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static boolean isResizeModeEnable(Activity activity) {
        return (activity.getWindow().getAttributes().softInputMode & 16) == 16;
    }

    public static boolean isContentNeedResume() {
        return sCurrentContentHeight != -1;
    }

    public static View getContentLayout(Activity activity) {
        return ((FrameLayout) activity.findViewById(16908290)).getChildAt(0);
    }
}
