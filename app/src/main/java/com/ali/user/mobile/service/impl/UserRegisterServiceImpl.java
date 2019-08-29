package com.ali.user.mobile.service.impl;

import android.content.Context;
import com.ali.user.mobile.info.AppInfo;
import com.ali.user.mobile.info.DeviceInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.TimeConsumingLogAgent;
import com.ali.user.mobile.rpc.facade.MobileRegFacade;
import com.ali.user.mobile.rpc.vo.mobilegw.GwCommonReq;
import com.ali.user.mobile.rpc.vo.mobilegw.GwCommonRes;
import com.ali.user.mobile.rpc.vo.mobilegw.register.RegMixRes;
import com.ali.user.mobile.rpc.vo.mobilegw.register.SupplementReq;
import com.ali.user.mobile.service.BaseBizService;
import com.ali.user.mobile.service.UserRegisterService;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import java.util.HashMap;

public class UserRegisterServiceImpl extends BaseBizService<MobileRegFacade> implements UserRegisterService {
    private final AppInfo c = AppInfo.getInstance();

    public UserRegisterServiceImpl(Context context) {
        super(context);
    }

    public final RegMixRes a() {
        TimeConsumingLogAgent timeConsumingLogAgent = new TimeConsumingLogAgent("UC-ZC-150512-T01", "getCountyCode");
        timeConsumingLogAgent.a().a("ali.user.gw.register.countryCodeProcesser");
        try {
            GwCommonReq gwCommonReq = new GwCommonReq();
            gwCommonReq.appId = "ALIPAY";
            gwCommonReq.apdId = AppInfo.getInstance().getApdid();
            gwCommonReq.productVersion = this.c.getProductVersion();
            gwCommonReq.sdkVersion = this.c.getSdkVersion();
            gwCommonReq.appKey = AppInfo.getInstance().getAppKey(this.a);
            gwCommonReq.productId = AppInfo.getInstance().getProductId();
            gwCommonReq.productVersion = AppInfo.getInstance().getProductVersion();
            DeviceInfo.b();
            gwCommonReq.IMEI = DeviceInfo.c();
            RegMixRes countyCode = ((MobileRegFacade) this.b).getCountyCode(gwCommonReq);
            if (countyCode == null) {
                timeConsumingLogAgent.b().d("RegMixRes=null").c();
            } else {
                timeConsumingLogAgent.b().d(String.valueOf(countyCode.resultStatus)).b(countyCode.token).c();
            }
            return countyCode;
        } catch (RpcException e) {
            TimeConsumingLogAgent.a(timeConsumingLogAgent, e);
            timeConsumingLogAgent.b().c();
            throw e;
        }
    }

    public final GwCommonRes a(String str, String str2, String str3, String str4, String str5, boolean z) {
        TimeConsumingLogAgent timeConsumingLogAgent = new TimeConsumingLogAgent("UC-ZC-150512-T01", "supplementV2");
        timeConsumingLogAgent.a().a("ali.user.gw.register.completeProcesserV2");
        try {
            SupplementReq supplementReq = new SupplementReq();
            supplementReq.appId = "ALIPAY";
            supplementReq.payPassword = str2;
            supplementReq.simplePassword = str3;
            supplementReq.token = str;
            supplementReq.rdsInfo = str4;
            supplementReq.wa = str5;
            supplementReq.productVersion = this.c.getProductVersion();
            supplementReq.sdkVersion = this.c.getSdkVersion();
            supplementReq.optionStatus = z;
            supplementReq.appKey = AppInfo.getInstance().getAppKey(this.a);
            supplementReq.productId = AppInfo.getInstance().getProductId();
            supplementReq.productVersion = AppInfo.getInstance().getProductVersion();
            DeviceInfo.b();
            supplementReq.IMEI = DeviceInfo.c();
            try {
                if (supplementReq.externParams == null) {
                    supplementReq.externParams = new HashMap();
                    supplementReq.externParams.put("isPrisonBreak", String.valueOf(OutsideConfig.i()));
                    supplementReq.externParams.put("mobileModel", com.alipay.android.phone.inside.common.info.DeviceInfo.a().j());
                    supplementReq.externParams.put("isTrojan", String.valueOf(OutsideConfig.j()));
                    supplementReq.externParams.put("currentOperateMobile", OutsideConfig.h());
                }
            } catch (Throwable th) {
                AliUserLog.a("supplementV2", "reg-supply six error", th);
            }
            GwCommonRes supplementV2 = ((MobileRegFacade) this.b).supplementV2(supplementReq);
            if (supplementV2 == null) {
                timeConsumingLogAgent.b().d("supplementV2Res=null").b(str).c();
            } else {
                timeConsumingLogAgent.b().d(String.valueOf(supplementV2.resultStatus)).b(supplementV2.token).c();
            }
            return supplementV2;
        } catch (RpcException e) {
            TimeConsumingLogAgent.a(timeConsumingLogAgent, e);
            timeConsumingLogAgent.b().c();
            throw e;
        }
    }
}
