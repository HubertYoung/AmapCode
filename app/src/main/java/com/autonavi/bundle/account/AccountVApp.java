package com.autonavi.bundle.account;

import android.content.Context;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.autonavi.annotation.VirtualApp;
import com.autonavi.bundle.account.ajx.ModuleAccount;
import com.autonavi.bundle.account.impl.BaichuanSDKWebViewApiImpl;
import com.autonavi.minimap.ajx3.Ajx;

@VirtualApp(priority = 100)
public class AccountVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleAccount.class);
        yx.a().a(new apb());
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        Context applicationContext = getApplicationContext();
        BaichuanSDKWebViewApiImpl.a(2);
        AlibcTradeSDK.asyncInit(applicationContext, new AlibcTradeInitCallback() {
            public void onSuccess() {
                BaichuanSDKWebViewApiImpl.a(3);
                AlibcTradeSDK.setIsAuthVip(true);
            }

            public void onFailure(int i, String str) {
                BaichuanSDKWebViewApiImpl.a(4);
            }
        });
    }

    public void vAppDestroy() {
        a.a.a();
    }
}
