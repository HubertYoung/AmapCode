package com.autonavi.bundle.uitemplate.mapwidget;

import android.content.Context;
import android.graphics.Rect;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetManagerExtend;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetListener;
import com.autonavi.map.fragmentcontainer.page.IPage;

public interface IMapWidgetManagerService extends bie, IMapWidgetManagerExtend {
    int addWidget(IWidgetProperty iWidgetProperty, int i);

    int addWidget(IWidgetProperty... iWidgetPropertyArr);

    int addWidget(IMapWidget... iMapWidgetArr);

    void enterImmersiveMode(Rect rect, String... strArr);

    void enterImmersiveMode(String... strArr);

    void existImmersiveMode(String... strArr);

    String getCombineWidgetsTag(String... strArr);

    int getContainerVisible();

    Context getContext();

    <T extends IMapWidgetPresenter> T getPresenter(String... strArr);

    int getWidgetVisibleForType(String str);

    void initialize(ViewGroup viewGroup, LinearLayout linearLayout, ViewGroup viewGroup2);

    boolean isNewHomePage();

    <T extends IPage> void registerListener(String str, WidgetListener<T> widgetListener);

    <T extends IPage> void registerListener(String str, String str2, WidgetListener<T> widgetListener);

    void removeWidget(String str);

    void removeWidget(IWidgetProperty... iWidgetPropertyArr);

    void setContainerVisible(int i);

    void setHeaderVisibility(int i);

    void setWidget(bid bid, IWidgetProperty... iWidgetPropertyArr);

    void setWidget(IWidgetProperty... iWidgetPropertyArr);

    void setWidget(IMapWidget... iMapWidgetArr);

    void setWidgetVisibleForType(String str, int i);

    <T extends IPage> void unregisterListener(String str, WidgetListener<T> widgetListener);

    <T extends IPage> void unregisterListener(String str, String str2, WidgetListener<T> widgetListener);
}
