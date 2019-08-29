package com.alipay.mobile.h5container.service;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.app.IApplicationInstaller;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BaseFragment;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageReadyListener;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5UcReadyCallBack;
import com.alipay.mobile.h5container.api.H5WebDriverHelper;
import com.alipay.mobile.nebula.appcenter.common.NebulaCommonManager;
import com.alipay.mobile.nebula.appcenter.listen.NebulaAppManager;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.config.H5EmbedViewConfig;
import com.alipay.mobile.nebula.config.H5PluginConfig;
import com.alipay.mobile.nebula.dev.H5BugMeManager;
import com.alipay.mobile.nebula.newembedview.H5NewEmbedViewConfig;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.wallet.H5ExternalService;
import com.alipay.mobileappconfig.core.model.hybirdPB.PackInfoReq;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public abstract class H5Service extends H5ExternalService {
    public static final String H5APP_ENGINE_TYPE = "H5App";

    public abstract IApplicationInstaller H5IApplicationInstaller();

    public abstract void addEmbedViewConfig(H5EmbedViewConfig h5EmbedViewConfig);

    public abstract void addEmbedViewConfig(List<H5EmbedViewConfig> list);

    public abstract void addH5PluginConfigList(List<H5PluginConfig> list);

    public abstract void addNewEmbedViewConfig(H5NewEmbedViewConfig h5NewEmbedViewConfig);

    public abstract void addNewEmbedViewConfig(List<H5NewEmbedViewConfig> list);

    public abstract void addPluginConfig(H5PluginConfig h5PluginConfig);

    public abstract void clearServiceWorker(String str);

    public abstract void clearServiceWorkerSync(String str, H5CallBack h5CallBack);

    public abstract H5Page createPage(Activity activity, H5Bundle h5Bundle);

    public abstract void createPageAsync(Activity activity, H5Bundle h5Bundle, H5PageReadyListener h5PageReadyListener);

    public abstract H5Plugin createPlugin(String str, H5Page h5Page, H5PluginManager h5PluginManager);

    @Deprecated
    public abstract H5Plugin createPlugin(String str, H5PluginManager h5PluginManager);

    public abstract H5PluginManager createPluginManager(H5CoreNode h5CoreNode);

    public abstract PackInfoReq generatePackInfoReq(List<String> list);

    public abstract H5BugMeManager getBugMeManager();

    public abstract byte[] getH5GlobalDegradePkg(String str);

    public abstract H5Page getH5PageByViewId(int i);

    public abstract H5CoreNode getItsOwnNode();

    public abstract NebulaAppManager getNebulaAppManager();

    public abstract NebulaCommonManager getNebulaCommonManager();

    public abstract H5PluginManager getPluginManager();

    public abstract Class[] getProcessH5Activity();

    public abstract Class[] getProcessH5TransActivity();

    public abstract H5ProviderManager getProviderManager();

    public abstract H5Session getSessionByWorkerId(String str);

    public abstract Stack<H5Session> getSessions();

    public abstract String getSharedData(String str);

    public abstract H5BaseFragment getTopH5BaseFragment();

    public abstract H5BaseFragment getTopH5BaseFragmentByViewId(int i);

    public abstract H5BaseFragment getTopH5BaseFragmentByWorkerId(String str);

    public abstract Fragment getTopH5Fragment();

    public abstract H5Page getTopH5Page();

    public abstract H5Page getTopH5PageForTiny();

    public abstract H5Session getTopSession();

    public abstract Runnable getUcInitTask();

    public abstract H5WebDriverHelper getWebDriverHelper();

    public abstract void initServicePlugin();

    public abstract boolean isAliDomain(String str);

    public abstract int parseRNPkg(String str);

    public abstract boolean permitLocation(String str);

    public abstract void preLoadInTinyProcess();

    public abstract void prepareApp(String str, String str2, H5UpdateAppCallback h5UpdateAppCallback);

    @Deprecated
    public abstract void prepareRNApp(String str, H5UpdateAppCallback h5UpdateAppCallback);

    public abstract void removeSharedData(String str);

    public abstract void savePackJson(String str);

    public abstract boolean sendEvent(H5Event h5Event);

    public abstract boolean sendEvent(H5Event h5Event, H5BridgeContext h5BridgeContext);

    public abstract void sendServiceWorkerPushMessage(HashMap<String, String> hashMap);

    public abstract void sendServiceWorkerPushMessage(HashMap<String, String> hashMap, H5CallBack h5CallBack);

    public abstract void sendToWebFromMainProcess(String str, String str2, JSONObject jSONObject);

    public abstract void setSharedData(String str, String str2);

    public abstract void setWebDriverHelper(H5WebDriverHelper h5WebDriverHelper);

    public abstract void startPage(MicroApplication microApplication, H5Bundle h5Bundle);

    public abstract void startPageFromActivity(Activity activity, H5Bundle h5Bundle);

    @Deprecated
    public abstract void startWebActivity(MicroApplication microApplication, Bundle bundle);

    @Deprecated
    public abstract void startWebActivity(MicroApplication microApplication, Bundle bundle, List<Object> list);

    public abstract void ucIsReady(H5UcReadyCallBack h5UcReadyCallBack);
}
