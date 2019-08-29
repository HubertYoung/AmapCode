package com.alipay.mobile.security.bio.service;

import android.os.Bundle;
import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BioAppDescription implements Serializable {
    private static final long serialVersionUID = 1;
    private String appInterfaceName;
    private String appName;
    private boolean autoClose = true;
    private int bioAction;
    private int bioType;
    private String bistoken;
    private Bundle bundle;
    private String cfg;
    protected Map<String, String> extMetaInfo = new HashMap();
    protected Map<String, String> extProperty = new HashMap();
    private String headImageDir;
    private JSONObject mFcSpecialData;
    private long productID = -1;
    private String remoteURL;
    private boolean signed = true;
    private String tag;

    public String getRemoteURL() {
        return this.remoteURL;
    }

    public void setRemoteURL(String str) {
        this.remoteURL = str;
    }

    public String getHeadImageURL() {
        return this.headImageDir;
    }

    public void setHeadImageURL(String str) {
        this.headImageDir = str;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public String getAppInterfaceName() {
        return this.appInterfaceName;
    }

    public void setAppInterfaceName(String str) {
        this.appInterfaceName = str;
    }

    public int getBioType() {
        return this.bioType;
    }

    public void setBioType(int i) {
        this.bioType = i;
    }

    public int getBioAction() {
        return this.bioAction;
    }

    public void setBioAction(int i) {
        this.bioAction = i;
    }

    public String getCfg() {
        return this.cfg;
    }

    public void setCfg(String str) {
        this.cfg = str;
    }

    public Map<String, String> getExtProperty() {
        return this.extProperty;
    }

    public void addExtProperty(String str, String str2) {
        this.extProperty.put(str, str2);
    }

    public boolean isSigned() {
        return this.signed;
    }

    public void setSigned(boolean z) {
        this.signed = z;
    }

    public String getBistoken() {
        return this.bistoken;
    }

    public void setBistoken(String str) {
        this.bistoken = str;
    }

    public JSONObject getFcSpecialData() {
        return this.mFcSpecialData;
    }

    public void setFcSpecialData(JSONObject jSONObject) {
        this.mFcSpecialData = jSONObject;
    }

    public boolean isAutoClose() {
        return this.autoClose;
    }

    public void setAutoClose(boolean z) {
        this.autoClose = z;
    }

    public long getProductID() {
        return this.productID;
    }

    public void setProductID(long j) {
        this.productID = j;
    }

    public Map<String, String> getExtMetaInfo() {
        return this.extMetaInfo;
    }

    public void setExtMetaInfo(Map<String, String> map) {
        this.extMetaInfo = map;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void setBundle(Bundle bundle2) {
        this.bundle = bundle2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" bioType:" + this.bioType);
        sb.append(" bioAction:" + this.bioAction);
        sb.append(" appName:" + this.appName);
        sb.append(" cfg:" + this.cfg);
        sb.append(" signed:" + this.signed);
        sb.append(" bistoken:" + this.bistoken);
        sb.append(" autoClose:" + this.autoClose);
        sb.append(" appInterfaceName:" + this.appInterfaceName);
        sb.append(" productID:" + this.productID);
        return sb.toString();
    }
}
