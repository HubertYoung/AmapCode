package com.alipay.mobile.nebula.util;

import android.os.ConditionVariable;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5PreSetPkgInfo;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.nebula.appcenter.api.H5LoadPresetListen;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class H5PresetResUtil {
    public static String APP_RESOURCE_PACKAGE_ID = "63300038";
    public static String APP_RESOURCE_PACKAGE_URL = "https://gw.alipayobjects.com/os/nebulamng/AP_63300038-sign/80gf3b0kaa2.amr";
    public static String APP_RESOURCE_PACKAGE_VERSION = "0.1.1810102121.51";
    private static final String TAG = "H5PresetResUtil";

    public static synchronized void unzipPresetResourcePkg(H5LoadPresetListen h5LoadPresetListen) {
        synchronized (H5PresetResUtil.class) {
            if (!needUnzip()) {
                H5Log.d(TAG, "h5_specialSyncUnzip " + APP_RESOURCE_PACKAGE_ID + " already install");
            } else {
                try {
                    H5AppCenterService appCenterService = (H5AppCenterService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5AppCenterService.class.getName());
                    if (appCenterService != null) {
                        InputStream pkgSrc = H5Utils.getContext().getAssets().open("nebulaPreset/" + APP_RESOURCE_PACKAGE_ID);
                        List list = new ArrayList();
                        list.add(new H5PreSetPkgInfo(APP_RESOURCE_PACKAGE_ID, APP_RESOURCE_PACKAGE_VERSION, pkgSrc, true, APP_RESOURCE_PACKAGE_URL));
                        H5Log.d(TAG, "begin loadPresetAppNow " + APP_RESOURCE_PACKAGE_ID);
                        appCenterService.loadPresetAppNow(list, h5LoadPresetListen);
                    }
                } catch (Throwable t) {
                    H5Log.e((String) "H5PresetResUtilunzipPresetResourcePkg", t);
                }
            }
        }
        return;
    }

    public static void unzipPresetResourcePkgByPageSetup(final H5LoadPresetListen h5LoadPresetListen) {
        if (!needUnzip()) {
            H5Log.d(TAG, "h5_specialSyncUnzip " + APP_RESOURCE_PACKAGE_ID + " already install");
            return;
        }
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            JSONObject jsonObject = H5Utils.parseObject(h5ConfigProvider.getConfig("h5_specialSyncUnzip"));
            String syncSwitch = H5Utils.getString(jsonObject, (String) FunctionSupportConfiger.SWITCH_TAG, (String) "yes");
            int timeLimit = H5Utils.parseInt(H5Utils.getString(jsonObject, (String) "time", (String) "3"));
            if ("yes".equalsIgnoreCase(syncSwitch) && timeLimit != 0) {
                final ConditionVariable cv = new ConditionVariable();
                H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                    public final void run() {
                        H5PresetResUtil.unzipPresetResourcePkg(h5LoadPresetListen);
                        cv.open();
                    }
                });
                cv.block((long) (timeLimit * 1000));
            }
        }
    }

    private static boolean needUnzip() {
        H5AppProvider appProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (appProvider == null || !TextUtils.isEmpty(H5ServiceUtils.getAppDBService().findInstallAppVersion(APP_RESOURCE_PACKAGE_ID))) {
            return false;
        }
        String versionOnline = appProvider.getVersion(APP_RESOURCE_PACKAGE_ID);
        if (TextUtils.isEmpty(versionOnline)) {
            return true;
        }
        if (H5AppUtil.compareVersion(versionOnline, APP_RESOURCE_PACKAGE_VERSION) != 1) {
            return true;
        }
        return false;
    }
}
