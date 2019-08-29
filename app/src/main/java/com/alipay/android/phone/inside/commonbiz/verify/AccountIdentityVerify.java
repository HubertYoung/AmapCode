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
import com.alipay.mobile.security.zim.plugin.ZIMH5Plugin;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import java.util.HashMap;

public class AccountIdentityVerify {
    /* access modifiers changed from: private */
    public String a;
    /* access modifiers changed from: private */
    public String b;
    private String c;

    public class VerifyResult {
        public boolean a;

        public VerifyResult() {
        }
    }

    private VerifyResult b(Context context, String str, String str2, String str3) throws TimeoutException {
        JumpAlipaySchemeProvider jumpAlipaySchemeProvider = new JumpAlipaySchemeProvider();
        HashMap hashMap = new HashMap();
        hashMap.put("bizId", str2);
        hashMap.put(ZIMH5Plugin.ZIM_IDENTIFY_VERIFYID, str3);
        hashMap.put("verifyMode", this.c);
        VerifyResult verifyResult = new VerifyResult();
        try {
            if (TextUtils.equals(jumpAlipaySchemeProvider.jumpScheme(context, JumpAlipaySchemeProvider.BIZ_VERITY, str, hashMap, new INotifyChecker() {
                public boolean illegel(Bundle bundle) {
                    if (bundle == null) {
                        return false;
                    }
                    if (!bundle.getBoolean("insideFlag", false)) {
                        LoggerFactory.e().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, "VerifyNotifyInsideFlagIllegel");
                        return true;
                    }
                    String string = bundle.getString("bizId");
                    String string2 = bundle.getString(ZIMH5Plugin.ZIM_IDENTIFY_VERIFYID);
                    if (TextUtils.equals(string, AccountIdentityVerify.this.a) && TextUtils.equals(string2, AccountIdentityVerify.this.b)) {
                        return false;
                    }
                    StringBuilder sb = new StringBuilder("bizId=");
                    sb.append(string);
                    sb.append(",verifyId=");
                    sb.append(string2);
                    sb.append(",initBizId=");
                    sb.append(AccountIdentityVerify.this.a);
                    sb.append(",initVerifyId=");
                    sb.append(AccountIdentityVerify.this.b);
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

    public final boolean a(Context context, String str, String str2, String str3) throws Exception {
        try {
            String a2 = RandamUtil.a();
            this.a = a2;
            this.b = str3;
            this.c = str2;
            return b(context, str, a2, str3).a;
        } catch (TimeoutException e) {
            throw e;
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return false;
        }
    }
}
