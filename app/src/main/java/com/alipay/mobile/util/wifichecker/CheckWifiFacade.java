package com.alipay.mobile.util.wifichecker;

import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobileappcommon.biz.rpc.checkwifi.model.ClientCheckWifiReq;
import com.alipay.mobileappcommon.biz.rpc.checkwifi.model.ClientCheckWifiRes;

public interface CheckWifiFacade {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    @OperationType("alipay.mobileappcommon.checkWifi")
    ClientCheckWifiRes checkWIFI(ClientCheckWifiReq clientCheckWifiReq);
}
