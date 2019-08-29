package com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item;

import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import java.util.ArrayList;
import java.util.List;

public class PolygonOverlayItem extends BaseOverlayItem {
    public int fillColor = 0;
    public final List<Coord> points = new ArrayList();

    public boolean isValid() {
        return this.points.size() > 0;
    }
}
