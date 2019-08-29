package com.autonavi.minimap.ajx3.widget.view.toast;

import android.app.Activity;
import android.content.Context;
import com.autonavi.minimap.ajx3.widget.view.toast.impl.ActivityToast;
import com.autonavi.minimap.ajx3.widget.view.toast.impl.SystemToast;

public class AjxToast {
    public static final int DURATION_LONG = 3500;
    public static final int DURATION_SHORT = 2000;
    public static final int TYPE_TOAST_ACTIVITY = 1;
    public static final int TYPE_TOAST_SYSTEM = 2;

    public static IToast make(Context context) {
        if (context instanceof Activity) {
            return new ActivityToast(context);
        }
        return new SystemToast(context);
    }

    public static IToast make(Context context, int i) {
        switch (i) {
            case 1:
                if (context instanceof Activity) {
                    return new ActivityToast(context);
                }
                throw new IllegalArgumentException("make ActivityToast param must be Activity type!!!");
            case 2:
                return new SystemToast(context);
            default:
                return new SystemToast(context);
        }
    }
}
