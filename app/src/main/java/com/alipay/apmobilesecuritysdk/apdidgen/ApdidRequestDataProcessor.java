package com.alipay.apmobilesecuritysdk.apdidgen;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.commonbiz.external.UmidSdkWrapper;
import com.alipay.apmobilesecuritysdk.commonbiz.monitor.LogAgent;
import com.alipay.apmobilesecuritysdk.model.DeviceInfoManager;
import com.alipay.apmobilesecuritysdk.proxydetect.WebRTCClient;
import com.alipay.apmobilesecuritysdk.rpc.util.DeviceDataRequestModel;
import com.alipay.apmobilesecuritysdk.storage.ApdidStorage;
import com.alipay.apmobilesecuritysdk.storage.ApdidStorageModel;
import com.alipay.apmobilesecuritysdk.storage.ApdidStorageModelV4;
import com.alipay.apmobilesecuritysdk.storage.ApdidStorageV4;
import com.alipay.apmobilesecuritysdk.storage.SettingsStorage;
import com.alipay.apmobilesecuritysdk.storage.TokenStorage;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.util.Map;

public class ApdidRequestDataProcessor implements ApdidProcessor {
    private TraceLogger a = LoggerFactory.f();

    public final boolean a(Context context, Map<String, Object> map) {
        long currentTimeMillis = System.currentTimeMillis();
        this.a.b((String) CONST.LOG_TAG, "ApdidRequestDataProcessor() start : ".concat(String.valueOf(currentTimeMillis)));
        DeviceDataRequestModel deviceDataRequestModel = new DeviceDataRequestModel();
        WebRTCClient.a(context).b();
        this.a.b((String) CONST.LOG_TAG, (String) "stun initialized async successfully.");
        String str = "";
        String str2 = "";
        String intializeSyncAndGetUmidToken = UmidSdkWrapper.intializeSyncAndGetUmidToken(context);
        LogAgent.a(intializeSyncAndGetUmidToken);
        String stringFromMap = CommonUtils.getStringFromMap(map, TransportConstants.KEY_RPC_VERSION, "");
        String b = TokenStorage.b(context, CommonUtils.getStringFromMap(map, "appName", ""));
        String f = SettingsStorage.f(context);
        ApdidStorageModelV4 c = ApdidStorageV4.c(context);
        if (c != null) {
            str = c.a();
            str2 = c.c();
        }
        if (CommonUtils.isBlank(str)) {
            ApdidStorageModel c2 = ApdidStorage.c(context);
            if (c2 != null) {
                str = c2.a();
                str2 = c2.c();
            }
        }
        if (CommonUtils.isBlank(str)) {
            ApdidStorageModelV4 a2 = ApdidStorageV4.a();
            if (a2 != null) {
                str = a2.a();
                str2 = a2.c();
            }
        }
        if (CommonUtils.isBlank(str)) {
            ApdidStorageModel a3 = ApdidStorage.a();
            if (a3 != null) {
                str = a3.a();
                str2 = a3.c();
            }
        }
        deviceDataRequestModel.a((String) "android");
        deviceDataRequestModel.c(str);
        deviceDataRequestModel.d(b);
        deviceDataRequestModel.e(intializeSyncAndGetUmidToken);
        deviceDataRequestModel.g(str2);
        deviceDataRequestModel.f(stringFromMap);
        deviceDataRequestModel.b(f);
        this.a.b((String) CONST.LOG_TAG, (String) "initialize request headers successfully.");
        deviceDataRequestModel.a(DeviceInfoManager.a().a(context, map));
        this.a.b((String) CONST.LOG_TAG, (String) "set request model datamap successfully.");
        map.put("rpc_quest", deviceDataRequestModel);
        this.a.b((String) CONST.LOG_TAG, (String) "put datamap to request model successfully.");
        TraceLogger traceLogger = this.a;
        StringBuilder sb = new StringBuilder("ApdidRequestDataProcessor() cost ");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        sb.append(" ms.");
        traceLogger.b((String) CONST.LOG_TAG, sb.toString());
        return true;
    }
}
