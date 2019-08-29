package defpackage;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.MainMapFeature;
import com.autonavi.annotation.MainMapInvokePriority;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.suspend.refactor.scenic.ISketchScenic;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.life.sketchscenic.SketchScenic;
import java.util.ArrayList;

@MainMapFeature
/* renamed from: dqr reason: default package */
/* compiled from: MainMapSketchScenicManager */
public class dqr implements apt, czu, czy {
    awb a = ((awb) defpackage.esb.a.a.a(awb.class));
    /* access modifiers changed from: private */
    public final String b = dqr.class.getSimpleName();
    /* access modifiers changed from: private */
    public SketchScenic c;
    /* access modifiers changed from: private */
    public boolean d;
    private MainMapEventListener e = new awc() {
        public final void onMapLevelChange(boolean z) {
            super.onMapLevelChange(z);
            if (dqr.this.c != null) {
                SketchScenic a2 = dqr.this.c;
                if (a2.a.a()) {
                    a2.a(a2.h, a2.i, a2.j, a2.k);
                }
            }
        }
    };

    /* renamed from: dqr$a */
    /* compiled from: MainMapSketchScenicManager */
    class a implements amw {
        private a() {
        }

        /* synthetic */ a(dqr dqr, byte b) {
            this();
        }

        public final void a(int i, amv amv) {
            dqr.this.c.a.a = amv;
            if (bno.a) {
                String b = dqr.this.b;
                StringBuilder sb = new StringBuilder("MainMapSketchScenicManager.onScenicActive: Callback. mAppearing=");
                sb.append(dqr.this.d);
                sb.append("; scenicInfor=");
                sb.append(amv);
                AMapLog.i(b, sb.toString(), true);
            }
            if (dqr.this.d) {
                if (amv != null) {
                    dqr.this.c.c();
                    if (bno.a) {
                        StringBuilder sb2 = new StringBuilder("景区激活方法里景区信息不为空，发起请求显示控件--景区poiid---");
                        sb2.append(amv.a);
                        AMapLog.debug("infoservice.scenic", "hanyumin", sb2.toString());
                    }
                } else {
                    dqr.this.c.f();
                    dqr.this.c.a(true, true);
                    if (bno.a) {
                        AMapLog.debug("infoservice.scenic", "hanyumin", "景区激活方法里信息为空，隐藏控件");
                    }
                }
            }
        }
    }

    public void onPause() {
    }

    @MainMapInvokePriority(5.0f)
    public void onCreate() {
        cde b2 = ((IMainMapService) ank.a(IMainMapService.class)).b();
        if (b2 == null) {
            if (bno.a) {
                AMapLog.e(this.b, "SuspendManager is null", true);
            }
            return;
        }
        this.c = new SketchScenic();
        SketchScenic sketchScenic = this.c;
        if (!sketchScenic.e) {
            bck bck = (bck) defpackage.esb.a.a.a(bck.class);
            if (bck == null) {
                sketchScenic.e = false;
            } else {
                IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
                sketchScenic.m = iMainMapService.g();
                if (sketchScenic.m == null) {
                    throw new IllegalArgumentException("mapView is null!");
                }
                sketchScenic.l = iMainMapService.e();
                if (sketchScenic.l == null) {
                    sketchScenic.e = false;
                } else {
                    sketchScenic.a = new dqx(sketchScenic.m);
                    sketchScenic.c = new dqw();
                    if (sketchScenic.l instanceof AbstractBaseMapPage) {
                        sketchScenic.b = new drg((AbstractBaseMapPage) sketchScenic.l, bck);
                        if (sketchScenic.l instanceof AbstractBaseMapPage) {
                            sketchScenic.d = new dra(sketchScenic.m, (AbstractBaseMapPage) sketchScenic.l, bck);
                            sketchScenic.n = new drd(sketchScenic.m, (AbstractBaseMapPage) sketchScenic.l, bck);
                            sketchScenic.e = true;
                        } else {
                            throw new IllegalArgumentException("page is not mapPage!");
                        }
                    } else {
                        throw new IllegalArgumentException("page is not mapPage!");
                    }
                }
            }
        }
        b2.a((ISketchScenic) this.c);
        avv.a((amw) new a(this, 0));
        apt apt = (apt) ank.a(apt.class);
        if (apt instanceof dqu) {
            ((dqu) apt).a = this;
        }
        if (this.a != null) {
            this.a.a(this.e);
        }
    }

    public void onDestroy() {
        if (this.c != null) {
            SketchScenic sketchScenic = this.c;
            if (sketchScenic.e) {
                if (sketchScenic.f != null) {
                    dqs dqs = sketchScenic.f;
                    dqs.b = true;
                    if (dqs.a != null) {
                        in.a().a((AosRequest) dqs.a);
                    }
                }
                if (sketchScenic.g != null) {
                    dqt dqt = sketchScenic.g;
                    if (dqt.a != null) {
                        in.a().a((AosRequest) dqt.a);
                    }
                }
                sketchScenic.e = false;
                sketchScenic.h = null;
                sketchScenic.i = null;
                sketchScenic.j = null;
                sketchScenic.k = null;
                sketchScenic.a(sketchScenic.h, sketchScenic.i, sketchScenic.j, sketchScenic.k);
            }
        }
        avv.c();
        apt apt = (apt) ank.a(apt.class);
        if (apt instanceof dqu) {
            ((dqu) apt).a = null;
        }
        if (this.a != null) {
            this.a.b(this.e);
        }
    }

    public void onResume() {
        this.d = true;
        SketchScenic sketchScenic = this.c;
        amv amv = sketchScenic.a.a;
        if (amv != null && !TextUtils.isEmpty(amv.a)) {
            dra dra = sketchScenic.d;
            ArrayList arrayList = dra.a.b.get(amv.a);
            if (!(arrayList == null || arrayList.size() <= 0 || dra.a == null)) {
                dqz.a(arrayList, dra.b);
            }
        }
        SketchScenic sketchScenic2 = this.c;
        amv amv2 = sketchScenic2.a.a;
        if (amv2 != null && !TextUtils.isEmpty(amv2.a)) {
            drd drd = sketchScenic2.n;
            ArrayList arrayList2 = drd.a.b.get(amv2.a);
            if (!(arrayList2 == null || arrayList2.size() <= 0 || drd.a == null)) {
                drc.a(arrayList2, drd.b);
            }
        }
        if (((IMainMapService) ank.a(IMainMapService.class)).isScenicPlayRouteShown()) {
            SketchScenic.e();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x009a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCover() {
        /*
            r7 = this;
            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            java.lang.Class<com.autonavi.minimap.bundle.maphome.service.IMainMapService> r1 = com.autonavi.minimap.bundle.maphome.service.IMainMapService.class
            java.lang.Object r1 = defpackage.ank.a(r1)
            com.autonavi.minimap.bundle.maphome.service.IMainMapService r1 = (com.autonavi.minimap.bundle.maphome.service.IMainMapService) r1
            boolean r0 = r0 instanceof com.autonavi.map.search.fragment.SearchCQDetailPage
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x0014
            r0 = 1
            goto L_0x0015
        L_0x0014:
            r0 = 0
        L_0x0015:
            if (r0 != 0) goto L_0x0021
            com.autonavi.minimap.life.sketchscenic.SketchScenic r0 = r7.c
            r0.a(r1)
            com.autonavi.minimap.life.sketchscenic.SketchScenic r0 = r7.c
            r0.f()
        L_0x0021:
            com.autonavi.minimap.life.sketchscenic.SketchScenic r0 = r7.c
            if (r0 == 0) goto L_0x00a5
            esb r0 = defpackage.esb.a.a
            java.lang.Class<apr> r1 = defpackage.apr.class
            r0.a(r1)
            java.util.ArrayList r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContextStacks()
            if (r0 == 0) goto L_0x0097
            int r1 = r0.size()
            r4 = 2
            if (r1 == r4) goto L_0x003c
            goto L_0x0097
        L_0x003c:
            esb r1 = defpackage.esb.a.a
            java.lang.Class<apr> r4 = defpackage.apr.class
            esc r1 = r1.a(r4)
            apr r1 = (defpackage.apr) r1
            if (r1 != 0) goto L_0x004c
            r1 = 0
            goto L_0x0056
        L_0x004c:
            java.lang.Object r4 = r0.get(r3)
            bid r4 = (defpackage.bid) r4
            boolean r1 = r1.a(r4)
        L_0x0056:
            esb r4 = defpackage.esb.a.a
            java.lang.Class<bci> r5 = defpackage.bci.class
            esc r4 = r4.a(r5)
            bci r4 = (defpackage.bci) r4
            if (r4 == 0) goto L_0x0072
            java.lang.Object r5 = r0.get(r2)
            bid r5 = (defpackage.bid) r5
            boolean r4 = r4.a(r5)
            if (r4 == 0) goto L_0x0072
            r4 = 1
            goto L_0x0073
        L_0x0072:
            r4 = 0
        L_0x0073:
            esb r5 = defpackage.esb.a.a
            java.lang.Class<bdl> r6 = defpackage.bdl.class
            esc r5 = r5.a(r6)
            bdl r5 = (defpackage.bdl) r5
            java.lang.Object r0 = r0.get(r2)
            bid r0 = (defpackage.bid) r0
            boolean r0 = r5.a(r0)
            if (r4 != 0) goto L_0x0090
            if (r0 == 0) goto L_0x008e
            goto L_0x0090
        L_0x008e:
            r0 = 0
            goto L_0x0091
        L_0x0090:
            r0 = 1
        L_0x0091:
            if (r1 == 0) goto L_0x0097
            if (r0 == 0) goto L_0x0097
            r0 = 1
            goto L_0x0098
        L_0x0097:
            r0 = 0
        L_0x0098:
            if (r0 != 0) goto L_0x00a1
            com.autonavi.minimap.life.sketchscenic.SketchScenic r0 = r7.c
            r0.a(r2, r3)
            r7.d = r3
        L_0x00a1:
            com.autonavi.minimap.life.sketchscenic.SketchScenic r0 = r7.c
            r0.o = r3
        L_0x00a5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dqr.onCover():void");
    }

    public void onAppear() {
        if (this.c != null) {
            this.c.c();
            SketchScenic sketchScenic = this.c;
            if (sketchScenic.d != null) {
                dra dra = sketchScenic.d;
                if (dra.a != null) {
                    dqz dqz = dra.a;
                    if (dqz.b != null) {
                        dqz.b.clear();
                    }
                }
            }
            SketchScenic sketchScenic2 = this.c;
            if (sketchScenic2.n != null) {
                drd drd = sketchScenic2.n;
                if (drd.a != null) {
                    drd.a.b();
                }
            }
        }
        this.d = true;
    }

    public final void a() {
        if (this.c != null && this.c.d != null) {
            this.c.d.b();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:60:0x0116  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.lang.String r6, int r7) {
        /*
            r5 = this;
            com.autonavi.minimap.life.sketchscenic.SketchScenic r0 = r5.c
            if (r0 == 0) goto L_0x0159
            r0 = 0
            r1 = 0
            switch(r7) {
                case 20190000: goto L_0x00c6;
                case 20190001: goto L_0x0022;
                default: goto L_0x0009;
            }
        L_0x0009:
            com.autonavi.minimap.life.sketchscenic.SketchScenic r6 = r5.c
            drd r6 = r6.n
            if (r6 == 0) goto L_0x0159
            com.autonavi.minimap.life.sketchscenic.SketchScenic r6 = r5.c
            drd r6 = r6.n
            drc r7 = r6.a
            if (r7 == 0) goto L_0x0159
            drc r7 = r6.a
            r7.f = r1
            drc r6 = r6.a
            r6.clearFocus()
            goto L_0x0159
        L_0x0022:
            com.autonavi.minimap.life.sketchscenic.SketchScenic r7 = r5.c
            drd r7 = r7.n
            if (r7 == 0) goto L_0x0159
            com.autonavi.minimap.life.sketchscenic.SketchScenic r7 = r5.c
            drd r7 = r7.n
            drc r2 = r7.a
            if (r2 == 0) goto L_0x00c5
            drc r2 = r7.a
            java.util.List<com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi> r3 = r2.c
            if (r3 == 0) goto L_0x006f
            r3 = 0
        L_0x0037:
            java.util.List<com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi> r4 = r2.c
            int r4 = r4.size()
            if (r3 >= r4) goto L_0x006f
            java.util.List<com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi> r4 = r2.c
            java.lang.Object r4 = r4.get(r3)
            com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi r4 = (com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi) r4
            java.lang.String r4 = r4.getPid()
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x006c
            java.util.List<com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi> r4 = r2.c
            java.lang.Object r4 = r4.get(r3)
            com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi r4 = (com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi) r4
            java.lang.String r4 = r4.getPid()
            boolean r4 = android.text.TextUtils.equals(r6, r4)
            if (r4 == 0) goto L_0x006c
            java.util.List<com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi> r6 = r2.c
            java.lang.Object r6 = r6.get(r3)
            com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi r6 = (com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi) r6
            goto L_0x0070
        L_0x006c:
            int r3 = r3 + 1
            goto L_0x0037
        L_0x006f:
            r6 = r1
        L_0x0070:
            if (r6 == 0) goto L_0x00c5
            drc r2 = r7.a
            r2.f = r6
            drc r7 = r7.a
            java.lang.String r2 = r6.getId()
            r7.clearFocus()
            com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi r3 = r7.f
            java.lang.String r3 = r3.getPid()
            r7.a(r0, r3)
            com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay<dre> r3 = r7.a
            if (r3 == 0) goto L_0x008f
            com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay<dre> r3 = r7.a
            goto L_0x0090
        L_0x008f:
            r3 = r1
        L_0x0090:
            if (r3 == 0) goto L_0x00b0
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L_0x00b0
            com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi r4 = r7.e     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r4 = r4.getId()     // Catch:{ Exception -> 0x00b0 }
            boolean r4 = r4.equals(r2)     // Catch:{ Exception -> 0x00b0 }
            if (r4 == 0) goto L_0x00a9
            java.lang.String r4 = "redesign://basemap/ScenicPlayFocus/"
            r7.destroyTexture(r4)     // Catch:{ Exception -> 0x00b0 }
        L_0x00a9:
            int r7 = java.lang.Integer.parseInt(r2)     // Catch:{ Exception -> 0x00b0 }
            r3.setFocus(r7)     // Catch:{ Exception -> 0x00b0 }
        L_0x00b0:
            esb r7 = defpackage.esb.a.a
            java.lang.Class<bci> r2 = defpackage.bci.class
            esc r7 = r7.a(r2)
            bci r7 = (defpackage.bci) r7
            if (r7 == 0) goto L_0x00c5
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r7.a(r2, r6, r1, r0)
        L_0x00c5:
            return
        L_0x00c6:
            com.autonavi.minimap.life.sketchscenic.SketchScenic r7 = r5.c
            dra r7 = r7.d
            if (r7 == 0) goto L_0x0159
            com.autonavi.minimap.life.sketchscenic.SketchScenic r7 = r5.c
            dra r7 = r7.d
            dqz r2 = r7.a
            if (r2 == 0) goto L_0x0158
            dqz r2 = r7.a
            java.util.List<com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi> r3 = r2.c
            if (r3 == 0) goto L_0x0113
            r3 = 0
        L_0x00db:
            java.util.List<com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi> r4 = r2.c
            int r4 = r4.size()
            if (r3 >= r4) goto L_0x0113
            java.util.List<com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi> r4 = r2.c
            java.lang.Object r4 = r4.get(r3)
            com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi r4 = (com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi) r4
            java.lang.String r4 = r4.getPid()
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x0110
            java.util.List<com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi> r4 = r2.c
            java.lang.Object r4 = r4.get(r3)
            com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi r4 = (com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi) r4
            java.lang.String r4 = r4.getPid()
            boolean r4 = android.text.TextUtils.equals(r6, r4)
            if (r4 == 0) goto L_0x0110
            java.util.List<com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi> r6 = r2.c
            java.lang.Object r6 = r6.get(r3)
            com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi r6 = (com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi) r6
            goto L_0x0114
        L_0x0110:
            int r3 = r3 + 1
            goto L_0x00db
        L_0x0113:
            r6 = r1
        L_0x0114:
            if (r6 == 0) goto L_0x0158
            r7.e = r6
            r7.b()
            dqz r2 = r7.a
            com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay<drb> r3 = r2.a
            if (r3 == 0) goto L_0x0124
            com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay<drb> r2 = r2.a
            goto L_0x0125
        L_0x0124:
            r2 = r1
        L_0x0125:
            if (r2 == 0) goto L_0x013c
            java.lang.String r3 = r6.getIdentityId()
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x013c
            java.lang.String r3 = r6.getIdentityId()     // Catch:{ Exception -> 0x013c }
            int r3 = java.lang.Integer.parseInt(r3)     // Catch:{ Exception -> 0x013c }
            r2.setFocus(r3)     // Catch:{ Exception -> 0x013c }
        L_0x013c:
            java.lang.String r2 = r6.getPid()
            r7.a(r2)
            esb r7 = defpackage.esb.a.a
            java.lang.Class<bci> r2 = defpackage.bci.class
            esc r7 = r7.a(r2)
            bci r7 = (defpackage.bci) r7
            if (r7 == 0) goto L_0x0158
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r7.a(r2, r6, r1, r0)
        L_0x0158:
            return
        L_0x0159:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dqr.a(java.lang.String, int):void");
    }
}
