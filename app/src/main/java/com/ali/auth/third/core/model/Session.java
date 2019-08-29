package com.ali.auth.third.core.model;

public class Session {
    public String avatarUrl;
    public String nick;
    public String openId;
    public String openSid;
    public String topAccessToken;
    public String topAuthCode;
    public String topExpireTime;

    public String toString() {
        StringBuilder sb = new StringBuilder("nick = ");
        sb.append(this.nick);
        sb.append(", ava = ");
        sb.append(this.avatarUrl);
        sb.append(" , openId=");
        sb.append(this.openId);
        sb.append(", openSid=");
        sb.append(this.openSid);
        sb.append(", topAccessToken=");
        sb.append(this.topAccessToken);
        sb.append(", topAuthCode=");
        sb.append(this.topAuthCode);
        sb.append(",topExpireTime=");
        sb.append(this.topExpireTime);
        return sb.toString();
    }
}
