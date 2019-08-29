package com.alipay.android.phone.secauthenticator.kcart;

import android.util.Base64;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.protobuf.okio.ByteString;
import com.alipay.edge.face.EdgeRiskAnalyzer;
import com.alipay.edge.face.EdgeRiskResult;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class KcartService {
    private final String EDGE_UA_KCART = "pay";
    private final String TAG = "kcart";

    public String extract() {
        return extractIner(System.currentTimeMillis() / 1000);
    }

    private synchronized String extractIner(long j) {
        ByteString edgeRiskData = getEdgeRiskData(j);
        LoggerFactory.f().b((String) "kcart", "edge sealData ".concat(String.valueOf(edgeRiskData)));
        if (edgeRiskData == null || edgeRiskData.size() <= 0) {
            return "";
        }
        return extractV4(j, edgeRiskData);
    }

    private synchronized String extractV4(long j, ByteString byteString) {
        UploadedVariables uploadedVariables;
        LoggerFactory.f().b((String) "kcart", "now: ".concat(String.valueOf(j)));
        uploadedVariables = new UploadedVariables();
        uploadedVariables.VERSION = Integer.valueOf(6);
        uploadedVariables.RT_FUND_1D_1MTH = Integer.valueOf(0);
        uploadedVariables.D1D_CNT_HOME = Integer.valueOf(0);
        uploadedVariables.RT2_YEB_1D_1MTH = Integer.valueOf(0);
        uploadedVariables.RT_FUNDCARD_1D_1MTH = Integer.valueOf(0);
        uploadedVariables.D1D_CNT_YEBHOME = Integer.valueOf(0);
        uploadedVariables.DSM_FUNDSAFESECUR = Integer.valueOf(0);
        uploadedVariables.D1D_CNT_SETTING = Integer.valueOf(0);
        uploadedVariables.RT_SECURITYSAFE_1D_1MTH = Integer.valueOf(0);
        uploadedVariables.RT_ACCTDTL_1D_1MTH = Integer.valueOf(0);
        if (byteString != null) {
            uploadedVariables.EDGE_RISK_DATA = byteString;
        }
        return Base64.encodeToString(uploadedVariables.toByteArray(), 2);
    }

    private ByteString getEdgeRiskData(final long j) {
        ByteString byteString;
        String str;
        long currentTimeMillis = System.currentTimeMillis();
        try {
            FutureTask futureTask = new FutureTask(new Callable<ByteString>() {
                /* access modifiers changed from: private */
                /* renamed from: a */
                public ByteString call() throws Exception {
                    try {
                        EdgeRiskResult riskResult = EdgeRiskAnalyzer.getInstance(LauncherApplication.a()).getRiskResult("pay", KcartService.this.buildParameter(j), 50);
                        if (riskResult != null) {
                            if (riskResult.sealedData != null) {
                                byte[] decode = Base64.decode(riskResult.sealedData, 2);
                                if (decode != null) {
                                    if (decode.length != 0) {
                                        return ByteString.of(decode);
                                    }
                                }
                                LoggerFactory.f().d("kcart", "getEdgeRiskData base64 decode return null.");
                                return null;
                            }
                        }
                        LoggerFactory.f().d("kcart", "getEdgeRiskData call edge return null.");
                        return null;
                    } catch (Throwable th) {
                        LoggerFactory.f().b((String) "kcart", th);
                        return null;
                    }
                }
            });
            new Thread(futureTask).start();
            byteString = (ByteString) futureTask.get(50, TimeUnit.MILLISECONDS);
        } catch (Throwable unused) {
            byteString = null;
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (byteString == null) {
            str = "null";
        } else {
            str = String.valueOf(byteString.size());
        }
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("getEdgeRiskData cost ");
        sb.append(currentTimeMillis2);
        sb.append(" ms. result len= ");
        sb.append(str);
        f.b((String) "kcart", sb.toString());
        return byteString;
    }

    /* access modifiers changed from: private */
    public HashMap<String, String> buildParameter(long j) {
        LoggerFactory.f().b((String) "kcart", "now: ".concat(String.valueOf(j)));
        HashMap<String, String> hashMap = new HashMap<>();
        Integer valueOf = Integer.valueOf(0);
        Integer valueOf2 = Integer.valueOf(0);
        hashMap.put("rtFund1D1Mth", Integer.toString(valueOf.intValue()));
        hashMap.put("rtFundcard1D1Mth", String.valueOf(valueOf2));
        return hashMap;
    }
}
