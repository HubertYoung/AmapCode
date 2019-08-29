package com.alipay.mobile.tinyappcommon.storage;

import android.text.TextUtils;
import com.alipay.mobile.liteprocess.LiteProcessApi;
import com.alipay.mobile.tinyappcommon.config.TinyAppConfig;
import com.alipay.mobile.tinyappcommon.storage.H5SharedPreferenceStorage.MainProcProxyListener;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;

public class IPCSharedPreferenceStorage implements MainProcProxyListener {
    private static final IPCSharedPreferenceStorage INSTANCE = new IPCSharedPreferenceStorage();

    private IPCSharedPreferenceStorage() {
    }

    public static IPCSharedPreferenceStorage getInstance() {
        return INSTANCE;
    }

    public void init() {
        if (LiteProcessApi.isLiteProcess()) {
            H5SharedPreferenceStorage.getInstance().registerMainProcProxyListener(this);
        }
        H5SharedPreferenceStorage.getInstance().initLoadStorage();
    }

    public void setVConsoleVisible(String appId, boolean visible) {
        WalletTinyappUtils.getMultiProcessService().a(appId, visible);
    }

    public void setPerformancePanelVisible(String appId, boolean visible) {
        WalletTinyappUtils.getMultiProcessService().b(appId, visible);
    }

    public int getDefaultCurrentStorageSize(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return 0;
        }
        return WalletTinyappUtils.getMultiProcessService().a(appId, WalletTinyappUtils.getUserId());
    }

    public void putInt(String key, int value, boolean need2SyncAllLite) {
        WalletTinyappUtils.getMultiProcessService().a(key, value, need2SyncAllLite);
    }

    public void remove(String key) {
        WalletTinyappUtils.getMultiProcessService().a(key);
    }

    public boolean getAssistantPanelSwitch() {
        return TinyAppConfig.getInstance().getAssistantPanelSwitch();
    }
}
