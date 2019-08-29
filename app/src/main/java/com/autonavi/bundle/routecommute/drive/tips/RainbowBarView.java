package com.autonavi.bundle.routecommute.drive.tips;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.PaintDrawable;
import android.support.v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class RainbowBarView extends ImageView {
    private float[] ROUND_CORNER_END;
    private float[] ROUND_CORNER_START;
    private List<azx> rainbowItemDatas;

    public RainbowBarView(Context context) {
        super(context);
        init();
    }

    public RainbowBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public RainbowBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        float applyDimension = TypedValue.applyDimension(1, 6.0f, getResources().getDisplayMetrics());
        this.ROUND_CORNER_START = new float[]{applyDimension, applyDimension, 0.0f, 0.0f, 0.0f, 0.0f, applyDimension, applyDimension};
        this.ROUND_CORNER_END = new float[]{0.0f, 0.0f, applyDimension, applyDimension, applyDimension, applyDimension, 0.0f, 0.0f};
    }

    private void fillTestData() {
        this.rainbowItemDatas = new ArrayList();
        this.rainbowItemDatas.add(new azx(0.30000001192092896d, -16711936));
        this.rainbowItemDatas.add(new azx(0.4000000059604645d, SupportMenu.CATEGORY_MASK));
        this.rainbowItemDatas.add(new azx(0.30000001192092896d, -16776961));
    }

    public void setRainbowData(List<azx> list) {
        if (list != null && list.size() > 0) {
            this.rainbowItemDatas = list;
            postInvalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.rainbowItemDatas != null && this.rainbowItemDatas.size() != 0) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            int i = 0;
            for (azx next : this.rainbowItemDatas) {
                int i2 = (int) (next.b * ((double) measuredWidth));
                PaintDrawable paintDrawable = new PaintDrawable(next.c);
                int indexOf = this.rainbowItemDatas.indexOf(next);
                if (indexOf == 0) {
                    paintDrawable.setCornerRadii(this.ROUND_CORNER_START);
                } else if (indexOf == this.rainbowItemDatas.size() - 1) {
                    paintDrawable.setCornerRadii(this.ROUND_CORNER_END);
                } else {
                    paintDrawable.setCornerRadius(0.0f);
                }
                int i3 = i + i2;
                paintDrawable.setBounds(i, 0, i3, measuredHeight);
                paintDrawable.draw(canvas);
                i = i3;
            }
        }
    }
}
