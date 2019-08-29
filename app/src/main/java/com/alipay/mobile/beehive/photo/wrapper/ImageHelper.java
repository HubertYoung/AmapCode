package com.alipay.mobile.beehive.photo.wrapper;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APCacheBitmapReq;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageCacheQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageClearCacheQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageLoadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageMarkRequest.Builder;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageSourceCutQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APThumbnailBitmapReq;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.BaseOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.api.BeehiveService;
import com.alipay.mobile.beehive.photo.data.LoadInfo;
import com.alipay.mobile.beehive.photo.data.PhotoMark;
import com.alipay.mobile.beehive.photo.util.CloudConfig;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.util.JumpUtil;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

public class ImageHelper {
    public static final String DEFAULT_CURRENT_LIMIT_CAUSE = "#DEFAULT_CURRENT_LIMIT_CAUSE#";
    public static final String TAG = "ImageHelper";
    private static String sBizType;
    private static String sBuinessId;
    private static MultimediaImageService service;

    public interface LoadListener {
        void onLoadCanceled(LoadInfo loadInfo);

        void onLoadComplete(LoadInfo loadInfo);

        void onLoadFailed(LoadInfo loadInfo, APImageDownloadRsp aPImageDownloadRsp);

        void onLoadProgress(LoadInfo loadInfo, int i, int i2);
    }

    public static void updateBusinessId(String businessId, String holderDesc) {
        sBuinessId = businessId;
        PhotoLogger.debug(TAG, holderDesc + " update businessId to :" + sBuinessId);
    }

    public static String getBusinessId() {
        if (TextUtils.isEmpty(sBuinessId)) {
            PhotoLogger.debug(TAG, "Warning,businessId is Empty when used!");
        }
        return sBuinessId;
    }

    public static void updateBizType(String bizType) {
        sBizType = bizType;
        PhotoLogger.debug(TAG, "setBizType:### ->" + bizType);
    }

    public static String getBizType() {
        return sBizType;
    }

    public static Bitmap loadThumbPhoto(PhotoInfo photoInfo) {
        String photoPath = photoInfo.getPhotoPath();
        long time = System.currentTimeMillis();
        APThumbnailBitmapReq imgReq = new APThumbnailBitmapReq(photoPath);
        if (service == null) {
            service = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName());
        }
        Bitmap bitmap = service.loadCacheBitmap(imgReq);
        PhotoLogger.debug(TAG, "loadThumbPhoto time " + (System.currentTimeMillis() - time));
        return bitmap;
    }

    public static Bitmap loadThumbPhoto(PhotoInfo photoInfo, int whPx) {
        String photoPath = photoInfo.getPhotoPath();
        long time = System.currentTimeMillis();
        APThumbnailBitmapReq imgReq = new APThumbnailBitmapReq(photoPath);
        imgReq.width = Integer.valueOf(whPx);
        imgReq.height = Integer.valueOf(whPx);
        imgReq.minWidth = Integer.valueOf(whPx);
        imgReq.minHeight = Integer.valueOf(whPx);
        imgReq.loadFromDiskCache = false;
        imgReq.setBizType(sBizType);
        if (service == null) {
            service = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName());
        }
        Bitmap bitmap = service.loadCacheBitmap(imgReq);
        PhotoLogger.debug(TAG, "loadThumbPhoto time " + (System.currentTimeMillis() - time));
        return bitmap;
    }

    public static void safeRemoveDrawable(ImageView imgView) {
        if (service == null) {
            service = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName());
        }
        service.loadImage((String) null, imgView, (Drawable) null, sBuinessId);
    }

    public static Bitmap loadBigPhoto(PhotoInfo photoInfo) {
        long time = System.currentTimeMillis();
        APCacheBitmapReq imgReq = new APCacheBitmapReq(photoInfo.getPhotoPath(), 0, 0);
        imgReq.loadFromDiskCache = false;
        if (service == null) {
            service = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName());
        }
        Bitmap bitmap = service.loadCacheBitmap(imgReq);
        PhotoLogger.debug(TAG, "loadBigPhoto time " + (System.currentTimeMillis() - time));
        return bitmap;
    }

    public static int[] getSingleImageSize(int width, int height, int maxWidth, float scale) {
        if (service != null) {
            return service.calculateCutImageRect(width, height, maxWidth, scale, null);
        }
        return new int[]{0, 0};
    }

    @Deprecated
    public static void loadImage(ImageView imageView, String path, int width, int height) {
        if (service == null) {
            service = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName());
        }
        if (service != null) {
            service.loadImage(path, imageView, width, height);
        }
    }

    @Deprecated
    public static void load(ImageView imageView, String path) {
        load(imageView, path, null, 0, 0, null, null);
    }

    @Deprecated
    public static void load(ImageView imageView, String path, int width, int height) {
        load(imageView, path, null, width, height, null, null);
    }

    public static void load(ImageView imageView, String path, Drawable drawable, int width, int height) {
        load(imageView, path, drawable, width, height, null, null);
    }

    public static void load(ImageView imageView, String path, Drawable drawable, int width, int height, Size size) {
        load(imageView, path, drawable, width, height, null, size);
    }

    public static void load(ImageView imageView, String path, Drawable drawable, int width, int height, LoadInfo loadInfo) {
        load(imageView, path, drawable, width, height, loadInfo, null, true, null, -1, false);
    }

    public static void load(ImageView imageView, String path, Drawable defaultDrawable, int width, int height, LoadInfo loadInfo, Size size) {
        load(imageView, path, defaultDrawable, width, height, loadInfo, size, false, null, -1, false);
    }

    public static void loadWidthThumbnailPath(ImageView imageView, String path, Drawable defaultDrawable, int width, int height, Size size, String thumbnailPath, int mediaId, boolean isAlbumMedia) {
        load(imageView, path, defaultDrawable, width, height, null, size, false, thumbnailPath, mediaId, isAlbumMedia);
    }

    private static void load(ImageView imageView, String path, Drawable defaultDrawable, int width, int height, LoadInfo loadInfo, Size size, boolean isProgressive, String thumbnailPath, int mediaId, boolean isAlbumMedia) {
        APMultimediaTaskModel task;
        if (TextUtils.isEmpty(path)) {
            PhotoLogger.debug(TAG, "invalid path");
            return;
        }
        if (loadInfo != null) {
            loadInfo.loading = true;
        }
        if (service == null) {
            service = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName());
        }
        if (service == null) {
            PhotoLogger.warn((String) TAG, (String) "Get MultimediaImageService return null!");
            return;
        }
        if (loadInfo == null || loadInfo.photoItem == null || loadInfo.photoItem.getPhotoMark() == null) {
            task = doLoadImage(imageView, path, defaultDrawable, width, height, loadInfo, size, isProgressive, thumbnailPath, mediaId, isAlbumMedia);
        } else {
            task = loadPhotoWithMarker(imageView, path, width, height, loadInfo, isProgressive);
        }
        if (loadInfo != null && task != null) {
            loadInfo.taskId = task.getTaskId();
        }
    }

    private static Bundle getBizExtraParamsFromTag(ImageView v) {
        Object o = v.getTag(R.id.id_photo_info_tag);
        if (o != null) {
            return ((PhotoInfo) o).bizExtraParams;
        }
        return null;
    }

    private static APMultimediaTaskModel loadPhotoWithMarker(ImageView imageView, String path, int width, int height, LoadInfo loadInfo, boolean isProgressive) {
        int transparency;
        PhotoMark photoMark = loadInfo.photoItem.getPhotoMark();
        Builder position = new Builder().markId(photoMark.getMarkId()).position(Integer.valueOf(photoMark.getPosition() == 0 ? 3 : photoMark.getPosition()));
        if (photoMark.getTransparency() == 0) {
            transparency = 100;
        } else {
            transparency = photoMark.getTransparency();
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder().width(Integer.valueOf(width)).height(Integer.valueOf(height)).imageMarkRequest(position.transparency(Integer.valueOf(transparency)).markWidth(Integer.valueOf(photoMark.getMarkWidth())).markHeight(Integer.valueOf(photoMark.getMarkHeight())).paddingX(Integer.valueOf(photoMark.getPaddingX())).paddingY(Integer.valueOf(photoMark.getPaddingY())).percent(photoMark.getPercent()).build()).build();
        options.setBizType(sBizType);
        options.setProgressive(isProgressive);
        return service.loadImageWithMark(path, imageView, options, loadInfo, sBuinessId);
    }

    private static APMultimediaTaskModel doLoadImage(ImageView imageView, String path, Drawable defaultDrawable, int width, int height, LoadInfo loadInfo, Size size, boolean isProgressive, String thumbnailPath, int mediaId, boolean isAlbumMedia) {
        APImageLoadRequest req = new APImageLoadRequest();
        req.imageView = imageView;
        req.thumbPath = thumbnailPath;
        req.path = path;
        req.imageId = mediaId;
        req.defaultDrawable = defaultDrawable;
        req.callback = loadInfo;
        req.bundle = getBizExtraParamsFromTag(imageView);
        if (width == -1 && height == -1) {
            req.width = Integer.MAX_VALUE;
            req.height = Integer.MAX_VALUE;
            req.loadType = 3;
        } else if (width == 0 && height == 0) {
            req.width = 0;
            req.height = 0;
        } else {
            req.width = width;
            req.height = height;
            req.srcSize = size;
        }
        req.setProgressive(isProgressive);
        req.setBizType(sBizType);
        if (!CloudConfig.isConfigToDisableCheckLocalPhotoDiskCache() && isAlbumMedia) {
            req.baseOptions = new BaseOptions();
            req.baseOptions.saveToDiskCache = false;
        }
        return service.loadImage(req, sBuinessId);
    }

    public static Bitmap loadFromCache(String path, boolean isLoadOri) {
        APCacheBitmapReq req;
        if (isLoadOri) {
            req = new APCacheBitmapReq(path, Integer.MAX_VALUE, Integer.MAX_VALUE);
            req.cutScaleType = CutScaleType.NONE;
        } else {
            req = new APCacheBitmapReq(path, 0, 0);
        }
        req.loadFromDiskCache = false;
        req.setBizType(sBizType);
        if (service == null) {
            service = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName());
        }
        if (service != null) {
            return service.loadCacheBitmap(req);
        }
        return null;
    }

    public static boolean hasOriginPhoto(String path) {
        boolean ret = true;
        if (isLocalFile(path)) {
            PhotoLogger.debug(TAG, "Consider local file as original photo.");
        } else {
            if (TextUtils.isEmpty(((MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName())).getOriginalImagePath(path))) {
                ret = false;
            }
            PhotoLogger.debug(TAG, "Check origin photo exist = " + ret + "<---,at path:" + path);
        }
        return ret;
    }

    public static boolean isLocalFile(String path) {
        return !TextUtils.isEmpty(path) && (path.startsWith("file:") || path.startsWith(File.separator));
    }

    public static boolean hasBigPhoto(String path) {
        APImageQueryResult result = ((MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName())).queryImageFor(new APImageCacheQuery(path, 0, 0));
        PhotoLogger.debug(TAG, "Check big photo exist = " + result.success + "<---,at path:" + path);
        return result.success;
    }

    public static void cancel(String path) {
        PhotoLogger.debug(TAG, "cancel image load " + path);
        ((MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName())).cancelLoad(path);
    }

    public static void optimizeView(AbsListView view, OnScrollListener listener) {
        ((MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName())).optimizeView(view, listener);
    }

    public static String getCachePath(String url) {
        APImageQueryResult result = ((MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName())).queryImageFor(new APImageClearCacheQuery(url));
        PhotoLogger.debug(TAG, "getCachePath " + url + "-->" + result.path);
        return result.path;
    }

    public static String getCachePath(String url, boolean isGif) {
        APImageQuery q;
        MultimediaImageService service2 = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName());
        if (isGif) {
            q = new APGifQuery(url);
        } else {
            q = new APImageClearCacheQuery(url);
        }
        APImageQueryResult result = service2.queryImageFor(q);
        PhotoLogger.debug(TAG, "getCachePath " + url + "-->" + result.path);
        return result.path;
    }

    public static String getThumbCachePath(String url, int width, int height) {
        APImageQueryResult result = ((MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName())).queryImageFor(new APImageCacheQuery(url, width, height));
        PhotoLogger.debug(TAG, "getCachePath " + url + "-->" + result.path);
        return result.path;
    }

    public static boolean hasBigImageLoaded(String url) {
        APImageQueryResult result = ((MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName())).queryImageFor(new APImageSourceCutQuery(url));
        PhotoLogger.debug(TAG, "getCachePath " + url + "-->" + result.path);
        return result.success;
    }

    public static String detectQRCode(String filePath) {
        String code = null;
        BeehiveService service2 = (BeehiveService) MicroServiceUtil.getMicroService(BeehiveService.class);
        if (!(service2 == null || service2.getScanExecutor() == null)) {
            code = service2.getScanExecutor().scan(filePath);
        }
        PhotoLogger.debug(TAG, "detect QR code result " + code);
        return code;
    }

    public static Map<String, String> detectVirantQRCode(String filePath) {
        Map code = null;
        BeehiveService service2 = (BeehiveService) MicroServiceUtil.getMicroService(BeehiveService.class);
        if (!(service2 == null || service2.getScanExecutor() == null)) {
            code = service2.getScanExecutor().scanVariantQrCodeCompact(filePath);
        }
        PhotoLogger.debug(TAG, "detect QR code result " + code);
        return code;
    }

    public static void processQRCode(String code, String sourceId) {
        processQRCode(code, sourceId, null);
    }

    public static void processQRCode(String code, String sourceId, String codeType) {
        PhotoLogger.debug(TAG, "processQRCode " + code + ",sourceId = " + sourceId);
        try {
            String url = "alipays://platformapi/startapp?appId=10000007&actionType=route&codeContent=" + URLEncoder.encode(code, "utf-8");
            if (!TextUtils.isEmpty(sourceId)) {
                url = url + "&sourceId=" + sourceId;
            }
            if (!TextUtils.isEmpty(codeType)) {
                url = url + "&scanType=" + codeType;
            }
            JumpUtil.processSchema(url);
        } catch (Exception e) {
        }
    }
}
