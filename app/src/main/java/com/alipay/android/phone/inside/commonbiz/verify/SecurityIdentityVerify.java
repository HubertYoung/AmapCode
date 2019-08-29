package com.alipay.android.phone.inside.commonbiz.verify;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.common.util.RandamUtil;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.inside.wallet.JumpAlipaySchemeProvider;
import com.alipay.android.phone.inside.wallet.model.INotifyChecker;
import com.alipay.android.phone.inside.wallet.model.TimeoutException;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import java.util.HashMap;

public class SecurityIdentityVerify {
    /* access modifiers changed from: private */
    public String a;

    public class VerifyResult {
        public boolean a;

        public VerifyResult() {
        }
    }

    public final boolean a(Context context, String str, String str2) throws Exception {
        try {
            this.a = RandamUtil.a();
            return a(context, str, this.a, str2).a;
        } catch (TimeoutException e) {
            throw e;
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return false;
        }
    }

    private VerifyResult a(Context context, String str, String str2, String str3) throws TimeoutException {
        JumpAlipaySchemeProvider jumpAlipaySchemeProvider = new JumpAlipaySchemeProvider();
        HashMap hashMap = new HashMap();
        hashMap.put("bizId", str2);
        hashMap.put("securityCheckUrl", str3);
        VerifyResult verifyResult = new VerifyResult();
        try {
            if (TextUtils.equals(jumpAlipaySchemeProvider.jumpScheme(context, JumpAlipaySchemeProvider.BIZ_COMMON_H5_VERIFY, str, hashMap, new INotifyChecker() {
                public boolean illegel(Bundle bundle) {
                    if (bundle == null) {
                        return false;
                    }
                    if (!bundle.getBoolean("insideFlag", false)) {
                        LoggerFactory.e().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, "VerifyNotifyInsideFlagIllegel");
                        return true;
                    }
                    String string = bundle.getString("bizId");
                    if (TextUtils.equals(string, SecurityIdentityVerify.this.a)) {
                        return false;
                    }
                    StringBuilder sb = new StringBuilder("bizId=");
                    sb.append(string);
                    sb.append(",initBizId=");
                    sb.append(SecurityIdentityVerify.this.a);
                    String sb2 = sb.toString();
                    LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "VerifyNotifyIdIllegel", sb2);
                    return true;
                }
            }).getString("resultCode"), GenBusCodeService.CODE_SUCESS)) {
                verifyResult.a = true;
            }
        } catch (TimeoutException e) {
            throw e;
        } catch (Throwable th) {
            verifyResult.a = false;
            LoggerFactory.f().c((String) "buscde", th);
        }
        return verifyResult;
    }
}
