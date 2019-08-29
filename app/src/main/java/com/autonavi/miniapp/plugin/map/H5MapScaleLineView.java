package com.autonavi.miniapp.plugin.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.util.AttributeSet;
import android.view.View;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.DPoint;

public class H5MapScaleLineView extends View {
    public static final int MAX_LINE_WIDTH = 500;
    public int centerX;
    public int centerY;
    private int colorText = -16777216;
    private int colorTextOutline = -1;
    public float curLevel = 0.0f;
    private Bitmap logoBitmap = null;
    public boolean mAlignRight = true;
    Context mContext;
    private bty mapView;
    private int textLineMargin = 0;

    public H5MapScaleLineView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.textLineMargin = getResources().getDimensionPixelSize(R.dimen.scaline_text_line_margin);
    }

    public void setMapView(bty bty) {
        this.mapView = bty;
    }

    public void refresh() {
        try {
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postRefresh() {
        try {
            postInvalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mapView != null && this.mapView.k()) {
            paintScaleLine(canvas);
            paintLogoBitmap(canvas);
            super.onDraw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void paintScaleLine(Canvas canvas) {
        int i;
        Canvas canvas2 = canvas;
        this.curLevel = this.mapView.v();
        int height = ((this.logoBitmap == null || this.logoBitmap.isRecycled()) ? 0 : this.logoBitmap.getHeight()) + getPaddingBottom() + agn.a(this.mContext, 2.0f);
        int height2 = getHeight();
        int width = getWidth();
        int i2 = (int) (this.curLevel - 1.0f);
        this.centerX = this.mapView.p();
        this.centerY = this.mapView.q();
        int scaleLineLength = (int) getScaleLineLength();
        while (scaleLineLength > 500 && i2 < 19) {
            i2++;
            scaleLineLength = (int) (((float) scaleLineLength) / (((float) cer.b(i2)) / ((float) cer.b(i2))));
        }
        String a = cer.a(i2);
        Paint paint = new Paint();
        paint.setTextSize((float) agn.a(getContext(), 14.0f));
        paint.setColor(this.colorTextOutline);
        paint.setAntiAlias(true);
        int measureText = (int) paint.measureText(a);
        if (this.mAlignRight) {
            i = Math.min(((width - getPaddingRight()) - scaleLineLength) + ((scaleLineLength - measureText) >> 1), width - measureText);
        } else {
            i = getPaddingLeft() + agn.a(getContext(), 4.0f);
        }
        int i3 = (height2 - height) - 5;
        canvas2.drawText(a, (float) (i - 1), (float) (i3 - this.textLineMargin), paint);
        canvas2.drawText(a, (float) (i + 1), (float) (i3 - this.textLineMargin), paint);
        float f = (float) i;
        canvas2.drawText(a, f, (float) ((i3 - 1) - this.textLineMargin), paint);
        canvas2.drawText(a, f, (float) ((i3 + 1) - this.textLineMargin), paint);
        paint.setColor(this.colorText);
        canvas2.drawText(a, f, (float) (i3 - this.textLineMargin), paint);
        paint.setStrokeWidth(2.0f);
        paint.setColor(this.colorTextOutline);
        paint.setStrokeCap(Cap.SQUARE);
        if (this.mAlignRight) {
            float paddingRight = ((float) width) - ((float) getPaddingRight());
            float f2 = paddingRight - 1.0f;
            float f3 = (float) scaleLineLength;
            float f4 = ((float) height2) - ((float) height);
            float f5 = f4 - 2.0f;
            float f6 = f5 - 1.0f;
            float f7 = paddingRight - f3;
            float f8 = f7 - 1.0f;
            float f9 = 2.0f + f4;
            float f10 = f9 - 1.0f;
            Canvas canvas3 = canvas2;
            float f11 = f6;
            float f12 = f10;
            Paint paint2 = paint;
            canvas3.drawLine(f2 - f3, f11, f8, f12, paint2);
            float f13 = f2;
            float f14 = f2;
            canvas3.drawLine(f13, f11, f14, f12, paint2);
            float f15 = f4 - 1.0f;
            canvas3.drawLine(f13, f15, f14, f15, paint2);
            float f16 = f5 + 1.0f;
            float f17 = f9 + 1.0f;
            float f18 = f16;
            float f19 = f17;
            canvas3.drawLine(f8, f18, f8, f19, paint2);
            float f20 = f2;
            canvas3.drawLine(f2, f18, f20, f19, paint2);
            float f21 = f4 + 1.0f;
            canvas3.drawLine(f8, f21, f20, f21, paint2);
            float f22 = f7 + 1.0f;
            float f23 = f6;
            float f24 = f10;
            canvas3.drawLine(f22, f23, f22, f24, paint2);
            float f25 = 1.0f + paddingRight;
            float f26 = f25;
            canvas3.drawLine(f25, f23, f26, f24, paint2);
            float f27 = f22;
            canvas3.drawLine(f27, f15, f26, f15, paint2);
            float f28 = f16;
            float f29 = f17;
            canvas3.drawLine(f27, f28, f22, f29, paint2);
            float f30 = f25;
            canvas3.drawLine(f25, f28, f30, f29, paint2);
            canvas3.drawLine(f22, f21, f30, f21, paint2);
            paint.setColor(this.colorText);
            Canvas canvas4 = canvas2;
            float f31 = f5;
            float f32 = f9;
            canvas4.drawLine(f7, f31, f7, f32, paint2);
            float f33 = paddingRight;
            canvas4.drawLine(paddingRight, f31, f33, f32, paint2);
            canvas4.drawLine(f7, f4, f33, f4, paint2);
            return;
        }
        float paddingLeft = (float) (getPaddingLeft() + agn.a(getContext(), 6.0f));
        float f34 = paddingLeft + 1.0f;
        float f35 = ((float) height2) - ((float) height);
        float f36 = f35 - 2.0f;
        float f37 = f36 - 1.0f;
        float f38 = paddingLeft - 1.0f;
        Canvas canvas5 = canvas2;
        float f39 = f37;
        float f40 = f38;
        Paint paint3 = paint;
        canvas5.drawLine(f34, f39, f40, f37, paint3);
        float f41 = 2.0f + f35;
        float f42 = f41 + 1.0f;
        float f43 = f38;
        float f44 = f42;
        canvas5.drawLine(f43, f39, f40, f44, paint3);
        float f45 = f42;
        float f46 = f34;
        canvas5.drawLine(f43, f45, f46, f44, paint3);
        float f47 = f41 - 1.0f;
        float f48 = f34;
        float f49 = f47;
        canvas5.drawLine(f48, f45, f46, f49, paint3);
        float f50 = paddingLeft + ((float) scaleLineLength);
        float f51 = f50 - 1.0f;
        Canvas canvas6 = canvas2;
        float f52 = f47;
        float f53 = f51;
        canvas6.drawLine(f48, f52, f53, f49, paint3);
        float f54 = f51;
        float f55 = f42;
        canvas6.drawLine(f54, f52, f53, f55, paint3);
        float f56 = f50 + 1.0f;
        float f57 = f42;
        float f58 = f56;
        canvas6.drawLine(f54, f57, f58, f55, paint3);
        float f59 = f56;
        float f60 = f37;
        canvas6.drawLine(f59, f57, f58, f60, paint3);
        float f61 = f37;
        float f62 = f51;
        canvas6.drawLine(f59, f61, f62, f60, paint3);
        float f63 = 1.0f + f36;
        float f64 = f51;
        float f65 = f63;
        canvas6.drawLine(f64, f61, f62, f65, paint3);
        float f66 = f63;
        float f67 = f34;
        canvas6.drawLine(f64, f66, f67, f65, paint3);
        canvas6.drawLine(f34, f66, f67, f37, paint3);
        paint.setColor(this.colorText);
        Canvas canvas7 = canvas2;
        float f68 = f36;
        float f69 = f41;
        canvas7.drawLine(f50, f68, f50, f69, paint3);
        float f70 = paddingLeft;
        canvas7.drawLine(paddingLeft, f68, f70, f69, paint3);
        canvas7.drawLine(f50, f35, f70, f35, paint3);
    }

    private void paintLogoBitmap(Canvas canvas) {
        float f;
        if (this.logoBitmap != null && !this.logoBitmap.isRecycled()) {
            Paint paint = new Paint();
            if (this.mAlignRight) {
                f = (float) ((getWidth() - this.logoBitmap.getWidth()) - getPaddingRight());
            } else {
                f = (float) getPaddingLeft();
            }
            canvas.drawBitmap(this.logoBitmap, f, (float) ((getHeight() - getPaddingBottom()) - this.logoBitmap.getHeight()), paint);
        }
    }

    public void setBitmap(Bitmap bitmap) {
        this.logoBitmap = bitmap;
    }

    public void setScaleLineColor(int i, int i2) {
        this.colorText = i;
        this.colorTextOutline = i2;
    }

    public float getScaleLineLength() {
        if (this.mapView == null || !this.mapView.k()) {
            return 0.0f;
        }
        float v = this.mapView.v();
        DPoint a = cfg.a((long) this.centerX, (long) this.centerY);
        int i = ((int) v) - 1;
        float U = this.mapView.U();
        a.y = a.y > 0.0d ? Math.min(71.0d, a.y) : Math.max(-71.0d, a.y);
        return (float) (((double) cer.b(i)) / (((double) ((float) ((((Math.cos((a.y * 3.141592653589793d) / 180.0d) * 2.0d) * 3.141592653589793d) * 6378137.0d) / (Math.pow(2.0d, (double) v) * 256.0d)))) * ((double) U)));
    }

    public static boolean isLevelChanged(float f, bty bty) {
        return bty != null && ((double) Math.abs(f - bty.v())) > 1.0E-7d;
    }

    public void refreshScaleLineView() {
        if (Math.abs(this.curLevel - this.mapView.v()) > 1.0E-7f) {
            postRefresh();
        }
    }

    public void setScaleColor(int i, int i2) {
        if (Math.abs(this.curLevel - this.mapView.v()) > 1.0E-7f) {
            postRefresh();
        }
    }

    public boolean needrefreshLogo() {
        return Math.abs(this.curLevel - this.mapView.v()) > 1.0E-7f;
    }
}
