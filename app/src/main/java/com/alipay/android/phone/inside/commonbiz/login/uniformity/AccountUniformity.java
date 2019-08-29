package com.alipay.android.phone.inside.commonbiz.login.uniformity;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.commonbiz.login.utils.LoginUtils;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class AccountUniformity {
    static final Object a = new Object();
    static final Map<String, AccountUniformity> b = new HashMap();
    private UniformityCallback c;

    public final boolean a(JSONObject jSONObject) {
        LoggerFactory.f().b((String) "inside", (String) "AccountUniformity::checkUniformity start");
        if (!(PluginManager.b("CHECK_LOGIN_CONSISTENCY_SERVICE") != null)) {
            LoggerFactory.f().b((String) "inside", (String) "AccountUniformity::checkUniformity hasLoginService=false");
            return true;
        } else if (LoginUtils.d(jSONObject)) {
            String c2 = LoginUtils.c(jSONObject);
            String d = LoginUtils.d();
            return TextUtils.isEmpty(d) || TextUtils.equals(c2, d);
        } else {
            final Bundle bundle = new Bundle();
            ServiceExecutor.a("CHECK_LOGIN_CONSISTENCY_SERVICE", null, new IInsideServiceCallback<String>() {
                public /* synthetic */ void onComplted(Object obj) {
                    bundle.putString("uniformity", (String) obj);
                    synchronized (AccountUniformity.a) {
                        AccountUniformity.a.notifyAll();
                    }
                }

                public void onException(Throwable th) {
                    LoggerFactory.e().a((String) "main", (String) "AccountUniformityEx", th);
                    synchronized (AccountUniformity.a) {
                        AccountUniformity.a.notifyAll();
                    }
                }
            });
            synchronized (a) {
                try {
                    a.wait(20000);
                } catch (Throwable th) {
                    LoggerFactory.f().c((String) "inside", th);
                }
            }
            String string = bundle.getString("uniformity");
            LoggerFactory.d().b("main", BehaviorType.EVENT, "AccountUniformity|".concat(String.valueOf(string)));
            LoggerFactory.f().b((String) "inside", "AccountUniformity::checkUniformity uniformity=".concat(String.valueOf(string)));
            return !TextUtils.equals(string, "notConsistentcy");
        }
    }

    public final UniformityCallback a() {
        return this.c;
    }

    public static void a(String str) {
        AccountUniformity accountUniformity = b.get(str);
        if (accountUniformity != null) {
            synchronized (accountUniformity) {
                accountUniformity.notifyAll();
            }
            return;
        }
        LoggerFactory.e().a((String) "commonbiz", (String) "NotifyFinishUniformityUnMatch", "uuid=".concat(String.valueOf(str)));
    }
}
