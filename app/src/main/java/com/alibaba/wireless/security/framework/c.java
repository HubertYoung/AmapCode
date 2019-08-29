package com.alibaba.wireless.security.framework;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import com.alibaba.wireless.security.framework.utils.a;
import com.alibaba.wireless.security.framework.utils.d;
import com.alibaba.wireless.security.open.initialize.ISecurityGuardPlugin;

public class c implements ISGPluginInfo {
    private String a;
    private String b;
    private ClassLoader c;
    private AssetManager d;
    private a e;
    private ISecurityGuardPlugin f;
    private ISGPluginManager g;

    public c(String str, ISGPluginManager iSGPluginManager, String str2, ClassLoader classLoader, a aVar, ISecurityGuardPlugin iSecurityGuardPlugin) {
        this.a = str;
        this.g = iSGPluginManager;
        this.b = str2;
        this.c = classLoader;
        this.e = aVar;
        this.f = iSecurityGuardPlugin;
    }

    public String a(String str) {
        return this.e.a != null ? this.e.a.applicationInfo.metaData.getString(str) : this.e.a(str);
    }

    public AssetManager getAssetManager() {
        if (this.d != null) {
            return this.d;
        }
        try {
            AssetManager newInstance = AssetManager.class.newInstance();
            d.a((Object) newInstance).a((String) "addAssetPath", this.a);
            this.d = newInstance;
            return this.d;
        } catch (Exception e2) {
            a.a("", e2);
            return null;
        }
    }

    public ClassLoader getClassLoader() {
        return this.c;
    }

    public PackageInfo getPackageInfo() {
        return this.e.a;
    }

    public String getPluginName() {
        return this.b;
    }

    public String getPluginPath() {
        return this.a;
    }

    public ISecurityGuardPlugin getSGPluginEntry() {
        return this.f;
    }

    public ISGPluginManager getSGPluginManager() {
        return this.g;
    }

    public String getVersion() {
        return this.e.a != null ? this.e.a.versionName : this.e.a("version");
    }
}
