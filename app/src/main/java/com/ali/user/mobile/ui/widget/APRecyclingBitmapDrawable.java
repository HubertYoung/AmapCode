package com.ali.user.mobile.ui.widget;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class APRecyclingBitmapDrawable extends BitmapDrawable {
    private int a;
    private int b;
    private boolean c;

    public final void a(boolean z) {
        synchronized (this) {
            if (z) {
                try {
                    this.b++;
                    this.c = true;
                } catch (Throwable th) {
                    while (true) {
                        throw th;
                    }
                }
            } else {
                this.b--;
            }
        }
        a();
    }

    private synchronized void a() {
        if (this.a <= 0 && this.b <= 0 && this.c && b()) {
            new StringBuilder("No longer being used or cached so recycling. ").append(toString());
            getBitmap().recycle();
        }
    }

    private synchronized boolean b() {
        Bitmap bitmap;
        bitmap = getBitmap();
        return bitmap != null && !bitmap.isRecycled();
    }
}
