package com.alipay.streammedia.encode;

import android.view.Surface;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.MMNativeException.NativeExceptionCode;
import java.nio.ByteBuffer;
import tv.danmaku.ijk.media.player.IjkLibLoader;
import tv.danmaku.ijk.media.player.annotations.CalledByNative;
import tv.danmaku.ijk.media.player.pragma.DebugLog;

public class NativeRecordMuxer {
    public static final int ERR_ALLOC_MEM_FAIL = 1;
    public static final int ERR_BROKEN_PIPE = -32;
    public static final int ERR_HTTP_CONN_TIMEOUT = -110;
    public static final int ERR_QUEUE_FULL = 2;
    public static final int FRAME_TYPE_CONFIG = 2;
    public static final int FRAME_TYPE_DEFAULT = 0;
    public static final int FRAME_TYPE_KEY = 1;
    private static final String TAG = "FFmpegMuxer";
    private static volatile boolean mIsLibLoaded = false;
    private static NativeIOMX mNativeIOMX;
    private static final IjkLibLoader sLocalLibLoader = new IjkLibLoader() {
        public final void loadLibrary(String libName) {
            System.loadLibrary(libName);
        }
    };

    private native int _init(NativeSessionConfig nativeSessionConfig);

    private native RecordVideoResult _uninit();

    public static native void initDump();

    public static native void testDumpRGBA(ByteBuffer byteBuffer);

    public static native void uninitDump();

    public native int drainEncoder();

    public native Surface getInputSurface(NativeSessionConfig nativeSessionConfig);

    public native RecorderInternalCounter getPublishCounter();

    public native int putAudioData(byte[] bArr, int i, int i2, long j);

    public native int putVideoBeautyBuffer(ByteBuffer byteBuffer, long j, int i);

    public native int putVideoBuffer(ByteBuffer byteBuffer, long j, int i, int i2);

    public native int putVideoData(byte[] bArr, int i, long j, int i2, int i3);

    public native int putVideoDataBeauty(byte[] bArr, int i, long j, int i2);

    public native int putVideoDataHardware(byte[] bArr, int i, long j, int i2);

    public native int reconfig(NativeSessionConfig nativeSessionConfig);

    public native void releaseInputSurface(Surface surface);

    public native int setMetadataInt(String str, int i);

    public native int setParams(String str, String str2, String str3);

    public static void loadLibrariesOnce(IjkLibLoader libLoader) {
        synchronized (NativeRecordMuxer.class) {
            mNativeIOMX = new NativeIOMX();
            if (!mIsLibLoaded) {
                if (libLoader == null) {
                    libLoader = sLocalLibLoader;
                }
                try {
                    libLoader.loadLibrary("ijkffmpeg");
                    libLoader.loadLibrary("ijksdl");
                    libLoader.loadLibrary("ijkmmengine");
                    libLoader.loadLibrary("ijkrecorder");
                    libLoader.loadLibrary("ijkplayer");
                    mIsLibLoaded = true;
                } catch (Error e) {
                    throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
    }

    public NativeRecordMuxer() {
        loadLibrariesOnce(sLocalLibLoader);
    }

    public NativeRecordMuxer(IjkLibLoader libLoader) {
        loadLibrariesOnce(libLoader);
    }

    public int init(NativeSessionConfig cfg) {
        try {
            int result = _init(cfg);
            DebugLog.d(TAG, "set Muxing to softencoder result=" + result);
            if (result != 0) {
            }
            return result;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    @CalledByNative
    private static int GetIOMXHandle(int iomxHandle) {
        try {
            return mNativeIOMX.NativeGetHandle(iomxHandle);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public RecordVideoResult uninit() {
        try {
            return _uninit();
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public int setParams(String type, String value) {
        try {
            return setParams(type, value, "0");
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }
}
