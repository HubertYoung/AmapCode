package com.alipay.mobile.accountopenauth.biz.insideplugin.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.mobile.accountopenauth.biz.oauthstrategy.FastOAuthStrategy;
import com.alipay.mobile.accountopenauth.biz.oauthstrategy.H5RegOAuthStrategy;
import com.alipay.mobile.accountopenauth.biz.oauthstrategy.StrategyContext;
import com.alipay.mobile.accountopenauth.biz.oauthstrategy.WalletOAuthStrategy;
import com.alipay.mobile.accountopenauth.common.OAuthBehaviorLogger;
import org.json.JSONObject;

public class FastOAuthService implements IInsideService<JSONObject, Bundle> {
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0112  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void start(com.alipay.android.phone.inside.framework.service.IInsideServiceCallback r13, java.lang.Object r14) throws java.lang.Exception {
        /*
            r12 = this;
            org.json.JSONObject r14 = (org.json.JSONObject) r14
            if (r14 == 0) goto L_0x0148
            if (r13 == 0) goto L_0x0148
            com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthServiceManager r0 = com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthServiceManager.getInstance()
            com.alipay.android.phone.inside.api.accountopenauth.IFastOAuthService r0 = r0.getFastOAuthService()
            java.lang.String r1 = "authUrlAddress"
            java.lang.String r1 = r14.optString(r1)
            java.lang.String r2 = "authUrlParams"
            java.lang.String r2 = r14.optString(r2)
            java.lang.String r3 = "phoneNumber"
            java.lang.String r3 = r14.optString(r3)
            java.lang.String r4 = "authUUID"
            long r4 = r14.optLong(r4)
            java.lang.String r6 = "isRecommend"
            boolean r6 = r14.optBoolean(r6)
            java.lang.String r7 = "needShowFastAuthPage"
            boolean r14 = r14.getBoolean(r7)
            android.os.Bundle r7 = new android.os.Bundle
            r7.<init>()
            r8 = 0
            if (r6 != 0) goto L_0x003c
            if (r14 == 0) goto L_0x008e
        L_0x003c:
            if (r0 == 0) goto L_0x0051
            boolean r0 = r0.canShowFastPage(r4)
            if (r0 != 0) goto L_0x0051
            java.lang.String r14 = "FastOAuthService"
            java.lang.String r0 = "canShowFastPage false step 1"
            com.alipay.mobile.accountopenauth.common.OAuthTraceLogger.a(r14, r0)
            java.lang.String r14 = "AUTH_MC_CANCELLED"
            a(r13, r14)
            return
        L_0x0051:
            java.lang.String r0 = "FastOAuthService"
            java.lang.String r9 = "getVerifiedSSOInfo start"
            com.alipay.mobile.accountopenauth.common.OAuthTraceLogger.a(r0, r9)
            android.os.Bundle r0 = com.alipay.mobile.accountopenauth.biz.SSOInfoProvider.a()
            java.lang.String r9 = "FastOAuthService"
            java.lang.String r10 = "getVerifiedSSOInfo end"
            com.alipay.mobile.accountopenauth.common.OAuthTraceLogger.a(r9, r10)
            if (r0 == 0) goto L_0x008e
            java.lang.String r7 = "token"
            java.lang.String r8 = r0.getString(r7)
            java.lang.String r7 = "loginId"
            java.lang.String r7 = r0.getString(r7)
            java.lang.String r9 = "FastOAuthService"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "getVerifiedSSOInfo ssoToken = "
            r10.<init>(r11)
            r10.append(r8)
            java.lang.String r11 = ",loginId = "
            r10.append(r11)
            r10.append(r7)
            java.lang.String r10 = r10.toString()
            com.alipay.mobile.accountopenauth.common.OAuthTraceLogger.a(r9, r10)
            goto L_0x0090
        L_0x008e:
            r0 = r7
            r7 = r8
        L_0x0090:
            java.lang.String r9 = "isRecommend"
            r0.putBoolean(r9, r6)
            java.lang.String r9 = "phoneNumber"
            r0.putString(r9, r3)
            java.lang.String r3 = "authUUID"
            r0.putLong(r3, r4)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r1)
            java.lang.String r1 = "?"
            r3.append(r1)
            r3.append(r2)
            java.lang.String r1 = r3.toString()
            java.lang.String r3 = "FastOAuthService"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "conn authUrl:"
            r4.<init>(r5)
            r4.append(r1)
            java.lang.String r5 = "，isMcRecommend："
            r4.append(r5)
            r4.append(r6)
            java.lang.String r5 = "，needShowFastAuthPage："
            r4.append(r5)
            r4.append(r14)
            java.lang.String r4 = r4.toString()
            com.alipay.mobile.accountopenauth.common.OAuthTraceLogger.a(r3, r4)
            if (r6 == 0) goto L_0x0112
            boolean r14 = android.text.TextUtils.isEmpty(r8)
            if (r14 != 0) goto L_0x00ea
            boolean r14 = android.text.TextUtils.isEmpty(r7)
            if (r14 != 0) goto L_0x00ea
            a(r13, r2, r0, r1)
            return
        L_0x00ea:
            android.os.Bundle r14 = new android.os.Bundle
            r14.<init>()
            java.lang.String r0 = "resultCode"
            java.lang.String r1 = "AUTH_FAILED_ST_INVALID"
            r14.putString(r0, r1)
            java.lang.String r0 = "FastOAuthService"
            java.lang.String r1 = "For user experience，mc recommend,but key param is null,so return failed"
            com.alipay.mobile.accountopenauth.common.OAuthTraceLogger.a(r0, r1)
            java.lang.String r2 = "action"
            java.lang.String r3 = "Enter_OpenAuthLogin_Native_Fast_Failed"
            java.lang.String r4 = "fastoauth"
            java.lang.String r5 = "tokenIsNull"
            java.lang.String r6 = ""
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r7 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT
            com.alipay.mobile.accountopenauth.common.OAuthBehaviorLogger.a(r2, r3, r4, r5, r6, r7)
            r13.onComplted(r14)
            return
        L_0x0112:
            if (r14 == 0) goto L_0x0136
            boolean r14 = android.text.TextUtils.isEmpty(r8)
            if (r14 != 0) goto L_0x0124
            boolean r14 = android.text.TextUtils.isEmpty(r7)
            if (r14 != 0) goto L_0x0124
            a(r13, r2, r0, r1)
            return
        L_0x0124:
            android.app.Application r14 = com.alipay.android.phone.inside.framework.LauncherApplication.a()
            boolean r14 = com.alipay.mobile.accountopenauth.common.CommonUtil.a(r14)
            if (r14 == 0) goto L_0x0132
            b(r13, r1)
            return
        L_0x0132:
            a(r13, r0, r1)
            return
        L_0x0136:
            android.app.Application r14 = com.alipay.android.phone.inside.framework.LauncherApplication.a()
            boolean r14 = com.alipay.mobile.accountopenauth.common.CommonUtil.a(r14)
            if (r14 == 0) goto L_0x0144
            b(r13, r1)
            return
        L_0x0144:
            a(r13, r0, r1)
            return
        L_0x0148:
            java.lang.String r14 = "AUTH_FAILED"
            a(r13, r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.accountopenauth.biz.insideplugin.service.FastOAuthService.start(com.alipay.android.phone.inside.framework.service.IInsideServiceCallback, java.lang.Object):void");
    }

    private static void a(IInsideServiceCallback<Bundle> iInsideServiceCallback, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("resultCode", str);
        if (iInsideServiceCallback != null) {
            iInsideServiceCallback.onComplted(bundle);
        }
    }

    private static void a(IInsideServiceCallback<Bundle> iInsideServiceCallback, Bundle bundle, String str) {
        OAuthBehaviorLogger.a("action", "Enter_OpenAuthLogin_Web_Reg_Fast", "fastoauth", "", "", BehaviorType.EVENT);
        iInsideServiceCallback.onComplted(new StrategyContext(new H5RegOAuthStrategy("fastoauth")).a(str, "", bundle));
    }

    private static void b(IInsideServiceCallback<Bundle> iInsideServiceCallback, String str) {
        OAuthBehaviorLogger.a("action", "Enter_OpenAuthLogin_Wallet_Fast", "fastoauth", "", "", BehaviorType.EVENT);
        iInsideServiceCallback.onComplted(new StrategyContext(new WalletOAuthStrategy("fastoauth")).a(str, "", null));
    }

    private static void a(IInsideServiceCallback<Bundle> iInsideServiceCallback, String str, Bundle bundle, String str2) {
        iInsideServiceCallback.onComplted(new StrategyContext(new FastOAuthStrategy()).a(str2, str, bundle));
    }

    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    public /* synthetic */ void start(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }
}
