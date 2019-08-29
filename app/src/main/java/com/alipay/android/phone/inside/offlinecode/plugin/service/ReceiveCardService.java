package com.alipay.android.phone.inside.offlinecode.plugin.service;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.offlinecode.plugin.utils.Utils;
import com.alipay.android.phone.inside.wallet.JumpAlipaySchemeProvider;
import com.alipay.android.phone.inside.wallet.model.NotifyResult;
import com.alipay.android.phone.inside.wallet.model.TimeoutException;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import java.util.HashMap;

public class ReceiveCardService extends AbstractInsideService<Bundle, Bundle> {
    static final String CODE_FAILED = "FAILED";
    static final String CODE_SUCCESS = "SUCCESS";
    static final String CODE_TIMEOUT = "TIMEOUT";

    public Bundle startForResult(Bundle bundle) throws Exception {
        Context context = getContext();
        String string = bundle.getString("cardType", "");
        String string2 = bundle.getString("cardNo", "");
        String loginId = Utils.getLoginId();
        LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "BusReceiveCardType").g = string;
        String str = "FAILED";
        try {
            Bundle bundle2 = jumpAlipayScheme(context, string, string2, loginId).getBundle("receiveResult");
            if (bundle2 != null && TextUtils.equals(bundle2.getString("result"), "success")) {
                LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "BusReceiveCardTypeSuccess").g = string;
                str = "SUCCESS";
            }
        } catch (TimeoutException unused) {
            str = "TIMEOUT";
        }
        Bundle bundle3 = new Bundle();
        bundle3.putString("code", str);
        return bundle3;
    }

    private NotifyResult jumpAlipayScheme(Context context, String str, String str2, String str3) throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put("cardType", str);
        hashMap.put("appName", getAppLabel(context));
        return new JumpAlipaySchemeProvider().jumpScheme(context, JumpAlipaySchemeProvider.BIZ_RECEIVECARD, str3, hashMap);
    }

    private String getAppLabel(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0)).toString();
        } catch (Exception e) {
            LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "GetAppLabelEx", (Throwable) e);
            r5 = "";
            return "";
        }
    }
}
