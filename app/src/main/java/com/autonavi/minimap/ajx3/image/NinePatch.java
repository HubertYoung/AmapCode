package com.autonavi.minimap.ajx3.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

class NinePatch {
    private static final int NO_COLOR = 1;

    public static class Range {
        public int end;
        public int start;
    }

    public static class RangeLists {
        public List<Range> rangeListX;
        public List<Range> rangeListY;
    }

    NinePatch() {
    }

    public static Drawable create(Resources resources, Bitmap bitmap, int[] iArr, float f) {
        float f2;
        float f3 = resources.getDisplayMetrics().density;
        if (f > 0.0f && f3 != f) {
            f2 = f / f3;
            int width = (int) (((float) bitmap.getWidth()) / f2);
            int height = (int) (((float) bitmap.getHeight()) / f2);
            if (width > 0 && height > 0) {
                bitmap = resize(bitmap, width, height);
                Bitmap bitmap2 = bitmap;
                float f4 = (f * 0.5f) / f2;
                Resources resources2 = resources;
                NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(resources2, bitmap2, getByteBuffer(bitmap2, (int) (((float) iArr[0]) * f4), (int) (((float) iArr[3]) * f4), (int) (((float) iArr[2]) * f4), (int) (((float) iArr[1]) * f4)).array(), new Rect(), null);
                return ninePatchDrawable;
            }
        }
        f2 = 1.0f;
        Bitmap bitmap22 = bitmap;
        float f42 = (f * 0.5f) / f2;
        Resources resources22 = resources;
        NinePatchDrawable ninePatchDrawable2 = new NinePatchDrawable(resources22, bitmap22, getByteBuffer(bitmap22, (int) (((float) iArr[0]) * f42), (int) (((float) iArr[3]) * f42), (int) (((float) iArr[2]) * f42), (int) (((float) iArr[1]) * f42)).array(), new Rect(), null);
        return ninePatchDrawable2;
    }

    private static ByteBuffer getByteBuffer(Bitmap bitmap, int i, int i2, int i3, int i4) {
        RangeLists checkBitmap = checkBitmap(bitmap, i, i2, i3, i4);
        List<Range> list = checkBitmap.rangeListX;
        List<Range> list2 = checkBitmap.rangeListY;
        ByteBuffer order = ByteBuffer.allocate((list.size() * 8) + 32 + (list2.size() * 8) + 36).order(ByteOrder.nativeOrder());
        order.put(1);
        order.put((byte) (list.size() * 2));
        order.put((byte) (list2.size() * 2));
        order.put(9);
        order.putInt(0);
        order.putInt(0);
        order.putInt(0);
        order.putInt(0);
        order.putInt(0);
        order.putInt(0);
        order.putInt(0);
        for (Range next : list) {
            order.putInt(next.start);
            order.putInt(next.end);
        }
        for (Range next2 : list2) {
            order.putInt(next2.start);
            order.putInt(next2.end);
        }
        order.putInt(1);
        order.putInt(1);
        order.putInt(1);
        order.putInt(1);
        order.putInt(1);
        order.putInt(1);
        order.putInt(1);
        order.putInt(1);
        order.putInt(1);
        return order;
    }

    public static RangeLists checkBitmap(Bitmap bitmap, int i, int i2, int i3, int i4) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Range range = new Range();
        if (i2 <= width) {
            range.start = i2 - 1;
            range.end = (width - i4) - 1;
            if (range.end <= range.start) {
                range.end = range.start + 1;
            }
        } else {
            int i5 = width - 1;
            range.start = i5;
            range.end = i5;
        }
        Range range2 = new Range();
        if (i <= height) {
            range2.start = i - 1;
            range2.end = (height - i3) - 1;
            if (range2.end <= range2.start) {
                range2.end = range2.start + 1;
            }
        } else {
            int i6 = height - 1;
            range2.start = i6;
            range2.end = i6;
        }
        if (range.start < 0) {
            range.start = 0;
        }
        if (range.start >= width) {
            range.start = width - 1;
        }
        if (range.end < 0) {
            range.end = 0;
        }
        if (range.end >= width) {
            range.end = width - 1;
        }
        if (range2.start < 0) {
            range2.start = 0;
        }
        if (range2.start >= height) {
            range2.start = height - 1;
        }
        if (range2.end < 0) {
            range2.end = 0;
        }
        if (range2.end >= height) {
            range2.end = height - 1;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(range);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(range2);
        RangeLists rangeLists = new RangeLists();
        rangeLists.rangeListX = arrayList;
        rangeLists.rangeListY = arrayList2;
        return rangeLists;
    }

    private static Bitmap resize(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i) / ((float) width), ((float) i2) / ((float) height));
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}
