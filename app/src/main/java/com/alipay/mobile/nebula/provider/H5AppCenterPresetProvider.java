package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.nebula.appcenter.H5PresetPkg;
import java.io.InputStream;
import java.util.Set;

public interface H5AppCenterPresetProvider {
    Set<String> getCommonResourceAppList();

    Set<String> getEnableDegradeApp();

    H5PresetPkg getH5PresetPkg();

    InputStream getPresetAppInfo();

    InputStream getPresetAppInfoObject();

    String getTinyCommonApp();
}
