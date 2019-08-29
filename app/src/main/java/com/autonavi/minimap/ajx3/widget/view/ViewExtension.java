package com.autonavi.minimap.ajx3.widget.view;

import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;

public interface ViewExtension {
    void bind(AjxDomNode ajxDomNode);

    void bindStrictly(long j);

    Object getAttribute(String str);

    BaseProperty getProperty();

    float getSize(String str);

    Object getStyle(int i);

    void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4);

    void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4);

    void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4);

    void updateDiffProperty();
}
