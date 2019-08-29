package defpackage;

import android.graphics.Rect;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.common.OfflineSearchMode;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.Callback.c;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.model.SearchConst.SearchFor;

/* renamed from: bvt reason: default package */
/* compiled from: SearchCallBackEx */
public class bvt extends aea<InfoliteResult> implements c {
    public SearchFor b = SearchFor.DEFAULT;
    public int c = -1;
    public bvr d;
    public String e;
    public TipItem f;
    public boolean g = true;
    private InfoliteResult h;
    private boolean i = true;
    private boolean j = false;
    private boolean k = false;
    private String l = "";
    private a m;
    private InfoliteParam n;

    public bvt() {
    }

    public bvt(bcl bcl) {
        bwx bwx = new bwx(bcl.c, bcl.b, bcl.i, bcl.m, bcl.k, bcl.l, bcl.n);
        bwx.r = bcl.h;
        bwx.l = bcl.j;
        bwx.o = bcl.g;
        this.d = bwx;
        this.c = bcl.b;
        this.e = bcl.c;
        this.f = bcl.d;
        this.n = bcl.f;
        this.g = bcl.e;
        this.b = bcl.a;
        this.m = null;
        this.j = bcl.l;
        this.i = bcl.k;
        this.k = bcl.m;
        this.l = bcl.n;
    }

    public final void a(Rect rect) {
        if (this.d instanceof bwx) {
            ((bwx) this.d).o = rect;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:92:0x017d A[ADDED_TO_REGION] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void callback(final com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12) {
        /*
            r11 = this;
            android.os.Looper r0 = android.os.Looper.getMainLooper()
            android.os.Looper r1 = android.os.Looper.myLooper()
            if (r0 == r1) goto L_0x0013
            bvt$1 r0 = new bvt$1
            r0.<init>(r12)
            defpackage.aho.a(r0)
            return
        L_0x0013:
            r0 = 0
            if (r12 != 0) goto L_0x0021
            java.lang.String r12 = "P00004"
            java.lang.String r1 = "B008"
            com.amap.bundle.statistics.LogManager.actionLogV2(r12, r1)
            r11.error(r0)
            return
        L_0x0021:
            r11.h = r12
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r11.h
            java.lang.String r1 = r11.e
            r12.mKeyword = r1
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r11.h
            aus r1 = r12.searchInfo
            r2 = 1
            if (r1 == 0) goto L_0x0053
            aus r1 = r12.searchInfo
            java.util.ArrayList<aue> r1 = r1.r
            if (r1 == 0) goto L_0x0040
            aus r1 = r12.searchInfo
            java.util.ArrayList<aue> r1 = r1.r
            int r1 = r1.size()
            if (r1 != 0) goto L_0x0051
        L_0x0040:
            aus r1 = r12.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r1 = r1.l
            if (r1 == 0) goto L_0x0053
            aus r12 = r12.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r12 = r12.l
            int r12 = r12.size()
            if (r12 != 0) goto L_0x0051
            goto L_0x0053
        L_0x0051:
            r12 = 0
            goto L_0x0054
        L_0x0053:
            r12 = 1
        L_0x0054:
            if (r12 == 0) goto L_0x005d
            java.lang.String r12 = "P00004"
            java.lang.String r1 = "B008"
            com.amap.bundle.statistics.LogManager.actionLogV2(r12, r1)
        L_0x005d:
            bvr r12 = r11.d
            if (r12 != 0) goto L_0x0076
            bwx r12 = new bwx
            java.lang.String r4 = r11.e
            int r5 = r11.c
            r6 = 0
            boolean r7 = r11.k
            boolean r8 = r11.i
            boolean r9 = r11.j
            java.lang.String r10 = r11.l
            r3 = r12
            r3.<init>(r4, r5, r6, r7, r8, r9, r10)
            r11.d = r12
        L_0x0076:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r11.h
            bvr r1 = r11.d
            com.autonavi.bundle.entity.sugg.TipItem r3 = r11.f
            boolean r4 = r11.g
            r5 = 4
            r6 = 7
            if (r12 == 0) goto L_0x01e1
            auq r7 = r12.responseHeader
            if (r7 != 0) goto L_0x0088
            goto L_0x01e1
        L_0x0088:
            auq r7 = r12.responseHeader
            int r7 = r7.c
            if (r7 == r6) goto L_0x00c5
            com.autonavi.bl.search.InfoliteParam r7 = r12.mWrapper
            int r7 = r7.pagenum
            if (r7 != r2) goto L_0x00c5
            aus r7 = r12.searchInfo
            auk r7 = r7.a
            java.lang.String r7 = r7.j
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x00c5
            aus r7 = r12.searchInfo
            auk r7 = r7.a
            int r7 = r7.i
            if (r7 != 0) goto L_0x00c5
            android.content.Context r7 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            if (r7 == 0) goto L_0x00c5
            int r8 = com.autonavi.minimap.R.string.change_city_to
            java.lang.String r7 = r7.getString(r8)
            java.lang.Object[] r8 = new java.lang.Object[r2]
            aus r9 = r12.searchInfo
            auk r9 = r9.a
            java.lang.String r9 = r9.j
            r8[r0] = r9
            java.lang.String r7 = java.lang.String.format(r7, r8)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r7)
        L_0x00c5:
            if (r1 == 0) goto L_0x01e1
            aus r7 = r12.searchInfo
            auk r7 = r7.a
            java.lang.String r7 = r7.q
            boolean r8 = android.text.TextUtils.isEmpty(r7)
            if (r8 != 0) goto L_0x00d8
            r1.a(r7)
            goto L_0x01e1
        L_0x00d8:
            if (r12 == 0) goto L_0x017a
            if (r1 != 0) goto L_0x00de
            goto L_0x017a
        L_0x00de:
            auq r7 = r12.responseHeader
            boolean r7 = r7.f
            if (r7 != 0) goto L_0x016f
            aus r7 = r12.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r7 = r7.l
            if (r7 == 0) goto L_0x00f6
            aus r7 = r12.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r7 = r7.l
            int r7 = r7.size()
            if (r7 <= 0) goto L_0x00f6
            r7 = 1
            goto L_0x00f7
        L_0x00f6:
            r7 = 0
        L_0x00f7:
            aus r8 = r12.searchInfo
            auk r8 = r8.a
            int r8 = r8.a
            if (r8 != r2) goto L_0x0101
            r8 = 1
            goto L_0x0102
        L_0x0101:
            r8 = 0
        L_0x0102:
            boolean r9 = defpackage.bxy.a(r12)
            boolean r10 = defpackage.aey.d()
            if (r8 == 0) goto L_0x010e
            r7 = 1
            goto L_0x0129
        L_0x010e:
            if (r7 == 0) goto L_0x0114
            if (r10 != 0) goto L_0x0114
            r7 = 0
            goto L_0x0129
        L_0x0114:
            if (r7 == 0) goto L_0x0118
            r7 = 4
            goto L_0x0129
        L_0x0118:
            if (r10 != 0) goto L_0x011e
            if (r9 == 0) goto L_0x011e
            r7 = 2
            goto L_0x0129
        L_0x011e:
            if (r10 == 0) goto L_0x0124
            if (r9 == 0) goto L_0x0124
            r7 = 5
            goto L_0x0129
        L_0x0124:
            if (r10 != 0) goto L_0x0128
            r7 = 3
            goto L_0x0129
        L_0x0128:
            r7 = 6
        L_0x0129:
            switch(r7) {
                case 0: goto L_0x017a;
                case 1: goto L_0x0139;
                case 2: goto L_0x0135;
                case 3: goto L_0x0131;
                case 4: goto L_0x017a;
                case 5: goto L_0x012d;
                case 6: goto L_0x017a;
                default: goto L_0x012c;
            }
        L_0x012c:
            goto L_0x017a
        L_0x012d:
            r1.a(r12, r3, r4)
            goto L_0x0178
        L_0x0131:
            r1.b(r12, r3, r4)
            goto L_0x0178
        L_0x0135:
            r1.c(r12, r3, r4)
            goto L_0x0178
        L_0x0139:
            auj r4 = r12.locationInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.c
            if (r4 == 0) goto L_0x017a
            auj r4 = r12.locationInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.c
            int r4 = r4.size()
            if (r4 != r2) goto L_0x017a
            android.content.Context r4 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            if (r4 == 0) goto L_0x017a
            int r7 = com.autonavi.minimap.R.string.change_city_to
            java.lang.String r4 = r4.getString(r7)
            java.lang.Object[] r7 = new java.lang.Object[r2]
            auj r8 = r12.locationInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r8 = r8.c
            java.lang.Object r8 = r8.get(r0)
            com.autonavi.common.model.POI r8 = (com.autonavi.common.model.POI) r8
            java.lang.String r8 = r8.getName()
            r7[r0] = r8
            java.lang.String r4 = java.lang.String.format(r4, r7)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r4)
            goto L_0x017a
        L_0x016f:
            auq r4 = r12.responseHeader
            int r4 = r4.c
            if (r4 == r2) goto L_0x017a
            r1.b(r12)
        L_0x0178:
            r4 = 1
            goto L_0x017b
        L_0x017a:
            r4 = 0
        L_0x017b:
            if (r4 != 0) goto L_0x01e1
            if (r12 == 0) goto L_0x01d9
            aus r4 = r12.searchInfo
            if (r4 == 0) goto L_0x01d9
            if (r1 != 0) goto L_0x0186
            goto L_0x01d9
        L_0x0186:
            auq r4 = r12.responseHeader
            int r4 = r4.c
            if (r4 == r2) goto L_0x0195
            auq r4 = r12.responseHeader
            int r4 = r4.c
            if (r4 != r6) goto L_0x0193
            goto L_0x0195
        L_0x0193:
            r4 = 0
            goto L_0x0196
        L_0x0195:
            r4 = 1
        L_0x0196:
            aus r7 = r12.searchInfo
            java.util.ArrayList<java.lang.String> r7 = r7.f
            aus r8 = r12.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r8 = r8.l
            if (r7 == 0) goto L_0x01c1
            int r7 = r7.size()
            if (r7 <= 0) goto L_0x01c1
            if (r8 == 0) goto L_0x01ae
            int r7 = r8.size()
            if (r7 != 0) goto L_0x01c1
        L_0x01ae:
            aus r3 = r12.searchInfo
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.external.CitySuggestion> r3 = r3.g
            int r3 = r3.size()
            if (r3 != 0) goto L_0x01bd
            r3 = 0
            r1.a(r12, r4, r3)
            goto L_0x01d9
        L_0x01bd:
            r1.a(r12)
            goto L_0x01d9
        L_0x01c1:
            aus r7 = r12.searchInfo
            auk r7 = r7.a
            int r7 = r7.a
            switch(r7) {
                case 1: goto L_0x01d6;
                case 2: goto L_0x01d2;
                case 3: goto L_0x01d2;
                case 4: goto L_0x01ce;
                case 5: goto L_0x01d2;
                default: goto L_0x01ca;
            }
        L_0x01ca:
            r1.b(r12, r4, r3)
            goto L_0x01d9
        L_0x01ce:
            r1.c(r12)
            goto L_0x01d9
        L_0x01d2:
            r1.a(r12, r4, r3)
            goto L_0x01d9
        L_0x01d6:
            r1.a(r12, r4)
        L_0x01d9:
            bxy$1 r3 = new bxy$1
            r3.<init>(r12, r1)
            defpackage.aho.a(r3)
        L_0x01e1:
            defpackage.bct.a()
            java.lang.String r12 = "1"
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r11.h
            com.autonavi.bl.search.InfoliteParam r1 = r1.mWrapper
            java.lang.String r1 = r1.transfer_pdheatmap
            boolean r12 = r12.equals(r1)
            if (r12 == 0) goto L_0x01f4
            r11.g = r0
        L_0x01f4:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r11.h
            aus r12 = r12.searchInfo
            auk r12 = r12.a
            java.lang.String r12 = r12.S
            boolean r1 = android.text.TextUtils.isEmpty(r12)
            if (r1 != 0) goto L_0x020c
            java.lang.String r1 = "0"
            boolean r12 = r1.equals(r12)
            if (r12 == 0) goto L_0x020c
            r11.g = r0
        L_0x020c:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r11.h
            java.util.List r1 = defpackage.bcy.h(r12)
            if (r12 == 0) goto L_0x0259
            auq r3 = r12.responseHeader
            int r3 = r3.c
            if (r3 == r6) goto L_0x022a
            aus r3 = r12.searchInfo
            java.util.ArrayList<aue> r3 = r3.r
            if (r3 == 0) goto L_0x022a
            aus r3 = r12.searchInfo
            java.util.ArrayList<aue> r3 = r3.r
            int r3 = r3.size()
            if (r3 > 0) goto L_0x025a
        L_0x022a:
            int r1 = r1.size()
            if (r1 > 0) goto L_0x0254
            aus r1 = r12.searchInfo
            auk r1 = r1.a
            int r1 = r1.a
            if (r1 == r2) goto L_0x0254
            aus r1 = r12.searchInfo
            auk r1 = r1.a
            int r1 = r1.a
            if (r1 == r5) goto L_0x0254
            aus r1 = r12.searchInfo
            auk r1 = r1.a
            int r1 = r1.k
            if (r2 == r1) goto L_0x0254
            aus r1 = r12.searchInfo
            auk r1 = r1.a
            java.lang.String r1 = r1.q
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0259
        L_0x0254:
            boolean r1 = r11.g
            if (r1 == 0) goto L_0x0259
            goto L_0x025a
        L_0x0259:
            r2 = 0
        L_0x025a:
            if (r2 == 0) goto L_0x0298
            com.autonavi.bundle.entity.sugg.TipItem r1 = r11.f
            if (r1 != 0) goto L_0x026f
            com.autonavi.bundle.entity.sugg.TipItem r1 = new com.autonavi.bundle.entity.sugg.TipItem
            r1.<init>()
            r11.f = r1
            com.autonavi.bundle.entity.sugg.TipItem r1 = r11.f
            com.autonavi.bl.search.InfoliteParam r12 = r12.mWrapper
            java.lang.String r12 = r12.keywords
            r1.name = r12
        L_0x026f:
            android.app.Application r12 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r1 = com.autonavi.minimap.R.string.LocationMe
            java.lang.String r12 = r12.getString(r1)
            com.autonavi.bundle.entity.sugg.TipItem r1 = r11.f
            java.lang.String r1 = r1.name
            boolean r12 = r12.equals(r1)
            if (r12 != 0) goto L_0x0298
            com.autonavi.bundle.entity.sugg.TipItem r12 = r11.f
            r12.type = r0
            com.autonavi.bundle.entity.sugg.TipItem r12 = r11.f
            java.util.Date r0 = new java.util.Date
            r0.<init>()
            r12.time = r0
            bvt$2 r12 = new bvt$2
            r12.<init>()
            defpackage.ahm.a(r12)
        L_0x0298:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bvt.callback(com.autonavi.bundle.entity.infolite.internal.InfoliteResult):void");
    }

    public void error(int i2) {
        bct.a();
        if (i2 == 0) {
            OfflineSearchMode offlineSearchMode = this.a;
            if (offlineSearchMode == null || bby.c(offlineSearchMode.strAdCode)) {
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.no_result_tip));
            }
        }
    }

    public String getLoadingMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(AMapAppGlobal.getApplication().getString(R.string.searching));
        sb.append("\"");
        sb.append(this.e);
        sb.append("\"");
        return sb.toString();
    }
}
