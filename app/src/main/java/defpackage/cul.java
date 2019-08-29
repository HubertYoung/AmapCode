package defpackage;

import com.autonavi.bundle.amaphome.page.MapHomeTabPage;
import com.autonavi.minimap.bundle.amaphome.ab.page.OldMapHomePage;

/* renamed from: cul reason: default package */
/* compiled from: ABClassLoader */
public final class cul implements buh {
    public final Class<? extends bid> a(String str) {
        if (!"amap.basemap.action.default_page".equals(str)) {
            return null;
        }
        if (a.a.a) {
            return MapHomeTabPage.class;
        }
        return OldMapHomePage.class;
    }
}
