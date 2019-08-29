package com.alipay.mobileapp.biz.rpc.taobao.bind.vo;

import java.io.Serializable;
import java.util.Map;

public class BindTaobaoReq implements Serializable {
    public Map<String, String> externParam;
    public String imei;
    public String imsi;
    public String productId;
    public String productVersion;
    public String sdkVersion;
    public String umidToken;
    public String userid;
}
