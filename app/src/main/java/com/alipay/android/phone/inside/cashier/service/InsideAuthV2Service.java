package com.alipay.android.phone.inside.cashier.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.cashier.utils.AuthV2Provider;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class InsideAuthV2Service extends AbstractInsideService<Bundle, Bundle> {
    public void start(IInsideServiceCallback<Bundle> iInsideServiceCallback, Bundle bundle) throws Exception {
        new AuthV2Provider().render(getContext(), bundle.getString("signParams"), iInsideServiceCallback);
    }

    public Bundle startForResult(Bundle bundle) throws Exception {
        LoggerFactory.f().b((String) "inside", (String) "InsideServicePay::startForResult(_)");
        throw new UnsupportedOperationException("not impl of this method");
    }
}
