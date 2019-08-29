package com.autonavi.minimap.ajx3.widget.property;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.ImageView;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.image.PictureFactory;
import com.autonavi.minimap.ajx3.image.PictureParams;

public class ShadowView extends ImageView {
    private static final int MAX_TRY_COUNT = 5;
    private static final float SHADOW_BLUR_RECTIFICATION = 0.735f;
    private static final float SHADOW_SCALE = 0.5f;
    private boolean isFirstLayout = true;
    private boolean isParamEnable = false;
    private boolean isShadowChanged = false;
    private View mContent;
    private Rect mContentRect = new Rect();
    private AjxDomNode mNode;
    private int mPaddingX = -1;
    private int mPaddingY = -1;
    private Paint mPaint = null;
    private PictureParams mParams;
    private Path mPath = null;
    private RectF mShapeRectF = null;
    private int mTryCount = 0;

    public ShadowView(AjxDomNode ajxDomNode, View view, PictureParams pictureParams) {
        super(view.getContext());
        this.mNode = ajxDomNode;
        this.mContent = view;
        this.mParams = pictureParams;
    }

    public void onContentLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        if (i == this.mContentRect.left && i2 == this.mContentRect.top && i3 == this.mContentRect.right && i4 == this.mContentRect.bottom) {
            z2 = false;
        } else {
            this.mContentRect.set(i, i2, i3, i4);
            z2 = true;
        }
        if (z2) {
            generateShadow(true);
        }
    }

    public boolean addShadow() {
        if (checkParent(this) != null || this.mTryCount >= 5) {
            return false;
        }
        AbsoluteLayout checkParent = checkParent(this.mContent);
        if (checkParent != null) {
            checkParent.addView(this, checkParent.indexOfChild(this.mContent) + 1);
            ShadowHelper.checkContentAttribute(this.mContent, this);
            this.mNode.addAttachedView(this);
            this.mTryCount = 0;
            return true;
        }
        this.mTryCount++;
        return false;
    }

    public boolean updateShadow() {
        if (checkParent(this) == null) {
            return false;
        }
        this.isShadowChanged = true;
        generateShadow(false);
        return true;
    }

    public boolean removeShadow() {
        AbsoluteLayout checkParent = checkParent(this);
        if (checkParent == null) {
            return false;
        }
        checkParent.removeView(this);
        this.mNode.removeAttachedView(this);
        return true;
    }

    private Bitmap getShadowBitmap(float f) {
        float[] fArr;
        int width = (int) (((float) this.mContentRect.width()) * f);
        int height = (int) (((float) this.mContentRect.height()) * f);
        if (width <= 0 || height <= 0) {
            return null;
        }
        int paddingX = getPaddingX();
        int paddingY = getPaddingY();
        float f2 = (float) width;
        int i = (int) ((((float) (paddingX * 2)) * f) + f2);
        float f3 = (float) height;
        int i2 = (int) ((((float) (paddingY * 2)) * f) + f3);
        int i3 = this.mParams.shadowBlur;
        int i4 = this.mParams.shadowX;
        int i5 = this.mParams.shadowY;
        int i6 = (int) (((float) i3) * f);
        int i7 = i4 > 0 ? (int) (((float) i6) + (((float) i4) * 2.0f * f)) : i6;
        if (i5 > 0) {
            i6 = (int) (((float) i6) + (((float) i5) * 2.0f * f));
        }
        if (this.mShapeRectF == null) {
            this.mShapeRectF = new RectF();
        }
        this.mShapeRectF.set(0.0f, 0.0f, f2, f3);
        this.mShapeRectF.offset((float) i7, (float) i6);
        int[] iArr = this.mParams.cornerRadius;
        if (PictureFactory.hasCornerRadius(iArr)) {
            fArr = new float[8];
            for (int i8 = 0; i8 < 8; i8++) {
                fArr[i8] = ((float) iArr[i8 / 2]) * f;
            }
        } else {
            fArr = null;
        }
        Path shadowPath = getShadowPath(this.mShapeRectF, fArr);
        if (shadowPath == null) {
            return null;
        }
        initShadowPaint(f);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_4444);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawPath(shadowPath, this.mPaint);
        this.mShapeRectF.set(0.0f, 0.0f, f2, f3);
        this.mShapeRectF.offset((float) ((int) (((float) paddingX) * f)), (float) ((int) (((float) paddingY) * f)));
        Path contentPath = getContentPath(this.mShapeRectF, fArr);
        initShapePaint();
        canvas.drawPath(contentPath, this.mPaint);
        return createBitmap;
    }

    private Paint initShapePaint() {
        if (this.mPaint == null) {
            this.mPaint = new Paint();
        }
        this.mPaint.reset();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        return this.mPaint;
    }

    private Paint initShadowPaint(float f) {
        if (this.mPaint == null) {
            this.mPaint = new Paint();
        }
        this.mPaint.reset();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.mParams.shadowColor);
        this.mPaint.setStyle(Style.FILL);
        int i = this.mParams.shadowBlur;
        if (i > 0) {
            this.mPaint.setMaskFilter(new BlurMaskFilter(((float) i) * f * SHADOW_BLUR_RECTIFICATION, Blur.NORMAL));
        }
        return this.mPaint;
    }

    private Path getContentPath(RectF rectF, float[] fArr) {
        if (this.mPath == null) {
            this.mPath = new Path();
        }
        this.mPath.reset();
        Path path = this.mPath;
        if (fArr != null) {
            path.addRoundRect(rectF, fArr, Direction.CCW);
        } else {
            path.addRect(rectF, Direction.CCW);
        }
        return path;
    }

    private Path getShadowPath(RectF rectF, float[] fArr) {
        if (this.mPath == null) {
            this.mPath = new Path();
        }
        this.mPath.reset();
        Path path = this.mPath;
        if (fArr != null) {
            path.addRoundRect(rectF, fArr, Direction.CCW);
        } else {
            path.addRect(rectF, Direction.CCW);
        }
        return path;
    }

    public int getPaddingX() {
        return this.mParams.shadowBlur + Math.abs(this.mParams.shadowX);
    }

    public int getPaddingY() {
        return this.mParams.shadowBlur + Math.abs(this.mParams.shadowY);
    }

    private void generateShadow(boolean z) {
        if (this.isFirstLayout || this.isShadowChanged || z) {
            this.isFirstLayout = false;
            this.isShadowChanged = false;
            int paddingX = getPaddingX();
            int paddingY = getPaddingY();
            if (!(paddingX == this.mPaddingX && paddingY == this.mPaddingY && !z)) {
                LayoutParams shadowLayoutParams = getShadowLayoutParams();
                if (shadowLayoutParams != null) {
                    setLayoutParams(shadowLayoutParams);
                    this.isParamEnable = true;
                    this.mPaddingX = paddingX;
                    this.mPaddingY = paddingY;
                    setPivotX(this.mContent.getPivotX() + ((float) getPaddingX()));
                    setPivotY(this.mContent.getPivotY() + ((float) getPaddingY()));
                } else {
                    return;
                }
            }
            if (this.isParamEnable) {
                setImageBitmap(getShadowBitmap(SHADOW_SCALE));
            }
        }
    }

    private LayoutParams getShadowLayoutParams() {
        int paddingX = getPaddingX();
        int paddingY = getPaddingY();
        int i = this.mContentRect.left;
        int i2 = this.mContentRect.top;
        int i3 = i - paddingX;
        int i4 = i2 - paddingY;
        int width = this.mContentRect.width() + (paddingX * 2);
        int height = this.mContentRect.height() + (paddingY * 2);
        LayoutParams checkParams = checkParams(this.mContent);
        if (checkParams == null) {
            return null;
        }
        LayoutParams layoutParams = new LayoutParams(checkParams);
        layoutParams.x = i3;
        layoutParams.y = i4;
        layoutParams.width = width;
        layoutParams.height = height;
        return layoutParams;
    }

    private LayoutParams checkParams(View view) {
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof LayoutParams) {
                return (LayoutParams) layoutParams;
            }
        }
        return null;
    }

    private AbsoluteLayout checkParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof AbsoluteLayout) {
                return (AbsoluteLayout) parent;
            }
        }
        return null;
    }
}
