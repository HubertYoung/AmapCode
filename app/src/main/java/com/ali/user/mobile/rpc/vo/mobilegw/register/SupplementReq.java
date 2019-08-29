package com.ali.user.mobile.rpc.vo.mobilegw.register;

import com.ali.user.mobile.rpc.vo.mobilegw.GwCommonReq;
import java.io.Serializable;

public class SupplementReq extends GwCommonReq implements Serializable {
    public String clientIp;
    public boolean optionStatus;
    public String payPassword;
    public String rdsInfo;
    public String simplePassword;
    public String wa;
}
