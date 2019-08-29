package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class APImageView extends ImageView {
    public APImageView(Context context) {
        super(context);
    }

    public APImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public APImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        super.setOnClickListener(APViewEventHelper.a(onClickListener));
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (getDrawable() instanceof APRecyclingBitmapDrawable) {
            setImageDrawable(null);
        }
        super.onDetachedFromWindow();
    }

    public void setImageDrawable(Drawable drawable) {
        Drawable drawable2 = getDrawable();
        super.setImageDrawable(drawable);
        a(drawable, true);
        a(drawable2, false);
    }

    private static void a(Drawable drawable, boolean z) {
        if (drawable instanceof APRecyclingBitmapDrawable) {
            ((APRecyclingBitmapDrawable) drawable).a(z);
            return;
        }
        if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            int numberOfLayers = layerDrawable.getNumberOfLayers();
            for (int i = 0; i < numberOfLayers; i++) {
                a(layerDrawable.getDrawable(i), z);
            }
        }
    }
}
