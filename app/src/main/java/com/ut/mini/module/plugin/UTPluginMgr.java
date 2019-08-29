package com.ut.mini.module.plugin;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.ut.mini.module.appstatus.UTAppStatusCallbacks;
import com.ut.mini.module.appstatus.UTAppStatusRegHelper;
import java.util.LinkedList;
import java.util.List;

public class UTPluginMgr implements UTAppStatusCallbacks {
    private static UTPluginMgr s_instance = new UTPluginMgr();
    private List<UTPlugin> mPluginList = new LinkedList();

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onSwitchBackground() {
    }

    public void onSwitchForeground() {
    }

    private UTPluginMgr() {
        if (VERSION.SDK_INT >= 14) {
            UTAppStatusRegHelper.registerAppStatusCallbacks(this);
        }
    }

    public static UTPluginMgr getInstance() {
        return s_instance;
    }

    public void forEachPlugin(IUTPluginForEachDelegate iUTPluginForEachDelegate) {
        if (iUTPluginForEachDelegate != null) {
            try {
                for (UTPlugin onPluginForEach : this.mPluginList) {
                    iUTPluginForEachDelegate.onPluginForEach(onPluginForEach);
                }
            } catch (Throwable unused) {
            }
        }
    }

    public synchronized void registerPlugin(UTPlugin uTPlugin) {
        if (!this.mPluginList.contains(uTPlugin)) {
            this.mPluginList.add(uTPlugin);
        }
    }

    public synchronized void unregisterPlugin(UTPlugin uTPlugin) {
        this.mPluginList.remove(uTPlugin);
    }
}
