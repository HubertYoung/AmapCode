package com.autonavi.miniapp.plugin.map.indoor.widget;

import android.view.ViewGroup;

public interface MiniAppIFloorManager extends a, b, e, MiniAppIFloorWidgetController {
    public static final String SP_KEY_indoor_building_poiid = "indoor_building_poiid";
    public static final String SP_KEY_show_map_indoor_guide = "show_map_indoor_guide";
    public static final String SP_NAME_indoor_config = "indoor_config";

    void addFloorWidgetChangedListener(MiniAppFloorWidgetChangedListener miniAppFloorWidgetChangedListener);

    String getCurOrLastPoiid();

    MiniAppMapIndoorFloor getCurrentMapIndoorFloor();

    ami getIndoorBuilding();

    ami getLastIndoorBuilding();

    MiniAppMapIndoorFloor getMapIndoorFloorByFloorNum(int i);

    void indoorBuildingActivity(ami ami);

    void init(cdc cdc);

    boolean isFloorWidgetVisible();

    boolean isIndoor();

    boolean isShowFloorWidget();

    boolean onIndoorBuildingActive(ami ami);

    void onResetViewState();

    void removeFloorWidgetChangedListener(MiniAppFloorWidgetChangedListener miniAppFloorWidgetChangedListener);

    void removeFloorWidgetLayoutWithGuildTip();

    void setCurrentValue(int i);

    void setCurrentValueByFloorName(String str);

    void setFloorWidgetAlpha(float f);

    void setFloorWidgetParent(ViewGroup viewGroup);

    void setIndoorBuildingToBeActive(String str, int i, String str2);

    void setIndoorCurrentFloor(String str);

    void setLastIndoorBuildingCurrentFloor(String str, int i, boolean z);

    void setLastIndoorBuildingCurrentFloor(String str, String str2, boolean z);

    void setTipPosition(boolean z);

    void updateFloorWidgetVisibility();

    void updateFloorWidgetVisibility(boolean z);

    void updateStateWhenCompassPaint();
}
