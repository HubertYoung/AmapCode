package defpackage;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.model.POI;
import com.autonavi.map.db.SavePointDao;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.sync.beans.GirfFavoritePoint;
import com.autonavi.sync.beans.JsonDataWithId;
import com.autonavi.sync.beans.JsonDatasWithType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cpf reason: default package */
/* compiled from: SavePointController */
public final class cpf implements cop {
    private static final Hashtable<String, cpf> b = new Hashtable<>();
    public String a;

    private cpf(String str) {
        this.a = str;
    }

    public static cpf b(String str) {
        cpf cpf;
        if (str == null) {
            str = "";
        }
        synchronized (b) {
            try {
                cpf = b.get(str);
                if (cpf == null) {
                    cpf = new cpf(str);
                    b.put(str, cpf);
                }
            }
        }
        return cpf;
    }

    public final int a() {
        if (TextUtils.isEmpty(this.a)) {
            return 0;
        }
        int a2 = bim.aa().a((String) "101");
        if (bim.aa().y().size() > a2) {
            a2 = bim.aa().y().size();
        }
        return a2;
    }

    public final FavoritePOI b() {
        if (!TextUtils.isEmpty(this.a)) {
            ArrayList arrayList = new ArrayList();
            JsonDatasWithType h = bim.aa().h((String) "101");
            if (h != null) {
                List<JsonDataWithId> list = h.jsonDataWithId;
                if (h != null && list.size() > 0) {
                    JsonDataWithId jsonDataWithId = list.get(0);
                    arrayList.add(bsr.a(jsonDataWithId.data, jsonDataWithId.id, this.a));
                }
            }
            if (arrayList.size() > 0) {
                bth bth = (bth) arrayList.get(0);
                FavoritePOI favoritePOI = (FavoritePOI) bth.a().as(FavoritePOI.class);
                favoritePOI.setUserId(bth.b);
                favoritePOI.setCreateTime(bth.f);
                favoritePOI.setPoiJson(bth.c);
                return favoritePOI;
            }
        }
        return null;
    }

    public final FavoritePOI c() {
        List<GirfFavoritePoint> o = bim.aa().o();
        new bth();
        if (o == null || o.size() <= 0) {
            return null;
        }
        String str = o.get(0).item_id;
        bth a2 = bsr.a(bim.aa().b((String) "101", str), str, g());
        if (a2 == null) {
            return null;
        }
        FavoritePOI favoritePOI = (FavoritePOI) a2.a().as(FavoritePOI.class);
        favoritePOI.setUserId(a2.b);
        favoritePOI.setCreateTime(a2.f);
        favoritePOI.setPoiJson(a2.c);
        return favoritePOI;
    }

    public final FavoritePOI d() {
        List<GirfFavoritePoint> p = bim.aa().p();
        new bth();
        if (p == null || p.size() <= 0) {
            return null;
        }
        String str = p.get(0).item_id;
        bth a2 = bsr.a(bim.aa().b((String) "101", str), str, g());
        if (a2 == null) {
            return null;
        }
        FavoritePOI favoritePOI = (FavoritePOI) a2.a().as(FavoritePOI.class);
        favoritePOI.setUserId(a2.b);
        favoritePOI.setCreateTime(a2.f);
        favoritePOI.setPoiJson(a2.c);
        return favoritePOI;
    }

    public final void b(POI poi) {
        cru.a(poi);
        bth bth = new bth();
        cpm.b();
        String b2 = cpm.b(poi);
        bth.a(poi);
        bth.b = this.a;
        bth.a = b2;
        bth.f = Long.valueOf(System.currentTimeMillis());
        bim.aa().a((String) "101", bth.a, bsr.a(bth, b2).toString(), 1);
    }

    public final boolean c(POI poi) {
        if (poi == null) {
            return false;
        }
        if (AMapAppGlobal.getApplication().getString(R.string.my_location).equals(poi.getName())) {
            poi = poi.clone();
            String addr = poi.getAddr();
            if (TextUtils.isEmpty(addr)) {
                addr = AMapAppGlobal.getApplication().getString(R.string.map_point);
            }
            poi.setName(addr);
        }
        cpm.b();
        if (!TextUtils.isEmpty(bim.aa().b((String) "101", cpm.b(poi)))) {
            return true;
        }
        String id = poi.getId();
        if (!TextUtils.isEmpty(id)) {
            List<JsonDataWithId> g = bim.aa().g(id);
            if (g == null || g.size() <= 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final void e(POI poi) {
        if (poi != null) {
            String string = AMapAppGlobal.getApplication().getString(R.string.company);
            new ArrayList();
            List<GirfFavoritePoint> p = bim.aa().p();
            boolean z = p == null || p.size() == 0;
            if (p != null && p.size() > 0) {
                for (int i = 0; i < p.size(); i++) {
                    bim.aa().b("101", p.get(i).item_id, 1);
                }
            }
            if (a(this.a, string) == null) {
                FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
                favoritePOI.setCommonName(string);
                b((POI) favoritePOI);
                ank.a(djk.class);
            }
            a(z);
        }
    }

    public static void a(boolean z) {
        if (z) {
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.fav_company_add_toast));
        } else {
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.fav_company_update_toast));
        }
    }

    public final void f(POI poi) {
        if (poi != null) {
            String string = AMapAppGlobal.getApplication().getString(R.string.home);
            new ArrayList();
            List<GirfFavoritePoint> o = bim.aa().o();
            boolean z = o == null || o.size() == 0;
            if (o != null && o.size() > 0) {
                for (int i = 0; i < o.size(); i++) {
                    bim.aa().b("101", o.get(i).item_id, 1);
                }
            }
            if (a(this.a, string) == null) {
                FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
                favoritePOI.setCommonName(string);
                b((POI) favoritePOI);
                ank.a(djk.class);
            }
            a(z);
        }
    }

    public final List<FavoritePOI> e() {
        ArrayList arrayList = new ArrayList();
        List<bth> c = c(this.a);
        Collections.sort(c, bth.i);
        for (bth next : c) {
            POI a2 = next.a();
            if (a2 != null) {
                FavoritePOI favoritePOI = (FavoritePOI) a2.as(FavoritePOI.class);
                favoritePOI.setSaved(true);
                favoritePOI.setUserId(next.b);
                favoritePOI.setCreateTime(next.f);
                favoritePOI.setPoiJson(next.c);
                arrayList.add(favoritePOI);
            }
        }
        return arrayList;
    }

    public final List<FavoritePOI> f() {
        String str = this.a;
        ArrayList arrayList = new ArrayList();
        JsonDatasWithType h = bim.aa().h((String) "101");
        if (!(h == null || h.jsonDataWithId == null || h.jsonDataWithId.size() <= 0)) {
            for (int i = 0; i < h.jsonDataWithId.size(); i++) {
                JsonDataWithId jsonDataWithId = h.jsonDataWithId.get(i);
                bth a2 = bsr.a(jsonDataWithId.data, jsonDataWithId.id, str);
                if (TextUtils.isEmpty(a2.d)) {
                    arrayList.add(a2);
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            bth bth = (bth) arrayList.get(i2);
            FavoritePOI favoritePOI = (FavoritePOI) bth.a().as(FavoritePOI.class);
            favoritePOI.setUserId(bth.b);
            favoritePOI.setCreateTime(bth.f);
            favoritePOI.setPoiJson(bth.c);
            favoritePOI.setCommonName(bth.d);
            arrayList2.add(favoritePOI);
        }
        return arrayList2;
    }

    public final void a(POI poi, POI poi2) {
        POI b2 = b(poi, poi2);
        if (poi != null && b2 != null) {
            FavoritePOI d = d(poi);
            if (d != null && TextUtils.isEmpty(d.getNewType())) {
                FavoritePOI favoritePOI = (FavoritePOI) b2.as(FavoritePOI.class);
                String newType = favoritePOI.getNewType();
                if (TextUtils.isEmpty(newType)) {
                    newType = favoritePOI.getType();
                }
                if (!TextUtils.isEmpty(newType)) {
                    d.setNewType(newType);
                }
            }
        }
    }

    private static POI b(POI poi, POI poi2) {
        String name = poi2.getName();
        cpm.b();
        String b2 = cpm.b(poi);
        bth a2 = bsr.a(bim.aa().b((String) "101", b2), b2, g());
        if (a2 != null) {
            JSONObject jSONObject = null;
            try {
                jSONObject = new JSONObject(a2.c);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String e2 = agd.e(jSONObject, GirfFavoritePoint.JSON_FIELD_POI_COMMON_NAME);
            String e3 = agd.e(jSONObject, GirfFavoritePoint.JSON_FIELD_POI_CUSTOM_NAME);
            if (!TextUtils.isEmpty(e2)) {
                name = e2;
            }
            if (!TextUtils.isEmpty(e3)) {
                name = e3;
            }
        }
        poi2.getPoiExtra().put("titleName", name);
        return poi2;
    }

    private static String h(POI poi) {
        cpm.b();
        return cpm.b(poi);
    }

    public static bth a(POI poi, String str) {
        bth bth = null;
        if (poi == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(poi.getId())) {
            return null;
        }
        i(poi);
        String b2 = bim.aa().b((String) "101", (String) null);
        if (b2 != null && !"".equals(b2)) {
            bth = bsr.a(b2, null, str);
        }
        return bth;
    }

    private static String i(POI poi) {
        cpm.b();
        cpm.b(poi);
        return null;
    }

    public static bth a(String str, String str2) {
        List arrayList = new ArrayList();
        if (str2.equals("家")) {
            arrayList = bim.aa().o();
        } else if (str2.equals("公司")) {
            arrayList = bim.aa().p();
        }
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        String str3 = ((GirfFavoritePoint) arrayList.get(0)).item_id;
        return bsr.a(bim.aa().a((String) "101", str3).toString(), str3, str);
    }

    private void e(List<bth> list) {
        if (list != null && list.size() > 0) {
            SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences("save_flag_sp_file", 0);
            if (!sharedPreferences.getBoolean(h(), false)) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (bth next : list) {
                    if (!(next == null || next.a() == null)) {
                        FavoritePOI favoritePOI = (FavoritePOI) next.a().as(FavoritePOI.class);
                        if (AMapAppGlobal.getApplication().getString(R.string.my_location).equals(favoritePOI.getName())) {
                            arrayList.add(next);
                            POI clone = favoritePOI.clone();
                            if (clone != null) {
                                String addr = clone.getAddr();
                                if (TextUtils.isEmpty(addr)) {
                                    addr = AMapAppGlobal.getApplication().getString(R.string.map_point);
                                }
                                clone.setName(addr);
                                bth bth = new bth();
                                bth.a(clone);
                                Long valueOf = Long.valueOf(0);
                                if (next.f != null) {
                                    valueOf = next.f;
                                }
                                bth.f = valueOf;
                                bth.b = this.a;
                                bth.d = favoritePOI.getCommonName();
                                bth.e = favoritePOI.getId();
                                bth.a = h(clone);
                                arrayList2.add(bth);
                            }
                        }
                    }
                }
                if (arrayList.size() > 0 && arrayList2.size() > 0) {
                    f((List<bth>) arrayList);
                    b((List<bth>) arrayList2);
                }
                Editor putBoolean = sharedPreferences.edit().putBoolean(h(), true);
                if (putBoolean != null) {
                    if (VERSION.SDK_INT >= 9) {
                        putBoolean.apply();
                        return;
                    }
                    putBoolean.commit();
                }
            }
        }
    }

    private String h() {
        StringBuilder sb = new StringBuilder("clean_my_location_key_");
        sb.append(this.a);
        return sb.toString();
    }

    private static void f(List<bth> list) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                bim.aa().b("101", list.get(i).a, 1);
            }
        }
    }

    public static void b(List<bth> list) {
        if (list.size() > 0) {
            c(list);
        }
    }

    public static List<bth> c(String str) {
        ArrayList arrayList = new ArrayList();
        JsonDatasWithType h = bim.aa().h((String) "101");
        if (!(h == null || h.jsonDataWithId == null || h.jsonDataWithId.size() <= 0)) {
            for (int i = 0; i < h.jsonDataWithId.size(); i++) {
                JsonDataWithId jsonDataWithId = h.jsonDataWithId.get(i);
                arrayList.add(bsr.a(jsonDataWithId.data, jsonDataWithId.id, str));
            }
        }
        return arrayList;
    }

    public static void c(List<bth> list) {
        if (list != null) {
            for (bth next : list) {
                bim.aa().a((String) "101", next.a, bsr.a(next, next.a).toString(), 1);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x016e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<defpackage.bth> a(java.lang.String r13) {
        /*
            r12 = this;
            com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer r0 = com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer.c()
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r2 = "sync_time_file"
            r3 = 0
            android.content.SharedPreferences r1 = r1.getSharedPreferences(r2, r3)
            java.lang.String r2 = "transfer_home_and_company_key"
            boolean r1 = r1.getBoolean(r2, r3)
            r2 = 1
            if (r1 != 0) goto L_0x00bb
            java.lang.Class<com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer$ICustomAddressStorage> r1 = com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer.ICustomAddressStorage.class
            com.autonavi.common.KeyValueStorage r1 = defpackage.bic.a(r1)
            com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer$ICustomAddressStorage r1 = (com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer.ICustomAddressStorage) r1
            com.autonavi.common.model.POI r4 = r1.getCompanyPoi()
            if (r4 != 0) goto L_0x0033
            java.lang.String r4 = "GoCompany"
            java.lang.String r5 = "gocompany"
            com.autonavi.common.model.POI r4 = com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer.a(r4, r5)
            if (r4 == 0) goto L_0x0033
            r1.setCompanyPoi(r4)
        L_0x0033:
            if (r4 == 0) goto L_0x005b
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r1 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r1 = r4.as(r1)
            com.amap.bundle.datamodel.FavoritePOI r1 = (com.amap.bundle.datamodel.FavoritePOI) r1
            android.app.Application r4 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r5 = com.autonavi.minimap.R.string.company
            java.lang.String r4 = r4.getString(r5)
            r1.setCommonName(r4)
            bth r4 = new bth
            r4.<init>()
            r4.a(r1)
            java.lang.String r0 = r0.b
            cpf r0 = b(r0)
            r0.b(r1)
        L_0x005b:
            java.lang.Class<com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer$ICustomAddressStorage> r0 = com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer.ICustomAddressStorage.class
            com.autonavi.common.KeyValueStorage r0 = defpackage.bic.a(r0)
            com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer$ICustomAddressStorage r0 = (com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer.ICustomAddressStorage) r0
            com.autonavi.common.model.POI r1 = r0.getHomePoi()
            if (r1 != 0) goto L_0x0076
            java.lang.String r1 = "GoHome"
            java.lang.String r4 = "gohome"
            com.autonavi.common.model.POI r1 = com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer.a(r1, r4)
            if (r1 == 0) goto L_0x0076
            r0.setHomePoi(r1)
        L_0x0076:
            if (r1 == 0) goto L_0x00a4
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r0 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r0 = r1.as(r0)
            com.amap.bundle.datamodel.FavoritePOI r0 = (com.amap.bundle.datamodel.FavoritePOI) r0
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r4 = com.autonavi.minimap.R.string.home
            java.lang.String r1 = r1.getString(r4)
            r0.setCommonName(r1)
            bth r1 = new bth
            r1.<init>()
            r1.a(r0)
            cpm r1 = defpackage.cpm.b()
            java.lang.String r1 = r1.a()
            cpf r1 = b(r1)
            r1.b(r0)
        L_0x00a4:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "sync_time_file"
            android.content.SharedPreferences r0 = r0.getSharedPreferences(r1, r3)
            android.content.SharedPreferences$Editor r0 = r0.edit()
            java.lang.String r1 = "transfer_home_and_company_key"
            android.content.SharedPreferences$Editor r0 = r0.putBoolean(r1, r2)
            r0.apply()
        L_0x00bb:
            com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer.c()
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "sync_time_file"
            android.content.SharedPreferences r0 = r0.getSharedPreferences(r1, r3)
            java.lang.String r1 = "transfer_other_common_point_key"
            boolean r0 = r0.getBoolean(r1, r3)
            if (r0 != 0) goto L_0x01b7
            cpm r0 = defpackage.cpm.b()
            java.lang.String r0 = r0.a()
            cpf r0 = b(r0)
            com.autonavi.amap.app.AMapAppGlobal.getApplication()
            xt r1 = defpackage.xv.b()
            java.lang.Class<com.autonavi.map.db.SavePointDao> r4 = com.autonavi.map.db.SavePointDao.class
            de.greenrobot.dao.AbstractDao r1 = r1.a(r4)
            com.autonavi.map.db.SavePointDao r1 = (com.autonavi.map.db.SavePointDao) r1
            de.greenrobot.dao.query.QueryBuilder r1 = r1.queryBuilder()
            de.greenrobot.dao.Property r4 = com.autonavi.map.db.SavePointDao.Properties.d
            de.greenrobot.dao.query.WhereCondition r4 = r4.isNotNull()
            de.greenrobot.dao.Property r5 = com.autonavi.map.db.SavePointDao.Properties.d
            java.lang.String r6 = ""
            de.greenrobot.dao.query.WhereCondition r5 = r5.notEq(r6)
            de.greenrobot.dao.query.WhereCondition[] r6 = new de.greenrobot.dao.query.WhereCondition[r3]
            de.greenrobot.dao.query.WhereCondition r4 = r1.and(r4, r5, r6)
            de.greenrobot.dao.query.WhereCondition[] r5 = new de.greenrobot.dao.query.WhereCondition[r3]
            de.greenrobot.dao.query.QueryBuilder r1 = r1.where(r4, r5)
            java.util.List r1 = r1.list()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            if (r1 == 0) goto L_0x01a0
            int r5 = r1.size()
            if (r5 <= 0) goto L_0x01a0
            java.util.Iterator r1 = r1.iterator()
        L_0x011e:
            boolean r5 = r1.hasNext()
            if (r5 == 0) goto L_0x0182
            java.lang.Object r5 = r1.next()
            bth r5 = (defpackage.bth) r5
            if (r5 != 0) goto L_0x012e
        L_0x012c:
            r6 = 0
            goto L_0x0149
        L_0x012e:
            java.lang.String r6 = r5.d
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 == 0) goto L_0x0137
            goto L_0x012c
        L_0x0137:
            java.lang.String r7 = defpackage.crt.c
            boolean r7 = android.text.TextUtils.equals(r6, r7)
            if (r7 != 0) goto L_0x012c
            java.lang.String r7 = defpackage.crt.b
            boolean r6 = android.text.TextUtils.equals(r6, r7)
            if (r6 == 0) goto L_0x0148
            goto L_0x012c
        L_0x0148:
            r6 = 1
        L_0x0149:
            if (r6 == 0) goto L_0x011e
            boolean r6 = defpackage.cru.b(r5)
            if (r6 != 0) goto L_0x011e
            com.autonavi.common.model.POI r6 = r5.a()
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r7 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r6 = r6.as(r7)
            com.amap.bundle.datamodel.FavoritePOI r6 = (com.amap.bundle.datamodel.FavoritePOI) r6
            java.text.DecimalFormat r7 = new java.text.DecimalFormat
            java.lang.String r8 = "#.######"
            r7.<init>(r8)
            r8 = 0
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            java.lang.Long r9 = r5.f
            if (r9 == 0) goto L_0x0170
            java.lang.Long r8 = r5.f
        L_0x0170:
            long r8 = r8.longValue()
            r10 = 1000(0x3e8, double:4.94E-321)
            long r8 = r8 / r10
            java.lang.String r7 = r7.format(r8)
            r6.setTopTime(r7)
            r4.add(r5)
            goto L_0x011e
        L_0x0182:
            int r1 = r4.size()
            if (r1 <= 0) goto L_0x01a0
            java.util.Iterator r1 = r4.iterator()
        L_0x018c:
            boolean r4 = r1.hasNext()
            if (r4 == 0) goto L_0x01a0
            java.lang.Object r4 = r1.next()
            bth r4 = (defpackage.bth) r4
            com.autonavi.common.model.POI r4 = r4.a()
            r0.g(r4)
            goto L_0x018c
        L_0x01a0:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "sync_time_file"
            android.content.SharedPreferences r0 = r0.getSharedPreferences(r1, r3)
            android.content.SharedPreferences$Editor r0 = r0.edit()
            java.lang.String r1 = "transfer_other_common_point_key"
            android.content.SharedPreferences$Editor r0 = r0.putBoolean(r1, r2)
            r0.apply()
        L_0x01b7:
            xt r0 = defpackage.xv.b()
            java.lang.Class<com.autonavi.map.db.SavePointDao> r1 = com.autonavi.map.db.SavePointDao.class
            de.greenrobot.dao.AbstractDao r0 = r0.a(r1)
            com.autonavi.map.db.SavePointDao r0 = (com.autonavi.map.db.SavePointDao) r0
            de.greenrobot.dao.query.QueryBuilder r0 = r0.queryBuilder()
            de.greenrobot.dao.Property r1 = com.autonavi.map.db.SavePointDao.Properties.b
            de.greenrobot.dao.query.WhereCondition r13 = r1.eq(r13)
            de.greenrobot.dao.query.WhereCondition[] r1 = new de.greenrobot.dao.query.WhereCondition[r3]
            de.greenrobot.dao.query.QueryBuilder r13 = r0.where(r13, r1)
            de.greenrobot.dao.Property[] r0 = new de.greenrobot.dao.Property[r2]
            de.greenrobot.dao.Property r1 = com.autonavi.map.db.SavePointDao.Properties.f
            r0[r3] = r1
            de.greenrobot.dao.query.QueryBuilder r13 = r13.orderDesc(r0)
            java.util.List r13 = r13.list()
            r12.e(r13)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cpf.a(java.lang.String):java.util.List");
    }

    public final void a(List<bth> list) {
        if (list != null && list.size() > 0) {
            for (bth next : list) {
                if (!(next == null || next.a() == null)) {
                    next.b = this.a;
                    String str = next.a;
                    if (TextUtils.isEmpty(str)) {
                        str = h(next.a());
                    }
                    next.a = str;
                }
            }
            SavePointDao savePointDao = (SavePointDao) xv.b().a(SavePointDao.class);
            if (list != null) {
                savePointDao.deleteInTx((Iterable<T>) list);
            }
        }
    }

    public static void d(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            bim.aa().b("101", list.get(i), 1);
        }
    }

    private static String g() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.a;
    }

    public final void a(POI poi) {
        cpm.b();
        bim.aa().b("101", cpm.b(poi), 1);
        if (poi != null && poi.getId() != null) {
            String id = poi.getId();
            if (id.length() > 0) {
                List<JsonDataWithId> g = bim.aa().g(id);
                if (g != null && g.size() > 0) {
                    for (int i = 0; i < g.size(); i++) {
                        String str = g.get(i).id;
                        if (str != null && str.length() > 0) {
                            bim.aa().b("101", str, 1);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x007b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void g(com.autonavi.common.model.POI r13) {
        /*
            r12 = this;
            if (r13 == 0) goto L_0x0148
            defpackage.cpm.b()
            java.lang.String r0 = defpackage.cpm.b(r13)
            bim r1 = defpackage.bim.aa()
            java.lang.String r2 = "101"
            java.lang.String r1 = r1.b(r2, r0)
            java.lang.String r2 = g()
            bth r1 = defpackage.bsr.a(r1, r0, r2)
            r2 = 0
            if (r1 == 0) goto L_0x0032
            java.lang.String r3 = r1.c     // Catch:{ JSONException -> 0x002e }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x002e }
            if (r3 != 0) goto L_0x0032
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x002e }
            java.lang.String r4 = r1.c     // Catch:{ JSONException -> 0x002e }
            r3.<init>(r4)     // Catch:{ JSONException -> 0x002e }
            goto L_0x0033
        L_0x002e:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0032:
            r3 = r2
        L_0x0033:
            java.lang.String r4 = "classification"
            java.lang.String r4 = defpackage.agd.e(r3, r4)
            java.lang.String r5 = "common_name"
            java.lang.String r5 = defpackage.agd.e(r3, r5)
            java.lang.String r6 = "custom_name"
            java.lang.String r6 = defpackage.agd.e(r3, r6)
            java.lang.String r7 = "tag"
            java.lang.String r7 = defpackage.agd.e(r3, r7)
            java.lang.String r8 = "top_time"
            java.lang.String r3 = defpackage.agd.e(r3, r8)
            java.util.HashMap r8 = r13.getPoiExtra()
            java.lang.String r9 = "FavoritePOI.TopTime"
            boolean r8 = r8.containsKey(r9)
            if (r8 == 0) goto L_0x0071
            java.util.HashMap r8 = r13.getPoiExtra()
            java.lang.String r9 = "FavoritePOI.TopTime"
            java.lang.Object r8 = r8.get(r9)
            if (r8 != 0) goto L_0x0071
            boolean r8 = android.text.TextUtils.isEmpty(r3)
            if (r8 != 0) goto L_0x0071
            java.lang.String r3 = ""
        L_0x0071:
            if (r1 != 0) goto L_0x0079
            java.lang.String r1 = r12.a
            bth r1 = a(r13, r1)
        L_0x0079:
            if (r1 != 0) goto L_0x0095
            bth r1 = new bth
            r1.<init>()
            java.lang.String r8 = r12.a
            r1.b = r8
            r1.a = r0
            long r8 = java.lang.System.currentTimeMillis()
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r1.f = r8
            java.lang.String r8 = "Update an unExist save point and will change the order of save points.."
            com.amap.bundle.blutils.log.DebugLog.warn(r8)
        L_0x0095:
            r1.a(r13)
            org.json.JSONObject r13 = defpackage.bsr.a(r1, r0)
            java.lang.String r13 = r13.toString()
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00a6 }
            r0.<init>(r13)     // Catch:{ JSONException -> 0x00a6 }
            goto L_0x00ab
        L_0x00a6:
            r0 = move-exception
            r0.printStackTrace()
            r0 = r2
        L_0x00ab:
            java.lang.String r2 = "classification"
            java.lang.String r2 = defpackage.agd.e(r0, r2)
            java.lang.String r8 = "tag"
            java.lang.String r8 = defpackage.agd.e(r0, r8)
            java.lang.String r9 = "common_name"
            java.lang.String r9 = defpackage.agd.e(r0, r9)
            java.lang.String r10 = "custom_name"
            java.lang.String r10 = defpackage.agd.e(r0, r10)
            java.lang.String r11 = "top_time"
            java.lang.String r11 = defpackage.agd.e(r0, r11)
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x00e0
            boolean r2 = android.text.TextUtils.isEmpty(r4)
            if (r2 != 0) goto L_0x00e0
            java.lang.String r13 = "classification"
            java.lang.String r2 = ""
            defpackage.agd.a(r0, r13, r4, r2)
            java.lang.String r13 = r0.toString()
        L_0x00e0:
            boolean r2 = android.text.TextUtils.isEmpty(r8)
            if (r2 == 0) goto L_0x00f7
            boolean r2 = android.text.TextUtils.isEmpty(r7)
            if (r2 != 0) goto L_0x00f7
            java.lang.String r13 = "tag"
            java.lang.String r2 = ""
            defpackage.agd.a(r0, r13, r7, r2)
            java.lang.String r13 = r0.toString()
        L_0x00f7:
            boolean r2 = android.text.TextUtils.isEmpty(r9)
            if (r2 == 0) goto L_0x010e
            boolean r2 = android.text.TextUtils.isEmpty(r5)
            if (r2 != 0) goto L_0x010e
            java.lang.String r13 = "common_name"
            java.lang.String r2 = ""
            defpackage.agd.a(r0, r13, r5, r2)
            java.lang.String r13 = r0.toString()
        L_0x010e:
            boolean r2 = android.text.TextUtils.isEmpty(r10)
            if (r2 == 0) goto L_0x0125
            boolean r2 = android.text.TextUtils.isEmpty(r6)
            if (r2 != 0) goto L_0x0125
            java.lang.String r13 = "custom_name"
            java.lang.String r2 = ""
            defpackage.agd.a(r0, r13, r6, r2)
            java.lang.String r13 = r0.toString()
        L_0x0125:
            boolean r2 = android.text.TextUtils.isEmpty(r11)
            if (r2 == 0) goto L_0x013c
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L_0x013c
            java.lang.String r13 = "top_time"
            java.lang.String r2 = ""
            defpackage.agd.a(r0, r13, r3, r2)
            java.lang.String r13 = r0.toString()
        L_0x013c:
            bim r0 = defpackage.bim.aa()
            java.lang.String r2 = "101"
            java.lang.String r1 = r1.a
            r3 = 1
            r0.b(r2, r1, r13, r3)
        L_0x0148:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cpf.g(com.autonavi.common.model.POI):void");
    }

    public final FavoritePOI d(POI poi) {
        cpm.b();
        String b2 = cpm.b(poi);
        bth bth = new bth();
        bth.a(poi);
        bth.b = this.a;
        bth.f = Long.valueOf(System.currentTimeMillis());
        String b3 = bim.aa().b((String) "101", b2);
        if (b3 == null || b3.length() <= 0) {
            String id = poi.getId();
            if (id != null && id.length() > 0) {
                List<JsonDataWithId> g = bim.aa().g(id);
                if (g != null && g.size() > 0) {
                    JsonDataWithId jsonDataWithId = g.get(0);
                    bth.a = jsonDataWithId.id;
                    bth.c = jsonDataWithId.data;
                }
            }
        } else {
            bth.a = b2;
            bth.c = b3;
        }
        if (TextUtils.isEmpty(bth.a) || TextUtils.isEmpty(bth.c)) {
            return null;
        }
        FavoritePOI favoritePOI = (FavoritePOI) bth.a().as(FavoritePOI.class);
        favoritePOI.setUserId(bth.b);
        favoritePOI.setCreateTime(bth.f);
        favoritePOI.setPoiJson(bth.c);
        return favoritePOI;
    }
}
