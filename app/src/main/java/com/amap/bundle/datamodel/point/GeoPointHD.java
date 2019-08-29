package com.amap.bundle.datamodel.point;

import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.utils.GLMapUtil;
import com.autonavi.minimap.map.DPoint;

public class GeoPointHD extends GeoPoint {
    public GeoPointHD() {
    }

    public GeoPointHD(int i, int i2) {
        super(i, i2);
    }

    public GeoPointHD(GLGeoPoint gLGeoPoint) {
        super(gLGeoPoint);
    }

    public GeoPointHD(double d, double d2, double d3, double d4, int i) {
        super(d, d2, d3, d4, i);
    }

    public GeoPointHD(double d, double d2, double d3, double d4, int i, boolean z) {
        super(d, d2, d3, d4, i, z);
    }

    public GeoPointHD(double d, double d2) {
        super(d, d2);
    }

    public GeoPointHD(double d, double d2, boolean z) {
        super(d, d2, z);
    }

    public void latLonToPixels(double d, double d2, int i) {
        int[] lonLatToPixel = GLMapUtil.lonLatToPixel(d2, d, i);
        if (lonLatToPixel == null) {
            cjy.c("GeoPointHD.latLonToPixels: xy is null!");
            return;
        }
        this.x = lonLatToPixel[0];
        this.y = lonLatToPixel[1];
    }

    public void latLon3DToPixels(double d, double d2, int i) {
        int[] lonLatToPixel = GLMapUtil.lonLatToPixel(d2, d, i);
        if (lonLatToPixel == null) {
            cjy.c("GeoPointHD.latLon3DToPixels: xy is null!");
            return;
        }
        this.x3D = lonLatToPixel[0];
        this.y3D = lonLatToPixel[1];
    }

    public DPoint pixelsToLatLon(long j, long j2, int i) {
        double[] pixelToLonLat = GLMapUtil.pixelToLonLat((int) j, (int) j2, i);
        if (pixelToLonLat != null) {
            return new DPoint(pixelToLonLat[0], pixelToLonLat[1]);
        }
        cjy.c("GeoPointHD.pixelsToLatLon: xy is null!");
        return null;
    }
}
