package com.autonavi.minimap.ajx3.widget.view;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v4.util.LongSparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.ShadowView;
import java.util.List;

public class AjxAbsoluteLayout extends AbsoluteLayout implements ViewExtension {
    protected final BaseProperty mProperty;
    private LongSparseArray<View> mViewMap = new LongSparseArray<>();

    public void bind(AjxDomNode ajxDomNode) {
    }

    public void bindStrictly(long j) {
    }

    public Object getAttribute(String str) {
        return null;
    }

    public float getSize(String str) {
        return 0.0f;
    }

    public Object getStyle(int i) {
        return null;
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
    }

    public void updateDiffProperty() {
    }

    public AjxAbsoluteLayout(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mProperty = new BaseProperty(this, iAjxContext);
        this.mProperty.setEventspenetrate(true);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mProperty.onLayout(z, i, i2, i3, i4);
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int i6 = layoutParams.x;
                int i7 = layoutParams.y;
                childAt.layout(i6, i7, layoutParams.width + i6, layoutParams.height + i7);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    public void saveView(View view, long j) {
        this.mViewMap.put(j, view);
    }

    public void addView(View view, AjxDomNode ajxDomNode) {
        super.addView(view);
        saveViewToMap(this.mViewMap, ajxDomNode);
    }

    public View findViewByNodeId(long j) {
        return (View) this.mViewMap.get(j, null);
    }

    public void saveScrollerView(AjxDomNode ajxDomNode, AjxDomNode ajxDomNode2) {
        if (ajxDomNode2 != null) {
            removeViewFromMap(ajxDomNode2.getId());
            this.mViewMap.put(ajxDomNode2.getId(), ajxDomNode2.getEnterView());
        }
        if (ajxDomNode != null && ajxDomNode.getChildren() != null) {
            for (AjxDomNode saveViewToMap : ajxDomNode.getChildren()) {
                saveViewToMap(this.mViewMap, saveViewToMap);
            }
        }
    }

    private void removeViewFromMap(long j) {
        this.mViewMap.remove(j);
    }

    private void saveViewToMap(LongSparseArray<View> longSparseArray, AjxDomNode ajxDomNode) {
        if (ajxDomNode != null) {
            if (ajxDomNode.getEnterView() != null) {
                longSparseArray.put(ajxDomNode.getId(), ajxDomNode.getEnterView());
            }
            List<AjxDomNode> children = ajxDomNode.getChildren();
            if (children != null && children.size() > 0) {
                for (AjxDomNode saveViewToMap : children) {
                    saveViewToMap(longSparseArray, saveViewToMap);
                }
            }
        }
    }

    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    public View getRealChildAt(int i) {
        while (i < getChildCount()) {
            View childAt = super.getChildAt(i);
            if (childAt == null) {
                return null;
            }
            if (!(childAt instanceof ShadowView)) {
                return childAt;
            }
            i++;
        }
        return null;
    }
}
