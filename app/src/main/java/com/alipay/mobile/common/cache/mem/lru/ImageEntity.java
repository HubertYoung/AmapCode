package com.alipay.mobile.common.cache.mem.lru;

import android.graphics.Bitmap;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.cache.mem.Entity;

public class ImageEntity extends Entity<Bitmap> {
    private int a;

    public ImageEntity(String owner, String group, Bitmap value) {
        super(owner, group, value);
        this.a = value.getRowBytes() * value.getHeight();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public int getSize() {
        return this.a;
    }

    public String toString() {
        return String.format("value: %s size: %d", new Object[]{((Bitmap) this.mValue).toString(), Integer.valueOf(this.a)});
    }
}
