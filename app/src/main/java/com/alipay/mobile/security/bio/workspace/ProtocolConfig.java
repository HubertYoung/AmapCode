package com.alipay.mobile.security.bio.workspace;

public class ProtocolConfig {
    private int env = 0;
    private NavPageConfig navigatepage;
    private int ui = 991;
    String version = "1.0";

    public int getEnv() {
        return this.env;
    }

    public void setEnv(int i) {
        this.env = i;
    }

    public NavPageConfig getNavigatepage() {
        return this.navigatepage;
    }

    public void setNavigatepage(NavPageConfig navPageConfig) {
        this.navigatepage = navPageConfig;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public int getUi() {
        return this.ui;
    }

    public void setUi(int i) {
        this.ui = i;
    }
}
