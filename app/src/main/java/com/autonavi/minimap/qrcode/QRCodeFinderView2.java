package com.autonavi.minimap.qrcode;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.autonavi.minimap.R;
import com.google.zxing.ResultPoint;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressFBWarnings({"RCN_REDUNDANT_NULLCHECK_OF_NULL_VALUE"})
public final class QRCodeFinderView2 extends View implements dty {
    private static final long ANIMATION_DELAY = 25;
    private static final int CURRENT_POINT_OPACITY = 160;
    public static boolean ERR_TIP_SHOW = false;
    private static final int MAX_RESULT_POINTS = 20;
    private static final int OPAQUE = 255;
    private static final int POINT_SIZE = 6;
    private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
    public static boolean STOP_SHOW = false;
    static final String TAG = "qrcode";
    private dtt cameraManager;
    private int cornerAlpha;
    private final int cornerColor;
    private final int frameColor;
    private boolean isStartDrawScanLine = false;
    private final int laserColor;
    private List<ResultPoint> lastPossibleResultPoints;
    private String mErrorText = "";
    private String mErrorText2 = "";
    private Rect mFrameRect;
    private int mIncreaseValue = 0;
    private int mLineHeight;
    private Paint mPaint = new Paint();
    private Drawable mScanErrFrameDrawable;
    private Drawable mScanFrameDrawable;
    private Drawable mScanLineDrawable;
    a mStateChangedHandler = null;
    private final int maskColor;
    private final Paint paint = new Paint();
    private List<ResultPoint> possibleResultPoints;
    private Bitmap resultBitmap;
    private final int resultColor;
    private final int resultPointColor;
    private int scannerAlpha;

    public final boolean useAbsForFrameRect() {
        return true;
    }

    public QRCodeFinderView2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Resources resources = getResources();
        this.maskColor = resources.getColor(R.color.viewfinder_mask);
        this.resultColor = resources.getColor(R.color.result_view);
        this.frameColor = resources.getColor(R.color.viewfinder_frame);
        this.laserColor = resources.getColor(R.color.viewfinder_laser);
        this.resultPointColor = resources.getColor(R.color.possible_result_points);
        this.cornerColor = resources.getColor(R.color.viewfinder_corner);
        this.scannerAlpha = 0;
        this.cornerAlpha = 128;
        this.possibleResultPoints = new ArrayList(5);
        this.lastPossibleResultPoints = null;
        this.mScanLineDrawable = resources.getDrawable(R.drawable.qrcode_scan_line);
        this.mScanFrameDrawable = resources.getDrawable(R.drawable.qrcode_scan_frame);
        this.mScanErrFrameDrawable = resources.getDrawable(R.drawable.qrcode_scan_err_frame);
        this.mLineHeight = this.mScanLineDrawable.getMinimumHeight();
    }

    public final void setCameraManager(dtt dtt) {
        this.cameraManager = dtt;
    }

    public final void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        if (this.cameraManager != null) {
            Rect e = this.cameraManager.e();
            Rect f = this.cameraManager.f();
            if (e != null && f != null) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                int i = e.left;
                int i2 = e.top;
                int i3 = e.right;
                int i4 = e.bottom;
                if (STOP_SHOW) {
                    this.paint.setColor(this.resultBitmap != null ? this.resultColor : this.maskColor);
                    canvas2.drawRect(0.0f, 0.0f, (float) width, (float) height, this.paint);
                } else if (this.resultBitmap != null) {
                    Rect f2 = this.cameraManager.f();
                    this.paint.setAlpha(255);
                    canvas2.drawBitmap(this.resultBitmap, null, f2, this.paint);
                } else {
                    if (this.mFrameRect == null) {
                        this.mFrameRect = new Rect();
                        this.mFrameRect.left = i;
                        this.mFrameRect.top = i2;
                        this.mFrameRect.bottom = i4;
                        this.mFrameRect.right = i3;
                        if (this.mStateChangedHandler != null) {
                            this.mStateChangedHandler.a(new Rect(this.mFrameRect));
                        }
                    }
                    if (this.mErrorText == null || this.mErrorText.isEmpty()) {
                        this.mScanFrameDrawable.setBounds(i, i2, i3, i4);
                        this.mScanFrameDrawable.draw(canvas2);
                        this.paint.setColor(this.resultBitmap != null ? this.resultColor : this.maskColor);
                        float f3 = (float) width;
                        float f4 = (float) i2;
                        float f5 = f3;
                        canvas2.drawRect(0.0f, 0.0f, f3, f4, this.paint);
                        float f6 = (float) (i4 + 1);
                        float f7 = f4;
                        float f8 = f6;
                        canvas2.drawRect(0.0f, f7, (float) i, f6, this.paint);
                        Canvas canvas3 = canvas2;
                        float f9 = f5;
                        canvas3.drawRect((float) (i3 + 1), f7, f9, f8, this.paint);
                        canvas3.drawRect(0.0f, f8, f9, (float) height, this.paint);
                        if (this.isStartDrawScanLine) {
                            int i5 = this.mIncreaseValue + i2;
                            this.mScanLineDrawable.setBounds(e.left, i5, e.right, this.mLineHeight + i5);
                            this.mScanLineDrawable.draw(canvas2);
                            this.mIncreaseValue += 5;
                            if (this.mIncreaseValue > i4 - i2) {
                                this.mIncreaseValue = 0;
                            }
                        } else {
                            this.isStartDrawScanLine = true;
                        }
                        postInvalidateDelayed(ANIMATION_DELAY, i, i2, i3, i4);
                        return;
                    }
                    this.paint.setColor(this.resultBitmap != null ? this.resultColor : this.maskColor);
                    float f10 = (float) width;
                    float f11 = (float) i2;
                    Canvas canvas4 = canvas2;
                    canvas4.drawRect(0.0f, 0.0f, f10, f11, this.paint);
                    float f12 = (float) i;
                    float f13 = (float) (i4 + 1);
                    float f14 = f11;
                    float f15 = f13;
                    canvas4.drawRect(0.0f, f14, f12, f15, this.paint);
                    float f16 = f10;
                    canvas4.drawRect((float) (i3 + 1), f14, f16, f15, this.paint);
                    canvas4.drawRect(0.0f, f13, f16, (float) height, this.paint);
                    this.mPaint.setColor(-1);
                    this.mPaint.setStyle(Style.FILL);
                    this.mPaint.setStrokeWidth(200.0f);
                    this.mPaint.setTextSize(38.0f);
                    this.mPaint.setTextAlign(Align.CENTER);
                    this.mPaint.setAntiAlias(true);
                    if (((float) e.width()) > this.mPaint.measureText(this.mErrorText, 0, this.mErrorText.length())) {
                        this.mPaint.setTextSize(38.0f);
                    } else {
                        this.mPaint.setTextSize(28.0f);
                    }
                    this.mScanErrFrameDrawable.setBounds(e.left, e.top, e.right, e.bottom);
                    this.mScanErrFrameDrawable.draw(canvas2);
                    canvas2.drawText(this.mErrorText, (float) e.centerX(), (float) e.centerY(), this.mPaint);
                    canvas2.drawText(this.mErrorText2, (float) e.centerX(), (float) (e.centerY() + 50), this.mPaint);
                }
            }
        }
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        drawResultErrorTip("", "");
        ERR_TIP_SHOW = false;
        if (this.cameraManager != null) {
            this.cameraManager.c();
        }
        return true;
    }

    public final void drawViewfinder() {
        Bitmap bitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (bitmap != null) {
            bitmap.recycle();
        }
        invalidate();
    }

    public final void drawResultBitmap(Bitmap bitmap) {
        this.resultBitmap = bitmap;
        invalidate();
    }

    public final void drawResultErrorTip(String str, String str2) {
        this.mErrorText = str;
        this.mErrorText2 = str2;
        ERR_TIP_SHOW = true;
        invalidate();
    }

    public final void setVisible(boolean z) {
        STOP_SHOW = !z;
        invalidate();
    }

    public final void addPossibleResultPoint(ResultPoint resultPoint) {
        List<ResultPoint> list = this.possibleResultPoints;
        synchronized (list) {
            list.add(resultPoint);
            int size = list.size();
            if (size > 20) {
                list.subList(0, size - 10).clear();
            }
        }
    }

    public final Rect getFramRect() {
        return this.mFrameRect;
    }

    public final void setStateChangedListner(a aVar) {
        this.mStateChangedHandler = aVar;
    }

    public final void cleanUp() {
        releaseDrawable(this.mScanLineDrawable);
        releaseDrawable(this.mScanFrameDrawable);
        releaseDrawable(this.mScanErrFrameDrawable);
    }

    private void releaseDrawable(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(null);
        }
    }
}
