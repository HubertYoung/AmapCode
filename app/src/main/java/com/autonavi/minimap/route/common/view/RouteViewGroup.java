package com.autonavi.minimap.route.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.amap.bundle.logs.AMapLog;

public class RouteViewGroup extends RelativeLayout {
    a mViewGroupStatusListener;
    boolean noticed;

    public interface a {
        void a();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        clearAnimation();
        super.onDetachedFromWindow();
    }

    public RouteViewGroup(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public RouteViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWillNotDraw(false);
    }

    public RouteViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setWillNotDraw(false);
    }

    public void setStatusListener(a aVar) {
        this.mViewGroupStatusListener = aVar;
        if (aVar != null) {
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        StringBuilder sb = new StringBuilder("----> onDraw");
        sb.append(this.mViewGroupStatusListener != null);
        AMapLog.e("RouteViewGroup", sb.toString());
        if (this.mViewGroupStatusListener != null && !this.noticed) {
            this.mViewGroupStatusListener.a();
            this.noticed = true;
        }
    }
}
