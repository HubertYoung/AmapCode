package com.autonavi.minimap.ajx3.widget.view;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView.ScaleType;
import com.autonavi.minimap.ajx3.IPageLifeCircleView;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.util.ViewUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.ImageProperty;
import com.autonavi.widget.gif.GifImageView;

@SuppressLint({"ViewConstructor"})
public class Image extends GifImageView implements IPageLifeCircleView, ViewExtension {
    private final ImageProperty mProperty;

    public void onNewIntent() {
    }

    public void onPageDestroy() {
    }

    public void onPageStop() {
    }

    public Image(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        setScaleType(ScaleType.FIT_XY);
        this.mProperty = new ImageProperty(this, iAjxContext);
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
        this.mProperty.afterDraw(canvas);
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

    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        ViewUtils.setBackground(this, null);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        postDelayed(new Runnable() {
            public void run() {
                Image.this.invalidate();
            }
        }, 100);
    }

    public void onPageResume() {
        postDelayed(new Runnable() {
            public void run() {
                Image.this.invalidate();
            }
        }, 100);
    }
}
