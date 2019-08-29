package defpackage;

import com.autonavi.jni.ae.bl.map.IPageService;

/* renamed from: bud reason: default package */
/* compiled from: IMapVirtualizationPageServiceImpl */
public final class bud implements buc {
    private final IPageService a;

    public bud(IPageService iPageService) {
        this.a = iPageService;
    }

    public final bua a(String str) {
        if (this.a == null) {
            return null;
        }
        return new bub(this.a.createPage(str));
    }

    public final void a(bua bua) {
        if (this.a != null) {
            this.a.destroyPage(((bub) bua).a);
        }
    }
}
