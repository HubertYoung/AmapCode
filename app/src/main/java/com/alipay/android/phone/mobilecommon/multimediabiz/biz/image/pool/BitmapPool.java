package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.pool;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BitmapPool {
    private static final Logger a = Logger.getLogger((String) "BitmapPool");
    private int b;
    private LinkedBlockingDeque<Bitmap> c = new LinkedBlockingDeque<>();
    private AtomicInteger d = new AtomicInteger(0);
    private Lock e = new ReentrantLock();

    public BitmapPool(int maxSize) {
        this.b = maxSize;
    }

    public Bitmap get(int width, int height) {
        boolean fromCache = true;
        Bitmap bitmap = a(width * height * 4);
        if (bitmap == null) {
            fromCache = false;
            bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        }
        a.d("get width: " + width + ", height: " + height + ", fromCache: " + fromCache, new Object[0]);
        return bitmap;
    }

    public void release(Bitmap bitmap) {
        if (bitmap != null) {
            this.e.lock();
            try {
                int size = a(bitmap);
                int avgSize = this.d.get() / this.c.size();
                while (this.d.get() + size > this.b && size < avgSize) {
                    this.d.addAndGet(-a(this.c.removeLast()));
                }
                if (this.d.get() + size < this.b) {
                    this.c.addFirst(bitmap);
                    this.d.addAndGet(size);
                }
            } finally {
                this.e.unlock();
            }
        }
    }

    private Bitmap a(int memSize) {
        this.e.lock();
        try {
            Iterator<Bitmap> it = this.c.iterator();
            while (it.hasNext()) {
                Bitmap bitmap = it.next();
                int size = a(bitmap);
                if (size == memSize) {
                    this.d.addAndGet(-size);
                    return bitmap;
                }
            }
            this.e.unlock();
            return null;
        } finally {
            this.e.unlock();
        }
    }

    private static int a(Bitmap bitmap) {
        return bitmap.getHeight() * bitmap.getRowBytes();
    }
}
