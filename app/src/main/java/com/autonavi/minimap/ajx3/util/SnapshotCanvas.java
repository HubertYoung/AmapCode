package com.autonavi.minimap.ajx3.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

public class SnapshotCanvas extends Canvas {
    public SnapshotCanvas(@NonNull Bitmap bitmap) {
        super(bitmap);
    }
}
