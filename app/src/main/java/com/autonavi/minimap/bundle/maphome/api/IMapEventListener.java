package com.autonavi.minimap.bundle.maphome.api;

import android.app.Activity;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPoiDetailDelegate;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IMapEventListener {
    void a();

    void a(Activity activity);

    void a(PageBundle pageBundle, IPoiDetailDelegate iPoiDetailDelegate);
}
