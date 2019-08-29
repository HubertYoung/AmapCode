package com.autonavi.jni.eyrie.amap.redesign.maps.overlay;

import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay.OverlayType;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.LineOverlayItem;

public class LineOverlay<T extends LineOverlayItem> extends BaseOverlay<T> {

    public enum LineType {
        LineTypeColor(1),
        LineTypeTexture(2),
        LineTypeArrow(3),
        LineTypeDotTexture(4),
        LineTypeDotColor(5),
        LineTypeDotArrow(6),
        LineTypeLongDotArrow(7);
        
        private final int mValue;

        private LineType(int i) {
            this.mValue = i;
        }

        public final int getValue() {
            return this.mValue;
        }
    }

    private static native void nativeAddItem(long j, LineOverlayItem lineOverlayItem);

    private static native void nativeRemoveItem(long j, int i);

    private static native void nativeUpdateItem(long j, LineOverlayItem lineOverlayItem);

    public LineOverlay(BaseLayer baseLayer, String str) {
        super(baseLayer, str);
    }

    /* access modifiers changed from: protected */
    public void initOverlay() {
        this.mNativePtr = this.mLayer.createNativeOverlay(OverlayType.Line, this.mName);
        this.mLayer.addLineOverlay(this);
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
