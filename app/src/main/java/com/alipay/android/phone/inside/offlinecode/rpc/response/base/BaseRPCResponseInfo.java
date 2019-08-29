package com.alipay.android.phone.inside.offlinecode.rpc.response.base;

import java.util.Map;

public class BaseRPCResponseInfo {
    public ErrorIndicator errorIndicator;
    public Map<String, String> extInfo;
    public boolean success = false;
    public long time = 0;
}
