package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

import android.content.res.Resources;
import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable.APMBitmapDrawable;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable.Reusable;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReusableBitmapDrawable extends APMBitmapDrawable implements Reusable {
    private AtomicBoolean mReused = new AtomicBoolean(false);

    public ReusableBitmapDrawable() {
    }

    public ReusableBitmapDrawable(Resources res) {
        super(res);
    }

    public ReusableBitmapDrawable(Bitmap bitmap) {
        super(bitmap);
    }

    public ReusableBitmapDrawable(Resources res, Bitmap bitmap) {
        super(res, bitmap);
    }

    public ReusableBitmapDrawable(String filepath) {
        super(filepath);
    }

    public ReusableBitmapDrawable(Resources res, String filepath) {
        super(res, filepath);
    }

    public ReusableBitmapDrawable(InputStream is) {
        super(is);
    }

    public ReusableBitmapDrawable(Resources res, InputStream is) {
        super(res, is);
    }

    public final Bitmap getReusableBitmap() {
        if (this.mReused.compareAndSet(false, true)) {
            return getBitmap();
        }
        return null;
    }

    public void reuse() {
    }
}
