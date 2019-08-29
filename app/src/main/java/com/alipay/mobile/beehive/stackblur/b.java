package com.alipay.mobile.beehive.stackblur;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/* compiled from: JavaBlurProcess */
final class b implements a {
    private static final short[] a = {512, 512, 456, 512, 328, 456, 335, 512, 405, 328, 271, 456, 388, 335, 292, 512, 454, 405, 364, 328, 298, 271, 496, 456, 420, 388, 360, 335, 312, 292, 273, 512, 482, 454, 428, 405, 383, 364, 345, 328, 312, 298, 284, 271, 259, 496, 475, 456, 437, 420, 404, 388, 374, 360, 347, 335, 323, 312, 302, 292, 282, 273, 265, 512, 497, 482, 468, 454, 441, 428, 417, 405, 394, 383, 373, 364, 354, 345, 337, 328, 320, 312, 305, 298, 291, 284, 278, 271, 265, 259, 507, 496, 485, 475, 465, 456, 446, 437, 428, 420, 412, 404, 396, 388, 381, 374, 367, 360, 354, 347, 341, 335, 329, 323, 318, 312, 307, 302, 297, 292, 287, 282, 278, 273, 269, 265, 261, 512, 505, 497, 489, 482, 475, 468, 461, 454, 447, 441, 435, 428, 422, 417, 411, 405, 399, 394, 389, 383, 378, 373, 368, 364, 359, 354, 350, 345, 341, 337, 332, 328, 324, 320, 316, 312, 309, 305, 301, 298, 294, 291, 287, 284, 281, 278, 274, 271, 268, 265, 262, 259, 257, 507, 501, 496, 491, 485, 480, 475, 470, 465, 460, 456, 451, 446, 442, 437, 433, 428, 424, 420, 416, 412, 408, 404, 400, 396, 392, 388, 385, 381, 377, 374, 370, 367, 363, 360, 357, 354, 350, 347, 344, 341, 338, 335, 332, 329, 326, 323, 320, 318, 315, 312, 310, 307, 304, 302, 299, 297, 294, 292, 289, 287, 285, 282, 280, 278, 275, 273, 271, 269, 267, 265, 263, 261, 259};
    private static final byte[] b = {9, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 13, 13, 14, 14, 15, 15, 15, 15, 16, 16, 16, 16, 17, 17, 17, 17, 17, 17, 17, 18, 18, 18, 18, 18, 18, 18, 18, 18, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24};

    /* compiled from: JavaBlurProcess */
    private static class a implements Callable<Void> {
        private final int[] a;
        private final int b;
        private final int c;
        private final int d;
        private final int e = 2;
        private final int f;
        private final int g;

        public a(int[] src, int w, int h, int radius, int coreIndex, int round) {
            this.a = src;
            this.b = w;
            this.c = h;
            this.d = radius;
            this.f = coreIndex;
            this.g = round;
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public Void call() {
            b.b(this.a, this.b, this.c, this.d, this.e, this.f, this.g);
            return null;
        }
    }

    b() {
    }

    public final Bitmap a(Bitmap original, float radius) {
        int w = original.getWidth();
        int h = original.getHeight();
        int[] currentPixels = new int[(w * h)];
        original.getPixels(currentPixels, 0, w, 0, 0, w, h);
        ArrayList horizontal = new ArrayList(2);
        ArrayList vertical = new ArrayList(2);
        for (int i = 0; i < 2; i++) {
            horizontal.add(new a(currentPixels, w, h, (int) radius, i, 1));
            vertical.add(new a(currentPixels, w, h, (int) radius, i, 2));
        }
        try {
            StackBlurManager.getExecutor().invokeAll(horizontal);
            try {
                StackBlurManager.getExecutor().invokeAll(vertical);
                return Bitmap.createBitmap(currentPixels, w, h, Config.ARGB_8888);
            } catch (InterruptedException e) {
                return null;
            }
        } catch (InterruptedException e2) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static void b(int[] src, int w, int h, int radius, int cores, int core, int step) {
        int wm = w - 1;
        int hm = h - 1;
        int div = (radius * 2) + 1;
        short mul_sum = a[radius];
        byte shr_sum = b[radius];
        int[] stack = new int[div];
        if (step == 1) {
            int maxY = ((core + 1) * h) / cores;
            for (int y = (core * h) / cores; y < maxY; y++) {
                long sum_out_b = 0;
                long sum_out_g = 0;
                long sum_out_r = 0;
                long sum_in_b = 0;
                long sum_in_g = 0;
                long sum_in_r = 0;
                long sum_b = 0;
                long sum_g = 0;
                long sum_r = 0;
                int src_i = w * y;
                for (int i = 0; i <= radius; i++) {
                    stack[i] = src[src_i];
                    sum_r += (long) (((src[src_i] >>> 16) & 255) * (i + 1));
                    sum_g += (long) (((src[src_i] >>> 8) & 255) * (i + 1));
                    sum_b += (long) ((src[src_i] & 255) * (i + 1));
                    sum_out_r += (long) ((src[src_i] >>> 16) & 255);
                    sum_out_g += (long) ((src[src_i] >>> 8) & 255);
                    sum_out_b += (long) (src[src_i] & 255);
                }
                for (int i2 = 1; i2 <= radius; i2++) {
                    if (i2 <= wm) {
                        src_i++;
                    }
                    stack[i2 + radius] = src[src_i];
                    sum_r += (long) (((src[src_i] >>> 16) & 255) * ((radius + 1) - i2));
                    sum_g += (long) (((src[src_i] >>> 8) & 255) * ((radius + 1) - i2));
                    sum_b += (long) ((src[src_i] & 255) * ((radius + 1) - i2));
                    sum_in_r += (long) ((src[src_i] >>> 16) & 255);
                    sum_in_g += (long) ((src[src_i] >>> 8) & 255);
                    sum_in_b += (long) (src[src_i] & 255);
                }
                int sp = radius;
                int xp = radius;
                if (radius > wm) {
                    xp = wm;
                }
                int src_i2 = xp + (y * w);
                int dst_i = y * w;
                for (int x = 0; x < w; x++) {
                    src[dst_i] = (int) (((long) (src[dst_i] & -16777216)) | ((((((long) mul_sum) * sum_r) >>> shr_sum) & 255) << 16) | ((((((long) mul_sum) * sum_g) >>> shr_sum) & 255) << 8) | (((((long) mul_sum) * sum_b) >>> shr_sum) & 255));
                    dst_i++;
                    long sum_r2 = sum_r - sum_out_r;
                    long sum_g2 = sum_g - sum_out_g;
                    long sum_b2 = sum_b - sum_out_b;
                    int stack_start = (sp + div) - radius;
                    if (stack_start >= div) {
                        stack_start -= div;
                    }
                    int stack_i = stack_start;
                    long sum_out_r2 = sum_out_r - ((long) ((stack[stack_i] >>> 16) & 255));
                    long sum_out_g2 = sum_out_g - ((long) ((stack[stack_i] >>> 8) & 255));
                    long sum_out_b2 = sum_out_b - ((long) (stack[stack_i] & 255));
                    if (xp < wm) {
                        src_i2++;
                        xp++;
                    }
                    stack[stack_i] = src[src_i2];
                    long sum_in_r2 = sum_in_r + ((long) ((src[src_i2] >>> 16) & 255));
                    long sum_in_g2 = sum_in_g + ((long) ((src[src_i2] >>> 8) & 255));
                    long sum_in_b2 = sum_in_b + ((long) (src[src_i2] & 255));
                    sum_r = sum_r2 + sum_in_r2;
                    sum_g = sum_g2 + sum_in_g2;
                    sum_b = sum_b2 + sum_in_b2;
                    sp++;
                    if (sp >= div) {
                        sp = 0;
                    }
                    int stack_i2 = sp;
                    sum_out_r = sum_out_r2 + ((long) ((stack[stack_i2] >>> 16) & 255));
                    sum_out_g = sum_out_g2 + ((long) ((stack[stack_i2] >>> 8) & 255));
                    sum_out_b = sum_out_b2 + ((long) (stack[stack_i2] & 255));
                    sum_in_r = sum_in_r2 - ((long) ((stack[stack_i2] >>> 16) & 255));
                    sum_in_g = sum_in_g2 - ((long) ((stack[stack_i2] >>> 8) & 255));
                    sum_in_b = sum_in_b2 - ((long) (stack[stack_i2] & 255));
                }
            }
        } else if (step == 2) {
            int maxX = ((core + 1) * w) / cores;
            for (int x2 = (core * w) / cores; x2 < maxX; x2++) {
                long sum_out_b3 = 0;
                long sum_out_g3 = 0;
                long sum_out_r3 = 0;
                long sum_in_b3 = 0;
                long sum_in_g3 = 0;
                long sum_in_r3 = 0;
                long sum_b3 = 0;
                long sum_g3 = 0;
                long sum_r3 = 0;
                int src_i3 = x2;
                for (int i3 = 0; i3 <= radius; i3++) {
                    stack[i3] = src[src_i3];
                    sum_r3 += (long) (((src[src_i3] >>> 16) & 255) * (i3 + 1));
                    sum_g3 += (long) (((src[src_i3] >>> 8) & 255) * (i3 + 1));
                    sum_b3 += (long) ((src[src_i3] & 255) * (i3 + 1));
                    sum_out_r3 += (long) ((src[src_i3] >>> 16) & 255);
                    sum_out_g3 += (long) ((src[src_i3] >>> 8) & 255);
                    sum_out_b3 += (long) (src[src_i3] & 255);
                }
                for (int i4 = 1; i4 <= radius; i4++) {
                    if (i4 <= hm) {
                        src_i3 += w;
                    }
                    stack[i4 + radius] = src[src_i3];
                    sum_r3 += (long) (((src[src_i3] >>> 16) & 255) * ((radius + 1) - i4));
                    sum_g3 += (long) (((src[src_i3] >>> 8) & 255) * ((radius + 1) - i4));
                    sum_b3 += (long) ((src[src_i3] & 255) * ((radius + 1) - i4));
                    sum_in_r3 += (long) ((src[src_i3] >>> 16) & 255);
                    sum_in_g3 += (long) ((src[src_i3] >>> 8) & 255);
                    sum_in_b3 += (long) (src[src_i3] & 255);
                }
                int sp2 = radius;
                int yp = radius;
                if (radius > hm) {
                    yp = hm;
                }
                int src_i4 = x2 + (yp * w);
                int dst_i2 = x2;
                for (int y2 = 0; y2 < h; y2++) {
                    src[dst_i2] = (int) (((long) (src[dst_i2] & -16777216)) | ((((((long) mul_sum) * sum_r3) >>> shr_sum) & 255) << 16) | ((((((long) mul_sum) * sum_g3) >>> shr_sum) & 255) << 8) | (((((long) mul_sum) * sum_b3) >>> shr_sum) & 255));
                    dst_i2 += w;
                    long sum_r4 = sum_r3 - sum_out_r3;
                    long sum_g4 = sum_g3 - sum_out_g3;
                    long sum_b4 = sum_b3 - sum_out_b3;
                    int stack_start2 = (sp2 + div) - radius;
                    if (stack_start2 >= div) {
                        stack_start2 -= div;
                    }
                    int stack_i3 = stack_start2;
                    long sum_out_r4 = sum_out_r3 - ((long) ((stack[stack_i3] >>> 16) & 255));
                    long sum_out_g4 = sum_out_g3 - ((long) ((stack[stack_i3] >>> 8) & 255));
                    long sum_out_b4 = sum_out_b3 - ((long) (stack[stack_i3] & 255));
                    if (yp < hm) {
                        src_i4 += w;
                        yp++;
                    }
                    stack[stack_i3] = src[src_i4];
                    long sum_in_r4 = sum_in_r3 + ((long) ((src[src_i4] >>> 16) & 255));
                    long sum_in_g4 = sum_in_g3 + ((long) ((src[src_i4] >>> 8) & 255));
                    long sum_in_b4 = sum_in_b3 + ((long) (src[src_i4] & 255));
                    sum_r3 = sum_r4 + sum_in_r4;
                    sum_g3 = sum_g4 + sum_in_g4;
                    sum_b3 = sum_b4 + sum_in_b4;
                    sp2++;
                    if (sp2 >= div) {
                        sp2 = 0;
                    }
                    int stack_i4 = sp2;
                    sum_out_r3 = sum_out_r4 + ((long) ((stack[stack_i4] >>> 16) & 255));
                    sum_out_g3 = sum_out_g4 + ((long) ((stack[stack_i4] >>> 8) & 255));
                    sum_out_b3 = sum_out_b4 + ((long) (stack[stack_i4] & 255));
                    sum_in_r3 = sum_in_r4 - ((long) ((stack[stack_i4] >>> 16) & 255));
                    sum_in_g3 = sum_in_g4 - ((long) ((stack[stack_i4] >>> 8) & 255));
                    sum_in_b3 = sum_in_b4 - ((long) (stack[stack_i4] & 255));
                }
            }
        }
    }
}
