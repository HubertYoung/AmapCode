package com.mpaas.nebula.adapter.api;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.exception.IllegalParameterException;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5PreSetPkgInfo;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.appcenter.downloadImpl.H5DownLoadCallBackList;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppInstallStep;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MPaaSNebula {
    private static MPaaSNebula a;
    private final H5AppProvider b = ((H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName()));
    private H5AppCenterService c = ((H5AppCenterService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5AppCenterService.class.getName()));

    public class DefaultNebulaAppReady implements NebulaAppReady {
        public DefaultNebulaAppReady() {
        }

        public void onReady(String appId, boolean success) {
            LoggerFactory.getTraceLogger().info("MPaaSNebula", "onReady " + appId + " success: " + success);
        }

        public void prepare(H5AppInstallStep installStep, String appId) {
        }

        public void onResult(boolean finish, boolean limit) {
        }
    }

    public interface NebulaAppReady {
        void onReady(String str, boolean z);

        void onResult(boolean z, boolean z2);

        void prepare(H5AppInstallStep h5AppInstallStep, String str);
    }

    private MPaaSNebula() {
    }

    public H5AppProvider getH5AppProvider() {
        return this.b;
    }

    public static MPaaSNebula getInstance() {
        if (a == null) {
            synchronized (MPaaSNebula.class) {
                if (a == null) {
                    a = new MPaaSNebula();
                }
            }
        }
        return a;
    }

    public void startUpdateAllApp(NebulaAppReady nebulaAppReady) {
        startUpdateApp(null, nebulaAppReady);
    }

    public void startUpdateApp(Map<String, String> appMap, final NebulaAppReady nebulaAppReady) {
        if (appMap == null) {
            appMap = new HashMap<>();
        }
        final Map finalAppMap = new HashMap();
        for (Entry entry : appMap.entrySet()) {
            String tAppId = (String) entry.getKey();
            String tVersion = (String) entry.getValue();
            try {
                String tmp = this.b.getVersion(tAppId);
                if (!TextUtils.isEmpty(tmp)) {
                    tVersion = tmp;
                }
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) "MPaaSNebula", e);
            }
            finalAppMap.put(tAppId, tVersion);
        }
        if (appMap.isEmpty()) {
            appMap.put("nebula-*-all", "0");
        }
        this.b.updateApp(H5UpdateAppParam.newBuilder().setAppMap(appMap).setDownLoadAmr(true).setUpdateCallback(new H5UpdateAppCallback() {
            public void onResult(boolean finish, boolean isLimit) {
                super.onResult(finish, isLimit);
                H5Log.d("MPaaSNebula", "finish:" + finish);
                if (finish && finalAppMap != null) {
                    for (String appId : finalAppMap.keySet()) {
                        if (!TextUtils.isEmpty(appId)) {
                            MPaaSNebula.this.ensureApp(appId, new DefaultNebulaAppReady() {
                                {
                                    MPaaSNebula mPaaSNebula = MPaaSNebula.this;
                                }

                                public void onReady(String appId, boolean success) {
                                    if (nebulaAppReady != null) {
                                        nebulaAppReady.onReady(appId, success);
                                    }
                                }
                            });
                        }
                    }
                }
                if (nebulaAppReady != null) {
                    nebulaAppReady.onResult(finish, isLimit);
                }
            }
        }).setForceRpc(true).build());
    }

    public void ensureApp(final String appId, final NebulaAppReady callback) {
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider == null) {
            H5Log.e((String) "MPaaSNebula", "ensureApp  appId is :" + appId + " , h5AppProvider == null ");
            return;
        }
        String version = h5AppProvider.getVersion(appId);
        H5Log.d("MPaaSNebula", "ensureApp appId is " + appId + " , version is " + version + " , isAvailable is " + h5AppProvider.isAvailable(appId, version));
        AppInfo appInfo = h5AppProvider.getAppInfo(appId, version);
        if (appInfo == null) {
            H5Log.e((String) "MPaaSNebula", "ensureApp  appId is :" + appId + " , appInfo == null ");
        } else {
            H5DownLoadCallBackList.registerCallback(appInfo.package_url, new H5DownloadCallback() {
                public void onPrepare(H5DownloadRequest h5DownloadRequest) {
                    H5Log.d("MPaaSNebula", "downloadApp appId is " + appId + ",onPrepare : " + h5DownloadRequest.getDownloadUrl());
                }

                public void onProgress(H5DownloadRequest h5DownloadRequest, int i) {
                    H5Log.d("MPaaSNebula", "downloadApp appId is " + appId + ", onProgress h5DownloadRequest : " + h5DownloadRequest.getDownloadUrl());
                }

                public void onCancel(H5DownloadRequest h5DownloadRequest) {
                    H5Log.d("MPaaSNebula", "downloadApp appId is " + appId + ",onCancel h5DownloadRequest getDownloadUrl : " + h5DownloadRequest.getDownloadUrl());
                }

                public void onFinish(H5DownloadRequest h5DownloadRequest, String path) {
                    H5Log.d("MPaaSNebula", "downloadApp onFinish:" + path);
                    callback.onReady(appId, true);
                }

                public void onFailed(H5DownloadRequest h5DownloadRequest, int code, String error) {
                    H5Log.d("MPaaSNebula", "onFailed" + code + error);
                    callback.onReady(appId, false);
                }
            });
        }
    }

    public void loadOffLineNebula(Context context, String jsonFileName, MPNebulaOfflineInfo... mpNebulaOfflineInfos) {
        if (mpNebulaOfflineInfos == null) {
            throw new IllegalParameterException("mpNebulaOfflineInfos can not be null");
        }
        H5AppCenterService h5AppCenterService = (H5AppCenterService) H5Utils.findServiceByInterface(H5AppCenterService.class.getName());
        try {
            List list = new ArrayList();
            for (MPNebulaOfflineInfo mpNebulaOfflineInfo : mpNebulaOfflineInfos) {
                list.add(new H5PreSetPkgInfo(mpNebulaOfflineInfo.appId, mpNebulaOfflineInfo.version, context.getResources().getAssets().open(mpNebulaOfflineInfo.offLineFileName), false));
            }
            h5AppCenterService.loadPresetApp(list);
            h5AppCenterService.loadPresetAppList(context.getResources().getAssets().open(jsonFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public View getH5AppView(Activity activity, String appId, String url) {
        if (activity == null || TextUtils.isEmpty(appId)) {
            return null;
        }
        H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(H5Service.class.getName());
        H5Bundle bundle = new H5Bundle();
        Bundle param = new Bundle();
        param.putString("appId", appId);
        param.putString("url", url);
        bundle.setParams(param);
        if (h5Service != null) {
            return h5Service.createPage(activity, bundle).getContentView();
        }
        return null;
    }

    public View getH5AppView(Activity activity, String appId) {
        return getH5AppView(activity, appId, "/www/index.html");
    }

    public Map<String, String> queryAll() {
        H5AppDBService h5AppDBService = this.c.getAppDBService();
        if (h5AppDBService == null) {
            return new HashMap();
        }
        Map installedApp = h5AppDBService.getInstalledApp();
        if (installedApp == null) {
            return new HashMap();
        }
        return installedApp;
    }
}
