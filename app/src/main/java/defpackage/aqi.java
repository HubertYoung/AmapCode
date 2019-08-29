package defpackage;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCMPackageInfo;

/* renamed from: aqi reason: default package */
/* compiled from: MainVoiceOperationService */
public final class aqi implements czn {
    a a = null;

    /* renamed from: aqi$a */
    /* compiled from: MainVoiceOperationService */
    public interface a {
        bru a();

        bty b();
    }

    private static boolean a(int i) {
        return (i & 1) != 1;
    }

    public final void setVoiceOperationServiceDelegate(czn czn) {
    }

    public aqi(a aVar) {
        this.a = aVar;
    }

    public final boolean voiceOpenTraffic() {
        return a(true);
    }

    public final boolean voiceCloseTraffic() {
        return a(false);
    }

    private boolean a(boolean z) {
        bru a2 = this.a.a();
        bty b = this.a.b();
        if (b == null || !b.k() || a2 == null) {
            return false;
        }
        Boolean a3 = bru.a();
        boolean s = b.s();
        if (!((a3 == null || a3.booleanValue() == z) && s == z)) {
            a2.a(z, false, false);
        }
        return true;
    }

    public final int voiceZoomInMainMap() {
        bty b = this.a.b();
        if (b == null || !b.k()) {
            return SDKFactory.getCoreType;
        }
        if (b.v() + 1.0f > ((float) b.l())) {
            return UCMPackageInfo.getKernelResFiles;
        }
        b.x();
        return 10000;
    }

    public final int voiceZoomOutMainMap() {
        bty b = this.a.b();
        if (b == null || !b.k()) {
            return SDKFactory.getCoreType;
        }
        if (b.v() - 1.0f < ((float) b.m())) {
            return UCMPackageInfo.sortByLastModified;
        }
        b.y();
        return 10000;
    }

    public final float[] voiceMixMaxZoom() {
        float[] fArr = new float[3];
        bty b = this.a.b();
        if (b != null && b.k()) {
            fArr[0] = (float) b.m();
            fArr[1] = (float) b.l();
            fArr[2] = b.v();
        }
        return fArr;
    }

    public final int voiceZoomInDiffMainMap(float f) {
        bty b = this.a.b();
        if (b == null || !b.k()) {
            return SDKFactory.getCoreType;
        }
        float v = b.v() + f;
        if (v > ((float) b.l())) {
            b.e(b.l());
            return UCMPackageInfo.getKernelResFiles;
        }
        b.d(v);
        return 10000;
    }

    public final int voiceZoomOutDiffMainMap(float f) {
        bty b = this.a.b();
        if (b == null || !b.k()) {
            return SDKFactory.getCoreType;
        }
        float v = b.v() - Math.abs(f);
        if (v < ((float) b.m())) {
            b.e(b.m());
            return UCMPackageInfo.sortByLastModified;
        }
        b.d(v);
        return 10000;
    }

    public final void moveMapView(int i, int i2) {
        bty b = this.a.b();
        if (b != null && b.k()) {
            IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
            if (iMainMapService != null && iMainMapService.a()) {
                cde b2 = iMainMapService.b();
                MapManager d = iMainMapService.d();
                if (b2 != null && d != null) {
                    cdz d2 = b2.d();
                    if (d2 != null) {
                        d2.f();
                        GeoPoint a2 = cfg.a(b, GeoPoint.glGeoPoint2GeoPoint(b.n()), i, i2, b.v(), (int) b.I());
                        double longitude = a2.getLongitude() / 180.0d;
                        int ceil = (int) Math.ceil(longitude);
                        if (longitude > 1.0d) {
                            if (a(ceil)) {
                                a2.setLonLat((a2.getLongitude() - ((double) (ceil * 180))) - 180.0d, a2.getLatitude());
                            } else {
                                a2.setLonLat(a2.getLongitude() - ((double) (ceil * 180)), a2.getLatitude());
                            }
                        }
                        if (longitude < -1.0d) {
                            if (a(ceil)) {
                                a2.setLonLat(180.0d - Math.abs(a2.getLongitude() + ((double) (ceil * 180))), a2.getLatitude());
                            } else {
                                a2.setLonLat(-(a2.getLongitude() + ((double) (ceil * 180))), a2.getLatitude());
                            }
                        }
                        b.a(a2.x, a2.y);
                    }
                }
            }
        }
    }
}
