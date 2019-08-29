package com.amap.bundle.drive.result.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class RouteCarResultTouchEventView extends View {
    private a mOnTouchCallback = null;

    public interface a {
    }

    private void initView(Context context) {
    }

    public RouteCarResultTouchEventView(Context context) {
        super(context);
        initView(context);
    }

    public RouteCarResultTouchEventView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public RouteCarResultTouchEventView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mOnTouchCallback != null) {
            System.currentTimeMillis();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setOnTouchCallback(a aVar) {
        this.mOnTouchCallback = aVar;
    }
}
