package com.alipay.mobile.nebula.appcenter.listen;

import java.util.List;

public class NebulaAppCallbackInfo {
    private List<String> appIdList;
    private int callBackSource;

    public List<String> getAppIdList() {
        return this.appIdList;
    }

    public void setAppIdList(List<String> appIdList2) {
        this.appIdList = appIdList2;
    }

    public int getCallBackSource() {
        return this.callBackSource;
    }

    public void setCallBackSource(int callBackFrom) {
        this.callBackSource = callBackFrom;
    }
}
