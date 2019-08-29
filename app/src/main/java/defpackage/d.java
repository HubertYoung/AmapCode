package defpackage;

import android.text.TextUtils;
import com.alibaba.appmonitor.sample.SampleConfigConstant;
import com.alipay.mobile.beehive.audio.Constants;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.framework.util.xml.MetaInfoXmlParser;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.tinyappcommon.h5plugin.H5SensorPlugin;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.sdk.app.statistic.c;
import com.autonavi.bundle.feedback.ajx.ModuleFeedBack;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.Callout;
import com.autonavi.minimap.life.db.CouponDao;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: d reason: default package */
/* compiled from: PoiliteConvert */
public final class d {
    public static JSONObject a(a aVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (aVar.a != null) {
            C0059c cVar = aVar.a;
            JSONObject jSONObject2 = new JSONObject();
            if (!TextUtils.isEmpty(cVar.a)) {
                jSONObject2.put("address", cVar.a);
            }
            if (!TextUtils.isEmpty(cVar.b)) {
                jSONObject2.put("bcs", cVar.b);
            }
            if (!TextUtils.isEmpty(cVar.c)) {
                jSONObject2.put("brand_code", cVar.c);
            }
            if (!TextUtils.isEmpty(cVar.d)) {
                jSONObject2.put("brand_logo", cVar.d);
            }
            if (!TextUtils.isEmpty(cVar.e)) {
                jSONObject2.put("brand_title", cVar.e);
            }
            if (!TextUtils.isEmpty(cVar.f)) {
                jSONObject2.put(Constants.KEY_AUDIO_BUSINESS_ID, cVar.f);
            }
            if (!TextUtils.isEmpty(cVar.g)) {
                jSONObject2.put("city_adcode", cVar.g);
            }
            if (!TextUtils.isEmpty(cVar.h)) {
                jSONObject2.put("city_name", cVar.h);
            }
            if (!TextUtils.isEmpty(cVar.i)) {
                jSONObject2.put("code", cVar.i);
            }
            jSONObject2.put("cre_flag", cVar.j);
            jSONObject2.put("distance", (double) cVar.k);
            if (!TextUtils.isEmpty(cVar.l)) {
                jSONObject2.put("name", cVar.l);
            }
            if (!TextUtils.isEmpty(cVar.m)) {
                jSONObject2.put("navi_geometry", cVar.m);
            }
            if (!TextUtils.isEmpty(cVar.n)) {
                jSONObject2.put("new_type", cVar.n);
            }
            if (!TextUtils.isEmpty(cVar.o)) {
                jSONObject2.put("pixelx", cVar.o);
            }
            if (!TextUtils.isEmpty(cVar.p)) {
                jSONObject2.put("pixely", cVar.p);
            }
            if (!TextUtils.isEmpty(cVar.q)) {
                jSONObject2.put("poi_status", cVar.q);
            }
            if (!TextUtils.isEmpty(cVar.r)) {
                jSONObject2.put("poi_tag", cVar.r);
            }
            if (!TextUtils.isEmpty(cVar.s)) {
                jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, cVar.s);
            }
            if (!TextUtils.isEmpty(cVar.t)) {
                jSONObject2.put("telephone", cVar.t);
            }
            if (!TextUtils.isEmpty(cVar.u)) {
                jSONObject2.put(DictionaryKeys.CTRLXY_X, cVar.u);
            }
            if (!TextUtils.isEmpty(cVar.v)) {
                jSONObject2.put(DictionaryKeys.CTRLXY_Y, cVar.v);
            }
            jSONObject.put(RpcConstant.BASE, jSONObject2);
        }
        if (aVar.b != null && aVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (defpackage.c.d a : aVar.b) {
                jSONArray.put(a(a));
            }
            jSONObject.put("deep", jSONArray);
        }
        if (aVar.c != null) {
            jSONObject.put("rti", a(aVar.c));
        }
        if (aVar.d != null) {
            jSONObject.put("pic", a(aVar.d));
        }
        if (aVar.e != null) {
            aq aqVar = aVar.e;
            JSONObject jSONObject3 = new JSONObject();
            if (!TextUtils.isEmpty(aqVar.a)) {
                jSONObject3.put("sndt_id", aqVar.a);
            }
            if (!TextUtils.isEmpty(aqVar.b)) {
                jSONObject3.put("dining_meiwei_api_id", aqVar.b);
            }
            jSONObject.put("idDictionaries", jSONObject3);
        }
        if (aVar.f != null) {
            bv bvVar = aVar.f;
            JSONObject jSONObject4 = new JSONObject();
            if (bvVar.a != null) {
                bx bxVar = bvVar.a;
                JSONObject jSONObject5 = new JSONObject();
                if (!TextUtils.isEmpty(bxVar.a)) {
                    jSONObject5.put("shape", bxVar.a);
                }
                if (!TextUtils.isEmpty(bxVar.b)) {
                    jSONObject5.put("type", bxVar.b);
                }
                if (!TextUtils.isEmpty(bxVar.c)) {
                    jSONObject5.put(Callout.CALLOUT_TEXT_ALIGN_CENTER, bxVar.c);
                }
                if (!TextUtils.isEmpty(bxVar.d)) {
                    jSONObject5.put("sp_type", bxVar.d);
                }
                if (!TextUtils.isEmpty(bxVar.e)) {
                    jSONObject5.put("area", bxVar.e);
                }
                jSONObject5.put(H5PermissionManager.level, bxVar.f);
                if (!TextUtils.isEmpty(bxVar.g)) {
                    jSONObject5.put("parentid", bxVar.g);
                }
                if (!TextUtils.isEmpty(bxVar.h)) {
                    jSONObject5.put("aoiid", bxVar.h);
                }
                jSONObject4.put("mining_shape", jSONObject5);
            }
            if (bvVar.b != null) {
                jSONObject4.put("sndt", a(bvVar.b));
            }
            if (bvVar.c != null) {
                bw bwVar = bvVar.c;
                JSONObject jSONObject6 = new JSONObject();
                if (bwVar.a != null) {
                    jSONObject6.put("tbshop_api", a(bwVar.a));
                }
                jSONObject4.put("alio2o_api", jSONObject6);
            }
            if (bvVar.d != null) {
                jSONObject4.put("poi_contribution", a(bvVar.d));
            }
            jSONObject.put("spec", jSONObject4);
        }
        if (aVar.g != null && aVar.g.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (i a2 : aVar.g) {
                jSONArray2.put(a(a2));
            }
            jSONObject.put("deep_common", jSONArray2);
        }
        if (aVar.h != null) {
            jSONObject.put("modules_data", a(aVar.h));
        }
        return jSONObject;
    }

    private static JSONObject a(ar arVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (arVar.a != null) {
            jSONObject.put(Performance.KEY_LOG_HEADER, a(arVar.a));
        }
        if (!TextUtils.isEmpty(arVar.b)) {
            jSONObject.put("template_version", arVar.b);
        }
        if (arVar.c != null && arVar.c.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (String put : arVar.c) {
                jSONArray.put(put);
            }
            jSONObject.put("sort", jSONArray);
        }
        if (arVar.d != null && arVar.d.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (au a : arVar.d) {
                jSONArray2.put(a(a));
            }
            jSONObject.put("com_airport_service", jSONArray2);
        }
        if (arVar.e != null) {
            jSONObject.put("action_bar", a(arVar.e));
        }
        if (arVar.f != null) {
            jSONObject.put("detail_info", a(arVar.f));
        }
        if (arVar.g != null) {
            jSONObject.put("charging_motortype", a(arVar.g));
        }
        if (arVar.h != null) {
            jSONObject.put("supportbrand", a(arVar.h));
        }
        if (arVar.i != null) {
            jSONObject.put("serviceitem", a(arVar.i));
        }
        if (arVar.j != null) {
            jSONObject.put("opentime", a(arVar.j));
        }
        if (arVar.k != null) {
            jSONObject.put("business_scope", a(arVar.k));
        }
        if (arVar.l != null) {
            jSONObject.put("service_call", a(arVar.l));
        }
        if (arVar.m != null) {
            jSONObject.put("guide", a(arVar.m));
        }
        if (arVar.n != null) {
            jSONObject.put("charges", a(arVar.n));
        }
        if (arVar.o != null) {
            jSONObject.put("help_info", a(arVar.o));
        }
        if (arVar.p != null) {
            jSONObject.put("online_business", a(arVar.p));
        }
        if (arVar.q != null) {
            jSONObject.put("queue_info", a(arVar.q));
        }
        if (arVar.r != null) {
            jSONObject.put("discount", a(arVar.r));
        }
        if (arVar.s != null) {
            jSONObject.put("appointment", a(arVar.s));
        }
        if (arVar.t != null) {
            jSONObject.put("fee_scale", a(arVar.t));
        }
        if (arVar.u != null) {
            jSONObject.put("events", a(arVar.u));
        }
        if (arVar.v != null) {
            jSONObject.put("child_poi", a(arVar.v));
        }
        if (arVar.w != null) {
            jSONObject.put("park_info", a(arVar.w));
        }
        if (arVar.x != null) {
            jSONObject.put("service_info", a(arVar.x));
        }
        if (arVar.y != null) {
            jSONObject.put("swimmingpool_rti", a(arVar.y));
        }
        if (arVar.z != null) {
            jSONObject.put("onsale_discount", a(arVar.z));
        }
        if (arVar.A != null) {
            jSONObject.put("dynamic_info", a(arVar.A));
        }
        if (arVar.B != null) {
            jSONObject.put("store_discount", a(arVar.B));
        }
        if (arVar.C != null) {
            jSONObject.put("shop_basicinfo", a(arVar.C));
        }
        if (arVar.D != null) {
            jSONObject.put("shop_service", a(arVar.D));
        }
        if (arVar.E != null) {
            jSONObject.put("famous", a(arVar.E));
        }
        return jSONObject;
    }

    private static JSONObject a(ax axVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(axVar.a)) {
            jSONObject.put("data_type", axVar.a);
        }
        if (axVar.b != null && axVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (ay a : axVar.b) {
                jSONArray.put(a(a));
            }
            jSONObject.put("data", jSONArray);
        }
        if (axVar.c != null) {
            jSONObject.put("hide_log", new JSONObject(axVar.c));
        }
        return jSONObject;
    }

    private static JSONObject a(az azVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(azVar.a)) {
            jSONObject.put("text", azVar.a);
        }
        if (!TextUtils.isEmpty(azVar.b)) {
            jSONObject.put("width", azVar.b);
        }
        if (!TextUtils.isEmpty(azVar.c)) {
            jSONObject.put("rel_type", azVar.c);
        }
        if (!TextUtils.isEmpty(azVar.d)) {
            jSONObject.put("url", azVar.d);
        }
        if (azVar.e != null) {
            jSONObject.put("req_params", new JSONObject(azVar.e));
        }
        if (!TextUtils.isEmpty(azVar.f)) {
            jSONObject.put("type", azVar.f);
        }
        return jSONObject;
    }

    private static JSONObject a(ay ayVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(ayVar.a)) {
            jSONObject.put(ResUtils.STYLE, ayVar.a);
        }
        if (!TextUtils.isEmpty(ayVar.b)) {
            jSONObject.put("title", ayVar.b);
        }
        if (!TextUtils.isEmpty(ayVar.c)) {
            jSONObject.put("text", ayVar.c);
        }
        if (ayVar.d != null && ayVar.d.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (String put : ayVar.d) {
                jSONArray.put(put);
            }
            jSONObject.put("textlist", jSONArray);
        }
        if (ayVar.e != null && ayVar.e.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (ba baVar : ayVar.e) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(baVar.a)) {
                    jSONObject2.put("text1", baVar.a);
                }
                if (!TextUtils.isEmpty(baVar.b)) {
                    jSONObject2.put("text2", baVar.b);
                }
                if (!TextUtils.isEmpty(baVar.c)) {
                    jSONObject2.put("text3", baVar.c);
                }
                if (!TextUtils.isEmpty(baVar.d)) {
                    jSONObject2.put("color", baVar.d);
                }
                if (!TextUtils.isEmpty(baVar.e)) {
                    jSONObject2.put("text", baVar.e);
                }
                if (!TextUtils.isEmpty(baVar.f)) {
                    jSONObject2.put("doc", baVar.f);
                }
                if (!TextUtils.isEmpty(baVar.g)) {
                    jSONObject2.put("url", baVar.g);
                }
                if (!TextUtils.isEmpty(baVar.h)) {
                    jSONObject2.put("type", baVar.h);
                }
                jSONArray2.put(jSONObject2);
            }
            jSONObject.put("objectlist", jSONArray2);
        }
        if (!TextUtils.isEmpty(ayVar.f)) {
            jSONObject.put("color", ayVar.f);
        }
        if (!TextUtils.isEmpty(ayVar.g)) {
            jSONObject.put("type", ayVar.g);
        }
        if (!TextUtils.isEmpty(ayVar.h)) {
            jSONObject.put("source", ayVar.h);
        }
        if (!TextUtils.isEmpty(ayVar.i)) {
            jSONObject.put(H5Param.MENU_ICON, ayVar.i);
        }
        if (!TextUtils.isEmpty(ayVar.j)) {
            jSONObject.put("price_ori", ayVar.j);
        }
        if (!TextUtils.isEmpty(ayVar.k)) {
            jSONObject.put("price", ayVar.k);
        }
        if (!TextUtils.isEmpty(ayVar.l)) {
            jSONObject.put("doc1", ayVar.l);
        }
        if (!TextUtils.isEmpty(ayVar.m)) {
            jSONObject.put("doc2", ayVar.m);
        }
        if (!TextUtils.isEmpty(ayVar.n)) {
            jSONObject.put("jump", ayVar.n);
        }
        if (!TextUtils.isEmpty(ayVar.o)) {
            jSONObject.put("url", ayVar.o);
        }
        jSONObject.put("cols", ayVar.p);
        if (ayVar.q != null && ayVar.q.length > 0) {
            JSONArray jSONArray3 = new JSONArray();
            for (az a : ayVar.q) {
                jSONArray3.put(a(a));
            }
            jSONObject.put("datalist", jSONArray3);
        }
        if (ayVar.r != null) {
            jSONObject.put("req_params", new JSONObject(ayVar.r));
        }
        if (!TextUtils.isEmpty(ayVar.s)) {
            jSONObject.put("groupkey", ayVar.s);
        }
        jSONObject.put("isborder", ayVar.t);
        jSONObject.put("status", ayVar.u);
        if (!TextUtils.isEmpty(ayVar.v)) {
            jSONObject.put("height", ayVar.v);
        }
        if (!TextUtils.isEmpty(ayVar.w)) {
            jSONObject.put("padding", ayVar.w);
        }
        if (!TextUtils.isEmpty(ayVar.x)) {
            jSONObject.put("bgcolor", ayVar.x);
        }
        jSONObject.put("ishide", ayVar.y);
        jSONObject.put("line_type", ayVar.z);
        if (!TextUtils.isEmpty(ayVar.A)) {
            jSONObject.put("align", ayVar.A);
        }
        if (ayVar.B != null && ayVar.B.length > 0) {
            JSONArray jSONArray4 = new JSONArray();
            for (az a2 : ayVar.B) {
                jSONArray4.put(a(a2));
            }
            jSONObject.put("cells", jSONArray4);
        }
        if (!TextUtils.isEmpty(ayVar.C)) {
            jSONObject.put("group", ayVar.C);
        }
        if (!TextUtils.isEmpty(ayVar.D)) {
            jSONObject.put("doc", ayVar.D);
        }
        if (ayVar.E != null) {
            jSONObject.put("hide_log", new JSONObject(ayVar.E));
        }
        if (ayVar.F != null) {
            jSONObject.put("query_fields", new JSONObject(ayVar.F));
        }
        if (!TextUtils.isEmpty(ayVar.G)) {
            jSONObject.put("src_type", ayVar.G);
        }
        return jSONObject;
    }

    private static JSONObject a(au auVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(auVar.a)) {
            jSONObject.put("name", auVar.a);
        }
        if (!TextUtils.isEmpty(auVar.b)) {
            jSONObject.put("anchor", auVar.b);
        }
        if (auVar.c != null && auVar.c.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (au a : auVar.c) {
                jSONArray.put(a(a));
            }
            jSONObject.put("services", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(as asVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (asVar.a != null && asVar.a.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (at atVar : asVar.a) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(atVar.a)) {
                    jSONObject2.put("name", atVar.a);
                }
                if (!TextUtils.isEmpty(atVar.b)) {
                    jSONObject2.put("name_cn", atVar.b);
                }
                if (!TextUtils.isEmpty(atVar.c)) {
                    jSONObject2.put("tel", atVar.c);
                }
                if (!TextUtils.isEmpty(atVar.d)) {
                    jSONObject2.put(ModulePoi.TIPS, atVar.d);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("bar_items", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(av avVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (avVar.a != null) {
            jSONObject.put("t_tag", a(avVar.a));
        }
        if (avVar.b != null && avVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bc a : avVar.b) {
                jSONArray.put(a(a));
            }
            jSONObject.put("poitag", jSONArray);
        }
        if (!TextUtils.isEmpty(avVar.c)) {
            jSONObject.put("name", avVar.c);
        }
        if (!TextUtils.isEmpty(avVar.d)) {
            jSONObject.put("distance", avVar.d);
        }
        if (!TextUtils.isEmpty(avVar.e)) {
            jSONObject.put("address", avVar.e);
        }
        if (!TextUtils.isEmpty(avVar.f)) {
            jSONObject.put("star", avVar.f);
        }
        if (!TextUtils.isEmpty(avVar.g)) {
            jSONObject.put("price", avVar.g);
        }
        if (avVar.h != null && avVar.h.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (bb bbVar : avVar.h) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(bbVar.a)) {
                    jSONObject2.put(H5Param.MENU_ICON, bbVar.a);
                }
                if (!TextUtils.isEmpty(bbVar.b)) {
                    jSONObject2.put("type", bbVar.b);
                }
                jSONArray2.put(jSONObject2);
            }
            jSONObject.put("poi_logo", jSONArray2);
        }
        return jSONObject;
    }

    private static JSONObject a(bc bcVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bcVar.a)) {
            jSONObject.put("bg_color", bcVar.a);
        }
        if (!TextUtils.isEmpty(bcVar.b)) {
            jSONObject.put("text_color", bcVar.b);
        }
        if (!TextUtils.isEmpty(bcVar.c)) {
            jSONObject.put("value", bcVar.c);
        }
        return jSONObject;
    }

    private static JSONObject a(bf bfVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("pic_style", bfVar.a);
        jSONObject.put("upload_pic", bfVar.b);
        jSONObject.put("pic_count", bfVar.c);
        if (bfVar.d != null) {
            bg bgVar = bfVar.d;
            JSONObject jSONObject2 = new JSONObject();
            if (!TextUtils.isEmpty(bgVar.a)) {
                jSONObject2.put("url", bgVar.a);
            }
            jSONObject.put("cover", jSONObject2);
        }
        if (bfVar.e != null && bfVar.e.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bd bdVar : bfVar.e) {
                JSONObject jSONObject3 = new JSONObject();
                if (!TextUtils.isEmpty(bdVar.a)) {
                    jSONObject3.put("fetch_type", bdVar.a);
                }
                jSONObject3.put("srcheight", bdVar.b);
                if (!TextUtils.isEmpty(bdVar.c)) {
                    jSONObject3.put("title", bdVar.c);
                }
                if (!TextUtils.isEmpty(bdVar.d)) {
                    jSONObject3.put("url", bdVar.d);
                }
                if (!TextUtils.isEmpty(bdVar.e)) {
                    jSONObject3.put("text_sensibility", bdVar.e);
                }
                if (!TextUtils.isEmpty(bdVar.f)) {
                    jSONObject3.put(c.b, bdVar.f);
                }
                if (!TextUtils.isEmpty(bdVar.g)) {
                    jSONObject3.put("pic_id", bdVar.g);
                }
                jSONObject3.put("iscover", bdVar.h);
                jSONObject3.put("srcwidth", bdVar.i);
                if (bdVar.j != null) {
                    be beVar = bdVar.j;
                    JSONObject jSONObject4 = new JSONObject();
                    if (!TextUtils.isEmpty(beVar.a)) {
                        jSONObject4.put("environment", beVar.a);
                    }
                    if (!TextUtils.isEmpty(beVar.b)) {
                        jSONObject4.put("front", beVar.b);
                    }
                    if (!TextUtils.isEmpty(beVar.c)) {
                        jSONObject4.put("commodity", beVar.c);
                    }
                    jSONObject3.put("type_score", jSONObject4);
                }
                jSONArray.put(jSONObject3);
            }
            jSONObject.put("gallery", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(bh bhVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bhVar.a)) {
            jSONObject.put("title", bhVar.a);
        }
        if (!TextUtils.isEmpty(bhVar.b)) {
            jSONObject.put("url", bhVar.b);
        }
        jSONObject.put("iscover", bhVar.c);
        if (!TextUtils.isEmpty(bhVar.d)) {
            jSONObject.put("pic_id", bhVar.d);
        }
        if (!TextUtils.isEmpty(bhVar.e)) {
            jSONObject.put("fetch_type", bhVar.e);
        }
        if (!TextUtils.isEmpty(bhVar.f)) {
            jSONObject.put("src_type", bhVar.f);
        }
        return jSONObject;
    }

    private static JSONObject a(bi biVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (biVar.a != null && biVar.a.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bp bpVar : biVar.a) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(bpVar.a)) {
                    jSONObject2.put("id", bpVar.a);
                }
                if (!TextUtils.isEmpty(bpVar.b)) {
                    jSONObject2.put("name", bpVar.b);
                }
                if (!TextUtils.isEmpty(bpVar.c)) {
                    jSONObject2.put("length", bpVar.c);
                }
                if (!TextUtils.isEmpty(bpVar.d)) {
                    jSONObject2.put("score_movie", bpVar.d);
                }
                if (!TextUtils.isEmpty(bpVar.e)) {
                    jSONObject2.put("coverpic", bpVar.e);
                }
                if (!TextUtils.isEmpty(bpVar.f)) {
                    jSONObject2.put("releasedate", bpVar.f);
                }
                jSONObject2.put("sale_status", bpVar.g);
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("movies", jSONArray);
        }
        if (biVar.b != null) {
            bo boVar = biVar.b;
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("have_tmall", boVar.a);
            jSONObject3.put("have_meetbooking", boVar.b);
            jSONObject3.put("have_trade", boVar.c);
            jSONObject3.put("have_media", boVar.d);
            jSONObject.put("module_switch", jSONObject3);
        }
        if (!TextUtils.isEmpty(biVar.c)) {
            jSONObject.put("isHaveHotel", biVar.c);
        }
        if (!TextUtils.isEmpty(biVar.d)) {
            jSONObject.put("hotel_price_lowest", biVar.d);
        }
        jSONObject.put("is_queue", biVar.e);
        jSONObject.put("review_count", biVar.f);
        if (!TextUtils.isEmpty(biVar.g)) {
            jSONObject.put("review_summary", biVar.g);
        }
        if (biVar.h != null && biVar.h.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (String put : biVar.h) {
                jSONArray2.put(put);
            }
            jSONObject.put("review_labels", jSONArray2);
        }
        jSONObject.put("review_entrance", biVar.i);
        if (!TextUtils.isEmpty(biVar.j)) {
            jSONObject.put("first_review_banner", biVar.j);
        }
        if (biVar.k != null && biVar.k.length > 0) {
            JSONArray jSONArray3 = new JSONArray();
            for (bn a : biVar.k) {
                jSONArray3.put(a(a));
            }
            jSONObject.put("gasstation", jSONArray3);
        }
        if (biVar.l != null && biVar.l.length > 0) {
            JSONArray jSONArray4 = new JSONArray();
            for (bq bqVar : biVar.l) {
                JSONObject jSONObject4 = new JSONObject();
                if (!TextUtils.isEmpty(bqVar.a)) {
                    jSONObject4.put("src_type", bqVar.a);
                }
                if (!TextUtils.isEmpty(bqVar.b)) {
                    jSONObject4.put("num_space_s", bqVar.b);
                }
                if (!TextUtils.isEmpty(bqVar.c)) {
                    jSONObject4.put("num_space_f", bqVar.c);
                }
                if (!TextUtils.isEmpty(bqVar.d)) {
                    jSONObject4.put("parkcolor", bqVar.d);
                }
                if (!TextUtils.isEmpty(bqVar.e)) {
                    jSONObject4.put("market", bqVar.e);
                }
                jSONArray4.put(jSONObject4);
            }
            jSONObject.put("parking", jSONArray4);
        }
        if (biVar.m != null) {
            bt btVar = biVar.m;
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put("is_txt_tts", btVar.a);
            if (!TextUtils.isEmpty(btVar.b)) {
                jSONObject5.put("hand_pic_url", btVar.b);
            }
            if (!TextUtils.isEmpty(btVar.c)) {
                jSONObject5.put("show_name", btVar.c);
            }
            if (btVar.d != null) {
                bj bjVar = btVar.d;
                JSONObject jSONObject6 = new JSONObject();
                if (!TextUtils.isEmpty(bjVar.a)) {
                    jSONObject6.put("android_appurl", bjVar.a);
                }
                if (!TextUtils.isEmpty(bjVar.b)) {
                    jSONObject6.put("ios_appurl", bjVar.b);
                }
                jSONObject5.put("third_url", jSONObject6);
            }
            jSONObject.put("scenic_guide", jSONObject5);
        }
        if (biVar.n != null && biVar.n.length > 0) {
            JSONArray jSONArray5 = new JSONArray();
            for (bu buVar : biVar.n) {
                JSONObject jSONObject7 = new JSONObject();
                jSONObject7.put(NewHtcHomeBadger.COUNT, buVar.a);
                if (!TextUtils.isEmpty(buVar.b)) {
                    jSONObject7.put("name", buVar.b);
                }
                jSONArray5.put(jSONObject7);
            }
            jSONObject.put("shopping_guide", jSONArray5);
        }
        if (biVar.o != null && biVar.o.length > 0) {
            JSONArray jSONArray6 = new JSONArray();
            for (bm a2 : biVar.o) {
                jSONArray6.put(a(a2));
            }
            jSONObject.put("floor_guide", jSONArray6);
        }
        if (biVar.p != null) {
            bk bkVar = biVar.p;
            JSONObject jSONObject8 = new JSONObject();
            jSONObject8.put("total", bkVar.a);
            if (!TextUtils.isEmpty(bkVar.b)) {
                jSONObject8.put("brand_code", bkVar.b);
            }
            if (bkVar.c != null) {
                bl blVar = bkVar.c;
                JSONObject jSONObject9 = new JSONObject();
                if (!TextUtils.isEmpty(blVar.a)) {
                    jSONObject9.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, blVar.a);
                }
                if (!TextUtils.isEmpty(blVar.b)) {
                    jSONObject9.put("name", blVar.b);
                }
                if (!TextUtils.isEmpty(blVar.c)) {
                    jSONObject9.put("distance", blVar.c);
                }
                jSONObject8.put("nearest_store", jSONObject9);
            }
            jSONObject.put("brand_info", jSONObject8);
        }
        jSONObject.put("has_discount_flag", biVar.q);
        return jSONObject;
    }

    private static JSONObject a(br brVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(brVar.a)) {
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, brVar.a);
        }
        if (!TextUtils.isEmpty(brVar.b)) {
            jSONObject.put("name", brVar.b);
        }
        if (!TextUtils.isEmpty(brVar.c)) {
            jSONObject.put(DictionaryKeys.CTRLXY_X, brVar.c);
        }
        if (!TextUtils.isEmpty(brVar.d)) {
            jSONObject.put(DictionaryKeys.CTRLXY_Y, brVar.d);
        }
        if (!TextUtils.isEmpty(brVar.e)) {
            jSONObject.put("address", brVar.e);
        }
        if (!TextUtils.isEmpty(brVar.f)) {
            jSONObject.put("type", brVar.f);
        }
        if (!TextUtils.isEmpty(brVar.g)) {
            jSONObject.put("new_type", brVar.g);
        }
        if (!TextUtils.isEmpty(brVar.h)) {
            jSONObject.put("pixelx", brVar.h);
        }
        if (!TextUtils.isEmpty(brVar.i)) {
            jSONObject.put("pixely", brVar.i);
        }
        if (!TextUtils.isEmpty(brVar.j)) {
            jSONObject.put("category", brVar.j);
        }
        if (!TextUtils.isEmpty(brVar.k)) {
            jSONObject.put("brand_logo", brVar.k);
        }
        return jSONObject;
    }

    private static JSONObject a(bm bmVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bmVar.a)) {
            jSONObject.put("fl_no", bmVar.a);
        }
        if (!TextUtils.isEmpty(bmVar.b)) {
            jSONObject.put("name", bmVar.b);
        }
        if (!TextUtils.isEmpty(bmVar.c)) {
            jSONObject.put("main_type", bmVar.c);
        }
        if (bmVar.d != null && bmVar.d.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (br a : bmVar.d) {
                jSONArray.put(a(a));
            }
            jSONObject.put("poilist", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(bn bnVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bnVar.a)) {
            jSONObject.put("src_type", bnVar.a);
        }
        if (bnVar.b != null && bnVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bs bsVar : bnVar.b) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(bsVar.a)) {
                    jSONObject2.put("gastype", bsVar.a);
                }
                if (!TextUtils.isEmpty(bsVar.b)) {
                    jSONObject2.put("price", bsVar.b);
                }
                if (!TextUtils.isEmpty(bsVar.c)) {
                    jSONObject2.put("oilprices", bsVar.c);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("price_list", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(defpackage.c.d dVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(dVar.a)) {
            jSONObject.put("brief_intro", dVar.a);
        }
        if (!TextUtils.isEmpty(dVar.b)) {
            jSONObject.put(Constants.KEY_AUDIO_BUSINESS_ID, dVar.b);
        }
        if (!TextUtils.isEmpty(dVar.c)) {
            jSONObject.put("info_wapurl", dVar.c);
        }
        if (!TextUtils.isEmpty(dVar.d)) {
            jSONObject.put("info_weburl", dVar.d);
        }
        if (!TextUtils.isEmpty(dVar.e)) {
            jSONObject.put("intro", dVar.e);
        }
        if (dVar.f != null && dVar.f.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (String put : dVar.f) {
                jSONArray.put(put);
            }
            jSONObject.put("keys", jSONArray);
        }
        if (!TextUtils.isEmpty(dVar.g)) {
            jSONObject.put("src_star", dVar.g);
        }
        if (!TextUtils.isEmpty(dVar.h)) {
            jSONObject.put("detail_facilities", dVar.h);
        }
        if (dVar.i != null && dVar.i.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (n nVar : dVar.i) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(nVar.a)) {
                    jSONObject2.put("fac_type", nVar.a);
                }
                if (!TextUtils.isEmpty(nVar.b)) {
                    jSONObject2.put("big_icon", nVar.b);
                }
                if (!TextUtils.isEmpty(nVar.c)) {
                    jSONObject2.put("little_icon", nVar.c);
                }
                jSONArray2.put(jSONObject2);
            }
            jSONObject.put("hotel_facilities", jSONArray2);
        }
        if (dVar.j != null) {
            jSONObject.put("index_dining", a(dVar.j));
        }
        if (dVar.k != null) {
            jSONObject.put("index_trans", a(dVar.k));
        }
        if (!TextUtils.isEmpty(dVar.l)) {
            jSONObject.put("openyear", dVar.l);
        }
        if (!TextUtils.isEmpty(dVar.m)) {
            jSONObject.put("reno_date", dVar.m);
        }
        if (!TextUtils.isEmpty(dVar.n)) {
            jSONObject.put("service", dVar.n);
        }
        if (!TextUtils.isEmpty(dVar.o)) {
            jSONObject.put("star", dVar.o);
        }
        if (!TextUtils.isEmpty(dVar.p)) {
            jSONObject.put("club_id", dVar.p);
        }
        if (!TextUtils.isEmpty(dVar.q)) {
            jSONObject.put("club_name", dVar.q);
        }
        jSONObject.put("price_lowest", dVar.r);
        if (!TextUtils.isEmpty(dVar.s)) {
            jSONObject.put("club_facility", dVar.s);
        }
        if (!TextUtils.isEmpty(dVar.t)) {
            jSONObject.put("club_logo", dVar.t);
        }
        if (dVar.u != null && dVar.u.length > 0) {
            JSONArray jSONArray3 = new JSONArray();
            for (m a : dVar.u) {
                jSONArray3.put(a(a));
            }
            jSONObject.put("course", jSONArray3);
        }
        if (!TextUtils.isEmpty(dVar.v)) {
            jSONObject.put("tag_category", dVar.v);
        }
        if (!TextUtils.isEmpty(dVar.w)) {
            jSONObject.put("tag_atmosphere", dVar.w);
        }
        jSONObject.put("dining_park", dVar.x);
        if (dVar.y != null) {
            k kVar = dVar.y;
            JSONObject jSONObject3 = new JSONObject();
            if (!TextUtils.isEmpty(kVar.a)) {
                jSONObject3.put("title", kVar.a);
            }
            if (!TextUtils.isEmpty(kVar.b)) {
                jSONObject3.put("url", kVar.b);
            }
            if (!TextUtils.isEmpty(kVar.c)) {
                jSONObject3.put(ModuleFeedBack.RECOMMEND, kVar.c);
            }
            jSONObject.put("highlight", jSONObject3);
        }
        jSONObject.put("is_24h", dVar.z);
        jSONObject.put("is_open", dVar.A);
        if (!TextUtils.isEmpty(dVar.B)) {
            jSONObject.put("recom_type", dVar.B);
        }
        if (dVar.C != null && dVar.C.length > 0) {
            JSONArray jSONArray4 = new JSONArray();
            for (l lVar : dVar.C) {
                JSONObject jSONObject4 = new JSONObject();
                if (!TextUtils.isEmpty(lVar.a)) {
                    jSONObject4.put("famous_content", lVar.a);
                }
                if (!TextUtils.isEmpty(lVar.b)) {
                    jSONObject4.put("famous_url", lVar.b);
                }
                if (!TextUtils.isEmpty(lVar.c)) {
                    jSONObject4.put("media_content", lVar.c);
                }
                if (!TextUtils.isEmpty(lVar.d)) {
                    jSONObject4.put("media_url", lVar.d);
                }
                jSONArray4.put(jSONObject4);
            }
            jSONObject.put("recom_content", jSONArray4);
        }
        if (!TextUtils.isEmpty(dVar.D)) {
            jSONObject.put("tag_special", dVar.D);
        }
        jSONObject.put("tag_special_count", dVar.E);
        jSONObject.put("menu_count", dVar.F);
        if (dVar.G != null && dVar.G.length > 0) {
            JSONArray jSONArray5 = new JSONArray();
            for (ad adVar : dVar.G) {
                JSONObject jSONObject5 = new JSONObject();
                if (!TextUtils.isEmpty(adVar.a)) {
                    jSONObject5.put("name", adVar.a);
                }
                if (!TextUtils.isEmpty(adVar.b)) {
                    jSONObject5.put("pic_cover", adVar.b);
                }
                jSONArray5.put(jSONObject5);
            }
            jSONObject.put("menu_pic", jSONArray5);
        }
        if (!TextUtils.isEmpty(dVar.H)) {
            jSONObject.put("opentime2", dVar.H);
        }
        if (!TextUtils.isEmpty(dVar.I)) {
            jSONObject.put("notice", dVar.I);
        }
        if (dVar.J != null && dVar.J.length > 0) {
            JSONArray jSONArray6 = new JSONArray();
            for (g gVar : dVar.J) {
                JSONObject jSONObject6 = new JSONObject();
                if (!TextUtils.isEmpty(gVar.a)) {
                    jSONObject6.put("coupon_name", gVar.a);
                }
                if (!TextUtils.isEmpty(gVar.b)) {
                    jSONObject6.put("coupon_price", gVar.b);
                }
                if (!TextUtils.isEmpty(gVar.c)) {
                    jSONObject6.put("coupon_url", gVar.c);
                }
                jSONArray6.put(jSONObject6);
            }
            jSONObject.put(CouponDao.TABLENAME, jSONArray6);
        }
        if (dVar.K != null && dVar.K.length > 0) {
            JSONArray jSONArray7 = new JSONArray();
            for (h hVar : dVar.K) {
                JSONObject jSONObject7 = new JSONObject();
                if (!TextUtils.isEmpty(hVar.a)) {
                    jSONObject7.put("code", hVar.a);
                }
                if (!TextUtils.isEmpty(hVar.b)) {
                    jSONObject7.put("showname", hVar.b);
                }
                if (!TextUtils.isEmpty(hVar.c)) {
                    jSONObject7.put("desc", hVar.c);
                }
                jSONObject7.put("support", hVar.d);
                jSONArray7.put(jSONObject7);
            }
            jSONObject.put("cinema_info", jSONArray7);
        }
        if (!TextUtils.isEmpty(dVar.L)) {
            jSONObject.put(H5PermissionManager.level, dVar.L);
        }
        if (!TextUtils.isEmpty(dVar.M)) {
            jSONObject.put("special", dVar.M);
        }
        if (!TextUtils.isEmpty(dVar.N)) {
            jSONObject.put("s_duration", dVar.N);
        }
        if (!TextUtils.isEmpty(dVar.O)) {
            jSONObject.put("visit_month", dVar.O);
        }
        if (!TextUtils.isEmpty(dVar.P)) {
            jSONObject.put("heat_map", dVar.P);
        }
        if (!TextUtils.isEmpty(dVar.Q)) {
            jSONObject.put("shenma_weather_name", dVar.Q);
        }
        jSONObject.put("have_in_spots", dVar.R);
        jSONObject.put("have_rec_food", dVar.S);
        if (dVar.T != null) {
            jSONObject.put("travels_info", a(dVar.T));
        }
        if (!TextUtils.isEmpty(dVar.U)) {
            jSONObject.put("first_travel_line", dVar.U);
        }
        if (dVar.V != null && dVar.V.length > 0) {
            JSONArray jSONArray8 = new JSONArray();
            for (b bVar : dVar.V) {
                JSONObject jSONObject8 = new JSONObject();
                if (!TextUtils.isEmpty(bVar.a)) {
                    jSONObject8.put("airways", bVar.a);
                }
                if (!TextUtils.isEmpty(bVar.b)) {
                    jSONObject8.put("cuss", bVar.b);
                }
                if (!TextUtils.isEmpty(bVar.c)) {
                    jSONObject8.put("logo_id", bVar.c);
                }
                if (!TextUtils.isEmpty(bVar.d)) {
                    jSONObject8.put("air_tel", bVar.d);
                }
                if (!TextUtils.isEmpty(bVar.e)) {
                    jSONObject8.put("logo_url", bVar.e);
                }
                jSONArray8.put(jSONObject8);
            }
            jSONObject.put("air_info", jSONArray8);
        }
        jSONObject.put("air_total", dVar.W);
        if (dVar.X != null) {
            jSONObject.put("line_type_info", a(dVar.X));
        }
        if (!TextUtils.isEmpty(dVar.Y)) {
            jSONObject.put("cscf", dVar.Y);
        }
        jSONObject.put("num_fast", dVar.Z);
        jSONObject.put("num_slow", dVar.aa);
        if (!TextUtils.isEmpty(dVar.ab)) {
            jSONObject.put("pay_type", dVar.ab);
        }
        if (!TextUtils.isEmpty(dVar.ac)) {
            jSONObject.put("charge_src_name", dVar.ac);
        }
        if (!TextUtils.isEmpty(dVar.ad)) {
            jSONObject.put("national_standard", dVar.ad);
        }
        if (!TextUtils.isEmpty(dVar.ae)) {
            jSONObject.put("order_url", dVar.ae);
        }
        jSONObject.put("is_depart", dVar.af);
        if (dVar.ag != null && dVar.ag.length > 0) {
            JSONArray jSONArray9 = new JSONArray();
            for (String put2 : dVar.ag) {
                jSONArray9.put(put2);
            }
            jSONObject.put("depart_name", jSONArray9);
        }
        if (!TextUtils.isEmpty(dVar.ah)) {
            jSONObject.put("nature", dVar.ah);
        }
        if (dVar.ai != null) {
            jSONObject.put("works", a(dVar.ai));
        }
        if (dVar.aj != null) {
            jSONObject.put("environment", a(dVar.aj));
        }
        if (dVar.ak != null) {
            aa aaVar = dVar.ak;
            JSONObject jSONObject9 = new JSONObject();
            if (!TextUtils.isEmpty(aaVar.a)) {
                jSONObject9.put("enro_num", aaVar.a);
            }
            if (!TextUtils.isEmpty(aaVar.b)) {
                jSONObject9.put("class_num", aaVar.b);
            }
            if (!TextUtils.isEmpty(aaVar.c)) {
                jSONObject9.put("care_fee", aaVar.c);
            }
            if (!TextUtils.isEmpty(aaVar.d)) {
                jSONObject9.put("meals", aaVar.d);
            }
            if (!TextUtils.isEmpty(aaVar.e)) {
                jSONObject9.put("enro_way", aaVar.e);
            }
            if (!TextUtils.isEmpty(aaVar.f)) {
                jSONObject9.put("fee_desc", aaVar.f);
            }
            if (!TextUtils.isEmpty(aaVar.g)) {
                jSONObject9.put("notes", aaVar.g);
            }
            jSONObject.put("enrollment", jSONObject9);
        }
        if (dVar.al != null && dVar.al.length > 0) {
            JSONArray jSONArray10 = new JSONArray();
            for (ac acVar : dVar.al) {
                JSONObject jSONObject10 = new JSONObject();
                jSONObject10.put("spe_code", acVar.a);
                if (!TextUtils.isEmpty(acVar.b)) {
                    jSONObject10.put("spe_desc", acVar.b);
                }
                jSONArray10.put(jSONObject10);
            }
            jSONObject.put("kg_tag_special", jSONArray10);
        }
        if (!TextUtils.isEmpty(dVar.am)) {
            jSONObject.put("motto", dVar.am);
        }
        if (!TextUtils.isEmpty(dVar.an)) {
            jSONObject.put("badge_pic", dVar.an);
        }
        if (!TextUtils.isEmpty(dVar.ao)) {
            jSONObject.put("tag_class", dVar.ao);
        }
        if (!TextUtils.isEmpty(dVar.ap)) {
            jSONObject.put("property_tel", dVar.ap);
        }
        if (!TextUtils.isEmpty(dVar.aq)) {
            jSONObject.put("green_rate", dVar.aq);
        }
        if (!TextUtils.isEmpty(dVar.ar)) {
            jSONObject.put("volume_rate", dVar.ar);
        }
        if (!TextUtils.isEmpty(dVar.as)) {
            jSONObject.put("service_parking", dVar.as);
        }
        if (!TextUtils.isEmpty(dVar.at)) {
            jSONObject.put("property_fee", dVar.at);
        }
        if (!TextUtils.isEmpty(dVar.au)) {
            jSONObject.put("land_year", dVar.au);
        }
        if (!TextUtils.isEmpty(dVar.av)) {
            jSONObject.put("area_total", dVar.av);
        }
        jSONObject.put("sch_dis_pri", dVar.aw);
        jSONObject.put("sch_dis_mid", dVar.ax);
        jSONObject.put("is_community", dVar.ay);
        jSONObject.put("is_news", dVar.az);
        jSONObject.put("have_sch_district", dVar.aA);
        if (dVar.aB != null && dVar.aB.length > 0) {
            JSONArray jSONArray11 = new JSONArray();
            for (ae aeVar : dVar.aB) {
                JSONObject jSONObject11 = new JSONObject();
                if (!TextUtils.isEmpty(aeVar.a)) {
                    jSONObject11.put("comm_type", aeVar.a);
                }
                if (!TextUtils.isEmpty(aeVar.b)) {
                    jSONObject11.put("name", aeVar.b);
                }
                if (!TextUtils.isEmpty(aeVar.c)) {
                    jSONObject11.put("address", aeVar.c);
                }
                if (!TextUtils.isEmpty(aeVar.d)) {
                    jSONObject11.put("tel", aeVar.d);
                }
                if (!TextUtils.isEmpty(aeVar.e)) {
                    jSONObject11.put("community", aeVar.e);
                }
                jSONArray11.put(jSONObject11);
            }
            jSONObject.put("comm_info", jSONArray11);
        }
        if (dVar.aC != null) {
            jSONObject.put("index_life", a(dVar.aC));
        }
        if (dVar.aD != null) {
            jSONObject.put("index_edu", a(dVar.aD));
        }
        if (!TextUtils.isEmpty(dVar.aE)) {
            jSONObject.put("tele400_read", dVar.aE);
        }
        if (!TextUtils.isEmpty(dVar.aF)) {
            jSONObject.put("auth_type", dVar.aF);
        }
        if (!TextUtils.isEmpty(dVar.aG)) {
            jSONObject.put("auth_time", dVar.aG);
        }
        if (!TextUtils.isEmpty(dVar.aH)) {
            jSONObject.put("scope", dVar.aH);
        }
        if (!TextUtils.isEmpty(dVar.aI)) {
            jSONObject.put("main_industry", dVar.aI);
        }
        if (!TextUtils.isEmpty(dVar.aJ)) {
            jSONObject.put("regist_name", dVar.aJ);
        }
        if (!TextUtils.isEmpty(dVar.aK)) {
            jSONObject.put("regist_addr", dVar.aK);
        }
        if (!TextUtils.isEmpty(dVar.aL)) {
            jSONObject.put("regist_no", dVar.aL);
        }
        if (!TextUtils.isEmpty(dVar.aM)) {
            jSONObject.put("regist_cap", dVar.aM);
        }
        if (!TextUtils.isEmpty(dVar.aN)) {
            jSONObject.put("fouding_time", dVar.aN);
        }
        if (!TextUtils.isEmpty(dVar.aO)) {
            jSONObject.put("legal_rep", dVar.aO);
        }
        if (!TextUtils.isEmpty(dVar.aP)) {
            jSONObject.put("business_per_beg", dVar.aP);
        }
        if (!TextUtils.isEmpty(dVar.aQ)) {
            jSONObject.put("business_per_end", dVar.aQ);
        }
        jSONObject.put("cxt_per", dVar.aR);
        if (!TextUtils.isEmpty(dVar.aS)) {
            jSONObject.put("intro_wapurl", dVar.aS);
        }
        if (!TextUtils.isEmpty(dVar.aT)) {
            jSONObject.put("store_wapurl", dVar.aT);
        }
        if (dVar.aU != null && dVar.aU.length > 0) {
            JSONArray jSONArray12 = new JSONArray();
            for (bh a2 : dVar.aU) {
                jSONArray12.put(a(a2));
            }
            jSONObject.put("pic_info", jSONArray12);
        }
        if (!TextUtils.isEmpty(dVar.aV)) {
            jSONObject.put("head_count", dVar.aV);
        }
        if (!TextUtils.isEmpty(dVar.aW)) {
            jSONObject.put("turnover", dVar.aW);
        }
        if (!TextUtils.isEmpty(dVar.aX)) {
            jSONObject.put("area", dVar.aX);
        }
        if (!TextUtils.isEmpty(dVar.aY)) {
            jSONObject.put("sales_area", dVar.aY);
        }
        if (!TextUtils.isEmpty(dVar.aZ)) {
            jSONObject.put("dingding_wapurl", dVar.aZ);
        }
        if (!TextUtils.isEmpty(dVar.ba)) {
            jSONObject.put("b_sphere", dVar.ba);
        }
        if (!TextUtils.isEmpty(dVar.bb)) {
            jSONObject.put("price", dVar.bb);
        }
        if (!TextUtils.isEmpty(dVar.bc)) {
            jSONObject.put("date", dVar.bc);
        }
        if (!TextUtils.isEmpty(dVar.bd)) {
            jSONObject.put(SampleConfigConstant.TAG_ROOT, dVar.bd);
        }
        jSONObject.put(WidgetType.SCALE, dVar.be);
        jSONObject.put("is_pri_bar", dVar.bf);
        if (!TextUtils.isEmpty(dVar.bg)) {
            jSONObject.put("graphics", dVar.bg);
        }
        if (!TextUtils.isEmpty(dVar.bh)) {
            jSONObject.put("display", dVar.bh);
        }
        if (!TextUtils.isEmpty(dVar.bi)) {
            jSONObject.put("memory", dVar.bi);
        }
        if (!TextUtils.isEmpty(dVar.bj)) {
            jSONObject.put("cpu", dVar.bj);
        }
        if (dVar.bk != null && dVar.bk.length > 0) {
            JSONArray jSONArray13 = new JSONArray();
            for (y a3 : dVar.bk) {
                jSONArray13.put(a(a3));
            }
            jSONObject.put("pri_info", jSONArray13);
        }
        jSONObject.put("have_plan_res", dVar.bl);
        if (!TextUtils.isEmpty(dVar.bm)) {
            jSONObject.put("tag_property", dVar.bm);
        }
        if (!TextUtils.isEmpty(dVar.bn)) {
            jSONObject.put("students_comp", dVar.bn);
        }
        if (!TextUtils.isEmpty(dVar.bo)) {
            jSONObject.put("num_limitation", dVar.bo);
        }
        if (!TextUtils.isEmpty(dVar.bp)) {
            jSONObject.put("enroll_scale", dVar.bp);
        }
        if (!TextUtils.isEmpty(dVar.bq)) {
            jSONObject.put("entrance_order_pic", dVar.bq);
        }
        if (dVar.br != null) {
            ai aiVar = dVar.br;
            JSONObject jSONObject12 = new JSONObject();
            if (!TextUtils.isEmpty(aiVar.a)) {
                jSONObject12.put("title", aiVar.a);
            }
            if (!TextUtils.isEmpty(aiVar.b)) {
                jSONObject12.put("content", aiVar.b);
            }
            jSONObject.put("enroll_rules", jSONObject12);
        }
        if (dVar.bs != null && dVar.bs.length > 0) {
            JSONArray jSONArray14 = new JSONArray();
            for (aj ajVar : dVar.bs) {
                JSONObject jSONObject13 = new JSONObject();
                if (!TextUtils.isEmpty(ajVar.a)) {
                    jSONObject13.put("type", ajVar.a);
                }
                if (!TextUtils.isEmpty(ajVar.b)) {
                    jSONObject13.put("details", ajVar.b);
                }
                jSONArray14.put(jSONObject13);
            }
            jSONObject.put("entrance_info", jSONArray14);
        }
        if (dVar.bt != null) {
            al alVar = dVar.bt;
            JSONObject jSONObject14 = new JSONObject();
            if (!TextUtils.isEmpty(alVar.a)) {
                jSONObject14.put("hd_level", alVar.a);
            }
            if (!TextUtils.isEmpty(alVar.b)) {
                jSONObject14.put("history", alVar.b);
            }
            if (!TextUtils.isEmpty(alVar.c)) {
                jSONObject14.put("teachers", alVar.c);
            }
            if (!TextUtils.isEmpty(alVar.d)) {
                jSONObject14.put("areas", alVar.d);
            }
            if (!TextUtils.isEmpty(alVar.e)) {
                jSONObject14.put("food_provide", alVar.e);
            }
            jSONObject.put("facilities", jSONObject14);
        }
        if (dVar.bu != null) {
            ak akVar = dVar.bu;
            JSONObject jSONObject15 = new JSONObject();
            if (!TextUtils.isEmpty(akVar.a)) {
                jSONObject15.put("extra_act", akVar.a);
            }
            if (!TextUtils.isEmpty(akVar.b)) {
                jSONObject15.put("speciality", akVar.b);
            }
            jSONObject.put("extra_curri", jSONObject15);
        }
        if (!TextUtils.isEmpty(dVar.bv)) {
            jSONObject.put("charge", dVar.bv);
        }
        if (!TextUtils.isEmpty(dVar.bw)) {
            jSONObject.put("num_space", dVar.bw);
        }
        if (!TextUtils.isEmpty(dVar.bx)) {
            jSONObject.put("prc_c_d_e", dVar.bx);
        }
        if (!TextUtils.isEmpty(dVar.by)) {
            jSONObject.put("prc_c_n_e", dVar.by);
        }
        if (!TextUtils.isEmpty(dVar.bz)) {
            jSONObject.put("prc_c_wm", dVar.bz);
        }
        if (!TextUtils.isEmpty(dVar.bA)) {
            jSONObject.put("prc_t_d_e", dVar.bA);
        }
        if (!TextUtils.isEmpty(dVar.bB)) {
            jSONObject.put("prc_t_n_e", dVar.bB);
        }
        if (!TextUtils.isEmpty(dVar.bC)) {
            jSONObject.put("prc_t_wm", dVar.bC);
        }
        if (!TextUtils.isEmpty(dVar.bD)) {
            jSONObject.put("remark", dVar.bD);
        }
        if (!TextUtils.isEmpty(dVar.bE)) {
            jSONObject.put("info_src_name", dVar.bE);
        }
        jSONObject.put("show_old_fee_scale", dVar.bF);
        if (dVar.bG != null) {
            jSONObject.put("train_station_nearby", a(dVar.bG));
        }
        if (dVar.bH != null && dVar.bH.length > 0) {
            JSONArray jSONArray15 = new JSONArray();
            for (String put3 : dVar.bH) {
                jSONArray15.put(put3);
            }
            jSONObject.put("train_station_facilities", jSONArray15);
        }
        if (!TextUtils.isEmpty(dVar.bI)) {
            jSONObject.put("city_pic", dVar.bI);
        }
        if (dVar.bJ != null && dVar.bJ.length > 0) {
            JSONArray jSONArray16 = new JSONArray();
            for (am a4 : dVar.bJ) {
                jSONArray16.put(a(a4));
            }
            jSONObject.put("entrances_landmarks", jSONArray16);
        }
        if (!TextUtils.isEmpty(dVar.bK)) {
            jSONObject.put("src_id", dVar.bK);
        }
        if (!TextUtils.isEmpty(dVar.bL)) {
            jSONObject.put("src_star_desc", dVar.bL);
        }
        if (!TextUtils.isEmpty(dVar.bM)) {
            jSONObject.put("src_type", dVar.bM);
        }
        if (!TextUtils.isEmpty(dVar.bN)) {
            jSONObject.put("src_type_mix", dVar.bN);
        }
        if (!TextUtils.isEmpty(dVar.bO)) {
            jSONObject.put("company", dVar.bO);
        }
        if (!TextUtils.isEmpty(dVar.bP)) {
            jSONObject.put("brand_desc", dVar.bP);
        }
        if (!TextUtils.isEmpty(dVar.bQ)) {
            jSONObject.put("serviceitem", dVar.bQ);
        }
        if (!TextUtils.isEmpty(dVar.bR)) {
            jSONObject.put("ctcc_url", dVar.bR);
        }
        if (!TextUtils.isEmpty(dVar.bS)) {
            jSONObject.put("appointment", dVar.bS);
        }
        if (!TextUtils.isEmpty(dVar.bT)) {
            jSONObject.put("link_title", dVar.bT);
        }
        if (!TextUtils.isEmpty(dVar.bU)) {
            jSONObject.put("link_url", dVar.bU);
        }
        return jSONObject;
    }

    private static JSONObject a(am amVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (amVar.a != null && amVar.a.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (br a : amVar.a) {
                jSONArray.put(a(a));
            }
            jSONObject.put("landmarks", jSONArray);
        }
        if (amVar.b != null && amVar.b.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (an anVar : amVar.b) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(anVar.a)) {
                    jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, anVar.a);
                }
                if (!TextUtils.isEmpty(anVar.b)) {
                    jSONObject2.put("name", anVar.b);
                }
                if (!TextUtils.isEmpty(anVar.c)) {
                    jSONObject2.put("businfo_line_keys", anVar.c);
                }
                jSONObject2.put("distance", (double) anVar.d);
                if (!TextUtils.isEmpty(anVar.e)) {
                    jSONObject2.put(DictionaryKeys.CTRLXY_X, anVar.e);
                }
                if (!TextUtils.isEmpty(anVar.f)) {
                    jSONObject2.put(DictionaryKeys.CTRLXY_Y, anVar.f);
                }
                jSONArray2.put(jSONObject2);
            }
            jSONObject.put("landmark_bus_stations", jSONArray2);
        }
        if (!TextUtils.isEmpty(amVar.c)) {
            jSONObject.put("short_name", amVar.c);
        }
        return jSONObject;
    }

    private static JSONObject a(ao aoVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(aoVar.a)) {
            jSONObject.put("id", aoVar.a);
        }
        if (!TextUtils.isEmpty(aoVar.b)) {
            jSONObject.put("lines", aoVar.b);
        }
        if (!TextUtils.isEmpty(aoVar.c)) {
            jSONObject.put("colors", aoVar.c);
        }
        if (!TextUtils.isEmpty(aoVar.d)) {
            jSONObject.put("name", aoVar.d);
        }
        if (!TextUtils.isEmpty(aoVar.e)) {
            jSONObject.put("entrance_name", aoVar.e);
        }
        if (!TextUtils.isEmpty(aoVar.f)) {
            jSONObject.put(DictionaryKeys.CTRLXY_X, aoVar.f);
        }
        if (!TextUtils.isEmpty(aoVar.g)) {
            jSONObject.put(DictionaryKeys.CTRLXY_Y, aoVar.g);
        }
        return jSONObject;
    }

    private static JSONObject a(ap apVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (apVar.a != null && apVar.a.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (ao a : apVar.a) {
                jSONArray.put(a(a));
            }
            jSONObject.put("subway_stations", jSONArray);
        }
        if (apVar.b != null && apVar.b.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (ao a2 : apVar.b) {
                jSONArray2.put(a(a2));
            }
            jSONObject.put("bus_stations", jSONArray2);
        }
        if (apVar.c != null && apVar.c.length > 0) {
            JSONArray jSONArray3 = new JSONArray();
            for (ao a3 : apVar.c) {
                jSONArray3.put(a(a3));
            }
            jSONObject.put("taxi_stations", jSONArray3);
        }
        return jSONObject;
    }

    private static JSONObject a(z zVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(zVar.a)) {
            jSONObject.put("title", zVar.a);
        }
        if (!TextUtils.isEmpty(zVar.b)) {
            jSONObject.put("content", zVar.b);
        }
        if (zVar.c != null && zVar.c.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bh a : zVar.c) {
                jSONArray.put(a(a));
            }
            jSONObject.put("pic_info", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(y yVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(yVar.a)) {
            jSONObject.put("pri_type", yVar.a);
        }
        if (yVar.b != null && yVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (z a : yVar.b) {
                jSONArray.put(a(a));
            }
            jSONObject.put("pri_info_detail", jSONArray);
        }
        if (yVar.c != null && yVar.c.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (bh a2 : yVar.c) {
                jSONArray2.put(a(a2));
            }
            jSONObject.put("pic_info", jSONArray2);
        }
        return jSONObject;
    }

    private static JSONObject a(s sVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(sVar.a)) {
            jSONObject.put("score", sVar.a);
        }
        if (sVar.b != null && sVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (t a : sVar.b) {
                jSONArray.put(a(a));
            }
            jSONObject.put("index_life_detail", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(t tVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(tVar.a)) {
            jSONObject.put("life_type", tVar.a);
        }
        jSONObject.put("num", tVar.b);
        if (!TextUtils.isEmpty(tVar.c)) {
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, tVar.c);
        }
        if (!TextUtils.isEmpty(tVar.d)) {
            jSONObject.put("name", tVar.d);
        }
        if (!TextUtils.isEmpty(tVar.e)) {
            jSONObject.put("geometry", tVar.e);
        }
        return jSONObject;
    }

    private static JSONObject a(r rVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(rVar.a)) {
            jSONObject.put("score", rVar.a);
        }
        if (rVar.b != null && rVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (t a : rVar.b) {
                jSONArray.put(a(a));
            }
            jSONObject.put("index_life_detail", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(ab abVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (abVar.a != null && abVar.a.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bh a : abVar.a) {
                jSONArray.put(a(a));
            }
            jSONObject.put("pic_info", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(f fVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (fVar.a != null && fVar.a.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (e a : fVar.a) {
                jSONArray.put(a(a));
            }
            jSONObject.put("type0", jSONArray);
        }
        if (fVar.b != null && fVar.b.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (e a2 : fVar.b) {
                jSONArray2.put(a(a2));
            }
            jSONObject.put("type1", jSONArray2);
        }
        if (fVar.c != null && fVar.c.length > 0) {
            JSONArray jSONArray3 = new JSONArray();
            for (e a3 : fVar.c) {
                jSONArray3.put(a(a3));
            }
            jSONObject.put("type2", jSONArray3);
        }
        return jSONObject;
    }

    private static JSONObject a(e eVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(eVar.a)) {
            jSONObject.put("basic_price", eVar.a);
        }
        if (!TextUtils.isEmpty(eVar.b)) {
            jSONObject.put("direc", eVar.b);
        }
        if (!TextUtils.isEmpty(eVar.c)) {
            jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, eVar.c);
        }
        if (!TextUtils.isEmpty(eVar.d)) {
            jSONObject.put("front_name", eVar.d);
        }
        if (!TextUtils.isEmpty(eVar.e)) {
            jSONObject.put(H5SensorPlugin.PARAM_INTERVAL, eVar.e);
        }
        if (!TextUtils.isEmpty(eVar.f)) {
            jSONObject.put("key_name", eVar.f);
        }
        if (!TextUtils.isEmpty(eVar.g)) {
            jSONObject.put("line_id", eVar.g);
        }
        if (!TextUtils.isEmpty(eVar.h)) {
            jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, eVar.h);
        }
        if (!TextUtils.isEmpty(eVar.i)) {
            jSONObject.put("terminal_name", eVar.i);
        }
        if (!TextUtils.isEmpty(eVar.j)) {
            jSONObject.put("total_price", eVar.j);
        }
        return jSONObject;
    }

    private static JSONObject a(af afVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(afVar.a)) {
            jSONObject.put("travel_notes_url", afVar.a);
        }
        if (!TextUtils.isEmpty(afVar.b)) {
            jSONObject.put("travel_qa_url", afVar.b);
        }
        if (afVar.c != null && afVar.c.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (ag a : afVar.c) {
                jSONArray.put(a(a));
            }
            jSONObject.put("travel_notes", jSONArray);
        }
        if (afVar.d != null && afVar.d.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (ah ahVar : afVar.d) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(ahVar.a)) {
                    jSONObject2.put("question", ahVar.a);
                }
                if (!TextUtils.isEmpty(ahVar.b)) {
                    jSONObject2.put("answer", ahVar.b);
                }
                if (!TextUtils.isEmpty(ahVar.c)) {
                    jSONObject2.put("appurl", ahVar.c);
                }
                jSONArray2.put(jSONObject2);
            }
            jSONObject.put("travel_qa", jSONArray2);
        }
        return jSONObject;
    }

    private static JSONObject a(ag agVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(agVar.a)) {
            jSONObject.put("travels_anme", agVar.a);
        }
        jSONObject.put("views_num", agVar.b);
        if (!TextUtils.isEmpty(agVar.c)) {
            jSONObject.put("time", agVar.c);
        }
        if (!TextUtils.isEmpty(agVar.d)) {
            jSONObject.put("appurl", agVar.d);
        }
        if (!TextUtils.isEmpty(agVar.e)) {
            jSONObject.put("notes", agVar.e);
        }
        if (agVar.f != null && agVar.f.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bh a : agVar.f) {
                jSONArray.put(a(a));
            }
            jSONObject.put("pic_info", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(m mVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(mVar.a)) {
            jSONObject.put("course_id", mVar.a);
        }
        if (!TextUtils.isEmpty(mVar.b)) {
            jSONObject.put("course_name", mVar.b);
        }
        if (!TextUtils.isEmpty(mVar.c)) {
            jSONObject.put("course_holenum", mVar.c);
        }
        if (!TextUtils.isEmpty(mVar.d)) {
            jSONObject.put("course_kind", mVar.d);
        }
        if (!TextUtils.isEmpty(mVar.e)) {
            jSONObject.put("pic_count", mVar.e);
        }
        if (mVar.f != null && mVar.f.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (bh a : mVar.f) {
                jSONArray.put(a(a));
            }
            jSONObject.put("pic_info", jSONArray);
        }
        if (!TextUtils.isEmpty(mVar.g)) {
            jSONObject.put("order_price", mVar.g);
        }
        if (!TextUtils.isEmpty(mVar.h)) {
            jSONObject.put("order_url", mVar.h);
        }
        if (!TextUtils.isEmpty(mVar.i)) {
            jSONObject.put("designer", mVar.i);
        }
        if (!TextUtils.isEmpty(mVar.j)) {
            jSONObject.put("fwseed_name", mVar.j);
        }
        if (!TextUtils.isEmpty(mVar.k)) {
            jSONObject.put("fw_length", mVar.k);
        }
        if (!TextUtils.isEmpty(mVar.l)) {
            jSONObject.put("course_data", mVar.l);
        }
        if (!TextUtils.isEmpty(mVar.m)) {
            jSONObject.put("ggseed_name", mVar.m);
        }
        if (!TextUtils.isEmpty(mVar.n)) {
            jSONObject.put("built_time", mVar.n);
        }
        if (!TextUtils.isEmpty(mVar.o)) {
            jSONObject.put("course_area", mVar.o);
        }
        return jSONObject;
    }

    private static JSONObject a(p pVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(pVar.a)) {
            jSONObject.put("score", pVar.a);
        }
        if (pVar.b != null && pVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (q qVar : pVar.b) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(qVar.a)) {
                    jSONObject2.put("geometry", qVar.a);
                }
                if (!TextUtils.isEmpty(qVar.b)) {
                    jSONObject2.put("dining_type", qVar.b);
                }
                jSONObject2.put("num", qVar.c);
                if (!TextUtils.isEmpty(qVar.d)) {
                    jSONObject2.put("name", qVar.d);
                }
                if (!TextUtils.isEmpty(qVar.e)) {
                    jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, qVar.e);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("dining_detail", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(u uVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(uVar.a)) {
            jSONObject.put("score", uVar.a);
        }
        jSONObject.put("num_subway", uVar.b);
        jSONObject.put("num_bus", uVar.c);
        if (uVar.d != null) {
            jSONObject.put("airport", a(uVar.d));
        }
        if (uVar.e != null) {
            jSONObject.put("station", a(uVar.e));
        }
        if (uVar.f != null && uVar.f.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (w wVar : uVar.f) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(wVar.a)) {
                    jSONObject2.put("businfo_line_alias", wVar.a);
                }
                if (!TextUtils.isEmpty(wVar.b)) {
                    jSONObject2.put("geometry", wVar.b);
                }
                if (!TextUtils.isEmpty(wVar.c)) {
                    jSONObject2.put("name", wVar.c);
                }
                if (!TextUtils.isEmpty(wVar.d)) {
                    jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, wVar.d);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("subway", jSONArray);
        }
        if (uVar.g != null) {
            x xVar = uVar.g;
            JSONObject jSONObject3 = new JSONObject();
            if (!TextUtils.isEmpty(xVar.a)) {
                jSONObject3.put("road_name", xVar.a);
            }
            if (!TextUtils.isEmpty(xVar.b)) {
                jSONObject3.put("road_id", xVar.b);
            }
            if (!TextUtils.isEmpty(xVar.c)) {
                jSONObject3.put("timespan", xVar.c);
            }
            if (!TextUtils.isEmpty(xVar.d)) {
                jSONObject3.put("day_type", xVar.d);
            }
            if (!TextUtils.isEmpty(xVar.e)) {
                jSONObject3.put("road_jam", xVar.e);
            }
            jSONObject.put("traffic", jSONObject3);
        }
        if (uVar.h != null && uVar.h.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (o oVar : uVar.h) {
                JSONObject jSONObject4 = new JSONObject();
                if (!TextUtils.isEmpty(oVar.a)) {
                    jSONObject4.put("geometry", oVar.a);
                }
                if (!TextUtils.isEmpty(oVar.b)) {
                    jSONObject4.put("busi_type", oVar.b);
                }
                jSONObject4.put("num", oVar.c);
                if (!TextUtils.isEmpty(oVar.d)) {
                    jSONObject4.put("name", oVar.d);
                }
                if (!TextUtils.isEmpty(oVar.e)) {
                    jSONObject4.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, oVar.e);
                }
                jSONArray2.put(jSONObject4);
            }
            jSONObject.put(ShowRouteActionProcessor.SEARCH_TYPE_BUS, jSONArray2);
        }
        return jSONObject;
    }

    private static JSONObject a(v vVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(vVar.a)) {
            jSONObject.put("geometry", vVar.a);
        }
        if (!TextUtils.isEmpty(vVar.b)) {
            jSONObject.put("name", vVar.b);
        }
        if (!TextUtils.isEmpty(vVar.c)) {
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, vVar.c);
        }
        return jSONObject;
    }

    private static JSONObject a(cc ccVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(ccVar.a)) {
            jSONObject.put("shopid", ccVar.a);
        }
        if (!TextUtils.isEmpty(ccVar.b)) {
            jSONObject.put("title", ccVar.b);
        }
        if (!TextUtils.isEmpty(ccVar.c)) {
            jSONObject.put("userid", ccVar.c);
        }
        if (!TextUtils.isEmpty(ccVar.d)) {
            jSONObject.put("nick", ccVar.d);
        }
        if (!TextUtils.isEmpty(ccVar.e)) {
            jSONObject.put("url", ccVar.e);
        }
        jSONObject.put("is_b2c_shop", ccVar.f);
        jSONObject.put("g_percent", (double) ccVar.g);
        if (ccVar.h != null && ccVar.h.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (String put : ccVar.h) {
                jSONArray.put(put);
            }
            jSONObject.put("m_catlist", jSONArray);
        }
        jSONObject.put("sell_score", ccVar.i);
        if (ccVar.j != null) {
            cd cdVar = ccVar.j;
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("service_score", (double) cdVar.a);
            jSONObject2.put("service_com", cdVar.b);
            jSONObject2.put("service_dif", (double) cdVar.c);
            jSONObject2.put("desc_score", (double) cdVar.d);
            jSONObject2.put("desc_com", cdVar.e);
            jSONObject2.put("desc_dif", (double) cdVar.f);
            jSONObject2.put("delivery_score", (double) cdVar.g);
            jSONObject2.put("delivery_com", cdVar.h);
            jSONObject2.put("delivery_dif", (double) cdVar.i);
            jSONObject.put("score_full", jSONObject2);
        }
        return jSONObject;
    }

    private static JSONObject a(ca caVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (caVar.a != null && caVar.a.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (cb cbVar : caVar.a) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(cbVar.a)) {
                    jSONObject2.put("fl_no", cbVar.a);
                }
                if (!TextUtils.isEmpty(cbVar.b)) {
                    jSONObject2.put("fl_nona", cbVar.b);
                }
                if (!TextUtils.isEmpty(cbVar.c)) {
                    jSONObject2.put("fl_name", cbVar.c);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("fl_info", jSONArray);
        }
        if (!TextUtils.isEmpty(caVar.b)) {
            jSONObject.put("sndt_unify", caVar.b);
        }
        return jSONObject;
    }

    private static JSONObject a(bz bzVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (bzVar.a != null) {
            jSONObject.put("first_pic", a(bzVar.a));
        }
        if (bzVar.b != null) {
            jSONObject.put("first_review", a(bzVar.b));
        }
        if (bzVar.c != null && bzVar.c.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (by a : bzVar.c) {
                jSONArray.put(a(a));
            }
            jSONObject.put("user_add", jSONArray);
        }
        if (bzVar.d != null && bzVar.d.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (by a2 : bzVar.d) {
                jSONArray2.put(a(a2));
            }
            jSONObject.put("user_revise", jSONArray2);
        }
        if (bzVar.e != null && bzVar.e.length > 0) {
            JSONArray jSONArray3 = new JSONArray();
            for (by a3 : bzVar.e) {
                jSONArray3.put(a(a3));
            }
            jSONObject.put("list", jSONArray3);
        }
        return jSONObject;
    }

    private static JSONObject a(by byVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("quality_flag", byVar.a);
        if (!TextUtils.isEmpty(byVar.b)) {
            jSONObject.put("update_flag", byVar.b);
        }
        if (!TextUtils.isEmpty(byVar.c)) {
            jSONObject.put("review_id", byVar.c);
        }
        if (!TextUtils.isEmpty(byVar.d)) {
            jSONObject.put("author_profileurl", byVar.d);
        }
        if (!TextUtils.isEmpty(byVar.e)) {
            jSONObject.put("type", byVar.e);
        }
        if (!TextUtils.isEmpty(byVar.f)) {
            jSONObject.put("review", byVar.f);
        }
        if (!TextUtils.isEmpty(byVar.g)) {
            jSONObject.put("author", byVar.g);
        }
        jSONObject.put("score", (double) byVar.h);
        jSONObject.put("like_num", byVar.i);
        if (!TextUtils.isEmpty(byVar.j)) {
            jSONObject.put("time", byVar.j);
        }
        if (!TextUtils.isEmpty(byVar.k)) {
            jSONObject.put("src_type", byVar.k);
        }
        if (!TextUtils.isEmpty(byVar.l)) {
            jSONObject.put("nickname", byVar.l);
        }
        return jSONObject;
    }

    private static JSONObject a(i iVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(iVar.a)) {
            jSONObject.put("tag_category", iVar.a);
        }
        if (!TextUtils.isEmpty(iVar.b)) {
            jSONObject.put("place_flag", iVar.b);
        }
        jSONObject.put("low_price_area", iVar.c);
        if (!TextUtils.isEmpty(iVar.d)) {
            jSONObject.put("article_id", iVar.d);
        }
        if (!TextUtils.isEmpty(iVar.e)) {
            jSONObject.put("brief", iVar.e);
        }
        if (!TextUtils.isEmpty(iVar.f)) {
            jSONObject.put("brief_intro", iVar.f);
        }
        if (!TextUtils.isEmpty(iVar.g)) {
            jSONObject.put("service", iVar.g);
        }
        if (!TextUtils.isEmpty(iVar.h)) {
            jSONObject.put("tv_series_info", iVar.h);
        }
        if (iVar.i != null && iVar.i.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (j jVar : iVar.i) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(MetaInfoXmlParser.KEY_VALVE_WEIGHT, jVar.a);
                if (!TextUtils.isEmpty(jVar.b)) {
                    jSONObject2.put("district", jVar.b);
                }
                if (!TextUtils.isEmpty(jVar.c)) {
                    jSONObject2.put("top_list_id", jVar.c);
                }
                jSONObject2.put(H5PermissionManager.level, jVar.d);
                if (!TextUtils.isEmpty(jVar.e)) {
                    jSONObject2.put("theme", jVar.e);
                }
                if (!TextUtils.isEmpty(jVar.f)) {
                    jSONObject2.put("type_code", jVar.f);
                }
                jSONObject2.put("list_no", jVar.g);
                jSONObject2.put("flag_mount", jVar.h);
                if (!TextUtils.isEmpty(jVar.i)) {
                    jSONObject2.put("type", jVar.i);
                }
                if (!TextUtils.isEmpty(jVar.j)) {
                    jSONObject2.put("dimension", jVar.j);
                }
                jSONObject2.put("top_list_type", jVar.k);
                if (!TextUtils.isEmpty(jVar.l)) {
                    jSONObject2.put(H5Param.MENU_ICON, jVar.l);
                }
                if (!TextUtils.isEmpty(jVar.m)) {
                    jSONObject2.put("toplist_template_id", jVar.m);
                }
                if (!TextUtils.isEmpty(jVar.n)) {
                    jSONObject2.put("recommend_pos_id", jVar.n);
                }
                if (!TextUtils.isEmpty(jVar.o)) {
                    jSONObject2.put("details_icon", jVar.o);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("top_list", jSONArray);
        }
        if (!TextUtils.isEmpty(iVar.j)) {
            jSONObject.put("top_list_abtest_id", iVar.j);
        }
        if (!TextUtils.isEmpty(iVar.k)) {
            jSONObject.put("complain_url", iVar.k);
        }
        return jSONObject;
    }
}
