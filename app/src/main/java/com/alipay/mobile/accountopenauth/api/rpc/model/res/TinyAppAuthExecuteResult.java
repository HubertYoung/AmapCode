package com.alipay.mobile.accountopenauth.api.rpc.model.res;

import java.util.List;
import java.util.Map;

public class TinyAppAuthExecuteResult {
    public String appId = "";
    public String authCode = "";
    public int error = 0;
    public String errorCode;
    public String errorMessage;
    public String errorMsg;
    public Map<String, String> errorScopes;
    public Map<String, String> extInfo;
    public boolean isSuccess;
    public String isvAppId;
    public String state;
    public List<String> successScopes;
}
