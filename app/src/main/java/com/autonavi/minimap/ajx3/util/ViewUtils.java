package com.autonavi.minimap.ajx3.util;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.minimap.ajx3.widget.property.TouchHelper;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.list.AjxList;

public class ViewUtils {

    @TargetApi(16)
    static class SDK16 {
        SDK16() {
        }

        public static void setBackground(View view, Drawable drawable) {
            view.setBackground(drawable);
        }

        public static void postOnAnimation(View view, Runnable runnable) {
            view.postOnAnimation(runnable);
        }
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        if (VERSION.SDK_INT >= 16) {
            SDK16.postOnAnimation(view, runnable);
        } else {
            view.postDelayed(runnable, 16);
        }
    }

    public static void setBackground(@Nullable View view, @Nullable Drawable drawable) {
        if (view != null) {
            if (VERSION.SDK_INT >= 16) {
                SDK16.setBackground(view, drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        }
    }

    public static View getClickView(float f, float f2, ViewGroup viewGroup) {
        while (true) {
            int childCount = viewGroup.getChildCount();
            int scrollX = viewGroup.getScrollX();
            int scrollY = viewGroup.getScrollY();
            for (int i = childCount - 1; i >= 0; i--) {
                View childAt = viewGroup.getChildAt(i);
                float[] tempPoint = TouchHelper.getTempPoint();
                tempPoint[0] = f;
                tempPoint[1] = f2;
                if (TouchHelper.pointInView(tempPoint, childAt, scrollX, scrollY)) {
                    float f3 = tempPoint[0];
                    float f4 = tempPoint[1];
                    if (childAt instanceof ViewExtension) {
                        if (!(childAt instanceof ViewGroup)) {
                            return childAt;
                        }
                        if (childAt instanceof AjxList) {
                            return ((AjxList) childAt).findTouchView(f3, f4);
                        }
                        viewGroup = (ViewGroup) childAt;
                        f2 = f4;
                        f = f3;
                    }
                }
            }
            return viewGroup;
        }
    }

    public static void blockDescendantFocusability(View view) {
        if (view instanceof ViewGroup) {
            ((ViewGroup) view).setDescendantFocusability(393216);
        }
    }

    public static void afterDescendantFocusability(View view) {
        if (view instanceof ViewGroup) {
            ((ViewGroup) view).setDescendantFocusability(262144);
        }
    }
}
