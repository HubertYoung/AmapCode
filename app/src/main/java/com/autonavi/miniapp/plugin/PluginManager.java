package com.autonavi.miniapp.plugin;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.config.H5PluginConfig;
import com.autonavi.miniapp.biz.plugin.H5AppManagePlugin;
import com.autonavi.miniapp.biz.plugin.H5FavoritePlugin;
import com.autonavi.miniapp.plugin.carowner.CarOwnerPlugin;
import com.autonavi.miniapp.plugin.feedback.FeedbackPlugin;
import com.autonavi.miniapp.plugin.lbs.H5LocationPlugin;
import com.autonavi.miniapp.plugin.log.UploadLogPlugin;
import com.autonavi.miniapp.plugin.mtop.H5MtopPlugin;
import com.autonavi.miniapp.plugin.safe.TinyAmapUserDataPlugin;
import com.autonavi.miniapp.plugin.shortcut.ShortCutPlugin;
import com.autonavi.miniapp.plugin.userinfo.UserInfoPlugin;
import java.util.Arrays;
import java.util.List;

public class PluginManager {
    private static final String BUNDLE_NAME = "android-phone-mobilesdk-framework";
    public static final String TAG = "PluginManager";
    List<? extends BasePlugin> mPlugins = null;

    static class Holder {
        static PluginManager instance = new PluginManager();

        Holder() {
        }
    }

    public static PluginManager getInstance() {
        return Holder.instance;
    }

    public PluginManager() {
        try {
            this.mPlugins = Arrays.asList(new BasePlugin[]{H5LocationPlugin.class.newInstance(), UserInfoPlugin.class.newInstance(), H5AppManagePlugin.class.newInstance(), H5FavoritePlugin.class.newInstance(), ShortCutPlugin.class.newInstance(), TinyAmapUserDataPlugin.class.newInstance(), H5MtopPlugin.class.newInstance(), FeedbackPlugin.class.newInstance(), CarOwnerPlugin.class.newInstance(), UploadLogPlugin.class.newInstance(), LoggerJsApiPlugin.class.newInstance()});
        } catch (Throwable unused) {
            LoggerFactory.getTraceLogger().info(TAG, "puglin list is null");
        }
    }

    public void initAMapPlugin(H5Service h5Service) {
        if (h5Service != null) {
            initPlugins(h5Service);
        }
    }

    private void initPlugins(H5Service h5Service) {
        if (this.mPlugins != null && h5Service != null) {
            for (BasePlugin basePlugin : this.mPlugins) {
                H5PluginConfig h5PluginConfig = new H5PluginConfig();
                h5PluginConfig.bundleName = "android-phone-mobilesdk-framework";
                h5PluginConfig.className = basePlugin.getClass().getName();
                h5PluginConfig.scope = basePlugin.getScope();
                h5PluginConfig.setEvents(basePlugin.getEvents());
                h5Service.addPluginConfig(h5PluginConfig);
            }
        }
    }
}
