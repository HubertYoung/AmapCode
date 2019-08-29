package com.alipay.mobile.nebulacore.wallet;

import android.content.Context;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5Context;

public class WalletContext extends H5Context {
    private MicroApplication a;

    public WalletContext(Context context) {
        super(context);
    }

    public MicroApplication getMicroApplication() {
        return this.a;
    }

    public void setMicroApplication(MicroApplication ma) {
        this.a = ma;
    }
}
