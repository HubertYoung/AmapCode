package com.ant.phone.xmedia.receiver;

import com.alipay.alipaylogger.Log;
import com.alipay.mobile.beehive.eventbus.EventBusManager;
import com.alipay.mobile.beehive.eventbus.Subscribe;

public class ImageReceiver {
    public static final String TAG = "ImageReceiver";
    private ImageCallback mCallback;

    public interface ImageCallback {
        void onRgbFrameAvailable(byte[] bArr, int i, int i2);

        void onYuvFrameAvailable(byte[] bArr, int i, int i2, int i3);
    }

    public void setCallback(ImageCallback cb) {
        this.mCallback = cb;
    }

    @Subscribe(name = "xmedia_yuvframe")
    public void onYuvFrame(Object[] data) {
        Log.i(TAG, "onYuvFrame xmedia_yuvframe");
        if (this.mCallback != null) {
            this.mCallback.onYuvFrameAvailable(data[2], data[0].intValue(), data[1].intValue(), data[3].intValue());
        }
    }

    @Subscribe(name = "xmedia_rgbframe")
    public void onRgbFrame(Object[] data) {
        Log.i(TAG, "onRgbFrame xmedia_rgbframe");
        if (this.mCallback != null) {
            this.mCallback.onRgbFrameAvailable(data[2], data[0].intValue(), data[1].intValue());
        }
    }

    public void init() {
        Log.i(TAG, "init");
        if (EventBusManager.getInstance().register(this)) {
            Log.i(TAG, "register success");
        } else {
            Log.i(TAG, "register failed");
        }
    }

    public void uninit() {
        Log.i(TAG, "uninit");
        EventBusManager.getInstance().unregister(this);
    }
}
