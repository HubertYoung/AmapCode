package com.alipay.android.phone.mobilecommon.multimediabiz.biz.video;

import android.text.TextUtils;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoEffect;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.MediaUtils;
import com.alipay.diskcache.DiskCache;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.io.File;
import java.util.List;

public class VideoFileManager {
    public static final String BUSSINESS_ID = "multimedia_video";
    public static final String JPEG_EXT = ".tdat";
    public static final int MAXSIZE = 104857600;
    public static final String VIDEO_EXT = ".vdat";
    private static VideoFileManager a;
    public static String mBaseDir = "/sdcard/alipay/watermark";
    public static String mWatermarkDir = (mBaseDir + File.separator + APVideoEffect.TPYE_WATERMARK);
    private DiskCache b = CacheContext.get().getDiskCache();

    private VideoFileManager() {
    }

    public static VideoFileManager getInstance() {
        if (a == null) {
            synchronized (VideoFileManager.class) {
                try {
                    if (a == null) {
                        a = new VideoFileManager();
                    }
                }
            }
        }
        return a;
    }

    public void insertRecord(String cloudId, String localId, int type, int tag, String business) {
        insertRecord(cloudId, localId, type, tag, business, Long.MAX_VALUE);
    }

    public void insertRecord(String cloudId, String localId, int type, int tag, String business, long expiredTime) {
        String key;
        Logger.D("VideoFileManager", "insertRecord cloudId:" + cloudId + ", localId:" + localId + ", type: " + type + ", tag: " + tag + ",\tbizId: " + business, new Object[0]);
        if (TextUtils.isEmpty(cloudId)) {
            key = localId;
        } else {
            key = cloudId;
        }
        this.b.save(key, type, tag, business, expiredTime);
    }

    public void setCloudIdByLocalId(String cloudId, String localId) {
        Logger.D("VideoFileManager", "setCloudIdByLocalId cloudId:" + cloudId + ", localId:" + localId, new Object[0]);
        FileCacheModel model = this.b.get(localId);
        if (model != null) {
            this.b.update(localId, cloudId, model.tag & -17);
        }
    }

    public String getVideoPathById(String id) {
        Logger.D("VideoFileManager", "getVideoPathById in:" + id, new Object[0]);
        if (TextUtils.isEmpty(id)) {
            return id;
        }
        if (id.contains(MergeUtil.SEPARATOR_KV)) {
            id = id.substring(0, id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA));
        }
        FileCacheModel model = this.b.get(id);
        String ret = model != null ? model.path : "";
        Logger.D("VideoFileManager", "getVideoPathById out:" + ret + ";\tid: " + id, new Object[0]);
        return ret;
    }

    public FileCacheModel getVideoInfo(String id) {
        Logger.D("VideoFileManager", "getVideoInfo in:" + id, new Object[0]);
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        if (id.contains(MergeUtil.SEPARATOR_KV)) {
            id = id.substring(0, id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA));
        }
        FileCacheModel model = this.b.get(id);
        Logger.D("VideoFileManager", "getVideoInfo out:" + model + ";\tid: " + id, new Object[0]);
        return model;
    }

    public String getThumbPathById(String id) {
        Logger.D("VideoFileManager", "getThumbPathById in:" + id, new Object[0]);
        if (TextUtils.isEmpty(id)) {
            return id;
        }
        if (id.contains(MergeUtil.SEPARATOR_KV)) {
            id = id.substring(id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA) + 1);
        }
        if (a(id)) {
            id = id + "_thumb";
        }
        FileCacheModel model = this.b.get(id);
        String ret = model != null ? model.path : "";
        Logger.D("VideoFileManager", "getThumbPathById out:" + ret + ";\tid: " + id, new Object[0]);
        return ret;
    }

    public FileCacheModel getVideoThumbCacheInfo(String id) {
        Logger.D("VideoFileManager", "getVideoThumbCacheInfo in:" + id, new Object[0]);
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        if (id.contains(MergeUtil.SEPARATOR_KV)) {
            id = id.substring(id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA) + 1, id.length());
        } else if (a(id)) {
            id = id + "_thumb";
        }
        FileCacheModel model = this.b.get(id);
        Logger.D("VideoFileManager", "getVideoThumbCacheInfo out:" + model + ";id: " + id, new Object[0]);
        return model;
    }

    public String generateVideoPath(String id) {
        if (!id.contains(MergeUtil.SEPARATOR_KV)) {
            id = id + MergeUtil.SEPARATOR_KV + id;
        }
        return this.b.genPathByKey(id.substring(0, id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA)));
    }

    public String generateThumbPath(String id) {
        if (!id.contains(MergeUtil.SEPARATOR_KV)) {
            id = id + MergeUtil.SEPARATOR_KV + id;
        }
        return this.b.genPathByKey(id.substring(id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA) + 1, id.length()));
    }

    public List<FileCacheModel> queryRecentVideo(long interval) {
        return this.b.getRecent(interval, 32);
    }

    public void deleteByLocalId(String localid) {
        Logger.D("VideoFileManager", "deleteByLocalId: " + localid, new Object[0]);
        FileCacheModel model = this.b.get(localid);
        if (model != null) {
            this.b.update(localid, model.tag | 64);
        }
    }

    public void removeRecordById(String id) {
        Logger.D("VideoFileManager", "removeRecordById: " + id, new Object[0]);
        if (id.contains(MergeUtil.SEPARATOR_KV)) {
            this.b.remove(id.substring(id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA) + 1, id.length()));
            id = id.substring(0, id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA));
        }
        this.b.remove(id);
    }

    public int saveVideo(String localId, File targetFile) {
        if (TextUtils.isEmpty(localId)) {
            return -1;
        }
        String path = this.b.getPath(localId);
        if (!FileUtils.checkFile(path)) {
            return -1;
        }
        if (targetFile == null) {
            targetFile = FileUtils.makeTakenVideoPath();
        }
        if (!FileUtils.copyFile(new File(path), targetFile)) {
            return -1;
        }
        MediaUtils.scanPictureAsync(AppUtils.getApplicationContext(), targetFile.getAbsolutePath());
        return 0;
    }

    public DiskCache getDiskCache() {
        return this.b;
    }

    private static boolean a(String id) {
        try {
            Long.parseLong(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
