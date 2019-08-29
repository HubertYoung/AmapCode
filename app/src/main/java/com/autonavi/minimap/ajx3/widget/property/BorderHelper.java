package com.autonavi.minimap.ajx3.widget.property;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.View;
import com.autonavi.minimap.ajx3.image.PictureFactory;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.util.AjxALCLog;
import com.autonavi.minimap.ajx3.util.HardwareAdapterUtils;

public class BorderHelper {
    private boolean hardwareFirstLog = true;
    private boolean isColorInvalid = false;
    private boolean isInvalid = false;
    private final BaseProperty mAttribute;
    private final PictureParams mBgParams;
    private Paint mBorderPaint = null;
    private Path mBorderPath = null;
    private int mHeight = -1;
    private int mSaveCount = -1;
    private Paint mShapePaint = null;
    private Path mShapePath = null;
    private final View mView;
    private int mWidth = -1;

    public BorderHelper(BaseProperty baseProperty, PictureParams pictureParams) {
        this.mAttribute = baseProperty;
        this.mBgParams = pictureParams;
        this.mView = baseProperty.mView;
    }

    public void notifyRadiusInvalid() {
        this.isInvalid = true;
    }

    public void notifyBorderWidthInvalid() {
        this.isInvalid = true;
    }

    public void notifyBorderColorInvalid() {
        this.isInvalid = true;
        this.isColorInvalid = true;
    }

    public void beforeDraw(Canvas canvas, boolean z) {
        if (this.mAttribute.canSupportBorderClip()) {
            boolean isHardwareAccelerated = this.mView.isHardwareAccelerated();
            int i = 1;
            boolean z2 = !z;
            boolean isHardwareAcceleratedForBorder = HardwareAdapterUtils.isHardwareAcceleratedForBorder();
            boolean z3 = isHardwareAccelerated && z2 && isHardwareAcceleratedForBorder;
            if (z3) {
                i = 2;
            }
            if (this.mView.getLayerType() != i) {
                this.mView.setLayerType(i, null);
            }
            if (this.hardwareFirstLog && !z3) {
                this.hardwareFirstLog = false;
                StringBuilder sb = new StringBuilder("AJX draw border disableHardware. hardware1=");
                sb.append(isHardwareAccelerated);
                sb.append(", hardware2=");
                sb.append(z2);
                sb.append(", hardware3=");
                sb.append(isHardwareAcceleratedForBorder);
                sb.append(".");
                AjxALCLog.debug(AjxALCLog.TAG_PERFORMANCE, sb.toString());
            }
            this.mSaveCount = canvas.saveLayer(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), null, 31);
        }
    }

    public void afterDraw(Canvas canvas, boolean z) {
        if (this.mAttribute.canSupportBorderClip()) {
            drawBorderAndRadius(canvas);
            if (this.mSaveCount != -1) {
                canvas.restoreToCount(this.mSaveCount);
                this.mSaveCount = -1;
            }
        }
    }

    private void drawBorderAndRadius(Canvas canvas) {
        if (canvas != null) {
            int width = this.mView.getWidth();
            int height = this.mView.getHeight();
            if (width > 0 && height > 0) {
                if (!(width == this.mWidth && height == this.mHeight)) {
                    this.isInvalid = true;
                    this.mWidth = width;
                    this.mHeight = height;
                }
                Path path = this.mShapePath;
                Path path2 = this.mBorderPath;
                if (this.isInvalid) {
                    RectF rectF = new RectF(0.0f, 0.0f, (float) this.mWidth, (float) this.mHeight);
                    RectF calculateBorder = calculateBorder(this.mBgParams.borderWidth, null);
                    float[] calculateRadius = calculateRadius(this.mBgParams.cornerRadius, null);
                    RectF calculateInnerShape = calculateInnerShape(calculateBorder, rectF, null);
                    float[] calculateInnerRadius = calculateInnerRadius(calculateBorder, calculateRadius, null);
                    Path updateShapePath = updateShapePath(rectF, calculateInnerShape, calculateRadius, calculateInnerRadius);
                    path2 = updateBorderPath(rectF, calculateInnerShape, calculateRadius, calculateInnerRadius);
                    path = updateShapePath;
                }
                if (path != null) {
                    canvas.drawPath(path, getShapePaint());
                }
                if (path2 != null) {
                    canvas.drawPath(path2, getBorderPaint());
                }
                this.isInvalid = false;
                this.isColorInvalid = false;
            }
        }
    }

    private Paint getShapePaint() {
        if (this.mShapePaint != null) {
            return this.mShapePaint;
        }
        this.mShapePaint = new Paint();
        this.mShapePaint.setAntiAlias(true);
        this.mShapePaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        return this.mShapePaint;
    }

    private Paint getBorderPaint() {
        if (!this.isColorInvalid && this.mBorderPaint != null) {
            return this.mBorderPaint;
        }
        if (this.mBorderPaint == null) {
            this.mBorderPaint = new Paint();
        }
        this.mBorderPaint.reset();
        this.mBorderPaint.setAntiAlias(true);
        this.mBorderPaint.setColor(this.mBgParams.borderColor);
        return this.mBorderPaint;
    }

    private Path updateShapePath(@NonNull RectF rectF, RectF rectF2, float[] fArr, float[] fArr2) {
        Path path = null;
        if (rectF2 == null && fArr == null && fArr2 == null) {
            return null;
        }
        if (rectF2 != null) {
            if (fArr2 != null) {
                path = getShapePath();
                path.addRect(rectF, Direction.CCW);
                path.addRoundRect(rectF2, fArr2, Direction.CW);
            } else {
                path = getShapePath();
                path.addRect(rectF, Direction.CCW);
                path.addRect(rectF2, Direction.CW);
            }
        } else if (fArr != null) {
            path = getShapePath();
            path.addRect(rectF, Direction.CCW);
            path.addRoundRect(rectF, fArr, Direction.CW);
        }
        this.mShapePath = path;
        return path;
    }

    private Path getShapePath() {
        Path path = this.mShapePath;
        if (path == null) {
            return new Path();
        }
        path.reset();
        return path;
    }

    private Path getBorderPath() {
        Path path = this.mBorderPath;
        if (path == null) {
            return new Path();
        }
        path.reset();
        return path;
    }

    private Path updateBorderPath(RectF rectF, RectF rectF2, float[] fArr, float[] fArr2) {
        Path path;
        if (rectF2 == null) {
            path = null;
        } else if (fArr == null || fArr.length != 8) {
            path = getBorderPath();
            path.addRect(rectF, Direction.CCW);
            path.addRect(rectF2, Direction.CW);
        } else {
            path = getBorderPath();
            path.addRoundRect(rectF, fArr, Direction.CCW);
            path.addRoundRect(rectF2, fArr2, Direction.CW);
        }
        this.mBorderPath = path;
        return path;
    }

    private float[] calculateInnerRadius(RectF rectF, float[] fArr, float[] fArr2) {
        if (rectF == null || fArr == null || fArr.length != 8) {
            return fArr2;
        }
        if (fArr2 == null) {
            fArr2 = new float[8];
        }
        fArr2[0] = fArr[0] - rectF.left;
        fArr2[1] = fArr[1] - rectF.top;
        fArr2[2] = fArr[2] - rectF.right;
        fArr2[3] = fArr[3] - rectF.top;
        fArr2[4] = fArr[4] - rectF.left;
        fArr2[5] = fArr[5] - rectF.bottom;
        fArr2[6] = fArr[6] - rectF.right;
        fArr2[7] = fArr[7] - rectF.bottom;
        for (int i = 0; i < 8; i++) {
            float f = 0.0f;
            if (fArr2[i] >= 0.0f) {
                f = fArr2[i];
            }
            fArr2[i] = f;
        }
        return fArr2;
    }

    private RectF calculateInnerShape(RectF rectF, RectF rectF2, RectF rectF3) {
        if (rectF == null || rectF2 == null) {
            return rectF3;
        }
        float f = rectF2.left + rectF.left;
        float f2 = rectF2.top + rectF.top;
        float f3 = rectF2.right - rectF.right;
        float f4 = rectF2.bottom - rectF.bottom;
        if (rectF3 == null) {
            rectF3 = new RectF();
        }
        rectF3.set(f, f2, f3, f4);
        return rectF3;
    }

    private float[] calculateRadius(int[] iArr, float[] fArr) {
        if (!PictureFactory.hasCornerRadius(iArr)) {
            return fArr;
        }
        if (fArr == null) {
            fArr = new float[8];
        }
        for (int i = 0; i < 8; i++) {
            fArr[i] = (float) iArr[i / 2];
        }
        return fArr;
    }

    private RectF calculateBorder(int[] iArr, RectF rectF) {
        if (!PictureFactory.hasBorder(iArr)) {
            return rectF;
        }
        if (rectF == null) {
            rectF = new RectF();
        }
        rectF.set((float) iArr[0], (float) iArr[1], (float) iArr[2], (float) iArr[3]);
        return rectF;
    }
}
