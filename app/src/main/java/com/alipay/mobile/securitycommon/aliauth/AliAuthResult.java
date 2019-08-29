package com.alipay.mobile.securitycommon.aliauth;

public class AliAuthResult {
    public String ecode;
    public String memo;
    public String noticeUrl;
    public String redirectUrl;
    public String resultStatus;
    public String sid;
    public String statusAction;
    public boolean success;
    public String tbNick;
    public String tbUserId;
    public long timeStamp;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[success:");
        sb.append(this.success);
        sb.append(", ");
        sb.append("sid:");
        sb.append(this.sid);
        sb.append(", ");
        sb.append("ecode:");
        sb.append(this.ecode);
        sb.append(", ");
        sb.append("tbUserId:");
        sb.append(this.tbUserId);
        sb.append(", ");
        sb.append("tbNick:");
        sb.append(this.tbNick);
        sb.append(", ");
        sb.append("noticeUrl:");
        sb.append(this.noticeUrl);
        sb.append(", ");
        sb.append("redirectUrl:");
        sb.append(this.redirectUrl);
        sb.append(", ");
        sb.append("statusAction:");
        sb.append(this.statusAction);
        sb.append("]");
        return super.toString();
    }
}
