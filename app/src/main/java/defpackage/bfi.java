package defpackage;

import android.support.annotation.Nullable;
import com.autonavi.bundle.vui.ajx.ModuleVUI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import org.json.JSONObject;

/* renamed from: bfi reason: default package */
/* compiled from: VUIPageFramework */
public final class bfi {

    /* renamed from: bfi$a */
    /* compiled from: VUIPageFramework */
    static class a implements bgm {
        private final ModuleVUI a;

        public final void finishSelf() {
        }

        public a(ModuleVUI moduleVUI) {
            this.a = moduleVUI;
            moduleVUI.attachPage(this);
        }

        public final bgo getPresenter() {
            return this.a;
        }

        public final long getScenesID() {
            return this.a.getScenesID();
        }

        public final JSONObject getScenesData() {
            return this.a.getScenesData();
        }

        public final boolean needKeepSessionAlive() {
            return this.a.needKeepSession();
        }

        public final boolean isInnerPage() {
            return this.a.isInnerPage();
        }
    }

    @Nullable
    public static Object a() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof bgm) {
            return (bgm) pageContext;
        }
        if (pageContext instanceof bfk) {
            return (bfk) pageContext;
        }
        if (pageContext instanceof Ajx3Page) {
            return a((Ajx3Page) pageContext);
        }
        return null;
    }

    private static bgm a(Ajx3Page ajx3Page) {
        AmapAjxView ajxView = ajx3Page.getAjxView();
        if (ajxView == null) {
            return null;
        }
        ModuleVUI moduleVUI = (ModuleVUI) ajxView.getJsModule(ModuleVUI.MODULE_NAME);
        if (moduleVUI == null) {
            return null;
        }
        return new a(moduleVUI);
    }
}
