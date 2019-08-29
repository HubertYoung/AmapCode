package com.alipay.streammedia.video.editor;

import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.MMNativeException.NativeExceptionCode;

public class VideoCutter {
    private final Object stateLock = new Object();

    private native CutResult nativeCut(CutParam cutParam);

    public CutResult cut(CutParam param) {
        CutResult nativeCut;
        synchronized (this.stateLock) {
            try {
                nativeCut = nativeCut(param);
            } catch (Error e) {
                throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
            }
        }
        return nativeCut;
    }
}
