package com.alipay.mobilecodec.service.pai.req;

import java.io.Serializable;
import java.util.Map;

public class RouteCommandReq implements Serializable {
    public String channelId;
    public String dataType;
    public Map<String, String> decodeData;
    public Map<String, String> extData;
    public String paiType;
    public Map<String, String> productContext;
}
