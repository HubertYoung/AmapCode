package com.amap.bundle.drivecommon.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class NaviSwitchParentLayout extends RelativeLayout {
    boolean mIsIntercept = false;

    public NaviSwitchParentLayout(Context context) {
        super(context);
    }

    public NaviSwitchParentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NaviSwitchParentLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mIsIntercept) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void setIntercept(boolean z) {
        this.mIsIntercept = z;
    }
}
