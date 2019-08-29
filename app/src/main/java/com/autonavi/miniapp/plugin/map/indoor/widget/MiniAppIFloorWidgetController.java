package com.autonavi.miniapp.plugin.map.indoor.widget;

public interface MiniAppIFloorWidgetController {
    void addFloorWidgetChangedListener(MiniAppFloorWidgetChangedListener miniAppFloorWidgetChangedListener);

    MiniAppMapIndoorFloor getCurrentMapIndoorFloor();

    ami getIndoorBuilding();

    MiniAppMapIndoorFloor getMapIndoorFloorByFloorNum(int i);

    void indoorBuildingActivity(ami ami);

    boolean isFloorWidgetVisible();

    boolean isIndoor();

    boolean isShowFloorWidget();

    void removeFloorWidgetChangedListener(MiniAppFloorWidgetChangedListener miniAppFloorWidgetChangedListener);

    void setCurrentValue(int i);

    void setCurrentValueByFloorName(String str);

    void setFloorWidgetAlpha(float f);

    void setIndoorBuildingToBeActive(String str, int i, String str2);

    void setIndoorCurrentFloor(String str);

    void updateFloorWidgetVisibility();

    void updateFloorWidgetVisibility(boolean z);
}
