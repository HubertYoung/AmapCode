package com.uc.webview.export.extension;

import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.extension.IARDetector.ResultListener;
import com.uc.webview.export.internal.uc.CoreFactory;
import com.uc.webview.export.internal.utility.ReflectionUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

@Api
/* compiled from: ProGuard */
public abstract class ARManager implements ResultListener {
    private static ARManager a;
    private static Method b;

    public static ARManager getInstance() {
        if (a == null) {
            try {
                ARManager q = CoreFactory.q();
                a = q;
                b = ReflectionUtil.getMethod(q.getClass(), "invoke");
            } catch (Exception unused) {
            }
        }
        return a;
    }

    public void registerARDetector(String str, String str2) {
        if (a != null) {
            a.registerARDetector(str, str2);
        }
    }

    public void registerARDetector(String str, String str2, HashMap<String, String> hashMap) {
        if (!(a == null || b == null)) {
            Object[] objArr = {str, str2, hashMap};
            try {
                b.invoke(a, new Object[]{Integer.valueOf(2), objArr});
            } catch (RuntimeException unused) {
            } catch (IllegalAccessException unused2) {
            } catch (InvocationTargetException unused3) {
            }
        }
    }

    public void registerARDetector(Object obj) {
        if (!(a == null || b == null)) {
            Object[] objArr = {obj};
            try {
                b.invoke(a, new Object[]{Integer.valueOf(3), objArr});
            } catch (RuntimeException unused) {
            } catch (IllegalAccessException unused2) {
            } catch (InvocationTargetException unused3) {
            }
        }
    }

    public void registerARSession(Object obj) {
        if (!(a == null || b == null)) {
            Object[] objArr = {obj};
            try {
                b.invoke(a, new Object[]{Integer.valueOf(4), objArr});
            } catch (RuntimeException unused) {
            } catch (IllegalAccessException unused2) {
            } catch (InvocationTargetException unused3) {
            }
        }
    }
}
