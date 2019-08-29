package com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item;

import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;

public class PointOverlayItem extends BaseOverlayItem {
    public float angle;
    public OverlayTextureParam bgFocusTexture;
    public OverlayTextureParam bgTexture;
    public boolean bgVisible;
    public OverlayTextureParam bubbleFocusTexture;
    public OverlayTextureParam bubbleTexture;
    public boolean bubbleVisible;
    public Coord coord;
    public OverlayTextureParam defaultTexture;
    public OverlayTextureParam focusTexture;
    public boolean iconVisible;
    public boolean isBillboard;
    public int priority;
    public Object tag;

    public PointOverlayItem() {
        this.coord = null;
        this.defaultTexture = null;
        this.focusTexture = null;
        this.bgTexture = null;
        this.bgFocusTexture = null;
        this.bubbleTexture = null;
        this.bubbleFocusTexture = null;
        this.angle = 0.0f;
        this.isBillboard = false;
        this.iconVisible = true;
        this.bgVisible = true;
        this.bubbleVisible = true;
        this.tag = null;
        this.priority = 0;
    }

    public PointOverlayItem(double d, double d2) {
        this();
        this.coord = new Coord(d, d2);
    }

    public PointOverlayItem(double d, double d2, double d3) {
        this();
        Coord coord2 = new Coord(d, d2, d3);
        this.coord = coord2;
    }
}
