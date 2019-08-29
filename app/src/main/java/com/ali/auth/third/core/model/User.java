package com.ali.auth.third.core.model;

public class User {
    public String avatarUrl;
    public String deviceTokenKey;
    public String deviceTokenSalt;
    public String nick;
    public String openId;
    public String openSid;
    public String userId;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User [userId=");
        sb.append(this.userId);
        sb.append(", openId=");
        sb.append(this.openId);
        sb.append(", openSid= ");
        sb.append(this.openSid);
        sb.append(", nick=");
        sb.append(this.nick);
        sb.append(",deviceTokenKey=");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.deviceTokenKey);
        sb.append(sb2.toString());
        sb.append(",deviceTokenSalt=");
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.deviceTokenSalt);
        sb.append(sb3.toString());
        sb.append("]");
        return sb.toString();
    }
}
