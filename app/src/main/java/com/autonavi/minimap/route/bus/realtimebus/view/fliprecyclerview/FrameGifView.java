package com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.autonavi.minimap.R;

public class FrameGifView extends View {
    public static final int NON_REALTIME = 0;
    public static final int REALTIME_SIGINAL = 1;
    public static final int REALTIME_SLEEP = 2;
    private int currentResId;
    /* access modifiers changed from: private */
    public AnimationDrawable mDrawable;

    public FrameGifView(Context context) {
        super(context);
    }

    public FrameGifView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FrameGifView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void initAnimationResDrawable(int i) {
        if (i != this.currentResId) {
            setBackgroundResource(i);
            this.mDrawable = (AnimationDrawable) getBackground();
        }
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i != 0 || this.mDrawable == null) {
            if (this.mDrawable != null && this.mDrawable.isRunning()) {
                post(new Runnable() {
                    public final void run() {
                        FrameGifView.this.mDrawable.stop();
                    }
                });
            }
            return;
        }
        post(new Runnable() {
            public final void run() {
                FrameGifView.this.mDrawable.start();
            }
        });
    }

    public void setStatus(int i) {
        switch (i) {
            case 0:
                setVisibility(8);
                return;
            case 1:
                initAnimationResDrawable(R.anim.realtime_siginal);
                setVisibility(0);
                return;
            case 2:
                initAnimationResDrawable(R.anim.realtime_bus_sleep);
                setVisibility(0);
                break;
        }
    }
}
