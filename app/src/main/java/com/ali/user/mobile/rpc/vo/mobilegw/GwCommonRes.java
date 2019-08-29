package com.ali.user.mobile.rpc.vo.mobilegw;

import java.io.Serializable;
import java.util.Map;

public class GwCommonRes extends ToString implements Serializable {
    public Map<String, String> extInfos;
    public String memo;
    public String mobileNo;
    public int resultStatus = 0;
    public String token;
}
