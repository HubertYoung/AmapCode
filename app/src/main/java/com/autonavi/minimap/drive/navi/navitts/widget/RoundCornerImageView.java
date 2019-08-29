package com.autonavi.minimap.drive.navi.navitts.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.autonavi.minimap.R;

public class RoundCornerImageView extends ImageView {
    protected Path clipPath = new Path();
    protected RectF mRectF = new RectF();
    private float radius = 0.0f;

    public RoundCornerImageView(Context context) {
        super(context);
        init(context, null);
    }

    public RoundCornerImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        init(context, attributeSet);
    }

    public RoundCornerImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RoundCornerImageView);
        this.radius = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.RoundCornerImageView_rciv_radius, 0);
        obtainStyledAttributes.recycle();
    }

    public void setRadius(float f) {
        this.radius = f;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.mRectF.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        this.clipPath.reset();
        this.clipPath.addRoundRect(this.mRectF, this.radius, this.radius, Direction.CCW);
        canvas.clipPath(this.clipPath);
        super.onDraw(canvas);
    }
}
