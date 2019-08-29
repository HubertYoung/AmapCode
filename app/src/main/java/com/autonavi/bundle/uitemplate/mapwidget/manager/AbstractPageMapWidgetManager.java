package com.autonavi.bundle.uitemplate.mapwidget.manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetListener;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.gps.IGpsStateResume;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

public abstract class AbstractPageMapWidgetManager<T extends AbstractBaseMapPage> extends WidgetListener<T> {
    public final int COMMON_MARGIN_BOTTOM = 24;
    protected final int CONTAINER_PADDING = bet.a(AMapPageUtil.getAppContext(), 5);
    protected IGpsStateResume mGpsStateResume;
    protected T mMapPage;
    protected IWidgetProperty[] mWidgetProperties;

    public abstract IWidgetProperty[] getPageMapWidgets();

    /* access modifiers changed from: protected */
    public void initMapWidgetDelegate() {
    }

    public void onClick(View view, String str) {
    }

    /* access modifiers changed from: protected */
    public void registerListeners() {
    }

    public AbstractPageMapWidgetManager(T t) {
        this.mMapPage = t;
    }

    public void initMapHomePageWidget() {
        initMapWidgetDelegate();
        registerListeners();
    }

    /* access modifiers changed from: protected */
    public void setMapWidgetContainerPadding() {
        int i = this.CONTAINER_PADDING;
        setContainerPadding(i, i, i, -5);
    }

    /* access modifiers changed from: protected */
    public IWidgetProperty getGpsScaleCombineWidget(int i) {
        WidgetProperty widgetProperty = new WidgetProperty(3, Math.max(85, 72), (String) WidgetType.COMBINE, i);
        WidgetProperty widgetProperty2 = new WidgetProperty(2, 85, WidgetType.GPS, 0, null);
        WidgetProperty widgetProperty3 = new WidgetProperty(5, 72, WidgetType.SCALE, 0, setWidgetParams(0, 0, 0, 5));
        return widgetProperty.setCombineWidgets(new IWidgetProperty[]{widgetProperty2, widgetProperty3});
    }

    /* access modifiers changed from: protected */
    public MarginLayoutParams setWidgetParams(int i, int i2, int i3, int i4) {
        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(-2, -2);
        Context appContext = AMapPageUtil.getAppContext();
        marginLayoutParams.leftMargin = bet.a(appContext, i);
        marginLayoutParams.topMargin = bet.a(appContext, i2);
        marginLayoutParams.rightMargin = bet.a(appContext, i3);
        marginLayoutParams.bottomMargin = bet.a(appContext, i4);
        return marginLayoutParams;
    }

    /* access modifiers changed from: protected */
    public MarginLayoutParams setWidgetLeftMargin(int i) {
        return setWidgetParams(i, 0, 0, 0);
    }

    /* access modifiers changed from: protected */
    public MarginLayoutParams setWidgetTopMargin(int i) {
        return setWidgetParams(0, i, 0, 0);
    }

    /* access modifiers changed from: protected */
    public MarginLayoutParams setWidgetRightMargin(int i) {
        return setWidgetParams(0, 0, i, 0);
    }

    /* access modifiers changed from: protected */
    public MarginLayoutParams setWidgetBottomMargin(int i) {
        return setWidgetParams(0, 0, 0, i);
    }

    /* access modifiers changed from: protected */
    public void setContainerPadding(int i, int i2, int i3, int i4) {
        Stub.getMapWidgetManager().setContainerPadding(i, i2, i3, i4);
    }

    public IGpsStateResume getGpsStateResume() {
        return this.mGpsStateResume;
    }
}
