package com.autonavi.minimap.basemap.traffic.page;

import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.manager.AbstractPageMapWidgetManager;

public class TrafficPageMapWidgetManager extends AbstractPageMapWidgetManager<TrafficMainMapPage> {
    public TrafficPageMapWidgetManager(TrafficMainMapPage trafficMainMapPage) {
        super(trafficMainMapPage);
    }

    public IWidgetProperty[] getPageMapWidgets() {
        setMapWidgetContainerPadding();
        if (isNewHomePage()) {
            return getNewStyleMapWidgets();
        }
        return getOldStyleMapWidgets();
    }

    public boolean isNewHomePage() {
        IMapWidgetManagerService uITemplateService = getUITemplateService();
        return uITemplateService != null && uITemplateService.isNewHomePage();
    }

    public IMapWidgetManagerService getUITemplateService() {
        return (IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class);
    }

    private IWidgetProperty[] getNewStyleMapWidgets() {
        if (this.mWidgetProperties == null) {
            WidgetProperty widgetProperty = new WidgetProperty(1, 35, WidgetType.ACTIVITY, 0, setWidgetParams(0, 5, 0, 5));
            WidgetProperty widgetProperty2 = new WidgetProperty(1, 25, WidgetType.COMPASS, 1, setWidgetParams(5, 5, 0, 0));
            WidgetProperty widgetProperty3 = new WidgetProperty(3, 72, WidgetType.SCALE, 1, setWidgetParams(5, 0, 0, 10));
            WidgetProperty widgetProperty4 = new WidgetProperty(6, 20, WidgetType.ZOOM_IN_OUT, 0, setWidgetBottomMargin(24));
            this.mWidgetProperties = new IWidgetProperty[]{widgetProperty, widgetProperty2, new WidgetProperty(4, 60, (String) WidgetType.LAYER, 1), new WidgetProperty(4, 30, (String) WidgetType.MSG_BOX, 0), widgetProperty3, widgetProperty4};
        }
        return this.mWidgetProperties;
    }

    private IWidgetProperty[] getOldStyleMapWidgets() {
        if (this.mWidgetProperties == null) {
            WidgetProperty widgetProperty = new WidgetProperty(1, 35, WidgetType.ACTIVITY, 1, setWidgetParams(0, 5, 0, 10));
            WidgetProperty widgetProperty2 = new WidgetProperty(1, 25, WidgetType.COMPASS, 2, setWidgetParams(5, 0, 0, 0));
            WidgetProperty widgetProperty3 = new WidgetProperty(4, 80, "diy", 1, setWidgetParams(0, 0, 1, 0));
            WidgetProperty widgetProperty4 = new WidgetProperty(3, 45, "floor", 2, setWidgetParams(5, 0, 0, 0));
            this.mWidgetProperties = new IWidgetProperty[]{new WidgetProperty(4, 60, (String) WidgetType.LAYER, 0), new WidgetProperty(1, 30, (String) WidgetType.MSG_BOX, 0), widgetProperty, widgetProperty2, widgetProperty3, new WidgetProperty(5, 20, (String) WidgetType.ZOOM_IN_OUT, 0), new WidgetProperty(3, 72, (String) WidgetType.SCALE, 0), widgetProperty4};
        }
        return this.mWidgetProperties;
    }
}
