package org.android.agoo.vivo;

import android.content.Context;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.BaseNotifyClickActivity;

public class VivoRegister {
    private static Context a;

    public static void a(Context context) {
        if (context != null) {
            try {
                a = context.getApplicationContext();
                if (!UtilityImpl.isMainProcess(context)) {
                    ALog.i("VivoRegister", "not in main process, return", new Object[0]);
                    return;
                }
                ALog.d("VivoRegister", "register start", new Object[0]);
                BaseNotifyClickActivity.addNotifyListener(new VivoMsgParseImpl());
                eww.a(context);
                eww.a();
                eww.a(context);
                eww.a((ewu) new ewu() {
                    public final void a(int i) {
                        ALog.d("VivoRegister", "turnOnPush", "state", Integer.valueOf(i));
                    }
                });
            } catch (Throwable th) {
                ALog.e("VivoRegister", "register", th, new Object[0]);
            }
        }
    }
}
