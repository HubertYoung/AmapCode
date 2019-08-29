package com.autonavi.bundle.scenicarea.scenicguide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;

public class ScenicGuideWidgetViewLayout extends RelativeLayout {
    private int desiredWidth;
    private int headerHeight;
    private int itemHeight;
    private int itemHeightDivider;
    private RectF mClipRect;
    private float[] mCorners;
    a mOwner;
    private Path mPath;

    public interface a {
        int a();

        int b();

        View c();
    }

    public ScenicGuideWidgetViewLayout(Context context) {
        super(context);
        init(context);
    }

    public ScenicGuideWidgetViewLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        this.mPath = new Path();
        this.mClipRect = new RectF();
        float dimensionPixelSize = (float) getContext().getResources().getDimensionPixelSize(R.dimen.floor_widget_bg_corner);
        this.mCorners = new float[]{dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize};
        this.desiredWidth = getContext().getResources().getDimensionPixelSize(R.dimen.floor_widget_width);
        this.headerHeight = getContext().getResources().getDimensionPixelSize(R.dimen.floor_widget_header_height);
        this.itemHeight = getContext().getResources().getDimensionPixelSize(R.dimen.map_scenic_guide_widget_cell_height);
        this.itemHeightDivider = getContext().getResources().getDimensionPixelSize(R.dimen.map_scenic_guide_widget_cell_divider_height);
    }

    public void setOwner(a aVar) {
        this.mOwner = aVar;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        super.onMeasure(i, i2);
        if (this.mOwner != null) {
            int b = this.mOwner.b();
            if (b < this.mOwner.a()) {
                if (b > 1) {
                    i3 = (this.itemHeight * b) + (this.headerHeight * 2) + ((b - 1) * this.itemHeightDivider);
                } else {
                    i3 = (this.itemHeight * b) + (this.headerHeight * 2);
                }
            } else if (this.mOwner.a() > 1) {
                i3 = ((this.mOwner.a() - 1) * this.itemHeightDivider) + (this.itemHeight * this.mOwner.a()) + (this.headerHeight * 2);
            } else {
                i3 = (this.itemHeight * this.mOwner.a()) + (this.headerHeight * 2);
            }
        } else {
            i3 = (this.itemHeightDivider * 1) + (this.itemHeight * 2) + (this.headerHeight * 2);
        }
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            if (mode == Integer.MIN_VALUE) {
                size = Math.min(this.desiredWidth, size);
            } else {
                size = this.desiredWidth;
            }
        }
        if (mode2 == 1073741824) {
            i3 = size2;
        } else if (mode2 == Integer.MIN_VALUE) {
            i3 = Math.min(i3, size2);
        }
        if (!(this.mOwner == null || this.mOwner.c() == null)) {
            this.mOwner.c().measure(MeasureSpec.makeMeasureSpec(size, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(i3, UCCore.VERIFY_POLICY_QUICK));
        }
        setMeasuredDimension(size, i3);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        this.mPath.reset();
        this.mClipRect.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        this.mPath.addRoundRect(this.mClipRect, this.mCorners, Direction.CW);
        canvas.clipPath(this.mPath);
        super.dispatchDraw(canvas);
    }
}
