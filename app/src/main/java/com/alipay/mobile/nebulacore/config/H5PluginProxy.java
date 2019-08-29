package com.alipay.mobile.nebulacore.config;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.config.H5PluginConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5PluginProxy extends H5SimplePlugin {
    public static final String TAG = "H5PluginProxy";
    private Map<String, List<ProxyInfo>> a = new ConcurrentHashMap();
    private H5PluginManager b;

    class ProxyInfo {
        public H5Plugin plugin;
        public H5PluginConfig pluginInfo;
        public boolean registered;

        ProxyInfo() {
        }
    }

    public H5PluginProxy(List<H5PluginConfig> infoList, H5PluginManager pluginManager) {
        List pluginProxyList;
        this.b = pluginManager;
        if (infoList != null && !infoList.isEmpty()) {
            for (H5PluginConfig info : infoList) {
                if (info.lazyInit) {
                    ProxyInfo proxy = new ProxyInfo();
                    proxy.registered = false;
                    proxy.plugin = null;
                    proxy.pluginInfo = info;
                    for (String event : info.eventList) {
                        if (!TextUtils.isEmpty(event)) {
                            if (!this.a.containsKey(event)) {
                                pluginProxyList = new ArrayList();
                                this.a.put(event, pluginProxyList);
                            } else {
                                pluginProxyList = this.a.get(event);
                            }
                            pluginProxyList.add(proxy);
                        }
                    }
                } else {
                    a(info, null);
                }
            }
        }
    }

    public void onPrepare(H5EventFilter filter) {
        for (String apiName : this.a.keySet()) {
            filter.addAction(apiName);
        }
    }

    public void onRelease() {
        this.a.clear();
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        if (event == null) {
            H5Log.e((String) TAG, (String) "invalid event!");
            return false;
        }
        String action = event.getAction();
        if (TextUtils.isEmpty(action)) {
            H5Log.w(TAG, "invalid event name");
            return false;
        }
        List pluginProxyList = this.a.get(action);
        if (pluginProxyList == null || pluginProxyList.isEmpty()) {
            return false;
        }
        for (int index = pluginProxyList.size() - 1; index >= 0; index--) {
            ProxyInfo proxy = (ProxyInfo) pluginProxyList.get(index);
            if (proxy.plugin == null || !proxy.registered) {
                if (proxy.plugin == null) {
                    proxy.plugin = a(proxy.pluginInfo, event);
                }
                boolean result = false;
                if (proxy.plugin != null) {
                    H5Log.d(TAG, "[" + action + "] intercept pass " + proxy.pluginInfo.className);
                    result = proxy.plugin.interceptEvent(event, context);
                    proxy.registered = result;
                }
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (event == null) {
            H5Log.e((String) TAG, (String) "invalid event!");
            return false;
        }
        String action = event.getAction();
        if (TextUtils.isEmpty(action)) {
            H5Log.w(TAG, "invalid event name");
            return false;
        }
        List pluginProxyList = this.a.get(action);
        if (pluginProxyList == null || pluginProxyList.isEmpty()) {
            return false;
        }
        for (int index = pluginProxyList.size() - 1; index >= 0; index--) {
            ProxyInfo proxy = (ProxyInfo) pluginProxyList.get(index);
            if (proxy.plugin == null || !proxy.registered) {
                if (proxy.plugin == null) {
                    proxy.plugin = a(proxy.pluginInfo, event);
                }
                boolean result = false;
                if (proxy.plugin != null) {
                    H5Log.d(TAG, "[" + action + "] handle pass " + proxy.pluginInfo.className);
                    result = proxy.plugin.handleEvent(event, context);
                    proxy.registered = true;
                }
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }

    private H5Plugin a(H5PluginConfig info, H5Event event) {
        long time = System.currentTimeMillis();
        H5Plugin plugin = null;
        String bundleName = info.bundleName;
        String clazzName = info.className;
        if (event != null && !TextUtils.isEmpty(event.getId()) && event.getId().startsWith("native_") && H5Utils.isInTinyProcess() && !TextUtils.isEmpty(bundleName)) {
            if (bundleName.contains("nebula") || bundleName.contains("liteprocess") || bundleName.contains("tinyappservice") || bundleName.contains("tinyappcommon") || clazzName.contains("H5BeeVideoPlayerPlugin")) {
                H5Log.d(TAG, "in tinyProcess event " + event.getAction() + Token.SEPARATOR + bundleName + Token.SEPARATOR + clazzName + "  can to load class ");
            } else {
                H5Log.d(TAG, "in tinyProcess event " + event.getAction() + Token.SEPARATOR + bundleName + " is not to load class ");
                return null;
            }
        }
        Class clazz = H5WalletWrapper.getClass(bundleName, clazzName, true);
        if (clazz == null) {
            H5Log.e((String) TAG, "could not find plugin class " + bundleName + ":" + clazzName);
            return null;
        }
        try {
            if (!H5Plugin.class.isAssignableFrom(clazz)) {
                return null;
            }
            plugin = (H5Plugin) clazz.newInstance();
            if (plugin == null) {
                return null;
            }
            H5Log.d(TAG, "register ext plugin " + clazzName + " elapse " + (System.currentTimeMillis() - time));
            this.b.register(plugin);
            return plugin;
        } catch (Throwable t) {
            H5Log.e(TAG, "failed to initialize plugin " + clazzName, t);
            H5LogUtil.logNebulaTech(H5LogData.seedId(TAG).param1().add("load plugin fail", null).param4().add("className", clazzName).add(LogCategory.CATEGORY_EXCEPTION, t));
        }
    }
}
