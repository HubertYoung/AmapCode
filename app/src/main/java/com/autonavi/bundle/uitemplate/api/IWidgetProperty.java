package com.autonavi.bundle.uitemplate.api;

import android.support.annotation.NonNull;
import android.view.ViewGroup.MarginLayoutParams;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;

public interface IWidgetProperty extends IMapWidgetProperty {
    int getAlignType();

    String getCombineTag();

    <T extends IWidgetProperty> T[] getCombineWidgetTypes();

    int getIndex();

    JsFunctionCallback getJsFunctionCallback();

    MarginLayoutParams getLayoutParams();

    MarginLayoutParams getOffsetParams();

    int getPriority();

    String getWidgetType();

    boolean isEnable();

    boolean isHaveCombineWidgets();

    boolean isLoadNewWidgetStyle();

    boolean isShowInImmersiveMode();

    boolean isWillBindToPage(String str);

    <T extends IWidgetProperty> IWidgetProperty setCombineWidgets(T[] tArr);

    <T extends IWidgetProperty> T setEnable(boolean z);

    IWidgetProperty setInImmersiveModeVisible(boolean z);

    void setOffsetParams(@NonNull MarginLayoutParams marginLayoutParams);

    IWidgetProperty setWidgetStyle(boolean z);

    IWidgetProperty setWillBindPages(String[] strArr);
}
