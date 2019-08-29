package com.alipay.mobile.binarize;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.os.HandlerThread;
import com.alipay.mobile.bqcscanservice.BQCScanResult;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.mascanengine.BuryRecord;
import com.alipay.mobile.mascanengine.MultiMaScanResult;
import com.alipay.mobile.mascanengine.impl.MaScanEngineImpl;
import java.util.Map;

public class BinarizeScanEngineImpl extends MaScanEngineImpl {
    private HandlerThread a;
    private Handler b;
    /* access modifiers changed from: private */
    public BinarizeHandler c;
    /* access modifiers changed from: private */
    public Context d;
    /* access modifiers changed from: private */
    public volatile boolean e;
    /* access modifiers changed from: private */
    public MultiMaScanResult f;
    private byte[] g;
    private int h;
    private int i;
    private boolean j;
    /* access modifiers changed from: private */
    public volatile boolean k;
    /* access modifiers changed from: private */
    public int l = 0;
    /* access modifiers changed from: private */
    public boolean m;
    /* access modifiers changed from: private */
    public int n = 0;
    /* access modifiers changed from: private */
    public long o = 0;
    /* access modifiers changed from: private */
    public long p = 0;
    /* access modifiers changed from: private */
    public int q = 0;
    /* access modifiers changed from: private */
    public int r = 0;

    public boolean init(Context ctx, Map<String, Object> cameraParameters) {
        if (cameraParameters == null || !cameraParameters.containsKey(BinarizeUtils.KEY_ENABLE_RS_BINARIZE)) {
            this.j = false;
        } else {
            Logger.d("BinarizeScanEngineImpl", "rsBinarizeEnable:" + cameraParameters.get(BinarizeUtils.KEY_ENABLE_RS_BINARIZE));
            this.j = ((Boolean) cameraParameters.get(BinarizeUtils.KEY_ENABLE_RS_BINARIZE)).booleanValue() && BinarizeUtils.supportRsBinarize() && !BinarizeHandler.INIT_EXCEPTION_REACHES_LIMIT;
        }
        this.m = false;
        this.k = false;
        this.q = 0;
        this.r = 0;
        if (this.j) {
            Logger.p("BinarizeScanEngineImpl", "before init");
            this.d = ctx;
            this.n = 0;
            this.p = System.currentTimeMillis();
            this.a = new HandlerThread("Scan-Recognize", 10);
            this.a.start();
            this.b = new Handler(this.a.getLooper());
            this.b.post(new Runnable() {
                public void run() {
                    try {
                        BinarizeScanEngineImpl.this.c = new BinarizeHandler(BinarizeScanEngineImpl.this.d);
                        BinarizeScanEngineImpl.this.k = true;
                        BinarizeScanEngineImpl.this.o = System.currentTimeMillis() - BinarizeScanEngineImpl.this.p;
                    } catch (Exception e) {
                        Logger.d("BinarizeScanEngineImpl", "init binarizer exception " + e);
                        BuryRecord.recordRsBinarizeException("init");
                    }
                    if (BinarizeScanEngineImpl.this.m && BinarizeScanEngineImpl.this.c != null) {
                        try {
                            BinarizeScanEngineImpl.this.c.destroy();
                            BinarizeScanEngineImpl.this.k = false;
                        } catch (Exception e2) {
                            Logger.d("BinarizeScanEngineImpl", "release binarizer exception1 " + e2);
                            BuryRecord.recordRsBinarizeException("release");
                        }
                    }
                }
            });
            this.e = false;
            Logger.p("BinarizeScanEngineImpl", "after init");
        }
        return super.init(ctx, cameraParameters);
    }

    public void destroy() {
        super.destroy();
        if (this.j) {
            this.a.quit();
            Logger.p("BinarizeScanEngineImpl", "destroy, binarizeHandler == null:" + (this.c == null));
            if (this.c != null) {
                try {
                    this.c.destroy();
                } catch (Exception ex) {
                    Logger.d("BinarizeScanEngineImpl", "release binarizer exception2 " + ex);
                    BuryRecord.recordRsBinarizeException("release");
                }
            }
            this.e = false;
            this.k = false;
        }
        this.m = true;
    }

    private BQCScanResult a(byte[] bytes, Camera camera, Rect region, Size size, int previewFormat) {
        if (this.f != null) {
            return this.f;
        }
        Logger.p("BinarizeScanEngineImpl", "rs before binarize");
        if (region == null) {
            return null;
        }
        if (region.left < 0) {
            region.left = 0;
        }
        if (region.top < 0) {
            region.top = 0;
        }
        if (region.right > size.width) {
            region.right = size.width;
        }
        if (region.bottom > size.height) {
            region.bottom = size.height;
        }
        final Rect scanRect = region;
        a(bytes, size.width, size.height, scanRect);
        scanRect.right = this.h;
        scanRect.bottom = this.i;
        this.l = a(this.g, this.h, this.i);
        this.c.doBinarize(this.g, this.h, this.i);
        this.n++;
        Logger.p("BinarizeScanEngineImpl", "rs after binarize");
        if (this.f != null) {
            return this.f;
        }
        if (this.e) {
            return null;
        }
        final Camera camera2 = camera;
        final Size size2 = size;
        final int i2 = previewFormat;
        this.b.post(new Runnable() {
            public void run() {
                BinarizeScanEngineImpl.this.e = true;
                while (true) {
                    if (BinarizeScanEngineImpl.this.c.isBinarizePoolEmpty()) {
                        break;
                    }
                    Logger.p("BinarizeScanEngineImpl", "rs start recognize");
                    BinarizeResult binarizeResult = BinarizeScanEngineImpl.this.c.popFirstBinarizeResult();
                    if (binarizeResult == null) {
                        break;
                    }
                    BinarizeScanEngineImpl.this.r = BinarizeScanEngineImpl.this.r + 1;
                    MultiMaScanResult result = BinarizeScanEngineImpl.this.doProcessBinary(binarizeResult.bitMatrixData, camera2, scanRect, binarizeResult.methodId, size2, i2, BinarizeScanEngineImpl.this.l);
                    if (result != null && result.maScanResults != null) {
                        BinarizeScanEngineImpl.this.f = result;
                        BinarizeScanEngineImpl.this.f.rsBinarized = true;
                        BinarizeScanEngineImpl.this.f.rsBinarizedCount = BinarizeScanEngineImpl.this.n;
                        BinarizeScanEngineImpl.this.f.rsInitTime = BinarizeScanEngineImpl.this.o;
                        BinarizeScanEngineImpl.this.f.classicFrameCount = BinarizeScanEngineImpl.this.q;
                        BinarizeScanEngineImpl.this.e = false;
                        Logger.d("BinarizeScanEngineImpl", "recognize rs binarize code");
                        break;
                    }
                }
                BinarizeScanEngineImpl.this.e = false;
            }
        });
        return null;
    }

    public BQCScanResult process(byte[] bytes, Camera camera, Rect rect, Size size, int previewFormat) {
        if (!this.j || !this.k) {
            Logger.p("BinarizeScanEngineImpl", "process classic");
            this.q++;
            return super.process(bytes, camera, rect, size, previewFormat);
        }
        Logger.p("BinarizeScanEngineImpl", "process binary");
        try {
            return a(bytes, camera, rect, size, previewFormat);
        } catch (Exception e2) {
            Logger.d("BinarizeScanEngineImpl", "process binarize exception " + e2);
            this.j = false;
            this.a.quit();
            if (this.c != null) {
                this.c.destroy();
            }
            this.k = false;
            BuryRecord.recordRsBinarizeException("binarize");
            return null;
        }
    }

    public boolean onProcessFinish(BQCScanResult result) {
        if (result != null && (result instanceof MultiMaScanResult)) {
            ((MultiMaScanResult) result).frameCount = this.q + this.r;
        }
        return super.onProcessFinish(result);
    }

    private static int a(byte[] data, int width, int height) {
        if (data == null) {
            return 0;
        }
        int total = 0;
        int count = 0;
        for (int i2 = 0; i2 < height; i2 += 32) {
            for (int j2 = 0; j2 < width; j2 += 32) {
                total += data[(i2 * width) + j2] & 255;
                count++;
            }
        }
        if (count > 0) {
            return total / count;
        }
        return 0;
    }

    private byte[] a(byte[] data, int width, int height, Rect cropRect) {
        int cropStartX = cropRect.left;
        int cropStartY = cropRect.top;
        this.h = cropRect.right;
        this.i = cropRect.bottom;
        if (cropRect.right % 8 != 0) {
            this.h = (cropRect.right / 8) * 8;
        }
        if (cropRect.bottom % 8 != 0) {
            this.i = (cropRect.bottom / 8) * 8;
        }
        int tmpW = Math.min((width - cropStartX) - 1, this.h);
        if (tmpW <= 0) {
            return null;
        }
        int tmpH = Math.min((height - cropStartY) - 1, this.i);
        if (tmpH <= 0) {
            return null;
        }
        if (this.g == null) {
            this.g = new byte[(this.h * this.i)];
        } else if (this.g.length != this.h * this.i) {
            this.g = new byte[(this.h * this.i)];
        }
        for (int i2 = cropStartY; i2 < tmpH + cropStartY; i2++) {
            System.arraycopy(data, (i2 * width) + cropStartX, this.g, (i2 - cropStartY) * this.h, tmpW);
        }
        return this.g;
    }
}
