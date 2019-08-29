package com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import java.io.InputStream;

public class APMBitmapDrawable extends BitmapDrawable {
    public APMBitmapDrawable() {
    }

    public APMBitmapDrawable(Resources res) {
        super(res);
    }

    public APMBitmapDrawable(Bitmap bitmap) {
        super(bitmap);
    }

    public APMBitmapDrawable(Resources res, Bitmap bitmap) {
        super(res, bitmap);
    }

    public APMBitmapDrawable(String filepath) {
        super(filepath);
    }

    public APMBitmapDrawable(Resources res, String filepath) {
        super(res, filepath);
    }

    public APMBitmapDrawable(InputStream is) {
        super(is);
    }

    public APMBitmapDrawable(Resources res, InputStream is) {
        super(res, is);
    }
}
