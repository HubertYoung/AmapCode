package com.alipay.mobile.framework.quinoxless;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.alipay.mobile.quinox.apkfile.ApkFileInputStreamCallback;
import com.alipay.mobile.quinox.apkfile.ApkFileReader;
import com.alipay.mobile.quinox.bundle.Bundle;
import com.alipay.mobile.quinox.bundle.IBundle;
import com.alipay.mobile.quinox.bundle.IBundleOperator;
import com.alipay.mobile.quinox.bundle.bytedata.ByteDataBundleOperator;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class QuinoxlessBundleContext {
    public static final String TAG = QuinoxlessBundleContext.class.getSimpleName();
    public static boolean isDebug = false;
    private final Context a;
    private Map<String, Bundle> b;

    QuinoxlessBundleContext(Context context) {
        this.a = context;
    }

    public Collection<String> getAllBundleNames() {
        return a().keySet();
    }

    public ClassLoader findClassLoaderByBundleName(String bundleName) {
        return getClass().getClassLoader();
    }

    public Set<String> findPackagesByBundleName(String bundleName) {
        if (TextUtils.isEmpty(bundleName)) {
            throw new IllegalArgumentException("bundleName can't be empty.");
        }
        Bundle bundle = a().get(bundleName);
        if (bundle != null) {
            List strings = bundle.getPackageNames();
            if (strings != null && !strings.isEmpty()) {
                Set rets = new HashSet(strings.size());
                for (String string : strings) {
                    if (string != null && string.length() > 0) {
                        rets.add(string);
                    }
                }
                return rets;
            }
        }
        return null;
    }

    public String getBundleNameByComponent(String component) {
        return null;
    }

    public String getBundleFilePath(String groupId, String artifactId, String version) {
        return null;
    }

    public String getBundleLocation(String bundleName) {
        return null;
    }

    public String addExternalBundle(String path) {
        throw new UnsupportedOperationException("addExternalBundle not supported");
    }

    public boolean updateBundles(List<String> updatePaths, List<String> removeNames, Set<String> bundleNames) {
        throw new UnsupportedOperationException("updateBundles not supported");
    }

    public Set<String> revertBundles(List<String> bundleNames) {
        throw new UnsupportedOperationException("revertBundles not supported");
    }

    public void removeExternalBundle(String path) {
        throw new UnsupportedOperationException("removeExternalBundle not supported");
    }

    public Bundle init(String bundleName) {
        return a().get(bundleName);
    }

    public void replaceResources(Context context, String component, String... bundleNames) {
        if (isDebug) {
            TraceLogger.w(TAG, (Throwable) new Exception("replaceResources component=" + component));
        }
    }

    public void replaceResourcesByBundleName(Context context, String bundleName, String... bundleNames) {
        if (isDebug) {
            TraceLogger.w(TAG, (Throwable) new Exception("replaceResourcesByBundleName bundleName=" + bundleName));
        }
    }

    public void appendResourcesByBundleName(Context context, String... bundleNames) {
        if (isDebug) {
            TraceLogger.w(TAG, (Throwable) new Exception("appendResourcesByBundleName bundleNames=" + bundleNames));
        }
    }

    public Resources getResourcesByBundle(String bundleName) {
        return this.a.getResources();
    }

    public Map<String, String> getReusedBundleLocations() {
        return null;
    }

    public void setupInstrumentation() {
        if (isDebug) {
            TraceLogger.w(TAG, (Throwable) new Exception("setupInstrumentation"));
        }
    }

    private Map<String, Bundle> a() {
        if (this.b == null) {
            List slinks = new ArrayList();
            Map _bundles = new HashMap();
            a(slinks, _bundles, new ByteDataBundleOperator(null));
            this.b = new HashMap();
            for (Entry entry : _bundles.entrySet()) {
                this.b.put(entry.getKey(), new Bundle((IBundle) entry.getValue()));
            }
        }
        return this.b;
    }

    private boolean a(final List<String> slinks, final Map<String, IBundle> bundles, final IBundleOperator bundleOperator) {
        if (!new ApkFileReader().readAssets(this.a, bundleOperator.getBundleType().getName(), false, new ApkFileInputStreamCallback() {
            public boolean onInputStream(InputStream is) {
                try {
                    bundleOperator.readBundlesFromInputStream(is, slinks, bundles);
                    return true;
                } catch (IOException e) {
                    return false;
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxlessBundleContext.TAG, t);
                    slinks.clear();
                    bundles.clear();
                    return true;
                }
            }
        }) || slinks.isEmpty() || bundles.isEmpty()) {
            return false;
        }
        return true;
    }
}
