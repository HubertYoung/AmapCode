package com.autonavi.minimap.route.ajx.inter;

public interface OnAjxRideNaviInterface {
    void onExitPage();

    void onNotifyChange(String str, String str2, boolean z);

    void onVoiceStatusChange(boolean z);

    void onVoiceToast(boolean z);
}
