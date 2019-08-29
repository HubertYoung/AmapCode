package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import java.util.ArrayList;

/* renamed from: drf reason: default package */
/* compiled from: SketchScenicLayer */
public final class drf extends BaseLayer {
    PointOverlay<drh> a;
    @NonNull
    private final Context b;
    @NonNull
    private final bck c;
    private final String d = "redesign://basemap/SketchScenic/";
    private final ArrayList<String> e = new ArrayList<>();

    drf(@NonNull IVPageContext iVPageContext, @NonNull bck bck) {
        super(iVPageContext);
        this.b = iVPageContext.getContext();
        this.c = bck;
        this.a = new PointOverlay<>(this, "GGC");
        this.a.setVisible(!Stub.getMapWidgetManager().isNewHomePage());
    }

    public final void a(SearchPoi searchPoi) {
        if (!this.e.contains(searchPoi.getId())) {
            drh drh = new drh(searchPoi);
            StringBuilder sb = new StringBuilder("redesign://basemap/SketchScenic/");
            sb.append(searchPoi.getId());
            drh.defaultTexture = makeCustomTextureParam(sb.toString(), 0.5f, 0.87f, drh);
            drh.coord = new Coord(searchPoi.getPoint().getLongitude(), searchPoi.getPoint().getLatitude());
            String id = drh.a.getId();
            if (!this.e.contains(id)) {
                this.a.addItem(drh);
                this.e.add(id);
            }
        }
    }

    public final void clear() {
        this.e.clear();
        super.clear();
    }

    public final void onDestroy() {
        this.e.clear();
        super.onDestroy();
    }

    public final TextureWrapper loadTexture(OverlayTextureParam overlayTextureParam) {
        View view;
        if (!overlayTextureParam.uri.startsWith("redesign://basemap/SketchScenic/") || !(overlayTextureParam.data instanceof drh)) {
            return null;
        }
        drh drh = (drh) overlayTextureParam.data;
        bch a2 = this.c.a(this.b);
        if (a2 == null || !(a2 instanceof View)) {
            cjy.c("SketchScenicLayer.makeOverlayView: view is null or not View object");
            view = null;
        } else {
            a2.setTitle(drh.a.getName());
            a2.setDesc(drh.a.getSketchDuration());
            view = (View) a2;
        }
        if (view == null) {
            return null;
        }
        return makeTextureWrapper(view);
    }
}
