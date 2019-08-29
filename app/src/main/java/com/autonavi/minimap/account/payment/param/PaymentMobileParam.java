package com.autonavi.minimap.account.payment.param;

import java.io.Serializable;

public class PaymentMobileParam implements Serializable {
    public String code = null;
    public String mobile = null;
    public int mode = 0;
    public String pushToken = null;
    public int replaceType = 0;
    public String verifyToken = null;
}
