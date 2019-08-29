package com.alibaba.wireless.security.open.pkgvaliditycheck;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

@InterfacePluginInfo(pluginName = "misc")
public interface IPkgValidityCheckComponent extends IComponent {
    int checkEnvAndFiles(String... strArr) throws SecException;
}
