package com.alipay.mobile.binarize;

import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Environment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class Binarizer {
    private static boolean a = false;
    private boolean b;

    public abstract BinarizeResult getBinarizedData(byte[] bArr);

    /* access modifiers changed from: protected */
    public void saveOriginalImage(byte[] data, int width, int height) {
        if (a) {
            YuvImage yuvImage = new YuvImage(data, 17, width, height, null);
            Rect rect = new Rect(0, 0, width, height);
            try {
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "original.jpg");
                if (!file.exists()) {
                    file.createNewFile();
                }
                yuvImage.compressToJpeg(rect, 100, new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void saveBinarizedImage(byte[] data, int width, int height) {
        if (a) {
            for (int i = width * height; i < data.length; i++) {
                data[i] = Byte.MIN_VALUE;
            }
            YuvImage yuvImage = new YuvImage(data, 17, width, height, null);
            Rect rect = new Rect(0, 0, width, height);
            try {
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "binarize.jpg");
                if (!file.exists()) {
                    file.createNewFile();
                }
                yuvImage.compressToJpeg(rect, 100, new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void saveBinarizedImage(byte[] data, String fileName, int width, int height) {
        if (a) {
            byte[] outData = data;
            if (data.length == width * height) {
                outData = new byte[(((width * height) * 3) / 2)];
                System.arraycopy(data, 0, outData, 0, width * height);
            }
            for (int i = width * height; i < outData.length; i++) {
                outData[i] = Byte.MIN_VALUE;
            }
            YuvImage yuvImage = new YuvImage(outData, 17, width, height, null);
            Rect rect = new Rect(0, 0, width, height);
            try {
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                yuvImage.compressToJpeg(rect, 100, new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public boolean isInitialized() {
        return this.b;
    }

    public void setInitialized(boolean initialized) {
        this.b = initialized;
    }

    public void destroy() {
        this.b = false;
    }

    public void initialize(int width, int height) {
        this.b = true;
    }
}
