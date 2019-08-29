package com.taobao.tao.remotebusiness.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.alibaba.fastjson.JSON;
import com.alipay.sdk.cons.c;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.intf.Mtop;

public final class DefaultLoginImpl implements IRemoteLogin {
    private static final String MTOP_API_REFERENCE = "apiReferer";
    private static final String STATS_MODULE_MTOPRB = "mtoprb";
    private static final String STATS_MONITOR_POINT_SESSION_INVALID = "SessionInvalid";
    private static final String TAG = "mtopsdk.DefaultLoginImpl";
    public static volatile DefaultLoginImpl instance;
    private static volatile AtomicBoolean isRegistered = new AtomicBoolean(false);
    static Context mContext;
    private static ThreadLocal<SessionInvalidEvent> threadLocal = new ThreadLocal<>();
    private Method checkSessionValidMethod;
    private Method getNickMethod;
    private Method getSidMethod;
    private Method getUserIdMethod;
    private Method isLoginingMethod;
    private Class<?> loginBroadcastHelperCls = null;
    private Class<?> loginCls = null;
    private LoginContext loginContext = new LoginContext();
    private Method loginMethod;
    private Class<?> loginStatusCls = null;
    protected BroadcastReceiver receiver = null;
    private Method registerReceiverMethod;

    static class SessionInvalidEvent {
        private static final String HEADER_KEY = "S";
        public String S_STATUS;
        public String apiName;
        public boolean appBackGround;
        public String eventName;
        public String long_nick;
        public String msgCode;
        public String processName;
        public String v;

        public SessionInvalidEvent(MtopResponse mtopResponse, String str) {
            this.appBackGround = false;
            this.eventName = "SESSION_INVALID";
            this.long_nick = str;
            this.apiName = mtopResponse.getApi();
            this.v = mtopResponse.getV();
            this.msgCode = mtopResponse.getRetCode();
            this.S_STATUS = fcz.a(mtopResponse.getHeaderFields(), HEADER_KEY);
            this.processName = fdb.b(DefaultLoginImpl.mContext);
            this.appBackGround = fgy.b();
        }

        public SessionInvalidEvent(MtopRequest mtopRequest) {
            this.appBackGround = false;
            this.apiName = mtopRequest.getApiName();
            this.v = mtopRequest.getVersion();
            this.processName = fdb.b(DefaultLoginImpl.mContext);
            this.appBackGround = fgy.b();
        }

        public String toJSONString() {
            return JSON.toJSONString(this);
        }
    }

    public static DefaultLoginImpl getDefaultLoginImpl(@NonNull Context context) {
        if (instance == null) {
            synchronized (DefaultLoginImpl.class) {
                if (instance == null) {
                    if (context == null) {
                        try {
                            context = fdb.c();
                            if (context == null) {
                                TBSdkLog.d(TAG, "context can't be null.reflect context is still null.");
                                Mtop a = Mtop.a((String) "INNER", (Context) null);
                                if (a.c.e == null) {
                                    TBSdkLog.d(TAG, "context can't be null.wait INNER mtopInstance init.");
                                    a.b();
                                }
                                context = a.c.e;
                                if (context == null) {
                                    TBSdkLog.d(TAG, "context can't be null.wait INNER mtopInstance init finish,context is still null");
                                    DefaultLoginImpl defaultLoginImpl = instance;
                                    return defaultLoginImpl;
                                }
                                TBSdkLog.d(TAG, "context can't be null.wait INNER mtopInstance init finish.context=".concat(String.valueOf(context)));
                            }
                        } catch (Exception e) {
                            TBSdkLog.b((String) TAG, (String) "get DefaultLoginImpl instance error", (Throwable) e);
                            return instance;
                        }
                    }
                    mContext = context;
                    instance = new DefaultLoginImpl();
                }
            }
        }
        return instance;
    }

    private DefaultLoginImpl() throws ClassNotFoundException, NoSuchMethodException {
        try {
            this.loginCls = Class.forName("com.taobao.login4android.Login");
        } catch (ClassNotFoundException unused) {
            this.loginCls = Class.forName("com.taobao.login4android.api.Login");
        }
        this.loginMethod = this.loginCls.getDeclaredMethod("login", new Class[]{Boolean.TYPE, Bundle.class});
        this.checkSessionValidMethod = this.loginCls.getDeclaredMethod("checkSessionValid", new Class[0]);
        this.getSidMethod = this.loginCls.getDeclaredMethod("getSid", new Class[0]);
        this.getUserIdMethod = this.loginCls.getDeclaredMethod("getUserId", new Class[0]);
        this.getNickMethod = this.loginCls.getDeclaredMethod("getNick", new Class[0]);
        this.loginStatusCls = Class.forName("com.taobao.login4android.constants.LoginStatus");
        this.isLoginingMethod = this.loginStatusCls.getDeclaredMethod("isLogining", new Class[0]);
        this.loginBroadcastHelperCls = Class.forName("com.taobao.login4android.broadcast.LoginBroadcastHelper");
        this.registerReceiverMethod = this.loginBroadcastHelperCls.getMethod("registerLoginReceiver", new Class[]{Context.class, BroadcastReceiver.class});
        registerReceiver();
        TBSdkLog.b(TAG, "register login event receiver");
    }

    private void registerReceiver() {
        if (this.receiver != null) {
            return;
        }
        if (mContext == null) {
            TBSdkLog.d(TAG, "Context is null, register receiver fail.");
            return;
        }
        synchronized (DefaultLoginImpl.class) {
            if (this.receiver == null) {
                this.receiver = new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        if (intent != null) {
                            String action = intent.getAction();
                            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                                TBSdkLog.b(DefaultLoginImpl.TAG, "Login Broadcast Received. action=".concat(String.valueOf(action)));
                            }
                            if ("NOTIFY_LOGIN_SUCCESS".equals(action)) {
                                LoginHandler.instance().onLoginSuccess();
                            } else if ("NOTIFY_LOGIN_FAILED".equals(action)) {
                                LoginHandler.instance().onLoginFail();
                            } else {
                                if ("NOTIFY_LOGIN_CANCEL".equals(action)) {
                                    LoginHandler.instance().onLoginCancel();
                                }
                            }
                        }
                    }
                };
                invokeMethod(this.registerReceiverMethod, mContext, this.receiver);
            }
        }
    }

    public final void setSessionInvalid(Object obj) {
        if (obj instanceof MtopResponse) {
            threadLocal.set(new SessionInvalidEvent((MtopResponse) obj, (String) invokeMethod(this.getNickMethod, new Object[0])));
            return;
        }
        if (obj instanceof MtopRequest) {
            threadLocal.set(new SessionInvalidEvent((MtopRequest) obj));
        }
    }

    private <T> T invokeMethod(Method method, Object... objArr) {
        if (method != null) {
            try {
                return method.invoke(this.loginCls, objArr);
            } catch (Exception e) {
                TBSdkLog.b((String) TAG, (String) "invokeMethod error", (Throwable) e);
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0043, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0044, code lost:
        threadLocal.remove();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0049, code lost:
        throw r6;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0043 A[ExcHandler: all (r6v7 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:2:0x0012] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void login(com.taobao.tao.remotebusiness.login.onLoginListener r6, boolean r7) {
        /*
            r5 = this;
            java.lang.String r6 = "mtopsdk.DefaultLoginImpl"
            java.lang.String r0 = "call login"
            mtopsdk.common.util.TBSdkLog.b(r6, r0)
            java.lang.ThreadLocal<com.taobao.tao.remotebusiness.login.DefaultLoginImpl$SessionInvalidEvent> r6 = threadLocal
            java.lang.Object r6 = r6.get()
            com.taobao.tao.remotebusiness.login.DefaultLoginImpl$SessionInvalidEvent r6 = (com.taobao.tao.remotebusiness.login.DefaultLoginImpl.SessionInvalidEvent) r6
            r0 = 0
            if (r6 == 0) goto L_0x004f
            android.os.Bundle r1 = new android.os.Bundle     // Catch:{ Exception -> 0x004a, all -> 0x0043 }
            r1.<init>()     // Catch:{ Exception -> 0x004a, all -> 0x0043 }
            java.lang.String r0 = r6.toJSONString()     // Catch:{ Exception -> 0x0041, all -> 0x0043 }
            mtopsdk.common.util.TBSdkLog$LogEnable r2 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable     // Catch:{ Exception -> 0x0041, all -> 0x0043 }
            boolean r2 = mtopsdk.common.util.TBSdkLog.a(r2)     // Catch:{ Exception -> 0x0041, all -> 0x0043 }
            if (r2 == 0) goto L_0x0032
            java.lang.String r2 = "mtopsdk.DefaultLoginImpl"
            java.lang.String r3 = "apiRefer="
            java.lang.String r4 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x0041, all -> 0x0043 }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Exception -> 0x0041, all -> 0x0043 }
            mtopsdk.common.util.TBSdkLog.b(r2, r3)     // Catch:{ Exception -> 0x0041, all -> 0x0043 }
        L_0x0032:
            java.lang.String r2 = "apiReferer"
            r1.putString(r2, r0)     // Catch:{ Exception -> 0x0041, all -> 0x0043 }
            r5.commitSessionInvalidEvent(r6)     // Catch:{ Exception -> 0x0041, all -> 0x0043 }
            java.lang.ThreadLocal<com.taobao.tao.remotebusiness.login.DefaultLoginImpl$SessionInvalidEvent> r6 = threadLocal
            r6.remove()
            r0 = r1
            goto L_0x004f
        L_0x0041:
            r0 = r1
            goto L_0x004a
        L_0x0043:
            r6 = move-exception
            java.lang.ThreadLocal<com.taobao.tao.remotebusiness.login.DefaultLoginImpl$SessionInvalidEvent> r7 = threadLocal
            r7.remove()
            throw r6
        L_0x004a:
            java.lang.ThreadLocal<com.taobao.tao.remotebusiness.login.DefaultLoginImpl$SessionInvalidEvent> r6 = threadLocal
            r6.remove()
        L_0x004f:
            r5.registerReceiver()
            java.lang.reflect.Method r6 = r5.loginMethod
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r2 = 0
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            r1[r2] = r7
            r7 = 1
            r1[r7] = r0
            r5.invokeMethod(r6, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.tao.remotebusiness.login.DefaultLoginImpl.login(com.taobao.tao.remotebusiness.login.onLoginListener, boolean):void");
    }

    public final boolean isSessionValid() {
        Boolean bool = (Boolean) invokeMethod(this.checkSessionValidMethod, new Object[0]);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public final boolean isLogining() {
        Boolean bool = (Boolean) invokeMethod(this.isLoginingMethod, new Object[0]);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public final LoginContext getLoginContext() {
        this.loginContext.sid = (String) invokeMethod(this.getSidMethod, new Object[0]);
        this.loginContext.userId = (String) invokeMethod(this.getUserIdMethod, new Object[0]);
        this.loginContext.nickname = (String) invokeMethod(this.getNickMethod, new Object[0]);
        return this.loginContext;
    }

    private void commitSessionInvalidEvent(SessionInvalidEvent sessionInvalidEvent) {
        ffv ffv = Mtop.a(mContext).c.w;
        if (ffv != null) {
            if (isRegistered.compareAndSet(false, true)) {
                HashSet hashSet = new HashSet();
                hashSet.add("long_nick");
                hashSet.add(c.n);
                hashSet.add("apiV");
                hashSet.add("msgCode");
                hashSet.add("S_STATUS");
                hashSet.add("processName");
                hashSet.add("appBackGround");
                if (ffv != null) {
                    ffv.a((String) STATS_MODULE_MTOPRB, (String) STATS_MONITOR_POINT_SESSION_INVALID, (Set<String>) hashSet, (Set<String>) null);
                }
                if (TBSdkLog.a(LogEnable.DebugEnable)) {
                    TBSdkLog.a((String) TAG, (String) "onRegister called. module=mtoprb,monitorPoint=SessionInvalid");
                }
            }
            HashMap hashMap = new HashMap();
            hashMap.put("long_nick", sessionInvalidEvent.long_nick);
            hashMap.put(c.n, sessionInvalidEvent.apiName);
            hashMap.put("apiV", sessionInvalidEvent.v);
            hashMap.put("msgCode", sessionInvalidEvent.msgCode);
            hashMap.put("S_STATUS", sessionInvalidEvent.S_STATUS);
            hashMap.put("processName", sessionInvalidEvent.processName);
            hashMap.put("appBackGround", sessionInvalidEvent.appBackGround ? "1" : "0");
            if (ffv != null) {
                ffv.a((String) STATS_MODULE_MTOPRB, (String) STATS_MONITOR_POINT_SESSION_INVALID, (Map<String, String>) hashMap, (Map<String, Double>) null);
            }
        }
    }
}
