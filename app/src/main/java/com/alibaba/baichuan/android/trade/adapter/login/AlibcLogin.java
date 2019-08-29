package com.alibaba.baichuan.android.trade.adapter.login;

import android.app.Activity;
import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.core.config.Environment;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.LoginService;
import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.adapter.ut.UserTrackerCompoment;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;

public class AlibcLogin implements IAlibcLogin {
    public static final AlibcLogin INSTANCE = new AlibcLogin();
    private LoginService a;
    private boolean b;

    static class a {
        public static AlibcLogin a = new AlibcLogin(null);
    }

    private AlibcLogin() {
    }

    /* synthetic */ AlibcLogin(a aVar) {
        this();
    }

    private synchronized void a() {
        if (this.a == null) {
            this.a = (LoginService) MemberSDK.getService(LoginService.class);
        }
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        UserTrackerCompoment.sendUseabilityFailureForOtherErrmsg(UserTrackerConstants.U_LOGIN, str, UserTrackerConstants.ERRCODE_LOGIN);
    }

    private void b() {
        Environment environment = AlibcContext.environment == AlibcContext.Environment.TEST ? Environment.TEST : AlibcContext.environment == AlibcContext.Environment.PRE ? Environment.PRE : Environment.ONLINE;
        MemberSDK.setEnvironment(environment);
    }

    /* access modifiers changed from: private */
    public void c() {
        UserTrackerCompoment.sendUseabilitySuccess(UserTrackerConstants.U_LOGIN);
    }

    public static AlibcLogin getInstance() {
        return a.a;
    }

    public Session getSession() {
        a();
        if (this.a == null) {
            return null;
        }
        this.a.checkSessionValid();
        return this.a.getSession();
    }

    public synchronized void init() {
        if (!this.b) {
            this.b = true;
            b();
            MemberSDK.init(AlibcContext.context, new a(this));
        }
    }

    public boolean isLogin() {
        if (this.a != null) {
            return this.a.checkSessionValid();
        }
        return false;
    }

    public void logout(Activity activity, LogoutCallback logoutCallback) {
        a();
        if (this.a == null) {
            logoutCallback.onFailure(0, "login服务为null");
            return;
        }
        if (this.a != null) {
            this.a.logout(logoutCallback);
        }
    }

    public void showLogin(Activity activity, AlibcLoginCallback alibcLoginCallback) {
        a();
        if (this.a == null) {
            alibcLoginCallback.onFailure(0, "login服务为null");
        } else {
            this.a.auth(new b(this, alibcLoginCallback));
        }
    }
}
