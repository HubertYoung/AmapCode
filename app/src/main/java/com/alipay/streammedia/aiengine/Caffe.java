package com.alipay.streammedia.aiengine;

import android.graphics.Bitmap;
import com.alipay.streammedia.aiengine.AINativeException.NativeExceptionCode;
import com.alipay.streammedia.utils.SoLoadLock;
import tv.danmaku.ijk.media.player.IjkLibLoader;

public class Caffe {
    public static final String TAG = "aiengine-caffe";
    private static volatile boolean mIsLibLoaded = false;
    private static final IjkLibLoader sLocalLibLoader = new IjkLibLoader() {
        public final void loadLibrary(String libName) {
            System.loadLibrary(libName);
        }
    };
    private long instanceId = -1;

    /* access modifiers changed from: 0000 */
    public native ClassifyResult classify(long j, Bitmap bitmap);

    /* access modifiers changed from: 0000 */
    public native long init(String str, String str2, String str3);

    /* access modifiers changed from: 0000 */
    public native void uninit(long j);

    public static void loadLibrariesOnce(IjkLibLoader libLoader) {
        synchronized (SoLoadLock.class) {
            if (!mIsLibLoaded) {
                if (libLoader == null) {
                    libLoader = sLocalLibLoader;
                }
                try {
                    libLoader.loadLibrary("ijkaiengine");
                    mIsLibLoaded = true;
                } catch (Error e) {
                    throw new AINativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
    }

    public void initEngine(String model_file, String trained_file, String label_file) {
        try {
            this.instanceId = init(model_file, trained_file, label_file);
            if (this.instanceId == -8) {
                throw new AINativeException(-8);
            }
        } catch (Error e) {
            throw new AINativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public ClassifyResult imgClassify(Bitmap img) {
        try {
            ClassifyResult ret = classify(this.instanceId, img);
            if (ret.retCode == 0) {
                return ret;
            }
            throw new AINativeException(ret.retCode);
        } catch (Error e) {
            throw new AINativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public void Destory() {
        try {
            uninit(this.instanceId);
            this.instanceId = -1;
        } catch (Error e) {
            throw new AINativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }
}
