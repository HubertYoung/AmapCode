package com.alipay.mobile.liteprocess.advice;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.liteprocess.Config;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcessClientManager;
import com.alipay.mobile.liteprocess.LiteProcessServerManager;
import com.alipay.mobile.liteprocess.Util;
import com.alipay.mobile.liteprocess.perf.PerformanceLogger;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.Arrays;

public class StartAppAdvice extends AbsLiteProcessAdvice {
    static boolean a = false;
    private static StartAppAdvice b;
    private static boolean d = false;
    private Object c;

    public /* bridge */ /* synthetic */ void onCallAfter(String str, Object obj, Object[] objArr) {
        super.onCallAfter(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ Pair onCallAround(String str, Object obj, Object[] objArr) {
        return super.onCallAround(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ void onCallBefore(String str, Object obj, Object[] objArr) {
        super.onCallBefore(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ void onExecutionAfter(String str, Object obj, Object[] objArr) {
        super.onExecutionAfter(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ Pair onExecutionAround(String str, Object obj, Object[] objArr) {
        return super.onExecutionAround(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ void onExecutionBefore(String str, Object obj, Object[] objArr) {
        super.onExecutionBefore(str, obj, objArr);
    }

    public static void register() {
        if (b == null) {
            synchronized (StartAppAdvice.class) {
                if (b == null) {
                    b = new StartAppAdvice();
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "register startAppAdvice");
                    FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTAPP, (Advice) b);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Pair<Boolean, Object> a(Object thisPoint, Object[] args) {
        String sourceAppId = "";
        String targetAppId = "";
        Bundle params = null;
        if (args == null || args.length < 3) {
            return null;
        }
        LoggerFactory.getTraceLogger().debug(Const.TAG, "StartAppAdvice@Main onExecutionAround args = " + Arrays.toString(args));
        if (args[0] instanceof String) {
            sourceAppId = args[0];
        }
        if (args[1] instanceof String) {
            targetAppId = args[1];
        }
        if (args[2] instanceof Bundle) {
            params = args[2];
        }
        if (H5Utils.SCAN_APP_ID.equals(targetAppId) || "20000056".equals(targetAppId) || "20000992".equals(targetAppId)) {
            return null;
        }
        if (!H5AppHandler.hasCheckParam(params)) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "StartAppAdvice targetAppId " + targetAppId);
            if (Config.NEED_LITE && !LiteProcessServerManager.g().hasPreloadProcess() && Config.URGENT_PRELOAD) {
                if ("60000155".equals(targetAppId)) {
                    LiteProcessServerManager.g().startLiteProcessAsync(-2);
                } else if ("66666666".equals(targetAppId)) {
                    LiteProcessServerManager.g().startLiteProcessAsync(-3);
                } else if ("20001003".equals(targetAppId)) {
                    LiteProcessServerManager.g().startLiteProcessAsync(-4);
                } else if (com.alipay.android.nebulaapp.Config.MY_LITTLE_APP_ID.equalsIgnoreCase(targetAppId)) {
                    LiteProcessServerManager.g().startLiteProcessAsync(-7);
                }
            }
            return null;
        } else if (Config.TINY_APP_LIST_MAIN_PROCESS == null || !Config.TINY_APP_LIST_MAIN_PROCESS.contains("\"" + targetAppId + "\"") || (LiteProcessServerManager.g().hasPreloadCompletedProcess() && !d)) {
            H5AppProvider h5AppProvider = null;
            try {
                h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            } catch (Exception ignore) {
                LoggerFactory.getTraceLogger().warn((String) Const.TAG, Log.getStackTraceString(ignore));
            }
            if (h5AppProvider == null) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "StartAppAdvice h5AppProvider is null targetAppId " + targetAppId);
                return null;
            } else if (targetAppId.equals("66666672")) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "hit 66666672");
                PerformanceLogger.init(targetAppId);
                PerformanceLogger.setPreload(params.getBoolean(Const.PERF_IS_PRELOAD, false));
                PerformanceLogger.setLocal(params.getBoolean("is_local", true));
                PerformanceLogger.setPrepareTime(SystemClock.elapsedRealtime());
                PerformanceLogger.setOpenAppTime(params.getLong("perf_open_app_time", -1));
                LoggerFactory.getTraceLogger().debug(Const.TAG, "StartAppAdvice startApp in main process sourceAppId = " + sourceAppId + " targetAppId = " + targetAppId + " params = " + params);
                return null;
            } else if (params.getBoolean(Const.START_APP_IN_CURRENT_PROCESS)) {
                LoggerFactory.getTraceLogger().info(Const.TAG_SAIL, "startAppInCurrentProcess@Main finish and return");
                return null;
            } else if (params.getBoolean(Const.START_APP_IN_LITE)) {
                LiteProcessServerManager.g().startAppInLiteStep3(params);
                return new Pair<>(Boolean.valueOf(true), null);
            } else if (Util.needSupportLiteProcess() && h5AppProvider.enableMultiProcess(targetAppId, params) && Config.NEED_LITE) {
                a = true;
                Bundle newParams = new Bundle(params);
                String appType = Util.getMicroAppContext().findDescriptionByAppId(targetAppId).getEngineType();
                newParams.putString(Const.APP_TYPE, appType);
                if (Const.TYPE_RN.equals(appType)) {
                    Util.getMicroAppContext().findServiceByInterface(Const.TINY_SERVICE);
                }
                LoggerFactory.getTraceLogger().debug(Const.TAG, "StartAppAdvice startApp targetAppId " + targetAppId + " appType " + appType);
                LiteProcessServerManager.g().startAppAsync(sourceAppId, targetAppId, newParams);
                a(thisPoint, args, new Bundle(params));
                return new Pair<>(Boolean.valueOf(true), null);
            } else if (!h5AppProvider.isUseAppX(targetAppId)) {
                return null;
            } else {
                PerformanceLogger.init(targetAppId);
                PerformanceLogger.setPreload(H5Flag.ucReady);
                PerformanceLogger.setLocal(params.getBoolean("is_local", true));
                PerformanceLogger.setPrepareTime(SystemClock.elapsedRealtime());
                PerformanceLogger.setOpenAppTime(params.getLong("perf_open_app_time", -1));
                if (!(params == null || params.getString("query") == null)) {
                    PerformanceLogger.setStartupQuery(params.getString("query"));
                }
                LoggerFactory.getTraceLogger().debug(Const.TAG, "StartAppAdvice startApp in main process sourceAppId = " + sourceAppId + " targetAppId = " + targetAppId + " params = " + params);
                return null;
            }
        } else {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "launch tiny app in main process, hasPreloadCompletedProcess = " + LiteProcessServerManager.g().hasPreloadCompletedProcess() + ", hasMainProcessLaunchedTinyapp = " + d);
            d = true;
            return null;
        }
    }

    private void a(Object thisPoint, Object[] args, Bundle params) {
        try {
            Object[] new_args = {args[0], args[1], params};
            params.remove(PointCutConstants.REALLY_DOSTARTAPP);
            params.putBoolean(PointCutConstants.REALLY_DOSTARTAPP, true);
            Class cls = Class.forName("com.alipay.mobile.appstoreapp.receiver.OpenplatformAppAdvice");
            if (this.c == null) {
                this.c = cls.newInstance();
            }
            cls.getDeclaredMethod("onExecutionAfter", new Class[]{String.class, Object.class, Object[].class}).invoke(this.c, new Object[]{PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_DOSTARTAPP, thisPoint, new_args});
            LoggerFactory.getTraceLogger().debug(Const.TAG, "callOpenPlatformAppAdvice success");
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "callOpenPlatformAppAdvice " + Log.getStackTraceString(e));
        }
    }

    /* access modifiers changed from: protected */
    public final Pair<Boolean, Object> b(Object thisPoint, Object[] args) {
        String sourceAppId = "";
        String targetAppId = "";
        Bundle params = null;
        if (args != null && args.length >= 3) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "StartAppAdvice@Lite onExecutionAround args = " + Arrays.toString(args));
            if (args[0] instanceof String) {
                sourceAppId = args[0];
            }
            if (args[1] instanceof String) {
                targetAppId = args[1];
            }
            if (args[2] instanceof Bundle) {
                params = args[2];
            }
        }
        if (Util.getMicroAppContext().getTopActivity() == null || Util.getMicroAppContext().getTopActivity().get() == null) {
            Util.getMicroAppContext().setStartActivityContext(Util.getContext());
        }
        if (params == null || !params.getBoolean(Const.START_APP_IN_CURRENT_PROCESS)) {
            return null;
        }
        LiteProcessClientManager.startAppInLiteStep1(sourceAppId, targetAppId, params);
        return new Pair<>(Boolean.valueOf(true), null);
    }

    /* access modifiers changed from: protected */
    public final boolean a(String pointCut, Object thisPoint) {
        if (!TextUtils.equals(pointCut, PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTAPP)) {
            return false;
        }
        return true;
    }
}
