package com.alipay.biometrics.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;
import com.alipay.mobile.security.bio.utils.BioLog;

public class WaveView extends View {
    private static final float DEFAULT_AMPLITUDE_RATIO = 0.05f;
    public static final int DEFAULT_BEHIND_WAVE_COLOR = Color.parseColor("#28FFFFFF");
    public static final int DEFAULT_FRONT_WAVE_COLOR = Color.parseColor("#3CFFFFFF");
    private static final float DEFAULT_WATER_LEVEL_RATIO = 0.5f;
    private static final float DEFAULT_WAVE_LENGTH_RATIO = 1.0f;
    public static final ShapeType DEFAULT_WAVE_SHAPE = ShapeType.CIRCLE;
    private static final float DEFAULT_WAVE_SHIFT_RATIO = 0.0f;
    private float mAmplitudeRatio = DEFAULT_AMPLITUDE_RATIO;
    private int mBehindWaveColor = DEFAULT_BEHIND_WAVE_COLOR;
    private Paint mBorderPaint;
    private float mDefaultAmplitude;
    private double mDefaultAngularFrequency;
    private float mDefaultWaterLevel;
    private float mDefaultWaveLength;
    private int mFrontWaveColor = DEFAULT_FRONT_WAVE_COLOR;
    private Matrix mShaderMatrix;
    private ShapeType mShapeType = DEFAULT_WAVE_SHAPE;
    private boolean mShowWave;
    private Paint mViewPaint;
    private float mWaterLevelRatio = DEFAULT_WATER_LEVEL_RATIO;
    private float mWaveLengthRatio = 1.0f;
    private BitmapShader mWaveShader;
    private float mWaveShiftRatio = 0.0f;

    public enum ShapeType {
        CIRCLE,
        SQUARE
    }

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public WaveView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.mShaderMatrix = new Matrix();
        this.mViewPaint = new Paint();
        this.mViewPaint.setAntiAlias(true);
    }

    public float getWaveShiftRatio() {
        return this.mWaveShiftRatio;
    }

    public void setWaveShiftRatio(float f) {
        if (this.mWaveShiftRatio != f) {
            this.mWaveShiftRatio = f;
            invalidate();
        }
    }

    public float getWaterLevelRatio() {
        return this.mWaterLevelRatio;
    }

    public void setWaterLevelRatio(float f) {
        if (this.mWaterLevelRatio != f) {
            this.mWaterLevelRatio = f;
            invalidate();
        }
    }

    public float getAmplitudeRatio() {
        return this.mAmplitudeRatio;
    }

    public void setAmplitudeRatio(float f) {
        if (this.mAmplitudeRatio != f) {
            this.mAmplitudeRatio = f;
            invalidate();
        }
    }

    public float getWaveLengthRatio() {
        return this.mWaveLengthRatio;
    }

    public void setWaveLengthRatio(float f) {
        this.mWaveLengthRatio = f;
    }

    public boolean isShowWave() {
        return this.mShowWave;
    }

    public void setShowWave(boolean z) {
        this.mShowWave = z;
    }

    public void setBorder(int i, int i2) {
        if (this.mBorderPaint == null) {
            this.mBorderPaint = new Paint();
            this.mBorderPaint.setAntiAlias(true);
            this.mBorderPaint.setStyle(Style.STROKE);
        }
        this.mBorderPaint.setColor(i2);
        this.mBorderPaint.setStrokeWidth((float) i);
        invalidate();
    }

    public void setWaveColor(int i, int i2) {
        this.mBehindWaveColor = i;
        this.mFrontWaveColor = i2;
        this.mWaveShader = null;
        createShader();
        invalidate();
    }

    public void setShapeType(ShapeType shapeType) {
        this.mShapeType = shapeType;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        createShader();
    }

    private void createShader() {
        this.mDefaultAngularFrequency = 6.283185307179586d / ((double) getWidth());
        this.mDefaultAmplitude = ((float) getHeight()) * DEFAULT_AMPLITUDE_RATIO;
        this.mDefaultWaterLevel = ((float) getHeight()) * DEFAULT_WATER_LEVEL_RATIO;
        this.mDefaultWaveLength = (float) getWidth();
        try {
            Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint();
            paint.setStrokeWidth(2.0f);
            paint.setAntiAlias(true);
            int width = getWidth() + 1;
            int height = getHeight() + 1;
            float[] fArr = new float[width];
            paint.setColor(this.mBehindWaveColor);
            for (int i = 0; i < width; i++) {
                float sin = (float) ((Math.sin(((double) i) * this.mDefaultAngularFrequency) * ((double) this.mDefaultAmplitude)) + ((double) this.mDefaultWaterLevel));
                canvas.drawLine((float) i, sin, (float) i, (float) height, paint);
                fArr[i] = sin;
            }
            paint.setColor(this.mFrontWaveColor);
            int i2 = (int) (this.mDefaultWaveLength / 4.0f);
            for (int i3 = 0; i3 < width; i3++) {
                canvas.drawLine((float) i3, fArr[(i3 + i2) % width], (float) i3, (float) height, paint);
            }
            this.mWaveShader = new BitmapShader(createBitmap, TileMode.REPEAT, TileMode.CLAMP);
            this.mViewPaint.setShader(this.mWaveShader);
        } catch (OutOfMemoryError e) {
            BioLog.e(e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float f = 0.0f;
        if (!this.mShowWave || this.mWaveShader == null) {
            this.mViewPaint.setShader(null);
            return;
        }
        if (this.mViewPaint.getShader() == null) {
            this.mViewPaint.setShader(this.mWaveShader);
        }
        this.mShaderMatrix.setScale(this.mWaveLengthRatio / 1.0f, this.mAmplitudeRatio / DEFAULT_AMPLITUDE_RATIO, 0.0f, this.mDefaultWaterLevel);
        this.mShaderMatrix.postTranslate(this.mWaveShiftRatio * ((float) getWidth()), (DEFAULT_WATER_LEVEL_RATIO - this.mWaterLevelRatio) * ((float) getHeight()));
        this.mWaveShader.setLocalMatrix(this.mShaderMatrix);
        if (this.mBorderPaint != null) {
            f = this.mBorderPaint.getStrokeWidth();
        }
        switch (this.mShapeType) {
            case CIRCLE:
                paintCircleShape(canvas, f);
                return;
            case SQUARE:
                paintSquare(canvas, f);
                return;
            default:
                return;
        }
    }

    private void paintSquare(Canvas canvas, float f) {
        if (f > 0.0f) {
            canvas.drawRect(f / 2.0f, f / 2.0f, (((float) getWidth()) - (f / 2.0f)) - DEFAULT_WATER_LEVEL_RATIO, (((float) getHeight()) - (f / 2.0f)) - DEFAULT_WATER_LEVEL_RATIO, this.mBorderPaint);
        }
        canvas.drawRect(f, f, ((float) getWidth()) - f, ((float) getHeight()) - f, this.mViewPaint);
    }

    private void paintCircleShape(Canvas canvas, float f) {
        if (f > 0.0f) {
            canvas.drawCircle(((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f, ((((float) getWidth()) - f) / 2.0f) - 1.0f, this.mBorderPaint);
        }
        canvas.drawCircle(((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f, (((float) getWidth()) / 2.0f) - f, this.mViewPaint);
    }
}
