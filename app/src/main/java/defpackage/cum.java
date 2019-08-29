package defpackage;

import com.autonavi.bundle.amaphome.page.MapHomePage;
import com.autonavi.bundle.amaphome.page.MapHomeTabPage;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.bundle.amaphome.ab.page.OldMapHomePage;

/* renamed from: cum reason: default package */
/* compiled from: RedesignAB */
public final class cum {
    public final boolean a;

    /* renamed from: cum$a */
    /* compiled from: RedesignAB */
    public interface a {
        public static final cum a = new cum(0);
    }

    /* synthetic */ cum(byte b) {
        this();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x006e, code lost:
        r2 = com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor.SEARCH_TYPE_BUS;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private cum() {
        /*
            r4 = this;
            r4.<init>()
            bnv r0 = new bnv
            r0.<init>()
            int r1 = defpackage.bnv.c()
            r2 = 1
            switch(r1) {
                case -1: goto L_0x0017;
                case 0: goto L_0x0013;
                case 1: goto L_0x0017;
                default: goto L_0x0010;
            }
        L_0x0010:
            r4.a = r2
            goto L_0x0019
        L_0x0013:
            r1 = 0
            r4.a = r1
            goto L_0x0019
        L_0x0017:
            r4.a = r2
        L_0x0019:
            boolean r1 = r4.a
            com.amap.bundle.mapstorage.MapSharePreference r2 = r0.a
            android.content.SharedPreferences$Editor r2 = r2.edit()
            java.lang.String r3 = "key_ab_page_switch"
            android.content.SharedPreferences$Editor r1 = r2.putBoolean(r3, r1)
            r1.commit()
            boolean r1 = r4.a
            com.amap.bundle.mapstorage.MapSharePreference r0 = r0.a
            android.content.SharedPreferences$Editor r0 = r0.edit()
            java.lang.String r2 = "key_redesign_ab_cloud_state"
            android.content.SharedPreferences$Editor r0 = r0.putBoolean(r2, r1)
            r0.commit()
            boolean r0 = r4.a
            if (r0 == 0) goto L_0x0042
            java.lang.String r0 = "1"
            goto L_0x0044
        L_0x0042:
            java.lang.String r0 = "0"
        L_0x0044:
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference
            java.lang.String r2 = "basemap"
            r1.<init>(r2)
            java.lang.String r2 = "userIndividualityType"
            java.lang.String r3 = ""
            java.lang.String r1 = r1.getStringValue(r2, r3)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x0084
            java.lang.String r2 = "skip"
            java.lang.String r3 = "1"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0066
            java.lang.String r2 = "car"
            goto L_0x0070
        L_0x0066:
            java.lang.String r3 = "2"
            boolean r1 = r3.equals(r1)
            if (r1 == 0) goto L_0x0070
            java.lang.String r2 = "bus"
        L_0x0070:
            com.autonavi.bundle.amaphome.log.RedesignOnLineLogParam r1 = new com.autonavi.bundle.amaphome.log.RedesignOnLineLogParam
            r1.<init>(r0, r2)
            com.amap.bundle.aosservice.request.AosGetRequest r0 = defpackage.aax.a(r1)
            defpackage.yq.a()
            com.autonavi.bundle.amaphome.log.RedesignOnLineLogCallback r1 = new com.autonavi.bundle.amaphome.log.RedesignOnLineLogCallback
            r1.<init>()
            defpackage.yq.a(r0, r1)
        L_0x0084:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cum.<init>():void");
    }

    public final void a(buj buj, PageBundle pageBundle) {
        Stub.getMapWidgetManager().setABHomePage(this.a);
        if (buj != null) {
            if (this.a) {
                if (pageBundle == null) {
                    pageBundle = new PageBundle();
                }
                pageBundle.putString(MapHomeTabPage.a, bep.a((String) "首页", MapHomePage.class));
                buj.startPage(MapHomeTabPage.class, pageBundle);
                return;
            }
            buj.startPage(OldMapHomePage.class, pageBundle);
        }
    }
}
