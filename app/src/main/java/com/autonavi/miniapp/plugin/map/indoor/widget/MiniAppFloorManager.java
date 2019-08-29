package com.autonavi.miniapp.plugin.map.indoor.widget;

import android.view.ViewGroup;

public class MiniAppFloorManager implements MiniAppIFloorManager {
    private final MiniAppIFloorManager mRealFloorManager = ((MiniAppIFloorManager) ank.a(MiniAppIFloorManager.class));

    public void init(cdc cdc) {
    }

    public MiniAppFloorManager(cdc cdc) {
        this.mRealFloorManager.init(cdc);
    }

    public void addFloorWidgetChangedListener(MiniAppFloorWidgetChangedListener miniAppFloorWidgetChangedListener) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.addFloorWidgetChangedListener(miniAppFloorWidgetChangedListener);
        }
    }

    public void removeFloorWidgetChangedListener(MiniAppFloorWidgetChangedListener miniAppFloorWidgetChangedListener) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.removeFloorWidgetChangedListener(miniAppFloorWidgetChangedListener);
        }
    }

    public ami getIndoorBuilding() {
        if (this.mRealFloorManager != null) {
            return this.mRealFloorManager.getIndoorBuilding();
        }
        return null;
    }

    public ami getLastIndoorBuilding() {
        if (this.mRealFloorManager != null) {
            return this.mRealFloorManager.getLastIndoorBuilding();
        }
        return null;
    }

    public boolean isIndoor() {
        if (this.mRealFloorManager != null) {
            return this.mRealFloorManager.isIndoor();
        }
        return false;
    }

    public void setIndoorBuildingToBeActive(String str, int i, String str2) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.setIndoorBuildingToBeActive(str, i, str2);
        }
    }

    public void setIndoorCurrentFloor(String str) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.setIndoorCurrentFloor(str);
        }
    }

    public MiniAppMapIndoorFloor getCurrentMapIndoorFloor() {
        if (this.mRealFloorManager != null) {
            return this.mRealFloorManager.getCurrentMapIndoorFloor();
        }
        return null;
    }

    public MiniAppMapIndoorFloor getMapIndoorFloorByFloorNum(int i) {
        if (this.mRealFloorManager != null) {
            return this.mRealFloorManager.getMapIndoorFloorByFloorNum(i);
        }
        return null;
    }

    public void setCurrentValue(int i) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.setCurrentValue(i);
        }
    }

    public void setCurrentValueByFloorName(String str) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.setCurrentValueByFloorName(str);
        }
    }

    public void setLastIndoorBuildingCurrentFloor(String str, int i, boolean z) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.setLastIndoorBuildingCurrentFloor(str, i, z);
        }
    }

    public void setLastIndoorBuildingCurrentFloor(String str, String str2, boolean z) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.setLastIndoorBuildingCurrentFloor(str, str2, z);
        }
    }

    public String getCurOrLastPoiid() {
        if (this.mRealFloorManager != null) {
            return this.mRealFloorManager.getCurOrLastPoiid();
        }
        return null;
    }

    public boolean isShowFloorWidget() {
        if (this.mRealFloorManager != null) {
            return this.mRealFloorManager.isShowFloorWidget();
        }
        return false;
    }

    public boolean isFloorWidgetVisible() {
        if (this.mRealFloorManager != null) {
            return this.mRealFloorManager.isFloorWidgetVisible();
        }
        return false;
    }

    public void updateFloorWidgetVisibility() {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.updateFloorWidgetVisibility();
        }
    }

    public void updateFloorWidgetVisibility(boolean z) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.updateFloorWidgetVisibility(z);
        }
    }

    public void setFloorWidgetAlpha(float f) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.setFloorWidgetAlpha(f);
        }
    }

    public void indoorBuildingActivity(ami ami) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.indoorBuildingActivity(ami);
        }
    }

    public void setFloorWidgetParent(ViewGroup viewGroup) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.setFloorWidgetParent(viewGroup);
        }
    }

    public void setTipPosition(boolean z) {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.setTipPosition(z);
        }
    }

    public void removeFloorWidgetLayoutWithGuildTip() {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.removeFloorWidgetLayoutWithGuildTip();
        }
    }

    public void updateStateWhenCompassPaint() {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.updateStateWhenCompassPaint();
        }
    }

    public void onResetViewState() {
        if (this.mRealFloorManager != null) {
            this.mRealFloorManager.onResetViewState();
        }
    }

    public boolean onIndoorBuildingActive(ami ami) {
        if (this.mRealFloorManager != null) {
            return this.mRealFloorManager.onIndoorBuildingActive(ami);
        }
        return false;
    }
}
