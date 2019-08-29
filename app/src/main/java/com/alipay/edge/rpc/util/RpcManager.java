package com.alipay.edge.rpc.util;

import android.content.Context;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.protobuf.okio.ByteString;
import com.alipay.apmobilesecuritysdk.commonbiz.ApplistUtil;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.edge.impl.EdgeSwitchManager;
import com.alipay.edge.rpc.gen.EdgePBService;
import com.alipay.edge.rpc.gen.EdgeRequest;
import com.alipay.edge.rpc.gen.EdgeResult;
import com.alipay.edge.rpc.gen.EdgeStatus;
import com.alipay.edge.rpc.gen.OsType;
import com.alipay.edge.utils.Constant;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.deviceinfo.AppInfo;
import com.alipay.security.mobile.module.deviceinfo.EnvironmentInfo;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public class RpcManager {
    private static TraceLogger a = LoggerFactory.f();
    private static RpcManager b = new RpcManager();

    public static class EdgeResponseModel {
        public boolean a;
        public byte[] b;
        public int c;
        public String d;
        public String e;
    }

    private RpcManager() {
    }

    public static RpcManager a() {
        return b;
    }

    public static EdgeResponseModel a(Context context, byte[] bArr, int i, int i2, Map<String, String> map) {
        EdgeResponseModel edgeResponseModel = new EdgeResponseModel();
        if (context == null) {
            try {
                a.b((String) "t0dbg", (String) "reportData ctx == null");
                edgeResponseModel.a = false;
                return edgeResponseModel;
            } catch (Throwable th) {
                TraceLogger traceLogger = a;
                StringBuilder sb = new StringBuilder("rpc exception: ");
                sb.append(CommonUtils.getStackString(th));
                traceLogger.b((String) "t0dbg", sb.toString());
                edgeResponseModel.a = false;
                return edgeResponseModel;
            }
        } else {
            EdgePBService edgePBService = (EdgePBService) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(EdgePBService.class);
            if (edgePBService == null) {
                edgeResponseModel.a = false;
                a.b((String) "t0dbg", (String) "reportData edgePBService == null");
                return edgeResponseModel;
            }
            EdgeResult updateData = edgePBService.updateData(b(context, bArr, i2, i, map));
            if (updateData == null) {
                edgeResponseModel.a = false;
                a.b((String) "t0dbg", (String) "reportData result == null");
                return edgeResponseModel;
            } else if (!updateData.success.booleanValue()) {
                edgeResponseModel.a = false;
                a.b((String) "t0dbg", (String) "reportData !result.success");
                return edgeResponseModel;
            } else {
                edgeResponseModel.a = true;
                edgeResponseModel.c = updateData.flag.intValue();
                if (updateData.policyPackData != null) {
                    edgeResponseModel.b = updateData.policyPackData.toByteArray();
                }
                if (updateData.extData != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(updateData.extData);
                        edgeResponseModel.d = jSONObject.getString("exAppListVer");
                        edgeResponseModel.e = jSONObject.getString("exAppList");
                    } catch (Throwable unused) {
                    }
                }
                return edgeResponseModel;
            }
        }
    }

    private static EdgeRequest b(Context context, byte[] bArr, int i, int i2, Map<String, String> map) {
        String str;
        TokenResult tokenResult = APSecuritySdk.getInstance(context).getTokenResult();
        EdgeRequest edgeRequest = new EdgeRequest();
        edgeRequest.appName = context.getPackageName();
        String str2 = null;
        if (tokenResult == null) {
            str = null;
        } else {
            str = tokenResult.apdid;
        }
        edgeRequest.apdid = str;
        if (tokenResult != null) {
            str2 = tokenResult.apdidToken;
        }
        edgeRequest.apdidToken = str2;
        edgeRequest.appVersion = AppInfo.getInstance().getAppVersion(context);
        EdgeSwitchManager a2 = EdgeSwitchManager.a(context);
        if (!a2.f() || a2.e()) {
            edgeRequest.edgeStatus = EdgeStatus.DISABLED;
        } else {
            edgeRequest.edgeStatus = EdgeStatus.ENABLED;
        }
        edgeRequest.brand = EnvironmentInfo.getInstance().getProductBrand();
        edgeRequest.model = EnvironmentInfo.getInstance().getProductModel();
        edgeRequest.osType = OsType.ANDROID;
        edgeRequest.osVersion = EnvironmentInfo.getInstance().getBuildVersionRelease();
        edgeRequest.sdkVersion = Constant.a;
        edgeRequest.policyPackVersion = String.valueOf(i);
        edgeRequest.secureData = ByteString.of(bArr);
        edgeRequest.credibleTimestamp = Integer.valueOf(i2);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("exAppListVer", ApplistUtil.d(context).a);
        } catch (Throwable unused) {
        }
        if (map != null) {
            for (Entry next : map.entrySet()) {
                try {
                    jSONObject.put((String) next.getKey(), next.getValue());
                } catch (Throwable unused2) {
                }
            }
        }
        edgeRequest.extData = jSONObject.toString();
        return edgeRequest;
    }
}
