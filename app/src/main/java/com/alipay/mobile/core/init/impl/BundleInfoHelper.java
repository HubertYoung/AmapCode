package com.alipay.mobile.core.init.impl;

import com.alipay.mobile.framework.BundleContext;
import com.alipay.mobile.framework.DescriptionManager;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BundleInfoHelper {
    public static Collection<String> getBundleNames() {
        Collection quinoxBundleNames = LauncherApplicationAgent.getInstance().getBundleContext().getAllBundleNames();
        Collection metaBundleNames = DescriptionManager.getInstance().getBundleSet();
        Set bundleNames = new HashSet();
        if (quinoxBundleNames != null) {
            bundleNames.addAll(quinoxBundleNames);
        }
        if (metaBundleNames != null) {
            bundleNames.addAll(metaBundleNames);
        }
        return bundleNames;
    }

    public static Set<String> getBundlePackageNames(String bundleName) {
        BundleContext bundleContext = LauncherApplicationAgent.getInstance().getBundleContext();
        Set metaPackages = DescriptionManager.getInstance().getPackagesByBundleName(bundleName);
        Set quinoxPackages = bundleContext.findPackagesByBundleName(bundleName);
        Set packages = new HashSet();
        if (metaPackages != null) {
            packages.addAll(metaPackages);
        }
        if (quinoxPackages != null) {
            packages.addAll(quinoxPackages);
        }
        return packages;
    }
}
