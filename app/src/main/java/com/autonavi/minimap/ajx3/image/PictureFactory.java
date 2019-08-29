package com.autonavi.minimap.ajx3.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.text.TextUtils;

public class PictureFactory {
    public static boolean hasBoxShadow(int i, int i2, int i3) {
        return (i == 0 && i2 == 0 && i3 == 0) ? false : true;
    }

    public static Drawable edit(@NonNull Context context, Bitmap bitmap, PictureParams pictureParams) {
        boolean z = false;
        boolean z2 = bitmap != null;
        if (pictureParams != null) {
            boolean z3 = !TextUtils.isEmpty(pictureParams.background);
            if (z3) {
                bitmap = null;
                z2 = false;
            }
            if (z2 && pictureParams.blur > 0.0f) {
                float f = pictureParams.blur;
                if (f > 25.0f) {
                    f = 25.0f;
                }
                bitmap = blurBitmap(bitmap, context, f);
            }
            boolean hasBorder = hasBorder(pictureParams.borderWidth);
            boolean hasCornerRadius = hasCornerRadius(pictureParams.cornerRadius);
            boolean z4 = z2 && hasStretch(pictureParams.stretch);
            boolean z5 = z2 && pictureParams.bgColor != 0;
            if (pictureParams.scaleMode != 0) {
                z = true;
            }
            if (hasBorder || hasCornerRadius || z || z5 || z3) {
                return new ShapeDrawable(context.getResources(), pictureParams, bitmap);
            }
            if (z4) {
                return NinePatch.create(context.getResources(), bitmap, pictureParams.stretch, pictureParams.imageSize);
            }
            if (pictureParams.bgColor != 0) {
                return new ColorDrawable(pictureParams.bgColor);
            }
            if (z2) {
                return new BitmapDrawable(context.getResources(), bitmap);
            }
            return null;
        } else if (z2) {
            return new BitmapDrawable(context.getResources(), bitmap);
        } else {
            return null;
        }
    }

    public static boolean hasBorder(int[] iArr) {
        return iArr != null && iArr.length == 4 && (iArr[0] > 0 || iArr[1] > 0 || iArr[2] > 0 || iArr[3] > 0);
    }

    public static boolean hasCornerRadius(int[] iArr) {
        return iArr != null && iArr.length == 4 && (iArr[0] > 0 || iArr[1] > 0 || iArr[2] > 0 || iArr[3] > 0);
    }

    public static boolean hasStretch(int[] iArr) {
        return iArr != null && iArr.length == 4 && (iArr[0] > 0 || iArr[1] > 0 || iArr[2] > 0 || iArr[3] > 0);
    }

    private static Bitmap blurBitmap(Bitmap bitmap, Context context, float f) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        RenderScript create = RenderScript.create(context);
        ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
        Allocation createFromBitmap = Allocation.createFromBitmap(create, bitmap);
        Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap);
        create2.setRadius(f);
        create2.setInput(createFromBitmap);
        create2.forEach(createFromBitmap2);
        createFromBitmap2.copyTo(createBitmap);
        create.destroy();
        return createBitmap;
    }
}
