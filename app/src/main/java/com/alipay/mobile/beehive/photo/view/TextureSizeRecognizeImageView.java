package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;

public class TextureSizeRecognizeImageView extends ImageView {
    public static boolean IS_MAX_TEXTURE_SIZE_INIT = false;
    public static int MAX_TEXTURE_SIZE = 2048;
    private static final String TAG = "TextureSizeRecognizeImageView";

    public TextureSizeRecognizeImageView(Context context) {
        this(context, null, 0);
    }

    public TextureSizeRecognizeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextureSizeRecognizeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!IS_MAX_TEXTURE_SIZE_INIT && canvas.isHardwareAccelerated()) {
            MAX_TEXTURE_SIZE = Math.min(canvas.getMaximumBitmapHeight(), canvas.getMaximumBitmapWidth());
            PhotoLogger.debug(TAG, "MAX_TEXTURE_SIZE = " + MAX_TEXTURE_SIZE);
            IS_MAX_TEXTURE_SIZE_INIT = true;
        }
    }
}
