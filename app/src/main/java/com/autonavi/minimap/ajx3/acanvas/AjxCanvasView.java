package com.autonavi.minimap.ajx3.acanvas;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.autonavi.minimap.acanvas.ACanvasView;
import com.autonavi.minimap.ajx3.IPageLifeCircleView;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.util.SnapshotCanvas;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;

@SuppressLint({"ViewConstructor"})
public class AjxCanvasView extends FrameLayout implements IPageLifeCircleView, ViewExtension {
    private IAjxContext mAjxContext;
    private ACanvasView mCanvasView = new ACanvasView(getContext());
    protected BaseProperty mProperty;

    public void onNewIntent() {
    }

    public void onPageStop() {
    }

    public AjxCanvasView(IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAjxContext = iAjxContext;
        this.mProperty = new AjxCanvasViewProperty(this, iAjxContext);
        addView(this.mCanvasView, new LayoutParams(-1, -1));
    }

    public ACanvasView getCanvasView() {
        return this.mCanvasView;
    }

    public void onPageResume() {
        this.mCanvasView.redraw();
    }

    public void onPageDestroy() {
        AjxCanvasBridgeManager.getSingleton().getBridge(this.mAjxContext).destroyContext2D(this.mCanvasView.getCanvasId());
        this.mCanvasView.release();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        if (canvas instanceof SnapshotCanvas) {
            canvas.drawBitmap(this.mCanvasView.getBitmap(), 0.0f, 0.0f, null);
        }
        this.mProperty.afterDraw(canvas);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
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

    public BaseProperty getProperty() {
        return this.mProperty;
    }
}
