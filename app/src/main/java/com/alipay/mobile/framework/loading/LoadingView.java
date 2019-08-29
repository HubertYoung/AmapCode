package com.alipay.mobile.framework.loading;

import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.Map;

public abstract class LoadingView extends FrameLayout {
    protected Activity hostActivity;
    protected OnCancelListener onCancelListener;

    public interface Factory {
        LoadingView createLoadingView(String str, String str2, Bundle bundle);
    }

    public interface OnCancelListener {
        void onCancel();
    }

    public abstract void onHandleMessage(String str, Map<String, Object> map);

    public abstract void onStart();

    public abstract void onStop();

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnCancelListener(OnCancelListener onCancelListener2) {
        this.onCancelListener = onCancelListener2;
    }

    public void setHostActivity(Activity hostActivity2) {
        this.hostActivity = hostActivity2;
    }

    public final void start() {
        post(new Runnable() {
            public void run() {
                try {
                    LoadingView.this.onStart();
                } catch (Throwable t) {
                    TraceLogger.e(LoadingPageManager.TAG, t);
                }
            }
        });
    }

    public final void stop() {
        post(new Runnable() {
            public void run() {
                try {
                    LoadingView.this.onStop();
                } catch (Throwable t) {
                    TraceLogger.e(LoadingPageManager.TAG, t);
                }
            }
        });
    }

    public final void sendMessage(final String msg, final Map<String, Object> data) {
        post(new Runnable() {
            public void run() {
                try {
                    LoadingView.this.onHandleMessage(msg, data);
                } catch (Throwable t) {
                    TraceLogger.e(LoadingPageManager.TAG, t);
                }
            }
        });
    }

    public final void cancel() {
        if (this.onCancelListener != null) {
            this.onCancelListener.onCancel();
        }
    }

    public void performAnimation(String animationType, AnimatorListener animatorListener) {
        throw new IllegalArgumentException(animationType + " animation not supported.");
    }
}
