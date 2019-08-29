package com.alipay.android.phone.mobilecommon.multimedia.audio.data;

public class APRequestParam {
    private String ACL;
    private String UID;

    public APRequestParam(String ACL2, String UID2) {
        this.ACL = ACL2;
        this.UID = UID2;
    }

    public String getACL() {
        return this.ACL;
    }

    public void setACL(String ACL2) {
        this.ACL = ACL2;
    }

    public String getUID() {
        return this.UID;
    }

    public void setUID(String UID2) {
        this.UID = UID2;
    }

    public String toString() {
        return "APRequestParam{ACL='" + this.ACL + '\'' + ", UID='" + this.UID + '\'' + '}';
    }
}
