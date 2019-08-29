package com.alipay.deviceid.module.x;

import android.content.Context;
import com.alipay.deviceid.module.rpc.report.open.model.ReportRequest;
import com.alipay.deviceid.module.rpc.report.open.model.ReportResult;
import com.alipay.deviceid.module.senative.DeviceIdUtil;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public final class o {
    public static ReportRequest a(Context context, q qVar) {
        ReportRequest reportRequest = new ReportRequest();
        if (qVar == null) {
            return null;
        }
        Map<String, String> d = qVar.d();
        if (d == null) {
            return null;
        }
        reportRequest.bizType = "1";
        reportRequest.rpcVersion = qVar.c();
        reportRequest.os = qVar.a();
        reportRequest.appName = d.get("AA1");
        reportRequest.appVersion = d.get("AA2");
        reportRequest.sdkName = d.get("AA3");
        reportRequest.sdkVersion = d.get("AA4");
        HashMap hashMap = new HashMap();
        hashMap.put(DictionaryKeys.V2_APDID, qVar.b());
        reportRequest.bizData = hashMap;
        try {
            JSONObject jSONObject = new JSONObject();
            for (String next : d.keySet()) {
                if (next != null && next.length() > 0) {
                    jSONObject.put(next, d.get(next));
                }
            }
            String jSONObject2 = jSONObject.toString();
            StringBuilder sb = new StringBuilder("{\"AA9\":\"%s\",\"AE20\":\"%s\",");
            sb.append(jSONObject2.substring(1));
            reportRequest.deviceData = new String(DeviceIdUtil.getInstance(context).packageDevideData(sb.toString().getBytes("UTF-8")));
            return reportRequest;
        } catch (Exception unused) {
            reportRequest.deviceData = "";
            return reportRequest;
        }
    }

    public static p a(ReportResult reportResult) {
        p pVar = new p();
        if (reportResult == null) {
            return null;
        }
        pVar.a = reportResult.success;
        pVar.b = reportResult.resultCode;
        if (reportResult.resultData == null) {
            return null;
        }
        Map<String, String> map = reportResult.resultData;
        pVar.c = e.a(map, DictionaryKeys.V2_APDID, "");
        pVar.d = e.a(map, "appListCmdVer", "");
        pVar.g = e.a(map, "webrtcUrl", "");
        String a = e.a(map, "drmSwitch", "");
        pVar.e = "0";
        pVar.f = "0";
        if (e.b(a) && a.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(a.charAt(0));
            pVar.e = sb.toString();
        }
        return pVar;
    }
}
