package com.autonavi.minimap.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class CommonView extends ImageView {
    private Bitmap bitmap = null;

    public CommonView(Context context, drr drr, Bitmap bitmap2) {
        super(context);
        this.bitmap = bitmap2;
        init(drr, bitmap2);
    }

    private void init(drr drr, Bitmap bitmap2) {
        setLayoutParams(new LayoutParams(-1, -1));
        setBackgroundColor(-1);
        setScaleType(ScaleType.FIT_XY);
        setImageBitmap(bitmap2);
    }

    public void destroy() {
        if (this.bitmap != null) {
            this.bitmap.recycle();
        }
    }
}
