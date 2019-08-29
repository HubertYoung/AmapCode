package com.alipay.mobile.beehive.photo.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Thumbnails;
import android.provider.MediaStore.Video.Media;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.SparseArray;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImageInfo;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoInfo;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.photo.util.CloudConfig;
import com.alipay.mobile.beehive.photo.util.ImageObserver;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.util.UserBehavior;
import com.alipay.mobile.beehive.photo.util.UserBehavior.PhotoServiceStatistics;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.multimedia.adjuster.api.APMSandboxProcessor;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Executor;

public class PhotoResolver {
    private static final int FIRST_SCNA_STEP = 100;
    private static final int STEP_SCAN_COUNT = 1000;
    public static final String TAG = "PhotoResolver";
    private volatile List<PhotoItem> allPhotosShadow;
    private volatile List<BucketInfo> bucketList;
    private volatile List<BucketInfo> bucketListShadow;
    /* access modifiers changed from: private */
    public BucketUpdateListener bucketListener;
    private volatile Map<String, List<PhotoItem>> bucketMap;
    private volatile Map<String, List<PhotoItem>> bucketMapShadow;
    private volatile Map<String, BucketInfo> bucketSet;
    private boolean enableGif;
    private SimpleDateFormat mDateFormat;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public Set<String> mImageFileDir = new HashSet();
    private MultimediaImageProcessor mImageProcesser;
    private boolean mIsEnableStepScan;
    private volatile int mMinPhotoHeight;
    private volatile int mMinPhotoWidth;
    /* access modifiers changed from: private */
    public SparseArray<String> mThumbnailMap;
    private Executor mUrgentExecutor;
    private MultimediaVideoService mVideoService;
    private int minPhotoSize;
    private volatile List<PhotoItem> photoList;
    private ContentResolver resolver;
    public PhotoServiceStatistics scanStatistics;
    private LinkedHashSet<String> selectedPhotoPaths;
    private String textAllBucket;

    public interface BucketUpdateListener {
        void onScanFinished();

        void onScanStep();
    }

    public PhotoResolver(Context context, BucketUpdateListener listener) {
        initContentDescInfo(context);
        Context context2 = context.getApplicationContext();
        this.bucketListener = listener;
        this.resolver = context2.getContentResolver();
        this.minPhotoSize = 10240;
        this.enableGif = false;
        this.mUrgentExecutor = ((TaskScheduleService) MicroServiceUtil.getMicroService(TaskScheduleService.class)).acquireExecutor(ScheduleType.URGENT);
        this.mThumbnailMap = new SparseArray<>();
    }

    public void setBucketListener(BucketUpdateListener listener) {
        this.bucketListener = listener;
    }

    public void setAllBucketName(String textAllBucket2) {
        this.textAllBucket = textAllBucket2;
    }

    public void setEnableGif(boolean enableGif2) {
        this.enableGif = enableGif2;
    }

    public void setMinPhotoSize(int minPhotoSize2) {
        this.minPhotoSize = minPhotoSize2;
    }

    public void setMinPhotoWidth(int minPhotoWidth) {
        this.mMinPhotoWidth = minPhotoWidth;
    }

    public void setMinPhotoHeight(int minPhotoHeight) {
        this.mMinPhotoHeight = minPhotoHeight;
    }

    public void setSelectedPhotoPaths(LinkedHashSet<String> selectedPhotoPaths2) {
        this.selectedPhotoPaths = selectedPhotoPaths2;
    }

    public List<PhotoItem> getBucketList(String bucketName) {
        return this.bucketMap.get(bucketName);
    }

    public List<BucketInfo> getBucketList() {
        return this.bucketList;
    }

    public boolean addMediaInfoToList(Uri uri) {
        PhotoLogger.debug(TAG, "addMediaInfoToList " + uri);
        if (uri == null) {
            return false;
        }
        String localPath = uri.getPath();
        File photoFile = new File(localPath);
        PhotoItem photoInfo = new PhotoItem("file://" + localPath);
        markPreSelected(photoInfo);
        photoInfo.setPhotoSize(photoFile.length());
        photoInfo.setModifiedTime(photoFile.lastModified());
        if (!PhotoUtil.isPhoto(uri.toString())) {
            photoInfo.setMediaType(2);
        }
        addMediaInfoToList(photoInfo, new File(photoFile.getParent()).getName(), false);
        PhotoLogger.debug(TAG, "addMediaInfoToList " + localPath);
        initBucket();
        return true;
    }

    public void asyncScanPhotoAndVideo(final boolean isScanPhoto, final boolean isScanVideo) {
        init();
        PhotoLogger.debug("PhotoDisplayLink", "Fire async scan job.");
        this.scanStatistics = new PhotoServiceStatistics();
        PhotoServiceStatistics.scanCount++;
        this.scanStatistics.triggerScanTime = System.currentTimeMillis();
        this.mUrgentExecutor.execute(new Runnable() {
            public final void run() {
                Cursor actionCursor;
                PhotoLogger.debug("PhotoDisplayLink", "Async scan started.");
                long startAt = System.currentTimeMillis();
                PhotoResolver.this.scanStatistics.startScanTime = startAt;
                Cursor imgCursor = null;
                Cursor videoCursor = null;
                int mediaCount = 0;
                if (isScanPhoto) {
                    imgCursor = PhotoResolver.this.getImageCursor();
                    if (!CloudConfig.isIsConfigToDisablePhotoThumbnailScan()) {
                        PhotoResolver.this.queryThumbnailInfo();
                    }
                }
                if (isScanVideo) {
                    videoCursor = PhotoResolver.this.getVideoCursor();
                }
                if (imgCursor == null || !imgCursor.moveToLast()) {
                    PhotoLogger.debug("PhotoDisplayLink", "asyncScanPhotoAndVideo(), No img!");
                }
                if (videoCursor == null || !videoCursor.moveToLast()) {
                    PhotoLogger.debug("PhotoDisplayLink", "scanPhotoAndVideo(), No video!");
                }
                do {
                    actionCursor = PhotoResolver.this.addBoth(videoCursor, imgCursor, true);
                    if (actionCursor != null) {
                        mediaCount++;
                    }
                } while (PhotoResolver.this.dataRemain(actionCursor, videoCursor, imgCursor));
                if (imgCursor != null) {
                    imgCursor.close();
                }
                if (videoCursor != null) {
                    videoCursor.close();
                }
                PhotoLogger.debug("PhotoDisplayLink", "Async scan finished, costTime = " + (System.currentTimeMillis() - startAt) + ",fileCount = " + mediaCount + "imageThumbCount =" + PhotoResolver.this.mThumbnailMap.size());
                PhotoResolver.this.swapData();
                PhotoResolver.this.scanStatistics.timeCost = System.currentTimeMillis() - PhotoResolver.this.scanStatistics.startScanTime;
                PhotoResolver.this.postBackgroundScanFinished();
                ImageObserver.getInstance().active(new ArrayList(PhotoResolver.this.mImageFileDir));
            }
        });
    }

    /* access modifiers changed from: private */
    public void queryThumbnailInfo() {
        PhotoLogger.error((String) TAG, (String) "queryThumbnailInfo:###");
        long startAt = System.currentTimeMillis();
        Cursor thumbnailCursor = getThumbnailCursor();
        if (thumbnailCursor != null) {
            try {
                if (thumbnailCursor.moveToFirst()) {
                    do {
                        this.mThumbnailMap.put(thumbnailCursor.getInt(thumbnailCursor.getColumnIndex("image_id")), thumbnailCursor.getString(thumbnailCursor.getColumnIndex("_data")));
                    } while (thumbnailCursor.moveToNext());
                }
                thumbnailCursor.close();
            } catch (Throwable tr) {
                PhotoLogger.error((String) TAG, "Parse thumbnail exception :" + tr.getMessage());
            }
        }
        PhotoLogger.debug(TAG, "Query thumbnail cost time = " + (System.currentTimeMillis() - startAt));
    }

    private void initContentDescInfo(Context context) {
        this.mDateFormat = new SimpleDateFormat(context.getString(R.string.str_media_time_pattern));
        this.mDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private void init() {
        PhotoLogger.debug("PhotoDisplayLink", "initPhotoBucket");
        this.bucketMap = new HashMap();
        this.photoList = new ArrayList();
        this.bucketList = new ArrayList();
        this.bucketSet = new HashMap();
        this.bucketMapShadow = new HashMap();
        this.allPhotosShadow = new ArrayList();
        this.bucketListShadow = new ArrayList();
        this.mThumbnailMap = new SparseArray<>();
    }

    /* access modifiers changed from: private */
    public Cursor addBoth(Cursor videoCursor, Cursor imgCursor, boolean isAppend) {
        if ((videoCursor == null || videoCursor.isBeforeFirst() || videoCursor.isAfterLast()) && (imgCursor == null || imgCursor.isBeforeFirst() || imgCursor.isAfterLast())) {
            return null;
        }
        long vmt = -1;
        long imt = -1;
        if (videoCursor != null && !videoCursor.isBeforeFirst() && !videoCursor.isAfterLast()) {
            vmt = videoCursor.getLong(videoCursor.getColumnIndex("date_added")) * 1000;
        }
        if (imgCursor != null && !imgCursor.isBeforeFirst() && !imgCursor.isAfterLast()) {
            imt = imgCursor.getLong(imgCursor.getColumnIndex("date_added")) * 1000;
        }
        if (vmt < 0 && imt < 0) {
            PhotoLogger.debug("mediaScan", "both cursor no more data!");
            return null;
        } else if (vmt >= imt) {
            buildVideoInfo(videoCursor, isAppend);
            return videoCursor;
        } else {
            buildPhotoInfo(imgCursor, isAppend);
            return imgCursor;
        }
    }

    /* access modifiers changed from: private */
    public Cursor getVideoCursor() {
        Cursor cursor = null;
        try {
            return this.resolver.query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "bucket_display_name", CaptureParam.CAPTURE_PICTURE_SIZE, "_data", "_size", "mini_thumb_magic", "duration", "date_modified", "date_added", "datetaken", "latitude", "longitude"}, null, null, sortOrder());
        } catch (Exception e) {
            PhotoLogger.debug(TAG, "getVideoCursor exception:" + e.getMessage());
            return cursor;
        }
    }

    private String sortOrder() {
        return "date_added" + " ASC" + ", _id" + " ASC";
    }

    /* access modifiers changed from: private */
    public void postBackgroundScanFinished() {
        this.mHandler.post(new Runnable() {
            public final void run() {
                PhotoResolver.this.initBucket();
                if (PhotoResolver.this.bucketListener != null) {
                    PhotoLogger.debug("PhotoDisplayLink", "OnScanFinished");
                    PhotoResolver.this.bucketListener.onScanFinished();
                }
                UserBehavior.reportPhotoScanEvent(PhotoResolver.this.scanStatistics);
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean dataRemain(Cursor actionCursor, Cursor videoC, Cursor imgC) {
        if (actionCursor == null) {
            return false;
        }
        if (actionCursor.moveToPrevious() && !actionCursor.isBeforeFirst()) {
            return true;
        }
        if (actionCursor == videoC) {
            if (imgC == null || !imgC.moveToPrevious() || imgC.isBeforeFirst()) {
                return false;
            }
            return true;
        } else if (videoC == null || !videoC.moveToPrevious() || videoC.isBeforeFirst()) {
            return false;
        } else {
            return true;
        }
    }

    /* access modifiers changed from: private */
    public Cursor getImageCursor() {
        Cursor cursor = null;
        try {
            cursor = this.resolver.query(Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "bucket_display_name", "_data", "_size", "mime_type", "width", "height", CaptureParam.ORIENTATION_MODE, "datetaken", "date_added", "date_modified", "latitude", "longitude"}, null, null, sortOrder());
        } catch (Exception e) {
            PhotoLogger.debug(TAG, "getImageCursor exception:" + e.getMessage());
        }
        if (cursor == null || !cursor.isClosed()) {
            return cursor;
        }
        return null;
    }

    private Cursor getThumbnailCursor() {
        Cursor cursor = null;
        try {
            cursor = this.resolver.query(Thumbnails.EXTERNAL_CONTENT_URI, new String[]{"image_id", "_data"}, null, null, null);
        } catch (Throwable tr) {
            PhotoLogger.error((String) TAG, "GetThumbnailCursor exception :" + tr.getMessage());
        }
        if (cursor == null || !cursor.isClosed()) {
            return cursor;
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void initBucket() {
        if (!this.bucketList.isEmpty() && !this.photoList.isEmpty()) {
            if (this.bucketList.get(0).allPhoto) {
                this.bucketList.remove(0);
            }
            BucketInfo allAlbum = new BucketInfo(this.textAllBucket, 0, this.photoList.get(0), true);
            allAlbum.allPhoto = true;
            this.bucketList.add(0, allAlbum);
            this.bucketMap.put(this.textAllBucket, this.photoList);
        }
    }

    private boolean buildVideoInfo(Cursor cursor, boolean append) {
        if (cursor.getLong(cursor.getColumnIndex("_size")) >= ((long) this.minPhotoSize)) {
            String localPath = cursor.getString(cursor.getColumnIndex("_data"));
            if (TextUtils.isEmpty(localPath)) {
                PhotoLogger.debug(TAG, "Video path is invalid.");
                return false;
            }
            String photoPath = "file://" + localPath;
            if (PhotoUtil.isQCompact()) {
                photoPath = Uri.parse(Media.EXTERNAL_CONTENT_URI.toString() + File.separator + cursor.getInt(cursor.getColumnIndex("_id"))).toString();
            }
            if (PhotoUtil.isQVersion() || APMSandboxProcessor.checkFileExist(photoPath)) {
                long photoSize = cursor.getLong(cursor.getColumnIndex("_size"));
                long photoModified = cursor.getLong(cursor.getColumnIndex("date_modified")) * 1000;
                String resolution = cursor.getString(cursor.getColumnIndex(CaptureParam.CAPTURE_PICTURE_SIZE));
                long duration = cursor.getLong(cursor.getColumnIndex("duration"));
                double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                PhotoItem photoItem = new PhotoItem(photoPath);
                addModifyTimeDescStr(photoItem, photoModified);
                photoItem.setVideoDuration(duration);
                photoItem.setMediaType(2);
                photoItem.setPhotoSize(photoSize);
                photoItem.setModifiedTime(photoModified);
                photoItem.setVideoResolution(resolution);
                photoItem.setLatitude(latitude);
                photoItem.setLongitude(longitude);
                setDurationAndResolutionByMultimedia(localPath, photoItem);
                String bucketName = cursor.getString(cursor.getColumnIndex("bucket_display_name"));
                photoItem.setMediaId(cursor.getInt(cursor.getColumnIndex("_id")));
                photoItem.setIsAlbumMedia(true);
                return addMediaInfoToList(photoItem, bucketName, append);
            }
            PhotoLogger.debug(TAG, "video file error,won`t add!");
            return false;
        }
        PhotoLogger.debug(TAG, "video file size too small,won`t add!");
        return false;
    }

    private void setDurationAndResolutionByMultimedia(String localPath, PhotoInfo videoInfo) {
        if (this.mVideoService == null) {
            this.mVideoService = (MultimediaVideoService) MicroServiceUtil.getMicroService(MultimediaVideoService.class);
            APVideoInfo info = this.mVideoService.parseVideoInfo(localPath);
            if (info != null) {
                videoInfo.setVideoDuration((long) info.duration);
                videoInfo.setVideoWidth(info.correctWidth);
                videoInfo.setVideoHeight(info.correctHeight);
                PhotoLogger.debug(TAG, "Update video info to :d =" + info.duration + ",w =" + info.correctWidth + ",h =" + info.height);
                return;
            }
            PhotoLogger.warn((String) TAG, "Parse video info return null @" + localPath);
        }
    }

    private boolean buildPhotoInfo(Cursor cursor, boolean append) {
        if (cursor.getLong(cursor.getColumnIndex("_size")) < ((long) this.minPhotoSize)) {
            return false;
        }
        String localPath = cursor.getString(cursor.getColumnIndex("_data"));
        String photoPath = "file://" + localPath;
        if (PhotoUtil.isQCompact()) {
            photoPath = Uri.parse(Images.Media.EXTERNAL_CONTENT_URI.toString() + File.separator + cursor.getInt(cursor.getColumnIndex("_id"))).toString();
        } else {
            try {
                if (!TextUtils.isEmpty(localPath) && !localPath.contains("cache") && localPath.contains("/")) {
                    this.mImageFileDir.add(localPath.substring(0, localPath.lastIndexOf("/")));
                }
            } catch (Exception e) {
                PhotoLogger.error((String) TAG, "Parse observer path exception :" + e.getMessage());
            }
        }
        if (TextUtils.isEmpty(localPath)) {
            PhotoLogger.debug(TAG, "Photo path is invalid.");
            return false;
        } else if (!PhotoUtil.isQVersion() && !APMSandboxProcessor.checkFileExist(photoPath)) {
            return false;
        } else {
            boolean isGif = TextUtils.equals(cursor.getString(cursor.getColumnIndex("mime_type")), "image/gif");
            if (!this.enableGif && isGif) {
                return false;
            }
            long photoSize = cursor.getLong(cursor.getColumnIndex("_size"));
            long photoModified = cursor.getLong(cursor.getColumnIndex("date_modified")) * 1000;
            int orientation = cursor.getInt(cursor.getColumnIndex(CaptureParam.ORIENTATION_MODE));
            int width = cursor.getInt(cursor.getColumnIndex("width"));
            int height = cursor.getInt(cursor.getColumnIndex("height"));
            if (width <= 0 || height <= 0) {
                try {
                    Point p = updateWidthHeight(localPath, width, height, isGif);
                    width = p.x;
                    height = p.y;
                } catch (Exception e2) {
                    PhotoLogger.e(TAG, "Update size exception :" + e2.getMessage());
                }
            }
            if (width <= 0 || height <= 0) {
                PhotoLogger.w(TAG, "Invalid size : w = " + width + "h = " + height);
            }
            if (width < this.mMinPhotoWidth || height < this.mMinPhotoHeight) {
                PhotoLogger.debug(TAG, "Ignore photo when w=" + width + ",h=" + height);
                return false;
            }
            double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            PhotoItem photoItem = new PhotoItem(photoPath);
            addModifyTimeDescStr(photoItem, photoModified);
            markPreSelected(photoItem);
            photoItem.setPhotoSize(photoSize);
            photoItem.setModifiedTime(photoModified);
            photoItem.setPhotoOrientation(orientation);
            photoItem.setPhotoHeight(height);
            photoItem.setPhotoWidth(width);
            photoItem.setLatitude(latitude);
            photoItem.setLongitude(longitude);
            String bucketName = cursor.getString(cursor.getColumnIndex("bucket_display_name"));
            int imageId = cursor.getInt(cursor.getColumnIndex("_id"));
            photoItem.setThumbPath(this.mThumbnailMap.get(imageId));
            photoItem.setMediaId(imageId);
            photoItem.setIsAlbumMedia(true);
            if (isGif) {
                photoItem.setMediaSubType(100);
            }
            return addMediaInfoToList(photoItem, bucketName, append);
        }
    }

    @NonNull
    private Point updateWidthHeight(String localPath, int width, int height, boolean isGif) {
        APImageInfo info;
        Point p = new Point(width, height);
        if (this.mImageProcesser == null) {
            this.mImageProcesser = (MultimediaImageProcessor) MicroServiceUtil.getMicroService(MultimediaImageProcessor.class);
        }
        if (isGif) {
            info = this.mImageProcesser.parseGifInfo(localPath);
        } else {
            info = this.mImageProcesser.parseImageInfo(localPath);
        }
        if (info != null) {
            p.x = info.width;
            p.y = info.height;
            PhotoLogger.debug(TAG, "Update wh to " + width + ", " + height);
        }
        return p;
    }

    private void markPreSelected(PhotoItem photoInfo) {
        if (this.selectedPhotoPaths != null && this.selectedPhotoPaths.contains(photoInfo.getPhotoPath())) {
            photoInfo.setSelected(true);
        }
    }

    private void addModifyTimeDescStr(PhotoItem pi, long photoModified) {
        try {
            pi.modifyTimeDesc = this.mDateFormat.format(new Date(photoModified));
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) TAG, "addModifyTimeDescStr exception." + e.getMessage());
        }
    }

    private boolean addMediaInfoToList(PhotoItem photoInfo, String bucketName, boolean append) {
        int i;
        int i2;
        int i3 = 0;
        String photoPath = photoInfo.getPhotoPath();
        if (!this.enableGif && photoPath.toLowerCase().endsWith("gif")) {
            PhotoLogger.debug(TAG, "select gif not enabled.");
            return false;
        } else if (photoInfo.getPhotoSize() < ((long) this.minPhotoSize)) {
            PhotoLogger.debug(TAG, "ignore small file " + photoPath);
            return false;
        } else {
            BucketInfo bucketInfo = this.bucketSet.get(bucketName);
            if (bucketInfo == null) {
                bucketInfo = new BucketInfo(bucketName, 0, photoInfo);
                this.bucketSet.put(bucketName, bucketInfo);
                List<BucketInfo> list = this.bucketListShadow;
                if (append) {
                    i2 = this.bucketListShadow.size();
                } else {
                    i2 = 0;
                }
                list.add(i2, bucketInfo);
            }
            if (!append) {
                bucketInfo.setPhoto(photoInfo);
            }
            bucketInfo.increaseCount();
            List bucketPhotoList = this.bucketMapShadow.get(bucketName);
            if (bucketPhotoList == null) {
                bucketPhotoList = new ArrayList();
                this.bucketMapShadow.put(bucketName, bucketPhotoList);
            }
            List<PhotoItem> list2 = this.allPhotosShadow;
            if (append) {
                i = this.allPhotosShadow.size();
            } else {
                i = 0;
            }
            list2.add(i, photoInfo);
            if (append) {
                i3 = bucketPhotoList.size();
            }
            bucketPhotoList.add(i3, photoInfo);
            if (this.mIsEnableStepScan) {
                pendingStepUpdate();
            }
            return true;
        }
    }

    private void pendingStepUpdate() {
        if ((this.bucketListener != null && this.allPhotosShadow.size() == 100) || (this.allPhotosShadow.size() > 100 && this.allPhotosShadow.size() % 1000 == 0)) {
            if (CloudConfig.isConfigToDisableBeePhotoStepUpdate()) {
                PhotoLogger.debug("PhotoDisplayLink", "Config to disable step update.");
                return;
            }
            swapData();
            PhotoLogger.debug("PhotoDisplayLink", "Do step update.");
            this.mHandler.post(new Runnable() {
                public final void run() {
                    if (PhotoResolver.this.bucketListener != null) {
                        PhotoResolver.this.initBucket();
                        PhotoResolver.this.bucketListener.onScanStep();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void swapData() {
        Map tempBucketMapShadow = this.bucketMapShadow;
        List tempAllPhotoShadow = this.allPhotosShadow;
        List tempBucketListShadow = this.bucketListShadow;
        this.bucketMapShadow = new HashMap();
        this.allPhotosShadow = new ArrayList();
        this.bucketListShadow = new ArrayList();
        this.bucketMapShadow.putAll(tempBucketMapShadow);
        this.allPhotosShadow.addAll(tempAllPhotoShadow);
        this.bucketListShadow.addAll(tempBucketListShadow);
        this.bucketMap = tempBucketMapShadow;
        this.photoList = tempAllPhotoShadow;
        this.bucketList = tempBucketListShadow;
    }

    public void setEnableStepScan(boolean isEnable) {
        this.mIsEnableStepScan = isEnable;
    }
}
