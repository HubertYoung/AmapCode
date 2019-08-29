package com.autonavi.minimap.ajx3.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.widget.property.PictureHelper;

public class AlmightyShape extends RectShape {
    private boolean isBgShaderDirty = false;
    private boolean isNinePatch = false;
    private String mBackground;
    @ColorInt
    public int mBgColor;
    private Path mBgColorPath;
    private RectF mBgColorRect;
    private Drawable mBgImage = null;
    private Path mBgImagePath;
    private RectF mBgImageRect;
    private BitmapShader mBgShader = null;
    private Bitmap mBitmap = null;
    @ColorInt
    public int mBorderColor;
    private Path mBorderPath;
    private RectF mBorderRect;
    private float mDensity = 2.0f;
    private float mImageSize = 2.0f;
    private Paint mPaint;
    private float[] mRadii;
    private int mScaleType = 0;
    private Shader mShader;
    private float mShapeH;
    private float mShapeW;
    private Matrix matrix = null;

    public static class ScaleType {
        public static final int SCALEMODE_ADAPT = 3;
        public static final int SCALEMODE_ASPECTFILL = 2;
        public static final int SCALEMODE_ASPECTFIT = 1;
        public static final int SCALEMODE_FILL = 0;
    }

    public AlmightyShape(Resources resources, PictureParams pictureParams, Bitmap bitmap) {
        int[] iArr = pictureParams.cornerRadius;
        if (iArr != null && iArr.length == 4) {
            this.mRadii = new float[8];
            for (int i = 0; i < 8; i++) {
                this.mRadii[i] = (float) iArr[i / 2];
            }
        }
        int[] iArr2 = pictureParams.borderWidth;
        if (iArr2 != null && iArr2.length == 4) {
            this.mBorderRect = new RectF((float) iArr2[3], (float) iArr2[0], (float) iArr2[1], (float) iArr2[2]);
        }
        this.mBgColorRect = new RectF();
        this.mBgImageRect = new RectF();
        this.mBorderPath = new Path();
        this.mBgColorPath = new Path();
        this.mBgImagePath = new Path();
        this.mImageSize = pictureParams.imageSize;
        this.mScaleType = pictureParams.scaleMode;
        this.mDensity = resources.getDisplayMetrics().density;
        this.mBgColor = pictureParams.bgColor;
        this.mBorderColor = pictureParams.borderColor;
        this.mBackground = pictureParams.background;
        if (TextUtils.isEmpty(this.mBackground) && bitmap != null) {
            this.matrix = new Matrix();
            this.mBitmap = bitmap;
            this.isNinePatch = PictureFactory.hasStretch(pictureParams.stretch);
            if (this.isNinePatch) {
                this.mBgImage = NinePatch.create(resources, bitmap, pictureParams.stretch, pictureParams.imageSize);
            }
        }
    }

    private void initBgColorPaint() {
        if (this.mPaint == null) {
            this.mPaint = new Paint();
        }
        this.mPaint.reset();
        this.mPaint.setAntiAlias(true);
        if (this.mShader != null) {
            this.mPaint.setShader(this.mShader);
        } else {
            this.mPaint.setColor(this.mBgColor);
        }
    }

    private void initBorderPaint() {
        if (this.mPaint == null) {
            this.mPaint = new Paint();
        }
        this.mPaint.reset();
        this.mPaint.setColor(this.mBorderColor);
        this.mPaint.setAntiAlias(true);
    }

    private void initBgImagePaint(Matrix matrix2) {
        if (this.mPaint == null) {
            this.mPaint = new Paint();
        }
        this.mPaint.reset();
        this.mPaint.setColor(-16777216);
        this.mPaint.setAntiAlias(true);
        if (this.mBitmap != null) {
            if (this.mBgShader == null || this.isBgShaderDirty) {
                Bitmap bitmap = this.mBitmap;
                TileMode tileMode = TileMode.CLAMP;
                this.mBgShader = new BitmapShader(bitmap, tileMode, tileMode);
            }
            this.mPaint.setShader(this.mBgShader);
        }
        if (matrix2 != null) {
            this.mPaint.getShader().setLocalMatrix(matrix2);
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        float f;
        int i;
        int i2;
        if (!(this.mShader == null && this.mBgColor == 0)) {
            initBgColorPaint();
            canvas.drawPath(this.mBgColorPath, this.mPaint);
        }
        if (this.mBitmap != null) {
            float width = (float) this.mBitmap.getWidth();
            float height = (float) this.mBitmap.getHeight();
            float f2 = this.mShapeW;
            float f3 = this.mShapeH;
            if (this.mScaleType == 1 && !this.isNinePatch) {
                float f4 = width / f2;
                float f5 = height / f3;
                if (f5 > f4) {
                    i2 = (int) f3;
                    i = Math.round(((float) i2) * (width / height));
                } else {
                    int i3 = (int) f2;
                    float f6 = f4;
                    i2 = Math.round(((float) i3) * (height / width));
                    i = i3;
                    f5 = f6;
                }
                float f7 = 1.0f / f5;
                this.matrix.setScale(f7, f7);
                this.matrix.postTranslate((float) Math.round((f2 - ((float) i)) / 2.0f), (float) Math.round((f3 - ((float) i2)) / 2.0f));
            } else if (this.mScaleType == 2 && !this.isNinePatch) {
                float f8 = width / f2;
                float f9 = height / f3;
                float f10 = 0.0f;
                if (f9 > f8) {
                    f10 = (-((height / f8) - f3)) / 2.0f;
                    f = 0.0f;
                } else {
                    f = (-((width / f9) - f2)) / 2.0f;
                    f8 = f9;
                }
                float f11 = 1.0f / f8;
                this.matrix.setScale(f11, f11);
                this.matrix.postTranslate((float) Math.round(f), (float) Math.round(f10));
            } else if (this.mScaleType != 3 || this.isNinePatch) {
                double d = (double) (f2 / width);
                double d2 = (double) (f3 / height);
                this.matrix.set(null);
                this.matrix.postTranslate((float) (0.0d / d), (float) (0.0d / d2));
                this.matrix.postScale((float) d, (float) d2);
            } else {
                this.matrix.set(null);
                this.matrix.postScale(this.mDensity / this.mImageSize, this.mDensity / this.mImageSize);
                this.matrix.postTranslate((float) Math.round((f2 - ((width * this.mDensity) / this.mImageSize)) / 2.0f), (float) Math.round((f3 - ((height * this.mDensity) / this.mImageSize)) / 2.0f));
            }
            initBgImagePaint(this.matrix);
            canvas.drawPath(this.mBgImagePath, this.mPaint);
        }
        if (this.mBorderRect != null) {
            initBorderPaint();
            canvas.drawPath(this.mBorderPath, this.mPaint);
        }
    }

    /* access modifiers changed from: protected */
    public void onResize(float f, float f2) {
        super.onResize(f, f2);
        this.mShapeW = f;
        this.mShapeH = f2;
        RectF rect = rect();
        this.mBorderPath.reset();
        this.mBgColorPath.reset();
        this.mBgImagePath.reset();
        if (this.mBorderRect != null) {
            operateBorderRectAndPath(rect);
        }
        operateBgColorRectAndPath(rect, f, f2);
        operateBgImageRectAndPath(rect, f, f2);
        this.mShader = PictureHelper.getShader(this.mBackground, f, f2);
    }

    private void operateBorderRectAndPath(RectF rectF) {
        if (this.mRadii != null) {
            this.mBorderPath.addRoundRect(rectF, this.mRadii, Direction.CW);
        } else {
            this.mBorderPath.addRect(rectF, Direction.CW);
        }
    }

    private void operateBgColorRectAndPath(RectF rectF, float f, float f2) {
        RectF rectF2 = this.mBgColorRect;
        float f3 = 0.0f;
        float f4 = rectF.left + (this.mBorderRect == null ? 0.0f : this.mBorderRect.left);
        float f5 = rectF.top + (this.mBorderRect == null ? 0.0f : this.mBorderRect.top);
        float f6 = rectF.right - (this.mBorderRect == null ? 0.0f : this.mBorderRect.right);
        float f7 = rectF.bottom;
        if (this.mBorderRect != null) {
            f3 = this.mBorderRect.bottom;
        }
        rectF2.set(f4, f5, f6, f7 - f3);
        if (this.mBgColorRect.width() <= f && this.mBgColorRect.height() <= f2) {
            if (this.mRadii != null) {
                if (this.mBorderRect != null) {
                    this.mBorderPath.addRoundRect(this.mBgColorRect, this.mRadii, Direction.CCW);
                }
                this.mBgColorPath.addRoundRect(this.mBgColorRect, this.mRadii, Direction.CCW);
                return;
            }
            if (this.mBorderRect != null) {
                this.mBorderPath.addRect(this.mBgColorRect, Direction.CCW);
            }
            this.mBgColorPath.addRect(this.mBgColorRect, Direction.CCW);
        }
    }

    private void operateBgImageRectAndPath(RectF rectF, float f, float f2) {
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        if (this.mScaleType == 1) {
            if (this.mBitmap != null) {
                float width = (float) this.mBitmap.getWidth();
                float height = (float) this.mBitmap.getHeight();
                if (height / f2 > width / f) {
                    f9 = f2;
                    f7 = f9;
                    f8 = (float) Math.round((width / height) * f2);
                } else {
                    f8 = (float) ((int) f);
                    f9 = f2;
                    f7 = (float) Math.round((height / width) * f8);
                }
                f10 = f;
            } else {
                f10 = 0.0f;
                f9 = 0.0f;
                f8 = 0.0f;
                f7 = 0.0f;
            }
            float round = (float) Math.round((f10 - f8) / 2.0f);
            float round2 = (float) Math.round((f9 - f7) / 2.0f);
            if (!this.isNinePatch) {
                this.mBgImageRect.set(round, round2, f8 + round, f7 + round2);
            } else {
                this.mBgImageRect.set(rectF.left, rectF.top, rectF.right, rectF.bottom);
            }
        } else if (this.mScaleType == 3) {
            if (this.mBitmap != null) {
                f4 = f2;
                f3 = (((float) this.mBitmap.getHeight()) * this.mDensity) / this.mImageSize;
                f5 = (((float) this.mBitmap.getWidth()) * this.mDensity) / this.mImageSize;
                f6 = f;
            } else {
                f6 = 0.0f;
                f5 = 0.0f;
                f4 = 0.0f;
                f3 = 0.0f;
            }
            float round3 = (float) Math.round((f6 - f5) / 2.0f);
            float round4 = (float) Math.round((f4 - f3) / 2.0f);
            if (!this.isNinePatch) {
                float f11 = round3 >= 0.0f ? round3 : 0.0f;
                float f12 = round4 >= 0.0f ? round4 : 0.0f;
                float f13 = f5 + round3;
                float f14 = round4 + f3;
                if (f13 <= f6) {
                    f6 = f13;
                }
                if (f14 > f4) {
                    f14 = f4;
                }
                this.mBgImageRect.set(f11, f12, f6, f14);
            } else {
                this.mBgImageRect.set(rectF.left, rectF.top, rectF.right, rectF.bottom);
            }
        } else {
            this.mBgImageRect.set(rectF.left, rectF.top, rectF.right, rectF.bottom);
        }
        float width2 = this.mBgImageRect.width();
        float height2 = this.mBgImageRect.height();
        if (width2 <= f && height2 <= f2) {
            if (this.mRadii != null) {
                this.mBgImagePath.addRoundRect(this.mBgImageRect, this.mRadii, Direction.CCW);
            } else {
                this.mBgImagePath.addRect(this.mBgImageRect, Direction.CCW);
            }
        }
        if (this.isNinePatch && width2 > 0.0f && height2 > 0.0f) {
            int i = (int) width2;
            int i2 = (int) height2;
            this.mBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
            this.mBgImage.setBounds(0, 0, i, i2);
            this.mBgImage.draw(new Canvas(this.mBitmap));
        }
        if (this.mBitmap != null) {
            this.isBgShaderDirty = true;
        }
    }
}
