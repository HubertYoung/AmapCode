package com.autonavi.bundle.uitemplate.mapwidget.inter;

import android.support.annotation.NonNull;
import android.view.View;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetPresenter;

public interface IMapWidget<K extends IMapWidgetPresenter> {
    void combineWidgets(IMapWidget<? extends IMapWidgetPresenter>... iMapWidgetArr);

    void enterImmersiveMode();

    void exitImmersiveMode();

    View getContentView();

    String getLogVersionState();

    K getPresenter();

    <T extends IWidgetProperty> T getWidgetProperty();

    void refreshState();

    <T extends IWidgetProperty> IMapWidget<K> setWidgetProperty(@NonNull T t);
}
