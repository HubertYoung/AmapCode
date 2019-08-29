package com.alipay.mobile.nebula.config;

public class H5EmbedViewConfig {
    public static final String TAG = "H5EmbedViewConfig";
    private String bundleName;
    private String className;
    private String type;

    public H5EmbedViewConfig(String bundleName2, String className2, String type2) {
        this.bundleName = bundleName2;
        this.className = className2;
        this.type = type2;
    }

    public String getBundleName() {
        return this.bundleName;
    }

    public String getClassName() {
        return this.className;
    }

    public String getType() {
        return this.type;
    }
}
