package com.ali.user.mobile.context;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.external.AuthLoginResultActivity;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoginMonitor;
import com.ali.user.mobile.login.OnLoginCaller;
import com.ali.user.mobile.rsa.RSAHandler;
import com.ali.user.mobile.service.UserLoginService;
import com.ali.user.mobile.service.UserRegisterService;
import com.ali.user.mobile.service.impl.UserLoginServiceImpl;
import com.ali.user.mobile.service.impl.UserRegisterServiceImpl;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import java.util.Stack;

public class AliuserLoginContext {
    private static boolean a = false;
    private static boolean b = false;
    private static String c = null;
    private static Class<?> d = null;
    private static RSAHandler e = null;
    private static UserLoginService f = null;
    private static UserRegisterService g = null;
    private static Stack<LoginHandler> h = new Stack<>();
    private static IInsideServiceCallback i = null;
    private static IInsideServiceCallback j = null;
    private static OnLoginCaller k = null;
    private static boolean l = true;

    public static boolean a() {
        return a;
    }

    public static void a(boolean z) {
        AliUserLog.c("AliuserLoginContext", "can login come back：".concat(String.valueOf(z)));
        a = z;
    }

    public static boolean b() {
        return b;
    }

    public static Intent a(Context context) {
        Intent intent = new Intent();
        if (l() != null) {
            intent.setClass(context, l());
        } else {
            if (TextUtils.isEmpty(c)) {
                c = "com.ali.user.mobile.login.ui.AliUserLoginActivity";
            }
            intent.setClassName(context, c);
        }
        return intent;
    }

    private static Class<?> l() {
        StringBuilder sb = new StringBuilder("config login clazz:");
        sb.append(d);
        AliUserLog.c("AliuserLoginContext", sb.toString());
        return d;
    }

    public static void a(Class<?> cls) {
        d = cls;
    }

    public static void a(RSAHandler rSAHandler) {
        e = rSAHandler;
    }

    public static RSAHandler c() {
        return e;
    }

    public static UserLoginService d() {
        synchronized (AliuserLoginContext.class) {
            try {
                if (f == null) {
                    f = new UserLoginServiceImpl(AliUserInit.b());
                }
            }
        }
        return f;
    }

    public static UserRegisterService e() {
        synchronized (AliuserLoginContext.class) {
            if (g == null) {
                g = new UserRegisterServiceImpl(AliUserInit.b());
            }
        }
        return g;
    }

    public static LoginHandler f() {
        AliUserLog.c("AliuserLoginContext", String.format("stack size is %s when get: ", new Object[]{Integer.valueOf(h.size())}));
        if (h.isEmpty()) {
            return null;
        }
        LoginHandler peek = h.peek();
        AliUserLog.c("AliuserLoginContext", "getLoginHandler:".concat(String.valueOf(peek)));
        return peek;
    }

    public static void a(LoginHandler loginHandler) {
        if (loginHandler == null || !(loginHandler instanceof AuthLoginResultActivity)) {
            h.clear();
            h.push(loginHandler);
        } else {
            AliUserLog.c("AliuserLoginContext", "stack push auth");
            h.push(loginHandler);
        }
        AliUserLog.c("AliuserLoginContext", String.format("stack size is %s after push: ", new Object[]{Integer.valueOf(h.size()), loginHandler}));
    }

    public static IInsideServiceCallback g() {
        return i;
    }

    public static void a(IInsideServiceCallback iInsideServiceCallback) {
        i = iInsideServiceCallback;
    }

    public static IInsideServiceCallback h() {
        return j;
    }

    public static void b(IInsideServiceCallback iInsideServiceCallback) {
        j = iInsideServiceCallback;
    }

    public static OnLoginCaller i() {
        return k;
    }

    public static void a(OnLoginCaller onLoginCaller) {
        k = onLoginCaller;
    }

    public static boolean j() {
        return l;
    }

    public static void k() {
        AliUserLog.c("AliuserLoginContext", "destroy");
        b = false;
        a = false;
        h.clear();
        LoginMonitor.a();
    }
}
