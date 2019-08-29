package com.alipay.android.phone.inside.proxy.util;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;

public class ServerTimeSyncUtil {

    public interface Action<T> {
    }

    public static void a() {
        ServiceExecutor.a("BARCODE_PLUGIN_AYNC_SERCER_TIME", null, new IInsideServiceCallback<Bundle>() {
            public final /* synthetic */ void onComplted(Object obj) {
                boolean z = ((Bundle) obj).getBoolean("success", false);
                LoggerFactory.d().b("auth", BehaviorType.EVENT, "AyncServerTime|".concat(String.valueOf(z)));
                if (Action.this != null) {
                    Boolean.valueOf(z);
                }
                LoggerFactory.f().b((String) "inside", "success:".concat(String.valueOf(z)));
            }

            public final void onException(Throwable th) {
                LoggerFactory.f().c((String) "inside", th);
            }
        });
    }
}
