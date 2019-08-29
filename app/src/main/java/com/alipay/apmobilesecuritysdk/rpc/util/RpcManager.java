package com.alipay.apmobilesecuritysdk.rpc.util;

import android.content.Context;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.commonservice.RpcService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.rpc.gen.BugTrackMessageService;
import com.alipay.apmobilesecuritysdk.rpc.gen.ReportPBService4AndroidV7;
import com.alipay.apmobilesecuritysdk.rpc.gen.ReportRequest;
import com.alipay.apmobilesecuritysdk.rpc.gen.ReportResult;
import com.alipay.edge.face.EdgeRiskAnalyzer;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.util.HashMap;
import org.json.JSONObject;

public class RpcManager {
    private static RpcManager a;
    /* access modifiers changed from: private */
    public ReportPBService4AndroidV7 b = null;
    private BugTrackMessageService c = null;
    /* access modifiers changed from: private */
    public ReportResult d = null;
    /* access modifiers changed from: private */
    public TraceLogger e = LoggerFactory.f();
    private EdgeRiskAnalyzer f = null;

    private RpcManager(Context context) {
        this.e.b((String) CONST.LOG_TAG, (String) "call RpcManager");
        RpcService rpcService = CommonServiceFactory.getInstance().getRpcService();
        this.e.b((String) CONST.LOG_TAG, (String) "RpcManager new RpcService() succ");
        this.c = (BugTrackMessageService) rpcService.getRpcProxy(BugTrackMessageService.class);
        this.e.b((String) CONST.LOG_TAG, (String) "RpcManager rpcService.getRpcProxy(BugTrackMessageService.class) succ");
        this.b = (ReportPBService4AndroidV7) rpcService.getRpcProxy(ReportPBService4AndroidV7.class);
        this.e.b((String) CONST.LOG_TAG, (String) "RpcManager rpcService.getRpcProxy(ReportPBService4AndroidV7.class) succ");
        this.f = EdgeRiskAnalyzer.getInstance(context);
        this.e.b((String) CONST.LOG_TAG, (String) "RpcManager 构造成功");
    }

    public static synchronized RpcManager a(Context context) {
        RpcManager rpcManager;
        synchronized (RpcManager.class) {
            try {
                if (a == null) {
                    a = new RpcManager(context);
                }
                rpcManager = a;
            }
        }
        return rpcManager;
    }

    public final DeviceDataReponseModel a(DeviceDataRequestModel deviceDataRequestModel) throws Exception {
        this.e.b((String) CONST.LOG_TAG, (String) "call updateStaticData");
        DeviceDataReponseModel deviceDataReponseModel = null;
        if (deviceDataRequestModel == null || this.b == null) {
            if (deviceDataRequestModel == null) {
                this.e.b((String) CONST.LOG_TAG, (String) "data is null");
            }
            if (this.b == null) {
                this.e.b((String) CONST.LOG_TAG, (String) "mDeviceFingerPrintService is null");
            }
            return null;
        }
        final ReportRequest a2 = ConvertUtil.a(deviceDataRequestModel);
        if (a2 == null) {
            this.e.b((String) CONST.LOG_TAG, (String) "ConvertUtil.convertFrom(data) null");
            return null;
        }
        this.e.b((String) CONST.LOG_TAG, (String) "data encapsolute success, prepare to send data to server.");
        this.d = null;
        new Thread(new Runnable() {
            public void run() {
                try {
                    RpcManager.this.d = RpcManager.this.b.reportData(a2);
                } catch (Throwable th) {
                    th.printStackTrace();
                    RpcManager.this.d = new ReportResult();
                    RpcManager.this.d.success = Boolean.FALSE;
                    ReportResult b2 = RpcManager.this.d;
                    StringBuilder sb = new StringBuilder("static data rpc upload error, ");
                    sb.append(CommonUtils.getStackString(th));
                    b2.resultCode = sb.toString();
                    RpcManager.this.e.b((String) CONST.LOG_TAG, CommonUtils.getStackString(th));
                }
            }
        }).start();
        int i = 300000;
        while (this.d == null && i >= 0) {
            Thread.sleep(50);
            i -= 50;
        }
        if (i < 0) {
            b((String) "timeout");
            this.e.b((String) CONST.LOG_TAG, (String) "send request failed, prepare to parse server response data.");
        } else {
            this.e.b((String) CONST.LOG_TAG, (String) "send request success, prepare to parse server response data.");
            deviceDataReponseModel = ConvertUtil.a(this.d);
        }
        b((String) "total");
        return deviceDataReponseModel;
    }

    private void b(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("result", str);
        this.f.postUserAction("security_sdk", hashMap);
        TraceLogger f2 = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("edgeRiskAnalyzer post ua ");
        sb.append("security_sdk");
        sb.append(" properties result is ");
        sb.append(str);
        f2.b((String) CONST.LOG_TAG, sb.toString());
    }

    public final boolean a(String str) throws Exception {
        if (CommonUtils.isBlank(str) || this.c == null) {
            return false;
        }
        String logCollect = this.c.logCollect(CommonUtils.textCompress(str));
        if (CommonUtils.isBlank(logCollect)) {
            return false;
        }
        return ((Boolean) new JSONObject(logCollect).get("success")).booleanValue();
    }
}
