package com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tbl_local_id")
public class LocalIdModel {
    public static final String FIELD_CREATE_TIME = "create_time";
    public static final String FIELD_LAST_ACCESS_TIME = "last_access_time";
    public static final String FIELD_LOCAL_ID = "local_id";
    public static final String FIELD_PATH = "path";
    public static final String TABLE_NAME = "tbl_local_id";
    @DatabaseField(columnName = "create_time")
    public long createTime;
    @DatabaseField(columnName = "last_access_time", index = true)
    public long lastAccessTime;
    @DatabaseField(columnName = "local_id", id = true)
    public String localId;
    @DatabaseField(columnName = "path", index = true)
    public String path;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocalIdModel that = (LocalIdModel) o;
        if (this.localId != null) {
            return this.localId.equals(that.localId);
        }
        if (that.localId != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.localId != null) {
            return this.localId.hashCode();
        }
        return 0;
    }
}
