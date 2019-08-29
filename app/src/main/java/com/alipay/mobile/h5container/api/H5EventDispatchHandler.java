package com.alipay.mobile.h5container.api;

import android.os.Handler;
import android.os.HandlerThread;

public class H5EventDispatchHandler {
    private static Handler asyncHandler;

    public static synchronized Handler getAsyncHandler() {
        Handler handler;
        synchronized (H5EventDispatchHandler.class) {
            try {
                if (asyncHandler == null) {
                    HandlerThread handlerThread = new HandlerThread("H5EvenDispatchHandler");
                    handlerThread.start();
                    asyncHandler = new Handler(handlerThread.getLooper());
                }
                handler = asyncHandler;
            }
        }
        return handler;
    }
}
