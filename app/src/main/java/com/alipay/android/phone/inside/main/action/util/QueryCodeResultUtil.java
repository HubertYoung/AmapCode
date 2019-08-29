package com.alipay.android.phone.inside.main.action.util;

import android.app.KeyguardManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.QueryPayCode;
import com.alipay.android.phone.inside.cashier.PhoneCashierPlugin;
import com.alipay.android.phone.inside.commonbiz.action.SdkActionFactory;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.main.action.OnlinePayAction;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.sdk.app.statistic.c;
import com.uc.webview.export.internal.interfaces.IPreloadManager;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class QueryCodeResultUtil {
    private static List<String> a = new ArrayList();

    static class ToOnlineResult {
        OperationResult<QueryPayCode> a;
        boolean b;

        public ToOnlineResult(OperationResult<QueryPayCode> operationResult, boolean z) {
            this.a = operationResult;
            this.b = z;
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00cd A[Catch:{ Throwable -> 0x02ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00e6 A[Catch:{ Throwable -> 0x02ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0128 A[Catch:{ Throwable -> 0x02ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x02ac A[Catch:{ Throwable -> 0x02ca }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized com.alipay.android.phone.inside.api.result.OperationResult<com.alipay.android.phone.inside.api.result.code.QueryPayCode> a(com.alipay.android.phone.inside.api.action.ActionEnum r17, org.json.JSONObject r18) {
        /*
            r1 = r18
            java.lang.Class<com.alipay.android.phone.inside.main.action.util.QueryCodeResultUtil> r2 = com.alipay.android.phone.inside.main.action.util.QueryCodeResultUtil.class
            monitor-enter(r2)
            com.alipay.android.phone.inside.api.result.OperationResult r3 = new com.alipay.android.phone.inside.api.result.OperationResult     // Catch:{ all -> 0x02d9 }
            com.alipay.android.phone.inside.api.result.code.QueryPayCode r4 = com.alipay.android.phone.inside.api.result.code.QueryPayCode.QUERY_UNKNOWN     // Catch:{ all -> 0x02d9 }
            java.lang.String r5 = r17.getActionName()     // Catch:{ all -> 0x02d9 }
            r3.<init>(r4, r5)     // Catch:{ all -> 0x02d9 }
            if (r1 != 0) goto L_0x0014
            monitor-exit(r2)
            return r3
        L_0x0014:
            java.lang.String r4 = "dynamicId"
            java.lang.String r4 = r1.optString(r4)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "action"
            java.lang.String r5 = r1.optString(r5)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r6 = "attachAction"
            java.lang.String r6 = r1.optString(r6)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r7 = "sdkBizData"
            java.lang.String r7 = r1.optString(r7)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r8 = "mcashierParamStr"
            java.lang.String r8 = r1.optString(r8)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r9 = "dynamicIds"
            java.lang.String r9 = r1.optString(r9)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r10 = "appName"
            java.lang.String r10 = r1.optString(r10)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r11 = "externalToken"
            java.lang.String r11 = r1.optString(r11)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r12 = "payCodePageVisible"
            r13 = 1
            boolean r1 = r1.optBoolean(r12, r13)     // Catch:{ Throwable -> 0x02ca }
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ Throwable -> 0x02ca }
            r12.<init>()     // Catch:{ Throwable -> 0x02ca }
            boolean r14 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x02ca }
            if (r14 != 0) goto L_0x006a
            java.lang.String r14 = "#"
            java.lang.String[] r9 = r9.split(r14)     // Catch:{ Throwable -> 0x02ca }
            int r14 = r9.length     // Catch:{ Throwable -> 0x02ca }
            r15 = 0
        L_0x005f:
            if (r15 >= r14) goto L_0x006a
            r13 = r9[r15]     // Catch:{ Throwable -> 0x02ca }
            r12.add(r13)     // Catch:{ Throwable -> 0x02ca }
            int r15 = r15 + 1
            r13 = 1
            goto L_0x005f
        L_0x006a:
            boolean r9 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x02ca }
            if (r9 == 0) goto L_0x0078
            boolean r9 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x02ca }
            if (r9 == 0) goto L_0x0078
            r9 = 1
            goto L_0x0079
        L_0x0078:
            r9 = 0
        L_0x0079:
            if (r9 == 0) goto L_0x00a9
            com.alipay.android.phone.inside.api.result.code.QueryPayCode r1 = com.alipay.android.phone.inside.api.result.code.QueryPayCode.QUERY_UNKNOWN     // Catch:{ Throwable -> 0x02ca }
            r3.setCode(r1)     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.log.api.ex.ExceptionLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.e()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r6 = "querycode"
            java.lang.String r7 = "AnalysisParamsIllegalEx"
            r8 = 1
            java.lang.String[] r8 = new java.lang.String[r8]     // Catch:{ Throwable -> 0x02ca }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r10 = "dynamicId:"
            r9.<init>(r10)     // Catch:{ Throwable -> 0x02ca }
            r9.append(r4)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r4 = ",action:"
            r9.append(r4)     // Catch:{ Throwable -> 0x02ca }
            r9.append(r5)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r4 = r9.toString()     // Catch:{ Throwable -> 0x02ca }
            r9 = 0
            r8[r9] = r4     // Catch:{ Throwable -> 0x02ca }
            r1.a(r6, r7, r8)     // Catch:{ Throwable -> 0x02ca }
            monitor-exit(r2)
            return r3
        L_0x00a9:
            r9 = 0
            java.lang.String r13 = "DELSEED"
            boolean r13 = r13.equals(r6)     // Catch:{ Throwable -> 0x02ca }
            if (r13 != 0) goto L_0x00bd
            java.lang.String r13 = "DELSEEDANDINDEX"
            boolean r6 = r13.equals(r6)     // Catch:{ Throwable -> 0x02ca }
            if (r6 == 0) goto L_0x00bb
            goto L_0x00bd
        L_0x00bb:
            r6 = 0
            goto L_0x00cb
        L_0x00bd:
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r6 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r13 = "querycode"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r14 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r15 = "QueryCodeDeleteSeed"
            r6.b(r13, r14, r15)     // Catch:{ Throwable -> 0x02ca }
            r6 = 1
        L_0x00cb:
            if (r6 == 0) goto L_0x00de
            boolean r13 = com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig.q()     // Catch:{ Throwable -> 0x02ca }
            if (r13 != 0) goto L_0x00d9
            com.alipay.android.phone.inside.api.result.code.QueryPayCode r13 = com.alipay.android.phone.inside.api.result.code.QueryPayCode.AUTH_INVALID     // Catch:{ Throwable -> 0x02ca }
            r3.setCode(r13)     // Catch:{ Throwable -> 0x02ca }
            goto L_0x00de
        L_0x00d9:
            com.alipay.android.phone.inside.api.result.code.QueryPayCode r13 = com.alipay.android.phone.inside.api.result.code.QueryPayCode.QUERY_EXPIRED     // Catch:{ Throwable -> 0x02ca }
            r3.setCode(r13)     // Catch:{ Throwable -> 0x02ca }
        L_0x00de:
            java.lang.String r13 = "pay_success"
            boolean r5 = android.text.TextUtils.equals(r5, r13)     // Catch:{ Throwable -> 0x02ca }
            if (r5 == 0) goto L_0x0128
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "querycode"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r8 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r10 = "PayResultSuccess"
            r1.b(r5, r8, r10)     // Catch:{ Throwable -> 0x02ca }
            android.os.Bundle r1 = new android.os.Bundle     // Catch:{ Throwable -> 0x02ca }
            r1.<init>()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "dynamicId"
            r1.putString(r5, r4)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "isForAlipay"
            boolean r8 = com.alipay.android.phone.inside.commonbiz.ids.StaticConfig.i()     // Catch:{ Throwable -> 0x02ca }
            r1.putBoolean(r5, r8)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "BARCODE_PLUGIN_ACK_CODE"
            com.alipay.android.phone.inside.framework.service.ServiceExecutor.a(r5, r1)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r1 = a(r7)     // Catch:{ Throwable -> 0x02ca }
            boolean r5 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x02ca }
            r8 = 1
            r5 = r5 ^ r8
            if (r5 == 0) goto L_0x011a
            b(r1, r4)     // Catch:{ Throwable -> 0x02ca }
        L_0x011a:
            com.alipay.android.phone.inside.api.result.code.QueryPayCode r1 = com.alipay.android.phone.inside.api.result.code.QueryPayCode.SUCCESS     // Catch:{ Throwable -> 0x02ca }
            r3.setCode(r1)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r1 = a(r7, r4)     // Catch:{ Throwable -> 0x02ca }
            r3.setResult(r1)     // Catch:{ Throwable -> 0x02ca }
            goto L_0x0295
        L_0x0128:
            boolean r5 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x02ca }
            r7 = 1
            r5 = r5 ^ r7
            if (r5 == 0) goto L_0x0295
            if (r1 != 0) goto L_0x0146
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r4 = "querycode"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r5 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r7 = "ToOnlineInVisibleIgnore"
            r1.b(r4, r5, r7)     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.api.result.code.QueryPayCode r1 = com.alipay.android.phone.inside.api.result.code.QueryPayCode.QUERY_IGNORE     // Catch:{ Throwable -> 0x02ca }
            r3.setCode(r1)     // Catch:{ Throwable -> 0x02ca }
            goto L_0x0295
        L_0x0146:
            boolean r1 = a()     // Catch:{ Throwable -> 0x02ca }
            if (r1 == 0) goto L_0x0160
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r4 = "querycode"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r5 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r7 = "ToOnlineLockIgnore"
            r1.b(r4, r5, r7)     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.api.result.code.QueryPayCode r1 = com.alipay.android.phone.inside.api.result.code.QueryPayCode.QUERY_IGNORE     // Catch:{ Throwable -> 0x02ca }
            r3.setCode(r1)     // Catch:{ Throwable -> 0x02ca }
            goto L_0x0295
        L_0x0160:
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "querycode"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r7 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r13 = "ToOnlinePay"
            r1.b(r5, r7, r13)     // Catch:{ Throwable -> 0x02ca }
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Throwable -> 0x02ca }
            if (r1 != 0) goto L_0x018c
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02ca }
            r1.<init>()     // Catch:{ Throwable -> 0x02ca }
            r1.append(r8)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "&app_name=\""
            r1.append(r5)     // Catch:{ Throwable -> 0x02ca }
            r1.append(r10)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "\""
            r1.append(r5)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r8 = r1.toString()     // Catch:{ Throwable -> 0x02ca }
        L_0x018c:
            boolean r1 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Throwable -> 0x02ca }
            if (r1 != 0) goto L_0x01ab
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02ca }
            r1.<init>()     // Catch:{ Throwable -> 0x02ca }
            r1.append(r8)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "&externa_token=\""
            r1.append(r5)     // Catch:{ Throwable -> 0x02ca }
            r1.append(r11)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "\""
            r1.append(r5)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r8 = r1.toString()     // Catch:{ Throwable -> 0x02ca }
        L_0x01ab:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "inside"
            java.lang.String r7 = "QueryCodeResultUtil::buildPayInfo > payInfo:"
            java.lang.String r10 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r7 = r7.concat(r10)     // Catch:{ Throwable -> 0x02ca }
            r1.b(r5, r7)     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.api.result.OperationResult r1 = new com.alipay.android.phone.inside.api.result.OperationResult     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.api.result.code.QueryPayCode r5 = com.alipay.android.phone.inside.api.result.code.QueryPayCode.QUERY_FAILED     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r7 = r17.getActionName()     // Catch:{ Throwable -> 0x02ca }
            r1.<init>(r5, r7)     // Catch:{ Throwable -> 0x02ca }
            boolean r5 = r12.contains(r4)     // Catch:{ Throwable -> 0x02ca }
            if (r5 == 0) goto L_0x0275
            java.util.List<java.lang.String> r5 = a     // Catch:{ Throwable -> 0x02ca }
            boolean r5 = r5.contains(r4)     // Catch:{ Throwable -> 0x02ca }
            if (r5 != 0) goto L_0x0275
            java.util.List<java.lang.String> r5 = a     // Catch:{ Throwable -> 0x02ca }
            r5.add(r4)     // Catch:{ Throwable -> 0x02ca }
            boolean r5 = com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig.q()     // Catch:{ Throwable -> 0x02ca }
            if (r5 == 0) goto L_0x01ea
            r5 = r17
            com.alipay.android.phone.inside.api.result.OperationResult r1 = a(r5, r8, r4)     // Catch:{ Throwable -> 0x02ca }
            goto L_0x0287
        L_0x01ea:
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Throwable -> 0x02ca }
            r5.<init>()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r7 = "payStr"
            r5.put(r7, r8)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r7 = "dynamicId"
            r5.put(r7, r4)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r7 = "needInitOtp"
            r5.put(r7, r6)     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.api.action.ActionEnum r7 = com.alipay.android.phone.inside.api.action.ActionEnum.ONLINE_PAY     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r7 = r7.getActionName()     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.commonbiz.action.SdkAction r7 = com.alipay.android.phone.inside.commonbiz.action.SdkActionFactory.a(r7)     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.api.result.OperationResult r5 = r7.a(r5)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r7 = "memo"
            java.lang.String r7 = r5.getExtParams(r7)     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.api.result.ResultCode r8 = r5.getCode()     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.api.result.code.OnlinePayCode r10 = com.alipay.android.phone.inside.api.result.code.OnlinePayCode.SUCCESS     // Catch:{ Throwable -> 0x02ca }
            if (r8 != r10) goto L_0x0255
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r8 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r9 = "querycode"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r10 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r11 = "ToOnlinePaySuccess"
            r8.b(r9, r10, r11)     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.api.result.code.QueryPayCode r8 = com.alipay.android.phone.inside.api.result.code.QueryPayCode.SUCCESS     // Catch:{ Throwable -> 0x02ca }
            r1.setCode(r8)     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.main.action.provider.OtpSeedOpProvider r8 = new com.alipay.android.phone.inside.main.action.provider.OtpSeedOpProvider     // Catch:{ Throwable -> 0x02ca }
            r8.<init>()     // Catch:{ Throwable -> 0x02ca }
            boolean r7 = com.alipay.android.phone.inside.main.action.provider.OtpSeedOpProvider.a(r7)     // Catch:{ Throwable -> 0x02ca }
            if (r6 == 0) goto L_0x023c
            java.lang.String r8 = java.lang.String.valueOf(r7)     // Catch:{ Throwable -> 0x02ca }
            goto L_0x023e
        L_0x023c:
            java.lang.String r8 = "noNeed"
        L_0x023e:
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r9 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r10 = "querycode"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r11 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r12 = "ToOnlinePayInitOtp|"
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r8 = r12.concat(r8)     // Catch:{ Throwable -> 0x02ca }
            r9.b(r10, r11, r8)     // Catch:{ Throwable -> 0x02ca }
            r15 = r7
            goto L_0x0268
        L_0x0255:
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r7 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r8 = "querycode"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r10 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r11 = "ToOnlinePayFailed"
            r7.b(r8, r10, r11)     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.api.result.code.QueryPayCode r7 = com.alipay.android.phone.inside.api.result.code.QueryPayCode.QUERY_EXPIRED     // Catch:{ Throwable -> 0x02ca }
            r1.setCode(r7)     // Catch:{ Throwable -> 0x02ca }
            r15 = 0
        L_0x0268:
            java.lang.String r5 = r5.getResult()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r4 = a(r5, r4)     // Catch:{ Throwable -> 0x02ca }
            r1.setResult(r4)     // Catch:{ Throwable -> 0x02ca }
            r9 = r15
            goto L_0x0287
        L_0x0275:
            com.alipay.android.phone.inside.api.result.code.QueryPayCode r4 = com.alipay.android.phone.inside.api.result.code.QueryPayCode.QUERY_IGNORE     // Catch:{ Throwable -> 0x02ca }
            r1.setCode(r4)     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r4 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "querycode"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r7 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r8 = "ToOnlinePayIgnore"
            r4.b(r5, r7, r8)     // Catch:{ Throwable -> 0x02ca }
        L_0x0287:
            com.alipay.android.phone.inside.main.action.util.QueryCodeResultUtil$ToOnlineResult r4 = new com.alipay.android.phone.inside.main.action.util.QueryCodeResultUtil$ToOnlineResult     // Catch:{ Throwable -> 0x02ca }
            r4.<init>(r1, r9)     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.api.result.OperationResult<com.alipay.android.phone.inside.api.result.code.QueryPayCode> r1 = r4.a     // Catch:{ Throwable -> 0x02ca }
            boolean r15 = r4.b     // Catch:{ Throwable -> 0x0292 }
            r3 = r1
            goto L_0x0296
        L_0x0292:
            r0 = move-exception
            r3 = r1
            goto L_0x02cb
        L_0x0295:
            r15 = 0
        L_0x0296:
            if (r6 == 0) goto L_0x02d7
            if (r15 != 0) goto L_0x02d7
            if (r3 == 0) goto L_0x02d7
            java.lang.String r1 = r3.getCodeValue()     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.api.result.code.QueryPayCode r4 = com.alipay.android.phone.inside.api.result.code.QueryPayCode.QUERY_IGNORE     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r4 = r4.getValue()     // Catch:{ Throwable -> 0x02ca }
            boolean r1 = android.text.TextUtils.equals(r1, r4)     // Catch:{ Throwable -> 0x02ca }
            if (r1 != 0) goto L_0x02d7
            com.alipay.android.phone.inside.main.action.provider.OtpSeedOpProvider r1 = new com.alipay.android.phone.inside.main.action.provider.OtpSeedOpProvider     // Catch:{ Throwable -> 0x02ca }
            r1.<init>()     // Catch:{ Throwable -> 0x02ca }
            boolean r1 = com.alipay.android.phone.inside.main.action.provider.OtpSeedOpProvider.a()     // Catch:{ Throwable -> 0x02ca }
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r4 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r5 = "barcode"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r6 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r7 = "QueryCodeDeleteSeed"
            com.alipay.android.phone.inside.log.api.behavior.Behavior r4 = r4.a(r5, r6, r7)     // Catch:{ Throwable -> 0x02ca }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x02ca }
            r4.g = r1     // Catch:{ Throwable -> 0x02ca }
            goto L_0x02d7
        L_0x02ca:
            r0 = move-exception
        L_0x02cb:
            r1 = r0
            com.alipay.android.phone.inside.log.api.ex.ExceptionLogger r4 = com.alipay.android.phone.inside.log.api.LoggerFactory.e()     // Catch:{ all -> 0x02d9 }
            java.lang.String r5 = "querycode"
            java.lang.String r6 = "QueryCodeAnalysisEx"
            r4.a(r5, r6, r1)     // Catch:{ all -> 0x02d9 }
        L_0x02d7:
            monitor-exit(r2)
            return r3
        L_0x02d9:
            r0 = move-exception
            r1 = r0
            monitor-exit(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.main.action.util.QueryCodeResultUtil.a(com.alipay.android.phone.inside.api.action.ActionEnum, org.json.JSONObject):com.alipay.android.phone.inside.api.result.OperationResult");
    }

    private static OperationResult<QueryPayCode> a(ActionEnum actionEnum, String str, String str2) {
        OperationResult<QueryPayCode> operationResult = new OperationResult<>(QueryPayCode.QUERY_FAILED, actionEnum.getActionName());
        Bundle bundle = new Bundle();
        bundle.putString("payInfo", str);
        String str3 = "";
        try {
            Bundle bundle2 = new Bundle();
            bundle2.putString(c.b, IPreloadManager.SIR_COMMON_TYPE);
            str3 = (String) ServiceExecutor.b(PhoneCashierPlugin.KEY_SERVICE_INSIDE_ENV, bundle2);
        } catch (Exception e) {
            LoggerFactory.f().b((String) "inside", (Throwable) e);
        }
        bundle.putString("insideEnv", str3);
        try {
            Bundle bundle3 = (Bundle) ServiceExecutor.b("WALLET_PLUGIN_CASHIER_PAY_SERVICE", bundle);
            String string = bundle3.getString("resultCode");
            String string2 = bundle3.getString("resultValue");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                String optString = new JSONObject(string2).optString("result");
                operationResult.setCode(QueryPayCode.SUCCESS);
                operationResult.setResult(a(OnlinePayAction.a(optString), str2));
            } else {
                operationResult.setResult(string2);
                operationResult.setCode(QueryPayCode.QUERY_EXPIRED);
            }
        } catch (Exception e2) {
            operationResult.setCode(QueryPayCode.QUERY_EXPIRED);
            LoggerFactory.f().b((String) "inside", (Throwable) e2);
        }
        return operationResult;
    }

    private static String a(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        if (TextUtils.isEmpty(str)) {
            LoggerFactory.d().b("querycode", BehaviorType.EVENT, "SdkBizDataEmpty");
        } else if (str.startsWith("{")) {
            try {
                JSONObject jSONObject2 = new JSONObject(str);
                if (!jSONObject2.has("dataFor")) {
                    jSONObject = new JSONObject(str);
                } else if (TextUtils.equals(jSONObject2.optString("dataFor", null), "cashierAndCaller")) {
                    JSONObject optJSONObject = jSONObject2.optJSONObject("callerDataDetail");
                    if (optJSONObject != null) {
                        jSONObject = optJSONObject;
                    }
                }
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "querycode", (String) "GetResultDataEx", th);
            }
        }
        try {
            if (jSONObject.length() > 0) {
                jSONObject.put("payCode", str2);
            }
        } catch (Throwable th2) {
            LoggerFactory.f().c((String) "inside", th2);
        }
        return jSONObject.toString();
    }

    private static String a(String str) {
        String str2 = null;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("dataFor", null);
            LoggerFactory.f().b((String) "inside", "QueryCodeResultUtil::showResultPage > dataFor:".concat(String.valueOf(optString)));
            if (TextUtils.equals(optString, "cashier") || TextUtils.equals(optString, "cashierAndCaller")) {
                JSONObject optJSONObject = jSONObject.optJSONObject("dataDetail");
                if (optJSONObject != null) {
                    String jSONObject2 = optJSONObject.toString();
                    try {
                        LoggerFactory.d().b("querycode", BehaviorType.EVENT, "NeedShowResultPage");
                        return jSONObject2;
                    } catch (Throwable th) {
                        Throwable th2 = th;
                        str2 = jSONObject2;
                        th = th2;
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
            LoggerFactory.e().a((String) "querycode", (String) "AnalysisSdkBizDataEx", th);
            return str2;
        }
        return str2;
    }

    private static boolean b(String str, String str2) {
        if (a.contains(str2)) {
            LoggerFactory.d().b("querycode", BehaviorType.EVENT, "ShowResultPageIgnore");
            return false;
        }
        a.add(str2);
        LoggerFactory.d().b("querycode", BehaviorType.EVENT, "ShowResultPage");
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("renderData", str);
            SdkActionFactory.a(ActionEnum.OFFLINE_RENDER.getActionName()).a(jSONObject);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "querycode", (String) "OfflineRenderEx", th);
        }
        return true;
    }

    private static boolean a() {
        try {
            return ((KeyguardManager) LauncherApplication.a().getSystemService("keyguard")).inKeyguardRestrictedInputMode();
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "querycode", (String) "NeedUnLockScreenEx", th);
            return false;
        }
    }
}
