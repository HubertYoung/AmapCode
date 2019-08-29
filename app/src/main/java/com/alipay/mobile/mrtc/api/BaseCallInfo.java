package com.alipay.mobile.mrtc.api;

import java.util.Map;

public abstract class BaseCallInfo {
    public String bizName = "";
    public int callMode = -1;
    public int callType = -1;
    public Map<String, String> extInfos;
    public String localUserId;
    public String subBiz = "";

    public abstract boolean isCaller();

    public String toString() {
        return "BaseCallInfo{, localUserId='" + this.localUserId + '\'' + ", callType=" + this.callType + ", callMode=" + this.callMode + ", bizName=" + this.bizName + ", subBiz=" + this.subBiz + '}';
    }
}
