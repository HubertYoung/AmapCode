package com.alipay.mobile.nebula.appcenter.apphandler;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.HashMap;
import java.util.Map;

public class H5InstallAppAdvice implements Advice {
    private static final String H5App = "H5App";
    private static final String TAG = "H5InstallAppAdvice";
    public static final String debug = "debug";
    private static final Map<String, String> mEngineMap = new HashMap();
    public static final String nbsource = "nbsource";
    private static final String tinyApp = "tinyApp";

    public void onCallBefore(String pointCut, Object targetPoint, Object[] args) {
    }

    public Pair<Boolean, Object> onCallAround(String pointCut, Object targetPoint, Object[] args) {
        if (!TextUtils.equals(pointCut, PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_INSTALLAPP) || args == null) {
            return null;
        }
        try {
            if (args.length < 3) {
                return null;
            }
            String sourceAppId = "";
            String targetAppId = "";
            Bundle param = null;
            if (args[0] instanceof String) {
                sourceAppId = args[0];
            }
            if (args[1] instanceof String) {
                targetAppId = args[1];
            }
            if (args[2] instanceof Bundle) {
                param = args[2];
            }
            H5Log.d(TAG, "sourceAppId " + sourceAppId + " targetAppId:" + targetAppId + " param:" + param);
            if (!enableUseDevMode(param) || TextUtils.isEmpty(targetAppId)) {
                return null;
            }
            ApplicationDescription appDescription = generateApplicationDescription(targetAppId, H5Utils.getString(param, (String) H5PreferAppList.nbsv));
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().addDescription(appDescription);
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(sourceAppId, targetAppId, param);
            return new Pair(Boolean.valueOf(true), null);
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return null;
        }
    }

    public static void updateApplicationDescription(String targetAppId, Bundle param) {
        synchronized (mEngineMap) {
            ApplicationDescription originDesc = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findDescriptionByAppId(targetAppId);
            if (originDesc != null) {
                H5Log.d(TAG, "updateApplicationDescription " + targetAppId + " origin " + originDesc);
                if (!mEngineMap.containsKey(targetAppId)) {
                    mEngineMap.put(targetAppId, originDesc.getEngineType());
                }
            }
            ApplicationDescription appDescription = generateApplicationDescription(targetAppId, H5Utils.getString(param, (String) H5PreferAppList.nbsv));
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().deleteDescriptionByAppId(targetAppId);
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().addDescription(appDescription);
        }
    }

    static void restoreApplicationDescription(String targetAppId) {
        synchronized (mEngineMap) {
            String originEngineType = mEngineMap.remove(targetAppId);
            if (originEngineType != null) {
                H5Log.d(TAG, "restoreApplicationDescription " + targetAppId + " to " + originEngineType);
                ApplicationDescription appDescription = new ApplicationDescription();
                appDescription.setAppId(targetAppId);
                appDescription.setEngineType(originEngineType);
                LauncherApplicationAgent.getInstance().getMicroApplicationContext().deleteDescriptionByAppId(targetAppId);
                LauncherApplicationAgent.getInstance().getMicroApplicationContext().addDescription(appDescription);
            }
        }
    }

    private static ApplicationDescription generateApplicationDescription(String targetAppId, String version) {
        AppInfo appInfo = null;
        H5AppDBService h5AppDBService = H5ServiceUtils.getAppDBService();
        if (h5AppDBService != null) {
            appInfo = h5AppDBService.getAppInfo(targetAppId, version);
            if (appInfo == null) {
                H5Log.d(TAG, "get null appInfo for nbsv " + version);
                appInfo = h5AppDBService.getAppInfo(targetAppId, h5AppDBService.getHighestAppVersion(targetAppId));
            }
        }
        H5Log.d(TAG, "get appInfo: " + appInfo);
        ApplicationDescription appDescription = new ApplicationDescription();
        appDescription.setAppId(targetAppId);
        if (appInfo == null) {
            appDescription.setEngineType("H5App");
        } else if (H5AppUtil.isRNPackage(appInfo) && canSwitchRNPackageToTinyApp()) {
            appDescription.setEngineType("tinyApp");
        } else if (appInfo.app_type == 5) {
            appDescription.setEngineType("tinyApp");
        } else if (appInfo.app_type == 1) {
            appDescription.setEngineType("H5App");
        }
        H5Log.d(TAG, "get ApplicationDesc for " + targetAppId + " version: " + version + ": " + appDescription);
        return appDescription;
    }

    private static boolean canSwitchRNPackageToTinyApp() {
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider == null || "false".equalsIgnoreCase(provider.getConfig("NEED_APP_TYPE_SWITCH_TO_TINY_APP_DEBUG"))) {
            return false;
        }
        return true;
    }

    public void onCallAfter(String pointCut, Object targetPoint, Object[] args) {
    }

    public void onExecutionBefore(String pointCut, Object thisPoint, Object[] args) {
    }

    public Pair<Boolean, Object> onExecutionAround(String pointCut, Object thisPoint, Object[] args) {
        return null;
    }

    public void onExecutionAfter(String pointCut, Object thisPoint, Object[] args) {
    }

    public static boolean enableUseDevMode(Bundle param) {
        return "debug".equalsIgnoreCase(H5Utils.getString(param, (String) "nbsource"));
    }
}
