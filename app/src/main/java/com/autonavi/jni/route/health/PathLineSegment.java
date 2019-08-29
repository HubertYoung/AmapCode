package com.autonavi.jni.route.health;

import com.autonavi.common.model.GeoPoint;
import java.util.ArrayList;

public class PathLineSegment {
    private ArrayList<Integer> colors = new ArrayList<>();
    private GeoPoint lastPoint = null;
    private int lastPointSpeed;
    private ArrayList<GeoPoint> points = new ArrayList<>();

    private int colorPercent(int i, int i2, float f) {
        int i3 = (i >> 24) & 255;
        int i4 = (i >> 16) & 255;
        int i5 = (i >> 8) & 255;
        int i6 = i & 255;
        return ((int) (((double) i6) + ((double) (((float) ((i2 & 255) - i6)) * f)) + 0.5d)) | (((int) (((double) i3) + (((double) (((float) (((i2 >> 24) & 255) - i3)) * f)) + 0.5d))) << 24) | (((int) (((double) i4) + (((double) (((float) (((i2 >> 16) & 255) - i4)) * f)) + 0.5d))) << 16) | (((int) (((double) i5) + (((double) (((float) (((i2 >> 8) & 255) - i5)) * f)) + 0.5d))) << 8);
    }

    /* access modifiers changed from: protected */
    public void addPoint(GeoPoint geoPoint, int i, int[] iArr, int[] iArr2) {
        if (this.lastPoint != null) {
            checkAddMiddlePoint(geoPoint, i, this.lastPoint, this.lastPointSpeed, iArr, iArr2);
        }
        innerAddPoint(geoPoint, i, iArr, iArr2);
    }

    private void checkAddMiddlePoint(GeoPoint geoPoint, int i, GeoPoint geoPoint2, int i2, int[] iArr, int[] iArr2) {
        GeoPoint geoPoint3;
        GeoPoint geoPoint4;
        int[] iArr3 = iArr;
        if (iArr3.length > 2) {
            int i3 = i;
            int i4 = i2;
            if (i3 > i4) {
                geoPoint3 = geoPoint;
                geoPoint4 = geoPoint2;
            } else {
                geoPoint4 = geoPoint;
                geoPoint3 = geoPoint2;
                int i5 = i4;
                i4 = i3;
                i3 = i5;
            }
            int length = iArr3.length - 1;
            for (int i6 = 1; i6 < length; i6++) {
                if (iArr3[i6] > i4 && iArr3[i6] < i3) {
                    int i7 = iArr3[i6];
                    int i8 = iArr2[i6];
                    float f = ((float) (i7 - i4)) / ((float) (i3 - i4));
                    GeoPoint geoPoint5 = new GeoPoint();
                    geoPoint5.x = geoPoint4.x + ((int) (((double) (((float) (geoPoint3.x - geoPoint4.x)) * f)) + 0.5d));
                    geoPoint5.y = geoPoint4.y + ((int) (((double) (((float) (geoPoint3.y - geoPoint4.y)) * f)) + 0.5d));
                    innerAddPoint(geoPoint5, i7, i8);
                }
            }
        }
    }

    private void innerAddPoint(GeoPoint geoPoint, int i, int i2) {
        this.points.add(geoPoint);
        this.colors.add(Integer.valueOf(i2));
        this.lastPoint = geoPoint;
        this.lastPointSpeed = i;
    }

    private void innerAddPoint(GeoPoint geoPoint, int i, int[] iArr, int[] iArr2) {
        innerAddPoint(geoPoint, i, getColor(i, iArr, iArr2));
    }

    private int getColor(int i, int[] iArr, int[] iArr2) {
        int i2 = iArr2[0];
        int length = iArr.length;
        if (i <= iArr[0]) {
            return iArr2[0];
        }
        int i3 = length - 1;
        if (i >= iArr[i3]) {
            return iArr2[i3];
        }
        for (int i4 = 1; i4 < length; i4++) {
            if (i == iArr[i4]) {
                return iArr2[i4];
            }
            if (i < iArr[i4]) {
                int i5 = i4 - 1;
                return colorPercent(iArr2[i5], iArr2[i4], ((float) (i - iArr[i5])) / ((float) (iArr[i4] - iArr[i5])));
            }
        }
        return i2;
    }

    /* access modifiers changed from: protected */
    public int getSize() {
        if (this.points != null) {
            return this.points.size();
        }
        return 0;
    }

    public GeoPoint[] getGeoPointArray() {
        return (GeoPoint[]) this.points.toArray(new GeoPoint[this.points.size()]);
    }

    public int[] getColorArray() {
        int[] iArr = new int[0];
        if (this.colors == null) {
            return iArr;
        }
        int size = this.colors.size();
        int[] iArr2 = new int[size];
        for (int i = 0; i < size; i++) {
            iArr2[i] = this.colors.get(i).intValue();
        }
        return iArr2;
    }
}
