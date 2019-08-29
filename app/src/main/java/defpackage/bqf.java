package defpackage;

import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.b;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.c;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.idqmax.controller.IdqMaxAjxLayerManager$3;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.minimap.ajx3.modules.ModuleJsBridge;
import com.autonavi.minimap.ajx3.modules.ModulePhoto;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.BackCallback;

/* renamed from: bqf reason: default package */
/* compiled from: IdqMaxAjxLayerManager */
public final class bqf {
    public AmapAjxView a;
    public ModuleIdqPlus b;
    public com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.a c;
    public c d;
    public a e = new a(0);
    FrameLayout f;
    public ccs g;
    volatile int h = 0;
    MapBasePage i;
    BackCallback j = new BackCallback() {
        public final void back(Object obj, String str) {
            bqf.this.i.finish();
        }
    };
    Callback<AmapAjxView> k = new IdqMaxAjxLayerManager$3(this);
    public b l = new b() {
        public final void a() {
            if (bqf.this.e.a != null && bqf.this.a() != null) {
                bqf.this.a().setViewState(bqf.this.e.a, bqf.this.e.b);
            }
        }
    };

    /* renamed from: bqf$a */
    /* compiled from: IdqMaxAjxLayerManager */
    public static class a {
        public String a;
        boolean b;
        public PageBundle c;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public bqf(MapBasePage mapBasePage) {
        this.i = mapBasePage;
        this.a = new AmapAjxView(mapBasePage.getContext());
    }

    @Nullable
    public final ModuleIdqPlus a() {
        if (this.b == null) {
            this.b = (ModuleIdqPlus) this.a.getJsModule(ModuleIdqPlus.MODULE_NAME);
        }
        return this.b;
    }

    public final void a(String str) {
        if (this.a != null) {
            this.a.onResume(false, str);
        }
    }

    public final JsAdapter b() {
        if (this.a == null) {
            return null;
        }
        ModuleJsBridge moduleJsBridge = (ModuleJsBridge) this.a.getJsModule("js");
        if (moduleJsBridge != null) {
            return moduleJsBridge.getJsMethod();
        }
        return null;
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle != null) {
            if (this.a == null) {
                this.e.c = pageBundle;
                return;
            }
            JsAdapter b2 = b();
            if (b2 == null) {
                this.e.c = pageBundle;
                return;
            }
            ModulePhoto modulePhoto = (ModulePhoto) this.a.getJsModule("photo");
            if (modulePhoto != null) {
                modulePhoto.getJsMethods().setBundle(pageBundle);
            }
            b2.setBundle(pageBundle);
        }
    }
}
