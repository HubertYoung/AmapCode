package com.alibaba.appmonitor.offline;

import com.alibaba.analytics.core.db.annotation.Column;
import com.alibaba.analytics.core.db.annotation.TableName;

@TableName("alarm_temp")
public class TempAlarm extends TempEvent {
    @Column("arg")
    public String arg;
    @Column("err_code")
    public String errCode;
    @Column("err_msg")
    public String errMsg;
    @Column("success")
    public String success;

    public TempAlarm() {
    }

    public TempAlarm(String str, String str2, String str3, String str4, String str5, boolean z, String str6, String str7) {
        super(str, str2, str6, str7);
        this.arg = str3;
        this.errCode = str4;
        this.errMsg = str5;
        this.success = z ? "1" : "0";
    }

    public boolean isSuccessEvent() {
        return "1".equalsIgnoreCase(this.success);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("TempAlarm{");
        sb.append(" module='");
        sb.append(this.module);
        sb.append('\'');
        sb.append(", monitorPoint='");
        sb.append(this.monitorPoint);
        sb.append('\'');
        sb.append(", commitTime=");
        sb.append(this.commitTime);
        sb.append(", access='");
        sb.append(this.access);
        sb.append('\'');
        sb.append(", accessSubType='");
        sb.append(this.accessSubType);
        sb.append('\'');
        sb.append(", arg='");
        sb.append(this.arg);
        sb.append('\'');
        sb.append(", errCode='");
        sb.append(this.errCode);
        sb.append('\'');
        sb.append(", errMsg='");
        sb.append(this.errMsg);
        sb.append('\'');
        sb.append(", success='");
        sb.append(this.success);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
