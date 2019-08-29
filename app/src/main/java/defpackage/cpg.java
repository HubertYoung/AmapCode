package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.db.SaveRouteDao;
import com.autonavi.map.db.SaveRouteDao.Properties;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cpg reason: default package */
/* compiled from: SaveRouteController */
public final class cpg implements coq {
    private static Hashtable<String, cpg> a = new Hashtable<>();
    private String b;
    private SaveRouteDao c = ((SaveRouteDao) xv.b().a(SaveRouteDao.class));

    private cpg(String str) {
        this.b = str;
    }

    public static cpg c(String str) {
        cpg cpg;
        if (str == null) {
            str = "";
        }
        synchronized (a) {
            try {
                cpg = a.get(str);
                if (cpg == null) {
                    cpg = new cpg(str);
                    a.put(str, cpg);
                }
            }
        }
        return cpg;
    }

    public final cor a(cos cos) {
        JSONObject jSONObject;
        bti c2 = c(cos);
        if (this.c != null) {
            this.c.insertOrReplace(c2);
        }
        String str = c2.a;
        switch (c2.c) {
            case 0:
                jSONObject = new JSONObject();
                cpl.a(jSONObject, c2, str);
                asy asy = (asy) a.a.a(asy.class);
                if (asy != null) {
                    asy.c().a(jSONObject, c2.d());
                    break;
                }
                break;
            case 1:
                jSONObject = new JSONObject();
                cpl.a(jSONObject, c2, str);
                Object d = c2.d();
                if (d != null && dia.class.isInstance(d)) {
                    dia dia = (dia) c2.d();
                    agd.a(jSONObject, (String) "strategy", dia.getPathStrategy());
                    agd.a(jSONObject, (String) "mPathlength", dia.getPathlength());
                    agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_COST_TIME, dia.getCostTime());
                    break;
                }
            case 2:
                jSONObject = new JSONObject();
                cpl.a(jSONObject, c2, str);
                atb atb = (atb) a.a.a(atb.class);
                if (atb != null) {
                    atb.e().a(c2.d(), jSONObject);
                    break;
                }
                break;
            case 3:
                jSONObject = new JSONObject();
                cpl.a(jSONObject, c2, str);
                break;
            default:
                jSONObject = null;
                break;
        }
        bim.aa().a(bti.a(c2.c), c2.a, jSONObject.toString(), 1);
        return c2;
    }

    public final void a(cor cor) {
        bti bti = (bti) cor;
        if (bti != null) {
            String str = bti.a;
            if (TextUtils.isEmpty(str)) {
                cpm.b();
                str = cpm.a(bti);
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(bti);
            a((List<bti>) arrayList);
            bim.aa().b(bti.a(bti.c), str, 1);
        }
    }

    public final void a(String str, int i) {
        bti d = d(str);
        if (d != null) {
            if (!(this.c == null || d == null)) {
                SaveRouteDao saveRouteDao = this.c;
                bti[] btiArr = new bti[1];
                d.b = this.b;
                String str2 = d.a;
                if (TextUtils.isEmpty(str2)) {
                    cpm.b();
                    str2 = cpm.a(d);
                }
                d.a = str2;
                btiArr[0] = d;
                saveRouteDao.deleteInTx((T[]) btiArr);
            }
            bim.aa().b(bti.a(i), str, 1);
        }
    }

    public final cor b(cos cos) {
        bti c2 = c(cos);
        if (!TextUtils.isEmpty(c2.a)) {
            bti f = f(c2.a);
            if (f != null) {
                return f;
            }
        }
        return null;
    }

    public final cor a(String str) {
        return d(str);
    }

    public final bti d(String str) {
        bti e = e(str);
        if (e == null || e.c == 0) {
            return f(str);
        }
        return e;
    }

    public final List<bti> b(String str) {
        if (this.c == null) {
            return null;
        }
        List<bti> list = this.c.queryBuilder().where(Properties.b.eq(str), Properties.s.eq(Integer.valueOf(0))).list();
        if (list != null && list.size() > 0) {
            bti[] btiArr = new bti[list.size()];
            list.toArray(btiArr);
            for (bti bti : btiArr) {
                if (bti.d() == null) {
                    list.remove(bti);
                }
            }
        }
        return list;
    }

    public final void a(bti bti) {
        if (bti != null && this.c != null) {
            bti.t = Integer.valueOf(1);
            this.c.update(bti);
        }
    }

    private bti c(cos cos) {
        bti bti = new bti();
        bti.d = cos.b;
        bti.c = cos.a;
        bti.e = cos.c;
        bti.f = cos.d;
        bti.g = cos.e;
        bti.h = cos.f;
        bti.i = cos.g;
        bti.j = cos.h;
        bti.k = cos.i;
        bti.l = cos.j;
        bti.u = cos.p;
        bti.a(cos.m);
        bti.b(cos.n);
        bti.a(cos.o);
        bti.p = cos.k;
        bti.a(cos.a, cos.l);
        bti.s = Long.valueOf(System.currentTimeMillis());
        bti.t = Integer.valueOf(1);
        bti.b = this.b;
        cpm.b();
        bti.a = cpm.a(bti);
        return bti;
    }

    private void a(List<bti> list) {
        if (list.size() > 0 && this.c != null) {
            for (bti next : list) {
                if (next != null) {
                    next.b = this.b;
                    String str = next.a;
                    if (TextUtils.isEmpty(str)) {
                        cpm.b();
                        str = cpm.a(next);
                    }
                    next.a = str;
                }
            }
            this.c.deleteInTx((Iterable<T>) list);
        }
    }

    private bti e(String str) {
        if (this.c == null) {
            return null;
        }
        List list = this.c.queryBuilder().where(Properties.b.eq(this.b), Properties.a.eq(str)).list();
        if (list != null && list.size() > 0) {
            bti[] btiArr = new bti[list.size()];
            list.toArray(btiArr);
            for (bti bti : btiArr) {
                if (bti.d() == null) {
                    list.remove(bti);
                }
            }
        }
        if (list == null || list.size() <= 0) {
            return null;
        }
        return (bti) list.get(0);
    }

    private static bti f(String str) {
        JSONObject jSONObject;
        JSONObject jSONObject2;
        JSONObject jSONObject3;
        JSONObject jSONObject4;
        String b2 = bim.aa().b((String) "104", str);
        if (!"".equals(b2)) {
            try {
                jSONObject4 = new JSONObject(b2);
            } catch (JSONException e) {
                e.printStackTrace();
                jSONObject4 = null;
            }
            a();
            return cpl.a(3, jSONObject4, str);
        }
        String b3 = bim.aa().b((String) "102", str);
        if (!"".equals(b3)) {
            try {
                jSONObject3 = new JSONObject(b3);
            } catch (JSONException e2) {
                e2.printStackTrace();
                jSONObject3 = null;
            }
            a();
            return cpl.a(1, jSONObject3, str);
        }
        String b4 = bim.aa().b((String) "103", str);
        if (!"".equals(b4)) {
            try {
                jSONObject2 = new JSONObject(b4);
            } catch (JSONException e3) {
                e3.printStackTrace();
                jSONObject2 = null;
            }
            a();
            return cpl.a(2, jSONObject2, str);
        }
        String b5 = bim.aa().b((String) "105", str);
        if ("".equals(b5)) {
            return null;
        }
        try {
            jSONObject = new JSONObject(b5);
        } catch (JSONException e4) {
            e4.printStackTrace();
            jSONObject = null;
        }
        a();
        return cpl.a(0, jSONObject, str);
    }

    private static String a() {
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
}
