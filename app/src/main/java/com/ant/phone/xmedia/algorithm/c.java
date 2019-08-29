package com.ant.phone.xmedia.algorithm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.alipay.alipaylogger.Log;
import java.lang.ref.WeakReference;

/* compiled from: FrameXNNAlgorithm */
final class c extends Handler {
    final /* synthetic */ FrameXNNAlgorithm a;
    private WeakReference<FrameXNNAlgorithm> b;
    private Looper c;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public c(FrameXNNAlgorithm frameXNNAlgorithm, FrameXNNAlgorithm render, Looper looper) {
        // this.a = frameXNNAlgorithm;
        super(looper);
        this.b = new WeakReference<>(render);
        this.c = looper;
    }

    public final void handleMessage(Message inputMessage) {
        int what = inputMessage.what;
        Object obj = inputMessage.obj;
        if (((FrameXNNAlgorithm) this.b.get()) == null) {
            Log.i("FrameXNNAlgorithm", "TrackHandler.handleMessage: render is null");
            return;
        }
        Log.i("FrameXNNAlgorithm", "handleMessage handle msg:" + what);
        switch (what) {
            case 0:
                try {
                    this.a.d();
                    this.a.a((String) obj);
                    return;
                } catch (Exception e) {
                    Log.e("FrameXNNAlgorithm", "handleMessage error, msg mErrInfo:" + what, e);
                }
            case 1:
                this.a.a(inputMessage.arg1, inputMessage.arg2);
                return;
            case 3:
                this.a.b(inputMessage.arg1, inputMessage.arg2);
                return;
            case 5:
                this.a.c();
                return;
            case 6:
                try {
                    this.c.quit();
                    Log.i("FrameXNNAlgorithm", "thread quit");
                    synchronized (this.a.o) {
                        this.a.o.notifyAll();
                        Log.i("FrameXNNAlgorithm", "notifyAll");
                    }
                    return;
                } catch (Exception e2) {
                    Log.e("FrameXNNAlgorithm", "quit exp:", e2);
                    Log.i("FrameXNNAlgorithm", "thread quit");
                    synchronized (this.a.o) {
                        this.a.o.notifyAll();
                        Log.i("FrameXNNAlgorithm", "notifyAll");
                        return;
                    }
                } catch (Throwable th) {
                    Log.i("FrameXNNAlgorithm", "thread quit");
                    synchronized (this.a.o) {
                        this.a.o.notifyAll();
                        Log.i("FrameXNNAlgorithm", "notifyAll");
                        throw th;
                    }
                }
            default:
                return;
        }
        Log.e("FrameXNNAlgorithm", "handleMessage error, msg mErrInfo:" + what, e);
    }
}
