package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.bundle.life.api.entity.ScenicPlayRouteItemEntity;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.IClickListener;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.BaseOverlayItem;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi;

/* renamed from: drd reason: default package */
/* compiled from: ScenicPlayLayerHelper */
public class drd {
    private static final String c = "drd";
    public final drc a;
    public bty b;
    private final AbstractBaseMapPage d;

    /* renamed from: drd$a */
    /* compiled from: ScenicPlayLayerHelper */
    class a implements IClickListener {
        private a() {
        }

        /* synthetic */ a(drd drd, byte b) {
            this();
        }

        public final void onClick(BaseOverlay baseOverlay, BaseOverlayItem baseOverlayItem, int i) {
            ScenicGuidePoi scenicGuidePoi = ((dre) baseOverlayItem).a;
            drd.this.a.f = scenicGuidePoi;
            drd drd = drd.this;
            if (!(drd.a != null ? drd.a.b(i, scenicGuidePoi.getPid()) : false) && scenicGuidePoi != null) {
                bci bci = (bci) defpackage.esb.a.a.a(bci.class);
                if (bci != null) {
                    bci.a(AMapPageUtil.getPageContext(), scenicGuidePoi, null, false);
                }
            }
        }
    }

    public drd(bty bty, @NonNull AbstractBaseMapPage abstractBaseMapPage, @NonNull bck bck) {
        this.d = abstractBaseMapPage;
        this.b = bty;
        this.a = new drc(abstractBaseMapPage.getVMapPage(), bck);
        drc drc = this.a;
        drc.a.setClickListener(new a(this, 0));
    }

    public final void a(String str, ScenicPlayRouteItemEntity scenicPlayRouteItemEntity, int i, int i2) {
        this.a.a(str, scenicPlayRouteItemEntity, this.b, i, i2);
    }

    public final void a() {
        if (this.a != null) {
            this.a.clear();
        }
    }

    public final void b() {
        if (this.a != null) {
            this.a.clearFocus();
        }
    }
}
