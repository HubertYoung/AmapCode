package defpackage;

import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* renamed from: dxp reason: default package */
/* compiled from: RouteSuspendViewController */
public final class dxp implements dxo {
    ArrayList<Integer> a = new ArrayList<>();
    ArrayList<Integer> b;
    b c;
    a d;
    c e;
    WeakReference<MapManager> f;
    boolean g;
    private ccv h;
    private ccy i;
    private HashMap<Integer, dxq> j = new HashMap<>();
    private dxn k;
    private WeakReference<cde> l;
    private float m = 1.0f;
    private float n = 0.0f;
    private boolean o = false;

    public dxp(ccy ccy, dxn dxn, cde cde, MapManager mapManager) {
        this.i = ccy;
        this.k = dxn;
        this.l = new WeakReference<>(cde);
        this.f = new WeakReference<>(mapManager);
    }

    public final void a() {
        if (this.j.containsKey(Integer.valueOf(8))) {
            dxq dxq = this.j.get(Integer.valueOf(8));
            if (dxq != null && (dxq.b instanceof AGroupSuspendView)) {
                ((AGroupSuspendView) dxq.b).destroy();
            }
        }
        if (this.j.containsKey(Integer.valueOf(1)) && this.i != null) {
            this.i.a((cdp) null);
        }
        this.j.clear();
        this.d = null;
        this.c = null;
        this.e = null;
        this.i = null;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View b() {
        /*
            r11 = this;
            boolean r0 = r11.o
            r1 = 0
            if (r0 != 0) goto L_0x020c
            android.content.Context r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            ccv r2 = r11.h
            if (r2 != 0) goto L_0x0014
            ccv r2 = new ccv
            r2.<init>(r0)
            r11.h = r2
        L_0x0014:
            dxn r0 = r11.k
            java.util.ArrayList r0 = r0.a()
            r11.b = r0
            java.util.ArrayList<java.lang.Integer> r0 = r11.b
            r2 = 1
            java.lang.Integer r3 = java.lang.Integer.valueOf(r2)
            boolean r0 = r0.contains(r3)
            if (r0 == 0) goto L_0x0034
            ccy r0 = r11.i
            ccy r3 = r11.i
            android.view.View r3 = r3.e(r2)
            r0.a(r3)
        L_0x0034:
            java.util.ArrayList<java.lang.Integer> r0 = r11.b
            java.util.Iterator r0 = r0.iterator()
        L_0x003a:
            boolean r3 = r0.hasNext()
            r4 = 4
            if (r3 == 0) goto L_0x0160
            java.lang.Object r3 = r0.next()
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            ccy r5 = r11.i
            android.content.Context r6 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            r7 = 0
            r8 = 1082130432(0x40800000, float:4.0)
            switch(r3) {
                case 1: goto L_0x010c;
                case 2: goto L_0x0057;
                case 3: goto L_0x00ea;
                case 4: goto L_0x00da;
                case 5: goto L_0x0057;
                case 6: goto L_0x00af;
                case 7: goto L_0x0088;
                case 8: goto L_0x006b;
                case 9: goto L_0x0059;
                default: goto L_0x0057;
            }
        L_0x0057:
            goto L_0x013e
        L_0x0059:
            if (r5 == 0) goto L_0x013e
            android.widget.LinearLayout$LayoutParams r4 = r5.m()
            dxq r6 = new dxq
            com.autonavi.map.suspend.refactor.zoom.ZoomView r5 = r5.l()
            r7 = 5
            r6.<init>(r3, r5, r4, r7)
            goto L_0x013f
        L_0x006b:
            if (r6 == 0) goto L_0x013e
            com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView r5 = new com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView
            r5.<init>(r6)
            android.widget.LinearLayout$LayoutParams r7 = defpackage.dxq.a()
            int r6 = defpackage.agn.a(r6, r8)
            r7.rightMargin = r6
            int r6 = com.autonavi.minimap.R.drawable.icon_c_bg_single
            r5.setBackgroundResource(r6)
            dxq r6 = new dxq
            r6.<init>(r3, r5, r7, r4)
            goto L_0x013f
        L_0x0088:
            if (r6 == 0) goto L_0x013e
            com.autonavi.map.core.view.MvpImageView r5 = new com.autonavi.map.core.view.MvpImageView
            r5.<init>(r6)
            int r7 = com.autonavi.minimap.R.drawable.icon_c14_selector
            r5.setImageResource(r7)
            int r7 = com.autonavi.minimap.R.drawable.rt_bus_around_refresh_bg_selector
            r5.setBackgroundResource(r7)
            java.lang.String r7 = "实时公交"
            r5.setContentDescription(r7)
            android.widget.LinearLayout$LayoutParams r7 = defpackage.dxq.a()
            int r6 = defpackage.agn.a(r6, r8)
            r7.rightMargin = r6
            dxq r6 = new dxq
            r6.<init>(r3, r5, r7, r4)
            goto L_0x013f
        L_0x00af:
            if (r6 == 0) goto L_0x013e
            com.autonavi.map.core.view.MvpImageView r5 = new com.autonavi.map.core.view.MvpImageView
            r5.<init>(r6)
            android.widget.ImageView$ScaleType r7 = android.widget.ImageView.ScaleType.CENTER_INSIDE
            r5.setScaleType(r7)
            int r7 = com.autonavi.minimap.R.drawable.icon_c18_selector
            r5.setImageResource(r7)
            int r7 = com.autonavi.minimap.R.drawable.rt_bus_around_refresh_bg_selector
            r5.setBackgroundResource(r7)
            java.lang.String r7 = "报错"
            r5.setContentDescription(r7)
            android.widget.LinearLayout$LayoutParams r7 = defpackage.dxq.a()
            int r6 = defpackage.agn.a(r6, r8)
            r7.rightMargin = r6
            dxq r6 = new dxq
            r6.<init>(r3, r5, r7, r4)
            goto L_0x013f
        L_0x00da:
            if (r5 == 0) goto L_0x013e
            dxq r6 = new dxq
            android.view.View r7 = r5.c(r7)
            android.widget.LinearLayout$LayoutParams r5 = r5.k()
            r6.<init>(r3, r7, r5, r4)
            goto L_0x013f
        L_0x00ea:
            if (r6 == 0) goto L_0x013e
            if (r5 == 0) goto L_0x013e
            android.widget.LinearLayout$LayoutParams r4 = r5.c()
            r8 = 1112014848(0x42480000, float:50.0)
            int r8 = defpackage.agn.a(r6, r8)
            r4.topMargin = r8
            r8 = 1090519040(0x41000000, float:8.0)
            int r6 = defpackage.agn.a(r6, r8)
            r4.leftMargin = r6
            dxq r6 = new dxq
            android.view.View r5 = r5.a(r7)
            r6.<init>(r3, r5, r4, r2)
            goto L_0x013f
        L_0x010c:
            if (r5 == 0) goto L_0x013e
            android.widget.LinearLayout$LayoutParams r4 = r5.q()
            r9 = 1110966272(0x42380000, float:46.0)
            int r9 = defpackage.agn.a(r6, r9)
            com.autonavi.map.suspend.refactor.gps.GPSButton r10 = r5.d()
            if (r10 == 0) goto L_0x0131
            r10.measure(r7, r7)
            int r7 = r10.getMeasuredHeight()
            if (r7 <= 0) goto L_0x0131
            int r7 = r10.getMeasuredHeight()
            int r6 = defpackage.agn.a(r6, r8)
            int r9 = r7 + r6
        L_0x0131:
            r4.bottomMargin = r9
            dxq r6 = new dxq
            android.view.View r5 = r5.e(r2)
            r7 = 2
            r6.<init>(r3, r5, r4, r7)
            goto L_0x013f
        L_0x013e:
            r6 = r1
        L_0x013f:
            if (r6 == 0) goto L_0x003a
            ccv r4 = r11.h
            android.view.View r5 = r6.b
            android.view.ViewGroup$LayoutParams r7 = r6.c
            int r8 = r6.d
            r4.addWidget(r5, r7, r8)
            java.util.HashMap<java.lang.Integer, dxq> r4 = r11.j
            java.lang.Integer r5 = java.lang.Integer.valueOf(r3)
            r4.put(r5, r6)
            java.util.ArrayList<java.lang.Integer> r4 = r11.a
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r4.add(r3)
            goto L_0x003a
        L_0x0160:
            java.util.ArrayList<java.lang.Integer> r0 = r11.b
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)
            boolean r3 = r0.contains(r3)
            if (r3 == 0) goto L_0x0184
            java.util.HashMap<java.lang.Integer, dxq> r3 = r11.j
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            java.lang.Object r3 = r3.get(r4)
            dxq r3 = (defpackage.dxq) r3
            if (r3 == 0) goto L_0x0184
            android.view.View r3 = r3.b
            dxp$1 r4 = new dxp$1
            r4.<init>()
            r3.setOnClickListener(r4)
        L_0x0184:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r2)
            boolean r3 = r0.contains(r3)
            if (r3 == 0) goto L_0x0198
            ccy r3 = r11.i
            dxp$2 r4 = new dxp$2
            r4.<init>()
            r3.a(r4)
        L_0x0198:
            r3 = 8
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            boolean r4 = r0.contains(r4)
            if (r4 == 0) goto L_0x01c4
            java.util.HashMap<java.lang.Integer, dxq> r4 = r11.j
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.Object r3 = r4.get(r3)
            dxq r3 = (defpackage.dxq) r3
            if (r3 == 0) goto L_0x01c4
            android.view.View r4 = r3.b
            boolean r4 = r4 instanceof com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView
            if (r4 == 0) goto L_0x01c4
            android.view.View r3 = r3.b
            com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView r3 = (com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView) r3
            dxp$3 r4 = new dxp$3
            r4.<init>()
            r3.setOnEntryEventListener(r4)
        L_0x01c4:
            r3 = 6
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            boolean r4 = r0.contains(r4)
            if (r4 == 0) goto L_0x01e7
            java.util.HashMap<java.lang.Integer, dxq> r4 = r11.j
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.Object r3 = r4.get(r3)
            dxq r3 = (defpackage.dxq) r3
            if (r3 == 0) goto L_0x01e7
            android.view.View r3 = r3.b
            dxp$4 r4 = new dxp$4
            r4.<init>()
            r3.setOnClickListener(r4)
        L_0x01e7:
            r3 = 7
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            boolean r0 = r0.contains(r4)
            if (r0 == 0) goto L_0x020a
            java.util.HashMap<java.lang.Integer, dxq> r0 = r11.j
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.Object r0 = r0.get(r3)
            dxq r0 = (defpackage.dxq) r0
            if (r0 == 0) goto L_0x020a
            android.view.View r0 = r0.b
            dxp$5 r3 = new dxp$5
            r3.<init>()
            com.amap.bundle.utils.ui.NoDBClickUtil.a(r0, r3)
        L_0x020a:
            r11.o = r2
        L_0x020c:
            ccv r0 = r11.h
            if (r0 == 0) goto L_0x0217
            ccv r0 = r11.h
            android.view.View r0 = r0.getSuspendView()
            return r0
        L_0x0217:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dxp.b():android.view.View");
    }

    public final void a(b bVar) {
        this.c = bVar;
    }

    public final void a(a aVar) {
        this.d = aVar;
    }

    public final void a(c cVar) {
        this.e = cVar;
    }

    public final void a(int i2) {
        float f2 = (float) i2;
        if (this.n != f2) {
            Iterator<Integer> it = this.a.iterator();
            while (it.hasNext()) {
                dxq dxq = this.j.get(Integer.valueOf(it.next().intValue()));
                if (dxq != null) {
                    a(dxq, i2);
                }
            }
            this.n = f2;
        }
    }

    public final void a(float f2) {
        if (this.m != f2) {
            Iterator<Integer> it = this.a.iterator();
            while (it.hasNext()) {
                dxq dxq = this.j.get(Integer.valueOf(it.next().intValue()));
                if (dxq != null) {
                    dxq.a(f2);
                    if (this.m != 0.0f && f2 == 0.0f) {
                        a(dxq, 8);
                    } else if (this.m == 0.0f && f2 != 0.0f) {
                        a(dxq, 0);
                    }
                }
            }
            this.m = f2;
        }
    }

    public final void b(int i2) {
        if (!this.a.contains(Integer.valueOf(i2))) {
            dxq dxq = this.j.get(Integer.valueOf(i2));
            if (dxq != null) {
                if (this.n == 0.0f) {
                    a(dxq, 0);
                    dxq.a(1.0f);
                }
                dxq.a(this.m);
                this.a.add(Integer.valueOf(i2));
            }
        }
    }

    public final void c(int i2) {
        if (this.a.contains(Integer.valueOf(i2))) {
            dxq dxq = this.j.get(Integer.valueOf(i2));
            if (dxq != null) {
                if (this.n == 0.0f) {
                    a(dxq, 8);
                }
                this.a.remove(this.a.indexOf(Integer.valueOf(i2)));
            }
        }
    }

    public final void a(boolean z) {
        a(z, false);
    }

    /* access modifiers changed from: 0000 */
    public final void a(boolean z, boolean z2) {
        if (this.b.contains(Integer.valueOf(4)) && this.i != null) {
            this.i.d(z);
            MapManager mapManager = (MapManager) this.f.get();
            if (mapManager != null) {
                bqx bqx = (bqx) ank.a(bqx.class);
                if (bqx != null) {
                    bqx.a(false, z, z2, mapManager, AMapPageUtil.getAppContext());
                }
                if (z && mapManager.getMapView() != null) {
                    mapManager.getMapView().t();
                }
            }
            this.g = z;
        }
    }

    private void a(dxq dxq, int i2) {
        if (dxq != null) {
            if (dxq.a != 3) {
                dxq.a(i2);
            }
            if (dxq.a == 5) {
                cde cde = (cde) this.l.get();
                if (!(cde == null || cde.b() == null)) {
                    if (i2 == 8 || i2 == 4) {
                        cde.b().disableView(4);
                        return;
                    }
                    cde.b().enableView(4);
                }
            }
        }
    }
}
