package com.alibaba.wireless.security.open.initialize;

import android.content.Context;
import com.alibaba.wireless.security.framework.IRouterComponent;
import com.alibaba.wireless.security.framework.ISGPluginInfo;
import com.alibaba.wireless.security.open.SecException;

public interface ISecurityGuardPlugin {
    Context getContext();

    <T> T getInterface(Class<T> cls);

    IRouterComponent getRouter();

    ISGPluginInfo getSGPluginInfo();

    IRouterComponent onPluginLoaded(Context context, IRouterComponent iRouterComponent, ISGPluginInfo iSGPluginInfo, String str, Object... objArr) throws SecException;
}
