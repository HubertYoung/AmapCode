package tv.danmaku.ijk.media.encode;

import android.support.v4.view.MotionEventCompat;

public class VideoHelper {
    public static void NV21ToYUV420Planar(byte[] input, byte[] output, int width, int height) {
        int frameSize = width * height;
        int qFrameSize = frameSize / 4;
        System.arraycopy(input, 0, output, 0, frameSize);
        for (int i = 0; i < qFrameSize; i++) {
            output[frameSize + i] = input[frameSize + i + i + 1];
            output[frameSize + i + qFrameSize] = input[frameSize + i + i];
        }
    }

    public static void rotateYUV420SPClockwiseDegree90(byte[] src, byte[] dst, int width, int height) {
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

    public static void rotateYUV420SPAntiClockwiseDegree90(byte[] data, byte[] output, int width, int height) {
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

    public static byte[] cropYUV420(byte[] data, int imageW, int imageH, int newImageH) {
        byte[] yuv = new byte[(((imageW * newImageH) * 3) / 2)];
        int cropH = (imageH - newImageH) / 2;
        int count = 0;
        int j = cropH;
        while (j < cropH + newImageH) {
            int i = 0;
            int count2 = count;
            while (i < imageW) {
                yuv[count2] = data[(j * imageW) + i];
                i++;
                count2++;
            }
            j++;
            count = count2;
        }
        int tmp = imageH + (cropH / 2);
        int j2 = tmp;
        while (j2 < (newImageH / 2) + tmp) {
            int i2 = 0;
            int count3 = count;
            while (i2 < imageW) {
                yuv[count3] = data[(j2 * imageW) + i2];
                i2++;
                count3++;
            }
            j2++;
            count = count3;
        }
        return yuv;
    }

    public static void encodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) {
        int uvIndex;
        int yIndex;
        int yIndex2 = 0;
        int uvIndex2 = width * height;
        int index = 0;
        int j = 0;
        while (j < height) {
            int i = 0;
            while (true) {
                uvIndex = uvIndex2;
                yIndex = yIndex2;
                if (i >= width) {
                    break;
                }
                int R = (argb[index] & 16711680) >> 16;
                int G = (argb[index] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                int B = (argb[index] & 255) >> 0;
                int Y = (((((R * 66) + (G * 129)) + (B * 25)) + 128) >> 8) + 16;
                int U = (((((R * -38) - (G * 74)) + (B * 112)) + 128) >> 8) + 128;
                int V = (((((R * 112) - (G * 94)) - (B * 18)) + 128) >> 8) + 128;
                yIndex2 = yIndex + 1;
                if (Y < 0) {
                    Y = 0;
                } else if (Y > 255) {
                    Y = 255;
                }
                yuv420sp[yIndex] = (byte) Y;
                if (j % 2 == 0 && index % 2 == 0) {
                    int uvIndex3 = uvIndex + 1;
                    if (V < 0) {
                        V = 0;
                    } else if (V > 255) {
                        V = 255;
                    }
                    yuv420sp[uvIndex] = (byte) V;
                    uvIndex = uvIndex3 + 1;
                    if (U < 0) {
                        U = 0;
                    } else if (U > 255) {
                        U = 255;
                    }
                    yuv420sp[uvIndex3] = (byte) U;
                }
                uvIndex2 = uvIndex;
                index++;
                i++;
            }
            j++;
            uvIndex2 = uvIndex;
            yIndex2 = yIndex;
        }
    }
}
