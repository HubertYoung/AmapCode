package com.ali.user.mobile.ui.widget;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.common.image.ImageLoader.ClipsInfo;
import com.autonavi.sdk.log.util.LogConstant;

public class ImageLoader {
    public static void a(String str, ImageView imageView, Drawable drawable) {
        try {
            imageView.setImageDrawable(drawable);
            AliUserLog.c("ImageLoader", "call common ImageLoader");
            com.alipay.android.phone.inside.common.image.ImageLoader.a(imageView, str, false, new ClipsInfo());
            AliUserLog.c("ImageLoader", LogConstant.SPLASH_SCREEN_DOWNLOADED);
        } catch (Throwable th) {
            AliUserLog.a("ImageLoader", "download image error", th);
        }
    }
}
