package com.ali.user.mobile.rpc.vo.mobilegw;

import java.io.Serializable;

public class SendSmsGwReq extends GwCommonReq implements Serializable {
    public String appKey;
    public String mobile;
    public String type;
}
