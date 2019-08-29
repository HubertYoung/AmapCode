package com.alipay.mobile.tinyappcommon.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.nebula.util.H5Log;

public class ToggleButtonView extends TextView {
    private ViewGroup a;
    private float b;
    private float c;
    private float d;
    private float e;
    private int f = 0;
    private final int g;
    private a h = null;
    private int i = 0;
    private int j = 0;

    public interface a {
        void onClick(View view);
    }

    public void setOnProxyClickListener(a proxyClickListener) {
        this.h = proxyClickListener;
    }

    public ToggleButtonView(Context context) {
        super(context);
        this.g = ViewConfiguration.get(context).getScaledTouchSlop() / 2;
    }

    public void setViewContainer(ViewGroup viewContainer) {
        this.a = viewContainer;
    }

    public void setMaxTopMargin(int maxTopMargin) {
        this.f = maxTopMargin;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.d = event.getX();
                this.e = event.getY();
                this.i = (int) event.getRawX();
                this.j = (int) event.getRawY();
                this.b = 0.0f;
                this.c = 0.0f;
                break;
            case 1:
                if (Math.abs(event.getRawX() - ((float) this.i)) <= ((float) this.g) && Math.abs(event.getRawY() - ((float) this.j)) <= ((float) this.g) && this.h != null) {
                    this.h.onClick(this);
                    break;
                }
            case 2:
                this.b = event.getX() - this.d;
                this.c = event.getY() - this.e;
                a();
                break;
        }
        return true;
    }

    private void a() {
        try {
            LayoutParams layoutParams = (LayoutParams) getLayoutParams();
            layoutParams.rightMargin = (int) (((float) layoutParams.rightMargin) - this.b);
            layoutParams.bottomMargin = (int) (((float) layoutParams.bottomMargin) - this.c);
            int parentWidth = this.a.getWidth();
            if (layoutParams.rightMargin <= 0) {
                layoutParams.rightMargin = 0;
            } else if (layoutParams.rightMargin + getWidth() > parentWidth) {
                layoutParams.rightMargin = parentWidth - getWidth();
            }
            int parentHeight = this.a.getHeight() - this.f;
            if (layoutParams.bottomMargin <= 0) {
                layoutParams.bottomMargin = 0;
            } else if (layoutParams.bottomMargin + getHeight() > parentHeight) {
                layoutParams.bottomMargin = parentHeight - getHeight();
            }
            this.a.updateViewLayout(this, layoutParams);
        } catch (Throwable e2) {
            H5Log.e(ToggleButtonView.class.getSimpleName(), "updateViewPosition...e=" + e2);
        }
    }
}
