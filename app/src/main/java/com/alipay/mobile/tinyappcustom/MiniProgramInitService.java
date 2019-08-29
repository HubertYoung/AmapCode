package com.alipay.mobile.tinyappcustom;

import com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthServiceManager;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.base.config.ConfigService.ConfigChangeListener;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.remotedebug.TinyAppRemoteDebugInterceptorImpl;
import com.alipay.mobile.tinyappcommon.storage.H5SharedPreferenceStorage;
import com.alipay.mobile.tinyappcustom.biz.auth.TinyAppAuthBridge;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptorManager;
import java.util.ArrayList;
import java.util.List;

public class MiniProgramInitService {
    private static final String CONFIG_AMAP_MINI_APP_JSAPI_WHITELIST = "amap_mini_app_jsapi_whitelist";
    private static final String TAG = MiniProgramInitService.class.getSimpleName();
    private static MiniProgramInitService sMe;
    private H5Plugin mInterceptEventPlugin;

    private MiniProgramInitService() {
    }

    public static MiniProgramInitService get() {
        if (sMe == null) {
            synchronized (MiniProgramInitService.class) {
                if (sMe == null) {
                    sMe = new MiniProgramInitService();
                }
            }
        }
        return sMe;
    }

    public void init() {
        try {
            H5Log.d(TAG, "init...");
            AccountOAuthServiceManager.getInstance().setOAuthService(TinyAppAuthBridge.get());
            addConfigChangeListener();
            registerInterceptPlugin();
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public final void run() {
                    H5SharedPreferenceStorage.getInstance().initLoadStorage();
                }
            });
            TinyAppRemoteDebugInterceptorManager.get().injectRemoteDebugInterceptor(TinyAppRemoteDebugInterceptorImpl.getInstance());
        } catch (Throwable e) {
            H5Log.e(TAG, "init...e=" + e);
        }
    }

    private void addConfigChangeListener() {
        ConfigService configService = (ConfigService) H5Utils.findServiceByInterface(ConfigService.class.getName());
        if (configService != null) {
            configService.addConfigChangeListener(new ConfigChangeListener() {
                public final List<String> getKeys() {
                    ArrayList keyList = new ArrayList();
                    keyList.add(MiniProgramInitService.CONFIG_AMAP_MINI_APP_JSAPI_WHITELIST);
                    return keyList;
                }

                public final void onConfigChange(String key, String value) {
                    if (MiniProgramInitService.CONFIG_AMAP_MINI_APP_JSAPI_WHITELIST.equals(key)) {
                        MiniProgramInitService.this.reregisterInterceptPlugin();
                    }
                }
            });
        }
    }

    private void registerInterceptPlugin() {
        try {
            this.mInterceptEventPlugin = (H5Plugin) Class.forName("com.autonavi.miniapp.plugin.H5InterceptEventPlugin").newInstance();
            ((H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(H5Service.class.getName())).getPluginManager().register(this.mInterceptEventPlugin);
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error(TAG, "register intercept plugin error", e);
        }
    }

    /* access modifiers changed from: private */
    public void reregisterInterceptPlugin() {
        if (this.mInterceptEventPlugin != null) {
            ((H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(H5Service.class.getName())).getPluginManager().reregisterFront(this.mInterceptEventPlugin);
        }
    }
}
