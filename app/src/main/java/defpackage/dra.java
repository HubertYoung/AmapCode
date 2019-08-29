package defpackage;

import android.support.annotation.NonNull;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.IClickListener;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.BaseOverlayItem;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.AbstractMap.SimpleEntry;

/* renamed from: dra reason: default package */
/* compiled from: ScenicGuideLayerHelper */
public class dra {
    private static final String f = "dra";
    public final dqz a;
    public bty b;
    public String c;
    public String d;
    public ScenicGuidePoi e;
    private final AbstractBaseMapPage g;

    /* renamed from: dra$a */
    /* compiled from: ScenicGuideLayerHelper */
    class a implements IClickListener {
        private a() {
        }

        /* synthetic */ a(dra dra, byte b) {
            this();
        }

        public final void onClick(BaseOverlay baseOverlay, BaseOverlayItem baseOverlayItem, int i) {
            ScenicGuidePoi scenicGuidePoi = ((drb) baseOverlayItem).a;
            if (scenicGuidePoi != null) {
                dra.this.e = scenicGuidePoi;
                dra.this.a(scenicGuidePoi.getPid());
                bci bci = (bci) defpackage.esb.a.a.a(bci.class);
                if (bci != null) {
                    bci.a(AMapPageUtil.getPageContext(), scenicGuidePoi, null, false);
                }
            }
        }
    }

    public dra(bty bty, @NonNull AbstractBaseMapPage abstractBaseMapPage, @NonNull bck bck) {
        this.g = abstractBaseMapPage;
        this.b = bty;
        this.a = new dqz(abstractBaseMapPage.getVMapPage(), bck);
        dqz dqz = this.a;
        dqz.a.setClickListener(new a(this, 0));
    }

    public final void a() {
        if (this.a != null) {
            this.a.clear();
        }
        if (this.b != null) {
            this.b.b(20190000, true);
        }
    }

    public final void b() {
        if (this.a != null) {
            this.a.clearFocus();
        }
    }

    public final void a(String str) {
        LogManager.actionLogV25("P00383", "B022", new SimpleEntry("type", this.d), new SimpleEntry("itemid", str), new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, this.c));
    }
}
