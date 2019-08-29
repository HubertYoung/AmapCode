package com.alipay.mobile.nebula.appcenter.model;

import java.io.Serializable;
import java.util.Map;

public class AppInfo implements Serializable {
    private static final long serialVersionUID = -8934694297420862082L;
    public int app_channel;
    public String app_dsec;
    public String app_id;
    public int app_type;
    public int auto_install;
    public Map<String, String> extend_info;
    public String extend_info_jo;
    public String fallback_base_url;
    public String fromPreset;
    public String icon_url;
    public int is_limit;
    public int is_mapping;
    public int localReport;
    public String main_url;
    public String name;
    public String nbAppType;
    public String nbl_id;
    public int online;
    public String package_url;
    public String patch;
    public String release_type;
    public String scene;
    public long size;
    public String slogan;
    public String sub_url;
    public String syncTime;
    public String system_max;
    public String system_min;
    public String third_platform;
    public String unAvailableReason;
    public String update_app_time;
    public String version;
    public String vhost;

    public String toString() {
        return "AppInfo{name='" + this.name + '\'' + ", version='" + this.version + '\'' + ", patch='" + this.patch + '\'' + ", online=" + this.online + ", auto_install=" + this.auto_install + ", app_dsec='" + this.app_dsec + '\'' + ", fallback_base_url='" + this.fallback_base_url + '\'' + ", icon_url='" + this.icon_url + '\'' + ", sub_url='" + this.sub_url + '\'' + ", main_url='" + this.main_url + '\'' + ", vhost='" + this.vhost + '\'' + ", extend_info=" + this.extend_info + ", extend_info_jo='" + this.extend_info_jo + '\'' + ", nbl_id='" + this.nbl_id + '\'' + ", app_id='" + this.app_id + '\'' + ", package_url='" + this.package_url + '\'' + ", size=" + this.size + ", system_max='" + this.system_max + '\'' + ", system_min='" + this.system_min + '\'' + ", third_platform='" + this.third_platform + '\'' + ", app_type=" + this.app_type + ", app_channel=" + this.app_channel + ", release_type='" + this.release_type + '\'' + ", is_mapping=" + this.is_mapping + ", update_app_time='" + this.update_app_time + '\'' + ", is_limit=" + this.is_limit + ", fromPreset='" + this.fromPreset + '\'' + ", localReport=" + this.localReport + ", nbAppType=" + this.nbAppType + ", slogan=" + this.slogan + '}';
    }
}
