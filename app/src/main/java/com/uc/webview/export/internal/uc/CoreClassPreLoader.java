package com.uc.webview.export.internal.uc;

import com.uc.webview.export.annotations.Reflection;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import com.uc.webview.export.internal.setup.UCSetupException;
import com.uc.webview.export.internal.utility.ReflectionUtil.BindingMethod;

/* compiled from: ProGuard */
public class CoreClassPreLoader {
    @Reflection
    protected static LazyClass Lazy;
    @Reflection
    protected static Runnable sLazyUpdateCallback;

    /* compiled from: ProGuard */
    public static class LazyClass {
        final BindingMethod<Boolean> a = new BindingMethod<>(this.sCoreClassLoaderImpl, "loadCoreClass", new Class[]{ClassLoader.class});
        @Reflection
        public final Class<?> sCoreClassLoaderImpl;

        private static Class<?> a(ClassLoader classLoader) {
            try {
                return Class.forName(UCMPackageInfo.CORE_CLASS_LOADER_IMPL_CLASS, true, classLoader);
            } catch (ClassNotFoundException e) {
                throw new UCSetupException(4028, (Throwable) e);
            }
        }

        public LazyClass(ClassLoader classLoader) {
            this.sCoreClassLoaderImpl = a(classLoader);
        }
    }

    @Reflection
    public static synchronized void updateLazy(ClassLoader classLoader) {
        synchronized (CoreClassPreLoader.class) {
            if (Lazy == null) {
                Lazy = new LazyClass(classLoader);
                if (sLazyUpdateCallback != null) {
                    sLazyUpdateCallback.run();
                }
            }
        }
    }

    @Reflection
    public static boolean loadCoreClass(ClassLoader classLoader) {
        return ((Boolean) Lazy.a.invoke(new Object[]{classLoader})).booleanValue();
    }
}
