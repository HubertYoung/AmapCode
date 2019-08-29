package com.alipay.biometrics.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Region.Op;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.alipay.android.phone.mobilecommon.biometric.a.a.f;
import com.alipay.mobile.security.bio.utils.BioLog;

public class CircleFrameLayout extends FrameLayout {
    private final String BRAND = "Meizu";
    private float circleX = -1.0f;
    private float circleY = -1.0f;
    boolean isEnable;
    WidgetListener widgetListener;

    public interface WidgetListener {
        void notifyError(int i);
    }

    public CircleFrameLayout(Context context) {
        super(context);
    }

    public CircleFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public CircleFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f.bio_circle_frrameLayout);
        this.isEnable = obtainStyledAttributes.getBoolean(f.bio_circle_frrameLayout_bio_facesdk_enabled, false);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        if (this.isEnable) {
            if (this.circleX < 0.0f) {
                this.circleX = (((float) canvas.getWidth()) * 1.0f) / 2.0f;
                this.circleY = (((float) canvas.getHeight()) * 1.0f) / 2.0f;
            }
            Path path = new Path();
            path.addCircle(this.circleX, this.circleY, this.circleX, Direction.CCW);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            try {
                if (!canvas.clipPath(path, Op.REPLACE) && this.widgetListener != null) {
                    "Meizu".equalsIgnoreCase(Build.BRAND);
                }
            } catch (UnsupportedOperationException e) {
                BioLog.e(e.toString());
            }
        }
        super.dispatchDraw(canvas);
    }

    public void setEnable(boolean z) {
        this.isEnable = z;
        postInvalidate();
    }

    public void setWidgetListener(WidgetListener widgetListener2) {
        this.widgetListener = widgetListener2;
    }
}
