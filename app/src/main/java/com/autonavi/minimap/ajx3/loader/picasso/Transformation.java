package com.autonavi.minimap.ajx3.loader.picasso;

import android.graphics.Bitmap;

public interface Transformation {
    String key();

    Bitmap transform(Bitmap bitmap);
}
