package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.ReusableBitmapDrawable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class ImageDisplayUtils {
    public static final int APP_RESOURCE_KEY = 33554432;
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger((String) "ImageDisplayUtils");

    public static boolean display(final Bitmap bitmap, final ViewWrapper viewWrapper, final boolean bitmapReusable) {
        AppUtils.runOnUiThread(new Runnable() {
            public final void run() {
                if (ImageDisplayUtils.checkImageViewReused(viewWrapper)) {
                    ImageDisplayUtils.a.p("display bitmap checkImageViewReused return !", new Object[0]);
                } else if (viewWrapper.getTargetView() instanceof ImageView) {
                    ImageDisplayUtils.b(bitmap, (ImageView) viewWrapper.getTargetView(), bitmapReusable);
                }
            }
        });
        return true;
    }

    public static boolean display(final Drawable drawable, final ViewWrapper viewWrapper) {
        AppUtils.runOnUiThread(new Runnable() {
            public final void run() {
                if (ImageDisplayUtils.checkImageViewReused(viewWrapper)) {
                    ImageDisplayUtils.a.p("display drawable checkImageViewReused return !", new Object[0]);
                } else if (viewWrapper.getTargetView() instanceof ImageView) {
                    ImageDisplayUtils.b(drawable, (ImageView) viewWrapper.getTargetView());
                }
            }
        });
        return true;
    }

    /* access modifiers changed from: private */
    public static boolean b(Bitmap bitmap, ImageView imageView, boolean bitmapReusable) {
        boolean ret = false;
        if (ImageUtils.checkBitmap(bitmap)) {
            if (bitmapReusable) {
                ret = b(new ReusableBitmapDrawable(AppUtils.getResources(), bitmap), imageView);
            } else if (imageView != null) {
                imageView.setImageBitmap(bitmap);
                ret = true;
            }
        }
        if (AppUtils.isDebug(AppUtils.getApplicationContext())) {
            a.p("setImage bitmap: " + bitmap + ", imageView: " + imageView + ", reusable: " + bitmapReusable + ", ret: " + ret, new Object[0]);
        }
        return ret;
    }

    /* access modifiers changed from: private */
    public static boolean b(Drawable drawable, ImageView imageView) {
        if (imageView == null) {
            return false;
        }
        imageView.setImageDrawable(drawable);
        if (drawable instanceof ReusableBitmapDrawable) {
            resetReusableBitmap(imageView);
        }
        return true;
    }

    public static Bitmap getReusableBitmap(ImageView imageView) {
        if (imageView == null) {
            return null;
        }
        Bitmap reusableBitmap = null;
        Drawable resuseDrawable = imageView.getDrawable();
        if (resuseDrawable instanceof ReusableBitmapDrawable) {
            ReusableBitmapDrawable drawable = (ReusableBitmapDrawable) resuseDrawable;
            reusableBitmap = drawable.getReusableBitmap();
            if (reusableBitmap == null) {
                Logger.E("getReusableBitmap", "getReusableBitmap: " + drawable.getBitmap(), new Object[0]);
            }
        }
        if (reusableBitmap == null) {
            Object tag = imageView.getTag(33554432);
            if (tag instanceof ReusableBitmapDrawable) {
                reusableBitmap = ((ReusableBitmapDrawable) tag).getReusableBitmap();
            } else {
                reusableBitmap = null;
            }
        }
        return reusableBitmap;
    }

    public static void fillReusableBitmap(ImageView imageView, Bitmap reusableBitmap) {
        if (reusableBitmap != null && imageView != null && imageView.getTag(33554432) == null) {
            imageView.setTag(33554432, new ReusableBitmapDrawable(AppUtils.getResources(), reusableBitmap));
        }
    }

    public static void resetReusableBitmap(ImageView imageView) {
        if (imageView != null) {
            imageView.setTag(33554432, null);
        }
    }

    public static boolean checkImageViewReused(ViewWrapper viewWrapper) {
        return ViewAssistant.getInstance().checkViewReused(viewWrapper);
    }
}
