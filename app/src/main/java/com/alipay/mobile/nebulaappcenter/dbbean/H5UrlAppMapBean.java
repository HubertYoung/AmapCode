package com.alipay.mobile.nebulaappcenter.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "nebula_app_urlmap_table")
public class H5UrlAppMapBean {
    public static final String COL_APPID = "app_id";
    public static final String COL_PUBLICURL = "public_url";
    public static final String COL_USER_ID = "user_id";
    @DatabaseField(columnName = "app_id")
    private String appId;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "public_url", index = true, uniqueCombo = true)
    private String publicUrl;
    @DatabaseField(columnName = "user_id", uniqueCombo = true)
    private String userId;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public String getPublicUrl() {
        return this.publicUrl;
    }

    public void setPublicUrl(String publicUrl2) {
        this.publicUrl = publicUrl2;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId2) {
        this.appId = appId2;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId2) {
        this.userId = userId2;
    }
}
