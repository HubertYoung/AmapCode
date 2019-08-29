package com.autonavi.minimap.ajx3.widget.view;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.util.AjxALCLog;
import com.autonavi.minimap.ajx3.util.PerformanceUtil;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;

@SuppressLint({"ViewConstructor"})
public class Container extends AbsoluteLayout implements ViewExtension {
    private IAjxContext mAjxContext;
    private boolean mIsFirstDrawChild = true;
    private boolean mIsLogedDrawTime = false;
    private boolean mIsRootView = false;
    private final BaseProperty mProperty;

    public Container(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mProperty = new BaseProperty(this, iAjxContext);
        this.mAjxContext = iAjxContext;
        setClickable(true);
    }

    public Container(@NonNull IAjxContext iAjxContext, boolean z) {
        super(iAjxContext.getNativeContext());
        this.mProperty = new BaseProperty(this, iAjxContext);
        this.mIsRootView = z;
        this.mAjxContext = iAjxContext;
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j) {
        boolean drawChild = super.drawChild(canvas, view, j);
        if (this.mIsRootView && this.mIsFirstDrawChild) {
            this.mIsFirstDrawChild = false;
            StringBuilder sb = new StringBuilder("native draw first node ");
            sb.append(this.mAjxContext.getJsPath());
            sb.append(".");
            AjxALCLog.info(AjxALCLog.TAG_PERFORMANCE, sb.toString());
            Ajx.getInstance().addTimestamp(PerformanceUtil.FIRST_DRAW);
            this.mAjxContext.getDomTree().getRootView().onLoadingDismiss();
        }
        if (!this.mIsLogedDrawTime && this.mProperty.mLogDrawTime) {
            this.mAjxContext.getDomTree().getRootView().onChildViewShow();
            Ajx.getInstance().addTimestamp(PerformanceUtil.VIEW_SHOW);
            LogManager.performaceLog(PerformanceUtil.VIEW_SHOW);
            this.mIsLogedDrawTime = true;
        }
        return drawChild;
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        measureChildren(i, i2);
        setMeasuredDimension(resolveSizeAndState(MeasureSpec.getSize(i), i, 0), resolveSizeAndState(MeasureSpec.getSize(i2), i2, 0));
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

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }
}
