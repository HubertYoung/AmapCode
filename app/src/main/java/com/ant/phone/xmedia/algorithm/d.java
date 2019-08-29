package com.ant.phone.xmedia.algorithm;

import android.graphics.ImageFormat;
import android.os.Message;
import com.alipay.alipaylogger.Log;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.ant.phone.xmedia.data.DebugSwitch;
import com.ant.phone.xmedia.receiver.ImageReceiver.ImageCallback;

/* compiled from: TrackAlgorithm */
final class d implements ImageCallback {
    final /* synthetic */ TrackAlgorithm a;

    d(TrackAlgorithm this$0) {
        this.a = this$0;
    }

    public final void onYuvFrameAvailable(byte[] yuv, int w, int h, int rotation) {
        Log.i("TrackAlgorithm", "############onYuvFrameAvailable begin, w:" + w + ", h:" + h + ", rotation:" + rotation);
        this.a.m = rotation;
        if (!this.a.c) {
            Log.i("TrackAlgorithm", "onYuvFrameAvailable but tracker not initialized, just return.\n");
        } else if (this.a.b) {
            Log.i("TrackAlgorithm", "onYuvFrameAvailable but tracker is busy, just return.\n");
        } else {
            this.a.b = true;
            if (this.a.d == null) {
                this.a.d = new byte[(w * h)];
                Log.i("TrackAlgorithm", "mYuvBuffer construct, size: " + this.a.d.length);
            }
            long t = System.currentTimeMillis();
            System.arraycopy(yuv, 0, this.a.d, 0, this.a.d.length);
            Log.i("TrackAlgorithm", "arraycopy took " + (System.currentTimeMillis() - t) + RPCDataParser.TIME_MS);
            if (DebugSwitch.b) {
                if (this.a.e == null) {
                    this.a.e = new byte[(((w * h) * ImageFormat.getBitsPerPixel(17)) / 8)];
                    Log.i("TrackAlgorithm", "mYuvVideoBuffer construct, size: " + this.a.e.length);
                }
                System.arraycopy(yuv, 0, this.a.e, 0, this.a.e.length);
            }
            Message msg = Message.obtain();
            msg.what = 1;
            msg.arg1 = w;
            msg.arg2 = h;
            this.a.a(msg);
            Log.i("TrackAlgorithm", "onYuvFrameAvailable end\n\n");
        }
    }

    public final void onRgbFrameAvailable(byte[] rgb, int w, int h) {
    }
}
