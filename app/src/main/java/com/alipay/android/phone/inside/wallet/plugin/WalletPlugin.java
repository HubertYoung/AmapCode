package com.alipay.android.phone.inside.wallet.plugin;

import com.alipay.android.phone.inside.framework.plugin.IInsidePlugin;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.wallet.plugin.service.AlipayStatusService;
import com.alipay.android.phone.inside.wallet.plugin.service.CashierAuthService;
import com.alipay.android.phone.inside.wallet.plugin.service.CashierPayService;
import com.alipay.android.phone.inside.wallet.plugin.service.InstallGuideService;
import com.alipay.android.phone.inside.wallet.plugin.service.JumpSchemeService;
import com.alipay.android.phone.inside.wallet.plugin.service.PreloadWalletService;
import java.util.HashMap;
import java.util.Map;

public class WalletPlugin implements IInsidePlugin {
    static final String INSTALL_GUIDE_SERVICE = "WALLET_PLUGIN_INSTALL_GUIDE_SERVICE";
    static final String WALLET_PLUGIN_ALIPAY_STATUS_SERVICE = "WALLET_PLUGIN_ALIPAY_STATUS_SERVICE";
    static final String WALLET_PLUGIN_CASHIER_AUTH_SERVICE = "WALLET_PLUGIN_CASHIER_AUTH_SERVICE";
    static final String WALLET_PLUGIN_CASHIER_PAY_SERVICE = "WALLET_PLUGIN_CASHIER_PAY_SERVICE";
    static final String WALLET_PLUGIN_JUMP_SCHEME_SERVICE = "WALLET_PLUGIN_JUMP_SCHEME_SERVICE";
    static final String WALLET_PLUGIN_PRELOAD_WALLET_SERVICE = "WALLET_PLUGIN_PRELOAD_WALLET_SERVICE";
    private Map<String, IInsideService> mServices;

    public void onRegisted(Object obj) {
    }

    public void onUnRegisted(Object obj) {
    }

    public Map<String, IInsideService> getServiceMap() {
        if (this.mServices == null) {
            this.mServices = new HashMap();
            this.mServices.put(INSTALL_GUIDE_SERVICE, new InstallGuideService());
            this.mServices.put(WALLET_PLUGIN_CASHIER_AUTH_SERVICE, new CashierAuthService());
            this.mServices.put(WALLET_PLUGIN_CASHIER_PAY_SERVICE, new CashierPayService());
            this.mServices.put(WALLET_PLUGIN_ALIPAY_STATUS_SERVICE, new AlipayStatusService());
            this.mServices.put(WALLET_PLUGIN_JUMP_SCHEME_SERVICE, new JumpSchemeService());
            this.mServices.put(WALLET_PLUGIN_PRELOAD_WALLET_SERVICE, new PreloadWalletService());
        }
        return this.mServices;
    }

    public IInsideService getService(String str) {
        return this.mServices.get(str);
    }
}
