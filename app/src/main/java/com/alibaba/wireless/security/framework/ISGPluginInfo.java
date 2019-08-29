package com.alibaba.wireless.security.framework;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import com.alibaba.wireless.security.open.initialize.ISecurityGuardPlugin;

public interface ISGPluginInfo {
    AssetManager getAssetManager();

    ClassLoader getClassLoader();

    PackageInfo getPackageInfo();

    String getPluginName();

    String getPluginPath();

    ISecurityGuardPlugin getSGPluginEntry();

    ISGPluginManager getSGPluginManager();

    String getVersion();
}
