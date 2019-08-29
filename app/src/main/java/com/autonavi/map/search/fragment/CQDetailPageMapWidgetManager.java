package com.autonavi.map.search.fragment;

import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.manager.AbstractPageMapWidgetManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.gps.GpsStateResume;

public class CQDetailPageMapWidgetManager extends AbstractPageMapWidgetManager<SearchCQDetailPage> {
    public CQDetailPageMapWidgetManager(SearchCQDetailPage searchCQDetailPage) {
        super(searchCQDetailPage);
        this.mGpsStateResume = new GpsStateResume();
    }

    public IWidgetProperty[] getPageMapWidgets() {
        setMapWidgetContainerPadding();
        if (Stub.getMapWidgetManager().isNewHomePage()) {
            return getNewStyleMapWidgets();
        }
        return getOldStyleMapWidgets();
    }

    private IWidgetProperty[] getNewStyleMapWidgets() {
        if (this.mWidgetProperties == null) {
            WidgetProperty widgetProperty = new WidgetProperty(1, 35, WidgetType.ACTIVITY, 0, setWidgetParams(0, 5, 0, 5));
            WidgetProperty widgetProperty2 = new WidgetProperty(1, 25, WidgetType.COMPASS, 1, setWidgetParams(5, 5, 0, 0));
            WidgetProperty widgetProperty3 = new WidgetProperty(6, 20, WidgetType.ZOOM_IN_OUT, 2, setWidgetBottomMargin(10));
            WidgetProperty widgetProperty4 = new WidgetProperty(6, 85, WidgetType.GPS, 1, setWidgetBottomMargin(24));
            WidgetProperty widgetProperty5 = new WidgetProperty(3, 72, WidgetType.SCALE, 0, setWidgetLeftMargin(5));
            WidgetProperty widgetProperty6 = new WidgetProperty(3, 45, "floor", 3, setWidgetParams(5, 0, 0, 2));
            this.mWidgetProperties = new IWidgetProperty[]{widgetProperty, widgetProperty2, new WidgetProperty(4, 60, (String) WidgetType.LAYER, 1), new WidgetProperty(4, 30, (String) WidgetType.MSG_BOX, 0), widgetProperty3, widgetProperty4, widgetProperty5, new WidgetProperty(3, 90, (String) WidgetType.MINI_SEARCH, 1), new WidgetProperty(3, 40, (String) WidgetType.AUTO_REMOTE, 2), widgetProperty6};
        }
        return this.mWidgetProperties;
    }

    private IWidgetProperty[] getOldStyleMapWidgets() {
        if (this.mWidgetProperties == null) {
            WidgetProperty widgetProperty = new WidgetProperty(1, 35, WidgetType.ACTIVITY, 1, setWidgetParams(0, 5, 0, 10));
            WidgetProperty widgetProperty2 = new WidgetProperty(1, 25, WidgetType.COMPASS, 2, setWidgetLeftMargin(5));
            WidgetProperty widgetProperty3 = new WidgetProperty(4, 80, "diy", 1, setWidgetParams(0, 0, 1, 0));
            WidgetProperty widgetProperty4 = new WidgetProperty(3, 45, "floor", 2, setWidgetLeftMargin(5));
            this.mWidgetProperties = new IWidgetProperty[]{new WidgetProperty(4, 60, (String) WidgetType.LAYER, 0), new WidgetProperty(1, 30, (String) WidgetType.MSG_BOX, 0), widgetProperty, widgetProperty2, widgetProperty3, new WidgetProperty(5, 20, (String) WidgetType.ZOOM_IN_OUT, 0), getGpsScaleCombineWidget(0), new WidgetProperty(3, 40, (String) WidgetType.AUTO_REMOTE, 1), widgetProperty4};
        }
        return this.mWidgetProperties;
    }
}
