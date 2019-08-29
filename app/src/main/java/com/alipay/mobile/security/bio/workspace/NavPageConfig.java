package com.alipay.mobile.security.bio.workspace;

public class NavPageConfig {
    private boolean a = true;
    private String b = "";
    private String c = "";
    private String d = "";
    private boolean e = false;

    public boolean isEnable() {
        return this.a;
    }

    public void setEnable(boolean z) {
        this.a = z;
    }

    public String getVersion() {
        return this.b;
    }

    public void setVersion(String str) {
        this.b = str;
    }

    public String getUserNameHidden() {
        return this.c;
    }

    public void setUserNameHidden(String str) {
        this.c = str;
    }

    public String getUrl() {
        return this.d;
    }

    public void setUrl(String str) {
        this.d = str;
    }

    public boolean isTitleVisible() {
        return this.e;
    }

    public void setTitleVisible(boolean z) {
        this.e = z;
    }
}
