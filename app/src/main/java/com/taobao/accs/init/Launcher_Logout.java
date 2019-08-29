package com.taobao.accs.init;

import android.app.Application;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.utl.ALog;
import java.io.Serializable;
import java.util.HashMap;

public class Launcher_Logout implements Serializable {
    public void init(Application application, HashMap<String, Object> hashMap) {
        ALog.i("Launcher_Logout", "logout", new Object[0]);
        Launcher_InitAccs.mUserId = null;
        Launcher_InitAccs.mSid = null;
        ACCSManager.unbindUser(application.getApplicationContext());
    }
}
