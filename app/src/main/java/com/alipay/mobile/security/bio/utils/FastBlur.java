package com.alipay.mobile.security.bio.utils;

import android.graphics.Bitmap;
import java.lang.reflect.Array;

public class FastBlur {
    public static Bitmap doBlur(Bitmap bitmap, int i, boolean z) {
        Bitmap bitmap2;
        if (!z) {
            bitmap2 = bitmap.copy(bitmap.getConfig(), true);
        } else {
            bitmap2 = bitmap;
        }
        if (i <= 0) {
            return null;
        }
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap2.getPixels(iArr, 0, width, 0, 0, width, height);
        int i2 = width - 1;
        int i3 = height - 1;
        int i4 = width * height;
        int i5 = i + i + 1;
        int[] iArr2 = new int[i4];
        int[] iArr3 = new int[i4];
        int[] iArr4 = new int[i4];
        int[] iArr5 = new int[Math.max(width, height)];
        int i6 = (i5 + 1) >> 1;
        int i7 = i6 * i6;
        int[] iArr6 = new int[(i7 * 256)];
        for (int i8 = 0; i8 < i7 * 256; i8++) {
            iArr6[i8] = i8 / i7;
        }
        int[][] iArr7 = (int[][]) Array.newInstance(Integer.TYPE, new int[]{i5, 3});
        int i9 = i + 1;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (true) {
            int i13 = i10;
            if (i13 >= height) {
                break;
            }
            int i14 = 0;
            int i15 = 0;
            int i16 = 0;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            for (int i23 = -i; i23 <= i; i23++) {
                int i24 = iArr[Math.min(i2, Math.max(i23, 0)) + i12];
                int[] iArr8 = iArr7[i23 + i];
                iArr8[0] = (16711680 & i24) >> 16;
                iArr8[1] = (65280 & i24) >> 8;
                iArr8[2] = i24 & 255;
                int abs = i9 - Math.abs(i23);
                i21 += iArr8[0] * abs;
                i20 += iArr8[1] * abs;
                i19 += abs * iArr8[2];
                if (i23 > 0) {
                    i15 += iArr8[0];
                    i22 += iArr8[1];
                    i14 += iArr8[2];
                } else {
                    i18 += iArr8[0];
                    i17 += iArr8[1];
                    i16 += iArr8[2];
                }
            }
            int i25 = i21;
            int i26 = i20;
            int i27 = i19;
            int i28 = i;
            for (int i29 = 0; i29 < width; i29++) {
                iArr2[i12] = iArr6[i25];
                iArr3[i12] = iArr6[i26];
                iArr4[i12] = iArr6[i27];
                int i30 = i25 - i18;
                int i31 = i26 - i17;
                int i32 = i27 - i16;
                int[] iArr9 = iArr7[((i28 - i) + i5) % i5];
                int i33 = i18 - iArr9[0];
                int i34 = i17 - iArr9[1];
                int i35 = i16 - iArr9[2];
                if (i13 == 0) {
                    iArr5[i29] = Math.min(i29 + i + 1, i2);
                }
                int i36 = iArr[iArr5[i29] + i11];
                iArr9[0] = (16711680 & i36) >> 16;
                iArr9[1] = (65280 & i36) >> 8;
                iArr9[2] = i36 & 255;
                int i37 = i15 + iArr9[0];
                int i38 = i22 + iArr9[1];
                int i39 = i14 + iArr9[2];
                i25 = i30 + i37;
                i26 = i31 + i38;
                i27 = i32 + i39;
                i28 = (i28 + 1) % i5;
                int[] iArr10 = iArr7[i28 % i5];
                i18 = i33 + iArr10[0];
                i17 = i34 + iArr10[1];
                i16 = i35 + iArr10[2];
                i15 = i37 - iArr10[0];
                i22 = i38 - iArr10[1];
                i14 = i39 - iArr10[2];
                i12++;
            }
            i10 = i13 + 1;
            i11 += width;
        }
        for (int i40 = 0; i40 < width; i40++) {
            int i41 = 0;
            int i42 = 0;
            int i43 = 0;
            int i44 = 0;
            int i45 = 0;
            int i46 = -i;
            int i47 = 0;
            int i48 = 0;
            int i49 = 0;
            int i50 = (-i) * width;
            int i51 = 0;
            while (i46 <= i) {
                int max = Math.max(0, i50) + i40;
                int[] iArr11 = iArr7[i46 + i];
                iArr11[0] = iArr2[max];
                iArr11[1] = iArr3[max];
                iArr11[2] = iArr4[max];
                int abs2 = i9 - Math.abs(i46);
                int i52 = (iArr2[max] * abs2) + i49;
                int i53 = (iArr3[max] * abs2) + i48;
                int i54 = (iArr4[max] * abs2) + i47;
                if (i46 > 0) {
                    i42 += iArr11[0];
                    i51 += iArr11[1];
                    i41 += iArr11[2];
                } else {
                    i45 += iArr11[0];
                    i44 += iArr11[1];
                    i43 += iArr11[2];
                }
                if (i46 < i3) {
                    i50 += width;
                }
                i46++;
                i47 = i54;
                i48 = i53;
                i49 = i52;
            }
            int i55 = i48;
            int i56 = i49;
            int i57 = i47;
            int i58 = i;
            int i59 = i41;
            int i60 = i51;
            int i61 = i42;
            int i62 = i43;
            int i63 = i44;
            int i64 = i45;
            int i65 = i40;
            for (int i66 = 0; i66 < height; i66++) {
                iArr[i65] = (-16777216 & iArr[i65]) | (iArr6[i56] << 16) | (iArr6[i55] << 8) | iArr6[i57];
                int i67 = i56 - i64;
                int i68 = i55 - i63;
                int i69 = i57 - i62;
                int[] iArr12 = iArr7[((i58 - i) + i5) % i5];
                int i70 = i64 - iArr12[0];
                int i71 = i63 - iArr12[1];
                int i72 = i62 - iArr12[2];
                if (i40 == 0) {
                    iArr5[i66] = Math.min(i66 + i9, i3) * width;
                }
                int i73 = iArr5[i66] + i40;
                iArr12[0] = iArr2[i73];
                iArr12[1] = iArr3[i73];
                iArr12[2] = iArr4[i73];
                int i74 = i61 + iArr12[0];
                int i75 = i60 + iArr12[1];
                int i76 = i59 + iArr12[2];
                i56 = i67 + i74;
                i55 = i68 + i75;
                i57 = i69 + i76;
                i58 = (i58 + 1) % i5;
                int[] iArr13 = iArr7[i58];
                i64 = i70 + iArr13[0];
                i63 = i71 + iArr13[1];
                i62 = i72 + iArr13[2];
                i61 = i74 - iArr13[0];
                i60 = i75 - iArr13[1];
                i59 = i76 - iArr13[2];
                i65 += width;
            }
        }
        bitmap2.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmap2;
    }
}
