package com.ant.phone.xmedia.algorithm;

import android.os.Message;
import com.alipay.alipaylogger.Log;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.ant.phone.xmedia.api.utils.FrameCapture;
import com.ant.phone.xmedia.data.DebugSwitch;
import com.ant.phone.xmedia.receiver.ImageReceiver.ImageCallback;

/* compiled from: FrameXNNAlgorithm */
final class a implements ImageCallback {
    final /* synthetic */ FrameXNNAlgorithm a;

    a(FrameXNNAlgorithm this$0) {
        this.a = this$0;
    }

    public final void onYuvFrameAvailable(byte[] yuv, int w, int h, int rotation) {
        Log.i("FrameXNNAlgorithm", "############onYuvFrameAvailable begin, w:" + w + ", h:" + h + ", rotation:" + rotation);
        if (DebugSwitch.a && FrameXNNAlgorithm.a(this.a) % 60 == 0) {
            if (this.a.m == null) {
                this.a.m = new FrameCapture();
            }
            this.a.m.a(yuv, w, h, 0);
            this.a.m.a(yuv, w, h, rotation);
        }
        this.a.k = rotation;
        if (!this.a.j) {
            Log.i("FrameXNNAlgorithm", "not running yet, return\n");
        } else if (this.a.b == 0) {
            Log.i("FrameXNNAlgorithm", "onYuvFrameAvailable but xnn not initialized, just return.\n");
        } else if (this.a.c) {
            Log.i("FrameXNNAlgorithm", "onYuvFrameAvailable but xnn is busy, just return.\n");
        } else {
            this.a.c = true;
            if (this.a.f == null) {
                this.a.f = new byte[yuv.length];
                Log.i("FrameXNNAlgorithm", "mYuvBuffer construct, size: " + yuv.length);
            }
            long t = System.currentTimeMillis();
            System.arraycopy(yuv, 0, this.a.f, 0, this.a.f.length);
            Log.i("FrameXNNAlgorithm", "arraycopy took " + (System.currentTimeMillis() - t) + RPCDataParser.TIME_MS);
            int msgcode = 3;
            switch (this.a.g) {
                case 1:
                    msgcode = 1;
                    break;
                case 2:
                    msgcode = 3;
                    break;
                case 9:
                    msgcode = 1;
                    break;
            }
            Message msg = Message.obtain();
            msg.what = msgcode;
            msg.arg1 = w;
            msg.arg2 = h;
            this.a.a(msg);
            Log.i("FrameXNNAlgorithm", "onYuvFrameAvailable end\n\n");
        }
    }

    public final void onRgbFrameAvailable(byte[] rgb, int w, int h) {
    }
}
