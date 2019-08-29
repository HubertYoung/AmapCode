package com.alipay.mobile.nebulacore.dev.bugme;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

public class H5BugmeConsoleButton extends ImageButton {
    private boolean a = false;
    private float b;
    private float c;
    private float d;
    private float e;

    public H5BugmeConsoleButton(Context context) {
        super(context);
    }

    public H5BugmeConsoleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public H5BugmeConsoleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & 255) {
            case 0:
                this.a = false;
                this.b = getX() - event.getRawX();
                this.c = getY() - event.getRawY();
                this.d = getX();
                this.e = getY();
                break;
            case 2:
                this.a = Math.abs((this.d - event.getRawX()) - this.b) >= 10.0f || Math.abs((this.e - event.getRawY()) - this.c) >= 10.0f;
                animate().x(event.getRawX() + this.b).y(event.getRawY() + this.c).setDuration(0).start();
                break;
        }
        if (this.a || super.onTouchEvent(event)) {
            return true;
        }
        return false;
    }
}
