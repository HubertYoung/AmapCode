package com.autonavi.bundle.amaphome.widget.guideview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

public class MaskView extends ViewGroup {
    private boolean ignoreRepadding;
    private int mChangedHeight;
    private final RectF mChildTmpRect;
    private int mCorner;
    private Paint mEraser;
    private Bitmap mEraserBitmap;
    private Canvas mEraserCanvas;
    private boolean mFirstFlag;
    private final Paint mFullingPaint;
    private final RectF mFullingRect;
    private int mInitHeight;
    private boolean mOverlayTarget;
    private int mPadding;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mStyle;
    private final RectF mTargetRect;

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        public int a = 4;
        public int b = 32;
        public int c = 0;
        public int d = 0;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i) {
            super(i, -2);
        }
    }

    public MaskView(Context context) {
        this(context, null, 0);
    }

    public MaskView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaskView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTargetRect = new RectF();
        this.mFullingRect = new RectF();
        this.mChildTmpRect = new RectF();
        this.mPadding = 0;
        this.mPaddingLeft = 0;
        this.mPaddingTop = 0;
        this.mPaddingRight = 0;
        this.mPaddingBottom = 0;
        this.mOverlayTarget = false;
        this.mCorner = 0;
        this.mStyle = 0;
        this.mChangedHeight = 0;
        this.mFirstFlag = true;
        setWillNotDraw(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getRealMetrics(displayMetrics);
        this.mEraserBitmap = Bitmap.createBitmap(displayMetrics.widthPixels, displayMetrics.heightPixels, Config.ARGB_8888);
        this.mEraserCanvas = new Canvas(this.mEraserBitmap);
        this.mFullingPaint = new Paint();
        this.mEraser = new Paint();
        this.mEraser.setColor(-1);
        this.mEraser.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        this.mEraser.setFlags(1);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            clearFocus();
            this.mEraserCanvas.setBitmap(null);
            this.mEraserBitmap = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (this.mFirstFlag) {
            this.mInitHeight = size2;
            this.mFirstFlag = false;
        }
        if (this.mInitHeight > size2) {
            this.mChangedHeight = size2 - this.mInitHeight;
        } else if (this.mInitHeight < size2) {
            this.mChangedHeight = size2 - this.mInitHeight;
        } else {
            this.mChangedHeight = 0;
        }
        setMeasuredDimension(size, size2);
        this.mFullingRect.set(0.0f, 0.0f, (float) size, (float) size2);
        resetOutPath();
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt != null) {
                measureChild(childAt, i, i2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        float f = getResources().getDisplayMetrics().density;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt != null) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams != null) {
                    switch (layoutParams.a) {
                        case 1:
                            this.mChildTmpRect.right = this.mTargetRect.left;
                            this.mChildTmpRect.left = this.mChildTmpRect.right - ((float) childAt.getMeasuredWidth());
                            verticalChildPositionLayout(childAt, this.mChildTmpRect, layoutParams.b);
                            break;
                        case 2:
                            this.mChildTmpRect.bottom = this.mTargetRect.top;
                            this.mChildTmpRect.top = this.mChildTmpRect.bottom - ((float) childAt.getMeasuredHeight());
                            horizontalChildPositionLayout(childAt, this.mChildTmpRect, layoutParams.b);
                            break;
                        case 3:
                            this.mChildTmpRect.left = this.mTargetRect.right;
                            this.mChildTmpRect.right = this.mChildTmpRect.left + ((float) childAt.getMeasuredWidth());
                            verticalChildPositionLayout(childAt, this.mChildTmpRect, layoutParams.b);
                            break;
                        case 4:
                            this.mChildTmpRect.top = this.mTargetRect.bottom;
                            this.mChildTmpRect.bottom = this.mChildTmpRect.top + ((float) childAt.getMeasuredHeight());
                            horizontalChildPositionLayout(childAt, this.mChildTmpRect, layoutParams.b);
                            break;
                        case 5:
                            this.mChildTmpRect.left = (float) ((((int) this.mTargetRect.width()) - childAt.getMeasuredWidth()) >> 1);
                            this.mChildTmpRect.top = (float) ((((int) this.mTargetRect.height()) - childAt.getMeasuredHeight()) >> 1);
                            this.mChildTmpRect.right = (float) ((((int) this.mTargetRect.width()) + childAt.getMeasuredWidth()) >> 1);
                            this.mChildTmpRect.bottom = (float) ((((int) this.mTargetRect.height()) + childAt.getMeasuredHeight()) >> 1);
                            this.mChildTmpRect.offset(this.mTargetRect.left, this.mTargetRect.top);
                            break;
                    }
                    this.mChildTmpRect.offset((float) ((int) ((((float) layoutParams.c) * f) + 0.5f)), (float) ((int) ((((float) layoutParams.d) * f) + 0.5f)));
                    childAt.layout((int) this.mChildTmpRect.left, (int) this.mChildTmpRect.top, (int) this.mChildTmpRect.right, (int) this.mChildTmpRect.bottom);
                }
            }
        }
    }

    private void horizontalChildPositionLayout(View view, RectF rectF, int i) {
        if (i == 16) {
            rectF.left = this.mTargetRect.left;
            rectF.right = rectF.left + ((float) view.getMeasuredWidth());
        } else if (i != 32) {
            if (i == 48) {
                rectF.right = this.mTargetRect.right;
                rectF.left = rectF.right - ((float) view.getMeasuredWidth());
            }
        } else {
            rectF.left = (this.mTargetRect.width() - ((float) view.getMeasuredWidth())) / 2.0f;
            rectF.right = (this.mTargetRect.width() + ((float) view.getMeasuredWidth())) / 2.0f;
            rectF.offset(this.mTargetRect.left, 0.0f);
        }
    }

    private void verticalChildPositionLayout(View view, RectF rectF, int i) {
        if (i == 16) {
            rectF.top = this.mTargetRect.top;
            rectF.bottom = rectF.top + ((float) view.getMeasuredHeight());
        } else if (i != 32) {
            if (i == 48) {
                rectF.bottom = this.mTargetRect.bottom;
                rectF.top = this.mTargetRect.bottom - ((float) view.getMeasuredHeight());
            }
        } else {
            rectF.top = (this.mTargetRect.width() - ((float) view.getMeasuredHeight())) / 2.0f;
            rectF.bottom = (this.mTargetRect.width() + ((float) view.getMeasuredHeight())) / 2.0f;
            rectF.offset(0.0f, this.mTargetRect.top);
        }
    }

    private void resetOutPath() {
        resetPadding();
    }

    private void resetPadding() {
        if (!this.ignoreRepadding) {
            if (this.mPadding != 0 && this.mPaddingLeft == 0) {
                this.mTargetRect.left -= (float) this.mPadding;
            }
            if (this.mPadding != 0 && this.mPaddingTop == 0) {
                this.mTargetRect.top -= (float) this.mPadding;
            }
            if (this.mPadding != 0 && this.mPaddingRight == 0) {
                this.mTargetRect.right += (float) this.mPadding;
            }
            if (this.mPadding != 0 && this.mPaddingBottom == 0) {
                this.mTargetRect.bottom += (float) this.mPadding;
            }
            if (this.mPaddingLeft != 0) {
                this.mTargetRect.left -= (float) this.mPaddingLeft;
            }
            if (this.mPaddingTop != 0) {
                this.mTargetRect.top -= (float) this.mPaddingTop;
            }
            if (this.mPaddingRight != 0) {
                this.mTargetRect.right += (float) this.mPaddingRight;
            }
            if (this.mPaddingBottom != 0) {
                this.mTargetRect.bottom += (float) this.mPaddingBottom;
            }
            this.ignoreRepadding = true;
        }
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        long drawingTime = getDrawingTime();
        int i = 0;
        while (i < getChildCount()) {
            try {
                drawChild(canvas, getChildAt(i), drawingTime);
                i++;
            } catch (NullPointerException unused) {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mChangedHeight != 0) {
            this.mTargetRect.offset(0.0f, (float) this.mChangedHeight);
            this.mInitHeight += this.mChangedHeight;
            this.mChangedHeight = 0;
        }
        this.mEraserBitmap.eraseColor(0);
        this.mEraserCanvas.drawColor(this.mFullingPaint.getColor());
        if (!this.mOverlayTarget) {
            switch (this.mStyle) {
                case 0:
                    this.mEraserCanvas.drawRoundRect(this.mTargetRect, (float) this.mCorner, (float) this.mCorner, this.mEraser);
                    break;
                case 1:
                    this.mEraserCanvas.drawCircle(this.mTargetRect.centerX(), this.mTargetRect.centerY(), this.mTargetRect.width() / 2.0f, this.mEraser);
                    break;
                default:
                    this.mEraserCanvas.drawRoundRect(this.mTargetRect, (float) this.mCorner, (float) this.mCorner, this.mEraser);
                    break;
            }
        }
        canvas.drawBitmap(this.mEraserBitmap, 0.0f, 0.0f, null);
    }

    public void setTargetRect(Rect rect) {
        this.mTargetRect.set(rect);
    }

    public void setFullingRect(Rect rect) {
        this.mFullingRect.set(rect);
    }

    public void setFullingAlpha(int i) {
        this.mFullingPaint.setAlpha(i);
    }

    public void setFullingColor(int i) {
        this.mFullingPaint.setColor(i);
    }

    public void setHighTargetCorner(int i) {
        this.mCorner = i;
    }

    public void setHighTargetGraphStyle(int i) {
        this.mStyle = i;
    }

    public void setOverlayTarget(boolean z) {
        this.mOverlayTarget = z;
    }

    public void setPadding(int i) {
        this.mPadding = i;
    }

    public void setPaddingLeft(int i) {
        this.mPaddingLeft = i;
    }

    public void setPaddingTop(int i) {
        this.mPaddingTop = i;
    }

    public void setPaddingRight(int i) {
        this.mPaddingRight = i;
    }

    public void setPaddingBottom(int i) {
        this.mPaddingBottom = i;
    }
}
