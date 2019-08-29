package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory;

import android.graphics.Bitmap;
import java.lang.ref.SoftReference;

public class BitmapReference extends SoftReference<Bitmap> {
    private long a = System.currentTimeMillis();

    public BitmapReference(Bitmap r) {
        super(r);
    }

    public Bitmap get() {
        Bitmap bitmap = (Bitmap) super.get();
        if (bitmap != null) {
            this.a = System.currentTimeMillis();
        }
        return bitmap;
    }

    public long getLastAccessTime() {
        return this.a;
    }

    public int hashCode() {
        return get() != null ? get().hashCode() : super.hashCode();
    }

    public boolean equals(Object o) {
        if (o instanceof BitmapReference) {
            return get() == ((BitmapReference) o).get();
        }
        return super.equals(o);
    }

    public String toString() {
        return "[BitmapReference] " + super.toString() + " :: " + get();
    }
}
