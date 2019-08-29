package com.autonavi.minimap.base.overlay;

import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;

public class LineOverlayItem {
    public static final int TYPE_MARKER_LINE = 2;
    public static final int TYPE_MARKER_LINE_ARROW = 3;
    public static final int TYPE_MARKER_LINE_COLOR = 1;
    public static final int TYPE_MARKER_LINE_DOT = 4;
    public static final int TYPE_MARKER_LINE_DOT_ARROW = 6;
    public static final int TYPE_MARKER_LINE_DOT_COLOR = 5;
    public static final int TYPE_MARKER_LINE_LONG_DOT_ARROW = 7;
    public static final int TYPE_MARKER_LINE_MINIAPP_ICONPATH_ARROW = 8;
    public long mLineItemId;
    public ama mLineProperty = new ama();
    public int mLineType;
    public GeoPoint[] mPoints;

    public void setbTexPreMulAlpha(boolean z) {
    }

    public LineOverlayItem(int i, GeoPoint[] geoPointArr, int i2) {
        this.mLineType = i;
        this.mPoints = geoPointArr;
        setLineItemProperty(i);
        this.mLineProperty.a = GeoPoint.geoPoints2GlGeoPoints(geoPointArr);
        this.mLineProperty.j = i2;
        this.mLineProperty.k = i2;
        this.mLineProperty.z = true;
        this.mLineProperty.g = -1;
        this.mLineProperty.i = -1;
        this.mLineProperty.f = -1;
        this.mLineProperty.h = -1;
    }

    public void setFillLineColor(int i) {
        this.mLineProperty.g = i;
    }

    public void setFillLineId(int i) {
        this.mLineProperty.f = i;
    }

    public void setBackgroundColor(int i) {
        this.mLineProperty.i = i;
    }

    public void setBackgrondId(int i) {
        this.mLineProperty.h = i;
    }

    public void setIsRefreshMap(boolean z) {
        this.mLineProperty.z = z;
    }

    public void setIsColorGradient(boolean z) {
        this.mLineProperty.A = z;
    }

    public void setGeoPointsSpeeds(int[] iArr) {
        this.mLineProperty.c = iArr;
    }

    public void setMatchSpeeds(int[] iArr) {
        this.mLineProperty.d = iArr;
    }

    public void setMatchColors(int[] iArr) {
        this.mLineProperty.e = iArr;
    }

    public void setBorderLineWidth(int i) {
        this.mLineProperty.k = i;
    }

    private void setLineItemProperty(int i) {
        switch (i) {
            case 1:
                this.mLineProperty.l = 0.05f;
                this.mLineProperty.m = 0.5f;
                this.mLineProperty.n = 0.95f;
                this.mLineProperty.o = 0.5f;
                this.mLineProperty.r = 0.05f;
                this.mLineProperty.s = 0.5f;
                this.mLineProperty.t = 0.95f;
                this.mLineProperty.u = 0.75f;
                this.mLineProperty.q = 32.0f;
                this.mLineProperty.y = true;
                this.mLineProperty.x = true;
                this.mLineProperty.v = false;
                this.mLineProperty.w = true;
                return;
            case 2:
                this.mLineProperty.l = 0.0f;
                this.mLineProperty.m = 0.5f;
                this.mLineProperty.n = 1.0f;
                this.mLineProperty.o = 0.5f;
                this.mLineProperty.r = 0.0f;
                this.mLineProperty.s = 0.5f;
                this.mLineProperty.t = 1.0f;
                this.mLineProperty.u = 0.75f;
                this.mLineProperty.q = 32.0f;
                this.mLineProperty.y = true;
                this.mLineProperty.x = false;
                this.mLineProperty.v = false;
                this.mLineProperty.w = true;
                return;
            case 3:
                this.mLineProperty.l = 0.0f;
                this.mLineProperty.m = 1.0f;
                this.mLineProperty.n = 0.5f;
                this.mLineProperty.o = 0.0f;
                this.mLineProperty.r = 0.5f;
                this.mLineProperty.s = 0.25f;
                this.mLineProperty.t = 1.0f;
                this.mLineProperty.u = 0.6f;
                this.mLineProperty.q = 64.0f;
                this.mLineProperty.y = true;
                this.mLineProperty.x = false;
                this.mLineProperty.v = false;
                this.mLineProperty.w = true;
                return;
            case 4:
                this.mLineProperty.l = 0.0f;
                this.mLineProperty.m = 1.0f;
                this.mLineProperty.n = 1.0f;
                this.mLineProperty.o = 0.0f;
                this.mLineProperty.q = 64.0f;
                this.mLineProperty.y = false;
                this.mLineProperty.x = false;
                this.mLineProperty.v = false;
                this.mLineProperty.w = true;
                return;
            case 5:
                this.mLineProperty.l = 0.0f;
                this.mLineProperty.m = 1.0f;
                this.mLineProperty.n = 1.0f;
                this.mLineProperty.o = 0.0f;
                this.mLineProperty.q = 32.0f;
                this.mLineProperty.y = false;
                this.mLineProperty.x = true;
                this.mLineProperty.v = false;
                this.mLineProperty.w = true;
                return;
            case 6:
                this.mLineProperty.l = 0.0f;
                this.mLineProperty.m = 1.0f;
                this.mLineProperty.n = 1.0f;
                this.mLineProperty.o = 0.0f;
                this.mLineProperty.q = 128.0f;
                this.mLineProperty.y = false;
                this.mLineProperty.x = true;
                this.mLineProperty.v = false;
                this.mLineProperty.w = true;
                return;
            case 7:
                this.mLineProperty.l = 0.0f;
                this.mLineProperty.m = 1.0f;
                this.mLineProperty.n = 1.0f;
                this.mLineProperty.o = 0.0f;
                this.mLineProperty.q = 256.0f;
                this.mLineProperty.y = false;
                this.mLineProperty.x = true;
                this.mLineProperty.v = false;
                this.mLineProperty.w = true;
                return;
            case 8:
                this.mLineProperty.l = 0.0f;
                this.mLineProperty.m = 1.0f;
                this.mLineProperty.n = 1.0f;
                this.mLineProperty.o = 0.0f;
                this.mLineProperty.q = 32.0f;
                this.mLineProperty.y = true;
                this.mLineProperty.x = true;
                this.mLineProperty.v = false;
                this.mLineProperty.w = true;
                break;
        }
    }

    public Rect getBound() {
        if (this.mPoints.length == 0) {
            return null;
        }
        int i = 999999999;
        int i2 = 999999999;
        int i3 = -999999999;
        int i4 = -999999999;
        for (int i5 = 0; i5 < this.mPoints.length; i5++) {
            i = Math.min(i, this.mPoints[i5].x);
            i2 = Math.min(i2, this.mPoints[i5].y);
            i3 = Math.max(i3, this.mPoints[i5].x);
            i4 = Math.max(i4, this.mPoints[i5].y);
        }
        Rect rect = new Rect();
        rect.set(i, i2, i3, i4);
        return rect;
    }
}
