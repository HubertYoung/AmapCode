package com.ant.phone.xmedia.algorithm;

import android.graphics.ImageFormat;
import android.os.HandlerThread;
import android.os.Message;
import com.alipay.alipaylogger.Log;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.streammedia.cvengine.CVNativeEngineApi;
import com.alipay.streammedia.cvengine.CVNativeException;
import com.ant.phone.xmedia.XMediaEngine.XMediaCallback;
import com.ant.phone.xmedia.data.DebugSwitch;
import com.ant.phone.xmedia.params.TrackerItem;
import com.ant.phone.xmedia.receiver.ImageReceiver;
import com.ant.phone.xmedia.receiver.ImageReceiver.ImageCallback;
import java.util.List;

public class TrackAlgorithm {
    /* access modifiers changed from: private */
    public CVNativeEngineApi a;
    /* access modifiers changed from: private */
    public boolean b = false;
    /* access modifiers changed from: private */
    public boolean c = false;
    /* access modifiers changed from: private */
    public byte[] d;
    /* access modifiers changed from: private */
    public byte[] e;
    /* access modifiers changed from: private */
    public String f;
    private HandlerThread g = null;
    private g h = null;
    /* access modifiers changed from: private */
    public int i = 0;
    /* access modifiers changed from: private */
    public long j = 0;
    private ImageReceiver k;
    /* access modifiers changed from: private */
    public XMediaCallback l;
    /* access modifiers changed from: private */
    public int m = 0;
    /* access modifiers changed from: private */
    public Object n = new Object();
    private ImageCallback o = new d(this);

    public TrackAlgorithm() {
        Log.i("TrackAlgorithm", "TrackAlgorithm construct, id:" + this);
        this.a = new CVNativeEngineApi();
        try {
            CVNativeEngineApi.loadLibrariesOnce(new e(this));
        } catch (CVNativeException e2) {
            Log.e("TrackAlgorithm", "loadLibrariesOnce exp:" + e2);
        }
    }

    public final void a(List<TrackerItem> list, int w, int h2, byte[] nv21data, XMediaCallback callback) {
        this.l = callback;
        if (this.d == null) {
            this.d = new byte[(w * h2)];
            Log.i("TrackAlgorithm", "mYuvBuffer construct, size: " + this.d.length);
        }
        long t = System.currentTimeMillis();
        System.arraycopy(nv21data, 0, this.d, 0, this.d.length);
        Log.i("TrackAlgorithm", "arraycopy took " + (System.currentTimeMillis() - t) + RPCDataParser.TIME_MS);
        if (DebugSwitch.b) {
            if (this.e == null) {
                this.e = new byte[(((w * h2) * ImageFormat.getBitsPerPixel(17)) / 8)];
                Log.i("TrackAlgorithm", "mYuvVideoBuffer construct, size: " + this.e.length);
            }
            System.arraycopy(nv21data, 0, this.e, 0, this.e.length);
            this.f = "/sdcard/" + System.currentTimeMillis() + ".yuv";
        }
        if (this.k == null) {
            this.k = new ImageReceiver();
            this.k.init();
            this.k.setCallback(this.o);
        }
        Message msg = Message.obtain();
        msg.what = 2;
        a(msg);
        Message msg2 = Message.obtain();
        msg2.what = 0;
        msg2.arg1 = w;
        msg2.arg2 = h2;
        msg2.obj = list;
        a(msg2);
    }

    public final void a() {
        if (this.k != null) {
            this.k.uninit();
        }
        a(0);
        a(1);
        Message msg = Message.obtain();
        msg.what = 2;
        a(msg);
        Message msg1 = Message.obtain();
        msg1.what = 3;
        a(msg1);
        synchronized (this.n) {
            try {
                this.n.wait(2500);
            } catch (InterruptedException e2) {
                Log.e("TrackAlgorithm", "wait exp:", e2);
            }
        }
        Log.i("TrackAlgorithm", "stop end");
    }

    private void a(int what) {
        if (this.h != null && this.g != null && this.g.isAlive() && this.h.getLooper() != null) {
            Log.i("TrackAlgorithm", "removeMessages what: " + what);
            this.h.removeMessages(what);
        }
    }

    /* access modifiers changed from: private */
    public boolean a(Message msg) {
        if (this.h != null && this.g != null && this.g.isAlive() && this.h.getLooper() != null) {
            return this.h.sendMessage(msg);
        }
        c();
        return this.h.sendMessage(msg);
    }

    /* access modifiers changed from: private */
    public void b() {
        Thread.currentThread().setUncaughtExceptionHandler(new f(this));
    }

    private synchronized void c() {
        if (this.h == null) {
            this.g = new HandlerThread("XMedia_TrackThread_" + System.currentTimeMillis());
            this.g.start();
            this.h = new g(this, this, this.g.getLooper());
            Log.d("TrackAlgorithm", "track work thread prepared.");
        }
    }
}
