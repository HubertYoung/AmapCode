package com.taobao.accs.init;

import android.app.Application;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import java.io.Serializable;
import java.util.HashMap;

public class Launcher_CrossActivityStarted implements Serializable {
    public void init(Application application, HashMap<String, Object> hashMap) {
        try {
            if (ALog.isPrintLog(Level.I)) {
                ALog.i("Launcher_CrossActivityStarted", "onStarted", new Object[0]);
            }
            cm.b();
            if (!TextUtils.isEmpty(Launcher_InitAccs.mAppkey)) {
                if (Launcher_InitAccs.mContext != null) {
                    if (Launcher_InitAccs.mIsInited) {
                        ThreadPoolExecutorFactory.execute(new Runnable() {
                            public void run() {
                                ACCSManager.bindApp(Launcher_InitAccs.mContext, Launcher_InitAccs.mAppkey, Launcher_InitAccs.mTtid, Launcher_InitAccs.mAppReceiver);
                            }
                        });
                    }
                    return;
                }
            }
            ALog.e("Launcher_CrossActivityStarted", "params null!!!", "appkey", Launcher_InitAccs.mAppkey, "context", Launcher_InitAccs.mContext);
        } catch (Throwable th) {
            ALog.e("Launcher_CrossActivityStarted", "onStarted", th, new Object[0]);
            th.printStackTrace();
        }
    }
}
