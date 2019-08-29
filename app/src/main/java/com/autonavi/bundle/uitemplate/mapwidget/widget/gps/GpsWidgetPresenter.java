package com.autonavi.bundle.uitemplate.mapwidget.widget.gps;

import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;

public class GpsWidgetPresenter extends BaseMapWidgetPresenter<GPSMapWidget> {
    public void initialize(GPSMapWidget gPSMapWidget) {
        super.initialize(gPSMapWidget);
        DoNotUseTool.getSuspendManager().d().a(getGpsButton(), new cee() {
            public void onClickDoing() {
            }

            public void onClickEnd() {
            }

            public void onClickBefore() {
                IGpsMapWidgetDelegate iGpsMapWidgetDelegate = (IGpsMapWidgetDelegate) GpsWidgetPresenter.this.getEventDelegate();
                if (iGpsMapWidgetDelegate != null) {
                    iGpsMapWidgetDelegate.doClickBeforeAction();
                }
            }
        });
    }

    public ced getGpsButton() {
        if (this.mBindWidget != null) {
            return (GpsMapView) ((GPSMapWidget) this.mBindWidget).getContentView();
        }
        return null;
    }

    public boolean isGpsBtnInFollowingState() {
        if (isWidgetNotNull()) {
            GpsMapView gpsMapView = (GpsMapView) ((GPSMapWidget) this.mBindWidget).getContentView();
            if (gpsMapView == null || 12 != gpsMapView.getCurGPSBtnState()) {
                return false;
            }
            return true;
        }
        return false;
    }
}
