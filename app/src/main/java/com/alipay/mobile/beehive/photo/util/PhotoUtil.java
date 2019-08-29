package com.alipay.mobile.beehive.photo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.beehive.video.views.OriVideoPreviewCon;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.multimedia.adjuster.api.APMSandboxProcessor;
import com.alipay.multimedia.adjuster.api.data.APMInsertReq.Builder;
import com.alipay.sdk.util.e;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PhotoUtil {
    public static final float DEFAULT_HEIGHT_SCALE = 0.5625f;
    public static final float MAX_HEIGHT_SCALE = 1.3333334f;
    public static final int MAX_WIDTH = 800;
    public static final float MIN_HEIGHT_SCALE = 0.18518518f;
    public static final String TAG = "PhotoUtil";
    private static String sDefaultFolder;

    public static boolean isPowerOfTwo(int num) {
        boolean z = true;
        boolean z2 = num > 0;
        if (((num - 1) & num) != 0) {
            z = false;
        }
        return z & z2;
    }

    public static void startAnimation(View view, int res) {
        view.startAnimation(AnimationUtils.loadAnimation(view.getContext(), res));
    }

    public static final boolean savePhoto(PhotoInfo photoInfo, String dstFolder, Drawable drawable) {
        long time = System.currentTimeMillis();
        String photoUrl = photoInfo.getPhotoPath();
        if (TextUtils.isEmpty(photoUrl)) {
            PhotoLogger.info(TAG, "empty photo path!");
            return false;
        } else if (createDirIfNotExist(dstFolder) == null) {
            PhotoLogger.warn((String) TAG, (String) "Create dest dir failed.");
            return false;
        } else if (photoInfo.getPhotoMark() != null) {
            return isSaveMarkedPhotoSuccess(dstFolder, drawable);
        } else {
            String cachePath = getMediaCachePath(photoInfo);
            if (TextUtils.isEmpty(cachePath)) {
                return onCachePathEmpty(dstFolder, drawable, photoUrl);
            }
            String dstPath = createPhoto(dstFolder, photoInfo.isVideo(), photoInfo.isGif());
            if (TextUtils.isEmpty(dstPath)) {
                PhotoLogger.warn((String) TAG, (String) "Create imageFile failed.");
                return false;
            }
            boolean copyFile = copyFile(cachePath, dstPath, photoInfo.isVideo(), photoInfo.isGif());
            if (!photoInfo.isVideo() && !photoInfo.isGif()) {
                notifyScanner(dstPath);
            }
            PhotoLogger.debug(TAG, "savePhoto delta " + (System.currentTimeMillis() - time));
            return copyFile;
        }
    }

    private static boolean onCachePathEmpty(String dstFolder, Drawable drawable, String photoUrl) {
        PhotoLogger.debug(TAG, photoUrl + " not cached!");
        if (drawable == null) {
            return false;
        }
        ((BitmapDrawable) drawable).getBitmap();
        return saveBitmap(((BitmapDrawable) drawable).getBitmap(), dstFolder, System.currentTimeMillis() + ".jpg");
    }

    private static boolean isSaveMarkedPhotoSuccess(String dstFolder, Drawable drawable) {
        if (drawable != null) {
            ((BitmapDrawable) drawable).getBitmap();
            boolean isSuccess = saveBitmap(((BitmapDrawable) drawable).getBitmap(), dstFolder, System.currentTimeMillis() + ".jpg");
            PhotoLogger.info(TAG, "save marked photo success ? " + (isSuccess ? "success" : e.b));
            return isSuccess;
        }
        PhotoLogger.info(TAG, "save marked photo success ? " + "failed,drawable = null");
        return false;
    }

    public static void reportSaveFailed() {
        try {
            Behavor unUsable = new Behavor();
            unUsable.setAppID("20000222");
            unUsable.addExtParam("savePhotoResult", e.b);
            unUsable.setUserCaseID("beephotobrowser_savephoto");
            LoggerFactory.getBehavorLogger().event(null, unUsable);
        } catch (Exception e) {
            PhotoLogger.debug(TAG, "reportSaveFailed exception:" + e.getMessage());
        }
    }

    private static boolean saveBitmap(Bitmap bm, String savePath, String fileName) {
        if (bm == null || bm.isRecycled()) {
            return false;
        }
        boolean isSuccess = false;
        File f = new File(savePath, fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            isSuccess = true;
            notifyScanner(f.getAbsolutePath());
            return true;
        } catch (FileNotFoundException e) {
            PhotoLogger.info(TAG, "saveBitmap Failed,exception:" + e.getMessage());
            return isSuccess;
        } catch (IOException e2) {
            PhotoLogger.info(TAG, "saveBitmap Failed,exception:" + e2.getMessage());
            return isSuccess;
        } catch (Exception e3) {
            PhotoLogger.info(TAG, "saveBitmap Failed,exception:" + e3.getMessage());
            return isSuccess;
        }
    }

    public static String getMediaCachePath(PhotoInfo photoInfo) {
        String photoUrlOrId = photoInfo.getPhotoPath();
        if (photoInfo.getMediaType() == 0) {
            return ImageHelper.getCachePath(photoUrlOrId, photoInfo.isGif());
        }
        if (!photoInfo.isVideo()) {
            return null;
        }
        MultimediaVideoService mediaService = (MultimediaVideoService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaVideoService.class.getName());
        if (mediaService.isVideoAvailable(photoUrlOrId)) {
            String path = Uri.parse(photoUrlOrId).getPath();
            File cacheFile = new File(path);
            if (!cacheFile.exists() || !cacheFile.isFile()) {
                return mediaService.getVideoPathById(photoUrlOrId);
            }
            return path;
        }
        PhotoLogger.debug(TAG, "video file not cached");
        return null;
    }

    private static final void notifyScanner(String photoPath) {
        notifyScanner(photoPath, "image/*");
    }

    public static final void notifyScanner(String photoPath, String mimeType) {
        if (isQCompact()) {
            Uri uri = Media.EXTERNAL_CONTENT_URI;
            if (!TextUtils.isEmpty(mimeType) && mimeType.contains("video")) {
                uri = Video.Media.EXTERNAL_CONTENT_URI;
            }
            APMSandboxProcessor.insertMediaFile(new Builder(uri, photoPath, getNameFromPath(photoPath)).mimeType(mimeType).build());
            PhotoLogger.d(TAG, "notifyScanner:### Q compact.path =" + photoPath);
            return;
        }
        PhotoLogger.d(TAG, "notifyScanner:###" + photoPath);
        MediaScannerConnection.scanFile(LauncherApplicationAgent.getInstance().getApplicationContext(), new String[]{photoPath}, new String[]{mimeType}, null);
    }

    private static String getNameFromPath(String path) {
        String ret = path;
        if (TextUtils.isEmpty(path)) {
            return ret;
        }
        try {
            String nameWithSuffix = path.substring(path.lastIndexOf("\\") + 1);
            return nameWithSuffix.substring(0, nameWithSuffix.lastIndexOf("."));
        } catch (Throwable tr) {
            PhotoLogger.e(TAG, "getNameFromPath### Exception = " + tr.getMessage());
            return ret;
        }
    }

    public static final boolean savePhoto(Bitmap bitmap, File file) {
        if (file == null || bitmap == null || bitmap.isRecycled()) {
            return false;
        }
        boolean result = true;
        long time = System.currentTimeMillis();
        try {
            File parentFile = new File(file.getParent());
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            notifyScanner(file.getAbsolutePath());
        } catch (IOException e) {
            PhotoLogger.error(TAG, "save photo exception.", e);
            result = false;
        }
        PhotoLogger.debug(TAG, "savePhoto delta " + (System.currentTimeMillis() - time));
        return result;
    }

    public static final String createPhoto(String folder) {
        return createPhoto(folder, false, false);
    }

    public static final String createPhoto(String folder, boolean isVideo, boolean isGif) {
        File imageFolder = createDirIfNotExist(folder);
        if (imageFolder == null) {
            return null;
        }
        String suffix = isVideo ? PhotoParam.VIDEO_SUFFIX : ".jpg";
        if (isGif) {
            suffix = ".gif";
        }
        return imageFolder + File.separator + (System.currentTimeMillis() + suffix);
    }

    private static File createDirIfNotExist(String folder) {
        if (TextUtils.isEmpty(folder)) {
            folder = getDefaultPhotoFolder();
        }
        File imageFolder = new File(folder);
        imageFolder.mkdirs();
        if (!imageFolder.exists()) {
            return null;
        }
        return imageFolder;
    }

    public static final String getDefaultPhotoFolder() {
        if (TextUtils.isEmpty(sDefaultFolder)) {
            sDefaultFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera";
        }
        return sDefaultFolder;
    }

    public static long getThisWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.clear(12);
        calendar.clear(13);
        calendar.clear(14);
        calendar.set(7, calendar.getFirstDayOfWeek());
        return calendar.getTimeInMillis();
    }

    public static long getThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.clear(12);
        calendar.clear(13);
        calendar.clear(14);
        calendar.set(5, 1);
        return calendar.getTimeInMillis();
    }

    public static long getLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.clear(12);
        calendar.clear(13);
        calendar.clear(14);
        calendar.set(5, 1);
        calendar.add(2, -1);
        return calendar.getTimeInMillis();
    }

    public static boolean copyFile(String srcPath, String dstPath, boolean isVideo, boolean isGif) {
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(dstPath)) {
            return false;
        }
        if (srcPath.equals(dstPath)) {
            return true;
        }
        File srcFile = new File(srcPath);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        File dstFile = new File(dstPath);
        new File(dstFile.getParent()).mkdirs();
        return copyAction(srcFile, dstFile, isVideo, isGif);
    }

    private static boolean copyAction(final File srcFile, final File dstFile, boolean isVideo, final boolean isGif) {
        if (!isVideo && !isGif) {
            return doCopy(srcFile, dstFile);
        }
        TaskScheduleService ts = (TaskScheduleService) MicroServiceUtil.getMicroService(TaskScheduleService.class);
        if (ts != null) {
            ts.acquireExecutor(ScheduleType.URGENT).execute(new Runnable() {
                public final void run() {
                    if (PhotoUtil.doCopy(srcFile, dstFile)) {
                        String mimeType = "video/*";
                        if (isGif) {
                            mimeType = "image/gif";
                        }
                        PhotoUtil.notifyScanner(dstFile.getAbsolutePath(), mimeType);
                        PhotoLogger.info(PhotoUtil.TAG, srcFile.getAbsolutePath() + " save to " + dstFile.getAbsolutePath() + " success ,MediaScanner notified!");
                        return;
                    }
                    PhotoLogger.info(PhotoUtil.TAG, srcFile.getAbsolutePath() + " save to " + dstFile.getAbsolutePath() + " failed!");
                }
            });
            return true;
        }
        PhotoLogger.error((String) TAG, (String) "Get TaskScheduleService failed,when save file!");
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean doCopy(File srcFile, File dstFile) {
        try {
            dstFile.createNewFile();
            FileInputStream in = new FileInputStream(srcFile);
            FileOutputStream out = new FileOutputStream(dstFile);
            try {
                byte[] buffer = new byte[8192];
                while (true) {
                    int len = in.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    out.write(buffer, 0, len);
                }
                out.flush();
                try {
                } catch (Exception e) {
                    PhotoLogger.error(TAG, "copy file exception.", e);
                }
                return true;
            } catch (Exception e2) {
                PhotoLogger.error(TAG, "exception.", e2);
                try {
                    return false;
                } catch (Exception e3) {
                    PhotoLogger.error(TAG, "copy file exception.", e3);
                    return false;
                }
            } finally {
                try {
                    in.close();
                    out.close();
                } catch (Exception e4) {
                    PhotoLogger.error(TAG, "copy file exception.", e4);
                }
            }
        } catch (Exception e5) {
            PhotoLogger.error(TAG, "new file exception.", e5);
            return false;
        }
    }

    public static void runOnMain(Runnable runnable) {
        if (runnable != null) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                runnable.run();
            } else {
                new Handler(Looper.getMainLooper()).post(runnable);
            }
        }
    }

    public static final int dp2px(Context context, int dip) {
        return Math.round(TypedValue.applyDimension(1, (float) dip, context.getResources().getDisplayMetrics()));
    }

    public static final Size reorderSize(Size size) {
        MultimediaImageService imgService = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName());
        if (imgService != null) {
            return imgService.getDjangoNearestImageSize(size);
        }
        PhotoLogger.warn((String) TAG, (String) "get MultimediaImageService error");
        return size;
    }

    public static final Size reorderSize(int wOrH) {
        return reorderSize(new Size(wOrH, wOrH));
    }

    public static String formatDuration(long duration) {
        if (duration < 0) {
            duration = 0;
        }
        long duration2 = (long) Math.round(((float) duration) / 1000.0f);
        if (duration2 <= 0) {
            return "";
        }
        long duration3 = duration2 * 1000;
        String pattern = "mm:ss";
        if (duration3 >= 3600000) {
            pattern = "HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date(duration3));
    }

    public static String formatDurationWithZero(long duration) {
        String ret = formatDuration(duration);
        if (TextUtils.isEmpty(ret)) {
            return OriVideoPreviewCon.ZERO_DURATION;
        }
        return ret;
    }

    public static final Size getPhotoSize(PhotoInfo info) {
        try {
            if (info.oriPhotoSize != null) {
                return info.oriPhotoSize;
            }
            int width = info.getPhotoWidth();
            int height = info.getPhotoHeight();
            if (info.getPhotoOrientation() == 90 || info.getPhotoOrientation() == 270) {
                int temp = width;
                width = height;
                height = temp;
            }
            info.oriPhotoSize = new Size(width, height);
            return info.oriPhotoSize;
        } catch (Exception e) {
            return new Size(0, 0);
        }
    }

    public static boolean isPhoto(String fullName) {
        return fullName.endsWith("jpeg") || fullName.endsWith("png") || fullName.endsWith("gif") || fullName.endsWith("jpg") || fullName.endsWith("JPEG") || fullName.endsWith("PNG") || fullName.endsWith("GIF") || fullName.endsWith("GIF") || fullName.endsWith("bmp") || fullName.endsWith("BMP");
    }

    public static Size calcViewSize(int maxWidth, float scale) {
        return (maxWidth <= 0 || scale <= 0.0f) ? new Size(800, 800) : new Size(maxWidth, (int) (((float) maxWidth) / scale));
    }

    public static float getHeightScale(int photoWidth, int photoHeight) {
        if (photoWidth <= 0 || photoHeight <= 0) {
            return 0.5625f;
        }
        float heightSacle = ((float) photoHeight) / ((float) photoWidth);
        if (heightSacle > 1.3333334f) {
            return 1.3333334f;
        }
        if (heightSacle < 0.18518518f) {
            return 0.18518518f;
        }
        return heightSacle;
    }

    public static DisplayImageOptions getOption(Context context, Drawable defaultDrawable, int photoWidth, int photoHeight) {
        float viewScale = 1.0f / getHeightScale(photoWidth, photoHeight);
        Size viewSize = calcViewSize(Math.min(dp2px(context, Callback.DEFAULT_SWIPE_ANIMATION_DURATION), 800), viewScale);
        PhotoLogger.debug(TAG, "photoInfo width = " + photoWidth + " height = " + photoHeight + " viewSize: " + viewSize + ", scale: " + viewScale);
        return new DisplayImageOptions.Builder().width(Integer.valueOf(viewSize.getWidth())).height(Integer.valueOf(viewSize.getHeight())).viewW2HRatio(viewScale).showImageOnLoading(defaultDrawable).originalSize(new Size(photoWidth, photoHeight)).build();
    }

    public static void loadRatioSpecifiedImage(PhotoInfo photoInfo, ImageView imageView, float viewScale, Drawable defaultDrawable, Size viewSize, MultimediaImageService imageService, String businessId, String bizType) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().imageScaleType(CutScaleType.SMART_CROP).width(Integer.valueOf(viewSize.getWidth())).height(Integer.valueOf(viewSize.getHeight())).viewW2HRatio(viewScale).showImageOnLoading(defaultDrawable).originalSize(new Size(photoInfo.getPhotoWidth(), photoInfo.getPhotoHeight())).build();
        options.bundle = photoInfo.bizExtraParams;
        if (isNativePhoto(photoInfo.getPhotoPath())) {
            options.setProgressive(false);
        } else {
            options.setProgressive(true);
        }
        if (!TextUtils.isEmpty(bizType)) {
            options.setBizType(bizType);
        }
        imageService.loadImage(photoInfo.getPhotoPath(), imageView, options, (APImageDownLoadCallback) null, businessId);
    }

    public static void loadSingleSpecifiedImage(PhotoInfo photoInfo, ImageView imageView, float viewScale, Drawable defaultDrawable, Size viewSize, MultimediaImageService imageService, String businessId, String bizType) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().imageScaleType(CutScaleType.SMART_CROP).setSecondaryCutScaleType(CutScaleType.REGION_CROP_CENTER_CENTER).enableSaliency(true).width(Integer.valueOf(viewSize.getWidth())).height(Integer.valueOf(viewSize.getHeight())).viewW2HRatio(viewScale).showImageOnLoading(defaultDrawable).originalSize(new Size(photoInfo.getPhotoWidth(), photoInfo.getPhotoHeight())).build();
        if (isNativePhoto(photoInfo.getPhotoPath())) {
            options.setProgressive(false);
        } else {
            options.setProgressive(true);
        }
        if (!TextUtils.isEmpty(bizType)) {
            options.setBizType(bizType);
        }
        options.bundle = photoInfo.bizExtraParams;
        imageService.loadImage(photoInfo.getPhotoPath(), imageView, options, (APImageDownLoadCallback) null, businessId);
    }

    public static void loadRatioSpecifiedVideo(PhotoInfo photoInfo, ImageView imageView, float viewScale, Drawable defaultDrawable, Size viewSize, MultimediaImageService imageService, String businessId, String bizType) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().width(Integer.valueOf(viewSize.getWidth())).height(Integer.valueOf(viewSize.getHeight())).viewW2HRatio(viewScale).showImageOnLoading(defaultDrawable).originalSize(new Size(photoInfo.getPhotoWidth(), photoInfo.getPhotoHeight())).build();
        if (!TextUtils.isEmpty(bizType)) {
            options.setBizType(bizType);
        }
        options.bundle = photoInfo.bizExtraParams;
        imageService.loadImage(photoInfo.getPhotoPath(), imageView, options, (APImageDownLoadCallback) null, businessId);
    }

    public static void loadWithOption(PhotoInfo photoInfo, DisplayImageOptions op, ImageView imageView, float viewScale, Drawable defaultDrawable, Size viewSize, MultimediaImageService imageService, String businessId, String bizType) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().imageScaleType(op.getCutScaleType()).width(Integer.valueOf(viewSize.getWidth())).height(Integer.valueOf(viewSize.getHeight())).viewW2HRatio(viewScale).showImageOnLoading(defaultDrawable).originalSize(new Size(photoInfo.getPhotoWidth(), photoInfo.getPhotoHeight())).build();
        options.bundle = photoInfo.bizExtraParams;
        if (isNativePhoto(photoInfo.getPhotoPath())) {
            options.setProgressive(false);
        } else {
            options.setProgressive(true);
        }
        if (!TextUtils.isEmpty(bizType)) {
            options.setBizType(bizType);
        }
        imageService.loadImage(photoInfo.getPhotoPath(), imageView, options, (APImageDownLoadCallback) null, businessId);
    }

    public static boolean isNativePhoto(String imgPath) {
        if (TextUtils.isEmpty(imgPath) || (!imgPath.startsWith(File.separator) && !imgPath.startsWith("file://"))) {
            return false;
        }
        return true;
    }

    public static void loadGifImage(PhotoInfo photoInfo, ImageView imageView, float viewScale, Drawable defaultDrawable, Size viewSize, MultimediaImageService imageService, String businessId, String bizType) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().width(Integer.valueOf(viewSize.getWidth())).height(Integer.valueOf(viewSize.getHeight())).viewW2HRatio(viewScale).showImageOnLoading(defaultDrawable).originalSize(new Size(photoInfo.getPhotoWidth(), photoInfo.getPhotoHeight())).usingSourceType(true).build();
        if (!TextUtils.isEmpty(bizType)) {
            options.setBizType(bizType);
        }
        options.bundle = photoInfo.bizExtraParams;
        LoggerFactory.getTraceLogger().debug(TAG, "photoPath = " + photoInfo.getPhotoPath() + ", ThumbPath = " + photoInfo.getThumbPath());
        imageService.loadImage(TextUtils.isEmpty(photoInfo.getThumbPath()) ? photoInfo.getPhotoPath() : photoInfo.getThumbPath(), imageView, options, (APImageDownLoadCallback) null, businessId);
    }

    public static boolean isQVersion() {
        return VERSION.SDK_INT > 28 || (VERSION.SDK_INT == 28 && VERSION.PREVIEW_SDK_INT > 0);
    }

    public static boolean isQCompact() {
        return isQVersion() && !CloudConfig.isConfigToDisableQCompact();
    }
}
