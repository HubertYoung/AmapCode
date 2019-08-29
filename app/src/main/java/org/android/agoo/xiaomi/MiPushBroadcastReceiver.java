package org.android.agoo.xiaomi;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.utl.ALog;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import java.util.List;
import org.android.agoo.control.AgooFactory;
import org.android.agoo.control.NotifManager;
import org.android.agoo.message.MessageService;

public class MiPushBroadcastReceiver extends PushMessageReceiver {
    private static final String MI_TOKEN = "MI_TOKEN";
    private static final String TAG = "MiPushBroadcastReceiver";
    private AgooFactory agooFactory;

    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        String command = miPushCommandMessage.getCommand();
        List<String> commandArguments = miPushCommandMessage.getCommandArguments();
        String str = null;
        String str2 = (commandArguments == null || commandArguments.size() <= 0) ? null : commandArguments.get(0);
        if (commandArguments != null && commandArguments.size() > 1) {
            commandArguments.get(1);
        }
        if ("register".equals(command) && miPushCommandMessage.getResultCode() == 0) {
            str = str2;
        }
        ALog.d(TAG, "onReceiveRegisterResult", "regId", str);
        if (!TextUtils.isEmpty(str)) {
            NotifManager notifManager = new NotifManager();
            notifManager.a(context.getApplicationContext());
            notifManager.a(str, MI_TOKEN, true);
            Intent intent = new Intent("com.taobao.android.mipush.token");
            intent.putExtra("token", str);
            context.sendBroadcast(intent);
        }
    }

    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        try {
            String content = miPushMessage.getContent();
            ALog.d(TAG, "onReceivePassThroughMessage", "msg", content);
            if (this.agooFactory == null) {
                this.agooFactory = new AgooFactory();
                this.agooFactory.a(context, (NotifManager) null, (MessageService) null);
            }
            this.agooFactory.a(content.getBytes("UTF-8"), (String) "xiaomi", (ExtraInfo) null);
        } catch (Throwable th) {
            ALog.e(TAG, "onReceivePassThroughMessage", th, new Object[0]);
        }
    }
}
