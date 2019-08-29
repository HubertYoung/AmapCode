package com.autonavi.bundle.miniapp;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.nebulaapp.MiniAppInitHelper;
import com.alipay.android.nebulaapp.MiniAppUtil;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.nebula.util.H5Log;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.VirtualApp;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.miniapp.plugin.modules.ModuleMiniApp;
import com.autonavi.minimap.ajx3.Ajx;
import mtopsdk.mtop.intf.Mtop;

@VirtualApp(priority = 100)
public class MiniAppVApp extends esh implements anp {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleMiniApp.class);
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a((anp) this);
        }
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.b((anp) this);
        }
    }

    public void vAppEnterForeground() {
        super.vAppEnterForeground();
        if (MiniAppInitHelper.getInstance().isMiniAppInited()) {
            LoggerFactory.getLogContext().notifyClientEvent(LogContext.CLIENT_ENVENT_GOTOFOREGROUND, null);
        }
    }

    public void onLoginStateChanged(boolean z, boolean z2) {
        if (!z2) {
            Mtop.a((Context) AMapAppGlobal.getApplication()).c();
            MiniAppUtil.refreshAfterLogout();
        }
    }

    public void onUserInfoUpdate(ant ant) {
        StringBuilder sb = new StringBuilder("onUserInfoUpdate, alipayUID: ");
        sb.append(ant != null ? ant.u : "");
        H5Log.d("MiniAppVApp", sb.toString());
        if (ant == null || TextUtils.isEmpty(ant.u)) {
            MiniAppUtil.refreshAfterLogout();
        } else {
            MiniAppUtil.refreshAfterLogin(ant.u);
        }
    }
}
