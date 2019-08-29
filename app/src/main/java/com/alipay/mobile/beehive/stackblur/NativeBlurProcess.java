package com.alipay.mobile.beehive.stackblur;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import java.util.ArrayList;
import java.util.concurrent.Callable;

class NativeBlurProcess implements a {

    private static class a implements Callable<Void> {
        private final Bitmap a;
        private final int b;
        private final int c = 2;
        private final int d;
        private final int e;

        public a(Bitmap bitmapOut, int radius, int coreIndex, int round) {
            this.a = bitmapOut;
            this.b = radius;
            this.d = coreIndex;
            this.e = round;
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public Void call() {
            NativeBlurProcess.functionToBlur(this.a, this.b, this.c, this.d, this.e);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static native void functionToBlur(Bitmap bitmap, int i, int i2, int i3, int i4);

    NativeBlurProcess() {
    }

    static {
        System.loadLibrary("blur");
    }

    public final Bitmap a(Bitmap original, float radius) {
        Bitmap bitmapOut = original.copy(Config.ARGB_8888, true);
        ArrayList horizontal = new ArrayList(2);
        ArrayList vertical = new ArrayList(2);
        for (int i = 0; i < 2; i++) {
            horizontal.add(new a(bitmapOut, (int) radius, i, 1));
            vertical.add(new a(bitmapOut, (int) radius, i, 2));
        }
        try {
            StackBlurManager.getExecutor().invokeAll(horizontal);
            try {
                StackBlurManager.getExecutor().invokeAll(vertical);
            } catch (InterruptedException e) {
            }
        } catch (InterruptedException e2) {
        }
        return bitmapOut;
    }
}
