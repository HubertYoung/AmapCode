package com.autonavi.map.core.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.amap.bundle.logs.AMapLog;

public class MvpImageView extends ImageView implements brh {
    private Context mContext;

    public MvpImageView(Context context) {
        this(context, null);
    }

    public MvpImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MvpImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
    }

    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(ScaleType.FIT_CENTER);
        String simpleName = getClass().getSimpleName();
        String simpleName2 = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append(simpleName);
        sb.append(" can't support other scale type!");
        AMapLog.e(simpleName2, sb.toString());
    }

    @Deprecated
    public void setBackgroundLayers(int i, int i2, int i3, int i4) {
        setBackgroundLayers(ContextCompat.getDrawable(this.mContext, i), ContextCompat.getDrawable(this.mContext, i2), i3, i4);
    }

    @Deprecated
    public void setBackgroundLayers(Drawable drawable, Drawable drawable2, int i, int i2) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        int i3 = 2;
        Drawable[] drawableArr = new Drawable[(drawable2 == null ? 2 : 3)];
        drawableArr[0] = ContextCompat.getDrawable(this.mContext, i2);
        drawableArr[1] = ContextCompat.getDrawable(this.mContext, i);
        if (drawable2 != null) {
            drawableArr[2] = drawable2;
        }
        stateListDrawable.addState(new int[]{16842919}, new LayerDrawable(drawableArr));
        if (drawable == null) {
            i3 = 1;
        }
        Drawable[] drawableArr2 = new Drawable[i3];
        drawableArr2[0] = ContextCompat.getDrawable(this.mContext, i2);
        if (drawable != null) {
            drawableArr2[1] = drawable;
        }
        stateListDrawable.addState(new int[0], new LayerDrawable(drawableArr2));
        setBackground(stateListDrawable);
    }
}
