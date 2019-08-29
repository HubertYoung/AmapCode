package com.alipay.mobile.scansdk.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.alipay.android.phone.scancode.export.R;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

public class ScaleFinderView extends View {
    private int LINE_COLOR = -16733457;
    private Bitmap angle_leftBottom;
    private Bitmap angle_leftTop;
    private Bitmap angle_rightBottom;
    private Bitmap angle_rightTop;
    private boolean bDrawBottom;
    private boolean bDrawLeft;
    private boolean bDrawRight;
    private boolean bDrawTop;
    private int barScaleSize = 20;
    private int dLineWidth = 10;
    private int dhalfLineWidth;
    private boolean isDrawLine;
    private boolean isRegionOK = false;
    private int lineBottom;
    private int lineLeft;
    private int lineRight;
    private int lineTop;
    private Paint paint;
    private int qrBottom;
    private int qrLeft;
    private int qrRight;
    private int qrTop;
    private int scanViewSize = 118;
    private int screenHeight;
    private int screenWidth;
    private int shadowColor = -1778384896;
    private int targetBottom;
    private int targetLeft;
    private int targetRight;
    private int targetTop;

    public ScaleFinderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAngleBitmap(context);
        initScreenSize(context);
        initPaint();
        init(context);
    }

    public ScaleFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAngleBitmap(context);
        initScreenSize(context);
        initPaint();
        init(context);
    }

    public ScaleFinderView(Context context) {
        super(context);
        initAngleBitmap(context);
        initScreenSize(context);
        initPaint();
        init(context);
    }

    public void setShadowColor(int shadowColor2) {
        this.shadowColor = shadowColor2;
    }

    public void setAngleColor(int angleColor) {
        this.angle_leftTop = changeBitmapColor(this.angle_leftTop, angleColor);
        this.angle_rightTop = changeBitmapColor(this.angle_rightTop, angleColor);
        this.angle_leftBottom = changeBitmapColor(this.angle_leftBottom, angleColor);
        this.angle_rightBottom = changeBitmapColor(this.angle_rightBottom, angleColor);
    }

    public void setScanType(ScanType type) {
        switch (type) {
            case SCAN_MA:
                this.isDrawLine = false;
                this.targetLeft = this.qrLeft;
                this.targetRight = this.qrRight;
                this.targetTop = this.qrTop;
                this.targetBottom = this.qrBottom;
                return;
            default:
                return;
        }
    }

    private void init(Context c) {
        float nUseHeight;
        setVisibility(4);
        int i = this.screenWidth / 2;
        this.lineRight = i;
        this.lineLeft = i;
        int i2 = this.screenHeight / 2;
        this.lineBottom = i2;
        this.lineTop = i2;
        this.scanViewSize = dip2px(c, this.scanViewSize);
        this.barScaleSize = dip2px(c, this.barScaleSize);
        this.qrLeft = (this.screenWidth / 2) - this.scanViewSize;
        this.qrTop = (this.screenHeight / 2) - this.scanViewSize;
        this.qrRight = (this.screenWidth / 2) + this.scanViewSize;
        this.qrBottom = (this.screenHeight / 2) + this.scanViewSize;
        float nFitWidth = (float) this.screenWidth;
        if (((double) (nFitWidth / ((float) this.screenHeight))) >= 0.75d) {
            nUseHeight = (3.0f * nFitWidth) / 4.0f;
        } else {
            nUseHeight = (4.0f * nFitWidth) / 3.0f;
        }
        this.dLineWidth = ((int) ((nUseHeight / 480.0f) * 420.0f)) / 100;
        this.dhalfLineWidth = this.dLineWidth / 2;
    }

    private void initScreenSize(Context c) {
        Display display = ((WindowManager) c.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
        this.screenWidth = display.getWidth();
        this.screenHeight = display.getHeight();
    }

    private void initAngleBitmap(Context c) {
        Resources res = c.getResources();
        this.angle_leftTop = BitmapFactory.decodeResource(res, R.drawable.scan_aimingbox_lu);
        this.angle_rightTop = BitmapFactory.decodeResource(res, R.drawable.scan_aimingbox_ru);
        this.angle_leftBottom = BitmapFactory.decodeResource(res, R.drawable.scan_aimingbox_ld);
        this.angle_rightBottom = BitmapFactory.decodeResource(res, R.drawable.scan_aimingbox_rd);
    }

    private Bitmap changeBitmapColor(Bitmap bitmap, int color) {
        int bitmap_w = bitmap.getWidth();
        int bitmap_h = bitmap.getHeight();
        int[] arrayColor = new int[(bitmap_w * bitmap_h)];
        int count = 0;
        for (int i = 0; i < bitmap_h; i++) {
            for (int j = 0; j < bitmap_w; j++) {
                int originColor = bitmap.getPixel(j, i);
                if (originColor != 0) {
                    originColor = color;
                }
                arrayColor[count] = originColor;
                count++;
            }
        }
        return Bitmap.createBitmap(arrayColor, bitmap_w, bitmap_h, Config.ARGB_8888);
    }

    private void initPaint() {
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
    }

    public void setTargetLocation(int left, int top, int right, int bottom) {
        this.targetLeft = left;
        this.targetRight = right;
        this.targetTop = top;
        this.targetBottom = bottom;
        invalidate();
        setVisibility(0);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.lineLeft = this.targetLeft;
        this.lineRight = this.targetRight;
        this.lineTop = this.targetTop;
        this.lineBottom = this.targetBottom;
        drawAngle(canvas);
        drawShadow(canvas);
        if (this.isDrawLine) {
            drawGreenRect(canvas);
            drawLine(canvas);
        }
    }

    private void drawAngle(Canvas canvas) {
        this.paint.setAlpha(255);
        canvas.drawBitmap(this.angle_leftTop, (float) this.lineLeft, (float) this.lineTop, this.paint);
        canvas.drawBitmap(this.angle_rightTop, (float) (this.lineRight - this.angle_rightTop.getWidth()), (float) this.lineTop, this.paint);
        canvas.drawBitmap(this.angle_leftBottom, (float) this.lineLeft, (float) (this.lineBottom - this.angle_leftBottom.getHeight()), this.paint);
        canvas.drawBitmap(this.angle_rightBottom, (float) (this.lineRight - this.angle_rightBottom.getWidth()), (float) (this.lineBottom - this.angle_rightBottom.getHeight()), this.paint);
    }

    private void drawShadow(Canvas canvas) {
        this.paint.setColor(this.shadowColor);
        canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) this.lineTop, this.paint);
        canvas.drawRect(0.0f, (float) this.lineTop, (float) this.lineLeft, (float) this.lineBottom, this.paint);
        canvas.drawRect((float) this.lineRight, (float) this.lineTop, (float) getWidth(), (float) this.lineBottom, this.paint);
        canvas.drawRect(0.0f, (float) this.lineBottom, (float) getWidth(), (float) getHeight(), this.paint);
    }

    private void drawLine(Canvas canvas) {
        this.paint.setColor(this.LINE_COLOR);
        this.paint.setStrokeWidth((float) this.dLineWidth);
        if (this.bDrawTop) {
            canvas.drawLine((float) (this.lineRight - this.dhalfLineWidth), (float) this.lineTop, (float) (this.lineRight - this.dhalfLineWidth), (float) this.lineBottom, this.paint);
        }
        if (this.bDrawBottom) {
            canvas.drawLine((float) (this.lineLeft + this.dhalfLineWidth), (float) this.lineTop, (float) (this.lineLeft + this.dhalfLineWidth), (float) this.lineBottom, this.paint);
        }
        if (this.bDrawLeft) {
            canvas.drawLine((float) this.lineLeft, (float) (this.lineBottom - this.dhalfLineWidth), (float) this.lineRight, (float) (this.lineBottom - this.dhalfLineWidth), this.paint);
        }
        if (this.bDrawRight) {
            canvas.drawLine((float) this.lineLeft, (float) (this.lineTop + this.dhalfLineWidth), (float) this.lineRight, (float) (this.lineTop + this.dhalfLineWidth), this.paint);
        }
    }

    private void drawGreenRect(Canvas canvas) {
        if (this.isRegionOK) {
            this.bDrawBottom = false;
            this.bDrawRight = false;
            this.bDrawLeft = false;
            this.bDrawTop = false;
            this.paint.setColor(-16711936);
            this.paint.setStrokeWidth((float) this.dLineWidth);
            canvas.drawLine((float) this.lineLeft, (float) this.lineTop, (float) this.lineRight, (float) this.lineTop, this.paint);
            canvas.drawLine((float) this.lineLeft, (float) this.lineBottom, (float) this.lineRight, (float) this.lineBottom, this.paint);
            canvas.drawLine((float) this.lineLeft, (float) this.lineTop, (float) this.lineLeft, (float) this.lineBottom, this.paint);
            canvas.drawLine((float) this.lineRight, (float) this.lineTop, (float) this.lineRight, (float) this.lineBottom, this.paint);
            this.isRegionOK = false;
        }
    }

    public int dip2px(Context mContext, int dipValue) {
        return (int) ((((float) dipValue) * mContext.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
