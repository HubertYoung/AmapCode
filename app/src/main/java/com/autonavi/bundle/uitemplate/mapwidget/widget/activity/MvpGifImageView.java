package com.autonavi.bundle.uitemplate.mapwidget.widget.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView.ScaleType;
import com.autonavi.widget.gif.GifImageView;

public class MvpGifImageView extends GifImageView implements brh {
    private boolean mNeedCrop;

    public MvpGifImageView(Context context) {
        this(context, null);
    }

    public MvpGifImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MvpGifImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNeedCrop = false;
    }

    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(ScaleType.FIT_CENTER);
        String simpleName = getClass().getSimpleName();
        String simpleName2 = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append(simpleName);
        sb.append(" can't support other scale type!");
        bez.b(simpleName2, sb.toString(), new bew[0]);
    }

    public void setNeedCrop(boolean z) {
        this.mNeedCrop = z;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mNeedCrop) {
            int[] imageResourceSize = getImageResourceSize();
            float f = ((float) imageResourceSize[1]) / ((float) imageResourceSize[0]);
            canvas.scale(f, f);
            float f2 = -(1.0f - (1.0f / f));
            canvas.translate(((float) getWidth()) * f2 * 0.5f, f2 * ((float) getHeight()));
        }
        super.onDraw(canvas);
    }
}
