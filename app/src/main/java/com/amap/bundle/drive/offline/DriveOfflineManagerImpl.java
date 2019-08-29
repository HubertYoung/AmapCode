package com.amap.bundle.drive.offline;

import android.net.NetworkInfo;
import com.amap.bundle.logs.AMapLog;

public class DriveOfflineManagerImpl implements IDriveOfflineManager {
    public void init() {
        oh.a().b();
    }

    public void destroy() {
        oh a = oh.a();
        AMapLog.e("DriveOfflineManager", "destroy");
        a.d = false;
        a.e = false;
        a.f = false;
        a.g = -1;
        a.b = -1;
        a.c = null;
        lo.a().b("3d_cross", a.i);
        lo.a().b("_user_profile_", a.k);
        lt.a().b(a.j);
        a.h.removeCallbacksAndMessages(null);
        a.a.nativeDestroy();
    }

    public void onConnectionChanged(NetworkInfo networkInfo) {
        oh.a().a.onConnectionChanged(networkInfo);
    }
}
