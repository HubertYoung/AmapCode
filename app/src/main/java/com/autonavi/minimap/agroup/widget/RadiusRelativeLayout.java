package com.autonavi.minimap.agroup.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class RadiusRelativeLayout extends RelativeLayout {
    private RectF mClipRect;
    private float[] mCorners;
    private Path mPath;

    public RadiusRelativeLayout(Context context) {
        this(context, null);
    }

    public RadiusRelativeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RadiusRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPath = new Path();
        this.mClipRect = new RectF();
        float a = (float) agn.a(context, 4.0f);
        this.mCorners = new float[]{a, a, a, a, a, a, a, a};
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        this.mPath.reset();
        this.mClipRect.set(0.0f, 0.0f, (float) (getRight() - getLeft()), (float) (getBottom() - getTop()));
        this.mPath.addRoundRect(this.mClipRect, this.mCorners, Direction.CW);
        canvas.clipPath(this.mPath);
        super.dispatchDraw(canvas);
    }
}
