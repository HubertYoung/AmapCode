package com.alipay.streammedia.video.editor;

import android.graphics.Bitmap;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.MMNativeException.NativeExceptionCode;

public class VideoPicker {
    private boolean inited = false;
    private long nativeInstance = 0;
    private final Object stateLock = new Object();

    public int init(PickerParam param) {
        int ret;
        synchronized (this.stateLock) {
            if (this.inited) {
                ret = -105;
            } else {
                try {
                    ret = NativeVideoEditor.pickerInit(this, param);
                    if (ret == 0) {
                        this.inited = true;
                    }
                } catch (Error e) {
                    throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
        return ret;
    }

    public VideoSeekResult seek(long pts) {
        VideoSeekResult res;
        synchronized (this.stateLock) {
            if (!this.inited) {
                res = new VideoSeekResult();
                res.code = -104;
            } else {
                try {
                    res = NativeVideoEditor.pickerSeek(this, pts);
                } catch (Error e) {
                    throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
        return res;
    }

    public VideoGetFrameResult getFrame(long pts, Bitmap bmp) {
        VideoGetFrameResult res;
        synchronized (this.stateLock) {
            if (!this.inited) {
                res = new VideoGetFrameResult();
                res.code = -104;
            } else {
                try {
                    res = NativeVideoEditor.pickerGetFrame(this, pts, bmp);
                } catch (Error e) {
                    throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
        return res;
    }

    public int release() {
        int i = 0;
        synchronized (this.stateLock) {
            if (this.inited) {
                try {
                    i = NativeVideoEditor.pickerRelease(this);
                    this.inited = false;
                } catch (Error e) {
                    throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
        return i;
    }
}
