package com.autonavi.minimap.bundle.agroup.api;

import android.support.annotation.NonNull;
import com.autonavi.common.PageBundle;

public interface IAgroupOverlayService {

    public enum AgroupScenes {
        Navigation,
        RouteCarResult,
        MapHome,
        SearchCQDetail,
        TrafficMainMap,
        BusRideRemind,
        DestMap,
        FootResult
    }

    public enum MemberIconStyle {
        BIG_DAY,
        BIG_NIGHT,
        MID_DAY,
        SMALL_DAY,
        SMALL_NIGHT
    }

    void a(MemberIconStyle memberIconStyle);

    void a(cuj cuj);

    void a(@NonNull Class<?> cls);

    void a(@NonNull Class<?> cls, AgroupScenes agroupScenes, PageBundle pageBundle, boolean z);

    void a(boolean z);

    void c(boolean z);

    void d(boolean z);

    void f();

    void i();
}
