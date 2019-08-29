package com.ali.user.mobile.rpc.facade;

import com.ali.user.mobile.rpc.vo.mobilegw.GwCommonReq;
import com.ali.user.mobile.rpc.vo.mobilegw.GwCommonRes;
import com.ali.user.mobile.rpc.vo.mobilegw.SendSmsGwReq;
import com.ali.user.mobile.rpc.vo.mobilegw.SmsGwRes;
import com.ali.user.mobile.rpc.vo.mobilegw.register.RegMixRes;
import com.ali.user.mobile.rpc.vo.mobilegw.register.SupplementReq;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;

public interface MobileRegFacade {
    @OperationType("ali.user.gw.register.countryCodeProcesser")
    RegMixRes getCountyCode(GwCommonReq gwCommonReq);

    @OperationType("ali.user.gw.sms.sendSms")
    SmsGwRes sendSms(SendSmsGwReq sendSmsGwReq);

    @OperationType("ali.user.gw.register.completeProcesserV2")
    GwCommonRes supplementV2(SupplementReq supplementReq);
}
