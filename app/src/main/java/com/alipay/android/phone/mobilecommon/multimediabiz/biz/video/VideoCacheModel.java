package com.alipay.android.phone.mobilecommon.multimediabiz.biz.video;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APStorageCacheInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tbl_video_cache")
public class VideoCacheModel extends APStorageCacheInfo {
    public static final String FIELD_CLOUD_ID = "cloud_id";
    public static final String FIELD_CREATE_TIME = "create_time";
    public static final String FIELD_DELETE_BY_USER = "deleteByUser";
    public static final String FIELD_ID = "id";
    public static final String FIELD_LOCAL_ID = "local_id";
    public static final String FIELD_PATH = "path";
    public static final String FIELD_STORE_TYPE = "store_type";
    public static final String FIELD_TYPE = "type";
    public static final int STORE_TYPE_ALBUM_VIDEO = 1;
    public static final int STORE_TYPE_ALBUM_VIDEO_THUMB = 0;
    public static final int STORE_TYPE_SHORT_VIDEO = 3;
    public static final int STORE_TYPE_SHORT_VIDEO_THUMB = 2;
    @JSONField(name = "cloudId")
    @DatabaseField(columnName = "cloud_id", defaultValue = "")
    public String cloudId;
    @JSONField(name = "createTime")
    @DatabaseField(columnName = "create_time", defaultValue = "0")
    public long createTime;
    @JSONField(name = "deleteByUser")
    @DatabaseField(columnName = "deleteByUser", defaultValue = "0")
    public int deleteByUser;
    @JSONField(name = "id")
    @DatabaseField(generatedId = true)
    public int id;
    @JSONField(name = "localId")
    @DatabaseField(columnName = "local_id", defaultValue = "")
    public String localId;
    @JSONField(name = "path")
    @DatabaseField(columnName = "path", defaultValue = "")
    public String path;
    @JSONField(name = "storeType")
    @DatabaseField(columnName = "store_type")
    public int storeType;
    @JSONField(name = "type")
    @DatabaseField(columnName = "type", defaultValue = "-1")
    public int type;

    public VideoCacheModel() {
    }

    public VideoCacheModel(String path2, String cloudid, String localid, int type2, int storeType2) {
        this.path = path2;
        this.cloudId = cloudid;
        this.localId = localid;
        this.type = type2;
        this.storeType = storeType2;
        if (TextUtils.isEmpty(localid)) {
            this.createTime = System.currentTimeMillis();
        } else {
            this.createTime = Long.parseLong(localid);
        }
    }

    public void updateStorageCacheInfo() {
        if (TextUtils.isEmpty(this.cId)) {
            this.cId = TextUtils.isEmpty(this.cloudId) ? this.localId : this.cloudId;
        }
        this.cPath = this.path;
        this.cCreateTime = this.createTime;
        this.cLastModifiedTime = System.currentTimeMillis();
        this.cFileSize = FileUtils.fileSize(this.path);
        this.cExtra = JSON.toJSONString(this);
        this.cCacheType = APStorageCacheInfo.TYPE_VIDEO;
    }

    public String toString() {
        return "VideoCacheModel{id=" + this.id + ", path='" + this.path + '\'' + ", cloudId='" + this.cloudId + '\'' + ", localId='" + this.localId + '\'' + ", deleteByUser=" + this.deleteByUser + ", createTime=" + this.createTime + ", type=" + this.type + '}' + "\n" + super.toString();
    }
}
