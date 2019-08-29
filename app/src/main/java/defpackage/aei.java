package defpackage;

import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.bl.search.InfoliteCallback;
import com.autonavi.bl.search.InfoliteResult;
import com.autonavi.bl.search.SearchService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: aei reason: default package */
/* compiled from: SearchInfoliteServiceImpl */
final class aei {
    final SearchService a;
    Map<Long, aeb> b;
    private a c;

    /* renamed from: aei$a */
    /* compiled from: SearchInfoliteServiceImpl */
    public class a extends InfoliteCallback {
        public a() {
        }

        public final void callBack(long j, final InfoliteResult infoliteResult) {
            final aeb aeb = aei.this.b.get(Long.valueOf(j));
            if (aeb == null) {
                aeb aeb2 = aeb;
                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException unused) {
                    }
                    aeb2 = aei.this.b.get(Long.valueOf(j));
                    if (aeb2 != null) {
                        break;
                    }
                }
                aeb = aeb2;
                if (aeb == null) {
                    return;
                }
            }
            if (infoliteResult != null) {
                try {
                    if (infoliteResult.Result != null) {
                        aho.a(new Runnable() {
                            public final void run() {
                                aeb.callback(infoliteResult);
                            }
                        });
                        aei.this.b.remove(Long.valueOf(j));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    aho.a(new Runnable() {
                        public final void run() {
                            aeb.error(0);
                        }
                    });
                } catch (Throwable th) {
                    aei.this.b.remove(Long.valueOf(j));
                    throw th;
                }
            }
            aeb.error(0);
            aei.this.b.remove(Long.valueOf(j));
        }

        public final void error(long j, final int i) {
            final aeb aeb = aei.this.b.get(Long.valueOf(j));
            if (aeb != null) {
                try {
                    aho.a(new Runnable() {
                        public final void run() {
                            aeb aeb = aeb;
                            int i = i;
                            int i2 = i != 6 ? (i == 258 || i == 515) ? 3 : -1 : 1;
                            aeb.error(i2);
                        }
                    });
                } finally {
                    aei.this.b.remove(Long.valueOf(j));
                }
            }
        }
    }

    /* renamed from: aei$b */
    /* compiled from: SearchInfoliteServiceImpl */
    static class b {
        static final aei a = new aei(0);
    }

    /* synthetic */ aei(byte b2) {
        this();
    }

    private aei() {
        this.b = new ConcurrentHashMap();
        this.a = SearchService.createServiceS(ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.SEARCH_AOS_URL_KEY), bno.a);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00e8  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x012c  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x013f  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0142  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final defpackage.adx a(com.autonavi.bl.search.InfoliteParam r13, int r14, defpackage.aeb<com.autonavi.bl.search.InfoliteResult> r15) {
        /*
            r12 = this;
            double r0 = r13.longitude
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L_0x0017
            double r0 = r13.latitude
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L_0x0017
            r0 = -4571364728013586432(0xc08f400000000000, double:-1000.0)
            r13.latitude = r0
            r13.longitude = r0
        L_0x0017:
            com.autonavi.bl.search.OfflineParam r0 = new com.autonavi.bl.search.OfflineParam
            r0.<init>()
            java.lang.String r1 = r13.keywords
            r0.keyword = r1
            java.lang.String r1 = "IDQ"
            java.lang.String r4 = r13.query_type
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x002e
            java.lang.String r1 = r13.id
            r0.keyword = r1
        L_0x002e:
            r1 = 200(0xc8, float:2.8E-43)
            r0.resultMaxCount = r1
            r1 = 2
            r0.searchForm = r1
            java.lang.String r4 = "TQUERY"
            java.lang.String r5 = r13.query_type
            boolean r4 = r4.equals(r5)
            r5 = 1
            if (r4 != 0) goto L_0x005b
            java.lang.String r4 = "RQBXY"
            java.lang.String r6 = r13.query_type
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L_0x004d
            r0.searchType = r1
            goto L_0x005d
        L_0x004d:
            java.lang.String r1 = "IDQ"
            java.lang.String r4 = r13.query_type
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x005b
            r1 = 5
            r0.searchType = r1
            goto L_0x005d
        L_0x005b:
            r0.searchType = r5
        L_0x005d:
            r1 = 0
            r0.languageType = r1
            r4 = 20000(0x4e20, float:2.8026E-41)
            r0.aroundRadius = r4
            java.lang.String r4 = r13.city
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x009c
            java.lang.String r4 = r13.city
            int r4 = r4.length()
            r6 = 6
            if (r4 >= r6) goto L_0x0080
            li r4 = defpackage.li.a()
            java.lang.String r6 = r13.city
            lj r4 = r4.a(r6)
            goto L_0x0090
        L_0x0080:
            java.lang.String r4 = r13.city
            int r4 = java.lang.Integer.parseInt(r4)
            li r6 = defpackage.li.a()
            long r7 = (long) r4
            int r4 = (int) r7
            lj r4 = r6.a(r4)
        L_0x0090:
            if (r4 == 0) goto L_0x009c
            com.autonavi.common.model.GeoPoint r6 = new com.autonavi.common.model.GeoPoint
            int r7 = r4.f
            int r4 = r4.g
            r6.<init>(r7, r4)
            goto L_0x00f1
        L_0x009c:
            java.lang.String r4 = "TQUERY"
            java.lang.String r6 = r13.query_type
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L_0x00e8
            com.autonavi.ae.gmap.glinterface.GLGeoPoint r4 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapCenter()
            com.autonavi.common.model.GeoPoint r4 = com.autonavi.common.model.GeoPoint.glGeoPoint2GeoPoint(r4)
            com.autonavi.sdk.location.LocationInstrument r6 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            com.autonavi.common.model.GeoPoint r6 = r6.getLatestPosition()
            if (r6 == 0) goto L_0x00d1
            li r7 = defpackage.li.a()
            double r8 = r6.getLongitude()
            double r10 = r6.getLatitude()
            int r7 = r7.a(r8, r10)
            if (r4 == 0) goto L_0x00d1
            int r8 = r4.getAdCode()
            if (r7 != r8) goto L_0x00d1
            r4 = r6
        L_0x00d1:
            if (r4 != 0) goto L_0x00d4
            goto L_0x00f1
        L_0x00d4:
            int r6 = r4.x
            long r6 = (long) r6
            int r4 = r4.y
            long r8 = (long) r4
            com.autonavi.minimap.map.DPoint r4 = defpackage.cfg.a(r6, r8)
            com.autonavi.common.model.GeoPoint r6 = new com.autonavi.common.model.GeoPoint
            double r7 = r4.x
            double r9 = r4.y
            r6.<init>(r7, r9)
            goto L_0x00f1
        L_0x00e8:
            com.autonavi.common.model.GeoPoint r6 = new com.autonavi.common.model.GeoPoint
            double r7 = r13.longitude
            double r9 = r13.latitude
            r6.<init>(r7, r9)
        L_0x00f1:
            int r4 = r6.getAdCode()
            r0.adcode = r4
            com.autonavi.bl.search.OfflineParam r4 = r13.offline_param
            if (r4 == 0) goto L_0x0118
            com.autonavi.bl.search.OfflineParam r4 = r13.offline_param
            double r7 = r4.latitude
            int r4 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x0118
            com.autonavi.bl.search.OfflineParam r4 = r13.offline_param
            double r7 = r4.longitude
            int r2 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0118
            com.autonavi.bl.search.OfflineParam r2 = r13.offline_param
            double r2 = r2.latitude
            r0.latitude = r2
            com.autonavi.bl.search.OfflineParam r2 = r13.offline_param
            double r2 = r2.longitude
            r0.longitude = r2
            goto L_0x0124
        L_0x0118:
            double r2 = r6.getLatitude()
            r0.latitude = r2
            double r2 = r6.getLongitude()
            r0.longitude = r2
        L_0x0124:
            r13.offline_param = r0
            com.autonavi.bl.search.OfflineParam r0 = r13.offline_param
            if (r0 != 0) goto L_0x012c
            r0 = 0
            goto L_0x0130
        L_0x012c:
            com.autonavi.bl.search.OfflineParam r0 = r13.offline_param
            int r0 = r0.adcode
        L_0x0130:
            if (r14 != r5) goto L_0x0139
            boolean r0 = defpackage.aey.a(r0)
            if (r0 != 0) goto L_0x0139
            r14 = 0
        L_0x0139:
            com.autonavi.bl.search.SearchService r0 = r12.a
            aei$a r1 = r12.c
            if (r1 == 0) goto L_0x0142
            aei$a r1 = r12.c
            goto L_0x014b
        L_0x0142:
            aei$a r1 = new aei$a
            r1.<init>()
            r12.c = r1
            aei$a r1 = r12.c
        L_0x014b:
            long r13 = r0.startKeywordSearch(r13, r14, r1)
            java.util.Map<java.lang.Long, aeb> r0 = r12.b
            java.lang.Long r1 = java.lang.Long.valueOf(r13)
            r0.put(r1, r15)
            aei$1 r15 = new aei$1
            r15.<init>(r13)
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aei.a(com.autonavi.bl.search.InfoliteParam, int, aeb):adx");
    }
}
