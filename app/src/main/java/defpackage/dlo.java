package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.intent.BaseIntent;
import com.autonavi.minimap.intent.BaseIntent.a;
import com.autonavi.minimap.intent.BaseIntent.d;

/* renamed from: dlo reason: default package */
/* compiled from: ThirdIntent */
public final class dlo extends BaseIntent {
    dlm s;

    public final void a(String str) {
    }

    public dlo(Activity activity, Intent intent) {
        super(activity, intent);
    }

    public final boolean c() {
        boolean z;
        boolean z2;
        if (this.d == null || !this.d.equals("android.intent.action.VIEW") || this.e == null) {
            return false;
        }
        b();
        String scheme = Uri.parse(this.e).getScheme();
        try {
            if (scheme.equals(BioDetector.EXT_KEY_GEO)) {
                String str = this.e;
                int indexOf = str.indexOf("q=");
                if (indexOf >= 0) {
                    a(str, indexOf);
                } else {
                    String[] split = str.split("\\:|\\?");
                    if (split == null) {
                        dlj.a(this.a);
                    } else {
                        String str2 = null;
                        String str3 = split.length > 0 ? split[0] : null;
                        String str4 = split.length >= 2 ? split[1] : null;
                        if (split.length >= 3) {
                            str3 = split[0];
                            str4 = split[1];
                            str2 = split[2];
                        }
                        int i = 17;
                        if (str2 != null) {
                            String a = dlj.a(str2, (String) "offset");
                            String a2 = dlj.a(str2, (String) "z");
                            if (a2 != null) {
                                i = Integer.parseInt(a2);
                            }
                            z2 = a == null || Integer.parseInt(a) != 0;
                            int i2 = new MapSharePreference((String) "SharedPreferences").sharedPrefs().getInt("Z", -1);
                            if (i2 > 0) {
                                i = i2;
                            }
                        } else {
                            z2 = true;
                        }
                        if (str4 != null) {
                            if (str3 != null) {
                                POI a3 = dlj.a(str4, z2);
                                if (a3 == null) {
                                    dlj.a(this.a);
                                } else {
                                    this.q = new a();
                                    this.q.a = a3;
                                    this.q.b = i;
                                    this.f = true;
                                }
                            }
                        }
                        dlj.a(this.a);
                    }
                }
                this.m = true;
            } else if (scheme.equals("content")) {
                this.m = c(this.e);
            } else if (scheme.equals("http")) {
                String str5 = this.e;
                Uri parse = Uri.parse(str5);
                String host = parse.getHost();
                if (!host.equals("wb.amap.com") && !host.equals("amap.com") && !host.equals("dypx.amap.com") && !host.equals("surl.testing.amap.com") && !host.equals("mo.amap.com")) {
                    if (!host.equals("f.amap.com")) {
                        if (host.equals("maps.google.com") || host.equals("ditu.google.cn") || host.equals("www.mapbar.com") || host.equals("maps.google.cn") || host.equals("ditu.google.com")) {
                            if (parse.getQueryParameter("saddr") != null && parse.getQueryParameter("daddr") != null) {
                                z = a(parse);
                                this.m = z;
                            } else if (str5.indexOf("q=") >= 0) {
                                z = d(str5);
                                this.m = z;
                            }
                        }
                        z = false;
                        this.m = z;
                    }
                }
                this.s = new dlm(this.a, this.b);
                this.s.a(this.n);
                z = this.s.c();
                this.f = this.s.f;
                this.m = z;
            } else if (scheme.equals("wechatnav")) {
                this.m = b(this.e);
            }
            return this.m;
        } catch (Exception unused) {
            e();
            return false;
        }
    }

    public final void b() {
        super.b();
        this.s = null;
    }

    public final boolean d() {
        if (this.s == null) {
            return super.d();
        }
        this.s.g();
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0096  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.lang.String r11, int r12) {
        /*
            r10 = this;
            r0 = 2
            int r12 = r12 + r0
            int r1 = r11.length()
            java.lang.String r12 = r11.substring(r12, r1)
            java.lang.String r1 = "?"
            int r1 = r12.indexOf(r1)
            if (r1 >= 0) goto L_0x0016
            int r1 = r12.length()
        L_0x0016:
            r2 = 0
            java.lang.String r12 = r12.substring(r2, r1)
            java.lang.String r1 = ","
            int r1 = r12.indexOf(r1)
            r3 = 1
            if (r1 >= 0) goto L_0x0026
            r1 = 1
            goto L_0x0027
        L_0x0026:
            r1 = 0
        L_0x0027:
            java.lang.String r4 = "\\,|\\(|\\)"
            java.lang.String[] r4 = r12.split(r4)
            r5 = 17
            if (r1 != 0) goto L_0x007b
            if (r4 == 0) goto L_0x007b
            int r1 = r4.length
            if (r1 < r0) goto L_0x007b
            com.autonavi.common.model.POI r11 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            com.autonavi.common.model.GeoPoint r12 = r11.getPoint()
            r1 = r4[r3]
            double r6 = java.lang.Double.parseDouble(r1)
            r1 = r4[r2]
            double r1 = java.lang.Double.parseDouble(r1)
            r12.setLonLat(r6, r1)
            int r12 = r4.length
            r1 = 3
            if (r12 < r1) goto L_0x0057
            r12 = r4[r0]
            r11.setName(r12)
            goto L_0x0064
        L_0x0057:
            android.app.Application r12 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = com.autonavi.minimap.R.string.map_unnamed_location
            java.lang.String r12 = r12.getString(r0)
            r11.setName(r12)
        L_0x0064:
            int r12 = com.autonavi.minimap.R.drawable.b_poi
            r11.setIconId(r12)
            com.autonavi.minimap.intent.BaseIntent$a r12 = new com.autonavi.minimap.intent.BaseIntent$a
            r12.<init>()
            r10.q = r12
            com.autonavi.minimap.intent.BaseIntent$a r12 = r10.q
            r12.a = r11
            com.autonavi.minimap.intent.BaseIntent$a r11 = r10.q
            r11.b = r5
            r10.f = r3
            return
        L_0x007b:
            r1 = 0
            java.lang.String r4 = "utf-8"
            java.lang.String r12 = java.net.URLDecoder.decode(r12, r4)     // Catch:{ Exception -> 0x008f }
            java.lang.String r4 = "\n"
            java.lang.String r6 = " "
            java.lang.String r4 = r12.replace(r4, r6)     // Catch:{ Exception -> 0x008d }
            r12 = r4
            goto L_0x0094
        L_0x008d:
            r4 = move-exception
            goto L_0x0091
        L_0x008f:
            r4 = move-exception
            r12 = r1
        L_0x0091:
            defpackage.kf.a(r4)
        L_0x0094:
            if (r12 == 0) goto L_0x011a
            java.lang.String r4 = "\\:|\\?"
            java.lang.String[] r11 = r11.split(r4)
            int r4 = r11.length
            if (r4 < r0) goto L_0x00a1
            r1 = r11[r3]
        L_0x00a1:
            if (r1 == 0) goto L_0x010b
            java.lang.String r11 = "\\,|\\(|\\)"
            java.lang.String[] r11 = r1.split(r11)
            if (r11 == 0) goto L_0x010b
            int r1 = r11.length
            if (r1 < r0) goto L_0x010b
            r0 = r11[r3]
            double r0 = java.lang.Double.parseDouble(r0)
            r11 = r11[r2]
            double r6 = java.lang.Double.parseDouble(r11)
            r8 = 4625517698782949540(0x403123d70a3d70a4, double:17.14)
            int r11 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r11 >= 0) goto L_0x010b
            r8 = 4632820567053707510(0x404b15c28f5c28f6, double:54.17)
            int r11 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r11 >= 0) goto L_0x010b
            r8 = 4634808484076726518(0x405225c28f5c28f6, double:72.59)
            int r11 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r11 >= 0) goto L_0x010b
            r8 = 4639009849947853947(0x406112e147ae147b, double:136.59)
            int r11 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r11 >= 0) goto L_0x010b
            com.autonavi.common.model.POI r11 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            android.graphics.Point r0 = defpackage.cfg.a(r6, r0)
            int r1 = r0.x
            int r0 = r0.y
            com.autonavi.common.model.GeoPoint r0 = defpackage.cff.a(r1, r0)
            r11.setPoint(r0)
            r11.setName(r12)
            int r0 = com.autonavi.minimap.R.drawable.b_poi
            r11.setIconId(r0)
            com.autonavi.minimap.intent.BaseIntent$a r0 = new com.autonavi.minimap.intent.BaseIntent$a
            r0.<init>()
            r10.q = r0
            com.autonavi.minimap.intent.BaseIntent$a r0 = r10.q
            r0.a = r11
            com.autonavi.minimap.intent.BaseIntent$a r11 = r10.q
            r11.b = r5
            r10.f = r3
            r2 = 1
        L_0x010b:
            if (r2 != 0) goto L_0x011a
            com.autonavi.minimap.intent.BaseIntent$c r11 = new com.autonavi.minimap.intent.BaseIntent$c
            r11.<init>()
            r10.k = r11
            com.autonavi.minimap.intent.BaseIntent$c r11 = r10.k
            r11.a = r12
            r10.f = r3
        L_0x011a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dlo.a(java.lang.String, int):void");
    }

    private boolean b(String str) {
        String str2;
        String str3;
        try {
            NetworkParam.setSa("weixin_navi");
            String string = AMapAppGlobal.getApplication().getString(R.string.intent_weixin_name_reg);
            Uri parse = Uri.parse(str.replace("wechatnav://", "http://www.weixin.com/?"));
            String queryParameter = parse.getQueryParameter("type");
            String queryParameter2 = parse.getQueryParameter("fromcoord");
            String queryParameter3 = parse.getQueryParameter("from");
            String[] split = queryParameter2.split(",");
            double parseDouble = Double.parseDouble(split[0]);
            double parseDouble2 = Double.parseDouble(split[1]);
            double d = parseDouble > parseDouble2 ? parseDouble : parseDouble2;
            if (parseDouble >= parseDouble2) {
                parseDouble = parseDouble2;
            }
            GeoPoint geoPoint = new GeoPoint(d, parseDouble);
            if (TextUtils.isEmpty(queryParameter3)) {
                str2 = AMapAppGlobal.getApplication().getString(R.string.intent_weixin_start);
            } else {
                str2 = queryParameter3.replaceAll(string, "").trim();
            }
            POI createPOI = POIFactory.createPOI(str2, geoPoint);
            String queryParameter4 = parse.getQueryParameter("tocoord");
            String queryParameter5 = parse.getQueryParameter("to");
            String[] split2 = queryParameter4.split(",");
            double parseDouble3 = Double.parseDouble(split2[0]);
            double parseDouble4 = Double.parseDouble(split2[1]);
            double d2 = parseDouble3 > parseDouble4 ? parseDouble3 : parseDouble4;
            if (parseDouble3 >= parseDouble4) {
                parseDouble3 = parseDouble4;
            }
            GeoPoint geoPoint2 = new GeoPoint(d2, parseDouble3);
            if (TextUtils.isEmpty(queryParameter5)) {
                str3 = AMapAppGlobal.getApplication().getString(R.string.intent_weixin_end);
            } else {
                str3 = queryParameter5.replaceAll(string, "").trim();
            }
            POI createPOI2 = POIFactory.createPOI(str3, geoPoint2);
            if ("transit".equals(queryParameter)) {
                atb atb = (atb) a.a.a(atb.class);
                if (atb == null) {
                    return false;
                }
                this.h = atb.d();
                if (this.h != null) {
                    this.h.setFromPOI(createPOI);
                    this.h.setToPOI(createPOI2);
                    this.h.setMethod(atb.f());
                }
            } else if ("driver".equals(queryParameter)) {
                dhz dhz = (dhz) ank.a(dhz.class);
                dhx dhx = new dhx(createPOI, createPOI2);
                if (dhz != null) {
                    dhz.a(dhx);
                }
                this.f = false;
                return true;
            } else if ("walking".equals(queryParameter)) {
                avi avi = (avi) a.a.a(avi.class);
                if (avi == null) {
                    return false;
                }
                this.i = avi.a((Context) this.a);
                if (this.i != null) {
                    this.i.setFromPOI(createPOI);
                    this.i.setToPOI(createPOI2);
                    this.i.setMethod("0");
                }
            } else {
                dhz dhz2 = (dhz) ank.a(dhz.class);
                dhx dhx2 = new dhx(createPOI, createPOI2);
                if (dhz2 != null) {
                    dhz2.a(dhx2);
                }
                this.f = false;
                return true;
            }
            this.f = true;
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean c(java.lang.String r7) {
        /*
            r6 = this;
            android.net.Uri r1 = android.net.Uri.parse(r7)
            java.lang.String r7 = r1.getHost()
            java.lang.String r0 = "com.android.contacts"
            boolean r7 = r0.equals(r7)
            r0 = 0
            if (r7 != 0) goto L_0x0012
            return r0
        L_0x0012:
            android.app.Activity r7 = r6.a
            java.lang.String r2 = "android.permission.READ_CONTACTS"
            java.lang.String[] r2 = new java.lang.String[]{r2}
            boolean r7 = defpackage.kj.a(r7, r2)
            if (r7 != 0) goto L_0x0021
            return r0
        L_0x0021:
            java.lang.String r7 = r1.getPath()
            java.lang.String r2 = "data/"
            int r7 = r7.indexOf(r2)
            if (r7 > 0) goto L_0x002e
            return r0
        L_0x002e:
            r7 = 0
            android.app.Activity r0 = r6.a     // Catch:{ Exception -> 0x0066 }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ Exception -> 0x0066 }
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            android.database.Cursor r0 = r0.query(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x0066 }
            if (r0 == 0) goto L_0x0066
            int r1 = r0.getCount()     // Catch:{ Exception -> 0x0066 }
            if (r1 <= 0) goto L_0x0066
            r1 = r7
            r2 = r1
        L_0x0047:
            boolean r3 = r0.moveToNext()     // Catch:{ Exception -> 0x0068 }
            if (r3 == 0) goto L_0x0068
            java.lang.String r3 = "data7"
            int r3 = r0.getColumnIndex(r3)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r3 = r0.getString(r3)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r1 = "data4"
            int r1 = r0.getColumnIndex(r1)     // Catch:{ Exception -> 0x0064 }
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x0064 }
            r2 = r1
            r1 = r3
            goto L_0x0047
        L_0x0064:
            r1 = r3
            goto L_0x0068
        L_0x0066:
            r1 = r7
            r2 = r1
        L_0x0068:
            if (r1 == 0) goto L_0x0075
            java.lang.String r0 = "null"
            boolean r0 = r1.equals(r0)     // Catch:{ Exception -> 0x0073 }
            if (r0 == 0) goto L_0x0077
            goto L_0x0075
        L_0x0073:
            r0 = move-exception
            goto L_0x009f
        L_0x0075:
            java.lang.String r1 = ""
        L_0x0077:
            if (r2 == 0) goto L_0x0081
            java.lang.String r0 = "null"
            boolean r0 = r2.equals(r0)     // Catch:{ Exception -> 0x0073 }
            if (r0 == 0) goto L_0x0083
        L_0x0081:
            java.lang.String r2 = ""
        L_0x0083:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0073 }
            r0.<init>()     // Catch:{ Exception -> 0x0073 }
            r0.append(r1)     // Catch:{ Exception -> 0x0073 }
            r0.append(r2)     // Catch:{ Exception -> 0x0073 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0073 }
            java.lang.String r1 = new java.lang.String     // Catch:{ Exception -> 0x0073 }
            byte[] r0 = r0.getBytes()     // Catch:{ Exception -> 0x0073 }
            java.lang.String r2 = "UTF-8"
            r1.<init>(r0, r2)     // Catch:{ Exception -> 0x0073 }
            r7 = r1
            goto L_0x00a2
        L_0x009f:
            defpackage.kf.a(r0)
        L_0x00a2:
            r0 = 1
            if (r7 == 0) goto L_0x00b2
            com.autonavi.minimap.intent.BaseIntent$c r1 = new com.autonavi.minimap.intent.BaseIntent$c
            r1.<init>()
            r6.k = r1
            com.autonavi.minimap.intent.BaseIntent$c r1 = r6.k
            r1.a = r7
            r6.f = r0
        L_0x00b2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dlo.c(java.lang.String):boolean");
    }

    private boolean a(Uri uri) {
        GeoPoint geoPoint;
        boolean z;
        boolean z2;
        GeoPoint geoPoint2;
        boolean z3;
        Uri uri2 = uri;
        String queryParameter = uri2.getQueryParameter("dirflg");
        if (queryParameter == null) {
            queryParameter = uri2.getQueryParameter("f");
            if (queryParameter == null) {
                queryParameter = "d";
            }
        }
        if (queryParameter.equals("w")) {
            return false;
        }
        String queryParameter2 = uri2.getQueryParameter("offset");
        boolean z4 = queryParameter2 == null || queryParameter2.length() == 0 || Integer.parseInt(queryParameter2) != 0;
        String queryParameter3 = uri2.getQueryParameter("saddr");
        if (queryParameter3 == null || queryParameter3.length() == 0) {
            return false;
        }
        String[] split = queryParameter3.split(",|\\(|\\)");
        if (split == null) {
            return false;
        }
        String queryParameter4 = uri2.getQueryParameter("daddr");
        if (queryParameter4 == null || queryParameter4.length() == 0) {
            return false;
        }
        String[] split2 = queryParameter4.split(",|\\(|\\)");
        if (split2 == null) {
            return false;
        }
        String string = AMapAppGlobal.getApplication().getString(R.string.specific_start);
        try {
            Double.parseDouble(split[0]);
            Double.parseDouble(split[1]);
            z = false;
            geoPoint = null;
        } catch (Exception unused) {
            geoPoint = new GeoPoint(0, 0);
            z = true;
        }
        if (!z) {
            if (split.length >= 3) {
                String str = split[2];
                if (str != null && str.length() > 0) {
                    string = split[2];
                }
            }
            z2 = z;
            Point a = cfg.a(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
            if (z4) {
                geoPoint = cff.a(a.x, a.y);
            } else {
                geoPoint = new GeoPoint(a.x, a.y);
            }
        } else {
            z2 = z;
        }
        POI createPOI = POIFactory.createPOI(string, geoPoint);
        String string2 = AMapAppGlobal.getApplication().getString(R.string.specific_end);
        try {
            Double.parseDouble(split2[0]);
            Double.parseDouble(split2[1]);
            z3 = false;
            geoPoint2 = null;
        } catch (Exception unused2) {
            geoPoint2 = new GeoPoint(0, 0);
            z3 = true;
        }
        if (!z3) {
            if (split2.length >= 3) {
                String str2 = split2[2];
                if (str2 != null && str2.length() > 0) {
                    string2 = split2[2];
                }
            }
            Point a2 = cfg.a(Double.parseDouble(split2[0]), Double.parseDouble(split2[1]));
            if (z4) {
                geoPoint2 = cff.a(a2.x, a2.y);
            } else {
                geoPoint2 = new GeoPoint(a2.x, a2.y);
            }
        }
        POI createPOI2 = POIFactory.createPOI(string2, geoPoint2);
        if (z2 || z3) {
            this.p = new d();
            if (z2) {
                this.p.a = split[0];
            } else {
                this.p.a = string;
            }
            this.p.b = new GeoPoint(geoPoint.x, geoPoint.y);
            if (z3) {
                this.p.c = split2[0];
            } else {
                this.p.c = string2;
            }
            this.p.d = new GeoPoint(geoPoint2.x, geoPoint2.y);
            if (queryParameter.equals(UploadQueueMgr.MSGTYPE_REALTIME)) {
                this.p.e = queryParameter;
            } else if (queryParameter.equals("d")) {
                this.p.e = queryParameter;
            }
            this.f = true;
            return true;
        }
        if (queryParameter.equals(UploadQueueMgr.MSGTYPE_REALTIME)) {
            atb atb = (atb) a.a.a(atb.class);
            if (atb == null) {
                return false;
            }
            this.h = atb.d();
            if (this.h != null) {
                this.h.setFromPOI(createPOI);
                this.h.setToPOI(createPOI2);
                this.h.setMethod(atb.f());
                this.f = true;
            }
        } else if (queryParameter.equals("d")) {
            dhz dhz = (dhz) ank.a(dhz.class);
            dhx dhx = new dhx(createPOI, createPOI2);
            if (dhz != null) {
                dhz.a(dhx);
            }
            this.f = false;
            return true;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0092 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean d(java.lang.String r12) {
        /*
            r11 = this;
            android.net.Uri r12 = android.net.Uri.parse(r12)
            java.lang.String r0 = "q"
            java.lang.String r12 = r12.getQueryParameter(r0)
            r0 = 0
            if (r12 != 0) goto L_0x000e
            return r0
        L_0x000e:
            java.lang.String r1 = "+"
            int r1 = r12.indexOf(r1)
            java.lang.String r2 = "-"
            int r2 = r12.indexOf(r2)
            r3 = -1
            if (r1 == r3) goto L_0x001e
            goto L_0x001f
        L_0x001e:
            r1 = -1
        L_0x001f:
            if (r2 == r3) goto L_0x0022
            r1 = r2
        L_0x0022:
            r2 = 1
            if (r1 == r3) goto L_0x002a
            int r1 = r1 + r2
            java.lang.String r12 = r12.substring(r1)
        L_0x002a:
            java.lang.String r1 = ",|\\(|\\)"
            java.lang.String[] r1 = r12.split(r1)
            if (r1 != 0) goto L_0x0033
            return r0
        L_0x0033:
            int r3 = r1.length
            r4 = 2
            r5 = 0
            if (r3 < r4) goto L_0x004a
            r3 = r1[r0]     // Catch:{ Exception -> 0x0047 }
            double r7 = java.lang.Double.parseDouble(r3)     // Catch:{ Exception -> 0x0047 }
            r1 = r1[r2]     // Catch:{ Exception -> 0x0048 }
            double r9 = java.lang.Double.parseDouble(r1)     // Catch:{ Exception -> 0x0048 }
            r1 = 2
            goto L_0x004d
        L_0x0047:
            r7 = r5
        L_0x0048:
            r9 = r5
            goto L_0x004c
        L_0x004a:
            r7 = r5
            r9 = r7
        L_0x004c:
            r1 = 1
        L_0x004d:
            r3 = 0
            if (r1 != r2) goto L_0x0092
            java.lang.String r1 = " "
            java.lang.String[] r12 = r12.split(r1)     // Catch:{ Exception -> 0x007d }
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x007d }
            r1.<init>()     // Catch:{ Exception -> 0x007d }
            r4 = 0
        L_0x005c:
            int r5 = r12.length     // Catch:{ Exception -> 0x007d }
            if (r4 >= r5) goto L_0x0071
            r5 = r12[r4]     // Catch:{ Exception -> 0x007d }
            java.lang.String r5 = r5.trim()     // Catch:{ Exception -> 0x007d }
            int r6 = r5.length()     // Catch:{ Exception -> 0x007d }
            if (r6 <= 0) goto L_0x006e
            r1.append(r5)     // Catch:{ Exception -> 0x007d }
        L_0x006e:
            int r4 = r4 + 1
            goto L_0x005c
        L_0x0071:
            java.lang.String r12 = r1.toString()     // Catch:{ Exception -> 0x007d }
            java.lang.String r1 = "utf-8"
            java.lang.String r12 = java.net.URLDecoder.decode(r12, r1)     // Catch:{ Exception -> 0x007d }
            goto L_0x0082
        L_0x007d:
            r12 = move-exception
            defpackage.kf.a(r12)
            r12 = r3
        L_0x0082:
            if (r12 == 0) goto L_0x00ca
            com.autonavi.minimap.intent.BaseIntent$c r0 = new com.autonavi.minimap.intent.BaseIntent$c
            r0.<init>()
            r11.k = r0
            com.autonavi.minimap.intent.BaseIntent$c r0 = r11.k
            r0.a = r12
            r11.f = r2
            return r2
        L_0x0092:
            if (r1 != r4) goto L_0x00ca
            int r12 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r12 <= 0) goto L_0x00ca
            int r12 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r12 <= 0) goto L_0x00ca
            android.graphics.Point r12 = defpackage.cfg.a(r7, r9)
            int r0 = r12.x
            int r12 = r12.y
            com.autonavi.common.model.GeoPoint r12 = defpackage.cff.a(r0, r12)
            dmf r0 = new dmf
            r0.<init>()
            r11.l = r0
            dmf r0 = r11.l
            r0.a = r12
            dmf r12 = r11.l
            android.app.Activity r0 = r11.a
            android.content.Context r0 = r0.getApplicationContext()
            android.content.res.Resources r0 = r0.getResources()
            int r1 = com.autonavi.minimap.R.string.wait_for_search
            java.lang.String r0 = r0.getString(r1)
            r12.b = r0
            r11.f = r2
            return r2
        L_0x00ca:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dlo.d(java.lang.String):boolean");
    }
}
