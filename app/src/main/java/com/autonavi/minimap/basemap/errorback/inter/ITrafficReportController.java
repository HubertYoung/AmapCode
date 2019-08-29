package com.autonavi.minimap.basemap.errorback.inter;

import android.app.Activity;
import android.app.Dialog;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.widget.ITrafficViewForFeed;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface ITrafficReportController {
    Dialog a(Activity activity);

    Dialog a(Activity activity, PageBundle pageBundle, MapManager mapManager);

    ITrafficViewForFeed a(Activity activity, PageBundle pageBundle, MapManager mapManager, a aVar);

    void a();

    void a(bid bid);
}
