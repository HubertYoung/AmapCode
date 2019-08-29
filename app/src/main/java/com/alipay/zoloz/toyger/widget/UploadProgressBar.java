package com.alipay.zoloz.toyger.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.view.View;
import com.alipay.zoloz.toyger.R;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;

public class UploadProgressBar extends View {
    public static final int FILL = 1;
    public static final int STROKE = 0;
    final int ANGLE_STEP;
    private float backColorWidth;
    private int backgroundColor;
    private int endAngle;
    public BitmapShader mBitmapShader;
    private Handler mMainHandle;
    private Matrix mMatrix;
    int mProgressAngle;
    private int mWidth;
    private int max;
    private Paint paint;
    private int progress;
    private int radius;
    private int roundColor;
    private int roundProgressColor;
    private boolean roundShader;
    private float roundWidth;
    private int secondProgressColor;
    private int startAngle;
    private int style;
    private int textColor;
    private boolean textIsDisplayable;
    private float textSize;

    public int getRadius() {
        return this.radius;
    }

    public UploadProgressBar(Context context) {
        this(context, null);
    }

    public UploadProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public UploadProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.style = 0;
        this.radius = 0;
        this.mProgressAngle = 0;
        this.ANGLE_STEP = MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_;
        this.paint = new Paint();
        this.mMainHandle = new Handler(Looper.getMainLooper());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.eye_round_progressBar);
        this.roundColor = obtainStyledAttributes.getColor(R.styleable.eye_round_progressBar_eye_round_color, SupportMenu.CATEGORY_MASK);
        this.roundProgressColor = obtainStyledAttributes.getColor(R.styleable.eye_round_progressBar_eye_round_progress_color, -16711936);
        this.secondProgressColor = obtainStyledAttributes.getColor(R.styleable.eye_round_progressBar_eye_round_progress_color, -16711936);
        this.textColor = obtainStyledAttributes.getColor(R.styleable.eye_round_progressBar_eye_text_color, -16711936);
        this.textSize = obtainStyledAttributes.getDimension(R.styleable.eye_round_progressBar_eye_text_size, 15.0f);
        this.roundWidth = obtainStyledAttributes.getDimension(R.styleable.eye_round_progressBar_eye_round_width, 5.0f);
        this.max = obtainStyledAttributes.getInteger(R.styleable.eye_round_progressBar_eye_max, 100);
        this.textIsDisplayable = obtainStyledAttributes.getBoolean(R.styleable.eye_round_progressBar_eye_text_is_displayable, true);
        this.style = obtainStyledAttributes.getInt(R.styleable.eye_round_progressBar_eye_style, 0);
        this.roundShader = obtainStyledAttributes.getBoolean(R.styleable.eye_round_progressBar_eye_progress_shader, false);
        this.backColorWidth = obtainStyledAttributes.getDimension(R.styleable.eye_round_progressBar_eye_color_bg_width, 0.0f);
        this.startAngle = obtainStyledAttributes.getInt(R.styleable.eye_round_progressBar_eye_start_angle, 0);
        this.endAngle = obtainStyledAttributes.getInt(R.styleable.eye_round_progressBar_eye_end_angle, 360);
        this.backgroundColor = obtainStyledAttributes.getColor(R.styleable.eye_round_progressBar_eye_background_color, -1);
        if (this.backColorWidth > 0.0f && this.roundShader) {
            this.mMatrix = new Matrix();
            Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.circle_bg);
            this.mBitmapShader = new BitmapShader(decodeResource, TileMode.CLAMP, TileMode.CLAMP);
            this.mWidth = (int) this.backColorWidth;
            float min = (((float) this.mWidth) * 1.0f) / ((float) Math.min(decodeResource.getWidth(), decodeResource.getHeight()));
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
        int i;
        int i2;
        int i3;
        this.paint.setStyle(Style.STROKE);
        if (this.mBitmapShader != null) {
            this.paint.setShader(this.mBitmapShader);
        }
        int i4 = this.startAngle + 180;
        int i5 = 180 - (this.startAngle * 2);
        canvas.drawArc(rectF, (float) i4, (float) i5, false, this.paint);
        int i6 = this.mProgressAngle % 360;
        if (i6 < i4) {
            if (i6 + MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_ > i4) {
                i = (i6 + MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_) - i4;
                i6 = i4;
            }
            i = 0;
        } else {
            if (i6 <= i4 + i5) {
                if (i6 + MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_ < i4 + i5) {
                    i = 120;
                } else {
                    i = (i4 + i5) - i6;
                }
            }
            i = 0;
        }
        if (i > 0) {
            this.paint.setColor(this.roundProgressColor);
            canvas.drawArc(rectF, (float) i6, (float) i, false, this.paint);
        }
        int i7 = this.mProgressAngle % 360;
        int i8 = this.startAngle;
        int i9 = 180 - (this.startAngle * 2);
        this.paint.setColor(this.roundColor);
        canvas.drawArc(rectF, (float) i8, (float) i9, false, this.paint);
        if (i7 < i8) {
            if (i7 + MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_ > i8) {
                i2 = (i7 + MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_) - i8;
                i3 = i8;
            }
            i2 = 0;
            i3 = i7;
        } else {
            if (i7 <= i8 + i9) {
                if (i7 + MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_ < i8 + i9) {
                    i2 = 120;
                    i3 = i7;
                } else {
                    i2 = (i8 + i9) - i7;
                    i3 = i7;
                }
            }
            i2 = 0;
            i3 = i7;
        }
        if (i2 > 0) {
            this.paint.setColor(this.roundProgressColor);
            canvas.drawArc(rectF, (float) i3, (float) i2, false, this.paint);
        } else if (i3 > 270) {
            int i10 = (this.mProgressAngle + MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_) % 360;
            if (i10 < 180 && i10 > i8) {
                this.paint.setColor(this.roundProgressColor);
                canvas.drawArc(rectF, (float) i8, (float) (i10 - i8), false, this.paint);
            }
        }
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

    public synchronized void setProgressAngle(int i) {
        this.mProgressAngle = i;
        postInvalidate();
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
