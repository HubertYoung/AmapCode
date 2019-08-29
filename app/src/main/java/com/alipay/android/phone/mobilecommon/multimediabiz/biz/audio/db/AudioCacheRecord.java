package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.db;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APStorageCacheInfo;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tbl_audio_cache")
public class AudioCacheRecord extends APStorageCacheInfo {
    public static final String FIELD_AUDIO_ID = "audio_id";
    public static final String FIELD_CREATE_TIME = "create_time";
    public static final String FIELD_FILE_PATH = "file_path";
    public static final String FIELD_FILE_SIZE = "file_size";
    public static final String FIELD_LAST_MODIFIED_TIME = "last_modified_time";
    @JSONField(name = "audioId")
    @DatabaseField(columnName = "audio_id", id = true)
    public String audioId;
    @JSONField(name = "createTime")
    @DatabaseField(columnName = "create_time")
    public long createTime;
    @JSONField(name = "filePath")
    @DatabaseField(columnName = "file_path")
    public String filePath;
    @JSONField(name = "fileSize")
    @DatabaseField(columnName = "file_size")
    public long fileSize;
    @JSONField(name = "lastModifiedTime")
    @DatabaseField(columnName = "last_modified_time")
    public long lastModifiedTime;

    public void updateStorageCacheInfo() {
        if (TextUtils.isEmpty(this.cId)) {
            this.cId = this.audioId;
        }
        this.cPath = this.filePath;
        this.cFileSize = this.fileSize;
        this.cCreateTime = this.createTime;
        this.cLastModifiedTime = this.lastModifiedTime;
        this.cCacheType = APStorageCacheInfo.TYPE_AUDIO;
        this.cExtra = JSON.toJSONString(this);
    }

    public AudioCacheRecord() {
    }

    public AudioCacheRecord(String audioId2, String filePath2, long fileSize2, long lastModifiedTime2, long createTime2, String businessId) {
        this.audioId = audioId2;
        this.filePath = filePath2;
        this.fileSize = fileSize2;
        this.lastModifiedTime = lastModifiedTime2;
        this.createTime = createTime2;
        this.cBusinessId = businessId;
    }

    public String toString() {
        return "AudioCacheRecord{audioId='" + this.audioId + '\'' + ", filePath='" + this.filePath + '\'' + ", fileSize=" + this.fileSize + ", lastModifiedTime=" + this.lastModifiedTime + ", createTime=" + this.createTime + '}' + "\n" + super.toString();
    }
}
