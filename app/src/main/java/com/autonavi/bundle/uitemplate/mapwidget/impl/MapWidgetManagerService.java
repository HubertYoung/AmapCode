package com.autonavi.bundle.uitemplate.mapwidget.impl;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetListener;
import com.autonavi.map.fragmentcontainer.page.IPage;

public class MapWidgetManagerService implements IMapWidgetManagerService {
    private IMapWidgetManager mMapWidgetManager = Stub.getMapWidgetManager();

    public void initialize(ViewGroup viewGroup, LinearLayout linearLayout, ViewGroup viewGroup2) {
        this.mMapWidgetManager.initialize(viewGroup, linearLayout, viewGroup2);
    }

    public int addWidget(IMapWidget... iMapWidgetArr) {
        return this.mMapWidgetManager.addWidget(iMapWidgetArr);
    }

    public int addWidget(IWidgetProperty... iWidgetPropertyArr) {
        return this.mMapWidgetManager.addWidget(iWidgetPropertyArr);
    }

    public int addWidget(IWidgetProperty iWidgetProperty, int i) {
        return this.mMapWidgetManager.addWidget(iWidgetProperty, i);
    }

    public <T extends IMapWidgetPresenter> T getPresenter(String... strArr) {
        return this.mMapWidgetManager.getPresenter(strArr);
    }

    public void removeWidget(IWidgetProperty... iWidgetPropertyArr) {
        this.mMapWidgetManager.removeWidget(iWidgetPropertyArr);
    }

    public void removeWidget(String str) {
        this.mMapWidgetManager.removeWidget(str);
    }

    public void setWidget(IMapWidget... iMapWidgetArr) {
        this.mMapWidgetManager.setWidget(iMapWidgetArr);
    }

    public void setWidget(IWidgetProperty... iWidgetPropertyArr) {
        this.mMapWidgetManager.setWidget(iWidgetPropertyArr);
    }

    public <T extends IPage> void registerListener(String str, WidgetListener<T> widgetListener) {
        this.mMapWidgetManager.registerListener(str, widgetListener);
    }

    public <T extends IPage> void unregisterListener(String str, WidgetListener<T> widgetListener) {
        this.mMapWidgetManager.unregisterListener(str, widgetListener);
    }

    public <T extends IPage> void registerListener(String str, String str2, WidgetListener<T> widgetListener) {
        this.mMapWidgetManager.registerListener(str, str2, widgetListener);
    }

    public <T extends IPage> void unregisterListener(String str, String str2, WidgetListener<T> widgetListener) {
        this.mMapWidgetManager.unregisterListener(str, str2, widgetListener);
    }

    public String getCombineWidgetsTag(String... strArr) {
        return this.mMapWidgetManager.getCombineWidgetsTag(strArr);
    }

    public Context getContext() {
        return this.mMapWidgetManager.getContext();
    }

    public void enterImmersiveMode(Rect rect, String... strArr) {
        this.mMapWidgetManager.enterImmersiveMode(rect, strArr);
    }

    public void enterImmersiveMode(String... strArr) {
        this.mMapWidgetManager.enterImmersiveMode(strArr);
    }

    public void existImmersiveMode(String... strArr) {
        this.mMapWidgetManager.existImmersiveMode(strArr);
    }

    public void setContainerVisible(int i) {
        this.mMapWidgetManager.setContainerVisible(i);
    }

    public void setHeaderVisibility(int i) {
        this.mMapWidgetManager.setHeaderVisibility(i);
    }

    public int getContainerVisible() {
        return this.mMapWidgetManager.getContainerVisible();
    }

    public boolean isNewHomePage() {
        return this.mMapWidgetManager.isNewHomePage();
    }

    public void setWidgetVisibleForType(String str, int i) {
        this.mMapWidgetManager.setWidgetVisibleForType(str, i);
    }

    public int getWidgetVisibleForType(String str) {
        return this.mMapWidgetManager.getWidgetVisibleForType(str);
    }

    public int addWidget(IMapWidget iMapWidget, int i) {
        return this.mMapWidgetManager.addWidget(iMapWidget, i);
    }

    public void setContainerMargin(int i, int i2, int i3, int i4) {
        this.mMapWidgetManager.setContainerMargin(i, i2, i3, i4);
    }

    public MarginLayoutParams getContainerMargin() {
        return this.mMapWidgetManager.getContainerMargin();
    }

    public void setContainerBottomMargin(int i, boolean z) {
        this.mMapWidgetManager.setContainerBottomMargin(i, z);
    }

    public void setContainerTopMargin(int i, boolean z) {
        this.mMapWidgetManager.setContainerTopMargin(i, z);
    }

    public void setContainerPadding(int i, int i2, int i3, int i4) {
        this.mMapWidgetManager.setContainerPadding(i, i2, i3, i4);
    }

    public void setContainerAlpha(float f) {
        this.mMapWidgetManager.setContainerAlpha(f);
    }

    public float getContainerAlpha() {
        return this.mMapWidgetManager.getContainerAlpha();
    }

    public void removeWidget(IMapWidget iMapWidget) {
        this.mMapWidgetManager.removeWidget(iMapWidget);
    }

    public void removeAllWidget() {
        this.mMapWidgetManager.removeAllWidget();
    }

    public void setWidgetsVisibility(boolean z) {
        this.mMapWidgetManager.setWidgetsVisibility(z);
    }

    public void requestContainerLayout() {
        this.mMapWidgetManager.requestContainerLayout();
    }

    public Rect getContainerArea(boolean z) {
        return this.mMapWidgetManager.getContainerArea(z);
    }

    @Nullable
    public IMapWidget findWidgetByWidgetType(String str) {
        return this.mMapWidgetManager.findWidgetByWidgetType(str);
    }

    public void existImmersiveMode() {
        this.mMapWidgetManager.existImmersiveMode();
    }

    public void setWidget(bid bid, IWidgetProperty... iWidgetPropertyArr) {
        this.mMapWidgetManager.setWidget(bid, iWidgetPropertyArr);
    }
}
