package com.alipay.mobile.nebulacore.appcenter.center;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5PreSetPkgInfo;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.H5PresetInfo;
import com.alipay.mobile.nebula.appcenter.H5PresetPkg;
import com.alipay.mobile.nebula.appcenter.api.H5LoadPresetListen;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppScoreList;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage;
import com.alipay.mobile.nebulacore.appcenter.parse.H5PackageParser;
import com.alipay.mobile.nebulacore.core.H5ContentProviderImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5GlobalDegradePkg {
    private static H5GlobalDegradePkg a;
    private Map<String, H5ContentPackage> b = new ConcurrentHashMap();

    private H5GlobalDegradePkg() {
    }

    public byte[] getContent(String url) {
        if (this.b == null || this.b.isEmpty()) {
            return null;
        }
        for (String action : this.b.keySet()) {
            H5ContentPackage h5ContentPackage = this.b.get(action);
            byte[] bytes = h5ContentPackage.get(url);
            if (bytes != null) {
                H5Log.d(H5ContentProviderImpl.TAG, "[res_degrade] load response from " + h5ContentPackage.getAppId() + " version:" + h5ContentPackage.currentUseVersion + " package " + url);
                return bytes;
            }
        }
        return null;
    }

    public static synchronized H5GlobalDegradePkg getInstance() {
        H5GlobalDegradePkg h5GlobalDegradePkg;
        synchronized (H5GlobalDegradePkg.class) {
            try {
                if (a == null) {
                    a = new H5GlobalDegradePkg();
                }
                h5GlobalDegradePkg = a;
            }
        }
        return h5GlobalDegradePkg;
    }

    public void put(String appId, ConcurrentHashMap<String, byte[]> concurrentHashMap, String version) {
        if (this.b != null) {
            H5ContentPackage current = this.b.get(appId);
            if (current != null) {
                current.clear();
                current.putAll(concurrentHashMap);
                current.currentUseVersion = version;
                current.refreshLogTag(appId, version);
            }
        }
    }

    public H5ContentPackage getH5ContentPackage(String appId) {
        if (this.b == null || this.b.isEmpty()) {
            return null;
        }
        return this.b.get(appId);
    }

    public void clear(String appId) {
        if (this.b != null && this.b.get(appId) != null) {
            this.b.get(appId).clear();
        }
    }

    public boolean prepareContent(String appId) {
        final long time = System.currentTimeMillis();
        H5ContentPackage h5ContentPackage = this.b.get(appId);
        if (h5ContentPackage == null) {
            Bundle bundle = new Bundle();
            H5Log.d("H5GlobalDegradePkg", "init h5TempPkg app " + appId);
            bundle.putString("appId", appId);
            bundle.putInt("appType", 2);
            this.b.put(appId, new H5ContentPackage(bundle, true));
        }
        H5AppCenterService h5AppCenterService = H5ServiceUtils.getAppCenterService();
        if (h5AppCenterService != null) {
            H5AppDBService h5AppDBService = h5AppCenterService.getAppDBService();
            if (h5AppDBService != null) {
                String installVersion = h5AppDBService.findInstallAppVersion(appId);
                H5Log.d("H5GlobalDegradePkg", "prepareContent installVersion:" + installVersion);
                boolean isInstallVersionValid = true;
                if (H5AppUtil.isTinyResAppId(appId)) {
                    String presetVersion = H5AppUtil.getTinyResPresetVersion();
                    if (presetVersion != null && H5AppUtil.compareVersion(installVersion, presetVersion) < 0 && H5AppUtil.isTinyResPresetForceCheck()) {
                        isInstallVersionValid = false;
                    }
                }
                if (!TextUtils.isEmpty(installVersion) && isInstallVersionValid) {
                    if (h5ContentPackage != null) {
                        if (!h5ContentPackage.isEmpty()) {
                            if (TextUtils.equals(installVersion, h5ContentPackage.currentUseVersion)) {
                                H5Log.d("H5GlobalDegradePkg", "prepareContent h5ContentPackage is not Empty not parse");
                                return false;
                            }
                        }
                        h5ContentPackage.currentUseVersion = installVersion;
                    }
                    H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                    if (h5AppProvider != null) {
                        String installPath = h5AppProvider.getInstallPath(appId, installVersion);
                        if (!TextUtils.isEmpty(installPath)) {
                            boolean result = a(appId, installPath, installVersion);
                            H5Log.d("H5GlobalDegradePkg", "prepareContent from " + installPath + " result:" + result + " speed:" + (System.currentTimeMillis() - time));
                            if (result) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        H5AppCenterPresetProvider presetProvider = (H5AppCenterPresetProvider) H5Utils.getProvider(H5AppCenterPresetProvider.class.getName());
        if (!(presetProvider == null || presetProvider.getH5PresetPkg() == null)) {
            try {
                H5PresetPkg h5PresetPkg = presetProvider.getH5PresetPkg();
                if (h5PresetPkg.getPreSetInfo() != null && h5PresetPkg.getPreSetInfo().containsKey(appId)) {
                    H5PresetInfo h5PresetInfo = h5PresetPkg.getPreSetInfo().get(appId);
                    String amrName = h5PresetInfo.appId;
                    String version = h5PresetInfo.version;
                    String downloadUrl = h5PresetInfo.downloadUrl;
                    H5Log.d("H5GlobalDegradePkg", "setWalletPreset getPreSetInfo  " + amrName + Token.SEPARATOR + version);
                    InputStream isSrc = H5Environment.getContext().getAssets().open(h5PresetPkg.getPresetPath() + amrName);
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new H5PreSetPkgInfo(appId, version, isSrc, true, downloadUrl));
                    if (h5AppCenterService != null) {
                        final String str = appId;
                        final String str2 = version;
                        h5AppCenterService.loadPresetAppNow(arrayList, new H5LoadPresetListen() {
                            public void getPresetPath(String path) {
                                if (!TextUtils.isEmpty(path)) {
                                    H5Log.d("H5GlobalDegradePkg", "prepareContent from apk  result:" + H5GlobalDegradePkg.this.a(str, path, str2) + " speed:" + (System.currentTimeMillis() - time));
                                }
                            }
                        });
                        return true;
                    }
                }
            } catch (Exception e) {
                H5Log.e("H5GlobalDegradePkg", "setWalletPreset not exist", e);
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean a(String appId, String installPath, String version) {
        if (TextUtils.isEmpty(installPath)) {
            H5Log.d("H5GlobalDegradePkg", "installPath is empty, return");
            return false;
        }
        H5ContentPackage current = getInstance().getH5ContentPackage(appId);
        if (current == null) {
            return false;
        }
        Bundle bundle = current.getParams();
        String offlineHost = "file://" + installPath;
        if (!offlineHost.endsWith("/")) {
            offlineHost = offlineHost + "/";
        }
        bundle.putString(H5Param.OFFLINE_HOST, offlineHost);
        ConcurrentHashMap content = new ConcurrentHashMap();
        if (H5PackageParser.parsePackage(bundle, content).code != 0) {
            return false;
        }
        H5Log.d("H5GlobalDegradePkg", "appId parsePackage success");
        clear(appId);
        put(appId, content, version);
        return true;
    }

    public String getPkgInfo(String appId) {
        String info = null;
        H5ContentPackage content = this.b.get(appId);
        if (content != null) {
            if (TextUtils.isEmpty(content.currentUseVersion)) {
                H5Log.d("H5GlobalDegradePkg", "appId : " + appId + " content.currentUseVersion is null");
            } else {
                if (content.size() > 0) {
                    info = content.getAppId() + "_Y_" + content.currentUseVersion + "_" + H5AppScoreList.getInstance().getAppCredit(content.getAppId()) + MergeUtil.SEPARATOR_KV;
                } else {
                    info = content.getAppId() + "_N_" + content.currentUseVersion + "_" + H5AppScoreList.getInstance().getAppCredit(content.getAppId()) + MergeUtil.SEPARATOR_KV;
                }
                H5Log.d("H5GlobalDegradePkg", "getPkgInfo : " + info);
            }
        }
        return info;
    }
}
