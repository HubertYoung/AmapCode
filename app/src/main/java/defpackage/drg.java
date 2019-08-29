package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.IClickListener;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.BaseOverlayItem;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.life.sketchscenic.layer.SketchScenicLayerHelper$1;

/* renamed from: drg reason: default package */
/* compiled from: SketchScenicLayerHelper */
public class drg {
    /* access modifiers changed from: private */
    public static final String a = "drg";
    /* access modifiers changed from: private */
    public final AbstractBaseMapPage b;
    /* access modifiers changed from: private */
    public final drf c;
    private dqv d = new dqv();

    /* renamed from: drg$a */
    /* compiled from: SketchScenicLayerHelper */
    class a implements IClickListener {
        private a() {
        }

        /* synthetic */ a(drg drg, byte b) {
            this();
        }

        public final void onClick(BaseOverlay baseOverlay, BaseOverlayItem baseOverlayItem, int i) {
            drg.this.b.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(((drh) baseOverlayItem).a.getSketchUrl())));
        }
    }

    public drg(@NonNull AbstractBaseMapPage abstractBaseMapPage, @NonNull bck bck) {
        this.b = abstractBaseMapPage;
        this.c = new drf(abstractBaseMapPage.getVMapPage(), bck);
        drf drf = this.c;
        drf.a.setClickListener(new a(this, 0));
    }

    public final void a(String str) {
        if (!this.d.a(str)) {
            SearchPoi b2 = this.d.b(str);
            if (b2 != null) {
                this.c.a(b2);
            } else {
                this.d.a(str, new SketchScenicLayerHelper$1(this));
            }
        }
    }

    public final void a() {
        this.d.a.clear();
        this.c.clear();
    }
}
