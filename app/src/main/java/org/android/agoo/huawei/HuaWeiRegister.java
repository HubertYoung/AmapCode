package org.android.agoo.huawei;

import android.content.Context;
import android.os.Build;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.huawei.android.pushagent.PushReceiver;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.BaseNotifyClickActivity;

public class HuaWeiRegister {
    private static String a = Build.BRAND;

    public static void a(final Context context) {
        try {
            if (UtilityImpl.isMainProcess(context)) {
                boolean z = false;
                if (a.equalsIgnoreCase(DeviceProperty.ALIAS_HUAWEI) || a.equalsIgnoreCase("honor")) {
                    z = true;
                }
                if (z) {
                    BaseNotifyClickActivity.addNotifyListener(new HuaweiMsgParseImpl());
                    new Thread(new Runnable() {
                        public final void run() {
                            PushReceiver.getToken(context);
                        }
                    }).start();
                }
            }
        } catch (Throwable th) {
            th.getMessage();
        }
    }
}
