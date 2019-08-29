package com.autonavi.minimap.bundle.qrscan.scanner;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout.LayoutParams;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.BalloonLayout;

public class ScanFinderView extends View {
    public static final String TAG = "ScanFinderView";
    private View animationView;
    private Bitmap bitmap;
    private int frameH;
    private int frameLeft;
    private int frameTop;
    private int frameW;
    private boolean isAlive;
    private ScaleAnimation scaleAnimation;
    private int shadowColor;

    public ScanFinderView(Context context) {
        this(context, null);
    }

    public ScanFinderView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScanFinderView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.shadowColor = -1778384896;
        init(context);
    }

    public void attachScan() {
        this.isAlive = true;
    }

    public void detachScan() {
        this.isAlive = false;
    }

    private void init(Context context) {
        initAngleBitmap(context);
    }

    public void onStartScan() {
        if (this.animationView == null) {
            this.animationView = new View(getContext());
            Drawable drawable = getContext().getResources().getDrawable(R.drawable.bundle_qrcode_scan_ray);
            this.animationView.setBackgroundResource(R.drawable.bundle_qrcode_scan_ray);
            int minimumHeight = drawable.getMinimumHeight();
            int i = this.frameW;
            if (minimumHeight <= 0) {
                minimumHeight = 4;
            }
            LayoutParams layoutParams = new LayoutParams(i, minimumHeight);
            layoutParams.topMargin = this.frameTop;
            layoutParams.leftMargin = this.frameLeft;
            ((ViewGroup) getParent()).addView(this.animationView, layoutParams);
            this.scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 0.0f, ((float) this.frameH) / ((float) drawable.getMinimumHeight()));
            this.scaleAnimation.setDuration(BalloonLayout.DEFAULT_DISPLAY_DURATION);
            this.scaleAnimation.setFillAfter(true);
            this.scaleAnimation.setRepeatCount(-1);
            this.scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            this.animationView.setAnimation(this.scaleAnimation);
        } else {
            this.animationView.clearAnimation();
            this.animationView.setAnimation(this.scaleAnimation);
        }
        this.scaleAnimation.start();
    }

    public void onStopScan() {
        if (this.scaleAnimation != null) {
            this.scaleAnimation.cancel();
        }
    }

    public float getCropWidth() {
        return ((float) this.frameW) * 1.1f;
    }

    public Rect getScanRect(Camera camera, int i, int i2) {
        Rect rect;
        if (camera == null) {
            return null;
        }
        Rect rect2 = new Rect(this.frameLeft, this.frameTop, this.frameLeft + this.frameW, this.frameTop + this.frameH);
        try {
            Size previewSize = camera.getParameters().getPreviewSize();
            if (previewSize == null) {
                return null;
            }
            double d = ((double) previewSize.height) / ((double) i);
            double d2 = ((double) previewSize.width) / ((double) i2);
            int i3 = (int) (((double) this.frameW) * 0.05d);
            int i4 = (int) (((double) this.frameH) * 0.05d);
            Rect rect3 = new Rect((int) (((double) (rect2.top - i4)) * d2), (int) (((double) (rect2.left - i3)) * d), (int) (((double) (rect2.bottom + i4)) * d2), (int) (((double) (rect2.right + i3)) * d));
            int i5 = 0;
            int i6 = rect3.left < 0 ? 0 : rect3.left;
            if (rect3.top >= 0) {
                i5 = rect3.top;
            }
            Rect rect4 = new Rect(i6, i5, rect3.width() > previewSize.width ? previewSize.width : rect3.width(), rect3.height() > previewSize.height ? previewSize.height : rect3.height());
            Rect rect5 = new Rect((rect4.left / 4) * 4, (rect4.top / 4) * 4, (rect4.right / 4) * 4, (rect4.bottom / 4) * 4);
            int max = Math.max(rect5.right, rect5.bottom);
            int abs = (Math.abs(rect5.right - rect5.bottom) / 8) * 4;
            if (rect5.right > rect5.bottom) {
                rect = new Rect(rect5.left, rect5.top - abs, max, max);
            } else {
                rect = new Rect(rect5.left - abs, rect5.top, max, max);
            }
            return rect;
        } catch (Exception unused) {
            return null;
        }
    }

    private void initAngleBitmap(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        this.frameLeft = i / 5;
        this.frameTop = i2 / 5;
        this.frameW = (i * 3) / 5;
        this.frameH = this.frameW;
        this.bitmap = BitmapFactory.decodeResource(resources, R.drawable.bundle_qrcode_scan_frame);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAngle(canvas);
        drawShadow(canvas);
    }

    private void drawAngle(Canvas canvas) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.bitmap);
        bitmapDrawable.setBounds(this.frameLeft, this.frameTop, this.frameLeft + this.frameW, this.frameTop + this.frameH);
        bitmapDrawable.draw(canvas);
    }

    private void drawShadow(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(this.shadowColor);
        Paint paint2 = paint;
        canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) this.frameTop, paint2);
        canvas.drawRect(0.0f, (float) this.frameTop, (float) this.frameLeft, (float) (this.frameTop + this.frameH), paint2);
        canvas.drawRect((float) (this.frameLeft + this.frameW), (float) this.frameTop, (float) getWidth(), (float) (this.frameTop + this.frameH), paint2);
        canvas.drawRect(0.0f, (float) (this.frameTop + this.frameH), (float) getWidth(), (float) getHeight(), paint2);
    }

    public void setFramingLeft(int i) {
        this.frameLeft = i;
        postInvalidate();
    }

    public void setFramingTop(int i) {
        this.frameTop = i;
        postInvalidate();
    }

    public void setFramingWidth(int i) {
        this.frameW = i;
        postInvalidate();
    }

    public void setFramingHeight(int i) {
        this.frameH = i;
        postInvalidate();
    }
}
