package com.alipay.android.nebulaapp;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.accountopenauth.IAccountOAuthCallback;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.aliautologin.PreLoadConfigModel;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.service.InsideOperationService;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.info.AppInfo;
import com.alipay.mobile.common.info.DeviceInfo;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.quinoxless.QuinoxlessFramework;
import com.alipay.mobile.framework.quinoxless.QuinoxlessFramework.OnInitListener;
import com.alipay.mobile.framework.service.LBSLocationManagerService;
import com.alipay.mobile.framework.service.ServiceDescription;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.inside.h5.insideh5adapter.IInsideH5Service;
import com.alipay.mobile.inside.h5.insideh5adapter.InsideH5ServiceManager;
import com.alipay.mobile.monitor.api.ClientMonitor;
import com.alipay.mobile.monitor.api.MonitorFactory;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5UaProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.util.H5NebulaUtil;
import com.alipay.mobile.tinyappcommon.api.TinyAppFavoriteService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.api.TinyAppShareInterface;
import com.alipay.mobile.tinyappcustom.api.TinyAppAuthCallback;
import com.alipay.mobile.tinyappcustom.api.TinyAppAuthManager;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.a;
import com.autonavi.miniapp.biz.service.TinyAppFavoriteServiceImpl;
import com.autonavi.miniapp.monitor.api.AmapClientMonitor;
import com.autonavi.miniapp.plugin.PluginManager;
import com.autonavi.miniapp.plugin.lbs.AdapterLBSLocationManagerService;
import com.autonavi.miniapp.plugin.map.route.MiniAppTextureMapHelper;
import com.autonavi.miniapp.plugin.mtop.MtopService;
import com.autonavi.miniapp.plugin.mtop.MtopServiceImpl;
import com.autonavi.miniapp.plugin.router.MiniAppRouter;
import com.autonavi.miniapp.plugin.share.MiniAppShareUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MiniAppInitHelper {
    static final String TAG = "MiniAppInitHelper";
    private static MiniAppInitHelper instance;
    private boolean isInited = false;
    private TinyAppShareInterface mShareInterface = new TinyAppShareInterface() {
        public void startShare(String str, String str2, String str3, String str4, String str5, String str6, H5Page h5Page, Context context, H5BridgeContext h5BridgeContext) {
            MiniAppShareUtil.startShare(MiniAppShareUtil.createShareUrl(MiniAppRouter.convertAlipaySchemeToAmapScheme(str2)), str3, str4, str5, TextUtils.isEmpty(str6) ? MiniAppUtil.getMiniAppLogo(str) : str6, h5Page, context, h5BridgeContext);
        }
    };
    private TinyAppFavoriteService mTinyAppFavoriteService = TinyAppFavoriteServiceImpl.getInstance();

    private void presetBizApp(Application application) {
    }

    private MiniAppInitHelper() {
    }

    public static synchronized MiniAppInitHelper getInstance() {
        MiniAppInitHelper miniAppInitHelper;
        synchronized (MiniAppInitHelper.class) {
            try {
                if (instance == null) {
                    instance = new MiniAppInitHelper();
                }
                miniAppInitHelper = instance;
            }
        }
        return miniAppInitHelper;
    }

    public void setupMiniAppEnvironment() {
        AMapAppGlobal.getApplication().registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            public void onActivityPaused(Activity activity) {
            }

            public void onActivityResumed(Activity activity) {
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            public void onActivityStarted(Activity activity) {
            }

            public void onActivityStopped(Activity activity) {
            }

            public void onActivityCreated(Activity activity, Bundle bundle) {
                if ("com.alipay.mobile.nebulacore.ui.H5MainProcTinyActivity".equals(activity.getComponentName().getClassName())) {
                    MiniAppUtil.onH5ActivityCreated(activity);
                }
            }

            public void onActivityDestroyed(Activity activity) {
                if ("com.alipay.mobile.nebulacore.ui.H5MainProcTinyActivity".equals(activity.getComponentName().getClassName())) {
                    MiniAppUtil.onH5ActivityDestroyed(activity);
                }
            }
        });
        closeAndroidPDialog();
        getInstance().setupQuinoxlessFramework(AMapAppGlobal.getApplication());
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a((a) new a() {
                public void openH5Page(Bundle bundle) {
                    MiniAppUtil.openH5Page(bundle);
                }
            });
        }
    }

    public void initMiniApp() {
        if (this.isInited) {
            AMapLog.debug("infoservice.miniapp", TAG, "initMiniApp(), isInitied:true");
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        long currentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
        Application application = AMapAppGlobal.getApplication();
        beforeQuinoxlessFrameworkInit(application);
        QuinoxlessFramework.init();
        afterQuinoxlessFrameworkInit(application);
        LoggerFactory.getLogContext().notifyClientEvent(LogContext.CLIENT_ENVENT_CLIENTLAUNCH, null);
        StringBuilder sb = new StringBuilder("initMiniApp, clock cost: ");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        sb.append(", thread cost: ");
        sb.append(SystemClock.currentThreadTimeMillis() - currentThreadTimeMillis);
        H5Log.d(TAG, sb.toString());
        this.isInited = true;
    }

    private void afterQuinoxlessFrameworkInit(Application application) {
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5NebulaUtil.getConfigWithProcessCache("enable_alipay_log"))) {
            LoggerFactory.setTraceLogger(new TestLogger());
        }
        MonitorFactory.bind(new AdapterMonitorContext(), new AdapterTimestampInfo());
        setupNebula(application);
        setupAmapHooks(application);
        initUpgradeHelper(application);
        loadAutoLoginBlackList();
        FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_DOSTARTAPP, (Advice) new AmapH5StartAppAdviceImpl());
        ahm.a(new Runnable() {
            public void run() {
                MiniAppTextureMapHelper.getInstance().initTextureMap();
            }
        });
    }

    private void initUpgradeHelper(Context context) {
        try {
            Class<?> cls = Class.forName("com.alipay.mobile.quinox.startup.UpgradeHelper");
            cls.getDeclaredMethod("init", new Class[0]).invoke(cls.getDeclaredMethod("getInstance", new Class[]{Context.class}).invoke(null, new Object[]{context}), new Object[0]);
        } catch (Throwable th) {
            H5Log.e((String) TAG, "initUpgradeHelper...e=".concat(String.valueOf(th)));
        }
    }

    private void loadAutoLoginBlackList() {
        H5Utils.getExecutor("RPC").execute(new Runnable() {
            public void run() {
                try {
                    OperationResult startAction = InsideOperationService.getInstance().startAction((Context) LauncherApplicationAgent.getInstance().getApplicationContext(), (BaseModel<T>) new PreLoadConfigModel<T>());
                    StringBuilder sb = new StringBuilder("preload autoLogin blacklist result: ");
                    sb.append(startAction != null ? startAction.toJsonString() : "");
                    H5Log.d(MiniAppInitHelper.TAG, sb.toString());
                } catch (Throwable th) {
                    H5Log.e(MiniAppInitHelper.TAG, "preload autoLogin blacklist error", th);
                }
            }
        });
    }

    private void beforeQuinoxlessFrameworkInit(Application application) {
        if (!QuinoxlessFramework.isQuinoxlessMode()) {
            setupQuinoxlessFramework(application);
        }
        ajm.b();
    }

    public void setupQuinoxlessFramework(final Application application) {
        QuinoxlessFramework.setup(application, new OnInitListener() {
            public void postInit() {
            }

            public void preInit() {
                MiniAppInitHelper.this.setupLogging(application);
                MiniAppInitHelper.this.setupMonitor(application);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setupLogging(Application application) {
        LoggerFactory.init(application);
        AppInfo createInstance = AppInfo.createInstance(application);
        createInstance.setProductID("AMAP_ANDROID");
        createInstance.setProductVersion(a.a().a);
        createInstance.setChannels("amap");
        LoggerFactory.getLogContext().setProductId(createInstance.getProductID());
        LoggerFactory.getLogContext().setProductVersion(a.a().a);
        LoggerFactory.getLogContext().setDeviceId(MiniAppDataUtil.getTaobaoId());
        if (!"".equals(MiniAppDataUtil.getAlipayId())) {
            LoggerFactory.getLogContext().setUserId(MiniAppDataUtil.getAlipayId());
        } else {
            LoggerFactory.getLogContext().setUserId(MiniAppDataUtil.getTaobaoId());
        }
        LoggerFactory.getLogContext().putBizExternParams(MiniAppDataUtil.EXT_CLIENT_ID_KEY, MiniAppDataUtil.getAmapId());
    }

    /* access modifiers changed from: private */
    public void setupMonitor(Application application) {
        DeviceInfo.createInstance(application);
        ClientMonitor.createInstance(application);
        AmapClientMonitor.createInstance(application);
    }

    private void setupNebula(Application application) {
        presetAppx();
        presetBizApp(application);
        ((H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName())).getProviderManager().setProvider(H5UaProvider.class.getName(), new H5UaProvider() {
            public String getUa(String str) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" myApp 1.0 AMapClient/1.0.4");
                return sb.toString();
            }
        });
        try {
            TinyAppService.get().setUserId(MiniAppDataUtil.getTaobaoId());
        } catch (Throwable unused) {
        }
    }

    private void setupAmapHooks(Application application) {
        initAmapApi();
        initAmapService();
        setupLBS(application);
        TinyAppService.get().interceptDefaultShareAction(this.mShareInterface);
        TinyAppService.get().setTinyAppFavoriteService(this.mTinyAppFavoriteService);
        TinyAppAuthManager.get().registerAuthCallback(new TinyAppAuthCallback() {
            public void getMCAuthLoginInfo(String str, Bundle bundle, IAccountOAuthCallback iAccountOAuthCallback) {
                StringBuilder sb = new StringBuilder("getMCAuthLoginInfo1, bundle: ");
                sb.append(bundle);
                sb.append(", needRefreshToken: ");
                sb.append(str);
                AMapLog.debug("infoservice.miniapp", MiniAppInitHelper.TAG, sb.toString());
                new MiniAppAuthHelper().authMiniApp(iAccountOAuthCallback, bundle, TextUtils.equals(str, "YES"));
            }

            public void getMCAuthLoginInfo(String str, IAccountOAuthCallback iAccountOAuthCallback) {
                AMapLog.debug("infoservice.miniapp", MiniAppInitHelper.TAG, "getMCAuthLoginInfo2, needRefreshToken: ".concat(String.valueOf(str)));
                new MiniAppAuthHelper().authMiniApp(iAccountOAuthCallback, null, TextUtils.equals(str, "YES"));
            }

            public String getMcBindAlipayUid() {
                AMapLog.debug("infoservice.miniapp", MiniAppInitHelper.TAG, "getMcBindAlipayUid");
                IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                if (iAccountService == null) {
                    return null;
                }
                ant e = iAccountService.e();
                if (e != null) {
                    return e.u;
                }
                return null;
            }
        });
        InsideH5ServiceManager.getInstance().setInsideH5Service(new IInsideH5Service() {
            public H5Service getH5Service() {
                return (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
            }
        });
    }

    public boolean isMiniAppInited() {
        return this.isInited;
    }

    private void presetAppx() {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            H5Log.e((String) TAG, (String) "h5Service == null");
        } else {
            h5Service.getProviderManager().setProvider(H5AppCenterPresetProvider.class.getName(), new H5AppCenterPresetProviderImpl());
        }
    }

    private void initAmapApi() {
        PluginManager.getInstance().initAMapPlugin((H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName()));
    }

    private void initAmapService() {
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().registerService(MtopService.class.getName(), new MtopServiceImpl());
    }

    private void setupLBS(Application application) {
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setInterfaceClass(LBSLocationManagerService.class.getName());
        serviceDescription.setLazy(true);
        serviceDescription.setClassName(AdapterLBSLocationManagerService.class.getName());
        serviceDescription.setClassLoader(application.getClassLoader());
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().registerExternalService(serviceDescription);
    }

    private void closeAndroidPDialog() {
        if (VERSION.SDK_INT > 27) {
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Method declaredMethod = cls.getDeclaredMethod("currentActivityThread", new Class[0]);
                declaredMethod.setAccessible(true);
                Object invoke = declaredMethod.invoke(null, new Object[0]);
                Field declaredField = cls.getDeclaredField("mHiddenApiWarningShown");
                declaredField.setAccessible(true);
                declaredField.setBoolean(invoke, true);
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("close AndroidP dialog exception: ");
                sb.append(e.getMessage());
                AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
            }
        }
    }
}
