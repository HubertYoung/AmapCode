package com.ant.phone.xmedia.config;

import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.android.phone.falcon.ar.render.cloudconfig.DeviceConfig;
import com.alipay.android.phone.falcon.util.log.LogUtil;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.googlecode.androidannotations.api.BackgroundExecutor;

public class ConfigManager {
    private static ConfigManager b;
    private String[] a = {"XMEDIA_NEON_INCOMPATIBLE"};
    private XMediaNeonConfig c = null;
    private LocalBroadcastManager d = LocalBroadcastManager.getInstance(this.f);
    private b e;
    private Context f = LauncherApplicationAgent.getInstance().getApplicationContext();

    public static ConfigManager a() {
        if (b == null) {
            synchronized (ConfigManager.class) {
                try {
                    if (b == null) {
                        b = new ConfigManager();
                    }
                }
            }
        }
        return b;
    }

    public final synchronized void a(boolean bBackground) {
        LogUtil.logInfo("ConfigManager", "updateConfig" + Thread.currentThread().getName());
        if (bBackground) {
            BackgroundExecutor.execute((Runnable) new a(this));
        } else {
            b();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        String[] strArr;
        try {
            ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
            if (configService != null) {
                for (String key : this.a) {
                    String configValue = configService.getConfig(key);
                    if (configValue != null) {
                        DeviceConfigUtils.a(key, configValue);
                    }
                    DeviceConfigUtils.b(key);
                }
            }
        } catch (Throwable e2) {
            LogUtil.logError("ConfigManager", "updateConfigInner", e2);
        }
    }

    public final XMediaNeonConfig a(String configKey) {
        DeviceConfig config = DeviceConfigUtils.a(configKey);
        this.c = null;
        this.c = new XMediaNeonConfig();
        XMediaNeonConfig.a(this.c, config);
        return this.c;
    }

    public ConfigManager() {
        if (this.d != null) {
            this.e = new b(this);
            this.d.registerReceiver(this.e, new IntentFilter("com.alipay.mobile.client.CONFIG_CHANGE"));
        }
    }
}
