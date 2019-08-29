package com.alipay.mobile.nebula.networksupervisor;

public interface H5NetworkSupervisor {
    void onReceiveRsp(H5NetworkSuResponse h5NetworkSuResponse);

    void onSendReq(H5NetworkSuRequest h5NetworkSuRequest);
}
