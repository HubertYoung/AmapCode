package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.autonavi.sdk.location.LocationInstrument;
import com.uc.webview.export.internal.interfaces.IPreloadManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: h reason: default package */
/* compiled from: InfoDetailConvert */
public final class h {
    public static JSONObject a(p pVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (pVar.a != null && pVar.a.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (String put : pVar.a) {
                jSONArray.put(put);
            }
            jSONObject.put("sort", jSONArray);
        }
        if (pVar.b != null) {
            jSONObject.put("modules_data", new JSONObject(pVar.b));
        }
        if (!TextUtils.isEmpty(pVar.c)) {
            jSONObject.put("code", pVar.c);
        }
        if (!TextUtils.isEmpty(pVar.d)) {
            jSONObject.put("timestamp", pVar.d);
        }
        if (pVar.e != null) {
            jSONObject.put("baseinfo", a(pVar.e));
        }
        if (pVar.f != null && pVar.f.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (c cVar : pVar.f) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(cVar.a)) {
                    jSONObject2.put(ModulePoi.TIPS, cVar.a);
                }
                if (!TextUtils.isEmpty(cVar.b)) {
                    jSONObject2.put("type", cVar.b);
                }
                if (!TextUtils.isEmpty(cVar.c)) {
                    jSONObject2.put("name", cVar.c);
                }
                if (!TextUtils.isEmpty(cVar.d)) {
                    jSONObject2.put("url", cVar.d);
                }
                jSONArray2.put(jSONObject2);
            }
            jSONObject.put(PoiLayoutTemplate.BUTTON, jSONArray2);
        }
        if (pVar.g != null) {
            jSONObject.put(AudioUtils.CMDPLAY, a(pVar.g));
        }
        if (pVar.h != null) {
            jSONObject.put(IPreloadManager.SIR_COMMON_TYPE, a(pVar.h));
        }
        if (pVar.i != null) {
            jSONObject.put("ticket_office", a(pVar.i));
        }
        if (!TextUtils.isEmpty(pVar.j)) {
            jSONObject.put("version", pVar.j);
        }
        if (!TextUtils.isEmpty(pVar.k)) {
            jSONObject.put("result", pVar.k);
        }
        if (!TextUtils.isEmpty(pVar.l)) {
            jSONObject.put("message", pVar.l);
        }
        if (!TextUtils.isEmpty(pVar.m)) {
            jSONObject.put("modules_data_template", pVar.m);
        }
        return jSONObject;
    }

    private static JSONObject a(r rVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(rVar.a)) {
            jSONObject.put("name", rVar.a);
        }
        if (rVar.b != null && rVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (e eVar : rVar.b) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(eVar.a)) {
                    jSONObject2.put("title", eVar.a);
                }
                if (!TextUtils.isEmpty(eVar.b)) {
                    jSONObject2.put("content", eVar.b);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("data", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(d dVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (dVar.a != null && dVar.a.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (j a : dVar.a) {
                jSONArray.put(a(a));
            }
            jSONObject.put("info", jSONArray);
        }
        if (!TextUtils.isEmpty(dVar.b)) {
            jSONObject.put("txt_tts", dVar.b);
        }
        if (dVar.c != null && dVar.c.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (j a2 : dVar.c) {
                jSONArray2.put(a(a2));
            }
            jSONObject.put("time_data", jSONArray2);
        }
        if (!TextUtils.isEmpty(dVar.d)) {
            jSONObject.put("tips_info", dVar.d);
        }
        if (dVar.e != null) {
            jSONObject.put("pic_info", a(dVar.e));
        }
        if (dVar.f != null && dVar.f.length > 0) {
            JSONArray jSONArray3 = new JSONArray();
            for (j a3 : dVar.f) {
                jSONArray3.put(a(a3));
            }
            jSONObject.put("intro_data", jSONArray3);
        }
        return jSONObject;
    }

    private static JSONObject a(l lVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(lVar.a)) {
            jSONObject.put("name", lVar.a);
        }
        if (lVar.b != null && lVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (m mVar : lVar.b) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(mVar.a)) {
                    jSONObject2.put("url", mVar.a);
                }
                if (!TextUtils.isEmpty(mVar.b)) {
                    jSONObject2.put("title", mVar.b);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("piclist", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(j jVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(jVar.a)) {
            jSONObject.put("text", jVar.a);
        }
        return jSONObject;
    }

    private static JSONObject a(n nVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(nVar.a)) {
            jSONObject.put("desc", nVar.a);
        }
        if (nVar.b != null && nVar.b.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (C0089g a : nVar.b) {
                jSONArray.put(a(a));
            }
            jSONObject.put("days", jSONArray);
        }
        if (!TextUtils.isEmpty(nVar.c)) {
            jSONObject.put("name", nVar.c);
        }
        return jSONObject;
    }

    private static JSONObject a(C0089g gVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(gVar.a)) {
            jSONObject.put("info", gVar.a);
        }
        if (!TextUtils.isEmpty(gVar.b)) {
            jSONObject.put("name", gVar.b);
        }
        if (!TextUtils.isEmpty(gVar.c)) {
            jSONObject.put("day_spec", gVar.c);
        }
        if (!TextUtils.isEmpty(gVar.d)) {
            jSONObject.put("desc", gVar.d);
        }
        if (gVar.e != null && gVar.e.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (o a : gVar.e) {
                jSONArray.put(a(a));
            }
            jSONObject.put("poilist", jSONArray);
        }
        if (!TextUtils.isEmpty(gVar.f)) {
            jSONObject.put("day_duration", gVar.f);
        }
        return jSONObject;
    }

    private static JSONObject a(o oVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(oVar.a)) {
            jSONObject.put("remark", oVar.a);
        }
        if (!TextUtils.isEmpty(oVar.b)) {
            jSONObject.put("name", oVar.b);
        }
        if (!TextUtils.isEmpty(oVar.c)) {
            jSONObject.put("price", oVar.c);
        }
        if (!TextUtils.isEmpty(oVar.d)) {
            jSONObject.put("brief_intro", oVar.d);
        }
        if (!TextUtils.isEmpty(oVar.e)) {
            jSONObject.put("duration", oVar.e);
        }
        if (!TextUtils.isEmpty(oVar.f)) {
            jSONObject.put("type", oVar.f);
        }
        if (!TextUtils.isEmpty(oVar.g)) {
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, oVar.g);
        }
        if (!TextUtils.isEmpty(oVar.h)) {
            jSONObject.put("desc", oVar.h);
        }
        if (oVar.i != null && oVar.i.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (String put : oVar.i) {
                jSONArray.put(put);
            }
            jSONObject.put(ModulePoi.TIPS, jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(b bVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (bVar.a != null && bVar.a.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (a aVar : bVar.a) {
                JSONObject jSONObject2 = new JSONObject();
                if (!TextUtils.isEmpty(aVar.a)) {
                    jSONObject2.put("anchor", aVar.a);
                }
                if (!TextUtils.isEmpty(aVar.b)) {
                    jSONObject2.put("title", aVar.b);
                }
                if (!TextUtils.isEmpty(aVar.c)) {
                    jSONObject2.put("dayid", aVar.c);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("anchorinfo", jSONArray);
        }
        if (!TextUtils.isEmpty(bVar.b)) {
            jSONObject.put("name", bVar.b);
        }
        if (!TextUtils.isEmpty(bVar.c)) {
            jSONObject.put("pic", bVar.c);
        }
        if (!TextUtils.isEmpty(bVar.d)) {
            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, bVar.d);
        }
        if (!TextUtils.isEmpty(bVar.e)) {
            jSONObject.put("lat", bVar.e);
        }
        if (!TextUtils.isEmpty(bVar.f)) {
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, bVar.f);
        }
        if (bVar.g != null && bVar.g.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            for (String put : bVar.g) {
                jSONArray2.put(put);
            }
            jSONObject.put(H5Param.MENU_ICON, jSONArray2);
        }
        if (bVar.h != null) {
            defpackage.g.h hVar = bVar.h;
            JSONObject jSONObject3 = new JSONObject();
            if (hVar.a != null) {
                q qVar = hVar.a;
                JSONObject jSONObject4 = new JSONObject();
                if (!TextUtils.isEmpty(qVar.a)) {
                    jSONObject4.put("buttonid", qVar.a);
                }
                if (qVar.b != null) {
                    jSONObject4.put("const_param", new JSONObject(qVar.b));
                }
                jSONObject3.put(ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW, jSONObject4);
            }
            jSONObject.put("hide_log", jSONObject3);
        }
        return jSONObject;
    }
}
