package com.autonavi.bundle.uitemplate.mapwidget.inter;

import android.graphics.Rect;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.ViewGroup.MarginLayoutParams;

public interface IMapWidgetManagerExtend {
    int addWidget(IMapWidget iMapWidget, int i);

    void existImmersiveMode();

    @Nullable
    IMapWidget findWidgetByWidgetType(String str);

    float getContainerAlpha();

    Rect getContainerArea(boolean z);

    @Nullable
    MarginLayoutParams getContainerMargin();

    void removeAllWidget();

    void removeWidget(IMapWidget iMapWidget);

    void requestContainerLayout();

    void setContainerAlpha(@FloatRange(from = 0.0d, to = 1.0d) float f);

    void setContainerBottomMargin(int i, boolean z);

    void setContainerMargin(int i, int i2, int i3, int i4);

    void setContainerPadding(int i, int i2, int i3, int i4);

    void setContainerTopMargin(int i, boolean z);

    void setWidgetsVisibility(boolean z);
}
