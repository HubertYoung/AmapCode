package com.alipay.mobile.security.bio.api;

import android.os.Bundle;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BioParameter implements Serializable {
    private static final long serialVersionUID = 111;
    private boolean autoClose = true;
    private Bundle bundle;
    private Map<String, String> extProperty = new HashMap();
    private String headImageUrl;
    public boolean isValidate = false;
    private String protocol;
    private String remoteURL;

    public void addExtProperty(String str, String str2) {
        this.extProperty.put(str, str2);
    }

    public Map<String, String> getExtProperty() {
        return this.extProperty;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String str) {
        this.protocol = str;
    }

    public String getHeadImageUrl() {
        return this.headImageUrl;
    }

    public void setHeadImageUrl(String str) {
        this.headImageUrl = str;
    }

    public String getRemoteURL() {
        return this.remoteURL;
    }

    public void setRemoteURL(String str) {
        this.remoteURL = str;
    }

    public boolean isAutoClose() {
        return this.autoClose;
    }

    public void setAutoClose(boolean z) {
        this.autoClose = z;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void setBundle(Bundle bundle2) {
        this.bundle = bundle2;
    }
}
