package com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.processor;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APTakePictureOption;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APTakePicRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.APTakePictureListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.TakePictureListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.TakePictureMPListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CacheUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Exif;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.OrientationDetector;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import tv.danmaku.ijk.media.encode.CameraHelper;

public class TakePictureProcessor {
    public static int DEFAULT_PICTURE_MIN_HEIGHT = 0;
    protected static final String TAG = "TakePictureProcessor";
    private int a = DEFAULT_PICTURE_MIN_HEIGHT;
    protected int mCameraFacing;

    static {
        DEFAULT_PICTURE_MIN_HEIGHT = 1280;
        if (!"samsung".equalsIgnoreCase(Build.MANUFACTURER) || (!"SM-G9500".equalsIgnoreCase(Build.MODEL) && !"SM-G9550".equalsIgnoreCase(Build.MODEL) && !"SM-G9508".equalsIgnoreCase(Build.MODEL))) {
            DEFAULT_PICTURE_MIN_HEIGHT = 1280;
        } else {
            DEFAULT_PICTURE_MIN_HEIGHT = 1920;
        }
    }

    public void takePicture(Camera camera, int cameraId, TakePictureListener listener, Looper looper, CameraParams params, APTakePictureOption option) {
        final CameraParams finalParams;
        Logger.D(TAG, "takePicture start looper: " + looper + ", params: " + params, new Object[0]);
        if (option != null) {
            Logger.D(TAG, "takePicture start looper, option: " + option, new Object[0]);
        }
        final long start = System.currentTimeMillis();
        final Handler handler = new Handler(selectLooper(looper));
        if (params == null) {
            finalParams = new CameraParams();
        } else {
            finalParams = params;
        }
        final Size pictureSize = setupPictureParams(camera, cameraId, params);
        final int orientation = OrientationDetector.getInstance(AppUtils.getApplicationContext()).getDevOrientation();
        final APTakePictureOption aPTakePictureOption = option;
        final TakePictureListener takePictureListener = listener;
        camera.takePicture(null, null, new PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                if (!(aPTakePictureOption == null || !aPTakePictureOption.isKeepPreview() || camera == null)) {
                    camera.startPreview();
                }
                if (data == null) {
                    Logger.E(TakePictureProcessor.TAG, "onPictureTaken data is empty!!!", new Object[0]);
                    TakePictureProcessor.this.notifyTakenPictureError(camera, handler, takePictureListener);
                } else if (aPTakePictureOption.getDataType() == 1) {
                    TakePictureProcessor.this.notifyTakenPictureData(data, camera, handler, takePictureListener);
                } else {
                    Logger.D(TakePictureProcessor.TAG, "onPictureTaken start to doPictureProcess size=" + pictureSize, new Object[0]);
                    TakePictureProcessor.this.doPictureProcess(camera, handler, takePictureListener, data, finalParams, pictureSize, orientation, aPTakePictureOption);
                }
                Logger.D(TakePictureProcessor.TAG, "onPictureTaken cost: " + (System.currentTimeMillis() - start), new Object[0]);
            }
        });
    }

    /* access modifiers changed from: protected */
    public Size setupPictureParams(Camera camera, int cameraId, CameraParams params) {
        Size pictureSize;
        if (params.exif != null) {
            String height = params.exif.get("minPictureHeight");
            if (!TextUtils.isEmpty(height)) {
                Logger.I(TAG, "minPictureHeight set to " + height, new Object[0]);
                this.a = Integer.valueOf(height).intValue();
            }
        }
        if (params.getPictureSize() == null) {
            pictureSize = CameraHelper.getSuggestPictureSize(camera, this.a);
        } else {
            pictureSize = params.getPictureSize();
        }
        Logger.D(TAG, "takePicture pictureSize: [width:" + pictureSize.width + ",\theight:" + pictureSize.height + "]", new Object[0]);
        Parameters parameters = camera.getParameters();
        parameters.setPictureSize(pictureSize.width, pictureSize.height);
        camera.setParameters(parameters);
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);
        this.mCameraFacing = cameraInfo.facing;
        return pictureSize;
    }

    /* access modifiers changed from: protected */
    public void doPictureProcess(Camera camera, Handler handler, TakePictureListener listener, byte[] data, CameraParams params, Size pictureSize, int orientation, APTakePictureOption option) {
        if (!params.isConvertPicture() || this.mCameraFacing != 1) {
            saveCommonTakePicture(handler, listener, data, pictureSize, params, orientation, option);
        } else {
            a(handler, listener, data, pictureSize, params, orientation, option);
        }
    }

    /* access modifiers changed from: protected */
    public File getSaveFile(APTakePictureOption option) {
        if (option == null || !option.saveToPrivateDir) {
            return FileUtils.makeTakenPicturePath();
        }
        return FileUtils.makeTakenPicturePrivatePath();
    }

    /* access modifiers changed from: protected */
    public Bitmap convertPicture(Bitmap bitmap, byte[] jpegData, Size pictureSize, CameraParams params) {
        try {
            long start = System.currentTimeMillis();
            int orientation = Exif.getOrientation(jpegData);
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            Matrix matrix = new Matrix();
            int[] fixedSize = {w, h};
            Logger.D(TAG, "convertPicture w: " + w + ", h: " + h + ", picSize w: " + pictureSize.width + ", h: " + pictureSize.height + ", fixed w: " + fixedSize[0] + ", h: " + fixedSize[1] + ", rotation: " + orientation + ", facing: " + this.mCameraFacing, new Object[0]);
            if (this.mCameraFacing == 1 && params.isConvertPicture()) {
                Logger.D(TAG, "convertPicture doMirror", new Object[0]);
                matrix.postScale(-1.0f, 1.0f);
            }
            if (!(fixedSize[0] == pictureSize.height && fixedSize[1] == pictureSize.width)) {
                float scale = ImageUtils.getScale(pictureSize.height, pictureSize.width, (float) fixedSize[0], (float) fixedSize[1]);
                Logger.D(TAG, "convertPicture before  fixScale: " + scale, new Object[0]);
                float scale2 = a(scale, pictureSize.height, pictureSize.width, fixedSize[0], fixedSize[1]);
                matrix.postScale(scale2, scale2);
                Logger.D(TAG, "convertPicture doScale scale: " + scale2, new Object[0]);
            }
            Bitmap convertBmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
            Logger.D(TAG, "convert bitmap result:" + convertBmp.getWidth() + DictionaryKeys.CTRLXY_X + convertBmp.getHeight() + ", cost:" + (System.currentTimeMillis() - start), new Object[0]);
            return convertBmp;
        } catch (Throwable t) {
            Logger.E((String) TAG, t, (String) "convert bitmap error", new Object[0]);
            return null;
        }
    }

    private static float a(float scale, int targetWidth, int targetHeight, int srcWidth, int srcHeight) {
        float decScale = (float) (1.0d / Math.pow(10.0d, (double) (String.valueOf(Math.min(targetWidth, targetHeight)).length() - 1)));
        Logger.D(TAG, "fixScale decScale: " + decScale, new Object[0]);
        while (true) {
            if (a(scale, srcWidth) && a(scale, srcHeight)) {
                return scale;
            }
            scale -= decScale;
        }
    }

    private static boolean a(float scale, int val) {
        return Math.round(((float) val) * scale) % 2 == 0;
    }

    /* access modifiers changed from: protected */
    public String savePicture(byte[] data, Size pictureSize, CameraParams params, int orientation, APTakePictureOption option) {
        Logger.D(TAG, "savePicture data: " + data + ", picSize: " + pictureSize + ", params: " + params + ", orientation: " + orientation, new Object[0]);
        String outPath = null;
        if (data == null) {
            return outPath;
        }
        try {
            return savePicture(FalconFacade.get().cutImageKeepRatio(data, pictureSize.height, pictureSize.width), orientation, (Point) null, params, option);
        } catch (Throwable t) {
            Logger.W(TAG, "falcon decode bitmap error, " + t + ", save normal", new Object[0]);
            File savePath = getSaveFile(option);
            try {
                if (FileUtils.safeCopyToFile(data, savePath)) {
                    return savePath.getAbsolutePath();
                }
                return outPath;
            } catch (IOException e) {
                Logger.E((String) TAG, (Throwable) e, (String) "savePicture error", new Object[0]);
                return outPath;
            }
        }
    }

    private static void a(Bitmap bitmap, String path) {
        try {
            CacheContext.get().getMemCache().put(CacheUtils.makeImageCacheKey(null, path, Integer.MAX_VALUE, Integer.MAX_VALUE, CutScaleType.NONE, null), bitmap);
        } catch (Exception e) {
            Logger.W(TAG, "saveToCache error, e: " + e, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public String savePicture(Bitmap bitmap, int orientation, Point outSize, CameraParams params, APTakePictureOption option) {
        int max;
        float scale;
        String outPath = null;
        File savePath = getSaveFile(option);
        Logger.D(TAG, "savePicture path: " + savePath + ", checkBitmap: " + ImageUtils.checkBitmap(bitmap), new Object[0]);
        try {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            Logger.D(TAG, "picture w,h:" + w + "," + h, new Object[0]);
            if (params.getPictureSize() == null) {
                if (w > h) {
                    max = w;
                } else {
                    max = h;
                }
                if (max > this.a) {
                    Matrix matrix = new Matrix();
                    if (max == h) {
                        scale = (1.0f * ((float) this.a)) / ((float) h);
                    } else {
                        scale = (1.0f * ((float) this.a)) / ((float) w);
                    }
                    matrix.postScale(scale, scale);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                }
            }
            if (params.mActivityRotation >= 0) {
                Matrix matrix2 = new Matrix();
                matrix2.postRotate((float) params.mActivityRotation);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix2, true);
            } else if (orientation != 0 && params.autoRotateTakenPicture && ImageUtils.checkBitmap(bitmap)) {
                Matrix matrix3 = new Matrix();
                matrix3.postRotate((float) orientation);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix3, true);
            }
            if (!(params.mCropRect == null || params.mSrcRect == null || !ImageUtils.checkBitmap(bitmap))) {
                float scaleX = (((float) bitmap.getWidth()) * 1.0f) / ((float) (params.mSrcRect.right - params.mSrcRect.left));
                float scaleY = (((float) bitmap.getHeight()) * 1.0f) / ((float) (params.mSrcRect.bottom - params.mSrcRect.top));
                int cropW = (int) (((float) (params.mCropRect.right - params.mCropRect.left)) * scaleX);
                int cropH = (int) (((float) (params.mCropRect.bottom - params.mCropRect.top)) * scaleY);
                int left = (int) (((float) params.mCropRect.left) * scaleX);
                int top = (int) (((float) params.mCropRect.top) * scaleY);
                Logger.I(TAG, "bitmap crop rect: left:" + left + ", top:" + top + ", w:" + cropW + "h:" + cropH, new Object[0]);
                bitmap = Bitmap.createBitmap(bitmap, left, top, cropW, cropH);
            }
            a(orientation, params);
            if (ImageUtils.compressJpg(bitmap, savePath.getAbsolutePath(), option == null ? 100 : option.getQuality())) {
                outPath = savePath.getAbsolutePath();
                a(bitmap, outPath);
                a(outPath, params.exif);
                Logger.E(TAG, "double check picture, outPath.exists(): " + savePath.exists() + ", length: " + savePath.length() + ", modifyTime: " + savePath.lastModified(), new Object[0]);
            }
            if (outSize != null) {
                outSize.x = bitmap.getWidth();
                outSize.y = bitmap.getHeight();
            }
        } catch (Exception e) {
            Logger.E((String) TAG, (Throwable) e, (String) "savePicture error", new Object[0]);
        }
        return outPath;
    }

    private static void a(int orientation, CameraParams params) {
        if (orientation != 0 && !params.autoRotateTakenPicture) {
            if (params.exif == null) {
                params.exif = new HashMap();
            }
            switch (orientation) {
                case 90:
                    params.exif.put("Orientation", "6");
                    return;
                case 180:
                    params.exif.put("Orientation", "3");
                    return;
                case 270:
                    params.exif.put("Orientation", "8");
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyProcessPictureError(Handler handler, final TakePictureListener listener, final int errorCode, final byte[] data) {
        handler.post(new Runnable() {
            public void run() {
                listener.onPictureProcessError(errorCode, data);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void notifyTakenPictureError(final Camera camera, Handler handler, final TakePictureListener listener) {
        handler.post(new Runnable() {
            public void run() {
                listener.onPictureTakenError(1, camera);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void notifyTakenPictureData(final byte[] data, Camera camera, Handler handler, final TakePictureListener listener) {
        handler.post(new Runnable() {
            public void run() {
                if (listener instanceof APTakePictureListener) {
                    APTakePicRsp rsp = new APTakePicRsp();
                    rsp.data = data;
                    rsp.dataType = 1;
                    ((APTakePictureListener) listener).onPictureProcessFinish(rsp);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void notifyTakenPictureBitmap(Bitmap bitmap, Handler handler, TakePictureListener listener, int orientation, byte[] data) {
        final Bitmap bitmap2 = bitmap;
        final TakePictureListener takePictureListener = listener;
        final byte[] bArr = data;
        final int i = orientation;
        handler.post(new Runnable() {
            public void run() {
                if (!ImageUtils.checkBitmap(bitmap2)) {
                    takePictureListener.onPictureProcessError(2, bArr);
                } else if (takePictureListener instanceof APTakePictureListener) {
                    APTakePicRsp rsp = new APTakePicRsp();
                    rsp.bitmap = bitmap2;
                    rsp.orientation = i;
                    rsp.dataType = 2;
                    ((APTakePictureListener) takePictureListener).onPictureProcessFinish(rsp);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void notifyProcessFinished(String savePath, ImageInfo info, Handler handler, TakePictureListener listener) {
        UCLogUtil.UC_MM_C49(FileUtils.checkFile(savePath), savePath);
        if (info == null) {
            notifyProcessPictureError(handler, listener, 103, null);
            return;
        }
        long size = 0;
        if (listener instanceof TakePictureMPListener) {
            try {
                size = new File(savePath).length();
            } catch (Throwable e) {
                Logger.D(TAG, "notifyProcessFinished get file size error,  " + e.toString(), new Object[0]);
            }
        }
        final long fileSize = size;
        final String str = savePath;
        final ImageInfo imageInfo = info;
        final TakePictureListener takePictureListener = listener;
        handler.post(new Runnable() {
            public void run() {
                Logger.D(TakePictureProcessor.TAG, "notifyProcessFinished notify save process picture success, path: " + str + ", info: " + imageInfo, new Object[0]);
                if (takePictureListener instanceof TakePictureMPListener) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("picSize", fileSize);
                    ((TakePictureMPListener) takePictureListener).onPictureProcessFinish(str, imageInfo.width, imageInfo.height, imageInfo.orientation, bundle);
                } else if (takePictureListener instanceof APTakePictureListener) {
                    APTakePicRsp rsp = new APTakePicRsp();
                    rsp.dataType = 0;
                    rsp.savePath = str;
                    rsp.width = imageInfo.width;
                    rsp.height = imageInfo.height;
                    rsp.orientation = imageInfo.orientation;
                    ((APTakePictureListener) takePictureListener).onPictureProcessFinish(rsp);
                } else {
                    takePictureListener.onPictureProcessFinish(str, imageInfo.width, imageInfo.height, imageInfo.orientation);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public Looper selectLooper(Looper looper) {
        return looper == null ? Looper.getMainLooper() : looper;
    }

    /* access modifiers changed from: protected */
    public boolean saveCommonTakePicture(Handler handler, TakePictureListener listener, byte[] data, Size pictureSize, CameraParams params, int orientation, APTakePictureOption option) {
        Logger.D(TAG, "saveCommonTakePicture", new Object[0]);
        final APTakePictureOption aPTakePictureOption = option;
        final byte[] bArr = data;
        final Size size = pictureSize;
        final Handler handler2 = handler;
        final TakePictureListener takePictureListener = listener;
        final int i = orientation;
        final CameraParams cameraParams = params;
        TaskScheduleManager.get().execute(new Runnable() {
            public void run() {
                if (aPTakePictureOption.getDataType() == 2) {
                    Bitmap bitmap = null;
                    if (bArr != null) {
                        try {
                            bitmap = FalconFacade.get().cutImageKeepRatio(bArr, size.height, size.width);
                        } catch (Throwable e2) {
                            Logger.E((String) TakePictureProcessor.TAG, e2, (String) "saveCommonTakePicture exp", new Object[0]);
                        }
                    }
                    TakePictureProcessor.this.notifyTakenPictureBitmap(bitmap, handler2, takePictureListener, i, bArr);
                    return;
                }
                String savePath = TakePictureProcessor.this.savePicture(bArr, size, cameraParams, i, aPTakePictureOption);
                ImageInfo info = ImageInfo.getImageInfo(savePath);
                if (TextUtils.isEmpty(savePath) || info == null) {
                    TakePictureProcessor.this.notifyProcessPictureError(handler2, takePictureListener, 103, bArr);
                } else {
                    TakePictureProcessor.this.notifyProcessFinished(savePath, info, handler2, takePictureListener);
                }
            }
        });
        return true;
    }

    private void a(Handler handler, TakePictureListener listener, byte[] data, Size pictureSize, CameraParams params, int orientation, APTakePictureOption option) {
        String savePath = null;
        try {
            Logger.D(TAG, "falconFacade decode picture, pictureSize: [" + pictureSize.height + DictionaryKeys.CTRLXY_X + pictureSize.width + "]", new Object[0]);
            Bitmap jpeg = convertPicture(FalconFacade.get().cutImageKeepRatio(data, pictureSize.height, pictureSize.width), data, pictureSize, params);
            if (option.getDataType() == 2) {
                notifyTakenPictureBitmap(jpeg, handler, listener, orientation, data);
                return;
            }
            savePath = savePicture(jpeg, orientation, (Point) null, params, option);
            if (savePath != null) {
                notifyProcessFinished(savePath, ImageInfo.getImageInfo(savePath), handler, listener);
            } else {
                notifyProcessPictureError(handler, listener, 103, data);
            }
        } catch (Throwable e) {
            Logger.E((String) TAG, e, (String) "process mirror error", new Object[0]);
        }
    }

    private static void a(String path, Map<String, String> exif) {
        Logger.D(TAG, "addExif path: " + path + ", exif: " + exif, new Object[0]);
        if (exif != null && !exif.isEmpty() && FileUtils.checkFile(path)) {
            try {
                ExifInterface exifInterface = new ExifInterface(path);
                for (Entry entry : exif.entrySet()) {
                    exifInterface.setAttribute((String) entry.getKey(), (String) entry.getValue());
                }
                exifInterface.saveAttributes();
            } catch (Exception e) {
                Logger.E((String) TAG, (Throwable) e, "addExif error, path: " + path + ", exif: " + exif, new Object[0]);
            }
        }
    }
}
