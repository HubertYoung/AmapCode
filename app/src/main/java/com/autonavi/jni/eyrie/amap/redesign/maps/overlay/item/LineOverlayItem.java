package com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item;

import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.LineOverlay.LineType;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import java.util.ArrayList;
import java.util.List;

public class LineOverlayItem extends BaseOverlayItem {
    public int borderColor;
    public OverlayTextureParam borderTextureParam;
    public int borderWidth;
    public int fillColor;
    public OverlayTextureParam fillTextureParam;
    public int lineWidth;
    public final List<Coord> points;
    private int type;

    public LineOverlayItem() {
        this.type = LineType.LineTypeColor.getValue();
        this.points = new ArrayList();
        this.lineWidth = 0;
        this.borderWidth = 0;
        this.fillColor = 0;
        this.borderColor = 0;
        this.fillTextureParam = null;
        this.borderTextureParam = null;
    }

    public LineOverlayItem(LineType lineType) {
        this();
        this.type = lineType.getValue();
    }

    public void setFillTextureResource(int i) {
        StringBuilder sb = new StringBuilder(OverlayTextureParam.STATIC_TEXTURE_URI_PREFIX);
        sb.append(String.valueOf(i));
        this.fillTextureParam = OverlayTextureParam.make(0, sb.toString(), 0.0f, 0.0f, false, null);
    }

    public void setBorderTextureResource(int i) {
        StringBuilder sb = new StringBuilder(OverlayTextureParam.STATIC_TEXTURE_URI_PREFIX);
        sb.append(String.valueOf(i));
        this.borderTextureParam = OverlayTextureParam.make(0, sb.toString(), 0.0f, 0.0f, false, null);
    }
}
