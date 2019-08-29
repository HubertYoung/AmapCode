package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Arrays;

public class LogConf {
    @JSONField(name = "cancelswitch")
    public int cancelswitch = 1;
    @JSONField(name = "lbl")
    public String[] logBlacklist = {"UC-MM-C01", "UC-MM-C04", "UC-MM-C20"};
    @JSONField(name = "maxc")
    public int maxErrorCount = 5;
    @JSONField(name = "rp")
    public long reportPeriod = 300000;
    @JSONField(name = "uans")
    public int uaNewSwitch = 1;
    @JSONField(name = "uaswitch")
    public int uaswitch = 1;

    public String toString() {
        return "LogConf{uaswitch=" + this.uaswitch + ", cancelswitch=" + this.cancelswitch + ", logBlacklist=" + Arrays.toString(this.logBlacklist) + ", reportPeriod=" + this.reportPeriod + ", maxErrorCount=" + this.maxErrorCount + ", uaNewSwitch=" + this.uaNewSwitch + '}';
    }
}
