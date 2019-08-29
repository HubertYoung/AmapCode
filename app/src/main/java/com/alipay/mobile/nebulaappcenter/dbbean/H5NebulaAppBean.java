package com.alipay.mobile.nebulaappcenter.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "nebula_app_info_table")
public class H5NebulaAppBean {
    public static final String COL_APP_ID = "app_id";
    public static final String COL_APP_POOL_ID = "app_pool_id";
    public static final String COL_IS_LIMIT = "is_limit";
    public static final String COL_MAP = "is_mapping";
    public static final String COL_UNAVAIL_REASON = "unavailable_reason";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_VERSION = "version";
    public static final String LOCAL_REPORT = "localReport";
    @DatabaseField
    public int app_channel;
    @DatabaseField
    private String app_dsec;
    @DatabaseField
    private String app_id;
    @DatabaseField
    public int app_type;
    @DatabaseField
    private int auto_install;
    @DatabaseField
    private String extend_info;
    @DatabaseField
    private String fallback_base_url;
    @DatabaseField
    private String icon_url;
    @DatabaseField
    private int is_limit;
    @DatabaseField
    private int is_mapping;
    @DatabaseField
    private int localReport;
    @DatabaseField
    private String main_url;
    @DatabaseField
    private String name;
    @DatabaseField
    private String nbAppType;
    @DatabaseField(generatedId = true, index = true, unique = true)
    private int nbId;
    @DatabaseField
    private String nbl_id;
    @DatabaseField
    private int online;
    @DatabaseField
    private String package_url;
    @DatabaseField
    private String patch;
    @DatabaseField
    private String release_type;
    @DatabaseField
    private Long size;
    @DatabaseField
    private String slogan;
    @DatabaseField
    private String sub_url;
    @DatabaseField
    private String system_max;
    @DatabaseField
    private String system_min;
    @DatabaseField
    private String third_platform;
    @DatabaseField(columnName = "unavailable_reason", defaultValue = "")
    private String unAvailableReason;
    @DatabaseField
    private String update_app_time;
    @DatabaseField(columnName = "user_id")
    private String user_id;
    @DatabaseField
    private String version;
    @DatabaseField
    private String vhost;

    public String getSlogan() {
        return this.slogan;
    }

    public void setSlogan(String slogan2) {
        this.slogan = slogan2;
    }

    public String getNbl_id() {
        return this.nbl_id;
    }

    public void setNbl_id(String nblId) {
        this.nbl_id = nblId;
    }

    public String getNbAppType() {
        return this.nbAppType;
    }

    public void setNbAppType(String NbAppType) {
        this.nbAppType = NbAppType;
    }

    public int getLocalReport() {
        return this.localReport;
    }

    public void setLocalReport(int localReport2) {
        this.localReport = localReport2;
    }

    public String getRelease_type() {
        return this.release_type;
    }

    public void setRelease_type(String release_type2) {
        this.release_type = release_type2;
    }

    public int getApp_channel() {
        return this.app_channel;
    }

    public void setApp_channel(int app_channel2) {
        this.app_channel = app_channel2;
    }

    public int getApp_type() {
        return this.app_type;
    }

    public void setApp_type(int app_type2) {
        this.app_type = app_type2;
    }

    public String getThird_platform() {
        return this.third_platform;
    }

    public void setThird_platform(String third_platform2) {
        this.third_platform = third_platform2;
    }

    public int getIs_limit() {
        return this.is_limit;
    }

    public void setIs_limit(int is_limit2) {
        this.is_limit = is_limit2;
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

    public String getVersion() {
        return this.version;
    }

    public String getApp_id() {
        return this.app_id;
    }

    public void setApp_id(String app_id2) {
        this.app_id = app_id2;
    }

    public void setVersion(String version2) {
        this.version = version2;
    }

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
        return Long.valueOf(this.size == null ? 0 : this.size.longValue());
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

    public String getUnAvailableReason() {
        return this.unAvailableReason;
    }

    public void setUnAvailableReason(String unAvailableReason2) {
        this.unAvailableReason = unAvailableReason2;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id2) {
        this.user_id = user_id2;
    }
}
