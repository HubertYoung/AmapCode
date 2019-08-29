package com.autonavi.bundle.amaphome.widget;

import android.text.TextUtils;
import android.view.View;
import com.autonavi.bundle.amaphome.page.MapHomePage;
import com.autonavi.bundle.amaphome.page.MapHomeTabPage;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.gps.GpsWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.layer.LayerWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.weather.WeatherRestrictWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.zoom.IZoomWidgetEventDelegate;
import com.autonavi.bundle.uitemplate.mapwidget.widget.zoom.ZoomWidgetPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.util.DimensionUtils;

public class MapHomePageWidgetManager extends BaseHomePageWidgetManager<MapHomePage> {
    private final int GUIDE_BOTTOM_MARGIN = 26;
    private final String ROUTE_BUTTON_HIDDEN = "route_button_hidden";

    public MapHomePageWidgetManager(MapHomePage mapHomePage) {
        super(mapHomePage);
    }

    public void initMapWidgetDelegate() {
        super.initMapWidgetDelegate();
        setGpsWidgetDelegate();
        setZoomWidgetEventDelegate();
    }

    private void setGpsWidgetDelegate() {
        GpsWidgetPresenter gpsWidgetPresenter = (GpsWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.GPS);
        if (gpsWidgetPresenter != null && gpsWidgetPresenter.getEventDelegate() == null) {
            gpsWidgetPresenter.setEventDelegate(new arz(this));
        }
    }

    /* access modifiers changed from: protected */
    public void setZoomWidgetEventDelegate() {
        IZoomWidgetEventDelegate iZoomWidgetEventDelegate = ((MapHomePage) this.mMapPage).q;
        if (iZoomWidgetEventDelegate != null) {
            ZoomWidgetPresenter zoomWidgetPresenter = (ZoomWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.ZOOM_IN_OUT);
            if (zoomWidgetPresenter != null) {
                zoomWidgetPresenter.setEventDelegate(iZoomWidgetEventDelegate);
            }
        }
    }

    public IWidgetProperty[] getPageMapWidgets() {
        setMapWidgetContainerPadding();
        if (this.mWidgetProperties == null) {
            WidgetProperty widgetProperty = new WidgetProperty(1, 35, WidgetType.ACTIVITY, 0, setWidgetParams(0, 5, 0, 5));
            WidgetProperty widgetProperty2 = new WidgetProperty(1, 25, WidgetType.COMPASS, 1, setWidgetParams(5, 5, 0, 0));
            WidgetProperty widgetProperty3 = new WidgetProperty(6, 20, WidgetType.ZOOM_IN_OUT, 2, setWidgetBottomMargin(10));
            WidgetProperty widgetProperty4 = new WidgetProperty(3, 72, WidgetType.SCALE, 0, setWidgetLeftMargin(5));
            WidgetProperty widgetProperty5 = new WidgetProperty(3, 70, WidgetType.WEATHER_RESTRICT, 1, setWidgetRightMargin(5));
            WidgetProperty widgetProperty6 = new WidgetProperty(3, 45, "floor", 3, setWidgetParams(5, 0, 0, 2));
            WidgetProperty widgetProperty7 = new WidgetProperty(8, 55, WidgetType.INDOOR_GUIDE, 0, setWidgetBottomMargin(26));
            WidgetProperty widgetProperty8 = new WidgetProperty(8, 50, WidgetType.SCENIC_AREA, 1, setWidgetBottomMargin(26));
            this.mWidgetProperties = new IWidgetProperty[]{new WidgetProperty(4, 60, (String) WidgetType.LAYER, 1), new WidgetProperty(4, 30, (String) WidgetType.MSG_BOX, 0), widgetProperty, widgetProperty2, widgetProperty3, new WidgetProperty(6, 85, (String) WidgetType.GPS, 1), cloudConfigRouteLine(), widgetProperty4, widgetProperty5, new WidgetProperty(3, 40, (String) WidgetType.AUTO_REMOTE, 2), widgetProperty6, widgetProperty7, widgetProperty8};
        }
        return this.mWidgetProperties;
    }

    public void responseNearbyData(String str) {
        IMapWidgetManagerService iMapWidgetManagerService = (IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class);
        WeatherRestrictWidgetPresenter weatherRestrictWidgetPresenter = iMapWidgetManagerService != null ? (WeatherRestrictWidgetPresenter) iMapWidgetManagerService.getPresenter(WidgetType.WEATHER_RESTRICT) : null;
        if (weatherRestrictWidgetPresenter != null) {
            weatherRestrictWidgetPresenter.responseData(str);
        }
    }

    public int getCCardContainerWidth() {
        if (((MapHomePage) this.mMapPage).getMapView().al() <= 500) {
            return ags.a(AMapPageUtil.getAppContext()).right - (DimensionUtils.dipToPixel(10.0f) * 2);
        }
        return ((MapHomePage) this.mMapPage).getMapView().al() - (DimensionUtils.dipToPixel(10.0f) * 2);
    }

    public void registerListeners() {
        Stub.getMapWidgetManager().registerListener(WidgetType.LAYER, this);
    }

    public void dispatchClickEvent(View view, String str) {
        if (((str.hashCode() == 102749521 && str.equals(WidgetType.LAYER)) ? (char) 0 : 65535) == 0) {
            LayerWidgetPresenter.logClick();
            aqw aqw = ((aro) ((MapHomePage) this.mMapPage).mPresenter).b;
            if (aqw != null && !aqw.c()) {
                aqw.a();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doGpsAction() {
        MapHomePage mapHomePage = (MapHomePage) this.mMapPage;
        PanelState panelState = mapHomePage.r != null ? mapHomePage.r.getPanelState() : null;
        bez.b("redesign-gps-click", "cur panel state:".concat(String.valueOf(panelState)), new bew[0]);
        if (panelState != null && panelState != PanelState.DRAGGING) {
        }
    }

    public void setGpsOverlayProjectCenter() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext == null) {
            return;
        }
        if ((TextUtils.equals(MapHomePage.class.getSimpleName(), pageContext.getClass().getSimpleName()) || TextUtils.equals(MapHomeTabPage.class.getSimpleName(), pageContext.getClass().getSimpleName())) && this.mMapPage != null) {
            ((MapHomePage) this.mMapPage).a(false);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0021  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x001f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty cloudConfigRouteLine() {
        /*
            r7 = this;
            lo r0 = defpackage.lo.a()
            java.lang.String r1 = "route_button_hidden"
            java.lang.String r0 = r0.a(r1)
            r1 = 0
            if (r0 == 0) goto L_0x001b
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x001b }
            r2.<init>(r0)     // Catch:{ Exception -> 0x001b }
            java.lang.String r0 = "route_button_hidden"
            int r0 = r2.optInt(r0, r1)     // Catch:{ Exception -> 0x001b }
            goto L_0x001c
        L_0x001b:
            r0 = 0
        L_0x001c:
            r1 = 1
            if (r0 != r1) goto L_0x0021
            r0 = 0
            return r0
        L_0x0021:
            com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty r0 = new com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty
            r2 = 6
            r3 = 100
            java.lang.String r4 = "route_line"
            r5 = 0
            r1 = 24
            android.view.ViewGroup$MarginLayoutParams r6 = r7.setWidgetBottomMargin(r1)
            r1 = r0
            r1.<init>(r2, r3, r4, r5, r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.amaphome.widget.MapHomePageWidgetManager.cloudConfigRouteLine():com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty");
    }

    public void updateWidgetContainerPadding(int i) {
        setContainerPadding(this.CONTAINER_PADDING, this.CONTAINER_PADDING, this.CONTAINER_PADDING, i);
    }

    public int getSlideAnchorHeight() {
        if (this.mMapPage != null) {
            return ((MapHomePage) this.mMapPage).o;
        }
        return 262;
    }

    public String getPageSimpleName() {
        if (this.mMapPage != null) {
            ((MapHomePage) this.mMapPage).getClass().getSimpleName();
        }
        return "MapHomePage";
    }
}
