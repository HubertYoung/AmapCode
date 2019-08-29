package com.autonavi.map.wallet;

import android.text.TextUtils;
import com.amap.bundle.network.response.exception.ServerException;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;

public class WalletUiController$4 implements Callback<String> {
    final /* synthetic */ boolean a = true;
    final /* synthetic */ a b;
    final /* synthetic */ cfl c;

    public WalletUiController$4(cfl cfl, a aVar) {
        this.c = cfl;
        this.b = aVar;
    }

    public void callback(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (!this.a) {
                this.c.a(str, this.b, true);
            } else if (this.b != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("withdraw_taobao_token", str);
                this.b.a(pageBundle, 2);
                return;
            }
            return;
        }
        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.wallet_ali_repuest_fail));
    }

    public void error(Throwable th, boolean z) {
        if (th != null && (th instanceof ServerException) && ((ServerException) th).getCode() != 10003) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.wallet_ali_repuest_fail));
        }
    }
}
