package com.autonavi.bundle.uitemplate.container.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.RelativeLayout;
import com.autonavi.minimap.R;

public class RoundRectRelativeLayout extends RelativeLayout {
    public static final int DRAG_BAR_VERTICAL_DP = 18;
    public static final int SHADOW_HEIGHT_DP = 10;
    public static final int SHADOW_RADIUS_DP = 12;
    private final int H;
    private final int LINE_GAP;
    private final int LINE_WIDTH;
    private final int STROKE_WIDTH;
    private boolean isShowDragBar = true;
    private Float mAnchor = null;
    private float mCenterX;
    private float mCenterY;
    private Path mClipPath = new Path();
    private float mEndX;
    private float mEndY;
    private NinePatch mNinePatch;
    private Float mOffset = null;
    private Paint mPaint;
    private PaintFlagsDrawFilter mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
    private float mStartX;
    private float mStartY;

    public void setShowDragBar(boolean z) {
        this.isShowDragBar = z;
        postInvalidate();
    }

    public RoundRectRelativeLayout(Context context) {
        super(context);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.slidecontainer);
        this.mNinePatch = new NinePatch(decodeResource, decodeResource.getNinePatchChunk(), null);
        this.LINE_WIDTH = dp2px(30);
        this.STROKE_WIDTH = bet.a(context, 4);
        this.LINE_GAP = bet.a(context, 3);
        this.H = dp2px(30);
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.parseColor("#cccccc"));
        this.mPaint.setStrokeWidth((float) this.STROKE_WIDTH);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeCap(Cap.ROUND);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        float f = (float) i;
        this.mClipPath.addRect(new RectF(0.0f, (float) dp2px(22), f, (float) i2), Direction.CCW);
        float f2 = f / 2.0f;
        this.mCenterX = f2;
        this.mCenterY = (float) dp2px(18);
        this.mStartX = f2 - (((float) this.LINE_WIDTH) / 2.0f);
        this.mStartY = (float) dp2px(18);
        this.mEndX = f2 + (((float) this.LINE_WIDTH) / 2.0f);
        this.mEndY = (float) dp2px(18);
        if (this.mOffset != null && this.mAnchor != null) {
            bending(this.mOffset.floatValue(), this.mAnchor.floatValue());
        }
    }

    public void bending(float f, float f2) {
        float f3;
        this.mOffset = Float.valueOf(f);
        this.mAnchor = Float.valueOf(f2);
        float f4 = f - f2;
        if (f4 >= 0.0f) {
            f3 = (((float) this.LINE_GAP) / (1.0f - f2)) * f4;
        } else {
            f3 = (((float) this.LINE_GAP) / f2) * f4;
        }
        this.mCenterY = ((float) dp2px(18)) + f3;
        invalidate();
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.setDrawFilter(this.mPaintFlagsDrawFilter);
        canvas.clipPath(this.mClipPath);
        super.draw(canvas);
        canvas.restore();
        this.mNinePatch.draw(canvas, new Rect(0, 0, getWidth(), dp2px(22)));
        if (this.isShowDragBar) {
            canvas.drawLine(this.mStartX, this.mStartY, this.mCenterX, this.mCenterY, this.mPaint);
            canvas.drawLine(this.mCenterX, this.mCenterY, this.mEndX, this.mEndY, this.mPaint);
        }
    }

    private int dp2px(int i) {
        return (int) ((((float) i) * getResources().getDisplayMetrics().density) + 0.5f);
    }
}
