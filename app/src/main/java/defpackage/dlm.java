package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.searchservice.api.ISearchService;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.MapApplication;
import com.autonavi.minimap.R;
import com.autonavi.minimap.intent.BaseIntent;
import com.autonavi.minimap.intent.BaseIntent.MyShortUrlListener;
import com.autonavi.minimap.intent.MsgSharedIntent$4;
import com.autonavi.server.aos.serverkey;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

/* renamed from: dlm reason: default package */
/* compiled from: MsgSharedIntent */
public final class dlm extends BaseIntent {
    Uri s = null;
    Uri t = null;
    Handler u = new Handler() {
        public final void handleMessage(Message message) {
            dlm.this.t = Uri.parse(dlm.this.e);
            dlm.this.h();
        }
    };
    Handler v = new Handler() {
        public final void handleMessage(Message message) {
            if (dlm.this.e != null) {
                dlm.this.t = Uri.parse(dlm.this.e);
                dlm.a(dlm.this);
            }
        }
    };

    /* renamed from: dlm$a */
    /* compiled from: MsgSharedIntent */
    class a implements aeb<aud> {
        a() {
        }

        /* renamed from: a */
        public final void callback(final aud aud) {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                aho.a(new Runnable() {
                    public final void run() {
                        a.this.callback(aud);
                    }
                });
                return;
            }
            bck bck = (bck) defpackage.esb.a.a.a(bck.class);
            if (bck != null) {
                bck.c();
            }
            if (aud == null || aud.b.d == null) {
                dlm.this.e();
            }
            ArrayList<POI> arrayList = aud.b.d;
            if (arrayList == null || arrayList.size() <= 0) {
                dlm.this.e();
                return;
            }
            POI poi = arrayList.get(1);
            poi.setIconId(R.drawable.b_poi);
            if (dlm.this.n != null) {
                dlm.this.n.a(poi, 13);
            }
            dlm.this.f();
        }

        public final void error(int i) {
            dlm.this.e();
        }
    }

    private static boolean b(String str) {
        if (!str.contains(".") && str.length() >= 4) {
            return false;
        }
        return true;
    }

    public dlm(Activity activity, Intent intent) {
        super(activity, intent);
    }

    public final boolean c() {
        if (this.d == null || !this.d.equals("android.intent.action.VIEW") || this.e == null) {
            return false;
        }
        this.s = Uri.parse(this.e);
        super.b();
        String host = this.s.getHost();
        if (!this.s.getScheme().equals("http") || (!host.equals("wb.amap.com") && !host.equals("amap.com") && !host.equals("dypx.amap.com") && !host.equals("surl.testing.amap.com") && !host.equals("mo.amap.com") && !host.equals("f.amap.com"))) {
            return false;
        }
        if (host.equals("wb.amap.com")) {
            this.u.sendMessage(this.u.obtainMessage());
            this.f = false;
            this.m = true;
        } else if (host.equals("mo.amap.com")) {
            this.v.sendMessage(this.v.obtainMessage());
            this.f = false;
            this.m = true;
        } else {
            this.f = true;
            this.m = true;
        }
        return this.m;
    }

    public final void b() {
        super.b();
    }

    public final boolean d() {
        if (super.d()) {
            return true;
        }
        if (this.j == null) {
            return false;
        }
        bnw.a((String) this.j.get(0), (String) this.j.get(1), "", new MyShortUrlListener());
        return true;
    }

    public final void g() {
        this.f = true;
        this.j = new ArrayList();
        this.j.add("2");
        this.j.add(this.e.substring(this.e.lastIndexOf("/") + 1));
        d();
    }

    public final void a(String str) {
        if (str != null && str.length() > 0) {
            try {
                str = URLDecoder.decode(str, "utf-8");
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
            this.e = str;
            this.t = Uri.parse(str);
            h();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void h() {
        if (this.t != null) {
            String queryParameter = this.t.getQueryParameter(SuperId.BIT_1_MAIN_VOICE_ASSISTANT);
            String queryParameter2 = this.t.getQueryParameter("q");
            String queryParameter3 = this.t.getQueryParameter(UploadQueueMgr.MSGTYPE_REALTIME);
            String queryParameter4 = this.t.getQueryParameter(SuperId.BIT_1_MAIN_BUSSTATION);
            String queryParameter5 = this.t.getQueryParameter("s");
            String queryParameter6 = this.t.getQueryParameter("l");
            String queryParameter7 = this.t.getQueryParameter(SuperId.BIT_1_RQBXY);
            String queryParameter8 = this.t.getQueryParameter("z");
            String queryParameter9 = this.t.getQueryParameter("d");
            String queryParameter10 = this.t.getQueryParameter("articleid");
            String queryParameter11 = this.t.getQueryParameter("OpenURL");
            String queryParameter12 = this.t.getQueryParameter("ExternalURL");
            if (queryParameter != null) {
                g(queryParameter);
            } else if (queryParameter2 != null) {
                f(queryParameter2);
            } else if (queryParameter3 != null) {
                i(queryParameter3);
            } else if (queryParameter4 != null) {
                j(queryParameter4);
            } else if (queryParameter5 != null) {
                k(queryParameter5);
            } else if (queryParameter6 != null) {
                e(queryParameter6);
            } else if (queryParameter7 != null) {
                d(queryParameter7);
            } else if (queryParameter8 != null) {
                k();
            } else if (queryParameter9 != null) {
                j();
            } else if (queryParameter10 != null) {
                c(queryParameter10);
            } else if (queryParameter11 != null) {
                a(this.t);
            } else {
                if (queryParameter12 != null) {
                    i();
                }
            }
        }
    }

    private void a(final Uri uri) {
        String queryParameter = uri.getQueryParameter("OpenURL");
        String queryParameter2 = uri.getQueryParameter("urlType");
        final String queryParameter3 = uri.getQueryParameter("contentType");
        if ("1".equals(queryParameter2)) {
            bgx bgx = (bgx) defpackage.esb.a.a.a(bgx.class);
            if (bgx != null) {
                queryParameter = bgx.getUrl(queryParameter);
            }
        }
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            aja aja = new aja(queryParameter);
            aja.b = new ajf() {
                public final boolean d() {
                    return !"autonavi".equals(queryParameter3);
                }

                public final b c() {
                    return new b() {
                        public final long c() {
                            return 1000;
                        }

                        public final boolean a() {
                            return !"autonavi".equals(queryParameter3);
                        }

                        public final String b() {
                            if ("autonavi".equals(queryParameter3)) {
                                return null;
                            }
                            return uri.getQueryParameter("websiteName");
                        }
                    };
                }
            };
            aix aix = (aix) defpackage.esb.a.a.a(aix.class);
            if (aix != null) {
                aix.a(pageContext, aja);
            }
        }
    }

    private void i() {
        try {
            this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.s.getQueryParameter("ExternalURL"))));
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }

    private static void c(String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putSerializable("articleItemId", str);
        AMapPageUtil.getPageContext().startPage((String) "amap.life.action.WeekendHappyDetailPage", pageBundle);
    }

    private static void j() {
        AMapPageUtil.getPageContext().startPage((String) "amap.life.action.WeekendHappyMainPage", new PageBundle());
    }

    private void e(String str) {
        int i;
        int i2;
        if (str.length() == 0) {
            this.m = false;
            return;
        }
        String[] split = str.split(",");
        if (split == null) {
            this.m = false;
        } else if (split.length < 2) {
            this.m = false;
        } else {
            String str2 = split[1];
            String str3 = split[0];
            try {
                if (!b(str3) || !b(str2)) {
                    i = Integer.parseInt(str3);
                    i2 = Integer.parseInt(str2);
                } else {
                    Point a2 = cfg.a(Double.parseDouble(str2), Double.parseDouble(str3));
                    int i3 = a2.x;
                    i2 = a2.y;
                    i = i3;
                }
                if (this.n != null) {
                    this.n.a(i, i2);
                }
            } catch (Exception unused) {
                this.m = false;
            }
        }
    }

    private void f(String str) {
        int i;
        int i2;
        String str2 = "";
        if (str.length() == 0) {
            this.m = false;
            return;
        }
        String[] split = str.split(",");
        if (split == null) {
            this.m = false;
            return;
        }
        int length = split.length;
        if (length < 2) {
            this.m = false;
            return;
        }
        String str3 = split[0];
        String str4 = split[1];
        if (length > 2) {
            str2 = split[2];
        }
        try {
            if (!b(str4) || !b(str3)) {
                i2 = Integer.parseInt(str4);
                i = Integer.parseInt(str3);
            } else {
                Point a2 = cfg.a(Double.parseDouble(str3), Double.parseDouble(str4));
                i2 = a2.x;
                i = a2.y;
            }
            if (str2.length() > 0) {
                POI createPOI = POIFactory.createPOI(str2, new GeoPoint(i2, i));
                createPOI.setIconId(R.drawable.b_poi);
                if (this.n != null) {
                    this.n.a(createPOI, 13);
                }
                f();
                return;
            }
            a(new GeoPoint(i2, i));
        } catch (Exception unused) {
            this.m = false;
        }
    }

    private void a(GeoPoint geoPoint) {
        this.l = new dmf();
        this.l.a = geoPoint;
        this.l.b = this.a.getApplication().getResources().getString(R.string.wait_for_search);
        this.f = true;
        this.m = true;
        d();
    }

    private void g(String str) {
        int i;
        int i2;
        String str2 = "";
        if (str.length() == 0) {
            this.m = false;
            return;
        }
        String[] split = str.split(",");
        if (split == null) {
            this.m = false;
            return;
        }
        int length = split.length;
        if (length < 4) {
            this.m = false;
            return;
        }
        String str3 = split[0];
        String str4 = split[1];
        String str5 = split[2];
        String str6 = split[3];
        if (length == 5) {
            str2 = split[4];
        }
        try {
            if (!b(str5) || !b(str4)) {
                i2 = Integer.parseInt(str5);
                i = Integer.parseInt(str4);
            } else {
                Point a2 = cfg.a(Double.parseDouble(str4), Double.parseDouble(str5));
                i2 = a2.x;
                i = a2.y;
            }
            POI createPOI = POIFactory.createPOI(str6, new GeoPoint(i2, i));
            createPOI.setId(str3);
            createPOI.setAddr(str2);
            createPOI.setIconId(R.drawable.b_poi);
            if (this.n != null) {
                this.n.a(createPOI, 13);
            }
            f();
        } catch (Exception unused) {
            h(str3);
        }
    }

    private void h(String str) {
        ael ael = new ael(str);
        ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
        if (iSearchService != null) {
            iSearchService.b(aew.a((aem) ael), 0, new a());
        }
        this.f = true;
        this.m = true;
    }

    private void i(String str) {
        int i;
        int i2;
        int i3;
        int i4;
        String str2;
        int i5;
        int i6;
        String string = AMapAppGlobal.getApplication().getString(R.string.specific_start);
        String string2 = AMapAppGlobal.getApplication().getString(R.string.specific_end);
        AMapAppGlobal.getApplication().getString(R.string.specific_turn_point);
        String[] split = str.split(",");
        if (split != null && split.length >= 8) {
            try {
                if (!b(split[1]) || !b(split[0])) {
                    i = Integer.parseInt(split[1]);
                    i2 = Integer.parseInt(split[0]);
                } else {
                    Point a2 = cfg.a(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
                    i = a2.x;
                    i2 = a2.y;
                }
                if (split[2].length() > 0) {
                    string = split[2];
                }
                try {
                    if (!b(split[4]) || !b(split[3])) {
                        i3 = Integer.parseInt(split[4]);
                        i4 = Integer.parseInt(split[3]);
                    } else {
                        Point a3 = cfg.a(Double.parseDouble(split[3]), Double.parseDouble(split[4]));
                        i3 = a3.x;
                        i4 = a3.y;
                    }
                    if (split[5].length() > 0) {
                        string2 = split[5];
                    }
                    try {
                        str2 = split[6];
                    } catch (Exception unused) {
                        str2 = "0";
                    }
                    try {
                        i5 = Integer.parseInt(split[7]);
                    } catch (Exception unused2) {
                        i5 = 0;
                    }
                    if (i5 == 0) {
                        try {
                            i6 = Integer.parseInt(str2);
                        } catch (Exception unused3) {
                            i6 = 0;
                        }
                        str2 = dhw.a(i6);
                    }
                    POI createPOI = POIFactory.createPOI(string, new GeoPoint(i, i2));
                    POI createPOI2 = POIFactory.createPOI(string2, new GeoPoint(i3, i4));
                    if (i5 == 0) {
                        dhz dhz = (dhz) ank.a(dhz.class);
                        dhx dhx = new dhx(createPOI, createPOI2, a(split));
                        if (dhz != null) {
                            dhz.a(dhx);
                        }
                        this.f = false;
                        return;
                    }
                    if (i5 == 1) {
                        atb atb = (atb) defpackage.esb.a.a.a(atb.class);
                        if (atb != null) {
                            this.h = atb.d();
                            if (this.h != null) {
                                this.h.setFromPOI(createPOI);
                                this.h.setToPOI(createPOI2);
                                this.h.setMethod(str2);
                                this.f = true;
                            }
                        } else {
                            return;
                        }
                    } else if (i5 == 2) {
                        avi avi = (avi) defpackage.esb.a.a.a(avi.class);
                        if (avi != null) {
                            this.i = avi.a((Context) this.a);
                            if (this.i != null) {
                                this.i.setFromPOI(createPOI);
                                this.i.setToPOI(createPOI2);
                                this.i.setMethod(str2);
                                this.f = true;
                            }
                        } else {
                            return;
                        }
                    }
                    d();
                } catch (Exception unused4) {
                }
            } catch (Exception unused5) {
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x007b A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.autonavi.common.model.POI> a(java.lang.String[] r9) {
        /*
            r0 = 0
            r1 = 8
            if (r9 == 0) goto L_0x0019
            int r2 = r9.length
            r3 = 9
            if (r2 < r3) goto L_0x0019
            r2 = r9[r1]
            int r2 = r2.length()
            if (r2 <= 0) goto L_0x0019
            r2 = r9[r1]
            int r2 = java.lang.Integer.parseInt(r2)
            goto L_0x001a
        L_0x0019:
            r2 = 0
        L_0x001a:
            r3 = 0
            r4 = 3
            int r2 = java.lang.Math.min(r2, r4)
            if (r2 <= 0) goto L_0x007e
            int r4 = r9.length
            int r5 = r2 * 3
            int r5 = r5 + r1
            if (r4 <= r5) goto L_0x007e
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
        L_0x002d:
            if (r0 >= r2) goto L_0x007e
            int r1 = r0 * 3
            int r4 = r1 + 11
            r4 = r9[r4]
            int r5 = r1 + 9
            r6 = r9[r5]
            boolean r6 = b(r6)
            if (r6 == 0) goto L_0x005f
            int r6 = r1 + 10
            r7 = r9[r6]
            boolean r7 = b(r7)
            if (r7 == 0) goto L_0x005f
            r1 = r9[r5]
            double r7 = java.lang.Double.parseDouble(r1)
            r1 = r9[r6]
            double r5 = java.lang.Double.parseDouble(r1)
            com.autonavi.common.model.GeoPoint r1 = new com.autonavi.common.model.GeoPoint
            r1.<init>(r5, r7)
            com.autonavi.common.model.POI r1 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r4, r1)
            goto L_0x0076
        L_0x005f:
            int r1 = r1 + 10
            r1 = r9[r1]
            int r1 = java.lang.Integer.parseInt(r1)
            r5 = r9[r5]
            int r5 = java.lang.Integer.parseInt(r5)
            com.autonavi.common.model.GeoPoint r6 = new com.autonavi.common.model.GeoPoint
            r6.<init>(r1, r5)
            com.autonavi.common.model.POI r1 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r4, r6)
        L_0x0076:
            if (r1 == 0) goto L_0x007b
            r3.add(r1)
        L_0x007b:
            int r0 = r0 + 1
            goto L_0x002d
        L_0x007e:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dlm.a(java.lang.String[]):java.util.List");
    }

    private static void j(String str) {
        int i;
        int i2;
        int i3;
        int i4;
        String[] split = str.split(",");
        if (split != null && split.length >= 5) {
            POI poi = null;
            try {
                if (!b(split[1]) || !b(split[0])) {
                    i = Integer.parseInt(split[1]);
                    i2 = Integer.parseInt(split[0]);
                } else {
                    Point a2 = cfg.a(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
                    i = a2.x;
                    i2 = a2.y;
                }
                if (i > 0 && i2 > 0) {
                    poi = POIFactory.createPOI(MapApplication.getApplication().getString(R.string.LocationMe), new GeoPoint(i, i2));
                }
                try {
                    if (!b(split[3]) || !b(split[2])) {
                        i3 = Integer.parseInt(split[3]);
                        i4 = Integer.parseInt(split[2]);
                    } else {
                        Point a3 = cfg.a(Double.parseDouble(split[2]), Double.parseDouble(split[3]));
                        i3 = a3.x;
                        i4 = a3.y;
                    }
                    if (i3 > 0 && i4 > 0) {
                        try {
                            String str2 = split[4];
                            if (str2 != null) {
                                POI createPOI = POIFactory.createPOI("", new GeoPoint(i3, i4));
                                dfm dfm = (dfm) ank.a(dfm.class);
                                if (dfm != null) {
                                    dfm.a(poi, createPOI, str2);
                                }
                            }
                        } catch (Exception e) {
                            kf.a((Throwable) e);
                        }
                    }
                } catch (Exception unused) {
                }
            } catch (Exception unused2) {
            }
        }
    }

    private void k(String str) {
        int i;
        int i2;
        if (str.length() == 0) {
            this.m = false;
            return;
        }
        String[] split = str.split(",");
        if (split == null) {
            this.m = false;
        } else if (split.length < 6) {
            this.m = false;
        } else {
            try {
                if (!b(split[0]) || !b(split[1])) {
                    i = Integer.parseInt(split[0]);
                    i2 = Integer.parseInt(split[1]);
                } else {
                    Point a2 = cfg.a(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
                    i = a2.x;
                    i2 = a2.y;
                }
                int i3 = i2;
                int i4 = i;
                String str2 = split[2];
                String str3 = split[3];
                String str4 = split[4];
                String str5 = split[5];
                String replace = str4.replace(Token.SEPARATOR, "+");
                String replace2 = str5.replace(Token.SEPARATOR, "+");
                String b = agw.b(replace, serverkey.getSsoKey());
                String b2 = agw.b(replace2, serverkey.getSsoKey());
                if (this.n != null) {
                    new cqe(i3, i4, str2, str3, b, b2);
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    private static void k() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage((String) "amap.basemap.action.acticities", (PageBundle) null);
        }
    }

    private void d(String str) {
        asy asy = (asy) defpackage.esb.a.a.a(asy.class);
        if (asy != null) {
            asy.a().b(str, "", new MsgSharedIntent$4(this));
        }
    }

    static /* synthetic */ void a(dlm dlm) {
        String str;
        if (dlm.t != null) {
            String queryParameter = dlm.t.getQueryParameter("q");
            if (queryParameter != null) {
                if (!queryParameter.contains(",")) {
                    String queryParameter2 = dlm.t.getQueryParameter("name");
                    if (queryParameter2 != null) {
                        int[] iArr = new int[2];
                        byte[] bytes = queryParameter.getBytes();
                        int a2 = dln.a(bytes);
                        for (int i = 0; i < a2; i++) {
                            if (bytes[i] == 45 || bytes[i] == 32) {
                                int i2 = i;
                                while (i2 < a2 - 1) {
                                    int i3 = i2 + 1;
                                    bytes[i2] = bytes[i3];
                                    i2 = i3;
                                }
                                bytes[i2] = 0;
                            }
                        }
                        int[] iArr2 = null;
                        if (dln.a(bytes) == 12) {
                            int i4 = 0;
                            int i5 = 0;
                            while (true) {
                                if (i4 < 6) {
                                    int a3 = dln.a(bytes[i4]);
                                    if (a3 == 24) {
                                        break;
                                    }
                                    i4++;
                                    int i6 = 1;
                                    for (int i7 = i4; i7 < 6; i7++) {
                                        i6 *= 24;
                                    }
                                    i5 += a3 * i6;
                                } else if (i5 <= 136000000 && i5 >= 73000000) {
                                    iArr[0] = i5;
                                    int i8 = 0;
                                    int i9 = 0;
                                    while (true) {
                                        if (i8 < 6) {
                                            int a4 = dln.a(bytes[i8 + 6]);
                                            if (a4 == 24) {
                                                break;
                                            }
                                            i8++;
                                            int i10 = 1;
                                            for (int i11 = i8; i11 < 6; i11++) {
                                                i10 *= 24;
                                            }
                                            i9 += a4 * i10;
                                        } else if (i9 <= 55000000 && i9 >= 17000000) {
                                            iArr[1] = i9;
                                            iArr2 = iArr;
                                        }
                                    }
                                }
                            }
                        }
                        if (iArr2 != null && iArr2.length > 1) {
                            double d = ((double) iArr2[0]) / 1000000.0d;
                            double d2 = ((double) iArr2[1]) / 1000000.0d;
                            try {
                                str = URLDecoder.decode(queryParameter2, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                str = queryParameter2;
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append(d2);
                            sb.append(",");
                            sb.append(d);
                            sb.append(",");
                            sb.append(str);
                            dlm.f(sb.toString());
                        }
                    }
                } else {
                    String[] split = queryParameter.split(",");
                    if (split != null && split.length >= 2) {
                        double parseDouble = Double.parseDouble(split[0]);
                        double parseDouble2 = Double.parseDouble(split[1]);
                        String queryParameter3 = dlm.t.getQueryParameter("name");
                        if (queryParameter3 != null) {
                            try {
                                queryParameter3 = URLDecoder.decode(queryParameter3, "UTF-8");
                            } catch (UnsupportedEncodingException e2) {
                                e2.printStackTrace();
                            }
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(parseDouble);
                            sb2.append(",");
                            sb2.append(parseDouble2);
                            sb2.append(",");
                            sb2.append(queryParameter3);
                            dlm.f(sb2.toString());
                        }
                    }
                }
            }
        }
    }
}
