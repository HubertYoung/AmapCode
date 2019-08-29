package com.ali.user.mobile.accountbiz.extservice.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.account.domain.MspDeviceInfoBean;
import com.ali.user.mobile.account.model.MobileSecurityResult;
import com.ali.user.mobile.accountbiz.AccountManagerFacadeBiz;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.accountbiz.extservice.AUMemCacheService;
import com.ali.user.mobile.accountbiz.extservice.AuthService;
import com.ali.user.mobile.accountbiz.extservice.DeviceService;
import com.ali.user.mobile.accountbiz.extservice.SecurityInitService;
import com.ali.user.mobile.accountbiz.extservice.base.BaseExtService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogSb;
import com.ali.user.mobile.log.LoggerUtils;
import com.alipay.android.phone.inside.common.info.DeviceInfo;
import com.alipay.android.phone.inside.common.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SecurityInitServiceImpl extends BaseExtService implements SecurityInitService {
    private static final String TAG = "SecurityInitServiceImpl";
    private static SecurityInitService mSecurityService;
    String initFlagSynLock = SecurityInitServiceImpl.class.getName();
    /* access modifiers changed from: private */
    public boolean isScheme;
    /* access modifiers changed from: private */
    public boolean justGenTid;
    private LogSb logSb;
    private LogSb logUpdate;
    private AuthService mAuthService = AntExtServiceManager.getAuthService(this.mContext);
    protected DeviceService mDeviceService = AntExtServiceManager.getDeviceService(this.mContext);
    private AUMemCacheService mMemCacheService = AntExtServiceManager.getMemCacheService();

    class InitThread implements Runnable {
        Intent intent;

        public InitThread(Intent intent2) {
            this.intent = intent2;
        }

        public void run() {
            synchronized (SecurityInitServiceImpl.this.initFlagSynLock) {
                AliUserLog.c(SecurityInitServiceImpl.TAG, String.format("InitThread-intent:%s", new Object[]{this.intent}));
                SecurityInitServiceImpl.this.isScheme = this.intent.getBooleanExtra("toBiz", false);
                SecurityInitServiceImpl.this.justGenTid = this.intent.getBooleanExtra("genTid", false);
                SecurityInitServiceImpl.this.securityInitStart();
            }
        }
    }

    private void checkLogoutForStart() {
    }

    private SecurityInitServiceImpl(Context context) {
        super(context);
    }

    public static SecurityInitService getInstance(Context context) {
        if (mSecurityService == null) {
            synchronized (SecurityInitServiceImpl.class) {
                try {
                    if (mSecurityService == null) {
                        mSecurityService = new SecurityInitServiceImpl(context);
                    }
                }
            }
        }
        return mSecurityService;
    }

    public void securityInit(Intent intent) {
        String stringExtra = intent.getStringExtra("inited_param");
        this.logSb = new LogSb();
        if (SecurityUtil.a(stringExtra)) {
            this.logSb.a("isNoNeedAutoLogin = true");
            LoggerUtils.a(this.logSb.toString());
            AliUserLog.a((String) TAG, String.format("在不需要自动登陆set中所以不做安全模块初始化和免登操作,intent uri: %s", new Object[]{intent.getData()}));
            return;
        }
        DeviceInfo.c();
        SecurityUtil.a((Runnable) new InitThread(intent));
    }

    public void securityInitStart() {
        try {
            AliUserLog.c(TAG, String.format("securityInitStart, justGenTid:%s, isToBiz:%s", new Object[]{Boolean.valueOf(this.justGenTid), Boolean.valueOf(this.isScheme)}));
            putLoginKey("justGenTid", Boolean.valueOf(this.justGenTid));
            putLoginKey("isToBiz", Boolean.valueOf(this.isScheme));
            if (this.mAuthService != null) {
                this.mAuthService.logLoginKey();
            }
            if (this.justGenTid) {
                putLoginKey("tid", "false");
                this.logSb.a("justGenTid = true");
                AliUserLog.c(TAG, "当前客户端中不存在用户，首次启动生成tid");
                generateDid();
                LoggerUtils.a(this.logSb.toString());
                return;
            }
            this.logSb.a("justGenTid = false");
            AliUserLog.c(TAG, "从本地获取tid，如果已经存在tid校验是否合法，如果不存在重新生成tid");
            String tid = this.mDeviceService.queryCertification().getTid();
            if (!TextUtils.isEmpty(tid)) {
                putLoginKey("tid", "true");
                this.logSb.a("tid != null, tid = ".concat(String.valueOf(tid)));
                UserInfo loginUserInfo = this.mAuthService.getLoginUserInfo();
                if (loginUserInfo == null || !loginUserInfo.isAutoLogin()) {
                    if (loginUserInfo != null) {
                        AliUserLog.c(TAG, "userInfo.isAutoLogin()=false");
                        this.logSb.a("userInfo != null && userInfo.isAutoLogin()=false");
                    } else {
                        AliUserLog.c(TAG, "userInfo == null");
                        this.logSb.a("userInfo == null");
                        LogSb logSb2 = this.logSb;
                        StringBuilder sb = new StringBuilder("mAuthService.isLogin() = ");
                        sb.append(this.mAuthService.isLogin());
                        logSb2.a(sb.toString());
                    }
                    AliUserLog.c(TAG, "无免登用户，tablauncher调起登录");
                    checkLogoutForStart();
                    return;
                }
                AliUserLog.c(TAG, "开始自动登录");
                this.logSb.a("userInfo != null && userInfo.isAutoLogin()=true");
                notifyLogin(loginUserInfo);
                return;
            }
            putLoginKey("tid", "null");
            this.logSb.a("tid = null");
            AliUserLog.c(TAG, "tid为null，从快捷获取tid并保存在客户端本地");
            generateDid();
            AliUserLog.c(TAG, "此时不会免登，但需要调pipeline");
            startPipelineForLoginFail();
            LoggerUtils.a(this.logSb.toString());
        } catch (Exception e) {
            LogSb logSb3 = this.logSb;
            StringBuilder sb2 = new StringBuilder("securityInitStart exception = ");
            sb2.append(e.getMessage());
            logSb3.a(sb2.toString());
            AliUserLog.a((String) TAG, (Throwable) e);
            LoggerUtils.a(this.logSb.toString());
        }
    }

    private MspDeviceInfoBean getTidFromMsp() {
        AliUserLog.c(TAG, "从移动快捷获取tid开始");
        try {
            return this.mDeviceService.queryCertification();
        } catch (Exception e) {
            AliUserLog.a((String) TAG, (Throwable) e);
            return null;
        }
    }

    private void notifyLogin(UserInfo userInfo) {
        AliUserLog.c(TAG, String.format("notify login, userInfo=%s", new Object[]{userInfo}));
        LogSb logSb2 = this.logSb;
        StringBuilder sb = new StringBuilder("notifyLogin  isToBiz = ");
        sb.append(this.isScheme);
        logSb2.a(sb.toString());
        if (userInfo != null) {
            AliUserLog.c(TAG, String.format("isAutoLogin:%s", new Object[]{Boolean.valueOf(userInfo.isAutoLogin())}));
            if (userInfo.isAutoLogin()) {
                startAutoLogin();
                return;
            }
            if (!this.isScheme) {
                AliUserLog.c(TAG, "another showLoginActivity");
                this.logSb.a("showLoginActivity userInfo != null && !isToBiz");
                LoggerUtils.a(this.logSb.toString());
                showLoginActivity(null);
            }
            return;
        }
        if (!this.isScheme) {
            AliUserLog.c(TAG, "showLoginActivity");
            this.logSb.a("showLoginActivity userInfo == null");
            LoggerUtils.a(this.logSb.toString());
            showLoginActivity(null);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0081, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0082, code lost:
        r1 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0086, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2 = r8.logSb;
        r3 = new java.lang.StringBuilder("RpcException:");
        r3.append(r1.getMessage());
        r2.a(r3.toString());
        com.ali.user.mobile.log.AliUserLog.a((java.lang.String) TAG, (java.lang.Throwable) r1);
        com.ali.user.mobile.log.LoggerUtils.a(r8.logSb.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00ac, code lost:
        startPipelineForLoginFail();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00af, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b0, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00b1, code lost:
        r0 = r1;
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00b5, code lost:
        startPipelineForLoginFail();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0086 A[ExcHandler: RpcException (r1v2 'e' com.alipay.inside.android.phone.mrpc.core.RpcException A[CUSTOM_DECLARE]), Splitter:B:1:0x0002] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00b5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void startAutoLogin() {
        /*
            r8 = this;
            r0 = 1
            r1 = 0
            com.ali.user.mobile.log.LogSb r2 = r8.logSb     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            java.lang.String r4 = "startAutoLogin isToBiz = "
            r3.<init>(r4)     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            boolean r4 = r8.isScheme     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            r3.append(r4)     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            java.lang.String r3 = r3.toString()     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            r2.a(r3)     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            java.lang.String r2 = "SecurityInitServiceImpl"
            java.lang.String r3 = "start auto login"
            com.ali.user.mobile.log.AliUserLog.c(r2, r3)     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            com.ali.user.mobile.accountbiz.extservice.AuthService r2 = r8.mAuthService     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            android.os.Bundle r2 = r2.autoAuth()     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            java.lang.String r3 = "auto_login_result_logined"
            boolean r3 = r2.getBoolean(r3)     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            java.lang.String r4 = "SecurityInitServiceImpl"
            java.lang.String r5 = "免登logined"
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            java.lang.String r5 = r5.concat(r6)     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            com.ali.user.mobile.log.AliUserLog.c(r4, r5)     // Catch:{ RpcException -> 0x0086, all -> 0x0084 }
            r4 = r3 ^ 1
            com.ali.user.mobile.log.LogSb r5 = r8.logSb     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            java.lang.String r6 = "mAuthService.autoAuth() is success?"
            java.lang.String r7 = java.lang.String.valueOf(r3)     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            java.lang.String r6 = r6.concat(r7)     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            r5.a(r6)     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            boolean r5 = r8.isScheme     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            if (r5 != 0) goto L_0x0068
            if (r3 != 0) goto L_0x0068
            com.ali.user.mobile.log.LogSb r3 = r8.logSb     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            java.lang.String r5 = "showLoginActivity"
            r3.a(r5)     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            com.ali.user.mobile.log.LogSb r3 = r8.logSb     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            java.lang.String r3 = r3.toString()     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            com.ali.user.mobile.log.LoggerUtils.a(r3)     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            r8.showLoginActivity(r2)     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            goto L_0x007a
        L_0x0068:
            com.ali.user.mobile.log.LogSb r1 = r8.logSb     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            java.lang.String r1 = r1.toString()     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            com.ali.user.mobile.log.LoggerUtils.a(r1)     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            java.lang.String r1 = "SecurityInitServiceImpl"
            java.lang.String r2 = "startAutoLogin success, reportDeviceLocation"
            com.ali.user.mobile.log.AliUserLog.c(r1, r2)     // Catch:{ RpcException -> 0x0086, all -> 0x0081 }
            r1 = r4
        L_0x007a:
            if (r1 == 0) goto L_0x0080
            r8.startPipelineForLoginFail()
            return
        L_0x0080:
            return
        L_0x0081:
            r0 = move-exception
            r1 = r4
            goto L_0x00b3
        L_0x0084:
            r0 = move-exception
            goto L_0x00b3
        L_0x0086:
            r1 = move-exception
            com.ali.user.mobile.log.LogSb r2 = r8.logSb     // Catch:{ all -> 0x00b0 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b0 }
            java.lang.String r4 = "RpcException:"
            r3.<init>(r4)     // Catch:{ all -> 0x00b0 }
            java.lang.String r4 = r1.getMessage()     // Catch:{ all -> 0x00b0 }
            r3.append(r4)     // Catch:{ all -> 0x00b0 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00b0 }
            r2.a(r3)     // Catch:{ all -> 0x00b0 }
            java.lang.String r2 = "SecurityInitServiceImpl"
            com.ali.user.mobile.log.AliUserLog.a(r2, r1)     // Catch:{ all -> 0x00b0 }
            com.ali.user.mobile.log.LogSb r1 = r8.logSb     // Catch:{ all -> 0x00b0 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00b0 }
            com.ali.user.mobile.log.LoggerUtils.a(r1)     // Catch:{ all -> 0x00b0 }
            r8.startPipelineForLoginFail()
            return
        L_0x00b0:
            r1 = move-exception
            r0 = r1
            r1 = 1
        L_0x00b3:
            if (r1 == 0) goto L_0x00b8
            r8.startPipelineForLoginFail()
        L_0x00b8:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.accountbiz.extservice.impl.SecurityInitServiceImpl.startAutoLogin():void");
    }

    private void startPipelineForLoginFail() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                AliUserLog.c(SecurityInitServiceImpl.TAG, ">>>>>>>>>>>> startPipelineForLoginFail");
            }
        });
    }

    private void showLoginActivity(Bundle bundle) {
        if (bundle == null) {
            try {
                bundle = new Bundle();
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append(e.getMessage());
                AliUserLog.b((String) TAG, sb.toString());
                return;
            }
        }
        bundle.putString("LoginSource", "securityInit");
        this.mAuthService.showActivityLogin(bundle, null);
    }

    private void generateDid() {
        AliUserLog.c(TAG, "开始生成tid");
        this.logSb.a("generatetid...");
        this.mDeviceService.queryCertification();
    }

    public void updateWalletLoginAuth(final List<String> list) {
        Executors.newCachedThreadPool().execute(new Runnable() {
            public void run() {
                if (SecurityInitServiceImpl.this.mDeviceService != null) {
                    String walletTid = SecurityInitServiceImpl.this.mDeviceService.queryDeviceInfo().getWalletTid();
                    AliUserLog.c(SecurityInitServiceImpl.TAG, "walletTid=".concat(String.valueOf(walletTid)));
                    MspDeviceInfoBean queryCertification = SecurityInitServiceImpl.this.mDeviceService.queryCertification();
                    if (walletTid != null && !StringUtils.a(walletTid.trim()) && !walletTid.equals(queryCertification.getTid())) {
                        SecurityInitServiceImpl.this.doUpdateWalletLoginAuth(list, queryCertification);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void doUpdateWalletLoginAuth(List<String> list, MspDeviceInfoBean mspDeviceInfoBean) {
        try {
            List<UserInfo> autoLoginAlipayUser = AntExtServiceManager.getAccountService(this.mContext).getAutoLoginAlipayUser();
            if (autoLoginAlipayUser != null && !autoLoginAlipayUser.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                for (UserInfo next : autoLoginAlipayUser) {
                    if (next.isAutoLogin()) {
                        arrayList.add(next.getUserId());
                    }
                }
            }
            if (list != null) {
                if (!list.isEmpty()) {
                    handleUpdateLoginResult(new AccountManagerFacadeBiz(null, null).a(mspDeviceInfoBean, list));
                }
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("更新免登关系异常:");
            sb.append(e.getMessage());
            AliUserLog.c(TAG, sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void handleUpdateLoginResult(MobileSecurityResult mobileSecurityResult) {
        Object[] objArr = new Object[1];
        objArr[0] = (mobileSecurityResult == null || !mobileSecurityResult.isSuccess()) ? "失败" : "成功";
        AliUserLog.c(TAG, String.format("更新客户端免登关系%s", objArr));
        if (mobileSecurityResult == null || !mobileSecurityResult.isSuccess()) {
            this.logUpdate.a("handleUpdateLoginResult fail");
            LoggerUtils.a(this.logUpdate.toString());
            return;
        }
        this.logUpdate.a("handleUpdateLoginResult begin");
        MspDeviceInfoBean tidFromMsp = getTidFromMsp();
        if (tidFromMsp == null || StringUtils.a(tidFromMsp.getTid())) {
            if (tidFromMsp != null) {
                LogSb logSb2 = this.logUpdate;
                StringBuilder sb = new StringBuilder("tempInfoBean.getTid = ");
                sb.append(tidFromMsp.getTid());
                logSb2.a(sb.toString());
            } else {
                this.logUpdate.a("tempInfoBean = null");
            }
            LoggerUtils.a(this.logUpdate.toString());
            return;
        }
        AliUserLog.c(TAG, "handleUpdateLoginResult-不再保存walterid ");
    }

    private void putLoginKey(String str, Object obj) {
        if (this.mMemCacheService != null) {
            this.mMemCacheService.put("securityapp", "security", str, obj);
        }
    }
}
