package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.beehive.audio.Constants;
import com.alipay.mobile.beehive.cityselect.ui.SelectCityActivity;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.tinyappcommon.h5plugin.H5SensorPlugin;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.bundle.feedback.ajx.ModuleFeedBack;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: b reason: default package */
/* compiled from: DetailV2Convert */
public final class b {
    private static JSONObject a(bb bbVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bbVar.a)) {
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, bbVar.a);
        }
        if (!TextUtils.isEmpty(bbVar.b)) {
            jSONObject.put("name", bbVar.b);
        }
        if (!TextUtils.isEmpty(bbVar.c)) {
            jSONObject.put(DictionaryKeys.CTRLXY_X, bbVar.c);
        }
        if (!TextUtils.isEmpty(bbVar.d)) {
            jSONObject.put(DictionaryKeys.CTRLXY_Y, bbVar.d);
        }
        if (!TextUtils.isEmpty(bbVar.e)) {
            jSONObject.put("new_type", bbVar.e);
        }
        if (!TextUtils.isEmpty(bbVar.f)) {
            jSONObject.put("poi_business", bbVar.f);
        }
        if (!TextUtils.isEmpty(bbVar.g)) {
            jSONObject.put("pic", bbVar.g);
        }
        jSONObject.put("pic_type", bbVar.h);
        if (!TextUtils.isEmpty(bbVar.i)) {
            jSONObject.put("src_star", bbVar.i);
        }
        if (!TextUtils.isEmpty(bbVar.j)) {
            jSONObject.put("price_pre", bbVar.j);
        }
        if (!TextUtils.isEmpty(bbVar.k)) {
            jSONObject.put("price", bbVar.k);
        }
        if (!TextUtils.isEmpty(bbVar.l)) {
            jSONObject.put("price_end", bbVar.l);
        }
        jSONObject.put("distance", (double) bbVar.m);
        if (!TextUtils.isEmpty(bbVar.n)) {
            jSONObject.put("std_tag", bbVar.n);
        }
        if (!TextUtils.isEmpty(bbVar.o)) {
            jSONObject.put("poi_tag", bbVar.o);
        }
        jSONObject.put("uv", bbVar.p);
        if (bbVar.q != null && bbVar.q.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (t tVar : bbVar.q) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(tVar.a)) {
                    jSONObject2.put("type", tVar.a);
                }
                if (!TextUtils.isEmpty(tVar.b)) {
                    jSONObject2.put(H5Param.MENU_ICON, tVar.b);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("facilities", jSONArray);
        }
        if (bbVar.r != null && bbVar.r.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (j jVar : bbVar.r) {
                JSONObject jSONObject3 = new JSONObject();
                if (!TextUtils.isEmpty(jVar.a)) {
                    jSONObject3.put("type", jVar.a);
                }
                if (!TextUtils.isEmpty(jVar.b)) {
                    jSONObject3.put(H5Param.MENU_ICON, jVar.b);
                }
                jSONArray2.put(jSONObject3);
            }
            jSONObject.put("discount_flag", jSONArray2);
        }
        return jSONObject;
    }

    private static JSONObject a(ba baVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("recom_code", baVar.a);
        jSONObject.put("show_pos", baVar.b);
        if (!TextUtils.isEmpty(baVar.c)) {
            jSONObject.put("recom_theme", baVar.c);
        }
        if (!TextUtils.isEmpty(baVar.d)) {
            jSONObject.put("head_pic", baVar.d);
        }
        jSONObject.put("has_more", baVar.e);
        if (!TextUtils.isEmpty(baVar.f)) {
            jSONObject.put("toplist_template_id", baVar.f);
        }
        if (!TextUtils.isEmpty(baVar.g)) {
            jSONObject.put("recommend_pos_id", baVar.g);
        }
        if (baVar.h != null && baVar.h.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bb a : baVar.h) {
                jSONArray.put(a(a));
            }
            jSONObject.put("poi_list", jSONArray);
        }
        if (!TextUtils.isEmpty(baVar.i)) {
            jSONObject.put("back_args", baVar.i);
        }
        return jSONObject;
    }

    private static JSONObject a(C0041a aVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("pep_percent", aVar.a);
        if (!TextUtils.isEmpty(aVar.b)) {
            jSONObject.put("abtest_id", aVar.b);
        }
        if (!TextUtils.isEmpty(aVar.c)) {
            jSONObject.put("item_style", aVar.c);
        }
        jSONObject.put("need_new_landpage", aVar.d);
        if (aVar.e != null && aVar.e.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (ba a : aVar.e) {
                jSONArray.put(a(a));
            }
            jSONObject.put("data", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(bt btVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(btVar.a)) {
            jSONObject.put("tab_name", btVar.a);
        }
        if (!TextUtils.isEmpty(btVar.b)) {
            jSONObject.put("toplist_template_id", btVar.b);
        }
        if (btVar.c != null && btVar.c.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bs bsVar : btVar.c) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(bsVar.a)) {
                    jSONObject2.put("distance", bsVar.a);
                }
                if (!TextUtils.isEmpty(bsVar.b)) {
                    jSONObject2.put("poi_tag", bsVar.b);
                }
                if (!TextUtils.isEmpty(bsVar.c)) {
                    jSONObject2.put("uv", bsVar.c);
                }
                if (!TextUtils.isEmpty(bsVar.d)) {
                    jSONObject2.put("name", bsVar.d);
                }
                if (!TextUtils.isEmpty(bsVar.e)) {
                    jSONObject2.put("new_type", bsVar.e);
                }
                if (!TextUtils.isEmpty(bsVar.f)) {
                    jSONObject2.put("std_tag", bsVar.f);
                }
                if (!TextUtils.isEmpty(bsVar.g)) {
                    jSONObject2.put("src_star", bsVar.g);
                }
                if (!TextUtils.isEmpty(bsVar.h)) {
                    jSONObject2.put("pic", bsVar.h);
                }
                if (!TextUtils.isEmpty(bsVar.i)) {
                    jSONObject2.put("adm9_chn", bsVar.i);
                }
                if (!TextUtils.isEmpty(bsVar.j)) {
                    jSONObject2.put("poi_business", bsVar.j);
                }
                if (!TextUtils.isEmpty(bsVar.k)) {
                    jSONObject2.put("price", bsVar.k);
                }
                jSONObject2.put(DictionaryKeys.CTRLXY_X, (double) bsVar.l);
                jSONObject2.put(DictionaryKeys.CTRLXY_Y, (double) bsVar.m);
                if (!TextUtils.isEmpty(bsVar.n)) {
                    jSONObject2.put("bcs", bsVar.n);
                }
                if (!TextUtils.isEmpty(bsVar.o)) {
                    jSONObject2.put("rec_reason", bsVar.o);
                }
                jSONObject2.put("pic_type", bsVar.p);
                jSONObject2.put("has_book", bsVar.q);
                jSONObject2.put("has_discount", bsVar.r);
                jSONObject2.put("has_group", bsVar.s);
                if (!TextUtils.isEmpty(bsVar.t)) {
                    jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, bsVar.t);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("poi_list", jSONArray);
        }
        if (!TextUtils.isEmpty(btVar.d)) {
            jSONObject.put("toplist_id", btVar.d);
        }
        jSONObject.put("poi_num", btVar.e);
        if (!TextUtils.isEmpty(btVar.f)) {
            jSONObject.put("show_flag", btVar.f);
        }
        return jSONObject;
    }

    public static JSONObject a(g gVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(gVar.a)) {
            jSONObject.put("code", gVar.a);
        }
        if (!TextUtils.isEmpty(gVar.b)) {
            jSONObject.put("result", gVar.b);
        }
        if (!TextUtils.isEmpty(gVar.c)) {
            jSONObject.put("timestamp", gVar.c);
        }
        if (!TextUtils.isEmpty(gVar.d)) {
            jSONObject.put("version", gVar.d);
        }
        if (!TextUtils.isEmpty(gVar.e)) {
            jSONObject.put("message", gVar.e);
        }
        if (gVar.f != null) {
            jSONObject.put("ab_data", a(gVar.f));
        }
        jSONObject.put("recom_code", gVar.g);
        if (!TextUtils.isEmpty(gVar.h)) {
            jSONObject.put("recom_theme", gVar.h);
        }
        if (gVar.i != null && gVar.i.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bb a : gVar.i) {
                jSONArray.put(a(a));
            }
            jSONObject.put("poi_list", jSONArray);
        }
        if (!TextUtils.isEmpty(gVar.j)) {
            jSONObject.put("title", gVar.j);
        }
        if (!TextUtils.isEmpty(gVar.k)) {
            jSONObject.put("subtitle", gVar.k);
        }
        if (gVar.l != null) {
            x xVar = gVar.l;
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("pic_size", xVar.a);
            jSONObject2.put("pic_pos", xVar.b);
            if (!TextUtils.isEmpty(xVar.c)) {
                jSONObject2.put("pic_url", xVar.c);
            }
            jSONObject.put("head_pic", jSONObject2);
        }
        if (gVar.m != null && gVar.m.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (bt a2 : gVar.m) {
                jSONArray2.put(a(a2));
            }
            jSONObject.put("toplist_tab_list", jSONArray2);
        }
        if (!TextUtils.isEmpty(gVar.n)) {
            jSONObject.put(H5AppUtil.scene, gVar.n);
        }
        if (!TextUtils.isEmpty(gVar.o)) {
            jSONObject.put("toplist_id", gVar.o);
        }
        if (!TextUtils.isEmpty(gVar.p)) {
            jSONObject.put("back_args", gVar.p);
        }
        return jSONObject;
    }

    private static JSONObject a(l lVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(lVar.a)) {
            jSONObject.put("doc_id", lVar.a);
        }
        if (!TextUtils.isEmpty(lVar.b)) {
            jSONObject.put("doc_name", lVar.b);
        }
        if (!TextUtils.isEmpty(lVar.c)) {
            jSONObject.put("doc_title", lVar.c);
        }
        jSONObject.put("is_book", lVar.d);
        if (!TextUtils.isEmpty(lVar.e)) {
            jSONObject.put("bookurl", lVar.e);
        }
        if (!TextUtils.isEmpty(lVar.f)) {
            jSONObject.put("doc_intro", lVar.f);
        }
        if (!TextUtils.isEmpty(lVar.g)) {
            jSONObject.put("total_patient", lVar.g);
        }
        if (lVar.h != null && lVar.h.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (p pVar : lVar.h) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(pVar.a)) {
                    jSONObject2.put("title", pVar.a);
                }
                if (!TextUtils.isEmpty(pVar.b)) {
                    jSONObject2.put("url", pVar.b);
                }
                jSONObject2.put("iscover", pVar.c);
                if (!TextUtils.isEmpty(pVar.d)) {
                    jSONObject2.put("pic_id", pVar.d);
                }
                if (!TextUtils.isEmpty(pVar.e)) {
                    jSONObject2.put("fetch_type", pVar.e);
                }
                if (!TextUtils.isEmpty(pVar.f)) {
                    jSONObject2.put("src_type", pVar.f);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("pic_info", jSONArray);
        }
        if (!TextUtils.isEmpty(lVar.i)) {
            jSONObject.put("doc_speciality", lVar.i);
        }
        if (!TextUtils.isEmpty(lVar.j)) {
            jSONObject.put("doc_effect", lVar.j);
        }
        if (!TextUtils.isEmpty(lVar.k)) {
            jSONObject.put("doc_attitude", lVar.k);
        }
        jSONObject.put("total_vote", lVar.l);
        jSONObject.put("today_status", lVar.m);
        if (!TextUtils.isEmpty(lVar.n)) {
            jSONObject.put("depart_id", lVar.n);
        }
        if (!TextUtils.isEmpty(lVar.o)) {
            jSONObject.put("depart_name", lVar.o);
        }
        if (!TextUtils.isEmpty(lVar.p)) {
            jSONObject.put("child_id", lVar.p);
        }
        if (!TextUtils.isEmpty(lVar.q)) {
            jSONObject.put("child_name", lVar.q);
        }
        if (lVar.r != null && lVar.r.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (o oVar : lVar.r) {
                JSONObject jSONObject3 = new JSONObject();
                if (!TextUtils.isEmpty(oVar.a)) {
                    jSONObject3.put("regis_type", oVar.a);
                }
                jSONObject3.put("regis_fee", (double) oVar.b);
                if (!TextUtils.isEmpty(oVar.c)) {
                    jSONObject3.put("visit_date", oVar.c);
                }
                if (!TextUtils.isEmpty(oVar.d)) {
                    jSONObject3.put("visit_time", oVar.d);
                }
                if (!TextUtils.isEmpty(oVar.e)) {
                    jSONObject3.put("visit_addr", oVar.e);
                }
                jSONArray2.put(jSONObject3);
            }
            jSONObject.put("visit_info", jSONArray2);
        }
        if (lVar.s != null && lVar.s.length > 0) {
            JSONArray jSONArray3 = new JSONArray();
            for (m mVar : lVar.s) {
                JSONObject jSONObject4 = new JSONObject();
                if (!TextUtils.isEmpty(mVar.a)) {
                    jSONObject4.put("patient_name", mVar.a);
                }
                if (!TextUtils.isEmpty(mVar.b)) {
                    jSONObject4.put("patient_disease", mVar.b);
                }
                if (!TextUtils.isEmpty(mVar.c)) {
                    jSONObject4.put("treatway", mVar.c);
                }
                if (!TextUtils.isEmpty(mVar.d)) {
                    jSONObject4.put("review", mVar.d);
                }
                if (!TextUtils.isEmpty(mVar.e)) {
                    jSONObject4.put("type", mVar.e);
                }
                if (!TextUtils.isEmpty(mVar.f)) {
                    jSONObject4.put("effect", mVar.f);
                }
                if (!TextUtils.isEmpty(mVar.g)) {
                    jSONObject4.put("attitude", mVar.g);
                }
                if (!TextUtils.isEmpty(mVar.h)) {
                    jSONObject4.put("time", mVar.h);
                }
                if (!TextUtils.isEmpty(mVar.i)) {
                    jSONObject4.put("from", mVar.i);
                }
                jSONArray3.put(jSONObject4);
            }
            jSONObject.put("patient_review", jSONArray3);
        }
        return jSONObject;
    }

    public static JSONObject a(n nVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(nVar.a)) {
            jSONObject.put("result", nVar.a);
        }
        if (!TextUtils.isEmpty(nVar.b)) {
            jSONObject.put("code", nVar.b);
        }
        if (!TextUtils.isEmpty(nVar.c)) {
            jSONObject.put("message", nVar.c);
        }
        if (!TextUtils.isEmpty(nVar.d)) {
            jSONObject.put("timestamp", nVar.d);
        }
        if (!TextUtils.isEmpty(nVar.e)) {
            jSONObject.put("version", nVar.e);
        }
        jSONObject.put("page_total", nVar.f);
        if (nVar.g != null && nVar.g.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (l a : nVar.g) {
                jSONArray.put(a(a));
            }
            jSONObject.put("doctors", jSONArray);
        }
        return jSONObject;
    }

    public static JSONObject a(q qVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(qVar.a)) {
            jSONObject.put("result", qVar.a);
        }
        if (!TextUtils.isEmpty(qVar.b)) {
            jSONObject.put("code", qVar.b);
        }
        if (!TextUtils.isEmpty(qVar.c)) {
            jSONObject.put("message", qVar.c);
        }
        if (!TextUtils.isEmpty(qVar.d)) {
            jSONObject.put("timestamp", qVar.d);
        }
        if (!TextUtils.isEmpty(qVar.e)) {
            jSONObject.put("version", qVar.e);
        }
        if (!TextUtils.isEmpty(qVar.f)) {
            jSONObject.put(SelectCityActivity.SELECT_PROVINCE, qVar.f);
        }
        if (!TextUtils.isEmpty(qVar.g)) {
            jSONObject.put("select_genre", qVar.g);
        }
        if (!TextUtils.isEmpty(qVar.h)) {
            jSONObject.put("select_batch", qVar.h);
        }
        if (qVar.i != null) {
            jSONObject.put("scores", new JSONObject(qVar.i));
        }
        if (qVar.j != null && qVar.j.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (String put : qVar.j) {
                jSONArray.put(put);
            }
            jSONObject.put("province_list", jSONArray);
        }
        if (qVar.k != null && qVar.k.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (String put2 : qVar.k) {
                jSONArray2.put(put2);
            }
            jSONObject.put("genre_list", jSONArray2);
        }
        if (qVar.l != null && qVar.l.length > 0) {
            JSONArray jSONArray3 = new JSONArray();
            for (String put3 : qVar.l) {
                jSONArray3.put(put3);
            }
            jSONObject.put("batch_list", jSONArray3);
        }
        return jSONObject;
    }

    private static JSONObject a(v vVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NewHtcHomeBadger.COUNT, vVar.a);
        if (!TextUtils.isEmpty(vVar.b)) {
            jSONObject.put("alipay_url", vVar.b);
        }
        if (vVar.c != null && vVar.c.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (k kVar : vVar.c) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(kVar.a)) {
                    jSONObject2.put("title", kVar.a);
                }
                if (!TextUtils.isEmpty(kVar.b)) {
                    jSONObject2.put("type", kVar.b);
                }
                if (!TextUtils.isEmpty(kVar.c)) {
                    jSONObject2.put("desc", kVar.c);
                }
                if (!TextUtils.isEmpty(kVar.d)) {
                    jSONObject2.put("sold_number", kVar.d);
                }
                if (!TextUtils.isEmpty(kVar.e)) {
                    jSONObject2.put("price_ori", kVar.e);
                }
                if (!TextUtils.isEmpty(kVar.f)) {
                    jSONObject2.put("price", kVar.f);
                }
                if (!TextUtils.isEmpty(kVar.g)) {
                    jSONObject2.put("src_type", kVar.g);
                }
                if (!TextUtils.isEmpty(kVar.h)) {
                    jSONObject2.put("discount", kVar.h);
                }
                if (!TextUtils.isEmpty(kVar.i)) {
                    jSONObject2.put("src_name", kVar.i);
                }
                if (!TextUtils.isEmpty(kVar.j)) {
                    jSONObject2.put("url", kVar.j);
                }
                if (kVar.k != null) {
                    az azVar = kVar.k;
                    JSONObject jSONObject3 = new JSONObject();
                    if (!TextUtils.isEmpty(azVar.a)) {
                        jSONObject3.put("mergeid", azVar.a);
                    }
                    if (!TextUtils.isEmpty(azVar.b)) {
                        jSONObject3.put("groupid", azVar.b);
                    }
                    if (!TextUtils.isEmpty(azVar.c)) {
                        jSONObject3.put("src_type", azVar.c);
                    }
                    if (!TextUtils.isEmpty(azVar.d)) {
                        jSONObject3.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, azVar.d);
                    }
                    if (!TextUtils.isEmpty(azVar.e)) {
                        jSONObject3.put("discount_gd_id", azVar.e);
                    }
                    jSONObject2.put("query_fields", jSONObject3);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("discounts", jSONArray);
        }
        return jSONObject;
    }

    public static JSONObject a(u uVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(uVar.a)) {
            jSONObject.put("code", uVar.a);
        }
        if (!TextUtils.isEmpty(uVar.b)) {
            jSONObject.put("result", uVar.b);
        }
        if (!TextUtils.isEmpty(uVar.c)) {
            jSONObject.put("timestamp", uVar.c);
        }
        if (!TextUtils.isEmpty(uVar.d)) {
            jSONObject.put("version", uVar.d);
        }
        if (!TextUtils.isEmpty(uVar.e)) {
            jSONObject.put("message", uVar.e);
        }
        if (uVar.f != null) {
            jSONObject.put("data", a(uVar.f));
        }
        return jSONObject;
    }

    private static JSONObject a(y yVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(yVar.a)) {
            jSONObject.put("id", yVar.a);
        }
        if (!TextUtils.isEmpty(yVar.b)) {
            jSONObject.put("name", yVar.b);
        }
        if (!TextUtils.isEmpty(yVar.c)) {
            jSONObject.put("desc", yVar.c);
        }
        jSONObject.put("doctor_total", yVar.d);
        if (yVar.e != null && yVar.e.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (z zVar : yVar.e) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(zVar.a)) {
                    jSONObject2.put("child_id", zVar.a);
                }
                if (!TextUtils.isEmpty(zVar.b)) {
                    jSONObject2.put("child_name", zVar.b);
                }
                if (!TextUtils.isEmpty(zVar.c)) {
                    jSONObject2.put("child_desc", zVar.c);
                }
                jSONObject2.put("doctor_count", zVar.d);
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("child_department", jSONArray);
        }
        return jSONObject;
    }

    public static JSONObject a(aa aaVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(aaVar.a)) {
            jSONObject.put("result", aaVar.a);
        }
        if (!TextUtils.isEmpty(aaVar.b)) {
            jSONObject.put("code", aaVar.b);
        }
        if (!TextUtils.isEmpty(aaVar.c)) {
            jSONObject.put("message", aaVar.c);
        }
        if (!TextUtils.isEmpty(aaVar.d)) {
            jSONObject.put("timestamp", aaVar.d);
        }
        if (!TextUtils.isEmpty(aaVar.e)) {
            jSONObject.put("version", aaVar.e);
        }
        if (aaVar.f != null && aaVar.f.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (y a : aaVar.f) {
                jSONArray.put(a(a));
            }
            jSONObject.put("department", jSONArray);
        }
        return jSONObject;
    }

    public static JSONObject a(ac acVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (acVar.a != null) {
            jSONObject.put(Performance.KEY_LOG_HEADER, a(acVar.a));
        }
        if (!TextUtils.isEmpty(acVar.b)) {
            jSONObject.put("template_version", acVar.b);
        }
        if (acVar.c != null && acVar.c.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (String put : acVar.c) {
                jSONArray.put(put);
            }
            jSONObject.put("sort", jSONArray);
        }
        if (acVar.d != null && acVar.d.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (af a : acVar.d) {
                jSONArray2.put(a(a));
            }
            jSONObject.put("com_airport_service", jSONArray2);
        }
        if (acVar.e != null) {
            jSONObject.put("action_bar", a(acVar.e));
        }
        if (acVar.f != null) {
            jSONObject.put("detail_info", a(acVar.f));
        }
        if (acVar.g != null) {
            jSONObject.put("charging_motortype", a(acVar.g));
        }
        if (acVar.h != null) {
            jSONObject.put("supportbrand", a(acVar.h));
        }
        if (acVar.i != null) {
            jSONObject.put("serviceitem", a(acVar.i));
        }
        if (acVar.j != null) {
            jSONObject.put("opentime", a(acVar.j));
        }
        if (acVar.k != null) {
            jSONObject.put("business_scope", a(acVar.k));
        }
        if (acVar.l != null) {
            jSONObject.put("service_call", a(acVar.l));
        }
        if (acVar.m != null) {
            jSONObject.put("guide", a(acVar.m));
        }
        if (acVar.n != null) {
            jSONObject.put("charges", a(acVar.n));
        }
        if (acVar.o != null) {
            jSONObject.put("help_info", a(acVar.o));
        }
        if (acVar.p != null) {
            jSONObject.put("online_business", a(acVar.p));
        }
        if (acVar.q != null) {
            jSONObject.put("queue_info", a(acVar.q));
        }
        if (acVar.r != null) {
            jSONObject.put("discount", a(acVar.r));
        }
        if (acVar.s != null) {
            jSONObject.put("appointment", a(acVar.s));
        }
        if (acVar.t != null) {
            jSONObject.put("fee_scale", a(acVar.t));
        }
        if (acVar.u != null) {
            jSONObject.put("events", a(acVar.u));
        }
        if (acVar.v != null) {
            jSONObject.put("child_poi", a(acVar.v));
        }
        if (acVar.w != null) {
            jSONObject.put("park_info", a(acVar.w));
        }
        if (acVar.x != null) {
            jSONObject.put("service_info", a(acVar.x));
        }
        if (acVar.y != null) {
            jSONObject.put("swimmingpool_rti", a(acVar.y));
        }
        if (acVar.z != null) {
            jSONObject.put("com_airport_service_detail", a(acVar.z));
        }
        if (acVar.A != null) {
            jSONObject.put("onsale_discount", a(acVar.A));
        }
        if (acVar.B != null) {
            jSONObject.put("dynamic_info", a(acVar.B));
        }
        if (acVar.C != null) {
            jSONObject.put("store_discount", a(acVar.C));
        }
        if (acVar.D != null) {
            jSONObject.put("shop_basicinfo", a(acVar.D));
        }
        if (acVar.E != null) {
            jSONObject.put("shop_service", a(acVar.E));
        }
        if (acVar.F != null) {
            jSONObject.put("famous", a(acVar.F));
        }
        return jSONObject;
    }

    private static JSONObject a(ai aiVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(aiVar.a)) {
            jSONObject.put("data_type", aiVar.a);
        }
        if (aiVar.b != null && aiVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (aj a : aiVar.b) {
                jSONArray.put(a(a));
            }
            jSONObject.put("data", jSONArray);
        }
        if (aiVar.c != null) {
            jSONObject.put("hide_log", new JSONObject(aiVar.c));
        }
        return jSONObject;
    }

    private static JSONObject a(ak akVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(akVar.a)) {
            jSONObject.put("text", akVar.a);
        }
        if (!TextUtils.isEmpty(akVar.b)) {
            jSONObject.put("width", akVar.b);
        }
        if (!TextUtils.isEmpty(akVar.c)) {
            jSONObject.put("rel_type", akVar.c);
        }
        if (!TextUtils.isEmpty(akVar.d)) {
            jSONObject.put("url", akVar.d);
        }
        if (akVar.e != null) {
            jSONObject.put("req_params", new JSONObject(akVar.e));
        }
        if (!TextUtils.isEmpty(akVar.f)) {
            jSONObject.put("type", akVar.f);
        }
        return jSONObject;
    }

    private static JSONObject a(aj ajVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(ajVar.a)) {
            jSONObject.put(ResUtils.STYLE, ajVar.a);
        }
        if (!TextUtils.isEmpty(ajVar.b)) {
            jSONObject.put("title", ajVar.b);
        }
        if (!TextUtils.isEmpty(ajVar.c)) {
            jSONObject.put("text", ajVar.c);
        }
        if (ajVar.d != null && ajVar.d.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (String put : ajVar.d) {
                jSONArray.put(put);
            }
            jSONObject.put("textlist", jSONArray);
        }
        if (ajVar.e != null && ajVar.e.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (al alVar : ajVar.e) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(alVar.a)) {
                    jSONObject2.put("text1", alVar.a);
                }
                if (!TextUtils.isEmpty(alVar.b)) {
                    jSONObject2.put("text2", alVar.b);
                }
                if (!TextUtils.isEmpty(alVar.c)) {
                    jSONObject2.put("text3", alVar.c);
                }
                if (!TextUtils.isEmpty(alVar.d)) {
                    jSONObject2.put("color", alVar.d);
                }
                if (!TextUtils.isEmpty(alVar.e)) {
                    jSONObject2.put("text", alVar.e);
                }
                if (!TextUtils.isEmpty(alVar.f)) {
                    jSONObject2.put("doc", alVar.f);
                }
                if (!TextUtils.isEmpty(alVar.g)) {
                    jSONObject2.put("url", alVar.g);
                }
                if (!TextUtils.isEmpty(alVar.h)) {
                    jSONObject2.put("type", alVar.h);
                }
                jSONArray2.put(jSONObject2);
            }
            jSONObject.put("objectlist", jSONArray2);
        }
        if (!TextUtils.isEmpty(ajVar.f)) {
            jSONObject.put("color", ajVar.f);
        }
        if (!TextUtils.isEmpty(ajVar.g)) {
            jSONObject.put("type", ajVar.g);
        }
        if (!TextUtils.isEmpty(ajVar.h)) {
            jSONObject.put("source", ajVar.h);
        }
        if (!TextUtils.isEmpty(ajVar.i)) {
            jSONObject.put(H5Param.MENU_ICON, ajVar.i);
        }
        if (!TextUtils.isEmpty(ajVar.j)) {
            jSONObject.put("price_ori", ajVar.j);
        }
        if (!TextUtils.isEmpty(ajVar.k)) {
            jSONObject.put("price", ajVar.k);
        }
        if (!TextUtils.isEmpty(ajVar.l)) {
            jSONObject.put("doc1", ajVar.l);
        }
        if (!TextUtils.isEmpty(ajVar.m)) {
            jSONObject.put("doc2", ajVar.m);
        }
        if (!TextUtils.isEmpty(ajVar.n)) {
            jSONObject.put("jump", ajVar.n);
        }
        if (!TextUtils.isEmpty(ajVar.o)) {
            jSONObject.put("url", ajVar.o);
        }
        jSONObject.put("cols", ajVar.p);
        if (ajVar.q != null && ajVar.q.length > 0) {
            JSONArray jSONArray3 = new JSONArray();
            for (ak a : ajVar.q) {
                jSONArray3.put(a(a));
            }
            jSONObject.put("datalist", jSONArray3);
        }
        if (ajVar.r != null) {
            jSONObject.put("req_params", new JSONObject(ajVar.r));
        }
        if (!TextUtils.isEmpty(ajVar.s)) {
            jSONObject.put("groupkey", ajVar.s);
        }
        jSONObject.put("isborder", ajVar.t);
        jSONObject.put("status", ajVar.u);
        if (!TextUtils.isEmpty(ajVar.v)) {
            jSONObject.put("height", ajVar.v);
        }
        if (!TextUtils.isEmpty(ajVar.w)) {
            jSONObject.put("padding", ajVar.w);
        }
        if (!TextUtils.isEmpty(ajVar.x)) {
            jSONObject.put("bgcolor", ajVar.x);
        }
        jSONObject.put("ishide", ajVar.y);
        jSONObject.put("line_type", ajVar.z);
        if (!TextUtils.isEmpty(ajVar.A)) {
            jSONObject.put("align", ajVar.A);
        }
        if (ajVar.B != null && ajVar.B.length > 0) {
            JSONArray jSONArray4 = new JSONArray();
            for (ak a2 : ajVar.B) {
                jSONArray4.put(a(a2));
            }
            jSONObject.put("cells", jSONArray4);
        }
        if (!TextUtils.isEmpty(ajVar.C)) {
            jSONObject.put("group", ajVar.C);
        }
        if (!TextUtils.isEmpty(ajVar.D)) {
            jSONObject.put("doc", ajVar.D);
        }
        if (ajVar.E != null) {
            jSONObject.put("hide_log", new JSONObject(ajVar.E));
        }
        if (ajVar.F != null) {
            jSONObject.put("query_fields", new JSONObject(ajVar.F));
        }
        if (!TextUtils.isEmpty(ajVar.G)) {
            jSONObject.put("src_type", ajVar.G);
        }
        return jSONObject;
    }

    private static JSONObject a(af afVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(afVar.a)) {
            jSONObject.put("name", afVar.a);
        }
        if (!TextUtils.isEmpty(afVar.b)) {
            jSONObject.put("anchor", afVar.b);
        }
        if (afVar.c != null && afVar.c.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (af a : afVar.c) {
                jSONArray.put(a(a));
            }
            jSONObject.put("services", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(ad adVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (adVar.a != null && adVar.a.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (ae aeVar : adVar.a) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(aeVar.a)) {
                    jSONObject2.put("name", aeVar.a);
                }
                if (!TextUtils.isEmpty(aeVar.b)) {
                    jSONObject2.put("name_cn", aeVar.b);
                }
                if (!TextUtils.isEmpty(aeVar.c)) {
                    jSONObject2.put("tel", aeVar.c);
                }
                if (!TextUtils.isEmpty(aeVar.d)) {
                    jSONObject2.put(ModulePoi.TIPS, aeVar.d);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("bar_items", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(ag agVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (agVar.a != null) {
            jSONObject.put("t_tag", a(agVar.a));
        }
        if (agVar.b != null && agVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (an a : agVar.b) {
                jSONArray.put(a(a));
            }
            jSONObject.put("poitag", jSONArray);
        }
        if (!TextUtils.isEmpty(agVar.c)) {
            jSONObject.put("name", agVar.c);
        }
        if (!TextUtils.isEmpty(agVar.d)) {
            jSONObject.put("distance", agVar.d);
        }
        if (!TextUtils.isEmpty(agVar.e)) {
            jSONObject.put("address", agVar.e);
        }
        if (!TextUtils.isEmpty(agVar.f)) {
            jSONObject.put("star", agVar.f);
        }
        if (!TextUtils.isEmpty(agVar.g)) {
            jSONObject.put("price", agVar.g);
        }
        if (agVar.h != null && agVar.h.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (am amVar : agVar.h) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(amVar.a)) {
                    jSONObject2.put(H5Param.MENU_ICON, amVar.a);
                }
                if (!TextUtils.isEmpty(amVar.b)) {
                    jSONObject2.put("type", amVar.b);
                }
                jSONArray2.put(jSONObject2);
            }
            jSONObject.put("poi_logo", jSONArray2);
        }
        return jSONObject;
    }

    private static JSONObject a(an anVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(anVar.a)) {
            jSONObject.put("bg_color", anVar.a);
        }
        if (!TextUtils.isEmpty(anVar.b)) {
            jSONObject.put("text_color", anVar.b);
        }
        if (!TextUtils.isEmpty(anVar.c)) {
            jSONObject.put("value", anVar.c);
        }
        return jSONObject;
    }

    private static JSONObject a(e eVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(eVar.a)) {
            jSONObject.put("name", eVar.a);
        }
        if (!TextUtils.isEmpty(eVar.b)) {
            jSONObject.put("anchor", eVar.b);
        }
        if (eVar.c != null && eVar.c.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (f fVar : eVar.c) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(fVar.a)) {
                    jSONObject2.put("name", fVar.a);
                }
                if (!TextUtils.isEmpty(fVar.b)) {
                    jSONObject2.put("anchor", fVar.b);
                }
                if (!TextUtils.isEmpty(fVar.c)) {
                    jSONObject2.put("intro", fVar.c);
                }
                if (fVar.d != null) {
                    d dVar = fVar.d;
                    JSONObject jSONObject3 = new JSONObject();
                    if (!TextUtils.isEmpty(dVar.a)) {
                        jSONObject3.put("floor", dVar.a);
                    }
                    if (!TextUtils.isEmpty(dVar.b)) {
                        jSONObject3.put("location", dVar.b);
                    }
                    jSONObject2.put("airport_info", jSONObject3);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("services", jSONArray);
        }
        return jSONObject;
    }

    public static JSONObject a(at atVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(atVar.a)) {
            jSONObject.put("result", atVar.a);
        }
        if (!TextUtils.isEmpty(atVar.b)) {
            jSONObject.put("code", atVar.b);
        }
        if (!TextUtils.isEmpty(atVar.c)) {
            jSONObject.put("message", atVar.c);
        }
        if (!TextUtils.isEmpty(atVar.d)) {
            jSONObject.put("timestamp", atVar.d);
        }
        if (!TextUtils.isEmpty(atVar.e)) {
            jSONObject.put("version", atVar.e);
        }
        jSONObject.put("total_count", atVar.f);
        if (atVar.g != null && atVar.g.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (as asVar : atVar.g) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(asVar.a)) {
                    jSONObject2.put("style_type", asVar.a);
                }
                if (!TextUtils.isEmpty(asVar.b)) {
                    jSONObject2.put("source", asVar.b);
                }
                if (!TextUtils.isEmpty(asVar.c)) {
                    jSONObject2.put("open_url", asVar.c);
                }
                if (!TextUtils.isEmpty(asVar.d)) {
                    jSONObject2.put("views_count", asVar.d);
                }
                if (!TextUtils.isEmpty(asVar.e)) {
                    jSONObject2.put("title", asVar.e);
                }
                if (!TextUtils.isEmpty(asVar.f)) {
                    jSONObject2.put("id", asVar.f);
                }
                if (!TextUtils.isEmpty(asVar.g)) {
                    jSONObject2.put("biz_type", asVar.g);
                }
                if (!TextUtils.isEmpty(asVar.h)) {
                    jSONObject2.put("date", asVar.h);
                }
                if (!TextUtils.isEmpty(asVar.i)) {
                    jSONObject2.put("media_type", asVar.i);
                }
                if (!TextUtils.isEmpty(asVar.j)) {
                    jSONObject2.put("data_date", asVar.j);
                }
                if (!TextUtils.isEmpty(asVar.k)) {
                    jSONObject2.put("pic_url", asVar.k);
                }
                if (!TextUtils.isEmpty(asVar.l)) {
                    jSONObject2.put("desc", asVar.l);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("media_list", jSONArray);
        }
        return jSONObject;
    }

    public static JSONObject a(ax axVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(axVar.a)) {
            jSONObject.put("code", axVar.a);
        }
        if (!TextUtils.isEmpty(axVar.b)) {
            jSONObject.put("result", axVar.b);
        }
        if (!TextUtils.isEmpty(axVar.c)) {
            jSONObject.put("timestamp", axVar.c);
        }
        if (!TextUtils.isEmpty(axVar.d)) {
            jSONObject.put("version", axVar.d);
        }
        if (!TextUtils.isEmpty(axVar.e)) {
            jSONObject.put("message", axVar.e);
        }
        if (!TextUtils.isEmpty(axVar.f)) {
            jSONObject.put("total", axVar.f);
        }
        if (!TextUtils.isEmpty(axVar.g)) {
            jSONObject.put("bounds", axVar.g);
        }
        if (!TextUtils.isEmpty(axVar.h)) {
            jSONObject.put("keywords", axVar.h);
        }
        if (axVar.i != null && axVar.i.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (aw awVar : axVar.i) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(awVar.a)) {
                    jSONObject2.put("businfo_lineids", awVar.a);
                }
                if (!TextUtils.isEmpty(awVar.b)) {
                    jSONObject2.put("code", awVar.b);
                }
                if (!TextUtils.isEmpty(awVar.c)) {
                    jSONObject2.put("tel", awVar.c);
                }
                if (!TextUtils.isEmpty(awVar.d)) {
                    jSONObject2.put("typecode", awVar.d);
                }
                if (!TextUtils.isEmpty(awVar.e)) {
                    jSONObject2.put("typeflag", awVar.e);
                }
                if (!TextUtils.isEmpty(awVar.f)) {
                    jSONObject2.put("areacode", awVar.f);
                }
                if (!TextUtils.isEmpty(awVar.g)) {
                    jSONObject2.put("businfo_stationids", awVar.g);
                }
                if (!TextUtils.isEmpty(awVar.h)) {
                    jSONObject2.put("businfo_angles", awVar.h);
                }
                if (!TextUtils.isEmpty(awVar.i)) {
                    jSONObject2.put("businfo_line_names", awVar.i);
                }
                if (!TextUtils.isEmpty(awVar.j)) {
                    jSONObject2.put("address", awVar.j);
                }
                if (!TextUtils.isEmpty(awVar.k)) {
                    jSONObject2.put("xs", awVar.k);
                }
                if (!TextUtils.isEmpty(awVar.l)) {
                    jSONObject2.put("ys", awVar.l);
                }
                if (!TextUtils.isEmpty(awVar.m)) {
                    jSONObject2.put("newtype", awVar.m);
                }
                if (!TextUtils.isEmpty(awVar.n)) {
                    jSONObject2.put("distance", awVar.n);
                }
                if (!TextUtils.isEmpty(awVar.o)) {
                    jSONObject2.put("businfo_alias", awVar.o);
                }
                if (!TextUtils.isEmpty(awVar.p)) {
                    jSONObject2.put("businfo_ui_colors", awVar.p);
                }
                if (!TextUtils.isEmpty(awVar.q)) {
                    jSONObject2.put("name", awVar.q);
                }
                if (!TextUtils.isEmpty(awVar.r)) {
                    jSONObject2.put("lines", awVar.r);
                }
                if (!TextUtils.isEmpty(awVar.s)) {
                    jSONObject2.put("id", awVar.s);
                }
                if (!TextUtils.isEmpty(awVar.t)) {
                    jSONObject2.put("srctype", awVar.t);
                }
                if (!TextUtils.isEmpty(awVar.u)) {
                    jSONObject2.put(DictionaryKeys.CTRLXY_Y, awVar.u);
                }
                if (!TextUtils.isEmpty(awVar.v)) {
                    jSONObject2.put(DictionaryKeys.CTRLXY_X, awVar.v);
                }
                if (!TextUtils.isEmpty(awVar.w)) {
                    jSONObject2.put("businfo_line_keys", awVar.w);
                }
                if (!TextUtils.isEmpty(awVar.x)) {
                    jSONObject2.put("type", awVar.x);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("bus_list", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(br brVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(brVar.a)) {
            jSONObject.put("name", brVar.a);
        }
        if (!TextUtils.isEmpty(brVar.b)) {
            jSONObject.put("status_text", brVar.b);
        }
        if (brVar.c != null && brVar.c.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (String put : brVar.c) {
                jSONArray.put(put);
            }
            jSONObject.put("changes", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(ar arVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(arVar.a)) {
            jSONObject.put("front_name", arVar.a);
        }
        if (!TextUtils.isEmpty(arVar.b)) {
            jSONObject.put("loop", arVar.b);
        }
        if (!TextUtils.isEmpty(arVar.c)) {
            jSONObject.put("next_station_name", arVar.c);
        }
        if (!TextUtils.isEmpty(arVar.d)) {
            jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, arVar.d);
        }
        if (!TextUtils.isEmpty(arVar.e)) {
            jSONObject.put("current_start_time", arVar.e);
        }
        if (!TextUtils.isEmpty(arVar.f)) {
            jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, arVar.f);
        }
        if (!TextUtils.isEmpty(arVar.g)) {
            jSONObject.put("current_end_time", arVar.g);
        }
        if (!TextUtils.isEmpty(arVar.h)) {
            jSONObject.put("terminal_name", arVar.h);
        }
        if (!TextUtils.isEmpty(arVar.i)) {
            jSONObject.put("arrival_time", arVar.i);
        }
        if (!TextUtils.isEmpty(arVar.j)) {
            jSONObject.put("id", arVar.j);
        }
        if (arVar.k != null && arVar.k.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (br a : arVar.k) {
                jSONArray.put(a(a));
            }
            jSONObject.put("stations", jSONArray);
        }
        if (!TextUtils.isEmpty(arVar.l)) {
            jSONObject.put("service_status", arVar.l);
        }
        return jSONObject;
    }

    private static JSONObject a(c cVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(cVar.a)) {
            jSONObject.put("total_price", cVar.a);
        }
        if (!TextUtils.isEmpty(cVar.b)) {
            jSONObject.put("under_construction", cVar.b);
        }
        if (!TextUtils.isEmpty(cVar.c)) {
            jSONObject.put("under_construction_desc", cVar.c);
        }
        if (!TextUtils.isEmpty(cVar.d)) {
            jSONObject.put("subway_icon", cVar.d);
        }
        if (!TextUtils.isEmpty(cVar.e)) {
            jSONObject.put("color", cVar.e);
        }
        if (!TextUtils.isEmpty(cVar.f)) {
            jSONObject.put("name", cVar.f);
        }
        if (!TextUtils.isEmpty(cVar.g)) {
            jSONObject.put(H5SensorPlugin.PARAM_INTERVAL, cVar.g);
        }
        if (!TextUtils.isEmpty(cVar.h)) {
            jSONObject.put("base_price", cVar.h);
        }
        if (cVar.i != null && cVar.i.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (ar a : cVar.i) {
                jSONArray.put(a(a));
            }
            jSONObject.put("lines", jSONArray);
        }
        return jSONObject;
    }

    public static JSONObject a(ay ayVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", ayVar.a);
        jSONObject.put("result", ayVar.b);
        if (!TextUtils.isEmpty(ayVar.c)) {
            jSONObject.put("timestamp", ayVar.c);
        }
        if (!TextUtils.isEmpty(ayVar.d)) {
            jSONObject.put("version", ayVar.d);
        }
        if (!TextUtils.isEmpty(ayVar.e)) {
            jSONObject.put("message", ayVar.e);
        }
        jSONObject.put("total", ayVar.f);
        if (ayVar.g != null && ayVar.g.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (s sVar : ayVar.g) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(sVar.a)) {
                    jSONObject2.put("description", sVar.a);
                }
                if (!TextUtils.isEmpty(sVar.b)) {
                    jSONObject2.put("state", sVar.b);
                }
                if (!TextUtils.isEmpty(sVar.c)) {
                    jSONObject2.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, sVar.c);
                }
                if (!TextUtils.isEmpty(sVar.d)) {
                    jSONObject2.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, sVar.d);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("emergencies", jSONArray);
        }
        if (ayVar.h != null && ayVar.h.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (c a : ayVar.h) {
                jSONArray2.put(a(a));
            }
            jSONObject.put("busline_list", jSONArray2);
        }
        return jSONObject;
    }

    public static JSONObject a(bc bcVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bcVar.a)) {
            jSONObject.put("code", bcVar.a);
        }
        if (!TextUtils.isEmpty(bcVar.b)) {
            jSONObject.put("result", bcVar.b);
        }
        if (!TextUtils.isEmpty(bcVar.c)) {
            jSONObject.put("timestamp", bcVar.c);
        }
        if (!TextUtils.isEmpty(bcVar.d)) {
            jSONObject.put("version", bcVar.d);
        }
        if (!TextUtils.isEmpty(bcVar.e)) {
            jSONObject.put("message", bcVar.e);
        }
        if (!TextUtils.isEmpty(bcVar.f)) {
            jSONObject.put("abtest_id", bcVar.f);
        }
        jSONObject.put("rows", bcVar.g);
        if (bcVar.h != null && bcVar.h.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bd bdVar : bcVar.h) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(bdVar.a)) {
                    jSONObject2.put("content", bdVar.a);
                }
                if (!TextUtils.isEmpty(bdVar.b)) {
                    jSONObject2.put(ResUtils.STYLE, bdVar.b);
                }
                if (!TextUtils.isEmpty(bdVar.c)) {
                    jSONObject2.put("type", bdVar.c);
                }
                if (!TextUtils.isEmpty(bdVar.d)) {
                    jSONObject2.put("id", bdVar.d);
                }
                if (!TextUtils.isEmpty(bdVar.e)) {
                    jSONObject2.put("pic", bdVar.e);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("data", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(bj bjVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bjVar.a)) {
            jSONObject.put("url", bjVar.a);
        }
        if (!TextUtils.isEmpty(bjVar.b)) {
            jSONObject.put("source", bjVar.b);
        }
        return jSONObject;
    }

    private static JSONObject a(defpackage.a.b bVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bVar.a)) {
            jSONObject.put("ios_appurl", bVar.a);
        }
        if (!TextUtils.isEmpty(bVar.b)) {
            jSONObject.put("android_appurl", bVar.b);
        }
        return jSONObject;
    }

    private static JSONObject a(bg bgVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bgVar.a)) {
            jSONObject.put("review_id", bgVar.a);
        }
        if (!TextUtils.isEmpty(bgVar.b)) {
            jSONObject.put("author", bgVar.b);
        }
        if (!TextUtils.isEmpty(bgVar.c)) {
            jSONObject.put("author_profileurl", bgVar.c);
        }
        if (!TextUtils.isEmpty(bgVar.d)) {
            jSONObject.put("review", bgVar.d);
        }
        if (!TextUtils.isEmpty(bgVar.e)) {
            jSONObject.put("time", bgVar.e);
        }
        jSONObject.put("score", (double) bgVar.f);
        if (!TextUtils.isEmpty(bgVar.g)) {
            jSONObject.put("src_type", bgVar.g);
        }
        if (!TextUtils.isEmpty(bgVar.h)) {
            jSONObject.put("src_name", bgVar.h);
        }
        if (!TextUtils.isEmpty(bgVar.i)) {
            jSONObject.put("gold_type", bgVar.i);
        }
        jSONObject.put("gold_num", bgVar.j);
        if (bgVar.k != null && bgVar.k.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bj a : bgVar.k) {
                jSONArray.put(a(a));
            }
            jSONObject.put("pic_info", jSONArray);
        }
        if (!TextUtils.isEmpty(bgVar.l)) {
            jSONObject.put("like_num", bgVar.l);
        }
        if (!TextUtils.isEmpty(bgVar.m)) {
            jSONObject.put(ModuleFeedBack.RECOMMEND, bgVar.m);
        }
        if (bgVar.n != null && bgVar.n.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (bi biVar : bgVar.n) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(biVar.a)) {
                    jSONObject2.put("label_content", biVar.a);
                }
                if (!TextUtils.isEmpty(biVar.b)) {
                    jSONObject2.put("label_light", biVar.b);
                }
                jSONArray2.put(jSONObject2);
            }
            jSONObject.put("labels", jSONArray2);
        }
        jSONObject.put("quality_flag", bgVar.o);
        if (!TextUtils.isEmpty(bgVar.p)) {
            jSONObject.put("review_wapurl", bgVar.p);
        }
        if (!TextUtils.isEmpty(bgVar.q)) {
            jSONObject.put("review_weburl", bgVar.q);
        }
        if (bgVar.r != null) {
            jSONObject.put("review_appurl", a(bgVar.r));
        }
        jSONObject.put("high_quality", bgVar.s);
        return jSONObject;
    }

    private static JSONObject a(w wVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(wVar.a)) {
            jSONObject.put("author", wVar.a);
        }
        if (!TextUtils.isEmpty(wVar.b)) {
            jSONObject.put("author_profileurl", wVar.b);
        }
        if (!TextUtils.isEmpty(wVar.c)) {
            jSONObject.put("time", wVar.c);
        }
        if (!TextUtils.isEmpty(wVar.d)) {
            jSONObject.put(Oauth2AccessToken.KEY_UID, wVar.d);
        }
        if (!TextUtils.isEmpty(wVar.e)) {
            jSONObject.put(ModuleFeedBack.RECOMMEND, wVar.e);
        }
        if (!TextUtils.isEmpty(wVar.f)) {
            jSONObject.put("review", wVar.f);
        }
        if (wVar.g != null && wVar.g.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bj a : wVar.g) {
                jSONArray.put(a(a));
            }
            jSONObject.put("pic_info", jSONArray);
        }
        return jSONObject;
    }

    public static JSONObject a(bk bkVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bkVar.a)) {
            jSONObject.put("code", bkVar.a);
        }
        if (!TextUtils.isEmpty(bkVar.b)) {
            jSONObject.put("result", bkVar.b);
        }
        if (!TextUtils.isEmpty(bkVar.c)) {
            jSONObject.put("timestamp", bkVar.c);
        }
        if (!TextUtils.isEmpty(bkVar.d)) {
            jSONObject.put("version", bkVar.d);
        }
        if (!TextUtils.isEmpty(bkVar.e)) {
            jSONObject.put("message", bkVar.e);
        }
        if (!TextUtils.isEmpty(bkVar.f)) {
            jSONObject.put(Constants.KEY_AUDIO_BUSINESS_ID, bkVar.f);
        }
        if (!TextUtils.isEmpty(bkVar.g)) {
            jSONObject.put("cityname", bkVar.g);
        }
        jSONObject.put("total", bkVar.h);
        jSONObject.put("rec_count", bkVar.i);
        jSONObject.put("new_count", bkVar.j);
        jSONObject.put("bad_count", bkVar.k);
        jSONObject.put("pic_count", bkVar.l);
        jSONObject.put("page_total", bkVar.m);
        if (!TextUtils.isEmpty(bkVar.n)) {
            jSONObject.put("positive_rate", bkVar.n);
        }
        if (!TextUtils.isEmpty(bkVar.o)) {
            jSONObject.put("negative_rate", bkVar.o);
        }
        jSONObject.put("src_star", (double) bkVar.p);
        jSONObject.put("myreview_total", bkVar.q);
        if (bkVar.r != null && bkVar.r.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (i iVar : bkVar.r) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(iVar.a)) {
                    jSONObject2.put("name", iVar.a);
                }
                if (!TextUtils.isEmpty(iVar.b)) {
                    jSONObject2.put("name_ch", iVar.b);
                }
                jSONObject2.put("score", (double) iVar.c);
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("detail_scores", jSONArray);
        }
        if (bkVar.s != null && bkVar.s.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (bn bnVar : bkVar.s) {
                JSONObject jSONObject3 = new JSONObject();
                if (!TextUtils.isEmpty(bnVar.a)) {
                    jSONObject3.put("name", bnVar.a);
                }
                jSONObject3.put(NewHtcHomeBadger.COUNT, bnVar.b);
                jSONObject3.put(H5PermissionManager.level, bnVar.c);
                jSONObject3.put("type", bnVar.d);
                jSONArray2.put(jSONObject3);
            }
            jSONObject.put("sellpoints", jSONArray2);
        }
        if (bkVar.t != null && bkVar.t.length > 0) {
            JSONArray jSONArray3 = new JSONArray();
            for (bg a : bkVar.t) {
                jSONArray3.put(a(a));
            }
            jSONObject.put("my_review", jSONArray3);
        }
        if (bkVar.u != null && bkVar.u.length > 0) {
            JSONArray jSONArray4 = new JSONArray();
            for (bg a2 : bkVar.u) {
                jSONArray4.put(a(a2));
            }
            jSONObject.put("review_list", jSONArray4);
        }
        if (bkVar.v != null && bkVar.v.length > 0) {
            JSONArray jSONArray5 = new JSONArray();
            for (bh bhVar : bkVar.v) {
                JSONObject jSONObject4 = new JSONObject();
                if (!TextUtils.isEmpty(bhVar.a)) {
                    jSONObject4.put("src_type", bhVar.a);
                }
                if (!TextUtils.isEmpty(bhVar.b)) {
                    jSONObject4.put("src_name", bhVar.b);
                }
                if (!TextUtils.isEmpty(bhVar.c)) {
                    jSONObject4.put("review_num", bhVar.c);
                }
                if (!TextUtils.isEmpty(bhVar.d)) {
                    jSONObject4.put("review_all_wapurl", bhVar.d);
                }
                if (!TextUtils.isEmpty(bhVar.e)) {
                    jSONObject4.put("review_all_weburl", bhVar.e);
                }
                if (bhVar.f != null) {
                    jSONObject4.put("review_all_appurl", a(bhVar.f));
                }
                jSONArray5.put(jSONObject4);
            }
            jSONObject.put("review_info", jSONArray5);
        }
        if (bkVar.w != null) {
            jSONObject.put("first_review", a(bkVar.w));
        }
        if (bkVar.x != null && bkVar.x.length > 0) {
            JSONArray jSONArray6 = new JSONArray();
            for (bl blVar : bkVar.x) {
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("good", blVar.a);
                jSONObject5.put("bad", blVar.b);
                jSONObject5.put("moderate", blVar.c);
                if (!TextUtils.isEmpty(blVar.d)) {
                    jSONObject5.put("good_rate", blVar.d);
                }
                if (!TextUtils.isEmpty(blVar.e)) {
                    jSONObject5.put("bad_rate", blVar.e);
                }
                if (!TextUtils.isEmpty(blVar.f)) {
                    jSONObject5.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, blVar.f);
                }
                if (!TextUtils.isEmpty(blVar.g)) {
                    jSONObject5.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, blVar.g);
                }
                jSONArray6.put(jSONObject5);
            }
            jSONObject.put("review_rate", jSONArray6);
        }
        if (bkVar.y != null) {
            jSONObject.put("star_list", new JSONObject(bkVar.y));
        }
        return jSONObject;
    }

    public static JSONObject a(bo boVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(boVar.a)) {
            jSONObject.put("code", boVar.a);
        }
        if (!TextUtils.isEmpty(boVar.b)) {
            jSONObject.put("result", boVar.b);
        }
        if (!TextUtils.isEmpty(boVar.c)) {
            jSONObject.put("timestamp", boVar.c);
        }
        if (!TextUtils.isEmpty(boVar.d)) {
            jSONObject.put("version", boVar.d);
        }
        if (!TextUtils.isEmpty(boVar.e)) {
            jSONObject.put("message", boVar.e);
        }
        if (boVar.f != null && boVar.f.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (h hVar : boVar.f) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(hVar.b)) {
                    jSONObject2.put("name", hVar.b);
                }
                jSONObject2.put("type", hVar.c);
                if (!TextUtils.isEmpty(hVar.d)) {
                    jSONObject2.put("url", hVar.d);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("coupon_data", jSONArray);
        }
        if (boVar.g != null && boVar.g.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (ab abVar : boVar.g) {
                JSONObject jSONObject3 = new JSONObject();
                if (!TextUtils.isEmpty(abVar.b)) {
                    jSONObject3.put("name", abVar.b);
                }
                jSONObject3.put("sold_count", abVar.c);
                if (!TextUtils.isEmpty(abVar.d)) {
                    jSONObject3.put("original_price", abVar.d);
                }
                if (!TextUtils.isEmpty(abVar.e)) {
                    jSONObject3.put("final_price", abVar.e);
                }
                if (!TextUtils.isEmpty(abVar.f)) {
                    jSONObject3.put("pic", abVar.f);
                }
                if (!TextUtils.isEmpty(abVar.h)) {
                    jSONObject3.put("url", abVar.h);
                }
                jSONArray2.put(jSONObject3);
            }
            jSONObject.put("hotsell_data", jSONArray2);
        }
        return jSONObject;
    }

    private static JSONObject a(bp bpVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bpVar.a)) {
            jSONObject.put("sp_id", bpVar.a);
        }
        if (!TextUtils.isEmpty(bpVar.b)) {
            jSONObject.put("sp_name", bpVar.b);
        }
        if (!TextUtils.isEmpty(bpVar.c)) {
            jSONObject.put("height", bpVar.c);
        }
        if (!TextUtils.isEmpty(bpVar.d)) {
            jSONObject.put("length", bpVar.d);
        }
        if (!TextUtils.isEmpty(bpVar.e)) {
            jSONObject.put("width", bpVar.e);
        }
        if (!TextUtils.isEmpty(bpVar.f)) {
            jSONObject.put("area", bpVar.f);
        }
        if (!TextUtils.isEmpty(bpVar.g)) {
            jSONObject.put(NewHtcHomeBadger.COUNT, bpVar.g);
        }
        jSONObject.put("price", bpVar.h);
        if (!TextUtils.isEmpty(bpVar.i)) {
            jSONObject.put("price_unit", bpVar.i);
        }
        if (bpVar.j != null && bpVar.j.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (av avVar : bpVar.j) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(avVar.a)) {
                    jSONObject2.put("url", avVar.a);
                }
                if (!TextUtils.isEmpty(avVar.b)) {
                    jSONObject2.put("src_type", avVar.b);
                }
                if (!TextUtils.isEmpty(avVar.c)) {
                    jSONObject2.put("title", avVar.c);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("pic_info", jSONArray);
        }
        if (bpVar.k != null && bpVar.k.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (bm bmVar : bpVar.k) {
                JSONObject jSONObject3 = new JSONObject();
                if (!TextUtils.isEmpty(bmVar.a)) {
                    jSONObject3.put("rp_id", bmVar.a);
                }
                if (!TextUtils.isEmpty(bmVar.b)) {
                    jSONObject3.put("rp_name", bmVar.b);
                }
                if (!TextUtils.isEmpty(bmVar.c)) {
                    jSONObject3.put("o_price", bmVar.c);
                }
                jSONObject3.put("n_price", bmVar.d);
                if (!TextUtils.isEmpty(bmVar.e)) {
                    jSONObject3.put("deposit", bmVar.e);
                }
                if (!TextUtils.isEmpty(bmVar.f)) {
                    jSONObject3.put("price_unit", bmVar.f);
                }
                if (!TextUtils.isEmpty(bmVar.g)) {
                    jSONObject3.put("tel", bmVar.g);
                }
                jSONArray2.put(jSONObject3);
            }
            jSONObject.put("rp_info", jSONArray2);
        }
        return jSONObject;
    }

    public static JSONObject a(bq bqVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bqVar.a)) {
            jSONObject.put("code", bqVar.a);
        }
        if (!TextUtils.isEmpty(bqVar.b)) {
            jSONObject.put("result", bqVar.b);
        }
        if (!TextUtils.isEmpty(bqVar.c)) {
            jSONObject.put("timestamp", bqVar.c);
        }
        if (!TextUtils.isEmpty(bqVar.d)) {
            jSONObject.put("version", bqVar.d);
        }
        if (!TextUtils.isEmpty(bqVar.e)) {
            jSONObject.put("message", bqVar.e);
        }
        if (bqVar.f != null && bqVar.f.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bp a : bqVar.f) {
                jSONArray.put(a(a));
            }
            jSONObject.put("data", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(bv bvVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bvVar.a)) {
            jSONObject.put("type", bvVar.a);
        }
        jSONObject.put("has_more", bvVar.b);
        if (bvVar.c != null && bvVar.c.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bw bwVar : bvVar.c) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(bwVar.a)) {
                    jSONObject2.put("new_type", bwVar.a);
                }
                if (!TextUtils.isEmpty(bwVar.b)) {
                    jSONObject2.put(Constants.KEY_AUDIO_BUSINESS_ID, bwVar.b);
                }
                if (!TextUtils.isEmpty(bwVar.c)) {
                    jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, bwVar.c);
                }
                if (!TextUtils.isEmpty(bwVar.d)) {
                    jSONObject2.put(DictionaryKeys.CTRLXY_X, bwVar.d);
                }
                if (!TextUtils.isEmpty(bwVar.e)) {
                    jSONObject2.put(DictionaryKeys.CTRLXY_Y, bwVar.e);
                }
                if (!TextUtils.isEmpty(bwVar.f)) {
                    jSONObject2.put("name", bwVar.f);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("poi_list", jSONArray);
        }
        return jSONObject;
    }

    public static JSONObject a(bu buVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(buVar.a)) {
            jSONObject.put("result", buVar.a);
        }
        if (!TextUtils.isEmpty(buVar.b)) {
            jSONObject.put("code", buVar.b);
        }
        if (!TextUtils.isEmpty(buVar.c)) {
            jSONObject.put("message", buVar.c);
        }
        if (!TextUtils.isEmpty(buVar.d)) {
            jSONObject.put("timestamp", buVar.d);
        }
        if (!TextUtils.isEmpty(buVar.e)) {
            jSONObject.put("version", buVar.e);
        }
        if (buVar.f != null && buVar.f.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bv a : buVar.f) {
                jSONArray.put(a(a));
            }
            jSONObject.put("data", jSONArray);
        }
        return jSONObject;
    }
}
