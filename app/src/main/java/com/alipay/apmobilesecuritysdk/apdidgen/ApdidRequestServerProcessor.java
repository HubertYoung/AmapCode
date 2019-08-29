package com.alipay.apmobilesecuritysdk.apdidgen;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.loggers.LoggerUtil;
import com.alipay.apmobilesecuritysdk.model.DeviceInfoManager;
import com.alipay.apmobilesecuritysdk.proxydetect.EntpClient;
import com.alipay.apmobilesecuritysdk.rpc.util.DeviceDataReponseModel;
import com.alipay.apmobilesecuritysdk.rpc.util.DeviceDataRequestModel;
import com.alipay.apmobilesecuritysdk.rpc.util.RpcManager;
import com.alipay.apmobilesecuritysdk.storage.ApdidStorage;
import com.alipay.apmobilesecuritysdk.storage.ApdidStorageModel;
import com.alipay.apmobilesecuritysdk.storage.ApdidStorageV4;
import com.alipay.apmobilesecuritysdk.storage.OpenApdidTokenStorage;
import com.alipay.apmobilesecuritysdk.storage.SettingsStorage;
import com.alipay.apmobilesecuritysdk.storage.TokenStorage;
import com.alipay.edge.impl.EdgeSwitchManager;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.util.Map;

public class ApdidRequestServerProcessor implements ApdidProcessor {
    private String a = null;
    private int b = 0;
    private TraceLogger c = LoggerFactory.f();

    public final boolean a(Context context, Map<String, Object> map) {
        long currentTimeMillis = System.currentTimeMillis();
        this.c.b((String) CONST.LOG_TAG, "ApdidRequestServerProcessor() start :".concat(String.valueOf(currentTimeMillis)));
        this.a = CommonUtils.getStringFromMap(map, "appName", "");
        DeviceDataReponseModel a2 = a(context, map.containsKey("rpc_quest") ? (DeviceDataRequestModel) map.get("rpc_quest") : null);
        char c2 = 2;
        if (a2 != null) {
            if (a2.a) {
                if (!CommonUtils.isBlank(a2.c)) {
                    c2 = 1;
                }
            } else if ("APPKEY_ERROR".equals(a2.b)) {
                c2 = 3;
            }
        }
        if (c2 == 1) {
            this.c.b((String) CONST.LOG_TAG, (String) "onSuccess(), data upload successfully, are you crazy!!!");
            String str = this.a;
            if (CommonUtils.isApkDebugable(context)) {
                TraceLogger traceLogger = this.c;
                StringBuilder sb = new StringBuilder("saveToStorage(), log switch   = ");
                sb.append(a2.a());
                traceLogger.b((String) CONST.LOG_TAG, sb.toString());
                TraceLogger traceLogger2 = this.c;
                StringBuilder sb2 = new StringBuilder("saveToStorage(), agent switch = ");
                sb2.append(a2.b());
                traceLogger2.b((String) CONST.LOG_TAG, sb2.toString());
                TraceLogger traceLogger3 = this.c;
                StringBuilder sb3 = new StringBuilder("saveToStorage(), webrtc url   = ");
                sb3.append(a2.j);
                traceLogger3.b((String) CONST.LOG_TAG, sb3.toString());
                TraceLogger traceLogger4 = this.c;
                StringBuilder sb4 = new StringBuilder("saveToStorage(), timeinterval = ");
                sb4.append(a2.k);
                traceLogger4.b((String) CONST.LOG_TAG, sb4.toString());
                TraceLogger traceLogger5 = this.c;
                StringBuilder sb5 = new StringBuilder("saveToStorage(), sensor config = ");
                sb5.append(a2.c());
                traceLogger5.b((String) CONST.LOG_TAG, sb5.toString());
                TraceLogger traceLogger6 = this.c;
                StringBuilder sb6 = new StringBuilder("saveToStorage(), apdid = ");
                sb6.append(a2.c);
                traceLogger6.b((String) CONST.LOG_TAG, sb6.toString());
                TraceLogger traceLogger7 = this.c;
                StringBuilder sb7 = new StringBuilder("saveToStorage(), token = ");
                sb7.append(a2.d);
                traceLogger7.b((String) CONST.LOG_TAG, sb7.toString());
                TraceLogger traceLogger8 = this.c;
                StringBuilder sb8 = new StringBuilder("saveToStorage(), edge switch = ");
                sb8.append(a2.m);
                traceLogger8.b((String) CONST.LOG_TAG, sb8.toString());
            }
            SettingsStorage.a(context, a2.a());
            SettingsStorage.d(context, a2.b());
            SettingsStorage.a(context, a2.k);
            String stringFromMap = CommonUtils.getStringFromMap(map, "devicehash", "");
            if (CommonUtils.isBlank(stringFromMap)) {
                stringFromMap = DeviceInfoManager.a().a(context);
                this.c.b((String) CONST.LOG_TAG, "saveToStorage(), calculate deviceinfo hash, ".concat(String.valueOf(stringFromMap)));
            } else {
                this.c.b((String) CONST.LOG_TAG, "saveToStorage(), use cached deviceinfo hash, ".concat(String.valueOf(stringFromMap)));
            }
            TokenStorage.c(stringFromMap);
            TokenStorage.a(str, a2.d);
            TokenStorage.b(a2.c);
            TokenStorage.f(a2.e);
            String stringFromMap2 = CommonUtils.getStringFromMap(map, "tid", "");
            if (CommonUtils.isNotBlank(stringFromMap2)) {
                TokenStorage.d(stringFromMap2);
            } else {
                TokenStorage.c();
            }
            String stringFromMap3 = CommonUtils.getStringFromMap(map, "utdid", "");
            if (CommonUtils.isNotBlank(stringFromMap3)) {
                TokenStorage.e(stringFromMap3);
            } else {
                TokenStorage.d();
            }
            TokenStorage.h();
            ApdidStorageV4.a(context, TokenStorage.f());
            ApdidStorage.a(context, new ApdidStorageModel(TokenStorage.a(), TokenStorage.b(), TokenStorage.e()));
            OpenApdidTokenStorage.a(context, str, TokenStorage.a(str));
            SettingsStorage.a(context, str, System.currentTimeMillis());
            SettingsStorage.e(context, a2.i);
            SettingsStorage.f(context, a2.j);
            if (CommonUtils.equals("0", a2.m)) {
                EdgeSwitchManager.a(context).b(false);
            } else if (CommonUtils.equals("1", a2.m)) {
                EdgeSwitchManager.a(context).b(true);
            }
            this.b = 0;
        } else if (c2 != 3) {
            this.b = 4;
            if (a2 != null) {
                String str2 = a2.b;
                LoggerUtil.a("onNetworkFailure(), Server error, result code :".concat(String.valueOf(str2)));
                this.c.b((String) CONST.LOG_TAG, "onNetworkFailure(), rpc failed. result code :".concat(String.valueOf(str2)));
            } else {
                LoggerUtil.a((String) "onNetworkFailure(), Server error, rreturn null.");
                this.c.b((String) CONST.LOG_TAG, (String) "onNetworkFailure(), responseModel = null.");
            }
        } else {
            this.c.b((String) CONST.LOG_TAG, (String) "onIllegalRequest(), illegal appName and appKey composition!");
            this.b = 1;
        }
        if (CommonUtils.isAlipayWallet(context)) {
            EntpClient.a(context, TokenStorage.b(context, this.a), SettingsStorage.h(context));
        }
        map.put("resultcode", Integer.valueOf(this.b));
        TraceLogger traceLogger9 = this.c;
        StringBuilder sb9 = new StringBuilder("ApdidRequestServerProcessor() cost ");
        sb9.append(System.currentTimeMillis() - currentTimeMillis);
        sb9.append(" ms.");
        traceLogger9.b((String) CONST.LOG_TAG, sb9.toString());
        EdgeSwitchManager.a(context).a();
        return true;
    }

    private DeviceDataReponseModel a(Context context, DeviceDataRequestModel deviceDataRequestModel) {
        if (deviceDataRequestModel == null) {
            return null;
        }
        try {
            return RpcManager.a(context).a(deviceDataRequestModel);
        } catch (Throwable th) {
            TraceLogger traceLogger = this.c;
            StringBuilder sb = new StringBuilder("collectAndSendStaticData throwable ");
            sb.append(th.getMessage());
            traceLogger.b((String) CONST.LOG_TAG, sb.toString());
            LoggerUtil.a(th);
            return null;
        }
    }
}
