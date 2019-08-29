package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"isAlipayBound", "logout", "getAmapUserId", "alipay_auth", "userUnbundling", "taoIfLogin", "loginBind", "showLoginPannel"}, jsActions = {"com.autonavi.bundle.account.jsaction.IsAlipayBound", "com.autonavi.bundle.account.jsaction.LogoutAction", "com.autonavi.bundle.account.jsaction.GetAmapUserIdAction", "com.autonavi.bundle.account.jsaction.AlipayAuthAction", "com.autonavi.bundle.account.jsaction.UserUnbindAction", "com.autonavi.bundle.account.jsaction.TaoIfLoginAction", "com.autonavi.bundle.account.jsaction.LoginBindAction", "com.autonavi.bundle.account.jsaction.ShowLoginPannelAction"}, module = "account")
@KeepName
public final class ACCOUNT_JsAction_DATA extends HashMap<String, Class<?>> {
    public ACCOUNT_JsAction_DATA() {
        put("isAlipayBound", anx.class);
        put("logout", anz.class);
        put("getAmapUserId", anw.class);
        put("alipay_auth", anv.class);
        put("userUnbundling", aoc.class);
        put("taoIfLogin", aob.class);
        put("loginBind", any.class);
        put("showLoginPannel", aoa.class);
    }
}
