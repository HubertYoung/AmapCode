package com.alipay.mobile.nebulacore.api;

import android.content.Context;
import android.support.v4.app.Fragment;
import com.alipay.mobile.h5container.api.H5BaseFragment;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5Context;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageReadyListener;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5UcReadyCallBack;
import com.alipay.mobile.h5container.api.H5WebDriverHelper;
import com.alipay.mobile.nebula.appcenter.common.NebulaCommonManager;
import com.alipay.mobile.nebula.appcenter.listen.NebulaAppManager;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.config.H5PluginConfig;
import com.alipay.mobile.nebula.dev.H5BugMeManager;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.provider.H5TaskScheduleProvider;
import java.util.Stack;

public interface NebulaService extends H5CoreNode {
    void addPluginConfig(H5PluginConfig h5PluginConfig);

    boolean addSession(H5Session h5Session);

    H5Page createPage(H5Context h5Context, H5Bundle h5Bundle);

    void createPageAsync(H5Context h5Context, H5Bundle h5Bundle, H5PageReadyListener h5PageReadyListener);

    boolean exitService();

    void fireUrgentUcInit();

    H5BugMeManager getBugMeManager();

    H5Page getH5PageByViewId(int i);

    H5TaskScheduleProvider getH5TaskScheduleProvider();

    NebulaAppManager getNebulaAppManager();

    NebulaCommonManager getNebulaCommonManager();

    H5ProviderManager getProviderManager();

    H5Session getSession(String str);

    H5Session getSession(String str, boolean z);

    H5Session getSessionByWorkerId(String str);

    Stack<H5Session> getSessions();

    H5BaseFragment getTopH5BaseFragment();

    H5BaseFragment getTopH5BaseFragmentByViewId(int i);

    H5BaseFragment getTopH5BaseFragmentByWorkerId(String str);

    Fragment getTopH5Fragment();

    H5Page getTopH5Page();

    H5Page getTopH5PageForTiny();

    H5Session getTopSession();

    H5WebDriverHelper getWebDriverHelper();

    void initServicePlugin();

    boolean isAliDomain(String str);

    void onCreate(Context context);

    int parseRNPkg(String str);

    boolean permitLocation(String str);

    void prepareRNApp(String str, H5UpdateAppCallback h5UpdateAppCallback);

    boolean removeSession(String str);

    boolean sendEvent(H5Event h5Event);

    void setH5TaskScheduleProvider(H5TaskScheduleProvider h5TaskScheduleProvider);

    void setWebDriverHelper(H5WebDriverHelper h5WebDriverHelper);

    boolean startPage(H5Context h5Context, H5Bundle h5Bundle);

    void ucIsReady(H5UcReadyCallBack h5UcReadyCallBack);
}
