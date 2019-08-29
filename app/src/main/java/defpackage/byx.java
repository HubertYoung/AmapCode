package defpackage;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.b;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.c;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.search.manager.IdqPlusAjxLayerManager$2;
import com.autonavi.map.search.manager.IdqPlusAjxLayerManager$4;
import com.autonavi.map.search.view.IdqLoadingView;
import com.autonavi.minimap.ajx3.modules.ModuleJsBridge;
import com.autonavi.minimap.ajx3.modules.ModulePhoto;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

/* renamed from: byx reason: default package */
/* compiled from: IdqPlusAjxLayerManager */
public final class byx {
    public boolean a;
    public AmapAjxView b;
    public ModuleIdqPlus c;
    public com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.a d;
    public c e;
    public a f = new a(0);
    public IdqLoadingView g;
    public FrameLayout h;
    Callback<AmapAjxView> i = new IdqPlusAjxLayerManager$2(this);
    public b j = new b() {
        public final void a() {
            if (byx.this.f.a != null && byx.this.a() != null) {
                byx.this.a().setViewState(byx.this.f.a, byx.this.f.b);
            }
        }
    };
    Callback<AmapAjxView> k = new IdqPlusAjxLayerManager$4(this);

    /* renamed from: byx$a */
    /* compiled from: IdqPlusAjxLayerManager */
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

    public byx(Context context) {
        this.b = new AmapAjxView(context);
        this.g = new IdqLoadingView(context);
    }

    public final void a(String str) {
        if (this.a) {
            a().setViewState(str, true);
            return;
        }
        this.f.a = str;
        this.f.b = true;
    }

    @Nullable
    public final ModuleIdqPlus a() {
        if (this.c == null) {
            this.c = (ModuleIdqPlus) this.b.getJsModule(ModuleIdqPlus.MODULE_NAME);
        }
        return this.c;
    }

    public final void b(String str) {
        if (this.b != null) {
            this.b.onResume(false, str);
        }
    }

    public final JsAdapter b() {
        if (this.b == null) {
            return null;
        }
        ModuleJsBridge moduleJsBridge = (ModuleJsBridge) this.b.getJsModule("js");
        if (moduleJsBridge != null) {
            return moduleJsBridge.getJsMethod();
        }
        return null;
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle != null) {
            if (this.b == null) {
                this.f.c = pageBundle;
                return;
            }
            JsAdapter b2 = b();
            if (b2 == null) {
                this.f.c = pageBundle;
                return;
            }
            ((ModulePhoto) this.b.getJsModule("photo")).getJsMethods().setBundle(pageBundle);
            b2.setBundle(pageBundle);
        }
    }
}
