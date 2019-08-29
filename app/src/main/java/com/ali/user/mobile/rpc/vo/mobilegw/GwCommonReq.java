package com.ali.user.mobile.rpc.vo.mobilegw;

import java.io.Serializable;
import java.util.Map;

public class GwCommonReq extends ToString implements Serializable {
    public String IMEI;
    public String IMSI;
    public String accessPoint;
    public String apdId;
    public String appId = "ALIPAY";
    public String appKey;
    public String cellId;
    public String channel;
    public String clientPostion;
    public String clientType;
    public String devKeySet;
    public Map<String, String> externParams;
    public String isPrisonBreak;
    public String lacId;
    public String longonId;
    public String mobileBrand;
    public String mobileModel;
    public String productId;
    public String productVersion;
    public String screenHigh;
    public String screenWidth;
    public String sdkVersion;
    public String systemType;
    public String systemVersion;
    public String tid;
    public String token;
    public String ttid;
    public String umidToken;
    public String userAgent;
    public String utdid;
    public String wifiMac;
    public String wifiNodeName;
}
