package com.alipay.android.phone.inside.wallet.plugin.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;

public class AlipayStatusService extends AbstractInsideService<Bundle, String> {
    public String startForResult(Bundle bundle) throws Exception {
        return ApkVerifyTool.checkAlipayStatus(getContext(), bundle.getInt("minVersionCode", 110)).name();
    }
}
