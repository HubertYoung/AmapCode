package com.mpaas.nebula.service;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.IApplicationInstaller;
import com.alipay.mobile.framework.loading.LoadingView;
import com.alipay.mobile.framework.loading.LoadingView.Factory;
import com.alipay.mobile.h5container.service.H5ConfigService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.provider.H5AppBizRpcProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.providermanager.H5ProviderConfig;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.mpaas.nebula.adapter.api.MPAppBizRpcImpl;
import com.mpaas.nebula.adapter.api.MPaaSH5AppProvider;
import com.mpaas.nebula.config.H5StartAppAdviceImpl;
import com.mpaas.nebula.provider.H5AlipayAppProvider;
import com.mpaas.nebula.provider.H5BaseProviderInfo;
import com.mpaas.nebula.util.H5BizPluginList;
import com.mpaas.nebula.util.H5EmbedViewConfigList;
import com.mpaas.nebula.util.Misc;
import com.mpaas.nebula.view.DefaultLoadingView;
import java.util.Map;

public class H5ConfigServiceImpl extends H5ConfigService {
    private static boolean a = false;

    public static class MyContextWrapper extends ContextThemeWrapper {
        private Resources a;

        public MyContextWrapper(Context context) {
            super(context, -1);
        }

        public Resources getResources() {
            if (this.a == null) {
                this.a = LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("com-mpaas-nebula-adapter-mpaasnebulaadapter");
            }
            return this.a;
        }
    }

    public Map<String, H5ProviderConfig> getProviderInfoMap() {
        if ("yes".equals(Misc.getStringValueFromMetaData(getMicroApplicationContext().getApplicationContext(), "use_nebula_mng"))) {
            LoggerFactory.getTraceLogger().info("MPH5ConfigServiceImpl", "use nebulamng.");
            H5BaseProviderInfo.providerInfoMap.put(H5AppProvider.class.getName(), new H5ProviderConfig("com-mpaas-nebula-adapter-mpaasnebulaadapter", H5AlipayAppProvider.class.getName()));
        } else {
            LoggerFactory.getTraceLogger().info("MPH5ConfigServiceImpl", "use mpaas.");
            H5BaseProviderInfo.providerInfoMap.put(H5AppProvider.class.getName(), new H5ProviderConfig("com-mpaas-nebula-adapter-mpaasnebulaadapter", MPaaSH5AppProvider.class.getName()));
            H5BaseProviderInfo.providerInfoMap.put(H5AppBizRpcProvider.class.getName(), new H5ProviderConfig("com-mpaas-nebula-adapter-mpaasnebulaadapter", MPAppBizRpcImpl.class.getName()));
        }
        return H5BaseProviderInfo.providerInfoMap;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        H5Log.d("MPH5ConfigServiceImpl", "onCreate");
        long time = System.currentTimeMillis();
        H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
        H5Log.d("MPH5ConfigServiceImpl", "H5Biz register plugin.");
        if (h5Service != null) {
            H5Log.d("MPH5ConfigServiceImpl", "register start.");
            h5Service.addH5PluginConfigList(H5BizPluginList.bizPluginList);
            try {
                h5Service.addEmbedViewConfig(H5EmbedViewConfigList.embedViewList);
            } catch (Throwable throwable) {
                H5Log.e((String) "MPH5ConfigServiceImpl", throwable);
            }
            FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_DOSTARTAPP, (Advice) new H5StartAppAdviceImpl());
            IApplicationInstaller applicationInstaller = h5Service.H5IApplicationInstaller();
            if (applicationInstaller != null) {
                LauncherApplicationAgent.getInstance().getMicroApplicationContext().registerApplicationInstaller(applicationInstaller);
            } else {
                H5Log.e((String) "MPH5ConfigServiceImpl", (String) "No H5Application installer is specified");
            }
        }
        H5Log.d("MPH5ConfigServiceImpl", "H5Biz register delta:" + (System.currentTimeMillis() - time));
        try {
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().getLoadingPageManager().setDefaultLoadingViewFactory(new Factory() {
                public LoadingView createLoadingView(String sourceId, String targetId, Bundle params) {
                    return new DefaultLoadingView(new MyContextWrapper(H5Utils.getContext()));
                }
            });
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) "MPH5ConfigServiceImpl", e);
        }
        if (!"NO".equalsIgnoreCase(H5Utils.getConfigByConfigService("h5_disableConfigServiceOpt"))) {
            addExternalPlugins();
        }
    }

    public void addExternalPlugins() {
        if (!a) {
            a = true;
            long time = System.currentTimeMillis();
            H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
            H5Log.d("MPH5ConfigServiceImpl", "H5Biz register plugin.");
            if (h5Service != null) {
                H5Log.d("MPH5ConfigServiceImpl", "register start.");
                h5Service.addH5PluginConfigList(H5BizPluginList.bizPluginList);
                try {
                    h5Service.addEmbedViewConfig(H5EmbedViewConfigList.embedViewList);
                    h5Service.addNewEmbedViewConfig(H5EmbedViewConfigList.newEmbedViewList);
                } catch (Throwable throwable) {
                    H5Log.e((String) "MPH5ConfigServiceImpl", throwable);
                }
            }
            H5Log.d("MPH5ConfigServiceImpl", "H5Biz register delta:" + (System.currentTimeMillis() - time));
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
