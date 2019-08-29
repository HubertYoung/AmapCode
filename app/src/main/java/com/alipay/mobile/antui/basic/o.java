package com.alipay.mobile.antui.basic;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.view.ViewConfiguration;

/* compiled from: AUHorizontalListView */
final class o implements Runnable {
    final /* synthetic */ AUHorizontalListView a;

    private o(AUHorizontalListView aUHorizontalListView) {
        this.a = aUHorizontalListView;
    }

    /* synthetic */ o(AUHorizontalListView x0, byte b) {
        this(x0);
    }

    public final void run() {
        if (this.a.mTouchMode == 0) {
            this.a.mTouchMode = 1;
            View child = this.a.getChildAt(this.a.mMotionPosition - this.a.mFirstPosition);
            if (child != null && !child.hasFocusable()) {
                this.a.mLayoutMode = 0;
                if (!this.a.mDataChanged) {
                    this.a.setPressed(true);
                    child.setPressed(true);
                    this.a.layoutChildren();
                    this.a.positionSelector(this.a.mMotionPosition, child);
                    this.a.refreshDrawableState();
                    this.a.positionSelector(this.a.mMotionPosition, child);
                    this.a.refreshDrawableState();
                    boolean longClickable = this.a.isLongClickable();
                    if (this.a.mSelector != null) {
                        Drawable d = this.a.mSelector.getCurrent();
                        if (d != null && (d instanceof TransitionDrawable)) {
                            if (longClickable) {
                                ((TransitionDrawable) d).startTransition(ViewConfiguration.getLongPressTimeout());
                            } else {
                                ((TransitionDrawable) d).resetTransition();
                            }
                        }
                    }
                    if (longClickable) {
                        this.a.triggerCheckForLongPress();
                    } else {
                        this.a.mTouchMode = 2;
                    }
                } else {
                    this.a.mTouchMode = 2;
                }
            }
        }
    }
}
