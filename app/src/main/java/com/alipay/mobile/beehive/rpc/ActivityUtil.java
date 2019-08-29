package com.alipay.mobile.beehive.rpc;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.commonui.widget.APTitleBar;

public class ActivityUtil {
    public static boolean isActivityContentVisible(Activity activity) {
        return isViewGroupContentVisible(getActivityRootView(activity));
    }

    private static boolean isViewGroupContentVisible(View v) {
        if (v instanceof APTitleBar) {
            return false;
        }
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            if (vg.getVisibility() != 0 || vg.getChildCount() <= 0) {
                return false;
            }
            for (int i = 0; i < vg.getChildCount(); i++) {
                if (isViewGroupContentVisible(vg.getChildAt(i))) {
                    return true;
                }
            }
            return false;
        } else if (v == null || v.getVisibility() != 0) {
            return false;
        } else {
            return true;
        }
    }

    public static View findExtendTitleBarFromView(View v) {
        if (isStandardTitleBar(v)) {
            return v;
        }
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            int c = vg.getChildCount();
            for (int i = 0; i < c; i++) {
                View result = findExtendTitleBarFromView(vg.getChildAt(i));
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public static View findStandardTitleBarFromView(View v) {
        if (isStandardTitleBar(v)) {
            return v;
        }
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            int c = vg.getChildCount();
            for (int i = 0; i < c; i++) {
                View result = findStandardTitleBarFromView(vg.getChildAt(i));
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private static boolean isStandardTitleBar(View v) {
        return (v instanceof APTitleBar) || (v instanceof AUTitleBar);
    }

    public static APTitleBar findTitleBarFromView(View v) {
        if (v instanceof APTitleBar) {
            return (APTitleBar) v;
        }
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            int c = vg.getChildCount();
            for (int i = 0; i < c; i++) {
                APTitleBar result = findTitleBarFromView(vg.getChildAt(i));
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public static ViewGroup findActivityContentRoot(Activity activity) {
        View titleBar = findStandardTitleBarFromView(getActivityRootView(activity));
        if (titleBar != null) {
            ViewGroup parentVg = (ViewGroup) titleBar.getParent();
            int titleBarIndex = parentVg.getChildCount();
            for (int i = 0; i < parentVg.getChildCount(); i++) {
                View v = parentVg.getChildAt(i);
                if (v == titleBar) {
                    titleBarIndex = i;
                } else if ((v instanceof ViewGroup) && i > titleBarIndex) {
                    return (ViewGroup) v;
                }
            }
        }
        return null;
    }

    public static View getActivityXmlRootView(Activity context) {
        ViewGroup v = getActivityIdContentView(context);
        if (v == null || v.getChildCount() <= 0) {
            return null;
        }
        return v.getChildAt(0);
    }

    public static ViewGroup getActivityRootView(Activity context) {
        return getActivityIdContentView(context);
    }

    public static ViewGroup getActivityIdContentView(Activity context) {
        if (context.getWindow() == null || context.getWindow().getDecorView() == null) {
            return (ViewGroup) context.findViewById(16908290);
        }
        return (ViewGroup) context.getWindow().getDecorView().findViewById(16908290);
    }
}
