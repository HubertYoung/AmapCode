package com.alipay.mobile.monitor.tools;

import android.os.Handler;
import android.os.HandlerThread;

public final class HandlerThreadFactory {
    private static HandlerThreadWrapper sLoopThread = new HandlerThreadWrapper("loop");

    static class HandlerThreadWrapper {
        private Handler handler = null;

        public HandlerThreadWrapper(String str) {
            HandlerThread handlerThread = new HandlerThread("Monitor-".concat(String.valueOf(str)));
            handlerThread.start();
            this.handler = new Handler(handlerThread.getLooper());
        }

        public Handler getHandler() {
            return this.handler;
        }
    }

    private HandlerThreadFactory() {
        throw new InstantiationError("Must not instantiate this class");
    }

    public static Handler getTimerThreadHandler() {
        return sLoopThread.getHandler();
    }
}
