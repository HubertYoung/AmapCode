package com.autonavi.minimap.drive.navi.navitts.PPHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment;
import java.io.File;
import java.lang.ref.WeakReference;

public class PPApkInstallReceiver extends BroadcastReceiver {
    public dgl a;
    private String b;
    private WeakReference<OfflineNaviTtsFragment> c;

    public PPApkInstallReceiver(OfflineNaviTtsFragment offlineNaviTtsFragment, dgl dgl, String str) {
        this.a = dgl;
        this.b = str;
        this.c = new WeakReference<>(offlineNaviTtsFragment);
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String dataString = intent.getDataString();
            if (!TextUtils.isEmpty(dataString) && dataString.contains("com.pp.assistant")) {
                dfr.a(context, this.a);
                OfflineNaviTtsFragment offlineNaviTtsFragment = (OfflineNaviTtsFragment) this.c.get();
                if (offlineNaviTtsFragment != null) {
                    b(offlineNaviTtsFragment, this);
                }
                if (!TextUtils.isEmpty(this.b)) {
                    File file = new File(this.b);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }
    }

    public static void a(OfflineNaviTtsFragment offlineNaviTtsFragment, BroadcastReceiver broadcastReceiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        offlineNaviTtsFragment.getContext().registerReceiver(broadcastReceiver, intentFilter);
        offlineNaviTtsFragment.mIsPPInstallReceiverRegister = true;
    }

    public static void b(OfflineNaviTtsFragment offlineNaviTtsFragment, BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null) {
            offlineNaviTtsFragment.getContext().unregisterReceiver(broadcastReceiver);
            offlineNaviTtsFragment.mIsPPInstallReceiverRegister = false;
        }
    }
}
