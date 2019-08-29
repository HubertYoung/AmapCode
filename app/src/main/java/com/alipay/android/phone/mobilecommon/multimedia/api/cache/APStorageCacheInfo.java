package com.alipay.android.phone.mobilecommon.multimedia.api.cache;

import android.text.TextUtils;
import com.alibaba.fastjson.annotation.JSONField;
import com.j256.ormlite.field.DatabaseField;

public abstract class APStorageCacheInfo {
    public static final String F_CACHE_BUSINESS_ID = "f_cache_business_id";
    public static final String F_CACHE_CREATE_TIME = "f_cache_create_time";
    public static final String F_CACHE_EXTRA = "f_cache_extra";
    public static final String F_CACHE_FILE_SIZE = "f_cache_file_size";
    public static final String F_CACHE_ID = "f_cache_id";
    public static final String F_CACHE_LAST_MODIFIED_TIME = "f_cache_last_modified_time";
    public static final String F_CACHE_LOCK = "f_cache_lock";
    public static final String F_CACHE_PATH = "f_cache_path";
    public static final String F_CACHE_TYPE = "f_cache_type";
    @JSONField(serialize = false)
    public static final String TYPE_AUDIO = "audio_cache";
    @JSONField(serialize = false)
    public static final String TYPE_FILE = "file_cache";
    @JSONField(serialize = false)
    public static final String TYPE_IMAGE = "image_cache";
    @JSONField(serialize = false)
    public static final String TYPE_IM_IMAGE = "im_image_cache";
    @JSONField(serialize = false)
    public static final String TYPE_VIDEO = "video_cache";
    @JSONField(serialize = false)
    @DatabaseField(columnName = "f_cache_business_id", defaultValue = "mm_other", index = true)
    public String cBusinessId;
    @JSONField(serialize = false)
    @DatabaseField(columnName = "f_cache_type")
    public String cCacheType;
    @JSONField(serialize = false)
    @DatabaseField(columnName = "f_cache_create_time", index = true)
    public long cCreateTime;
    @JSONField(serialize = false)
    @DatabaseField(columnName = "f_cache_extra")
    public String cExtra;
    @JSONField(serialize = false)
    @DatabaseField(columnName = "f_cache_file_size")
    public long cFileSize;
    @JSONField(serialize = false)
    @DatabaseField(columnName = "f_cache_id", index = true)
    public String cId;
    @JSONField(serialize = false)
    @DatabaseField(columnName = "f_cache_last_modified_time", index = true)
    public long cLastModifiedTime;
    @JSONField(serialize = false)
    @DatabaseField(columnName = "f_cache_lock", defaultValue = "0")
    public boolean cLock = false;
    @JSONField(serialize = false)
    @DatabaseField(columnName = "f_cache_path")
    public String cPath;

    public abstract void updateStorageCacheInfo();

    public void updateCacheId(String id) {
        if (TextUtils.isEmpty(this.cId)) {
            this.cId = id;
        }
    }

    public String toString() {
        return "APStorageCacheInfo{cId='" + this.cId + '\'' + ", cPath='" + this.cPath + '\'' + ", cFileSize=" + this.cFileSize + ", cCreateTime=" + this.cCreateTime + ", cLastModifiedTime=" + this.cLastModifiedTime + ", cLock=" + this.cLock + ", cBusinessId='" + this.cBusinessId + '\'' + ", cCacheType='" + this.cCacheType + '\'' + ", cExtra='" + this.cExtra + '\'' + '}';
    }
}
