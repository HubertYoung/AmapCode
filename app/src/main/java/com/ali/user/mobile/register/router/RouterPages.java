package com.ali.user.mobile.register.router;

import com.ali.user.mobile.log.AliUserLog;
import java.lang.ref.WeakReference;

public class RouterPages {
    private static WeakReference<IRouterHandler> a;

    public static synchronized IRouterHandler a() {
        synchronized (RouterPages.class) {
            try {
                if (a == null) {
                    return null;
                }
                IRouterHandler iRouterHandler = (IRouterHandler) a.get();
                return iRouterHandler;
            }
        }
    }

    public static synchronized void a(IRouterHandler iRouterHandler) {
        synchronized (RouterPages.class) {
            AliUserLog.c("Reg_router", "current handler ".concat(String.valueOf(iRouterHandler)));
            a = new WeakReference<>(iRouterHandler);
        }
    }
}
