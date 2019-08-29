package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.IPushConfigService;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;

/* renamed from: dbt reason: default package */
/* compiled from: ShortcutBadgeServiceImpl */
public class dbt implements fhd {
    public final void a(int i) {
        AMapLog.d("IShortcutBadgeService", "加载小红点数：".concat(String.valueOf(i)));
        IPushConfigService iPushConfigService = (IPushConfigService) ank.a(IPushConfigService.class);
        if (iPushConfigService != null) {
            iPushConfigService.a(i);
        }
        if (dbq.a()) {
            ShortcutBadger.with(AMapAppGlobal.getApplication()).countMainLauncher(i);
        }
    }

    public final void a() {
        ShortcutBadger.with(AMapAppGlobal.getApplication()).countMainLauncher(0);
    }
}
