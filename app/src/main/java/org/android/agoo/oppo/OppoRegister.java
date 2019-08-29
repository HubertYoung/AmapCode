package org.android.agoo.oppo;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.BaseNotifyClickActivity;
import org.android.agoo.control.NotifManager;

public class OppoRegister {
    /* access modifiers changed from: private */
    public static Context a;
    private static etb b = new eta() {
        public final void a(int i, String str) {
            if (i == 0) {
                ALog.i("OppoPush", "onRegister regid=".concat(String.valueOf(str)), new Object[0]);
                OppoRegister.a(OppoRegister.a, str);
                return;
            }
            StringBuilder sb = new StringBuilder("onRegister code=");
            sb.append(i);
            sb.append(",regid=");
            sb.append(str);
            ALog.e("OppoPush", sb.toString(), new Object[0]);
        }
    };

    public static void a(Context context, String str, String str2) {
        try {
            Context applicationContext = context.getApplicationContext();
            a = applicationContext;
            if (!UtilityImpl.isMainProcess(applicationContext)) {
                ALog.i("OppoPush", "not in main process, return", new Object[0]);
            } else if (esl.a(a)) {
                BaseNotifyClickActivity.addNotifyListener(new OppoMsgParseImpl());
                etb etb = b;
                ALog.i("OppoPush", "register oppo begin ", new Object[0]);
                esl a2 = esl.a();
                Context context2 = a;
                if (context2 == null) {
                    throw new IllegalArgumentException("context is null !");
                } else if (!esl.a(context2)) {
                    throw new IllegalArgumentException("the phone is not support oppo push!");
                } else {
                    a2.d = str;
                    a2.e = str2;
                    a2.a = context2.getApplicationContext();
                    a2.g = etb;
                    Intent intent = new Intent();
                    intent.setAction("com.coloros.mcssdk.action.RECEIVE_SDK_MESSAGE");
                    intent.setPackage("com.coloros.mcs");
                    intent.putExtra("type", 12289);
                    intent.putExtra("params", "");
                    intent.putExtra("appPackage", a2.a.getPackageName());
                    intent.putExtra("appKey", a2.d);
                    intent.putExtra("appSecret", a2.e);
                    intent.putExtra("registerID", a2.f);
                    intent.putExtra(Constants.KEY_SDK_VERSION, "1.0.1");
                    a2.a.startService(intent);
                }
            } else {
                ALog.i("OppoPush", "not support oppo push", new Object[0]);
            }
        } catch (Throwable th) {
            ALog.e("OppoPush", "register error", th, new Object[0]);
        }
    }

    static /* synthetic */ void a(Context context, String str) {
        if (!TextUtils.isEmpty(str) && context != null) {
            NotifManager notifManager = new NotifManager();
            notifManager.a(context.getApplicationContext());
            notifManager.a(str, "OPPO_TOKEN", false);
        }
    }
}
