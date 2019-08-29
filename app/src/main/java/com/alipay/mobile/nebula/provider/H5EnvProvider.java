package com.alipay.mobile.nebula.provider;

import android.os.Bundle;

public interface H5EnvProvider {
    String getLanguage();

    String getLoginId();

    String getProductVersion();

    String getRpcUrl();

    String getSnapshotJsapiSavePath();

    String getmDid();

    boolean goToSchemeService(String str);

    boolean goToSchemeService(String str, Bundle bundle);

    boolean goToSchemeService(String str, String str2, String str3);

    boolean isConcaveScreen();

    boolean updateStagesForTool();
}
