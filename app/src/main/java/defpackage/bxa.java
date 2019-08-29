package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.search.overlay.SearchUGCTipOverlay;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.ArrayList;

/* renamed from: bxa reason: default package */
/* compiled from: SearchPoiTipTools */
public final class bxa {
    public POI a;
    public int b;
    public View c;
    a d;
    private Context e = null;
    private bty f;
    private SearchUGCTipOverlay g = null;
    private LayoutInflater h;
    private int i;

    /* renamed from: bxa$a */
    /* compiled from: SearchPoiTipTools */
    public interface a {
        void a(POI poi);
    }

    public bxa(Context context, SearchUGCTipOverlay searchUGCTipOverlay, a aVar, bty bty) {
        this.e = context;
        this.f = bty;
        this.g = searchUGCTipOverlay;
        this.h = (LayoutInflater) this.e.getSystemService("layout_inflater");
        this.d = aVar;
        ((GLPointOverlay) this.g.getGLOverlay()).setShowFocusTop(true);
        this.i = -1;
    }

    public final void a(POI poi, int i2) {
        if (poi != null) {
            this.a = poi;
            this.b = i2;
            this.g.clear();
            int i3 = this.b;
            MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, poi.getPoint(), 81);
            if (this.b != -1) {
                mapViewLayoutParams.mode = 0;
                View view = null;
                if (i3 == 1) {
                    this.c = this.h.inflate(R.layout.poi_tip_layout, null);
                    view = this.c.findViewById(R.id.tip_text_container);
                    ((TextView) this.c.findViewById(R.id.text_hot)).setText((String) poi.getPoiExtra().get("tra_tag"));
                    ((TextView) this.c.findViewById(R.id.tip_pop_txt)).setText((String) poi.getPoiExtra().get("tra_title"));
                }
                this.c.measure(0, 0);
                if (view != null) {
                    view.measure(0, 0);
                }
                ArrayList arrayList = new ArrayList();
                aly aly = new aly(this.c.getMeasuredWidth(), view != null ? view.getMeasuredHeight() : 0);
                arrayList.add(aly);
                aly.e = new defpackage.aly.a() {
                    public final void a() {
                        if (bxa.this.d != null) {
                            bxa.this.d.a(bxa.this.a);
                        }
                    }
                };
                ((GLPointOverlay) this.g.getGLOverlay()).setClickList(arrayList);
            }
            GeoPoint point = poi.getPoint();
            PointOverlayItem pointOverlayItem = new PointOverlayItem(point);
            int i4 = this.i + 1;
            this.i = i4;
            if (!(this.c == null || this.f == null)) {
                MapViewLayoutParams mapViewLayoutParams2 = new MapViewLayoutParams(-2, -2, point, 3);
                mapViewLayoutParams2.mode = 0;
                this.f.a(this.c, (LayoutParams) mapViewLayoutParams2);
                pointOverlayItem.mDefaultMarker = this.g.createMarker(i4, this.c, 5, 0.0f, 0.0f, true);
                this.f.a(this.c);
                this.g.addItem(pointOverlayItem);
            }
        }
    }

    public final void a() {
        this.a = null;
        this.b = -1;
        this.g.clear();
    }

    public static int a(POI poi) {
        return !TextUtils.isEmpty((String) poi.getPoiExtra().get("tra_title")) ? 1 : -1;
    }
}
