package com.alipay.mobile.common.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public class ImageDrawable extends BitmapDrawable {
    private Drawable a;

    public ImageDrawable(Resources res, Bitmap bitmap, Bitmap holdPlacer) {
        super(res, bitmap);
        this.a = new BitmapDrawable(res, holdPlacer);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public ImageDrawable(Resources res, Bitmap bitmap, int holderId) {
        super(res, bitmap);
        this.a = res.getDrawable(holderId);
    }

    public void draw(Canvas canvas) {
        if (getBitmap() == null || getBitmap().isRecycled()) {
            this.a.setBounds(copyBounds());
            this.a.draw(canvas);
            return;
        }
        super.draw(canvas);
    }
}
