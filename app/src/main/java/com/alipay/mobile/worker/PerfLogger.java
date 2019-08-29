package com.alipay.mobile.worker;

import android.os.SystemClock;
import android.util.Log;

public class PerfLogger {
    private static long a = 0;
    private static long b;
    private static long c;
    private static long d;
    private static long e;
    private static long f;
    private static long g;
    private static long h;
    private static long i;
    private static long j;
    private static long k;
    private static long l;

    public static void onLoadUrl() {
        if (a == 0) {
            a = SystemClock.elapsedRealtime();
        }
    }

    public static void onConsoleMessage(String message) {
        if (message.contains("appx.min.js.start")) {
            b = SystemClock.elapsedRealtime();
        } else if (message.contains("appx.min.js.end")) {
            c = SystemClock.elapsedRealtime();
        } else if (message.contains("registerWorker: start")) {
            d = SystemClock.elapsedRealtime();
        } else if (message.contains("registerWorker: end")) {
            e = SystemClock.elapsedRealtime();
        } else if (message.contains("initMessageChannel: start")) {
            f = SystemClock.elapsedRealtime();
        } else if (message.contains("initMessageChannel: end")) {
            g = SystemClock.elapsedRealtime();
        } else if (message.contains("startRender: start send msg to render")) {
            h = SystemClock.elapsedRealtime();
        } else if (message.contains("startRender: render data")) {
            i = SystemClock.elapsedRealtime();
        } else if (message.contains("startRender: end")) {
            j = SystemClock.elapsedRealtime();
            Log.e("nicholas", "loadUrl " + (b - a) + " appxMinStart " + (c - b) + " appxMinEnd " + (d - c) + " registerWorkerStart " + (e - d) + " registerWorkerEnd " + (f - e) + " initMessageChannelStart " + (g - f) + " initMessageChannelEnd " + (h - g) + " sendRenderData " + (i - h) + " recvSendRenderData " + (j - i) + " renderEnd");
            Log.e("nicholas", "index.worker.js exec cost = " + (l - k) + ", parallel time = " + (l - a));
        } else if (message.contains("worker success set __appxStartupParams")) {
            k = SystemClock.elapsedRealtime();
        } else if (message.contains("index.worker.js.end")) {
            l = SystemClock.elapsedRealtime();
        }
    }
}
