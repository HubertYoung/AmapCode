package com.autonavi.map.wallet;

import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.amap.bundle.network.response.exception.ServerException;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.wallet.Page.WalletMainPage;

public class WalletUiController$6 implements Callback<cfu> {
    public final void error(Throwable th, boolean z) {
        if (th != null && (th instanceof ServerException) && ((ServerException) th).getCode() == 14) {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                iAccountService.a(AMapPageUtil.getPageContext(), (anq) new anq() {
                    public final void loginOrBindCancel() {
                    }

                    public final void onComplete(boolean z) {
                        if (z) {
                            cfl.a();
                        }
                    }
                });
            }
        }
    }

    public final void callback(cfu cfu) {
        if (cfu != null) {
            if (cfu.errorCode != 1) {
                ToastHelper.showLongToast(cfu.getErrorDesc(cfu.errorCode));
                return;
            }
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("AVAILABLE", cfu.b);
            pageBundle.putString("CASHOUTING", cfu.d);
            pageBundle.putString("CHECKING", cfu.c);
            pageBundle.putString("FAILURE", cfu.g);
            pageBundle.putString("FREEZE", cfu.f);
            pageBundle.putString(GenBusCodeService.CODE_SUCESS, cfu.e);
            pageBundle.putString("TOTAL", cfu.h);
            pageBundle.putString("WORDS", cfu.i);
            pageBundle.putString("NOTE", cfu.j);
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.startPage(WalletMainPage.class, pageBundle);
            }
        }
    }
}
