package com.ali.user.mobile.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.ali.user.mobile.register.router.LocalRouter;
import com.ali.user.mobile.register.router.RPCRouter;
import com.ali.user.mobile.register.store.ActionCenter;
import com.ali.user.mobile.register.store.IStorageCallback;
import com.ali.user.mobile.register.ui.RegPurePhoneActivity;

public class RegContext {
    private static volatile RegContext e;
    public LocalRouter a = new LocalRouter();
    public RPCRouter b = new RPCRouter();
    public ActionCenter c = new ActionCenter();
    private String d;

    public static RegContext a() {
        if (e == null) {
            synchronized (RegContext.class) {
                try {
                    if (e == null) {
                        e = new RegContext();
                    }
                }
            }
        }
        return e;
    }

    private RegContext() {
        this.c.a((IStorageCallback) this.a);
        this.c.b((IStorageCallback) this.b);
    }

    public final boolean b() {
        return "YES".equals(this.d);
    }

    public final void a(String str) {
        this.d = str;
    }

    public final void a(Activity activity, Bundle bundle, Account account) {
        if (activity != null) {
            Intent intent = new Intent(activity, RegPurePhoneActivity.class);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            if (activity.getIntent().getExtras() != null) {
                intent.putExtras(activity.getIntent().getExtras());
            }
            if (!(account == null || this.c == null)) {
                this.c.a(account);
            }
            activity.startActivity(intent);
        }
    }
}
