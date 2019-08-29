package com.autonavi.minimap.ajx3.widget.property;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;

class HoverHelper {
    private static final boolean DEBUG = false;
    private static final int MSG_REMOVE_HOVER_FOR_CLICK = 412;
    private static final int MSG_TAP_TIME_OUT = 411;
    private static final String TAG = "Helper";
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 411:
                    HoverHelper.this.setHover(HoverHelper.this.mHoverBoundary, true);
                    return;
                case 412:
                    Object obj = message.obj;
                    if (obj instanceof View) {
                        HoverHelper.this.setHover((View) obj, false);
                        break;
                    }
                    break;
            }
        }
    };
    /* access modifiers changed from: private */
    public View mHoverBoundary;
    private boolean mIsInScrollContainer;

    HoverHelper() {
    }

    public void prepare(View view) {
        this.mHandler.removeMessages(411);
        if (view != null) {
            this.mHoverBoundary = view;
        } else {
            this.mHoverBoundary = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean needHandleHover() {
        return this.mHoverBoundary != null;
    }

    public void clear() {
        this.mHandler.removeMessages(411);
        this.mHoverBoundary = null;
        this.mIsInScrollContainer = false;
    }

    /* access modifiers changed from: 0000 */
    public void handleDown() {
        if (this.mHoverBoundary != null) {
            this.mIsInScrollContainer = isInScrollingContainer();
            if (this.mIsInScrollContainer) {
                this.mHandler.sendEmptyMessageDelayed(411, (long) ViewConfiguration.getTapTimeout());
            } else {
                setHover(this.mHoverBoundary, true);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void handleDrag() {
        this.mHandler.removeMessages(411);
        if (this.mHoverBoundary != null) {
            setHover(this.mHoverBoundary, false);
        }
    }

    /* access modifiers changed from: 0000 */
    public void handleUp() {
        if (this.mHoverBoundary != null) {
            if (this.mHandler.hasMessages(411)) {
                this.mHandler.removeMessages(411);
                setHover(this.mHoverBoundary, true);
                Message message = new Message();
                message.what = 412;
                message.obj = this.mHoverBoundary;
                this.mHandler.sendMessageDelayed(message, 17);
                return;
            }
            setHover(this.mHoverBoundary, false);
        }
    }

    /* access modifiers changed from: private */
    public void setHover(View view, boolean z) {
        if (view instanceof ViewExtension) {
            BaseProperty property = ((ViewExtension) view).getProperty();
            if (property != null) {
                property.mGestureAttribute.handlePress(z);
            }
        }
    }

    private boolean isInScrollingContainer() {
        ViewParent parent = this.mHoverBoundary.getParent();
        while (parent != null && (parent instanceof ViewGroup) && !(parent instanceof AjxView)) {
            if (((ViewGroup) parent).shouldDelayChildPressedState()) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }
}
