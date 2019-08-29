package com.alipay.android.phone.inside.wallet.plugin.service;

import android.content.Context;
import android.os.Bundle;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.wallet.api.AlipayServiceBinder;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;

public class JumpSchemeService extends AbstractInsideService<Bundle, String> {
    private static final int ALIPAY_MIN_VERSION_FOR_JUMP = 126;
    private static final String JUMP_FAILURE = "FAILURE";
    private static final String JUMP_SUCCESS = "SUCCESS";

    public String startForResult(Bundle bundle) throws Exception {
        Context context = getContext();
        WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(context, 126);
        if (checkAlipayStatus != WalletStatusEnum.SUCCESS) {
            return checkAlipayStatus.name();
        }
        try {
            AlipayServiceBinder.getInstance().invokeAlipayService(context, bundle);
            return "SUCCESS";
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "inside", (String) "JumpSchemeService", th);
            return JUMP_FAILURE;
        }
    }
}
