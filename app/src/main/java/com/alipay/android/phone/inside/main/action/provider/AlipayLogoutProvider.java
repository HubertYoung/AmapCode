package com.alipay.android.phone.inside.main.action.provider;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.LogoutCode;
import com.alipay.android.phone.inside.cashier.PhoneCashierPlugin;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.sdk.util.j;
import org.json.JSONObject;

public class AlipayLogoutProvider {
    final Object a = new Object();

    public final OperationResult<LogoutCode> a(String str, JSONObject jSONObject) {
        final OperationResult<LogoutCode> operationResult = new OperationResult<>(LogoutCode.FAILED, str);
        Bundle bundle = new Bundle();
        bundle.putString("order_info", jSONObject.optString("authBizData"));
        LoggerFactory.d().b("main", BehaviorType.EVENT, "BarcodeUnauthStart");
        ServiceExecutor.a(PhoneCashierPlugin.KEY_SERVICE_PAY, bundle, new IInsideServiceCallback<Bundle>() {
            public /* synthetic */ void onComplted(Object obj) {
                String string = ((Bundle) obj).getString(j.a);
                if (TextUtils.equals(AlibcAlipay.PAY_SUCCESS_CODE, string)) {
                    operationResult.setCode(LogoutCode.SUCCESS);
                    new OtpSeedOpProvider();
                    boolean a2 = OtpSeedOpProvider.a();
                    LoggerFactory.d().a("barcode", BehaviorType.EVENT, "BarcodeUnauthDeleteSeed").g = String.valueOf(a2);
                }
                LoggerFactory.d().a("main", BehaviorType.EVENT, "BarcodeUnauthResult").g = string;
                AlipayLogoutProvider.a(AlipayLogoutProvider.this);
            }

            public void onException(Throwable th) {
                AlipayLogoutProvider.a(AlipayLogoutProvider.this);
            }
        });
        try {
            synchronized (this.a) {
                this.a.wait();
            }
        } catch (Throwable th) {
            LoggerFactory.f().b((String) "inside", th);
        }
        return operationResult;
    }

    static /* synthetic */ void a(AlipayLogoutProvider alipayLogoutProvider) {
        synchronized (alipayLogoutProvider.a) {
            alipayLogoutProvider.a.notifyAll();
        }
    }
}
