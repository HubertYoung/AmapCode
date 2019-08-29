package com.ali.user.mobile.accountbiz.extservice.impl;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.account.bean.UserLoginResultBiz;
import com.ali.user.mobile.accountbiz.extservice.AUMemCacheService;
import com.ali.user.mobile.accountbiz.extservice.AccountService;
import com.ali.user.mobile.accountbiz.extservice.AuthService;
import com.ali.user.mobile.accountbiz.extservice.LoginContext;
import com.ali.user.mobile.accountbiz.extservice.UserInfoService;
import com.ali.user.mobile.accountbiz.extservice.base.BaseExtService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.db.UserInfoDaoHelper;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.util.LoginUtil;
import com.ali.user.mobile.util.ReflectUtils;
import com.alipay.android.phone.inside.cashier.PhoneCashierPlugin;
import com.alipay.android.phone.inside.common.info.AppInfo;
import com.alipay.android.phone.inside.common.util.ThreadUtil;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.common.logging.api.LogContext;
import java.util.HashMap;
import java.util.Map.Entry;

public class AuthServiceImpl extends BaseExtService implements AuthService {
    private static final String AUTO_LOGIN = "autoLoginRpc";
    private static final String LOGIN_SOURCE = "loginSource";
    private static final String TAG = "AuthServiceImpl";
    private static final String THREAD_CAN_NOT_CANCEL = "thread_can_not_cancel";
    private static final String THREAD_OK = "thread_ok";
    private static AuthService mAuthService;
    boolean loginSuccess = false;
    private Object mLockGestureApp = new Object();
    private Object mLockLoginApp = new Object();
    private AUMemCacheService mMemService = AntExtServiceManager.getMemCacheService();
    private boolean taoBaoSsoFlag = false;
    private final HashMap<Long, String> threadMap = new HashMap<>();

    private AuthServiceImpl(Context context) {
        super(context);
    }

    public static AuthService getInstance(Context context) {
        if (mAuthService == null) {
            synchronized (AuthServiceImpl.class) {
                try {
                    if (mAuthService == null) {
                        mAuthService = new AuthServiceImpl(context);
                    }
                }
            }
        }
        return mAuthService;
    }

    public Bundle autoAuth() {
        _log("-----autoAuth");
        sLogLoginkey("autoAuth");
        if (ThreadUtil.a()) {
            throw new IllegalThreadStateException("can't in main thread call Auth Service .");
        }
        UserInfo lastLoginedUserInfo = getLastLoginedUserInfo();
        _log(String.format("客户端免登请求线程(启动免登): %s", new Object[]{Long.valueOf(Thread.currentThread().getId())}));
        synchronized (this.threadMap) {
            _log(String.format("autoAuth 添加等待线程 (启动免登)key=%s ", new Object[]{Long.valueOf(Thread.currentThread().getId())}));
            this.threadMap.put(Long.valueOf(Thread.currentThread().getId()), "wait");
        }
        LoginContext loginContext = new LoginContext();
        Bundle bundle = new Bundle();
        bundle.putString("LoginSource", "autoAuth");
        loginContext.setParams(bundle);
        loginContext.setUserInfo(lastLoginedUserInfo);
        try {
            boolean autoLogin = autoLogin(loginContext);
            synchronized (this.threadMap) {
                _log(String.format("autoAuth 移除等待线程(启动免登) key=%s ", new Object[]{Long.valueOf(Thread.currentThread().getId())}));
                this.threadMap.remove(Long.valueOf(Thread.currentThread().getId()));
            }
            Bundle params = loginContext.getParams();
            params.putBoolean("auto_login_result_logined", autoLogin);
            return params;
        } catch (RuntimeException e) {
            StringBuilder sb = new StringBuilder("fail-");
            sb.append(e.getMessage());
            putLoginKey(AUTO_LOGIN, sb.toString());
            throw e;
        } catch (Throwable th) {
            synchronized (this.threadMap) {
                _log(String.format("autoAuth 移除等待线程(启动免登) key=%s ", new Object[]{Long.valueOf(Thread.currentThread().getId())}));
                this.threadMap.remove(Long.valueOf(Thread.currentThread().getId()));
                throw th;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006b, code lost:
        return r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean autoLogin(com.ali.user.mobile.accountbiz.extservice.LoginContext r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            java.lang.String r0 = "-----autoLogin 是否跳过检查当前登录态标记  isSkipCheckIsLogin:%s, isLogin:%s"
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x006c }
            boolean r2 = r6.isSkipCheckIsLogin()     // Catch:{ all -> 0x006c }
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ all -> 0x006c }
            r3 = 0
            r1[r3] = r2     // Catch:{ all -> 0x006c }
            boolean r2 = r5.isLogin()     // Catch:{ all -> 0x006c }
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ all -> 0x006c }
            r4 = 1
            r1[r4] = r2     // Catch:{ all -> 0x006c }
            java.lang.String r0 = java.lang.String.format(r0, r1)     // Catch:{ all -> 0x006c }
            r5._log(r0)     // Catch:{ all -> 0x006c }
            java.util.HashMap<java.lang.Long, java.lang.String> r0 = r5.threadMap     // Catch:{ all -> 0x006c }
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x006c }
            long r1 = r1.getId()     // Catch:{ all -> 0x006c }
            java.lang.Long r1 = java.lang.Long.valueOf(r1)     // Catch:{ all -> 0x006c }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x006c }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x006c }
            java.lang.String r1 = "当前线程被标记状态(启动免登): %s"
            java.lang.Object[] r2 = new java.lang.Object[r4]     // Catch:{ all -> 0x006c }
            r2[r3] = r0     // Catch:{ all -> 0x006c }
            java.lang.String r1 = java.lang.String.format(r1, r2)     // Catch:{ all -> 0x006c }
            r5._log(r1)     // Catch:{ all -> 0x006c }
            java.lang.String r1 = "canceled"
            boolean r1 = r1.equals(r0)     // Catch:{ all -> 0x006c }
            if (r1 == 0) goto L_0x004f
            monitor-exit(r5)
            return r3
        L_0x004f:
            java.lang.String r1 = "thread_ok"
            boolean r0 = r1.equals(r0)     // Catch:{ all -> 0x006c }
            if (r0 == 0) goto L_0x005a
            monitor-exit(r5)
            return r4
        L_0x005a:
            boolean r6 = r5.loginForResult(r6)     // Catch:{ all -> 0x006c }
            if (r6 == 0) goto L_0x0064
            r5.setWaitingThreadOk()     // Catch:{ all -> 0x006c }
            goto L_0x006a
        L_0x0064:
            r5.setWaitingThreadCanceled()     // Catch:{ all -> 0x006c }
            r5.clearAuthLoginFlag()     // Catch:{ all -> 0x006c }
        L_0x006a:
            monitor-exit(r5)
            return r6
        L_0x006c:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.accountbiz.extservice.impl.AuthServiceImpl.autoLogin(com.ali.user.mobile.accountbiz.extservice.LoginContext):boolean");
    }

    public boolean auth() {
        return auth(new Bundle());
    }

    public boolean auth(Bundle bundle) {
        _log(String.format("-----auth, params: %s", new Object[]{bundle}));
        sLogLoginkey("auth");
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (!bundle.containsKey("LoginSource")) {
            bundle.putString("LoginSource", "auth");
        }
        UserInfo lastLoginedUserInfo = getLastLoginedUserInfo();
        LoginContext loginContext = new LoginContext();
        loginContext.setParams(bundle);
        loginContext.setUserInfo(lastLoginedUserInfo);
        if (bundle != null) {
            boolean z = bundle.getBoolean("resetCookie");
            _log(String.format("auth - resetCookie: %s", new Object[]{Boolean.valueOf(z)}));
            loginContext.setResetCookie(z);
            boolean z2 = bundle.getBoolean("directAuth");
            _log(String.format("auth - directAuth: %s", new Object[]{Boolean.valueOf(z2)}));
            loginContext.setDirectAuth(z2);
        }
        return innerAuth(loginContext);
    }

    public boolean showActivityLogin(Bundle bundle, UserInfo userInfo) {
        _log(String.format("-----showActivityLogin, params: %s, userInfo: %s", new Object[]{bundle, userInfo}));
        sLogLoginkey("showActivityLogin");
        LoginUtil.a(TAG, "showActivityLogin");
        LoginContext loginContext = new LoginContext();
        loginContext.setParams(bundle);
        loginContext.setUserInfo(userInfo);
        loginContext.setSkipAutoLogin(true);
        loginContext.setSkipCheckIsLogin(true);
        loginContext.setSkipSelectAccountApp(true);
        putLoginKey("isSkipAutoLogin", "true");
        return innerAuth(loginContext);
    }

    public boolean rpcAuth(boolean z) {
        _log("-----rpcAuth Inside");
        sLogLoginkey("rpcAuth Inside");
        traceRpcAuthStack();
        LoginContext loginContext = new LoginContext();
        loginContext.setUserInfo(getLoginUserInfo());
        loginContext.setSkipGestureApp(true);
        loginContext.setSkipCheckIsLogin(true);
        loginContext.setForceNotShowLogin(z);
        Bundle bundle = new Bundle();
        bundle.putString("LoginSource", "rpcAuth");
        loginContext.setParams(bundle);
        return innerAuth(loginContext);
    }

    public boolean rpcAuth() {
        _log("-----rpcAuth");
        sLogLoginkey("rpcAuth");
        traceRpcAuthStack();
        LoginContext loginContext = new LoginContext();
        loginContext.setUserInfo(getLoginUserInfo());
        loginContext.setSkipGestureApp(true);
        loginContext.setSkipCheckIsLogin(true);
        Bundle bundle = new Bundle();
        bundle.putString("LoginSource", "rpcAuth");
        loginContext.setParams(bundle);
        return innerAuth(loginContext);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0060 A[Catch:{ all -> 0x002d }] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0075 A[Catch:{ all -> 0x002d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean innerAuth(com.ali.user.mobile.accountbiz.extservice.LoginContext r10) {
        /*
            r9 = this;
            java.lang.String r0 = "innerAuth 线程 key=%s"
            r1 = 1
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Thread r3 = java.lang.Thread.currentThread()
            long r3 = r3.getId()
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r4 = 0
            r2[r4] = r3
            java.lang.String r0 = java.lang.String.format(r0, r2)
            r9._log(r0)
            java.util.HashMap<java.lang.Long, java.lang.String> r0 = r9.threadMap
            monitor-enter(r0)
            if (r10 == 0) goto L_0x0030
            android.os.Bundle r2 = r10.getParams()     // Catch:{ all -> 0x002d }
            if (r2 == 0) goto L_0x0030
            java.lang.String r3 = "key_device_lock_thread_cannot_cancel"
            boolean r2 = r2.getBoolean(r3, r4)     // Catch:{ all -> 0x002d }
            goto L_0x0031
        L_0x002d:
            r10 = move-exception
            goto L_0x00c0
        L_0x0030:
            r2 = 0
        L_0x0031:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x002d }
            r3.<init>()     // Catch:{ all -> 0x002d }
            java.lang.String r5 = "innerAuth 添加等待线程 key=%s"
            java.lang.Object[] r6 = new java.lang.Object[r1]     // Catch:{ all -> 0x002d }
            java.lang.Thread r7 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x002d }
            long r7 = r7.getId()     // Catch:{ all -> 0x002d }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x002d }
            r6[r4] = r7     // Catch:{ all -> 0x002d }
            java.lang.String r5 = java.lang.String.format(r5, r6)     // Catch:{ all -> 0x002d }
            r3.append(r5)     // Catch:{ all -> 0x002d }
            java.lang.String r5 = " 线程可否被取消threadCanCancel="
            r3.append(r5)     // Catch:{ all -> 0x002d }
            r3.append(r2)     // Catch:{ all -> 0x002d }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x002d }
            r9._log(r3)     // Catch:{ all -> 0x002d }
            if (r2 == 0) goto L_0x0075
            java.util.HashMap<java.lang.Long, java.lang.String> r2 = r9.threadMap     // Catch:{ all -> 0x002d }
            java.lang.Thread r3 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x002d }
            long r5 = r3.getId()     // Catch:{ all -> 0x002d }
            java.lang.Long r3 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x002d }
            java.lang.String r5 = "thread_can_not_cancel"
            r2.put(r3, r5)     // Catch:{ all -> 0x002d }
            goto L_0x0089
        L_0x0075:
            java.util.HashMap<java.lang.Long, java.lang.String> r2 = r9.threadMap     // Catch:{ all -> 0x002d }
            java.lang.Thread r3 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x002d }
            long r5 = r3.getId()     // Catch:{ all -> 0x002d }
            java.lang.Long r3 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x002d }
            java.lang.String r5 = "wait"
            r2.put(r3, r5)     // Catch:{ all -> 0x002d }
        L_0x0089:
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            boolean r10 = r9.syncAuth(r10)
            java.util.HashMap<java.lang.Long, java.lang.String> r2 = r9.threadMap
            monitor-enter(r2)
            java.lang.String r0 = "innerAuth 移除等待线程 key=%s"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00bd }
            java.lang.Thread r3 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x00bd }
            long r5 = r3.getId()     // Catch:{ all -> 0x00bd }
            java.lang.Long r3 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x00bd }
            r1[r4] = r3     // Catch:{ all -> 0x00bd }
            java.lang.String r0 = java.lang.String.format(r0, r1)     // Catch:{ all -> 0x00bd }
            r9._log(r0)     // Catch:{ all -> 0x00bd }
            java.util.HashMap<java.lang.Long, java.lang.String> r0 = r9.threadMap     // Catch:{ all -> 0x00bd }
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x00bd }
            long r3 = r1.getId()     // Catch:{ all -> 0x00bd }
            java.lang.Long r1 = java.lang.Long.valueOf(r3)     // Catch:{ all -> 0x00bd }
            r0.remove(r1)     // Catch:{ all -> 0x00bd }
            monitor-exit(r2)     // Catch:{ all -> 0x00bd }
            return r10
        L_0x00bd:
            r10 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00bd }
            throw r10
        L_0x00c0:
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.accountbiz.extservice.impl.AuthServiceImpl.innerAuth(com.ali.user.mobile.accountbiz.extservice.LoginContext):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0088, code lost:
        return r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean syncAuth(com.ali.user.mobile.accountbiz.extservice.LoginContext r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            java.lang.String r0 = "-----syncAuth 是否跳过检查当前登录态标记  isSkipCheckIsLogin:%s, isLogin:%s"
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0089 }
            boolean r2 = r6.isSkipCheckIsLogin()     // Catch:{ all -> 0x0089 }
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ all -> 0x0089 }
            r3 = 0
            r1[r3] = r2     // Catch:{ all -> 0x0089 }
            boolean r2 = r5.isLogin()     // Catch:{ all -> 0x0089 }
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ all -> 0x0089 }
            r4 = 1
            r1[r4] = r2     // Catch:{ all -> 0x0089 }
            java.lang.String r0 = java.lang.String.format(r0, r1)     // Catch:{ all -> 0x0089 }
            r5._log(r0)     // Catch:{ all -> 0x0089 }
            boolean r0 = r6.isDirectAuth()     // Catch:{ all -> 0x0089 }
            if (r0 == 0) goto L_0x002f
            java.lang.String r0 = "DirectAuth请求，不判断登录，直接尝试免登"
            r5._log(r0)     // Catch:{ all -> 0x0089 }
            goto L_0x0043
        L_0x002f:
            boolean r0 = r6.isSkipCheckIsLogin()     // Catch:{ all -> 0x0089 }
            if (r0 != 0) goto L_0x0043
            boolean r0 = r5.isLogin()     // Catch:{ all -> 0x0089 }
            if (r0 == 0) goto L_0x0043
            java.lang.String r6 = "需要检查登录态标记并且isLogin()=true, rpc请求已经登录 "
            r5._log(r6)     // Catch:{ all -> 0x0089 }
            monitor-exit(r5)
            return r4
        L_0x0043:
            java.util.HashMap<java.lang.Long, java.lang.String> r0 = r5.threadMap     // Catch:{ all -> 0x0089 }
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0089 }
            long r1 = r1.getId()     // Catch:{ all -> 0x0089 }
            java.lang.Long r1 = java.lang.Long.valueOf(r1)     // Catch:{ all -> 0x0089 }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x0089 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0089 }
            java.lang.String r1 = "当前线程被标记状态: %s"
            java.lang.Object[] r2 = new java.lang.Object[r4]     // Catch:{ all -> 0x0089 }
            r2[r3] = r0     // Catch:{ all -> 0x0089 }
            java.lang.String r1 = java.lang.String.format(r1, r2)     // Catch:{ all -> 0x0089 }
            r5._log(r1)     // Catch:{ all -> 0x0089 }
            java.lang.String r1 = "canceled"
            boolean r1 = r1.equals(r0)     // Catch:{ all -> 0x0089 }
            if (r1 == 0) goto L_0x006f
            monitor-exit(r5)
            return r3
        L_0x006f:
            java.lang.String r1 = "thread_ok"
            boolean r0 = r1.equals(r0)     // Catch:{ all -> 0x0089 }
            if (r0 == 0) goto L_0x007a
            monitor-exit(r5)
            return r4
        L_0x007a:
            boolean r6 = r5.innerFullLogin(r6)     // Catch:{ all -> 0x0089 }
            if (r6 == 0) goto L_0x0084
            r5.setWaitingThreadOk()     // Catch:{ all -> 0x0089 }
            goto L_0x0087
        L_0x0084:
            r5.setWaitingThreadCanceled()     // Catch:{ all -> 0x0089 }
        L_0x0087:
            monitor-exit(r5)
            return r6
        L_0x0089:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.accountbiz.extservice.impl.AuthServiceImpl.syncAuth(com.ali.user.mobile.accountbiz.extservice.LoginContext):boolean");
    }

    private void setWaitingThreadOk() {
        synchronized (this.threadMap) {
            for (Entry<Long, String> key : this.threadMap.entrySet()) {
                Long l = (Long) key.getKey();
                _log(String.format("登录完成且登录成功，设置等待线程 为ok状态 key=%s", new Object[]{l}));
                this.threadMap.put(l, THREAD_OK);
            }
        }
    }

    private void setWaitingThreadCanceled() {
        synchronized (this.threadMap) {
            for (Entry next : this.threadMap.entrySet()) {
                Long l = (Long) next.getKey();
                if (THREAD_CAN_NOT_CANCEL.equalsIgnoreCase((String) next.getValue())) {
                    _log(String.format("设备锁开启登录页线程不能被cancel，否则开启不了登录页", new Object[]{l}));
                } else {
                    _log(String.format("登录完成且登录失败，设置等待线程 为取消状态 key=%s", new Object[]{l}));
                    this.threadMap.put(l, "canceled");
                }
            }
        }
    }

    private boolean innerFullLogin(LoginContext loginContext) {
        this.loginSuccess = false;
        if (ThreadUtil.a()) {
            throw new IllegalThreadStateException("can't in main thread call Auth Service .");
        }
        _log(String.format("开始免登, loginContext=%s", new Object[]{loginContext}));
        boolean innerLogin = innerLogin(loginContext);
        _log(String.format("免登结束, showAppflag=%s", new Object[]{Boolean.valueOf(innerLogin)}));
        if (innerLogin) {
            synchronized (this.mLockLoginApp) {
                try {
                    _log("开始登录界面等待锁");
                    this.mLockLoginApp.wait();
                    _log("登录界面等待锁释放");
                } catch (InterruptedException e) {
                    AliUserLog.a((String) TAG, (Throwable) e);
                }
            }
        }
        _log(String.format("登录结果: %s, loginContext=%s", new Object[]{Boolean.valueOf(this.loginSuccess), loginContext}));
        _log("检查是否需要设置或者验证手势");
        if (this.loginSuccess) {
            _log("登录成功，重置mShowLoginCount");
        }
        return this.loginSuccess;
    }

    private boolean isBlank(String str) {
        if (str != null) {
            int length = str.length();
            if (length != 0) {
                for (int i = 0; i < length; i++) {
                    if (!Character.isWhitespace(str.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    private boolean innerLogin(LoginContext loginContext) {
        UserInfo userInfo = loginContext.getUserInfo();
        boolean isSkipAutoLogin = loginContext.isSkipAutoLogin();
        boolean isCanAutoLoginUser = isCanAutoLoginUser(userInfo);
        _log(String.format("innerLogin, isSkipAutoLogin: %s, isCanAutoLoginUser: %s, userInfo: %s", new Object[]{Boolean.valueOf(isSkipAutoLogin), Boolean.valueOf(isCanAutoLoginUser), LoginUtil.a(userInfo)}));
        if (!isSkipAutoLogin && isCanAutoLoginUser) {
            boolean autoLoginStart = autoLoginStart(loginContext);
            _log(String.format("autoLoginStart结果: %s", new Object[]{Boolean.valueOf(autoLoginStart)}));
            if (autoLoginStart) {
                this.loginSuccess = true;
                return false;
            }
        }
        if (loginContext.isForceNotShowLoginApp()) {
            return false;
        }
        _log("当前无法免登，启动登录界面");
        if (loginContext.getUserInfo() != null) {
            loginContext.getParams().putString("logonId", loginContext.getUserInfo().getLogonId());
        }
        return showLoginApp(loginContext.getParams());
    }

    public void notifyUnlockLoginApp(boolean z, boolean z2) {
        synchronized (this.mLockLoginApp) {
            try {
                _log(String.format("notifyUnlockLoginApp释放登录界面锁, loginSuccess: %s, autoLogin: %s", new Object[]{Boolean.valueOf(z), Boolean.valueOf(z2)}));
                this.loginSuccess = z;
                this.mLockLoginApp.notifyAll();
            } catch (Exception e) {
                AliUserLog.a((String) TAG, (Throwable) e);
            }
        }
    }

    public void notifyUnlockGestureApp() {
        synchronized (this.mLockGestureApp) {
            try {
                _log("释放手势界面锁");
                this.mLockGestureApp.notifyAll();
            } catch (Exception e) {
                AliUserLog.a((String) TAG, (Throwable) e);
            }
        }
    }

    private boolean autoLoginStart(LoginContext loginContext) {
        _log("可以免登，调用免登服务");
        try {
            return loginForResult(loginContext);
        } catch (RuntimeException e) {
            AliUserLog.a((String) TAG, (Throwable) e);
            StringBuilder sb = new StringBuilder("fail-");
            sb.append(e.getMessage());
            putLoginKey(AUTO_LOGIN, sb.toString());
            loginContext.setIsLoginSucess(Boolean.FALSE);
            if (!(e instanceof RpcException)) {
                return false;
            }
            _log(String.format("登陆RpcException(%s)，取消等待线程并抛出异常", new Object[]{Integer.valueOf(((RpcException) e).getCode())}));
            if (isCanAutoLoginUser(getLastLoginedUserInfo()) && !getAccountService().getCurrentLoginState()) {
                getAccountService().setCurrentLoginState("true");
            }
            setWaitingThreadCanceled();
            throw e;
        }
    }

    private boolean loginForResult(LoginContext loginContext) {
        return result(loginContext, AntExtServiceManager.getLoginService(this.mContext).login(loginContext.getUserInfo().getLogonId(), null, null, null, null, loginContext.isResetCookie()));
    }

    private boolean result(LoginContext loginContext, UserLoginResultBiz userLoginResultBiz) {
        _log("开始处理免登结果");
        if (userLoginResultBiz != null) {
            _log(String.format("免登服务结束, resultStatus: %s, memo: %s", new Object[]{Integer.valueOf(userLoginResultBiz.getResultStatus()), userLoginResultBiz.getMemo()}));
            if (1000 == userLoginResultBiz.getResultStatus()) {
                UserInfoService userInfoService = AntExtServiceManager.getUserInfoService(this.mContext);
                if (userLoginResultBiz.getUserId() != null) {
                    _log("查询用户信息并放入缓存开始");
                    userInfoService.queryUserInfo(userLoginResultBiz.getUserId()).getUserInfo();
                    _log("查询用户信息并放入缓存完成");
                    loginContext.setIsLoginSucess(Boolean.TRUE);
                    return true;
                }
                _log("登录成功后，返回userId 为 null ");
                loginContext.setIsLoginSucess(Boolean.FALSE);
                putLoginKey(AUTO_LOGIN, "fail-登录成功后，返回userId 为 null");
                return false;
            }
            StringBuilder sb = new StringBuilder("fail-status:");
            sb.append(userLoginResultBiz.getResultStatus());
            putLoginKey(AUTO_LOGIN, sb.toString());
            clearAuthLoginFlag();
            if (userLoginResultBiz.getResultStatus() == 7006 || userLoginResultBiz.getResultStatus() == 7007) {
                _log("免登校验tid失败，重新生成tid");
                reGenerateTid();
            }
            UserInfo lastLoginedUserInfo = getLastLoginedUserInfo();
            if (lastLoginedUserInfo != null) {
                lastLoginedUserInfo.setAutoLogin(false);
                autoLoginFail(lastLoginedUserInfo.getUserId());
            }
            _log("免登失败，显示登陆界面");
            if (userLoginResultBiz.getResultStatus() == 2003) {
                _log("免登失败，强升");
                if (loginContext.getParams() == null) {
                    loginContext.setParams(new Bundle());
                }
            }
            loginContext.setIsLoginSucess(Boolean.FALSE);
            return false;
        }
        _log("免登服务开始服务器响应异常userLoginResultBiz=null");
        loginContext.setIsLoginSucess(Boolean.FALSE);
        putLoginKey(AUTO_LOGIN, "fail-userLoginResultBiz is null");
        return false;
    }

    private void reGenerateTid() {
        try {
            UserInfoDaoHelper.a(this.mContext).b();
            PluginManager.b(PhoneCashierPlugin.KEY_SERVICE_RESET_TID).start(null);
        } catch (Throwable th) {
            AliUserLog.b((String) TAG, th);
        }
    }

    private void autoLoginFail(String str) {
        try {
            _log(String.format("清除本地所以用戶免登狀態 userId=%s", new Object[]{str}));
            UserInfoDaoHelper.a(this.mContext).c(str);
        } catch (Exception e) {
            AliUserLog.a((String) TAG, (Throwable) e);
        }
        try {
            _log("修改当前用户登录状态为未登录");
            getAccountService().setCurrentLoginState("false");
        } catch (Exception e2) {
            AliUserLog.a((String) TAG, (Throwable) e2);
        }
    }

    private boolean showLoginApp(Bundle bundle) {
        try {
            _log(String.format("开始唤起登陆界面, params: %s", new Object[]{bundle}));
            Object a = ReflectUtils.a("com.ali.user.mobile.login.app.LoginAppService");
            AliUserLog.c(TAG, "reflect get LoginAppService success");
            ReflectUtils.a(a, "startLoginPage", new Class[]{Context.class, Bundle.class}, new Object[]{this.mContext, bundle});
            if (bundle != null && !bundle.getBoolean("come_back")) {
                _log("调起登录页面，并且登录页面不可退出，清除本地登录态");
                clearAuthLoginFlag();
            }
            return true;
        } catch (Throwable th) {
            AliUserLog.a((String) TAG, th);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x004b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ali.user.mobile.account.bean.UserInfo queryLatelyLoginUser() {
        /*
            r6 = this;
            java.lang.String r0 = "查询最近一次登录用户信息"
            r6._log(r0)
            r0 = 0
            com.ali.user.mobile.accountbiz.extservice.AccountService r1 = r6.getAccountService()     // Catch:{ Exception -> 0x0041 }
            java.lang.String r1 = r1.getCurrentLoginLogonId()     // Catch:{ Exception -> 0x0041 }
            java.lang.String r2 = "查询出当前登录用户 currentUserLoginId=%s"
            java.lang.String r3 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x003e }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ Exception -> 0x003e }
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x003e }
            java.lang.String r2 = java.lang.String.format(r2, r4)     // Catch:{ Exception -> 0x003e }
            r6._log(r2)     // Catch:{ Exception -> 0x003e }
            com.ali.user.mobile.accountbiz.extservice.AccountService r2 = r6.getAccountService()     // Catch:{ Exception -> 0x003e }
            java.lang.String r2 = r2.getCurrentLoginUserId()     // Catch:{ Exception -> 0x003e }
            java.lang.String r4 = "查询出当前登录用户 currentUserId=%s"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x003c }
            r5[r3] = r2     // Catch:{ Exception -> 0x003c }
            java.lang.String r3 = java.lang.String.format(r4, r5)     // Catch:{ Exception -> 0x003c }
            r6._log(r3)     // Catch:{ Exception -> 0x003c }
            goto L_0x0049
        L_0x003c:
            r3 = move-exception
            goto L_0x0044
        L_0x003e:
            r3 = move-exception
            r2 = r0
            goto L_0x0044
        L_0x0041:
            r3 = move-exception
            r1 = r0
            r2 = r1
        L_0x0044:
            java.lang.String r4 = "AuthServiceImpl"
            com.ali.user.mobile.log.AliUserLog.a(r4, r3)
        L_0x0049:
            if (r1 == 0) goto L_0x0067
            java.lang.String r3 = ""
            boolean r1 = r3.equals(r1)     // Catch:{ Exception -> 0x0065 }
            if (r1 != 0) goto L_0x0067
            java.lang.String r1 = "当前用户已经登录状态 ，查询当前用户详细信息"
            r6._log(r1)     // Catch:{ Exception -> 0x0065 }
            android.content.Context r1 = r6.mContext     // Catch:{ Exception -> 0x0065 }
            com.ali.user.mobile.accountbiz.extservice.AccountService r1 = com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager.getAccountService(r1)     // Catch:{ Exception -> 0x0065 }
            com.ali.user.mobile.account.bean.UserInfo r1 = r1.queryAccountDetailInfoByUserId(r2)     // Catch:{ Exception -> 0x0065 }
            r0 = r1
            goto L_0x0073
        L_0x0065:
            r1 = move-exception
            goto L_0x006e
        L_0x0067:
            java.lang.String r1 = "当前无登陆用户，返回空数据"
            r6._log(r1)     // Catch:{ Exception -> 0x0065 }
            goto L_0x0073
        L_0x006e:
            java.lang.String r2 = "AuthServiceImpl"
            com.ali.user.mobile.log.AliUserLog.a(r2, r1)
        L_0x0073:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.accountbiz.extservice.impl.AuthServiceImpl.queryLatelyLoginUser():com.ali.user.mobile.account.bean.UserInfo");
    }

    public boolean isLogin() {
        return getAccountService().getCurrentLoginState();
    }

    public void clearAuthLoginFlag() {
        _log("清除本地登录态setCurrentLoginState = false");
        getAccountService().setCurrentLoginState("false");
    }

    public void clearLoginUserInfo() {
        _log("clearLoginUserInfo = null");
    }

    private boolean isCanAutoLoginUser(UserInfo userInfo) {
        return userInfo != null && userInfo.isAutoLogin();
    }

    private AccountService getAccountService() {
        return AntExtServiceManager.getAccountService(this.mContext);
    }

    public UserInfo getLastLoginedUserInfo() {
        String currentLoginUserId = getAccountService().getCurrentLoginUserId();
        if (!isBlank(currentLoginUserId)) {
            return getAccountService().queryAccountDetailInfoByUserId(currentLoginUserId);
        }
        return null;
    }

    public UserInfo getUserInfo() {
        String currentLoginUserId = getAccountService().getCurrentLoginUserId();
        Object[] objArr = new Object[1];
        objArr[0] = currentLoginUserId == null ? "null" : "not null";
        _log(String.format("getUserInfo - currentUserId:%s", objArr));
        UserInfo queryAccountDetailInfoByUserId = !isBlank(currentLoginUserId) ? getAccountService().queryAccountDetailInfoByUserId(currentLoginUserId) : null;
        if (queryAccountDetailInfoByUserId == null) {
            _log("getUserInfo is null");
        }
        return queryAccountDetailInfoByUserId;
    }

    public UserInfo getLoginUserInfo() {
        String currentLoginUserId = getAccountService().getCurrentLoginUserId();
        Object[] objArr = new Object[1];
        objArr[0] = currentLoginUserId == null ? "null" : "not null";
        _log(String.format("getLoginUserInfo - currentUserId: %s", objArr));
        if (currentLoginUserId != null) {
            UserInfo queryAccountDetailInfoByUserId = getAccountService().queryAccountDetailInfoByUserId(currentLoginUserId);
            _log(LoginUtil.a(queryAccountDetailInfoByUserId));
            if (queryAccountDetailInfoByUserId != null && queryAccountDetailInfoByUserId.isAutoLogin()) {
                _log("userInfo != null, can autoLogin, return userInfo");
                return queryAccountDetailInfoByUserId;
            }
        }
        return null;
    }

    public UserInfo gestureGetUserInfo() {
        UserInfo userInfoBySql = getAccountService().getUserInfoBySql(null, null);
        if (userInfoBySql == null) {
            return null;
        }
        boolean isAutoLogin = userInfoBySql.isAutoLogin();
        boolean currentLoginState = getAccountService().getCurrentLoginState();
        if (isAutoLogin || currentLoginState) {
            return userInfoBySql;
        }
        return null;
    }

    private void traceRpcAuthStack() {
        if ("test".equalsIgnoreCase(AppInfo.a().c()) || "dev".equalsIgnoreCase(AppInfo.a().c()) || LogContext.RELEASETYPE_RC.equalsIgnoreCase(AppInfo.a().c())) {
            try {
                StringBuilder sb = new StringBuilder();
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                if (stackTrace != null) {
                    for (StackTraceElement stackTraceElement : stackTrace) {
                        sb.append(stackTraceElement.toString());
                        sb.append("###");
                    }
                    sb.append("###");
                }
                if (!TextUtils.isEmpty(sb.toString())) {
                    StringBuilder sb2 = new StringBuilder("rpcAuthTrace = ");
                    sb2.append(sb.toString());
                    LogAgent.f("UC-ZHAQ-56", "loginTrace", sb2.toString(), "", "");
                }
            } catch (Exception e) {
                LoggerFactory.f().c((String) "怪物", (Throwable) e);
            }
        }
    }

    public boolean getTaoBaoSsoFlag() {
        return this.taoBaoSsoFlag;
    }

    public void setTaoBaoSsoFlag(boolean z) {
        this.taoBaoSsoFlag = z;
    }

    private void sLogLoginkey(String str) {
        putLoginKey(LOGIN_SOURCE, str);
        logLoginKey();
    }

    public void logLoginKey() {
        if (TextUtils.isEmpty(getAccountService().getCurrentLoginUserId())) {
            putLoginKey("CurrentLoginUserId", "false");
        } else {
            putLoginKey("CurrentLoginUserId", "true");
        }
        UserInfo lastLoginedUserInfo = getLastLoginedUserInfo();
        if (lastLoginedUserInfo != null) {
            putLoginKey("userInfo", "true");
            putLoginKey("isAutoLogin", Boolean.valueOf(lastLoginedUserInfo.isAutoLogin()));
        } else {
            putLoginKey("userInfo", "false");
            putLoginKey("isAutoLogin", "false");
        }
        putLoginKey("isSkipAutoLogin", "false");
        putLoginKey("isLogin", Boolean.valueOf(isLogin()));
    }

    private void putLoginKey(String str, Object obj) {
        if (this.mMemService != null) {
            this.mMemService.put("securityapp", "security", str, obj);
        }
    }

    private void _log(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("[Thread-id:");
        sb.append(Thread.currentThread().getId());
        sb.append(",name:");
        sb.append(Thread.currentThread().getName());
        sb.append("] ");
        sb.append(str);
        AliUserLog.c(TAG, sb.toString());
    }
}
