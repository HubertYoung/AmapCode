package defpackage;

import android.graphics.Point;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.bundle.searchcommon.overlay.SearchLineOverlay;
import com.autonavi.bundle.searchcommon.overlay.SearchPolygonOverlay;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.search.overlay.SearchParkPoiOverlay;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: bzc reason: default package */
/* compiled from: SearchParkInfoManager */
public final class bzc {
    SearchLineOverlay a;
    SearchPolygonOverlay b;
    SearchParkPoiOverlay c;
    POI d;
    a e;
    bty f;

    /* renamed from: bzc$a */
    /* compiled from: SearchParkInfoManager */
    public interface a {
        void a(BaseMapOverlay baseMapOverlay, PointOverlayItem pointOverlayItem);
    }

    public bzc(bty bty) {
        this.f = bty;
        if (this.b == null) {
            this.b = new SearchPolygonOverlay(this.f);
        }
        if (this.a == null) {
            this.a = new SearchLineOverlay(this.f);
        }
        if (this.c == null) {
            this.c = new SearchParkPoiOverlay(this.f);
            this.c.setOnItemClickListener(new OnItemClickListener() {
                public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                    if (bzc.this.e != null) {
                        bzc.this.e.a(baseMapOverlay, (PointOverlayItem) obj);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str) {
        if (str != null) {
            this.a.clear();
            try {
                JSONArray jSONArray = new JSONArray(str);
                int length = jSONArray.length();
                if (length > 0) {
                    int[] iArr = new int[length];
                    int[] iArr2 = new int[length];
                    for (int i = 0; i < length; i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        Point a2 = cfg.a(jSONObject.optDouble(DictionaryKeys.CTRLXY_Y), jSONObject.optDouble(DictionaryKeys.CTRLXY_X));
                        iArr[i] = a2.x;
                        iArr2[i] = a2.y;
                    }
                    this.a.showStopLine(iArr, iArr2, 20, 1);
                }
            } catch (Exception unused) {
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(String str) {
        if (str != null) {
            this.a.clear();
            try {
                JSONArray jSONArray = new JSONArray(str);
                int length = jSONArray.length();
                if (length > 0) {
                    for (int i = 0; i < length; i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        double optDouble = jSONObject.optDouble(DictionaryKeys.CTRLXY_X);
                        double optDouble2 = jSONObject.optDouble(DictionaryKeys.CTRLXY_Y);
                        String optString = jSONObject.optString("keytype");
                        Point a2 = cfg.a(optDouble2, optDouble);
                        this.a.showStopLine(new int[]{a2.x, this.d.getPoint().x}, new int[]{a2.y, this.d.getPoint().y}, 8, 0);
                        POI createPOI = POIFactory.createPOI();
                        createPOI.setPoint(new GeoPoint(a2.x, a2.y));
                        if ("0".equals(optString)) {
                            this.c.addPOI(createPOI, R.drawable.mark_park_double);
                        } else if ("1".equals(optString)) {
                            this.c.addPOI(createPOI, R.drawable.mark_park_out);
                        } else {
                            this.c.addPOI(createPOI, R.drawable.mark_park_in);
                        }
                        b();
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    public final void a() {
        if (this.b != null) {
            this.b.clear();
        }
        if (this.a != null) {
            this.a.clear();
        }
        if (this.c != null) {
            this.c.clear();
        }
    }

    private void a(boolean z) {
        if (this.b != null) {
            this.b.setVisible(z);
        }
        if (this.a != null) {
            this.a.setVisible(z);
        }
        if (this.c != null) {
            this.c.setVisible(z);
        }
    }

    public final void b() {
        if ((this.f != null ? this.f.w() : 0) >= 18) {
            a(true);
        } else {
            a(false);
        }
    }
}
