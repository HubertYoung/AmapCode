package com.autonavi.jni.eyrie.amap.redesign.maps.overlay;

import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay.OverlayType;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PolygonOverlayItem;

public class PolygonOverlay<T extends PolygonOverlayItem> extends BaseOverlay<T> {
    private static native void nativeAddItem(long j, PolygonOverlayItem polygonOverlayItem);

    private static native void nativeRemoveItem(long j, int i);

    private static native void nativeUpdateItem(long j, PolygonOverlayItem polygonOverlayItem);

    public PolygonOverlay(BaseLayer baseLayer, String str) {
        super(baseLayer, str);
    }

    /* access modifiers changed from: protected */
    public void initOverlay() {
        this.mNativePtr = this.mLayer.createNativeOverlay(OverlayType.Polygon, this.mName);
        this.mLayer.addPolygonOverlay(this);
    }

    public void addItem(T t, boolean z) {
        if (t.isValid()) {
            super.addItem(t, z);
            nativeAddItem(this.mNativePtr, t);
            if (z) {
                refresh();
            }
        }
    }

    public void updateItem(T t, boolean z) {
        if (t.isValid()) {
            super.updateItem(t, z);
            nativeUpdateItem(this.mNativePtr, t);
            if (z) {
                refresh();
            }
        }
    }

    public void removeItem(int i, boolean z) {
        super.removeItem(i, z);
        nativeRemoveItem(this.mNativePtr, i);
        if (z) {
            refresh();
        }
    }

    public void removeItem(T t, boolean z) {
        removeItem(t.getID(), z);
    }

    public void addItem(T t) {
        addItem(t, true);
    }

    public void updateItem(T t) {
        updateItem(t, true);
    }

    public void removeItem(int i) {
        removeItem(i, true);
    }
}
