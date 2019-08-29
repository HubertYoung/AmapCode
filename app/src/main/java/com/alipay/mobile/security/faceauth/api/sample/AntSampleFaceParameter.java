package com.alipay.mobile.security.faceauth.api.sample;

import com.alipay.mobile.security.faceauth.api.AntDetectParameter;
import java.util.Map.Entry;

public class AntSampleFaceParameter extends AntDetectParameter {
    private String appName = "";

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public AntSampleFaceParameter() {
        setAction(65536);
    }

    public void clone(AntDetectParameter antDetectParameter) {
        setAction(antDetectParameter.getAction());
        setAppID(antDetectParameter.getAppID());
        setExtJson(antDetectParameter.getExtJson());
        setApdid(antDetectParameter.getApdid());
        setTag(antDetectParameter.getTag());
        setRemoteUrl(antDetectParameter.getRemoteUrl());
        setAutoClose(antDetectParameter.isAutoClose());
        setScene(antDetectParameter.getScene());
        setEnableNavPage(antDetectParameter.enableNavPage());
        setEnablePrePoseAlert(antDetectParameter.enablePrePoseAlert());
        setProtocol(antDetectParameter.getProtocol());
        for (Entry next : antDetectParameter.getExtProperty().entrySet()) {
            Object key = next.getKey();
            Object value = next.getValue();
            if (!(key == null || value == null)) {
                addExtProperty(key.toString(), value.toString());
            }
        }
    }
}
