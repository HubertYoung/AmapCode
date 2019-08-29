package com.alipay.mobile.nebula.util;

import android.support.annotation.Nullable;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.BundleContext;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5container.service.UcService;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.amap.location.common.model.AmapLoc;
import com.taobao.accs.common.Constants;

public class H5ServiceUtils {
    private static final String TAG = "H5ServiceUtils";
    private static UcService sUcService = null;

    public static UcService getUcService() {
        UcService service = (UcService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(UcService.class.getName());
        if (service != null) {
            return service;
        }
        if (!AmapLoc.TYPE_NEW.equals(getServiceDownGradeMode())) {
            return null;
        }
        if (sUcService == null) {
            Class clazz = null;
            try {
                BundleContext bundleContext = LauncherApplicationAgent.getInstance().getBundleContext();
                bundleContext.loadBundle("android-phone-wallet-nebulauc");
                ClassLoader classLoader = bundleContext.findClassLoaderByBundleName("android-phone-wallet-nebulauc");
                if (classLoader != null) {
                    clazz = classLoader.loadClass("com.alipay.mobile.nebulauc.impl.UcServiceImpl");
                }
                if (clazz != null) {
                    sUcService = (UcService) clazz.newInstance();
                }
            } catch (Throwable throwable) {
                H5Log.e(TAG, "getUcService", throwable);
            }
        }
        H5LogUtil.logH5Exception(H5LogData.seedId(H5Logger.H5_ABNORMAL_ERROR).param1().add("SERVICE_NOT_FOUND", null).param2().add("UcService", null).param3().add("reflectFound", Boolean.valueOf(sUcService != null)));
        return sUcService;
    }

    public static H5Service getH5Service() {
        H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            H5LogUtil.logH5Exception(H5LogData.seedId(H5Logger.H5_ABNORMAL_ERROR).param1().add("SERVICE_NOT_FOUND", null).param2().add("H5Service", null));
        }
        return h5Service;
    }

    @Nullable
    public static H5AppCenterService getAppCenterService() {
        H5AppCenterService appCenterService = (H5AppCenterService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5AppCenterService.class.getName());
        if (appCenterService == null) {
            H5LogUtil.logH5Exception(H5LogData.seedId(H5Logger.H5_ABNORMAL_ERROR).param1().add("SERVICE_NOT_FOUND", null).param2().add("H5AppCenterService", null));
        }
        return appCenterService;
    }

    @Nullable
    public static H5AppDBService getAppDBService() {
        H5AppCenterService appCenterService = getAppCenterService();
        H5AppDBService appDBService = null;
        if (appCenterService != null) {
            appDBService = appCenterService.getAppDBService();
        }
        if (appDBService == null) {
            H5LogUtil.logH5Exception(H5LogData.seedId(H5Logger.H5_ABNORMAL_ERROR).param1().add("SERVICE_NOT_FOUND", null).param2().add("H5AppDBService", null));
        }
        return appDBService;
    }

    public static String getServiceDownGradeMode() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null) {
            return AmapLoc.TYPE_NEW;
        }
        JSONObject modeJson = h5ConfigProvider.getConfigJSONObject("h5ServiceDowngradeMode");
        if (modeJson != null) {
            return H5Utils.getString(modeJson, (String) Constants.KEY_MODE, (String) AmapLoc.TYPE_NEW);
        }
        return AmapLoc.TYPE_NEW;
    }
}
