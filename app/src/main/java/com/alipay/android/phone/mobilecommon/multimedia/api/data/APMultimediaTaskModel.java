package com.alipay.android.phone.mobilecommon.multimedia.api.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APStorageCacheInfo;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "multi_media_task")
public class APMultimediaTaskModel extends APStorageCacheInfo {
    public static final String F_CLOUD_ID = "cloud_id";
    public static final String F_CREATE_TIME = "create_time";
    public static final String F_TASK_STATUS = "status";
    public static final int STATUS_CANCEL = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_RUNNING = 1;
    public static final int STATUS_SUCCESS = 4;
    public static final int STATUS_WAIT = 0;
    @JSONField(name = "cacheId")
    @DatabaseField(columnName = "cache_id")
    private String cacheId;
    @JSONField(name = "cloudId")
    @DatabaseField(columnName = "cloud_id")
    private String cloudId;
    @JSONField(name = "creatTime")
    @DatabaseField(columnName = "create_time")
    private long creatTime;
    @JSONField(name = "currentSize")
    @DatabaseField(columnName = "current_size")
    private long currentSize;
    @JSONField(name = "destPath")
    @DatabaseField(columnName = "dest_path")
    private String destPath;
    @JSONField(deserialize = false, serialize = false)
    public boolean loadFromMemCache = false;
    @JSONField(deserialize = false, serialize = false)
    public boolean loadTaskStatusFromDb;
    @JSONField(name = "sourcePath")
    @DatabaseField(columnName = "source_path")
    private String sourcePath;
    @JSONField(name = "status")
    @DatabaseField(columnName = "status")
    private int status = 0;
    @JSONField(name = "taskId")
    @DatabaseField(columnName = "task_id", id = true)
    private String taskId;
    @JSONField(name = "totalSize")
    @DatabaseField(columnName = "total_size")
    private long totalSize;
    @JSONField(name = "updateTime")
    @DatabaseField(columnName = "update_time")
    private long updateTime;

    public String getCloudId() {
        return this.cloudId;
    }

    public void setCloudId(String cloudId2) {
        this.cloudId = cloudId2;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId2) {
        this.taskId = taskId2;
    }

    public long getCreatTime() {
        return this.creatTime;
    }

    public void setCreatTime(long creatTime2) {
        this.creatTime = creatTime2;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(long updateTime2) {
        this.updateTime = updateTime2;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status2) {
        this.status = status2;
    }

    public String getSourcePath() {
        return this.sourcePath;
    }

    public void setSourcePath(String sourcePath2) {
        this.sourcePath = sourcePath2;
    }

    public String getDestPath() {
        return this.destPath;
    }

    public void setDestPath(String destPath2) {
        this.destPath = destPath2;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long totalSize2) {
        this.totalSize = totalSize2;
    }

    public long getCurrentSize() {
        return this.currentSize;
    }

    public void setCurrentSize(long currentSize2) {
        this.currentSize = currentSize2;
    }

    public String getCacheId() {
        return this.cacheId;
    }

    public void setCacheId(String cacheId2) {
        this.cacheId = cacheId2;
    }

    public int getPercent() {
        if (this.totalSize > 0) {
            return (int) ((((float) this.currentSize) * 100.0f) / ((float) this.totalSize));
        }
        return 0;
    }

    public String toString() {
        return "APMultimediaTaskModel{taskId='" + this.taskId + '\'' + ", creatTime=" + this.creatTime + ", updateTime=" + this.updateTime + ", status=" + this.status + ", sourcePath='" + this.sourcePath + '\'' + ", destPath='" + this.destPath + '\'' + ", totalSize=" + this.totalSize + ", currentSize=" + this.currentSize + ", cacheId='" + this.cacheId + '\'' + ", cloudId='" + this.cloudId + '\'' + '}' + "\n" + super.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.taskId.equals(((APMultimediaTaskModel) o).taskId);
    }

    public void updateStorageCacheInfo() {
        updateCacheId(this.taskId);
        this.cCreateTime = this.creatTime;
        this.cLastModifiedTime = System.currentTimeMillis();
        this.cPath = this.destPath;
        this.cCacheType = APStorageCacheInfo.TYPE_FILE;
        this.cFileSize = this.totalSize;
        this.cExtra = JSON.toJSONString(this);
    }
}
