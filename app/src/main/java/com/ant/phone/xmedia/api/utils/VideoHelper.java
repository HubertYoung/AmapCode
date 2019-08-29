package com.ant.phone.xmedia.api.utils;

public class VideoHelper {
    public static void a(byte[] src, byte[] dst, int width, int height) {
        int wh = 0;
        int uvHeight = 0;
        if (!(width == 0 && height == 0)) {
            wh = width * height;
            uvHeight = height >> 1;
        }
        int k = 0;
        int i = 0;
        while (i < width) {
            int nPos = width - 1;
            int j = 0;
            int k2 = k;
            while (j < height) {
                dst[k2] = src[nPos - i];
                nPos += width;
                j++;
                k2++;
            }
            i++;
            k = k2;
        }
        for (int i2 = 0; i2 < width; i2 += 2) {
            int nPos2 = (wh + width) - 1;
            for (int j2 = 0; j2 < uvHeight; j2++) {
                dst[k] = src[(nPos2 - i2) - 1];
                dst[k + 1] = src[nPos2 - i2];
                k += 2;
                nPos2 += width;
            }
        }
    }

    public static void b(byte[] data, byte[] output, int width, int height) {
        int i = 0;
        int x = 0;
        while (x < width) {
            int y = height - 1;
            int i2 = i;
            while (y >= 0) {
                output[i2] = data[(y * width) + x];
                y--;
                i2++;
            }
            x++;
            i = i2;
        }
        int ustart = width * height;
        int i3 = ((ustart * 3) / 2) - 1;
        int pos = height >> 1;
        int x2 = width - 1;
        while (x2 > 0) {
            int i4 = i3;
            for (int y2 = 0; y2 < pos; y2++) {
                int i5 = i4 - 1;
                output[i4] = data[(y2 * width) + ustart + x2];
                i4 = i5 - 1;
                output[i5] = data[(y2 * width) + ustart + (x2 - 1)];
            }
            x2 -= 2;
            i3 = i4;
        }
    }
}
