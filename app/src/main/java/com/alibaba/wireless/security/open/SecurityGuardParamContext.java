package com.alibaba.wireless.security.open;

import java.util.HashMap;
import java.util.Map;

public class SecurityGuardParamContext {
    public String appKey;
    public Map<String, String> paramMap = new HashMap();
    public int requestType;
    public String reserved1;
    public String reserved2;
}
