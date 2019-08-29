package org.android.agoo.vivo;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;
import org.android.agoo.control.NotifManager;

public class PushMessageReceiverImpl extends OpenClientPushMessageReceiver {
    public final void a(Context context, String str) {
        if (!TextUtils.isEmpty(str) && context != null) {
            ALog.d("PushMessageReceiver", "onReceiveRegId", "token", str);
            NotifManager notifManager = new NotifManager();
            notifManager.a(context.getApplicationContext());
            notifManager.a(str, "VIVO_TOKEN", false);
        }
    }

    public final void a(ezq ezq) {
        long j = ezq.q;
        ALog.d("PushMessageReceiver", "onNotificationMessageClicked", "customMsgId", Long.valueOf(j), "customMsgContent", ezq.n);
    }
}
