package com.alipay.android.phone.inside.offlinecode.plugin.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.offlinecode.rpc.MobileSecurityRpcProvider;
import com.alipay.android.phone.inside.offlinecode.storage.CardDataStorage;

public class CloseBusCodeService extends AbstractInsideService<Bundle, Bundle> {
    public Bundle startForResult(Bundle bundle) throws Exception {
        boolean closeBusCode = new MobileSecurityRpcProvider().closeBusCode();
        CardDataStorage.getInstance().clearCardByUser(getContext());
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("success", closeBusCode);
        return bundle2;
    }
}
