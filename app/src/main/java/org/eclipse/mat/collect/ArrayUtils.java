package org.eclipse.mat.collect;

import android.support.v4.media.TransportMediator;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;

public class ArrayUtils {
    public static void sort(int[] iArr, int[] iArr2) {
        hybridsort(iArr, iArr2, 0, iArr.length - 1);
    }

    public static void sortDesc(long[] jArr, int[] iArr) {
        hybridsortDesc(jArr, iArr, null, null, 0, jArr.length - 1);
    }

    public static void sortDesc(long[] jArr, int[] iArr, long[] jArr2, int[] iArr2) {
        hybridsortDesc(jArr, iArr, jArr2, iArr2, 0, jArr.length - 1);
    }

    public static void sort(int[] iArr, int[] iArr2, int i, int i2) {
        hybridsort(iArr, iArr2, i, (i2 + i) - 1);
    }

    private static void swap(int[] iArr, int[] iArr2, int i, int i2) {
        int i3 = iArr[i];
        iArr[i] = iArr[i2];
        iArr[i2] = i3;
        int i4 = iArr2[i];
        iArr2[i] = iArr2[i2];
        iArr2[i2] = i4;
    }

    private static void swap(long[] jArr, int[] iArr, int i, int i2) {
        long j = jArr[i];
        jArr[i] = jArr[i2];
        jArr[i2] = j;
        int i3 = iArr[i];
        iArr[i] = iArr[i2];
        iArr[i2] = i3;
    }

    private static int median(int[] iArr, int i, int i2, int i3) {
        int i4 = iArr[i];
        int i5 = iArr[i2];
        int i6 = iArr[i3];
        if (i4 < i5) {
            if (i5 <= i6) {
                return i2;
            }
            return i4 < i6 ? i3 : i;
        } else if (i4 <= i6) {
            return i;
        } else {
            return i5 < i6 ? i3 : i2;
        }
    }

    private static int median(long[] jArr, int i, int i2, int i3) {
        long j = jArr[i];
        long j2 = jArr[i2];
        long j3 = jArr[i3];
        if (j < j2) {
            if (j2 <= j3) {
                return i2;
            }
            return j < j3 ? i3 : i;
        } else if (j <= j3) {
            return i;
        } else {
            return j2 < j3 ? i3 : i2;
        }
    }

    private static int[] split(int[] iArr, int[] iArr2, int i, int i2) {
        int median = median(iArr, i, i2, ((i2 - i) >> 1) + i);
        int i3 = iArr[median];
        swap(iArr, iArr2, i, median);
        int i4 = i;
        int i5 = 0;
        for (int i6 = i + 1; i6 <= i2; i6++) {
            if (iArr[i6] < i3) {
                i4++;
                swap(iArr, iArr2, i4, i6);
                if (i5 > 0) {
                    swap(iArr, iArr2, i4 + i5, i6);
                }
            } else if (iArr[i6] == i3) {
                i5++;
                swap(iArr, iArr2, i4 + i5, i6);
            }
        }
        swap(iArr, iArr2, i, i4);
        return new int[]{i4, i4 + i5};
    }

    private static int[] splitDesc(long[] jArr, int[] iArr, int i, int i2) {
        int median = median(jArr, i, i2, ((i2 - i) >> 1) + i);
        long j = jArr[median];
        swap(jArr, iArr, i, median);
        int i3 = i;
        int i4 = 0;
        for (int i5 = i + 1; i5 <= i2; i5++) {
            if (jArr[i5] > j) {
                i3++;
                swap(jArr, iArr, i3, i5);
                if (i4 > 0) {
                    swap(jArr, iArr, i3 + i4, i5);
                }
            } else if (jArr[i5] == j) {
                i4++;
                swap(jArr, iArr, i3 + i4, i5);
            }
        }
        swap(jArr, iArr, i, i3);
        return new int[]{i3, i3 + i4};
    }

    private static void hybridsort(int[] iArr, int[] iArr2, int i, int i2) {
        while (true) {
            int i3 = i2 - i;
            if (i3 <= 0) {
                return;
            }
            if (i3 < 5000000) {
                radixsort(iArr, iArr2, i, i3 + 1);
                return;
            }
            int[] split = split(iArr, iArr2, i, i2);
            if (split[0] - i <= i2 - split[1]) {
                hybridsort(iArr, iArr2, i, split[0] - 1);
                i = split[1] + 1;
            } else {
                hybridsort(iArr, iArr2, split[1] + 1, i2);
                i2 = split[0] - 1;
            }
        }
    }

    private static void hybridsortDesc(long[] jArr, int[] iArr, long[] jArr2, int[] iArr2, int i, int i2) {
        long[] jArr3 = jArr;
        int[] iArr3 = iArr;
        int i3 = i;
        int i4 = i2;
        while (true) {
            int i5 = i4 - i3;
            if (i5 <= 0) {
                return;
            }
            if (i5 >= 5000000) {
                int[] splitDesc = splitDesc(jArr3, iArr3, i3, i4);
                if (splitDesc[0] - i3 <= i4 - splitDesc[1]) {
                    hybridsortDesc(jArr3, iArr3, jArr2, iArr2, i3, splitDesc[0] - 1);
                    i3 = splitDesc[1] + 1;
                } else {
                    hybridsortDesc(jArr3, iArr3, jArr2, iArr2, splitDesc[1] + 1, i4);
                    i4 = splitDesc[0] - 1;
                }
            } else if (i5 < 12) {
                for (int i6 = i3; i6 <= i4; i6++) {
                    for (int i7 = i6; i7 > i3; i7--) {
                        int i8 = i7 - 1;
                        if (jArr3[i8] >= jArr3[i7]) {
                            break;
                        }
                        swap(jArr3, iArr3, i7, i8);
                    }
                }
                return;
            } else {
                radixsortDesc(jArr3, iArr3, jArr2, iArr2, i3, i5 + 1);
                return;
            }
        }
    }

    private static void radixsort(int[] iArr, int[] iArr2, int i, int i2) {
        int[] iArr3 = new int[i2];
        int[] iArr4 = new int[i2];
        int i3 = i2;
        countsort(iArr, iArr3, iArr2, iArr4, i, 0, i3, 0);
        countsort(iArr3, iArr, iArr4, iArr2, 0, i, i3, 1);
        countsort(iArr, iArr3, iArr2, iArr4, i, 0, i3, 2);
        countsort(iArr3, iArr, iArr4, iArr2, 0, i, i3, 3);
    }

    private static void radixsortDesc(long[] jArr, int[] iArr, long[] jArr2, int[] iArr2, int i, int i2) {
        if (jArr2 == null) {
            jArr2 = new long[i2];
        }
        if (iArr2 == null) {
            iArr2 = new int[i2];
        }
        int i3 = i2;
        countsortDesc(jArr, jArr2, iArr, iArr2, i, 0, i3, 0);
        countsortDesc(jArr2, jArr, iArr2, iArr, 0, i, i3, 1);
        countsortDesc(jArr, jArr2, iArr, iArr2, i, 0, i3, 2);
        countsortDesc(jArr2, jArr, iArr2, iArr, 0, i, i3, 3);
        countsortDesc(jArr, jArr2, iArr, iArr2, i, 0, i3, 4);
        countsortDesc(jArr2, jArr, iArr2, iArr, 0, i, i3, 5);
        countsortDesc(jArr, jArr2, iArr, iArr2, i, 0, i3, 6);
        countsortDesc(jArr2, jArr, iArr2, iArr, 0, i, i3, 7);
    }

    private static void countsort(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int i, int i2, int i3, int i4) {
        int i5;
        int[] iArr5 = new int[256];
        int[] iArr6 = new int[256];
        int i6 = i4 * 8;
        int i7 = i3 + i;
        int i8 = i;
        while (true) {
            i5 = 1;
            if (i8 >= i7) {
                break;
            }
            int i9 = 255 & (iArr[i8] >> i6);
            iArr5[i9] = iArr5[i9] + 1;
            i8++;
        }
        if (i4 == 3) {
            for (int i10 = 129; i10 < 256; i10++) {
                int i11 = i10 - 1;
                iArr6[i10] = iArr6[i11] + iArr5[i11];
            }
            iArr6[0] = iArr6[255] + iArr5[255];
            while (i5 < 128) {
                int i12 = i5 - 1;
                iArr6[i5] = iArr6[i12] + iArr5[i12];
                i5++;
            }
        } else {
            while (i5 < 256) {
                int i13 = i5 - 1;
                iArr6[i5] = iArr6[i13] + iArr5[i13];
                i5++;
            }
        }
        while (i < i7) {
            int i14 = (iArr[i] >> i6) & 255;
            iArr4[iArr6[i14] + i2] = iArr3[i];
            int i15 = iArr6[i14];
            iArr6[i14] = i15 + 1;
            iArr2[i15 + i2] = iArr[i];
            i++;
        }
    }

    private static void countsortDesc(long[] jArr, long[] jArr2, int[] iArr, int[] iArr2, int i, int i2, int i3, int i4) {
        int[] iArr3 = new int[256];
        int[] iArr4 = new int[256];
        int i5 = i4 * 8;
        int i6 = i3 + i;
        for (int i7 = i; i7 < i6; i7++) {
            int i8 = (int) (255 & (jArr[i7] >> i5));
            iArr3[i8] = iArr3[i8] + 1;
        }
        int i9 = DumpSegment.ANDROID_HEAP_DUMP_INFO;
        if (i4 == 7) {
            for (int i10 = TransportMediator.KEYCODE_MEDIA_PLAY; i10 >= 0; i10--) {
                int i11 = i10 + 1;
                iArr4[i10] = iArr4[i11] + iArr3[i11];
            }
            iArr4[255] = iArr4[0] + iArr3[0];
            while (i9 >= 128) {
                int i12 = i9 + 1;
                iArr4[i9] = iArr4[i12] + iArr3[i12];
                i9--;
            }
        } else {
            while (i9 >= 0) {
                int i13 = i9 + 1;
                iArr4[i9] = iArr4[i13] + iArr3[i13];
                i9--;
            }
        }
        while (i < i6) {
            int i14 = (int) ((jArr[i] >> i5) & 255);
            iArr2[iArr4[i14] + i2] = iArr[i];
            int i15 = iArr4[i14];
            iArr4[i14] = i15 + 1;
            jArr2[i15 + i2] = jArr[i];
            i++;
        }
    }
}
