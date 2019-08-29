package defpackage;

import android.graphics.Rect;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.Condition;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.model.POI;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SuppressFBWarnings({"EI_EXPOSE_REP"})
/* renamed from: bxh reason: default package */
/* compiled from: SearchResultDataProvider */
public final class bxh extends bxe {
    public InfoliteResult b;
    public List<POI> c;
    public List<ele> d;
    public auu e = new auu();
    public int f = 0;
    public boolean g;
    public int h;
    public Set<String> i;
    public Double[] j = null;
    public Rect k;
    public POI l;
    public String m;
    public String n;
    private int o;
    private boolean p = true;
    private Condition q = null;
    private aui r = null;

    public final void a(InfoliteResult infoliteResult) {
        c(infoliteResult);
        this.n = bcy.l(infoliteResult);
        if (bcy.c(infoliteResult)) {
            this.f = infoliteResult.searchInfo.v;
            this.c = bcy.h(infoliteResult);
            if (this.p) {
                this.e.a = i();
                this.p = false;
            }
            this.d = d(0);
            if (d()) {
                this.o = 10;
            } else {
                this.o = this.d.size();
            }
            j();
            this.j = e().u;
            return;
        }
        this.f = 0;
        this.e.a = 0;
    }

    public final void b(InfoliteResult infoliteResult) {
        c(infoliteResult);
        if (bcy.c(infoliteResult)) {
            this.h = 0;
            this.f = infoliteResult.searchInfo.v;
            this.c = bcy.h(infoliteResult);
            this.d = d(0);
            if (d()) {
                this.o = 10;
            } else {
                this.o = this.d.size();
            }
            this.j = e().u;
            return;
        }
        this.f = 0;
        this.e.a = 0;
    }

    public final void a(int i2) {
        super.a(i2);
        if (i2 == 2) {
            this.q = null;
            this.r = null;
        }
    }

    public final int a(POI poi) {
        if (this.c.size() > 0 && this.c.size() > this.o - this.h && this.o - this.h > 0) {
            this.c.remove(this.o - this.h);
        }
        this.c.add(poi);
        return this.c.size() - 1;
    }

    public final void a() {
        this.e.a = 0;
    }

    private int i() {
        List<POI> b2 = bcy.b(this.c);
        if (b2 == null || b2.size() <= 0) {
            return bcy.a(this.b, this.c) ? 2 : 0;
        }
        return 1;
    }

    private int k() {
        if (this.b == null || this.b.mWrapper == null) {
            return 0;
        }
        return this.b.mWrapper.pagenum;
    }

    public final String b() {
        if (this.b == null || this.b.mWrapper == null) {
            return null;
        }
        return this.b.mWrapper.keywords;
    }

    public final InfoliteParam c() {
        if (this.b != null) {
            return this.b.mWrapper;
        }
        return null;
    }

    public final boolean d() {
        if (bcy.e(this.b) && !this.b.responseHeader.f) {
            return true;
        }
        return false;
    }

    public final auk e() {
        if (this.b == null || this.b.searchInfo == null) {
            return null;
        }
        return this.b.searchInfo.a;
    }

    public final void a(boolean z) {
        this.e.d = z;
    }

    public final POI b(int i2) {
        if (this.c == null || this.c.isEmpty() || this.c.size() <= i2 || i2 < 0) {
            return null;
        }
        return this.c.get(i2);
    }

    public final boolean f() {
        return this.c != null && this.c.size() > 0;
    }

    public final int g() {
        return this.o - this.h;
    }

    private void e(int i2) {
        this.h += i2;
    }

    public final elk c(int i2) {
        if (this.d == null || i2 < 0) {
            return null;
        }
        List<elk> l2 = l();
        if (l2 != null && i2 < l2.size()) {
            return l2.get(i2);
        }
        return null;
    }

    private List<elk> l() {
        if (this.d == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(this.d.size());
        for (ele next : this.d) {
            if (next instanceof elk) {
                arrayList.add((elk) next);
            }
        }
        return arrayList;
    }

    public final String h() {
        if (!bcy.d(this.b)) {
            return "";
        }
        String str = this.b.searchInfo.a.O;
        return str == null ? "" : str;
    }

    private List<ele> a(InfoliteResult infoliteResult, int i2) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(c(infoliteResult, i2));
        a((List<ele>) arrayList, infoliteResult);
        b((List<ele>) arrayList, infoliteResult);
        c((List<ele>) arrayList, infoliteResult);
        return arrayList;
    }

    private static List<ele> d(InfoliteResult infoliteResult) {
        ArrayList arrayList = new ArrayList();
        eli eli = new eli(bcy.q(infoliteResult));
        if (bcy.a(infoliteResult)) {
            eli.a(infoliteResult.searchInfo.l);
        }
        arrayList.add(eli);
        return arrayList;
    }

    private static List<ele> b(InfoliteResult infoliteResult, int i2) {
        ArrayList arrayList = new ArrayList();
        if (!bcy.c(infoliteResult)) {
            return null;
        }
        eld eld = new eld(infoliteResult.searchInfo.r);
        eld.a = infoliteResult.searchInfo.l != null && !infoliteResult.searchInfo.l.isEmpty();
        arrayList.add(eld);
        arrayList.addAll(c(infoliteResult, i2));
        return arrayList;
    }

    private static List<ele> e(InfoliteResult infoliteResult) {
        if (!bcy.b(infoliteResult)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList<POI> arrayList2 = infoliteResult.searchInfo.l;
        boolean n2 = bcy.n(infoliteResult);
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            elj elj = new elj(arrayList2.get(i2));
            elj.a = n2;
            arrayList.add(elj);
            elj.d = i2;
            if (bcy.f(infoliteResult)) {
                elj.c = infoliteResult.mWrapper.keywords;
            }
        }
        return arrayList;
    }

    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r4v2, types: [elk] */
    /* JADX WARNING: type inference failed for: r4v7, types: [elo, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r4v8, types: [java.lang.Object, elf] */
    /* JADX WARNING: type inference failed for: r4v15, types: [elg, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r4v22, types: [elh, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r4v23 */
    /* JADX WARNING: type inference failed for: r4v24 */
    /* JADX WARNING: type inference failed for: r4v25 */
    /* JADX WARNING: type inference failed for: r4v26 */
    /* JADX WARNING: type inference failed for: r4v27 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r4v0
      assigns: [?[OBJECT, ARRAY], elo, elf, elg, elh]
      uses: [elk, java.lang.Object, elo, elh]
      mth insns count: 83
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<defpackage.ele> c(com.autonavi.bundle.entity.infolite.internal.InfoliteResult r8, int r9) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r1 = defpackage.bcy.i(r8)
            r2 = 0
            r3 = 0
            r4 = r3
            r3 = 0
        L_0x000d:
            int r5 = r1.size()
            if (r3 >= r5) goto L_0x00cf
            java.lang.Object r5 = r1.get(r3)
            com.autonavi.common.model.POI r5 = (com.autonavi.common.model.POI) r5
            boolean r6 = defpackage.bcy.b(r5)
            r7 = 1
            if (r6 == 0) goto L_0x0036
            if (r3 != 0) goto L_0x00a1
            elh r4 = new elh
            r4.<init>(r5)
            r0.add(r4)
            int r5 = r1.size()
            if (r5 <= r7) goto L_0x0031
            goto L_0x0032
        L_0x0031:
            r7 = 0
        L_0x0032:
            r4.a = r7
            goto L_0x00a1
        L_0x0036:
            boolean r4 = defpackage.bcy.d(r8)
            r6 = 2000(0x7d0, float:2.803E-42)
            if (r4 == 0) goto L_0x004e
            aus r4 = r8.searchInfo
            auk r4 = r4.a
            int r4 = r4.N
            if (r4 != r6) goto L_0x004e
            boolean r4 = g(r8)
            if (r4 == 0) goto L_0x004e
            r4 = 1
            goto L_0x004f
        L_0x004e:
            r4 = 0
        L_0x004f:
            if (r4 == 0) goto L_0x005a
            elg r4 = new elg
            r4.<init>(r5)
            r0.add(r4)
            goto L_0x00a1
        L_0x005a:
            boolean r4 = defpackage.bcy.d(r8)
            if (r4 == 0) goto L_0x0070
            aus r4 = r8.searchInfo
            auk r4 = r4.a
            int r4 = r4.N
            if (r4 != r6) goto L_0x0070
            boolean r4 = g(r8)
            if (r4 != 0) goto L_0x0070
            r4 = 1
            goto L_0x0071
        L_0x0070:
            r4 = 0
        L_0x0071:
            if (r4 == 0) goto L_0x007c
            elf r4 = new elf
            r4.<init>(r5)
            r0.add(r4)
            goto L_0x00a1
        L_0x007c:
            elo r4 = new elo
            r4.<init>(r5)
            boolean r5 = defpackage.bcy.d(r8)
            if (r5 == 0) goto L_0x0099
            aus r5 = r8.searchInfo
            auk r5 = r5.a
            int r5 = r5.N
            r6 = 1000(0x3e8, float:1.401E-42)
            if (r5 != r6) goto L_0x0099
            boolean r5 = g(r8)
            if (r5 == 0) goto L_0x0099
            r5 = 1
            goto L_0x009a
        L_0x0099:
            r5 = 0
        L_0x009a:
            if (r5 == 0) goto L_0x009e
            r4.a = r7
        L_0x009e:
            r0.add(r4)
        L_0x00a1:
            int r5 = r9 + r3
            r4.d = r5
            boolean r5 = defpackage.bcy.f(r8)
            if (r5 == 0) goto L_0x00b1
            com.autonavi.bl.search.InfoliteParam r5 = r8.mWrapper
            java.lang.String r5 = r5.keywords
            r4.c = r5
        L_0x00b1:
            boolean r5 = defpackage.bcy.d(r8)
            if (r5 == 0) goto L_0x00bf
            aus r5 = r8.searchInfo
            auk r5 = r5.a
            java.lang.String r5 = r5.O
            r4.e = r5
        L_0x00bf:
            boolean r5 = defpackage.bcy.c(r8)
            if (r5 == 0) goto L_0x00cb
            aus r5 = r8.searchInfo
            int r5 = r5.w
            r4.f = r5
        L_0x00cb:
            int r3 = r3 + 1
            goto L_0x000d
        L_0x00cf:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bxh.c(com.autonavi.bundle.entity.infolite.internal.InfoliteResult, int):java.util.List");
    }

    private static boolean f(InfoliteResult infoliteResult) {
        return bcy.e(infoliteResult) && !infoliteResult.responseHeader.f;
    }

    private static boolean g(InfoliteResult infoliteResult) {
        return "left".equals(infoliteResult.searchInfo.a.P);
    }

    private void a(List<ele> list, InfoliteResult infoliteResult) {
        List<aup> o2 = bcy.o(infoliteResult);
        if (o2 != null && o2.size() != 0) {
            for (aup next : o2) {
                int i2 = next.b;
                if (i2 >= 0) {
                    ell ell = new ell(next);
                    ell.b = infoliteResult.mKeyword;
                    if (i2 < list.size()) {
                        list.add(i2, ell);
                    } else {
                        list.add(ell);
                    }
                }
            }
            e(o2.size());
            for (int size = list.size() - 1; size >= 0; size--) {
                ele ele = list.get(size);
                if (ele instanceof ell) {
                    ell ell2 = (ell) ele;
                    if (ell2.a.f == null || ell2.a.f.size() < 2) {
                        list.remove(ele);
                    }
                }
            }
        }
    }

    private void b(List<ele> list, InfoliteResult infoliteResult) {
        if (g(infoliteResult)) {
            ArrayList<aun> arrayList = infoliteResult.searchInfo.j;
            if (arrayList != null) {
                for (aun next : arrayList) {
                    int i2 = next.f;
                    if (i2 >= 0) {
                        eln eln = new eln(next);
                        if (i2 < list.size()) {
                            list.add(i2, eln);
                        } else {
                            list.add(eln);
                        }
                        eln.a = infoliteResult.searchInfo.a.P;
                    }
                }
                e(arrayList.size());
            }
        }
    }

    private void c(List<ele> list, InfoliteResult infoliteResult) {
        if (!g(infoliteResult)) {
            ArrayList<aul> arrayList = infoliteResult.searchInfo.i;
            if (arrayList != null) {
                for (aul next : arrayList) {
                    int i2 = next.f;
                    if (i2 >= 0) {
                        elm elm = new elm(next);
                        if (i2 < list.size()) {
                            list.add(i2, elm);
                        } else {
                            list.add(elm);
                        }
                        elm.a = infoliteResult.searchInfo.a.P;
                    }
                }
                e(arrayList.size());
            }
        }
    }

    public final void c(InfoliteResult infoliteResult) {
        this.b = infoliteResult;
        n();
    }

    private void n() {
        if (bcy.c(this.b)) {
            int k2 = k();
            if (k2 == 1) {
                this.q = this.b.searchInfo.b;
                this.r = this.b.searchInfo.d;
                return;
            }
            if (k2 > 1) {
                this.b.searchInfo.b = this.q;
                this.b.searchInfo.d = this.r;
            }
        }
    }

    private void j() {
        if (this.e.a == 2) {
            ArrayList<POI> arrayList = this.b.searchInfo.l;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if ((((SearchPoi) arrayList.get(size).as(SearchPoi.class)).getRecommendFlag() & 1) != 1) {
                    arrayList.remove(size);
                }
            }
        } else if (this.e.a == 1) {
            bcy.r(this.b);
            POI c2 = bcy.c((List<POI>) this.b.searchInfo.l);
            this.b.searchInfo.l.clear();
            if (c2 != null) {
                this.b.searchInfo.l.add(c2);
            }
        }
        this.c = bcy.h(this.b);
    }

    public final List<ele> d(int i2) {
        InfoliteResult infoliteResult = this.b;
        if (m()) {
            return d(infoliteResult);
        }
        if (bcy.j(infoliteResult)) {
            List<ele> b2 = b(infoliteResult, i2);
            e(1);
            return b2;
        } else if (f(infoliteResult)) {
            return e(infoliteResult);
        } else {
            return a(infoliteResult, i2);
        }
    }

    private boolean m() {
        return this.e.a == 1;
    }
}
