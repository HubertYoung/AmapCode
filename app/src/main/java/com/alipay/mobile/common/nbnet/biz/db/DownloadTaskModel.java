package com.alipay.mobile.common.nbnet.biz.db;

import android.provider.BaseColumns;
import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetAssertionException;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.AssertUtil;
import com.j256.ormlite.field.DatabaseField;

public class DownloadTaskModel implements BaseColumns {
    public static final String TABLE_NAME = "download_task";
    public static final String _FILE_ID = "_file_id";
    public static final String _FILE_LENGTH = "_file_length";
    public static final String _FILE_MD5 = "_file_md5";
    public static final String _LAST_MODIFIED = "_last_modified";
    public static final String _REQUEST_ID = "_request_id";
    @DatabaseField(canBeNull = false, columnName = "_file_id")
    public String fileId;
    @DatabaseField(columnName = "_file_length")
    public int fileLength;
    @DatabaseField(columnName = "_file_md5")
    public String fileMD5;
    @DatabaseField(columnName = "_id", generatedId = true)
    public int id;
    @DatabaseField(columnName = "_last_modified", index = true)
    public long lastModified;
    @DatabaseField(canBeNull = false, columnName = "_request_id", index = true)
    public int requestId;

    public boolean equals(Object o) {
        boolean z;
        boolean z2;
        boolean z3;
        if (this == o) {
            return true;
        }
        try {
            AssertUtil.a(o instanceof DownloadTaskModel);
            DownloadTaskModel taskModel = (DownloadTaskModel) o;
            if (this.requestId == taskModel.requestId) {
                z = true;
            } else {
                z = false;
            }
            AssertUtil.a(z);
            if (this.lastModified == taskModel.lastModified) {
                z2 = true;
            } else {
                z2 = false;
            }
            AssertUtil.a(z2);
            if (this.fileLength == taskModel.fileLength) {
                z3 = true;
            } else {
                z3 = false;
            }
            AssertUtil.a(z3);
            AssertUtil.a(TextUtils.equals(this.fileId, taskModel.fileId));
            AssertUtil.a(TextUtils.equals(this.fileMD5, taskModel.fileMD5));
            return true;
        } catch (NBNetAssertionException assertException) {
            NBNetLogCat.d("DownloadTaskModel", "equals exception: " + assertException.toString());
            return false;
        }
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int i3 = this.requestId * 31;
        if (this.fileId != null) {
            i = this.fileId.hashCode();
        } else {
            i = 0;
        }
        int i4 = (((((i3 + i) * 31) + ((int) (this.lastModified ^ (this.lastModified >>> 32)))) * 31) + this.fileLength) * 31;
        if (!TextUtils.isEmpty(this.fileMD5)) {
            i2 = this.fileMD5.hashCode();
        }
        return i4 + i2;
    }

    public String toString() {
        return "requestId=" + this.requestId + ", fileId='" + this.fileId + '\'' + ", fileLength=" + this.fileLength + ", fileMD5=" + (this.fileMD5 != null ? this.fileMD5 : "") + ", lastModified=" + this.lastModified;
    }
}
