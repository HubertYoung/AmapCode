package com.autonavi.map.fragmentcontainer.page.mappage;

import android.support.annotation.NonNull;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.minimap.map.overlayholder.AjxOverlayPage;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.map.overlayholder.OverlayUtil;
import java.util.ArrayList;

public class UniversalOverlayManager {
    private IOverlayManager mOverlayManager;
    private OverlayPageProperty mOverlayProperties;
    private int mSaveOverlayFocusKey;

    public UniversalOverlayManager(@NonNull IOverlayManager iOverlayManager) {
        this.mOverlayManager = iOverlayManager;
    }

    public void onBackPressed() {
        this.mOverlayManager.clearAllFocus();
    }

    public void resumeUniversalOverlayCareConfig(IMapPage iMapPage) {
        OvProperty[] overlays;
        OvProperty[] overlays2;
        OverlayPageProperty defaultPageProperty = OverlayUtil.getDefaultPageProperty();
        OverlayPageProperty overlayPageProperty = (OverlayPageProperty) iMapPage.getClass().getAnnotation(OverlayPageProperty.class);
        if (overlayPageProperty == null && (iMapPage instanceof AjxOverlayPage)) {
            overlayPageProperty = OverlayUtil.getAjxPageProperty(((AjxOverlayPage) iMapPage).getUniversalOverlayConfig());
        }
        ArrayList arrayList = new ArrayList();
        if (overlayPageProperty != null) {
            for (OvProperty ovProperty : overlayPageProperty.overlays()) {
                if (ovProperty != null) {
                    setOverlayProperty(ovProperty);
                    arrayList.add(ovProperty.overlay());
                }
            }
        }
        for (OvProperty ovProperty2 : defaultPageProperty.overlays()) {
            if (ovProperty2 != null && !arrayList.contains(ovProperty2.overlay())) {
                setOverlayProperty(ovProperty2);
            }
        }
    }

    private void setOverlayProperty(OvProperty ovProperty) {
        xq selectOverlayByEnum = selectOverlayByEnum(ovProperty.overlay());
        if (selectOverlayByEnum != null) {
            OverlayUtil.setOverlayProperty(selectOverlayByEnum, ovProperty);
        }
    }

    private void setOverlayProperty(OverlayPageProperty overlayPageProperty) {
        OvProperty[] overlays;
        if (overlayPageProperty != null && overlayPageProperty.overlays() != null) {
            for (OvProperty ovProperty : overlayPageProperty.overlays()) {
                if (ovProperty != null) {
                    xq selectOverlayByEnum = selectOverlayByEnum(ovProperty.overlay());
                    if (selectOverlayByEnum != null) {
                        OverlayUtil.setOverlayProperty(selectOverlayByEnum, ovProperty);
                    }
                }
            }
        }
    }

    private xq selectOverlayByEnum(UvOverlay uvOverlay) {
        switch (uvOverlay) {
            case GpsOverlay:
                return this.mOverlayManager.getGpsLayer();
            case MapPointOverlay:
                return this.mOverlayManager.getMapPointOverlay();
            case GeoCodeOverlay:
                return this.mOverlayManager.getGeoCodeOverlay();
            case LocalReportOverlay:
            case SaveOverlay:
                return this.mOverlayManager.getUniversalOverlay(uvOverlay);
            default:
                return null;
        }
    }

    public void pauseUniversalOverlay() {
        setOverlayProperty(OverlayUtil.getDefaultPageProperty());
    }

    public void saveUniversalOverlayFocus() {
        this.mSaveOverlayFocusKey = this.mOverlayManager.saveFocus();
        this.mOverlayManager.clearAllFocus();
    }

    public void resumeUniversalOverlayFocus() {
        this.mOverlayManager.solveSavedFocusWithKey(this.mSaveOverlayFocusKey, true);
    }
}
