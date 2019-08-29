package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.ReusableBitmapDrawable;

public class ResBitmapDrawable extends ReusableBitmapDrawable {
    private ResBitmapDrawable(Resources resources, Bitmap bitmap) {
        super(resources, bitmap);
        setTargetDensity(resources.getDisplayMetrics().densityDpi);
    }

    public static ReusableBitmapDrawable create(Resources resources, Bitmap bitmap, int density) {
        bitmap.setDensity(density);
        return new ResBitmapDrawable(resources, bitmap);
    }
}
