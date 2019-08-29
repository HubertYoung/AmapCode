package com.ali.user.mobile.login.sso.impl;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import com.ali.user.mobile.info.AppInfo;
import com.ali.user.mobile.info.DeviceInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.TimeConsumingLogAgent;
import com.ali.user.mobile.login.sso.SSOService;
import com.ali.user.mobile.login.sso.info.SsoLoginInfo;
import com.ali.user.mobile.rpc.facade.CreateSsoTokenFacade;
import com.ali.user.mobile.rpc.vo.sso.KeyValueEntryPB;
import com.ali.user.mobile.rpc.vo.sso.UnifyVerifySSOTokenRequestPb;
import com.ali.user.mobile.rpc.vo.sso.UnifyVerifySSOTokenResultPb;
import com.ali.user.mobile.rpc.vo.sso.VerifySsoTokenRequestPb;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public final class SsoServiceImpl implements SSOService {
    private static SsoServiceImpl d;
    Callable<SsoLoginInfo> a = new Callable<SsoLoginInfo>() {
        /* access modifiers changed from: private */
        /* renamed from: a */
        public SsoLoginInfo call() {
            try {
                if (SsoServiceImpl.this.b()) {
                    Bundle a2 = SsoServiceImpl.c(SsoServiceImpl.this).a(SsoServiceImpl.this.b);
                    int i = a2.getInt("ssoVersionCode");
                    if (1 == i) {
                        AliUserLog.c("SsoServiceImpl", "version code is 1");
                        return null;
                    } else if (3 == i) {
                        AliUserLog.c("SsoServiceImpl", "version code is 3");
                        SsoLoginInfo ssoLoginInfo = new SsoLoginInfo();
                        ssoLoginInfo.loginId = a2.getString("loginId");
                        ssoLoginInfo.headImg = a2.getString("headImg");
                        ssoLoginInfo.loginToken = a2.getString("ssoToken");
                        ssoLoginInfo.userId = a2.getString("userId");
                        if (!TextUtils.isEmpty(ssoLoginInfo.loginToken)) {
                            if (!TextUtils.isEmpty(ssoLoginInfo.loginId)) {
                                AliUserLog.c("SsoServiceImpl", "version code is 3 and key params not null");
                                return ssoLoginInfo;
                            }
                        }
                        AliUserLog.c("SsoServiceImpl", "version code is 3 but key params is null,via contentprovider");
                        SsoServiceImpl.d(SsoServiceImpl.this);
                        return SSOProviderHelper.a(SsoServiceImpl.this.b);
                    } else {
                        AliUserLog.c("SsoServiceImpl", "version code is ".concat(String.valueOf(i)));
                        SsoServiceImpl.d(SsoServiceImpl.this);
                        return SSOProviderHelper.a(SsoServiceImpl.this.b);
                    }
                } else {
                    SsoServiceImpl.d(SsoServiceImpl.this);
                    return SSOProviderHelper.a(SsoServiceImpl.this.b);
                }
            } catch (Throwable th) {
                AliUserLog.b("SsoServiceImpl", "acquire exception", th);
                return null;
            }
        }
    };
    /* access modifiers changed from: private */
    public final Context b;
    private final CreateSsoTokenFacade c;
    private SSOBindServiceHelper e;
    private SSOProviderHelper f;

    public static SsoServiceImpl a(Context context) {
        synchronized (SsoServiceImpl.class) {
            try {
                if (d == null) {
                    d = new SsoServiceImpl(context);
                }
            }
        }
        return d;
    }

    private SsoServiceImpl(Context context) {
        this.b = context;
        this.c = (CreateSsoTokenFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(CreateSsoTokenFacade.class);
    }

    /* JADX INFO: finally extract failed */
    public final SsoLoginInfo a() {
        if (c()) {
            throw new RuntimeException("can not invoke on main thread!");
        }
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        FutureTask futureTask = new FutureTask(this.a);
        newSingleThreadExecutor.execute(futureTask);
        AliUserLog.c("SsoServiceImpl", "getResultTimeout executed");
        try {
            SsoLoginInfo ssoLoginInfo = (SsoLoginInfo) futureTask.get(10, TimeUnit.SECONDS);
            newSingleThreadExecutor.shutdown();
            return ssoLoginInfo;
        } catch (Throwable th) {
            newSingleThreadExecutor.shutdown();
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public boolean b() {
        try {
            boolean z = this.b.getPackageManager().getPackageInfo("com.eg.android.AlipayGphone", 0).versionCode >= 121;
            AliUserLog.c("SsoServiceImpl", "is support sso bind service : ".concat(String.valueOf(z)));
            return z;
        } catch (Throwable th) {
            AliUserLog.b("SsoServiceImpl", "isAlipaySupportSSOService error", th);
            return false;
        }
    }

    public final SsoLoginInfo a(SsoLoginInfo ssoLoginInfo) {
        if (c()) {
            throw new RuntimeException("can not invoke on main thread!");
        }
        try {
            AliUserLog.c("SsoServiceImpl", String.format("start verifyAlipaySsoToken:%s", new Object[]{ssoLoginInfo}));
            if (ssoLoginInfo != null && !TextUtils.isEmpty(ssoLoginInfo.loginToken)) {
                UnifyVerifySSOTokenResultPb a2 = a(ssoLoginInfo.loginId, ssoLoginInfo.loginToken);
                if (a2 != null && a2.success.booleanValue()) {
                    AliUserLog.c("SsoServiceImpl", "unifyVerifySsoTokenPb success");
                    ssoLoginInfo.loginToken = a2.loginToken;
                    ssoLoginInfo.isDirectLogin = a2.isDirectLogin;
                    return ssoLoginInfo;
                }
            }
        } catch (Throwable th) {
            AliUserLog.b((String) "SsoServiceImpl", th);
        }
        return null;
    }

    private UnifyVerifySSOTokenResultPb a(String str, String str2) {
        TimeConsumingLogAgent timeConsumingLogAgent = new TimeConsumingLogAgent("event", "YWUC-JTTYZH-C27sso", "unifyVerifySsoToken.pb");
        timeConsumingLogAgent.a().a("alipay.client.unifyVerifySsoToken.pb");
        try {
            UnifyVerifySSOTokenRequestPb unifyVerifySSOTokenRequestPb = new UnifyVerifySSOTokenRequestPb();
            VerifySsoTokenRequestPb verifySsoTokenRequestPb = new VerifySsoTokenRequestPb();
            verifySsoTokenRequestPb.loginId = str;
            verifySsoTokenRequestPb.alipaySsoToken = str2;
            DeviceInfo.b();
            verifySsoTokenRequestPb.did = DeviceInfo.g();
            verifySsoTokenRequestPb.productId = AppInfo.getInstance().getProductId();
            verifySsoTokenRequestPb.productVersion = AppInfo.getInstance().getProductVersion();
            verifySsoTokenRequestPb.systemType = "android";
            verifySsoTokenRequestPb.externParam = new LinkedList();
            TokenResult tokenResult = AppInfo.getInstance().getTokenResult();
            if (tokenResult != null) {
                KeyValueEntryPB keyValueEntryPB = new KeyValueEntryPB();
                keyValueEntryPB.key = DictionaryKeys.DEV_APDIDTOKEN;
                keyValueEntryPB.value = tokenResult.apdid;
                verifySsoTokenRequestPb.externParam.add(keyValueEntryPB);
                KeyValueEntryPB keyValueEntryPB2 = new KeyValueEntryPB();
                keyValueEntryPB2.key = DictionaryKeys.V2_APDID;
                keyValueEntryPB2.value = tokenResult.apdidToken;
                verifySsoTokenRequestPb.externParam.add(keyValueEntryPB2);
            }
            unifyVerifySSOTokenRequestPb.verifySsoTokenRequest = verifySsoTokenRequestPb;
            UnifyVerifySSOTokenResultPb unifyVerifySsoTokenPb = this.c.unifyVerifySsoTokenPb(unifyVerifySSOTokenRequestPb);
            if (unifyVerifySsoTokenPb == null) {
                timeConsumingLogAgent.b().d("UnifyVerifySSOTokenResultPb=null").c();
            } else {
                timeConsumingLogAgent.b().d(unifyVerifySsoTokenPb.resultCode).c();
            }
            return unifyVerifySsoTokenPb;
        } catch (RpcException e2) {
            AliUserLog.b((String) "SsoServiceImpl", (Throwable) e2);
            TimeConsumingLogAgent.a(timeConsumingLogAgent, e2);
            timeConsumingLogAgent.b().c();
            return null;
        }
    }

    private static boolean c() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    static /* synthetic */ SSOBindServiceHelper c(SsoServiceImpl ssoServiceImpl) {
        if (ssoServiceImpl.e == null) {
            ssoServiceImpl.e = new SSOBindServiceHelper();
        }
        return ssoServiceImpl.e;
    }

    static /* synthetic */ SSOProviderHelper d(SsoServiceImpl ssoServiceImpl) {
        if (ssoServiceImpl.f == null) {
            ssoServiceImpl.f = new SSOProviderHelper();
        }
        return ssoServiceImpl.f;
    }
}
