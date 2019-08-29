package com.alipay.android.phone.inside.wallet.plugin.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.wallet.api.AlipayServiceBinder;

public class PreloadWalletService extends AbstractInsideService<Bundle, Bundle> {
    public Bundle startForResult(Bundle bundle) throws Exception {
        AlipayServiceBinder.getInstance().bindAlipayService(getContext(), false);
        return new Bundle();
    }
}
