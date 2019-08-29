package com.autonavi.map.wallet.Presenter;

import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.wallet.Page.WalletMainPage;

public class WalletMainPresenter$1 implements Callback<cfu> {
    final /* synthetic */ cfh a;

    public void error(Throwable th, boolean z) {
    }

    public WalletMainPresenter$1(cfh cfh) {
        this.a = cfh;
    }

    public void callback(cfu cfu) {
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
        ((WalletMainPage) this.a.mPage).a(pageBundle);
    }
}
