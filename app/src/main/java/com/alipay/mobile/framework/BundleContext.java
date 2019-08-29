package com.alipay.mobile.framework;

import android.content.Context;
import android.content.res.Resources;
import com.alipay.mobile.quinox.utils.LogUtil;
import com.alipay.mobile.quinox.utils.ReflectUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BundleContext {
    public static final String TAG = "FrameworkBundleContext";
    private final Object a;
    private Method b;
    private Method c;
    private Method d;
    private Method e;
    private Method f;

    BundleContext(Object bundleContext) {
        this.a = bundleContext;
        try {
            this.b = this.a.getClass().getDeclaredMethod("findPackagesByBundleName", new Class[]{String.class});
            this.c = this.a.getClass().getDeclaredMethod("replaceResources", new Class[]{Context.class, String.class, String[].class});
            this.d = this.a.getClass().getDeclaredMethod("replaceResourcesByBundleName", new Class[]{Context.class, String.class, String[].class});
            this.e = this.a.getClass().getDeclaredMethod("getResourcesByBundle", new Class[]{String.class});
            this.f = this.a.getClass().getDeclaredMethod("findClassLoaderByBundleName", new Class[]{String.class});
        } catch (Exception e2) {
            LogUtil.w((String) TAG, (Throwable) e2);
        }
    }

    public boolean updateBundles(List<String> updatePaths, List<String> removeNames, Set<String> bundleNames) {
        return ((Boolean) this.a.getClass().getDeclaredMethod("updateBundles", new Class[]{List.class, List.class, Set.class}).invoke(this.a, new Object[]{updatePaths, removeNames, bundleNames})).booleanValue();
    }

    public Set<String> revertBundles(List<String> bundleNames) {
        return (Set) this.a.getClass().getDeclaredMethod("revertBundles", new Class[]{List.class}).invoke(this.a, new Object[]{bundleNames});
    }

    public void addExternalBundle(String path) {
        MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        microApplicationContext.loadBundle((String) this.a.getClass().getDeclaredMethod("addExternalBundle", new Class[]{String.class}).invoke(this.a, new Object[]{path}));
    }

    public void removeExternalBundle(String path) {
        this.a.getClass().getDeclaredMethod("removeExternalBundle", new Class[]{String.class}).invoke(this.a, new Object[]{path});
    }

    public ClassLoader findClassLoaderByBundleName(String bundleName) {
        try {
            return (ClassLoader) this.f.invoke(this.a, new Object[]{bundleName});
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
            return null;
        }
    }

    public Set<String> findPackagesByBundleName(String bundleName) {
        try {
            return (Set) this.b.invoke(this.a, new Object[]{bundleName});
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
            return null;
        }
    }

    public Collection<String> getAllBundleNames() {
        try {
            return (Collection) this.a.getClass().getDeclaredMethod("getAllBundleNames", new Class[0]).invoke(this.a, new Object[0]);
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
            return null;
        }
    }

    public String getBundleNameByComponent(String component) {
        try {
            return (String) this.a.getClass().getDeclaredMethod("getBundleNameByComponent", new Class[]{String.class}).invoke(this.a, new Object[]{component});
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
            return null;
        }
    }

    public boolean isLazyBundleByBundleName(String bundleName) {
        Map map = LauncherApplicationAgent.getInstance().getLazyBundles();
        if (map == null || bundleName == null) {
            return false;
        }
        return map.containsKey(bundleName);
    }

    public void init(String bundleName) {
        try {
            this.a.getClass().getDeclaredMethod("init", new Class[]{String.class}).invoke(this.a, new Object[]{bundleName});
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
            FrameworkMonitor.getInstance(null).handleLoadBundleFail(bundleName, "1000");
        }
    }

    public boolean isLazyBundleByAppId(String id) {
        Map map = LauncherApplicationAgent.getInstance().getLazyBundles();
        if (map == null) {
            return false;
        }
        for (Set<String> contains : map.values()) {
            if (contains.contains(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean isLazyBundleByServiceName(String serviceName) {
        return isLazyBundleByAppId(serviceName);
    }

    public String findBundleNameByAppId(String id) {
        Map map = LauncherApplicationAgent.getInstance().getLazyBundles();
        if (map != null) {
            for (String name : map.keySet()) {
                if (map.get(name).contains(id)) {
                    return name;
                }
            }
        }
        return null;
    }

    public String findBundleNameByServiceName(String serviceName) {
        return findBundleNameByAppId(serviceName);
    }

    public void loadBundle(String bundleName) {
        try {
            init(bundleName);
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().loadBundle(bundleName);
        } catch (Exception e2) {
            TraceLogger.e((String) "loadBundle", (Throwable) e2);
            FrameworkMonitor.getInstance(null).handleLoadBundleFail(bundleName, "1001");
        }
    }

    public void replaceResources(Context context, String component, String... bundleNames) {
        try {
            this.c.invoke(this.a, new Object[]{context, component, bundleNames});
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
        }
    }

    public void replaceResourcesByBundleName(Context context, String bundleName, String... bundleNames) {
        try {
            this.d.invoke(this.a, new Object[]{context, bundleName, bundleNames});
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
        }
    }

    public void appendResourcesByBundleName(Context context, String... bundleNames) {
        try {
            ReflectUtil.invokeMethod(this.a, (String) "appendResourcesByBundleName", new Class[]{Context.class, String[].class}, new Object[]{context, bundleNames});
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
        }
    }

    public Resources getResourcesByBundle(String name) {
        try {
            return (Resources) this.e.invoke(this.a, new Object[]{name});
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
            return null;
        }
    }

    public String getBundleFilePath(String groupId, String artifactId, String version) {
        try {
            return (String) this.a.getClass().getDeclaredMethod("getBundleFilePath", new Class[]{String.class, String.class, String.class}).invoke(this.a, new Object[]{groupId, artifactId, version});
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
            return null;
        }
    }

    public Map<String, String> getReusedBundleLocations() {
        try {
            return (Map) this.a.getClass().getDeclaredMethod("getReusedBundleLocations", new Class[0]).invoke(this.a, new Object[0]);
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
            return null;
        }
    }

    public String getBundleLocation(String bundleName) {
        try {
            return (String) this.a.getClass().getDeclaredMethod("getBundleLocation", new Class[]{String.class}).invoke(this.a, new Object[]{bundleName});
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
            return null;
        }
    }

    public void setupInstrumentation() {
        try {
            this.a.getClass().getDeclaredMethod("setupInstrumentation", new Class[0]).invoke(this.a, new Object[0]);
        } catch (Exception e2) {
            TraceLogger.e((String) TAG, (Throwable) e2);
        }
    }
}
