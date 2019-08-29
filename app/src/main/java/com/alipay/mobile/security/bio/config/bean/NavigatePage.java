package com.alipay.mobile.security.bio.config.bean;

public class NavigatePage {
    public static final String DEFAULT_URL = "https://render.alipay.com/p/f/fd-j8l9yjja/index.html";
    private boolean a = false;
    private String b = DEFAULT_URL;

    public void setEnable(boolean z) {
        this.a = z;
    }

    public boolean isEnable() {
        return this.a;
    }

    public void setUrl(String str) {
        this.b = str;
    }

    public String getUrl() {
        return this.b;
    }

    public String toString() {
        return "NavigatePage{enable=" + this.a + ", url='" + this.b + '\'' + '}';
    }
}
