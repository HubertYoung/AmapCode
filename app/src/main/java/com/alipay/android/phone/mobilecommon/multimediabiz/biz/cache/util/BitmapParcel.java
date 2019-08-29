package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util;

import android.graphics.Bitmap;
import android.os.Parcel;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BitmapParcel {
    private static final AtomicInteger a = new AtomicInteger((int) (((float) AppUtils.getHeapGrowthLimit()) * ConfigManager.getInstance().getCommonConfigItem().diskConf.useParcelMemFactor));
    private static final AtomicLong b = new AtomicLong();
    private Parcel c;
    private int d;
    private long e = b.incrementAndGet();

    private BitmapParcel(Parcel parcel, int size) {
        this.c = parcel;
        this.d = size;
        a.addAndGet(-size);
        Logger.D("BitmapParcel", "BitmapParcel parcel: " + parcel + ", size: " + size + ", available: " + a.get() + ", index: " + this.e, new Object[0]);
    }

    public static BitmapParcel create(Bitmap bitmap) {
        BitmapParcel bitmapParcel = null;
        if (!(ConfigManager.getInstance().getCommonConfigItem().diskConf.useParcelCache == 0 || bitmap == null)) {
            synchronized (BitmapParcel.class) {
                int memSize = bitmap.getRowBytes() * bitmap.getHeight();
                if (a.get() - memSize >= 0) {
                    Parcel parcel = Parcel.obtain();
                    try {
                        bitmap.writeToParcel(parcel, 0);
                        parcel.setDataPosition(0);
                        bitmapParcel = new BitmapParcel(parcel, memSize);
                    } catch (Exception e2) {
                        parcel.recycle();
                    }
                }
            }
        }
        return bitmapParcel;
    }

    public void recycle() {
        if (this.c != null) {
            synchronized (this) {
                if (this.c != null) {
                    a.addAndGet(this.d);
                    this.c.recycle();
                    this.c = null;
                    Logger.D("BitmapParcel", "recycle, index: " + this.e + ", curIndex: " + b.get() + ", available: " + a.get(), new Object[0]);
                }
            }
        }
    }

    public Bitmap getBitmap() {
        if (this.c != null) {
            try {
                return (Bitmap) Bitmap.CREATOR.createFromParcel(this.c);
            } catch (Throwable t) {
                Logger.W("BitmapParcel", "getBitmap error, " + t, new Object[0]);
            } finally {
                recycle();
            }
        }
        return null;
    }
}
