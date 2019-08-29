package com.alipay.mobile.security.bio.config.bean;

public class AlertConfig {
    private String a;
    private String b;
    private String c;
    private String d;
    private int e;

    public String getTitle() {
        return this.a;
    }

    public void setTitle(String str) {
        this.a = str;
    }

    public String getLeftButtonText() {
        return this.c;
    }

    public void setLeftButtonText(String str) {
        this.c = str;
    }

    public String getRightButtonText() {
        return this.d;
    }

    public void setRightButtonText(String str) {
        this.d = str;
    }

    public int getReturnCode() {
        return this.e;
    }

    public void setReturnCode(int i) {
        this.e = i;
    }

    public String getMessage() {
        return this.b;
    }

    public void setMessage(String str) {
        this.b = str;
    }

    public String toString() {
        return "AlertConfig{title='" + this.a + '\'' + ", message='" + this.b + '\'' + ", leftButtonText='" + this.c + '\'' + ", rightButtonText='" + this.d + '\'' + ", returnCode=" + this.e + '}';
    }
}
