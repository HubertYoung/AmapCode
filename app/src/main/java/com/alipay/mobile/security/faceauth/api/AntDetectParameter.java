package com.alipay.mobile.security.faceauth.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AntDetectParameter implements Serializable {
    private static final long serialVersionUID = 111;
    private int action = 0;
    private String apdid = "";
    private int appID;
    private boolean autoClose = true;
    private boolean enableNavPage = true;
    private boolean enablePrePoseAlert = true;
    private String extJson = "";
    protected Map<String, String> extProperty = new HashMap();
    private String protocol;
    private String remoteUrl = "";
    private int scene = 0;
    private String tag = "";

    public String getRemoteUrl() {
        return this.remoteUrl;
    }

    public void setRemoteUrl(String str) {
        this.remoteUrl = str;
    }

    public int getAction() {
        return this.action;
    }

    public void setAction(int i) {
        this.action = i;
    }

    public String getExtJson() {
        return this.extJson;
    }

    public void setExtJson(String str) {
        this.extJson = str;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public int getAppID() {
        return this.appID;
    }

    public void setAppID(int i) {
        this.appID = i;
    }

    public boolean isAutoClose() {
        return this.autoClose;
    }

    public void setAutoClose(boolean z) {
        this.autoClose = z;
    }

    public String getApdid() {
        return this.apdid;
    }

    public void setApdid(String str) {
        this.apdid = str;
    }

    public int getScene() {
        return this.scene;
    }

    public void setScene(int i) {
        this.scene = i;
    }

    public void addExtProperty(String str, String str2) {
        this.extProperty.put(str, str2);
    }

    public Map<String, String> getExtProperty() {
        return this.extProperty;
    }

    public boolean enableNavPage() {
        return this.enableNavPage;
    }

    public void setEnableNavPage(boolean z) {
        this.enableNavPage = z;
    }

    public boolean enablePrePoseAlert() {
        return this.enablePrePoseAlert;
    }

    public void setEnablePrePoseAlert(boolean z) {
        this.enablePrePoseAlert = z;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String str) {
        this.protocol = str;
    }
}
