package org.android.agoo.huawei;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.huawei.android.pushagent.PushReceiver;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.utl.UtilityImpl;
import org.android.agoo.control.AgooFactory;
import org.android.agoo.control.NotifManager;
import org.android.agoo.message.MessageService;

public class HuaWeiReceiver extends PushReceiver {
    private static final String HUAWEI_TOKEN = "HW_TOKEN";
    private final String TAG = "accs.HuaWeiReceiver";
    private AgooFactory agooFactory;

    public void onPushMsg(Context context, byte[] bArr, String str) {
        try {
            new String(bArr, "UTF-8");
            this.agooFactory = new AgooFactory();
            this.agooFactory.a(context, (NotifManager) null, (MessageService) null);
            this.agooFactory.a(bArr, (String) DeviceProperty.ALIAS_HUAWEI, (ExtraInfo) null);
        } catch (Throwable unused) {
        }
    }

    public void onToken(Context context, String str) {
        try {
            if (UtilityImpl.isMainProcess(context) && !TextUtils.isEmpty(str)) {
                NotifManager notifManager = new NotifManager();
                notifManager.a(context.getApplicationContext());
                notifManager.a(str, HUAWEI_TOKEN, true);
            }
        } catch (Throwable th) {
            new StringBuilder("onToken error: ").append(th.toString());
        }
    }
}
