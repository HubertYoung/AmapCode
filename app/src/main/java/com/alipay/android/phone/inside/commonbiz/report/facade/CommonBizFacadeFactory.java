package com.alipay.android.phone.inside.commonbiz.report.facade;

import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.commonservice.RpcService;

public class CommonBizFacadeFactory {
    public static DeviceLocationFacade a() {
        RpcService rpcService = CommonServiceFactory.getInstance().getRpcService();
        if (StaticConfig.j()) {
            return (DeviceLocationFacade) rpcService.getRpcProxy(DeviceLocationFacadeForAlipay.class);
        }
        return (DeviceLocationFacade) rpcService.getRpcProxy(DeviceLocationFacadeForSdk.class);
    }
}
