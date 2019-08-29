package com.alipay.mobile.core.service.impl;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.mobile.core.ServiceManager;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.service.CommonService;
import com.alipay.mobile.framework.service.MicroService;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceManagerImpl implements ServiceManager {
    private Map<String, Object> a = new ConcurrentHashMap();
    private Map<String, String> b = new ConcurrentHashMap();

    public <T> T findServiceByInterface(String className) {
        if (this.a.containsKey(className)) {
            return this.a.get(className);
        }
        if (!this.b.containsKey(className)) {
            return null;
        }
        String defaultClassName = this.b.get(className);
        if (TextUtils.isEmpty(defaultClassName)) {
            TraceLogger.e((String) "ServiceManagerImpl", "ServiceManagerImpl.findServiceByInterface(" + className + "), but defaultClassName=[" + defaultClassName + "] return null");
            return null;
        }
        synchronized (defaultClassName) {
            if (this.a.containsKey(className)) {
                T t = this.a.get(className);
                return t;
            }
            long startTime = System.currentTimeMillis();
            MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
            CommonService service = null;
            try {
                service = (CommonService) microApplicationContext.getApplicationContext().getClassLoader().loadClass(defaultClassName).newInstance();
            } catch (Throwable e) {
                TraceLogger.w((String) "ServiceManagerImpl", e);
            }
            if (service != null) {
                service.attachContext(microApplicationContext);
                try {
                    service.create(null);
                    this.a.put(className, service);
                } catch (Throwable e2) {
                    TraceLogger.w((String) "ServiceManagerImpl", e2);
                }
            }
            TraceLogger.w((String) "ServiceManagerImpl", "createService: " + className + ", cost " + (System.currentTimeMillis() - startTime) + " ms");
            return service;
        }
    }

    public <T> boolean registerService(String className, T service) {
        if (service instanceof MicroService) {
            if (this.a.put(className, service) == null) {
                return true;
            }
            return false;
        } else if (service instanceof String) {
            if (this.b.put(className, (String) service) != null) {
                return false;
            }
            return true;
        } else if (this.a.put(className, service) != null) {
            return false;
        } else {
            return true;
        }
    }

    public void onDestroyService(MicroService microService) {
        if (microService != null) {
            for (String key : this.a.keySet()) {
                Object obj = this.a.get(key);
                if ((obj instanceof MicroService) && ((MicroService) obj) == microService) {
                    this.a.remove(key);
                    return;
                }
            }
        }
    }

    public void attachContext(MicroApplicationContext applicationContext) {
    }

    public void exit() {
        Object[] array;
        for (Object object : this.a.values().toArray()) {
            if (object instanceof MicroService) {
                MicroService service = (MicroService) object;
                if (service.isActivated()) {
                    service.destroy(null);
                }
            }
        }
        this.a.clear();
        this.b.clear();
    }

    public void saveState(Editor editor) {
        for (Object object : this.a.values()) {
            if (object instanceof MicroService) {
                ((MicroService) object).saveState(editor);
            }
        }
    }

    public void restoreState(SharedPreferences preferences) {
        for (Object object : this.a.values()) {
            if (object instanceof MicroService) {
                ((MicroService) object).restoreState(preferences);
            }
        }
    }

    public <T> T unregisterService(String interfaceName) {
        this.b.remove(interfaceName);
        return this.a.remove(interfaceName);
    }
}
