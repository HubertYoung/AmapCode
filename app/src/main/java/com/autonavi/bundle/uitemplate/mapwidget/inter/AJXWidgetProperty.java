package com.autonavi.bundle.uitemplate.mapwidget.inter;

import android.view.ViewGroup.MarginLayoutParams;
import com.autonavi.bundle.uitemplate.api.IAJXWidgetProperty;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.util.Arrays;
import java.util.List;

public final class AJXWidgetProperty extends WidgetProperty implements IAJXWidgetProperty {
    private int containerRadius;
    private int containerTopBottomPadding;
    private int itemImageSlideLength;
    private int itemLeftRightPadding;
    private int itemTextImageMargin;
    private int itemTextSize = DimensionUtils.dipToPixel(8.0f);
    private int itemTopBottomPadding;
    private List<bel> mWidgetBeans;
    private int minSubWidgetCount = 0;

    public AJXWidgetProperty(int i, int i2, String str, int i3, MarginLayoutParams marginLayoutParams, bel... belArr) {
        super(i, i2, str, i3, marginLayoutParams);
        this.mWidgetBeans = Arrays.asList(belArr);
    }

    public final int getMinSubWidgetCount() {
        return this.minSubWidgetCount;
    }

    public final void setMinSubWidgetCount(int i) {
        this.minSubWidgetCount = i;
    }

    public final List<bel> getWidgetBeans() {
        return this.mWidgetBeans;
    }

    public final boolean isLayoutAdjustable() {
        return this.minSubWidgetCount > 0;
    }

    public final int getItemTextImageMargin() {
        return this.itemTextImageMargin;
    }

    public final void setItemTextImageMargin(int i) {
        this.itemTextImageMargin = i;
    }

    public final int getItemImageSlideLength() {
        return this.itemImageSlideLength;
    }

    public final void setItemImageSlideLength(int i) {
        this.itemImageSlideLength = i;
    }

    public final int getItemTopBottomPadding() {
        return this.itemTopBottomPadding;
    }

    public final void setItemTopBottomPadding(int i) {
        this.itemTopBottomPadding = i;
    }

    public final int getContainerRadius() {
        return this.containerRadius;
    }

    public final void setContainerRadius(int i) {
        this.containerRadius = i;
    }

    public final int getContainerTopBottomPadding() {
        return this.containerTopBottomPadding;
    }

    public final void setContainerTopBottomPadding(int i) {
        this.containerTopBottomPadding = i;
    }

    public final int getItemLeftRightPadding() {
        return this.itemLeftRightPadding;
    }

    public final void setItemLeftRightPadding(int i) {
        this.itemLeftRightPadding = i;
    }

    public final int getItemTextSize() {
        return this.itemTextSize;
    }

    public final void setItemTextSize(int i) {
        this.itemTextSize = i;
    }
}
