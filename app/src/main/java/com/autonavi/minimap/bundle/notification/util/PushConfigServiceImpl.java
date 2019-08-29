package com.autonavi.minimap.bundle.notification.util;

import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.IPushConfigService;

public class PushConfigServiceImpl implements IPushConfigService {
    public final void a(int i) {
        Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("sp_mesbox", 4).edit();
        edit.putInt("key_real_badge_count", i);
        if (VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
    }
}
