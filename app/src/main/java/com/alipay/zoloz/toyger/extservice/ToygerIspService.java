package com.alipay.zoloz.toyger.extservice;

import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import com.alipay.mobile.security.bio.service.BioService;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.streammedia.devicesengine.DevicesNativeEngineApi;
import com.alipay.streammedia.devicesengine.camera.ImageParams;
import com.alipay.zoloz.a.b;
import com.alipay.zoloz.hardware.a.a;
import com.alipay.zoloz.toyger.algorithm.TGDepthFrame;
import com.alipay.zoloz.toyger.algorithm.TGFaceAttr;
import com.alipay.zoloz.toyger.algorithm.TGFaceState;
import com.alipay.zoloz.toyger.algorithm.TGFrame;
import com.alipay.zoloz.toyger.face.FaceBlobManager;
import java.util.concurrent.TimeUnit;

public class ToygerIspService extends BioService {
    private static final String TAG = "ToygerIsp";
    private final long ISP_DELAY = TimeUnit.SECONDS.toMillis(3);
    private long begin = 0;
    private boolean ispRunning = false;
    private boolean mInitialized = false;
    private a mIspService;
    private HandlerThread mIspThread;
    private Handler mIspThreadHandler;
    private b mToygerIsp;

    public void onCreate(BioServiceManager bioServiceManager) {
        super.onCreate(bioServiceManager);
    }

    public void init(int i, int i2, int i3, int i4, int i5) {
        this.mIspService = (a) this.mBioServiceManager.getBioService(a.class);
        if (this.mIspService != null) {
            float[] b = this.mIspService.b();
            float[][][] c = this.mIspService.c();
            this.mIspThread = new HandlerThread("adjustIsp");
            this.mIspThread.start();
            this.mIspThreadHandler = new Handler(this.mIspThread.getLooper());
            this.mToygerIsp = new b();
            b bVar = this.mToygerIsp;
            long currentTimeMillis = System.currentTimeMillis();
            try {
                bVar.a = new DevicesNativeEngineApi();
                ImageParams imageParams = new ImageParams();
                imageParams.orgWidth = i;
                imageParams.orgHeight = i2;
                imageParams.dispWidth = i3;
                imageParams.dispHeight = i4;
                bVar.a.ispCreate(imageParams, b, c, i5);
            } catch (Throwable th) {
                bVar.a = null;
            }
            new StringBuilder("ToygerIsp.init(): mEngineApi=").append(bVar.a).append(", cost ").append(System.currentTimeMillis() - currentTimeMillis).append(" ms.");
        }
        this.mInitialized = true;
        this.begin = System.currentTimeMillis();
    }

    private boolean validateRegion(RectF rectF) {
        if (rectF == null || (rectF.left >= 0.0f && rectF.top >= 0.0f && rectF.right <= 1.0f && rectF.bottom <= 1.0f)) {
            return true;
        }
        return false;
    }

    public void adjustIsp(TGFrame tGFrame, TGDepthFrame tGDepthFrame, TGFaceState tGFaceState, TGFaceAttr tGFaceAttr) {
        RectF rectF;
        if (this.mIspService != null && System.currentTimeMillis() - this.begin >= this.ISP_DELAY) {
            if (tGFaceState.hasFace) {
                rectF = tGFaceAttr.faceRegion;
            } else {
                float f = 60.0f / ((float) tGFrame.width);
                float f2 = 60.0f / ((float) tGFrame.height);
                rectF = new RectF(0.5f - f, 0.5f - f2, f + 0.5f, f2 + 0.5f);
            }
            if (validateRegion(rectF)) {
                Rect convertFaceRegion = FaceBlobManager.convertFaceRegion(rectF, tGFrame.width, tGFrame.height, tGFrame.rotation, false);
                synchronized (this) {
                    if (this.ispRunning) {
                        BioLog.e((String) TAG, (String) "adjustIsp begin: but ispRunning==true, give up.");
                    } else {
                        this.ispRunning = true;
                        this.mIspThreadHandler.post(new a(this, convertFaceRegion, tGFrame, tGDepthFrame));
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void adjustIsp(Rect rect, byte[] bArr, short[] sArr) {
        BioLog.w((String) TAG, "adjustIsp begin: getAEMode()=" + this.mIspService.a());
        try {
            com.alipay.zoloz.a.a a = this.mToygerIsp.a(bArr, sArr, rect, this.mIspService.d(), this.mIspService.e());
            if (a != null) {
                BioLog.d(TAG, "result.needSet=" + a.a + ", result.exposureTime=" + a.b + ", result.ISO=" + a.c);
            }
        } catch (Throwable th) {
            BioLog.w((String) TAG, th);
        } finally {
            r2 = "adjustIsp end.";
            BioLog.w(TAG, r2);
        }
        synchronized (this) {
            this.ispRunning = false;
        }
    }

    public void onDestroy() {
        if (this.mToygerIsp != null) {
            b bVar = this.mToygerIsp;
            if (bVar.a != null) {
                try {
                    bVar.a.ispDestory();
                } catch (Throwable th) {
                }
            }
        }
        if (this.mIspThread != null) {
            if (VERSION.SDK_INT >= 18) {
                this.mIspThread.quitSafely();
            } else {
                this.mIspThread.quit();
            }
            this.mIspThread = null;
            this.mIspThreadHandler = null;
        }
        this.ispRunning = false;
        this.mIspService = null;
        super.onDestroy();
    }

    public boolean isInitialized() {
        return this.mInitialized;
    }
}
