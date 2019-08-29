package com.alipay.mobile.nebula.wallet;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.alipay.mobile.framework.BundleContext;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.appcenter.H5BaseApp;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.io.PoolingByteArrayOutputStream;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.provider.H5ThreadPoolProvider;
import com.alipay.mobile.nebula.providermanager.H5BaseProviderInfo;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.tar.TarEntry;
import com.alipay.mobile.nebula.util.tar.TarInputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class H5WalletWrapper {
    public static final String HPM_FILE_NAME = "hpmfile.json";
    public static final String TAG = "H5WalletWrapper";

    public static String getConfig(String configName) {
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider == null) {
            return null;
        }
        return provider.getConfig(configName);
    }

    public static String getConfigWithProcessCache(String configName) {
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider == null) {
            return null;
        }
        return provider.getConfigWithProcessCache(configName);
    }

    public static Resources getNebulaResources() {
        return LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-nebula");
    }

    public static Resources getNebulaBizResources() {
        return LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle(H5BaseProviderInfo.nebulabiz);
    }

    public static Resources getNebulaCoreResources() {
        return LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-nebula");
    }

    public static Class<?> getClass(String bundleName, String className) {
        return getClass(bundleName, className, false);
    }

    public static Class<?> getClass(String bundleName, String className, boolean ignoreError) {
        H5Log.d("H5WalletWrapper", "getClass " + bundleName + ":" + className);
        try {
            BundleContext bundleContext = LauncherApplicationAgent.getInstance().getBundleContext();
            bundleContext.loadBundle(bundleName);
            ClassLoader classLoader = bundleContext.findClassLoaderByBundleName(bundleName);
            if (classLoader != null) {
                return classLoader.loadClass(className);
            }
            return null;
        } catch (Throwable t) {
            if (ignoreError) {
                return null;
            }
            H5Log.e("H5WalletWrapper", "failed to load class bundle.", t);
            return null;
        }
    }

    public static final <T> T findServiceByInterface(String name) {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(name);
    }

    public static String getHpmFile(String appId, String version) {
        TarEntry te;
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(version)) {
            return null;
        }
        H5AppCenterService h5AppCenterService = H5ServiceUtils.getAppCenterService();
        if (h5AppCenterService != null) {
            H5BaseApp h5BaseApp = h5AppCenterService.getH5App();
            AppInfo appInfo = new AppInfo();
            appInfo.app_id = appId;
            appInfo.version = version;
            String path = h5BaseApp.getInstalledPath();
            TarInputStream tis = null;
            byte[] buffer = null;
            try {
                if (!H5FileUtil.exists(new File(path))) {
                    H5Log.e((String) "H5WalletWrapper", "getHPMFileConfig path" + path + " not exist, return");
                    H5IOUtils.returnBuf(null);
                    H5IOUtils.closeQuietly(null);
                    return null;
                }
                TarInputStream tis2 = new TarInputStream(new BufferedInputStream(new FileInputStream(new File(path))));
                try {
                    buffer = H5IOUtils.getBuf(2048);
                    do {
                        te = tis2.getNextEntry();
                        if (te == null) {
                            H5IOUtils.returnBuf(buffer);
                            H5IOUtils.closeQuietly(tis2);
                        }
                    } while (!TextUtils.equals(te.getName(), HPM_FILE_NAME));
                    ByteArrayOutputStream bos = new PoolingByteArrayOutputStream();
                    while (true) {
                        int count = tis2.read(buffer);
                        if (count != -1) {
                            bos.write(buffer, 0, count);
                        } else {
                            byte[] data = bos.toByteArray();
                            H5IOUtils.closeQuietly(bos);
                            String str = new String(data);
                            H5IOUtils.returnBuf(buffer);
                            H5IOUtils.closeQuietly(tis2);
                            return str;
                        }
                    }
                } catch (Exception e) {
                    e = e;
                    tis = tis2;
                    try {
                        H5Log.e((String) "H5WalletWrapper", (Throwable) e);
                        H5IOUtils.returnBuf(buffer);
                        H5IOUtils.closeQuietly(tis);
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        H5IOUtils.returnBuf(buffer);
                        H5IOUtils.closeQuietly(tis);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    tis = tis2;
                    H5IOUtils.returnBuf(buffer);
                    H5IOUtils.closeQuietly(tis);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                H5Log.e((String) "H5WalletWrapper", (Throwable) e);
                H5IOUtils.returnBuf(buffer);
                H5IOUtils.closeQuietly(tis);
                return null;
            }
        }
        return null;
    }

    public static ThreadPoolExecutor getExecutor(String type) {
        return H5ThreadPoolFactory.getExecutor(type);
    }

    public static ScheduledThreadPoolExecutor getScheduledExecutor() {
        return H5ThreadPoolFactory.getScheduledExecutor();
    }

    public static void executeOrdered(String key, Runnable task) {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            H5ThreadPoolProvider provider = (H5ThreadPoolProvider) h5Service.getProviderManager().getProvider(H5ThreadPoolProvider.class.getName());
            if (provider != null) {
                provider.submitOrdered(key, task);
                return;
            }
        }
        H5ThreadPoolFactory.getSingleThreadExecutor().execute(task);
    }

    public static H5ProviderManager getH5ProviderManager() {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            return h5Service.getProviderManager();
        }
        H5Log.e((String) "H5WalletWrapper", (String) "h5Service == null");
        return null;
    }

    public static Context getContext() {
        return LauncherApplicationAgent.getInstance().getApplicationContext();
    }
}
