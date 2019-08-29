package com.alipay.mobile.nebulaappcenter.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "nebula_app_install")
public class H5AppInstallBean {
    public static final String COL_APP_ID = "install_appId";
    public static final String COL_USER_ID = "user_id";
    @DatabaseField
    private String installPath;
    @DatabaseField
    private String install_appId;
    @DatabaseField(generatedId = true, index = true, unique = true)
    private int install_id;
    @DatabaseField(columnName = "user_id")
    private String install_userId;
    @DatabaseField
    private String install_version;

    public String getInstallPath() {
        return this.installPath;
    }

    public void setInstallPath(String installPath2) {
        this.installPath = installPath2;
    }

    public String getInstall_appId() {
        return this.install_appId;
    }

    public void setInstall_appId(String install_appId2) {
        this.install_appId = install_appId2;
    }

    public String getInstall_version() {
        return this.install_version;
    }

    public void setInstall_version(String install_version2) {
        this.install_version = install_version2;
    }

    public int getInstall_id() {
        return this.install_id;
    }

    public void setInstall_id(int install_id2) {
        this.install_id = install_id2;
    }

    public String getInstall_userId() {
        return this.install_userId;
    }

    public void setInstall_userId(String install_userId2) {
        this.install_userId = install_userId2;
    }
}
