package com.alipay.mobile.nebulaappcenter.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "nebula_config")
public class H5AppConfigBean {
    @DatabaseField(defaultValue = "3")
    private int app_pool_limit;
    @DatabaseField
    private String extra;
    @DatabaseField
    private String failed_request_app_list;
    @DatabaseField
    private String last_update_date;
    @DatabaseField(defaultValue = "600")
    private double limitReqRate;
    @DatabaseField(defaultValue = "1800")
    private double normalReqRate;
    @DatabaseField(generatedId = true, index = true, unique = true)
    private int status_id;
    @DatabaseField
    private int strictReqRate;

    public String getExtra() {
        return this.extra;
    }

    public void setExtra(String extra2) {
        this.extra = extra2;
    }

    public int getStrictReqRate() {
        return this.strictReqRate;
    }

    public void setStrictReqRate(int strictReqRate2) {
        this.strictReqRate = strictReqRate2;
    }

    public String getFailed_request_app_list() {
        return this.failed_request_app_list;
    }

    public void setFailed_request_app_list(String failed_request_app_list2) {
        this.failed_request_app_list = failed_request_app_list2;
    }

    public String getLast_update_date() {
        return this.last_update_date;
    }

    public void setLast_update_date(String last_update_date2) {
        this.last_update_date = last_update_date2;
    }

    public int getApp_pool_limit() {
        return this.app_pool_limit;
    }

    public void setApp_pool_limit(int app_pool_limit2) {
        this.app_pool_limit = app_pool_limit2;
    }

    public int getStatus_id() {
        return this.status_id;
    }

    public void setStatus_id(int status_id2) {
        this.status_id = status_id2;
    }

    public double getNormalReqRate() {
        return this.normalReqRate;
    }

    public void setNormalReqRate(double normalReqRate2) {
        this.normalReqRate = normalReqRate2;
    }

    public double getLimitReqRate() {
        return this.limitReqRate;
    }

    public void setLimitReqRate(double limitReqRate2) {
        this.limitReqRate = limitReqRate2;
    }
}
