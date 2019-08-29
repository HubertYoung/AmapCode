package com.autonavi.map.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;

public final class MapViewLayoutParams extends LayoutParams {
    public static final int BOTTOM = 80;
    public static final int BOTTOM_CENTER = 81;
    public static final int CENTER = 17;
    public static final int CENTER_HORIZONTAL = 1;
    public static final int CENTER_VERTICAL = 16;
    public static final int LEFT = 3;
    public static final int MODE_MAP = 0;
    public static final int MODE_VIEW = 1;
    public static final int RIGHT = 5;
    public static final int TOP = 48;
    public static final int TOP_LEFT = 51;
    public int alignment;
    public int mode;
    public GLGeoPoint point;
    public int x;
    public int y;

    public MapViewLayoutParams(int i, int i2, int i3, int i4, int i5) {
        super(i, i2);
        this.mode = 1;
        this.x = i3;
        this.y = i4;
        this.alignment = i5;
    }

    public MapViewLayoutParams(int i, int i2, GLGeoPoint gLGeoPoint, int i3) {
        super(i, i2);
        this.mode = 0;
        this.point = gLGeoPoint;
        this.x = 0;
        this.y = 0;
        this.alignment = i3;
    }

    public MapViewLayoutParams(int i, int i2, GLGeoPoint gLGeoPoint, int i3, int i4, int i5) {
        super(i, i2);
        this.mode = 0;
        this.point = gLGeoPoint;
        this.x = i3;
        this.y = i4;
        this.alignment = i5;
    }

    public MapViewLayoutParams(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mode = 1;
        this.alignment = 51;
    }

    public MapViewLayoutParams(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.mode = 1;
        this.alignment = 51;
    }

    public MapViewLayoutParams(int i, int i2) {
        super(i, i2);
        this.mode = 1;
        this.alignment = -1;
    }
}
