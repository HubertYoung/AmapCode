package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.OnlinePayCode;
import com.alipay.android.phone.inside.cashier.PhoneCashierPlugin;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import java.net.URLDecoder;
import org.json.JSONObject;

public class OnlinePayAction implements SdkAction {
    public final String a() {
        return ActionEnum.ONLINE_PAY.getActionName();
    }

    private static String a(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("bizType", "innerQrCodeToOnline");
        bundle.putBoolean("needInitOtp", z);
        try {
            return (String) ServiceExecutor.b(PhoneCashierPlugin.KEY_SERVICE_EXTEND_PARAMS, bundle);
        } catch (Exception e) {
            LoggerFactory.f().b((String) "insideSdk", (Throwable) e);
            return "";
        }
    }

    public final OperationResult<OnlinePayCode> a(final JSONObject jSONObject) {
        final OperationResult<OnlinePayCode> operationResult = new OperationResult<>(OnlinePayCode.CANCEL, ActionEnum.ONLINE_PAY.getActionName());
        try {
            Bundle bundle = new Bundle();
            String optString = jSONObject.optString("payStr");
            boolean optBoolean = jSONObject.optBoolean("needInitOtp", false);
            if (TextUtils.isEmpty(optString)) {
                LoggerFactory.e().a("action", "CashierPayParamsIllegal");
                operationResult.setCode(OnlinePayCode.PARAMS_ILLEGAL);
                return operationResult;
            }
            bundle.putString("order_info", optString);
            bundle.putString("extend_params", a(optBoolean));
            LoggerFactory.d().b("action", BehaviorType.EVENT, "CashierPayStart");
            ServiceExecutor.a(PhoneCashierPlugin.KEY_SERVICE_PAY, bundle, new IInsideServiceCallback<Bundle>() {
                /* JADX WARNING: Can't wrap try/catch for region: R(5:22|23|24|8b|29) */
                /* JADX WARNING: Code restructure failed: missing block: B:21:0x0080, code lost:
                    r7 = move-exception;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
                    r0.setCode(com.alipay.android.phone.inside.api.result.code.OnlinePayCode.INNER_EX);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:25:0x008b, code lost:
                    monitor-enter(r0);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
                    r0.notifyAll();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:29:0x0092, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:34:0x0098, code lost:
                    monitor-enter(r0);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
                    r0.notifyAll();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:38:0x009f, code lost:
                    throw r7;
                 */
                /* JADX WARNING: Failed to process nested try/catch */
                /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x0082 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public /* synthetic */ void onComplted(java.lang.Object r7) {
                    /*
                        r6 = this;
                        android.os.Bundle r7 = (android.os.Bundle) r7
                        java.lang.String r0 = "resultStatus"
                        java.lang.String r0 = r7.getString(r0)     // Catch:{ Throwable -> 0x0082 }
                        com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ Throwable -> 0x0082 }
                        java.lang.String r2 = "action"
                        com.alipay.android.phone.inside.log.api.behavior.BehaviorType r3 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ Throwable -> 0x0082 }
                        java.lang.String r4 = "CashierPayEnd|"
                        java.lang.String r5 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x0082 }
                        java.lang.String r4 = r4.concat(r5)     // Catch:{ Throwable -> 0x0082 }
                        r1.b(r2, r3, r4)     // Catch:{ Throwable -> 0x0082 }
                        com.alipay.android.phone.inside.api.result.OperationResult r1 = r0     // Catch:{ Throwable -> 0x0082 }
                        java.lang.String r2 = "memo"
                        java.lang.String r3 = "memo"
                        r4 = 0
                        java.lang.String r3 = r7.getString(r3, r4)     // Catch:{ Throwable -> 0x0082 }
                        r1.addExtParams(r2, r3)     // Catch:{ Throwable -> 0x0082 }
                        java.lang.String r1 = "9000"
                        boolean r1 = r1.equals(r0)     // Catch:{ Throwable -> 0x0082 }
                        if (r1 == 0) goto L_0x004c
                        java.lang.String r0 = "result"
                        java.lang.String r7 = r7.getString(r0)     // Catch:{ Throwable -> 0x0082 }
                        java.lang.String r7 = com.alipay.android.phone.inside.main.action.OnlinePayAction.a(r7)     // Catch:{ Throwable -> 0x0082 }
                        com.alipay.android.phone.inside.api.result.OperationResult r0 = r0     // Catch:{ Throwable -> 0x0082 }
                        r0.setResult(r7)     // Catch:{ Throwable -> 0x0082 }
                        com.alipay.android.phone.inside.api.result.OperationResult r7 = r0     // Catch:{ Throwable -> 0x0082 }
                        com.alipay.android.phone.inside.api.result.code.OnlinePayCode r0 = com.alipay.android.phone.inside.api.result.code.OnlinePayCode.SUCCESS     // Catch:{ Throwable -> 0x0082 }
                        r7.setCode(r0)     // Catch:{ Throwable -> 0x0082 }
                        goto L_0x0073
                    L_0x004c:
                        java.lang.String r7 = "8000"
                        boolean r7 = r7.equals(r0)     // Catch:{ Throwable -> 0x0082 }
                        if (r7 == 0) goto L_0x005c
                        com.alipay.android.phone.inside.api.result.OperationResult r7 = r0     // Catch:{ Throwable -> 0x0082 }
                        com.alipay.android.phone.inside.api.result.code.OnlinePayCode r0 = com.alipay.android.phone.inside.api.result.code.OnlinePayCode.PAY_UNKNOWN     // Catch:{ Throwable -> 0x0082 }
                        r7.setCode(r0)     // Catch:{ Throwable -> 0x0082 }
                        goto L_0x0073
                    L_0x005c:
                        java.lang.String r7 = "6001"
                        boolean r7 = r7.equals(r0)     // Catch:{ Throwable -> 0x0082 }
                        if (r7 == 0) goto L_0x006c
                        com.alipay.android.phone.inside.api.result.OperationResult r7 = r0     // Catch:{ Throwable -> 0x0082 }
                        com.alipay.android.phone.inside.api.result.code.OnlinePayCode r0 = com.alipay.android.phone.inside.api.result.code.OnlinePayCode.CANCEL     // Catch:{ Throwable -> 0x0082 }
                        r7.setCode(r0)     // Catch:{ Throwable -> 0x0082 }
                        goto L_0x0073
                    L_0x006c:
                        com.alipay.android.phone.inside.api.result.OperationResult r7 = r0     // Catch:{ Throwable -> 0x0082 }
                        com.alipay.android.phone.inside.api.result.code.OnlinePayCode r0 = com.alipay.android.phone.inside.api.result.code.OnlinePayCode.FAILED     // Catch:{ Throwable -> 0x0082 }
                        r7.setCode(r0)     // Catch:{ Throwable -> 0x0082 }
                    L_0x0073:
                        com.alipay.android.phone.inside.api.result.OperationResult r7 = r0
                        monitor-enter(r7)
                        com.alipay.android.phone.inside.api.result.OperationResult r0 = r0     // Catch:{ all -> 0x007d }
                        r0.notifyAll()     // Catch:{ all -> 0x007d }
                        monitor-exit(r7)     // Catch:{ all -> 0x007d }
                        return
                    L_0x007d:
                        r0 = move-exception
                        monitor-exit(r7)     // Catch:{ all -> 0x007d }
                        throw r0
                    L_0x0080:
                        r7 = move-exception
                        goto L_0x0096
                    L_0x0082:
                        com.alipay.android.phone.inside.api.result.OperationResult r7 = r0     // Catch:{ all -> 0x0080 }
                        com.alipay.android.phone.inside.api.result.code.OnlinePayCode r0 = com.alipay.android.phone.inside.api.result.code.OnlinePayCode.INNER_EX     // Catch:{ all -> 0x0080 }
                        r7.setCode(r0)     // Catch:{ all -> 0x0080 }
                        com.alipay.android.phone.inside.api.result.OperationResult r7 = r0
                        monitor-enter(r7)
                        com.alipay.android.phone.inside.api.result.OperationResult r0 = r0     // Catch:{ all -> 0x0093 }
                        r0.notifyAll()     // Catch:{ all -> 0x0093 }
                        monitor-exit(r7)     // Catch:{ all -> 0x0093 }
                        return
                    L_0x0093:
                        r0 = move-exception
                        monitor-exit(r7)     // Catch:{ all -> 0x0093 }
                        throw r0
                    L_0x0096:
                        com.alipay.android.phone.inside.api.result.OperationResult r0 = r0
                        monitor-enter(r0)
                        com.alipay.android.phone.inside.api.result.OperationResult r1 = r0     // Catch:{ all -> 0x00a0 }
                        r1.notifyAll()     // Catch:{ all -> 0x00a0 }
                        monitor-exit(r0)     // Catch:{ all -> 0x00a0 }
                        throw r7
                    L_0x00a0:
                        r7 = move-exception
                        monitor-exit(r0)     // Catch:{ all -> 0x00a0 }
                        throw r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.main.action.OnlinePayAction.AnonymousClass1.onComplted(java.lang.Object):void");
                }

                /* JADX WARNING: Code restructure failed: missing block: B:12:0x003f, code lost:
                    r4 = move-exception;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
                    r0.setCode(com.alipay.android.phone.inside.api.result.code.OnlinePayCode.INNER_EX);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:16:0x004a, code lost:
                    monitor-enter(r0);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
                    r0.notifyAll();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:20:0x0051, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:25:0x0057, code lost:
                    monitor-enter(r0);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
                    r0.notifyAll();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:29:0x005e, code lost:
                    throw r4;
                 */
                /* JADX WARNING: Failed to process nested try/catch */
                /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0041 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void onException(java.lang.Throwable r4) {
                    /*
                        r3 = this;
                        com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
                        java.lang.String r1 = "insideSdk"
                        r0.c(r1, r4)
                        org.json.JSONObject r4 = new org.json.JSONObject
                        r4.<init>()
                        java.lang.String r0 = "dynamicId"
                        org.json.JSONObject r1 = r7     // Catch:{ Throwable -> 0x0041 }
                        java.lang.String r2 = "dynamicId"
                        java.lang.String r1 = r1.getString(r2)     // Catch:{ Throwable -> 0x0041 }
                        r4.put(r0, r1)     // Catch:{ Throwable -> 0x0041 }
                        java.lang.String r0 = "payStatus"
                        java.lang.String r1 = "false"
                        r4.put(r0, r1)     // Catch:{ Throwable -> 0x0041 }
                        com.alipay.android.phone.inside.api.result.OperationResult r0 = r0     // Catch:{ Throwable -> 0x0041 }
                        java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0041 }
                        r0.setResult(r4)     // Catch:{ Throwable -> 0x0041 }
                        com.alipay.android.phone.inside.api.result.OperationResult r4 = r0     // Catch:{ Throwable -> 0x0041 }
                        com.alipay.android.phone.inside.api.result.code.OnlinePayCode r0 = com.alipay.android.phone.inside.api.result.code.OnlinePayCode.INNER_EX     // Catch:{ Throwable -> 0x0041 }
                        r4.setCode(r0)     // Catch:{ Throwable -> 0x0041 }
                        com.alipay.android.phone.inside.api.result.OperationResult r4 = r0
                        monitor-enter(r4)
                        com.alipay.android.phone.inside.api.result.OperationResult r0 = r0     // Catch:{ all -> 0x003c }
                        r0.notifyAll()     // Catch:{ all -> 0x003c }
                        monitor-exit(r4)     // Catch:{ all -> 0x003c }
                        return
                    L_0x003c:
                        r0 = move-exception
                        monitor-exit(r4)     // Catch:{ all -> 0x003c }
                        throw r0
                    L_0x003f:
                        r4 = move-exception
                        goto L_0x0055
                    L_0x0041:
                        com.alipay.android.phone.inside.api.result.OperationResult r4 = r0     // Catch:{ all -> 0x003f }
                        com.alipay.android.phone.inside.api.result.code.OnlinePayCode r0 = com.alipay.android.phone.inside.api.result.code.OnlinePayCode.INNER_EX     // Catch:{ all -> 0x003f }
                        r4.setCode(r0)     // Catch:{ all -> 0x003f }
                        com.alipay.android.phone.inside.api.result.OperationResult r4 = r0
                        monitor-enter(r4)
                        com.alipay.android.phone.inside.api.result.OperationResult r0 = r0     // Catch:{ all -> 0x0052 }
                        r0.notifyAll()     // Catch:{ all -> 0x0052 }
                        monitor-exit(r4)     // Catch:{ all -> 0x0052 }
                        return
                    L_0x0052:
                        r0 = move-exception
                        monitor-exit(r4)     // Catch:{ all -> 0x0052 }
                        throw r0
                    L_0x0055:
                        com.alipay.android.phone.inside.api.result.OperationResult r0 = r0
                        monitor-enter(r0)
                        com.alipay.android.phone.inside.api.result.OperationResult r1 = r0     // Catch:{ all -> 0x005f }
                        r1.notifyAll()     // Catch:{ all -> 0x005f }
                        monitor-exit(r0)     // Catch:{ all -> 0x005f }
                        throw r4
                    L_0x005f:
                        r4 = move-exception
                        monitor-exit(r0)     // Catch:{ all -> 0x005f }
                        throw r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.main.action.OnlinePayAction.AnonymousClass1.onException(java.lang.Throwable):void");
                }
            });
            synchronized (operationResult) {
                operationResult.wait();
            }
            return operationResult;
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "insideSdk", th);
        }
    }

    public static String a(String str) throws Exception {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String str2 = "";
        String[] split = str.split("&");
        int length = split.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String[] split2 = split[i].split("=");
            if ("return_caller_result".equals(split2[0])) {
                str2 = URLDecoder.decode(split2[1], "UTF-8");
                break;
            }
            i++;
        }
        if (str2.length() >= 2 && str2.startsWith("\"") && str2.endsWith("\"")) {
            str2 = str2.substring(1, str2.length() - 1);
        }
        return str2;
    }
}
