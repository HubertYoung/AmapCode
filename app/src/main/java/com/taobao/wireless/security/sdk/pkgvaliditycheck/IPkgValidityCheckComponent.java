package com.taobao.wireless.security.sdk.pkgvaliditycheck;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.taobao.wireless.security.sdk.IComponent;

@InterfacePluginInfo(pluginName = "misc")
public interface IPkgValidityCheckComponent extends IComponent {
    public static final int VALID_TYPE_PACKAGE = 0;
    public static final int VALID_TYPE_PATCH = 1;

    int checkEnvAndFiles(String... strArr);

    boolean generateValidPackage(String str, String str2, String str3);

    String getDexHash(String str, String str2, int i);

    boolean isPackageValid(String str);

    boolean isPackageValidEx(String str, String str2, int i);
}
