package com.ali.user.mobile.rpc.vo.mobilegw;

import java.io.Serializable;

public class SupplyPassGwReq extends GwCommonReq implements Serializable {
    public boolean optionStatus;
    public String paymentPassword;
    public String queryPassword;
    public String simplePassword;
}
