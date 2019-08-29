package com.ali.user.mobile.login;

import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.log.AliUserLog;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class LoginActivityCollections {
    private static LoginActivityCollections b;
    private final Map<String, WeakReference<BaseActivity>> a = new HashMap();

    private LoginActivityCollections() {
    }

    public static LoginActivityCollections a() {
        if (b == null) {
            synchronized (LoginActivityCollections.class) {
                try {
                    if (b == null) {
                        b = new LoginActivityCollections();
                    }
                }
            }
        }
        return b;
    }

    public final void a(BaseActivity baseActivity) {
        synchronized (this) {
            if (baseActivity != null) {
                try {
                    String obj = baseActivity.toString();
                    AliUserLog.c("LoginActivityCollections", "recordActivity,key=".concat(String.valueOf(obj)));
                    if (!this.a.containsKey(obj)) {
                        this.a.put(obj, new WeakReference(baseActivity));
                    }
                } finally {
                }
            }
        }
    }

    public final void b() {
        synchronized (this) {
            if (this.a != null) {
                AliUserLog.c("LoginActivityCollections", "start destroy");
                Collection<WeakReference<BaseActivity>> values = this.a.values();
                if (values != null && !values.isEmpty()) {
                    for (WeakReference<BaseActivity> weakReference : values) {
                        BaseActivity baseActivity = (BaseActivity) weakReference.get();
                        if (baseActivity != null && !baseActivity.isFinishing() && !baseActivity.isExclude()) {
                            StringBuilder sb = new StringBuilder("finish ");
                            sb.append(baseActivity.toString());
                            AliUserLog.c("LoginActivityCollections", sb.toString());
                            baseActivity.finish();
                        }
                    }
                    this.a.clear();
                }
            }
        }
    }
}
