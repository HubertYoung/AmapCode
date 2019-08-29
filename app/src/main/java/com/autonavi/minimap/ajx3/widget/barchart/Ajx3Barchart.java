package com.autonavi.minimap.ajx3.widget.barchart;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import com.amap.bundle.commonui.barchart.BarChartView;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;

@SuppressLint({"ViewConstructor"})
public class Ajx3Barchart extends BarChartView implements ViewExtension {
    protected BaseProperty mAttribute;

    public Ajx3Barchart(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAttribute = new Ajx3BarchartProperty(this, iAjxContext);
    }

    public BaseProperty getProperty() {
        return this.mAttribute;
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mAttribute.bind(ajxDomNode);
    }

    public void bindStrictly(long j) {
        this.mAttribute.bindStrictly(j);
    }

    public void updateDiffProperty() {
        this.mAttribute.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mAttribute.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mAttribute.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mAttribute.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mAttribute.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mAttribute.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mAttribute.getAttribute(str);
    }
}
