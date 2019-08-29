package com.alipay.android.phone.inside.bizadapter.rpc;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthHelper;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.inside.android.phone.mrpc.core.RpcInterceptor;
import com.alipay.inside.android.phone.mrpc.core.RpcInvocationHandler;
import com.alipay.inside.mobile.framework.service.annotation.NoRelogin;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

public class CommonInterceptor implements RpcInterceptor {
    private static final ThreadLocal<Map<String, Object>> b = new ThreadLocal<Map<String, Object>>() {
        /* access modifiers changed from: protected */
        public final /* synthetic */ Object initialValue() {
            return new HashMap(2);
        }
    };
    private Map<Long, String> a = AccountOAuthHelper.getInstance().getOAuthLoginTaskMap();

    public boolean postHandle(Object obj, ThreadLocal<Object> threadLocal, byte[] bArr, Class<?> cls, Method method, Object[] objArr, Annotation annotation) throws RpcException {
        return true;
    }

    public boolean preHandle(Object obj, ThreadLocal<Object> threadLocal, byte[] bArr, Class<?> cls, Method method, Object[] objArr, Annotation annotation, ThreadLocal<Map<String, Object>> threadLocal2) throws RpcException {
        return true;
    }

    public boolean exceptionHandle(Object obj, ThreadLocal<Object> threadLocal, byte[] bArr, Class<?> cls, Method method, Object[] objArr, RpcException rpcException, Annotation annotation) throws RpcException {
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("exceptionHandle,method:");
        sb.append(method.getName());
        f.b((String) "CommonInterceptor", sb.toString());
        int code = rpcException.getCode();
        boolean z = true;
        if (code == 2000) {
            Long l = (Long) a("expirTime");
            if (l == null || System.currentTimeMillis() > l.longValue()) {
                a((String) "retryTimes", (Object) Integer.valueOf(0));
                a((String) "expirTime", (Object) Long.valueOf(System.currentTimeMillis() + 60000));
            } else {
                Integer num = (Integer) a("retryTimes");
                if (num != null && num.intValue() > 2) {
                    LoggerFactory.f().b((String) "CommonInterceptor", (String) "retryTimes > 2, return.");
                    return true;
                }
            }
            String e = RunningConfig.e();
            if (!a()) {
                a((String) "retryTimes", (Object) Integer.valueOf(0));
                if (method.getAnnotation(NoRelogin.class) != null) {
                    String str = "";
                    try {
                        str = (String) ServiceExecutor.b("COMMONBIZ_SERVICE_LOGIN_EXPIRE_CHECK", new Bundle());
                    } catch (Throwable th) {
                        LoggerFactory.f().b((String) "CommonInterceptor", th);
                    }
                    if (!TextUtils.equals(str, "openAuthTokenLogin")) {
                        throw new RpcException(Integer.valueOf(-3007), (String) "rpc does not allow relogin");
                    }
                }
                RpcInvocationHandler rpcInvocationHandler = (RpcInvocationHandler) Proxy.getInvocationHandler(obj);
                if (rpcInvocationHandler == null || !TextUtils.equals(rpcInvocationHandler.getExtParams("OpenAuthLogin"), "YES")) {
                    try {
                        ServiceExecutor.b("COMMONBIZ_SERVICE_LOGIN_EXPIRE", new Bundle());
                    } catch (Throwable unused) {
                        throw new RpcException(Integer.valueOf(2000), (String) "re login fail.");
                    }
                } else {
                    LoggerFactory.f().a((String) "CommonInterceptor", String.format("innerOpenAuth 线程 key=%s", new Object[]{Long.valueOf(Thread.currentThread().getId())}));
                    synchronized (this.a) {
                        this.a.put(Long.valueOf(Thread.currentThread().getId()), "wait");
                    }
                    c(method, rpcInvocationHandler, "NO");
                    synchronized (this.a) {
                        LoggerFactory.f().a((String) "CommonInterceptor", String.format("innerOpenAuth 移除等待线程 key=%s", new Object[]{Long.valueOf(Thread.currentThread().getId())}));
                        this.a.remove(Long.valueOf(Thread.currentThread().getId()));
                    }
                }
            }
            String e2 = RunningConfig.e();
            if (RunningConfig.j() || e == null || e2 == null || e2.equals(e)) {
                try {
                    threadLocal.set(method.invoke(obj, objArr));
                    z = false;
                } catch (IllegalArgumentException e3) {
                    throw new RpcException(Integer.valueOf(9), String.valueOf(e3));
                } catch (IllegalAccessException e4) {
                    throw new RpcException(Integer.valueOf(9), String.valueOf(e4));
                } catch (InvocationTargetException e5) {
                    throw new RpcException(Integer.valueOf(9), String.valueOf(e5));
                }
            } else {
                throw new RpcException(Integer.valueOf(12), (String) "change user.");
            }
        }
        return z;
    }

    private void a(Method method, RpcInvocationHandler rpcInvocationHandler, String str) {
        Bundle bundle;
        Bundle bundle2 = new Bundle();
        LoggerFactory.f().e("CommonInterceptor", "auth new flow");
        bundle2.putString("cAuthUUID", rpcInvocationHandler.getExtParams("cAuthUUID"));
        bundle2.putString("needOpenAuth", rpcInvocationHandler.getExtParams("needOpenAuth"));
        bundle2.putString("bizSource", rpcInvocationHandler.getExtParams("bizSource"));
        bundle2.putString("needRefreshToken", str);
        try {
            bundle = a((String) "OBTAIN_MC_AUTHINFO_SERVICE", bundle2);
        } catch (Throwable th) {
            if (th instanceof TimeoutException) {
                b(method, rpcInvocationHandler, "Intercepter_OpenAuth_Timeout");
                LoggerFactory.b();
                throw new RpcException(Integer.valueOf(80001), (String) "get authInfo timeout");
            }
            LoggerFactory.f().a("CommonInterceptor", "getMcAuthLoginInfo error", th);
            bundle = null;
        }
        if (bundle == null || (TextUtils.isEmpty(bundle.getString("openMcUid")) && TextUtils.isEmpty(bundle.getString("alipayUserId")) && TextUtils.isEmpty(bundle.getString("authToken")))) {
            b(method, rpcInvocationHandler, "getMCAuthInfoFailed");
            LoggerFactory.b();
            throw new RpcException(Integer.valueOf(80001), (String) "authInfo is null");
        }
        bundle.putBoolean("isNewOpenAuthFlow", true);
        b(method, rpcInvocationHandler, "Intercepter_OpenAuthLogin_Begin");
        if (bundle == null) {
            try {
                bundle = new Bundle();
            } catch (Throwable th2) {
                LoggerFactory.b();
                throw th2;
            }
        }
        ServiceExecutor.b("COMMONBIZ_SERVICE_LOGIN_EXPIRE", bundle);
        LoggerFactory.b();
    }

    private Bundle a(String str, Bundle bundle) throws Exception {
        final Object obj = new Object();
        final Bundle bundle2 = new Bundle();
        ServiceExecutor.a(str, bundle, new IInsideServiceCallback<Bundle>() {
            public /* synthetic */ void onComplted(Object obj) {
                Bundle bundle = (Bundle) obj;
                LoggerFactory.f().e("openauth", "get McAuthLoginInfo result：".concat(String.valueOf(bundle)));
                bundle2.putAll(bundle);
                synchronized (obj) {
                    obj.notifyAll();
                }
            }

            public void onException(Throwable th) {
                LoggerFactory.e().a((String) "openauth", (String) "McAuthLoginInfo", th);
                synchronized (obj) {
                    obj.notifyAll();
                }
            }
        });
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (obj) {
            try {
                obj.wait(300000);
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "openauth", (String) "McAuthLoginInfo", th);
            }
        }
        if (System.currentTimeMillis() - currentTimeMillis <= 299000) {
            return bundle2;
        }
        throw new TimeoutException();
    }

    private static boolean a() throws RpcException {
        Object a2 = a("retryTimes");
        if (a2 == null || !(a2 instanceof Integer)) {
            a((String) "retryTimes", (Object) Integer.valueOf(1));
        } else {
            a((String) "retryTimes", (Object) Integer.valueOf(((Integer) a2).intValue() + 1));
        }
        LoggerFactory.f().b((String) "CommonInterceptor", (String) "CommonInterceptor::doLogin > start");
        boolean z = false;
        try {
            Bundle bundle = new Bundle();
            bundle.putString("action", "rpc_auth");
            bundle.putBoolean("notShowLoginApp", true);
            z = ((Boolean) ServiceExecutor.b("AUTO_AUTH_EXTERNAL_SERVICE", bundle)).booleanValue();
        } catch (RpcException e) {
            throw e;
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "CommonInterceptor", th);
        }
        LoggerFactory.f().b((String) "CommonInterceptor", "CommonInterceptor::doLogin > rpcAuth:".concat(String.valueOf(z)));
        return z;
    }

    private static final void a(String str, Object obj) {
        b.get().put(str, obj);
    }

    private static final Object a(String str) {
        return b.get().get(str);
    }

    private static void b(Method method, RpcInvocationHandler rpcInvocationHandler, String str) {
        try {
            Behavior behavior = new Behavior();
            behavior.a = "action";
            behavior.b = BehaviorType.EVENT;
            behavior.c = str;
            behavior.g = rpcInvocationHandler.getExtParams("bizSource");
            if (!(method == null || method.getAnnotation(OperationType.class) == null)) {
                behavior.h = ((OperationType) method.getAnnotation(OperationType.class)).value();
            }
            LoggerFactory.d().a(behavior);
            LoggerFactory.f().a((String) "CommonInterceptor", "reportAuthBehavior:".concat(String.valueOf(str)));
        } catch (Throwable th) {
            LoggerFactory.f().a((String) "CommonInterceptor", th);
        }
    }

    private void c(Method method, RpcInvocationHandler rpcInvocationHandler, String str) {
        synchronized (AccountOAuthHelper.getInstance().getLock()) {
            String str2 = this.a.get(Long.valueOf(Thread.currentThread().getId()));
            LoggerFactory.f().a((String) "CommonInterceptor", String.format("syncOpenAuth 当前线程被标记状态: %s", new Object[]{str2}));
            if ("canceled".equals(str2)) {
                throw new RpcException(Integer.valueOf(2000), (String) "login fail.");
            } else if ("thread_ok".equals(str2)) {
                LoggerFactory.f().a((String) "CommonInterceptor", (String) "return掉，被拦截业务继续执行");
                return;
            } else {
                try {
                    a(method, rpcInvocationHandler, str);
                    LoggerFactory.f().a((String) "CommonInterceptor", (String) "三方授权登录成功");
                    synchronized (this.a) {
                        for (Entry<Long, String> key : this.a.entrySet()) {
                            Long l = (Long) key.getKey();
                            LoggerFactory.f().a((String) "CommonInterceptor", String.format("登录完成且登录成功，设置 授权/登录 等待线程 为ok状态 key=%s", new Object[]{l}));
                            this.a.put(l, "thread_ok");
                        }
                    }
                    return;
                } catch (Throwable th) {
                    LoggerFactory.f().a((String) "CommonInterceptor", (String) "三方授权登录失败");
                    synchronized (this.a) {
                        for (Entry<Long, String> key2 : this.a.entrySet()) {
                            Long l2 = (Long) key2.getKey();
                            LoggerFactory.f().a((String) "CommonInterceptor", String.format("登录完成且登录失败，设置等待线程 为取消状态 key=%s", new Object[]{l2}));
                            this.a.put(l2, "canceled");
                        }
                        throw th;
                    }
                }
            }
        }
    }
}
