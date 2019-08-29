package com.alipay.mobile.nebula.util;

import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewStub;

public class H5ViewStubUtil {
    public static <T> T getView(View parent, @IdRes int stubId, @IdRes int targetId) {
        ViewStub viewStub = (ViewStub) parent.findViewById(stubId);
        if (viewStub != null) {
            return viewStub.inflate();
        }
        return parent.findViewById(targetId);
    }

    public static boolean isViewVisible(View parent, @IdRes int stubId, @IdRes int targetId) {
        if (((ViewStub) parent.findViewById(stubId)) != null) {
            return false;
        }
        View targetView = parent.findViewById(targetId);
        return targetView != null && targetView.getVisibility() == 0;
    }

    public static void setViewVisibility(View parent, @IdRes int stubId, @IdRes int targetId, int visibility) {
        if (parent != null) {
            if (parent.findViewById(stubId) == null) {
                View targetView = parent.findViewById(targetId);
                if (targetView != null) {
                    targetView.setVisibility(visibility);
                }
            } else if (visibility == 0) {
                ((ViewStub) parent.findViewById(stubId)).inflate().setVisibility(visibility);
            }
        }
    }
}
