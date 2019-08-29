package com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item;

public class BaseOverlayItem {
    private final int id = nativeNewOverlayItemGuid();
    public int priority = 0;

    private static native int nativeNewOverlayItemGuid();

    BaseOverlayItem() {
    }

    public int getID() {
        return this.id;
    }
}
