package com.alipay.streammedia.mmengine.picture.gif;

import android.graphics.Bitmap;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.MMNativeException.NativeExceptionCode;
import com.alipay.streammedia.utils.SoLoadLock;
import tv.danmaku.ijk.media.player.IjkLibLoader;

public class GifDecoder {
    private static volatile boolean mIsLibLoaded = false;
    private static final IjkLibLoader sLocalLibLoader = new IjkLibLoader() {
        public final void loadLibrary(String libName) {
            System.loadLibrary(libName);
        }
    };
    private boolean inited = false;
    private long nativeInstance;
    private final Object stateLock = new Object();

    static native int gifDecoderGetHeight(GifDecoder gifDecoder);

    static native int gifDecoderGetWidth(GifDecoder gifDecoder);

    static native int gifDecoderInitByFileBuffer(GifDecoder gifDecoder, String str, int i, int i2);

    static native int gifDecoderRelease(GifDecoder gifDecoder);

    static native GifParseResult gifDecoderRenderNextFrame(GifDecoder gifDecoder, Bitmap bitmap, int i, int i2);

    static native GifParseResult gifDecoderRenderNextFrameByIndex(GifDecoder gifDecoder, Bitmap bitmap, int i, int i2, int i3);

    public static void loadLibrariesOnce(IjkLibLoader libLoader) {
        synchronized (SoLoadLock.class) {
            if (!mIsLibLoaded) {
                if (libLoader == null) {
                    libLoader = sLocalLibLoader;
                }
                try {
                    libLoader.loadLibrary("ijkengine-gif");
                    mIsLibLoaded = true;
                } catch (Error e) {
                    throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
    }

    public static GifDecoder generateGifDecoder(String filepath, int bufSize, int loopCount) {
        GifDecoder decoder = new GifDecoder();
        decoder.init(filepath, bufSize, loopCount);
        return decoder;
    }

    public void init(String filepath, int bufSize, int loopCount) {
        synchronized (this.stateLock) {
            if (this.inited) {
                throw new MMNativeException(NativeExceptionCode.STATE_ERROR);
            }
            try {
                int ret = gifDecoderInitByFileBuffer(this, filepath, bufSize, loopCount);
                if (ret != 0) {
                    throw new MMNativeException(ret);
                }
                this.inited = true;
            } catch (Error e) {
                throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
            }
        }
    }

    public GifParseResult renderNextFrame(Bitmap bitmap) {
        GifParseResult res;
        synchronized (this.stateLock) {
            if (!this.inited) {
                res = new GifParseResult();
                res.setCode(NativeExceptionCode.STATE_ERROR.getIndex());
            } else {
                try {
                    res = gifDecoderRenderNextFrame(this, bitmap, bitmap.getWidth(), bitmap.getHeight());
                } catch (Error e) {
                    throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
        return res;
    }

    public GifParseResult renderNextFrameByIndex(Bitmap bitmap, int index) {
        GifParseResult res;
        synchronized (this.stateLock) {
            if (!this.inited) {
                res = new GifParseResult();
                res.setCode(NativeExceptionCode.STATE_ERROR.getIndex());
            } else {
                try {
                    res = gifDecoderRenderNextFrameByIndex(this, bitmap, bitmap.getWidth(), bitmap.getHeight(), index);
                } catch (Error e) {
                    throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
        return res;
    }

    public void release() {
        synchronized (this.stateLock) {
            if (this.inited) {
                try {
                    gifDecoderRelease(this);
                    this.nativeInstance = 0;
                    this.inited = false;
                } catch (Error e) {
                    throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
    }

    public int getWidth() {
        try {
            return gifDecoderGetWidth(this);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public int getHeight() {
        try {
            return gifDecoderGetHeight(this);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public long getNativeInstance() {
        return this.nativeInstance;
    }
}
