package defpackage;

import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetListener;
import com.autonavi.map.search.fragment.SearchCQDetailPage;

/* renamed from: bza reason: default package */
/* compiled from: SearchCQDetailWidgetManager */
public final class bza extends WidgetListener<SearchCQDetailPage> {
    public IMapWidgetManager a = Stub.getMapWidgetManager();
    public bzb b;
    private SearchCQDetailPage c;

    public bza(SearchCQDetailPage searchCQDetailPage) {
        this.c = searchCQDetailPage;
        this.b = new bzb(searchCQDetailPage);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0077  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onClick(android.view.View r5, java.lang.String r6) {
        /*
            r4 = this;
            int r0 = r6.hashCode()
            r1 = -1281874730(0xffffffffb39824d6, float:-7.084752E-8)
            r2 = 0
            r3 = 1
            if (r0 == r1) goto L_0x002a
            r1 = 102570(0x190aa, float:1.43731E-40)
            if (r0 == r1) goto L_0x0020
            r1 = 102749521(0x61fd551, float:3.0061296E-35)
            if (r0 == r1) goto L_0x0016
            goto L_0x0034
        L_0x0016:
            java.lang.String r0 = "layer"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0034
            r6 = 1
            goto L_0x0035
        L_0x0020:
            java.lang.String r0 = "gps"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0034
            r6 = 0
            goto L_0x0035
        L_0x002a:
            java.lang.String r0 = "auto_remote"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0034
            r6 = 2
            goto L_0x0035
        L_0x0034:
            r6 = -1
        L_0x0035:
            switch(r6) {
                case 0: goto L_0x0077;
                case 1: goto L_0x0059;
                case 2: goto L_0x0039;
                default: goto L_0x0038;
            }
        L_0x0038:
            goto L_0x00a5
        L_0x0039:
            com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager r6 = com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub.getMapWidgetManager()
            java.lang.String r0 = "auto_remote"
            java.lang.String[] r0 = new java.lang.String[]{r0}
            com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetPresenter r6 = r6.getPresenter(r0)
            com.autonavi.bundle.uitemplate.mapwidget.widget.carinterconn.AutoRemoteWidgetPresenter r6 = (com.autonavi.bundle.uitemplate.mapwidget.widget.carinterconn.AutoRemoteWidgetPresenter) r6
            if (r6 == 0) goto L_0x00a5
            com.autonavi.map.search.fragment.SearchCQDetailPage r0 = r4.c
            if (r0 != 0) goto L_0x0051
            r0 = 0
            goto L_0x0055
        L_0x0051:
            com.autonavi.map.search.fragment.SearchCQDetailPage r0 = r4.c
            com.autonavi.common.model.POI r0 = r0.i
        L_0x0055:
            r6.sendPoiToHeadUnit(r5, r0, r3)
            goto L_0x00a5
        L_0x0059:
            com.autonavi.bundle.uitemplate.mapwidget.widget.layer.LayerWidgetPresenter.logClick()
            bzb r5 = r4.b
            if (r5 == 0) goto L_0x00a5
            bzb r5 = r4.b
            bsc$f r6 = r5.b
            if (r6 == 0) goto L_0x006f
            bsc$f r5 = r5.b
            boolean r5 = r5.f()
            if (r5 == 0) goto L_0x006f
            r2 = 1
        L_0x006f:
            if (r2 != 0) goto L_0x00a5
            bzb r5 = r4.b
            r5.a()
            return
        L_0x0077:
            com.autonavi.map.search.fragment.SearchCQDetailPage r5 = r4.c
            boolean r5 = r5.b()
            if (r5 == 0) goto L_0x0080
            return
        L_0x0080:
            esb r5 = defpackage.esb.a.a
            java.lang.Class<bci> r6 = defpackage.bci.class
            esc r5 = r5.a(r6)
            bci r5 = (defpackage.bci) r5
            com.autonavi.map.search.fragment.SearchCQDetailPage r6 = r4.c
            com.autonavi.map.core.MapManager r6 = r6.getMapManager()
            com.autonavi.map.core.IOverlayManager r6 = r6.getOverlayManager()
            r6.getGpsLayer()
            cdy r6 = defpackage.cdx.b()
            if (r5 == 0) goto L_0x00a5
            com.autonavi.map.search.fragment.SearchCQDetailPage r0 = r4.c
            r5.a(r0, r6)
            return
        L_0x00a5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bza.onClick(android.view.View, java.lang.String):void");
    }
}
