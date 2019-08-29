package com.alipay.mobile.tinyappcommon.a;

import android.os.Bundle;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.liteprocess.LiteProcessApi;
import com.alipay.mobile.liteprocess.ipc.IpcMsgServer;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.config.TinyAppConfig;
import com.alipay.mobile.tinyappcommon.storage.H5SharedPreferenceStorage;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;

/* compiled from: IPCMainProcessServiceImpl */
public class b implements a {
    private static final String a = b.class.getSimpleName();

    /* compiled from: IPCMainProcessServiceImpl */
    private static class a {
        public static a a = new b();
    }

    public b() {
        if (LiteProcessApi.isMainProcess()) {
            WalletTinyappUtils.runOnMainThread(new Runnable() {
                public final void run() {
                    if (LiteProcessApi.isMainProcess()) {
                        IpcMsgServer.registerReqBizHandler("TINY_APP_BIZ", e.a());
                    }
                }
            });
            ConfigService configService = (ConfigService) H5Utils.findServiceByInterface(ConfigService.class.getName());
            H5Log.d("IPCMainProcessServiceImpl", "configService = " + configService);
            if (configService != null) {
                configService.addConfigChangeListener(TinyAppConfig.getInstance());
            }
        }
    }

    public static a a() {
        return a.a;
    }

    public final void a(String appId, boolean visible) {
        H5SharedPreferenceStorage.getInstance().setVConsoleVisible(appId, visible);
    }

    public final void b(String appId, boolean visible) {
        H5SharedPreferenceStorage.getInstance().setPerformancePanelVisible(appId, visible);
    }

    public final int a(String appId, String userId) {
        return H5SharedPreferenceStorage.getInstance().getDefaultCurrentStorageSize(appId, userId);
    }

    public final void a(String key, int value, boolean need2SyncAllLite) {
        H5SharedPreferenceStorage.getInstance().putInt(key, value, need2SyncAllLite);
        if (need2SyncAllLite) {
            Bundle bundle = new Bundle();
            bundle.putInt(key, value);
            c.a(bundle);
        }
    }

    public final void a(String key) {
        H5SharedPreferenceStorage.getInstance().remove(key);
    }

    public final void a(String sourceAppId, String targetAppId, JSONObject params, boolean closeCurrentApp) {
        if (closeCurrentApp) {
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().finishApp(sourceAppId, sourceAppId, null);
        }
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(sourceAppId, targetAppId, H5ParamParser.parse(H5Utils.toBundle(null, params), false));
    }
}
