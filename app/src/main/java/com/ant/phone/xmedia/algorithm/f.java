package com.ant.phone.xmedia.algorithm;

import com.alipay.alipaylogger.Log;
import com.alipay.streammedia.cvengine.CVNativeException;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: TrackAlgorithm */
final class f implements UncaughtExceptionHandler {
    final /* synthetic */ TrackAlgorithm a;

    f(TrackAlgorithm this$0) {
        this.a = this$0;
    }

    public final void uncaughtException(Thread thread, Throwable ex) {
        Log.e("TrackAlgorithm", "uncaughtException###, thread name:" + thread.getName() + ", thread id:" + thread.getId() + ",ex:" + ex.getMessage());
        StackTraceElement[] elements = ex.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTraceElement : elements) {
            sb.append(stackTraceElement.toString());
            sb.append("\n");
        }
        Log.e("TrackAlgorithm", "exception stack:\n" + sb.toString());
        if (this.a.a != null) {
            try {
                this.a.a.Destory();
            } catch (CVNativeException cve) {
                Log.e("TrackAlgorithm", "mTracker.Destory exp:" + cve);
            }
        }
    }
}
