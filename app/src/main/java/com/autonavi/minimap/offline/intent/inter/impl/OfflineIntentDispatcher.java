package com.autonavi.minimap.offline.intent.inter.impl;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.minimap.offline.externalimport.IExternalService;
import com.autonavi.minimap.offline.inter.inner.IOfflineIntentDispatcher;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.offline.map.inter.impl.OfflineManagerImpl;
import com.autonavi.minimap.offline.uiutils.UiUtils;

public class OfflineIntentDispatcher extends BaseIntentDispatcher implements IOfflineIntentDispatcher {
    private static final String PARAMS_DOWN_OFFLINE_MAP = "downOfflineMap";
    private static final String PARAMS_OFFLINE_ENLARGEMENT = "enlargement";
    private static final String PARAMS_OFFLINE_MAP = "OfflineMap";
    private static final String PARAMS_OFFLINE_NAVI = "offlineNavi";
    private static final String PARAMS_OFFLINE_QUICK_NAVI = "OfflineQuickNavi";
    IOfflineManager offlineManager = new OfflineManagerImpl();

    public boolean dispatch(Intent intent) {
        Uri data = intent.getData();
        if (data == null) {
            return false;
        }
        String host = data.getHost();
        if (data == null || TextUtils.isEmpty(host) || !host.equalsIgnoreCase(BaseIntentDispatcher.HOST_OPENFEATURE) || !doOpenFeature(intent)) {
            return false;
        }
        return true;
    }

    private boolean doOpenFeature(Intent intent) {
        String queryParameter = intent.getData().getQueryParameter("featureName");
        if (TextUtils.isEmpty(queryParameter)) {
            ToastHelper.showLongToast(((IExternalService) ank.a(IExternalService.class)).getApplication().getString(R.string.intent_open_fail_param_error));
            return true;
        }
        if (queryParameter.equalsIgnoreCase(PARAMS_OFFLINE_NAVI)) {
            doOpenFeatureOfflineNavi();
        } else if (queryParameter.equalsIgnoreCase(PARAMS_OFFLINE_MAP)) {
            doOpenFeatureOfflineMap();
        } else if (queryParameter.equalsIgnoreCase(PARAMS_OFFLINE_ENLARGEMENT)) {
            doOpenFeatureOfflineEnlargement();
        } else if (queryParameter.equalsIgnoreCase(PARAMS_DOWN_OFFLINE_MAP)) {
            doOpenFeatureOfflineMap();
        } else if (!queryParameter.equalsIgnoreCase(PARAMS_OFFLINE_QUICK_NAVI)) {
            return false;
        } else {
            doOpenFeatureOfflineQuickNavi();
        }
        return true;
    }

    public void doOpenFeatureOfflineNavi() {
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        if (iOfflineManager != null) {
            Intent intent = new Intent();
            intent.putExtra("showPoiRouteDownload", true);
            iOfflineManager.deal(AMapPageUtil.getPageContext(), intent);
        }
    }

    public void doOpenFeatureOfflineMap() {
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        if (iOfflineManager != null) {
            Intent intent = new Intent();
            intent.putExtra("showMapDownload", true);
            iOfflineManager.deal(AMapPageUtil.getPageContext(), intent);
        }
    }

    public void doOpenFeatureOfflineEnlargement() {
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        if (iOfflineManager != null) {
            Intent intent = new Intent();
            intent.putExtra("showEnlargementDownload", true);
            iOfflineManager.deal(AMapPageUtil.getPageContext(), intent);
        }
    }

    public void doOpenFeatureOfflineQuickNavi() {
        UiUtils.gotoOfflineNavi();
    }
}
