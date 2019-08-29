package com.autonavi.minimap.basemap.route.external.zxingwrapper.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.autonavi.minimap.R;
import com.google.zxing.ResultPoint;
import java.util.Collection;
import java.util.HashSet;

public final class ViewfinderView extends View {
    private static final long ANIMATION_DELAY = 10;
    private static final int CORNER_WIDTH = 10;
    private static final int FRAME_LEFT_MARGIN = 116;
    private static final int FRAME_TEXT_COLOR = 16777215;
    private static final int FRAME_TEXT_SIZE = 17;
    private static final int HINT_MARGIN_ABOVE_FRAME_RECT = 36;
    private static final int HINT_TEXT_COLOR = 16580352;
    private static final int HINT_TEXT_SIZE = 14;
    private static final int MIDDLE_LINE_PADDING = 5;
    private static final int MIDDLE_LINE_WIDTH = 6;
    private static final int OPAQUE = 255;
    private static final int SPEEN_DISTANCE = 5;
    private static final String TAG = "log";
    private static final int TEXT_PADDING_TOP = 30;
    private static final int TEXT_SIZE = 16;
    private static float density;
    private int ScreenRate;
    private Bitmap bmDefaultLicense;
    private int canvasHeight;
    private int defaultLicenseAlpha;
    private String engineNumber = "";
    private final int frameColor;
    private String frameNumber = "";
    private String hintText = "";
    private boolean isConfigurationChanged;
    private final int maskColor;
    private Paint paint;
    private Collection<ResultPoint> possibleResultPoints;
    private Bitmap resultBitmap;
    private final int resultColor;
    private final int resultPointColor;
    private Drawable scanLineDrawable;
    private int scanLineIncreaseValue;
    private int scanLineWeight;
    private boolean showDefaultLicense;

    public ViewfinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int i = 0;
        this.showDefaultLicense = false;
        this.defaultLicenseAlpha = 255;
        this.scanLineIncreaseValue = 0;
        this.scanLineWeight = 0;
        this.canvasHeight = 0;
        this.isConfigurationChanged = false;
        density = context.getResources().getDisplayMetrics().density;
        this.ScreenRate = (int) (density * 20.0f);
        this.paint = new Paint();
        Resources resources = getResources();
        this.maskColor = resources.getColor(R.color.viewfinder_mask);
        this.frameColor = resources.getColor(R.color.viewfinder_frame);
        this.resultColor = resources.getColor(R.color.result_view);
        this.resultPointColor = resources.getColor(R.color.possible_result_points);
        this.possibleResultPoints = new HashSet(5);
        try {
            this.bmDefaultLicense = BitmapFactory.decodeResource(getResources(), R.drawable.car_license_scan_default);
            this.scanLineDrawable = resources.getDrawable(R.drawable.qrcode_scan_line_vertical);
        } catch (OutOfMemoryError unused) {
        }
        this.scanLineWeight = this.scanLineDrawable != null ? this.scanLineDrawable.getMinimumWidth() : i;
    }

    /* access modifiers changed from: protected */
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.isConfigurationChanged = true;
    }

    public final void cleanUp() {
        this.showDefaultLicense = false;
        this.hintText = "";
        this.frameNumber = "";
        this.engineNumber = "";
        if (this.bmDefaultLicense != null && !this.bmDefaultLicense.isRecycled()) {
            this.bmDefaultLicense.recycle();
            this.bmDefaultLicense = null;
        }
    }

    public final void setShowDefaultLicense(boolean z) {
        this.showDefaultLicense = z;
        invalidate();
    }

    public final void setDefaultLicenseAlpha(int i) {
        if (i < 0) {
            i = 0;
        }
        this.defaultLicenseAlpha = i;
    }

    public final int getDefaultLicenseAlpha() {
        return this.defaultLicenseAlpha;
    }

    public final void setHintText(String str) {
        if (str != null) {
            this.hintText = str;
        }
    }

    public final void setFrameNumber(String str) {
        this.frameNumber = str;
    }

    public final void setEngineNumber(String str) {
        this.engineNumber = str;
    }

    private float getTextHeight(Paint paint2) {
        FontMetrics fontMetrics = paint2.getFontMetrics();
        return (float) Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent));
    }

    private float getTextWidth(String str, Paint paint2) {
        if (str == null || str.length() == 0) {
            return 0.0f;
        }
        return paint2.measureText(str);
    }

    private void drawHint(Canvas canvas, Rect rect) {
        if (this.hintText != null && this.hintText.length() > 0) {
            this.paint.setColor(HINT_TEXT_COLOR);
            this.paint.setTextSize(density * 14.0f);
            this.paint.setAlpha(255);
            this.paint.setStyle(Style.FILL);
            this.paint.setTypeface(Typeface.create("System", 0));
            canvas.drawText(this.hintText, ((float) rect.left) + ((((float) (rect.right - rect.left)) - getTextWidth(this.hintText, this.paint)) / 2.0f), (((float) rect.top) - 36.0f) - getTextHeight(this.paint), this.paint);
        }
    }

    private void drawFrameNum(Canvas canvas, Rect rect, Rect rect2) {
        if (this.frameNumber != null && this.frameNumber.length() > 0) {
            this.paint.setColor(16777215);
            this.paint.setTextSize(density * 17.0f);
            this.paint.setAlpha(255);
            this.paint.setStyle(Style.FILL);
            this.paint.setTypeface(Typeface.create("System", 0));
            canvas.drawText(this.frameNumber, (float) (rect.right + 116), (float) (rect.top + 50), this.paint);
        }
    }

    private void drawEngineNum(Canvas canvas, Rect rect, Rect rect2) {
        if (this.engineNumber != null && this.engineNumber.length() > 0) {
            this.paint.setColor(16777215);
            this.paint.setTextSize(density * 17.0f);
            this.paint.setAlpha(255);
            this.paint.setStyle(Style.FILL);
            this.paint.setTypeface(Typeface.create("System", 0));
            canvas.drawText(this.engineNumber, (float) (rect.right + 116), ((float) (rect.top + 50)) + getTextHeight(this.paint) + 24.0f, this.paint);
        }
    }

    private void drawLicense(Canvas canvas, Rect rect) {
        if (this.showDefaultLicense) {
            this.paint.setAlpha(this.defaultLicenseAlpha);
            Rect rect2 = new Rect(rect);
            rect2.left = rect.left + 50;
            rect2.top = rect.top + 30;
            rect2.right = rect.right - 50;
            rect2.bottom = rect.bottom - 30;
            if (this.bmDefaultLicense != null) {
                canvas.drawBitmap(this.bmDefaultLicense, null, rect2, this.paint);
            }
        }
    }

    private void drawFrameBackground(Canvas canvas, Rect rect) {
        this.paint.setColor(this.maskColor);
        this.paint.setStyle(Style.FILL);
        Canvas canvas2 = canvas;
        canvas2.drawRect(0.0f, 0.0f, (float) canvas.getWidth(), (float) (rect.top + 2), this.paint);
        canvas2.drawRect(0.0f, (float) (rect.top + 2), (float) (rect.left + 2), (float) (rect.bottom - 2), this.paint);
        canvas2.drawRect((float) (rect.right - 2), (float) (rect.top + 2), (float) canvas.getWidth(), (float) rect.bottom, this.paint);
        canvas.drawRect(0.0f, (float) (rect.bottom - 2), (float) canvas.getWidth(), (float) canvas.getHeight(), this.paint);
    }

    private void drawCorners(Canvas canvas, Rect rect) {
        this.paint.setColor(this.maskColor);
        this.paint.setStyle(Style.FILL);
        canvas.drawArc(new RectF(rect), 0.0f, 30.0f, true, this.paint);
    }

    private void drawOutsideRect(Canvas canvas, Rect rect) {
        this.paint.setColor(-1);
        RectF rectF = new RectF(rect);
        this.paint.setStyle(Style.STROKE);
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(7.0f);
        canvas.drawRoundRect(rectF, 20.0f, 20.0f, this.paint);
    }

    private Rect drawInsideRect(Canvas canvas, Rect rect) {
        int height = (int) (((double) rect.height()) / 2.7d);
        this.paint.setColor(-1);
        this.paint.setStyle(Style.STROKE);
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(7.0f);
        Rect rect2 = new Rect(rect.left + 50, (rect.bottom - 30) - height, rect.left + 50 + height, rect.bottom - 30);
        canvas.drawRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, this.paint);
        return rect2;
    }

    private void drawScanLine(Canvas canvas, Rect rect) {
        if (this.scanLineDrawable != null && !this.showDefaultLicense) {
            int i = rect.left + this.scanLineIncreaseValue;
            this.scanLineDrawable.setBounds(i, rect.top, this.scanLineWeight + i, rect.bottom);
            this.scanLineDrawable.draw(canvas);
            this.scanLineIncreaseValue += 10;
            if (this.scanLineIncreaseValue > rect.right - rect.left) {
                this.scanLineIncreaseValue = 0;
            }
            postInvalidateDelayed(ANIMATION_DELAY, rect.left, rect.top, rect.right - 10, rect.bottom);
        }
    }

    public final void onDraw(Canvas canvas) {
        if (cqm.a().b) {
            cqm.a().c = canvas.getHeight();
            Rect a = cqm.a().a(canvas.getHeight(), this.isConfigurationChanged);
            if (a != null) {
                canvas.getWidth();
                canvas.getHeight();
                drawFrameBackground(canvas, a);
                drawOutsideRect(canvas, a);
                drawHint(canvas, a);
                drawLicense(canvas, a);
                drawScanLine(canvas, a);
                Rect drawInsideRect = drawInsideRect(canvas, a);
                drawFrameNum(canvas, drawInsideRect, a);
                drawEngineNum(canvas, drawInsideRect, a);
                this.isConfigurationChanged = false;
            }
        }
    }

    public final void drawViewfinder() {
        invalidate();
    }

    public final void drawResultBitmap(Bitmap bitmap) {
        this.resultBitmap = bitmap;
        invalidate();
    }

    public final void addPossibleResultPoint(ResultPoint resultPoint) {
        this.possibleResultPoints.add(resultPoint);
    }
}
