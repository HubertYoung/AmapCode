package com.autonavi.jni.eyrie.amap.redesign.maps.overlay;

import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay.OverlayType;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem;

public class PointOverlay<T extends PointOverlayItem> extends BaseOverlay<T> {
    private static native void nativeAddItem(long j, PointOverlayItem pointOverlayItem);

    private static native void nativeClearFocus(long j);

    private static native int nativeGetFocusItemID(long j);

    private static native boolean nativeIsAutoMoveToFocus(long j);

    private static native void nativeRemoveItem(long j, int i);

    private static native void nativeSetAutoFocus(long j, boolean z);

    private static native void nativeSetAutoMoveToFocus(long j, boolean z);

    private static native void nativeSetCheckCover(long j, boolean z);

    private static native void nativeSetFocus(long j, int i);

    private static native void nativeSetReversed(long j, boolean z);

    private static native void nativeSetShowOnTop(long j, boolean z);

    private static native void nativeUpdateItem(long j, PointOverlayItem pointOverlayItem);

    public PointOverlay(BaseLayer baseLayer, String str) {
        super(baseLayer, str);
    }

    /* access modifiers changed from: protected */
    public void initOverlay() {
        this.mNativePtr = this.mLayer.createNativeOverlay(OverlayType.Point, this.mName);
        this.mLayer.addPointOverlay(this);
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

    public void addItem(T t, boolean z) {
        super.addItem(t, z);
        nativeAddItem(this.mNativePtr, t);
        if (z) {
            refresh();
        }
    }

    public void updateItem(T t, boolean z) {
        super.updateItem(t, z);
        nativeUpdateItem(this.mNativePtr, t);
        if (z) {
            refresh();
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

    public T getFocusItem() {
        return (PointOverlayItem) this.mItemMap.get(Integer.valueOf(nativeGetFocusItemID(this.mNativePtr)));
    }

    public void clearFocus() {
        nativeClearFocus(this.mNativePtr);
    }

    public void setFocus(int i) {
        nativeSetFocus(this.mNativePtr, i);
    }

    public void setCheckCover(boolean z) {
        nativeSetCheckCover(this.mNativePtr, z);
    }

    public void setReversed(boolean z) {
        nativeSetReversed(this.mNativePtr, z);
    }

    public void setAutoFocus(boolean z) {
        nativeSetAutoFocus(this.mNativePtr, z);
    }

    public void setAutoMoveToFocus(boolean z) {
        nativeSetAutoMoveToFocus(this.mNativePtr, z);
    }

    public boolean isAutoMoveToFocus() {
        return nativeIsAutoMoveToFocus(this.mNativePtr);
    }

    public void setItemVisble(T t, boolean z, boolean z2, boolean z3) {
        t.iconVisible = z;
        t.bgVisible = z2;
        t.bubbleVisible = z3;
        updateItem(t);
    }

    public void setShowOnTop(boolean z) {
        nativeSetShowOnTop(this.mNativePtr, z);
    }
}
