package com.alipay.mobile.common.nbnet.biz.db;

import android.provider.BaseColumns;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "nbnet_upload_record_table")
public class UploadRecordDo implements BaseColumns {
    public static final String FILE_ID_FIELD = "file_id";
    public static final String GMT_CREATED_FIELD = "gmt_created";
    public static final String GMT_MODIFIED_FIELD = "gmt_modifield";
    public static final String MD5_FIELD = "md5";
    public static final String OFFSET_FIELD = "offset";
    public static final String RESULT_CONTENT_FIELD = "result_content";
    public static final byte STATUS_DONE = 3;
    public static final byte STATUS_UNCONFIRMED = 2;
    public static final byte STATUS_UPLOADING = 1;
    public static final String TRACE_ID_FIELD = "trace_id";
    public static final String UPLOAD_STATUS_FIELD = "upload_status";
    @DatabaseField(columnName = "file_id")
    public String fileId;
    @DatabaseField(canBeNull = false, columnName = "gmt_created")
    public long gmtCreated;
    @DatabaseField(canBeNull = false, columnName = "gmt_modifield")
    public long gmtModifield;
    @DatabaseField(columnName = "_id", generatedId = true)
    public int id;
    @DatabaseField(canBeNull = false, columnName = "md5", index = true, unique = true)
    public String md5;
    @DatabaseField(columnName = "offset")
    public String offset;
    @DatabaseField(columnName = "result_content")
    public String resultContent;
    @DatabaseField(columnName = "trace_id")
    public String traceId;
    @DatabaseField(canBeNull = false, columnName = "upload_status")
    public byte uploadStatus = 2;

    public String toString() {
        return "UploadRecordDo{id=" + this.id + ", md5='" + this.md5 + '\'' + ", uploadStatus=" + this.uploadStatus + ", offset='" + this.offset + '\'' + ", fileId='" + this.fileId + '\'' + ", traceId='" + this.traceId + '\'' + ", resultContent='" + this.resultContent + '\'' + ", gmtModifield=" + this.gmtModifield + ", gmtCreated=" + this.gmtCreated + '}';
    }
}
