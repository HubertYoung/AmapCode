package com.autonavi.common.model;

import android.support.annotation.Nullable;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class GeoPoint extends GLGeoPoint implements Serializable, Cloneable {
    private static final long serialVersionUID = 927014135610245467L;
    private transient a ext;
    private transient a ext3d;
    public int x3D;
    public int y3D;
    public int z3D;

    static class a {
        int a;
        int b;
        double c;
        double d;
        int e;

        a(int i, int i2, double d2, double d3) {
            this.a = i;
            this.b = i2;
            this.d = d2;
            this.c = d3;
        }

        /* access modifiers changed from: 0000 */
        public final void a(int i, int i2, double d2, double d3) {
            this.a = i;
            this.b = i2;
            this.d = d2;
            this.c = d3;
            this.e = 0;
        }
    }

    public GeoPoint() {
    }

    public GeoPoint(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public GeoPoint(GLGeoPoint gLGeoPoint) {
        if (gLGeoPoint != null) {
            this.x = gLGeoPoint.x;
            this.y = gLGeoPoint.y;
        }
    }

    public GeoPoint(double d, double d2, double d3, double d4, int i) {
        this(d, d2, d3, d4, i, true);
    }

    public GeoPoint(double d, double d2, double d3, double d4, int i, boolean z) {
        latLonToPixels(d2, d, 20);
        latLon3DToPixels(d4, d3, 20);
        if (z) {
            a aVar = new a(this.x, this.y, d2, d);
            this.ext = aVar;
            a aVar2 = new a(this.x3D, this.y3D, d4, d3);
            this.ext3d = aVar2;
        }
        this.z3D = i;
    }

    public GeoPoint(double d, double d2) {
        this(d, d2, true);
    }

    public GeoPoint(double d, double d2, boolean z) {
        latLonToPixels(d2, d, 20);
        if (z) {
            a aVar = new a(this.x, this.y, d2, d);
            this.ext = aVar;
        }
    }

    public void setGeoPoint3D(int i, int i2, int i3) {
        this.x3D = i;
        this.y3D = i2;
        this.z3D = i3;
    }

    public void setGeoPoint3D(double d, double d2, int i) {
        latLon3DToPixels(d2, d, 20);
        this.z3D = i;
    }

    public void setGeoPoint3D(double d, double d2, int i, boolean z) {
        latLon3DToPixels(d2, d, 20);
        if (z) {
            a aVar = new a(this.x3D, this.y3D, d2, d);
            this.ext3d = aVar;
        }
        this.z3D = i;
    }

    public double getLongitude() {
        return getlonLat().c;
    }

    public double getLatitude() {
        return getlonLat().d;
    }

    public double getLongitude3D() {
        return getlonLat3D().c;
    }

    public double getLatitude3D() {
        return getlonLat3D().d;
    }

    public GeoPoint setLonLat(double d, double d2) {
        latLonToPixels(d2, d, 20);
        if (this.ext == null) {
            a aVar = new a(this.x, this.y, d2, d);
            this.ext = aVar;
        } else {
            this.ext.a(this.x, this.y, d2, d);
        }
        return this;
    }

    public int getAdCode() {
        int i;
        if (this.ext != null && this.ext.a == this.x && this.ext.b == this.y && this.ext.e != 0) {
            return this.ext.e;
        }
        if (ml.a == null) {
            i = 0;
        } else {
            i = ml.a.a(this.x, this.y);
        }
        if (this.ext != null && this.ext.a == this.x && this.ext.b == this.y) {
            this.ext.e = i;
        }
        return i;
    }

    public static GeoPoint glGeoPoint2GeoPoint(GLGeoPoint gLGeoPoint) {
        if (gLGeoPoint == null) {
            return null;
        }
        return new GeoPoint(gLGeoPoint);
    }

    public static GLGeoPoint geoPoint2GlGeoPoint(GeoPoint geoPoint) {
        if (geoPoint == null) {
            return null;
        }
        return new GLGeoPoint(geoPoint.x, geoPoint.y);
    }

    public static GLGeoPoint[] geoPoints2GlGeoPoints(GeoPoint[] geoPointArr) {
        if (geoPointArr == null) {
            return null;
        }
        int length = geoPointArr.length;
        GLGeoPoint[] gLGeoPointArr = new GLGeoPoint[length];
        for (int i = 0; i < length; i++) {
            gLGeoPointArr[i] = geoPoint2GlGeoPoint(geoPointArr[i]);
        }
        return gLGeoPointArr;
    }

    public GeoPoint clone() {
        return new GeoPoint(this.x, this.y);
    }

    @Nullable
    public String toJson() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(DictionaryKeys.CTRLXY_X, this.x);
            jSONObject.put(DictionaryKeys.CTRLXY_Y, this.y);
            jSONObject.put("z", (double) this.z);
            jSONObject.put("x3D", this.x3D);
            jSONObject.put("y3D", this.y3D);
            jSONObject.put("z3D", this.z3D);
            if (this.ext != null) {
                jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, this.ext.c);
                jSONObject.put("lat", this.ext.d);
            }
            if (this.ext3d != null) {
                jSONObject.put("lon3D", this.ext3d.c);
                jSONObject.put("lat3D", this.ext3d.d);
            }
            jSONObject.put(AutoJsonUtils.JSON_ADCODE, getAdCode());
            jSONObject.put("cityCode", getCity());
            jSONObject.put("province", getProvince());
            return jSONObject.toString();
        } catch (JSONException unused) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void latLonToPixels(double d, double d2, int i) {
        cob.a(this, d, d2, i);
    }

    /* access modifiers changed from: protected */
    public DPoint pixelsToLatLon(long j, long j2, int i) {
        return cob.a(j, j2, i);
    }

    private a getlonLat3D() {
        if (this.ext3d != null && this.ext3d.a == this.x3D && this.ext3d.b == this.y3D) {
            return this.ext3d;
        }
        DPoint pixelsToLatLon = pixelsToLatLon((long) this.x3D, (long) this.y3D, 20);
        if (this.ext3d == null) {
            a aVar = new a(this.x3D, this.y3D, pixelsToLatLon.y, pixelsToLatLon.x);
            this.ext3d = aVar;
        } else {
            this.ext3d.a(this.x3D, this.y3D, pixelsToLatLon.y, pixelsToLatLon.x);
        }
        return this.ext3d;
    }

    private a getlonLat() {
        if (this.ext != null && this.ext.a == this.x && this.ext.b == this.y) {
            return this.ext;
        }
        DPoint pixelsToLatLon = pixelsToLatLon((long) this.x, (long) this.y, 20);
        if (this.ext == null) {
            a aVar = new a(this.x, this.y, pixelsToLatLon.y, pixelsToLatLon.x);
            this.ext = aVar;
        } else {
            this.ext.a(this.x, this.y, pixelsToLatLon.y, pixelsToLatLon.x);
        }
        return this.ext;
    }

    public boolean inMainland() {
        return ml.a == null || ml.a.a(getAdCode());
    }

    public String getCity() {
        if (ml.a == null) {
            return null;
        }
        return ml.a.c(getAdCode());
    }

    public String getProvince() {
        if (ml.a == null) {
            return null;
        }
        return ml.a.b(getAdCode());
    }

    /* access modifiers changed from: protected */
    public void latLon3DToPixels(double d, double d2, int i) {
        DPoint a2 = cob.a(d, d2, i);
        this.x3D = (int) a2.x;
        this.y3D = (int) a2.y;
    }
}
