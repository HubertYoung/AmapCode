package com.autonavi.map.fragmentcontainer.page;

import android.view.View;
import com.autonavi.ae.gmap.gloverlay.BaseOverlay;
import com.autonavi.bundle.uitemplate.api.IMapWidgetService;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.VMapPage;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.dialog.TipContainer;

public interface IMapPage extends btu, btv, btx, IPage {
    void addOverlay(BaseOverlay baseOverlay);

    void addOverlay(BaseOverlay baseOverlay, boolean z);

    @Deprecated
    void bindMapSuspendView();

    IWidgetProperty[] customPageWidgets();

    void destroyOverlays();

    View getBottomMapInteractiveView();

    TipContainer getBottomTipsContainer();

    MapManager getMapManager();

    bty getMapView();

    brx getMapViewManager();

    IMapWidgetService getPageMapWidgetService();

    cde getSuspendManager();

    ccy getSuspendWidgetHelper();

    View getTopMapInteractiveView();

    VMapPage getVMapPage();

    void onBindMapWidgets();

    void onInitMapWidget();

    void removeOverlay(BaseOverlay baseOverlay);

    void restoreOverlays();

    void saveOverlays();

    void setPageHeader();

    void unBindMapWidgets();

    @Deprecated
    void unbindMapSuspendView();
}
