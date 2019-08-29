package com.huawei.android.pushselfshow.utils;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.huawei.android.pushagent.a.a.c;
import java.lang.reflect.InvocationTargetException;

public class RelativeLayoutForBckgColor extends RelativeLayout {
    private WallpaperManager a;
    private Drawable b;

    public RelativeLayoutForBckgColor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public RelativeLayoutForBckgColor(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private static Object a(WallpaperManager wallpaperManager, Rect rect) {
        String str;
        String str2;
        StringBuilder sb;
        try {
            Class<?> cls = Class.forName("com.huawei.android.app.WallpaperManagerEx");
            return cls.getDeclaredMethod("getBlurBitmap", new Class[]{WallpaperManager.class, Rect.class}).invoke(cls, new Object[]{wallpaperManager, rect});
        } catch (ClassNotFoundException e) {
            str = "PushSelfShowLog";
            sb = new StringBuilder(" WallpaperManagerEx getBlurBitmap wrong ");
            str2 = e.toString();
            sb.append(str2);
            c.d(str, sb.toString());
            return null;
        } catch (NoSuchMethodException e2) {
            str = "PushSelfShowLog";
            sb = new StringBuilder(" WallpaperManagerEx getBlurBitmap wrong ");
            str2 = e2.toString();
            sb.append(str2);
            c.d(str, sb.toString());
            return null;
        } catch (IllegalAccessException e3) {
            str = "PushSelfShowLog";
            sb = new StringBuilder(" WallpaperManagerEx getBlurBitmap wrong ");
            str2 = e3.toString();
            sb.append(str2);
            c.d(str, sb.toString());
            return null;
        } catch (IllegalArgumentException e4) {
            str = "PushSelfShowLog";
            sb = new StringBuilder(" WallpaperManagerEx getBlurBitmap wrong ");
            str2 = e4.toString();
            sb.append(str2);
            c.d(str, sb.toString());
            return null;
        } catch (InvocationTargetException e5) {
            str = "PushSelfShowLog";
            sb = new StringBuilder(" WallpaperManagerEx getBlurBitmap wrong ");
            str2 = e5.toString();
            sb.append(str2);
            c.d(str, sb.toString());
            return null;
        }
    }

    private void b() {
        int color = getContext().getResources().getColor(d.f(getContext(), "hwpush_bgcolor"));
        try {
            int[] iArr = new int[2];
            getLocationOnScreen(iArr);
            Rect rect = new Rect(iArr[0], iArr[1], iArr[0] + getWidth(), iArr[1] + getHeight());
            if (rect.width() > 0 && rect.height() > 0) {
                if (a.d()) {
                    int i = a.i(getContext());
                    if (i == 0) {
                        setBackgroundColor(color);
                    } else {
                        setBackgroundColor(i);
                    }
                } else if (a(this.a, rect) != null) {
                    this.b = new BitmapDrawable((Bitmap) a(this.a, rect));
                    setBackgroundDrawable(this.b);
                } else {
                    c.d("PushSelfShowLog", "not emui 3.0,can not use wallpaper as background");
                    setBackgroundColor(color);
                }
            }
        } catch (NotFoundException | Exception unused) {
            c.d("PushSelfShowLog", "setBlurWallpaperBackground error, use default backgroud");
            setBackgroundColor(color);
        }
    }

    @SuppressLint({"ServiceCast"})
    public void a() {
        this.a = (WallpaperManager) getContext().getSystemService("wallpaper");
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        b();
    }
}
