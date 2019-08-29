package com.alipay.mobile.nebulacore.dev.sampler;

import android.os.Handler;
import android.os.HandlerThread;

final class HandlerThreadFactory {
    private static HandlerThreadWrapper a = new HandlerThreadWrapper("loop");
    private static HandlerThreadWrapper b = new HandlerThreadWrapper("writer");

    private static class HandlerThreadWrapper {
        private Handler a = null;

        public HandlerThreadWrapper(String threadName) {
            HandlerThread handlerThread = new HandlerThread("Nebula-" + threadName);
            handlerThread.start();
            this.a = new Handler(handlerThread.getLooper());
        }

        public Handler getHandler() {
            return this.a;
        }
    }

    private HandlerThreadFactory() {
        throw new InstantiationError("Must not instantiate this class");
    }

    public static Handler getTimerThreadHandler() {
        return a.getHandler();
    }

    public static Handler getWriteLogThreadHandler() {
        return b.getHandler();
    }
}
