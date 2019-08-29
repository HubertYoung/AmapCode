package com.alipay.android.phone.inside.offlinecode.rpc.request.base;

import java.util.Map;

public class BaseRPCRequestInfo {
    public String adCode;
    public String apdidToken;
    public String clientVersion;
    public Map<String, String> extInfo;
    public boolean isRoot = false;
    public String packageVersion;
    public String systemType;
    public long time = 0;
}
