package com.autonavi.minimap.route.ajx.module.bus;

import android.support.annotation.NonNull;
import com.autonavi.bundle.busnavi.ajx.ModuleBus.a;

public interface IBusNaviDetailModuleBridge {

    public interface Callback<T> {
        void callback(T... tArr);
    }

    void accessFootOrRideNavi(dxa dxa);

    void accessReroute();

    void accessUgc();

    void calcDistanceToCurrentPath(@NonNull Callback<Integer> callback);

    boolean isFavorite();

    void onExchangeAlterRoute(dwy dwy);

    void onListStatusChange(String str);

    void selectBusRoute(int i);

    void showBusNaviDisclaimer(@NonNull Callback callback);

    void switchRealtimeBusEnable(boolean z);

    void triggerFavor(a aVar);

    void triggerFeedback();

    void triggerShare();

    void updateBusRemindStatus(boolean z);
}
