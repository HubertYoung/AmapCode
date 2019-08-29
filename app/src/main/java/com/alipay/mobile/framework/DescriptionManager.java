package com.alipay.mobile.framework;

import android.content.Context;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.framework.msg.BroadcastReceiverDescription;
import com.alipay.mobile.framework.pipeline.ValveDescription;
import com.alipay.mobile.framework.service.ServiceDescription;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class DescriptionManager {
    private static DescriptionManager a;

    public abstract void addDescription(String str, MicroDescription<?> microDescription);

    public abstract void addDescriptionsFromMetaInfo(String str, BaseMetaInfo baseMetaInfo);

    public abstract List<ApplicationDescription> findApplicationDescription(String str);

    public abstract List<BroadcastReceiverDescription> findBroadcastReceiverDescription(String str);

    public abstract List<ServiceDescription> findServiceDescription(String str);

    public abstract List<ValveDescription> findValveDescription(String str);

    public abstract String getBundleNameByAppId(String str);

    public abstract String getBundleNameByServiceName(String str);

    public abstract Set<String> getBundleSet();

    public abstract List<MicroDescription<?>> getDescription(String str, boolean z, boolean z2, boolean z3, boolean z4);

    public abstract Map<String, Set<String>> getLazyBundles();

    public abstract Set<String> getPackagesByBundleName(String str);

    public abstract boolean isLazyBundle(String str);

    public abstract void preload();

    public abstract void updateDescriptionsFromMetaInfoCfg(Collection<String> collection, Collection<String> collection2, Map<String, String> map, boolean z);

    public abstract void updateDescriptionsFromMetaInfoCfg(Collection<String> collection, Collection<String> collection2, boolean z);

    public static DescriptionManager getInstance() {
        if (a != null) {
            return a;
        }
        throw new IllegalStateException("DescriptionManager Instance is not created");
    }

    public static DescriptionManager createInstance(Context context) {
        if (a == null) {
            try {
                synchronized (DescriptionManager.class) {
                    DescriptionManager tmp = (DescriptionManager) DescriptionManager.class.getClassLoader().loadClass("com.alipay.mobile.core.impl.DescriptionManagerImpl").getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
                    if (a == null) {
                        a = tmp;
                    }
                }
            } catch (Throwable e) {
                TraceLogger.e((String) "DescriptionManager", e);
            }
        }
        return a;
    }
}
