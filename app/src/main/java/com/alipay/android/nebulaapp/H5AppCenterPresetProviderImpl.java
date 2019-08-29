package com.alipay.android.nebulaapp;

import com.alipay.mobile.nebula.appcenter.H5PresetInfo;
import com.alipay.mobile.nebula.appcenter.H5PresetPkg;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class H5AppCenterPresetProviderImpl implements H5AppCenterPresetProvider {
    private static final String NEBULA_APPS_PRE_INSTALL;
    private static final String TINY_COMMON_APP = "66666692";
    final Map<String, H5PresetInfo> NEBULA_LOCAL_PACKAGE_APP_IDS = new HashMap();

    public Set<String> getEnableDegradeApp() {
        return null;
    }

    public InputStream getPresetAppInfo() {
        return null;
    }

    public InputStream getPresetAppInfoObject() {
        return null;
    }

    public String getTinyCommonApp() {
        return TINY_COMMON_APP;
    }

    static {
        StringBuilder sb = new StringBuilder("nebulaPreset");
        sb.append(File.separator);
        NEBULA_APPS_PRE_INSTALL = sb.toString();
    }

    public Set<String> getCommonResourceAppList() {
        HashSet hashSet = new HashSet();
        hashSet.add(TINY_COMMON_APP);
        return hashSet;
    }

    public H5PresetPkg getH5PresetPkg() {
        H5PresetInfo h5PresetInfo = new H5PresetInfo();
        h5PresetInfo.appId = TINY_COMMON_APP;
        h5PresetInfo.version = "0.33.1908071009.1";
        h5PresetInfo.downloadUrl = "https://gw.alipayobjects.com/os/nebulamng/AM_66666692-sign/uupafp8hn7.amr";
        this.NEBULA_LOCAL_PACKAGE_APP_IDS.put(TINY_COMMON_APP, h5PresetInfo);
        H5PresetPkg h5PresetPkg = new H5PresetPkg();
        h5PresetPkg.setPreSetInfo(this.NEBULA_LOCAL_PACKAGE_APP_IDS);
        h5PresetPkg.setPresetPath(NEBULA_APPS_PRE_INSTALL);
        return h5PresetPkg;
    }
}
