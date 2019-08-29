package com.ant.phone.xmedia.algorithm;

import com.alipay.alipaylogger.Log;
import com.alipay.android.phone.falcon.xmedia.XNNJNI;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: FrameXNNAlgorithm */
final class b implements UncaughtExceptionHandler {
    final /* synthetic */ FrameXNNAlgorithm a;

    b(FrameXNNAlgorithm this$0) {
        this.a = this$0;
    }

    public final void uncaughtException(Thread thread, Throwable ex) {
        Log.e("FrameXNNAlgorithm", "uncaughtException###, thread name:" + thread.getName() + ", thread id:" + thread.getId() + ",ex:" + ex.getMessage());
        StackTraceElement[] elements = ex.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTraceElement : elements) {
            sb.append(stackTraceElement.toString());
            sb.append("\n");
        }
        Log.e("FrameXNNAlgorithm", "exception stack:\n" + sb.toString());
        if (this.a.b != 0) {
            XNNJNI.release(this.a.b);
        }
    }
}
