package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.utils.AshmemLocalMonitor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.FalconDecoderBridge;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.FalconEncoderBridge;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.FalconUtilsBridge;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Bitmaps;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.multimedia.img.ImageSize;
import com.alipay.multimedia.img.base.StaticOptions;
import com.alipay.multimedia.img.decode.CropOptions;
import com.alipay.multimedia.img.decode.ImageDecoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class FalconFacade {
    public static final int QUA_HIGH = 2;
    public static final int QUA_LOCAL_ORIGINAL = 3;
    public static final int QUA_LOW = 0;
    public static final int QUA_MIDDLE = 1;
    private static FalconFacade a = new FalconFacade();
    /* access modifiers changed from: private */
    public static final Logger b = Logger.getLogger((String) "FalconFacade");
    private FalconDecoderBridge c = new FalconDecoderBridge();
    /* access modifiers changed from: private */
    public FalconEncoderBridge d = new FalconEncoderBridge();

    private class FalconImageCutProxy implements InvocationHandler {
        private FalconInterface b;

        public FalconImageCutProxy(FalconInterface target) {
            this.b = target;
        }

        public Object invoke(Object proxy, Method method, Object[] args) {
            if (ByteArrayOutputStream.class.equals(method.getReturnType())) {
                return method.invoke(this.b, args);
            }
            return a(method, args);
        }

        private Object a(Method method, Object[] args) {
            Object obj;
            FalconDecoderBridge cut = this.b.getFalconImgCut();
            boolean useAshmem = this.b.isUseAshmem();
            boolean decodeByAshmem = false;
            Object result = null;
            if (useAshmem) {
                try {
                    result = method.invoke(this.b, args);
                    if (result != null) {
                        decodeByAshmem = true;
                    } else {
                        decodeByAshmem = false;
                    }
                } catch (InvocationTargetException e) {
                    decodeByAshmem = false;
                    FalconFacade.b.e(e.getCause(), "FalconImageCutProxy decode by ashmem error", new Object[0]);
                } catch (Throwable t) {
                    decodeByAshmem = false;
                    FalconFacade.b.e(t, "FalconImageCutProxy decode by ashmem error", new Object[0]);
                }
                if (decodeByAshmem) {
                    a((Bitmap) result);
                }
                if (!decodeByAshmem) {
                    FalconFacade.b.d("FalconImageCutProxy decode by ashmem result: false", new Object[0]);
                }
            }
            if (!decodeByAshmem) {
                cut.setIsUseNewMethod(false);
                Object result2 = method.invoke(this.b, args);
                if (result2 != null && useAshmem) {
                    AshmemLocalMonitor.get().increaseInvalidCount();
                }
                obj = result2;
            } else {
                obj = result;
            }
            if (obj instanceof Bitmap) {
                ((Bitmap) obj).setHasAlpha(true);
            }
            return obj;
        }

        private static boolean a(Bitmap bitmap) {
            if (bitmap == null) {
                return false;
            }
            try {
                Bitmaps.pinBitmap(bitmap);
                return true;
            } catch (Throwable e) {
                FalconFacade.b.e(e, "pinBitmap error, bitmap: " + bitmap, new Object[0]);
                AshmemLocalMonitor.get().increaseInvalidCount();
                return false;
            }
        }
    }

    interface FalconInterface {
        ByteArrayOutputStream compressImage(File file, int i, int i2, int i3);

        ByteArrayOutputStream compressImage(InputStream inputStream, int i, int i2, int i3);

        ByteArrayOutputStream compressImage(byte[] bArr, int i, int i2, int i3);

        Bitmap cutDataImage(byte[] bArr, Point point, int i, int i2, int i3);

        Bitmap cutImage(File file, Point point, int i, int i2, int i3);

        Bitmap cutImage_backgroud(File file, int i, int i2);

        Bitmap cutImage_backgroud(InputStream inputStream, int i, int i2);

        Bitmap cutImage_keepRatio(File file, int i, int i2);

        Bitmap cutImage_keepRatio(InputStream inputStream, int i, int i2);

        Bitmap cutImage_new(File file, int i, int i2, float f);

        Bitmap cutImage_new(InputStream inputStream, int i, int i2, float f);

        FalconDecoderBridge getFalconImgCut();

        boolean isUseAshmem();
    }

    class FalconInterfaceImpl implements FalconInterface {
        final /* synthetic */ FalconFacade a;
        private FalconDecoderBridge b;
        private boolean c = ConfigManager.getInstance().getAshmemConfSwitch();
        private boolean d = true;

        public FalconInterfaceImpl(FalconFacade this$0) {
            boolean z = true;
            this.a = this$0;
            this.d = ConfigManager.getInstance().getCommonConfigItem().imageProcessorConf.systemCropNew != 1 ? false : z;
            this.b = new FalconDecoderBridge();
            this.b.setIsUseNewMethod(this.c);
        }

        public FalconDecoderBridge getFalconImgCut() {
            return this.b;
        }

        public boolean isUseAshmem() {
            return this.c;
        }

        public Bitmap cutImage(File file, Point startPoint, int width, int height, int cropType) {
            CropOptions options = new CropOptions();
            options.startPoint = startPoint;
            options.cutSize = new ImageSize(width, height);
            options.cropMode = cropType;
            options.autoUseAshmem = this.c;
            options.systemCropNew = this.d;
            return ImageDecoder.cropBitmap(file, options).bitmap;
        }

        public Bitmap cutDataImage(byte[] data, Point startPoint, int width, int height, int cropType) {
            CropOptions options = new CropOptions();
            options.startPoint = startPoint;
            options.cutSize = new ImageSize(width, height);
            options.cropMode = cropType;
            options.autoUseAshmem = this.c;
            options.systemCropNew = this.d;
            return ImageDecoder.cropBitmap(data, options).bitmap;
        }

        public Bitmap cutImage_new(File file, int maxLen, int minLen, float scale) {
            return this.b.cutImage_new(file, maxLen, minLen, scale);
        }

        public Bitmap cutImage_new(InputStream in, int maxLen, int minLen, float scale) {
            return this.b.cutImage_new(in, maxLen, minLen, scale);
        }

        public Bitmap cutImage_backgroud(File file, int newWidth, int newHeight) {
            return this.b.cutImage_backgroud(file, newWidth, newHeight);
        }

        public Bitmap cutImage_backgroud(InputStream image, int newWidth, int newHeight) {
            return this.b.cutImage_backgroud(image, newWidth, newHeight);
        }

        public Bitmap cutImage_keepRatio(File file, int newWidth, int newHeight) {
            return this.b.cutImage_keepRatio(file, newWidth, newHeight);
        }

        public Bitmap cutImage_keepRatio(InputStream image, int newWidth, int newHeight) {
            return this.b.cutImage_keepRatio(image, newWidth, newHeight);
        }

        public ByteArrayOutputStream compressImage(File imageFile, int quality, int width, int height) {
            ByteArrayOutputStream baos;
            long start = System.currentTimeMillis();
            FalconFacade.b.d("compressImage " + imageFile.getName() + ";quality;=;" + quality + ";width=" + width + ";height=" + height + ";start at " + start, new Object[0]);
            if (width <= 0 || height <= 0) {
                baos = this.a.d.compressImage(imageFile, quality, 1280, 1280);
            } else {
                baos = this.a.d.compressImage(imageFile, quality, width, height);
            }
            FalconFacade.b.d("compressImage cost time: " + (System.currentTimeMillis() - start), new Object[0]);
            return baos;
        }

        public ByteArrayOutputStream compressImage(byte[] imageData, int quality, int width, int height) {
            ByteArrayInputStream bais = null;
            try {
                ByteArrayInputStream bais2 = new ByteArrayInputStream(imageData);
                try {
                    ByteArrayOutputStream compressImage = compressImage((InputStream) bais2, quality, width, height);
                    IOUtils.closeQuietly((InputStream) bais2);
                    return compressImage;
                } catch (Throwable th) {
                    th = th;
                    bais = bais2;
                    IOUtils.closeQuietly((InputStream) bais);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                IOUtils.closeQuietly((InputStream) bais);
                throw th;
            }
        }

        public ByteArrayOutputStream compressImage(InputStream in, int quality, int width, int height) {
            if (width <= 0 || height <= 0) {
                return this.a.d.compressImage(in, quality, 1280, 1280);
            }
            return this.a.d.compressImage(in, quality, width, height);
        }
    }

    public static FalconFacade get() {
        return a;
    }

    private FalconFacade() {
        boolean z;
        try {
            if (!AppUtils.isDebug(AppUtils.getApplicationContext()) || !new File("/sdcard/debugJni.t").exists()) {
                z = false;
            } else {
                z = true;
            }
            StaticOptions.jniDebug = z;
        } catch (Throwable th) {
            b.w("ignore init error", new Object[0]);
        }
    }

    public void setUseAshmem(boolean useAshmem) {
        Logger.I("FalconFacade", "setUseAshmem useAshmem: " + useAshmem, new Object[0]);
        this.c.setIsUseNewMethod(useAshmem);
        this.d.setIsUseNewMethod(useAshmem);
    }

    public int[] calculateCutImageRect(int width, int height, int maxLen, String path) {
        int[] wh = new int[2];
        try {
            FalconUtilsBridge.calcultDesWidthHeight_new(PathUtils.extractFile(path), width, height, maxLen, wh);
            int maxSide = Math.max(wh[0], wh[1]);
            if (maxSide < maxLen) {
                float newScale = ((float) maxLen) / ((float) maxSide);
                if (wh[0] > wh[1]) {
                    wh[0] = maxLen;
                    wh[1] = (int) (((float) wh[1]) * newScale);
                } else {
                    wh[1] = maxLen;
                    wh[0] = (int) (((float) wh[0]) * newScale);
                }
            }
            b.d("calculateCutImageRect, width: " + width + " height: " + height + ", maxLen: " + maxLen + ", out: " + Arrays.toString(wh), new Object[0]);
        } catch (Exception e) {
            wh[0] = 240;
            wh[1] = 240;
        }
        return wh;
    }

    public int[] calculateCutImageRect(int width, int height, int maxLen, float scale, String path) {
        int[] wh = new int[2];
        try {
            FalconUtilsBridge.calcultDesWidthHeight_new(PathUtils.extractFile(path), width, height, maxLen, scale, wh);
            int maxSide = Math.max(wh[0], wh[1]);
            if (maxSide < maxLen) {
                float newScale = ((float) maxLen) / ((float) maxSide);
                if (wh[0] > wh[1]) {
                    wh[0] = maxLen;
                    wh[1] = (int) (((float) wh[1]) * newScale);
                } else {
                    wh[1] = maxLen;
                    wh[0] = (int) (((float) wh[0]) * newScale);
                }
            }
            b.d("calculateCutImageRect, width: " + width + " height: " + height + ", maxLen: " + maxLen + ", out: " + Arrays.toString(wh), new Object[0]);
        } catch (Exception e) {
            wh[0] = 240;
            wh[1] = 240;
        }
        return wh;
    }

    public int[] calculateDesWidthHeight(String path) {
        int[] wh = new int[2];
        boolean fileDescriptor = null;
        boolean success = false;
        try {
            fileDescriptor = SandboxWrapper.isContentUriPath(path);
            if (fileDescriptor) {
                fileDescriptor = SandboxWrapper.openParcelFileDescriptor(Uri.parse(path));
                if (fileDescriptor != null) {
                    success = FalconUtilsBridge.calcultDesWidthHeight_new(fileDescriptor.getFileDescriptor(), wh);
                }
            } else {
                success = FalconUtilsBridge.calcultDesWidthHeight_new(PathUtils.extractFile(path), wh);
            }
            if (!success) {
                wh = null;
            }
            if (fileDescriptor != null) {
                try {
                    fileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            wh = null;
            if (fileDescriptor != null) {
                try {
                    fileDescriptor.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        } finally {
            if (fileDescriptor != null) {
                try {
                    fileDescriptor.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
        }
        return wh;
    }

    public ByteArrayOutputStream compressImage(File imageFile, int quality, int width, int height) {
        return b().compressImage(imageFile, quality, width, height);
    }

    public ByteArrayOutputStream compressImage(byte[] imageData, int quality, int width, int height) {
        return b().compressImage(imageData, quality, width, height);
    }

    public ByteArrayOutputStream compressImage(InputStream in, int quality, int width, int height) {
        return b().compressImage(in, quality, width, height);
    }

    private FalconInterface b() {
        FalconInterface cut = new FalconInterfaceImpl(this);
        return (FalconInterface) Proxy.newProxyInstance(cut.getClass().getClassLoader(), cut.getClass().getInterfaces(), new FalconImageCutProxy(cut));
    }

    public Bitmap cutImage(File file, Point startPoint, int width, int height, int cropType) {
        return b().cutImage(file, startPoint, width, height, cropType);
    }

    public Bitmap cutDataImage(byte[] data, Point startPoint, int width, int height, int cropType) {
        return b().cutDataImage(data, startPoint, width, height, cropType);
    }

    public Bitmap cutImage(File file, int maxLen, int minLen, float scale) {
        return b().cutImage_new(file, maxLen, minLen, scale);
    }

    public Bitmap cutImage(InputStream in, int maxLen, int minLen, float scale) {
        return b().cutImage_new(in, maxLen, minLen, scale);
    }

    public Bitmap cutImage(byte[] data, int maxLen, int minLen, float scale) {
        if (data == null || data.length <= 0) {
            return null;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        Bitmap bmp = cutImage((InputStream) in, maxLen, minLen, scale);
        IOUtils.closeQuietly((InputStream) in);
        return bmp;
    }

    public Bitmap cutImageBackground(File file, int newWidth, int newHeight) {
        return b().cutImage_backgroud(file, newWidth, newHeight);
    }

    public Bitmap cutImageBackground(InputStream in, int newWidth, int newHeight) {
        return b().cutImage_backgroud(in, newWidth, newHeight);
    }

    public Bitmap cutImageKeepRatio(File file, int newWidth, int newHeight) {
        return b().cutImage_keepRatio(file, newWidth, newHeight);
    }

    public Bitmap cutImageKeepRatio(InputStream in, int newWidth, int newHeight) {
        return b().cutImage_keepRatio(in, newWidth, newHeight);
    }

    public Bitmap cutImageKeepRatio(byte[] data, int newWidth, int newHeight) {
        if (data == null || data.length <= 0) {
            return null;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        Bitmap bitmap = cutImageKeepRatio((InputStream) in, newWidth, newHeight);
        IOUtils.closeQuietly((InputStream) in);
        return bitmap;
    }
}
