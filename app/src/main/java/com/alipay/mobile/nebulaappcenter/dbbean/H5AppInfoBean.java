package com.alipay.mobile.nebulaappcenter.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "nebula_app_table")
@Deprecated
public class H5AppInfoBean {
    public static final String COL_POOL_ID = "app_pool_id";
    @DatabaseField
    private String app_dsec;
    @DatabaseField
    private int app_pool_id;
    @DatabaseField
    private int auto_install;
    @DatabaseField
    private String extend_info;
    @DatabaseField
    private String fallback_base_url;
    @DatabaseField
    private String icon_url;
    @DatabaseField
    private String main_url;
    @DatabaseField
    private String name;
    @DatabaseField(generatedId = true, index = true, unique = true)
    private int nbId;
    @DatabaseField
    private int online;
    @DatabaseField
    private String package_url;
    @DatabaseField
    private String patch;
    @DatabaseField
    private Long size;
    @DatabaseField
    private String sub_url;
    @DatabaseField
    private String system_max;
    @DatabaseField
    private String system_min;
    @DatabaseField
    private String vhost;

    public int getNbId() {
        return this.nbId;
    }

    public void setNbId(int nbId2) {
        this.nbId = nbId2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public int getApp_pool_id() {
        return this.app_pool_id;
    }

    public void setApp_pool_id(int app_pool_id2) {
        this.app_pool_id = app_pool_id2;
    }

    public String getPatch() {
        return this.patch;
    }

    public void setPatch(String patch2) {
        this.patch = patch2;
    }

    public int getOnline() {
        return this.online;
    }

    public void setOnline(int online2) {
        this.online = online2;
    }

    public int getAuto_install() {
        return this.auto_install;
    }

    public void setAuto_install(int auto_install2) {
        this.auto_install = auto_install2;
    }

    public String getApp_dsec() {
        return this.app_dsec;
    }

    public void setApp_dsec(String app_dsec2) {
        this.app_dsec = app_dsec2;
    }

    public String getFallback_base_url() {
        return this.fallback_base_url;
    }

    public void setFallback_base_url(String fallback_base_url2) {
        this.fallback_base_url = fallback_base_url2;
    }

    public String getIcon_url() {
        return this.icon_url;
    }

    public void setIcon_url(String icon_url2) {
        this.icon_url = icon_url2;
    }

    public String getSub_url() {
        return this.sub_url;
    }

    public void setSub_url(String sub_url2) {
        this.sub_url = sub_url2;
    }

    public String getVhost() {
        return this.vhost;
    }

    public void setVhost(String vhost2) {
        this.vhost = vhost2;
    }

    public String getExtend_info() {
        return this.extend_info;
    }

    public void setExtend_info(String extend_info2) {
        this.extend_info = extend_info2;
    }

    public String getPackage_url() {
        return this.package_url;
    }

    public void setPackage_url(String package_url2) {
        this.package_url = package_url2;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize(Long size2) {
        this.size = size2;
    }

    public String getMain_url() {
        return this.main_url;
    }

    public void setMain_url(String main_url2) {
        this.main_url = main_url2;
    }

    public String getSystem_max() {
        return this.system_max;
    }

    public void setSystem_max(String system_max2) {
        this.system_max = system_max2;
    }

    public String getSystem_min() {
        return this.system_min;
    }

    public void setSystem_min(String system_min2) {
        this.system_min = system_min2;
    }
}
