package com.ali.user.mobile.util;

import android.os.SystemClock;
import android.text.TextUtils;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.edge.face.EdgeRiskAnalyzer;
import com.alipay.edge.face.EdgeRiskResult;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import java.util.HashMap;

public class EdgeUtil {
    public static String a(String str, String str2, String str3, String str4) {
        try {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            EdgeRiskAnalyzer instance = EdgeRiskAnalyzer.getInstance(LauncherApplication.a());
            if (instance == null) {
                return null;
            }
            if (TextUtils.isEmpty(str2)) {
                str2 = "";
            }
            if (TextUtils.isEmpty(str3)) {
                str3 = "";
            }
            if (TextUtils.isEmpty(str4)) {
                str4 = "";
            }
            HashMap hashMap = new HashMap();
            hashMap.put("iphoneNumber", str3);
            hashMap.put("type", str2);
            hashMap.put("userid", str4);
            EdgeRiskResult riskResult = instance.getRiskResult(str, hashMap, 50);
            if (riskResult != null && riskResult.status == 0) {
                StringBuilder sb = new StringBuilder("data size ");
                sb.append(riskResult.sealedData.length());
                sb.append("-- data:");
                sb.append(riskResult.sealedData);
                AliUserLog.c("EDGE", sb.toString());
                StringBuilder sb2 = new StringBuilder("get result time ");
                sb2.append(SystemClock.elapsedRealtime() - elapsedRealtime);
                AliUserLog.c("EDGE", sb2.toString());
                return riskResult.sealedData;
            }
            return null;
        } catch (Throwable th) {
            AliUserLog.a((String) "EDGE", th);
        }
    }

    public static void a(String str, String str2, String str3, String str4, boolean z) {
        try {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            EdgeRiskAnalyzer instance = EdgeRiskAnalyzer.getInstance(LauncherApplication.a());
            if (instance != null) {
                HashMap hashMap = new HashMap();
                hashMap.put("iphoneNumber", str2);
                hashMap.put("type", str3);
                hashMap.put("userid", str4);
                hashMap.put("result", z ? "success" : UploadDataResult.FAIL_MSG);
                instance.postUserAction(str, hashMap);
                StringBuilder sb = new StringBuilder("post action time ");
                sb.append(SystemClock.elapsedRealtime() - elapsedRealtime);
                sb.append(" params:");
                sb.append(hashMap.toString());
                AliUserLog.c("EDGE", sb.toString());
            }
        } catch (Throwable th) {
            AliUserLog.a((String) "EDGE", th);
        }
    }
}
