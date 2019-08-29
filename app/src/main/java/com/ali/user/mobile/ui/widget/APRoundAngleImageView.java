package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView.ScaleType;
import com.ali.user.mobile.security.ui.R;

public class APRoundAngleImageView extends APImageView {
    private final String a = "RoundAngleImageView";
    private int b = 5;
    private int c = 5;
    private boolean d = false;
    private int e = 5;
    private int f = 0;
    private boolean g = false;

    public APRoundAngleImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet);
    }

    public APRoundAngleImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    public APRoundAngleImageView(Context context) {
        super(context);
        a(context, null);
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.i);
            this.b = obtainStyledAttributes.getDimensionPixelSize(R.styleable.m, this.b);
            this.c = obtainStyledAttributes.getDimensionPixelSize(R.styleable.l, this.c);
            this.d = obtainStyledAttributes.getBoolean(R.styleable.n, this.d);
            this.e = obtainStyledAttributes.getDimensionPixelSize(R.styleable.k, this.e);
            this.f = obtainStyledAttributes.getColor(R.styleable.j, this.f);
            return;
        }
        float f2 = context.getResources().getDisplayMetrics().density;
        this.b = (int) (((float) this.b) * f2);
        this.c = (int) (((float) this.c) * f2);
    }

    public int getRoundWidth() {
        return this.b;
    }

    public void setRoundWidth(int i) {
        this.b = i;
    }

    public int getRoundHeight() {
        return this.c;
    }

    public void setRoundHeight(int i) {
        this.c = i;
    }

    public boolean isShowRound() {
        return this.d;
    }

    public void setShowRound(boolean z) {
        this.d = z;
    }

    public boolean isRoundDisable() {
        return this.g;
    }

    public void setRoundDisable(boolean z) {
        this.g = z;
    }

    public void draw(Canvas canvas) {
        ScaleToFit scaleToFit;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();
        ScaleType scaleType = getScaleType();
        if (bitmapDrawable == null || this.g) {
            super.draw(canvas);
            return;
        }
        Bitmap bitmap = bitmapDrawable.getBitmap();
        TileMode tileMode = TileMode.CLAMP;
        BitmapShader bitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
        RectF rectF = new RectF(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        RectF rectF2 = new RectF(0.0f, 0.0f, (float) bitmapDrawable.getBitmap().getWidth(), (float) bitmapDrawable.getBitmap().getHeight());
        Matrix matrix = new Matrix();
        if (scaleType.compareTo(ScaleType.FIT_XY) == 0) {
            scaleToFit = ScaleToFit.FILL;
        } else {
            scaleToFit = ScaleToFit.CENTER;
        }
        matrix.setRectToRect(rectF2, rectF, scaleToFit);
        bitmapShader.setLocalMatrix(matrix);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        if (this.d) {
            Paint paint2 = new Paint();
            paint.setAntiAlias(true);
            paint2.setColor(this.f);
            canvas.drawCircle((float) (getHeight() / 2), (float) (getWidth() / 2), (float) ((getWidth() / 2) - this.e), paint2);
            canvas.drawCircle((float) (getHeight() / 2), (float) (getWidth() / 2), (float) ((getWidth() / 2) - this.e), paint);
            return;
        }
        canvas.drawRoundRect(rectF, (float) this.b, (float) this.c, paint);
    }
}
