package com.alipay.mobile.antui.basic.banner;

import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;

public class RectDrawable {
    public static Drawable createSmallRectDrawable(int unSelectColor, int mHeight, int mSmallWidth) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(0.0f, (float) mHeight);
        path.lineTo((float) mSmallWidth, (float) mHeight);
        path.lineTo((float) mSmallWidth, 0.0f);
        path.close();
        ShapeDrawable drawable = new ShapeDrawable(new PathShape(path, (float) mSmallWidth, (float) mHeight));
        drawable.getPaint().setColor(unSelectColor);
        return drawable;
    }

    public static Drawable createBigRectDrawable(int color, int mHeight, int mBigWidth) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(0.0f, (float) mHeight);
        path.lineTo((float) mBigWidth, (float) mHeight);
        path.lineTo((float) mBigWidth, 0.0f);
        path.close();
        ShapeDrawable drawable = new ShapeDrawable(new PathShape(path, (float) mBigWidth, (float) mHeight));
        drawable.getPaint().setColor(color);
        return drawable;
    }
}
