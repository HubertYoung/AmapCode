package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView.ScaleType;
import com.alipay.mobile.antui.R;

public class AURoundImageView extends AUMaskImage {
    private static final DrawFilter FILTER = new PaintFlagsDrawFilter(0, 3);
    private static final ScaleToFit SCALE_TO_FIT = ScaleToFit.FILL;
    private RectF mCornerRectF = new RectF();
    private int mDiff = 0;
    private Matrix mMatrix = new Matrix();
    private Paint mPaint = new Paint(1);
    private Path mPath = new Path();
    private int mRadiusBottomLeft = 0;
    private int mRadiusBottomRight = 0;
    private int mRadiusTopLeft = 0;
    private int mRadiusTopRight = 0;
    private int mRoundHeight = 0;
    private int mRoundWidth = 0;
    private BitmapShader mShader;

    public AURoundImageView(Context paramContext) {
        super(paramContext);
        init(paramContext, null);
    }

    public AURoundImageView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramContext, paramAttributeSet);
    }

    public AURoundImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext, paramAttributeSet);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
            this.mRoundWidth = a.getDimensionPixelSize(0, this.mRoundWidth);
            this.mRoundHeight = a.getDimensionPixelSize(1, this.mRoundHeight);
            a.recycle();
        } else {
            float density = context.getResources().getDisplayMetrics().density;
            this.mRoundWidth = (int) (((float) this.mRoundWidth) * density);
            this.mRoundHeight = (int) (((float) this.mRoundHeight) * density);
        }
        int i = this.mRoundWidth;
        this.mRadiusBottomLeft = i;
        this.mRadiusBottomRight = i;
        this.mRadiusTopRight = i;
        this.mRadiusTopLeft = i;
        this.mDiff = this.mRoundHeight - this.mRoundWidth;
        setScaleType(ScaleType.CENTER_CROP);
    }

    public void setRoundSize(int width, int height) {
        if (width < 0) {
            width = 0;
        }
        this.mRoundWidth = width;
        if (height < 0) {
            height = 0;
        }
        this.mRoundHeight = height;
        int i = this.mRoundWidth;
        this.mRadiusBottomLeft = i;
        this.mRadiusBottomRight = i;
        this.mRadiusTopRight = i;
        this.mRadiusTopLeft = i;
        this.mDiff = this.mRoundHeight - this.mRoundWidth;
    }

    public void setRoundSize(int radius) {
        if (radius < 0) {
            radius = 0;
        }
        this.mRadiusBottomLeft = radius;
        this.mRadiusBottomRight = radius;
        this.mRadiusTopRight = radius;
        this.mRadiusTopLeft = radius;
    }

    public void setRounded(boolean hasRound) {
        setNeedMask(hasRound);
    }

    public void setRadiusTopLeft(int radiusTopLeft) {
        if (radiusTopLeft < 0) {
            radiusTopLeft = 0;
        }
        this.mRadiusTopLeft = radiusTopLeft;
    }

    public void setRadiusTopRight(int radiusTopRight) {
        if (radiusTopRight < 0) {
            radiusTopRight = 0;
        }
        this.mRadiusTopRight = radiusTopRight;
    }

    public void setRadiusBottomLeft(int radiusBottomLeft) {
        if (radiusBottomLeft < 0) {
            radiusBottomLeft = 0;
        }
        this.mRadiusBottomLeft = radiusBottomLeft;
    }

    public void setRadiusBottomRight(int radiusBottomRight) {
        if (radiusBottomRight < 0) {
            radiusBottomRight = 0;
        }
        this.mRadiusBottomRight = radiusBottomRight;
    }

    public Bitmap createMask(int width, int height) {
        Bitmap localBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        this.mPaint.setColor(-7829368);
        createMaskPath(width, height);
        localCanvas.drawPath(this.mPath, this.mPaint);
        return localBitmap;
    }

    private void createMaskPath(int width, int height) {
        this.mPath.rewind();
        this.mPath.moveTo((float) this.mRadiusTopLeft, 0.0f);
        this.mPath.lineTo((float) (width - this.mRadiusTopRight), 0.0f);
        this.mCornerRectF.set((float) (width - (this.mRadiusTopRight * 2)), 0.0f, (float) width, (float) ((this.mRadiusTopRight + this.mDiff) * 2));
        this.mPath.arcTo(this.mCornerRectF, -90.0f, 90.0f, false);
        this.mPath.lineTo((float) width, (float) (height - (this.mRadiusBottomRight + this.mDiff)));
        this.mCornerRectF.set((float) (width - (this.mRadiusBottomRight * 2)), (float) (height - ((this.mRadiusBottomRight + this.mDiff) * 2)), (float) width, (float) height);
        this.mPath.arcTo(this.mCornerRectF, 0.0f, 90.0f, false);
        this.mPath.lineTo((float) this.mRadiusBottomLeft, (float) height);
        this.mCornerRectF.set(0.0f, (float) (height - ((this.mRadiusBottomLeft + this.mDiff) * 2)), (float) (this.mRadiusBottomLeft * 2), (float) height);
        this.mPath.arcTo(this.mCornerRectF, 90.0f, 90.0f, false);
        this.mPath.lineTo(0.0f, (float) (this.mRadiusTopLeft + this.mDiff));
        this.mCornerRectF.set(0.0f, 0.0f, (float) (this.mRadiusTopLeft * 2), (float) ((this.mRadiusTopLeft + this.mDiff) * 2));
        this.mPath.arcTo(this.mCornerRectF, 180.0f, 90.0f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas paramCanvas) {
        if (Build.MODEL.contains("Redmi Note") && this.isCreateMask) {
            if (drawOnNote2(paramCanvas)) {
                super.onDraw(paramCanvas);
            } else {
                return;
            }
        }
        super.onDraw(paramCanvas);
    }

    private boolean drawOnNote2(Canvas paramCanvas) {
        Drawable localDrawable = getDrawable();
        if (localDrawable == null) {
            Log.e("AntUI-build", "drawOnNote2: localDrawable is null");
            return true;
        }
        Bitmap localBitmap = drawableToBitmap(localDrawable);
        this.mShader = new BitmapShader(localBitmap, TileMode.CLAMP, TileMode.CLAMP);
        RectF rect = new RectF(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        this.mMatrix.setRectToRect(new RectF(0.0f, 0.0f, (float) localBitmap.getWidth(), (float) localBitmap.getHeight()), rect, SCALE_TO_FIT);
        this.mShader.setLocalMatrix(this.mMatrix);
        this.mPaint.reset();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setShader(this.mShader);
        this.mPaint.setStyle(Style.FILL);
        createMaskPath(getWidth(), getHeight());
        paramCanvas.setDrawFilter(FILTER);
        paramCanvas.drawPath(this.mPath, this.mPaint);
        return false;
    }
}
