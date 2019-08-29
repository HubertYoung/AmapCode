package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tbl_upload_cache")
public class UploadCacheModel {
    public static final String FIELD_DATA = "data";
    public static final String FIELD_ID = "id_index";
    public static final String FIELD_MD5 = "md5";
    @DatabaseField(columnName = "data")
    public String data;
    @DatabaseField(columnName = "id_index", generatedId = true)
    public long idIndex;
    @DatabaseField(columnName = "md5")
    public String md5;

    public UploadCacheModel(String md52, String data2) {
        this.md5 = md52;
        this.data = data2;
    }

    public UploadCacheModel() {
    }

    public String toString() {
        return "UploadCacheModel{idIndex=" + this.idIndex + ", md5='" + this.md5 + '\'' + ", data='" + this.data + '\'' + '}';
    }
}
