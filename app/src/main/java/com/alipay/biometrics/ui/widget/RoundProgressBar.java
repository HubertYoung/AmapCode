package com.alipay.biometrics.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.support.v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.view.View;
import com.alipay.android.phone.mobilecommon.biometric.a.a.b;
import com.alipay.android.phone.mobilecommon.biometric.a.a.f;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.BitmapHelper;

public class RoundProgressBar extends View {
    public static final int FILL = 1;
    public static final int STROKE = 0;
    private float backColorWidth;
    private int backgroundColor;
    private int endAngle;
    public BitmapShader mBitmapShader;
    private Matrix mMatrix;
    private int mWidth;
    private int max;
    private Paint paint;
    private int progress;
    private int radius;
    private int roundColor;
    private int roundProgressColor;
    private boolean roundShader;
    private float roundWidth;
    private int startAngle;
    private int style;
    private int textColor;
    private boolean textIsDisplayable;
    private float textSize;

    public int getRadius() {
        return this.radius;
    }

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.style = 0;
        this.radius = 0;
        this.paint = new Paint();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f.bio_round_progressBar);
        this.roundColor = obtainStyledAttributes.getColor(f.bio_round_progressBar_bio_round_color, SupportMenu.CATEGORY_MASK);
        this.roundProgressColor = obtainStyledAttributes.getColor(f.bio_round_progressBar_bio_round_progress_color, -16711936);
        this.textColor = obtainStyledAttributes.getColor(f.bio_round_progressBar_bio_text_color, -16711936);
        this.textSize = obtainStyledAttributes.getDimension(f.bio_round_progressBar_bio_text_size, 15.0f);
        this.roundWidth = obtainStyledAttributes.getDimension(f.bio_round_progressBar_bio_round_width, 5.0f);
        this.max = obtainStyledAttributes.getInteger(f.bio_round_progressBar_bio_max, 100);
        this.textIsDisplayable = obtainStyledAttributes.getBoolean(f.bio_round_progressBar_bio_text_is_displayable, true);
        this.style = obtainStyledAttributes.getInt(f.bio_round_progressBar_bio_style, 0);
        this.roundShader = obtainStyledAttributes.getBoolean(f.bio_round_progressBar_bio_progress_shader, false);
        this.backColorWidth = obtainStyledAttributes.getDimension(f.bio_round_progressBar_bio_color_bg_width, 0.0f);
        this.startAngle = obtainStyledAttributes.getInt(f.bio_round_progressBar_bio_start_angle, 0);
        BioLog.i("bio startAngle:" + this.startAngle);
        this.endAngle = obtainStyledAttributes.getInt(f.bio_round_progressBar_bio_end_angle, 360);
        this.backgroundColor = obtainStyledAttributes.getColor(f.bio_round_progressBar_bio_background_color, -1);
        if (this.backColorWidth > 0.0f && this.roundShader) {
            this.mMatrix = new Matrix();
            Bitmap readBitMap = BitmapHelper.readBitMap(context, b.circle_bg);
            this.mBitmapShader = new BitmapShader(readBitMap, TileMode.CLAMP, TileMode.CLAMP);
            this.mWidth = (int) this.backColorWidth;
            float min = (((float) this.mWidth) * 1.0f) / ((float) Math.min(readBitMap.getWidth(), readBitMap.getHeight()));
            this.mMatrix.setScale(min, min);
            this.mBitmapShader.setLocalMatrix(this.mMatrix);
        }
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        this.radius = (int) (((float) width) - (this.roundWidth / 2.0f));
        this.paint.setColor(this.roundColor);
        this.paint.setStyle(Style.STROKE);
        this.paint.setStrokeWidth(this.roundWidth);
        this.paint.setAntiAlias(true);
        this.paint.setStrokeCap(Cap.ROUND);
        this.paint.setColor(this.backgroundColor);
        canvas.drawCircle((float) width, (float) width, (float) this.radius, this.paint);
        BioLog.e(String.valueOf(width));
        this.paint.setStrokeWidth(0.0f);
        this.paint.setColor(this.textColor);
        this.paint.setTextSize(this.textSize);
        this.paint.setTypeface(Typeface.DEFAULT_BOLD);
        int i = (int) ((((float) this.progress) / ((float) this.max)) * 100.0f);
        float measureText = this.paint.measureText(i + "%");
        this.paint.setShader(null);
        if (this.textIsDisplayable && i != 0 && this.style == 0) {
            canvas.drawText(i + "%", ((float) width) - (measureText / 2.0f), ((float) width) + (this.textSize / 2.0f), this.paint);
        }
        this.paint.setStrokeWidth(this.roundWidth);
        RectF rectF = new RectF((float) (width - this.radius), (float) (width - this.radius), (float) (this.radius + width), (float) (width + this.radius));
        this.paint.setColor(this.roundColor);
        canvas.drawArc(rectF, (float) (this.startAngle + 90), (float) (this.endAngle - this.startAngle), false, this.paint);
        this.paint.setColor(this.roundProgressColor);
        switch (this.style) {
            case 0:
                paintStroke(canvas, rectF);
                return;
            case 1:
                this.paint.setStyle(Style.FILL_AND_STROKE);
                if (this.progress != 0) {
                    canvas.drawArc(rectF, (float) (this.startAngle + 90), (float) (((this.endAngle - this.startAngle) * this.progress) / this.max), true, this.paint);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void paintStroke(Canvas canvas, RectF rectF) {
        this.paint.setStyle(Style.STROKE);
        if (this.mBitmapShader != null) {
            this.paint.setShader(this.mBitmapShader);
        }
        canvas.drawArc(rectF, (float) (this.startAngle + 90), (float) (((this.endAngle - this.startAngle) * this.progress) / this.max), false, this.paint);
        this.paint.setShader(null);
    }

    public void setBackgroundColor(int i) {
        this.backgroundColor = i;
        postInvalidate();
    }

    public void setRoundColor(int i) {
        this.roundColor = i;
        postInvalidate();
    }

    public synchronized int getMax() {
        return this.max;
    }

    public synchronized void setMax(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = i;
    }

    public synchronized int getProgress() {
        return this.progress;
    }

    public synchronized void setProgress(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (i > this.max) {
            i = this.max;
        }
        if (i <= this.max) {
            this.progress = i;
            postInvalidate();
        }
    }

    public int getCricleColor() {
        return this.roundColor;
    }

    public void setCricleColor(int i) {
        this.roundColor = i;
    }

    public int getCricleProgressColor() {
        return this.roundProgressColor;
    }

    public void setCricleProgressColor(int i) {
        this.roundProgressColor = i;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int i) {
        this.textColor = i;
    }

    public float getTextSize() {
        return this.textSize;
    }

    public void setTextSize(float f) {
        this.textSize = f;
    }

    public float getRoundWidth() {
        return this.roundWidth;
    }

    public void setRoundWidth(float f) {
        this.roundWidth = f;
    }
}
