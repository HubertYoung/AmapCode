package defpackage;

import android.view.MotionEvent;
import com.autonavi.ae.gmap.gloverlay.BaseOverlay;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.bundle.searchcommon.overlay.SearchPolygonOverlay;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import java.util.ArrayList;

/* renamed from: apy reason: default package */
/* compiled from: PolygonOverlayManager */
public final class apy {
    ArrayList<ArrayList<GeoPoint>> a = new ArrayList<>();
    bbp b;
    private arn c;

    public apy(arn arn) {
        this.c = arn;
        ((awb) a.a.a(awb.class)).a((MainMapEventListener) new awc() {
            public final void onLongPress(MotionEvent motionEvent) {
                super.onLongPress(motionEvent);
                apy.this.b();
            }

            public final boolean onSingleTapUp(MotionEvent motionEvent) {
                apy.this.b();
                return false;
            }

            public final void onMapLevelChange(boolean z) {
                super.onMapLevelChange(z);
                apy apy = apy.this;
                bty a2 = apy.a();
                if (a2 != null) {
                    int w = a2.w();
                    if (apy.b != null && apy.a.size() > 0) {
                        boolean z2 = true;
                        if (w > 13) {
                            bbp bbp = apy.b;
                            boolean isVisible = bbp.b != null ? bbp.b.isVisible() : false;
                            boolean isVisible2 = bbp.a != null ? bbp.a.isVisible() : false;
                            if (!isVisible && !isVisible2) {
                                z2 = false;
                            }
                            if (z2) {
                                apy.b.a(false);
                            }
                        } else {
                            if (!apy.b.d()) {
                                apy.b.b();
                            }
                            if (w == 13) {
                                if (apy.b.c()) {
                                    apy.b.b(false);
                                }
                            } else if (!apy.b.c()) {
                                apy.b.b(true);
                            }
                        }
                    }
                }
            }
        });
    }

    public final boolean a(PageBundle pageBundle) {
        if (pageBundle == null || !pageBundle.containsKey("key_coords")) {
            return false;
        }
        ArrayList arrayList = (ArrayList) pageBundle.get("key_coords");
        if (this.b == null) {
            this.b = new bbp(a());
            a((BaseOverlay) this.b.a);
            a((BaseOverlay) this.b.b);
        }
        this.a.clear();
        this.a.addAll(arrayList);
        this.b.b.mPolygonColor = SearchPolygonOverlay.AD_POLYGON_COLOR;
        this.b.a(arrayList);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final bty a() {
        if (this.c == null) {
            return null;
        }
        MapManager mapManager = DoNotUseTool.getMapManager();
        if (mapManager == null) {
            return null;
        }
        return mapManager.getMapView();
    }

    private void a(BaseOverlay baseOverlay) {
        this.c.addOverlay(baseOverlay);
    }

    public final void b() {
        if (this.b != null) {
            this.b.a();
        }
        if (this.a.size() > 0) {
            this.a.clear();
        }
    }

    public final void c() {
        if (this.b != null && !this.a.isEmpty()) {
            aho.a(new Runnable() {
                public final void run() {
                    if (apy.this.b != null && !apy.this.a.isEmpty()) {
                        apy.this.b.a(apy.this.a);
                    }
                }
            });
        }
    }
}
