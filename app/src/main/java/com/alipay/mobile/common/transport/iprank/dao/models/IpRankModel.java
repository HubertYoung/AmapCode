package com.alipay.mobile.common.transport.iprank.dao.models;

public class IpRankModel {
    public long _id = -1;
    public String domain = "";
    public int failCount = 0;
    public int feedbackSuccCount = 0;
    public long feedbackSuccTime = -1;
    public float grade = -1.0f;
    public String ip = "";
    public long lastSuccTime = -1;
    public long lbs_id = -1;
    public int netType = -1;
    public int rtt = 0;
    public int successCount = 0;
    public long time = -1;
    public long ttl = -1;

    public IpRankModel() {
    }

    public IpRankModel(long lbs_id2, String domain2, String ip2, long time2, long ttl2, int netType2, int rtt2, int successCount2, int failCount2, int feedbackSuccCount2, long feedbackSuccTime2, long lastSuccTime2, float grade2) {
        this.lbs_id = lbs_id2;
        this.domain = domain2;
        this.ip = ip2;
        this.time = time2;
        this.ttl = ttl2;
        this.netType = netType2;
        this.rtt = rtt2;
        this.successCount = successCount2;
        this.failCount = failCount2;
        this.feedbackSuccCount = feedbackSuccCount2;
        this.feedbackSuccTime = feedbackSuccTime2;
        this.lastSuccTime = lastSuccTime2;
        this.grade = grade2;
    }

    public boolean isTimeOut() {
        if (System.currentTimeMillis() > this.ttl) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "IpRankModel{_id=" + this._id + ", lbs_id=" + this.lbs_id + ", domain='" + this.domain + '\'' + ", ip='" + this.ip + '\'' + ", time=" + this.time + ", ttl=" + this.ttl + ", netType=" + this.netType + ", rtt=" + this.rtt + ", successCount=" + this.successCount + ", failCount=" + this.failCount + ", feedbackSuccCount=" + this.feedbackSuccCount + ", feedbackSuccTime=" + this.feedbackSuccTime + ", lastSuccTime=" + this.lastSuccTime + ", grade=" + this.grade + '}';
    }
}
