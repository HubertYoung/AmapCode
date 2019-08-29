package com.autonavi.bundle.uitemplate.indoor;

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

public class RedesignFloorWidgetViewLayout extends RelativeLayout {
    private RectF mClipRect;
    private float[] mCorners;
    a mOwner;
    private Path mPath;

    public interface a {
        int a();

        int b();

        View c();
    }

    public RedesignFloorWidgetViewLayout(Context context) {
        super(context);
        init(context);
    }

    public RedesignFloorWidgetViewLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        this.mPath = new Path();
        this.mClipRect = new RectF();
        float dimensionPixelSize = (float) getContext().getResources().getDimensionPixelSize(R.dimen.floor_widget_bg_corner);
        this.mCorners = new float[]{dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize};
    }

    public void setOwner(a aVar) {
        this.mOwner = aVar;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        super.onMeasure(i, i2);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.floor_widget_width);
        int dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(R.dimen.floor_widget_header_height);
        int dimensionPixelSize3 = getContext().getResources().getDimensionPixelSize(R.dimen.floor_widget_cell_height);
        if (this.mOwner != null) {
            int b = this.mOwner.b();
            i3 = b < this.mOwner.a() ? (dimensionPixelSize3 * b) + (dimensionPixelSize2 * 2) : (dimensionPixelSize3 * this.mOwner.a()) + (dimensionPixelSize2 * 2);
        } else {
            i3 = (dimensionPixelSize3 * 3) + (dimensionPixelSize2 * 2);
        }
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            dimensionPixelSize = size;
        } else if (mode == Integer.MIN_VALUE) {
            dimensionPixelSize = Math.min(dimensionPixelSize, size);
        }
        if (mode2 == 1073741824) {
            i3 = size2;
        } else if (mode2 == Integer.MIN_VALUE) {
            i3 = Math.min(i3, size2);
        }
        if (!(this.mOwner == null || this.mOwner.c() == null)) {
            this.mOwner.c().measure(MeasureSpec.makeMeasureSpec(dimensionPixelSize, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(i3, UCCore.VERIFY_POLICY_QUICK));
        }
        setMeasuredDimension(dimensionPixelSize, i3);
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
