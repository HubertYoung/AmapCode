package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.alipay.multimedia.img.base.SoLibLoader;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.streammedia.encode.NativeRecordMuxer;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.picture.NativeEngineApiCreateBitmap;
import com.alipay.streammedia.mmengine.video.VideoInfo;
import com.alipay.streammedia.video.editor.NativeVideoEditor;
import com.alipay.streammedia.video.editor.PickFrameParam;
import com.alipay.streammedia.video.editor.PickFrameResult;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class VideoUtils {
    public static final int BITRATE_1080 = 2048000;
    public static final int BITRATE_320 = 819200;
    public static final int BITRATE_540 = 1126400;
    public static final int BITRATE_720 = 1433600;
    public static final int BITRATE_DEFAULT = 1126400;
    private static volatile boolean a = false;

    public static void loadLibrariesOnce() {
        synchronized (VideoUtils.class) {
            if (!a) {
                try {
                    NativeRecordMuxer.loadLibrariesOnce(new SoLibLoader());
                    a = true;
                } catch (MMNativeException e) {
                    Logger.E((String) "VideoUtils", (Throwable) e, "loadLibrariesOnce exp code=" + e.getCode(), new Object[0]);
                }
            }
        }
    }

    private static Bitmap a(String path, Uri uri, long ts) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (!TextUtils.isEmpty(path)) {
                retriever.setDataSource(path);
            } else {
                retriever.setDataSource(AppUtils.getApplicationContext(), uri);
            }
            Bitmap src = retriever.getFrameAtTime(ts, 3);
            int width = src.getWidth();
            int height = src.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512.0f / ((float) max);
                src = Bitmap.createScaledBitmap(src, Math.round(((float) width) * scale), Math.round(((float) height) * scale), true);
            }
            try {
                return src;
            } catch (RuntimeException ex) {
                Logger.E((String) "VideoUtils", (Throwable) ex, (String) "getVideoFrame release exp", new Object[0]);
                return src;
            }
        } catch (IllegalArgumentException ex2) {
            Logger.E((String) "VideoUtils", (Throwable) ex2, (String) "getVideoFrame exp", new Object[0]);
            try {
            } catch (RuntimeException ex3) {
                Logger.E((String) "VideoUtils", (Throwable) ex3, (String) "getVideoFrame release exp", new Object[0]);
            }
        } catch (Exception ex4) {
            Logger.E((String) "VideoUtils", (Throwable) ex4, (String) "getVideoFrame exp", new Object[0]);
            try {
            } catch (RuntimeException ex5) {
                Logger.E((String) "VideoUtils", (Throwable) ex5, (String) "getVideoFrame release exp", new Object[0]);
            }
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex6) {
                Logger.E((String) "VideoUtils", (Throwable) ex6, (String) "getVideoFrame release exp", new Object[0]);
            }
        }
        return null;
    }

    public static Bitmap getVideoFrame(String path, long ts) {
        return a(path, (Uri) null, ts);
    }

    public static Bitmap getVideoFrameByUri(Uri path, long ts) {
        return a((String) null, path, ts);
    }

    public static Bitmap getVideoFrame2(String path, int frameIdx) {
        try {
            VideoInfo vi = MMNativeEngineApi.getVideoInfo(path);
            PickFrameParam params = new PickFrameParam();
            params.src = path;
            params.width = vi.width;
            params.height = vi.height;
            params.frameIndex = frameIdx;
            params.debugLog = 0;
            Bitmap bitmap = NativeEngineApiCreateBitmap.createBitmap(vi.width, vi.height, false);
            PickFrameResult sr = NativeVideoEditor.pickFrameByIndex(params, bitmap);
            if (sr == null) {
                return null;
            }
            if (sr != null && sr.code != 0) {
                Logger.E("VideoUtils", "getVideoFrame2 falied. code:" + sr.code, new Object[0]);
                return null;
            } else if (sr.rotation == 0) {
                return bitmap;
            } else {
                Logger.E("VideoUtils", "getVideoFrame2 rotate bitmap, rotation:" + sr.rotation, new Object[0]);
                Matrix matrix = new Matrix();
                matrix.postRotate((float) sr.rotation);
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
            }
        } catch (Exception ex) {
            Logger.E((String) "VideoUtils", (Throwable) ex, (String) "getVideoFrame2 exp", new Object[0]);
            return null;
        }
    }

    public static int getVideoRotation(String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            String rotation = retriever.extractMetadata(24);
            if (rotation != null) {
                int intValue = Integer.valueOf(rotation).intValue();
                try {
                    return intValue;
                } catch (RuntimeException ex) {
                    Logger.E((String) "VideoUtils", (Throwable) ex, (String) "getVideoFrame release exp", new Object[0]);
                    return intValue;
                }
            } else {
                try {
                    retriever.release();
                } catch (RuntimeException ex2) {
                    Logger.E((String) "VideoUtils", (Throwable) ex2, (String) "getVideoFrame release exp", new Object[0]);
                }
                return 0;
            }
        } catch (IllegalArgumentException ex3) {
            Logger.E((String) "VideoUtils", (Throwable) ex3, (String) "getVideoFrame exp", new Object[0]);
            try {
            } catch (RuntimeException ex4) {
                Logger.E((String) "VideoUtils", (Throwable) ex4, (String) "getVideoFrame release exp", new Object[0]);
            }
        } catch (RuntimeException ex5) {
            Logger.E((String) "VideoUtils", (Throwable) ex5, (String) "getVideoFrame exp", new Object[0]);
            try {
            } catch (RuntimeException ex6) {
                Logger.E((String) "VideoUtils", (Throwable) ex6, (String) "getVideoFrame release exp", new Object[0]);
            }
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex7) {
                Logger.E((String) "VideoUtils", (Throwable) ex7, (String) "getVideoFrame release exp", new Object[0]);
            }
        }
    }

    public static int convertMuxToRspCode(int ret) {
        if (-110 == ret || -32 == ret || -104 == ret) {
            return 8;
        }
        return 7;
    }

    public static int convertMuxInitToRspCode(int ret) {
        if (2 == ret) {
            return 8;
        }
        if (1 == ret) {
            return 7;
        }
        if (ret == 0) {
            return 0;
        }
        return 9;
    }

    public static String generateEagleId() {
        return String.format("%s%d%d%s", new Object[]{String.format("%02x%02x%02x%02x", new Object[]{Integer.valueOf(CommonUtils.generateRandom(1, 255)), Integer.valueOf(CommonUtils.generateRandom(1, 255)), Integer.valueOf(CommonUtils.generateRandom(1, 255)), Integer.valueOf(CommonUtils.generateRandom(1, 255))}), Long.valueOf(System.currentTimeMillis()), Integer.valueOf(CommonUtils.generateRandom(1000, 9999)), "e"});
    }

    public static float[] getCameraCropCoord(float[] input, Size size) {
        float[] tmpTextureCoord = new float[input.length];
        int count = tmpTextureCoord.length;
        if (size.width * 9 <= size.height * 16) {
            float delta = ((float) (size.height - ((size.width * 9) / 16))) / 2.0f;
            for (int i = 0; i < count; i++) {
                float fl = input[i];
                if (i == 0 || i == 4) {
                    fl = delta / ((float) size.height);
                }
                if (i == 2 || i == 6) {
                    fl = (((float) size.height) - delta) / ((float) size.height);
                }
                tmpTextureCoord[i] = fl;
            }
        } else {
            float delta2 = ((float) (size.width - ((size.height * 16) / 9))) / 2.0f;
            for (int i2 = 0; i2 < count; i2++) {
                float fl2 = input[i2];
                if (i2 == 1 || i2 == 3) {
                    fl2 = delta2 / ((float) size.width);
                }
                if (i2 == 5 || i2 == 7) {
                    fl2 = (((float) size.width) - delta2) / ((float) size.width);
                }
                tmpTextureCoord[i2] = fl2;
            }
        }
        return tmpTextureCoord;
    }

    public static void swapCameraTextureCoord(float[] left, float[] right) {
        a(0, left, right);
        a(2, left, right);
        a(4, left, right);
        a(6, left, right);
    }

    private static void a(int index, float[] left, float[] right) {
        float tmpValue = left[index];
        left[index] = right[index];
        right[index] = tmpValue;
    }

    public static APVideoInfo parseVideoInfo(String path) {
        APVideoInfo info = null;
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        VideoInfo fFmpegVideoInfo = getVideoInfo(path);
        if (fFmpegVideoInfo != null) {
            info = new APVideoInfo();
            info.width = fFmpegVideoInfo.width;
            info.height = fFmpegVideoInfo.height;
            info.duration = fFmpegVideoInfo.duration;
            info.rotation = fFmpegVideoInfo.rotation;
            a(info);
        }
        return info;
    }

    private static void a(APVideoInfo info) {
        if (info != null) {
            int absRotation = Math.abs(info.rotation);
            if (absRotation == 90 || absRotation == 270) {
                info.correctWidth = info.height;
                info.correctHeight = info.width;
                return;
            }
            info.correctWidth = info.width;
            info.correctHeight = info.height;
        }
    }

    /* JADX INFO: finally extract failed */
    public static VideoInfo getVideoInfo(String path) {
        loadLibrariesOnce();
        ParcelFileDescriptor pfd = null;
        String sourcePath = path;
        try {
            if (SandboxWrapper.isContentUriPath(path)) {
                pfd = SandboxWrapper.openParcelFileDescriptor(Uri.parse(path));
                if (pfd != null) {
                    int fd = pfd.getFd();
                    if (fd > 0) {
                        sourcePath = PathUtils.genPipePath(fd);
                    }
                }
                Logger.D("VideoUtils", "getVideoInfo path=" + sourcePath, new Object[0]);
            }
            VideoInfo videoInfo = MMNativeEngineApi.getVideoInfo(sourcePath);
            IOUtils.closeQuietly(pfd);
            return videoInfo;
        } catch (MMNativeException e) {
            Logger.E((String) "VideoUtils", (Throwable) e, "getVideoInfo exp code=" + e.getCode(), new Object[0]);
            IOUtils.closeQuietly((ParcelFileDescriptor) null);
            return null;
        } catch (Throwable th) {
            IOUtils.closeQuietly((ParcelFileDescriptor) null);
            throw th;
        }
    }

    public static boolean parseVideoInfoByMediaMeta(String path, APVideoInfo outVideoInfo) {
        boolean ret = false;
        if (!TextUtils.isEmpty(path)) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                retriever.setDataSource(PathUtils.extractPath(path));
                outVideoInfo.rotation = a(retriever.extractMetadata(24), 0);
                outVideoInfo.width = a(retriever.extractMetadata(18), -1);
                outVideoInfo.height = a(retriever.extractMetadata(19), -1);
                outVideoInfo.duration = a(retriever.extractMetadata(9), -1);
                if (outVideoInfo.width == -1 || outVideoInfo.height == -1 || outVideoInfo.duration == -1) {
                    ret = false;
                } else {
                    ret = true;
                }
                if (ret) {
                    a(outVideoInfo);
                }
                try {
                } catch (RuntimeException ex) {
                    Logger.E((String) "VideoUtils", (Throwable) ex, (String) "getVideoFrame release exp", new Object[0]);
                }
            } catch (Exception ex2) {
                Logger.E((String) "VideoUtils", (Throwable) ex2, (String) "getVideoFrame exp", new Object[0]);
                try {
                } catch (RuntimeException ex3) {
                    Logger.E((String) "VideoUtils", (Throwable) ex3, (String) "getVideoFrame release exp", new Object[0]);
                }
            } finally {
                try {
                    retriever.release();
                } catch (RuntimeException ex4) {
                    Logger.E((String) "VideoUtils", (Throwable) ex4, (String) "getVideoFrame release exp", new Object[0]);
                }
            }
        }
        return ret;
    }

    private static int a(String val, int defVal) {
        return TextUtils.isEmpty(val) ? defVal : Integer.parseInt(val);
    }

    public static Point getScreenSize(Context context) {
        Display display = ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
        Point screenSize = new Point();
        if (VERSION.SDK_INT >= 17) {
            display.getRealSize(screenSize);
        } else {
            display.getSize(screenSize);
        }
        return screenSize;
    }

    public static boolean isActivityLandscape(Activity context) {
        int orientation = context.getRequestedOrientation();
        return orientation == 0 || orientation == 8 || orientation == 6 || orientation == 11;
    }

    public static boolean previewRunning(Camera camera) {
        if (VERSION.SDK_INT >= 27) {
            return true;
        }
        boolean enable = true;
        try {
            enable = ((Boolean) camera.getClass().getDeclaredMethod("previewEnabled", new Class[0]).invoke(camera, new Object[0])).booleanValue();
        } catch (Exception e) {
            Logger.E((String) "VideoUtils", (Throwable) e, "previewEnabled exception:" + e.getMessage(), new Object[0]);
        }
        Logger.D("VideoUtils", "previewRunning enable: " + enable, new Object[0]);
        return enable;
    }

    public static boolean supportGles30(Context context) {
        boolean supportsEs3;
        ConfigurationInfo configurationInfo = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getDeviceConfigurationInfo();
        if (configurationInfo.reqGlEsVersion >= 196608) {
            supportsEs3 = true;
        } else {
            supportsEs3 = false;
        }
        Logger.D("VideoUtils", "reqGlEsVersion: 0x" + Integer.toHexString(configurationInfo.reqGlEsVersion), new Object[0]);
        return supportsEs3;
    }

    public static Point findBestPreviewSizeValue(Parameters parameters, Point screenResolution) {
        int maybeFlippedWidth;
        int maybeFlippedHeight;
        List<Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        if (supportedPreviewSizes == null) {
            Size defaultSize = parameters.getPreviewSize();
            if (defaultSize == null) {
                throw new IllegalStateException("Parameters contained no preview size!");
            }
            return new Point(defaultSize.width, defaultSize.height);
        }
        ArrayList<Size> arrayList = new ArrayList<>(supportedPreviewSizes);
        Collections.sort(arrayList, new Comparator<Size>() {
            public final int compare(Size a, Size b) {
                int aPixels = a.height * a.width;
                int bPixels = b.height * b.width;
                if (bPixels < aPixels) {
                    return -1;
                }
                if (bPixels > aPixels) {
                    return 1;
                }
                return 0;
            }
        });
        StringBuilder previewSizesString = new StringBuilder();
        for (Size supportedPreviewSize : arrayList) {
            previewSizesString.append(supportedPreviewSize.width).append('x').append(supportedPreviewSize.height).append(' ');
        }
        Logger.I("VideoUtils", "Supported preview sizes: " + previewSizesString, new Object[0]);
        double screenAspectRatio = ((double) screenResolution.x) / ((double) screenResolution.y);
        if (!(screenAspectRatio < 1.0d)) {
            screenAspectRatio = 1.0d / screenAspectRatio;
        }
        Logger.I("VideoUtils", "screen size:" + screenResolution.x + DictionaryKeys.CTRLXY_X + screenResolution.y, new Object[0]);
        Point bestSize = null;
        double diff = Double.POSITIVE_INFINITY;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Size supportedPreviewSize2 = (Size) it.next();
            int realWidth = supportedPreviewSize2.width;
            int realHeight = supportedPreviewSize2.height;
            int pixels = realWidth * realHeight;
            if (pixels < 153600 || pixels > 1536000) {
                it.remove();
            } else {
                boolean isCandidatePortrait = realWidth > realHeight;
                if (isCandidatePortrait) {
                    maybeFlippedWidth = realHeight;
                } else {
                    maybeFlippedWidth = realWidth;
                }
                if (isCandidatePortrait) {
                    maybeFlippedHeight = realWidth;
                } else {
                    maybeFlippedHeight = realHeight;
                }
                if (maybeFlippedWidth == screenResolution.x && maybeFlippedHeight == screenResolution.y) {
                    Point exactPoint = new Point(realWidth, realHeight);
                    Logger.I("VideoUtils", "Found preview size exactly matching screen size: " + exactPoint, new Object[0]);
                    return exactPoint;
                }
                double distortion = Math.abs((((double) maybeFlippedWidth) / ((double) maybeFlippedHeight)) - screenAspectRatio);
                if (distortion < diff) {
                    bestSize = new Point(realWidth, realHeight);
                    Logger.I("VideoUtils", "new update bestSize: " + bestSize + " --- diff = " + diff + " --- newDiff= " + distortion, new Object[0]);
                    diff = distortion;
                }
            }
        }
        if (bestSize == null) {
            Size defaultPreview = parameters.getPreviewSize();
            if (defaultPreview == null) {
                throw new IllegalStateException("Parameters contained no preview size!");
            }
            Point defaultSize2 = new Point(defaultPreview.width, defaultPreview.height);
            Logger.I("VideoUtils", "No suitable preview sizes, using default: " + defaultSize2, new Object[0]);
            Logger.I("VideoUtils", "default previewSize: " + defaultPreview.width + "," + defaultPreview.height, new Object[0]);
            return defaultSize2;
        }
        Logger.I("VideoUtils", "bestSize is not null: " + bestSize, new Object[0]);
        return bestSize;
    }

    public static Point calculatePoint(int x, int y, int view_w, int view_h, int graphic_w, int graphic_h) {
        float graphic_x;
        float graphic_y;
        if (graphic_w * view_h > view_w * graphic_h) {
            graphic_y = (float) ((y * graphic_h) / view_h);
            graphic_x = ((float) ((x * graphic_h) / view_h)) + (((float) (graphic_w - ((graphic_h * view_w) / view_h))) / 2.0f);
        } else {
            graphic_x = (float) ((x * graphic_w) / view_w);
            graphic_y = ((float) ((y * graphic_w) / view_w)) + (((float) (graphic_h - ((graphic_w * view_h) / view_w))) / 2.0f);
        }
        return new Point((int) graphic_x, (int) graphic_y);
    }

    public static int getTargetBitrate(int videoBitrate, int levelVideoBitrate) {
        return (videoBitrate <= 0 || videoBitrate >= levelVideoBitrate) ? levelVideoBitrate : videoBitrate;
    }

    public static int[] compareSize(int srcW, int srcH, int destMin) {
        int[] destSize = {srcW, srcH};
        if (Math.min(srcW, srcH) > destMin) {
            if (srcW < srcH) {
                destSize[0] = destMin;
                destSize[1] = (destMin * srcH) / srcW;
            } else {
                destSize[0] = (destMin * srcW) / srcH;
                destSize[1] = destMin;
            }
        }
        return destSize;
    }
}
