package com.ali.user.mobile.login.sso.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.alipay.android.phone.inside.commonbiz.util.ApkVerifyTool;
import com.alipay.mobile.accountauthbiz.AlipaySsoInfo;
import com.alipay.mobile.accountauthbiz.IAlipaySsoService;
import com.alipay.mobile.accountauthbiz.IAlipaySsoService.Stub;

public class SSOBindServiceHelper {
    /* access modifiers changed from: private */
    public Object a = new Object();
    /* access modifiers changed from: private */
    public IAlipaySsoService b;
    /* access modifiers changed from: private */
    public String c;
    private ServiceConnection d = new ServiceConnection() {
        public void onServiceDisconnected(ComponentName componentName) {
            synchronized (SSOBindServiceHelper.this.a) {
                AliUserLog.c("SSOBindServiceHelper", "onAlipayServiceDisconnected");
                SSOBindServiceHelper.this.b = null;
                SSOBindServiceHelper.this.a.notifyAll();
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AliUserLog.c("SSOBindServiceHelper", "onAlipayServiceConnected");
            synchronized (SSOBindServiceHelper.this.a) {
                try {
                    SSOBindServiceHelper.this.b = Stub.a(iBinder);
                    SSOBindServiceHelper.a(SSOBindServiceHelper.this, iBinder);
                } catch (Throwable th) {
                    SSOBindServiceHelper sSOBindServiceHelper = SSOBindServiceHelper.this;
                    StringBuilder sb = new StringBuilder("bindError:");
                    sb.append(th.getMessage());
                    sSOBindServiceHelper.c = sb.toString();
                    AliUserLog.b("SSOBindServiceHelper", "bindServiceError", th);
                }
                SSOBindServiceHelper.this.a.notifyAll();
            }
        }
    };

    public final Bundle a(Context context) throws Throwable {
        Intent intent = new Intent("com.alipay.mobile.accountauthbiz.sso.IAlipaySsoService");
        intent.setPackage("com.eg.android.AlipayGphone");
        Intent intent2 = new Intent();
        intent2.setClassName("com.eg.android.AlipayGphone", "com.alipay.android.app.TransProcessPayActivity");
        try {
            ServiceConnection serviceConnection = this.d;
            if (this.b == null) {
                StringBuilder sb = new StringBuilder("initialize binding ");
                sb.append("com.eg.android.AlipayGphone");
                sb.append(",");
                sb.append(intent2);
                AliUserLog.c("SSOBindServiceHelper", sb.toString());
                boolean a2 = "com.eg.android.AlipayGphone".equals("com.eg.android.AlipayGphone") ? ApkVerifyTool.a(context) : true;
                AliUserLog.c("SSOBindServiceHelper", "preCheck apk Sign:".concat(String.valueOf(a2)));
                if (a2) {
                    AliUserLog.c("SSOBindServiceHelper", "delete start alipay,because it sometime start aliapy page");
                    Thread.sleep(150);
                    AliUserLog.c("SSOBindServiceHelper", "bindService start");
                    LoggerUtils.a("event", "bindservice_getUserInfo_start", "UC-BINDSERVICE-LOG-171115-1", "");
                    context.getApplicationContext().bindService(intent, serviceConnection, 1);
                    synchronized (this.a) {
                        this.a.wait(5000);
                    }
                    StringBuilder sb2 = new StringBuilder("bindService end, remoteAlipayBindCode is ");
                    sb2.append(this.c);
                    AliUserLog.c("SSOBindServiceHelper", sb2.toString() == null ? "null" : this.c);
                } else {
                    AliUserLog.c("SSOBindServiceHelper", "aliapy not install or sign error");
                }
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("com.eg.android.AlipayGphone");
                sb3.append("bind sucess! ");
                AliUserLog.c("SSOBindServiceHelper", sb3.toString());
            }
        } catch (Throwable th) {
            AliUserLog.b("SSOBindServiceHelper", "doInvoke error", th);
        }
        Bundle bundle = new Bundle();
        if (this.b != null) {
            LoggerUtils.a("event", "bindservice_getUserInfo_success", "UC-BINDSERVICE-LOG-171115-2", "");
            AliUserLog.c("SSOBindServiceHelper", "bindService success,invoke remoteService method");
            int a3 = this.b.a();
            AliUserLog.c("SSOBindServiceHelper", "sso_version = ".concat(String.valueOf(a3)));
            if (3 != a3) {
                bundle.putInt("ssoVersionCode", a3);
                return bundle;
            }
            bundle.putInt("ssoVersionCode", a3);
            AliUserLog.c("SSOBindServiceHelper", "invoke getAlipaySsoInfo start");
            AlipaySsoInfo b2 = this.b.b();
            AliUserLog.c("SSOBindServiceHelper", "invoke getAlipaySsoInfo end");
            if (b2 != null) {
                bundle.putString("loginId", b2.loginId);
                bundle.putString("headImg", b2.headImg);
                bundle.putString("ssoToken", b2.ssoToken);
                bundle.putString("userId", b2.userId);
                StringBuilder sb4 = new StringBuilder("loginId=");
                sb4.append(b2.loginId);
                sb4.append(",headImg=");
                sb4.append(b2.headImg);
                sb4.append(",ssotoken=");
                sb4.append(b2.ssoToken);
                sb4.append(",userId=");
                sb4.append(b2.userId);
                AliUserLog.c("SSOBindServiceHelper", sb4.toString());
            }
            return bundle;
        }
        LoggerUtils.a("event", "bindservice_getUserInfo_failed", "UC-BINDSERVICE-LOG-171115-2", "");
        bundle.putInt("ssoVersionCode", 2);
        return bundle;
    }

    static /* synthetic */ void a(SSOBindServiceHelper sSOBindServiceHelper, IBinder iBinder) {
        if (iBinder != null) {
            try {
                iBinder.linkToDeath(new DeathRecipient() {
                    public void binderDied() {
                        SSOBindServiceHelper.this.b = null;
                    }
                }, 0);
            } catch (RemoteException e) {
                AliUserLog.b("SSOBindServiceHelper", "linkToDeath error", e);
            }
        }
    }
}
