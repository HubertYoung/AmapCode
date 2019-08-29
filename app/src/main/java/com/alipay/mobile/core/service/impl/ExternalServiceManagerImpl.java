package com.alipay.mobile.core.service.impl;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import com.alipay.mobile.framework.DescriptionManager;
import com.alipay.mobile.framework.FrameworkMonitor;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.MicroService;
import com.alipay.mobile.framework.service.ServiceDescription;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.alipay.mobile.framework.service.ext.ExternalServiceManager;
import com.alipay.mobile.framework.util.JSONUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ExternalServiceManagerImpl extends ExternalServiceManager {
    private Map<String, ServiceDescription> a = new ConcurrentHashMap();
    private Map<String, ExternalService> b = new ConcurrentHashMap();

    public boolean createExternalService(ServiceDescription description) {
        if (description == null) {
            return false;
        }
        String interfaceClass = description.getInterfaceClass();
        if (this.b.containsKey(interfaceClass)) {
            return true;
        }
        synchronized (description) {
            if (this.b.containsKey(interfaceClass)) {
                return true;
            }
            long start = System.currentTimeMillis();
            try {
                ExternalService externalService = (ExternalService) description.getClazz().newInstance();
                externalService.attachContext(getMicroApplicationContext());
                externalService.create(null);
                this.b.put(interfaceClass, externalService);
                TraceLogger.w((String) "ExternalServiceManager", "createExternalService: " + interfaceClass + ", cost " + (System.currentTimeMillis() - start) + " ms");
                Log.i("mytest", "createExternalService: " + interfaceClass + ", cost " + (System.currentTimeMillis() - start) + " ms", new RuntimeException());
                return true;
            } catch (Throwable e) {
                TraceLogger.e((String) "ExternalServiceManager", e);
                FrameworkMonitor.getInstance(null).handleDescriptionInitFail(description, e);
                return false;
            }
        }
    }

    public void registerExternalServiceOnly(ServiceDescription description) {
        if (description != null && !this.a.containsKey(description.getInterfaceClass())) {
            this.a.put(description.getInterfaceClass(), description);
        }
    }

    public void registerExternalService(ServiceDescription description) {
        if (description == null || this.a.containsKey(description.getInterfaceClass())) {
            return;
        }
        if (description.isLazy()) {
            registerExternalServiceOnly(description);
        } else if (createExternalService(description)) {
            registerExternalServiceOnly(description);
        }
    }

    public ExternalService getExternalService(String clazz) {
        ExternalService externalService = null;
        try {
            externalService = this.b.get(clazz);
            if (externalService == null) {
                ServiceDescription description = this.a.get(clazz);
                if (description == null) {
                    String bundleName = DescriptionManager.getInstance().getBundleNameByServiceName(clazz);
                    if (DescriptionManager.getInstance().isLazyBundle(bundleName)) {
                        LauncherApplicationAgent.getInstance().getBundleContext().loadBundle(bundleName);
                        ExternalService externalService2 = this.b.get(clazz);
                        if (externalService2 != null) {
                            return externalService2;
                        }
                    }
                    description = this.a.get(clazz);
                }
                if (description == null) {
                    List serviceDescs = DescriptionManager.getInstance().findServiceDescription(clazz);
                    if (serviceDescs.size() > 0) {
                        description = serviceDescs.get(0);
                    }
                }
                if (description == null) {
                    TraceLogger.e((String) "ExternalServiceManager", "[" + clazz + "] is not registered.");
                    return null;
                }
                if (!createExternalService(description)) {
                    TraceLogger.e((String) "ExternalServiceManager", "Failed to create service :" + description);
                }
                externalService = this.b.get(clazz);
            }
        } catch (Throwable e) {
            TraceLogger.d((String) "ExternalServiceManager", e);
        }
        return externalService;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle params) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle params) {
    }

    public void saveState(Editor editor) {
        editor.putString("_externalServiceClass_", JSONUtil.set2Json(this.b.keySet())).apply();
        for (Object object : this.b.values()) {
            if (object instanceof MicroService) {
                ((MicroService) object).saveState(editor);
            }
        }
    }

    public void restoreState(SharedPreferences preferences) {
        String externalServiceClass = preferences.getString("_externalServiceClass_", null);
        if (externalServiceClass != null) {
            Set savededExternalServices = JSONUtil.json2Set(externalServiceClass);
            if (savededExternalServices != null) {
                Set<String> classNames = new HashSet<>();
                for (String className : savededExternalServices) {
                    if (!this.b.containsKey(className)) {
                        classNames.add(className);
                    }
                }
                for (String clazz : classNames) {
                    getExternalService(clazz);
                }
            } else {
                return;
            }
        }
        for (Object object : this.b.values()) {
            if (object instanceof MicroService) {
                ((MicroService) object).restoreState(preferences);
            }
        }
    }
}
