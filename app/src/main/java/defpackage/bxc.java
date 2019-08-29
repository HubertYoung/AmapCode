package defpackage;

import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;

/* renamed from: bxc reason: default package */
/* compiled from: SearchPoiResultController */
public final class bxc {
    public InfoliteResult a = null;
    private cce b = null;

    public bxc(cce cce) {
        this.b = cce;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006e, code lost:
        if (android.text.TextUtils.isEmpty(r5.getId()) != false) goto L_0x0070;
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0118  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.util.List<com.autonavi.common.model.POI> r19, com.autonavi.map.search.overlay.SearchPoiOverlay r20, defpackage.bxf r21, boolean r22) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r0.a
            if (r4 == 0) goto L_0x0147
            if (r2 != 0) goto L_0x0010
            goto L_0x0147
        L_0x0010:
            if (r1 == 0) goto L_0x0146
            int r4 = r19.size()
            if (r4 != 0) goto L_0x001a
            goto L_0x0146
        L_0x001a:
            r20.clear()
            r4 = 0
            java.lang.Object r5 = r1.get(r4)
            com.autonavi.common.model.POI r5 = (com.autonavi.common.model.POI) r5
            r6 = 1
            if (r5 == 0) goto L_0x0064
            java.util.HashMap r7 = r5.getPoiExtra()
            if (r7 == 0) goto L_0x0064
            java.util.HashMap r7 = r5.getPoiExtra()
            java.lang.String r8 = "SrcType"
            boolean r7 = r7.containsKey(r8)
            if (r7 == 0) goto L_0x0064
            java.util.HashMap r7 = r5.getPoiExtra()
            java.lang.String r8 = "SrcType"
            java.lang.Object r7 = r7.get(r8)
            java.lang.String r7 = (java.lang.String) r7
            java.lang.String r8 = "nativepoi"
            boolean r8 = r8.equals(r7)
            if (r8 != 0) goto L_0x005e
            int r8 = r19.size()
            if (r8 <= 0) goto L_0x005e
            java.lang.String r5 = r5.getId()
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 == 0) goto L_0x005e
            goto L_0x0070
        L_0x005e:
            java.lang.String r5 = "nativepoi"
            r5.equals(r7)
            goto L_0x0072
        L_0x0064:
            if (r5 == 0) goto L_0x0072
            java.lang.String r5 = r5.getId()
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 == 0) goto L_0x0072
        L_0x0070:
            r5 = 1
            goto L_0x0073
        L_0x0072:
            r5 = 0
        L_0x0073:
            if (r3 == 0) goto L_0x0078
            int r8 = r3.b
            goto L_0x0079
        L_0x0078:
            r8 = -1
        L_0x0079:
            int r9 = r19.size()
            java.util.ArrayList r10 = new java.util.ArrayList
            r11 = 10
            r10.<init>(r11)
            r11 = 0
            r12 = r11
            r11 = 0
        L_0x0087:
            if (r11 >= r9) goto L_0x00d9
            java.lang.Object r13 = r1.get(r11)
            com.autonavi.common.model.POI r13 = (com.autonavi.common.model.POI) r13
            if (r13 == 0) goto L_0x00d6
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r14 = r0.a
            int r14 = defpackage.bcy.g(r14)
            if (r14 != r11) goto L_0x009b
            if (r22 != 0) goto L_0x00d9
        L_0x009b:
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r15 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r15 = r13.as(r15)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r15 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r15
            if (r11 <= 0) goto L_0x00aa
            int r16 = r11 - r5
            r7 = r16
            goto L_0x00ab
        L_0x00aa:
            r7 = r11
        L_0x00ab:
            r2.addPoiItem(r13, r7, r6)
            if (r15 == 0) goto L_0x00c5
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r7 = r15.getIDynamicRenderInfo()
            if (r7 == 0) goto L_0x00c5
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r7 = r15.getIDynamicRenderInfo()
            boolean r7 = r7.bFlag
            if (r7 == 0) goto L_0x00c5
            cce r7 = r0.b
            if (r7 == 0) goto L_0x00c5
            r10.add(r13)
        L_0x00c5:
            java.lang.Object r7 = r2.getItem(r8)
            com.autonavi.minimap.base.overlay.PointOverlayItem r7 = (com.autonavi.minimap.base.overlay.PointOverlayItem) r7
            if (r7 == 0) goto L_0x00d6
            r2.setPointItemVisble(r11, r6, r4)
            if (r11 != r8) goto L_0x00d6
            r2.setFocus(r7, r4)
            r12 = r13
        L_0x00d6:
            int r11 = r11 + 1
            goto L_0x0087
        L_0x00d9:
            cce r1 = r0.b
            if (r1 == 0) goto L_0x0145
            int r1 = r10.size()
            if (r1 <= 0) goto L_0x0145
            cce r1 = r0.b
            r2 = 90000(0x15f90, float:1.26117E-40)
            r1.b(r2)
            cce r1 = r0.b
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r0.a
            aus r4 = r4.searchInfo
            auk r4 = r4.a
            int r4 = r4.o
            r1.a(r10, r2, r4)
            cce r1 = r0.b
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r0.a
            aus r4 = r4.searchInfo
            auk r4 = r4.a
            int r4 = r4.o
            r1.a(r12, r2, r4)
            boolean r1 = defpackage.elc.b
            if (r1 == 0) goto L_0x0110
            if (r3 == 0) goto L_0x0145
            int r1 = r3.c
            r2 = -1
            if (r1 == r2) goto L_0x0145
        L_0x0110:
            cce r1 = r0.b
            r2 = 2
            r1.b(r2)
            if (r12 == 0) goto L_0x0145
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r1 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r1 = r12.as(r1)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r1 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r1
            if (r1 == 0) goto L_0x0145
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r3 = r1.getPoiChildrenInfo()
            if (r3 == 0) goto L_0x0145
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r3 = r1.getPoiChildrenInfo()
            java.util.Collection<? extends com.autonavi.common.model.POI> r3 = r3.poiList
            if (r3 == 0) goto L_0x0145
            cce r3 = r0.b
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r1 = r1.getPoiChildrenInfo()
            java.util.Collection<? extends com.autonavi.common.model.POI> r1 = r1.poiList
            java.util.List r1 = (java.util.List) r1
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r0.a
            aus r4 = r4.searchInfo
            auk r4 = r4.a
            int r4 = r4.o
            r3.a(r1, r2, r4)
        L_0x0145:
            return
        L_0x0146:
            return
        L_0x0147:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bxc.a(java.util.List, com.autonavi.map.search.overlay.SearchPoiOverlay, bxf, boolean):void");
    }
}
