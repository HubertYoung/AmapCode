package com.alipay.mobile.nebulaappcenter.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "nebula_app_pool")
@Deprecated
public class H5AppPoolBean {
    public static final String COL_APP_ID = "app_id";
    public static final String COL_APP_POOL_ID = "app_pool_id";
    public static final String COL_IS_LIMIT = "is_limit";
    public static final String COL_MAP = "is_mapping";
    public static final String COL_VERSION = "version";
    @DatabaseField
    private String app_id;
    @DatabaseField(generatedId = true, index = true, unique = true)
    private int app_pool_id;
    @DatabaseField
    private int is_limit;
    @DatabaseField
    private int is_mapping;
    @DatabaseField
    private String nbl_id;
    @DatabaseField
    private String update_app_time;
    @DatabaseField
    private String version;

    public String getNbl_id() {
        return this.nbl_id;
    }

    public void setNbl_id(String nbl_id2) {
        this.nbl_id = nbl_id2;
    }

    public int getApp_pool_id() {
        return this.app_pool_id;
    }

    public void setApp_pool_id(int app_pool_id2) {
        this.app_pool_id = app_pool_id2;
    }

    public String getApp_id() {
        return this.app_id;
    }

    public void setApp_id(String app_id2) {
        this.app_id = app_id2;
    }

    public int getIs_mapping() {
        return this.is_mapping;
    }

    public void setIs_mapping(int is_mapping2) {
        this.is_mapping = is_mapping2;
    }

    public String getUpdate_app_time() {
        return this.update_app_time;
    }

    public void setUpdate_app_time(String update_app_time2) {
        this.update_app_time = update_app_time2;
    }

    public int getIs_limit() {
        return this.is_limit;
    }

    public void setIs_limit(int is_limit2) {
        this.is_limit = is_limit2;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version2) {
        this.version = version2;
    }
}
