package com.autonavi.minimap.drive.navi.navitts.PPHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment;

public class PPDownloadResultReceiver extends BroadcastReceiver {
    private OfflineNaviTtsFragment a;

    public PPDownloadResultReceiver(OfflineNaviTtsFragment offlineNaviTtsFragment) {
        this.a = offlineNaviTtsFragment;
    }

    public void onReceive(Context context, Intent intent) {
        if (1 == intent.getIntExtra("ex_result", 0)) {
            String stringExtra = intent.getStringExtra("ex_path");
            String stringExtra2 = intent.getStringExtra("ex_task_id");
            if (this.a != null && !TextUtils.isEmpty(stringExtra) && !TextUtils.isEmpty(stringExtra2)) {
                this.a.onReceivePPHelperDownloadFinishBroadcast(stringExtra, stringExtra2);
            }
        }
    }

    public static void a(Context context, BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null) {
            context.registerReceiver(broadcastReceiver, new IntentFilter("intent.action.PP_HIGH_SPEED_RESULT"));
        }
    }

    public static void b(Context context, BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null) {
            context.unregisterReceiver(broadcastReceiver);
        }
    }
}
