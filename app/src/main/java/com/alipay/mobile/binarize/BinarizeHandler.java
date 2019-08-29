package com.alipay.mobile.binarize;

import android.content.Context;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.mascanengine.BuryRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BinarizeHandler {
    public static boolean INIT_EXCEPTION_REACHES_LIMIT = false;
    public static final String TAG = "BinarizeHandler";
    private static final int[] a = {0, 1, 2, 3, 4};
    private static boolean j = false;
    private static Lock k = new ReentrantLock();
    private List<BinarizeResult> b;
    private AdaptiveHybridBinarizer c;
    private HybridStdBinarizer d;
    private LocalAdaptiveBinarizer e;
    private int f;
    private boolean g;
    private BinarizeResult h;
    private BinarizeResult i;

    public BinarizeHandler(Context context) {
        try {
            k.lock();
            a(context);
        } finally {
            k.unlock();
        }
    }

    public void destroy() {
        try {
            k.lock();
            a();
        } finally {
            k.unlock();
        }
    }

    public static void preHeatBinarizer(Context context) {
        if (!j) {
            try {
                k.lock();
                new BinarizeHandler(context, 0).a();
                j = true;
            } catch (Exception ex) {
                Logger.d(TAG, "preHeatBinarizer exception " + ex);
                BuryRecord.recordRsBinarizeException("preHeat");
            } finally {
                k.unlock();
            }
        }
    }

    private BinarizeHandler(Context context, byte b2) {
        a(context);
    }

    private void a(Context context) {
        Logger.d(TAG, "BinarizeHandler init");
        this.f = 0;
        this.g = false;
        this.b = new ArrayList();
        this.c = new AdaptiveHybridBinarizer(context);
        this.d = new HybridStdBinarizer(context);
        this.e = new LocalAdaptiveBinarizer(context);
    }

    private void a() {
        Logger.d(TAG, "BinarizeHandler release");
        if (this.c != null) {
            this.c.destroy();
        }
        if (this.d != null) {
            this.d.destroy();
        }
        if (this.e != null) {
            this.e.destroy();
        }
        this.c = null;
        this.d = null;
        this.e = null;
    }

    public boolean isBinarizePoolEmpty() {
        boolean isEmpty;
        synchronized (this.b) {
            isEmpty = this.b.isEmpty();
        }
        return isEmpty;
    }

    public BinarizeResult popFirstBinarizeResult() {
        BinarizeResult binarizeResult;
        synchronized (this.b) {
            if (!this.b.isEmpty()) {
                this.g = true;
                BinarizeResult popResult = this.b.remove(0);
                if (this.i == null) {
                    this.i = new BinarizeResult();
                }
                a(popResult, this.i);
                binarizeResult = this.i;
            } else {
                binarizeResult = null;
            }
        }
        return binarizeResult;
    }

    public void doBinarize(byte[] data, int width, int height) {
        if (this.g) {
            this.f = (this.f + 1) % a.length;
            this.g = false;
        }
        BinarizeResult binarizeResult = null;
        switch (a[this.f]) {
            case 0:
                this.c.initialize(width, height);
                this.c.setPreferWhite(true);
                this.c.setDeNoiseByAvg(false);
                binarizeResult = this.c.getBinarizedData(data);
                break;
            case 1:
                this.d.initialize(width, height);
                binarizeResult = this.d.getBinarizedData(data);
                break;
            case 2:
                this.e.initialize(width, height);
                binarizeResult = this.e.getBinarizedData(data);
                break;
            case 3:
                this.c.initialize(width, height);
                this.c.setPreferWhite(true);
                this.c.setDeNoiseByAvg(true);
                binarizeResult = this.c.getBinarizedData(data);
                break;
            case 4:
                this.c.initialize(width, height);
                this.c.setPreferWhite(false);
                this.c.setDeNoiseByAvg(false);
                binarizeResult = this.c.getBinarizedData(data);
                break;
        }
        if (binarizeResult != null) {
            binarizeResult.methodId = a[this.f];
            synchronized (this.b) {
                if (this.h == null) {
                    this.h = new BinarizeResult();
                }
                a(binarizeResult, this.h);
                this.b.clear();
                this.b.add(this.h);
            }
        }
    }

    private static void a(BinarizeResult src, BinarizeResult dst) {
        if (src != null && dst != null) {
            dst.width = src.width;
            dst.height = src.height;
            dst.methodId = src.methodId;
            if (dst.bitMatrixData == null || dst.bitMatrixData.length != src.bitMatrixData.length) {
                dst.bitMatrixData = new byte[src.bitMatrixData.length];
            }
            System.arraycopy(src.bitMatrixData, 0, dst.bitMatrixData, 0, src.bitMatrixData.length);
        }
    }
}
