package com.alipay.android.phone.mobilecommon.multimedia.api.data;

import android.text.TextUtils;
import java.util.Map;

public class BaseInfo {
    public static final int PRIORITY_HIGH = 10;
    public static final int PRIORITY_LOW = 1;
    public static final int PRIORITY_MID = 5;
    private boolean bHttps = false;
    private String bizType;
    public String businessId;
    private long expiredTime = Long.MAX_VALUE;
    public Map<String, String> extInfo;
    private int mTimeout = -1;
    private String md5;
    private Integer priority = Integer.valueOf(5);

    public int getPriority() {
        return this.priority.intValue();
    }

    public void setPriority(int priority2) {
        if (priority2 > 0 && priority2 <= 10) {
            this.priority = Integer.valueOf(priority2);
        }
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md52) {
        this.md5 = md52;
    }

    public boolean isHttps() {
        return this.bHttps;
    }

    public void setHttps(boolean bHttps2) {
        this.bHttps = bHttps2;
    }

    public String getBizType() {
        if (TextUtils.isEmpty(this.bizType)) {
            this.bizType = this.businessId;
        }
        return this.bizType;
    }

    public BaseInfo setBizType(String bizType2) {
        this.bizType = bizType2;
        return this;
    }

    public String getBusinessId() {
        return this.businessId;
    }

    public BaseInfo setBusinessId(String businessId2) {
        this.businessId = businessId2;
        return this;
    }

    public long getExpiredTime() {
        return this.expiredTime;
    }

    public void setExpiredTime(long expiredTime2) {
        this.expiredTime = expiredTime2;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public void setTimeout(int timeout) {
        this.mTimeout = timeout;
    }

    public String toString() {
        return "BaseInfo{md5='" + this.md5 + '\'' + ", priority=" + this.priority + ", bHttps=" + this.bHttps + ", bizType='" + this.bizType + '\'' + ", businessId='" + this.businessId + '\'' + ", extInfo='" + this.extInfo + '\'' + ", expiredTime='" + this.expiredTime + '\'' + "，mTimeout=" + this.mTimeout + '\'' + '}';
    }
}
