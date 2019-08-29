package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.OfflineRenderCode;
import com.alipay.android.phone.inside.cashier.PhoneCashierPlugin;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.sdk.util.j;
import org.json.JSONObject;

public class OfflineRenderAction implements SdkAction {
    public final String a() {
        return ActionEnum.OFFLINE_RENDER.getActionName();
    }

    private static String a(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("bizType", "innerOfflinePage");
        bundle.putString("renderData", str);
        try {
            return (String) ServiceExecutor.b(PhoneCashierPlugin.KEY_SERVICE_EXTEND_PARAMS, bundle);
        } catch (Exception e) {
            LoggerFactory.f().b((String) "inside", (Throwable) e);
            return "";
        }
    }

    public final OperationResult<OfflineRenderCode> a(JSONObject jSONObject) {
        OperationResult<OfflineRenderCode> operationResult = new OperationResult<>(OfflineRenderCode.SUCCESS, ActionEnum.OFFLINE_RENDER.getActionName());
        final Object obj = new Object();
        Bundle bundle = new Bundle();
        StringBuilder sb = new StringBuilder("RENDER_LOCAL_DATA_");
        sb.append(System.currentTimeMillis());
        bundle.putString("order_info", sb.toString());
        bundle.putString("extend_params", a(jSONObject.optString("renderData")));
        ServiceExecutor.a(PhoneCashierPlugin.KEY_SERVICE_PAY, bundle, new IInsideServiceCallback<Bundle>() {
            public /* synthetic */ void onComplted(Object obj) {
                Bundle bundle = (Bundle) obj;
                if (bundle != null) {
                    LoggerFactory.d().a("barcode", BehaviorType.EVENT, "ShowOfflinePageResult").g = bundle.getString(j.a);
                }
                synchronized (obj) {
                    obj.notifyAll();
                }
            }

            public void onException(Throwable th) {
                synchronized (obj) {
                    obj.notifyAll();
                }
            }
        });
        try {
            synchronized (obj) {
                obj.wait();
            }
        } catch (Throwable th) {
            LoggerFactory.f().b((String) "inside", th);
        }
        return operationResult;
    }
}
